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
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractFieldMedicEntity extends PathfinderMob implements GrantAdvancementOnDiscovery {

	private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false);

	private final List<Class<? extends PathfinderMob>> checkedEntities = new ArrayList<>(4);
	private int checkForHurtEntitiesCooldown;
	@Nullable
	private LivingEntity currentlyTargetedEntity = null;
	@Nullable
	private LivingEntity lastTargetedEntity = null;
	private int unlockLastTargetedEntityCooldown = 0;
	private int healCooldown = 0;
	private int randomHealTicks = 0;


	AbstractFieldMedicEntity(EntityType<? extends PathfinderMob> type, Level level) {
		super(type, level);
		setCombatTask();

		checkedEntities.add(MinutemanEntity.class);
		checkedEntities.add(IronGolem.class);
		checkedEntities.add(Villager.class);
		checkedEntities.add(AbstractFieldMedicEntity.class);
		checkForHurtEntitiesCooldown = 0;
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 2.75D);
	}

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

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		playSound(getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

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

		if (!level().isClientSide) {
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
		}

		checkForDiscovery(this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyInstance,
	                                    MobSpawnType mobSpawnType, @Nullable SpawnGroupData groupData,
	                                    @Nullable CompoundTag compoundTag) {

		groupData = super.finalizeSpawn(level, difficultyInstance, mobSpawnType, groupData, compoundTag);
		populateDefaultEquipmentSlots(random, difficultyInstance);
		populateDefaultEquipmentEnchantments(random, difficultyInstance);
		setCombatTask();
		setCanPickUpLoot(random.nextFloat() < 0.55F * difficultyInstance.getSpecialMultiplier());

		if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate date = LocalDate.now();
			int day = date.getDayOfMonth();
			int month = date.getMonth().getValue();
			if (month == 10 && day == 31 && random.nextFloat() < 0.25F) {
				setItemSlot(EquipmentSlot.HEAD,
						new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));

				armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return groupData;
	}

	private void setCombatTask() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(meleeAttackGoal);
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.USED_SYRINGE.get()));
			goalSelector.addGoal(1, meleeAttackGoal);
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		super.hurt(source, amount);
		if (amount > 0 && !(source.getEntity() instanceof AbstractMinutemanEntity)
				&& !(source.getEntity() instanceof IronGolem)
				&& source.getEntity() instanceof Player || source.getEntity() instanceof Mob) {

			if (source.getEntity() instanceof Player player) {
				if (player.isCreative()) {
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
			List<MinutemanEntity> nearbyMinutemen = level().getEntitiesOfClass(MinutemanEntity.class,
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
			setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ItemRegistry.FIRST_AID_KIT.get()));
			addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 1, false, true));
			addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, true));
		} else {
			setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ItemRegistry.BANDAGE.get()));
			addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 0, false, true));
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean canHurtTarget = super.doHurtTarget(entity);
		if (canHurtTarget && entity instanceof LivingEntity livingEntity) {
			if (getMainHandItem().getItem() == ItemRegistry.USED_SYRINGE.get()) {
				float randomNumber = livingEntity.getRandom().nextFloat();
				// Poison chance
				if (randomNumber <= 0.8f) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 500,
							0, false, true));

					// Hepatitis chance
					if (randomNumber <= 0.3f) {
						entity.hurt(IWDamageSources.USED_SYRINGE, 8.0F);
						// :)
						if (randomNumber <= 0.005f) {
							PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() ->
											level().getChunkAt(blockPosition())),
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
			List<Entity> entities = level().getEntities(this, getBoundingBox().inflate(24, 5, 24));
			if (!entities.isEmpty()) {
				for (Entity entity : entities) {
					if (!checkedEntities.contains(entity.getClass())) {
						continue;
					}

					if (entity instanceof LivingEntity livingEntity) {
						if (livingEntity == lastTargetedEntity) {
							if (unlockLastTargetedEntityCooldown > 0) {
								unlockLastTargetedEntityCooldown--;
								continue;
							}

							lastTargetedEntity = null;
							continue;
						}
						if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
							currentlyTargetedEntity = livingEntity;
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
					setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.FIRST_AID_KIT.get()));
					currentlyTargetedEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 1, false, true));
					currentlyTargetedEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, true));
				} else {
					setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.BANDAGE.get()));
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
	public void readAdditionalSaveData(CompoundTag compound) {
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
						SoundEventRegistry.FIELD_MEDIC_ATTACK.get(), SoundSource.HOSTILE,
						1.0f, 1.0f, false);
			}
		}
	}
}