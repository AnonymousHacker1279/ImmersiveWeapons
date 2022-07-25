package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.function.Consumer;

import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.blockRegistryPath;
import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.itemRegistryPath;

public class FamilyGenerator extends RecipeProvider {

	public FamilyGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	public void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
		woodFamilies(consumer);
		stoneFamilies(consumer);
		toolFamilies(consumer);
	}

	private void woodFamilies(@NotNull Consumer<FinishedRecipe> consumer) {
		for (WoodFamilies family : WoodFamilies.FAMILIES) {
			Block planks = family.planks().get();
			final String planksTriggerName = "has_planks";
			InventoryChangeTrigger.TriggerInstance planksTrigger = has(planks);

			// Button
			ShapelessRecipeBuilder.shapeless(family.button().get())
					.requires(planks)
					.group("wooden_button")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.button().get()));

			// Door
			ShapedRecipeBuilder.shaped(family.door().get(), 3)
					.pattern("aa")
					.pattern("aa")
					.pattern("aa")
					.define('a', planks)
					.group("wooden_door")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.door().get()));

			// Fence
			ShapedRecipeBuilder.shaped(family.fence().get(), 3)
					.pattern("aba")
					.pattern("aba")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.group("wooden_fence")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.fence().get()));

			// Fence Gate
			ShapedRecipeBuilder.shaped(family.fenceGate().get())
					.pattern("bab")
					.pattern("bab")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.group("wooden_fence_gate")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.fenceGate().get()));

			// Planks
			ShapelessRecipeBuilder.shapeless(planks, 4)
					.requires(family.logsItemTag())
					.group("planks")
					.unlockedBy("has_" + blockRegistryPath(family.log().get()).getPath(), has(family.logsItemTag()))
					.save(consumer, blockRegistryPath(planks));

			// Wood
			ShapedRecipeBuilder.shaped(family.wood().get(), 3)
					.pattern("aa")
					.pattern("aa")
					.define('a', family.log().get())
					.group("wood")
					.unlockedBy("has_" + blockRegistryPath(family.log().get()).getPath(), has(family.logsItemTag()))
					.save(consumer, blockRegistryPath(family.wood().get()));

			// Pressure Plate
			ShapedRecipeBuilder.shaped(family.pressurePlate().get())
					.pattern("aa")
					.define('a', planks)
					.group("wooden_pressure_plate")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.pressurePlate().get()));

			// Sign
			ShapedRecipeBuilder.shaped(family.standingSign().get(), 3)
					.group("sign")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.pattern("aaa")
					.pattern("aaa")
					.pattern(" b ")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.standingSign().get()));

			// Slab
			ShapedRecipeBuilder.shaped(family.slab().get(), 6)
					.pattern("aaa")
					.define('a', planks)
					.group("wooden_slab")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.slab().get()));

			// Stairs
			ShapedRecipeBuilder.shaped(family.stairs().get(), 4)
					.pattern("a  ")
					.pattern("aa ")
					.pattern("aaa")
					.define('a', planks)
					.group("wooden_stairs")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.stairs().get()));

			// Trapdoor
			ShapedRecipeBuilder.shaped(family.trapdoor().get(), 2)
					.pattern("aaa")
					.pattern("aaa")
					.define('a', planks)
					.group("wooden_trapdoor")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(consumer, blockRegistryPath(family.trapdoor().get()));

			// Boat
			ShapedRecipeBuilder.shaped(family.boat().get())
					.pattern("a a")
					.pattern("aaa")
					.define('a', planks)
					.group("boat")
					.unlockedBy("water", insideOf(Blocks.WATER))
					.save(consumer, itemRegistryPath(family.boat().get()));

			// Chest boat
			ShapelessRecipeBuilder.shapeless(family.chestBoat().get())
					.requires(Items.CHEST)
					.requires(family.boat().get())
					.group("chest_boat")
					.unlockedBy("water", insideOf(Blocks.WATER))
					.save(consumer, itemRegistryPath(family.chestBoat().get()));
		}
	}

	private void stoneFamilies(@NotNull Consumer<FinishedRecipe> consumer) {
		for (StoneFamilies family : StoneFamilies.FAMILIES) {
			Block stone = family.stone().get();
			Block bricks = family.bricks().get();
			final String stoneTriggerName = "has_stone";
			InventoryChangeTrigger.TriggerInstance bricksTrigger = has(bricks);

			// Bricks from crafting table
			ShapedRecipeBuilder.shaped(bricks, 4)
					.define('a', stone)
					.pattern("aa ")
					.pattern("aa ")
					.unlockedBy("has_" + blockRegistryPath(stone).getPath(), has(stone))
					.save(consumer, blockRegistryPath(bricks));
			// Bricks from stonecutter
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(stone), bricks)
					.unlockedBy("has_" + blockRegistryPath(stone).getPath(), has(stone))
					.save(consumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(bricks, stone) + "_stonecutting");

			// Slab from crafting table
			ShapedRecipeBuilder.shaped(family.slab().get(), 6)
					.pattern("aaa")
					.define('a', bricks)
					.unlockedBy(stoneTriggerName, bricksTrigger)
					.save(consumer, blockRegistryPath(family.slab().get()));
			// Slab from stonecutter
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(bricks), family.slab().get())
					.unlockedBy(stoneTriggerName, bricksTrigger)
					.save(consumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(family.slab().get(), bricks) + "_stonecutting");

			// Stairs from crafting table
			ShapedRecipeBuilder.shaped(family.stairs().get(), 4)
					.pattern("a  ")
					.pattern("aa ")
					.pattern("aaa")
					.define('a', bricks)
					.unlockedBy(stoneTriggerName, bricksTrigger)
					.save(consumer, blockRegistryPath(family.stairs().get()));
			// Stairs from stonecutter
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(bricks), family.stairs().get())
					.unlockedBy(stoneTriggerName, bricksTrigger)
					.save(consumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(family.stairs().get(), bricks) + "_stonecutting");

			// Pillar from crafting table
			ShapedRecipeBuilder.shaped(family.pillar().get(), 4)
					.pattern("a")
					.pattern("a")
					.define('a', bricks)
					.unlockedBy(stoneTriggerName, bricksTrigger)
					.save(consumer, blockRegistryPath(family.pillar().get()));
			// Pillar from stonecutter
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(bricks), family.pillar().get())
					.unlockedBy(stoneTriggerName, bricksTrigger)
					.save(consumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(family.pillar().get(), bricks) + "_stonecutting");
		}
	}

	private void toolFamilies(@NotNull Consumer<FinishedRecipe> consumer) {
		for (ToolFamilies family : ToolFamilies.FAMILIES) {
			TagKey<Item> material = family.material();
			final String materialTriggerName = "has_material";

			// Sword
			ShapedRecipeBuilder.shaped(family.sword().get())
					.pattern("a")
					.pattern("a")
					.pattern("b")
					.define('a', material)
					.define('b', family.handle())
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.sword().get()));

			// Pickaxe
			ShapedRecipeBuilder.shaped(family.pickaxe().get())
					.pattern("aaa")
					.pattern(" b ")
					.pattern(" b ")
					.define('a', material)
					.define('b', family.handle())
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.pickaxe().get()));

			// Axe
			ShapedRecipeBuilder.shaped(family.axe().get())
					.pattern("aa")
					.pattern("ab")
					.pattern(" b")
					.define('a', material)
					.define('b', family.handle())
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.axe().get()));

			// Shovel
			ShapedRecipeBuilder.shaped(family.shovel().get())
					.pattern("a")
					.pattern("b")
					.pattern("b")
					.define('a', material)
					.define('b', family.handle())
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.shovel().get()));

			// Hoe
			ShapedRecipeBuilder.shaped(family.hoe().get())
					.pattern("aa")
					.pattern(" b")
					.pattern(" b")
					.define('a', material)
					.define('b', family.handle())
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.hoe().get()));
		}
	}
}