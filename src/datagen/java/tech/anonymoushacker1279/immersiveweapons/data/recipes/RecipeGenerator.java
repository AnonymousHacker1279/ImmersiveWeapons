package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.DataGenUtils;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.PotionRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe.MaterialGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider implements DataGenUtils {

	static RecipeOutput output;
	final PackOutput packOutput;

	private static final ImmutableList<ItemLike> COBALT_SMELTABLES = ImmutableList.of(
			BlockItemRegistry.COBALT_ORE_ITEM.get(), BlockItemRegistry.DEEPSLATE_COBALT_ORE_ITEM.get(),
			ItemRegistry.RAW_COBALT.get());

	public RecipeGenerator(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider);
		packOutput = output;
	}

	@Override
	protected void buildRecipes(RecipeOutput output) {
		RecipeGenerator.output = output;

		createFlagItems();
		createGlassItems();
		createCobaltItems();
		createCopperItems();
		createMoltenItems();
		createVentusItems();
		createTeslaItems();
		createAstralItems();
		createStarstormItems();
		createVoidItems();
		createSmithingItems();
		createSmallPartsItems();
		createBarrelTapItems();
		createAstralCrystalSorceryItems();
		createGrenades();
		createPanelItems();
		createShardItems();
		createUtilityItems();
		createFirstAidItems();
		createFoodItems();
		createWeapons();
		createMudItems();
		createDecorations();
		createAccessories();
		createMiscellaneousItems();
		createStarForgeItems();
		createMinecraftItems();
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
				.save(output);

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
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.CANADIAN_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.RED_WOOL)
				.define('c', Items.WHITE_WOOL)
				.pattern("bcb")
				.pattern("bbb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(output);

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
				.save(output);

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
				.save(output);


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.TROLL_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.WHITE_WOOL)
				.define('c', Items.BLACK_WOOL)
				.pattern("bcb")
				.pattern("cbc")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(output);


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.IMMERSIVE_WEAPONS_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.BLACK_WOOL)
				.define('c', Items.GOLDEN_SWORD)
				.define('d', Items.BLUE_WOOL)
				.pattern("bcb")
				.pattern("bdb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(output);


		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FLAG_POLE, 3)
				.define('a', Tags.Items.INGOTS_IRON)
				.pattern(" a ")
				.pattern(" a ")
				.pattern(" a ")
				.group("flag")
				.unlockedBy("iron", has(Tags.Items.INGOTS_IRON))
				.save(output);
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
				.save(output);
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
				.save(output);
		// Mud
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, MUD, 8)
				.requires(Items.WATER_BUCKET)
				.requires(Items.DIRT, 8)
				.group("mud")
				.unlockedBy("water_bucket", has(Items.WATER_BUCKET))
				.save(output);
		// Mud ball
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MUD_BALL.get(), 4)
				.requires(BlockRegistry.MUD.get())
				.group("mud")
				.unlockedBy("water_bucket", has(Items.WATER_BUCKET))
				.save(output);
	}

	private void createCobaltItems() {
		createCobaltIngot(ItemRegistry.COBALT_INGOT.get(), BlockItemRegistry.COBALT_BLOCK_ITEM.get());

		ShapedRecipeBuilder shapedBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.COBALT_BLOCK_ITEM.get())
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(CommonItemTagGroups.COBALT_INGOTS));
		create3x3Object(shapedBuilder, CommonItemTagGroups.COBALT_INGOTS);

		ShapelessRecipeBuilder shapelessBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.COBALT_NUGGET.get(), 9)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(CommonItemTagGroups.COBALT_INGOTS));
		createNuggetFromIngot(shapelessBuilder, CommonItemTagGroups.COBALT_INGOTS);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.RAW_COBALT.get(), 9)
				.requires(BlockItemRegistry.RAW_COBALT_BLOCK_ITEM.get())
				.group("cobalt")
				.unlockedBy("raw_cobalt_block", has(BlockItemRegistry.RAW_COBALT_BLOCK_ITEM.get()))
				.save(output);

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
				.define('b', Tags.Items.OBSIDIANS)
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("molten")
				.unlockedBy("molten_ingot", has(IWItemTagGroups.MOLTEN_INGOTS))
				.save(output);
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
				.save(output);

		// Ventus staff
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.VENTUS_STAFF.get())
				.define('a', VENTUS_STAFF_CORE)
				.define('b', ItemRegistry.OBSIDIAN_ROD.get())
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" b ")
				.group("ventus")
				.unlockedBy("ventus_staff_core", has(VENTUS_STAFF_CORE))
				.save(output);
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
				.save(output);

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
				.save(output, DataGenUtils.getItemFromNuggetsLocation(ItemRegistry.ASTRAL_INGOT.get()));

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

		// Arrow
		createArrow(ItemRegistry.ASTRAL_ARROW.get(), IWItemTagGroups.ASTRAL_INGOTS);
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
				.save(output);

		// Shards from crushing crystals
		pistonCrushing(BlockRegistry.STARSTORM_CRYSTAL.get(), ItemRegistry.STARSTORM_SHARD.get(), 2, 4);

		// Starstorm block
		ShapedRecipeBuilder shapedRecipeBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.STARSTORM_BLOCK_ITEM.get())
				.group("starstorm")
				.unlockedBy("starstorm_ingot", has(IWItemTagGroups.STARSTORM_INGOTS));
		create3x3Object(shapedRecipeBuilder, IWItemTagGroups.STARSTORM_INGOTS);

		// Arrow
		createArrow(ItemRegistry.STARSTORM_ARROW.get(), IWItemTagGroups.STARSTORM_SHARDS);
	}

	private void createVoidItems() {
		createVoidUpgrade(ItemRegistry.STARSTORM_SWORD.get(), ItemRegistry.ASTRAL_SWORD.get(), ItemRegistry.VOID_SWORD.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_PICKAXE.get(), ItemRegistry.ASTRAL_PICKAXE.get(), ItemRegistry.VOID_PICKAXE.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_AXE.get(), ItemRegistry.ASTRAL_AXE.get(), ItemRegistry.VOID_AXE.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_SHOVEL.get(), ItemRegistry.ASTRAL_SHOVEL.get(), ItemRegistry.VOID_SHOVEL.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_HOE.get(), ItemRegistry.ASTRAL_HOE.get(), ItemRegistry.VOID_HOE.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_PIKE.get(), ItemRegistry.ASTRAL_PIKE.get(), ItemRegistry.VOID_PIKE.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_GAUNTLET.get(), ItemRegistry.ASTRAL_GAUNTLET.get(), ItemRegistry.VOID_GAUNTLET.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_HELMET.get(), ItemRegistry.ASTRAL_HELMET.get(), ItemRegistry.VOID_HELMET.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_CHESTPLATE.get(), ItemRegistry.ASTRAL_CHESTPLATE.get(), ItemRegistry.VOID_CHESTPLATE.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_LEGGINGS.get(), ItemRegistry.ASTRAL_LEGGINGS.get(), ItemRegistry.VOID_LEGGINGS.get());
		createVoidUpgrade(ItemRegistry.STARSTORM_BOOTS.get(), ItemRegistry.ASTRAL_BOOTS.get(), ItemRegistry.VOID_BOOTS.get());

		// Arrow
		createArrow(ItemRegistry.VOID_ARROW.get(), IWItemTagGroups.VOID_INGOTS);

		// Dragon Fireball
		ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.DRAGON_FIREBALL.get(), 3)
				.requires(Items.FIRE_CHARGE, 3)
				.requires(Items.DRAGON_BREATH)
				.group("void")
				.unlockedBy("dragon_breath", has(Items.DRAGON_BREATH))
				.save(output);

		// Dragon's Breath Cannon
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.DRAGONS_BREATH_CANNON.get())
				.define('a', ItemRegistry.HAND_CANNON.get())
				.define('b', IWItemTagGroups.VOID_INGOTS)
				.define('c', Items.DRAGON_BREATH)
				.pattern("cbc")
				.pattern("bab")
				.pattern("cbc")
				.group("void")
				.unlockedBy("void_ingot", has(IWItemTagGroups.VOID_INGOTS))
				.save(output);

		// Teleporter
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.TELEPORTER_ITEM.get())
				.define('a', IWItemTagGroups.VOID_INGOTS)
				.define('b', IWItemTagGroups.TESLA_INGOTS)
				.define('c', Items.PURPUR_SLAB)
				.define('d', Items.ENDER_EYE)
				.define('e', Items.DRAGON_BREATH)
				.pattern("ede")
				.pattern("aaa")
				.pattern("cbc")
				.group("void")
				.unlockedBy("void_ingot", has(IWItemTagGroups.VOID_INGOTS))
				.save(output);

		// Void Blessing
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.VOID_BLESSING.get())
				.define('a', ItemRegistry.HANS_BLESSING.get())
				.define('b', IWItemTagGroups.VOID_INGOTS)
				.define('c', ItemRegistry.ENDER_ESSENCE.get())
				.pattern("cbc")
				.pattern("bab")
				.pattern("cbc")
				.group("void")
				.unlockedBy("void_ingot", has(IWItemTagGroups.VOID_INGOTS))
				.save(output);
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
				.save(output);

		// Netherite musket ball
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.NETHERITE_MUSKET_BALL.get(), 8)
				.define('a', ItemRegistry.DIAMOND_MUSKET_BALL.get())
				.define('b', Tags.Items.INGOTS_NETHERITE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("netherite")
				.unlockedBy("netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
				.save(output);

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
		smallPartsTinkering(CommonItemTagGroups.METAL_INGOTS, METAL_INGOT_CRAFTABLES);
		smallPartsTinkering(ItemTags.PLANKS, PLANK_CRAFTABLES);

		// Small parts table
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SMALL_PARTS_TABLE.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ItemTags.PLANKS)
				.define('c', Tags.Items.INGOTS_COPPER)
				.pattern("aca")
				.pattern("aba")
				.unlockedBy("copper_ingot", has(Tags.Items.INGOTS_COPPER))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.EXTENDED_IRON_BARREL.get())
				.define('a', ItemRegistry.IRON_BARREL.get())
				.pattern("aa")
				.group("small_parts")
				.unlockedBy("iron_barrel", has(ItemRegistry.IRON_BARREL.get()))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SCOPE.get())
				.define('a', Items.SPYGLASS)
				.define('b', ItemRegistry.SCOPE_MOUNT.get())
				.pattern("a")
				.pattern("b")
				.group("small_parts")
				.unlockedBy("spyglass", has(Items.SPYGLASS))
				.save(output);
	}

	private void createBarrelTapItems() {
		barrelTapFermenting(Items.WHEAT, 12, ItemRegistry.BOTTLE_OF_ALCOHOL.get());
		barrelTapFermenting(Items.SWEET_BERRIES, 12, ItemRegistry.BOTTLE_OF_WINE.get());
		barrelTapFermenting(Items.GLOW_BERRIES, 8, ItemRegistry.BOTTLE_OF_WINE.get());
	}

	private void createAstralCrystalSorceryItems() {
		ItemStack astralIngot = new ItemStack(ItemRegistry.ASTRAL_INGOT.get());
		astralCrystalSorcery(ItemRegistry.RAW_ASTRAL.get(), Items.AMETHYST_SHARD, astralIngot);
	}

	private void createStarForgeItems() {
		// Star Forge Bricks and Controller
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.STAR_FORGE_BRICKS.get(), 4)
				.define('a', BlockItemRegistry.MOONGLOW_ITEM.get())
				.define('b', CommonItemTagGroups.COBALT_INGOTS)
				.define('c', Items.BRICKS)
				.pattern("bcb")
				.pattern("cac")
				.pattern("bcb")
				.group("star_forge")
				.unlockedBy("moonglow", has(BlockItemRegistry.MOONGLOW_ITEM.get()))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.STAR_FORGE_CONTROLLER.get())
				.define('a', BlockItemRegistry.STAR_FORGE_BRICKS_ITEM.get())
				.define('b', IWItemTagGroups.MOLTEN_INGOTS)
				.define('c', Items.BLAST_FURNACE)
				.pattern("aaa")
				.pattern("bcb")
				.pattern("aaa")
				.group("star_forge")
				.unlockedBy("star_forge_bricks", has(BlockItemRegistry.STAR_FORGE_BRICKS_ITEM.get()))
				.save(output);

		// Solar Lens
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.SOLAR_LENS.get())
				.define('a', CommonItemTagGroups.COBALT_INGOTS)
				.define('b', Tags.Items.GLASS_PANES)
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("star_forge")
				.unlockedBy("cobalt_ingot", has(CommonItemTagGroups.COBALT_INGOTS))
				.save(output);

		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 2, ItemRegistry.OBSIDIAN_ROD.get(), 1, ItemRegistry.ASTRAL_SWORD.get(), 300);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 3, ItemRegistry.OBSIDIAN_ROD.get(), 2, ItemRegistry.ASTRAL_PICKAXE.get(), 300);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 3, ItemRegistry.OBSIDIAN_ROD.get(), 2, ItemRegistry.ASTRAL_AXE.get(), 300);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 1, ItemRegistry.OBSIDIAN_ROD.get(), 2, ItemRegistry.ASTRAL_SHOVEL.get(), 300);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 2, ItemRegistry.OBSIDIAN_ROD.get(), 2, ItemRegistry.ASTRAL_HOE.get(), 300);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 5, ItemRegistry.ASTRAL_HELMET.get(), 600);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 8, ItemRegistry.ASTRAL_CHESTPLATE.get(), 600);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 7, ItemRegistry.ASTRAL_LEGGINGS.get(), 600);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 4, ItemRegistry.ASTRAL_BOOTS.get(), 600);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 5, ItemRegistry.GAUNTLET_SCAFFOLDING.get(), 1, ItemRegistry.ASTRAL_GAUNTLET.get(), 300);
		starForgeSmelting(IWItemTagGroups.ASTRAL_INGOTS, 3, ItemRegistry.WOODEN_TOOL_ROD.get(), 1, ItemRegistry.ASTRAL_PIKE.get(), 300);

		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 2, ItemRegistry.OBSIDIAN_ROD.get(), 1, ItemRegistry.STARSTORM_SWORD.get(), 300);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 3, ItemRegistry.OBSIDIAN_ROD.get(), 2, ItemRegistry.STARSTORM_PICKAXE.get(), 300);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 3, ItemRegistry.OBSIDIAN_ROD.get(), 2, ItemRegistry.STARSTORM_AXE.get(), 300);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 1, ItemRegistry.OBSIDIAN_ROD.get(), 2, ItemRegistry.STARSTORM_SHOVEL.get(), 300);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 2, ItemRegistry.OBSIDIAN_ROD.get(), 2, ItemRegistry.STARSTORM_HOE.get(), 300);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 5, ItemRegistry.STARSTORM_HELMET.get(), 600);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 8, ItemRegistry.STARSTORM_CHESTPLATE.get(), 600);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 7, ItemRegistry.STARSTORM_LEGGINGS.get(), 600);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 4, ItemRegistry.STARSTORM_BOOTS.get(), 600);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 5, ItemRegistry.GAUNTLET_SCAFFOLDING.get(), 1, ItemRegistry.STARSTORM_GAUNTLET.get(), 300);
		starForgeSmelting(IWItemTagGroups.STARSTORM_INGOTS, 3, ItemRegistry.WOODEN_TOOL_ROD.get(), 1, ItemRegistry.STARSTORM_PIKE.get(), 300);

		starForgeSmelting(BlockItemRegistry.VOID_ORE_ITEM.get(), 1, ItemRegistry.ENDER_ESSENCE.get(), 3, ItemRegistry.VOID_INGOT.get(), 900);
	}

	private void createGrenades() {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SMOKE_GRENADE.get(), 2)
				.define('a', Items.BAMBOO)
				.define('b', ItemRegistry.GRENADE_ASSEMBLY.get())
				.define('c', ItemRegistry.SMOKE_POWDER.get())
				.pattern(" cb")
				.pattern(" a ")
				.pattern(" a ")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(ItemRegistry.SMOKE_POWDER.get()))
				.save(output);

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
				.save(output);

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
				.save(output);

		// Flashbang
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FLASHBANG.get(), 2)
				.define('a', Items.BAMBOO)
				.define('b', ItemRegistry.GRENADE_ASSEMBLY.get())
				.define('c', ItemRegistry.POTASSIUM_NITRATE.get())
				.pattern(" cb")
				.pattern(" a ")
				.pattern(" a ")
				.group("flashbang")
				.unlockedBy("flashbang", has(ItemRegistry.POTASSIUM_NITRATE.get()))
				.save(output);
	}

	private void createPanelItems() {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.IRON_PANEL_ITEM.get(), 8)
				.define('a', Tags.Items.STORAGE_BLOCKS_IRON)
				.pattern("aaa")
				.group("iron_panel")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.IRON_PANEL_BARS_ITEM.get(), 8)
				.define('a', Tags.Items.STORAGE_BLOCKS_IRON)
				.define('b', Items.IRON_BARS)
				.pattern("aba")
				.group("iron_panel")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(output);
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

	private void createUtilityItems() {
		// Barbed wire
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BARBED_WIRE_ITEM.get(), 3)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', CommonItemTagGroups.METAL_NUGGETS)
				.pattern("bab")
				.pattern("aba")
				.pattern("bab")
				.group("barbed_wire")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(output);
		// Barbed wire fence
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BARBED_WIRE_FENCE_ITEM.get(), 6)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', BlockItemRegistry.BARBED_WIRE_ITEM.get())
				.pattern("aba")
				.pattern("aba")
				.group("barbed_wire")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(output);
		// Bear trap
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BEAR_TRAP_ITEM.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
				.define('c', Tags.Items.DUSTS_REDSTONE)
				.pattern("aba")
				.pattern(" c ")
				.group("traps")
				.unlockedBy("redstone", has(Tags.Items.DUSTS_REDSTONE))
				.save(output);
		// Landmine
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.LANDMINE_ITEM.get())
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
				.define('c', Items.TNT)
				.pattern(" b ")
				.pattern("aca")
				.group("traps")
				.unlockedBy("gunpowder", has(Items.TNT))
				.save(output);
		// Mortar and shell
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BlockItemRegistry.MORTAR_ITEM.get())
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', Tags.Items.STONES)
				.pattern("b b")
				.pattern("aba")
				.group("artillery")
				.unlockedBy("metal_ingots", has(CommonItemTagGroups.METAL_INGOTS))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ItemRegistry.MORTAR_SHELL.get())
				.define('a', Items.TNT)
				.define('b', Tags.Items.STONES)
				.pattern(" b ")
				.pattern("bab")
				.pattern(" b ")
				.group("artillery")
				.unlockedBy("tnt", has(Items.TNT))
				.save(output);
		// Panic alarm
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.PANIC_ALARM_ITEM.get())
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', Items.NOTE_BLOCK)
				.pattern("aba")
				.group("alarms")
				.unlockedBy("note_block", has(Items.NOTE_BLOCK))
				.save(output);
		// Sandbag
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.SANDBAG_ITEM.get(), 6)
				.define('a', Tags.Items.SANDS)
				.define('b', ItemRegistry.CLOTH_SCRAP.get())
				.pattern("bbb")
				.pattern("aaa")
				.pattern("bbb")
				.group("sandbags")
				.unlockedBy("cloth_scrap", has(ItemRegistry.CLOTH_SCRAP.get()))
				.save(output);
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
				.save(output);
		// Pitfall
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.PITFALL_ITEM.get())
				.define('a', Items.STICK)
				.define('b', Items.SHORT_GRASS)
				.define('c', ItemTags.LEAVES)
				.pattern("bcb")
				.pattern("aaa")
				.group("traps")
				.unlockedBy("grass", has(Items.SHORT_GRASS))
				.save(output);
		// Punji sticks
		ItemStack poison = new ItemStack(Items.POTION);
		poison.update(DataComponents.POTION_CONTENTS, PotionContents.EMPTY, Potions.POISON, PotionContents::withPotion);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.PUNJI_STICKS_ITEM.get(), 3)
				.define('a', Items.DIRT)
				.define('b', Items.BAMBOO)
				.define('c', DataComponentIngredient.of(true, poison))
				.pattern("bbb")
				.pattern("bcb")
				.pattern("aaa")
				.group("traps")
				.unlockedBy("bamboo", has(Items.BAMBOO))
				.save(output);
		// Spotlight
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.SPOTLIGHT_ITEM.get())
				.define('a', Items.REDSTONE_LAMP)
				.define('b', Items.BLACK_CONCRETE)
				.define('c', CommonItemTagGroups.METAL_INGOTS)
				.pattern("bab")
				.pattern("c c")
				.group("lights")
				.unlockedBy("redstone_lamp", has(Items.REDSTONE_LAMP))
				.save(output);
		// Wooden spikes
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.WOODEN_SPIKES_ITEM.get())
				.define('a', ItemTags.PLANKS)
				.define('b', Items.STICK)
				.pattern("b b")
				.pattern(" a ")
				.pattern("b b")
				.group("traps")
				.unlockedBy("planks", has(ItemTags.PLANKS))
				.save(output);
		// Barrel tap
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.BARREL_TAP_ITEM.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', CommonItemTagGroups.METAL_NUGGETS)
				.pattern("  b")
				.pattern("aaa")
				.pattern("  a")
				.group("barrel_tap")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(output);
	}

	private void createFirstAidItems() {
		// Bandage
		ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.BANDAGE.get(), 2)
				.define('a', Tags.Items.STRINGS)
				.define('b', ItemTags.WOOL)
				.pattern("aba")
				.group("first_aid")
				.unlockedBy("wool", has(ItemTags.WOOL))
				.save(output);
		// First aid kit
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.FIRST_AID_KIT.get())
				.requires(ItemRegistry.BANDAGE.get(), 2)
				.requires(ItemRegistry.PAINKILLERS.get())
				.group("first_aid")
				.unlockedBy("painkillers", has(ItemRegistry.PAINKILLERS.get()))
				.save(output);
		// Morphine
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.MORPHINE.get())
				.requires(ItemRegistry.SYRINGE.get())
				.requires(Items.FERMENTED_SPIDER_EYE)
				.requires(Items.SUGAR, 3)
				.group("first_aid")
				.unlockedBy("syringe", has(ItemRegistry.SYRINGE.get()))
				.save(output);
		// Painkillers
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.PAINKILLERS.get())
				.requires(Items.SUGAR)
				.requires(Items.GLISTERING_MELON_SLICE)
				.requires(Items.BEETROOT)
				.group("first_aid")
				.unlockedBy("glistering_melon_slice", has(Items.GLISTERING_MELON_SLICE))
				.save(output);
		// Syringe
		ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.SYRINGE.get(), 2)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', Tags.Items.NUGGETS_IRON)
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" b ")
				.group("first_aid")
				.unlockedBy("iron_nugget", has(Tags.Items.NUGGETS_IRON))
				.save(output);
	}

	private void createFoodItems() {
		// Chocolate bar
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.CHOCOLATE_BAR.get(), 8)
				.requires(Items.COCOA_BEANS, 8)
				.requires(Items.MILK_BUCKET)
				.group("food")
				.unlockedBy("cocoa_beans", has(Items.COCOA_BEANS))
				.save(output);
		// Explosive chocolate bar
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.EXPLOSIVE_CHOCOLATE_BAR.get(), 8)
				.requires(ItemRegistry.CHOCOLATE_BAR.get(), 8)
				.requires(Items.TNT)
				.group("food")
				.unlockedBy("chocolate_bar", has(ItemRegistry.CHOCOLATE_BAR.get()))
				.save(output);
		// MRE
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.MRE.get())
				.requires(Items.CARROT)
				.requires(Items.POTATO)
				.requires(Items.BEETROOT)
				.requires(Items.COOKED_CHICKEN)
				.group("food")
				.unlockedBy("mre_items",
						InventoryChangeTrigger.TriggerInstance.hasItems(Items.CARROT, Items.POTATO, Items.BEETROOT, Items.COOKED_CHICKEN))
				.save(output);
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
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.BLUNDERBUSS.get())
				.define('a', ItemRegistry.HEAVY_WOODEN_STOCK.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.WIDE_GOLDEN_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MUSKET.get())
				.define('a', ItemRegistry.HEAVY_WOODEN_STOCK.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.EXTENDED_IRON_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(output);
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
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MUSKET_SCOPE.get())
				.define('a', ItemRegistry.SCOPE.get())
				.define('b', ItemRegistry.MUSKET.get())
				.pattern("a")
				.pattern("b")
				.group("firearm")
				.unlockedBy("musket", has(ItemRegistry.MUSKET.get()))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(ItemRegistry.MUSKET_SCOPE.get()) + "_alt");
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FLARE_GUN.get())
				.define('a', ItemRegistry.WOODEN_PISTOL_HANDLE.get())
				.define('b', ItemRegistry.FLINTLOCK_ASSEMBLY.get())
				.define('c', ItemRegistry.TRIGGER_ASSEMBLY.get())
				.define('d', ItemRegistry.SHORT_IRON_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(ItemRegistry.FLINTLOCK_ASSEMBLY.get()))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FLARE.get(), 3)
				.define('a', CommonItemTagGroups.COPPER_NUGGETS)
				.define('b', Items.PAPER)
				.define('c', ItemRegistry.SMOKE_POWDER.get())
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("smoke_powder", has(ItemRegistry.SMOKE_POWDER.get()))
				.save(output);
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
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CANNONBALL.get(), 2)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', Tags.Items.INGOTS_COPPER)
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("firearm")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.EXPLOSIVE_CANNONBALL.get(), 4)
				.define('a', Items.TNT)
				.define('b', ItemRegistry.CANNONBALL.get())
				.pattern(" b ")
				.pattern("bab")
				.pattern(" b ")
				.group("firearm")
				.unlockedBy("tnt", has(Items.TNT))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MOLOTOV_COCKTAIL.get())
				.define('a', Tags.Items.STRINGS)
				.define('b', ItemRegistry.BOTTLE_OF_ALCOHOL.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("molotov_cocktail")
				.unlockedBy("bottle_of_alcohol", has(ItemRegistry.BOTTLE_OF_ALCOHOL.get()))
				.save(output);

		// Musket balls
		List<MaterialGroup> musketBallMaterials = new ArrayList<>(20);

		// Wood
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.WOODEN_SHARDS, 0.08f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.WOODEN_MUSKET_BALL.get());

		// Stone
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.STONE_SHARDS, 0.25f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.STONE_MUSKET_BALL.get());

		// Gold
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(Tags.Items.INGOTS_GOLD, 1.93f, 1.3f));
		musketBallMaterials.add(new MaterialGroup(Tags.Items.NUGGETS_GOLD, 0.21f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.GOLDEN_MUSKET_BALL.get());

		// Copper
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(Tags.Items.INGOTS_COPPER, 0.89f, 1.3f));
		musketBallMaterials.add(new MaterialGroup(CommonItemTagGroups.COPPER_NUGGETS, 0.1f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.COPPER_MUSKET_BALL.get());

		// Iron
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(Tags.Items.INGOTS_IRON, 0.79f, 1.3f));
		musketBallMaterials.add(new MaterialGroup(Tags.Items.NUGGETS_IRON, 0.09f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.IRON_MUSKET_BALL.get());

		// Cobalt
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(CommonItemTagGroups.COBALT_INGOTS, 0.89f, 1.3f));
		musketBallMaterials.add(new MaterialGroup(CommonItemTagGroups.COBALT_NUGGETS, 0.1f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.COBALT_MUSKET_BALL.get());

		// Diamond
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.DIAMOND_SHARDS, 0.09f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.DIAMOND_MUSKET_BALL.get());

		// Netherite
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(Tags.Items.INGOTS_NETHERITE, 2.5f, 1.3f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.NETHERITE_MUSKET_BALL.get());

		// Molten
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.MOLTEN_INGOTS, 0.35f, 1.3f));
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.MOLTEN_SHARDS, 0.04f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.MOLTEN_MUSKET_BALL.get());

		// Tesla
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.TESLA_INGOTS, 2.0f, 1.3f));
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.TESLA_NUGGETS, 0.22f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.TESLA_MUSKET_BALL.get());

		// Ventus
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.VENTUS_SHARDS, 0.05f, 1.3f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.VENTUS_MUSKET_BALL.get());

		// Starstorm
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.STARSTORM_INGOTS, 0.4f, 1.3f));
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.STARSTORM_SHARDS, 0.04f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.STARSTORM_MUSKET_BALL.get());

		// Astral
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.ASTRAL_INGOTS, 0.6f, 1.3f));
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.ASTRAL_NUGGETS, 0.06f, 0.14f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.ASTRAL_MUSKET_BALL.get());

		// Void
		musketBallMaterials.clear();
		musketBallMaterials.add(new MaterialGroup(IWItemTagGroups.VOID_INGOTS, 0.03f, 1.3f));
		ammunitionTableCrafting(musketBallMaterials, ItemRegistry.VOID_MUSKET_BALL.get());

		// Ammunition Table
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BlockItemRegistry.AMMUNITION_TABLE_ITEM.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ItemTags.PLANKS)
				.define('c', ItemTags.STONE_CRAFTING_MATERIALS)
				.define('d', Tags.Items.GUNPOWDERS)
				.pattern("aaa")
				.pattern("bdb")
				.pattern("cbc")
				.group("ammunition_table")
				.unlockedBy("gunpowder", has(Tags.Items.GUNPOWDERS))
				.save(output);

		// The Sword
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.THE_SWORD.get())
				.define('a', ItemRegistry.MOLTEN_SWORD.get())
				.define('b', ItemRegistry.VOID_INGOT.get())
				.define('c', ItemRegistry.VENTUS_SWORD.get())
				.define('d', ItemRegistry.TESLA_SWORD.get())
				.define('e', ItemRegistry.VOID_SWORD.get())
				.define('f', ItemRegistry.HANS_BLESSING.get())
				.define('g', ItemRegistry.HANSIUM_INGOT.get())
				.pattern("abc")
				.pattern("dfe")
				.pattern("ggg")
				.group("the_sword")
				.unlockedBy("hans_blessing", has(ItemRegistry.HANSIUM_INGOT.get()))
				.save(output);
	}

	private void createDecorations() {
		// Camp chair
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.CAMP_CHAIR_ITEM.get())
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', ItemTags.WOOL)
				.pattern("b  ")
				.pattern("bbb")
				.pattern("a a")
				.group("chairs")
				.unlockedBy("wool", has(ItemTags.WOOL))
				.save(output);
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
				.save(output);
		// Wall shelf
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.WALL_SHELF_ITEM.get())
				.define('a', ItemTags.WOODEN_SLABS)
				.define('b', CommonItemTagGroups.METAL_INGOTS)
				.pattern("bab")
				.pattern("bab")
				.group("shelves")
				.unlockedBy("wooden_slabs", has(ItemTags.WOODEN_SLABS))
				.save(output);

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
				.define('a', Tags.Items.LEATHERS)
				.define('b', Tags.Items.STRINGS)
				.define('c', Tags.Items.CHESTS_WOODEN)
				.pattern(" b ")
				.pattern("aca")
				.pattern(" a ")
				.group("satchel")
				.unlockedBy("leather", has(Tags.Items.LEATHERS))
				.save(output);

		// Powder horn
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.POWDER_HORN.get())
				.define('a', Tags.Items.GUNPOWDERS)
				.define('b', Tags.Items.STRINGS)
				.define('c', Items.GOAT_HORN)
				.pattern(" b ")
				.pattern("aca")
				.pattern(" a ")
				.group("powder_horn")
				.unlockedBy("goat_horn", has(Items.GOAT_HORN))
				.save(output);

		// Blademaster Emblem
		ItemStack death = new ItemStack(Items.POTION);
		death.update(DataComponents.POTION_CONTENTS, PotionContents.EMPTY, PotionRegistry.DEATH_POTION, PotionContents::withPotion);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BLADEMASTER_EMBLEM.get())
				.define('a', IWItemTagGroups.STARSTORM_SHARDS)
				.define('b', DataComponentIngredient.of(true, death))
				.define('c', ItemRegistry.STARSTORM_SWORD.get())
				.pattern("aaa")
				.pattern("aca")
				.pattern("aba")
				.group("blademaster_emblem")
				.unlockedBy("starstorm_sword", has(ItemRegistry.STARSTORM_SWORD.get()))
				.save(output);

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
				.save(output);
		// Cobalt Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.COBALT_RING.get())
				.define('a', CommonItemTagGroups.COBALT_INGOTS)
				.define('b', ItemRegistry.IRON_RING.get())
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("ring")
				.unlockedBy("iron_ring", has(ItemRegistry.IRON_RING.get()))
				.save(output);
		// Golden Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLDEN_RING.get())
				.define('a', Tags.Items.INGOTS_GOLD)
				.define('b', ItemRegistry.COBALT_RING.get())
				.pattern(" a ")
				.pattern("aba")
				.pattern(" a ")
				.group("ring")
				.unlockedBy("cobalt_ring", has(ItemRegistry.COBALT_RING.get()))
				.save(output);
		// Diamond Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.DIAMOND_RING.get())
				.define('a', Tags.Items.GEMS_DIAMOND)
				.define('b', ItemRegistry.GOLDEN_RING.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("ring")
				.unlockedBy("golden_ring", has(ItemRegistry.GOLDEN_RING.get()))
				.save(output);
		// Amethyst Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.AMETHYST_RING.get())
				.define('a', Tags.Items.GEMS_AMETHYST)
				.define('b', ItemRegistry.GOLDEN_RING.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("ring")
				.unlockedBy("amethyst_ring", has(ItemRegistry.GOLDEN_RING.get()))
				.save(output);
		// Emerald Ring
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.EMERALD_RING.get())
				.define('a', Tags.Items.GEMS_EMERALD)
				.define('b', ItemRegistry.GOLDEN_RING.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("ring")
				.unlockedBy("emerald_ring", has(ItemRegistry.GOLDEN_RING.get()))
				.save(output);
		// Netherite Ring
		netheriteSmithing(ItemRegistry.DIAMOND_RING.get(), ItemRegistry.NETHERITE_RING.get());

		// Goggles
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOGGLES.get())
				.define('a', Tags.Items.GLASS_PANES)
				.define('b', Tags.Items.INGOTS_IRON)
				.define('c', Tags.Items.STRINGS)
				.pattern(" c ")
				.pattern("aba")
				.group("goggles")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(output);

		// Lava Goggles
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.LAVA_GOGGLES.get())
				.define('a', ItemRegistry.GOGGLES.get())
				.define('b', IWItemTagGroups.MOLTEN_INGOTS)
				.define('c', Tags.Items.OBSIDIANS)
				.pattern(" c ")
				.pattern("bab")
				.pattern(" c ")
				.group("goggles")
				.unlockedBy("molten_ingot", has(IWItemTagGroups.MOLTEN_INGOTS))
				.save(output);

		// Bloody Cloth
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BLOODY_CLOTH.get())
				.define('a', ItemRegistry.CLOTH_SCRAP.get())
				.define('b', Items.REDSTONE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("bloody_cloth")
				.unlockedBy("cloth_scrap", has(ItemRegistry.CLOTH_SCRAP.get()))
				.save(output);

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
				.save(output);

		// Reinforced Depth Charm
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.REINFORCED_DEPTH_CHARM.get())
				.requires(ItemRegistry.DEPTH_CHARM.get())
				.requires(ItemRegistry.WARDEN_HEART.get())
				.requires(Items.DEEPSLATE, 7)
				.group("reinforced_depth_charm")
				.unlockedBy("depth_charm", has(ItemRegistry.DEPTH_CHARM.get()))
				.save(output);

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
				.save(output);
	}

	private void createMiscellaneousItems() {
		// Azul keystone
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.AZUL_KEYSTONE.get())
				.define('a', Tags.Items.GEMS_DIAMOND)
				.define('b', ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get())
				.pattern(" b ")
				.pattern("bab")
				.pattern(" b ")
				.group("azul_keystone")
				.unlockedBy("azul_keystone_fragment", has(ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get()))
				.save(output);
		// Azul Locator
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.AZUL_LOCATOR.get())
				.define('a', Tags.Items.INGOTS_GOLD)
				.define('b', ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get())
				.pattern("aba")
				.pattern(" a ")
				.group("azul_keystone")
				.unlockedBy("azul_keystone_fragment", has(ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get()))
				.save(output);
		// Cloth scrap
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CLOTH_SCRAP.get(), 4)
				.define('a', Tags.Items.STRINGS)
				.define('b', Items.SHORT_GRASS)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("cloth_scrap")
				.unlockedBy("grass", has(Items.SHORT_GRASS))
				.save(output);
		// Conductive alloy
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CONDUCTIVE_ALLOY.get())
				.define('a', Tags.Items.INGOTS_COPPER)
				.define('b', Tags.Items.INGOTS_GOLD)
				.pattern("ab")
				.group("conductive_alloy")
				.unlockedBy("gold", has(Tags.Items.INGOTS_GOLD))
				.save(output);
		// Mortar and pestle
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MORTAR_AND_PESTLE.get())
				.define('a', Items.BOWL)
				.define('b', Items.STICK)
				.pattern(" b ")
				.pattern("a  ")
				.group("mortar_and_pestle")
				.unlockedBy("bowl", has(Items.BOWL))
				.save(output);
		// Obsidian rod
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.OBSIDIAN_ROD.get(), 16)
				.define('a', Items.OBSIDIAN)
				.pattern(" a ")
				.pattern(" a ")
				.group("rods")
				.unlockedBy("obsidian", has(Items.OBSIDIAN))
				.save(output);
		// Pliers
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.PLIERS.get())
				.define('a', Items.LEATHER)
				.define('b', ItemRegistry.TOOL_JOINT.get())
				.define('c', CommonItemTagGroups.METAL_INGOTS)
				.pattern(" c ")
				.pattern(" b ")
				.pattern("a a")
				.group("pliers")
				.unlockedBy("small_parts_metal_tool", has(ItemRegistry.TOOL_JOINT.get()))
				.save(output);
		// Wooden tool rod
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.WOODEN_TOOL_ROD.get())
				.define('a', ItemTags.PLANKS)
				.pattern(" a ")
				.pattern("a  ")
				.group("rods")
				.unlockedBy("planks", has(ItemTags.PLANKS))
				.save(output);

		// Sulfur stuff
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockItemRegistry.RAW_SULFUR_BLOCK_ITEM.get())
				.group("sulfur")
				.unlockedBy("sulfur", has(ItemRegistry.SULFUR.get()));
		create3x3Object(builder, ItemRegistry.SULFUR.get());

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SULFUR.get(), 9)
				.requires(BlockItemRegistry.RAW_SULFUR_BLOCK_ITEM.get())
				.group("sulfur")
				.unlockedBy("raw_sulfur_block", has(BlockItemRegistry.RAW_SULFUR_BLOCK_ITEM.get()))
				.save(output);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SULFUR_DUST.get(), 4)
				.requires(ItemRegistry.SULFUR.get())
				.requires(ItemRegistry.MORTAR_AND_PESTLE.get())
				.unlockedBy("sulfur", has(ItemRegistry.SULFUR.get()))
				.save(output);

		// Black Powder
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BLACKPOWDER.get(), 8)
				.requires(CommonItemTagGroups.SULFUR_DUSTS)
				.requires(ItemTags.COALS)
				.requires(Ingredient.of(ItemRegistry.POTASSIUM_NITRATE.get()), 6)
				.requires(ItemRegistry.MORTAR_AND_PESTLE.get())
				.unlockedBy("potassium_nitrate", has(ItemRegistry.POTASSIUM_NITRATE.get()))
				.save(output);

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
				.save(output);

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
				.save(output);

		// Cursed Sight Staff
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.CURSED_SIGHT_STAFF.get())
				.define('a', ItemRegistry.CURSED_SIGHT_STAFF_CORE.get())
				.define('b', ItemRegistry.OBSIDIAN_ROD.get())
				.pattern("a")
				.pattern("b")
				.pattern("b")
				.group("cursed_sight_staff")
				.unlockedBy("cursed_sight_staff_core", has(ItemRegistry.CURSED_SIGHT_STAFF_CORE.get()))
				.save(output);

		// Padded Leather Armor
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PADDED_LEATHER_HELMET.get())
				.define('a', Items.LEATHER_HELMET)
				.define('b', ItemTags.WOOL)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("padded_leather_armor")
				.unlockedBy("leather_helmet", has(Items.LEATHER_HELMET))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PADDED_LEATHER_CHESTPLATE.get())
				.define('a', Items.LEATHER_CHESTPLATE)
				.define('b', ItemTags.WOOL)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("padded_leather_armor")
				.unlockedBy("leather_chestplate", has(Items.LEATHER_CHESTPLATE))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PADDED_LEATHER_LEGGINGS.get())
				.define('a', Items.LEATHER_LEGGINGS)
				.define('b', ItemTags.WOOL)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("padded_leather_armor")
				.unlockedBy("leather_leggings", has(Items.LEATHER_LEGGINGS))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PADDED_LEATHER_BOOTS.get())
				.define('a', Items.LEATHER_BOOTS)
				.define('b', ItemTags.WOOL)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("padded_leather_armor")
				.unlockedBy("leather_boots", has(Items.LEATHER_BOOTS))
				.save(output);

		// Tiltros Portal Frame
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.TILTROS_PORTAL_FRAME.get(), 4)
				.define('a', BlockRegistry.MOLTEN_BLOCK.get())
				.define('b', IWItemTagGroups.TESLA_INGOTS)
				.define('c', BlockRegistry.COBALT_BLOCK.get())
				.define('d', Items.NETHERITE_SCRAP)
				.pattern("cdc")
				.pattern("bab")
				.pattern("cdc")
				.group("tiltros_portal_frame")
				.unlockedBy("molten_block", has(BlockRegistry.MOLTEN_BLOCK.get()))
				.save(output);

		// Sculk Staff
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SCULK_STAFF.get())
				.define('a', ItemRegistry.WARDEN_HEART.get())
				.define('b', ItemRegistry.OBSIDIAN_ROD.get())
				.define('c', Items.ECHO_SHARD)
				.pattern("cac")
				.pattern(" b ")
				.pattern(" b ")
				.group("sculk_staff")
				.unlockedBy("warden_heart", has(ItemRegistry.WARDEN_HEART.get()))
				.save(output);

		// Recovery Staff
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.RECOVERY_STAFF.get())
				.define('a', ItemRegistry.BLOATED_HEART.get())
				.define('b', ItemRegistry.HANSIUM_INGOT.get())
				.define('c', ItemRegistry.OBSIDIAN_ROD.get())
				.define('d', Items.AMETHYST_SHARD)
				.pattern("bab")
				.pattern("dcd")
				.pattern(" c ")
				.group("recovery_staff")
				.unlockedBy("bloated_heart", has(ItemRegistry.BLOATED_HEART.get()))
				.save(output);

		// Commander Pedestal Augments
		createPedestalAugment(ItemRegistry.PEDESTAL_AUGMENT_SPEED.get(), Items.SUGAR);
		createPedestalAugment(ItemRegistry.PEDESTAL_AUGMENT_ARMOR.get(), Items.IRON_HELMET);
		createPedestalAugment(ItemRegistry.PEDESTAL_AUGMENT_ENCHANTMENT.get(), Items.BOOK);
		createPedestalAugment(ItemRegistry.PEDESTAL_AUGMENT_CAPACITY.get(), Items.CHEST);

		// Summoning statues
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockItemRegistry.MINUTEMAN_STATUE_ITEM.get())
				.define('a', ItemRegistry.BLUNDERBUSS.get())
				.define('b', ItemRegistry.MEDAL_OF_HONOR.get())
				.define('c', ItemRegistry.AZUL_KEYSTONE.get())
				.define('d', Tags.Items.INGOTS_GOLD)
				.define('e', Items.CRACKED_POLISHED_BLACKSTONE_BRICKS)
				.pattern(" a ")
				.pattern("dbd")
				.pattern("ece")
				.group("summoning_statue")
				.unlockedBy("medal_of_honor", has(ItemRegistry.MEDAL_OF_HONOR.get()))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockItemRegistry.MEDIC_STATUE_ITEM.get())
				.define('a', ItemRegistry.FIRST_AID_KIT.get())
				.define('b', ItemRegistry.MEDAL_OF_HONOR.get())
				.define('c', ItemRegistry.AZUL_KEYSTONE.get())
				.define('d', Tags.Items.INGOTS_GOLD)
				.define('e', Items.CRACKED_POLISHED_BLACKSTONE_BRICKS)
				.pattern(" a ")
				.pattern("dbd")
				.pattern("ece")
				.group("summoning_statue")
				.unlockedBy("medal_of_honor", has(ItemRegistry.MEDAL_OF_HONOR.get()))
				.save(output);
	}

	private void createMinecraftItems() {
		// Gunpowder
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 4)
				.requires(Ingredient.of(ItemTags.COALS), 2)
				.requires(Ingredient.of(CommonItemTagGroups.SULFUR_DUSTS), 2)
				.requires(ItemRegistry.POTASSIUM_NITRATE.get(), 4)
				.requires(ItemRegistry.MORTAR_AND_PESTLE.get())
				.group("gunpowder")
				.unlockedBy("potassium_nitrate", has(ItemRegistry.POTASSIUM_NITRATE.get()))
				.save(output);
		// Copper ingot
		ShapedRecipeBuilder shapedRecipeBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COPPER_INGOT)
				.group("copper_ingot")
				.unlockedBy("copper_nugget", has(CommonItemTagGroups.COPPER_NUGGETS));
		create3x3Object(shapedRecipeBuilder, CommonItemTagGroups.COPPER_NUGGETS);

		// Replace some recipes that are hardcoded to use certain ingots to use a tag for any "metal" ingot

		// Blast furnace
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BLAST_FURNACE)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', Blocks.FURNACE)
				.define('c', Items.SMOOTH_STONE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("ccc")
				.unlockedBy("furnace", has(Blocks.FURNACE))
				.save(output);

		// Bucket
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUCKET)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.pattern("a a")
				.pattern(" a ")
				.unlockedBy("metal_ingot", has(CommonItemTagGroups.METAL_INGOTS))
				.save(output);

		// Cauldron
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.CAULDRON)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.pattern("a a")
				.pattern("a a")
				.pattern("aaa")
				.unlockedBy("metal_ingot", has(CommonItemTagGroups.METAL_INGOTS))
				.save(output);

		// Chain
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CHAIN)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', CommonItemTagGroups.METAL_NUGGETS)
				.pattern(" b ")
				.pattern(" a ")
				.pattern(" b ")
				.unlockedBy("metal_ingot", has(CommonItemTagGroups.METAL_INGOTS))
				.save(output);

		// Hopper
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.HOPPER)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', Items.CHEST)
				.pattern("a a")
				.pattern("aba")
				.pattern(" a ")
				.unlockedBy("chest", has(Items.CHEST))
				.save(output);

		// Lantern
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.LANTERN)
				.define('a', CommonItemTagGroups.METAL_NUGGETS)
				.define('b', Items.TORCH)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.unlockedBy("torch", has(Items.TORCH))
				.save(output);

		// Minecart
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.MINECART)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.pattern("a a")
				.pattern("aaa")
				.unlockedBy("metal_ingot", has(CommonItemTagGroups.METAL_INGOTS))
				.save(output);

		// Piston
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.PISTON)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', ItemTags.PLANKS)
				.define('c', Items.REDSTONE)
				.define('d', Items.COBBLESTONE)
				.pattern("bbb")
				.pattern("dad")
				.pattern("dcd")
				.unlockedBy("redstone", has(Items.REDSTONE))
				.save(output);

		// Soul Lantern
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SOUL_LANTERN)
				.define('a', CommonItemTagGroups.METAL_NUGGETS)
				.define('b', Items.SOUL_TORCH)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.unlockedBy("soul_torch", has(Items.SOUL_TORCH))
				.save(output);

		// Stonecutter
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.STONECUTTER)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', Blocks.STONE)
				.pattern(" a ")
				.pattern("bbb")
				.unlockedBy("stone", has(Blocks.STONE))
				.save(output);

		// Tripwire Hook
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.TRIPWIRE_HOOK)
				.define('a', CommonItemTagGroups.METAL_INGOTS)
				.define('b', Items.STICK)
				.define('c', ItemTags.PLANKS)
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" c ")
				.unlockedBy("string", has(Items.STRING))
				.save(output);
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
				.save(output, BuiltInRegistries.ITEM.getKey(arrow));
	}

	public static void createPikeHead(Item pikeHead, TagKey<Item> material, TagKey<Item> nugget) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, pikeHead)
				.pattern("a")
				.pattern("b")
				.define('a', nugget)
				.define('b', material)
				.unlockedBy("has_material", has(material))
				.save(output, BuiltInRegistries.ITEM.getKey(pikeHead));
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
				.save(output, BuiltInRegistries.ITEM.getKey(pike));
	}

	public static void createGauntlet(Item gauntlet, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, gauntlet)
				.pattern("aaa")
				.pattern("aba")
				.define('a', material)
				.define('b', ItemRegistry.GAUNTLET_SCAFFOLDING.get())
				.unlockedBy("has_material", has(material))
				.save(output, BuiltInRegistries.ITEM.getKey(gauntlet));
	}

	public static void createMusketBall(Item musketBall, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, musketBall, 8)
				.define('a', material)
				.define('b', Items.GUNPOWDER)
				.pattern(" a ")
				.pattern("aba")
				.unlockedBy("has_material", has(material))
				.save(output);
	}

	private static void createCobaltIngot(ItemLike ingotItem, ItemLike ingotBlock) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingotItem)
				.group("cobalt")
				.unlockedBy("cobalt_ore", has(CommonItemTagGroups.COBALT_ORES));
		create3x3Object(builder, CommonItemTagGroups.COBALT_NUGGETS);
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
				.save(output);
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
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingotItem)
				.define('a', IWItemTagGroups.TESLA_NUGGETS)
				.pattern("aaa")
				.pattern("aaa")
				.pattern("aaa")
				.group("tesla")
				.unlockedBy("tesla_ingot", has(IWItemTagGroups.TESLA_INGOTS))
				.save(output, DataGenUtils.getItemFromNuggetsLocation(ingotItem.asItem()));

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
				.save(output);
	}

	private static void create3x3Object(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.pattern("aaa")
				.pattern("aaa")
				.pattern("aaa")
				.save(output);
	}

	private static void create3x3Object(ShapedRecipeBuilder builder, ItemLike material) {
		builder.define('a', material)
				.pattern("aaa")
				.pattern("aaa")
				.pattern("aaa")
				.save(output);
	}

	private static void createIngotFromBlock(ShapelessRecipeBuilder builder, ItemLike ingotBlock) {
		builder.requires(ingotBlock)
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(builder.getResult()) + "_from_" + getItemName(ingotBlock));
	}

	private static void createNuggetFromIngot(ShapelessRecipeBuilder builder, TagKey<Item> tagKey) {
		builder.requires(tagKey)
				.save(output);
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
				.save(output);
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
				.save(output);
	}

	private static void createTable(ItemLike tableItem, ItemLike slabItem, ItemLike fenceItem) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tableItem)
				.define('a', slabItem)
				.define('b', fenceItem)
				.pattern("a")
				.pattern("b")
				.group("tables")
				.unlockedBy("planks", has(ItemTags.PLANKS))
				.save(output);
	}

	private static void createShard(ShapelessRecipeBuilder builder, ItemLike material) {
		builder.requires(material)
				.group("shard")
				.save(output);
	}

	public static void createPedestalAugment(ItemLike augment, ItemLike material) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, augment)
				.define('a', ItemRegistry.HANSIUM_INGOT.get())
				.define('b', Tags.Items.INGOTS_GOLD)
				.define('c', Tags.Items.DYES_RED)
				.define('d', material)
				.pattern("acb")
				.pattern("cdc")
				.pattern("bca")
				.group("pedestal_augments")
				.unlockedBy("hansium_ingot", has(ItemRegistry.HANSIUM_INGOT.get()))
				.save(output);
	}

	public static void createVoidUpgrade(ItemLike starstormItem, ItemLike astralItem, ItemLike voidItem) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, voidItem)
				.define('a', starstormItem)
				.define('b', astralItem)
				.define('c', IWItemTagGroups.STARSTORM_INGOTS)
				.define('d', IWItemTagGroups.ASTRAL_INGOTS)
				.define('e', IWItemTagGroups.VOID_INGOTS)
				.pattern("ced")
				.pattern("aeb")
				.pattern("ced")
				.group("void")
				.unlockedBy("void_ingot", has(IWItemTagGroups.VOID_INGOTS))
				.save(output);
	}

	public static void createSmeltingRecipe(List<ItemLike> ingredients, ItemLike result, float experience, int cookTime, @Nullable String group) {
		for (ItemLike itemlike : ingredients) {
			SimpleCookingRecipeBuilder.smelting(Ingredient.of(itemlike), RecipeCategory.MISC, result, experience, cookTime)
					.group(group)
					.unlockedBy(getHasName(itemlike), has(itemlike))
					.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_from_smelting_" + getItemName(itemlike));
		}
	}

	private static void createSmeltingRecipe(ItemLike ingredient, ItemLike result, float experience, int cookTime, String group) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookTime)
				.group(group).unlockedBy(getHasName(ingredient), has(ingredient))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_from_smelting_" + getItemName(ingredient));
	}

	private static void createBlastingRecipe(List<ItemLike> ingredients, ItemLike result, float experience, int cookTime, @Nullable String group) {
		for (ItemLike itemlike : ingredients) {
			SimpleCookingRecipeBuilder.smelting(Ingredient.of(itemlike), RecipeCategory.MISC, result, experience, cookTime)
					.group(group)
					.unlockedBy(getHasName(itemlike), has(itemlike))
					.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_from_blasting_" + getItemName(itemlike));
		}
	}

	private static void createBlastingRecipe(ItemLike ingredient, ItemLike result, float experience, int cookTime, String group) {
		SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookTime)
				.group(group).unlockedBy(getHasName(ingredient), has(ingredient))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_from_blasting_" + getItemName(ingredient));
	}

	private static void netheriteSmithing(Item baseItem, Item pResultItem) {
		SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
						Ingredient.of(baseItem),
						Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.MISC, pResultItem)
				.unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(pResultItem) + "_smithing");
	}

	private static void teslaSynthesizing(ItemLike block, ItemLike material1, ItemLike material2, int cookTime,
	                                      ItemLike result) {
		TeslaSynthesizerRecipeBuilder.synthesizing(Ingredient.of(block), Ingredient.of(material1), Ingredient.of(material2),
						cookTime, result.asItem())
				.unlockedBy("tesla_ingot", has(IWItemTagGroups.TESLA_INGOTS))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_tesla_synthesizing");
	}

	private static void smallPartsTinkering(TagKey<Item> material, List<Item> craftables) {
		SmallPartsRecipeBuilder.tinker(Ingredient.of(material), craftables)
				.unlockedBy("copper_ingot", has(Tags.Items.INGOTS_COPPER))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getTagName(material) + "_tinkering");
	}

	private static void starForgeSmelting(TagKey<Item> ingot, int ingotCount, Item secondaryMaterial, int secondaryMaterialCount, Item result, int smeltTime) {
		StarForgeRecipeBuilder.forge(Ingredient.of(ingot), ingotCount, Ingredient.of(secondaryMaterial), secondaryMaterialCount, result, smeltTime)
				.unlockedBy("star_forge_controller", has(BlockRegistry.STAR_FORGE_CONTROLLER.get()))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_star_forge_smelting");
	}

	private static void starForgeSmelting(ItemLike ingot, int ingotCount, Item secondaryMaterial, int secondaryMaterialCount, Item result, int smeltTime) {
		StarForgeRecipeBuilder.forge(Ingredient.of(ingot), ingotCount, Ingredient.of(secondaryMaterial), secondaryMaterialCount, result, smeltTime)
				.unlockedBy("star_forge_controller", has(BlockRegistry.STAR_FORGE_CONTROLLER.get()))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_star_forge_smelting");
	}

	private static void starForgeSmelting(TagKey<Item> ingot, int ingotCount, Item result, int smeltTime) {
		StarForgeRecipeBuilder.forge(Ingredient.of(ingot), ingotCount, Ingredient.EMPTY, 0, result, smeltTime)
				.unlockedBy("star_forge_controller", has(BlockRegistry.STAR_FORGE_CONTROLLER.get()))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_star_forge_smelting");
	}

	private static void barrelTapFermenting(ItemLike material, int materialCount, ItemLike result) {
		BarrelTapRecipeBuilder.fermenting(Ingredient.of(material), materialCount, result.asItem())
				.unlockedBy("barrel_tap", has(BlockItemRegistry.BARREL_TAP_ITEM.get()))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_fermenting_from_" + getItemName(material));
	}

	private static void astralCrystalSorcery(ItemLike primaryMaterial, ItemLike secondaryMaterial, ItemStack result) {
		AstralCrystalRecipeBuilder.sorcery(Ingredient.of(primaryMaterial), Ingredient.of(secondaryMaterial), result)
				.unlockedBy("astral_crystal", has(BlockItemRegistry.ASTRAL_CRYSTAL_ITEM.get()))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_astral_crystal_sorcery");
	}

	private static void pistonCrushing(Block block, ItemLike result, int minCount, int maxCount) {
		PistonCrushingRecipeBuilder.crushing(block, result.asItem(), minCount, maxCount)
				.unlockedBy("piston", has(Blocks.PISTON))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_piston_crushing");
	}

	private static void ammunitionTableCrafting(List<MaterialGroup> materials, ItemLike result) {
		AmmunitionTableRecipeBuilder.crafting(materials, result.asItem())
				.unlockedBy("ammunition_table", has(BlockRegistry.AMMUNITION_TABLE.get()))
				.save(output, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_ammunition_table_crafting");
	}

	protected static String getConversionRecipeName(ItemLike pResult, ItemLike pIngredient) {
		return getItemName(pResult) + "_from_" + getItemName(pIngredient);
	}

	protected static String getItemName(ItemLike itemLike) {
		return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(itemLike.asItem())).getPath();
	}

	protected static String getItemName(ItemStack itemStack) {
		return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(itemStack.getItem())).getPath();
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
					.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(bricks).getPath(), has(material))
					.save(output, BuiltInRegistries.BLOCK.getKey(bricks));
		}

		public static void slab(Block slab, ItemLike material, String group, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
					.pattern("aaa")
					.define('a', material)
					.group(group)
					.unlockedBy(triggerName, trigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(slab));
		}

		public static void slab(Block slab, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(slab));
		}

		public static void stairs(Block stairs, ItemLike material, String group, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
					.pattern("a  ")
					.pattern("aa ")
					.pattern("aaa")
					.define('a', material)
					.group(group)
					.unlockedBy(triggerName, trigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(stairs));
		}

		public static void stairs(Block stairs, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
					.pattern("a  ")
					.pattern("aa ")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(stairs));
		}

		public static void wall(Block wall, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
					.pattern("aaa")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(wall));
		}

		public static void pillar(Block pillar, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pillar, 4)
					.pattern("a")
					.pattern("a")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(pillar));
		}

		public static void chiseled(Block chiseled, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chiseled)
					.pattern("a")
					.pattern("a")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(chiseled));
		}

		public static void cut(Block cut, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cut, 4)
					.pattern("aa")
					.pattern("aa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(output, BuiltInRegistries.BLOCK.getKey(cut));
		}
	}

	public static class StonecutterRecipes {

		public static void bricks(Block bricks, ItemLike material) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, bricks)
					.unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(bricks).getPath(), has(material))
					.save(output, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(bricks, material) + "_stonecutting");
		}

		public static void slab(Block slab, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, slab, 2)
					.unlockedBy(triggerName, trigger)
					.save(output, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(slab, material) + "_stonecutting");
		}

		public static void stairs(Block stairs, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, stairs)
					.unlockedBy(triggerName, trigger)
					.save(output, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(stairs, material) + "_stonecutting");
		}

		public static void wall(Block wall, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, wall)
					.unlockedBy(triggerName, trigger)
					.save(output, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(wall, material) + "_stonecutting");
		}

		public static void pillar(Block pillar, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, pillar)
					.unlockedBy(triggerName, trigger)
					.save(output, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(pillar, material) + "_stonecutting");
		}

		public static void chiseled(Block chiseled, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, chiseled)
					.unlockedBy(triggerName, trigger)
					.save(output, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(chiseled, material) + "_stonecutting");
		}

		public static void cut(Block cut, ItemLike material, String triggerName, Criterion<InventoryChangeTrigger.TriggerInstance> trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, cut)
					.unlockedBy(triggerName, trigger)
					.save(output, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(cut, material) + "_stonecutting");
		}
	}
}