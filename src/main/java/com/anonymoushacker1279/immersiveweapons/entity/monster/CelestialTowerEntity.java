package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.block.decoration.CelestialLanternBlock;
import com.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.CelestialTowerSummonGoal;
import com.anonymoushacker1279.immersiveweapons.entity.ai.goal.HoverGoal;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class CelestialTowerEntity extends Monster {

	public final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(getDisplayName(), BossBarColor.RED,
			BossBarOverlay.PROGRESS)).setDarkenScreen(true);
	private int totalWavesToSpawn = 3;
	private int waveSizeModifier = 1;
	private int wavesSpawned = 0;
	private boolean doneSpawningWaves = false;
	private static final int SPAWN_CHECK_RADIUS = CommonConfig.CELESTIAL_TOWER_SPAWN_CHECK_RADIUS.get();

	public CelestialTowerEntity(EntityType<? extends Monster> type, Level level) {
		super(type, level);
		setNoGravity(true);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 240.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.01F)
				.add(Attributes.KNOCKBACK_RESISTANCE, 999.0f)
				.add(Attributes.ATTACK_DAMAGE, 6.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 2.0D)
				.add(Attributes.ARMOR, 5.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(1, new HoverGoal(this));
		goalSelector.addGoal(2, new CelestialTowerSummonGoal(this));
		goalSelector.addGoal(6, new RandomLookAroundGoal(this));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.CELESTIAL_TOWER_AMBIENT.get();
	}


	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
		return DeferredRegistryHandler.CELESTIAL_TOWER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.CELESTIAL_TOWER_DEATH.get();
	}

	@Override
	public @NotNull MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
		return 9F;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty,
	                                    @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData,
	                                    @Nullable CompoundTag pDataTag) {

		pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

		teleportTo(getX(), getY() + 2, getZ());

		bossEvent.setProgress(0f);

		// Add more waves based on difficulty + random modifier
		// Also increase the XP dropped
		if (pDifficulty.getDifficulty() == Difficulty.EASY) {
			totalWavesToSpawn = totalWavesToSpawn + GeneralUtilities.getRandomNumber(0, 2);
			xpReward = 25;
		} else if (pDifficulty.getDifficulty() == Difficulty.NORMAL) {
			totalWavesToSpawn = totalWavesToSpawn + GeneralUtilities.getRandomNumber(1, 4);
			waveSizeModifier = 2;
			xpReward = 50;
		} else if (pDifficulty.getDifficulty() == Difficulty.HARD) {
			totalWavesToSpawn = totalWavesToSpawn + GeneralUtilities.getRandomNumber(2, 5);
			waveSizeModifier = 3;
			xpReward = 75;
		}

		Objects.requireNonNull(getAttribute(Attributes.ARMOR)).setBaseValue(getAttributeBaseValue(Attributes.ARMOR)
				+ (totalWavesToSpawn * 5));

		return pSpawnData;
	}

	@Override
	public void tick() {
		super.tick();
		level.addParticle(ParticleTypes.LAVA, getX() + GeneralUtilities.getRandomNumber(-1d, 1.01d), getY(),
				getZ() + GeneralUtilities.getRandomNumber(-1d, 1.01d),
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
				GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
	}

	@Override
	public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
		if (pSource == DamageSource.OUT_OF_WORLD) {
			return super.hurt(pSource, pAmount); // For /kill, as the entity should never fall to death
		}
		if (doneSpawningWaves) {
			bossEvent.setProgress(getHealth() / 240f);
			return super.hurt(pSource, pAmount);
		} else {
			return false;
		}
	}

	@Override
	public void checkDespawn() {
		if (wavesSpawned <= 0 && noActionTime > 1200) {
			super.checkDespawn();
		}
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (hasCustomName()) {
			bossEvent.setName(getDisplayName());
		}
		totalWavesToSpawn = pCompound.getInt("totalWavesToSpawn");
		waveSizeModifier = pCompound.getInt("waveSizeModifier");
		wavesSpawned = pCompound.getInt("wavesSpawned");
		doneSpawningWaves = pCompound.getBoolean("doneSpawningWaves");

		if (wavesSpawned > 0) {
			bossEvent.setName(new TranslatableComponent("immersiveweapons.boss.celestial_tower.waves", wavesSpawned,
					totalWavesToSpawn));
			bossEvent.setProgress((float) wavesSpawned / totalWavesToSpawn);
		}
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("totalWavesToSpawn", totalWavesToSpawn);
		pCompound.putInt("waveSizeModifier", waveSizeModifier);
		pCompound.putInt("wavesSpawned", wavesSpawned);
		pCompound.putBoolean("doneSpawningWaves", doneSpawningWaves);
	}

	@Override
	public void setCustomName(@Nullable Component pName) {
		super.setCustomName(pName);
		bossEvent.setName(getDisplayName());
	}

	@Override
	public void startSeenByPlayer(@NotNull ServerPlayer pPlayer) {
		super.startSeenByPlayer(pPlayer);
		bossEvent.addPlayer(pPlayer);
	}

	@Override
	public void stopSeenByPlayer(@NotNull ServerPlayer pPlayer) {
		super.stopSeenByPlayer(pPlayer);
		bossEvent.removePlayer(pPlayer);
	}

	@Override
	public boolean checkSpawnRules(@NotNull LevelAccessor pLevel, @NotNull MobSpawnType pSpawnReason) {
		if (pSpawnReason == MobSpawnType.SPAWNER || pSpawnReason == MobSpawnType.SPAWN_EGG) {
			return true;
		}

		if (!pLevel.getBlockState(blockPosition().below()).isValidSpawn(pLevel, blockPosition().below(), getType())) {
			return false;
		}

		Vec3 position = position();
		List<BlockPos> ALL_TILTROS_LANTERNS = CelestialLanternBlock.ALL_TILTROS_LANTERNS;
		int nearbyLanterns = 0;

		for (BlockPos lanternPos : ALL_TILTROS_LANTERNS) {
			if (nearbyLanterns < 3) {
				if (lanternPos.distManhattan(new Vec3i(position.x, position.y, position.z)) < SPAWN_CHECK_RADIUS) {
					nearbyLanterns++;
				}
			}
		}
		
		if (nearbyLanterns == 3) {
			return false;
		} else if (nearbyLanterns == 0) {
			return true;
		} else return GeneralUtilities.getRandomNumber(0.0f, 1.0f) <= (nearbyLanterns == 2 ? 0.125f : 0.25f);
	}

	@Override
	public boolean isMaxGroupSizeReached(int pSize) {
		return pSize > 1;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	public boolean isDoneSpawningWaves() {
		return doneSpawningWaves;
	}

	public void setDoneSpawningWaves(boolean state) {
		doneSpawningWaves = state;
	}

	public int getTotalWavesToSpawn() {
		return totalWavesToSpawn;
	}

	public int getWavesSpawned() {
		return wavesSpawned;
	}

	public void setWavesSpawned(int waves) {
		wavesSpawned = waves;
	}

	public int getWaveSizeModifier() {
		return waveSizeModifier;
	}
}