package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
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

		// Add custom plants to the flower pot block
		FlowerPotBlock emptyPot = ((FlowerPotBlock) Blocks.FLOWER_POT);
		emptyPot.addPlant(DeferredRegistryHandler.MOONGLOW.getId(), DeferredRegistryHandler.POTTED_MOONGLOW);
		emptyPot.addPlant(DeferredRegistryHandler.DEATHWEED.getId(), DeferredRegistryHandler.POTTED_DEATHWEED);

		// Setup custom boats
		DeferredRegistryHandler.BURNED_OAK_BOAT.get()
				.setBoatEntityType(DeferredRegistryHandler.BURNED_OAK_BOAT_ENTITY.get());
		DeferredRegistryHandler.BURNED_OAK_CHEST_BOAT.get()
				.setBoatEntityType(DeferredRegistryHandler.BURNED_OAK_CHEST_BOAT_ENTITY.get());
		DeferredRegistryHandler.STARDUST_BOAT.get()
				.setBoatEntityType(DeferredRegistryHandler.STARDUST_BOAT_ENTITY.get());
		DeferredRegistryHandler.STARDUST_CHEST_BOAT.get()
				.setBoatEntityType(DeferredRegistryHandler.STARDUST_CHEST_BOAT_ENTITY.get());

		// Set brewing recipes, as these are not done via JSON like other recipes
		// Celestial potions
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
				Ingredient.of(DeferredRegistryHandler.MOONGLOW_ITEM.get()),
				PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.CELESTIAL_BREW_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.CELESTIAL_BREW_POTION.get())),
				Ingredient.of(Items.REDSTONE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.LONG_CELESTIAL_BREW_POTION.get()));
		// Death potions
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
				Ingredient.of(DeferredRegistryHandler.DEATHWEED_ITEM.get()),
				PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.DEATH_POTION.get())),
				Ingredient.of(Items.GLOWSTONE_DUST),
				PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.STRONG_DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.DEATH_POTION.get())),
				Ingredient.of(Items.REDSTONE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.LONG_DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRENGTH)),
				Ingredient.of(Items.FERMENTED_SPIDER_EYE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_STRENGTH)),
				Ingredient.of(Items.FERMENTED_SPIDER_EYE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.STRONG_DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LONG_STRENGTH)),
				Ingredient.of(Items.FERMENTED_SPIDER_EYE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.LONG_DEATH_POTION.get()));
	}
}