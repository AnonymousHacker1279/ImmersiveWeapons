package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.AttackerTracker;
import tech.anonymoushacker1279.immersiveweapons.entity.WaveSummoningBoss;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.TheCommanderSummonGoal;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem.PowderType;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.ArrayList;
import java.util.List;

public class TheCommanderEntity extends DyingSoldierEntity implements AttackerTracker, WaveSummoningBoss {

	public final ServerBossEvent bossEvent = new ServerBossEvent(getDisplayName(), BossBarColor.RED,
			BossBarOverlay.PROGRESS);
	private final RangedGunAttackGoal<DyingSoldierEntity> rangedGunAttackGoal =
			new RangedGunAttackGoal<>(this, 1.0D, 120, 12.0F);

	private int totalWavesToSpawn;
	private int waveSizeModifier;
	private int wavesSpawned = 0;
	private boolean doneSpawningWaves = false;
	private boolean breakTowerFences = false;

	final List<Entity> attackingEntities = new ArrayList<>(5);

	public TheCommanderEntity(EntityType<? extends DyingSoldierEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.27D)
				.add(Attributes.ARMOR, 15.0D)
				.add(Attributes.MAX_HEALTH, 100.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
				.add(Attributes.FOLLOW_RANGE, 64.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		goalSelector.addGoal(2, new TheCommanderSummonGoal(this));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
		bossEvent.setProgress(0f);

		totalWavesToSpawn = 3;
		waveSizeModifier = 1;

		setPersistenceRequired();

		switch (difficulty.getDifficulty()) {
			case NORMAL -> {
				xpReward = 75;
				getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.30D);

				totalWavesToSpawn = totalWavesToSpawn + getRandom().nextIntBetweenInclusive(0, 1);
				waveSizeModifier = 2;
			}
			case HARD -> {
				xpReward = 125;
				getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.32D);
				getAttribute(Attributes.ARMOR).setBaseValue(20.0D);
				getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(4.0D);
				getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);

				totalWavesToSpawn = totalWavesToSpawn + getRandom().nextIntBetweenInclusive(1, 3);
				waveSizeModifier = 3;
			}
			default -> xpReward = 50;
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack currentItem, EquipmentSlot slot) {
		if (newItem.getItem() instanceof AbstractGunItem newGun && currentItem.getItem() instanceof AbstractGunItem oldGun) {
			return newGun.getBaseFireVelocity() > oldGun.getBaseFireVelocity();
		}

		return super.canReplaceCurrentItem(newItem, currentItem, slot);
	}

	@Override
	public float getVoicePitch() {
		return super.getVoicePitch() * 0.5f;
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
		if (!source.is(DamageTypes.GENERIC_KILL) && !doneSpawningWaves) {
			return false;
		}

		// Damage reduction from ranged projectiles, excluding cannonballs
		if ((source.is(DamageTypeTags.IS_PROJECTILE) || source.getDirectEntity() instanceof AbstractArrow)
				&& !(source.is(IWDamageSources.CANNONBALL_KEY) || source.is(IWDamageSources.EXPLOSIVE_CANNONBALL_KEY))) {

			amount *= 0.7f;
		}

		// Reduce damage as the number of players attacking increase
		float playerCountReductionFactor;
		switch (getAttackingEntities()) {
			case 0, 1 -> playerCountReductionFactor = 1.0f;
			case 2 -> playerCountReductionFactor = 0.90f;
			case 3 -> playerCountReductionFactor = 0.80f;
			case 4 -> playerCountReductionFactor = 0.75f;
			default -> playerCountReductionFactor = 0.70f;
		}

		amount *= playerCountReductionFactor;

		boolean doesHurt = super.hurtServer(serverLevel, source, amount);

		if (doesHurt) {
			bossEvent.setProgress(getHealth() / getMaxHealth());

			if (source.getEntity() != null) {
				attackedByEntity(source.getEntity(), attackingEntities);
			}
		}

		return doesHurt;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (!breakTowerFences && doneSpawningWaves && getTarget() != null) {
			// Get nearby Burned Oak Fences in a 3x3 square and remove them
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					BlockPos pos = new BlockPos(getBlockX() + x, getBlockY(), getBlockZ() + z);
					if (level().getBlockState(pos).is(BlockRegistry.BURNED_OAK_FENCE.get())) {
						level().destroyBlock(pos, false);
					}
				}
			}

			breakTowerFences = true;
		}
	}

	@Override
	public RangedGunAttackGoal<DyingSoldierEntity> getRangedGunAttackGoal() {
		return rangedGunAttackGoal;
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
	public void setCustomName(@Nullable Component pName) {
		super.setCustomName(pName);
		bossEvent.setName(getDisplayName());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);

		bossEvent.setName(getDisplayName());
		totalWavesToSpawn = pCompound.getInt("totalWavesToSpawn");
		waveSizeModifier = pCompound.getInt("waveSizeModifier");
		wavesSpawned = pCompound.getInt("wavesSpawned");
		doneSpawningWaves = pCompound.getBoolean("doneSpawningWaves");
		breakTowerFences = pCompound.getBoolean("hasJumpedOutOfTower");

		if (wavesSpawned > 0 && !doneSpawningWaves) {
			bossEvent.setName(Component.translatable("immersiveweapons.boss.the_commander.waves", wavesSpawned,
					totalWavesToSpawn));
			bossEvent.setProgress((float) wavesSpawned / totalWavesToSpawn);
		} else {
			bossEvent.setColor(BossBarColor.GREEN);
			bossEvent.setProgress(getHealth() / getMaxHealth());
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
		pCompound.putBoolean("hasJumpedOutOfTower", breakTowerFences);
		pCompound.putInt("xpReward", xpReward);
	}

	@Override
	protected AbstractGunItem getDefaultGunItem() {
		return ItemRegistry.HAND_CANNON.get();
	}

	@Override
	protected PowderType getPowderType() {
		return new PowderType(Items.GUNPOWDER.getDefaultInstance());
	}

	@Override
	protected int getAttackIntervalModifier(Difficulty difficulty) {
		return switch (level().getDifficulty()) {
			case NORMAL -> 100;
			case HARD -> 80;
			default -> 120;
		};
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
		return Component.translatable("immersiveweapons.boss.the_commander.waves", getWavesSpawned(), getTotalWavesToSpawn());
	}

	@Override
	public void playSummonSound() {
	}

	@Override
	public void playVulnerableSound() {
	}
}