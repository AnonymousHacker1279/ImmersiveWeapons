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
			AbstractDyingSoldierEntity.this.setAggressive(false);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			super.start();
			AbstractDyingSoldierEntity.this.setAggressive(true);
		}
	};

	protected AbstractDyingSoldierEntity(EntityType<? extends AbstractDyingSoldierEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCombatTask();
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
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 1.0D, false, 6, this::isBreakDoorsTaskSet));
		this.goalSelector.addGoal(2, new OpenDoorGoal(this, false));
		this.goalSelector.addGoal(2, new OpenFenceGateGoal(this, false));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractMinutemanEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractFieldMedicEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
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
		if (this.getVehicle() instanceof CreatureEntity) {
			CreatureEntity creatureentity = (CreatureEntity) this.getVehicle();
			this.yBodyRot = creatureentity.yBodyRot;
		}

	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(DeferredRegistryHandler.FLINTLOCK_PISTOL.get()));
	}

	@Override
	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.populateDefaultEquipmentSlots(difficultyIn);
		this.populateDefaultEquipmentEnchantments(difficultyIn);
		this.setCombatTask();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficultyIn.getSpecialMultiplier());
		if (this.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int i = localdate.get(ChronoField.DAY_OF_MONTH);
			int j = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
				this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				this.armorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
			}
		}

		return spawnDataIn;
	}

	/**
	 * sets this entity's combat AI.
	 */
	public void setCombatTask() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.aiAttackOnCollide);
			this.goalSelector.removeGoal(this.aiPistolAttack);
			ItemStack itemstack = this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, DeferredRegistryHandler.FLINTLOCK_PISTOL.get()));
			if (itemstack.getItem() == DeferredRegistryHandler.FLINTLOCK_PISTOL.get()) {
				int i = 20;
				if (this.level.getDifficulty() != Difficulty.HARD) {
					i = 40;
				}

				this.aiPistolAttack.setAttackCooldown(i);
				this.goalSelector.addGoal(4, this.aiPistolAttack);
			} else {
				this.goalSelector.addGoal(4, this.aiAttackOnCollide);
			}

		}
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, DeferredRegistryHandler.FLINTLOCK_PISTOL.get())));
		AbstractArrowEntity abstractarrowentity = this.fireArrow(itemstack, distanceFactor);
		if (this.getMainHandItem().getItem() instanceof BowItem)
			abstractarrowentity = ((BowItem) this.getMainHandItem().getItem()).customArrow(abstractarrowentity);
		double d0 = target.getX() - this.getX();
		double d1 = target.getY(0.1D) - abstractarrowentity.getY();
		double d2 = target.getZ() - this.getZ();
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level.getDifficulty().getId() * 4));
		this.playSound(DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level.addFreshEntity(abstractarrowentity);
	}

	/**
	 * Fires an arrow
	 */
	protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
		CustomArrowItem arrowitem = (CustomArrowItem) (arrowStack.getItem() instanceof CustomArrowItem ? arrowStack.getItem() : DeferredRegistryHandler.IRON_MUSKET_BALL.get());
		AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(this.level, arrowStack, this);
		abstractarrowentity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return abstractarrowentity;
	}

	@Override
	public boolean canFireProjectileWeapon(ShootableItem p_230280_1_) {
		return p_230280_1_ == DeferredRegistryHandler.FLINTLOCK_PISTOL.get();
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.setCombatTask();
	}

	@Override
	public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {
		super.setItemSlot(slotIn, stack);
		if (!this.level.isClientSide) {
			this.setCombatTask();
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