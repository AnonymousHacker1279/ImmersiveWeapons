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
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.RockSpiderEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Objects;

public class CelestialTowerSummonGoal extends Goal {

	private final CelestialTowerEntity tower;
	private int waveSpawnCooldown = 100;
	@Nullable
	private AABB searchBox;

	public CelestialTowerSummonGoal(CelestialTowerEntity pMob) {
		tower = pMob;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return !tower.isDoneSpawningWaves() && getEligibleMobsInArea() == 0;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (tower.getWavesSpawned() < tower.getTotalWavesToSpawn() && waveSpawnCooldown <= 0) {
			int mobsToSpawn = (tower.getRandom().nextIntBetweenInclusive(8, 12 + tower.getWavesSpawned())) * tower.getWaveSizeModifier(); // Get the total mobs to spawn
			mobsToSpawn = (int) (mobsToSpawn * CommonConfig.CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER.get()); // Modify by the configuration option of setting wave sizes
			int fodderMobsToSpawn = (int) (mobsToSpawn * 0.3f); // Get the number of "fodder" mobs to spawn
			mobsToSpawn = mobsToSpawn - fodderMobsToSpawn; // Reduce the total number left to spawn
			int powerMobsToSpawn = isWavesPastHalf() ? (int) (mobsToSpawn * 0.2f) : 0; // Get the number of "power" mobs to spawn, if over halfway through the waves
			mobsToSpawn = mobsToSpawn - powerMobsToSpawn; // Reduce the total number left to spawn

			ServerLevel serverLevel = (ServerLevel) tower.level();

			for (int i = fodderMobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(tower.getBlockX() + tower.getRandom().nextIntBetweenInclusive(-8, 8),
						tower.getBlockY(),
						tower.getBlockZ() + tower.getRandom().nextIntBetweenInclusive(-8, 8));

				RockSpiderEntity rockSpiderEntity = new RockSpiderEntity(EntityRegistry.ROCK_SPIDER_ENTITY.get(), tower.level());
				spawnEntity(serverLevel, rockSpiderEntity, summonPos);
				spawnEntityParticles(serverLevel);
			}
			for (int i = powerMobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(tower.getBlockX() + tower.getRandom().nextIntBetweenInclusive(-8, 8),
						tower.getBlockY(),
						tower.getBlockZ() + tower.getRandom().nextIntBetweenInclusive(-8, 8));

				Zombie zombieEntity = new Zombie(EntityType.ZOMBIE, tower.level());
				ItemStack sword = new ItemStack(Items.IRON_SWORD);
				sword.enchant(Enchantments.SHARPNESS, tower.getRandom().nextIntBetweenInclusive(2, 3 + tower.getWavesSpawned()));
				sword.enchant(Enchantments.KNOCKBACK, tower.getRandom().nextIntBetweenInclusive(1, 2 + tower.getWavesSpawned()));
				sword.enchant(Enchantments.FIRE_ASPECT, tower.getRandom().nextIntBetweenInclusive(1, 2 + tower.getWavesSpawned()));
				zombieEntity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				zombieEntity.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				zombieEntity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
				zombieEntity.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				Objects.requireNonNull(zombieEntity.getAttribute(Attributes.MAX_HEALTH))
						.setBaseValue(20 + (tower.getRandom().nextIntBetweenInclusive(5, 10) * tower.getWaveSizeModifier()));

				zombieEntity.setItemInHand(InteractionHand.MAIN_HAND, sword);
				zombieEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9999, 0, true, true));
				spawnEntity(serverLevel, zombieEntity, summonPos);
				spawnEntityParticles(serverLevel);
			}
			for (int i = mobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(tower.getBlockX() + tower.getRandom().nextIntBetweenInclusive(-8, 8),
						tower.getBlockY(),
						tower.getBlockZ() + tower.getRandom().nextIntBetweenInclusive(-8, 8));

				Skeleton skeletonEntity = new Skeleton(EntityType.SKELETON, tower.level());
				ItemStack bow = new ItemStack(Items.BOW);
				bow.enchant(Enchantments.POWER_ARROWS, tower.getRandom().nextIntBetweenInclusive(1, 3 + tower.getWavesSpawned()));
				bow.enchant(Enchantments.PUNCH_ARROWS, tower.getRandom().nextIntBetweenInclusive(1, 2 + tower.getWavesSpawned()));
				skeletonEntity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				Objects.requireNonNull(skeletonEntity.getAttribute(Attributes.MAX_HEALTH))
						.setBaseValue(20 + (tower.getRandom().nextIntBetweenInclusive(0, 5) * tower.getWaveSizeModifier()));

				skeletonEntity.setItemInHand(InteractionHand.MAIN_HAND, bow);
				// 50% chance to have fire resistance
				if (GeneralUtilities.getRandomNumber(0, 2) == 1) {
					skeletonEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9999, 0, true, true));
				}
				spawnEntity(serverLevel, skeletonEntity, summonPos);
				spawnEntityParticles(serverLevel);
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

			tower.setWavesSpawned(tower.getWavesSpawned() + 1); // Increment the total spawned waves
			tower.bossEvent.setProgress((float) tower.getWavesSpawned() / tower.getTotalWavesToSpawn());
			tower.bossEvent.setName(Component.translatable("immersiveweapons.boss.celestial_tower.waves",
					tower.getWavesSpawned(),
					tower.getTotalWavesToSpawn()));

			tower.setNoActionTime(0);
			tower.playSound(SoundEventRegistry.CELESTIAL_TOWER_SUMMON.get(),
					1.0f,
					1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));

			waveSpawnCooldown = 100; // Set the wave spawn cooldown
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

	// Only return major mobs in the area; "fodder" entities are ignored
	private int getEligibleMobsInArea() {
		if (searchBox == null) {
			searchBox = new AABB(tower.getX() - 32,
					tower.getY() - 16,
					tower.getZ() - 32,
					tower.getX() + 16,
					tower.getY() + 16,
					tower.getZ() + 32);

		}

		int mobs = tower.level().getNearbyEntities(Skeleton.class, TargetingConditions.forNonCombat(), tower, searchBox).size();
		mobs = mobs + tower.level().getNearbyEntities(Zombie.class, TargetingConditions.forNonCombat(), tower, searchBox).size();
		return mobs;
	}

	private boolean isWavesPastHalf() {
		return tower.getTotalWavesToSpawn() / 2 <= tower.getWavesSpawned();
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

	private void spawnEntity(ServerLevel level, Mob mob, BlockPos summonPos) {
		mob.setPersistenceRequired();
		mob.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
		mob.heal(mob.getMaxHealth());
		level.addFreshEntity(mob);
	}
}