package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public abstract class AbstractWanderingWarriorEntity extends MonsterEntity {

	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		@Override
		public void stop() {
			super.stop();
			AbstractWanderingWarriorEntity.this.setAggressive(false);
		}

		@Override
		public void start() {
			super.start();
			AbstractWanderingWarriorEntity.this.setAggressive(true);
		}
	};

	public AbstractWanderingWarriorEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.setCombatTask();
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ARMOR, 4.0D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(3, new OpenFenceGateGoal(this, true));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CreatureEntity.class, false));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
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
		// Populate weapons
		float random = this.random.nextFloat();
		if (random <= 0.5) {
			this.setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
		} else if (random <= 0.3) {
			this.setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.COBALT_SWORD.get()));
		} else {
			this.setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.COPPER_SWORD.get()));
		}
		// Populate armor
		boolean flag = true;
		int armorTier = 0;
		if (this.random.nextFloat() < 0.1F) {
			armorTier++;
		}
		if (this.random.nextFloat() < 0.1F) {
			armorTier++;
		}
		float difficultyModifier = this.level.getDifficulty() == Difficulty.HARD ? 0.3F : 0.75F;
		for (EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
			if (equipmentslottype.getType() == EquipmentSlotType.Group.ARMOR) {
				ItemStack itemstack = this.getItemBySlot(equipmentslottype);
				if (!flag && this.random.nextFloat() < difficultyModifier) {
					break;
				}

				flag = false;
				if (itemstack.isEmpty()) {
					Item item = getEquipmentForSlot(equipmentslottype, armorTier);
					if (item != null) {
						this.setItemSlot(equipmentslottype, new ItemStack(item));
					}
				}
			}
		}
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
			if (this.getItemInHand(Hand.MAIN_HAND).getItem() == Items.AIR) {
				this.populateDefaultEquipmentSlots(this.level.getCurrentDifficultyAt(this.blockPosition()));
			}
			this.goalSelector.addGoal(12, this.aiAttackOnCollide);
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		super.hurt(source, amount);
		if (amount > 0 && source.getEntity() instanceof PlayerEntity || source.getEntity() instanceof MobEntity || source.getEntity() instanceof CreatureEntity) {
			if (source.getEntity() instanceof PlayerEntity) {
				if (((PlayerEntity) source.getEntity()).isCreative()) {
					return false;
				}
			}
			this.setTarget((LivingEntity) source.getEntity());
			this.setCombatTask();
			return true;
		}
		return false;
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
	}
}