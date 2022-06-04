package com.anonymoushacker1279.immersiveweapons.entity.neutral;

import com.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.DefendVillageTargetGoal;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedShotgunAttackGoal;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDyingSoldierEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;
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
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.*;
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

public abstract class AbstractMinutemanEntity extends PathfinderMob implements RangedAttackMob, NeutralMob, GrantAdvancementOnDiscovery {

	private static final UniformInt tickRange = TimeUtil.rangeOfSeconds(20, 39);
	private final RangedShotgunAttackGoal<AbstractMinutemanEntity> aiShotgunAttack =
			new RangedShotgunAttackGoal<>(this, 1.0D, 25, 14.0F);

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
	 *
	 * @param type  the <code>EntityType</code> instance
	 * @param level the <code>Level</code> the entity is in
	 */
	AbstractMinutemanEntity(EntityType<? extends AbstractMinutemanEntity> type, Level level) {
		super(type, level);
		setCombatTask();
	}

	/**
	 * Register this entity's attributes.
	 *
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
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8D, 0.35f));
		goalSelector.addGoal(3, new MoveBackToVillageGoal(this, 0.65D, false));
		goalSelector.addGoal(3, new MoveThroughVillageGoal(this, 1.0D, false,
				6, () -> true));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		goalSelector.addGoal(5, new MoveToBlockGoal(this, 0.65D, 24) {
			@Override
			protected boolean isValidTarget(@NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
				return pLevel.getBlockState(pPos).is(DeferredRegistryHandler.CAMP_CHAIR.get());
			}
		});

		targetSelector.addGoal(1, new HurtByTargetGoal(this, MinutemanEntity.class, IronGolem.class));
		targetSelector.addGoal(4, new DefendVillageTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractDyingSoldierEntity.class,
				true));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 8,
				true, false, this::isAngryAt));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, 10,
				true, false, (targetPredicate) -> !(targetPredicate instanceof Creeper)));
		targetSelector.addGoal(6, new ResetUniversalAngerTargetGoal<>(this, false));
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
		if (!level.isClientSide) {
			updatePersistentAnger((ServerLevel) level, true);

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
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 *
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 */
	@Override
	protected void populateDefaultEquipmentSlots(@NotNull DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(DeferredRegistryHandler.BLUNDERBUSS.get()));
	}

	/**
	 * Finalize spawn information.
	 *
	 * @param level        the <code>ServerLevelAccessor</code> the entity is in
	 * @param difficultyIn the <code>DifficultyInstance</code> of the world
	 * @param reason       the <code>SpawnReason</code> for the entity
	 * @param spawnDataIn  the <code>ILivingEntitySpawnData</code> for the entity
	 * @param dataTag      the <code>CompoundNBT</code> data tag for the entity
	 * @return ILivingEntityData
	 */
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficultyIn,
	                                    @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn,
	                                    @Nullable CompoundTag dataTag) {

		spawnDataIn = super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);

		populateDefaultEquipmentSlots(difficultyIn);
		populateDefaultEquipmentEnchantments(difficultyIn);
		setCombatTask();
		setCanPickUpLoot(random.nextFloat() < 0.55F * difficultyIn.getSpecialMultiplier());

		// Put pumpkins in the head slot on Halloween
		if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int day = localdate.get(ChronoField.DAY_OF_MONTH);
			int month = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (month == 10 && day == 31 && random.nextFloat() < 0.25F) {
				setItemSlot(EquipmentSlot.HEAD,
						new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
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
			goalSelector.removeGoal(aiShotgunAttack);
			ItemStack itemInHand = getItemInHand(ProjectileUtil.getWeaponHoldingHand(this,
					Predicate.isEqual(DeferredRegistryHandler.BLUNDERBUSS.get())));

			if (itemInHand.getItem() == DeferredRegistryHandler.BLUNDERBUSS.get()) {
				int cooldown = 25;
				if (level.getDifficulty() != Difficulty.HARD) {
					cooldown = 45;
				}

				aiShotgunAttack.setAttackCooldown(cooldown);
				goalSelector.addGoal(16, aiShotgunAttack);
			} else {
				populateDefaultEquipmentSlots(level.getCurrentDifficultyAt(blockPosition()));
				goalSelector.addGoal(12, aiAttackOnCollide);
			}
		}
	}

	/**
	 * Handle push behavior.
	 *
	 * @param entityIn the <code>Entity</code> being pushed
	 */
	@Override
	protected void doPush(@NotNull Entity entityIn) {
		if (entityIn instanceof Enemy && !(entityIn instanceof MinutemanEntity) && !(entityIn instanceof IronGolem)
				&& getRandom().nextInt(20) == 0) {

			setTarget((LivingEntity) entityIn);
		}
		super.doPush(entityIn);
	}

	/**
	 * Determine if the given entity can be attacked.
	 *
	 * @param typeIn the <code>EntityType</code> being checked
	 * @return boolean
	 */
	@Override
	public boolean canAttackType(@NotNull EntityType<?> typeIn) {
		return typeIn != DeferredRegistryHandler.MINUTEMAN_ENTITY.get() && typeIn != EntityType.CREEPER;
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 *
	 * @param target         the <code>LivingEntity</code> being targeted
	 * @param distanceFactor the distance factor
	 */
	@Override
	public void performRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
		// Fire four bullets for the blunderbuss
		for (int i = 0; i <= 4; i++) {
			BulletEntity bulletEntity = fireBullet(new ItemStack(DeferredRegistryHandler.COPPER_MUSKET_BALL.get()),
					distanceFactor);

			double deltaX = target.getX() - getX();
			double deltaY = target.getY(0.1D) - bulletEntity.getY();
			double deltaZ = target.getZ() - getZ();
			double sqrtXZ = Mth.sqrt((float) (deltaX * deltaX + deltaZ * deltaZ));

			bulletEntity.setKnockback(3);
			bulletEntity.setOwner(this);

			bulletEntity.shoot(deltaX + GeneralUtilities.getRandomNumber(-1.0f, 1.0f),
					deltaY + sqrtXZ * 0.2D + GeneralUtilities.getRandomNumber(-0.375f, 0.375f), deltaZ, 1.6F,
					18 - level.getDifficulty().getId() * 4 + GeneralUtilities.getRandomNumber(0.2f, 0.8f));
			level.addFreshEntity(bulletEntity);
		}
		playSound(DeferredRegistryHandler.BLUNDERBUSS_FIRE.get(), 1.0F,
				1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
	}

	/**
	 * Get the projectile for the entity's weapon.
	 *
	 * @param weapon the <code>ItemStack</code> instance
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack getProjectile(ItemStack weapon) {
		if (weapon.getItem() instanceof ProjectileWeaponItem) {
			Predicate<ItemStack> predicate = ((ProjectileWeaponItem) weapon.getItem()).getSupportedHeldProjectiles();
			ItemStack heldProjectile = ProjectileWeaponItem.getHeldProjectile(this, predicate);
			return heldProjectile.isEmpty() ? new ItemStack(DeferredRegistryHandler.COPPER_MUSKET_BALL.get()) : heldProjectile;
		} else {
			return ItemStack.EMPTY;
		}
	}

	/**
	 * Fires a bullet.
	 *
	 * @param bulletStack    the <code>ItemStack</code> of the bullet
	 * @param distanceFactor the distance factor for firing
	 * @return BulletEntity
	 */
	private BulletEntity fireBullet(ItemStack bulletStack, float distanceFactor) {
		AbstractBulletItem arrowItem = (AbstractBulletItem) (bulletStack.getItem() instanceof AbstractBulletItem
				? bulletStack.getItem() : DeferredRegistryHandler.COPPER_MUSKET_BALL.get());

		BulletEntity bulletEntity = arrowItem.createBullet(level, bulletStack, this);
		bulletEntity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return bulletEntity;
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
		if (amount > 0 && !(source.getEntity() instanceof MinutemanEntity) && !(source.getEntity() instanceof IronGolem)) {

			super.hurt(source, amount);

			if (source.getEntity() instanceof Player || source.getEntity() instanceof Mob) {
				if (source.getEntity() instanceof Player) {
					if (((Player) source.getEntity()).isCreative()) {
						return false;
					}
				}

				setCombatTask();
				setTarget((LivingEntity) source.getEntity());
				setPersistentAngerTarget(source.getEntity().getUUID());

				if (getTarget() != null && getTarget().getType() == DeferredRegistryHandler.MINUTEMAN_ENTITY.get()) {
					setTarget(null);
					return false;
				}

				// Aggro all other minutemen in the area
				List<MinutemanEntity> list = level.getEntitiesOfClass(MinutemanEntity.class,
						(new AABB(blockPosition())).inflate(24.0D, 8.0D, 24.0D));

				for (MinutemanEntity minutemanEntity : list) {
					minutemanEntity.setTarget((LivingEntity) source.getEntity());
					minutemanEntity.setPersistentAngerTarget(source.getEntity().getUUID());
				}
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Check if the entity can fire a projectile.
	 *
	 * @param projectileWeaponItem the <code>ProjectileItemWeapon</code> instance
	 * @return boolean
	 */
	@Override
	public boolean canFireProjectileWeapon(@NotNull ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem == DeferredRegistryHandler.BLUNDERBUSS.get().asItem();
	}

	/**
	 * Read entity NBT data.
	 *
	 * @param compound the <code>CompoundNBT</code> to read from
	 */
	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		readPersistentAngerSaveData(level, compound);
		setCombatTask();
	}

	/**
	 * Write entity NBT data.
	 *
	 * @param compound the <code>CompoundNBT</code> to write to
	 */
	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		addPersistentAngerSaveData(compound);
	}

	/**
	 * Set item slots.
	 *
	 * @param slotIn the <code>EquipmentSlotType</code> to set
	 * @param stack  the <code>ItemStack</code> to set in the slot
	 */
	@Override
	public void setItemSlot(@NotNull EquipmentSlot slotIn, @NotNull ItemStack stack) {
		super.setItemSlot(slotIn, stack);
		if (!level.isClientSide) {
			setCombatTask();
		}
	}

	/**
	 * Get the standing eye height of the entity.
	 *
	 * @param poseIn the <code>Pose</code> instance
	 * @param sizeIn the <code>EntitySize</code> of the entity
	 * @return float
	 */
	@Override
	protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntityDimensions sizeIn) {
		return 1.74F;
	}

	/**
	 * Get the Y offset of the entity.
	 *
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
	 *
	 * @return int
	 */
	@Override
	public int getRemainingPersistentAngerTime() {
		return angerTime;
	}

	/**
	 * Set the remaining anger time
	 *
	 * @param time the new remaining anger time
	 */
	@Override
	public void setRemainingPersistentAngerTime(int time) {
		angerTime = time;
	}

	/**
	 * Get anger target
	 *
	 * @return UUID
	 */
	@Override
	public UUID getPersistentAngerTarget() {
		return targetUUID;
	}

	/**
	 * Set the anger target
	 *
	 * @param target the <code>UUID</code> of the target
	 */
	@Override
	public void setPersistentAngerTarget(@Nullable UUID target) {
		targetUUID = target;
	}
}