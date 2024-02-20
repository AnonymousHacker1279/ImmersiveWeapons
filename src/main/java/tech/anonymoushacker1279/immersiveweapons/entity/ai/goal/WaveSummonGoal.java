package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.entity.WaveSummoningBoss;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.*;

public abstract class WaveSummonGoal<T extends Mob & WaveSummoningBoss> extends Goal {

	protected final T mob;
	protected final String summonedEntityTag;
	protected final Map<Mob, Boolean> mobSpawnQueue = new HashMap<>(25);
	protected int waveSpawnCooldown = 100;

	public WaveSummonGoal(T mob, String summonedEntityTag) {
		this.mob = mob;
		this.summonedEntityTag = summonedEntityTag;
	}

	@Override
	public boolean canUse() {
		return !mob.isDoneSpawningWaves();
	}

	@Override
	public void tick() {
		if (!mobSpawnQueue.isEmpty()) {
			spawnFromMobQueue(3);
		}

		if (getEligibleMobsInArea() == 0) {
			if (mob.getWavesSpawned() < mob.getTotalWavesToSpawn() && waveSpawnCooldown <= 0) {
				doWaveSpawnBehavior();

				mob.setWavesSpawned(mob.getWavesSpawned() + 1);
				mob.getBossEvent().setProgress((float) mob.getWavesSpawned() / mob.getTotalWavesToSpawn());
				mob.getBossEvent().setName(mob.getWaveComponent());

				mob.setNoActionTime(0);
				mob.playSummonSound();
				waveSpawnCooldown = 100;
			} else if (waveSpawnCooldown > 0) {
				waveSpawnCooldown--;
			} else {
				mob.setDoneSpawningWaves(true);
				mob.getBossEvent().setColor(BossBarColor.GREEN);
				mob.getBossEvent().setName(mob.getDisplayName());
				mob.getBossEvent().setProgress(1f);

				mob.setNoActionTime(0);
				mob.playVulnerableSound();
			}
		}

	}

	protected void spawnEntityParticles(ServerLevel level) {
		level.sendParticles(ParticleTypes.POOF,
				mob.position().x,
				mob.position().y,
				mob.position().z,
				1,
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
				GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d), 1.0f);
	}

	protected void spawnEntity(ServerLevel level, Mob mob, BlockPos summonPos, boolean isFodder) {
		mob.setPersistenceRequired();
		mob.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
		mob.heal(mob.getMaxHealth());

		if (!isFodder) {
			mob.addTag(summonedEntityTag);
		}

		if (mob instanceof PathfinderMob pathfinderMob) {
			mob.goalSelector.addGoal(5, new StayNearMobGoal(pathfinderMob, this.mob, 32d));
		}

		level.addFreshEntity(mob);
	}

	/**
	 * Get a nearby position for spawning an entity, within 8 blocks of the tower on the X/Z plane.
	 *
	 * @return BlockPos
	 */
	private BlockPos getRandomNearbyPosForSpawning() {  // TODO: allow range to be specified
		return new BlockPos(mob.getBlockX() + mob.getRandom().nextIntBetweenInclusive(-8, 8),
				mob.getBlockY(),
				mob.getBlockZ() + mob.getRandom().nextIntBetweenInclusive(-8, 8));
	}

	/**
	 * Add a mob to the spawn queue. A wave of entities is not spawned all at once for performance reasons.
	 *
	 * @param mob the <code>Mob</code> to add
	 */
	protected void addToSpawnQueue(Mob mob, boolean isFodder) {
		mobSpawnQueue.put(mob, isFodder);
	}

	/**
	 * Spawn a given amount of mobs from the spawn queue.
	 *
	 * @param amount the amount of mobs to spawn
	 */
	protected void spawnFromMobQueue(int amount) {
		for (int i = amount; i > 0; i--) {
			if (!mobSpawnQueue.isEmpty()) {
				Mob mob = mobSpawnQueue.keySet().iterator().next();
				boolean isFodder = mobSpawnQueue.get(mob);
				BlockPos summonPos = getRandomNearbyPosForSpawning();
				spawnEntity((ServerLevel) mob.level(), mob, summonPos, isFodder);
				spawnEntityParticles((ServerLevel) mob.level());
				mobSpawnQueue.remove(mob);
			}
		}
	}

	protected int getEligibleMobsInArea() {
		AABB searchBox = new AABB(mob.getX() - 32,
				mob.getY() - 16,
				mob.getZ() - 32,
				mob.getX() + 16,
				mob.getY() + 16,
				mob.getZ() + 32);

		// Check mobs in area which have the tag
		List<Mob> mobs = mob.level().getEntitiesOfClass(Mob.class, searchBox, mob -> mob.getTags().contains(summonedEntityTag));

		return mobs.size();
	}

	protected boolean isWavesPastHalf() {
		return mob.getTotalWavesToSpawn() / 2 <= mob.getWavesSpawned();
	}

	protected void enchantGear(Mob summonedEntity, boolean doWeapons, boolean doArmor) {
		RandomSource random = summonedEntity.getRandom();
		DifficultyInstance difficulty = summonedEntity.level().getCurrentDifficultyAt(mob.blockPosition());

		float specialMultiplier = difficulty.getSpecialMultiplier();

		if (doWeapons) {
			enchantSpawnedWeapon(summonedEntity, random, specialMultiplier);
		}

		if (doArmor) {
			enchantSpawnedArmor(summonedEntity, random, specialMultiplier);
		}
	}

	private void enchantSpawnedWeapon(Mob summonedEntity, RandomSource random, float chanceMultiplier) {
		if (!summonedEntity.getMainHandItem().isEmpty()) {
			summonedEntity.setItemSlot(
					EquipmentSlot.MAINHAND,
					EnchantmentHelper.enchantItem(random, summonedEntity.getMainHandItem(),
							(int) (5.0F + chanceMultiplier * (float) random.nextInt(18)),
							false)
			);
		}
	}

	private void enchantSpawnedArmor(Mob summonedEntity, RandomSource random, float chanceMultiplier) {
		for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
			if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR) {
				ItemStack slotStack = summonedEntity.getItemBySlot(equipmentslot);
				if (!slotStack.isEmpty()) {
					summonedEntity.setItemSlot(equipmentslot, EnchantmentHelper.enchantItem(random, slotStack,
							(int) (5.0F + chanceMultiplier * (float) random.nextInt(18)),
							false));
				}
			}
		}
	}

	protected abstract void doWaveSpawnBehavior();
}