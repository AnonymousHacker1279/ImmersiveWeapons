package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractFieldMedicEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractMinutemanEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrowItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public abstract class AbstractDyingSoldierEntity extends MonsterEntity implements IRangedAttackMob {

	private final RangedGunAttackGoal<AbstractDyingSoldierEntity> aiPistolAttack = new RangedGunAttackGoal<>(this, 1.0D, 20, 15.0F);
	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		@Override
		public void stop() {
			super.stop();
			setAggressive(false);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			super.start();
			setAggressive(true);
		}
	};

	protected AbstractDyingSoldierEntity(EntityType<? extends AbstractDyingSoldierEntity> type, World worldIn) {
		super(type, worldIn);
		setCombatTask();
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 5.0D);
	}

	public boolean isBreakDoorsTaskSet() {
		return true;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new SwimGoal(this));
		goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		goalSelector.addGoal(4, new LookRandomlyGoal(this));
		goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 1.0D, false, 6, this::isBreakDoorsTaskSet));
		goalSelector.addGoal(2, new OpenDoorGoal(this, false));
		goalSelector.addGoal(2, new OpenFenceGateGoal(this, false));
		targetSelector.addGoal(2, new HurtByTargetGoal(this));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractMinutemanEntity.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractFieldMedicEntity.class, true));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		playSound(getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

	@Override
	public CreatureAttribute getMobType() {
		return CreatureAttribute.ILLAGER;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
	}

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void rideTick() {
		super.rideTick();
		if (getVehicle() instanceof CreatureEntity) {
			CreatureEntity creatureentity = (CreatureEntity) getVehicle();
			yBodyRot = creatureentity.yBodyRot;
		}

	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(DeferredRegistryHandler.FLINTLOCK_PISTOL.get()));
	}

	@Override
	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		populateDefaultEquipmentSlots(difficultyIn);
		populateDefaultEquipmentEnchantments(difficultyIn);
		setCombatTask();
		setCanPickUpLoot(random.nextFloat() < 0.55F * difficultyIn.getSpecialMultiplier());
		if (getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int i = localdate.get(ChronoField.DAY_OF_MONTH);
			int j = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && random.nextFloat() < 0.25F) {
				setItemSlot(EquipmentSlotType.HEAD, new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				armorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
			}
		}

		return spawnDataIn;
	}

	/**
	 * sets this entity's combat AI.
	 */
	public void setCombatTask() {
		if (level != null && !level.isClientSide) {
			goalSelector.removeGoal(aiAttackOnCollide);
			goalSelector.removeGoal(aiPistolAttack);
			ItemStack itemstack = getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, DeferredRegistryHandler.FLINTLOCK_PISTOL.get()));
			if (itemstack.getItem() == DeferredRegistryHandler.FLINTLOCK_PISTOL.get()) {
				int i = 20;
				if (level.getDifficulty() != Difficulty.HARD) {
					i = 40;
				}

				aiPistolAttack.setAttackCooldown(i);
				goalSelector.addGoal(4, aiPistolAttack);
			} else {
				goalSelector.addGoal(4, aiAttackOnCollide);
			}

		}
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = getProjectile(getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, DeferredRegistryHandler.FLINTLOCK_PISTOL.get())));
		AbstractArrowEntity abstractarrowentity = fireArrow(itemstack, distanceFactor);
		if (getMainHandItem().getItem() instanceof BowItem)
			abstractarrowentity = ((BowItem) getMainHandItem().getItem()).customArrow(abstractarrowentity);
		double d0 = target.getX() - getX();
		double d1 = target.getY(0.1D) - abstractarrowentity.getY();
		double d2 = target.getZ() - getZ();
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - level.getDifficulty().getId() * 4));
		playSound(DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
		level.addFreshEntity(abstractarrowentity);
	}

	/**
	 * Fires an arrow
	 */
	protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
		CustomArrowItem arrowitem = (CustomArrowItem) (arrowStack.getItem() instanceof CustomArrowItem ? arrowStack.getItem() : DeferredRegistryHandler.IRON_MUSKET_BALL.get());
		AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(level, arrowStack, this);
		abstractarrowentity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return abstractarrowentity;
	}

	@Override
	public boolean canFireProjectileWeapon(ShootableItem shootableItem) {
		return shootableItem == DeferredRegistryHandler.FLINTLOCK_PISTOL.get();
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		setCombatTask();
	}

	@Override
	public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {
		super.setItemSlot(slotIn, stack);
		if (!level.isClientSide) {
			setCombatTask();
		}

	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 1.74F;
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	@Override
	public double getMyRidingOffset() {
		return -0.6D;
	}

	@Override
	protected int decreaseAirSupply(int air) {
		return air;
	}
}