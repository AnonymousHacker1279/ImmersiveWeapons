package tech.anonymoushacker1279.immersiveweapons.init;

import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomArmorMaterials;

public class PostSetupHandler {

	/**
	 * Initialize attributes which must be applied after setup.
	 */
	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing post-setup handler");

		// Set special attributes for pikes
		DeferredRegistryHandler.WOODEN_PIKE.get().addReachDistanceAttributes();
		DeferredRegistryHandler.STONE_PIKE.get().addReachDistanceAttributes();
		DeferredRegistryHandler.GOLDEN_PIKE.get().addReachDistanceAttributes();
		DeferredRegistryHandler.COPPER_PIKE.get().addReachDistanceAttributes();
		DeferredRegistryHandler.IRON_PIKE.get().addReachDistanceAttributes();
		DeferredRegistryHandler.COBALT_PIKE.get().addReachDistanceAttributes();
		DeferredRegistryHandler.DIAMOND_PIKE.get().addReachDistanceAttributes();
		DeferredRegistryHandler.NETHERITE_PIKE.get().addReachDistanceAttributes();

		// Set special attributes for gauntlets
		DeferredRegistryHandler.WOODEN_GAUNTLET.get().addReachDistanceAttributes();
		DeferredRegistryHandler.STONE_GAUNTLET.get().addReachDistanceAttributes();
		DeferredRegistryHandler.GOLDEN_GAUNTLET.get().addReachDistanceAttributes();
		DeferredRegistryHandler.COPPER_GAUNTLET.get().addReachDistanceAttributes();
		DeferredRegistryHandler.IRON_GAUNTLET.get().addReachDistanceAttributes();
		DeferredRegistryHandler.COBALT_GAUNTLET.get().addReachDistanceAttributes();
		DeferredRegistryHandler.DIAMOND_GAUNTLET.get().addReachDistanceAttributes();
		DeferredRegistryHandler.NETHERITE_GAUNTLET.get().addReachDistanceAttributes();

		// Set custom armor equip sounds, as these don't exist during the initialization of materials
		CustomArmorMaterials.TESLA.setEquipSound(DeferredRegistryHandler.TESLA_ARMOR_EQUIP.get());
		CustomArmorMaterials.MOLTEN.setEquipSound(DeferredRegistryHandler.MOLTEN_ARMOR_EQUIP.get());
		CustomArmorMaterials.VENTUS.setEquipSound(DeferredRegistryHandler.VENTUS_ARMOR_EQUIP.get());
		CustomArmorMaterials.COPPER.setEquipSound(DeferredRegistryHandler.COPPER_ARMOR_EQUIP.get());
		CustomArmorMaterials.COBALT.setEquipSound(DeferredRegistryHandler.COBALT_ARMOR_EQUIP.get());
	}
}