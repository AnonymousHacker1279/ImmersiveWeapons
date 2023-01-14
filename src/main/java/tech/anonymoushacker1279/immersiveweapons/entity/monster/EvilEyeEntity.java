package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.FlyAroundPlayerGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.FlyRandomlyGoal;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.*;

public class EvilEyeEntity extends FlyingMob implements Enemy, GrantAdvancementOnDiscovery {

	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(EvilEyeEntity.class,
			EntityDataSerializers.INT);
	private final List<MobEffect> lowTierDebuffs = new ArrayList<>(5);
	private final List<MobEffect> highTierDebuffs = new ArrayList<>(5);

	private final FlyRandomlyGoal flyRandomlyGoal = new FlyRandomlyGoal(this);
	@Nullable
	private Player targetedPlayer;
	private int ticksWatched;

	public EvilEyeEntity(EntityType<? extends FlyingMob> entityType, Level level) {
		super(entityType, level);

		lowTierDebuffs.add(MobEffects.LEVITATION);
		lowTierDebuffs.add(MobEffects.CONFUSION);
		lowTierDebuffs.add(MobEffects.HUNGER);
		lowTierDebuffs.add(MobEffects.DIG_SLOWDOWN);
		lowTierDebuffs.add(MobEffects.GLOWING);

		highTierDebuffs.add(MobEffects.WITHER);
		highTierDebuffs.add(MobEffects.BLINDNESS);
		highTierDebuffs.add(MobEffects.POISON);
		highTierDebuffs.add(MobEffects.WEAKNESS);
		highTierDebuffs.add(MobEffects.MOVEMENT_SLOWDOWN);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FLYING_SPEED, 0.85D)
				.add(Attributes.ARMOR, 2.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(ID_SIZE, 0);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	public void tick() {
		super.tick();

		// Find players in a 32 block radius every 16 ticks
		if (tickCount % 16 == 0) {
			Player player = level.getNearestPlayer(this, 32);
			// Check for proper targeting conditions
			if (player != null) {
				boolean validTarget = TargetingConditions.forCombat().test(this, player);
				if (validTarget) {
					targetedPlayer = player;

					// Remove the random flying goal
					goalSelector.removeGoal(flyRandomlyGoal);
				} else {
					targetedPlayer = null;
				}
			}
		}

		if (targetedPlayer == null) {
			goalSelector.addGoal(2, flyRandomlyGoal);
		}

		// Face the targeted player
		if (targetedPlayer != null) {
			lookAt(EntityAnchorArgument.Anchor.EYES, targetedPlayer.position());

			// If the entity is within 5 blocks of the player, start increasing the ticks watched
			if (distanceToSqr(targetedPlayer) < 25) {
				ticksWatched++;

				if (ticksWatched >= 100) {
					// If the entity has been watched for 100 ticks or more, there is a 5% chance every
					// 20 ticks to inflict a random debuff on the player
					if (tickCount % 20 == 0 && random.nextFloat() < 0.05F) {
						// If there are at least 3 entities within an 8 block radius, inflict a high tier debuff
						if (level.getEntitiesOfClass(EvilEyeEntity.class, getBoundingBox().inflate(8), (entity) -> true).size() >= 3) {
							MobEffect effect = highTierDebuffs.get(GeneralUtilities.getRandomNumber(0, highTierDebuffs.size() - 1));
							targetedPlayer.addEffect(new MobEffectInstance(effect, 100, 0));
						} else {
							MobEffect effect = lowTierDebuffs.get(GeneralUtilities.getRandomNumber(0, lowTierDebuffs.size() - 1));
							targetedPlayer.addEffect(new MobEffectInstance(effect, 100, 0));
						}
					}
				}
			} else {
				ticksWatched = 0;
			}
		}
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FlyAroundPlayerGoal(this));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
	                                    MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData,
	                                    @Nullable CompoundTag pDataTag) {
		xpReward = 3 * getSize();
		setSize(GeneralUtilities.getRandomNumber(1, 4));
		setHealth(getMaxHealth());
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("Size", getSize());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		setSize(pCompound.getInt("Size"));
	}

	private void updateSizeInfo() {
		refreshDimensions();
		Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(8 * (getSize() / 2.0f));
	}

	public int getSize() {
		return entityData.get(ID_SIZE);
	}

	public void setSize(int pSize) {
		entityData.set(ID_SIZE, Mth.clamp(pSize, 0, 64));
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
		if (ID_SIZE.equals(pKey)) {
			updateSizeInfo();
		}

		super.onSyncedDataUpdated(pKey);
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public EntityDimensions getDimensions(Pose pPose) {
		int size = getSize();
		EntityDimensions dimensions = super.getDimensions(pPose);
		float scaleFactor = (dimensions.width + 0.3F * (float) size) / dimensions.width;
		return dimensions.scale(scaleFactor);
	}

	@Nullable
	public Player getTargetedPlayer() {
		return targetedPlayer;
	}
}