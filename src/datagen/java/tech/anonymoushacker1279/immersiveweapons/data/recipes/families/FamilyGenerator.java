package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet;
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
import java.util.concurrent.CompletableFuture;

public class FamilyGenerator extends RecipeGenerator {

	public FamilyGenerator(HolderLookup.Provider registries, RecipeOutput output) {
		super(registries, output);
	}

	@Override
	protected void buildRecipes() {
		super.buildRecipes();

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
			ShapelessRecipeBuilder.shapeless(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.button().get())
					.requires(planks)
					.group("wooden_button")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output);

			// Door
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.door().get(), 3)
					.pattern("aa")
					.pattern("aa")
					.pattern("aa")
					.define('a', planks)
					.group("wooden_door")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output);

			// Fence
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.fence().get(), 3)
					.pattern("aba")
					.pattern("aba")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.group("wooden_fence")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output);

			// Fence Gate
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.fenceGate().get())
					.pattern("bab")
					.pattern("bab")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.group("wooden_fence_gate")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output);

			// Planks
			ShapelessRecipeBuilder.shapeless(itemGetter, RecipeCategory.BUILDING_BLOCKS, planks, 4)
					.requires(family.logsItemTag())
					.group("planks")
					.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(family.log().get()).getPath(), has(family.logsItemTag()))
					.save(output);

			// Wood
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.wood().get(), 3)
					.pattern("aa")
					.pattern("aa")
					.define('a', family.log().get())
					.group("wood")
					.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(family.log().get()).getPath(), has(family.logsItemTag()))
					.save(output);

			// Pressure Plate
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.pressurePlate().get())
					.pattern("aa")
					.define('a', planks)
					.group("wooden_pressure_plate")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output);

			// Sign
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.standingSign().get(), 3)
					.group("sign")
					.define('a', planks)
					.define('b', Tags.Items.RODS_WOODEN)
					.pattern("aaa")
					.pattern("aaa")
					.pattern(" b ")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output);

			// Hanging Sign
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.ceilingHangingSign().get(), 6)
					.group("sign")
					.define('a', family.strippedLog().get())
					.define('b', Items.CHAIN)
					.pattern("b b")
					.pattern("aaa")
					.pattern("aaa")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output);

			// Slab
			slab(RecipeCategory.BUILDING_BLOCKS, family.slab().get(), planks);

			// Stairs
			stairs(family.stairs().get(), planks, "wooden_stairs", planksTriggerName, planksTrigger);

			// Trapdoor
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.trapdoor().get(), 2)
					.pattern("aaa")
					.pattern("aaa")
					.define('a', planks)
					.group("wooden_trapdoor")
					.unlockedBy(planksTriggerName, planksTrigger)
					.save(output);

			// Boat
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.boat().get())
					.pattern("a a")
					.pattern("aaa")
					.define('a', planks)
					.group("boat")
					.unlockedBy("water", insideOf(Blocks.WATER))
					.save(output);

			// Chest boat
			ShapelessRecipeBuilder.shapeless(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.chestBoat().get())
					.requires(Items.CHEST)
					.requires(family.boat().get())
					.group("chest_boat")
					.unlockedBy("water", insideOf(Blocks.WATER))
					.save(output);
		}
	}

	private void stoneFamilies() {
		for (StoneFamilies family : StoneFamilies.FAMILIES) {
			Block stone = family.stone().get();
			Block bricks = family.bricks().get();
			final String stoneTriggerName = "has_stone";
			Criterion<TriggerInstance> bricksTrigger = has(bricks);

			bricks(bricks, stone);
			if (family.bricksHaveStonecutterRecipe()) {
				stonecutterBricks(bricks, stone);
			}

			slab(RecipeCategory.BUILDING_BLOCKS, family.slab().get(), bricks);
			stonecutterSlab(family.slab().get(), bricks, stoneTriggerName, bricksTrigger);

			stairs(family.stairs().get(), bricks, stoneTriggerName, bricksTrigger);
			stonecutterStairs(family.stairs().get(), bricks, stoneTriggerName, bricksTrigger);

			wall(family.wall().get(), bricks, stoneTriggerName, bricksTrigger);
			stonecutterWall(family.wall().get(), bricks, stoneTriggerName, bricksTrigger);

			// Start null checking these entries, because not every family has one.
			if (family.pillar() != null) {
				pillar(family.pillar().get(), bricks, stoneTriggerName, bricksTrigger);
				stonecutterPillar(family.pillar().get(), bricks, stoneTriggerName, bricksTrigger);
			}

			if (family.chiseled() != null) {
				chiseled(family.chiseled().get(), bricks, stoneTriggerName, bricksTrigger);
				stonecutterChiseled(family.chiseled().get(), bricks, stoneTriggerName, bricksTrigger);
			}

			if (family.cut() != null) {
				cut(family.cut().get(), bricks, stoneTriggerName, bricksTrigger);
				stonecutterCut(family.cut().get(), bricks, stoneTriggerName, bricksTrigger);

				if (family.cutSlab() != null) {
					slab(RecipeCategory.BUILDING_BLOCKS, family.cutSlab().get(), family.cut().get());
					stonecutterSlab(family.cutSlab().get(), family.cut().get(), stoneTriggerName, bricksTrigger);
				}
			}

			if (family.smooth() != null) {
				createSmeltingRecipe(
						List.of(family.bricks().get()),
						family.smooth().get(),
						0.1f,
						200,
						null);

				if (family.smoothSlab() != null) {
					slab(RecipeCategory.BUILDING_BLOCKS, family.smoothSlab().get(), family.smooth().get());
					stonecutterSlab(family.smoothSlab().get(), family.smooth().get(), stoneTriggerName, bricksTrigger);
				}

				if (family.smoothStairs() != null) {
					stairs(family.smoothStairs().get(), family.smooth().get(), stoneTriggerName, bricksTrigger);
					stonecutterStairs(family.smoothStairs().get(), family.smooth().get(), stoneTriggerName, bricksTrigger);
				}
			}
		}
	}

	private void toolFamilies(RecipeOutput output) {
		for (ToolFamilies family : ToolFamilies.FAMILIES) {
			TagKey<Item> material = family.material();
			HolderSet<Item> materialSet = BuiltInRegistries.ITEM.getOrThrow(material);
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
									Ingredient.of(materialSet),
									RecipeCategory.MISC,
									upgradeMap[i])
							.unlocks(materialTriggerName, has(material))
							.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(upgradeMap[i]) + "_smithing");

					i++;
				}
			}

			if (!smithTools) {
				// Sword
				ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.sword().get())
						.pattern("a")
						.pattern("a")
						.pattern("b")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output);

				// Pickaxe
				ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.pickaxe().get())
						.pattern("aaa")
						.pattern(" b ")
						.pattern(" b ")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output);

				// Axe
				ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.axe().get())
						.pattern("aa")
						.pattern("ab")
						.pattern(" b")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output);

				// Shovel
				ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.shovel().get())
						.pattern("a")
						.pattern("b")
						.pattern("b")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output);

				// Hoe
				ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.hoe().get())
						.pattern("aa")
						.pattern(" b")
						.pattern(" b")
						.define('a', material)
						.define('b', family.handle())
						.unlockedBy(materialTriggerName, has(material))
						.save(output);

				// Gauntlet
				createGauntlet(family.gauntlet().get(), material);

				if (family.pikeHead() != null) {
					// Pike
					createPike(family.pike().get(), material, family.pikeHead().get());

					// Pike head
					createPikeHead(family.pikeHead().get(), material, family.nugget());
				}
			}

			if (ToolFamilies.FAMILIES_USE_NUGGETS_FOR_PROJECTILES.contains(family)) {
				// Arrow
				createArrow(family.arrow().get(), family.nugget());
			} else {
				// Arrow
				createArrow(family.arrow().get(), family.material());
			}
		}
	}

	private void armorFamilies(RecipeOutput output) {
		for (ArmorFamilies family : ArmorFamilies.FAMILIES) {
			TagKey<Item> material = family.material();
			HolderSet<Item> materialSet = BuiltInRegistries.ITEM.getOrThrow(material);
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
									Ingredient.of(materialSet),
									RecipeCategory.MISC,
									upgradeMap[i])
							.unlocks(materialTriggerName, has(material))
							.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(upgradeMap[i]) + "_smithing");

					i++;
				}

				continue;
			}

			// Helmet
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.helmet().get())
					.pattern("aaa")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(output);

			// Chestplate
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.chestplate().get())
					.pattern("a a")
					.pattern("aaa")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(output);

			// Leggings
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.leggings().get())
					.pattern("aaa")
					.pattern("a a")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(output);

			// Boots
			ShapedRecipeBuilder.shaped(itemGetter, RecipeCategory.BUILDING_BLOCKS, family.boots().get())
					.pattern("a a")
					.pattern("a a")
					.define('a', material)
					.unlockedBy(materialTriggerName, has(material))
					.save(output);
		}
	}

	private void vanillaTieredItemFamilies() {
		for (VanillaTieredItemFamilies family : VanillaTieredItemFamilies.FAMILIES) {
			TagKey<Item> material = family.material();

			// Gauntlet
			createGauntlet(family.gauntlet().get(), material);

			// Pike
			createPike(family.pike().get(), material, family.pikeHead().get());

			// Pike head
			createPikeHead(family.pikeHead().get(), material, family.nugget());

			if (VanillaTieredItemFamilies.FAMILIES_USE_NUGGETS_FOR_PROJECTILES.contains(family)) {
				// Arrow
				createArrow(family.arrow().get(), family.nugget());
			} else {
				// Arrow
				createArrow(family.arrow().get(), family.material());
			}
		}
	}

	public static class Runner extends RecipeProvider.Runner {

		public Runner(PackOutput output, CompletableFuture<Provider> registries) {
			super(output, registries);
		}

		@Override
		protected RecipeProvider createRecipeProvider(Provider registries, RecipeOutput output) {
			return new FamilyGenerator(registries, output);
		}

		@Override
		public String getName() {
			return "Immersive Weapons Recipes";
		}
	}
}