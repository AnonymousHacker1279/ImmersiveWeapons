package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public abstract class AbstractWanderingWarriorEntity extends Monster {

	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		@Override
		public void stop() {
			super.stop();
			setAggressive(false);
		}

		@Override
		public void start() {
			super.start();
			setAggressive(true);
		}
	};

	/**
	 * Constructor for AbstractWanderingWarriorEntity.
	 * @param type the <code>EntityType</code> instance
	 * @param worldIn the <code>World</code> the entity is in
	 */
	AbstractWanderingWarriorEntity(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
		setCombatTask();
	}

	/**
	 * Register this entity's attributes.
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ARMOR, 4.0D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		goalSelector.addGoal(3, new OpenFenceGateGoal(this, true));
		goalSelector.addGoal(1, new FloatGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PathfinderMob.class, false));
		targetSelector.addGoal(2, new HurtByTargetGoal(this));
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
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		// Populate weapons
		float random = this.random.nextFloat();
		if (random <= 0.5) {
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
		} else if (random <= 0.3) {
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.COBALT_SWORD.get()));
		} else {
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.COPPER_SWORD.get()));
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
		float difficultyModifier = level.getDifficulty() == Difficulty.HARD ? 0.3F : 0.75F;
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
			if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
				ItemStack itemstack = getItemBySlot(equipmentSlot);
				if (!flag && this.random.nextFloat() < difficultyModifier) {
					break;
				}

				flag = false;
				if (itemstack.isEmpty()) {
					Item item = getEquipmentForSlot(equipmentSlot, armorTier);
					if (item != null) {
						setItemSlot(equipmentSlot, new ItemStack(item));
					}
				}
			}
		}
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
	void setCombatTask() {
		if (!level.isClientSide) {
			goalSelector.removeGoal(aiAttackOnCollide);
			if (getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.AIR) {
				populateDefaultEquipmentSlots(level.getCurrentDifficultyAt(blockPosition()));
			}
			goalSelector.addGoal(12, aiAttackOnCollide);
		}
	}

	/**
	 * Read entity NBT data.
	 * @param compound the <code>CompoundNBT</code> to read from
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
	}
}