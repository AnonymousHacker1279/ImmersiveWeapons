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
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.RockSpiderEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Objects;

public class CelestialTowerSummonGoal extends Goal {

	private final CelestialTowerEntity mob;
	private int waveSpawnCooldown = 100;
	private AABB searchBox;

	public CelestialTowerSummonGoal(CelestialTowerEntity pMob) {
		mob = pMob;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return !mob.isDoneSpawningWaves() && getEligibleMobsInArea() == 0;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (mob.getWavesSpawned() < mob.getTotalWavesToSpawn() && waveSpawnCooldown <= 0) {
			int mobsToSpawn = (GeneralUtilities.getRandomNumber(8, 12 + mob.getWavesSpawned())) * mob.getWaveSizeModifier(); // Get the total mobs to spawn
			mobsToSpawn = (int) (mobsToSpawn * CommonConfig.CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER.get()); // Modify by the configuration option of setting wave sizes
			int fodderMobsToSpawn = (int) (mobsToSpawn * 0.3f); // Get the number of "fodder" mobs to spawn
			mobsToSpawn = mobsToSpawn - fodderMobsToSpawn; // Reduce the total number left to spawn
			int powerMobsToSpawn = isWavesPastHalf() ? (int) (mobsToSpawn * 0.2f) : 0; // Get the number of "power" mobs to spawn, if over halfway through the waves
			mobsToSpawn = mobsToSpawn - powerMobsToSpawn; // Reduce the total number left to spawn

			ServerLevel serverLevel = (ServerLevel) mob.level;

			for (int i = fodderMobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(mob.getX() + GeneralUtilities.getRandomNumber(-8, 9),
						mob.getY(),
						mob.getZ() + GeneralUtilities.getRandomNumber(-8, 9));

				RockSpiderEntity rockSpiderEntity = new RockSpiderEntity(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), mob.level);
				spawnEntity(serverLevel, rockSpiderEntity, summonPos);
				spawnEntityParticles(serverLevel);
			}
			for (int i = powerMobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(mob.getX() + GeneralUtilities.getRandomNumber(-8, 9),
						mob.getY(),
						mob.getZ() + GeneralUtilities.getRandomNumber(-8, 9));

				Zombie zombieEntity = new Zombie(EntityType.ZOMBIE, mob.level);
				ItemStack sword = new ItemStack(Items.IRON_SWORD);
				sword.enchant(Enchantments.SHARPNESS, GeneralUtilities.getRandomNumber(2, 4 + mob.getWavesSpawned()));
				sword.enchant(Enchantments.KNOCKBACK, GeneralUtilities.getRandomNumber(1, 3 + mob.getWavesSpawned()));
				sword.enchant(Enchantments.FIRE_ASPECT, GeneralUtilities.getRandomNumber(1, 2 + mob.getWavesSpawned()));
				zombieEntity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				zombieEntity.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				zombieEntity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
				zombieEntity.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				Objects.requireNonNull(zombieEntity.getAttribute(Attributes.MAX_HEALTH))
						.setBaseValue(20 + (GeneralUtilities.getRandomNumber(5, 11) * mob.getWaveSizeModifier()));

				zombieEntity.setItemInHand(InteractionHand.MAIN_HAND, sword);
				zombieEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9999, 0, true, true));
				spawnEntity(serverLevel, zombieEntity, summonPos);
				spawnEntityParticles(serverLevel);
			}
			for (int i = mobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(mob.getX() + GeneralUtilities.getRandomNumber(-8, 9),
						mob.getY(),
						mob.getZ() + GeneralUtilities.getRandomNumber(-8, 9));

				Skeleton skeletonEntity = new Skeleton(EntityType.SKELETON, mob.level);
				ItemStack bow = new ItemStack(Items.BOW);
				bow.enchant(Enchantments.POWER_ARROWS, GeneralUtilities.getRandomNumber(1, 3 + mob.getWavesSpawned()));
				bow.enchant(Enchantments.PUNCH_ARROWS, GeneralUtilities.getRandomNumber(1, 2 + mob.getWavesSpawned()));
				skeletonEntity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				Objects.requireNonNull(skeletonEntity.getAttribute(Attributes.MAX_HEALTH))
						.setBaseValue(20 + (GeneralUtilities.getRandomNumber(0, 6) * mob.getWaveSizeModifier()));

				skeletonEntity.setItemInHand(InteractionHand.MAIN_HAND, bow);
				skeletonEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 9999, 0, true, true));
				spawnEntity(serverLevel, skeletonEntity, summonPos);
				spawnEntityParticles(serverLevel);
			}

			// Spawn some particles
			for (int i = 96; i > 0; i--) {
				((ServerLevel) mob.level).sendParticles(ParticleTypes.FLAME,
						mob.position().x,
						mob.position().y,
						mob.position().z,
						1,
						GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
						GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
						GeneralUtilities.getRandomNumber(-0.03d, 0.03d), 1.0f);
			}

			mob.setWavesSpawned(mob.getWavesSpawned() + 1); // Increment the total spawned waves
			mob.bossEvent.setProgress((float) mob.getWavesSpawned() / mob.getTotalWavesToSpawn());
			mob.bossEvent.setName(Component.translatable("immersiveweapons.boss.celestial_tower.waves",
					mob.getWavesSpawned(),
					mob.getTotalWavesToSpawn()));

			mob.setNoActionTime(0);
			mob.playSound(DeferredRegistryHandler.CELESTIAL_TOWER_SUMMON.get(),
					1.0f,
					1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));

			waveSpawnCooldown = 100; // Set the wave spawn cooldown
		} else if (waveSpawnCooldown > 0) {
			waveSpawnCooldown--;
		} else {
			mob.setDoneSpawningWaves(true);
			mob.bossEvent.setColor(BossBarColor.GREEN);
			mob.bossEvent.setName(mob.getDisplayName());
			mob.bossEvent.setProgress(1f);
			mob.setNoActionTime(0);
			mob.playSound(DeferredRegistryHandler.CELESTIAL_TOWER_VULNERABLE.get(),
					1.0f,
					1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));

		}
	}

	// Only return major mobs in the area; "fodder" entities are ignored
	private int getEligibleMobsInArea() {
		if (searchBox == null) {
			searchBox = new AABB(mob.getX() - 32,
					mob.getY() - 16,
					mob.getZ() - 32,
					mob.getX() + 16,
					mob.getY() + 16,
					mob.getZ() + 32);

		}

		int mobs = mob.level.getNearbyEntities(Skeleton.class, TargetingConditions.forNonCombat(), mob, searchBox).size();
		mobs = mobs + mob.level.getNearbyEntities(Zombie.class, TargetingConditions.forNonCombat(), mob, searchBox).size();
		return mobs;
	}

	private boolean isWavesPastHalf() {
		return mob.getTotalWavesToSpawn() / 2 <= mob.getWavesSpawned();
	}

	private void spawnEntityParticles(ServerLevel level) {
		level.sendParticles(ParticleTypes.POOF,
				mob.position().x,
				mob.position().y,
				mob.position().z,
				1,
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
				GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d), 1.0f);
	}

	private Mob spawnEntity(ServerLevel level, Mob mob, BlockPos summonPos) {
		mob.setPersistenceRequired();
		mob.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
		mob.heal(mob.getMaxHealth());
		level.addFreshEntity(mob);
		return mob;
	}
}