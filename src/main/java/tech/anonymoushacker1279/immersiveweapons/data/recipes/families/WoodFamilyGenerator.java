package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.blockRegistryPath;
import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.itemRegistryPath;

public class WoodFamilyGenerator extends RecipeProvider {

	public WoodFamilyGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	public void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
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
}