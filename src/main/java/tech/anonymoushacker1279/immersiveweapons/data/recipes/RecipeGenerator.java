package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.*;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

	static Consumer<FinishedRecipe> finishedRecipeConsumer;

	private static final ImmutableList<ItemLike> COBALT_SMELTABLES = ImmutableList.of(
			DeferredRegistryHandler.COBALT_ORE_ITEM.get(), DeferredRegistryHandler.DEEPSLATE_COBALT_ORE_ITEM.get(),
			DeferredRegistryHandler.RAW_COBALT.get());

	public RecipeGenerator(DataGenerator pGenerator) {
		super(pGenerator);
	}

	@Override
	protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
		finishedRecipeConsumer = pFinishedRecipeConsumer;

		createFlagItems();
		createGlassItems();
		createBurnedOakItems();
		createCloudMarbleItems();
		createCobaltItems();
		createCopperItems();
		createMoltenItems();
		createVentusItems();
		createTeslaItems();
		createVanillaTieredItems();
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
		createAmericanFlag(DeferredRegistryHandler.AMERICAN_FLAG_ITEM.get(),
				DeferredRegistryHandler.FLAG_POLE_ITEM.get());
		createBritishFlag(DeferredRegistryHandler.BRITISH_FLAG_ITEM.get(),
				DeferredRegistryHandler.FLAG_POLE_ITEM.get());
		createCanadianFlag(DeferredRegistryHandler.CANADIAN_FLAG_ITEM.get(),
				DeferredRegistryHandler.FLAG_POLE_ITEM.get());
		createGadsdenFlag(DeferredRegistryHandler.GADSDEN_FLAG_ITEM.get(),
				DeferredRegistryHandler.FLAG_POLE_ITEM.get());
		createMexicanFlag(DeferredRegistryHandler.MEXICAN_FLAG_ITEM.get(),
				DeferredRegistryHandler.FLAG_POLE_ITEM.get());
		createTrollFlag(DeferredRegistryHandler.TROLL_FLAG_ITEM.get(),
				DeferredRegistryHandler.FLAG_POLE_ITEM.get());
		createImmersiveWeaponsFlag(DeferredRegistryHandler.IMMERSIVE_WEAPONS_FLAG_ITEM.get(),
				DeferredRegistryHandler.FLAG_POLE_ITEM.get());
		createFlagPole(DeferredRegistryHandler.FLAG_POLE_ITEM.get());
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
		createBulletproofGlass(DeferredRegistryHandler.BULLETPROOF_GLASS_ITEM.get());
	}

	private void createBurnedOakItems() {
		createBurnedOakBoat(DeferredRegistryHandler.BURNED_OAK_BOAT.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakChestBoat(DeferredRegistryHandler.BURNED_OAK_CHEST_BOAT.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakButton(DeferredRegistryHandler.BURNED_OAK_BUTTON_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakDoor(DeferredRegistryHandler.BURNED_OAK_DOOR_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakFence(DeferredRegistryHandler.BURNED_OAK_FENCE_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakFenceGate(DeferredRegistryHandler.BURNED_OAK_FENCE_GATE_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakPlanks(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakPressurePlate(DeferredRegistryHandler.BURNED_OAK_PRESSURE_PLATE_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakSign(DeferredRegistryHandler.BURNED_OAK_SIGN_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakSlab(DeferredRegistryHandler.BURNED_OAK_SLAB_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakStairs(DeferredRegistryHandler.BURNED_OAK_STAIRS_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakTrapdoor(DeferredRegistryHandler.BURNED_OAK_TRAPDOOR_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get());
		createBurnedOakWood(DeferredRegistryHandler.BURNED_OAK_WOOD_ITEM.get(),
				DeferredRegistryHandler.BURNED_OAK_LOG_ITEM.get());
	}

	private void createCloudMarbleItems() {
		createCloudMarbleBrickSlab(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB_ITEM.get(),
				DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get());
		createCloudMarbleStairs(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS_ITEM.get(),
				DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get());
		createCloudMarbleBricks(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get(),
				DeferredRegistryHandler.CLOUD_MARBLE_ITEM.get());
		createCloudMarblePillar(DeferredRegistryHandler.CLOUD_MARBLE_PILLAR_ITEM.get(),
				DeferredRegistryHandler.CLOUD_MARBLE_ITEM.get());
	}

	private void createMudItems() {
		createSmeltingRecipe(DeferredRegistryHandler.MUD_ITEM.get(), DeferredRegistryHandler.DRIED_MUD_ITEM.get(),
				0.1f, 100, "mud");
		createBlastingRecipe(DeferredRegistryHandler.MUD_ITEM.get(), DeferredRegistryHandler.DRIED_MUD_ITEM.get(),
				0.1f, 50, "mud");
		createSmeltingRecipe(DeferredRegistryHandler.DRIED_MUD_ITEM.get(), DeferredRegistryHandler.HARDENED_MUD_ITEM.get(),
				0.1f, 100, "mud");
		createBlastingRecipe(DeferredRegistryHandler.DRIED_MUD_ITEM.get(), DeferredRegistryHandler.HARDENED_MUD_ITEM.get(),
				0.1f, 50, "mud");
		createHardenedMudSlab(DeferredRegistryHandler.HARDENED_MUD_SLAB_ITEM.get(),
				DeferredRegistryHandler.HARDENED_MUD_ITEM.get());
		createHardenedMudStairs(DeferredRegistryHandler.HARDENED_MUD_STAIRS_ITEM.get(),
				DeferredRegistryHandler.HARDENED_MUD_ITEM.get());

		// Hardened mud window
		ShapedRecipeBuilder.shaped(DeferredRegistryHandler.HARDENED_MUD_WINDOW_ITEM.get(), 8)
				.define('a', DeferredRegistryHandler.HARDENED_MUD_ITEM.get())
				.pattern("aaa")
				.pattern("a a")
				.pattern("aaa")
				.group("mud")
				.unlockedBy("hardened_mud", has(DeferredRegistryHandler.HARDENED_MUD_ITEM.get()))
				.save(finishedRecipeConsumer);
		// Mud
		ShapelessRecipeBuilder.shapeless(DeferredRegistryHandler.MUD_ITEM.get(), 8)
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
		createCobaltArrow(DeferredRegistryHandler.COBALT_ARROW.get());
		createCobaltSword(DeferredRegistryHandler.COBALT_SWORD.get());
		createCobaltPickaxe(DeferredRegistryHandler.COBALT_PICKAXE.get());
		createCobaltAxe(DeferredRegistryHandler.COBALT_AXE.get());
		createCobaltShovel(DeferredRegistryHandler.COBALT_SHOVEL.get());
		createCobaltHoe(DeferredRegistryHandler.COBALT_HOE.get());
		createCobaltHelmet(DeferredRegistryHandler.COBALT_HELMET.get());
		createCobaltChestplate(DeferredRegistryHandler.COBALT_CHESTPLATE.get());
		createCobaltLeggings(DeferredRegistryHandler.COBALT_LEGGINGS.get());
		createCobaltBoots(DeferredRegistryHandler.COBALT_BOOTS.get());
		createCobaltGauntlet(DeferredRegistryHandler.COBALT_GAUNTLET.get());
		createCobaltPike(DeferredRegistryHandler.COBALT_PIKE.get(), DeferredRegistryHandler.COBALT_PIKE_HEAD.get());
		createCobaltPikeHead(DeferredRegistryHandler.COBALT_PIKE_HEAD.get());
		createCobaltIngot(DeferredRegistryHandler.COBALT_INGOT.get(), DeferredRegistryHandler.COBALT_BLOCK_ITEM.get());
		createCobaltBlock(DeferredRegistryHandler.COBALT_BLOCK_ITEM.get());
		createCobaltNugget(DeferredRegistryHandler.COBALT_NUGGET.get());
		createCobaltMusketBall(DeferredRegistryHandler.COBALT_MUSKET_BALL.get());
		createRawCobalt(DeferredRegistryHandler.RAW_COBALT.get(), DeferredRegistryHandler.RAW_COBALT_BLOCK_ITEM.get());
		createRawCobaltBlock(DeferredRegistryHandler.RAW_COBALT_BLOCK_ITEM.get(), DeferredRegistryHandler.RAW_COBALT.get());
	}

	private void createCopperItems() {
		createCopperArrow(DeferredRegistryHandler.COPPER_ARROW.get());
		createCopperSword(DeferredRegistryHandler.COPPER_SWORD.get());
		createCopperPickaxe(DeferredRegistryHandler.COPPER_PICKAXE.get());
		createCopperAxe(DeferredRegistryHandler.COPPER_AXE.get());
		createCopperShovel(DeferredRegistryHandler.COPPER_SHOVEL.get());
		createCopperHoe(DeferredRegistryHandler.COPPER_HOE.get());
		createCopperHelmet(DeferredRegistryHandler.COPPER_HELMET.get());
		createCopperChestplate(DeferredRegistryHandler.COPPER_CHESTPLATE.get());
		createCopperLeggings(DeferredRegistryHandler.COPPER_LEGGINGS.get());
		createCopperBoots(DeferredRegistryHandler.COPPER_BOOTS.get());
		createCopperGauntlet(DeferredRegistryHandler.COPPER_GAUNTLET.get());
		createCopperPike(DeferredRegistryHandler.COPPER_PIKE.get(), DeferredRegistryHandler.COPPER_PIKE_HEAD.get());
		createCopperPikeHead(DeferredRegistryHandler.COPPER_PIKE_HEAD.get());
		createCopperIngot(Items.COPPER_INGOT);
		createCopperNugget(DeferredRegistryHandler.COPPER_NUGGET.get());
		createCopperMusketBall(DeferredRegistryHandler.COPPER_MUSKET_BALL.get());
	}

	private void createMoltenItems() {
		createMoltenSword(DeferredRegistryHandler.MOLTEN_SWORD.get());
		createMoltenPickaxe(DeferredRegistryHandler.MOLTEN_PICKAXE.get());
		createMoltenAxe(DeferredRegistryHandler.MOLTEN_AXE.get());
		createMoltenShovel(DeferredRegistryHandler.MOLTEN_SHOVEL.get());
		createMoltenHoe(DeferredRegistryHandler.MOLTEN_HOE.get());
		createMoltenHelmet(DeferredRegistryHandler.MOLTEN_HELMET.get());
		createMoltenChestplate(DeferredRegistryHandler.MOLTEN_CHESTPLATE.get());
		createMoltenLeggings(DeferredRegistryHandler.MOLTEN_LEGGINGS.get());
		createMoltenBoots(DeferredRegistryHandler.MOLTEN_BOOTS.get());
		createMoltenIngot(DeferredRegistryHandler.MOLTEN_INGOT.get(), DeferredRegistryHandler.MOLTEN_BLOCK_ITEM.get());
		createMoltenBlock(DeferredRegistryHandler.MOLTEN_BLOCK_ITEM.get());
	}

	private void createVentusItems() {
		createVentusSword(DeferredRegistryHandler.VENTUS_SWORD.get());
		createVentusPickaxe(DeferredRegistryHandler.VENTUS_PICKAXE.get());
		createVentusAxe(DeferredRegistryHandler.VENTUS_AXE.get());
		createVentusShovel(DeferredRegistryHandler.VENTUS_SHOVEL.get());
		createVentusHoe(DeferredRegistryHandler.VENTUS_HOE.get());
		createVentusHelmet(DeferredRegistryHandler.VENTUS_HELMET.get());
		createVentusChestplate(DeferredRegistryHandler.VENTUS_CHESTPLATE.get());
		createVentusLeggings(DeferredRegistryHandler.VENTUS_LEGGINGS.get());
		createVentusBoots(DeferredRegistryHandler.VENTUS_BOOTS.get());
		createVentusStaffCore(DeferredRegistryHandler.VENTUS_STAFF_CORE.get());
		createVentusStaff(DeferredRegistryHandler.VENTUS_STAFF.get(), DeferredRegistryHandler.VENTUS_STAFF_CORE.get());
	}

	private void createTeslaItems() {
		createTeslaSword(DeferredRegistryHandler.TESLA_SWORD.get());
		createTeslaPickaxe(DeferredRegistryHandler.TESLA_PICKAXE.get());
		createTeslaAxe(DeferredRegistryHandler.TESLA_AXE.get());
		createTeslaShovel(DeferredRegistryHandler.TESLA_SHOVEL.get());
		createTeslaHoe(DeferredRegistryHandler.TESLA_HOE.get());
		createTeslaHelmet(DeferredRegistryHandler.TESLA_HELMET.get());
		createTeslaChestplate(DeferredRegistryHandler.TESLA_CHESTPLATE.get());
		createTeslaLeggings(DeferredRegistryHandler.TESLA_LEGGINGS.get());
		createTeslaBoots(DeferredRegistryHandler.TESLA_BOOTS.get());
		createTeslaIngot(DeferredRegistryHandler.TESLA_INGOT.get(), DeferredRegistryHandler.TESLA_BLOCK_ITEM.get());
		createTeslaBlock(DeferredRegistryHandler.TESLA_BLOCK_ITEM.get());
		createTeslaSynthesizer(DeferredRegistryHandler.TESLA_SYNTHESIZER_ITEM.get());
		teslaSynthesizing(Items.STONE, Items.LAPIS_LAZULI, DeferredRegistryHandler.CONDUCTIVE_ALLOY.get(), 24000,
				DeferredRegistryHandler.ELECTRIC_INGOT.get());
	}

	private void createVanillaTieredItems() {
		createWoodenArrow(DeferredRegistryHandler.WOODEN_ARROW.get());
		createStoneArrow(DeferredRegistryHandler.STONE_ARROW.get());
		createGoldenArrow(DeferredRegistryHandler.GOLDEN_ARROW.get());
		createIronArrow(DeferredRegistryHandler.IRON_ARROW.get());
		createDiamondArrow(DeferredRegistryHandler.DIAMOND_ARROW.get());
		createNetheriteArrow(DeferredRegistryHandler.NETHERITE_ARROW.get());
		createWoodenMusketBall(DeferredRegistryHandler.WOODEN_MUSKET_BALL.get());
		createStoneMusketBall(DeferredRegistryHandler.STONE_MUSKET_BALL.get());
		createGoldenMusketBall(DeferredRegistryHandler.GOLDEN_MUSKET_BALL.get());
		createIronMusketBall(DeferredRegistryHandler.IRON_MUSKET_BALL.get());
		createDiamondMusketBall(DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get());
		createNetheriteMusketBall(DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get());
		createWoodenGauntlet(DeferredRegistryHandler.WOODEN_GAUNTLET.get());
		createStoneGauntlet(DeferredRegistryHandler.STONE_GAUNTLET.get());
		createGoldenGauntlet(DeferredRegistryHandler.GOLDEN_GAUNTLET.get());
		createIronGauntlet(DeferredRegistryHandler.IRON_GAUNTLET.get());
		createDiamondGauntlet(DeferredRegistryHandler.DIAMOND_GAUNTLET.get());
		netheriteSmithing(DeferredRegistryHandler.DIAMOND_GAUNTLET.get(), DeferredRegistryHandler.NETHERITE_GAUNTLET.get());
		createWoodenPike(DeferredRegistryHandler.WOODEN_PIKE.get(), DeferredRegistryHandler.WOODEN_PIKE_HEAD.get());
		createStonePike(DeferredRegistryHandler.STONE_PIKE.get(), DeferredRegistryHandler.STONE_PIKE_HEAD.get());
		createGoldenPike(DeferredRegistryHandler.GOLDEN_PIKE.get(), DeferredRegistryHandler.GOLDEN_PIKE_HEAD.get());
		createIronPike(DeferredRegistryHandler.IRON_PIKE.get(), DeferredRegistryHandler.IRON_PIKE_HEAD.get());
		createDiamondPike(DeferredRegistryHandler.DIAMOND_PIKE.get(), DeferredRegistryHandler.DIAMOND_PIKE_HEAD.get());
		netheriteSmithing(DeferredRegistryHandler.DIAMOND_PIKE.get(), DeferredRegistryHandler.NETHERITE_PIKE.get());
		createWoodenPikeHead(DeferredRegistryHandler.WOODEN_PIKE_HEAD.get());
		createStonePikeHead(DeferredRegistryHandler.STONE_PIKE_HEAD.get());
		createGoldenPikeHead(DeferredRegistryHandler.GOLDEN_PIKE_HEAD.get());
		createIronPikeHead(DeferredRegistryHandler.IRON_PIKE_HEAD.get());
		createDiamondPikeHead(DeferredRegistryHandler.DIAMOND_PIKE_HEAD.get());
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

		createSmallPartsTable(DeferredRegistryHandler.SMALL_PARTS_TABLE_ITEM.get());

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
		createStoneShard(DeferredRegistryHandler.STONE_SHARD.get());
		createObsidianShard(DeferredRegistryHandler.OBSIDIAN_SHARD.get());
		createDiamondShard(DeferredRegistryHandler.DIAMOND_SHARD.get());
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
				.define('a', ForgeItemTagGroups.COPPER_INGOTS)
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

	private static void createCobaltArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(arrowItem, 4)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createArrow(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createCobaltSword(ItemLike swordItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(swordItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createSword(builder, ForgeItemTagGroups.COBALT_INGOTS, Items.STICK);
	}

	private static void createCobaltPickaxe(ItemLike pickaxeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pickaxeItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createPickaxe(builder, ForgeItemTagGroups.COBALT_INGOTS, Items.STICK);
	}

	private static void createCobaltAxe(ItemLike axeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(axeItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createAxe(builder, ForgeItemTagGroups.COBALT_INGOTS, Items.STICK);
	}

	private static void createCobaltShovel(ItemLike shovelItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(shovelItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createShovel(builder, ForgeItemTagGroups.COBALT_INGOTS, Items.STICK);
	}

	private static void createCobaltHoe(ItemLike hoeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(hoeItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createHoe(builder, ForgeItemTagGroups.COBALT_INGOTS, Items.STICK);
	}

	private static void createCobaltHelmet(ItemLike helmetItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(helmetItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createHelmet(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createCobaltChestplate(ItemLike chestplateItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(chestplateItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createChestplate(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createCobaltLeggings(ItemLike leggingsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(leggingsItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createLeggings(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createCobaltBoots(ItemLike bootsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(bootsItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createBoots(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createCobaltGauntlet(ItemLike gauntletItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(gauntletItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createGauntlet(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createCobaltPike(ItemLike pikeItem, ItemLike pikeHead) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createPike(builder, ForgeItemTagGroups.COBALT_INGOTS, pikeHead);
	}

	private static void createCobaltPikeHead(ItemLike pikeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createPikeHead(builder, ForgeItemTagGroups.COBALT_INGOTS, ForgeItemTagGroups.COBALT_NUGGETS);
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

	private static void createCobaltMusketBall(ItemLike musketBallItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(musketBallItem, 8)
				.group("cobalt")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COBALT_INGOTS));
		createMusketBall(builder, ForgeItemTagGroups.COBALT_INGOTS);
	}

	private static void createCopperArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(arrowItem, 4)
				.group("copper")
				.unlockedBy("cobalt_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createArrow(builder, ForgeItemTagGroups.COPPER_INGOTS);
	}

	private static void createCopperSword(ItemLike swordItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(swordItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createSword(builder, ForgeItemTagGroups.COPPER_INGOTS, Items.STICK);
	}

	private static void createCopperPickaxe(ItemLike pickaxeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pickaxeItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createPickaxe(builder, ForgeItemTagGroups.COPPER_INGOTS, Items.STICK);
	}

	private static void createCopperAxe(ItemLike axeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(axeItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createAxe(builder, ForgeItemTagGroups.COPPER_INGOTS, Items.STICK);
	}

	private static void createCopperShovel(ItemLike shovelItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(shovelItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createShovel(builder, ForgeItemTagGroups.COPPER_INGOTS, Items.STICK);
	}

	private static void createCopperHoe(ItemLike hoeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(hoeItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createHoe(builder, ForgeItemTagGroups.COPPER_INGOTS, Items.STICK);
	}

	private static void createCopperHelmet(ItemLike helmetItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(helmetItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createHelmet(builder, ForgeItemTagGroups.COPPER_INGOTS);
	}

	private static void createCopperChestplate(ItemLike chestplateItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(chestplateItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createChestplate(builder, ForgeItemTagGroups.COPPER_INGOTS);
	}

	private static void createCopperLeggings(ItemLike leggingsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(leggingsItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createLeggings(builder, ForgeItemTagGroups.COPPER_INGOTS);
	}

	private static void createCopperBoots(ItemLike bootsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(bootsItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createBoots(builder, ForgeItemTagGroups.COPPER_INGOTS);
	}

	private static void createCopperGauntlet(ItemLike gauntletItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(gauntletItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createGauntlet(builder, ForgeItemTagGroups.COPPER_INGOTS);
	}

	private static void createCopperPike(ItemLike pikeItem, ItemLike pikeHead) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createPike(builder, ForgeItemTagGroups.COPPER_INGOTS, pikeHead);
	}

	private static void createCopperPikeHead(ItemLike pikeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createPikeHead(builder, ForgeItemTagGroups.COPPER_INGOTS, ForgeItemTagGroups.COPPER_NUGGETS);
	}

	private static void createCopperIngot(ItemLike ingotItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(ingotItem)
				.group("copper")
				.unlockedBy("copper_nugget", has(ForgeItemTagGroups.COPPER_NUGGETS));
		create3x3Object(builder, ForgeItemTagGroups.COPPER_NUGGETS);
	}

	private static void createCopperNugget(ItemLike nuggetItem) {
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(nuggetItem, 9)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createNuggetFromIngot(builder, ForgeItemTagGroups.COPPER_INGOTS);
	}

	private static void createCopperMusketBall(ItemLike musketBallItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(musketBallItem, 8)
				.group("copper")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS));
		createMusketBall(builder, ForgeItemTagGroups.COPPER_INGOTS);
	}

	private static void createMoltenSword(ItemLike swordItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(swordItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createSword(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createMoltenPickaxe(ItemLike pickaxeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pickaxeItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createPickaxe(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createMoltenAxe(ItemLike axeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(axeItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createAxe(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createMoltenShovel(ItemLike shovelItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(shovelItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createShovel(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createMoltenHoe(ItemLike hoeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(hoeItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createHoe(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createMoltenHelmet(ItemLike helmetItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(helmetItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createHelmet(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS);
	}

	private static void createMoltenChestplate(ItemLike chestplateItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(chestplateItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createChestplate(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS);
	}

	private static void createMoltenLeggings(ItemLike leggingsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(leggingsItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createLeggings(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS);
	}

	private static void createMoltenBoots(ItemLike bootsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(bootsItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(DeferredRegistryHandler.MOLTEN_INGOT.get()));
		createBoots(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS);
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

	private static void createMoltenBlock(ItemLike blockItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(blockItem)
				.group("molten")
				.unlockedBy("molten_ingot", has(ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS));
		create3x3Object(builder, ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS);
	}

	private static void createVentusSword(ItemLike swordItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(swordItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createSword(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createVentusPickaxe(ItemLike pickaxeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pickaxeItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createPickaxe(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createVentusAxe(ItemLike axeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(axeItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createAxe(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createVentusShovel(ItemLike shovelItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(shovelItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createShovel(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createVentusHoe(ItemLike hoeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(hoeItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createHoe(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createVentusHelmet(ItemLike helmetItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(helmetItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createHelmet(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS);
	}

	private static void createVentusChestplate(ItemLike chestplateItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(chestplateItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createChestplate(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS);
	}

	private static void createVentusLeggings(ItemLike leggingsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(leggingsItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createLeggings(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS);
	}

	private static void createVentusBoots(ItemLike bootsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(bootsItem)
				.group("ventus")
				.unlockedBy("ventus_shard", has(DeferredRegistryHandler.VENTUS_SHARD.get()));
		createBoots(builder, ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS);
	}

	private static void createVentusStaffCore(ItemLike coreItem) {
		ShapedRecipeBuilder.shaped(coreItem)
				.define('a', ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS)
				.define('b', Tags.Items.GEMS_DIAMOND)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("ventus")
				.unlockedBy("ventus_shard", has(ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS))
				.save(finishedRecipeConsumer);
	}

	private static void createVentusStaff(ItemLike staffItem, ItemLike coreItem) {
		ShapedRecipeBuilder.shaped(staffItem)
				.define('a', coreItem)
				.define('b', DeferredRegistryHandler.OBSIDIAN_ROD.get())
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" b ")
				.group("ventus")
				.unlockedBy("ventus_staff_core", has(DeferredRegistryHandler.VENTUS_STAFF_CORE.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createTeslaSword(ItemLike swordItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(swordItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createSword(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createTeslaPickaxe(ItemLike pickaxeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pickaxeItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createPickaxe(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createTeslaAxe(ItemLike axeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(axeItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createAxe(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createTeslaShovel(ItemLike shovelItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(shovelItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createShovel(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createTeslaHoe(ItemLike hoeItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(hoeItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createHoe(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS, DeferredRegistryHandler.OBSIDIAN_ROD.get());
	}

	private static void createTeslaHelmet(ItemLike helmetItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(helmetItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createHelmet(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS);
	}

	private static void createTeslaChestplate(ItemLike chestplateItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(chestplateItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createChestplate(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS);
	}

	private static void createTeslaLeggings(ItemLike leggingsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(leggingsItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createLeggings(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS);
	}

	private static void createTeslaBoots(ItemLike bootsItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(bootsItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		createBoots(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS);
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

	private static void createTeslaBlock(ItemLike blockItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(blockItem)
				.group("tesla")
				.unlockedBy("tesla_ingot", has(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS));
		create3x3Object(builder, ImmersiveWeaponsItemTagGroups.TESLA_INGOTS);
	}

	private static void createTeslaSynthesizer(ItemLike synthesizerItem) {
		ShapedRecipeBuilder.shaped(synthesizerItem)
				.define('a', Items.NETHERITE_BLOCK)
				.define('b', DeferredRegistryHandler.MOLTEN_BLOCK_ITEM.get())
				.define('c', ForgeItemTagGroups.COPPER_INGOTS)
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

	private static void createWoodenArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(arrowItem, 4)
				.group("wood")
				.unlockedBy("wooden_shard", has(ImmersiveWeaponsItemTagGroups.WOODEN_SHARDS));
		createArrow(builder, ImmersiveWeaponsItemTagGroups.WOODEN_SHARDS);
	}

	private static void createStoneArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(arrowItem, 4)
				.group("stone")
				.unlockedBy("stone_shard", has(ImmersiveWeaponsItemTagGroups.STONE_SHARDS));
		createArrow(builder, ImmersiveWeaponsItemTagGroups.STONE_SHARDS);
	}

	private static void createGoldenArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(arrowItem, 4)
				.group("gold")
				.unlockedBy("gold_ingot", has(Tags.Items.INGOTS_GOLD));
		createArrow(builder, Tags.Items.INGOTS_GOLD);
	}

	private static void createIronArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(arrowItem, 4)
				.group("iron")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON));
		createArrow(builder, Tags.Items.INGOTS_IRON);
	}

	private static void createDiamondArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(arrowItem, 4)
				.group("diamond")
				.unlockedBy("diamond_shard", has(ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS));
		createArrow(builder, ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS);
	}

	private static void createNetheriteArrow(ItemLike arrowItem) {
		ShapedRecipeBuilder.shaped(arrowItem, 8)
				.define('a', DeferredRegistryHandler.DIAMOND_ARROW.get())
				.define('b', Tags.Items.INGOTS_NETHERITE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("netherite")
				.unlockedBy("netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
				.save(finishedRecipeConsumer);
	}

	private static void createWoodenMusketBall(ItemLike musketBallItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(musketBallItem, 8)
				.group("wood")
				.unlockedBy("wooden_shard", has(ImmersiveWeaponsItemTagGroups.WOODEN_SHARDS));
		createMusketBall(builder, ImmersiveWeaponsItemTagGroups.WOODEN_SHARDS);
	}

	private static void createStoneMusketBall(ItemLike musketBallItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(musketBallItem, 8)
				.group("stone")
				.unlockedBy("stone_shard", has(ImmersiveWeaponsItemTagGroups.STONE_SHARDS));
		createMusketBall(builder, ImmersiveWeaponsItemTagGroups.STONE_SHARDS);
	}

	private static void createGoldenMusketBall(ItemLike musketBallItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(musketBallItem, 8)
				.group("gold")
				.unlockedBy("gold_ingot", has(Tags.Items.INGOTS_GOLD));
		createMusketBall(builder, Tags.Items.INGOTS_GOLD);
	}

	private static void createIronMusketBall(ItemLike musketBallItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(musketBallItem, 8)
				.group("iron")
				.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON));
		createMusketBall(builder, Tags.Items.INGOTS_IRON);
	}

	private static void createDiamondMusketBall(ItemLike musketBallItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(musketBallItem, 8)
				.group("diamond")
				.unlockedBy("diamond_shard", has(ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS));
		createMusketBall(builder, ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS);
	}

	private static void createNetheriteMusketBall(ItemLike musketBallItem) {
		ShapedRecipeBuilder.shaped(musketBallItem, 8)
				.define('a', DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get())
				.define('b', Tags.Items.INGOTS_NETHERITE)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("netherite")
				.unlockedBy("netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
				.save(finishedRecipeConsumer);
	}

	private static void createWoodenGauntlet(ItemLike gauntletItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(gauntletItem)
				.group("wood")
				.unlockedBy("planks", has(ItemTags.PLANKS));
		createGauntlet(builder, ItemTags.PLANKS);
	}

	private static void createStoneGauntlet(ItemLike gauntletItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(gauntletItem)
				.group("stone")
				.unlockedBy("stone", has(ItemTags.STONE_TOOL_MATERIALS));
		createGauntlet(builder, ItemTags.STONE_TOOL_MATERIALS);
	}

	private static void createGoldenGauntlet(ItemLike gauntletItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(gauntletItem)
				.group("gold")
				.unlockedBy("gold", has(Tags.Items.INGOTS_GOLD));
		createGauntlet(builder, Tags.Items.INGOTS_GOLD);
	}

	private static void createIronGauntlet(ItemLike gauntletItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(gauntletItem)
				.group("iron")
				.unlockedBy("iron", has(Tags.Items.INGOTS_IRON));
		createGauntlet(builder, Tags.Items.INGOTS_IRON);
	}

	private static void createDiamondGauntlet(ItemLike gauntletItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(gauntletItem)
				.group("diamond")
				.unlockedBy("diamond", has(Tags.Items.GEMS_DIAMOND));
		createGauntlet(builder, Tags.Items.GEMS_DIAMOND);
	}

	private static void createWoodenPike(ItemLike pikeItem, ItemLike pikeHead) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("wood")
				.unlockedBy("planks", has(ItemTags.PLANKS));
		createPike(builder, ItemTags.PLANKS, pikeHead);
	}

	private static void createStonePike(ItemLike pikeItem, ItemLike pikeHead) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("stone")
				.unlockedBy("stone", has(ItemTags.STONE_TOOL_MATERIALS));
		createPike(builder, ItemTags.STONE_TOOL_MATERIALS, pikeHead);
	}

	private static void createGoldenPike(ItemLike pikeItem, ItemLike pikeHead) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("gold")
				.unlockedBy("gold", has(Tags.Items.INGOTS_GOLD));
		createPike(builder, Tags.Items.INGOTS_GOLD, pikeHead);
	}

	private static void createIronPike(ItemLike pikeItem, ItemLike pikeHead) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("iron")
				.unlockedBy("iron", has(Tags.Items.INGOTS_IRON));
		createPike(builder, Tags.Items.INGOTS_IRON, pikeHead);
	}

	private static void createDiamondPike(ItemLike pikeItem, ItemLike pikeHead) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeItem)
				.group("diamond")
				.unlockedBy("diamond", has(Tags.Items.GEMS_DIAMOND));
		createPike(builder, Tags.Items.GEMS_DIAMOND, pikeHead);
	}

	private static void createWoodenPikeHead(ItemLike pikeHeadItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeHeadItem)
				.group("wood")
				.unlockedBy("planks", has(ItemTags.PLANKS));
		createPikeHead(builder, ItemTags.PLANKS, ImmersiveWeaponsItemTagGroups.WOODEN_SHARDS);
	}

	private static void createStonePikeHead(ItemLike pikeHeadItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeHeadItem)
				.group("stone")
				.unlockedBy("stone", has(ItemTags.STONE_TOOL_MATERIALS));
		createPikeHead(builder, ItemTags.STONE_TOOL_MATERIALS, ImmersiveWeaponsItemTagGroups.STONE_SHARDS);
	}

	private static void createGoldenPikeHead(ItemLike pikeHeadItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeHeadItem)
				.group("gold")
				.unlockedBy("gold", has(Tags.Items.INGOTS_GOLD));
		createPikeHead(builder, Tags.Items.INGOTS_GOLD, Tags.Items.NUGGETS_GOLD);
	}

	private static void createIronPikeHead(ItemLike pikeHeadItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeHeadItem)
				.group("iron")
				.unlockedBy("iron", has(Tags.Items.INGOTS_IRON));
		createPikeHead(builder, Tags.Items.INGOTS_IRON, Tags.Items.NUGGETS_IRON);
	}

	private static void createDiamondPikeHead(ItemLike pikeHeadItem) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pikeHeadItem)
				.group("diamond")
				.unlockedBy("diamond", has(Tags.Items.GEMS_DIAMOND));
		createPikeHead(builder, Tags.Items.GEMS_DIAMOND, ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS);
	}

	private static void createSmallPartsTable(ItemLike smallPartsTableItem) {
		ShapedRecipeBuilder.shaped(smallPartsTableItem)
				.define('a', Tags.Items.INGOTS_IRON)
				.define('b', ItemTags.PLANKS)
				.define('c', ForgeItemTagGroups.COPPER_INGOTS)
				.pattern("aca")
				.pattern("aba")
				.unlockedBy("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS))
				.save(finishedRecipeConsumer);
	}

	private static void createStoneShard(ItemLike shardItem) {
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(shardItem, 3)
				.unlockedBy("cobblestone", has(Items.COBBLESTONE));
		createShard(builder, Items.COBBLESTONE);
	}

	private static void createObsidianShard(ItemLike shardItem) {
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(shardItem, 9)
				.unlockedBy("obsidian", has(Items.OBSIDIAN));
		createShard(builder, Items.OBSIDIAN);
	}

	private static void createDiamondShard(ItemLike shardItem) {
		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(shardItem, 4)
				.unlockedBy("diamond", has(Tags.Items.GEMS_DIAMOND));
		createShard(builder, Items.DIAMOND);
	}

	private static void createCloudMarbleBrickSlab(ItemLike slabItem, ItemLike material) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(slabItem, 6)
				.group("cloud_marble")
				.unlockedBy("cloud_marble_bricks", has(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get()));
		createSlab(builder, material);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), slabItem, 2)
				.group("cloud_marble")
				.unlockedBy("cloud_marble_bricks", has(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getConversionRecipeName(slabItem, material) + "_stonecutting");
	}

	private static void createCloudMarbleStairs(ItemLike stairsItem, ItemLike material) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(stairsItem, 4)
				.group("cloud_marble")
				.unlockedBy("cloud_marble_bricks", has(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get()));
		createStairs(builder, material);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), stairsItem)
				.group("cloud_marble")
				.unlockedBy("cloud_marble_bricks", has(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getConversionRecipeName(stairsItem, material) + "_stonecutting");
	}

	private static void createCloudMarbleBricks(ItemLike bricksItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(bricksItem, 4)
				.define('a', material)
				.pattern("aa ")
				.pattern("aa ")
				.group("cloud_marble")
				.unlockedBy("cloud_marble", has(DeferredRegistryHandler.CLOUD_MARBLE_ITEM.get()))
				.save(finishedRecipeConsumer);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), bricksItem)
				.group("cloud_marble")
				.unlockedBy("cloud_marble", has(DeferredRegistryHandler.CLOUD_MARBLE_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getConversionRecipeName(bricksItem, material) + "_stonecutting");
	}

	private static void createCloudMarblePillar(ItemLike bricksItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(bricksItem)
				.define('a', material)
				.pattern("a  ")
				.pattern("a  ")
				.group("cloud_marble")
				.unlockedBy("cloud_marble", has(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get()))
				.save(finishedRecipeConsumer);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), bricksItem)
				.group("cloud_marble")
				.unlockedBy("cloud_marble", has(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getConversionRecipeName(bricksItem, material) + "_stonecutting");
	}

	private static void createBurnedOakBoat(ItemLike boatItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(boatItem)
				.define('a', material)
				.pattern("a a")
				.pattern("aaa")
				.group("burned_oak")
				.unlockedBy("water", insideOf(Blocks.WATER))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakChestBoat(ItemLike chestBoatItem, ItemLike boatItem) {
		ShapelessRecipeBuilder.shapeless(chestBoatItem)
				.requires(boatItem)
				.requires(Items.CHEST)
				.group("burned_oak")
				.unlockedBy("water", insideOf(Blocks.WATER))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakButton(ItemLike buttonItem, ItemLike material) {
		ShapelessRecipeBuilder.shapeless(buttonItem)
				.requires(material)
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakDoor(ItemLike doorItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(doorItem, 3)
				.define('a', material)
				.pattern("aa ")
				.pattern("aa ")
				.pattern("aa ")
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakFence(ItemLike fenceItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(fenceItem, 3)
				.define('a', material)
				.define('b', Items.STICK)
				.pattern("aba")
				.pattern("aba")
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakFenceGate(ItemLike fenceGateItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(fenceGateItem)
				.define('a', material)
				.define('b', Items.STICK)
				.pattern("bab")
				.pattern("bab")
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakPlanks(ItemLike planksItem) {
		ShapelessRecipeBuilder.shapeless(planksItem, 4)
				.requires(ImmersiveWeaponsItemTagGroups.BURNED_OAK_LOGS)
				.group("burned_oak")
				.unlockedBy("burned_oak_log", has(ImmersiveWeaponsItemTagGroups.BURNED_OAK_LOGS))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakPressurePlate(ItemLike pressurePlateItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(pressurePlateItem)
				.define('a', material)
				.pattern("aa ")
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakSign(ItemLike signItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(signItem, 3)
				.define('a', material)
				.define('b', Items.STICK)
				.pattern("aaa")
				.pattern("aaa")
				.pattern(" b ")
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakSlab(ItemLike slabItem, ItemLike material) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(slabItem, 6)
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()));
		createSlab(builder, material);
	}

	private static void createBurnedOakStairs(ItemLike stairsItem, ItemLike material) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(stairsItem, 4)
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()));
		createStairs(builder, material);
	}

	private static void createBurnedOakTrapdoor(ItemLike trapdoorItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(trapdoorItem, 2)
				.define('a', material)
				.pattern("aaa")
				.pattern("aaa")
				.group("burned_oak")
				.unlockedBy("burned_oak_planks", has(DeferredRegistryHandler.BURNED_OAK_PLANKS_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createBurnedOakWood(ItemLike woodItem, ItemLike material) {
		ShapedRecipeBuilder.shaped(woodItem, 3)
				.define('a', material)
				.pattern("aa ")
				.pattern("aa ")
				.group("burned_oak")
				.unlockedBy("burned_oak_log", has(ImmersiveWeaponsItemTagGroups.BURNED_OAK_LOGS))
				.save(finishedRecipeConsumer);
	}

	private static void createAmericanFlag(ItemLike flagItem, ItemLike flagPole) {
		ShapedRecipeBuilder.shaped(flagItem)
				.define('a', flagPole)
				.define('b', Items.BLUE_WOOL)
				.define('c', Items.RED_WOOL)
				.define('d', Items.WHITE_WOOL)
				.pattern("bcc")
				.pattern("bdd")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(DeferredRegistryHandler.FLAG_POLE_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createBritishFlag(ItemLike flagItem, ItemLike flagPole) {
		ShapedRecipeBuilder.shaped(flagItem)
				.define('a', flagPole)
				.define('b', Items.BLUE_WOOL)
				.define('c', Items.RED_WOOL)
				.define('d', Items.WHITE_WOOL)
				.pattern("cbc")
				.pattern("dcd")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(DeferredRegistryHandler.FLAG_POLE_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createCanadianFlag(ItemLike flagItem, ItemLike flagPole) {
		ShapedRecipeBuilder.shaped(flagItem)
				.define('a', flagPole)
				.define('b', Items.RED_WOOL)
				.define('c', Items.WHITE_WOOL)
				.pattern("bcb")
				.pattern("bbb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(DeferredRegistryHandler.FLAG_POLE_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createGadsdenFlag(ItemLike flagItem, ItemLike flagPole) {
		ShapedRecipeBuilder.shaped(flagItem)
				.define('a', flagPole)
				.define('b', Items.YELLOW_WOOL)
				.define('c', Items.BLACK_WOOL)
				.define('d', Items.GREEN_WOOL)
				.pattern("bcb")
				.pattern("bdb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(DeferredRegistryHandler.FLAG_POLE_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createMexicanFlag(ItemLike flagItem, ItemLike flagPole) {
		ShapedRecipeBuilder.shaped(flagItem)
				.define('a', flagPole)
				.define('b', Items.GREEN_WOOL)
				.define('c', Items.WHITE_WOOL)
				.define('d', Items.BROWN_WOOL)
				.define('e', Items.RED_WOOL)
				.pattern("bce")
				.pattern("bde")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(DeferredRegistryHandler.FLAG_POLE_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createTrollFlag(ItemLike flagItem, ItemLike flagPole) {
		ShapedRecipeBuilder.shaped(flagItem)
				.define('a', flagPole)
				.define('b', Items.WHITE_WOOL)
				.define('c', Items.BLACK_WOOL)
				.pattern("bcb")
				.pattern("cbc")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(DeferredRegistryHandler.FLAG_POLE_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createImmersiveWeaponsFlag(ItemLike flagItem, ItemLike flagPole) {
		ShapedRecipeBuilder.shaped(flagItem)
				.define('a', flagPole)
				.define('b', Items.LIGHT_BLUE_WOOL)
				.define('c', Items.GOLDEN_SWORD)
				.define('d', Items.YELLOW_WOOL)
				.pattern("bcb")
				.pattern("bdb")
				.pattern("a  ")
				.group("flag")
				.unlockedBy("flag_pole", has(DeferredRegistryHandler.FLAG_POLE_ITEM.get()))
				.save(finishedRecipeConsumer);
	}

	private static void createFlagPole(ItemLike flagPoleItem) {
		ShapedRecipeBuilder.shaped(flagPoleItem, 3)
				.define('a', Tags.Items.INGOTS_IRON)
				.pattern(" a ")
				.pattern(" a ")
				.pattern(" a ")
				.group("flag")
				.unlockedBy("iron", has(DeferredRegistryHandler.FLAG_POLE_ITEM.get()))
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

	private static void createBulletproofGlass(ItemLike glassItem) {
		ShapedRecipeBuilder.shaped(glassItem, 8)
				.define('a', Items.GLASS)
				.define('b', Tags.Items.INGOTS_IRON)
				.pattern("aaa")
				.pattern("aba")
				.pattern("aaa")
				.group("bulletproof_glass")
				.unlockedBy("glass", has(Items.GLASS))
				.save(finishedRecipeConsumer);
	}

	private static void createHardenedMudSlab(ItemLike slabItem, ItemLike material) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(slabItem, 6)
				.group("mud")
				.unlockedBy("hardened_mud", has(DeferredRegistryHandler.HARDENED_MUD_ITEM.get()));
		createSlab(builder, material);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), slabItem, 2)
				.group("mud")
				.unlockedBy("hardened_mud", has(DeferredRegistryHandler.HARDENED_MUD_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getConversionRecipeName(slabItem, material) + "_stonecutting");
	}

	private static void createHardenedMudStairs(ItemLike stairsItem, ItemLike material) {
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(stairsItem, 4)
				.group("mud")
				.unlockedBy("hardened_mud", has(DeferredRegistryHandler.HARDENED_MUD_ITEM.get()));
		createStairs(builder, material);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), stairsItem)
				.group("mud")
				.unlockedBy("hardened_mud", has(DeferredRegistryHandler.HARDENED_MUD_ITEM.get()))
				.save(finishedRecipeConsumer, ImmersiveWeapons.MOD_ID + ":" + getConversionRecipeName(stairsItem, material) + "_stonecutting");
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

	private static void createArrow(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.define('b', Items.STICK)
				.define('c', Items.FEATHER)
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" c ")
				.save(finishedRecipeConsumer);
	}

	private static void createSword(ShapedRecipeBuilder builder, TagKey<Item> material, Item stick) {
		builder.define('a', material)
				.define('b', stick)
				.pattern(" a ")
				.pattern(" a ")
				.pattern(" b ")
				.save(finishedRecipeConsumer);
	}

	private static void createPickaxe(ShapedRecipeBuilder builder, TagKey<Item> material, Item stick) {
		builder.define('a', material)
				.define('b', stick)
				.pattern("aaa")
				.pattern(" b ")
				.pattern(" b ")
				.save(finishedRecipeConsumer);
	}

	private static void createAxe(ShapedRecipeBuilder builder, TagKey<Item> material, Item stick) {
		builder.define('a', material)
				.define('b', stick)
				.pattern("aa ")
				.pattern("ab ")
				.pattern(" b ")
				.save(finishedRecipeConsumer);
	}

	private static void createShovel(ShapedRecipeBuilder builder, TagKey<Item> material, Item stick) {
		builder.define('a', material)
				.define('b', stick)
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" b ")
				.save(finishedRecipeConsumer);
	}

	private static void createHoe(ShapedRecipeBuilder builder, TagKey<Item> material, Item stick) {
		builder.define('a', material)
				.define('b', stick)
				.pattern("aa ")
				.pattern(" b ")
				.pattern(" b ")
				.save(finishedRecipeConsumer);
	}

	private static void createHelmet(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.pattern("aaa")
				.pattern("a a")
				.save(finishedRecipeConsumer);
	}

	private static void createChestplate(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.pattern("a a")
				.pattern("aaa")
				.pattern("aaa")
				.save(finishedRecipeConsumer);
	}

	private static void createLeggings(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.pattern("aaa")
				.pattern("a a")
				.pattern("a a")
				.save(finishedRecipeConsumer);
	}

	private static void createBoots(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.pattern("a a")
				.pattern("a a")
				.save(finishedRecipeConsumer);
	}

	private static void createGauntlet(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.define('b', DeferredRegistryHandler.GAUNTLET_SCAFFOLDING.get())
				.pattern("aaa")
				.pattern("aba")
				.save(finishedRecipeConsumer);
	}

	private static void createPike(ShapedRecipeBuilder builder, TagKey<Item> material, ItemLike pikeHead) {
		builder.define('a', pikeHead)
				.define('b', material)
				.define('c', DeferredRegistryHandler.WOODEN_TOOL_ROD.get())
				.pattern(" a ")
				.pattern(" b ")
				.pattern(" c ")
				.save(finishedRecipeConsumer);
	}

	private static void createPikeHead(ShapedRecipeBuilder builder, TagKey<Item> material, TagKey<Item> material1) {
		builder.define('a', material)
				.define('b', material1)
				.pattern(" a ")
				.pattern(" b ")
				.save(finishedRecipeConsumer);
	}

	private static void createMusketBall(ShapedRecipeBuilder builder, TagKey<Item> material) {
		builder.define('a', material)
				.define('b', Items.GUNPOWDER)
				.pattern(" a ")
				.pattern("aba")
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
		builder
				.define('a', material)
				.pattern("a  ")
				.pattern("aa ")
				.pattern("aaa")
				.save(finishedRecipeConsumer);
	}

	private static void createSlab(ShapedRecipeBuilder builder, ItemLike material) {
		builder
				.define('a', material)
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

	private static void createSmeltingRecipe(List<ItemLike> pIngredients,
	                                         ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
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
	                               ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
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
				.unlocks("copper_ingot", has(ForgeItemTagGroups.COPPER_INGOTS))
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

	protected static @NotNull String getItemName(ItemLike pItemLike) {
		return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(pItemLike.asItem())).getPath();
	}

	protected static String getTagName(TagKey<Item> tagKey) {
		return tagKey.location().getPath().replace('/', '_');
	}

	protected static String getHasName(ItemLike pItemLike) {
		return "has_" + getItemName(pItemLike);
	}
}