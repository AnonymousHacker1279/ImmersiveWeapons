package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.entity.AttackerTracker;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.WaveSummoningBoss;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.CelestialTowerSummonGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.CelestialTowerSummonMeteorGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.HoverGoal;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.saveddata.IWSavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CelestialTowerEntity extends Monster implements AttackerTracker, GrantAdvancementOnDiscovery, WaveSummoningBoss {

	public final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(getDisplayName(), BossBarColor.RED,
			BossBarOverlay.PROGRESS)).setDarkenScreen(true);
	private int totalWavesToSpawn;
	private int waveSizeModifier;
	private int wavesSpawned = 0;
	private boolean doneSpawningWaves = false;
	public final static List<CelestialTowerEntity> ALL_TOWERS = new ArrayList<>(3);

	final List<Entity> attackingEntities = new ArrayList<>(5);

	public CelestialTowerEntity(EntityType<? extends Monster> type, Level level) {
		super(type, level);
		setNoGravity(true);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 240.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 999.0f)
				.add(Attributes.ARMOR, 5.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(1, new HoverGoal(this));
		goalSelector.addGoal(2, new CelestialTowerSummonGoal(this));
		goalSelector.addGoal(3, new CelestialTowerSummonMeteorGoal(this));
		goalSelector.addGoal(6, new RandomLookAroundGoal(this));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEventRegistry.CELESTIAL_TOWER_AMBIENT.get();
	}


	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEventRegistry.CELESTIAL_TOWER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEventRegistry.CELESTIAL_TOWER_DEATH.get();
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		EntityDimensions dimensions = super.getDefaultDimensions(pose);
		return dimensions.withEyeHeight(9);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
	                                    MobSpawnType reason, @Nullable SpawnGroupData spawnData) {

		bossEvent.setProgress(0f);
		totalWavesToSpawn = 3;
		waveSizeModifier = 1;

		teleportTo(getX(), getY() + 2, getZ());
		ALL_TOWERS.add(this);

		switch (difficulty.getDifficulty()) {
			case NORMAL -> {
				totalWavesToSpawn = totalWavesToSpawn + getRandom().nextIntBetweenInclusive(1, 3);
				waveSizeModifier = 2;
				xpReward = 100;
			}
			case HARD -> {
				totalWavesToSpawn = totalWavesToSpawn + getRandom().nextIntBetweenInclusive(2, 4);
				waveSizeModifier = 3;
				xpReward = 125;
			}
			case EASY -> {
				totalWavesToSpawn = totalWavesToSpawn + getRandom().nextIntBetweenInclusive(0, 1);
				xpReward = 75;
			}
		}

		Objects.requireNonNull(getAttribute(Attributes.ARMOR))
				.setBaseValue(getAttributeBaseValue(Attributes.ARMOR) + ((double) (totalWavesToSpawn * 5) / 3));

		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	@Override
	public void tick() {
		super.tick();

		if (tickCount % 8 == 0) {
			level().addParticle(ParticleTypes.LAVA, getX() + GeneralUtilities.getRandomNumber(-1d, 1.01d), getY(),
					getZ() + GeneralUtilities.getRandomNumber(-1d, 1.01d),
					GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
					GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
					GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (pSource == damageSources().genericKill()) {
			return super.hurt(pSource, pAmount);
		}

		if (doneSpawningWaves) {
			boolean doesHurt = super.hurt(pSource, pAmount);

			if (doesHurt && pSource.getEntity() != null) {
				bossEvent.setProgress(getHealth() / 240f);
				attackedByEntity(pSource.getEntity(), attackingEntities);
			}

			return doesHurt;
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
	public void die(DamageSource damageSource) {
		super.die(damageSource);

		if (!level().isClientSide) {
			ALL_TOWERS.remove(this);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);

		bossEvent.setName(getDisplayName());
		totalWavesToSpawn = pCompound.getInt("totalWavesToSpawn");
		waveSizeModifier = pCompound.getInt("waveSizeModifier");
		wavesSpawned = pCompound.getInt("wavesSpawned");
		doneSpawningWaves = pCompound.getBoolean("doneSpawningWaves");

		if (wavesSpawned > 0 && !doneSpawningWaves) {
			bossEvent.setName(Component.translatable("immersiveweapons.boss.celestial_tower.waves", wavesSpawned,
					totalWavesToSpawn));
			bossEvent.setProgress((float) wavesSpawned / totalWavesToSpawn);
		} else if (doneSpawningWaves) {
			bossEvent.setColor(BossBarColor.GREEN);
		}

		// If loading the world, check if the tower is in the list
		if (!ALL_TOWERS.contains(this)) {
			ALL_TOWERS.add(this);
		}

		xpReward = pCompound.getInt("xpReward");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);

		pCompound.putInt("totalWavesToSpawn", totalWavesToSpawn);
		pCompound.putInt("waveSizeModifier", waveSizeModifier);
		pCompound.putInt("wavesSpawned", wavesSpawned);
		pCompound.putBoolean("doneSpawningWaves", doneSpawningWaves);
		pCompound.putInt("xpReward", xpReward);
	}

	@Override
	public void startSeenByPlayer(ServerPlayer pPlayer) {
		super.startSeenByPlayer(pPlayer);
		bossEvent.addPlayer(pPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer pPlayer) {
		super.stopSeenByPlayer(pPlayer);
		bossEvent.removePlayer(pPlayer);
	}

	@Override
	public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
		if (pSpawnReason == MobSpawnType.SPAWNER || pSpawnReason == MobSpawnType.SPAWN_EGG) {
			return true;
		}

		// Check if there are other Celestial Towers within a distance of 750 blocks
		for (CelestialTowerEntity tower : ALL_TOWERS) {
			if (tower.blockPosition().closerThan(blockPosition(), 750)) {
				return false;
			}
		}

		if (!pLevel.getBlockState(blockPosition().below()).isValidSpawn(pLevel, blockPosition().below(), getType())) {
			return false;
		}

		int nearbyLanterns = 0;


		if (pLevel instanceof ServerLevel serverLevel) {
			for (BlockPos lanternPos : IWSavedData.getData(serverLevel.getServer()).getAllLanterns()) {
				if (lanternPos.distManhattan(new Vec3i(blockPosition().getX(), blockPosition().getY(), blockPosition().getZ())) < IWConfigs.SERVER.celestialTowerSpawnCheckingRadius.getAsInt()) {
					nearbyLanterns++;

					if (nearbyLanterns >= 3) {
						break;
					}
				}
			}
		}

		if (nearbyLanterns == 3) {
			return false;
		} else if (nearbyLanterns == 0) {
			return true;
		} else {
			return getRandom().nextFloat() <= (nearbyLanterns == 2 ? 0.125f : 0.25f);
		}
	}

	@Override
	public boolean isMaxGroupSizeReached(int pSize) {
		return pSize > 1;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isDoneSpawningWaves() {
		return doneSpawningWaves;
	}

	@Override
	public void setDoneSpawningWaves(boolean state) {
		doneSpawningWaves = state;
	}

	@Override
	public int getTotalWavesToSpawn() {
		return totalWavesToSpawn;
	}

	@Override
	public int getWavesSpawned() {
		return wavesSpawned;
	}

	@Override
	public void setWavesSpawned(int waves) {
		wavesSpawned = waves;
	}

	public int getDifficultyWaveSizeModifier() {
		return waveSizeModifier;
	}

	public int getAttackingEntities() {
		return attackingEntities.size();
	}

	@Override
	public ServerBossEvent getBossEvent() {
		return bossEvent;
	}

	@Override
	public Component getWaveComponent() {
		return Component.translatable("immersiveweapons.boss.celestial_tower.waves", getWavesSpawned(), getTotalWavesToSpawn());
	}

	@Override
	public void playSummonSound() {
		playSound(SoundEventRegistry.CELESTIAL_TOWER_SUMMON.get(),
				1.0f,
				1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));
	}

	@Override
	public void playVulnerableSound() {
		playSound(SoundEventRegistry.CELESTIAL_TOWER_VULNERABLE.get(),
				1.0f,
				1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));
	}
}