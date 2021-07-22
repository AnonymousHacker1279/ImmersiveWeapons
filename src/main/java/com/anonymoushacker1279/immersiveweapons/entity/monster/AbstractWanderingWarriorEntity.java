package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
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
	public AbstractWanderingWarriorEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		setCombatTask();
	}

	/**
	 * Register this entity's attributes.
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ARMOR, 4.0D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		goalSelector.addGoal(4, new LookRandomlyGoal(this));
		goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		goalSelector.addGoal(3, new OpenFenceGateGoal(this, true));
		goalSelector.addGoal(1, new SwimGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CreatureEntity.class, false));
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
		// Populate weapons
		float random = this.random.nextFloat();
		if (random <= 0.5) {
			setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
		} else if (random <= 0.3) {
			setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.COBALT_SWORD.get()));
		} else {
			setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.COPPER_SWORD.get()));
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
		for (EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
			if (equipmentslottype.getType() == EquipmentSlotType.Group.ARMOR) {
				ItemStack itemstack = getItemBySlot(equipmentslottype);
				if (!flag && this.random.nextFloat() < difficultyModifier) {
					break;
				}

				flag = false;
				if (itemstack.isEmpty()) {
					Item item = getEquipmentForSlot(equipmentslottype, armorTier);
					if (item != null) {
						setItemSlot(equipmentslottype, new ItemStack(item));
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
			if (getItemInHand(Hand.MAIN_HAND).getItem() == Items.AIR) {
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
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
	}
}