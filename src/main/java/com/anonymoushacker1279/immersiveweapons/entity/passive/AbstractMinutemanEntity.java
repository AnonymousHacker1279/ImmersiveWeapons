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
			AbstractMinutemanEntity.this.setAggressive(false);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			super.start();
			AbstractMinutemanEntity.this.setAggressive(true);
		}
	};
	private int angerTime;
	private UUID targetUUID;

	protected AbstractMinutemanEntity(EntityType<? extends AbstractMinutemanEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCombatTask();
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 4.5D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new ReturnToVillageGoal(this, 0.6D, false));
		this.goalSelector.addGoal(3, new PatrolVillageGoal(this, 0.6D));
		this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(3, new OpenFenceGateGoal(this, true));
		this.targetSelector.addGoal(12, new HurtByTargetGoal(this, AbstractMinutemanEntity.class, IronGolemEntity.class));
		this.targetSelector.addGoal(5, new DefendVillageTargetGoal(this));
		this.targetSelector.addGoal(12, new NearestAttackableTargetGoal<>(this, AbstractDyingSoldierEntity.class, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 8, true, false, this::isAngryAt));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, MobEntity.class, 10, false, false, (targetPredicate) -> targetPredicate instanceof IMob && !(targetPredicate instanceof AbstractMinutemanEntity) && !(targetPredicate instanceof IronGolemEntity) && !(targetPredicate instanceof CreeperEntity)));
		this.targetSelector.addGoal(1, new ResetAngerGoal<>(this, false));
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
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerWorld) this.level, true);
		}
	}

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void rideTick() {
		super.rideTick();
		if (this.getVehicle() instanceof CreatureEntity) {
			CreatureEntity creatureEntity = (CreatureEntity) this.getVehicle();
			this.yBodyRot = creatureEntity.yBodyRot;
		}

	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(DeferredRegistryHandler.BLUNDERBUSS.get()));
	}

	@Override
	@Nullable
	public ILivingEntityData finalizeSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
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
			this.goalSelector.removeGoal(this.aiShotgunAttack);
			ItemStack itemstack = this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, DeferredRegistryHandler.BLUNDERBUSS.get()));
			if (itemstack.getItem() == DeferredRegistryHandler.BLUNDERBUSS.get()) {
				int i = 25;
				if (this.level.getDifficulty() != Difficulty.HARD) {
					i = 45;
				}

				this.aiShotgunAttack.setAttackCooldown(i);
				this.goalSelector.addGoal(16, this.aiShotgunAttack);
			} else {
				this.goalSelector.addGoal(12, this.aiAttackOnCollide);
			}

		}
	}

	@Override
	protected void doPush(Entity entityIn) {
		if (entityIn instanceof IMob && !(entityIn instanceof MinutemanEntity) && !(entityIn instanceof IronGolemEntity) && this.getRandom().nextInt(20) == 0) {
			this.setTarget((LivingEntity) entityIn);
		}

		super.doPush(entityIn);
	}

	@Override
	public boolean canAttackType(EntityType<?> typeIn) {
		return typeIn != DeferredRegistryHandler.MINUTEMAN_ENTITY.get() && typeIn != EntityType.CREEPER;
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, DeferredRegistryHandler.BLUNDERBUSS.get())));
		AbstractArrowEntity abstractBulletEntity = this.fireArrow(itemstack, distanceFactor);
		if (this.getMainHandItem().getItem() instanceof BowItem)
			abstractBulletEntity = ((BowItem) this.getMainHandItem().getItem()).customArrow(abstractBulletEntity);
		double d0 = target.getX() - this.getX();
		double d1 = target.getY(0.12D) - abstractBulletEntity.getY();
		double d2 = target.getZ() - this.getZ();
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		for (int i = 0; i <= 4; i++) {
			abstractBulletEntity.setKnockback(3);
			abstractBulletEntity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, 18 - this.level.getDifficulty().getId() * 4 + GeneralUtilities.getRandomNumber(0.2f, 0.8f));
		}
		this.playSound(DeferredRegistryHandler.BLUNDERBUSS_FIRE.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level.addFreshEntity(abstractBulletEntity);
	}

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
	 * Fires an arrow
	 */
	protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
		CustomArrowItem arrowitem = (CustomArrowItem) (arrowStack.getItem() instanceof CustomArrowItem ? arrowStack.getItem() : DeferredRegistryHandler.COPPER_MUSKET_BALL.get());
		AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(this.level, arrowStack, this);
		abstractarrowentity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return abstractarrowentity;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		super.hurt(source, amount);
		this.setCombatTask();
		if (amount > 0 && !(source.getEntity() instanceof AbstractMinutemanEntity) && !(source.getEntity() instanceof IronGolemEntity) && source.getEntity() instanceof PlayerEntity || source.getEntity() instanceof MobEntity) {
			if (source.getEntity() instanceof PlayerEntity) {
				if (((PlayerEntity) source.getEntity()).isCreative()) {
					return false;
				}
			}
			this.setTarget((LivingEntity) source.getEntity());
			this.setPersistentAngerTarget(source.getEntity().getUUID());
			// Aggro all other minutemen in the area
			List<MinutemanEntity> list = this.level.getEntitiesOfClass(MinutemanEntity.class, (new AxisAlignedBB(this.blockPosition())).inflate(24.0D, 8.0D, 24.0D));
			for (MinutemanEntity minutemanEntity : list) {
				minutemanEntity.setTarget((LivingEntity) source.getEntity());
				minutemanEntity.setPersistentAngerTarget(source.getEntity().getUUID());
			}
		}
		return false;
	}

	@Override
	public boolean canFireProjectileWeapon(ShootableItem shootableItem) {
		return shootableItem == DeferredRegistryHandler.BLUNDERBUSS.get();
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.readPersistentAngerSaveData((ServerWorld) this.level, compound);
		this.setCombatTask();
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		this.addPersistentAngerSaveData(compound);
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
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(tickRange.randomValue(this.random));
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.angerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int time) {
		this.angerTime = time;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.targetUUID;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID target) {
		this.targetUUID = target;
	}
}