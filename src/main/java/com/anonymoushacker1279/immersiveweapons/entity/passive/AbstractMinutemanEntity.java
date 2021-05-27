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

	private int angerTime;
	private UUID targetUUID;
	private static final RangedInteger tickRange = TickRangeConverter.convertRange(20, 39);
	private final RangedShotgunAttackGoal<AbstractMinutemanEntity> aiShotgunAttack = new RangedShotgunAttackGoal<>(this, 1.0D, 25, 14.0F);
	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		@Override
		public void resetTask() {
			super.resetTask();
			AbstractMinutemanEntity.this.setAggroed(false);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			super.startExecuting();
			AbstractMinutemanEntity.this.setAggroed(true);
		}
	};

	protected AbstractMinutemanEntity(EntityType<? extends AbstractMinutemanEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCombatTask();
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
				.createMutableAttribute(Attributes.ARMOR, 4.5D);
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
		this.targetSelector.addGoal(12, new HurtByTargetGoal(this, AbstractMinutemanEntity.class));
		this.targetSelector.addGoal(5, new DefendVillageTargetGoal(this));
		this.targetSelector.addGoal(12, new NearestAttackableTargetGoal<>(this, AbstractDyingSoldierEntity.class, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 8, true, false, this::func_233680_b_));
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
	public void livingTick() {
		super.livingTick();
		if (!this.world.isRemote) {
			this.func_241359_a_((ServerWorld) this.world, true);
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
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(DeferredRegistryHandler.BLUNDERBUSS.get()));
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
			this.goalSelector.removeGoal(this.aiShotgunAttack);
			ItemStack itemstack = this.getHeldItem(ProjectileHelper.getHandWith(this, DeferredRegistryHandler.BLUNDERBUSS.get()));
			if (itemstack.getItem() == DeferredRegistryHandler.BLUNDERBUSS.get()) {
				int i = 25;
				if (this.world.getDifficulty() != Difficulty.HARD) {
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
	protected void collideWithEntity(Entity entityIn) {
		if (entityIn instanceof IMob && !(entityIn instanceof MinutemanEntity) && this.getRNG().nextInt(20) == 0) {
			this.setAttackTarget((LivingEntity) entityIn);
		}

		super.collideWithEntity(entityIn);
	}

	@Override
	public boolean canAttack(EntityType<?> typeIn) {
		return typeIn != DeferredRegistryHandler.MINUTEMAN_ENTITY.get() && typeIn != EntityType.CREEPER;
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, DeferredRegistryHandler.BLUNDERBUSS.get())));
		AbstractArrowEntity abstractBulletEntity = this.fireArrow(itemstack, distanceFactor);
		if (this.getHeldItemMainhand().getItem() instanceof BowItem)
			abstractBulletEntity = ((BowItem) this.getHeldItemMainhand().getItem()).customArrow(abstractBulletEntity);
		double d0 = target.getPosX() - this.getPosX();
		double d1 = target.getPosYHeight(0.12D) - abstractBulletEntity.getPosY();
		double d2 = target.getPosZ() - this.getPosZ();
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		for (int i = 0; i <= 4; i++) {
			abstractBulletEntity.setKnockbackStrength(3);
			abstractBulletEntity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (18 - this.world.getDifficulty().getId() * 4 + GeneralUtilities.getRandomNumber(0.2f, 0.8f)));
		}
		this.playSound(DeferredRegistryHandler.BLUNDERBUSS_FIRE.get(), 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.world.addEntity(abstractBulletEntity);
	}

	@Override
	public ItemStack findAmmo(ItemStack shootable) {
		if (shootable.getItem() instanceof ShootableItem) {
			Predicate<ItemStack> predicate = ((ShootableItem) shootable.getItem()).getAmmoPredicate();
			ItemStack itemstack = ShootableItem.getHeldAmmo(this, predicate);
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
		AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(this.world, arrowStack, this);
		abstractarrowentity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return abstractarrowentity;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		super.attackEntityFrom(source, amount);
		this.setCombatTask();
		if (amount > 0 && !(source.getTrueSource() instanceof AbstractMinutemanEntity) && !(source.getTrueSource() instanceof IronGolemEntity) && source.getTrueSource() instanceof PlayerEntity || source.getTrueSource() instanceof MobEntity) {
			if (source.getTrueSource() instanceof PlayerEntity) {
				if (((PlayerEntity) source.getTrueSource()).isCreative()) {
					return false;
				}
			}
			this.setAttackTarget((LivingEntity) source.getTrueSource());
			this.setAngerTarget(source.getTrueSource().getUniqueID());
			// Aggro all other minutemen in the area
			List<MinutemanEntity> list = this.world.getEntitiesWithinAABB(MinutemanEntity.class, (new AxisAlignedBB(this.getPosition())).grow(24.0D, 8.0D, 24.0D));
			for (MinutemanEntity minutemanEntity : list) {
				minutemanEntity.setAttackTarget((LivingEntity) source.getTrueSource());
				minutemanEntity.setAngerTarget(source.getTrueSource().getUniqueID());
			}
		}
		return false;
	}

	@Override
	public boolean func_230280_a_(ShootableItem shootableItem) {
		return shootableItem == DeferredRegistryHandler.BLUNDERBUSS.get();
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.readAngerNBT((ServerWorld) this.world, compound);
		this.setCombatTask();
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		this.writeAngerNBT(compound);
	}

	@Override
	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
		super.setItemStackToSlot(slotIn, stack);
		if (!this.world.isRemote) {
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
	public double getYOffset() {
		return -0.6D;
	}

	@Override
	public void func_230258_H__() {
		this.setAngerTime(tickRange.getRandomWithinRange(this.rand));
	}

	@Override
	public void setAngerTime(int time) {
		this.angerTime = time;
	}

	@Override
	public int getAngerTime() {
		return this.angerTime;
	}

	@Override
	public void setAngerTarget(@Nullable UUID target) {
		this.targetUUID = target;
	}

	@Override
	public UUID getAngerTarget() {
		return this.targetUUID;
	}
}