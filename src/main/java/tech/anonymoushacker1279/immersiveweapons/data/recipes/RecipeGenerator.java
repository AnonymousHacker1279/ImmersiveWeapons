package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.families.FamilyGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe.MaterialGroup;

import java.util.*;
import java.util.function.Consumer;

import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.blockRegistryPath;
import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.itemRegistryPath;

public class RecipeGenerator extends RecipeProvider {

	static Consumer<FinishedRecipe> finishedRecipeConsumer;
	final PackOutput packOutput;

	private static final ImmutableList<ItemLike> COBALT_SMELTABLES = ImmutableList.of(
			BlockItemRegistry.COBALT_ORE_ITEM.get(), BlockItemRegistry.DEEPSLATE_COBALT_ORE_ITEM.get(),
			ItemRegistry.RAW_COBALT.get());

	public RecipeGenerator(PackOutput output) {
		super(output);
		packOutput = output;
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> recipeConsumer) {
		finishedRecipeConsumer = recipeConsumer;

		createFlagItems();
		createGlassItems();
		createCobaltItems();
		createCopperItems();
		createMoltenItems();
		createVentusItems();
		createTeslaItems();
		createAstralItems();
		createStarstormItems();
		createSmithingItems();
		createSmallPartsItems();
		createBarrelTapItems();
		createAstralCrystalSorceryItems();
		createSmokeGrenades();
		createPanelItems();
		createShardItems();
		createWarriorStatueItems();
		createUtilityItems();
		createFirstAidItems();
		createFoodItems();
		createWeapons();
		createMudItems();
		createDecorations();
		createAccessories();
		createMiscellaneousItems();
		createMinecraftItems();

		FamilyGenerator familyGenerator = new FamilyGenerator(packOutput);
		familyGenerator.buildRecipes(recipeConsumer);
	}

	private void createFlagItems() {
		Item FLAG_POLE = BlockItemRegistry.FLAG_POLE_ITEM.get();

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.AMERICAN_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.BLUE_WOOL)
				.define('c', Items.RED_WOOL)
				.define('d', Items.WHITE_WOOL)
				.pattern("bcc")
				.pattern("bdd")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BRITISH_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.BLUE_WOOL)
				.define('c', Items.RED_WOOL)
				.define('d', Items.WHITE_WOOL)
				.pattern("cbc")
				.pattern("dcd")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.CANADIAN_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.RED_WOOL)
				.define('c', Items.WHITE_WOOL)
				.pattern("bcb")
				.pattern("bbb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.GADSDEN_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.YELLOW_WOOL)
				.define('c', Items.BLACK_WOOL)
				.define('d', Items.GREEN_WOOL)
				.pattern("bcb")
				.pattern("bdb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.MEXICAN_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.GREEN_WOOL)
				.define('c', Items.WHITE_WOOL)
				.define('d', Items.BROWN_WOOL)
				.define('e', Items.RED_WOOL)
				.pattern("bce")
				.pattern("bde")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.TROLL_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.WHITE_WOOL)
				.define('c', Items.BLACK_WOOL)
				.pattern("bcb")
				.pattern("cbc")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.IMMERSIVE_WEAPONS_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.LIGHT_BLUE_WOOL)
				.define('c', Items.GOLDEN_SWORD)
				.define('d', Items.YELLOW_WOOL)
				.pattern("bcb")
				.pattern("bdb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FLAG_POLE, 3)
				.define('a', Tags.Items.INGOTS_IRON)
				.pattern(" a ")
				.pattern(" a ")
				.pattern(" a ")
				.group("flag")
				.unlockedBy("iron", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);
	}

	private void createGlassItems() {
		createBulletproofStainedGlass(BlockItemRegistry.BLACK_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_BLACK);
		createBulletproofStainedGlass(BlockItemRegistry.BLUE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_BLUE);
		createBulletproofStainedGlass(BlockItemRegistry.BROWN_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_BROWN);
		createBulletproofStainedGlass(BlockItemRegistry.CYAN_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_CYAN);
		createBulletproofStainedGlass(BlockItemRegistry.GRAY_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_GRAY);
		createBulletproofStainedGlass(BlockItemRegistry.GREEN_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_GREEN);
		createBulletproofStainedGlass(BlockItemRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_LIGHT_BLUE);
		createBulletproofStainedGlass(BlockItemRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_LIGHT_GRAY);
		createBulletproofStainedGlass(BlockItemRegistry.LIME_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_LIME);
		createBulletproofStainedGlass(BlockItemRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_MAGENTA);
		createBulletproofStainedGlass(BlockItemRegistry.ORANGE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_ORANGE);
		createBulletproofStainedGlass(BlockItemRegistry.PINK_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_PINK);
		createBulletproofStainedGlass(BlockItemRegistry.PURPLE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_PURPLE);
		createBulletproofStainedGlass(BlockItemRegistry.RED_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_RED);
		createBulletproofStainedGlass(BlockItemRegistry.WHITE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_WHITE);
		createBulletproofStainedGlass(BlockItemRegistry.YELLOW_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_YELLOW);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BULLETPROOF_GLASS_ITEM.get(), 8)
				.define('a', Items.GLASS)
				.define('b', Tags.Items.INGOTS_IRON)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("bulletproof_glass")
				.unlockedBy("glass", has(Items.GLASS))
				.save(finishedRecipeConsumer);
	}

	private void createMudItems() {
		Item MUD = BlockItemRegistry.MUD_ITEM.get();
		Item DRIED_MUD = BlockItemRegistry.DRIED_MUD_ITEM.get();
		Item HARDENED_MUD = BlockItemRegistry.HARDENED_MUD_ITEM.get();

		createSmeltingRecipe(MUD, DRIED_MUD,
				0.1f, 100, "mud");
		createBlastingRecipe(MUD, DRIED_MUD,
				0.1f, 50, "mud");
		createSmeltingRecipe(DRIED_MUD, HARDENED_MUD,
				0.1f, 100, "mud");
		createBlastingRecipe(DRIED_MUD, HARDENED_MUD,
				0.1f, 50, "mud");

		// Slab from crafting table
		CraftingTableRecipes.slab(BlockRegistry.HARDENED_MUD_SLAB.get(), HARDENED_MUD, "mud", "hardened_mud", has(HARDENED_MUD));
		// Slab from stonecutter
		StonecutterRecipes.slab(BlockRegistry.HARDENED_MUD_SLAB.get(), HARDENED_MUD, "hardened_mud", has(HARDENED_MUD));

		// Stairs from crafting table
		CraftingTableRecipes.stairs(BlockRegistry.HARDENED_MUD_STAIRS.get(), HARDENED_MUD, "mud", "hardened_mud", has(HARDENED_MUD));
		// Stairs from stonecutter
		StonecutterRecipes.stairs(BlockRegistry.HARDENED_MUD_STAIRS.get(), HARDENED_MUD, "hardened_mud", has(HARDENED_MUD));

		// Hardened mud window
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.HARDENED_MUD_WINDOW_ITEM.get(), 8)
				.define('a', HARDENED_MUD)
				.pattern("aaa")
				.pattern("a a")
				.pattern("aaa")
				.group("mud")
				.unlockedBy("hardened_mud", has(HARDENED_MUD))
				.save(finishedRecipeConsumer);
		// Mud
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, MUD, 8)
				.requires(Items.WATER_BUCKET)
				.requires(Items.DIRT, 8)
				.group("mud")
				.unlockedBy("water_bucket", has(Items.WATER_BUCKET))
				.save(finishedRecipeConsumer);
		// Mud ball
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MUD_BALL.get(), 4)
				.requires(BlockRegistry.MUD.get())
				.group("mud")
				.unlockedBy("water_bucket", has(Items.WATER_BUCKET))
				.save(finishedRecipeConsumer);
	}

	private void createCobaltItems() {
		createCobaltIngot(ItemRegistry.COBALT_INGOT.get(), BlockItemRegistry.COBALT_BLOCK_ITEM.get());

		ShapedRecipeBuilder shapedBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.COBALT_BLOCK_ITEM.get())
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		create3x3Object(shapedBuilder, ForgeItemTagGroups.COBALT_INGOTS);

		ShapelessRecipeBuilder shapelessBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.COBALT_NUGGET.get(), 9)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createNuggetFromIngot(shapelessBuilder, ForgeItemTagGroups.COBALT_INGOTS);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.RAW_COBALT.get(), 9)
				.requires(BlockItemRegistry.RAW_COBALT_BLOCK_ITEM.get())
				.group("cobalt")
				.unlockedBy("raw_cobalt_block", has(BlockItemRegistry.RAW_COBALT_BLOCK_ITEM.get()))
				.save(finishedRecipeConsumer);

		shapedBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.RAW_COBALT_BLOCK_ITEM.get())
				.group("cobalt")
				.unlockedBy("raw_cobalt", has(ItemRegistry.RAW_COBALT.get()));
		create3x3Object(shapedBuilder, ItemRegistry.RAW_COBALT.get());
	}

	private void createCopperItems() {
		// Copper nugget
		ShapelessRecipeBuilder shapelessRecipeBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.COPPER_NUGGET.get(), 9)
				.group("copper_nugget")
				.unlockedBy("copper_ingot", has(Tags.Items.INGOTS_COPPER));
		createNuggetFromIngot(shapelessRecipeBuilder, Tags.Items.INGOTS_COPPER);
	}

	private void createMoltenItems() {
		createMoltenIngot(ItemRegistry.MOLTEN_INGOT.get(), BlockItemRegistry.MOLTEN_BLOCK_ITEM.get());

		// Molten block
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockItemRegistry.MOLTEN_BLOCK_ITEM.get())
				.group("molten")
				.unlockedBy("molten_ingot", has(IWItemTagGroups.MOLTEN_INGOTS));
		create3x3Object(builder, IWItemTagGroups.MOLTEN_INGOTS);

		// Molten smithing template
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MOLTEN_SMITHING_TEMPLATE.get())
				.define('a', IWItemTagGroups.MOLTEN_INGOTS)
				.define('b', Tags.Items.OBSIDIAN)
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("molten")
				.unlockedBy("molten_ingot", has(IWItemTagGroups.MOLTEN_INGOTS))
				.save(finishedRecipeConsumer);
	}

	private void createVentusItems() {
		Item VENTUS_STAFF_CORE = ItemRegistry.VENTUS_STAFF_CORE.get();

		// Ventus staff core
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, VENTUS_STAFF_CORE)
				.define('a', IWItemTagGroups.VENTUS_SHARDS)
				.define('b', Tags.Items.GEMS_DIAMOND)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("ventus")
				.unlockedBy("ventus_shard", has(IWItemTagGroups.VENTUS_SHARDS))
				.save(finishedRecipeConsumer);

		// Ventus staff
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.VENTUS_STAFF.get())
				.define('a', VENTUS_STAFF_CORE)
				.define('b', ItemRegistry.OBSIDIAN_ROD.get())
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" b ")
				.group("ventus")
				.unlockedBy("ventus_staff_core", has(VENTUS_STAFF_CORE))
				.save(finishedRecipeConsumer);
	}

	private void createTeslaItems() {
		createTeslaIngot(ItemRegistry.TESLA_INGOT.get(), BlockItemRegistry.TESLA_BLOCK_ITEM.get());

		ShapelessRecipeBuilder shapelessRecipeBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.TESLA_NUGGET.get(), 9)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(IWItemTagGroups.TESLA_INGOTS));
		createNuggetFromIngot(shapelessRecipeBuilder, IWItemTagGroups.TESLA_INGOTS);

		teslaSynthesizing(Items.STONE, Items.LAPIS_LAZULI, ItemRegistry.CONDUCTIVE_ALLOY.get(), 12000,
				ItemRegistry.ELECTRIC_INGOT.get());

		// Tesla Synthesizer
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TESLA_SYNTHESIZER.get())
				.define('a', Items.NETHERITE_INGOT)
				.define('b', BlockItemRegistry.MOLTEN_BLOCK_ITEM.get())
				.define('c', Tags.Items.INGOTS_COPPER)
				.define('d', Tags.Items.GLASS_PANES)
				.define('e', Tags.Items.INGOTS_IRON)
				.define('f', ItemRegistry.TESLA_INGOT.get())
				.pattern("fff")
				.pattern("cde")
				.pattern("aba")
				.group("tesla")
				.unlockedBy("tesla_ingot", has(IWItemTagGroups.TESLA_INGOTS))
				.save(finishedRecipeConsumer);

		// Tesla block
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.TESLA_BLOCK_ITEM.get())
				.group("tesla")
				.unlockedBy("tesla_ingot", has(IWItemTagGroups.TESLA_INGOTS));
		create3x3Object(builder, IWItemTagGroups.TESLA_INGOTS);
	}

	private void createAstralItems() {
		// Ingot from block
		ShapelessRecipeBuilder shapelessRecipeBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.ASTRAL_INGOT.get(), 9)
				.group("astral")
				.unlockedBy("astral_block", has(BlockItemRegistry.ASTRAL_BLOCK_ITEM.get()));
		createIngotFromBlock(shapelessRecipeBuilder, BlockItemRegistry.ASTRAL_BLOCK_ITEM.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ASTRAL_INGOT.get())
				.define('a', IWItemTagGroups.ASTRAL_NUGGETS)
				.pattern("aaa")
				.pattern("aaa")
				.pattern("aaa")
				.group("astral")
				.unlockedBy("astral_ingot", has(IWItemTagGroups.ASTRAL_INGOTS))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + ItemRegistry.ASTRAL_INGOT.get() + "_from_nuggets");

		// Nugget from ingot
		ShapelessRecipeBuilder shapelessRecipeBuilder1 = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.ASTRAL_NUGGET.get(), 9)
				.group("astral")
				.unlockedBy("astral_nugget", has(IWItemTagGroups.ASTRAL_INGOTS));
		createNuggetFromIngot(shapelessRecipeBuilder1, IWItemTagGroups.ASTRAL_INGOTS);

		// Astral block
		ShapedRecipeBuilder shapedRecipeBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.ASTRAL_BLOCK_ITEM.get())
				.group("astral")
				.unlockedBy("astral_ingot", has(IWItemTagGroups.ASTRAL_INGOTS));
		create3x3Object(shapedRecipeBuilder, IWItemTagGroups.ASTRAL_INGOTS);
	}

	private void createStarstormItems() {
		// Ingot from block
		ShapelessRecipeBuilder shapelessRecipeBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.STARSTORM_INGOT.get(), 9)
				.group("starstorm")
				.unlockedBy("starstorm_block", has(BlockItemRegistry.STARSTORM_BLOCK_ITEM.get()));
		createIngotFromBlock(shapelessRecipeBuilder, BlockItemRegistry.STARSTORM_BLOCK_ITEM.get());

		// Ingot from shards
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.STARSTORM_INGOT.get())
				.define('a', IWItemTagGroups.STARSTORM_SHARDS)
				.define('b', ItemRegistry.OBSIDIAN_SHARD.get())
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("molten")
				.unlockedBy("starstorm_shard", has(IWItemTagGroups.STARSTORM_SHARDS))
				.save(finishedRecipeConsumer);

		// Shards from crushing crystals
		pistonCrushing(BlockRegistry.STARSTORM_CRYSTAL.get(), ItemRegistry.STARSTORM_SHARD.get(), 2, 4);

		// Starstorm block
		ShapedRecipeBuilder shapedRecipeBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.STARSTORM_BLOCK_ITEM.get())
				.group("starstorm")
				.unlockedBy("starstorm_ingot", has(IWItemTagGroups.STARSTORM_INGOTS));
		create3x3Object(shapedRecipeBuilder, IWItemTagGroups.STARSTORM_INGOTS);
	}

	private void createSmithingItems() {
		// Netherite arrow
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.NETHERITE_ARROW.get(), 8)
				.define('a', ItemRegistry.DIAMOND_ARROW.get())
				.define('b', Tags.Items.INGOTS_NETHERITE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("netherite")
				.unlockedBy("netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
				.save(finishedRecipeConsumer);

		// Netherite musket ball
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.NETHERITE_MUSKET_BALL.get(), 8)
				.define('a', ItemRegistry.DIAMOND_MUSKET_BALL.get())
				.define('b', Tags.Items.INGOTS_NETHERITE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("netherite")
				.unlockedBy("netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
				.save(finishedRecipeConsumer);

		netheriteSmithing(ItemRegistry.DIAMOND_GAUNTLET.get(), ItemRegistry.NETHERITE_GAUNTLET.get());
		netheriteSmithing(ItemRegistry.DIAMOND_PIKE.get(), ItemRegistry.NETHERITE_PIKE.get());
	}

	private void createSmallPartsItems() {
		List<Item> IRON_INGOT_CRAFTABLES = new ArrayList<>(5);
		List<Item> GOLD_INGOT_CRAFTABLES = new ArrayList<>(5);
		List<Item> METAL_INGOT_CRAFTABLES = new ArrayList<>(5);
		List<Item> PLANK_CRAFTABLES = new ArrayList<>(5);

		IRON_INGOT_CRAFTABLES.add(ItemRegistry.FLINTLOCK_ASSEMBLY.get());
		IRON_INGOT_CRAFTABLES.add(ItemRegistry.IRON_BARREL.get());
		IRON_INGOT_CRAFTABLES.add(ItemRegistry.SHORT_IRON_BARREL.get());

		GOLD_INGOT_CRAFTABLES.add(ItemRegistry.WIDE_GOLDEN_BARREL.get());
		GOLD_INGOT_CRAFTABLES.add(ItemRegistry.TRIGGER_ASSEMBLY.get());
		GOLD_INGOT_CRAFTABLES.add(ItemRegistry.SCOPE_MOUNT.get());

		METAL_INGOT_CRAFTABLES.add(ItemRegistry.GRENADE_ASSEMBLY.get());
		METAL_INGOT_CRAFTABLES.add(ItemRegistry.TOOL_JOINT.get());

		PLANK_CRAFTABLES.add(ItemRegistry.GAUNTLET_SCAFFOLDING.get());
		PLANK_CRAFTABLES.add(ItemRegistry.HEAVY_WOODEN_STOCK.get());
		PLANK_CRAFTABLES.add(ItemRegistry.WOODEN_PISTOL_HANDLE.get());

		smallPartsTinkering(Tags.Items.INGOTS_IRON, IRON_INGOT_CRAFTABLES);
		smallPartsTinkering(Tags.Items.INGOTS_GOLD, GOLD_INGOT_CRAFTABLES);
		smallPartsTinkering(ForgeItemTagGroups.METAL_INGOTS, METAL_INGOT_CRAFTABLES);
		smallPartsTinkering(ItemTags.PLANKS, PLANK_CRAFTABLES);

		// Small parts table
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SMALL_PARTS_TABLE.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ItemTags.PLANKS)
				.define('c', Tags.Items.INGOTS_COPPER)
				.pattern("aca")
				.pattern("aba")
				.unlockedBy("copper_ingot", has(Tags.Items.INGOTS_COPPER))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.EXTENDED_IRON_BARREL.get())
				.define('a', ItemRegistry.IRON_BARREL.get())
				.pattern("aa")
				.group("small_parts")
				.unlockedBy("iron_barrel", has(ItemRegistry.IRON_BARREL.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SCOPE.get())
				.define('a', Items.SPYGLASS)
				.define('b', ItemRegistry.SCOPE_MOUNT.get())
				.pattern("a")
				.pattern("b")
				.group("small_parts")
				.unlockedBy("spyglass", has(Items.SPYGLASS))
				.save(finishedRecipeConsumer);
	}

	private void createBarrelTapItems() {
		barrelTapFermenting(Items.WHEAT, 12, ItemRegistry.BOTTLE_OF_ALCOHOL.get());
		barrelTapFermenting(Items.SWEET_BERRIES, 12, ItemRegistry.BOTTLE_OF_WINE.get());
	}

	private void createAstralCrystalSorceryItems() {
		astralCrystalSorcery(ItemRegistry.RAW_ASTRAL.get(), Items.AMETHYST_SHARD, ItemRegistry.ASTRAL_INGOT.get(), 1);
	}

	private void createSmokeGrenades() {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SMOKE_GRENADE.get(), 2)
				.define('a', Items.BAMBOO)
				.define('b', ItemRegistry.GRENADE_ASSEMBLY.get())
				.define('c', ItemRegistry.SMOKE_POWDER.get())
				.pattern(" cb")
				.pattern(" a ")
				.pattern(" a ")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(ItemRegistry.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);

		createColoredSmokeGrenade(ItemRegistry.SMOKE_GRENADE_BLUE.get(), Tags.Items.DYES_BLUE);
		createColoredSmokeGrenade(ItemRegistry.SMOKE_GRENADE_GREEN.get(), Tags.Items.DYES_GREEN);
		createColoredSmokeGrenade(ItemRegistry.SMOKE_GRENADE_RED.get(), Tags.Items.DYES_RED);
		createColoredSmokeGrenade(ItemRegistry.SMOKE_GRENADE_PURPLE.get(), Tags.Items.DYES_PURPLE);
		createColoredSmokeGrenade(ItemRegistry.SMOKE_GRENADE_YELLOW.get(), Tags.Items.DYES_YELLOW);

		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SMOKE_GRENADE_ARROW.get(), 4)
				.define('a', Items.ARROW)
				.define('b', ItemRegistry.SMOKE_POWDER.get())
				.pattern("aab")
				.pattern("aa ")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(ItemRegistry.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);

		createColoredSmokeGrenadeArrow(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get(), Tags.Items.DYES_BLUE);
		createColoredSmokeGrenadeArrow(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get(), Tags.Items.DYES_GREEN);
		createColoredSmokeGrenadeArrow(ItemRegistry.SMOKE_GRENADE_ARROW_RED.get(), Tags.Items.DYES_RED);
		createColoredSmokeGrenadeArrow(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get(), Tags.Items.DYES_PURPLE);
		createColoredSmokeGrenadeArrow(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get(), Tags.Items.DYES_YELLOW);

		// Smoke powder
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SMOKE_POWDER.get(), 4)
				.requires(ItemRegistry.MORTAR_AND_PESTLE.get())
				.requires(Items.GUNPOWDER)
				.requires(ItemTags.COALS)
				.requires(ItemTags.LEAVES)
				.group("smoke_grenade")
				.unlockedBy("mortar_and_pestle", has(ItemRegistry.MORTAR_AND_PESTLE.get()))
				.save(finishedRecipeConsumer);
	}

	private void createPanelItems() {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.IRON_PANEL_ITEM.get(), 8)
				.define('a', Tags.Items.STORAGE_BLOCKS_IRON)
				.pattern("aaa")
				.group("iron_panel")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.IRON_PANEL_BARS_ITEM.get(), 8)
				.define('a', Tags.Items.STORAGE_BLOCKS_IRON)
				.define('b', Items.IRON_BARS)
				.pattern("aba")
				.group("iron_panel")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(finishedRecipeConsumer);
	}

	private void createShardItems() {
		// Stone shard
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.STONE_SHARD.get(), 3)
				.unlockedBy("cobblestone", has(Items.COBBLESTONE));
		createShard(builder, Items.COBBLESTONE);

		// Obsidian shard
		builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.OBSIDIAN_SHARD.get(), 9)
				.unlockedBy("obsidian", has(Items.OBSIDIAN));
		createShard(builder, Items.OBSIDIAN);

		// Diamond shard
		builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.DIAMOND_SHARD.get(), 4)
				.unlockedBy("diamond", has(Tags.Items.GEMS_DIAMOND));
		createShard(builder, Items.DIAMOND);
	}

	private void createWarriorStatueItems() {
		// Create warrior statute head
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.WARRIOR_STATUE_HEAD_ITEM.get())
				.define('a', Items.STONE)
				.define('b', Items.CARVED_PUMPKIN)
				.define('c', Items.ENDER_EYE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aca")
				.group("warrior_statue")
				.unlockedBy("ender_eye", has(Items.ENDER_EYE))
				.save(finishedRecipeConsumer);
		// Create warrior statute torso
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.WARRIOR_STATUE_TORSO_ITEM.get())
				.define('a', Items.STONE)
				.define('b', ItemTags.COALS)
				.define('c', Items.ENDER_EYE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aca")
				.group("warrior_statue")
				.unlockedBy("ender_eye", has(Items.ENDER_EYE))
				.save(finishedRecipeConsumer);
		// Create warrior statute base
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.WARRIOR_STATUE_BASE_ITEM.get())
				.define('a', Items.STONE)
				.define('b', Items.ENDER_EYE)
				.pattern("aba")
				.pattern("aaa")
				.pattern("a a")
				.group("warrior_statue")
				.unlockedBy("ender_eye", has(Items.ENDER_EYE))
				.save(finishedRecipeConsumer);
	}

	private void createUtilityItems() {
		// Barbed wire
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BARBED_WIRE_ITEM.get(), 3)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ForgeItemTagGroups.METAL_NUGGETS)
				.pattern("bab")
				.pattern("aba")
				.pattern("bab")
				.group("barbed_wire")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);
		// Barbed wire fence
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BARBED_WIRE_FENCE_ITEM.get(), 6)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', BlockItemRegistry.BARBED_WIRE_ITEM.get())
				.pattern("aba")
				.pattern("aba")
				.group("barbed_wire")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);
		// Bear trap
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BEAR_TRAP_ITEM.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
				.define('c', Tags.Items.DUSTS_REDSTONE)
				.pattern("aba")
				.pattern(" c ")
				.group("traps")
				.unlockedBy("redstone", has(Tags.Items.DUSTS_REDSTONE))
				.save(finishedRecipeConsumer);
		// Landmine
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.LANDMINE_ITEM.get())
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
				.define('c', Items.TNT)
				.pattern(" b ")
				.pattern("aca")
				.group("traps")
				.unlockedBy("gunpowder", has(Items.TNT))
				.save(finishedRecipeConsumer);
		// Mortar and shell
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BlockItemRegistry.MORTAR_ITEM.get())
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Tags.Items.STONE)
				.pattern("b b")
				.pattern("aba")
				.group("artillery")
				.unlockedBy("metal_ingots", has(ForgeItemTagGroups.METAL_INGOTS))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.MORTAR_SHELL.get())
				.define('a', Items.TNT)
				.define('b', Tags.Items.STONE)
				.pattern(" b ")
				.pattern("bab")
				.pattern(" b ")
				.group("artillery")
				.unlockedBy("tnt", has(Items.TNT))
				.save(finishedRecipeConsumer);
		// Panic alarm
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.PANIC_ALARM_ITEM.get())
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Items.NOTE_BLOCK)
				.pattern("aba")
				.group("alarms")
				.unlockedBy("note_block", has(Items.NOTE_BLOCK))
				.save(finishedRecipeConsumer);
		// Sandbag
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.SANDBAG_ITEM.get(), 6)
				.define('a', Tags.Items.SAND)
				.define('b', ItemRegistry.CLOTH_SCRAP.get())
				.pattern("bbb")
				.pattern("aaa")
				.pattern("bbb")
				.group("sandbags")
				.unlockedBy("cloth_scrap", has(ItemRegistry.CLOTH_SCRAP.get()))
				.save(finishedRecipeConsumer);
		// Spike trap
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.SPIKE_TRAP_ITEM.get())
				.define('a', Tags.Items.DUSTS_REDSTONE)
				.define('b', Tags.Items.INGOTS_IRON)
				.define('c', Tags.Items.NUGGETS_IRON)
				.pattern("ccc")
				.pattern("bbb")
				.pattern(" a ")
				.group("traps")
				.unlockedBy("redstone", has(Tags.Items.DUSTS_REDSTONE))
				.save(finishedRecipeConsumer);
		// Pitfall
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.PITFALL_ITEM.get())
				.define('a', Items.STICK)
				.define('b', Items.GRASS)
				.define('c', ItemTags.LEAVES)
				.pattern("bcb")
				.pattern("aaa")
				.group("traps")
				.unlockedBy("grass", has(Items.GRASS))
				.save(finishedRecipeConsumer);
		// Punji sticks
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.PUNJI_STICKS_ITEM.get(), 3)
				.define('a', Items.DIRT)
				.define('b', Items.BAMBOO)
				.define('c', new StrictNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON)) {
				})
				.pattern("bbb")
				.pattern("bcb")
				.pattern("aaa")
				.group("traps")
				.unlockedBy("bamboo", has(Items.BAMBOO))
				.save(finishedRecipeConsumer);
		// Spotlight
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.SPOTLIGHT_ITEM.get())
				.define('a', Items.REDSTONE_LAMP)
				.define('b', Items.BLACK_CONCRETE)
				.define('c', ForgeItemTagGroups.METAL_INGOTS)
				.pattern("bab")
				.pattern("c c")
				.group("lights")
				.unlockedBy("redstone_lamp", has(Items.REDSTONE_LAMP))
				.save(finishedRecipeConsumer);
		// Wooden spikes
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.WOODEN_SPIKES_ITEM.get())
				.define('a', ItemTags.PLANKS)
				.define('b', Items.STICK)
				.pattern("b b")
				.pattern(" a ")
				.pattern("b b")
				.group("traps")
				.unlockedBy("planks", has(ItemTags.PLANKS))
				.save(finishedRecipeConsumer);
		// Barrel tap
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BARREL_TAP_ITEM.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ForgeItemTagGroups.METAL_NUGGETS)
				.pattern("  b")
				.pattern("aaa")
				.pattern("  a")
				.group("barrel_tap")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);
	}

	private void createFirstAidItems() {
		// Bandage
		ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.BANDAGE.get(), 2)
				.define('a', Tags.Items.STRING)
				.define('b', ItemTags.WOOL)
				.pattern("aba")
				.group("first_aid")
				.unlockedBy("wool", has(ItemTags.WOOL))
				.save(finishedRecipeConsumer);
		// First aid kit
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.FIRST_AID_KIT.get())
				.requires(ItemRegistry.BANDAGE.get(), 2)
				.requires(ItemRegistry.PAINKILLERS.get())
				.group("first_aid")
				.unlockedBy("painkillers", has(ItemRegistry.PAINKILLERS.get()))
				.save(finishedRecipeConsumer);
		// Morphine
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.MORPHINE.get())
				.requires(ItemRegistry.SYRINGE.get())
				.requires(Items.FERMENTED_SPIDER_EYE)
				.requires(Items.SUGAR, 3)
				.group("first_aid")
				.unlockedBy("syringe", has(ItemRegistry.SYRINGE.get()))
				.save(finishedRecipeConsumer);
		// Painkillers
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.PAINKILLERS.get())
				.requires(Items.SUGAR)
				.requires(Items.GLISTERING_MELON_SLICE)
				.requires(Items.BEETROOT)
				.group("first_aid")
				.unlockedBy("glistering_melon_slice", has(Items.GLISTERING_MELON_SLICE))
				.save(finishedRecipeConsumer);
		// Syringe
		ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.SYRINGE.get(), 2)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', Tags.Items.NUGGETS_IRON)
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" b ")
				.group("first_aid")
				.unlockedBy("iron_nugget", has(Tags.Items.NUGGETS_IRON))
				.save(finishedRecipeConsumer);
	}

	private void createFoodItems() {
		// Chocolate bar
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.CHOCOLATE_BAR.get(), 8)
				.requires(Items.COCOA_BEANS, 8)
				.requires(Items.MILK_BUCKET)
				.group("food")
				.unlockedBy("cocoa_beans", has(Items.COCOA_BEANS))
				.save(finishedRecipeConsumer);
		// Explosive chocolate bar
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.EXPLOSIVE_CHOCOLATE_BAR.get(), 8)
				.requires(ItemRegistry.CHOCOLATE_BAR.get(), 8)
				.requires(Items.TNT)
				.group("food")
				.unlockedBy("chocolate_bar", has(ItemRegistry.CHOCOLATE_BAR.get()))
				.save(finishedRecipeConsumer);
		// MRE
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.MRE.get())
				.requires(Items.CARROT)
				.requires(Items.POTATO)
				.requires(Items.BEETROOT)
				.requires(Items.COOKED_CHICKEN)
				.group("food")
				.unlockedBy("mre_items",
						new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, Ints.ANY,
								Ints.ANY, Ints.ANY,
								new ItemPredicate[]{Builder.item().of(Items.CARROT).of(Items.POTATO)
										.of(Items.BEETROOT).of(Items.COOKED_CHICKEN).build()}))
				.save(finishedRecipeConsumer);
	}

	private void createWeapons() {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FLINTLOCK_PISTOL.get())
				.define('a', ItemRegistry.WOODEN_PISTOL_HANDLE.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.IRON_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BLUNDERBUSS.get())
				.define('a', ItemRegistry.HEAVY_WOODEN_STOCK.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.WIDE_GOLDEN_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MUSKET.get())
				.define('a', ItemRegistry.HEAVY_WOODEN_STOCK.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.EXTENDED_IRON_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MUSKET_SCOPE.get())
				.define('a', ItemRegistry.HEAVY_WOODEN_STOCK.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.EXTENDED_IRON_BARREL.get())
				.define('e', ItemRegistry.SCOPE.get())
				.pattern(" e ")
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MUSKET_SCOPE.get())
				.define('a', ItemRegistry.SCOPE.get())
				.define('b', ItemRegistry.MUSKET.get())
				.pattern("a")
				.pattern("b")
				.group("firearm")
				.unlockedBy("musket", has(ItemRegistry.MUSKET.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(ItemRegistry.MUSKET_SCOPE.get()) + "_alt");
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FLARE_GUN.get())
				.define('a', ItemRegistry.WOODEN_PISTOL_HANDLE.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.SHORT_IRON_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FLARE.get(), 3)
				.define('a', ForgeItemTagGroups.COPPER_NUGGETS)
				.define('b', Items.PAPER)
				.define('c', ItemRegistry.SMOKE_POWDER.get())
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("smoke_powder", has(ItemRegistry.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.HAND_CANNON.get())
				.define('a', ItemRegistry.WOODEN_PISTOL_HANDLE.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.SHORT_IRON_BARREL.get())
				.define('e', Items.STRING)
				.pattern(" e ")
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CANNONBALL.get(), 2)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', Tags.Items.INGOTS_COPPER)
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("firearm")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.EXPLOSIVE_CANNONBALL.get(), 4)
				.define('a', Items.TNT)
				.define('b', ItemRegistry.CANNONBALL.get())
				.pattern(" b ")
				.pattern("bab")
				.pattern(" b ")
				.group("firearm")
				.unlockedBy("tnt", has(Items.TNT))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MOLOTOV_COCKTAIL.get())
				.define('a', Tags.Items.STRING)
				.define('b', ItemRegistry.BOTTLE_OF_ALCOHOL.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("molotov_cocktail")
				.unlockedBy("bottle_of_alcohol", has(ItemRegistry.BOTTLE_OF_ALCOHOL.get()))
				.save(finishedRecipeConsumer);

		// Musket balls
		List<MaterialGroup> musketBallMaterials = new ArrayList<>(20);

		// Wood
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.WOODEN_SHARDS, 0.08f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.WOODEN_MUSKET_BALL.get());

		// Stone
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.STONE_SHARDS, 0.25f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.STONE_MUSKET_BALL.get());

		// Gold
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(Tags.Items.INGOTS_GOLD, 1.93f, 2.6f));
		musketBallMaterials.add(new MaterialGroup(Tags.Items.NUGGETS_GOLD, 0.21f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.GOLDEN_MUSKET_BALL.get());

		// Copper
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(Tags.Items.INGOTS_COPPER, 0.89f, 2.6f));
		musketBallMaterials.add(new MaterialGroup(ForgeItemTagGroups.COPPER_NUGGETS, 0.1f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.COPPER_MUSKET_BALL.get());

		// Iron
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(Tags.Items.INGOTS_IRON, 0.79f, 2.6f));
		musketBallMaterials.add(new MaterialGroup(Tags.Items.NUGGETS_IRON, 0.09f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.IRON_MUSKET_BALL.get());

		// Cobalt
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(ForgeItemTagGroups.COBALT_INGOTS, 0.89f, 2.6f));
		musketBallMaterials.add(new MaterialGroup(ForgeItemTagGroups.COBALT_NUGGETS, 0.1f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.COBALT_MUSKET_BALL.get());

		// Diamond
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.DIAMOND_SHARDS, 0.09f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.DIAMOND_MUSKET_BALL.get());

		// Netherite
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(Tags.Items.INGOTS_NETHERITE, 2.5f, 2.6f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.NETHERITE_MUSKET_BALL.get());

		// Molten
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.MOLTEN_INGOTS, 0.35f, 2.6f));
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.MOLTEN_SHARDS, 0.04f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.MOLTEN_MUSKET_BALL.get());

		// Tesla
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.TESLA_INGOTS, 2.0f, 2.6f));
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.TESLA_NUGGETS, 0.22f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.TESLA_MUSKET_BALL.get());

		// Ventus
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.VENTUS_SHARDS, 0.05f, 2.6f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.VENTUS_MUSKET_BALL.get());

		// Starstorm
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.STARSTORM_INGOTS, 0.4f, 2.6f));
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.STARSTORM_SHARDS, 0.04f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.STARSTORM_MUSKET_BALL.get());

		// Astral
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.ASTRAL_INGOTS, 0.6f, 2.6f));
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.ASTRAL_NUGGETS, 0.06f, 0.28f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.ASTRAL_MUSKET_BALL.get());

		// Ammunition Table
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BlockItemRegistry.AMMUNITION_TABLE_ITEM.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ItemTags.PLANKS)
				.define('c', ItemTags.STONE_CRAFTING_MATERIALS)
				.define('d', Tags.Items.GUNPOWDER)
				.pattern("aaa")
				.pattern("bdb")
				.pattern("cbc")
				.group("ammunition_table")
				.unlockedBy("gunpowder", has(Tags.Items.GUNPOWDER))
				.save(finishedRecipeConsumer);
	}

	private void createDecorations() {
		// Camp chair
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.CAMP_CHAIR_ITEM.get())
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', ItemTags.WOOL)
				.pattern("b  ")
				.pattern("bbb")
				.pattern("a a")
				.group("chairs")
				.unlockedBy("wool", has(ItemTags.WOOL))
				.save(finishedRecipeConsumer);
		// Celestial lantern
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.CELESTIAL_LANTERN_ITEM.get())
				.define('a', Tags.Items.NUGGETS_IRON)
				.define('b', Tags.Items.GLASS_PANES)
				.define('c', ItemRegistry.CELESTIAL_FRAGMENT.get())
				.pattern("aba")
				.pattern("bcb")
				.pattern("aba")
				.group("lights")
				.unlockedBy("celestial_fragment", has(ItemRegistry.CELESTIAL_FRAGMENT.get()))
				.save(finishedRecipeConsumer);
		// Wall shelf
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.WALL_SHELF_ITEM.get())
				.define('a', ItemTags.WOODEN_SLABS)
				.define('b', ForgeItemTagGroups.METAL_INGOTS)
				.pattern("bab")
				.pattern("bab")
				.group("shelves")
				.unlockedBy("wooden_slabs", has(ItemTags.WOODEN_SLABS))
				.save(finishedRecipeConsumer);

		// Tables
		createTable(BlockItemRegistry.OAK_TABLE_ITEM.get(), Items.OAK_SLAB, Items.OAK_FENCE);
		createTable(BlockItemRegistry.SPRUCE_TABLE_ITEM.get(), Items.SPRUCE_SLAB, Items.SPRUCE_FENCE);
		createTable(BlockItemRegistry.BIRCH_TABLE_ITEM.get(), Items.BIRCH_SLAB, Items.BIRCH_FENCE);
		createTable(BlockItemRegistry.JUNGLE_TABLE_ITEM.get(), Items.JUNGLE_SLAB, Items.JUNGLE_FENCE);
		createTable(BlockItemRegistry.ACACIA_TABLE_ITEM.get(), Items.ACACIA_SLAB, Items.ACACIA_FENCE);
		createTable(BlockItemRegistry.DARK_OAK_TABLE_ITEM.get(), Items.DARK_OAK_SLAB, Items.DARK_OAK_FENCE);
		createTable(BlockItemRegistry.CRIMSON_TABLE_ITEM.get(), Items.CRIMSON_SLAB, Items.CRIMSON_FENCE);
		createTable(BlockItemRegistry.WARPED_TABLE_ITEM.get(), Items.WARPED_SLAB, Items.WARPED_FENCE);
		createTable(BlockItemRegistry.MANGROVE_TABLE_ITEM.get(), Items.MANGROVE_SLAB, Items.MANGROVE_FENCE);
		createTable(BlockItemRegistry.CHERRY_TABLE_ITEM.get(), Items.CHERRY_SLAB, Items.CHERRY_FENCE);
		createTable(BlockItemRegistry.BAMBOO_TABLE_ITEM.get(), Items.BAMBOO_SLAB, Items.BAMBOO_FENCE);
		createTable(BlockItemRegistry.BURNED_OAK_TABLE_ITEM.get(), BlockItemRegistry.BURNED_OAK_SLAB_ITEM.get(),
				BlockItemRegistry.BURNED_OAK_FENCE_ITEM.get());
		createTable(BlockItemRegistry.STARDUST_TABLE_ITEM.get(), BlockItemRegistry.STARDUST_SLAB_ITEM.get(),
				BlockItemRegistry.STARDUST_FENCE_ITEM.get());
	}

	private void createAccessories() {
		// Satchel
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SATCHEL.get())
				.define('a', Tags.Items.LEATHER)
				.define('b', Tags.Items.STRING)
				.define('c', Tags.Items.CHESTS_WOODEN)
				.pattern(" b ")
				.pattern("aca")
				.pattern(" a ")
				.group("satchel")
				.unlockedBy("leather", has(Tags.Items.LEATHER))
				.save(finishedRecipeConsumer);

		// Powder horn
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.POWDER_HORN.get())
				.define('a', Tags.Items.GUNPOWDER)
				.define('b', Tags.Items.STRING)
				.define('c', Items.GOAT_HORN)
				.pattern(" b ")
				.pattern("aca")
				.pattern(" a ")
				.group("powder_horn")
				.unlockedBy("goat_horn", has(Items.GOAT_HORN))
				.save(finishedRecipeConsumer);

		// Blademaster Emblem
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BLADEMASTER_EMBLEM.get())
				.define('a', IWItemTagGroups.STARSTORM_SHARDS)
				.define('b', new StrictNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.DEATH_POTION.get())) {
				})
				.define('c', ItemRegistry.STARSTORM_SWORD.get())
				.pattern("aaa")
				.pattern("aca")
				.pattern("aba")
				.group("blademaster_emblem")
				.unlockedBy("starstorm_sword", has(ItemRegistry.STARSTORM_SWORD.get()))
				.save(finishedRecipeConsumer);

		// Netherite Shield
		netheriteSmithing(Items.SHIELD, ItemRegistry.NETHERITE_SHIELD.get());

		// Iron Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.IRON_RING.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ItemRegistry.COPPER_RING.get())
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("ring")
				.unlockedBy("copper_ring", has(ItemRegistry.COPPER_RING.get()))
				.save(finishedRecipeConsumer);
		// Cobalt Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.COBALT_RING.get())
				.define('a', ForgeItemTagGroups.COBALT_INGOTS)
				.define('b', ItemRegistry.IRON_RING.get())
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("ring")
				.unlockedBy("iron_ring", has(ItemRegistry.IRON_RING.get()))
				.save(finishedRecipeConsumer);
		// Golden Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLDEN_RING.get())
				.define('a', Tags.Items.INGOTS_GOLD)
				.define('b', ItemRegistry.COBALT_RING.get())
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("ring")
				.unlockedBy("cobalt_ring", has(ItemRegistry.COBALT_RING.get()))
				.save(finishedRecipeConsumer);
		// Diamond Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.DIAMOND_RING.get())
				.define('a', Tags.Items.GEMS_DIAMOND)
				.define('b', ItemRegistry.GOLDEN_RING.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("ring")
				.unlockedBy("golden_ring", has(ItemRegistry.GOLDEN_RING.get()))
				.save(finishedRecipeConsumer);
		// Amethyst Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.AMETHYST_RING.get())
				.define('a', Tags.Items.GEMS_AMETHYST)
				.define('b', ItemRegistry.GOLDEN_RING.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("ring")
				.unlockedBy("amethyst_ring", has(ItemRegistry.GOLDEN_RING.get()))
				.save(finishedRecipeConsumer);
		// Emerald Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.EMERALD_RING.get())
				.define('a', Tags.Items.GEMS_EMERALD)
				.define('b', ItemRegistry.GOLDEN_RING.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("ring")
				.unlockedBy("emerald_ring", has(ItemRegistry.GOLDEN_RING.get()))
				.save(finishedRecipeConsumer);
		// Netherite Ring
		netheriteSmithing(ItemRegistry.DIAMOND_RING.get(), ItemRegistry.NETHERITE_RING.get());

		// Goggles
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOGGLES.get())
				.define('a', Tags.Items.GLASS_PANES)
				.define('b', Tags.Items.INGOTS_IRON)
				.define('c', Tags.Items.STRING)
				.pattern(" c ")
				.pattern("aba")
				.group("goggles")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);

		// Lava Goggles
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.LAVA_GOGGLES.get())
				.define('a', ItemRegistry.GOGGLES.get())
				.define('b', IWItemTagGroups.MOLTEN_INGOTS)
				.define('c', Tags.Items.OBSIDIAN)
				.pattern(" c ")
				.pattern("bab")
				.pattern(" c ")
				.group("goggles")
				.unlockedBy("molten_ingot", has(IWItemTagGroups.MOLTEN_INGOTS))
				.save(finishedRecipeConsumer);

		// Bloody Cloth
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BLOODY_CLOTH.get())
				.define('a', ItemRegistry.CLOTH_SCRAP.get())
				.define('b', Items.REDSTONE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("bloody_cloth")
				.unlockedBy("cloth_scrap", has(ItemRegistry.CLOTH_SCRAP.get()))
				.save(finishedRecipeConsumer);

		// Hand of Doom
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.HAND_OF_DOOM.get())
				.requires(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get())
				.requires(ItemRegistry.IRON_FIST.get())
				.requires(ItemRegistry.GLOVE_OF_RAPID_SWINGING.get())
				.group("hand_of_doom")
				.unlockedBy("melee_glove_accessories", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get())
						.of(ItemRegistry.IRON_FIST.get())
						.of(ItemRegistry.GLOVE_OF_RAPID_SWINGING.get())
						.build()))
				.save(finishedRecipeConsumer);

		// Bloody Sacrifice
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BLOODY_SACRIFICE.get())
				.define('a', Items.SPIDER_EYE)
				.define('b', Items.PORKCHOP)
				.define('c', Items.MUTTON)
				.pattern("bbb")
				.pattern("bac")
				.pattern("ccc")
				.group("bloody_sacrifice")
				.unlockedBy("spider_eye", has(Items.SPIDER_EYE))
				.save(finishedRecipeConsumer);
	}

	private void createMiscellaneousItems() {
		// Azul keystone
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.AZUL_KEYSTONE.get())
				.define('a', Tags.Items.GEMS_DIAMOND)
				.define('b', ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get())
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("azul_keystone")
				.unlockedBy("azul_keystone_fragment", has(ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get()))
				.save(finishedRecipeConsumer);
		// Azul Locator
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.AZUL_LOCATOR.get())
				.define('a', Tags.Items.INGOTS_GOLD)
				.define('b', ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get())
				.pattern("aba")
				.pattern(" a ")
				.group("azul_keystone")
				.unlockedBy("azul_keystone_fragment", has(ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get()))
				.save(finishedRecipeConsumer);
		// Cloth scrap
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CLOTH_SCRAP.get(), 4)
				.define('a', Tags.Items.STRING)
				.define('b', Items.GRASS)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("cloth_scrap")
				.unlockedBy("grass", has(Items.GRASS))
				.save(finishedRecipeConsumer);
		// Conductive alloy
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CONDUCTIVE_ALLOY.get())
				.define('a', Tags.Items.INGOTS_COPPER)
				.define('b', Tags.Items.INGOTS_GOLD)
				.pattern("ab")
				.group("conductive_alloy")
				.unlockedBy("gold", has(Tags.Items.INGOTS_GOLD))
				.save(finishedRecipeConsumer);
		// Mortar and pestle
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MORTAR_AND_PESTLE.get())
				.define('a', Items.BOWL)
				.define('b', Items.STICK)
				.pattern(" b ")
				.pattern("a  ")
				.group("mortar_and_pestle")
				.unlockedBy("bowl", has(Items.BOWL))
				.save(finishedRecipeConsumer);
		// Obsidian rod
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.OBSIDIAN_ROD.get(), 16)
				.define('a', Items.OBSIDIAN)
				.pattern(" a ")
				.pattern(" a ")
				.group("rods")
				.unlockedBy("obsidian", has(Items.OBSIDIAN))
				.save(finishedRecipeConsumer);
		// Pliers
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.PLIERS.get())
				.define('a', Items.LEATHER)
				.define('b', ItemRegistry.TOOL_JOINT.get())
				.define('c', ForgeItemTagGroups.METAL_INGOTS)
				.pattern(" c ")
				.pattern(" b ")
				.pattern("a a")
				.group("pliers")
				.unlockedBy("small_parts_metal_tool", has(ItemRegistry.TOOL_JOINT.get()))
				.save(finishedRecipeConsumer);
		// Wooden tool rod
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.WOODEN_TOOL_ROD.get())
				.define('a', ItemTags.PLANKS)
				.pattern(" a ")
				.pattern("a  ")
				.group("rods")
				.unlockedBy("planks", has(ItemTags.PLANKS))
				.save(finishedRecipeConsumer);

		// Sulfur stuff
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.RAW_SULFUR_BLOCK_ITEM.get())
				.group("sulfur")
				.unlockedBy("sulfur", has(ItemRegistry.SULFUR.get()));
		create3x3Object(builder, ItemRegistry.SULFUR.get());

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SULFUR.get(), 9)
				.requires(BlockItemRegistry.RAW_SULFUR_BLOCK_ITEM.get())
				.group("sulfur")
				.unlockedBy("raw_sulfur_block", has(BlockItemRegistry.RAW_SULFUR_BLOCK_ITEM.get()))
				.save(finishedRecipeConsumer);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SULFUR_DUST.get(), 4)
				.requires(ItemRegistry.SULFUR.get())
				.requires(ItemRegistry.MORTAR_AND_PESTLE.get())
				.unlockedBy("sulfur", has(ItemRegistry.SULFUR.get()))
				.save(finishedRecipeConsumer);

		// Black Powder
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BLACKPOWDER.get(), 8)
				.requires(ForgeItemTagGroups.SULFUR_DUSTS)
				.requires(ItemTags.COALS)
				.requires(Ingredient.of(ItemRegistry.POTASSIUM_NITRATE.get()), 6)
				.requires(ItemRegistry.MORTAR_AND_PESTLE.get())
				.unlockedBy("potassium_nitrate", has(ItemRegistry.POTASSIUM_NITRATE.get()))
				.save(finishedRecipeConsumer);

		// Meteor Staff
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.METEOR_STAFF.get())
				.define('a', ItemRegistry.CELESTIAL_FRAGMENT.get())
				.define('b', ItemRegistry.OBSIDIAN_ROD.get())
				.define('c', Items.OBSIDIAN)
				.pattern("aca")
				.pattern(" b ")
				.pattern(" b ")
				.group("meteor_staff")
				.unlockedBy("celestial_fragment", has(ItemRegistry.CELESTIAL_FRAGMENT.get()))
				.save(finishedRecipeConsumer);

		// Cursed Sight Staff Core
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CURSED_SIGHT_STAFF_CORE.get())
				.define('a', ItemRegistry.CELESTIAL_FRAGMENT.get())
				.define('b', ItemRegistry.BROKEN_LENS.get())
				.define('c', Items.REDSTONE)
				.pattern("aba")
				.pattern("bcb")
				.pattern("aba")
				.group("cursed_sight_staff")
				.unlockedBy("broken_lens", has(ItemRegistry.BROKEN_LENS.get()))
				.save(finishedRecipeConsumer);

		// Cursed Sight Staff
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CURSED_SIGHT_STAFF.get())
				.define('a', ItemRegistry.CURSED_SIGHT_STAFF_CORE.get())
				.define('b', ItemRegistry.OBSIDIAN_ROD.get())
				.pattern("a")
				.pattern("b")
				.pattern("b")
				.group("cursed_sight_staff")
				.unlockedBy("cursed_sight_staff_core", has(ItemRegistry.CURSED_SIGHT_STAFF_CORE.get()))
				.save(finishedRecipeConsumer);

		// Padded Leather Armor
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PADDED_LEATHER_HELMET.get())
				.define('a', Items.LEATHER_HELMET)
				.define('b', ItemTags.WOOL)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("padded_leather_armor")
				.unlockedBy("leather_helmet", has(Items.LEATHER_HELMET))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PADDED_LEATHER_CHESTPLATE.get())
				.define('a', Items.LEATHER_CHESTPLATE)
				.define('b', ItemTags.WOOL)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("padded_leather_armor")
				.unlockedBy("leather_chestplate", has(Items.LEATHER_CHESTPLATE))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PADDED_LEATHER_LEGGINGS.get())
				.define('a', Items.LEATHER_LEGGINGS)
				.define('b', ItemTags.WOOL)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("padded_leather_armor")
				.unlockedBy("leather_leggings", has(Items.LEATHER_LEGGINGS))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PADDED_LEATHER_BOOTS.get())
				.define('a', Items.LEATHER_BOOTS)
				.define('b', ItemTags.WOOL)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("padded_leather_armor")
				.unlockedBy("leather_boots", has(Items.LEATHER_BOOTS))
				.save(finishedRecipeConsumer);
	}

	private void createMinecraftItems() {
		// Gunpowder
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 4)
				.requires(Ingredient.of(ItemTags.COALS), 2)
				.requires(Ingredient.of(ForgeItemTagGroups.SULFUR_DUSTS), 2)
				.requires(ItemRegistry.POTASSIUM_NITRATE.get(), 4)
				.requires(ItemRegistry.MORTAR_AND_PESTLE.get())
				.group("gunpowder")
				.unlockedBy("potassium_nitrate", has(ItemRegistry.POTASSIUM_NITRATE.get()))
				.save(finishedRecipeConsumer);
		// Copper ingot
		ShapedRecipeBuilder shapedRecipeBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COPPER_INGOT)
				.group("copper_ingot")
				.unlockedBy("copper_nugget", has(ForgeItemTagGroups.COPPER_NUGGETS));
		create3x3Object(shapedRecipeBuilder, ForgeItemTagGroups.COPPER_NUGGETS);

		// Replace some recipes that are hardcoded to use certain ingots to use a tag for any "metal" ingot

		// Blast furnace
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BLAST_FURNACE)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Blocks.FURNACE)
				.define('c', Items.SMOOTH_STONE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("ccc")
				.unlockedBy("furnace", has(Blocks.FURNACE))
				.save(finishedRecipeConsumer);

		// Bucket
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUCKET)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.pattern("a a")
				.pattern(" a ")
				.unlockedBy("metal_ingot", has(ForgeItemTagGroups.METAL_INGOTS))
				.save(finishedRecipeConsumer);

		// Cauldron
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.CAULDRON)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.pattern("a a")
				.pattern("a a")
				.pattern("aaa")
				.unlockedBy("metal_ingot", has(ForgeItemTagGroups.METAL_INGOTS))
				.save(finishedRecipeConsumer);

		// Chain
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CHAIN)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', ForgeItemTagGroups.METAL_NUGGETS)
				.pattern(" b ")
				.pattern(" a ")
				.pattern(" b ")
				.unlockedBy("metal_ingot", has(ForgeItemTagGroups.METAL_INGOTS))
				.save(finishedRecipeConsumer);

		// Hopper
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.HOPPER)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Items.CHEST)
				.pattern("a a")
				.pattern("aba")
				.pattern(" a ")
				.unlockedBy("chest", has(Items.CHEST))
				.save(finishedRecipeConsumer);

		// Lantern
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.LANTERN)
				.define('a', ForgeItemTagGroups.METAL_NUGGETS)
				.define('b', Items.TORCH)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.unlockedBy("torch", has(Items.TORCH))
				.save(finishedRecipeConsumer);

		// Minecart
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.MINECART)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.pattern("a a")
				.pattern("aaa")
				.unlockedBy("metal_ingot", has(ForgeItemTagGroups.METAL_INGOTS))
				.save(finishedRecipeConsumer);

		// Piston
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.PISTON)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', ItemTags.PLANKS)
				.define('c', Items.REDSTONE)
				.define('d', Items.COBBLESTONE)
				.pattern("bbb")
				.pattern("dad")
				.pattern("dcd")
				.unlockedBy("redstone", has(Items.REDSTONE))
				.save(finishedRecipeConsumer);

		// Soul Lantern
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SOUL_LANTERN)
				.define('a', ForgeItemTagGroups.METAL_NUGGETS)
				.define('b', Items.SOUL_TORCH)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.unlockedBy("soul_torch", has(Items.SOUL_TORCH))
				.save(finishedRecipeConsumer);

		// Stonecutter
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.STONECUTTER)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Blocks.STONE)
				.pattern(" a ")
				.pattern("bbb")
				.unlockedBy("stone", has(Blocks.STONE))
				.save(finishedRecipeConsumer);

		// Tripwire Hook
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.TRIPWIRE_HOOK)
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Items.STICK)
				.define('c', ItemTags.PLANKS)
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" c ")
				.unlockedBy("string", has(Items.STRING))
				.save(finishedRecipeConsumer);
	}

	public static void createArrow(ArrowItem arrow, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, arrow, 4)
				.pattern("a")
				.pattern("b")
				.pattern("c")
				.define('a', material)
				.define('b', Items.STICK)
				.define('c', Items.FEATHER)
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer, itemRegistryPath(arrow));
	}

	public static void createPikeHead(Item pikeHead, TagKey<Item> material, TagKey<Item> nugget) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, pikeHead)
				.pattern("a")
				.pattern("b")
				.define('a', nugget)
				.define('b', material)
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer, itemRegistryPath(pikeHead));
	}

	public static void createPike(Item pike, TagKey<Item> material, Item pikeHead) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, pike)
				.pattern("a")
				.pattern("b")
				.pattern("c")
				.define('a', pikeHead)
				.define('b', material)
				.define('c', ItemRegistry.WOODEN_TOOL_ROD.get())
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer, itemRegistryPath(pike));
	}

	public static void createGauntlet(Item gauntlet, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, gauntlet)
				.pattern("aaa")
				.pattern("aba")
				.define('a', material)
				.define('b', ItemRegistry.GAUNTLET_SCAFFOLDING.get())
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer, itemRegistryPath(gauntlet));
	}

	public static void createMusketBall(Item musketBall, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, musketBall, 8)
				.define('a', material)
				.define('b', Items.GUNPOWDER)
				.pattern(" a ")
				.pattern("aba")
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer);
	}

	private static void createCobaltIngot(ItemLike ingotItem, ItemLike ingotBlock) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingotItem)
				.group("cobalt")
				.unlockedBy("cobalt_ore", has(ForgeItemTagGroups.COBALT_ORES));
		create3x3Object(builder, ForgeItemTagGroups.COBALT_NUGGETS);
		ShapelessRecipeBuilder builder1 = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingotItem, 9)
				.group("cobalt")
				.unlockedBy("cobalt_block", has(BlockRegistry.COBALT_BLOCK.get()));
		createIngotFromBlock(builder1, ingotBlock);
		createSmeltingRecipe(COBALT_SMELTABLES, ItemRegistry.COBALT_INGOT.get(),
				0.8f, 200, "cobalt");
		createBlastingRecipe(COBALT_SMELTABLES, ItemRegistry.COBALT_INGOT.get(),
				0.8f, 100, "cobalt");
	}

	private static void createMoltenIngot(ItemLike ingotItem, ItemLike ingotBlock) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingotItem)
				.define('a', IWItemTagGroups.MOLTEN_SHARDS)
				.define('b', ItemRegistry.OBSIDIAN_SHARD.get())
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("molten")
				.unlockedBy("molten_shard", has(IWItemTagGroups.MOLTEN_SHARDS))
				.save(finishedRecipeConsumer);
		ShapelessRecipeBuilder builder1 = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingotItem, 9)
				.group("molten")
				.unlockedBy("molten_shard", has(IWItemTagGroups.MOLTEN_SHARDS));
		createIngotFromBlock(builder1, ingotBlock);
		createSmeltingRecipe(BlockItemRegistry.MOLTEN_ORE_ITEM.get(), ItemRegistry.MOLTEN_INGOT.get(),
				1.2f, 1200, "molten");
		createBlastingRecipe(BlockItemRegistry.MOLTEN_ORE_ITEM.get(), ItemRegistry.MOLTEN_INGOT.get(),
				1.2f, 600, "molten");
	}

	private static void createTeslaIngot(ItemLike ingotItem, ItemLike ingotBlock) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingotItem)
				.define('a', IWItemTagGroups.ELECTRIC_INGOTS)
				.define('b', ItemRegistry.CONDUCTIVE_ALLOY.get())
				.pattern("ab ")
				.group("tesla")
				.unlockedBy("electric_ingot", has(IWItemTagGroups.ELECTRIC_INGOTS))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingotItem)
				.define('a', IWItemTagGroups.TESLA_NUGGETS)
				.pattern("aaa")
				.pattern("aaa")
				.pattern("aaa")
				.group("tesla")
				.unlockedBy("tesla_ingot", has(IWItemTagGroups.TESLA_INGOTS))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + ingotItem + "_from_nuggets");

		ShapelessRecipeBuilder shapelessRecipeBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingotItem, 9)
				.group("tesla")
				.unlockedBy("electric_ingot", has(IWItemTagGroups.ELECTRIC_INGOTS));
		createIngotFromBlock(shapelessRecipeBuilder, ingotBlock);

		createSmeltingRecipe(BlockItemRegistry.ELECTRIC_ORE_ITEM.get(), ItemRegistry.ELECTRIC_INGOT.get(),
				1.3f, 200, "tesla");
		createBlastingRecipe(BlockItemRegistry.ELECTRIC_ORE_ITEM.get(), ItemRegistry.ELECTRIC_INGOT.get(),
				1.3f, 100, "tesla");
	}

	private static void createBulletproofStainedGlass(ItemLike stainedGlassItem, TagKey<Item> colorTag) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stainedGlassItem, 8)
				.define('a', BlockRegistry.BULLETPROOF_GLASS.get())
				.define('b', colorTag)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("stained_bulletproof_glass")
				.unlockedBy("bulletproof_glass", has(BlockRegistry.BULLETPROOF_GLASS.get()))
				.save(finishedRecipeConsumer);
	}

	private static void create3x3Object(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.pattern("aaa")
				.pattern("aaa")
				.pattern("aaa")
				.save(finishedRecipeConsumer);
	}

	private static void create3x3Object(ShapedRecipeBuilder builder, ItemLike material) {
		builder.define('a', material)
				.pattern("aaa")
				.pattern("aaa")
				.pattern("aaa")
				.save(finishedRecipeConsumer);
	}

	private static void createIngotFromBlock(ShapelessRecipeBuilder builder, ItemLike ingotBlock) {
		builder.requires(ingotBlock)
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(builder.getResult()) + "_from_" + getItemName(ingotBlock));
	}

	private static void createNuggetFromIngot(ShapelessRecipeBuilder builder, TagKey<Item> tagKey) {
		builder.requires(tagKey)
				.save(finishedRecipeConsumer);
	}

	private static void createColoredSmokeGrenade(ItemLike smokeGrenadeItem, TagKey<Item> colorTag) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, smokeGrenadeItem, 2)
				.define('a', Items.BAMBOO)
				.define('b', ItemRegistry.GRENADE_ASSEMBLY.get())
				.define('c', ItemRegistry.SMOKE_POWDER.get())
				.define('d', colorTag)
				.pattern("dcb")
				.pattern(" a ")
				.pattern(" a ")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(ItemRegistry.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createColoredSmokeGrenadeArrow(ItemLike arrowItem, TagKey<Item> colorTag) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, arrowItem, 4)
				.define('a', Items.ARROW)
				.define('b', ItemRegistry.SMOKE_POWDER.get())
				.define('c', colorTag)
				.pattern("aab")
				.pattern("aac")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(ItemRegistry.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createTable(ItemLike tableItem, ItemLike slabItem, ItemLike fenceItem) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tableItem)
				.define('a', slabItem)
				.define('b', fenceItem)
				.pattern("a")
				.pattern("b")
				.group("tables")
				.unlockedBy("planks", has(ItemTags.PLANKS))
				.save(finishedRecipeConsumer);
	}

	private static void createShard(ShapelessRecipeBuilder builder, ItemLike material) {
		builder.requires(material)
				.group("shard")
				.save(finishedRecipeConsumer);
	}

	public static void createSmeltingRecipe(List<ItemLike> pIngredients,
	                                        ItemLike pResult, float pExperience, int pCookingTime, @Nullable String pGroup) {
		oreCooking((SimpleCookingSerializer<?>) RecipeSerializer.SMELTING_RECIPE, pIngredients, pResult, pExperience,
				pCookingTime, pGroup, "_from_smelting");
	}

	private static void createSmeltingRecipe(ItemLike ingredient,
	                                         ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking((SimpleCookingSerializer<?>) RecipeSerializer.SMELTING_RECIPE, ingredient, pResult, pExperience,
				pCookingTime, pGroup, "_from_smelting");
	}

	private static void createBlastingRecipe(List<ItemLike> pIngredients,
	                                         ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking((SimpleCookingSerializer<?>) RecipeSerializer.BLASTING_RECIPE, pIngredients, pResult, pExperience,
				pCookingTime, pGroup, "_from_blasting");
	}

	private static void createBlastingRecipe(ItemLike ingredient,
	                                         ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking((SimpleCookingSerializer<?>) RecipeSerializer.BLASTING_RECIPE, ingredient, pResult, pExperience,
				pCookingTime, pGroup, "_from_blasting");
	}

	private static void oreCooking(SimpleCookingSerializer<?> pCookingSerializer, List<ItemLike> pIngredients,
	                               ItemLike pResult, float pExperience, int pCookingTime, @Nullable String pGroup, String pRecipeName) {
		for (ItemLike itemlike : pIngredients) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), RecipeCategory.MISC, pResult, pExperience, pCookingTime, pCookingSerializer)
					.group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
		}

	}

	private static void oreCooking(SimpleCookingSerializer<?> pCookingSerializer, ItemLike ingredient,
	                               ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
		SimpleCookingRecipeBuilder.generic(Ingredient.of(ingredient), RecipeCategory.MISC, pResult, pExperience, pCookingTime, pCookingSerializer)
				.group(pGroup).unlockedBy(getHasName(ingredient), has(ingredient))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(ingredient));
	}

	private static void netheriteSmithing(Item baseItem, Item pResultItem) {
		SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
						Ingredient.of(baseItem),
						Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.MISC, pResultItem)
				.unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(pResultItem) + "_smithing");
	}

	private static void teslaSynthesizing(ItemLike block, ItemLike material1, ItemLike material2, int cookTime,
	                                      ItemLike result) {
		TeslaSynthesizerRecipeBuilder.synthesizing(Ingredient.of(block), Ingredient.of(material1), Ingredient.of(material2),
						cookTime, result.asItem())
				.unlocks("tesla_ingot", has(IWItemTagGroups.TESLA_INGOTS))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_tesla_synthesizing");
	}

	private static void smallPartsTinkering(TagKey<Item> material, List<Item> craftables) {
		SmallPartsRecipeBuilder.tinker(Ingredient.of(material), craftables)
				.unlocks("copper_ingot", has(Tags.Items.INGOTS_COPPER))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getTagName(material) + "_tinkering");
	}

	private static void barrelTapFermenting(ItemLike material, int materialCount, ItemLike result) {
		BarrelTapRecipeBuilder.fermenting(Ingredient.of(material), materialCount, result.asItem())
				.unlocks("barrel_tap", has(BlockItemRegistry.BARREL_TAP_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_fermenting");
	}

	private static void astralCrystalSorcery(ItemLike primaryMaterial, ItemLike secondaryMaterial, ItemLike result, int resultCount) {
		AstralCrystalRecipeBuilder.sorcery(Ingredient.of(primaryMaterial), Ingredient.of(secondaryMaterial), result.asItem(), resultCount)
				.unlocks("astral_crystal", has(BlockItemRegistry.ASTRAL_CRYSTAL_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_astral_crystal_sorcery");
	}

	private static void pistonCrushing(Block block, ItemLike result, int minCount, int maxCount) {
		PistonCrushingRecipeBuilder.crushing(block, result.asItem(), minCount, maxCount)
				.unlocks("piston", has(Blocks.PISTON))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_piston_crushing");
	}

	private static void ammunitionTableCrafting(List<MaterialGroup> materials, ItemLike result) {
		AmmunitionTableRecipeBuilder.crafting(materials, result.asItem())
				.unlocks("ammunition_table", has(BlockRegistry.AMMUNITION_TABLE.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_ammunition_table_crafting");
	}

	protected static String getConversionRecipeName(ItemLike pResult, ItemLike pIngredient) {
		return getItemName(pResult) + "_from_" + getItemName(pIngredient);
	}

	protected static String getItemName(ItemLike pItemLike) {
		return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(pItemLike.asItem())).getPath();
	}

	protected static String getTagName(TagKey<Item> tagKey) {
		return tagKey.location().getPath().replace('/', '_');
	}

	protected static String getHasName(ItemLike pItemLike) {
		return "has_" + getItemName(pItemLike);
	}

	public static class CraftingTableRecipes {

		public static void bricks(Block bricks, ItemLike material) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bricks, 4)
					.define('a', material)
					.pattern("aa ")
					.pattern("aa ")
					.unlockedBy("has_" + blockRegistryPath(bricks).getPath(), has(material))
					.save(finishedRecipeConsumer, blockRegistryPath(bricks));
		}

		public static void slab(Block slab, ItemLike material, String group, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
					.pattern("aaa")
					.define('a', material)
					.group(group)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(slab));
		}

		public static void slab(Block slab, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(slab));
		}

		public static void stairs(Block stairs, ItemLike material, String group, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
					.pattern("a  ")
					.pattern("aa ")
					.pattern("aaa")
					.define('a', material)
					.group(group)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(stairs));
		}

		public static void stairs(Block stairs, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
					.pattern("a  ")
					.pattern("aa ")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(stairs));
		}

		public static void wall(Block wall, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
					.pattern("aaa")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(wall));
		}

		public static void pillar(Block pillar, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pillar, 4)
					.pattern("a")
					.pattern("a")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(pillar));
		}

		public static void chiseled(Block chiseled, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chiseled)
					.pattern("a")
					.pattern("a")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(chiseled));
		}

		public static void cut(Block cut, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cut, 4)
					.pattern("aa")
					.pattern("aa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(cut));
		}
	}

	public static class StonecutterRecipes {

		public static void bricks(Block bricks, ItemLike material) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, bricks)
					.unlockedBy("has_" + blockRegistryPath(bricks).getPath(), has(material))
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(bricks, material) + "_stonecutting");
		}

		public static void slab(Block slab, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, slab, 2)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(slab, material) + "_stonecutting");
		}

		public static void stairs(Block stairs, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, stairs)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(stairs, material) + "_stonecutting");
		}

		public static void wall(Block wall, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, wall)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(wall, material) + "_stonecutting");
		}

		public static void pillar(Block pillar, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, pillar)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(pillar, material) + "_stonecutting");
		}

		public static void chiseled(Block chiseled, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, chiseled)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(chiseled, material) + "_stonecutting");
		}

		public static void cut(Block cut, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, cut)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(cut, material) + "_stonecutting");
		}
	}
}