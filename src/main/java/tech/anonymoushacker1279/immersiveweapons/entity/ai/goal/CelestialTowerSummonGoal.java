package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.RockSpiderEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Objects;

public class CelestialTowerSummonGoal extends WaveSummonGoal<CelestialTowerEntity> {

	private static final String SUMMONED_ENTITY_TAG = "CelestialTowerWaveEntity";

	public CelestialTowerSummonGoal(CelestialTowerEntity mob) {
		super(mob, SUMMONED_ENTITY_TAG);
	}

	@Override
	protected void doWaveSpawnBehavior() {
		// Get the total number of mobs to be spawned
		int mobsToSpawn = (mob.getRandom().nextIntBetweenInclusive(8, 12 + mob.getWavesSpawned())) * mob.getDifficultyWaveSizeModifier();
		mobsToSpawn = (int) (mobsToSpawn * CommonConfig.celestialTowerWaveSizeModifier);

		// 30% of the mob spawns will be "fodder" mobs
		int fodderMobsToSpawn = (int) (mobsToSpawn * 0.3f);
		mobsToSpawn = mobsToSpawn - fodderMobsToSpawn;

		// 20% of the mob spawns will be "power" mobs, if over halfway through the waves
		int powerMobsToSpawn = isWavesPastHalf() ? (int) (mobsToSpawn * 0.2f) : 0;
		mobsToSpawn = mobsToSpawn - powerMobsToSpawn;

		for (int i = fodderMobsToSpawn; i > 0; i--) {
			RockSpiderEntity rockSpider = new RockSpiderEntity(EntityRegistry.ROCK_SPIDER_ENTITY.get(), mob.level());
			addToSpawnQueue(rockSpider, true);
		}

		for (int i = powerMobsToSpawn; i > 0; i--) {
			Zombie zombie = new Zombie(EntityType.ZOMBIE, mob.level());

			ItemStack sword = new ItemStack(Items.IRON_SWORD);
			sword.enchant(Enchantments.SHARPNESS, mob.getRandom().nextIntBetweenInclusive(2, 3 + mob.getWavesSpawned()));
			sword.enchant(Enchantments.KNOCKBACK, mob.getRandom().nextIntBetweenInclusive(1, 2 + mob.getWavesSpawned()));
			sword.enchant(Enchantments.FIRE_ASPECT, mob.getRandom().nextIntBetweenInclusive(1, 2 + mob.getWavesSpawned()));

			zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
			zombie.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
			zombie.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
			zombie.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));

			Objects.requireNonNull(zombie.getAttribute(Attributes.MAX_HEALTH))
					.setBaseValue(20 + (mob.getRandom().nextIntBetweenInclusive(5, 10) * mob.getDifficultyWaveSizeModifier()));

			zombie.setItemInHand(InteractionHand.MAIN_HAND, sword);
			zombie.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 0, true, true));

			addToSpawnQueue(zombie, false);
		}

		for (int i = mobsToSpawn; i > 0; i--) {
			Skeleton skeleton = new Skeleton(EntityType.SKELETON, mob.level());

			ItemStack bow = new ItemStack(Items.BOW);
			bow.enchant(Enchantments.POWER_ARROWS, mob.getRandom().nextIntBetweenInclusive(1, 3 + mob.getWavesSpawned()));
			bow.enchant(Enchantments.PUNCH_ARROWS, mob.getRandom().nextIntBetweenInclusive(1, 2 + mob.getWavesSpawned()));

			skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
			Objects.requireNonNull(skeleton.getAttribute(Attributes.MAX_HEALTH))
					.setBaseValue(20 + (mob.getRandom().nextIntBetweenInclusive(0, 5) * mob.getDifficultyWaveSizeModifier()));

			skeleton.setItemInHand(InteractionHand.MAIN_HAND, bow);
			// 50% chance to have fire resistance
			if (mob.getRandom().nextFloat() <= 0.5f) {
				skeleton.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 0, true, true));
			}

			addToSpawnQueue(skeleton, false);
		}

		// Spawn some particles
		for (int i = 96; i > 0; i--) {
			((ServerLevel) mob.level()).sendParticles(ParticleTypes.FLAME,
					mob.position().x,
					mob.position().y,
					mob.position().z,
					1,
					GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
					GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
					GeneralUtilities.getRandomNumber(-0.03d, 0.03d), 1.0f);
		}
	}
}