package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.RockSpiderEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.*;

public class CelestialTowerSummonGoal extends Goal {

	private final CelestialTowerEntity tower;
	private int waveSpawnCooldown = 100;
	private final Map<Mob, Boolean> mobSpawnQueue = new HashMap<>(25);

	private static final String CELESTIAL_TOWER_ENTITY_TAG = "CelestialTowerEntity";

	public CelestialTowerSummonGoal(CelestialTowerEntity pMob) {
		tower = pMob;
	}

	@Override
	public boolean canUse() {
		return !tower.isDoneSpawningWaves();
	}

	@Override
	public void tick() {
		if (!mobSpawnQueue.isEmpty()) {
			spawnFromMobQueue(3);
		}

		if (getEligibleMobsInArea() == 0) {
			if (tower.getWavesSpawned() < tower.getTotalWavesToSpawn() && waveSpawnCooldown <= 0) {
				// Get the total number of mobs to be spawned
				int mobsToSpawn = (tower.getRandom().nextIntBetweenInclusive(8, 12 + tower.getWavesSpawned())) * tower.getDifficultyWaveSizeModifier();
				mobsToSpawn = (int) (mobsToSpawn * CommonConfig.celestialTowerWaveSizeModifier);

				// 30% of the mob spawns will be "fodder" mobs
				int fodderMobsToSpawn = (int) (mobsToSpawn * 0.3f);
				mobsToSpawn = mobsToSpawn - fodderMobsToSpawn;

				// 20% of the mob spawns will be "power" mobs, if over halfway through the waves
				int powerMobsToSpawn = isWavesPastHalf() ? (int) (mobsToSpawn * 0.2f) : 0;
				mobsToSpawn = mobsToSpawn - powerMobsToSpawn;

				for (int i = fodderMobsToSpawn; i > 0; i--) {
					RockSpiderEntity rockSpider = new RockSpiderEntity(EntityRegistry.ROCK_SPIDER_ENTITY.get(), tower.level());
					addToSpawnQueue(rockSpider, true);
				}

				for (int i = powerMobsToSpawn; i > 0; i--) {
					Zombie zombie = new Zombie(EntityType.ZOMBIE, tower.level());

					ItemStack sword = new ItemStack(Items.IRON_SWORD);
					sword.enchant(Enchantments.SHARPNESS, tower.getRandom().nextIntBetweenInclusive(2, 3 + tower.getWavesSpawned()));
					sword.enchant(Enchantments.KNOCKBACK, tower.getRandom().nextIntBetweenInclusive(1, 2 + tower.getWavesSpawned()));
					sword.enchant(Enchantments.FIRE_ASPECT, tower.getRandom().nextIntBetweenInclusive(1, 2 + tower.getWavesSpawned()));

					zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
					zombie.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
					zombie.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
					zombie.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));

					Objects.requireNonNull(zombie.getAttribute(Attributes.MAX_HEALTH))
							.setBaseValue(20 + (tower.getRandom().nextIntBetweenInclusive(5, 10) * tower.getDifficultyWaveSizeModifier()));

					zombie.setItemInHand(InteractionHand.MAIN_HAND, sword);
					zombie.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 0, true, true));

					addToSpawnQueue(zombie, false);
				}

				for (int i = mobsToSpawn; i > 0; i--) {
					Skeleton skeleton = new Skeleton(EntityType.SKELETON, tower.level());

					ItemStack bow = new ItemStack(Items.BOW);
					bow.enchant(Enchantments.POWER_ARROWS, tower.getRandom().nextIntBetweenInclusive(1, 3 + tower.getWavesSpawned()));
					bow.enchant(Enchantments.PUNCH_ARROWS, tower.getRandom().nextIntBetweenInclusive(1, 2 + tower.getWavesSpawned()));

					skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
					Objects.requireNonNull(skeleton.getAttribute(Attributes.MAX_HEALTH))
							.setBaseValue(20 + (tower.getRandom().nextIntBetweenInclusive(0, 5) * tower.getDifficultyWaveSizeModifier()));

					skeleton.setItemInHand(InteractionHand.MAIN_HAND, bow);
					// 50% chance to have fire resistance
					if (tower.getRandom().nextFloat() <= 0.5f) {
						skeleton.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 0, true, true));
					}

					addToSpawnQueue(skeleton, false);
				}

				// Spawn some particles
				for (int i = 96; i > 0; i--) {
					((ServerLevel) tower.level()).sendParticles(ParticleTypes.FLAME,
							tower.position().x,
							tower.position().y,
							tower.position().z,
							1,
							GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
							GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
							GeneralUtilities.getRandomNumber(-0.03d, 0.03d), 1.0f);
				}

				// Increment the total spawned waves
				tower.setWavesSpawned(tower.getWavesSpawned() + 1);
				tower.bossEvent.setProgress((float) tower.getWavesSpawned() / tower.getTotalWavesToSpawn());
				tower.bossEvent.setName(Component.translatable("immersiveweapons.boss.celestial_tower.waves",
						tower.getWavesSpawned(),
						tower.getTotalWavesToSpawn()));

				tower.setNoActionTime(0);
				tower.playSound(SoundEventRegistry.CELESTIAL_TOWER_SUMMON.get(),
						1.0f,
						1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));

				waveSpawnCooldown = 100;
			} else if (waveSpawnCooldown > 0) {
				waveSpawnCooldown--;
			} else {
				tower.setDoneSpawningWaves(true);
				tower.bossEvent.setColor(BossBarColor.GREEN);
				tower.bossEvent.setName(tower.getDisplayName());
				tower.bossEvent.setProgress(1f);
				tower.setNoActionTime(0);
				tower.playSound(SoundEventRegistry.CELESTIAL_TOWER_VULNERABLE.get(),
						1.0f,
						1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));

			}
		}
	}

	// Only return major mobs in the area; "fodder" entities are ignored
	private int getEligibleMobsInArea() {
		AABB searchBox = new AABB(tower.getX() - 32,
				tower.getY() - 16,
				tower.getZ() - 32,
				tower.getX() + 16,
				tower.getY() + 16,
				tower.getZ() + 32);

		// Check mobs in area which have the tag
		List<Mob> mobs = tower.level().getEntitiesOfClass(Mob.class, searchBox, mob -> mob.getTags().contains(CELESTIAL_TOWER_ENTITY_TAG));

		return mobs.size();
	}

	private boolean isWavesPastHalf() {
		return tower.getTotalWavesToSpawn() / 2 <= tower.getWavesSpawned();
	}

	/**
	 * Get a nearby position for spawning an entity, within 8 blocks of the tower on the X/Z plane.
	 *
	 * @return BlockPos
	 */
	private BlockPos getRandomNearbyPosForSpawning() {
		return new BlockPos(tower.getBlockX() + tower.getRandom().nextIntBetweenInclusive(-8, 8),
				tower.getBlockY(),
				tower.getBlockZ() + tower.getRandom().nextIntBetweenInclusive(-8, 8));
	}

	/**
	 * Add a mob to the spawn queue. A wave of entities is not spawned all at once for performance reasons.
	 *
	 * @param mob the <code>Mob</code> to add
	 */
	private void addToSpawnQueue(Mob mob, boolean isFodder) {
		mobSpawnQueue.put(mob, isFodder);
	}

	/**
	 * Spawn a given amount of mobs from the spawn queue.
	 *
	 * @param amount the amount of mobs to spawn
	 */
	private void spawnFromMobQueue(int amount) {
		for (int i = amount; i > 0; i--) {
			if (!mobSpawnQueue.isEmpty()) {
				Mob mob = mobSpawnQueue.keySet().iterator().next();
				boolean isFodder = mobSpawnQueue.get(mob);
				BlockPos summonPos = getRandomNearbyPosForSpawning();
				spawnEntity((ServerLevel) tower.level(), mob, summonPos, isFodder);
				spawnEntityParticles((ServerLevel) tower.level());
				mobSpawnQueue.remove(mob);
			}
		}
	}

	private void spawnEntityParticles(ServerLevel level) {
		level.sendParticles(ParticleTypes.POOF,
				tower.position().x,
				tower.position().y,
				tower.position().z,
				1,
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
				GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d), 1.0f);
	}

	private void spawnEntity(ServerLevel level, Mob mob, BlockPos summonPos, boolean isFodder) {
		mob.setPersistenceRequired();
		mob.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
		mob.heal(mob.getMaxHealth());

		if (!isFodder) {
			mob.addTag(CELESTIAL_TOWER_ENTITY_TAG);
		}

		level.addFreshEntity(mob);
	}
}