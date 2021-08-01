package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractFieldMedicEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractMinutemanEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.function.Predicate;

public abstract class AbstractDyingSoldierEntity extends Monster implements RangedAttackMob {

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

	/**
	 * Constructor for AbstractDyingSoldierEntity.
	 * @param type the <code>EntityType</code> instance
	 * @param worldIn the <code>World</code> the entity is in
	 */
	AbstractDyingSoldierEntity(EntityType<? extends AbstractDyingSoldierEntity> type, Level worldIn) {
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
				.add(Attributes.ARMOR, 5.0D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 1.0D, false, 6, () -> true));
		goalSelector.addGoal(2, new OpenDoorGoal(this, false));
		goalSelector.addGoal(2, new OpenFenceGateGoal(this, false));
		targetSelector.addGoal(2, new HurtByTargetGoal(this));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractMinutemanEntity.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractFieldMedicEntity.class, true));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	/**
	 * Play the step sound.
	 * @param pos the <code>BlockPos</code> the entity is at
	 * @param blockIn the <code>BlockState</code> of the block being stepped on
	 */
	@Override
	protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
		playSound(getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

	/**
	 * Get the mob type.
	 * @return CreatureAttribute
	 */
	@Override
	public @NotNull MobType getMobType() {
		return MobType.ILLAGER;
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
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 */
	@Override
	protected void populateDefaultEquipmentSlots(@NotNull DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(DeferredRegistryHandler.FLINTLOCK_PISTOL.get()));
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
		if (!level.isClientSide) {
			goalSelector.removeGoal(aiAttackOnCollide);
			goalSelector.removeGoal(aiPistolAttack);
			ItemStack itemstack = getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Predicate.isEqual(DeferredRegistryHandler.FLINTLOCK_PISTOL.get())));
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
	 * @param target the <code>LivingEntity</code> being targeted
	 * @param distanceFactor the distance factor
	 */
	@Override
	public void performRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
		ItemStack itemstack = getProjectile(getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Predicate.isEqual(DeferredRegistryHandler.FLINTLOCK_PISTOL.get()))));
		AbstractArrow abstractArrow = fireArrow(itemstack, distanceFactor);
		if (getMainHandItem().getItem() instanceof BowItem)
			abstractArrow = ((BowItem) getMainHandItem().getItem()).customArrow(abstractArrow);
		double d0 = target.getX() - getX();
		double d1 = target.getY(0.1D) - abstractArrow.getY();
		double d2 = target.getZ() - getZ();
		double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
		abstractArrow.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - level.getDifficulty().getId() * 4));
		playSound(DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
		level.addFreshEntity(abstractArrow);
	}

	/**
	 * Fires an arrow.
	 * @param arrowStack the <code>ItemStack</code> of the arrow
	 * @param distanceFactor the distance factor for firing
	 * @return AbstractArrowEntity
	 */
	private AbstractArrow fireArrow(ItemStack arrowStack, float distanceFactor) {
		AbstractArrowItem arrowItem = (AbstractArrowItem) (arrowStack.getItem() instanceof AbstractArrowItem ? arrowStack.getItem() : DeferredRegistryHandler.IRON_MUSKET_BALL.get());
		AbstractArrow abstractArrowEntity = arrowItem.createArrow(level, arrowStack, this);
		abstractArrowEntity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return abstractArrowEntity;
	}

	/**
	 * Check if the entity can fire a projectile.
	 * @param projectileWeaponItem the <code>ProjectileWeaponItem</code> instance
	 * @return boolean
	 */
	@Override
	public boolean canFireProjectileWeapon(@NotNull ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem == DeferredRegistryHandler.FLINTLOCK_PISTOL.get();
	}

	/**
	 * Read entity NBT data.
	 * @param compound the <code>CompoundNBT</code> to read from
	 */
	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setCombatTask();
	}

	/**
	 * Set item slots.
	 * @param slotIn the <code>EquipmentSlotType</code> to set
	 * @param stack the <code>ItemStack</code> to set in the slot
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
	 * @return double
	 */
	@Override
	public double getMyRidingOffset() {
		return -0.6D;
	}
}