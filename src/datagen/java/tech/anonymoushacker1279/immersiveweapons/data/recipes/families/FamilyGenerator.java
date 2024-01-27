package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.RecipeGenerator;

import java.util.List;

public class FamilyGenerator extends RecipeGenerator {

	public FamilyGenerator(PackOutput output) {
		super(output);
	}

	@Override
	protected void buildRecipes(RecipeOutput output) {
		super.buildRecipes(output);

		woodFamilies(output);
		stoneFamilies();
		toolFamilies(output);
		armorFamilies(output);
		vanillaTieredItemFamilies();
	}

	private void woodFamilies(RecipeOutput output) {
		for (WoodFamilies family : WoodFamilies.FAMILIES) {
			Block planks = family.planks().get();
			final String planksTriggerName = "has_planks";
			Criterion<TriggerInstance> planksTrigger = has(planks);

			// Button
			ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, family.button().get())
					.requires(planks)
					.group("wooden_button")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(family.button().get()));

			// Door
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.door().get(), 3)
					.pattern("aa")
					.pattern("aa")
					.pattern("aa")
					.define('a', planks)
					.group("wooden_door")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(family.door().get()));

			// Fence
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.fence().get(), 3)
					.pattern("aba")
					.pattern("aba")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.group("wooden_fence")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(family.fence().get()));

			// Fence Gate
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.fenceGate().get())
					.pattern("bab")
					.pattern("bab")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.group("wooden_fence_gate")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(family.fenceGate().get()));

			// Planks
			ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
					.requires(family.logsItemTag())
					.group("planks")
					.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(family.log().get()).getPath(), has(family.logsItemTag()))
					.save(output, BuiltInRegistries.BLOCK.getKey(planks));

			// Wood
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.wood().get(), 3)
					.pattern("aa")
					.pattern("aa")
					.define('a', family.log().get())
					.group("wood")
					.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(family.log().get()).getPath(), has(family.logsItemTag()))
					.save(output, BuiltInRegistries.BLOCK.getKey(family.wood().get()));

			// Pressure Plate
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.pressurePlate().get())
					.pattern("aa")
					.define('a', planks)
					.group("wooden_pressure_plate")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(family.pressurePlate().get()));

			// Sign
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.standingSign().get(), 3)
					.group("sign")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.pattern("aaa")
					.pattern("aaa")
					.pattern(" b ")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(family.standingSign().get()));

			// Hanging Sign
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.ceilingHangingSign().get(), 6)
					.group("sign")
					.define('a', family.strippedLog().get())
					.define('b', Items.CHAIN)
					.pattern("b b")
					.pattern("aaa")
					.pattern("aaa")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(family.wallSign().get()));

			// Slab
			RecipeGenerator.CraftingTableRecipes.slab(family.slab().get(), planks, "wooden_slab", planksTriggerName, planksTrigger);

			// Stairs
			RecipeGenerator.CraftingTableRecipes.stairs(family.stairs().get(), planks, "wooden_stairs", planksTriggerName, planksTrigger);

			// Trapdoor
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.trapdoor().get(), 2)
					.pattern("aaa")
					.pattern("aaa")
					.define('a', planks)
					.group("wooden_trapdoor")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(family.trapdoor().get()));

			// Boat
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.boat().get())
					.pattern("a a")
					.pattern("aaa")
					.define('a', planks)
					.group("boat")
					.unlockedBy("water", insideOf(Blocks.WATER))
					.save(output, BuiltInRegistries.ITEM.getKey(family.boat().get()));

			// Chest boat
			ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, family.chestBoat().get())
					.requires(Items.CHEST)
					.requires(family.boat().get())
					.group("chest_boat")
					.unlockedBy("water", insideOf(Blocks.WATER))
					.save(output, BuiltInRegistries.ITEM.getKey(family.chestBoat().get()));
		}
	}

	private void stoneFamilies() {
		for (StoneFamilies family : StoneFamilies.FAMILIES) {
			Block stone = family.stone().get();
			Block bricks = family.bricks().get();
			final String stoneTriggerName = "has_stone";
			Criterion<TriggerInstance> bricksTrigger = has(bricks);

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

	private void toolFamilies(RecipeOutput output) {
		for (ToolFamilies family : ToolFamilies.FAMILIES) {
			TagKey<Item> material = family.material();
			final String materialTriggerName = "has_material";

			boolean smithTools = family.smithingTemplateItem() != null && family.smithingBaseUpgrades() != null;
			if (smithTools) {
				Item[] upgradeMap = new Item[7];
				upgradeMap[0] = family.sword().get();
				upgradeMap[1] = family.pickaxe().get();
				upgradeMap[2] = family.axe().get();
				upgradeMap[3] = family.shovel().get();
				upgradeMap[4] = family.hoe().get();
				upgradeMap[5] = family.gauntlet().get();
				upgradeMap[6] = family.pike().get();

				int i = 0;
				for (Item baseItem : family.smithingBaseUpgrades()) {
					SmithingTransformRecipeBuilder.smithing(
									Ingredient.of(family.smithingTemplateItem().get()),
									Ingredient.of(baseItem),
									Ingredient.of(material),
									RecipeCategory.MISC,
									upgradeMap[i])
							.unlocks(materialTriggerName, has(material))
							.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(upgradeMap[i]) + "_smithing");

					i++;
				}
			}

			if (!smithTools) {
				// Sword
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.sword().get())
						.pattern("a")
						.pattern("a")
						.pattern("b")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output, BuiltInRegistries.ITEM.getKey(family.sword().get()));

				// Pickaxe
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.pickaxe().get())
						.pattern("aaa")
						.pattern(" b ")
						.pattern(" b ")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output, BuiltInRegistries.ITEM.getKey(family.pickaxe().get()));

				// Axe
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.axe().get())
						.pattern("aa")
						.pattern("ab")
						.pattern(" b")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output, BuiltInRegistries.ITEM.getKey(family.axe().get()));

				// Shovel
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.shovel().get())
						.pattern("a")
						.pattern("b")
						.pattern("b")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output, BuiltInRegistries.ITEM.getKey(family.shovel().get()));

				// Hoe
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.hoe().get())
						.pattern("aa")
						.pattern(" b")
						.pattern(" b")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output, BuiltInRegistries.ITEM.getKey(family.hoe().get()));

				// Gauntlet
				RecipeGenerator.createGauntlet(family.gauntlet().get(), material);

				if (family.pikeHead() != null) {
					// Pike
					RecipeGenerator.createPike(family.pike().get(), material, family.pikeHead().get());

					// Pike head
					RecipeGenerator.createPikeHead(family.pikeHead().get(), material, family.nugget());
				}
			}

			if (ToolFamilies.FAMILIES_USE_NUGGETS_FOR_PROJECTILES.contains(family)) {
				// Arrow
				RecipeGenerator.createArrow(family.arrow().get(), family.nugget());
			} else {
				// Arrow
				RecipeGenerator.createArrow(family.arrow().get(), family.material());
			}
		}
	}

	private void armorFamilies(RecipeOutput output) {
		for (ArmorFamilies family : ArmorFamilies.FAMILIES) {
			TagKey<Item> material = family.material();
			final String materialTriggerName = "has_material";

			if (family.smithingTemplateItem() != null && family.smithingBaseUpgrades() != null) {
				Item[] upgradeMap = new Item[4];
				upgradeMap[0] = family.helmet().get();
				upgradeMap[1] = family.chestplate().get();
				upgradeMap[2] = family.leggings().get();
				upgradeMap[3] = family.boots().get();

				int i = 0;
				for (Item baseItem : family.smithingBaseUpgrades()) {
					SmithingTransformRecipeBuilder.smithing(
									Ingredient.of(family.smithingTemplateItem().get()),
									Ingredient.of(baseItem),
									Ingredient.of(material),
									RecipeCategory.MISC,
									upgradeMap[i])
							.unlocks(materialTriggerName, has(material))
							.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(upgradeMap[i]) + "_smithing");

					i++;
				}

				continue;
			}

			// Helmet
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.helmet().get())
					.pattern("aaa")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(output, BuiltInRegistries.ITEM.getKey(family.helmet().get()));

			// Chestplate
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.chestplate().get())
					.pattern("a a")
					.pattern("aaa")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(output, BuiltInRegistries.ITEM.getKey(family.chestplate().get()));

			// Leggings
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.leggings().get())
					.pattern("aaa")
					.pattern("a a")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(output, BuiltInRegistries.ITEM.getKey(family.leggings().get()));

			// Boots
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, family.boots().get())
					.pattern("a a")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(output, BuiltInRegistries.ITEM.getKey(family.boots().get()));
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

			if (VanillaTieredItemFamilies.FAMILIES_USE_NUGGETS_FOR_PROJECTILES.contains(family)) {
				// Arrow
				RecipeGenerator.createArrow(family.arrow().get(), family.nugget());
			} else {
				// Arrow
				RecipeGenerator.createArrow(family.arrow().get(), family.material());
			}
		}
	}
}