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
		ItemRegistry.WOODEN_PIKE.get().addReachDistanceAttributes();
		ItemRegistry.STONE_PIKE.get().addReachDistanceAttributes();
		ItemRegistry.GOLDEN_PIKE.get().addReachDistanceAttributes();
		ItemRegistry.COPPER_PIKE.get().addReachDistanceAttributes();
		ItemRegistry.IRON_PIKE.get().addReachDistanceAttributes();
		ItemRegistry.COBALT_PIKE.get().addReachDistanceAttributes();
		ItemRegistry.DIAMOND_PIKE.get().addReachDistanceAttributes();
		ItemRegistry.NETHERITE_PIKE.get().addReachDistanceAttributes();

		// Set special attributes for gauntlets
		ItemRegistry.WOODEN_GAUNTLET.get().addReachDistanceAttributes();
		ItemRegistry.STONE_GAUNTLET.get().addReachDistanceAttributes();
		ItemRegistry.GOLDEN_GAUNTLET.get().addReachDistanceAttributes();
		ItemRegistry.COPPER_GAUNTLET.get().addReachDistanceAttributes();
		ItemRegistry.IRON_GAUNTLET.get().addReachDistanceAttributes();
		ItemRegistry.COBALT_GAUNTLET.get().addReachDistanceAttributes();
		ItemRegistry.DIAMOND_GAUNTLET.get().addReachDistanceAttributes();
		ItemRegistry.NETHERITE_GAUNTLET.get().addReachDistanceAttributes();

		// Set custom armor equip sounds, as these don't exist during the initialization of materials
		CustomArmorMaterials.TESLA.setEquipSound(SoundEventRegistry.TESLA_ARMOR_EQUIP.get());
		CustomArmorMaterials.MOLTEN.setEquipSound(SoundEventRegistry.MOLTEN_ARMOR_EQUIP.get());
		CustomArmorMaterials.VENTUS.setEquipSound(SoundEventRegistry.VENTUS_ARMOR_EQUIP.get());
		CustomArmorMaterials.COPPER.setEquipSound(SoundEventRegistry.COPPER_ARMOR_EQUIP.get());
		CustomArmorMaterials.COBALT.setEquipSound(SoundEventRegistry.COBALT_ARMOR_EQUIP.get());
		CustomArmorMaterials.ASTRAL.setEquipSound(SoundEventRegistry.ASTRAL_ARMOR_EQUIP.get());
		CustomArmorMaterials.STARSTORM.setEquipSound(SoundEventRegistry.STARSTORM_ARMOR_EQUIP.get());

		// Add custom plants to the flower pot block
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
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
				Ingredient.of(BlockItemRegistry.MOONGLOW_ITEM.get()),
				PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.CELESTIAL_BREW_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.CELESTIAL_BREW_POTION.get())),
				Ingredient.of(Items.REDSTONE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
		// Death potions
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
				Ingredient.of(BlockItemRegistry.DEATHWEED_ITEM.get()),
				PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.DEATH_POTION.get())),
				Ingredient.of(Items.GLOWSTONE_DUST),
				PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.STRONG_DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.DEATH_POTION.get())),
				Ingredient.of(Items.REDSTONE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.LONG_DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRENGTH)),
				Ingredient.of(Items.FERMENTED_SPIDER_EYE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_STRENGTH)),
				Ingredient.of(Items.FERMENTED_SPIDER_EYE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.STRONG_DEATH_POTION.get()));
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LONG_STRENGTH)),
				Ingredient.of(Items.FERMENTED_SPIDER_EYE),
				PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.LONG_DEATH_POTION.get()));
	}
}