package com.anonymoushacker1279.immersiveweapons.entity.passive;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.UsedSyringeItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractFieldMedicEntity extends CreatureEntity {

	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		@Override
		public void resetTask() {
			super.resetTask();
			AbstractFieldMedicEntity.this.setAggroed(false);
		}

		@Override
		public void startExecuting() {
			super.startExecuting();
			AbstractFieldMedicEntity.this.setAggroed(true);
		}
	};
	private List<Class> checkedEntities = new ArrayList<>();
	private int checkForHurtEntitiesCooldown;
	private BlockPos PointOfInterestBlockPos;
	private int cooldownBeforeLocatingNewPOI;

	protected AbstractFieldMedicEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCombatTask();

		checkedEntities.add(MinutemanEntity.class);
		checkedEntities.add(IronGolemEntity.class);
		checkedEntities.add(VillagerEntity.class);
		checkedEntities.add(AbstractFieldMedicEntity.class);
		checkForHurtEntitiesCooldown = 0;
		cooldownBeforeLocatingNewPOI = 0;
	}

	private void spawnParticles(IParticleData particleData) {
		for (int i = 0; i < 5; ++i) {
			double d0 = this.rand.nextGaussian() * 0.02D;
			double d1 = this.rand.nextGaussian() * 0.02D;
			double d2 = this.rand.nextGaussian() * 0.02D;
			this.world.addParticle(particleData, this.getPosXRandom(1.0D), this.getPosYRandom() + 1.0D, this.getPosZRandom(1.0D), d0, d1, d2);
		}
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
				.createMutableAttribute(Attributes.ARMOR, 2.75D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new ReturnToVillageGoal(this, 0.6D, false));
		this.goalSelector.addGoal(4, new PatrolVillageGoal(this, 0.6D));
		this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(3, new OpenFenceGateGoal(this, true));
		this.goalSelector.addGoal(5, new UpdateFieldMedicPOIGoal());
		this.goalSelector.addGoal(4, new MoveToBlockGoal(this, 0.97D, 64) {
			@Override
			protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
				if (PointOfInterestBlockPos != null) {
					moveController.setMoveTo(PointOfInterestBlockPos.getX(), PointOfInterestBlockPos.getY(), PointOfInterestBlockPos.getX(), 0.97D);
					return true;
				} else {
					return false;
				}
			}
		});
		this.targetSelector.addGoal(7, new HurtByTargetGoal(this, AbstractMinutemanEntity.class, IronGolemEntity.class));
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void livingTick() {
		super.livingTick();
		this.checkForHurtEntities(this.checkedEntities);
		if (checkForHurtEntitiesCooldown > 0) {
			checkForHurtEntitiesCooldown--;
		}
	}

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void updateRidden() {
		super.updateRidden();
		if (this.getRidingEntity() instanceof CreatureEntity) {
			CreatureEntity creatureEntity = (CreatureEntity) this.getRidingEntity();
			this.renderYawOffset = creatureEntity.renderYawOffset;
		}

	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
	}

	@Override
	@Nullable
	public ILivingEntityData onInitialSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setEquipmentBasedOnDifficulty(difficultyIn);
		this.setEnchantmentBasedOnDifficulty(difficultyIn);
		this.setCombatTask();
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficultyIn.getClampedAdditionalDifficulty());
		if (this.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int i = localdate.get(ChronoField.DAY_OF_MONTH);
			int j = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && this.rand.nextFloat() < 0.25F) {
				this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				this.inventoryArmorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
			}
		}

		return spawnDataIn;
	}

	/**
	 * sets this entity's combat AI.
	 */
	public void setCombatTask() {
		if (this.world != null && !this.world.isRemote) {
			this.goalSelector.removeGoal(this.aiAttackOnCollide);
			this.setHeldItem(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
			this.goalSelector.addGoal(12, this.aiAttackOnCollide);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		super.attackEntityFrom(source, amount);
		if (amount > 0 && !(source.getTrueSource() instanceof AbstractMinutemanEntity) && !(source.getTrueSource() instanceof IronGolemEntity) && source.getTrueSource() instanceof PlayerEntity || source.getTrueSource() instanceof MobEntity) {
			if (source.getTrueSource() instanceof PlayerEntity) {
				if (((PlayerEntity) source.getTrueSource()).isCreative()) {
					return false;
				}
			}
			// Heal itself
			this.heal();
			this.setAttackTarget((LivingEntity) source.getTrueSource());
			this.setCombatTask();
			// Aggro all other minutemen in the area
			List<MinutemanEntity> list = this.world.getEntitiesWithinAABB(MinutemanEntity.class, (new AxisAlignedBB(this.getPosition())).grow(48.0D, 8.0D, 48.0D));
			for (MinutemanEntity minutemanEntity : list) {
				minutemanEntity.setAttackTarget((LivingEntity) source.getTrueSource());
				minutemanEntity.setAngerTarget(source.getTrueSource().getUniqueID());
			}
		}
		return false;
	}

	public void heal() {
		if (this.getHealth() <= this.getMaxHealth() / 2) {
			this.setHeldItem(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.FIRST_AID_KIT.get()));
			this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 240, 1, false, true));
			this.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1200, 0, false, true));
		} else {
			this.setHeldItem(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.BANDAGE.get()));
			this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 240, 0, false, true));
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = super.attackEntityAsMob(entityIn);
		if (flag) {
			if (this.getHeldItemMainhand().getItem() == DeferredRegistryHandler.USED_SYRINGE.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(0, 100);
				if (randomNumber <= 80) {
					((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 500, 0, false, true));
					if (randomNumber <= 30) {
						entityIn.attackEntityFrom(UsedSyringeItem.damageSource, 8.0F);
						if (randomNumber <= 5) {
							entityIn.world.playSound(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), DeferredRegistryHandler.FIELD_MEDIC_ATTACK.get(), SoundCategory.HOSTILE, 1.0f, 1.0f, false);
						}
					}
				}
			}
		}
		return flag;
	}

	private LivingEntity currentlyTargetedEntity = null;
	private LivingEntity lastTargetedEntity = null;
	private int unlockLastTargetedEntityCooldown = 0;

	private void checkForHurtEntities(List<Class> checkedEntities) {
		if (checkForHurtEntitiesCooldown == 0 && currentlyTargetedEntity == null) {
			List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().offset(-24, -5, -24).expand(24, 5, 24));
			if (!entity.isEmpty()) {
				for (Entity element : entity) {
					if (!checkedEntities.contains(element.getClass())) {
						continue;
					}
					if (element.getEntity() == lastTargetedEntity) {
						if (unlockLastTargetedEntityCooldown > 0) {
							unlockLastTargetedEntityCooldown--;
							continue;
						}
						lastTargetedEntity = null;
						continue;
					}
					if (element.isLiving()) {
						if (((LivingEntity) element).getHealth() < ((LivingEntity) element).getMaxHealth()) {
							currentlyTargetedEntity = (LivingEntity) element.getEntity();
							checkForHurtEntitiesCooldown = 100;
							return;
						} else {
							this.heal();
						}
					}
				}
				checkForHurtEntitiesCooldown = 100;
			}
		} else if (currentlyTargetedEntity != null) {
			this.getNavigator().tryMoveToEntityLiving(currentlyTargetedEntity, 1.0D);
			if (this.getDistance(currentlyTargetedEntity) <= 1.5 && this.canEntityBeSeen(currentlyTargetedEntity)) {
				if (currentlyTargetedEntity.getHealth() <= currentlyTargetedEntity.getMaxHealth() / 2) {
					this.setHeldItem(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.FIRST_AID_KIT.get()));
					currentlyTargetedEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 240, 1, false, true));
					currentlyTargetedEntity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1200, 0, false, true));
				} else {
					this.setHeldItem(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.BANDAGE.get()));
					currentlyTargetedEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 240, 0, false, true));
				}
				lastTargetedEntity = currentlyTargetedEntity;
				unlockLastTargetedEntityCooldown = 200;
				currentlyTargetedEntity = null;
			}
		}
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		if (this.PointOfInterestBlockPos != null) {
			compound.put("POIPos", NBTUtil.writeBlockPos(this.PointOfInterestBlockPos));
		}
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		this.PointOfInterestBlockPos = null;
		if (compound.contains("POIPos")) {
			this.PointOfInterestBlockPos = NBTUtil.readBlockPos(compound.getCompound("POIPos"));
		}
		super.readAdditional(compound);
	}

	class UpdateFieldMedicPOIGoal extends AbstractFieldMedicEntity.PassiveFieldMedicGoal {

		public UpdateFieldMedicPOIGoal() {
		}

		@Override
		public boolean canMedicStart() {
			return AbstractFieldMedicEntity.this.cooldownBeforeLocatingNewPOI == 0 && AbstractFieldMedicEntity.this.PointOfInterestBlockPos == null;
		}

		@Override
		public boolean canMedicContinue() {
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			AbstractFieldMedicEntity.this.cooldownBeforeLocatingNewPOI = 200;
			List<BlockPos> list = this.getNearbyPOIs();
			if (!list.isEmpty()) {
				for (BlockPos blockpos : list) {
					AbstractFieldMedicEntity.this.spawnParticles(ParticleTypes.SPLASH);
					AbstractFieldMedicEntity.this.PointOfInterestBlockPos = blockpos;
					return;
				}
				AbstractFieldMedicEntity.this.PointOfInterestBlockPos = list.get(0);
			}
		}

		private List<BlockPos> getNearbyPOIs() {
			BlockPos blockpos = AbstractFieldMedicEntity.this.getPosition();
			PointOfInterestManager pointOfInterestManager = ((ServerWorld) AbstractFieldMedicEntity.this.world).getPointOfInterestManager();
			Stream<PointOfInterest> stream = pointOfInterestManager.func_219146_b((pointOfInterestType) -> pointOfInterestType == DeferredRegistryHandler.FIELD_MEDIC_POI.get(), blockpos, 32, PointOfInterestManager.Status.ANY);
			return stream.map(PointOfInterest::getPos).sorted(Comparator.comparingDouble((extractor) -> extractor.distanceSq(blockpos))).collect(Collectors.toList());
		}
	}

	abstract static class PassiveFieldMedicGoal extends Goal {

		private PassiveFieldMedicGoal() {
		}

		public abstract boolean canMedicStart();

		public abstract boolean canMedicContinue();

		@Override
		public boolean shouldExecute() {
			return this.canMedicStart();
		}

		@Override
		public boolean shouldContinueExecuting() {
			return this.canMedicContinue();
		}
	}
}