package com.anonymoushacker1279.immersiveweapons.entity.passive;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.DefendVillageTargetGoal;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedShotgunAttackGoal;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDyingSoldierEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrowItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class AbstractMinutemanEntity extends CreatureEntity implements IRangedAttackMob, IAngerable {

	private static final RangedInteger tickRange = TickRangeConverter.rangeOfSeconds(20, 39);
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
	protected AbstractMinutemanEntity(EntityType<? extends AbstractMinutemanEntity> type, World worldIn) {
		super(type, worldIn);
		setCombatTask();
	}

	/**
	 * Register this entity's attributes.
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 4.5D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new SwimGoal(this));
		goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		goalSelector.addGoal(2, new ReturnToVillageGoal(this, 0.6D, false));
		goalSelector.addGoal(3, new PatrolVillageGoal(this, 0.6D));
		goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		goalSelector.addGoal(3, new LookRandomlyGoal(this));
		goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		goalSelector.addGoal(3, new OpenFenceGateGoal(this, true));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, AbstractMinutemanEntity.class, IronGolemEntity.class));
		targetSelector.addGoal(4, new DefendVillageTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractDyingSoldierEntity.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 8, true, false, this::isAngryAt));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MobEntity.class, 10, false, false, (targetPredicate) -> targetPredicate instanceof IMob && !(targetPredicate instanceof AbstractMinutemanEntity) && !(targetPredicate instanceof IronGolemEntity) && !(targetPredicate instanceof CreeperEntity)));
		targetSelector.addGoal(5, new ResetAngerGoal<>(this, false));
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
			updatePersistentAnger((ServerWorld) level, true);
		}
	}

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void rideTick() {
		super.rideTick();
		if (getVehicle() instanceof CreatureEntity) {
			CreatureEntity creatureEntity = (CreatureEntity) getVehicle();
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
		setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(DeferredRegistryHandler.BLUNDERBUSS.get()));
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
	public ILivingEntityData finalizeSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
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
	 * Set the entity's combat AI.
	 */
	public void setCombatTask() {
		if (level != null && !level.isClientSide) {
			goalSelector.removeGoal(aiAttackOnCollide);
			goalSelector.removeGoal(aiShotgunAttack);
			ItemStack itemstack = getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, DeferredRegistryHandler.BLUNDERBUSS.get()));
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
		if (entityIn instanceof IMob && !(entityIn instanceof MinutemanEntity) && !(entityIn instanceof IronGolemEntity) && getRandom().nextInt(20) == 0) {
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
		ItemStack itemstack = getProjectile(getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, DeferredRegistryHandler.BLUNDERBUSS.get())));
		AbstractArrowEntity abstractBulletEntity = fireArrow(itemstack, distanceFactor);
		if (getMainHandItem().getItem() instanceof BowItem)
			abstractBulletEntity = ((BowItem) getMainHandItem().getItem()).customArrow(abstractBulletEntity);
		double d0 = target.getX() - getX();
		double d1 = target.getY(0.12D) - abstractBulletEntity.getY();
		double d2 = target.getZ() - getZ();
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
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
		if (shootable.getItem() instanceof ShootableItem) {
			Predicate<ItemStack> predicate = ((ShootableItem) shootable.getItem()).getSupportedHeldProjectiles();
			ItemStack itemstack = ShootableItem.getHeldProjectile(this, predicate);
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
	protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
		CustomArrowItem arrowItem = (CustomArrowItem) (arrowStack.getItem() instanceof CustomArrowItem ? arrowStack.getItem() : DeferredRegistryHandler.COPPER_MUSKET_BALL.get());
		AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(level, arrowStack, this);
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
		if (amount > 0 && !(source.getEntity() instanceof AbstractMinutemanEntity) && !(source.getEntity() instanceof IronGolemEntity) && source.getEntity() instanceof PlayerEntity || source.getEntity() instanceof MobEntity) {
			if (source.getEntity() instanceof PlayerEntity) {
				if (((PlayerEntity) source.getEntity()).isCreative()) {
					return false;
				}
			}
			setTarget((LivingEntity) source.getEntity());
			setPersistentAngerTarget(source.getEntity().getUUID());
			// Aggro all other minutemen in the area
			List<MinutemanEntity> list = level.getEntitiesOfClass(MinutemanEntity.class, (new AxisAlignedBB(blockPosition())).inflate(24.0D, 8.0D, 24.0D));
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
	public boolean canFireProjectileWeapon(ShootableItem shootableItem) {
		return shootableItem == DeferredRegistryHandler.BLUNDERBUSS.get();
	}

	/**
	 * Read entity NBT data.
	 * @param compound the <code>CompoundNBT</code> to read from
	 */
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		readPersistentAngerSaveData((ServerWorld) level, compound);
		setCombatTask();
	}

	/**
	 * Write entity NBT data.
	 * @param compound the <code>CompoundNBT</code> to write to
	 */
	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		addPersistentAngerSaveData(compound);
	}

	/**
	 * Set item slots.
	 * @param slotIn the <code>EquipmentSlotType</code> to set
	 * @param stack the <code>ItemStack</code> to set in the slot
	 */
	@Override
	public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {
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
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
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
		setRemainingPersistentAngerTime(tickRange.randomValue(random));
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