package com.anonymoushacker1279.immersiveweapons.entity.passive;

import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.OpenFenceGateGoal;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.UsedSyringeItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFieldMedicEntity extends CreatureEntity {

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
	private final List<Class<? extends CreatureEntity>> checkedEntities = new ArrayList<>(4);
	private int checkForHurtEntitiesCooldown;
	private LivingEntity currentlyTargetedEntity = null;
	private LivingEntity lastTargetedEntity = null;
	private int unlockLastTargetedEntityCooldown = 0;

	/**
	 * Constructor for AbstractFieldMedicEntity.
	 * @param type the <code>EntityType</code> instance
	 * @param worldIn the <code>World</code> the entity is in
	 */
	protected AbstractFieldMedicEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(type, worldIn);
		setCombatTask();

		checkedEntities.add(MinutemanEntity.class);
		checkedEntities.add(IronGolemEntity.class);
		checkedEntities.add(VillagerEntity.class);
		checkedEntities.add(AbstractFieldMedicEntity.class);
		checkForHurtEntitiesCooldown = 0;
	}

	/**
	 * Register this entity's attributes.
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 2.75D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new SwimGoal(this));
		goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		goalSelector.addGoal(3, new ReturnToVillageGoal(this, 0.6D, false));
		goalSelector.addGoal(2, new PatrolVillageGoal(this, 0.6D));
		goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		goalSelector.addGoal(4, new LookRandomlyGoal(this));
		goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		goalSelector.addGoal(3, new OpenFenceGateGoal(this, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractMinutemanEntity.class, IronGolemEntity.class));
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
		checkForHurtEntities(checkedEntities);
		if (checkForHurtEntitiesCooldown > 0) {
			checkForHurtEntitiesCooldown--;
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
			setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
			goalSelector.addGoal(12, aiAttackOnCollide);
		}
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
		if (amount > 0 && !(source.getEntity() instanceof AbstractMinutemanEntity) && !(source.getEntity() instanceof IronGolemEntity) && source.getEntity() instanceof PlayerEntity || source.getEntity() instanceof MobEntity) {
			if (source.getEntity() instanceof PlayerEntity) {
				if (((PlayerEntity) source.getEntity()).isCreative()) {
					return false;
				}
			}
			// Heal itself
			heal();
			setTarget((LivingEntity) source.getEntity());
			setCombatTask();
			// Aggro all other minutemen in the area
			List<MinutemanEntity> list = level.getEntitiesOfClass(MinutemanEntity.class, (new AxisAlignedBB(blockPosition())).inflate(48.0D, 8.0D, 48.0D));
			for (MinutemanEntity minutemanEntity : list) {
				minutemanEntity.setTarget((LivingEntity) source.getEntity());
				minutemanEntity.setPersistentAngerTarget(source.getEntity().getUUID());
			}
		}
		return false;
	}

	/**
	 * Perform the healing process on the entity.
	 */
	public void heal() {
		if (getHealth() <= getMaxHealth() / 2) {
			setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.FIRST_AID_KIT.get()));
			addEffect(new EffectInstance(Effects.REGENERATION, 240, 1, false, true));
			addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 1200, 0, false, true));
		} else {
			setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.BANDAGE.get()));
			addEffect(new EffectInstance(Effects.REGENERATION, 240, 0, false, true));
		}
	}

	/**
	 * Runs when the entity hurts a target.
	 * @param entityIn the <code>Entity</code> being hurt
	 * @return boolean
	 */
	@Override
	public boolean doHurtTarget(Entity entityIn) {
		boolean flag = super.doHurtTarget(entityIn);
		if (flag) {
			if (getMainHandItem().getItem() == DeferredRegistryHandler.USED_SYRINGE.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(0, 100);
				if (randomNumber <= 80) {
					((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.POISON, 500, 0, false, true));
					if (randomNumber <= 30) {
						entityIn.hurt(UsedSyringeItem.damageSource, 8.0F);
						if (randomNumber <= 5) {
							entityIn.level.playLocalSound(entityIn.getX(), entityIn.getY(), entityIn.getZ(), DeferredRegistryHandler.FIELD_MEDIC_ATTACK.get(), SoundCategory.HOSTILE, 1.0f, 1.0f, false);
						}
					}
				}
			}
		}
		return flag;
	}

	/**
	 * Check for hurt entities in the nearby area, and heal them.
	 * @param checkedEntities a <code>List</code> containing entity classes to check for,
	 *                        entries must extend CreatureEntity
	 */
	private void checkForHurtEntities(List<Class<? extends CreatureEntity>> checkedEntities) {
		if (checkForHurtEntitiesCooldown == 0 && currentlyTargetedEntity == null) {
			List<Entity> entity = level.getEntities(this, getBoundingBox().move(-24, -5, -24).expandTowards(24, 5, 24));
			if (!entity.isEmpty()) {
				for (Entity element : entity) {
					if (!checkedEntities.contains(element.getClass())) {
						continue;
					}
					if (element.getEntity() == lastTargetedEntity) {
						if (unlockLastTargetedEntityCooldown > 0) {
							unlockLastTargetedEntityCooldown--;
							continue;
						}
						lastTargetedEntity = null;
						continue;
					}
					if (element.showVehicleHealth()) {
						if (((LivingEntity) element).getHealth() < ((LivingEntity) element).getMaxHealth()) {
							currentlyTargetedEntity = (LivingEntity) element.getEntity();
							checkForHurtEntitiesCooldown = 100;
							return;
						} else {
							heal();
						}
					}
				}
				checkForHurtEntitiesCooldown = 100;
			}
		} else if (currentlyTargetedEntity != null) {
			getNavigation().moveTo(currentlyTargetedEntity, 1.0D);
			if (distanceTo(currentlyTargetedEntity) <= 1.5 && canSee(currentlyTargetedEntity)) {
				if (currentlyTargetedEntity.getHealth() <= currentlyTargetedEntity.getMaxHealth() / 2) {
					setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.FIRST_AID_KIT.get()));
					currentlyTargetedEntity.addEffect(new EffectInstance(Effects.REGENERATION, 240, 1, false, true));
					currentlyTargetedEntity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 1200, 0, false, true));
				} else {
					setItemInHand(Hand.MAIN_HAND, new ItemStack(DeferredRegistryHandler.BANDAGE.get()));
					currentlyTargetedEntity.addEffect(new EffectInstance(Effects.REGENERATION, 240, 0, false, true));
				}
				lastTargetedEntity = currentlyTargetedEntity;
				unlockLastTargetedEntityCooldown = 200;
				currentlyTargetedEntity = null;
			}
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