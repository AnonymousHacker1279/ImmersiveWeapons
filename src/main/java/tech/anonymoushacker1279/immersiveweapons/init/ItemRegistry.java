package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomBoatType;
import tech.anonymoushacker1279.immersiveweapons.item.*;
import tech.anonymoushacker1279.immersiveweapons.item.armor.*;
import tech.anonymoushacker1279.immersiveweapons.item.fortitude.*;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomArmorMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomItemMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.potion.AlcoholItem;
import tech.anonymoushacker1279.immersiveweapons.item.potion.WineItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow.*;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.*;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.*;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.throwable.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.molten.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.tesla.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.ventus.*;
import tech.anonymoushacker1279.immersiveweapons.item.utility.*;
import tech.anonymoushacker1279.immersiveweapons.world.food.FoodItemProperties;

@SuppressWarnings({"unused"})
public class ItemRegistry {

	// Item Register
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveWeapons.MOD_ID);

	// Tools
	public static final RegistryObject<MoltenSword> MOLTEN_SWORD = ITEMS.register("molten_sword", () -> new MoltenSword(CustomItemMaterials.MOLTEN, 3, -2.4f, new Properties().fireResistant()));
	public static final RegistryObject<MoltenPickaxe> MOLTEN_PICKAXE = ITEMS.register("molten_pickaxe", () -> new MoltenPickaxe(CustomItemMaterials.MOLTEN, 1, -2.8F, new Properties().fireResistant()));
	public static final RegistryObject<MoltenAxe> MOLTEN_AXE = ITEMS.register("molten_axe", () -> new MoltenAxe(CustomItemMaterials.MOLTEN, 5, -3.0f, new Properties().fireResistant()));
	public static final RegistryObject<MoltenShovel> MOLTEN_SHOVEL = ITEMS.register("molten_shovel", () -> new MoltenShovel(CustomItemMaterials.MOLTEN, 1.5f, -3.0f, new Properties().fireResistant()));
	public static final RegistryObject<MoltenHoe> MOLTEN_HOE = ITEMS.register("molten_hoe", () -> new MoltenHoe(CustomItemMaterials.MOLTEN, -5, 0.0f, new Properties().fireResistant()));
	public static final RegistryObject<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(CustomItemMaterials.COPPER, 3, -2.4f, new Properties()));
	public static final RegistryObject<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(CustomItemMaterials.COPPER, 1, -2.8F, new Properties()));
	public static final RegistryObject<AxeItem> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(CustomItemMaterials.COPPER, 6, -3.1f, new Properties()));
	public static final RegistryObject<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(CustomItemMaterials.COPPER, 1.5f, -3.0f, new Properties()));
	public static final RegistryObject<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(CustomItemMaterials.COPPER, -1, -1.0f, new Properties()));
	public static final RegistryObject<TeslaSword> TESLA_SWORD = ITEMS.register("tesla_sword", () -> new TeslaSword(CustomItemMaterials.TESLA, 3, -2.4f, new Properties()));
	public static final RegistryObject<TeslaPickaxe> TESLA_PICKAXE = ITEMS.register("tesla_pickaxe", () -> new TeslaPickaxe(CustomItemMaterials.TESLA, 1, -2.8f, new Properties()));
	public static final RegistryObject<TeslaAxe> TESLA_AXE = ITEMS.register("tesla_axe", () -> new TeslaAxe(CustomItemMaterials.TESLA, 5, -3.0f, new Properties()));
	public static final RegistryObject<TeslaShovel> TESLA_SHOVEL = ITEMS.register("tesla_shovel", () -> new TeslaShovel(CustomItemMaterials.TESLA, 1.5f, -3.0f, new Properties()));
	public static final RegistryObject<TeslaHoe> TESLA_HOE = ITEMS.register("tesla_hoe", () -> new TeslaHoe(CustomItemMaterials.TESLA, -6, 0.0f, new Properties()));
	public static final RegistryObject<SwordItem> COBALT_SWORD = ITEMS.register("cobalt_sword", () -> new SwordItem(CustomItemMaterials.COBALT, 3, -2.4f, new Properties()));
	public static final RegistryObject<PickaxeItem> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe", () -> new PickaxeItem(CustomItemMaterials.COBALT, 1, -2.8F, new Properties()));
	public static final RegistryObject<AxeItem> COBALT_AXE = ITEMS.register("cobalt_axe", () -> new AxeItem(CustomItemMaterials.COBALT, 6, -3.1f, new Properties()));
	public static final RegistryObject<ShovelItem> COBALT_SHOVEL = ITEMS.register("cobalt_shovel", () -> new ShovelItem(CustomItemMaterials.COBALT, 1.5f, -3.0f, new Properties()));
	public static final RegistryObject<HoeItem> COBALT_HOE = ITEMS.register("cobalt_hoe", () -> new HoeItem(CustomItemMaterials.COBALT, -2, -1.0f, new Properties()));
	public static final RegistryObject<VentusSword> VENTUS_SWORD = ITEMS.register("ventus_sword", () -> new VentusSword(CustomItemMaterials.VENTUS, 3, -2.0f, new Properties()));
	public static final RegistryObject<VentusPickaxe> VENTUS_PICKAXE = ITEMS.register("ventus_pickaxe", () -> new VentusPickaxe(CustomItemMaterials.VENTUS, 1, -2.4f, new Properties()));
	public static final RegistryObject<VentusAxe> VENTUS_AXE = ITEMS.register("ventus_axe", () -> new VentusAxe(CustomItemMaterials.VENTUS, 5, -2.6f, new Properties()));
	public static final RegistryObject<VentusShovel> VENTUS_SHOVEL = ITEMS.register("ventus_shovel", () -> new VentusShovel(CustomItemMaterials.VENTUS, 1.5f, -2.6f, new Properties()));
	public static final RegistryObject<VentusHoe> VENTUS_HOE = ITEMS.register("ventus_hoe", () -> new VentusHoe(CustomItemMaterials.VENTUS, -5, 0.2f, new Properties()));
	public static final RegistryObject<VentusStaff> VENTUS_STAFF = ITEMS.register("ventus_staff", () -> new VentusStaff(new Properties().durability(300)));
	public static final RegistryObject<SwordItem> ASTRAL_SWORD = ITEMS.register("astral_sword", () -> new SwordItem(CustomItemMaterials.ASTRAL, 3, -1.5f, new Properties()));
	public static final RegistryObject<PickaxeItem> ASTRAL_PICKAXE = ITEMS.register("astral_pickaxe", () -> new PickaxeItem(CustomItemMaterials.ASTRAL, 1, -1.9f, new Properties()));
	public static final RegistryObject<AxeItem> ASTRAL_AXE = ITEMS.register("astral_axe", () -> new AxeItem(CustomItemMaterials.ASTRAL, 5, -2.1f, new Properties()));
	public static final RegistryObject<ShovelItem> ASTRAL_SHOVEL = ITEMS.register("astral_shovel", () -> new ShovelItem(CustomItemMaterials.ASTRAL, 1.5f, -2.1f, new Properties()));
	public static final RegistryObject<HoeItem> ASTRAL_HOE = ITEMS.register("astral_hoe", () -> new HoeItem(CustomItemMaterials.ASTRAL, -4, 0.8f, new Properties()));
	public static final RegistryObject<SwordItem> STARSTORM_SWORD = ITEMS.register("starstorm_sword", () -> new SwordItem(CustomItemMaterials.STARSTORM, 3, -2.4f, new Properties()));
	public static final RegistryObject<PickaxeItem> STARSTORM_PICKAXE = ITEMS.register("starstorm_pickaxe", () -> new PickaxeItem(CustomItemMaterials.STARSTORM, 1, -2.8f, new Properties()));
	public static final RegistryObject<AxeItem> STARSTORM_AXE = ITEMS.register("starstorm_axe", () -> new AxeItem(CustomItemMaterials.STARSTORM, 5, -3.0f, new Properties()));
	public static final RegistryObject<ShovelItem> STARSTORM_SHOVEL = ITEMS.register("starstorm_shovel", () -> new ShovelItem(CustomItemMaterials.STARSTORM, 1.5f, -3.0f, new Properties()));
	public static final RegistryObject<HoeItem> STARSTORM_HOE = ITEMS.register("starstorm_hoe", () -> new HoeItem(CustomItemMaterials.STARSTORM, -7, 0.0f, new Properties()));

	// Weapons
	public static final RegistryObject<PikeItem> WOODEN_PIKE = ITEMS.register("wooden_pike", () -> new PikeItem(new Properties().durability(59), 4.0d, -2.6d, Ingredient.of(ItemTags.PLANKS)));
	public static final RegistryObject<PikeItem> STONE_PIKE = ITEMS.register("stone_pike", () -> new PikeItem(new Properties().durability(131), 5.0d, -2.6d, Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)));
	public static final RegistryObject<PikeItem> GOLDEN_PIKE = ITEMS.register("golden_pike", () -> new PikeItem(new Properties().durability(32), 4.0d, -2.6d, Ingredient.of(Tags.Items.INGOTS_GOLD)));
	public static final RegistryObject<PikeItem> COPPER_PIKE = ITEMS.register("copper_pike", () -> new PikeItem(new Properties().durability(180), 5.0d, -2.6d, Ingredient.of(Tags.Items.INGOTS_COPPER)));
	public static final RegistryObject<PikeItem> IRON_PIKE = ITEMS.register("iron_pike", () -> new PikeItem(new Properties().durability(250), 6.0d, -2.6d, Ingredient.of(Tags.Items.INGOTS_IRON)));
	public static final RegistryObject<PikeItem> COBALT_PIKE = ITEMS.register("cobalt_pike", () -> new PikeItem(new Properties().durability(300), 6.0d, -2.6d, Ingredient.of(ForgeItemTagGroups.COBALT_INGOTS)));
	public static final RegistryObject<PikeItem> DIAMOND_PIKE = ITEMS.register("diamond_pike", () -> new PikeItem(new Properties().durability(1561), 7.0d, -2.6d, Ingredient.of(Tags.Items.GEMS_DIAMOND)));
	public static final RegistryObject<PikeItem> NETHERITE_PIKE = ITEMS.register("netherite_pike", () -> new PikeItem(new Properties().durability(2031).fireResistant(), 8.0d, -2.6d, Ingredient.of(Tags.Items.INGOTS_NETHERITE)));
	public static final RegistryObject<SimplePistolItem> FLINTLOCK_PISTOL = ITEMS.register("flintlock_pistol", () -> new SimplePistolItem(new Properties().durability(499)));
	public static final RegistryObject<SimpleShotgunItem> BLUNDERBUSS = ITEMS.register("blunderbuss", () -> new SimpleShotgunItem(new Properties().durability(449)));
	public static final RegistryObject<MusketItem> MUSKET = ITEMS.register("musket", () -> new MusketItem(new Properties().durability(499), false));
	public static final RegistryObject<MusketItem> MUSKET_SCOPE = ITEMS.register("musket_scope", () -> new MusketItem(new Properties().durability(499), true));
	public static final RegistryObject<FlareGunItem> FLARE_GUN = ITEMS.register("flare_gun", () -> new FlareGunItem(new Properties().durability(399)));
	public static final RegistryObject<GauntletItem> WOODEN_GAUNTLET = ITEMS.register("wooden_gauntlet", () -> new GauntletItem(Tiers.WOOD, 2, -2.3f, new Properties(), 0.15f, 0, Ingredient.of(ItemTags.PLANKS)));
	public static final RegistryObject<GauntletItem> STONE_GAUNTLET = ITEMS.register("stone_gauntlet", () -> new GauntletItem(Tiers.STONE, 2, -2.3f, new Properties(), 0.25f, 0, Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)));
	public static final RegistryObject<GauntletItem> GOLDEN_GAUNTLET = ITEMS.register("golden_gauntlet", () -> new GauntletItem(Tiers.GOLD, 2, -2.3f, new Properties(), 0.35f, 0, Ingredient.of(Tags.Items.INGOTS_GOLD)));
	public static final RegistryObject<GauntletItem> COPPER_GAUNTLET = ITEMS.register("copper_gauntlet", () -> new GauntletItem(CustomItemMaterials.COPPER, 1, -2.3f, new Properties(), 0.45f, 0, Ingredient.of(Tags.Items.INGOTS_COPPER)));
	public static final RegistryObject<GauntletItem> IRON_GAUNTLET = ITEMS.register("iron_gauntlet", () -> new GauntletItem(Tiers.IRON, 2, -2.3f, new Properties(), 0.55f, 0, Ingredient.of(Tags.Items.INGOTS_IRON)));
	public static final RegistryObject<GauntletItem> COBALT_GAUNTLET = ITEMS.register("cobalt_gauntlet", () -> new GauntletItem(CustomItemMaterials.COBALT, 1, -2.3f, new Properties(), 0.60f, 0, Ingredient.of(ForgeItemTagGroups.COBALT_INGOTS)));
	public static final RegistryObject<GauntletItem> DIAMOND_GAUNTLET = ITEMS.register("diamond_gauntlet", () -> new GauntletItem(Tiers.DIAMOND, 2, -2.3f, new Properties(), 0.75f, 1, Ingredient.of(Tags.Items.GEMS_DIAMOND)));
	public static final RegistryObject<GauntletItem> NETHERITE_GAUNTLET = ITEMS.register("netherite_gauntlet", () -> new GauntletItem(Tiers.NETHERITE, 2, -2.3f, new Properties(), 0.85f, 1, Ingredient.of(Tags.Items.INGOTS_NETHERITE)));
	public static final RegistryObject<MeteorStaffItem> METEOR_STAFF = ITEMS.register("meteor_staff", () -> new MeteorStaffItem(new Properties().durability(199)));
	public static final RegistryObject<CursedSightStaffItem> CURSED_SIGHT_STAFF = ITEMS.register("cursed_sight_staff", () -> new CursedSightStaffItem(new Properties().durability(149)));

	// Items
	public static final RegistryObject<Item> WOODEN_SHARD = ITEMS.register("wooden_shard", () -> new Item(new Properties()));
	public static final RegistryObject<Item> STONE_SHARD = ITEMS.register("stone_shard", () -> new Item(new Properties()));
	public static final RegistryObject<Item> VENTUS_SHARD = ITEMS.register("ventus_shard", () -> new Item(new Properties()));
	public static final RegistryObject<Item> MOLTEN_SHARD = ITEMS.register("molten_shard", () -> new Item(new Properties().fireResistant()));
	public static final RegistryObject<Item> OBSIDIAN_SHARD = ITEMS.register("obsidian_shard", () -> new Item(new Properties()));
	public static final RegistryObject<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Properties()));
	public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () -> new Item(new Properties()));
	public static final RegistryObject<Item> WOODEN_TOOL_ROD = ITEMS.register("wooden_tool_rod", () -> new Item(new Properties()));
	public static final RegistryObject<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget", () -> new Item(new Properties()));
	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Properties()));
	public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Properties()));
	public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt", () -> new Item(new Properties()));
	public static final RegistryObject<Item> STARSTORM_INGOT = ITEMS.register("starstorm_ingot", () -> new Item(new Properties()));
	public static final RegistryObject<Item> ASTRAL_INGOT = ITEMS.register("astral_ingot", () -> new Item(new Properties()));
	public static final RegistryObject<Item> RAW_ASTRAL = ITEMS.register("raw_astral", () -> new Item(new Properties()));
	public static final RegistryObject<Item> TESLA_INGOT = ITEMS.register("tesla_ingot", () -> new Item(new Properties()));
	public static final RegistryObject<Item> ELECTRIC_INGOT = ITEMS.register("electric_ingot", () -> new Item(new Properties()));
	public static final RegistryObject<Item> CONDUCTIVE_ALLOY = ITEMS.register("conductive_alloy", () -> new Item(new Properties()));
	public static final RegistryObject<Item> MOLTEN_INGOT = ITEMS.register("molten_ingot", () -> new FuelItem(new Properties().fireResistant(), 24000));
	public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Properties()));
	public static final RegistryObject<Item> VENTUS_STAFF_CORE = ITEMS.register("ventus_staff_core", () -> new Item(new Properties()));
	public static final RegistryObject<Item> CURSED_SIGHT_STAFF_CORE = ITEMS.register("cursed_sight_staff_core", () -> new Item(new Properties()));
	public static final RegistryObject<Item> AZUL_KEYSTONE = ITEMS.register("azul_keystone", () -> new Item(new Properties()));
	public static final RegistryObject<Item> AZUL_KEYSTONE_FRAGMENT = ITEMS.register("azul_keystone_fragment", () -> new Item(new Properties()));
	public static final RegistryObject<AzulLocatorItem> AZUL_LOCATOR = ITEMS.register("azul_locator", () -> new AzulLocatorItem(new Properties().durability(1)));
	public static final RegistryObject<Item> CELESTIAL_FRAGMENT = ITEMS.register("celestial_fragment", () -> new Item(new Properties().fireResistant()));
	public static final RegistryObject<Item> BROKEN_LENS = ITEMS.register("broken_lens", () -> new Item(new Properties()));
	public static final RegistryObject<Item> WOODEN_PIKE_HEAD = ITEMS.register("wooden_pike_head", () -> new Item(new Properties()));
	public static final RegistryObject<Item> STONE_PIKE_HEAD = ITEMS.register("stone_pike_head", () -> new Item(new Properties()));
	public static final RegistryObject<Item> GOLDEN_PIKE_HEAD = ITEMS.register("golden_pike_head", () -> new Item(new Properties()));
	public static final RegistryObject<Item> COPPER_PIKE_HEAD = ITEMS.register("copper_pike_head", () -> new Item(new Properties()));
	public static final RegistryObject<Item> IRON_PIKE_HEAD = ITEMS.register("iron_pike_head", () -> new Item(new Properties()));
	public static final RegistryObject<Item> COBALT_PIKE_HEAD = ITEMS.register("cobalt_pike_head", () -> new Item(new Properties()));
	public static final RegistryObject<Item> DIAMOND_PIKE_HEAD = ITEMS.register("diamond_pike_head", () -> new Item(new Properties()));
	public static final RegistryObject<WoodenArrowItem> WOODEN_ARROW = ITEMS.register("wooden_arrow", () -> new WoodenArrowItem(new Properties(), 1.65d));
	public static final RegistryObject<StoneArrowItem> STONE_ARROW = ITEMS.register("stone_arrow", () -> new StoneArrowItem(new Properties(), 1.85d));
	public static final RegistryObject<GoldenArrowItem> GOLDEN_ARROW = ITEMS.register("golden_arrow", () -> new GoldenArrowItem(new Properties(), 2.10d));
	public static final RegistryObject<CopperArrowItem> COPPER_ARROW = ITEMS.register("copper_arrow", () -> new CopperArrowItem(new Properties(), 2.15d));
	public static final RegistryObject<IronArrowItem> IRON_ARROW = ITEMS.register("iron_arrow", () -> new IronArrowItem(new Properties(), 2.35d));
	public static final RegistryObject<CobaltArrowItem> COBALT_ARROW = ITEMS.register("cobalt_arrow", () -> new CobaltArrowItem(new Properties(), 2.55d));
	public static final RegistryObject<DiamondArrowItem> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new DiamondArrowItem(new Properties(), 3.00d));
	public static final RegistryObject<NetheriteArrowItem> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new NetheriteArrowItem(new Properties().fireResistant(), 5.75d));
	public static final RegistryObject<SmokeGrenadeArrowItem> SMOKE_GRENADE_ARROW = ITEMS.register("smoke_grenade_arrow", () -> new SmokeGrenadeArrowItem(new Properties(), 2.00d, 0));
	public static final RegistryObject<SmokeGrenadeArrowItem> SMOKE_GRENADE_ARROW_RED = ITEMS.register("smoke_grenade_arrow_red", () -> new SmokeGrenadeArrowItem(new Properties(), 2.00d, 1));
	public static final RegistryObject<SmokeGrenadeArrowItem> SMOKE_GRENADE_ARROW_GREEN = ITEMS.register("smoke_grenade_arrow_green", () -> new SmokeGrenadeArrowItem(new Properties(), 2.00d, 2));
	public static final RegistryObject<SmokeGrenadeArrowItem> SMOKE_GRENADE_ARROW_BLUE = ITEMS.register("smoke_grenade_arrow_blue", () -> new SmokeGrenadeArrowItem(new Properties(), 2.00d, 3));
	public static final RegistryObject<SmokeGrenadeArrowItem> SMOKE_GRENADE_ARROW_PURPLE = ITEMS.register("smoke_grenade_arrow_purple", () -> new SmokeGrenadeArrowItem(new Properties(), 2.00d, 4));
	public static final RegistryObject<SmokeGrenadeArrowItem> SMOKE_GRENADE_ARROW_YELLOW = ITEMS.register("smoke_grenade_arrow_yellow", () -> new SmokeGrenadeArrowItem(new Properties(), 2.00d, 5));
	public static final RegistryObject<WoodenMusketBallItem> WOODEN_MUSKET_BALL = ITEMS.register("wooden_musket_ball", () -> new WoodenMusketBallItem(new Properties(), 2.0d));
	public static final RegistryObject<StoneMusketBallItem> STONE_MUSKET_BALL = ITEMS.register("stone_musket_ball", () -> new StoneMusketBallItem(new Properties(), 2.20d));
	public static final RegistryObject<GoldenMusketBallItem> GOLDEN_MUSKET_BALL = ITEMS.register("golden_musket_ball", () -> new GoldenMusketBallItem(new Properties(), 2.30d));
	public static final RegistryObject<CopperMusketBallItem> COPPER_MUSKET_BALL = ITEMS.register("copper_musket_ball", () -> new CopperMusketBallItem(new Properties(), 2.40d));
	public static final RegistryObject<IronMusketBallItem> IRON_MUSKET_BALL = ITEMS.register("iron_musket_ball", () -> new IronMusketBallItem(new Properties(), 2.65d));
	public static final RegistryObject<CobaltMusketBallItem> COBALT_MUSKET_BALL = ITEMS.register("cobalt_musket_ball", () -> new CobaltMusketBallItem(new Properties(), 2.90d));
	public static final RegistryObject<DiamondMusketBallItem> DIAMOND_MUSKET_BALL = ITEMS.register("diamond_musket_ball", () -> new DiamondMusketBallItem(new Properties(), 3.35d));
	public static final RegistryObject<NetheriteMusketBallItem> NETHERITE_MUSKET_BALL = ITEMS.register("netherite_musket_ball", () -> new NetheriteMusketBallItem(new Properties().fireResistant(), 6.50d));
	public static final RegistryObject<FlareItem> FLARE = ITEMS.register("flare", () -> new FlareItem(new Properties(), 0.1d));
	public static final RegistryObject<Item> MORTAR_SHELL = ITEMS.register("mortar_shell", () -> new Item(new Properties()));
	public static final RegistryObject<Item> GRENADE_ASSEMBLY = ITEMS.register("grenade_assembly", () -> new Item(new Properties()));
	public static final RegistryObject<Item> TOOL_JOINT = ITEMS.register("tool_joint", () -> new Item(new Properties()));
	public static final RegistryObject<Item> GAUNTLET_SCAFFOLDING = ITEMS.register("gauntlet_scaffolding", () -> new Item(new Properties()));
	public static final RegistryObject<Item> FLINTLOCK_ASSEMBLY = ITEMS.register("flintlock_assembly", () -> new Item(new Properties()));
	public static final RegistryObject<Item> TRIGGER_ASSEMBLY = ITEMS.register("trigger_assembly", () -> new Item(new Properties()));
	public static final RegistryObject<Item> HEAVY_WOODEN_STOCK = ITEMS.register("heavy_wooden_stock", () -> new Item(new Properties()));
	public static final RegistryObject<Item> WOODEN_PISTOL_HANDLE = ITEMS.register("wooden_pistol_handle", () -> new Item(new Properties()));
	public static final RegistryObject<Item> IRON_BARREL = ITEMS.register("iron_barrel", () -> new Item(new Properties()));
	public static final RegistryObject<Item> EXTENDED_IRON_BARREL = ITEMS.register("extended_iron_barrel", () -> new Item(new Properties()));
	public static final RegistryObject<Item> SHORT_IRON_BARREL = ITEMS.register("short_iron_barrel", () -> new Item(new Properties()));
	public static final RegistryObject<Item> WIDE_GOLDEN_BARREL = ITEMS.register("wide_golden_barrel", () -> new Item(new Properties()));
	public static final RegistryObject<Item> SCOPE_MOUNT = ITEMS.register("scope_mount", () -> new Item(new Properties()));
	public static final RegistryObject<Item> SCOPE = ITEMS.register("scope", () -> new Item(new Properties()));
	public static final RegistryObject<SmokeGrenadeItem> SMOKE_GRENADE = ITEMS.register("smoke_grenade", () -> new SmokeGrenadeItem(new Properties().stacksTo(16), 0));
	public static final RegistryObject<SmokeGrenadeItem> SMOKE_GRENADE_RED = ITEMS.register("smoke_grenade_red", () -> new SmokeGrenadeItem(new Properties().stacksTo(16), 1));
	public static final RegistryObject<SmokeGrenadeItem> SMOKE_GRENADE_GREEN = ITEMS.register("smoke_grenade_green", () -> new SmokeGrenadeItem(new Properties().stacksTo(16), 2));
	public static final RegistryObject<SmokeGrenadeItem> SMOKE_GRENADE_BLUE = ITEMS.register("smoke_grenade_blue", () -> new SmokeGrenadeItem(new Properties().stacksTo(16), 3));
	public static final RegistryObject<SmokeGrenadeItem> SMOKE_GRENADE_PURPLE = ITEMS.register("smoke_grenade_purple", () -> new SmokeGrenadeItem(new Properties().stacksTo(16), 4));
	public static final RegistryObject<SmokeGrenadeItem> SMOKE_GRENADE_YELLOW = ITEMS.register("smoke_grenade_yellow", () -> new SmokeGrenadeItem(new Properties().stacksTo(16), 5));
	public static final RegistryObject<MolotovItem> MOLOTOV_COCKTAIL = ITEMS.register("molotov_cocktail", () -> new MolotovItem(new Properties().stacksTo(16)));
	public static final RegistryObject<Item> SMOKE_POWDER = ITEMS.register("smoke_powder", () -> new Item(new Properties()));
	public static final RegistryObject<BasicContainerItem> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle", () -> new BasicContainerItem(new Properties()));
	public static final RegistryObject<AlcoholItem> BOTTLE_OF_ALCOHOL = ITEMS.register("bottle_of_alcohol", () -> new AlcoholItem(new Properties().stacksTo(16)));
	public static final RegistryObject<WineItem> BOTTLE_OF_WINE = ITEMS.register("bottle_of_wine", () -> new WineItem(new Properties().stacksTo(16)));
	public static final RegistryObject<Item> PLIERS = ITEMS.register("pliers", () -> new Item(new Properties().stacksTo(1)));
	public static final RegistryObject<ChocolateBarItem> CHOCOLATE_BAR = ITEMS.register("chocolate_bar", () -> new ChocolateBarItem(new Properties().food(FoodItemProperties.CHOCOLATE_BAR), false));
	public static final RegistryObject<ChocolateBarItem> EXPLOSIVE_CHOCOLATE_BAR = ITEMS.register("explosive_chocolate_bar", () -> new ChocolateBarItem(new Properties().food(FoodItemProperties.CHOCOLATE_BAR), true));
	public static final RegistryObject<BandageItem> BANDAGE = ITEMS.register("bandage", () -> new BandageItem(new Properties().stacksTo(16)));
	public static final RegistryObject<Item> MRE = ITEMS.register("mre", () -> new Item(new Properties().food(FoodItemProperties.MRE)));
	public static final RegistryObject<PainkillerItem> PAINKILLERS = ITEMS.register("painkillers", () -> new PainkillerItem(new Properties().stacksTo(24)));
	public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe", () -> new Item(new Properties().stacksTo(16)));
	public static final RegistryObject<MorphineItem> MORPHINE = ITEMS.register("morphine", () -> new MorphineItem(new Properties().stacksTo(16)));
	public static final RegistryObject<UsedSyringeItem> USED_SYRINGE = ITEMS.register("used_syringe", () -> new UsedSyringeItem(new Properties().stacksTo(16)));
	public static final RegistryObject<FirstAidKitItem> FIRST_AID_KIT = ITEMS.register("first_aid_kit", () -> new FirstAidKitItem(new Properties().stacksTo(8)));
	public static final RegistryObject<Item> CLOTH_SCRAP = ITEMS.register("cloth_scrap", () -> new Item(new Properties()));
	public static final RegistryObject<MudBallItem> MUD_BALL = ITEMS.register("mud_ball", () -> new MudBallItem(new Properties()));
	public static final RegistryObject<CustomBoatItem> BURNED_OAK_BOAT = ITEMS.register("burned_oak_boat", () -> new CustomBoatItem(CustomBoatType.BURNED_OAK, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<CustomBoatItem> BURNED_OAK_CHEST_BOAT = ITEMS.register("burned_oak_chest_boat", () -> new CustomBoatItem(CustomBoatType.BURNED_OAK, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<CustomBoatItem> STARDUST_BOAT = ITEMS.register("stardust_boat", () -> new CustomBoatItem(CustomBoatType.STARDUST, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<CustomBoatItem> STARDUST_CHEST_BOAT = ITEMS.register("stardust_chest_boat", () -> new CustomBoatItem(CustomBoatType.STARDUST, new Item.Properties().stacksTo(1)));

	// Armor
	public static final RegistryObject<MoltenArmorItem> MOLTEN_HELMET = ITEMS.register("molten_helmet", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlot.HEAD, new Item.Properties().fireResistant(), false));
	public static final RegistryObject<MoltenArmorItem> MOLTEN_CHESTPLATE = ITEMS.register("molten_chestplate", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlot.CHEST, new Item.Properties().fireResistant(), false));
	public static final RegistryObject<MoltenArmorItem> MOLTEN_LEGGINGS = ITEMS.register("molten_leggings", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlot.LEGS, new Item.Properties().fireResistant(), true));
	public static final RegistryObject<MoltenArmorItem> MOLTEN_BOOTS = ITEMS.register("molten_boots", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlot.FEET, new Item.Properties().fireResistant(), false));
	public static final RegistryObject<CopperArmorItem> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlot.HEAD, new Item.Properties(), false));
	public static final RegistryObject<CopperArmorItem> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlot.CHEST, new Item.Properties(), false));
	public static final RegistryObject<CopperArmorItem> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlot.LEGS, new Item.Properties(), true));
	public static final RegistryObject<CopperArmorItem> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlot.FEET, new Item.Properties(), false));
	public static final RegistryObject<TeslaArmorItem> TESLA_HELMET = ITEMS.register("tesla_helmet", () -> new TeslaArmorItem(CustomArmorMaterials.TESLA, EquipmentSlot.HEAD, new Item.Properties(), false));
	public static final RegistryObject<TeslaArmorItem> TESLA_CHESTPLATE = ITEMS.register("tesla_chestplate", () -> new TeslaArmorItem(CustomArmorMaterials.TESLA, EquipmentSlot.CHEST, new Item.Properties(), false));
	public static final RegistryObject<TeslaArmorItem> TESLA_LEGGINGS = ITEMS.register("tesla_leggings", () -> new TeslaArmorItem(CustomArmorMaterials.TESLA, EquipmentSlot.LEGS, new Item.Properties(), true));
	public static final RegistryObject<TeslaArmorItem> TESLA_BOOTS = ITEMS.register("tesla_boots", () -> new TeslaArmorItem(CustomArmorMaterials.TESLA, EquipmentSlot.FEET, new Item.Properties(), false));
	public static final RegistryObject<CobaltArmorItem> COBALT_HELMET = ITEMS.register("cobalt_helmet", () -> new CobaltArmorItem(CustomArmorMaterials.COBALT, EquipmentSlot.HEAD, new Item.Properties(), false));
	public static final RegistryObject<CobaltArmorItem> COBALT_CHESTPLATE = ITEMS.register("cobalt_chestplate", () -> new CobaltArmorItem(CustomArmorMaterials.COBALT, EquipmentSlot.CHEST, new Item.Properties(), false));
	public static final RegistryObject<CobaltArmorItem> COBALT_LEGGINGS = ITEMS.register("cobalt_leggings", () -> new CobaltArmorItem(CustomArmorMaterials.COBALT, EquipmentSlot.LEGS, new Item.Properties(), true));
	public static final RegistryObject<CobaltArmorItem> COBALT_BOOTS = ITEMS.register("cobalt_boots", () -> new CobaltArmorItem(CustomArmorMaterials.COBALT, EquipmentSlot.FEET, new Item.Properties(), false));
	public static final RegistryObject<VentusArmorItem> VENTUS_HELMET = ITEMS.register("ventus_helmet", () -> new VentusArmorItem(CustomArmorMaterials.VENTUS, EquipmentSlot.HEAD, new Item.Properties(), false));
	public static final RegistryObject<VentusArmorItem> VENTUS_CHESTPLATE = ITEMS.register("ventus_chestplate", () -> new VentusArmorItem(CustomArmorMaterials.VENTUS, EquipmentSlot.CHEST, new Item.Properties(), false));
	public static final RegistryObject<VentusArmorItem> VENTUS_LEGGINGS = ITEMS.register("ventus_leggings", () -> new VentusArmorItem(CustomArmorMaterials.VENTUS, EquipmentSlot.LEGS, new Item.Properties(), true));
	public static final RegistryObject<VentusArmorItem> VENTUS_BOOTS = ITEMS.register("ventus_boots", () -> new VentusArmorItem(CustomArmorMaterials.VENTUS, EquipmentSlot.FEET, new Item.Properties(), false));
	public static final RegistryObject<AstralArmorItem> ASTRAL_HELMET = ITEMS.register("astral_helmet", () -> new AstralArmorItem(CustomArmorMaterials.ASTRAL, EquipmentSlot.HEAD, new Item.Properties(), false));
	public static final RegistryObject<AstralArmorItem> ASTRAL_CHESTPLATE = ITEMS.register("astral_chestplate", () -> new AstralArmorItem(CustomArmorMaterials.ASTRAL, EquipmentSlot.CHEST, new Item.Properties(), false));
	public static final RegistryObject<AstralArmorItem> ASTRAL_LEGGINGS = ITEMS.register("astral_leggings", () -> new AstralArmorItem(CustomArmorMaterials.ASTRAL, EquipmentSlot.LEGS, new Item.Properties(), true));
	public static final RegistryObject<AstralArmorItem> ASTRAL_BOOTS = ITEMS.register("astral_boots", () -> new AstralArmorItem(CustomArmorMaterials.ASTRAL, EquipmentSlot.FEET, new Item.Properties(), false));
	public static final RegistryObject<StarstormArmorItem> STARSTORM_HELMET = ITEMS.register("starstorm_helmet", () -> new StarstormArmorItem(CustomArmorMaterials.STARSTORM, EquipmentSlot.HEAD, new Item.Properties(), false));
	public static final RegistryObject<StarstormArmorItem> STARSTORM_CHESTPLATE = ITEMS.register("starstorm_chestplate", () -> new StarstormArmorItem(CustomArmorMaterials.STARSTORM, EquipmentSlot.CHEST, new Item.Properties(), false));
	public static final RegistryObject<StarstormArmorItem> STARSTORM_LEGGINGS = ITEMS.register("starstorm_leggings", () -> new StarstormArmorItem(CustomArmorMaterials.STARSTORM, EquipmentSlot.LEGS, new Item.Properties(), true));
	public static final RegistryObject<StarstormArmorItem> STARSTORM_BOOTS = ITEMS.register("starstorm_boots", () -> new StarstormArmorItem(CustomArmorMaterials.STARSTORM, EquipmentSlot.FEET, new Item.Properties(), false));

	// Spawn eggs
	public static final RegistryObject<ForgeSpawnEggItem> DYING_SOLDIER_SPAWN_EGG = ITEMS.register("dying_soldier_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.DYING_SOLDIER_ENTITY, 0x7a6851, 0x783d22, (new Item.Properties()).stacksTo(16)));
	public static final RegistryObject<ForgeSpawnEggItem> MINUTEMAN_SPAWN_EGG = ITEMS.register("minuteman_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.MINUTEMAN_ENTITY, 0x494522, 0x204b2a, (new Item.Properties()).stacksTo(16)));
	public static final RegistryObject<ForgeSpawnEggItem> FIELD_MEDIC_SPAWN_EGG = ITEMS.register("field_medic_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.FIELD_MEDIC_ENTITY, 0xde5451, 0xebe4d2, (new Item.Properties()).stacksTo(16)));
	public static final RegistryObject<ForgeSpawnEggItem> WANDERING_WARRIOR_SPAWN_EGG = ITEMS.register("wandering_warrior_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.WANDERING_WARRIOR_ENTITY, 0x614226, 0x2e6278, (new Item.Properties()).stacksTo(16)));
	public static final RegistryObject<ForgeSpawnEggItem> HANS_SPAWN_EGG = ITEMS.register("hans_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.HANS_ENTITY, 0xd0a873, 0xafafaf, (new Item.Properties().stacksTo(16))));
	public static final RegistryObject<ForgeSpawnEggItem> LAVA_REVENANT_SPAWN_EGG = ITEMS.register("lava_revenant_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.LAVA_REVENANT_ENTITY, 0x640000, 0x990000, (new Item.Properties().stacksTo(16))));
	public static final RegistryObject<ForgeSpawnEggItem> ROCK_SPIDER_SPAWN_EGG = ITEMS.register("rock_spider_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.ROCK_SPIDER_ENTITY, 0x7f7f7f, 0xa80e0e, (new Item.Properties().stacksTo(16))));
	public static final RegistryObject<ForgeSpawnEggItem> CELESTIAL_TOWER_SPAWN_EGG = ITEMS.register("celestial_tower_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.CELESTIAL_TOWER_ENTITY, 0x63353d, 0xb3754b, (new Item.Properties().stacksTo(16))));
	public static final RegistryObject<ForgeSpawnEggItem> STARMITE_SPAWN_EGG = ITEMS.register("starmite_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.STARMITE_ENTITY, 0x8f4f1c, 0xa55c1e, (new Item.Properties().stacksTo(16))));
	public static final RegistryObject<ForgeSpawnEggItem> FIREFLY_SPAWN_EGG = ITEMS.register("firefly_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.FIREFLY_ENTITY, 0x703a2a, 0x43e88d, (new Item.Properties().stacksTo(16))));
	public static final RegistryObject<ForgeSpawnEggItem> STORM_CREEPER_SPAWN_EGG = ITEMS.register("storm_creeper_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.STORM_CREEPER_ENTITY, 0xfe162c, 0x00eaf6, (new Item.Properties().stacksTo(16))));
	public static final RegistryObject<ForgeSpawnEggItem> EVIL_EYE_SPAWN_EGG = ITEMS.register("evil_eye_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.EVIL_EYE_ENTITY, 0xd7d7d7, 0x4e8386, (new Item.Properties().stacksTo(16))));
}