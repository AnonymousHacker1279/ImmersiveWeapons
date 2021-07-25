package com.anonymoushacker1279.immersiveweapons.entity.passive;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.DefendVillageTargetGoal;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedShotgunAttackGoal;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDyingSoldierEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrowItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class AbstractMinutemanEntity extends PathfinderMob implements RangedAttackMob, NeutralMob {

	private static final UniformInt tickRange = TimeUtil.rangeOfSeconds(20, 39);
	private final RangedShotgunAttackGoal<AbstractMinutemanEntity> aiShotgunAttack = new RangedShotgunAttackGoal<>(this, 1.0D, 25, 14.0F);
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
	private int angerTime;
	private UUID targetUUID;

	/**
	 * Constructor for AbstractMinutemanEntity.
	 * @param type the <code>EntityType</code> instance
	 * @param worldIn the <code>World</code> the entity is in
	 */
	AbstractMinutemanEntity(EntityType<? extends AbstractMinutemanEntity> type, Level worldIn) {
		super(type, worldIn);
		setCombatTask();
	}

	/**
	 * Register this entity's attributes.
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 4.5D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(2, new MoveBackToVillageGoal(this, 0.6D, false));
		goalSelector.addGoal(3, new GolemRandomStrollInVillageGoal(this, 0.6D));
		goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		goalSelector.addGoal(3, new OpenFenceGateGoal(this, true));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, AbstractMinutemanEntity.class, IronGolem.class));
		targetSelector.addGoal(4, new DefendVillageTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractDyingSoldierEntity.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 8, true, false, this::isAngryAt));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, false, (targetPredicate) -> targetPredicate instanceof Enemy && !(targetPredicate instanceof AbstractMinutemanEntity) && !(targetPredicate instanceof IronGolem) && !(targetPredicate instanceof Creeper)));
		targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	/**
	 * Play the step sound.
	 * @param pos the <code>BlockPos</code> the entity is at
	 * @param blockIn the <code>BlockState</code> of the block being stepped on
	 */
	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		playSound(getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
		if (!level.isClientSide) {
			updatePersistentAnger((ServerLevel) level, true);
		}
	}

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void rideTick() {
		super.rideTick();
		if (getVehicle() instanceof PathfinderMob) {
			PathfinderMob creatureEntity = (PathfinderMob) getVehicle();
			yBodyRot = creatureEntity.yBodyRot;
		}
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 */
	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(DeferredRegistryHandler.BLUNDERBUSS.get()));
	}

	/**
	 * Finalize spawn information.
	 * @param worldIn the <code>IServerWorld</code> the entity is in
	 * @param difficultyIn the <code>DifficultyInstance</code> of the world
	 * @param reason the <code>SpawnReason</code> for the entity
	 * @param spawnDataIn the <code>ILivingEntitySpawnData</code> for the entity
	 * @param dataTag the <code>CompoundNBT</code> data tag for the entity
	 * @return ILivingEntityData
	 */
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		populateDefaultEquipmentSlots(difficultyIn);
		populateDefaultEquipmentEnchantments(difficultyIn);
		setCombatTask();
		setCanPickUpLoot(random.nextFloat() < 0.55F * difficultyIn.getSpecialMultiplier());
		if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int i = localdate.get(ChronoField.DAY_OF_MONTH);
			int j = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && random.nextFloat() < 0.25F) {
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
		if (level != null && !level.isClientSide) {
			goalSelector.removeGoal(aiAttackOnCollide);
			goalSelector.removeGoal(aiShotgunAttack);
			ItemStack itemstack = getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, DeferredRegistryHandler.BLUNDERBUSS.get()));
			if (itemstack.getItem() == DeferredRegistryHandler.BLUNDERBUSS.get()) {
				int i = 25;
				if (level.getDifficulty() != Difficulty.HARD) {
					i = 45;
				}

				aiShotgunAttack.setAttackCooldown(i);
				goalSelector.addGoal(16, aiShotgunAttack);
			} else {
				populateDefaultEquipmentSlots(level.getCurrentDifficultyAt(blockPosition()));
				goalSelector.addGoal(12, aiAttackOnCollide);
			}

		}
	}

	/**
	 * Handle push behavior.
	 * @param entityIn the <code>Entity</code> being pushed
	 */
	@Override
	protected void doPush(Entity entityIn) {
		if (entityIn instanceof Enemy && !(entityIn instanceof MinutemanEntity) && !(entityIn instanceof IronGolem) && getRandom().nextInt(20) == 0) {
			setTarget((LivingEntity) entityIn);
		}
		super.doPush(entityIn);
	}

	/**
	 * Determine if the given entity can be attacked.
	 * @param typeIn the <code>EntityType</code> being checked
	 * @return boolean
	 */
	@Override
	public boolean canAttackType(EntityType<?> typeIn) {
		return typeIn != DeferredRegistryHandler.MINUTEMAN_ENTITY.get() && typeIn != EntityType.CREEPER;
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 * @param target the <code>LivingEntity</code> being targeted
	 * @param distanceFactor the distance factor
	 */
	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = getProjectile(getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, DeferredRegistryHandler.BLUNDERBUSS.get())));
		AbstractArrow abstractBulletEntity = fireArrow(itemstack, distanceFactor);
		if (getMainHandItem().getItem() instanceof BowItem)
			abstractBulletEntity = ((BowItem) getMainHandItem().getItem()).customArrow(abstractBulletEntity);
		double d0 = target.getX() - getX();
		double d1 = target.getY(0.12D) - abstractBulletEntity.getY();
		double d2 = target.getZ() - getZ();
		double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
		for (int i = 0; i <= 4; i++) {
			abstractBulletEntity.setKnockback(3);
			abstractBulletEntity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, 18 - level.getDifficulty().getId() * 4 + GeneralUtilities.getRandomNumber(0.2f, 0.8f));
		}
		playSound(DeferredRegistryHandler.BLUNDERBUSS_FIRE.get(), 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
		level.addFreshEntity(abstractBulletEntity);
	}

	/**
	 * Get the projectile for the entity's weapon.
	 * @param shootable the <code>ItemStack</code> instance
	 * @return ItemStack
	 */
	@Override
	public ItemStack getProjectile(ItemStack shootable) {
		if (shootable.getItem() instanceof ProjectileWeaponItem) {
			Predicate<ItemStack> predicate = ((ProjectileWeaponItem) shootable.getItem()).getSupportedHeldProjectiles();
			ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
			return itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	/**
	 * Fires an arrow.
	 * @param arrowStack the <code>ItemStack</code> of the arrow
	 * @param distanceFactor the distance factor for firing
	 * @return AbstractArrowEntity
	 */
	private AbstractArrow fireArrow(ItemStack arrowStack, float distanceFactor) {
		CustomArrowItem arrowItem = (CustomArrowItem) (arrowStack.getItem() instanceof CustomArrowItem ? arrowStack.getItem() : DeferredRegistryHandler.COPPER_MUSKET_BALL.get());
		AbstractArrow abstractArrowEntity = arrowItem.createArrow(level, arrowStack, this);
		abstractArrowEntity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return abstractArrowEntity;
	}

	/**
	 * Runs when the entity is hurt.
	 * @param source the <code>DamageSource</code> instance
	 * @param amount the damage amount
	 * @return boolean
	 */
	@Override
	public boolean hurt(DamageSource source, float amount) {
		super.hurt(source, amount);
		setCombatTask();
		if (amount > 0 && !(source.getEntity() instanceof AbstractMinutemanEntity) && !(source.getEntity() instanceof IronGolem) && source.getEntity() instanceof Player || source.getEntity() instanceof Mob) {
			if (source.getEntity() instanceof Player) {
				if (((Player) source.getEntity()).isCreative()) {
					return false;
				}
			}
			setTarget((LivingEntity) source.getEntity());
			setPersistentAngerTarget(source.getEntity().getUUID());
			// Aggro all other minutemen in the area
			List<MinutemanEntity> list = level.getEntitiesOfClass(MinutemanEntity.class, (new AABB(blockPosition())).inflate(24.0D, 8.0D, 24.0D));
			for (MinutemanEntity minutemanEntity : list) {
				minutemanEntity.setTarget((LivingEntity) source.getEntity());
				minutemanEntity.setPersistentAngerTarget(source.getEntity().getUUID());
			}
		}
		return false;
	}

	/**
	 * Check if the entity can fire a projectile.
	 * @param shootableItem the <code>ShootableItem</code> instance
	 * @return boolean
	 */
	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem shootableItem) {
		return shootableItem == DeferredRegistryHandler.BLUNDERBUSS.get();
	}

	/**
	 * Read entity NBT data.
	 * @param compound the <code>CompoundNBT</code> to read from
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		readPersistentAngerSaveData((ServerLevel) level, compound);
		setCombatTask();
	}

	/**
	 * Write entity NBT data.
	 * @param compound the <code>CompoundNBT</code> to write to
	 */
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		addPersistentAngerSaveData(compound);
	}

	/**
	 * Set item slots.
	 * @param slotIn the <code>EquipmentSlotType</code> to set
	 * @param stack the <code>ItemStack</code> to set in the slot
	 */
	@Override
	public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {
		super.setItemSlot(slotIn, stack);
		if (!level.isClientSide) {
			setCombatTask();
		}
	}

	/**
	 * Get the standing eye height of the entity.
	 * @param poseIn the <code>Pose</code> instance
	 * @param sizeIn the <code>EntitySize</code> of the entity
	 * @return float
	 */
	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.74F;
	}

	/**
	 * Get the Y offset of the entity.
	 * @return double
	 */
	@Override
	public double getMyRidingOffset() {
		return -0.6D;
	}

	/**
	 * Start the anger timer
	 */
	@Override
	public void startPersistentAngerTimer() {
		setRemainingPersistentAngerTime(tickRange.sample(random));
	}

	/**
	 * Get the remaining anger time
	 * @return int
	 */
	@Override
	public int getRemainingPersistentAngerTime() {
		return angerTime;
	}

	/**
	 * Set the remaining anger time
	 * @param time the new remaining anger time
	 */
	@Override
	public void setRemainingPersistentAngerTime(int time) {
		angerTime = time;
	}

	/**
	 * Get anger target
	 * @return UUID
	 */
	@Override
	public UUID getPersistentAngerTarget() {
		return targetUUID;
	}

	/**
	 * Set the anger target
	 * @param target the <code>UUID</code> of the target
	 */
	@Override
	public void setPersistentAngerTarget(@Nullable UUID target) {
		targetUUID = target;
	}
}