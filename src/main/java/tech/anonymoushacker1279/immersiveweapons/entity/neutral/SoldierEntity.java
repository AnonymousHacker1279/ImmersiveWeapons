package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class SoldierEntity extends PathfinderMob implements NeutralMob, GrantAdvancementOnDiscovery {

	private static final UniformInt ANGER_TICK_RANGE = TimeUtil.rangeOfSeconds(20, 39);
	protected int angerTime;
	@Nullable
	protected UUID targetUUID;
	protected final List<Class<? extends Entity>> ignoresDamageFrom;

	protected SoldierEntity(EntityType<? extends PathfinderMob> entityType, Level level, List<Class<? extends Entity>> ignoresDamageFrom) {
		super(entityType, level);
		this.ignoresDamageFrom = ignoresDamageFrom;
	}

	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(4, new OpenDoorGoal(this, true));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D, 0.35f));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {

		populateDefaultEquipmentSlots(random, difficulty);
		populateDefaultEquipmentEnchantments(level, random, difficulty);
		prepareForCombat();
		setCanPickUpLoot(true);

		if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate date = LocalDate.now();
			int day = date.getDayOfMonth();
			int month = date.getMonth().getValue();
			if (month == 10 && day == 31 && random.nextFloat() < 0.25F) {
				setItemSlot(EquipmentSlot.HEAD,
						new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));

				armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		xpReward = calculateXPDropAmount(difficulty.getSpecialMultiplier());

		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	private int calculateXPDropAmount(float difficultyMultiplier) {
		int baseXP = Mth.ceil(100 * difficultyMultiplier);

		int armorXP = 0;
		for (ItemStack stack : getArmorSlots()) {
			if (!stack.isEmpty()) {
				armorXP += 2 + random.nextInt(3);

				if (stack.isEnchanted()) {
					armorXP += 2 + random.nextInt(3);
				}
			}
		}

		int weaponXP = 0;
		if (!getMainHandItem().isEmpty()) {
			weaponXP += 2 + random.nextInt(3);

			if (getMainHandItem().isEnchanted()) {
				weaponXP += 2 + random.nextInt(3);
			}
		}

		return baseXP + armorXP + weaponXP;
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack pCandidate, ItemStack pExisting) {
		if (pCandidate.getItem() instanceof AbstractGunItem && pExisting.getItem() instanceof AbstractGunItem) {
			return true;
		}

		return super.canReplaceCurrentItem(pCandidate, pExisting);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		checkForDiscovery(this);
		if (!level().isClientSide) {
			updatePersistentAnger((ServerLevel) level(), true);
		}
	}

	@Override
	public boolean canAttackType(EntityType<?> type) {
		return type != getType() && type != EntityType.CREEPER;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getEntity() != null) {
			for (Class<? extends Entity> clazz : ignoresDamageFrom) {
				if (clazz.isInstance(source.getEntity())) {
					return false;
				}
			}

			if (source.getEntity() instanceof Player player) {
				if (player.isCreative()) {
					return super.hurt(source, amount);
				} else {
					if (Accessory.isAccessoryActive(player, getPeaceAccessory())) {
						return false;
					}
				}
			}

			if (source.getEntity() instanceof LivingEntity livingEntity) {
				prepareForCombat();
				setTarget(livingEntity);
				setPersistentAngerTarget(source.getEntity().getUUID());

				return super.hurt(source, amount);
			}
		}

		return super.hurt(source, amount);
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		EntityDimensions dimensions = super.getDefaultDimensions(pose);
		return dimensions.withEyeHeight(1.74f);
	}

	@Override
	public void startPersistentAngerTimer() {
		setRemainingPersistentAngerTime(ANGER_TICK_RANGE.sample(random));
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return angerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int time) {
		angerTime = time;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return targetUUID;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID target) {
		targetUUID = target;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		readPersistentAngerSaveData(level(), compound);
		prepareForCombat();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);

		addPersistentAngerSaveData(compound);
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		playSound(SoundEventRegistry.SOLDIER_STEP.get(), 0.15F, 1.0F);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEventRegistry.SOLDIER_AMBIENT.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return getRandom().nextIntBetweenInclusive(240, 1600);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEventRegistry.SOLDIER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEventRegistry.SOLDIER_DEATH.get();
	}

	@Override
	public boolean canBeLeashed() {
		return false;
	}

	protected abstract void prepareForCombat();

	protected abstract Item getPeaceAccessory();

	protected abstract Item getAggroAccessory();

	protected boolean canTargetEntityWhenHurt(LivingEntity entity) {
		if (entity instanceof Player player) {
			return !player.isCreative()
					&& ((isAngryAt(player) || Accessory.isAccessoryActive(player, getAggroAccessory()))
					&& !Accessory.isAccessoryActive(player, getPeaceAccessory()));
		} else {
			for (Class<? extends Entity> clazz : ignoresDamageFrom) {
				if (clazz.isInstance(entity)) {
					return false;
				}
			}
		}

		return true;
	}

	protected abstract boolean canTargetPlayer(LivingEntity entity);
}