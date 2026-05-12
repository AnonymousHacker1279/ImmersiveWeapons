package tech.anonymoushacker1279.immersiveweapons.data.trades;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.List;
import java.util.Optional;

public class TradeGenerator {

	public static final ResourceKey<VillagerTrade> SKYGAZER_1_EMERALD_BANDAGE = create("skygazer/1/emerald_bandage");
	public static final ResourceKey<VillagerTrade> SKYGAZER_1_EMERALD_FIRST_AID_KIT = create("skygazer/1/emerald_first_aid_kit");
	public static final ResourceKey<VillagerTrade> SKYGAZER_1_EMERALD_PAINKILLERS = create("skygazer/1/emerald_painkillers");
	public static final ResourceKey<VillagerTrade> SKYGAZER_1_EMERALD_DIAMOND_MUSKET_BALL = create("skygazer/1/emerald_diamond_musket_ball");
	public static final ResourceKey<VillagerTrade> SKYGAZER_1_EMERALD_DIAMOND_ARROW = create("skygazer/1/emerald_diamond_arrow");
	public static final ResourceKey<VillagerTrade> SKYGAZER_1_EMERALD_AZUL_LOCATOR = create("skygazer/1/emerald_azul_locator");
	public static final ResourceKey<VillagerTrade> SKYGAZER_1_EMERALD_SPYGLASS = create("skygazer/1/emerald_spyglass");

	public static final ResourceKey<VillagerTrade> SKYGAZER_2_EMERALD_STARSTORM_CRYSTAL = create("skygazer/2/emerald_starstorm_crystal");
	public static final ResourceKey<VillagerTrade> SKYGAZER_2_EMERALD_ASTRAL_CRYSTAL = create("skygazer/2/emerald_astral_crystal");
	public static final ResourceKey<VillagerTrade> SKYGAZER_2_EMERALD_METEOR_STAFF = create("skygazer/2/emerald_meteor_staff");
	public static final ResourceKey<VillagerTrade> SKYGAZER_2_EMERALD_CURSED_SIGHT_STAFF = create("skygazer/2/emerald_cursed_sight_staff");
	public static final ResourceKey<VillagerTrade> SKYGAZER_2_EMERALD_NIGHT_VISION_GOGGLES = create("skygazer/2/emerald_night_vision_goggles");

	public static final ResourceKey<VillagerTrade> SKYGAZER_3_LECTERN_CELESTIAL_ALTAR = create("skygazer/3/lectern_celestial_altar");

	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_1_EMERALD_IRON_RING = create("skeleton_merchant/1/emerald_iron_ring");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_1_EMERALD_SATCHEL = create("skeleton_merchant/1/emerald_satchel");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_1_EMERALD_POWDER_HORN = create("skeleton_merchant/1/emerald_powder_horn");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_1_EMERALD_AGILITY_BRACELET = create("skeleton_merchant/1/emerald_agility_bracelet");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_1_EMERALD_BLOODY_SACRIFICE = create("skeleton_merchant/1/emerald_bloody_sacrifice");

	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_2_EMERALD_BLOATED_HEART = create("skeleton_merchant/2/emerald_bloated_heart");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_2_EMERALD_DEADEYE_PENDANT = create("skeleton_merchant/2/emerald_deadeye_pendant");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_2_EMERALD_MELEE_MASTERS_MOLTEN_GLOVE = create("skeleton_merchant/2/emerald_melee_masters_molten_glove");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_2_EMERALD_NETHERITE_SHIELD = create("skeleton_merchant/2/emerald_netherite_shield");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_2_EMERALD_BERSERKERS_AMULET = create("skeleton_merchant/2/emerald_berserkers_amulet");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_2_EMERALD_AMETHYST_RING = create("skeleton_merchant/2/emerald_amethyst_ring");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_2_EMERALD_EMERALD_RING = create("skeleton_merchant/2/emerald_emerald_ring");
	public static final ResourceKey<VillagerTrade> SKELETON_MERCHANT_2_EMERALD_TOTEM_OF_UNDYING = create("skeleton_merchant/2/emerald_totem_of_undying");

	public static void bootstrap(BootstrapContext<VillagerTrade> context) {
		generateSkygazerTrades(context);
		generateSkeletonMerchantTrades(context);
	}

	private static void generateSkygazerTrades(BootstrapContext<VillagerTrade> context) {
		VillagerTrades.register(context,
				SKYGAZER_1_EMERALD_BANDAGE,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 1),
						new ItemStackTemplate(ItemRegistry.BANDAGE.get(), 2),
						8,
						1,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_1_EMERALD_FIRST_AID_KIT,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 3),
						new ItemStackTemplate(ItemRegistry.FIRST_AID_KIT.get()),
						8,
						1,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_1_EMERALD_PAINKILLERS,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 2),
						new ItemStackTemplate(ItemRegistry.PAINKILLERS.get()),
						8,
						1,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_1_EMERALD_DIAMOND_MUSKET_BALL,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 2),
						new ItemStackTemplate(ItemRegistry.DIAMOND_MUSKET_BALL.get(), 8),
						4,
						2,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_1_EMERALD_DIAMOND_ARROW,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 2),
						new ItemStackTemplate(ItemRegistry.DIAMOND_ARROW.get(), 8),
						4,
						2,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_1_EMERALD_AZUL_LOCATOR,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 4),
						new ItemStackTemplate(ItemRegistry.AZUL_LOCATOR.get()),
						1,
						3,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_1_EMERALD_SPYGLASS,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 3),
						new ItemStackTemplate(Items.SPYGLASS),
						1,
						3,
						0.05f,
						Optional.empty(),
						List.of()
				));

		VillagerTrades.register(context,
				SKYGAZER_2_EMERALD_STARSTORM_CRYSTAL,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 12),
						new ItemStackTemplate(BlockItemRegistry.STARSTORM_CRYSTAL_ITEM.get()),
						4,
						4,
						0.05f,
						Optional.empty(),
						List.of()
				));

		VillagerTrades.register(context,
				SKYGAZER_2_EMERALD_ASTRAL_CRYSTAL,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 12),
						new ItemStackTemplate(BlockItemRegistry.ASTRAL_CRYSTAL_ITEM.get()),
						4,
						4,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_2_EMERALD_METEOR_STAFF,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 32),
						new ItemStackTemplate(ItemRegistry.METEOR_STAFF.get()),
						1,
						10,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_2_EMERALD_CURSED_SIGHT_STAFF,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 32),
						new ItemStackTemplate(ItemRegistry.CURSED_SIGHT_STAFF.get()),
						1,
						10,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKYGAZER_2_EMERALD_NIGHT_VISION_GOGGLES,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 28),
						new ItemStackTemplate(ItemRegistry.NIGHT_VISION_GOGGLES.get()),
						1,
						10,
						0.05f,
						Optional.empty(),
						List.of()
				));

		VillagerTrades.register(context,
				SKYGAZER_3_LECTERN_CELESTIAL_ALTAR,
				new VillagerTrade(
						new TradeCost(Items.LECTERN, 1),
						Optional.of(new TradeCost(Items.EMERALD, 16)),
						new ItemStackTemplate(BlockItemRegistry.CELESTIAL_ALTAR_ITEM.get()),
						1,
						25,
						0.05f,
						Optional.empty(),
						List.of()
				));
	}

	private static void generateSkeletonMerchantTrades(BootstrapContext<VillagerTrade> context) {
		VillagerTrades.register(context,
				SKELETON_MERCHANT_1_EMERALD_IRON_RING,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 3),
						new ItemStackTemplate(ItemRegistry.IRON_RING.get()),
						1,
						2,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_1_EMERALD_SATCHEL,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 16),
						new ItemStackTemplate(ItemRegistry.SATCHEL.get()),
						1,
						3,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_1_EMERALD_POWDER_HORN,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 16),
						new ItemStackTemplate(ItemRegistry.POWDER_HORN.get()),
						1,
						3,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_1_EMERALD_AGILITY_BRACELET,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 16),
						new ItemStackTemplate(ItemRegistry.AGILITY_BRACELET.get()),
						1,
						3,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_1_EMERALD_BLOODY_SACRIFICE,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 5),
						new ItemStackTemplate(ItemRegistry.BLOODY_SACRIFICE.get()),
						1,
						1,
						0.05f,
						Optional.empty(),
						List.of()
				));

		VillagerTrades.register(context,
				SKELETON_MERCHANT_2_EMERALD_BLOATED_HEART,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 12),
						new ItemStackTemplate(ItemRegistry.BLOATED_HEART.get()),
						1,
						4,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_2_EMERALD_DEADEYE_PENDANT,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 32),
						new ItemStackTemplate(ItemRegistry.DEADEYE_PENDANT.get()),
						1,
						10,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_2_EMERALD_MELEE_MASTERS_MOLTEN_GLOVE,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 32),
						new ItemStackTemplate(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get()),
						1,
						10,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_2_EMERALD_NETHERITE_SHIELD,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 28),
						new ItemStackTemplate(ItemRegistry.NETHERITE_SHIELD.get()),
						1,
						8,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_2_EMERALD_BERSERKERS_AMULET,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 24),
						new ItemStackTemplate(ItemRegistry.BERSERKERS_AMULET.get()),
						1,
						6,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_2_EMERALD_AMETHYST_RING,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 28),
						new ItemStackTemplate(ItemRegistry.AMETHYST_RING.get()),
						1,
						8,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_2_EMERALD_EMERALD_RING,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 28),
						new ItemStackTemplate(ItemRegistry.EMERALD_RING.get()),
						1,
						8,
						0.05f,
						Optional.empty(),
						List.of()
				));
		VillagerTrades.register(context,
				SKELETON_MERCHANT_2_EMERALD_TOTEM_OF_UNDYING,
				new VillagerTrade(
						new TradeCost(Items.EMERALD, 30),
						new ItemStackTemplate(Items.TOTEM_OF_UNDYING),
						1,
						9,
						0.05f,
						Optional.empty(),
						List.of()
				));
	}

	private static ResourceKey<VillagerTrade> create(String name) {
		return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}
}