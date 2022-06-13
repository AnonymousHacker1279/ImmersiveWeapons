package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.misc.UsedSyringeItem;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractFieldMedicEntity extends PathfinderMob implements GrantAdvancementOnDiscovery {

	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D,
			false) {
		@Override
		public void stop() {
			super.stop();
			setAggressive(false);
		}

		@Override
		public void start() {
			super.start();
			setAggressive(true);
		}
	};

	private final List<Class<? extends PathfinderMob>> checkedEntities = new ArrayList<>(4);
	private int checkForHurtEntitiesCooldown;
	private LivingEntity currentlyTargetedEntity = null;
	private LivingEntity lastTargetedEntity = null;
	private int unlockLastTargetedEntityCooldown = 0;
	private int healCooldown = 0;
	private int randomHealTicks = 0;

	/**
	 * Constructor for AbstractFieldMedicEntity.
	 *
	 * @param type    the <code>EntityType</code> instance
	 * @param worldIn the <code>World</code> the entity is in
	 */
	AbstractFieldMedicEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
		super(type, worldIn);
		setCombatTask();

		checkedEntities.add(MinutemanEntity.class);
		checkedEntities.add(IronGolem.class);
		checkedEntities.add(Villager.class);
		checkedEntities.add(AbstractFieldMedicEntity.class);
		checkForHurtEntitiesCooldown = 0;
	}

	/**
	 * Register this entity's attributes.
	 *
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 2.75D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(5, new MoveBackToVillageGoal(this, 0.6D, false));
		goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6D));
		goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(100, new RandomLookAroundGoal(this));
		goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractMinutemanEntity.class, IronGolem.class));
	}

	/**
	 * Play the step sound.
	 *
	 * @param pos     the <code>BlockPos</code> the entity is at
	 * @param blockIn the <code>BlockState</code> of the block being stepped on
	 */
	@Override
	protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
		playSound(getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
		checkForHurtEntities(checkedEntities);
		if (checkForHurtEntitiesCooldown > 0) {
			checkForHurtEntitiesCooldown--;
		}
		if (healCooldown > 0) {
			healCooldown--;
		}
		if (!level.isClientSide) {
			if (getHealth() <= getMaxHealth()) {
				if (randomHealTicks >= 20) {
					heal();
					randomHealTicks = 0;
				} else {
					if (random.nextFloat() <= 0.01f) {
						randomHealTicks++;
					}
				}
			}

			AABB scanningBox = new AABB(blockPosition().offset(-50, -50, -50),
					blockPosition().offset(50, 50, 50));

			for (Player player : level.getNearbyPlayers(TargetingConditions.forNonCombat(), this, scanningBox)) {
				checkForDiscovery(this, player);
			}
		}
	}

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void rideTick() {
		super.rideTick();
		if (getVehicle() instanceof PathfinderMob creatureEntity) {
			yBodyRot = creatureEntity.yBodyRot;
		}
	}

	/**
	 * Finalize spawn information.
	 *
	 * @param worldIn      the <code>IServerWorld</code> the entity is in
	 * @param difficultyIn the <code>DifficultyInstance</code> of the world
	 * @param reason       the <code>SpawnReason</code> for the entity
	 * @param spawnDataIn  the <code>ILivingEntitySpawnData</code> for the entity
	 * @param dataTag      the <code>CompoundNBT</code> data tag for the entity
	 * @return ILivingEntityData
	 */
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn,
	                                    @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn,
	                                    @Nullable CompoundTag dataTag) {

		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		populateDefaultEquipmentSlots(random, difficultyIn);
		populateDefaultEquipmentEnchantments(random, difficultyIn);
		setCombatTask();
		setCanPickUpLoot(random.nextFloat() < 0.55F * difficultyIn.getSpecialMultiplier());

		if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int day = localdate.get(ChronoField.DAY_OF_MONTH);
			int month = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (month == 10 && day == 31 && random.nextFloat() < 0.25F) {
				setItemSlot(EquipmentSlot.HEAD, new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return spawnDataIn;
	}

	/**
	 * Set the entity's combat AI.
	 */
	private void setCombatTask() {
		if (!level.isClientSide) {
			goalSelector.removeGoal(aiAttackOnCollide);
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
			goalSelector.addGoal(1, aiAttackOnCollide);
		}
	}

	/**
	 * Runs when the entity is hurt.
	 *
	 * @param source the <code>DamageSource</code> instance
	 * @param amount the damage amount
	 * @return boolean
	 */
	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		super.hurt(source, amount);
		if (amount > 0 && !(source.getEntity() instanceof AbstractMinutemanEntity)
				&& !(source.getEntity() instanceof IronGolem)
				&& source.getEntity() instanceof Player || source.getEntity() instanceof Mob) {

			if (source.getEntity() instanceof Player) {
				if (((Player) source.getEntity()).isCreative()) {
					return true;
				}
			}
			// Heal itself
			if (healCooldown == 0) {
				heal();
			}
			setTarget((LivingEntity) source.getEntity());
			setCombatTask();
			// Aggro all other minutemen in the area
			List<MinutemanEntity> nearbyMinutemen = level.getEntitiesOfClass(MinutemanEntity.class,
					(new AABB(blockPosition())).inflate(48.0D, 8.0D, 48.0D));

			for (MinutemanEntity minutemanEntity : nearbyMinutemen) {
				minutemanEntity.setTarget((LivingEntity) source.getEntity());
				minutemanEntity.setPersistentAngerTarget(source.getEntity().getUUID());
			}
			return true;
		}
		return false;
	}

	/**
	 * Perform the healing process on the entity.
	 */
	private void heal() {
		healCooldown = 100;
		if (getHealth() <= getMaxHealth() / 2) {
			setItemInHand(InteractionHand.OFF_HAND, new ItemStack(DeferredRegistryHandler.FIRST_AID_KIT.get()));
			addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 1, false, true));
			addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, true));
		} else {
			setItemInHand(InteractionHand.OFF_HAND, new ItemStack(DeferredRegistryHandler.BANDAGE.get()));
			addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 0, false, true));
		}
	}

	/**
	 * Runs when the entity hurts a target.
	 *
	 * @param entityIn the <code>Entity</code> being hurt
	 * @return boolean
	 */
	@Override
	public boolean doHurtTarget(@NotNull Entity entityIn) {
		boolean canHurtTarget = super.doHurtTarget(entityIn);
		if (canHurtTarget) {
			if (getMainHandItem().getItem() == DeferredRegistryHandler.USED_SYRINGE.get()) {
				float randomNumber = GeneralUtilities.getRandomNumber(0f, 1f);
				// Poison chance
				if (randomNumber <= 0.8f) {
					((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.POISON, 500,
							0, false, true));

					// Hepatitis chance
					if (randomNumber <= 0.3f) {
						entityIn.hurt(UsedSyringeItem.damageSource, 8.0F);
						// :)
						if (randomNumber <= 0.005f) {
							PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() ->
											level.getChunkAt(blockPosition())),
									new AbstractFieldMedicEntityPacketHandler(blockPosition()));
						}
					}
				}
			}
		}
		return canHurtTarget;
	}

	/**
	 * Check for hurt entities in the nearby area, and heal them.
	 *
	 * @param checkedEntities a <code>List</code> containing entity classes to check for,
	 *                        entries must extend CreatureEntity
	 */
	private void checkForHurtEntities(List<Class<? extends PathfinderMob>> checkedEntities) {
		if (checkForHurtEntitiesCooldown == 0 && currentlyTargetedEntity == null) {
			List<Entity> entity = level.getEntities(this, getBoundingBox().move(-24, -5, -24).expandTowards(24, 5, 24));
			if (!entity.isEmpty()) {
				for (Entity element : entity) {
					if (!checkedEntities.contains(element.getClass())) {
						continue;
					}
					if (element.getRootVehicle() == lastTargetedEntity) {
						if (unlockLastTargetedEntityCooldown > 0) {
							unlockLastTargetedEntityCooldown--;
							continue;
						}
						lastTargetedEntity = null;
						continue;
					}
					if (element.showVehicleHealth()) {
						if (((LivingEntity) element).getHealth() < ((LivingEntity) element).getMaxHealth()) {
							currentlyTargetedEntity = (LivingEntity) element.getRootVehicle();
							checkForHurtEntitiesCooldown = 100;
							return;
						} else {
							heal();
						}
					}
				}
				checkForHurtEntitiesCooldown = 100;
			}
		} else if (currentlyTargetedEntity != null) {
			getNavigation().moveTo(currentlyTargetedEntity, 1.0D);
			if (distanceTo(currentlyTargetedEntity) <= 1.5 && hasLineOfSight(currentlyTargetedEntity)) {
				if (currentlyTargetedEntity.getHealth() <= currentlyTargetedEntity.getMaxHealth() / 2) {
					setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.FIRST_AID_KIT.get()));
					currentlyTargetedEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 1, false, true));
					currentlyTargetedEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, true));
				} else {
					setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.BANDAGE.get()));
					currentlyTargetedEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 0, false, true));
				}
				lastTargetedEntity = currentlyTargetedEntity;
				unlockLastTargetedEntityCooldown = 200;
				currentlyTargetedEntity = null;
			}
		}
	}

	/**
	 * Read entity NBT data.
	 *
	 * @param compound the <code>CompoundNBT</code> to read from
	 */
	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
	}


	public record AbstractFieldMedicEntityPacketHandler(BlockPos blockPos) {

		/**
		 * Constructor for AbstractFieldMedicEntityPacketHandler.
		 *
		 * @param blockPos the <code>BlockPos</code> the packet came from
		 */
		public AbstractFieldMedicEntityPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>AbstractFieldMedicEntityPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(AbstractFieldMedicEntityPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return AbstractFieldMedicEntityPacketHandler
		 */
		public static AbstractFieldMedicEntityPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new AbstractFieldMedicEntityPacketHandler(packetBuffer.readBlockPos());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>AbstractFieldMedicEntityPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(AbstractFieldMedicEntityPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>AbstractFieldMedicEntityPacketHandler</code> message being sent
		 */
		private static void handleOnClient(AbstractFieldMedicEntityPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				minecraft.level.playLocalSound(msg.blockPos.getX(), msg.blockPos.getY(), msg.blockPos.getZ(),
						DeferredRegistryHandler.FIELD_MEDIC_ATTACK.get(), SoundSource.HOSTILE,
						1.0f, 1.0f, false);
			}
		}
	}
}