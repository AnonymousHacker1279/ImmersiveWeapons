package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.RecipeGenerator;

import java.util.List;
import java.util.function.Consumer;

import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.blockRegistryPath;
import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.itemRegistryPath;

public class FamilyGenerator extends RecipeProvider {

	public FamilyGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		woodFamilies(consumer);
		stoneFamilies();
		toolFamilies(consumer);
		armorFamilies(consumer);
		vanillaTieredItemFamilies();
	}

	private void woodFamilies(Consumer<FinishedRecipe> consumer) {
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
			RecipeGenerator.CraftingTableRecipes.slab(family.slab().get(), planks, planksTriggerName, planksTrigger);

			// Stairs
			RecipeGenerator.CraftingTableRecipes.stairs(family.stairs().get(), planks, planksTriggerName, planksTrigger);

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

	private void stoneFamilies() {
		for (StoneFamilies family : StoneFamilies.FAMILIES) {
			Block stone = family.stone().get();
			Block bricks = family.bricks().get();
			final String stoneTriggerName = "has_stone";
			InventoryChangeTrigger.TriggerInstance bricksTrigger = has(bricks);

			RecipeGenerator.CraftingTableRecipes.bricks(bricks, stone);
			if (family.bricksHaveStonecutterRecipe()) {
				RecipeGenerator.StonecutterRecipes.bricks(bricks, stone);
			}

			RecipeGenerator.CraftingTableRecipes.slab(family.slab().get(), bricks, stoneTriggerName, bricksTrigger);
			RecipeGenerator.StonecutterRecipes.slab(family.slab().get(), bricks, stoneTriggerName, bricksTrigger);

			RecipeGenerator.CraftingTableRecipes.stairs(family.stairs().get(), bricks, stoneTriggerName, bricksTrigger);
			RecipeGenerator.StonecutterRecipes.stairs(family.stairs().get(), bricks, stoneTriggerName, bricksTrigger);

			RecipeGenerator.CraftingTableRecipes.wall(family.wall().get(), bricks, stoneTriggerName, bricksTrigger);
			RecipeGenerator.StonecutterRecipes.wall(family.wall().get(), bricks, stoneTriggerName, bricksTrigger);

			// Start null checking these entries, because not every family has one.
			if (family.pillar() != null) {
				RecipeGenerator.CraftingTableRecipes.pillar(family.pillar().get(), bricks, stoneTriggerName, bricksTrigger);
				RecipeGenerator.StonecutterRecipes.pillar(family.pillar().get(), bricks, stoneTriggerName, bricksTrigger);
			}

			if (family.chiseled() != null) {
				RecipeGenerator.CraftingTableRecipes.chiseled(family.chiseled().get(), bricks, stoneTriggerName, bricksTrigger);
				RecipeGenerator.StonecutterRecipes.chiseled(family.chiseled().get(), bricks, stoneTriggerName, bricksTrigger);
			}

			if (family.cut() != null) {
				RecipeGenerator.CraftingTableRecipes.cut(family.cut().get(), bricks, stoneTriggerName, bricksTrigger);
				RecipeGenerator.StonecutterRecipes.cut(family.cut().get(), bricks, stoneTriggerName, bricksTrigger);

				if (family.cutSlab() != null) {
					RecipeGenerator.CraftingTableRecipes.slab(family.cutSlab().get(), family.cut().get(), stoneTriggerName, bricksTrigger);
					RecipeGenerator.StonecutterRecipes.slab(family.cutSlab().get(), family.cut().get(), stoneTriggerName, bricksTrigger);
				}
			}

			if (family.smooth() != null) {
				RecipeGenerator.createSmeltingRecipe(
						List.of(family.bricks().get()),
						family.smooth().get(),
						0.1f,
						200,
						null);

				if (family.smoothSlab() != null) {
					RecipeGenerator.CraftingTableRecipes.slab(family.smoothSlab().get(), family.smooth().get(), stoneTriggerName, bricksTrigger);
					RecipeGenerator.StonecutterRecipes.slab(family.smoothSlab().get(), family.smooth().get(), stoneTriggerName, bricksTrigger);
				}

				if (family.smoothStairs() != null) {
					RecipeGenerator.CraftingTableRecipes.stairs(family.smoothStairs().get(), family.smooth().get(), stoneTriggerName, bricksTrigger);
					RecipeGenerator.StonecutterRecipes.stairs(family.smoothStairs().get(), family.smooth().get(), stoneTriggerName, bricksTrigger);
				}
			}
		}
	}

	private void toolFamilies(Consumer<FinishedRecipe> consumer) {
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

			// Start doing null checks because not all tool families have these items.

			// Gauntlet
			if (family.gauntlet() != null) {
				RecipeGenerator.createGauntlet(family.gauntlet().get(), material);
			}

			// Pike
			if (family.pike() != null) {
				if (family.pikeHead() != null) {
					RecipeGenerator.createPike(family.pike().get(), material, family.pikeHead().get());
				}
			}

			// Pike head
			if (family.pikeHead() != null) {
				if (family.nugget() != null) {
					RecipeGenerator.createPikeHead(family.pikeHead().get(), material, family.nugget());
				}
			}

			// Arrow
			if (family.arrow() != null) {
				RecipeGenerator.createArrow(family.arrow().get(), family.material());
			}

			// Musket ball
			if (family.musketBall() != null) {
				RecipeGenerator.createMusketBall(family.musketBall().get(), family.material());
			}
		}
	}

	private void armorFamilies(Consumer<FinishedRecipe> consumer) {
		for (ArmorFamilies family : ArmorFamilies.FAMILIES) {
			TagKey<Item> material = family.material();
			final String materialTriggerName = "has_material";

			// Helmet
			ShapedRecipeBuilder.shaped(family.helmet().get())
					.pattern("aaa")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.helmet().get()));

			// Chestplate
			ShapedRecipeBuilder.shaped(family.chestplate().get())
					.pattern("a a")
					.pattern("aaa")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.chestplate().get()));

			// Leggings
			ShapedRecipeBuilder.shaped(family.leggings().get())
					.pattern("aaa")
					.pattern("a a")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.leggings().get()));

			// Boots
			ShapedRecipeBuilder.shaped(family.boots().get())
					.pattern("a a")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(consumer, itemRegistryPath(family.boots().get()));
		}
	}

	private void vanillaTieredItemFamilies() {
		for (VanillaTieredItemFamilies family : VanillaTieredItemFamilies.FAMILIES) {
			TagKey<Item> material = family.material();

			// Gauntlet
			RecipeGenerator.createGauntlet(family.gauntlet().get(), material);

			// Pike
			RecipeGenerator.createPike(family.pike().get(), material, family.pikeHead().get());

			// Pike head
			RecipeGenerator.createPikeHead(family.pikeHead().get(), material, family.nugget());

			// Arrow
			RecipeGenerator.createArrow(family.arrow().get(), family.material());

			// Musket ball
			RecipeGenerator.createMusketBall(family.musketBall().get(), family.material());
		}
	}
}