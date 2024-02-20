package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.DyingSoldierEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.TheCommanderEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class TheCommanderSummonGoal extends WaveSummonGoal<TheCommanderEntity> {

	private static final String SUMMONED_ENTITY_TAG = "TheCommanderWaveEntity";

	public TheCommanderSummonGoal(TheCommanderEntity mob) {
		super(mob, SUMMONED_ENTITY_TAG);
	}

	@Override
	protected void doWaveSpawnBehavior() {
		// Get the total number of mobs to be spawned
		int regularSoldiers = (mob.getRandom().nextIntBetweenInclusive(4, 8 + mob.getWavesSpawned())) * mob.getDifficultyWaveSizeModifier();
		regularSoldiers = (int) (regularSoldiers * CommonConfig.theCommanderWaveSizeModifier);

		// 20% of the soldiers will have full leather armor
		int leatherArmorSoldiers = (int) (regularSoldiers * 0.2f);
		regularSoldiers = regularSoldiers - leatherArmorSoldiers;

		// 20% of the soldiers will have full iron armor
		int ironArmorSoldiers = (int) (regularSoldiers * 0.2f);
		regularSoldiers = regularSoldiers - ironArmorSoldiers;

		// 10% of the soldiers will have full diamond armor, if over halfway through the waves
		int diamondArmorSoldiers = isWavesPastHalf() ? (int) (regularSoldiers * 0.1f) : 0;
		regularSoldiers = regularSoldiers - diamondArmorSoldiers;

		for (int i = leatherArmorSoldiers; i > 0; i--) {
			DyingSoldierEntity soldier = new DyingSoldierEntity(EntityRegistry.DYING_SOLDIER_ENTITY.get(), mob.level());
			soldier.prepareForCombat();

			soldier.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
			soldier.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
			soldier.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
			soldier.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));

			// 25% chance to roll for enchanted weapons
			if (mob.getRandom().nextFloat() <= 0.25f) {
				enchantGear(soldier, true, false);
			}

			addToSpawnQueue(soldier, false);
		}

		for (int i = ironArmorSoldiers; i > 0; i--) {
			DyingSoldierEntity soldier = new DyingSoldierEntity(EntityRegistry.DYING_SOLDIER_ENTITY.get(), mob.level());
			soldier.prepareForCombat();

			soldier.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
			soldier.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
			soldier.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
			soldier.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));

			// 50% chance to roll for enchanted armor
			if (mob.getRandom().nextBoolean()) {
				enchantGear(soldier, false, true);
			}

			addToSpawnQueue(soldier, false);
		}

		for (int i = diamondArmorSoldiers; i > 0; i--) {
			DyingSoldierEntity soldier = new DyingSoldierEntity(EntityRegistry.DYING_SOLDIER_ENTITY.get(), mob.level());
			soldier.prepareForCombat();

			soldier.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
			soldier.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
			soldier.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
			soldier.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));

			// 50% chance to roll for enchanted armor
			if (mob.getRandom().nextBoolean()) {
				enchantGear(soldier, true, true);
			}

			addToSpawnQueue(soldier, false);
		}

		for (int i = regularSoldiers; i > 0; i--) {
			DyingSoldierEntity soldier = new DyingSoldierEntity(EntityRegistry.DYING_SOLDIER_ENTITY.get(), mob.level());
			soldier.prepareForCombat();

			// 25% chance to roll for enchanted weapons
			if (mob.getRandom().nextFloat() <= 0.25f) {
				enchantGear(soldier, true, false);
			}

			addToSpawnQueue(soldier, false);
		}

		// Spawn some particles
		for (int i = 48; i > 0; i--) {
			((ServerLevel) mob.level()).sendParticles(ParticleTypes.CRIMSON_SPORE,
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