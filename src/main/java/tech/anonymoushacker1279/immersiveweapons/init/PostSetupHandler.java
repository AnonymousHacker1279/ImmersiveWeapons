package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.config.ConfigHelper.TomlConfigOps;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkygazerEntity;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomArmorMaterials;

public class PostSetupHandler {

	/**
	 * Initialize attributes which must be applied after setup.
	 */
	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing post-setup handler");

		// Set custom armor equip sounds, as these don't exist during the initialization of materials
		CustomArmorMaterials.TESLA.setEquipSound(SoundEventRegistry.TESLA_ARMOR_EQUIP.get());
		CustomArmorMaterials.MOLTEN.setEquipSound(SoundEventRegistry.MOLTEN_ARMOR_EQUIP.get());
		CustomArmorMaterials.VENTUS.setEquipSound(SoundEventRegistry.VENTUS_ARMOR_EQUIP.get());
		CustomArmorMaterials.COPPER.setEquipSound(SoundEventRegistry.COPPER_ARMOR_EQUIP.get());
		CustomArmorMaterials.COBALT.setEquipSound(SoundEventRegistry.COBALT_ARMOR_EQUIP.get());
		CustomArmorMaterials.ASTRAL.setEquipSound(SoundEventRegistry.ASTRAL_ARMOR_EQUIP.get());
		CustomArmorMaterials.STARSTORM.setEquipSound(SoundEventRegistry.STARSTORM_ARMOR_EQUIP.get());

		// Add custom plants to the flower pot blockLocation
		FlowerPotBlock emptyPot = ((FlowerPotBlock) Blocks.FLOWER_POT);
		emptyPot.addPlant(BlockRegistry.MOONGLOW.getId(), BlockRegistry.POTTED_MOONGLOW);
		emptyPot.addPlant(BlockRegistry.DEATHWEED.getId(), BlockRegistry.POTTED_DEATHWEED);

		// Setup custom boats
		ItemRegistry.BURNED_OAK_BOAT.get()
				.setBoatEntityType(EntityRegistry.BURNED_OAK_BOAT_ENTITY.get());
		ItemRegistry.BURNED_OAK_CHEST_BOAT.get()
				.setBoatEntityType(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get());
		ItemRegistry.STARDUST_BOAT.get()
				.setBoatEntityType(EntityRegistry.STARDUST_BOAT_ENTITY.get());
		ItemRegistry.STARDUST_CHEST_BOAT.get()
				.setBoatEntityType(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get());

		// Set brewing recipes, as these are not done via JSON like other recipes
		// Celestial potions
		PotionBrewing.addMix(
				Potions.AWKWARD,
				BlockItemRegistry.MOONGLOW_ITEM.get(),
				PotionRegistry.CELESTIAL_BREW_POTION.get());
		PotionBrewing.addMix(
				PotionRegistry.CELESTIAL_BREW_POTION.get(),
				Items.REDSTONE,
				PotionRegistry.LONG_CELESTIAL_BREW_POTION.get());

		// Death potions
		PotionBrewing.addMix(
				Potions.AWKWARD,
				BlockItemRegistry.DEATHWEED_ITEM.get(),
				PotionRegistry.DEATH_POTION.get());
		PotionBrewing.addMix(
				PotionRegistry.DEATH_POTION.get(),
				Items.GLOWSTONE_DUST,
				PotionRegistry.STRONG_DEATH_POTION.get());
		PotionBrewing.addMix(
				PotionRegistry.DEATH_POTION.get(),
				Items.REDSTONE,
				PotionRegistry.LONG_DEATH_POTION.get());
		PotionBrewing.addMix(
				Potions.STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.DEATH_POTION.get());
		PotionBrewing.addMix(
				Potions.STRONG_STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.STRONG_DEATH_POTION.get());
		PotionBrewing.addMix(
				Potions.LONG_STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.LONG_DEATH_POTION.get());

		// Populate enchant cap map
		TomlConfigOps.INSTANCE.getMapValues(ImmersiveWeapons.COMMON_CONFIG.skygazerEnchantCaps().get())
				.result()
				.ifPresent(map -> map.forEach((pair) -> SkygazerEntity.ENCHANT_CAPS.put((String) pair.getFirst(), (Integer) pair.getSecond())));
	}
}