package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.*;
import java.util.function.Consumer;

import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.blockRegistryPath;
import static tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities.itemRegistryPath;

public class RecipeGenerator extends RecipeProvider {

	static Consumer<FinishedRecipe> finishedRecipeConsumer;

	private static final ImmutableList<ItemLike> COBALT_SMELTABLES = ImmutableList.of(
			DeferredRegistryHandler.COBALT_ORE_ITEM.get(), DeferredRegistryHandler.DEEPSLATE_COBALT_ORE_ITEM.get(),
			DeferredRegistryHandler.RAW_COBALT.get());

	public RecipeGenerator(DataGenerator pGenerator) {
		super(pGenerator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
		finishedRecipeConsumer = pFinishedRecipeConsumer;

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
		createSmokeGrenades();
		createCorrugatedIronItems();
		createShardItems();
		createWarriorStatueItems();
		createUtilityItems();
		createFirstAidItems();
		createFoodItems();
		createWeapons();
		createMudItems();
		createDecorations();
		createMiscellaneousItems();
	}

	private void createFlagItems() {
		Item FLAG_POLE = DeferredRegistryHandler.FLAG_POLE_ITEM.get();

		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.AMERICAN_FLAG_ITEM.get())
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

		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.BRITISH_FLAG_ITEM.get())
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

		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.CANADIAN_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.RED_WOOL)
				.define('c', Items.WHITE_WOOL)
				.pattern("bcb")
				.pattern("bbb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.GADSDEN_FLAG_ITEM.get())
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

		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MEXICAN_FLAG_ITEM.get())
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


		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.TROLL_FLAG_ITEM.get())
				.define('a', FLAG_POLE)
				.define('b', Items.WHITE_WOOL)
				.define('c', Items.BLACK_WOOL)
				.pattern("bcb")
				.pattern("cbc")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(FLAG_POLE))
				.save(finishedRecipeConsumer);


		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.IMMERSIVE_WEAPONS_FLAG_ITEM.get())
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


		ShapedRecipeBuilder.shaped(FLAG_POLE, 3)
				.define('a', Tags.Items.INGOTS_IRON)
				.pattern(" a ")
				.pattern(" a ")
				.pattern(" a ")
				.group("flag")
				.unlockedBy("iron", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);
	}

	private void createGlassItems() {
		createBulletproofStainedGlass(DeferredRegistryHandler.BLACK_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_BLACK);
		createBulletproofStainedGlass(DeferredRegistryHandler.BLUE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_BLUE);
		createBulletproofStainedGlass(DeferredRegistryHandler.BROWN_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_BROWN);
		createBulletproofStainedGlass(DeferredRegistryHandler.CYAN_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_CYAN);
		createBulletproofStainedGlass(DeferredRegistryHandler.GRAY_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_GRAY);
		createBulletproofStainedGlass(DeferredRegistryHandler.GREEN_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_GREEN);
		createBulletproofStainedGlass(DeferredRegistryHandler.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_LIGHT_BLUE);
		createBulletproofStainedGlass(DeferredRegistryHandler.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_LIGHT_GRAY);
		createBulletproofStainedGlass(DeferredRegistryHandler.LIME_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_LIME);
		createBulletproofStainedGlass(DeferredRegistryHandler.MAGENTA_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_MAGENTA);
		createBulletproofStainedGlass(DeferredRegistryHandler.ORANGE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_ORANGE);
		createBulletproofStainedGlass(DeferredRegistryHandler.PINK_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_PINK);
		createBulletproofStainedGlass(DeferredRegistryHandler.PURPLE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_PURPLE);
		createBulletproofStainedGlass(DeferredRegistryHandler.RED_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_RED);
		createBulletproofStainedGlass(DeferredRegistryHandler.WHITE_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_WHITE);
		createBulletproofStainedGlass(DeferredRegistryHandler.YELLOW_STAINED_BULLETPROOF_GLASS_ITEM.get(),
				Tags.Items.DYES_YELLOW);

		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.BULLETPROOF_GLASS_ITEM.get(), 8)
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
		Item MUD = DeferredRegistryHandler.MUD_ITEM.get();
		Item DRIED_MUD = DeferredRegistryHandler.DRIED_MUD_ITEM.get();
		Item HARDENED_MUD = DeferredRegistryHandler.HARDENED_MUD_ITEM.get();
		Item HARDENED_MUD_SLAB = DeferredRegistryHandler.HARDENED_MUD_SLAB_ITEM.get();
		Item HARDENED_MUD_STAIRS = DeferredRegistryHandler.HARDENED_MUD_STAIRS_ITEM.get();

		createSmeltingRecipe(MUD, DRIED_MUD,
				0.1f, 100, "mud");
		createBlastingRecipe(MUD, DRIED_MUD,
				0.1f, 50, "mud");
		createSmeltingRecipe(DRIED_MUD, HARDENED_MUD,
				0.1f, 100, "mud");
		createBlastingRecipe(DRIED_MUD, HARDENED_MUD,
				0.1f, 50, "mud");

		// Slab from crafting table
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(HARDENED_MUD_SLAB, 6)
				.group("mud")
				.unlockedBy("hardened_mud", has(HARDENED_MUD));
		createSlab(builder, HARDENED_MUD);
		// Slab from stonecutter
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(HARDENED_MUD), HARDENED_MUD_SLAB, 2)
				.group("mud")
				.unlockedBy("hardened_mud", has(HARDENED_MUD))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
						+ getConversionRecipeName(HARDENED_MUD_SLAB, HARDENED_MUD) + "_stonecutting");

		// Stairs from crafting table
		builder = ShapedRecipeBuilder.shaped(HARDENED_MUD_STAIRS, 4)
				.group("mud")
				.unlockedBy("hardened_mud", has(HARDENED_MUD));
		createStairs(builder, HARDENED_MUD);
		// Stairs from stonecutter
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(HARDENED_MUD), HARDENED_MUD_STAIRS)
				.group("mud")
				.unlockedBy("hardened_mud", has(HARDENED_MUD))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
						+ getConversionRecipeName(HARDENED_MUD_STAIRS, HARDENED_MUD) + "_stonecutting");

		// Hardened mud window
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.HARDENED_MUD_WINDOW_ITEM.get(), 8)
				.define('a', HARDENED_MUD)
				.pattern("aaa")
				.pattern("a a")
				.pattern("aaa")
				.group("mud")
				.unlockedBy("hardened_mud", has(HARDENED_MUD))
				.save(finishedRecipeConsumer);
		// Mud
		ShapelessRecipeBuilder.shapeless(MUD, 8)
				.requires(Items.WATER_BUCKET)
				.requires(Items.DIRT, 8)
				.group("mud")
				.unlockedBy("water_bucket", has(Items.WATER_BUCKET))
				.save(finishedRecipeConsumer);
		// Mud ball
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.MUD_BALL.get(), 4)
				.requires(DeferredRegistryHandler.MUD.get())
				.group("mud")
				.unlockedBy("water_bucket", has(Items.WATER_BUCKET))
				.save(finishedRecipeConsumer);
	}

	private void createCobaltItems() {
		createCobaltIngot(DeferredRegistryHandler.COBALT_INGOT.get(), DeferredRegistryHandler.COBALT_BLOCK_ITEM.get());
		createCobaltBlock(DeferredRegistryHandler.COBALT_BLOCK_ITEM.get());
		createCobaltNugget(DeferredRegistryHandler.COBALT_NUGGET.get());
		createRawCobalt(DeferredRegistryHandler.RAW_COBALT.get(), DeferredRegistryHandler.RAW_COBALT_BLOCK_ITEM.get());
		createRawCobaltBlock(DeferredRegistryHandler.RAW_COBALT_BLOCK_ITEM.get(), DeferredRegistryHandler.RAW_COBALT.get());
	}

	private void createCopperItems() {
		// Copper ingot
		ShapedRecipeBuilder shapedRecipeBuilder = ShapedRecipeBuilder.shaped(Items.COPPER_INGOT)
				.group("copper_ingot")
				.unlockedBy("copper_nugget", has(ForgeItemTagGroups.COPPER_NUGGETS));
		create3x3Object(shapedRecipeBuilder, ForgeItemTagGroups.COPPER_NUGGETS);

		// Copper nugget
		ShapelessRecipeBuilder shapelessRecipeBuilder = ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.COPPER_NUGGET.get(), 9)
				.group("copper_nugget")
				.unlockedBy("copper_ingot", has(Tags.Items.INGOTS_COPPER));
		createNuggetFromIngot(shapelessRecipeBuilder, Tags.Items.INGOTS_COPPER);
	}

	private void createMoltenItems() {
		createMoltenIngot(DeferredRegistryHandler.MOLTEN_INGOT.get(), DeferredRegistryHandler.MOLTEN_BLOCK_ITEM.get());

		// Molten block
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MOLTEN_BLOCK_ITEM.get())
				.group("molten")
				.unlockedBy("molten_ingot", has(ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS));
		create3x3Object(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS);
	}

	private void createVentusItems() {
		Item VENTUS_STAFF_CORE = DeferredRegistryHandler.VENTUS_STAFF_CORE.get();

		// Ventus staff core
		ShapedRecipeBuilder.shaped(VENTUS_STAFF_CORE)
				.define('a', ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS)
				.define('b', Tags.Items.GEMS_DIAMOND)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("ventus")
				.unlockedBy("ventus_shard", has(ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS))
				.save(finishedRecipeConsumer);

		// Ventus staff
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.VENTUS_STAFF.get())
				.define('a', VENTUS_STAFF_CORE)
				.define('b', DeferredRegistryHandler.OBSIDIAN_ROD.get())
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" b ")
				.group("ventus")
				.unlockedBy("ventus_staff_core", has(VENTUS_STAFF_CORE))
				.save(finishedRecipeConsumer);
	}

	private void createTeslaItems() {
		createTeslaIngot(DeferredRegistryHandler.TESLA_INGOT.get(), DeferredRegistryHandler.TESLA_BLOCK_ITEM.get());
		createTeslaSynthesizer(DeferredRegistryHandler.TESLA_SYNTHESIZER_ITEM.get());
		teslaSynthesizing(Items.STONE, Items.LAPIS_LAZULI, DeferredRegistryHandler.CONDUCTIVE_ALLOY.get(), 24000,
				DeferredRegistryHandler.ELECTRIC_INGOT.get());

		// Tesla block
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(DeferredRegistryHandler.TESLA_BLOCK_ITEM.get())
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		create3x3Object(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS);
	}

	private void createAstralItems() {
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.ASTRAL_INGOT.get(), 9)
				.group("astral")
				.unlockedBy("astral_block", has(DeferredRegistryHandler.ASTRAL_BLOCK_ITEM.get()));
		createIngotFromBlock(builder, DeferredRegistryHandler.ASTRAL_BLOCK_ITEM.get());

		// Astral block
		ShapedRecipeBuilder builder1 = ShapedRecipeBuilder.shaped(DeferredRegistryHandler.ASTRAL_BLOCK_ITEM.get())
				.group("astral")
				.unlockedBy("astral_ingot", has(ImmersiveWeaponsItemTagGroups.ASTRAL_INGOTS));
		create3x3Object(builder1, ImmersiveWeaponsItemTagGroups.ASTRAL_INGOTS);
	}

	private void createStarstormItems() {
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.STARSTORM_INGOT.get(), 9)
				.group("starstorm")
				.unlockedBy("starstorm_block", has(DeferredRegistryHandler.STARSTORM_BLOCK_ITEM.get()));
		createIngotFromBlock(builder, DeferredRegistryHandler.STARSTORM_BLOCK_ITEM.get());

		// Starstorm block
		ShapedRecipeBuilder builder1 = ShapedRecipeBuilder.shaped(DeferredRegistryHandler.STARSTORM_BLOCK_ITEM.get())
				.group("starstorm")
				.unlockedBy("starstorm_ingot", has(ImmersiveWeaponsItemTagGroups.STARSTORM_INGOTS));
		create3x3Object(builder1, ImmersiveWeaponsItemTagGroups.STARSTORM_INGOTS);
	}

	private void createSmithingItems() {
		// Netherite arrow
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.NETHERITE_ARROW.get(), 8)
				.define('a', DeferredRegistryHandler.DIAMOND_ARROW.get())
				.define('b', Tags.Items.INGOTS_NETHERITE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("netherite")
				.unlockedBy("netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
				.save(finishedRecipeConsumer);

		// Netherite musket ball
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get(), 8)
				.define('a', DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get())
				.define('b', Tags.Items.INGOTS_NETHERITE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("netherite")
				.unlockedBy("netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
				.save(finishedRecipeConsumer);

		netheriteSmithing(DeferredRegistryHandler.DIAMOND_GAUNTLET.get(), DeferredRegistryHandler.NETHERITE_GAUNTLET.get());
		netheriteSmithing(DeferredRegistryHandler.DIAMOND_PIKE.get(), DeferredRegistryHandler.NETHERITE_PIKE.get());
	}

	private void createSmallPartsItems() {
		List<Item> IRON_INGOT_CRAFTABLES = new ArrayList<>(5);
		List<Item> GOLD_INGOT_CRAFTABLES = new ArrayList<>(5);
		List<Item> METAL_INGOT_CRAFTABLES = new ArrayList<>(5);
		List<Item> PLANK_CRAFTABLES = new ArrayList<>(5);

		IRON_INGOT_CRAFTABLES.add(DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get());
		IRON_INGOT_CRAFTABLES.add(DeferredRegistryHandler.IRON_BARREL.get());
		IRON_INGOT_CRAFTABLES.add(DeferredRegistryHandler.SHORT_IRON_BARREL.get());

		GOLD_INGOT_CRAFTABLES.add(DeferredRegistryHandler.WIDE_GOLDEN_BARREL.get());
		GOLD_INGOT_CRAFTABLES.add(DeferredRegistryHandler.TRIGGER_ASSEMBLY.get());
		GOLD_INGOT_CRAFTABLES.add(DeferredRegistryHandler.SCOPE_MOUNT.get());

		METAL_INGOT_CRAFTABLES.add(DeferredRegistryHandler.GRENADE_ASSEMBLY.get());
		METAL_INGOT_CRAFTABLES.add(DeferredRegistryHandler.TOOL_JOINT.get());

		PLANK_CRAFTABLES.add(DeferredRegistryHandler.GAUNTLET_SCAFFOLDING.get());
		PLANK_CRAFTABLES.add(DeferredRegistryHandler.HEAVY_WOODEN_STOCK.get());
		PLANK_CRAFTABLES.add(DeferredRegistryHandler.WOODEN_PISTOL_HANDLE.get());

		smallPartsTinkering(Tags.Items.INGOTS_IRON, IRON_INGOT_CRAFTABLES);
		smallPartsTinkering(Tags.Items.INGOTS_GOLD, GOLD_INGOT_CRAFTABLES);
		smallPartsTinkering(ForgeItemTagGroups.METAL_INGOTS, METAL_INGOT_CRAFTABLES);
		smallPartsTinkering(ItemTags.PLANKS, PLANK_CRAFTABLES);

		// Small parts table
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.SMALL_PARTS_TABLE.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ItemTags.PLANKS)
				.define('c', Tags.Items.INGOTS_COPPER)
				.pattern("aca")
				.pattern("aba")
				.unlockedBy("copper_ingot", has(Tags.Items.INGOTS_COPPER))
				.save(finishedRecipeConsumer);

		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.EXTENDED_IRON_BARREL.get())
				.define('a', DeferredRegistryHandler.IRON_BARREL.get())
				.pattern("aa")
				.group("small_parts")
				.unlockedBy("iron_barrel", has(DeferredRegistryHandler.IRON_BARREL.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.SCOPE.get())
				.define('a', Items.SPYGLASS)
				.define('b', DeferredRegistryHandler.SCOPE_MOUNT.get())
				.pattern("a")
				.pattern("b")
				.group("small_parts")
				.unlockedBy("spyglass", has(Items.SPYGLASS))
				.save(finishedRecipeConsumer);
	}

	private void createBarrelTapItems() {
		barrelTapFermenting(Items.WHEAT, 12, DeferredRegistryHandler.BOTTLE_OF_ALCOHOL.get());
		barrelTapFermenting(Items.SWEET_BERRIES, 12, DeferredRegistryHandler.BOTTLE_OF_WINE.get());
	}

	private void createSmokeGrenades() {
		createSmokeGrenade(DeferredRegistryHandler.SMOKE_GRENADE.get());
		createColoredSmokeGrenade(DeferredRegistryHandler.SMOKE_GRENADE_BLUE.get(), Tags.Items.DYES_BLUE);
		createColoredSmokeGrenade(DeferredRegistryHandler.SMOKE_GRENADE_GREEN.get(), Tags.Items.DYES_GREEN);
		createColoredSmokeGrenade(DeferredRegistryHandler.SMOKE_GRENADE_RED.get(), Tags.Items.DYES_RED);
		createColoredSmokeGrenade(DeferredRegistryHandler.SMOKE_GRENADE_PURPLE.get(), Tags.Items.DYES_PURPLE);
		createColoredSmokeGrenade(DeferredRegistryHandler.SMOKE_GRENADE_YELLOW.get(), Tags.Items.DYES_YELLOW);
		createSmokeGrenadeArrow(DeferredRegistryHandler.SMOKE_GRENADE_ARROW.get());
		createColoredSmokeGrenadeArrow(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_BLUE.get(), Tags.Items.DYES_BLUE);
		createColoredSmokeGrenadeArrow(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_GREEN.get(), Tags.Items.DYES_GREEN);
		createColoredSmokeGrenadeArrow(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_RED.get(), Tags.Items.DYES_RED);
		createColoredSmokeGrenadeArrow(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_PURPLE.get(), Tags.Items.DYES_PURPLE);
		createColoredSmokeGrenadeArrow(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_YELLOW.get(), Tags.Items.DYES_YELLOW);

		// Create smoke powder
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.SMOKE_POWDER.get(), 4)
				.requires(DeferredRegistryHandler.MORTAR_AND_PESTLE.get())
				.requires(Items.GUNPOWDER)
				.requires(ItemTags.COALS)
				.requires(ItemTags.LEAVES)
				.group("smoke_grenade")
				.unlockedBy("mortar_and_pestle", has(DeferredRegistryHandler.MORTAR_AND_PESTLE.get()))
				.save(finishedRecipeConsumer);
	}

	private void createCorrugatedIronItems() {
		createCorrugatedIronPanel(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_ITEM.get(),
				DeferredRegistryHandler.CORRUGATED_IRON_PANEL_FLAT_ITEM.get());
		createCorrugatedIronPanelBars(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_BARS_ITEM.get(),
				DeferredRegistryHandler.CORRUGATED_IRON_PANEL_FLAT_BARS_ITEM.get());
	}

	private void createShardItems() {
		// Stone shard
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.STONE_SHARD.get(), 3)
				.unlockedBy("cobblestone", has(Items.COBBLESTONE));
		createShard(builder, Items.COBBLESTONE);

		// Obsidian shard
		builder = ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.OBSIDIAN_SHARD.get(), 9)
				.unlockedBy("obsidian", has(Items.OBSIDIAN));
		createShard(builder, Items.OBSIDIAN);

		// Diamond shard
		builder = ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.DIAMOND_SHARD.get(), 4)
				.unlockedBy("diamond", has(Tags.Items.GEMS_DIAMOND));
		createShard(builder, Items.DIAMOND);
	}

	private void createWarriorStatueItems() {
		// Create warrior statute head
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.WARRIOR_STATUE_HEAD_ITEM.get())
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
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.WARRIOR_STATUE_TORSO_ITEM.get())
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
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.WARRIOR_STATUE_BASE_ITEM.get())
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
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.BARBED_WIRE_ITEM.get(), 3)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ForgeItemTagGroups.METAL_NUGGETS)
				.pattern("bab")
				.pattern("aba")
				.pattern("bab")
				.group("barbed_wire")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);
		// Barbed wire fence
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.BARBED_WIRE_FENCE_ITEM.get(), 6)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', DeferredRegistryHandler.BARBED_WIRE_ITEM.get())
				.pattern("aba")
				.pattern("aba")
				.group("barbed_wire")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
				.save(finishedRecipeConsumer);
		// Bear trap
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.BEAR_TRAP_ITEM.get())
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
				.define('c', Tags.Items.DUSTS_REDSTONE)
				.pattern("aba")
				.pattern(" c ")
				.group("traps")
				.unlockedBy("redstone", has(Tags.Items.DUSTS_REDSTONE))
				.save(finishedRecipeConsumer);
		// Landmine
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.LANDMINE_ITEM.get())
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
				.define('c', Items.TNT)
				.pattern(" b ")
				.pattern("aca")
				.group("traps")
				.unlockedBy("gunpowder", has(Items.TNT))
				.save(finishedRecipeConsumer);
		// Mortar and shell
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MORTAR_ITEM.get())
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Tags.Items.STONE)
				.pattern("b b")
				.pattern("aba")
				.group("artillery")
				.unlockedBy("metal_ingots", has(ForgeItemTagGroups.METAL_INGOTS))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MORTAR_SHELL.get())
				.define('a', Items.TNT)
				.define('b', Tags.Items.STONE)
				.pattern(" b ")
				.pattern("bab")
				.pattern(" b ")
				.group("artillery")
				.unlockedBy("tnt", has(Items.TNT))
				.save(finishedRecipeConsumer);
		// Panic alarm
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.PANIC_ALARM_ITEM.get())
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', Items.NOTE_BLOCK)
				.pattern("aba")
				.group("alarms")
				.unlockedBy("note_block", has(Items.NOTE_BLOCK))
				.save(finishedRecipeConsumer);
		// Sandbag
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.SANDBAG_ITEM.get(), 6)
				.define('a', Tags.Items.SAND)
				.define('b', DeferredRegistryHandler.CLOTH_SCRAP.get())
				.pattern("bbb")
				.pattern("aaa")
				.pattern("bbb")
				.group("sandbags")
				.unlockedBy("cloth_scrap", has(DeferredRegistryHandler.CLOTH_SCRAP.get()))
				.save(finishedRecipeConsumer);
		// Spike trap
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.SPIKE_TRAP_ITEM.get())
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
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.PITFALL_ITEM.get())
				.define('a', Items.STICK)
				.define('b', Items.GRASS)
				.define('c', ItemTags.LEAVES)
				.pattern("bcb")
				.pattern("aaa")
				.group("traps")
				.unlockedBy("grass", has(Items.GRASS))
				.save(finishedRecipeConsumer);
		// Punji sticks
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.PUNJI_STICKS_ITEM.get(), 3)
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
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.SPOTLIGHT_ITEM.get())
				.define('a', Items.REDSTONE_LAMP)
				.define('b', Items.BLACK_CONCRETE)
				.define('c', ForgeItemTagGroups.METAL_INGOTS)
				.pattern("bab")
				.pattern("c c")
				.group("lights")
				.unlockedBy("redstone_lamp", has(Items.REDSTONE_LAMP))
				.save(finishedRecipeConsumer);
		// Wooden spikes
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.WOODEN_SPIKES_ITEM.get())
				.define('a', ItemTags.PLANKS)
				.define('b', Items.STICK)
				.pattern("b b")
				.pattern(" a ")
				.pattern("b b")
				.group("traps")
				.unlockedBy("planks", has(ItemTags.PLANKS))
				.save(finishedRecipeConsumer);
		// Barrel tap
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.BARREL_TAP_ITEM.get())
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
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.BANDAGE.get(), 2)
				.define('a', Tags.Items.STRING)
				.define('b', ItemTags.WOOL)
				.pattern("aba")
				.group("first_aid")
				.unlockedBy("wool", has(ItemTags.WOOL))
				.save(finishedRecipeConsumer);
		// First aid kit
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.FIRST_AID_KIT.get())
				.requires(DeferredRegistryHandler.BANDAGE.get(), 2)
				.requires(DeferredRegistryHandler.PAINKILLERS.get())
				.group("first_aid")
				.unlockedBy("painkillers", has(DeferredRegistryHandler.PAINKILLERS.get()))
				.save(finishedRecipeConsumer);
		// Morphine
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.MORPHINE.get())
				.requires(DeferredRegistryHandler.SYRINGE.get())
				.requires(Items.FERMENTED_SPIDER_EYE)
				.requires(Items.SUGAR, 3)
				.group("first_aid")
				.unlockedBy("syringe", has(DeferredRegistryHandler.SYRINGE.get()))
				.save(finishedRecipeConsumer);
		// Painkillers
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.PAINKILLERS.get())
				.requires(Items.SUGAR)
				.requires(Items.GLISTERING_MELON_SLICE)
				.requires(Items.BEETROOT)
				.group("first_aid")
				.unlockedBy("glistering_melon_slice", has(Items.GLISTERING_MELON_SLICE))
				.save(finishedRecipeConsumer);
		// Syringe
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.SYRINGE.get(), 2)
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
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.CHOCOLATE_BAR.get(), 8)
				.requires(Items.COCOA_BEANS, 8)
				.requires(Items.MILK_BUCKET)
				.group("food")
				.unlockedBy("cocoa_beans", has(Items.COCOA_BEANS))
				.save(finishedRecipeConsumer);
		// Explosive chocolate bar
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.EXPLOSIVE_CHOCOLATE_BAR.get(), 8)
				.requires(DeferredRegistryHandler.CHOCOLATE_BAR.get(), 8)
				.requires(Items.TNT)
				.group("food")
				.unlockedBy("chocolate_bar", has(DeferredRegistryHandler.CHOCOLATE_BAR.get()))
				.save(finishedRecipeConsumer);
		// MRE
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.MRE.get())
				.requires(Items.CARROT)
				.requires(Items.POTATO)
				.requires(Items.BEETROOT)
				.requires(Items.COOKED_CHICKEN)
				.group("food")
				.unlockedBy("mre_items",
						new InventoryChangeTrigger.TriggerInstance(Composite.ANY, Ints.ANY,
								Ints.ANY, Ints.ANY,
								new ItemPredicate[]{Builder.item().of(Items.CARROT).of(Items.POTATO)
										.of(Items.BEETROOT).of(Items.COOKED_CHICKEN).build()}))
				.save(finishedRecipeConsumer);
	}

	private void createWeapons() {
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.FLINTLOCK_PISTOL.get())
				.define('a', DeferredRegistryHandler.WOODEN_PISTOL_HANDLE.get())
				.define('b', DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get())
				.define('c', DeferredRegistryHandler.TRIGGER_ASSEMBLY.get())
				.define('d', DeferredRegistryHandler.IRON_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.BLUNDERBUSS.get())
				.define('a', DeferredRegistryHandler.HEAVY_WOODEN_STOCK.get())
				.define('b', DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get())
				.define('c', DeferredRegistryHandler.TRIGGER_ASSEMBLY.get())
				.define('d', DeferredRegistryHandler.WIDE_GOLDEN_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MUSKET.get())
				.define('a', DeferredRegistryHandler.HEAVY_WOODEN_STOCK.get())
				.define('b', DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get())
				.define('c', DeferredRegistryHandler.TRIGGER_ASSEMBLY.get())
				.define('d', DeferredRegistryHandler.EXTENDED_IRON_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MUSKET_SCOPE.get())
				.define('a', DeferredRegistryHandler.HEAVY_WOODEN_STOCK.get())
				.define('b', DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get())
				.define('c', DeferredRegistryHandler.TRIGGER_ASSEMBLY.get())
				.define('d', DeferredRegistryHandler.EXTENDED_IRON_BARREL.get())
				.define('e', DeferredRegistryHandler.SCOPE.get())
				.pattern(" e ")
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MUSKET_SCOPE.get())
				.define('a', DeferredRegistryHandler.SCOPE.get())
				.define('b', DeferredRegistryHandler.MUSKET.get())
				.pattern("a")
				.pattern("b")
				.group("firearm")
				.unlockedBy("musket", has(DeferredRegistryHandler.MUSKET.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(DeferredRegistryHandler.MUSKET_SCOPE.get()) + "_alt");
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.FLARE_GUN.get())
				.define('a', DeferredRegistryHandler.WOODEN_PISTOL_HANDLE.get())
				.define('b', DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get())
				.define('c', DeferredRegistryHandler.TRIGGER_ASSEMBLY.get())
				.define('d', DeferredRegistryHandler.SHORT_IRON_BARREL.get())
				.pattern("abd")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("flintlock_assembly", has(DeferredRegistryHandler.FLINTLOCK_ASSEMBLY.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.FLARE.get(), 3)
				.define('a', ForgeItemTagGroups.COPPER_NUGGETS)
				.define('b', Items.PAPER)
				.define('c', DeferredRegistryHandler.SMOKE_POWDER.get())
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" c ")
				.group("firearm")
				.unlockedBy("smoke_powder", has(DeferredRegistryHandler.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MOLOTOV_COCKTAIL.get())
				.define('a', Tags.Items.STRING)
				.define('b', DeferredRegistryHandler.BOTTLE_OF_ALCOHOL.get())
				.pattern(" a ")
				.pattern(" b ")
				.group("molotov_cocktail")
				.unlockedBy("bottle_of_alcohol", has(DeferredRegistryHandler.BOTTLE_OF_ALCOHOL.get()))
				.save(finishedRecipeConsumer);
	}

	private void createDecorations() {
		// Camp chair
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.CAMP_CHAIR_ITEM.get())
				.define('a', ForgeItemTagGroups.METAL_INGOTS)
				.define('b', ItemTags.WOOL)
				.pattern("b  ")
				.pattern("bbb")
				.pattern("a a")
				.group("chairs")
				.unlockedBy("wool", has(ItemTags.WOOL))
				.save(finishedRecipeConsumer);
		// Celestial lantern
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.CELESTIAL_LANTERN_ITEM.get())
				.define('a', Tags.Items.NUGGETS_IRON)
				.define('b', Tags.Items.GLASS_PANES)
				.define('c', DeferredRegistryHandler.CELESTIAL_FRAGMENT.get())
				.pattern("aba")
				.pattern("bcb")
				.pattern("aba")
				.group("lights")
				.unlockedBy("celestial_fragment", has(DeferredRegistryHandler.CELESTIAL_FRAGMENT.get()))
				.save(finishedRecipeConsumer);
		// Wall shelf
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.WALL_SHELF_ITEM.get())
				.define('a', ItemTags.WOODEN_SLABS)
				.define('b', ForgeItemTagGroups.METAL_INGOTS)
				.pattern("bab")
				.pattern("bab")
				.group("shelves")
				.unlockedBy("wooden_slabs", has(ItemTags.WOODEN_SLABS))
				.save(finishedRecipeConsumer);

		// Tables
		createTable(DeferredRegistryHandler.OAK_TABLE_ITEM.get(), Items.OAK_SLAB, Items.OAK_FENCE);
		createTable(DeferredRegistryHandler.SPRUCE_TABLE_ITEM.get(), Items.SPRUCE_SLAB, Items.SPRUCE_FENCE);
		createTable(DeferredRegistryHandler.BIRCH_TABLE_ITEM.get(), Items.BIRCH_SLAB, Items.BIRCH_FENCE);
		createTable(DeferredRegistryHandler.JUNGLE_TABLE_ITEM.get(), Items.JUNGLE_SLAB, Items.JUNGLE_FENCE);
		createTable(DeferredRegistryHandler.ACACIA_TABLE_ITEM.get(), Items.ACACIA_SLAB, Items.ACACIA_FENCE);
		createTable(DeferredRegistryHandler.DARK_OAK_TABLE_ITEM.get(), Items.DARK_OAK_SLAB, Items.DARK_OAK_FENCE);
		createTable(DeferredRegistryHandler.CRIMSON_TABLE_ITEM.get(), Items.CRIMSON_SLAB, Items.CRIMSON_FENCE);
		createTable(DeferredRegistryHandler.WARPED_TABLE_ITEM.get(), Items.WARPED_SLAB, Items.WARPED_FENCE);
		createTable(DeferredRegistryHandler.BURNED_OAK_TABLE_ITEM.get(), DeferredRegistryHandler.BURNED_OAK_SLAB_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_FENCE_ITEM.get());

	}

	private void createMiscellaneousItems() {
		// Azul keystone
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.AZUL_KEYSTONE.get())
				.define('a', Tags.Items.GEMS_DIAMOND)
				.define('b', DeferredRegistryHandler.AZUL_KEYSTONE_FRAGMENT.get())
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("azul_keystone")
				.unlockedBy("azul_keystone_fragment", has(DeferredRegistryHandler.AZUL_KEYSTONE_FRAGMENT.get()))
				.save(finishedRecipeConsumer);
		// Azul Locator
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.AZUL_LOCATOR.get())
				.define('a', Tags.Items.INGOTS_GOLD)
				.define('b', DeferredRegistryHandler.AZUL_KEYSTONE_FRAGMENT.get())
				.pattern("aba")
				.pattern(" a ")
				.group("azul_keystone")
				.unlockedBy("azul_keystone_fragment", has(DeferredRegistryHandler.AZUL_KEYSTONE_FRAGMENT.get()))
				.save(finishedRecipeConsumer);
		// Cloth scrap
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.CLOTH_SCRAP.get(), 4)
				.define('a', Tags.Items.STRING)
				.define('b', Items.GRASS)
				.pattern("bbb")
				.pattern("bab")
				.pattern("bbb")
				.group("cloth_scrap")
				.unlockedBy("grass", has(Items.GRASS))
				.save(finishedRecipeConsumer);
		// Conductive alloy
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.CONDUCTIVE_ALLOY.get())
				.define('a', Tags.Items.INGOTS_COPPER)
				.define('b', Tags.Items.INGOTS_GOLD)
				.pattern("ab")
				.group("conductive_alloy")
				.unlockedBy("gold", has(Tags.Items.INGOTS_GOLD))
				.save(finishedRecipeConsumer);
		// Mortar and pestle
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.MORTAR_AND_PESTLE.get())
				.define('a', Items.BOWL)
				.define('b', Items.STICK)
				.pattern(" b ")
				.pattern("a  ")
				.group("mortar_and_pestle")
				.unlockedBy("bowl", has(Items.BOWL))
				.save(finishedRecipeConsumer);
		// Obsidian rod
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.OBSIDIAN_ROD.get(), 16)
				.define('a', Items.OBSIDIAN)
				.pattern(" a ")
				.pattern(" a ")
				.group("rods")
				.unlockedBy("obsidian", has(Items.OBSIDIAN))
				.save(finishedRecipeConsumer);
		// Pliers
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.PLIERS.get())
				.define('a', Items.LEATHER)
				.define('b', DeferredRegistryHandler.TOOL_JOINT.get())
				.define('c', ForgeItemTagGroups.METAL_INGOTS)
				.pattern(" c ")
				.pattern(" b ")
				.pattern("a a")
				.group("pliers")
				.unlockedBy("small_parts_metal_tool", has(DeferredRegistryHandler.TOOL_JOINT.get()))
				.save(finishedRecipeConsumer);
		// Gunpowder (from sulfur)
		ShapelessRecipeBuilder.shapeless(Items.GUNPOWDER)
				.requires(ItemTags.COALS)
				.requires(ForgeItemTagGroups.SULFUR_DUSTS)
				.group("gunpowder")
				.unlockedBy("sulfur", has(DeferredRegistryHandler.SULFUR.get()))
				.save(finishedRecipeConsumer);
		// Wooden tool rod
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.WOODEN_TOOL_ROD.get())
				.define('a', ItemTags.PLANKS)
				.pattern(" a ")
				.pattern("a  ")
				.group("rods")
				.unlockedBy("planks", has(ItemTags.PLANKS))
				.save(finishedRecipeConsumer);

		// Sulfur stuff
		createRawSulfurBlock(DeferredRegistryHandler.RAW_SULFUR_BLOCK_ITEM.get(), DeferredRegistryHandler.SULFUR.get());
		createSulfur(DeferredRegistryHandler.SULFUR.get(), DeferredRegistryHandler.RAW_SULFUR_BLOCK_ITEM.get());
	}

	public static void createArrow(ArrowItem arrow, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(arrow, 4)
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
		ShapedRecipeBuilder.shaped(pikeHead)
				.pattern("a")
				.pattern("b")
				.define('a', nugget)
				.define('b', material)
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer, itemRegistryPath(pikeHead));
	}

	public static void createPike(Item pike, TagKey<Item> material, Item pikeHead) {
		ShapedRecipeBuilder.shaped(pike)
				.pattern("a")
				.pattern("b")
				.pattern("c")
				.define('a', pikeHead)
				.define('b', material)
				.define('c', DeferredRegistryHandler.WOODEN_TOOL_ROD.get())
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer, itemRegistryPath(pike));
	}

	public static void createGauntlet(Item gauntlet, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(gauntlet)
				.pattern("aaa")
				.pattern("aba")
				.define('a', material)
				.define('b', DeferredRegistryHandler.GAUNTLET_SCAFFOLDING.get())
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer, itemRegistryPath(gauntlet));
	}

	public static void createMusketBall(Item musketBall, TagKey<Item> material) {
		ShapedRecipeBuilder.shaped(musketBall)
				.define('a', material)
				.define('b', Items.GUNPOWDER)
				.pattern(" a ")
				.pattern("aba")
				.unlockedBy("has_material", has(material))
				.save(finishedRecipeConsumer);
	}

	private static void createCobaltIngot(ItemLike ingotItem, ItemLike ingotBlock) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(ingotItem)
				.group("cobalt")
				.unlockedBy("cobalt_ore", has(ForgeItemTagGroups.COBALT_ORES));
		create3x3Object(builder, ForgeItemTagGroups.COBALT_NUGGETS);
		ShapelessRecipeBuilder builder1 = ShapelessRecipeBuilder.shapeless(ingotItem, 9)
				.group("cobalt")
				.unlockedBy("cobalt_block", has(DeferredRegistryHandler.COBALT_BLOCK.get()));
		createIngotFromBlock(builder1, ingotBlock);
		createSmeltingRecipe(COBALT_SMELTABLES, DeferredRegistryHandler.COBALT_INGOT.get(),
				0.8f, 200, "cobalt");
		createBlastingRecipe(COBALT_SMELTABLES, DeferredRegistryHandler.COBALT_INGOT.get(),
				0.8f, 100, "cobalt");
	}

	private static void createCobaltBlock(ItemLike blockItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(blockItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		create3x3Object(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createRawCobalt(ItemLike rawItem, ItemLike material) {
		ShapelessRecipeBuilder.shapeless(rawItem, 9)
				.requires(material)
				.group("cobalt")
				.unlockedBy("raw_cobalt_block", has(DeferredRegistryHandler.RAW_COBALT_BLOCK_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createRawCobaltBlock(ItemLike blockItem, ItemLike material) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(blockItem)
				.group("cobalt")
				.unlockedBy("raw_cobalt", has(DeferredRegistryHandler.RAW_COBALT.get()));
		create3x3Object(builder, material);
	}

	private static void createCobaltNugget(ItemLike nuggetItem) {
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(nuggetItem, 9)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createNuggetFromIngot(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createMoltenIngot(ItemLike ingotItem, ItemLike ingotBlock) {
		ShapedRecipeBuilder.shaped(ingotItem)
				.define('a', ImmersiveWeaponsItemTagGroups.MOLTEN_SHARDS)
				.define('b', DeferredRegistryHandler.OBSIDIAN_SHARD.get())
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("molten")
				.unlockedBy("molten_shard", has(ImmersiveWeaponsItemTagGroups.MOLTEN_SHARDS))
				.save(finishedRecipeConsumer);
		ShapelessRecipeBuilder builder1 = ShapelessRecipeBuilder.shapeless(ingotItem, 9)
				.group("molten")
				.unlockedBy("molten_shard", has(ImmersiveWeaponsItemTagGroups.MOLTEN_SHARDS));
		createIngotFromBlock(builder1, ingotBlock);
		createSmeltingRecipe(DeferredRegistryHandler.MOLTEN_ORE_ITEM.get(), DeferredRegistryHandler.MOLTEN_INGOT.get(),
				1.2f, 1200, "molten");
		createBlastingRecipe(DeferredRegistryHandler.MOLTEN_ORE_ITEM.get(), DeferredRegistryHandler.MOLTEN_INGOT.get(),
				1.2f, 600, "molten");
	}

	private static void createTeslaIngot(ItemLike ingotItem, ItemLike ingotBlock) {
		ShapedRecipeBuilder.shaped(ingotItem)
				.define('a', ImmersiveWeaponsItemTagGroups.ELECTRIC_INGOTS)
				.define('b', DeferredRegistryHandler.CONDUCTIVE_ALLOY.get())
				.pattern("ab ")
				.group("tesla")
				.unlockedBy("electric_ingot", has(ImmersiveWeaponsItemTagGroups.ELECTRIC_INGOTS))
				.save(finishedRecipeConsumer);
		ShapelessRecipeBuilder builder1 = ShapelessRecipeBuilder.shapeless(ingotItem, 9)
				.group("tesla")
				.unlockedBy("electric_ingot", has(ImmersiveWeaponsItemTagGroups.ELECTRIC_INGOTS));
		createIngotFromBlock(builder1, ingotBlock);
		createSmeltingRecipe(DeferredRegistryHandler.ELECTRIC_ORE_ITEM.get(), DeferredRegistryHandler.ELECTRIC_INGOT.get(),
				1.3f, 200, "tesla");
		createBlastingRecipe(DeferredRegistryHandler.ELECTRIC_ORE_ITEM.get(), DeferredRegistryHandler.ELECTRIC_INGOT.get(),
				1.3f, 100, "tesla");
	}

	private static void createTeslaSynthesizer(ItemLike synthesizerItem) {
		ShapedRecipeBuilder.shaped(synthesizerItem)
				.define('a', Items.NETHERITE_BLOCK)
				.define('b', DeferredRegistryHandler.MOLTEN_BLOCK_ITEM.get())
				.define('c', Tags.Items.INGOTS_COPPER)
				.define('d', Tags.Items.GLASS_PANES)
				.define('e', Tags.Items.INGOTS_IRON)
				.define('f', DeferredRegistryHandler.TESLA_INGOT.get())
				.pattern("fff")
				.pattern("cde")
				.pattern("aba")
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS))
				.save(finishedRecipeConsumer);
	}

	private static void createBulletproofStainedGlass(ItemLike stainedGlassItem, TagKey<Item> colorTag) {
		ShapedRecipeBuilder.shaped(stainedGlassItem, 8)
				.define('a', DeferredRegistryHandler.BULLETPROOF_GLASS.get())
				.define('b', colorTag)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("stained_bulletproof_glass")
				.unlockedBy("bulletproof_glass", has(DeferredRegistryHandler.BULLETPROOF_GLASS.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createRawSulfurBlock(ItemLike blockItem, ItemLike material) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(blockItem)
				.group("sulfur")
				.unlockedBy("sulfur", has(DeferredRegistryHandler.SULFUR.get()));
		create3x3Object(builder, material);
	}

	private static void createSulfur(ItemLike rawItem, ItemLike material) {
		ShapelessRecipeBuilder.shapeless(rawItem, 9)
				.requires(material)
				.group("sulfur")
				.unlockedBy("raw_sulfur_block", has(DeferredRegistryHandler.RAW_SULFUR_BLOCK_ITEM.get()))
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

	private static void createStairs(ShapedRecipeBuilder builder, ItemLike material) {
		builder.define('a', material)
				.pattern("a  ")
				.pattern("aa ")
				.pattern("aaa")
				.save(finishedRecipeConsumer);
	}

	private static void createSlab(ShapedRecipeBuilder builder, ItemLike material) {
		builder.define('a', material)
				.pattern("aaa")
				.save(finishedRecipeConsumer);
	}

	private static void createIngotFromBlock(ShapelessRecipeBuilder builder, ItemLike ingotBlock) {
		builder.requires(ingotBlock)
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(builder.getResult()) + "_from_" + getItemName(ingotBlock));
	}

	private static void createNuggetFromIngot(ShapelessRecipeBuilder builder, TagKey<Item> ingotBlock) {
		builder.requires(ingotBlock)
				.save(finishedRecipeConsumer);
	}

	private static void createSmokeGrenade(ItemLike smokeGrenadeItem) {
		ShapedRecipeBuilder.shaped(smokeGrenadeItem, 2)
				.define('a', Items.BAMBOO)
				.define('b', DeferredRegistryHandler.GRENADE_ASSEMBLY.get())
				.define('c', DeferredRegistryHandler.SMOKE_POWDER.get())
				.pattern(" cb")
				.pattern(" a ")
				.pattern(" a ")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(DeferredRegistryHandler.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createColoredSmokeGrenade(ItemLike smokeGrenadeItem, TagKey<Item> colorTag) {
		ShapedRecipeBuilder.shaped(smokeGrenadeItem, 2)
				.define('a', Items.BAMBOO)
				.define('b', DeferredRegistryHandler.GRENADE_ASSEMBLY.get())
				.define('c', DeferredRegistryHandler.SMOKE_POWDER.get())
				.define('d', colorTag)
				.pattern("dcb")
				.pattern(" a ")
				.pattern(" a ")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(DeferredRegistryHandler.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createSmokeGrenadeArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder.shaped(arrowItem, 4)
				.define('a', Items.ARROW)
				.define('b', DeferredRegistryHandler.SMOKE_POWDER.get())
				.pattern("aab")
				.pattern("aa ")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(DeferredRegistryHandler.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createColoredSmokeGrenadeArrow(ItemLike arrowItem, TagKey<Item> colorTag) {
		ShapedRecipeBuilder.shaped(arrowItem, 4)
				.define('a', Items.ARROW)
				.define('b', DeferredRegistryHandler.SMOKE_POWDER.get())
				.define('c', colorTag)
				.pattern("aab")
				.pattern("aac")
				.group("smoke_grenade")
				.unlockedBy("smoke_powder", has(DeferredRegistryHandler.SMOKE_POWDER.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createCorrugatedIronPanel(ItemLike panelItem, ItemLike flatPanelItem) {
		ShapedRecipeBuilder.shaped(panelItem, 8)
				.define('a', Tags.Items.STORAGE_BLOCKS_IRON)
				.pattern("aaa")
				.group("corrugated_iron")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(finishedRecipeConsumer);
		ShapelessRecipeBuilder.shapeless(panelItem)
				.requires(flatPanelItem)
				.group("corrugated_iron")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(panelItem) + "_alt");
		ShapelessRecipeBuilder.shapeless(flatPanelItem)
				.requires(panelItem)
				.group("corrugated_iron")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(finishedRecipeConsumer);
	}

	private static void createCorrugatedIronPanelBars(ItemLike panelItem, ItemLike flatPanelItem) {
		ShapedRecipeBuilder.shaped(panelItem, 8)
				.define('a', Tags.Items.STORAGE_BLOCKS_IRON)
				.define('b', Items.IRON_BARS)
				.pattern("aba")
				.group("corrugated_iron")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(finishedRecipeConsumer);
		ShapelessRecipeBuilder.shapeless(panelItem)
				.requires(flatPanelItem)
				.group("corrugated_iron")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(panelItem) + "_alt");
		ShapelessRecipeBuilder.shapeless(flatPanelItem)
				.requires(panelItem)
				.group("corrugated_iron")
				.unlockedBy("iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON))
				.save(finishedRecipeConsumer);
	}

	private static void createTable(ItemLike tableItem, ItemLike slabItem, ItemLike fenceItem) {
		ShapedRecipeBuilder.shaped(tableItem)
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
		oreCooking(RecipeSerializer.SMELTING_RECIPE, pIngredients, pResult, pExperience,
				pCookingTime, pGroup, "_from_smelting");
	}

	private static void createSmeltingRecipe(ItemLike ingredient,
	                                         ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking(RecipeSerializer.SMELTING_RECIPE, ingredient, pResult, pExperience,
				pCookingTime, pGroup, "_from_smelting");
	}

	private static void createBlastingRecipe(List<ItemLike> pIngredients,
	                                         ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking(RecipeSerializer.BLASTING_RECIPE, pIngredients, pResult, pExperience,
				pCookingTime, pGroup, "_from_blasting");
	}

	private static void createBlastingRecipe(ItemLike ingredient,
	                                         ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking(RecipeSerializer.BLASTING_RECIPE, ingredient, pResult, pExperience,
				pCookingTime, pGroup, "_from_blasting");
	}

	private static void oreCooking(SimpleCookingSerializer<?> pCookingSerializer, List<ItemLike> pIngredients,
	                               ItemLike pResult, float pExperience, int pCookingTime, @Nullable String pGroup, String pRecipeName) {
		for (ItemLike itemlike : pIngredients) {
			SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), pResult, pExperience, pCookingTime, pCookingSerializer)
					.group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
		}

	}

	private static void oreCooking(SimpleCookingSerializer<?> pCookingSerializer, ItemLike ingredient,
	                               ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
		SimpleCookingRecipeBuilder.cooking(Ingredient.of(ingredient), pResult, pExperience, pCookingTime, pCookingSerializer)
				.group(pGroup).unlockedBy(getHasName(ingredient), has(ingredient))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(ingredient));
	}

	private static void netheriteSmithing(Item baseItem, Item pResultItem) {
		UpgradeRecipeBuilder.smithing(Ingredient.of(baseItem),
						Ingredient.of(Items.NETHERITE_INGOT), pResultItem)
				.unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(pResultItem) + "_smithing");
	}

	private static void teslaSynthesizing(ItemLike block, ItemLike material1, ItemLike material2, int cookTime,
	                                      ItemLike result) {
		TeslaSynthesizerRecipeBuilder.synthesizing(Ingredient.of(block), Ingredient.of(material1), Ingredient.of(material2),
						cookTime, result.asItem())
				.unlocks("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_tesla_synthesizing");
	}

	private static void smallPartsTinkering(TagKey<Item> material, List<Item> craftables) {
		SmallPartsRecipeBuilder.tinker(Ingredient.of(material), craftables)
				.unlocks("copper_ingot", has(Tags.Items.INGOTS_COPPER))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getTagName(material) + "_tinkering");
	}

	private static void barrelTapFermenting(ItemLike material, int materialCount, ItemLike result) {
		BarrelTapRecipeBuilder.fermenting(Ingredient.of(material), materialCount, result.asItem())
				.unlocks("barrel_tap", has(DeferredRegistryHandler.BARREL_TAP_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getItemName(result) + "_fermenting");
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
			ShapedRecipeBuilder.shaped(bricks, 4)
					.define('a', material)
					.pattern("aa ")
					.pattern("aa ")
					.unlockedBy("has_" + blockRegistryPath(bricks).getPath(), has(material))
					.save(finishedRecipeConsumer, blockRegistryPath(bricks));
		}

		public static void slab(Block slab, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(slab, 6)
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(slab));
		}

		public static void stairs(Block stairs, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(stairs, 4)
					.pattern("a  ")
					.pattern("aa ")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(stairs));
		}

		public static void wall(Block wall, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(wall, 6)
					.pattern("aaa")
					.pattern("aaa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(wall));
		}

		public static void pillar(Block pillar, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(pillar, 4)
					.pattern("a")
					.pattern("a")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(pillar));
		}

		public static void chiseled(Block chiseled, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(chiseled)
					.pattern("a")
					.pattern("a")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(chiseled));
		}

		public static void cut(Block cut, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			ShapedRecipeBuilder.shaped(cut, 4)
					.pattern("aa")
					.pattern("aa")
					.define('a', material)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, blockRegistryPath(cut));
		}
	}

	public static class StonecutterRecipes {

		public static void bricks(Block bricks, ItemLike material) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), bricks)
					.unlockedBy("has_" + blockRegistryPath(bricks).getPath(), has(material))
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(bricks, material) + "_stonecutting");
		}

		public static void slab(Block slab, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), slab, 2)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(slab, material) + "_stonecutting");
		}

		public static void stairs(Block stairs, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), stairs)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(stairs, material) + "_stonecutting");
		}

		public static void wall(Block wall, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), wall)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(wall, material) + "_stonecutting");
		}

		public static void pillar(Block pillar, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), pillar)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(pillar, material) + "_stonecutting");
		}

		public static void chiseled(Block chiseled, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), chiseled)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(chiseled, material) + "_stonecutting");
		}

		public static void cut(Block cut, ItemLike material, String triggerName, CriterionTriggerInstance trigger) {
			SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), cut)
					.unlockedBy(triggerName, trigger)
					.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":"
							+ getConversionRecipeName(cut, material) + "_stonecutting");
		}
	}
}