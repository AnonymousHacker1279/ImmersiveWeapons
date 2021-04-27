package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrowItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
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

public abstract class AbstractDecayedSoldierEntity extends MonsterEntity implements IRangedAttackMob {

	private final RangedGunAttackGoal<AbstractDecayedSoldierEntity> aiArrowAttack = new RangedGunAttackGoal<>(this, 1.0D, 20, 15.0F);
	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		@Override
		public void resetTask() {
			super.resetTask();
			AbstractDecayedSoldierEntity.this.setAggroed(false);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			super.startExecuting();
			AbstractDecayedSoldierEntity.this.setAggroed(true);
		}
	};

	protected AbstractDecayedSoldierEntity(EntityType<? extends AbstractDecayedSoldierEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCombatTask();
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D)
				.createMutableAttribute(Attributes.ARMOR, 5.0D);
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

	@Override
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.UNDEAD;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void livingTick() {
		super.livingTick();
	}

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void updateRidden() {
		super.updateRidden();
		if (this.getRidingEntity() instanceof CreatureEntity) {
			CreatureEntity creatureentity = (CreatureEntity) this.getRidingEntity();
			this.renderYawOffset = creatureentity.renderYawOffset;
		}

	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(DeferredRegistryHandler.FLINTLOCK_PISTOL.get()));
	}

	@Override
	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
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
			this.goalSelector.removeGoal(this.aiArrowAttack);
			ItemStack itemstack = this.getHeldItem(ProjectileHelper.getHandWith(this, DeferredRegistryHandler.FLINTLOCK_PISTOL.get()));
			if (itemstack.getItem() == DeferredRegistryHandler.FLINTLOCK_PISTOL.get()) {
				int i = 20;
				if (this.world.getDifficulty() != Difficulty.HARD) {
					i = 40;
				}

				this.aiArrowAttack.setAttackCooldown(i);
				this.goalSelector.addGoal(4, this.aiArrowAttack);
			} else {
				this.goalSelector.addGoal(4, this.aiAttackOnCollide);
			}

		}
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, DeferredRegistryHandler.FLINTLOCK_PISTOL.get())));
		AbstractArrowEntity abstractarrowentity = this.fireArrow(itemstack, distanceFactor);
		if (this.getHeldItemMainhand().getItem() instanceof BowItem)
			abstractarrowentity = ((BowItem) this.getHeldItemMainhand().getItem()).customArrow(abstractarrowentity);
		double d0 = target.getPosX() - this.getPosX();
		double d1 = target.getPosYHeight(0.1D) - abstractarrowentity.getPosY();
		double d2 = target.getPosZ() - this.getPosZ();
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.world.getDifficulty().getId() * 4));
		this.playSound(DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.world.addEntity(abstractarrowentity);
	}

	/**
	 * Fires an arrow
	 */
	protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
		CustomArrowItem arrowitem = (CustomArrowItem) (arrowStack.getItem() instanceof CustomArrowItem ? arrowStack.getItem() : DeferredRegistryHandler.IRON_MUSKET_BALL.get());
		AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(this.world, arrowStack, this);
		abstractarrowentity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return abstractarrowentity;
	}

	@Override
	public boolean func_230280_a_(ShootableItem p_230280_1_) {
		return p_230280_1_ == DeferredRegistryHandler.FLINTLOCK_PISTOL.get();
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setCombatTask();
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
}