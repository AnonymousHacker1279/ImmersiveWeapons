package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.IWJukeboxSongs;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.item.*;
import tech.anonymoushacker1279.immersiveweapons.item.armor.*;
import tech.anonymoushacker1279.immersiveweapons.item.bow.AuroraBow;
import tech.anonymoushacker1279.immersiveweapons.item.bow.DragonBreathBow;
import tech.anonymoushacker1279.immersiveweapons.item.bow.IceBowItem;
import tech.anonymoushacker1279.immersiveweapons.item.fortitude.*;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.MoltenGauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.TeslaGauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.VentusGauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.*;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.pike.MoltenPikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.TeslaPikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.VentusPikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.potion.AlcoholItem;
import tech.anonymoushacker1279.immersiveweapons.item.potion.WineItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem.BulletBuilder;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem.ArrowBuilder;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.DragonFireballItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem.ThrowableType;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils.HitEffect;
import tech.anonymoushacker1279.immersiveweapons.item.tool.PliersItem;
import tech.anonymoushacker1279.immersiveweapons.item.tool.TheSword;
import tech.anonymoushacker1279.immersiveweapons.item.tool.molten.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.tesla.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.ventus.*;
import tech.anonymoushacker1279.immersiveweapons.item.utility.BasicContainerItem;
import tech.anonymoushacker1279.immersiveweapons.item.utility.CustomBoatItem;
import tech.anonymoushacker1279.immersiveweapons.item.utility.FuelItem;
import tech.anonymoushacker1279.immersiveweapons.util.markers.DatagenExclusionMarker;
import tech.anonymoushacker1279.immersiveweapons.util.markers.DatagenExclusionMarker.Type;
import tech.anonymoushacker1279.immersiveweapons.util.markers.LanguageEntryOverride;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TextureMetadataMarker;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TextureMetadataMarker.PredefinedGroups;
import tech.anonymoushacker1279.immersiveweapons.world.food.FoodItemProperties;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ItemRegistry {

	// Item Register
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ImmersiveWeapons.MOD_ID);

	// Tools
	@TextureMetadataMarker(frameTime = 2)
	public static final Supplier<MoltenSword> MOLTEN_SWORD = ITEMS.register("molten_sword", MoltenSword::new);
	@TextureMetadataMarker(frameTime = 5)
	public static final Supplier<MoltenPickaxe> MOLTEN_PICKAXE = ITEMS.register("molten_pickaxe", MoltenPickaxe::new);
	@TextureMetadataMarker(frameTime = 5)
	public static final Supplier<MoltenAxe> MOLTEN_AXE = ITEMS.register("molten_axe", MoltenAxe::new);
	@TextureMetadataMarker(frameTime = 5)
	public static final Supplier<MoltenShovel> MOLTEN_SHOVEL = ITEMS.register("molten_shovel", MoltenShovel::new);
	@TextureMetadataMarker(frameTime = 5)
	public static final Supplier<MoltenHoe> MOLTEN_HOE = ITEMS.register("molten_hoe", MoltenHoe::new);
	public static final Supplier<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(CustomTiers.COPPER, new Properties().attributes(
			SwordItem.createAttributes(CustomTiers.COPPER, 3, -2.4f)
	)));
	public static final Supplier<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(CustomTiers.COPPER, new Properties().attributes(
			PickaxeItem.createAttributes(CustomTiers.COPPER, 1, -2.8f)
	)));
	public static final Supplier<AxeItem> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(CustomTiers.COPPER, new Properties().attributes(
			AxeItem.createAttributes(CustomTiers.COPPER, 6, -3.1f)
	)));
	public static final Supplier<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(CustomTiers.COPPER, new Properties().attributes(
			ShovelItem.createAttributes(CustomTiers.COPPER, 1.5f, -3.0f)
	)));
	public static final Supplier<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(CustomTiers.COPPER, new Properties().attributes(
			HoeItem.createAttributes(CustomTiers.COPPER, -1, -1.0f)
	)));
	@TextureMetadataMarker(frameTime = 2)
	public static final Supplier<TeslaSword> TESLA_SWORD = ITEMS.register("tesla_sword", TeslaSword::new);
	@TextureMetadataMarker(frameTime = 2)
	public static final Supplier<TeslaPickaxe> TESLA_PICKAXE = ITEMS.register("tesla_pickaxe", TeslaPickaxe::new);
	@TextureMetadataMarker(frameTime = 2)
	public static final Supplier<TeslaAxe> TESLA_AXE = ITEMS.register("tesla_axe", TeslaAxe::new);
	@TextureMetadataMarker(frameTime = 3)
	public static final Supplier<TeslaShovel> TESLA_SHOVEL = ITEMS.register("tesla_shovel", TeslaShovel::new);
	@TextureMetadataMarker(frameTime = 3)
	public static final Supplier<TeslaHoe> TESLA_HOE = ITEMS.register("tesla_hoe", TeslaHoe::new);
	public static final Supplier<SwordItem> COBALT_SWORD = ITEMS.register("cobalt_sword", () -> new SwordItem(CustomTiers.COBALT, new Properties().attributes(
			SwordItem.createAttributes(CustomTiers.COBALT, 3, -2.4f)
	)));
	public static final Supplier<PickaxeItem> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe", () -> new PickaxeItem(CustomTiers.COBALT, new Properties().attributes(
			PickaxeItem.createAttributes(CustomTiers.COBALT, 1, -2.8f)
	)));
	public static final Supplier<AxeItem> COBALT_AXE = ITEMS.register("cobalt_axe", () -> new AxeItem(CustomTiers.COBALT, new Properties().attributes(
			AxeItem.createAttributes(CustomTiers.COBALT, 6, -3.1f)
	)));
	public static final Supplier<ShovelItem> COBALT_SHOVEL = ITEMS.register("cobalt_shovel", () -> new ShovelItem(CustomTiers.COBALT, new Properties().attributes(
			ShovelItem.createAttributes(CustomTiers.COBALT, 1.5f, -3.0f)
	)));
	public static final Supplier<HoeItem> COBALT_HOE = ITEMS.register("cobalt_hoe", () -> new HoeItem(CustomTiers.COBALT, new Properties().attributes(
			HoeItem.createAttributes(CustomTiers.COBALT, -2, -1.0f)
	)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusSword> VENTUS_SWORD = ITEMS.register("ventus_sword", VentusSword::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusPickaxe> VENTUS_PICKAXE = ITEMS.register("ventus_pickaxe", VentusPickaxe::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusAxe> VENTUS_AXE = ITEMS.register("ventus_axe", VentusAxe::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusShovel> VENTUS_SHOVEL = ITEMS.register("ventus_shovel", VentusShovel::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusHoe> VENTUS_HOE = ITEMS.register("ventus_hoe", VentusHoe::new);
	@TextureMetadataMarker(frameTime = 5, frames = {0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1})
	public static final Supplier<VentusStaff> VENTUS_STAFF = ITEMS.register("ventus_staff", () -> new VentusStaff(new Properties().durability(300)));
	public static final Supplier<SwordItem> ASTRAL_SWORD = ITEMS.register("astral_sword", () -> new SwordItem(CustomTiers.ASTRAL, new Properties().attributes(
			SwordItem.createAttributes(CustomTiers.ASTRAL, 3, -1.5f)
	)));
	public static final Supplier<PickaxeItem> ASTRAL_PICKAXE = ITEMS.register("astral_pickaxe", () -> new PickaxeItem(CustomTiers.ASTRAL, new Properties().attributes(
			PickaxeItem.createAttributes(CustomTiers.ASTRAL, 1, -1.9f)
	)));
	public static final Supplier<AxeItem> ASTRAL_AXE = ITEMS.register("astral_axe", () -> new AxeItem(CustomTiers.ASTRAL, new Properties().attributes(
			AxeItem.createAttributes(CustomTiers.ASTRAL, 5, -2.1f)
	)));
	public static final Supplier<ShovelItem> ASTRAL_SHOVEL = ITEMS.register("astral_shovel", () -> new ShovelItem(CustomTiers.ASTRAL, new Properties().attributes(
			ShovelItem.createAttributes(CustomTiers.ASTRAL, 1.5f, -2.1f)
	)));
	public static final Supplier<HoeItem> ASTRAL_HOE = ITEMS.register("astral_hoe", () -> new HoeItem(CustomTiers.ASTRAL, new Properties().attributes(
			HoeItem.createAttributes(CustomTiers.ASTRAL, -4, 0.8f)
	)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<SwordItem> STARSTORM_SWORD = ITEMS.register("starstorm_sword", () -> new SwordItem(CustomTiers.STARSTORM, new Properties().attributes(
			SwordItem.createAttributes(CustomTiers.STARSTORM, 3, -2.4f)
	)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<PickaxeItem> STARSTORM_PICKAXE = ITEMS.register("starstorm_pickaxe", () -> new PickaxeItem(CustomTiers.STARSTORM, new Properties().attributes(
			PickaxeItem.createAttributes(CustomTiers.STARSTORM, 1, -2.8f)
	)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<AxeItem> STARSTORM_AXE = ITEMS.register("starstorm_axe", () -> new AxeItem(CustomTiers.STARSTORM, new Properties().attributes(
			AxeItem.createAttributes(CustomTiers.STARSTORM, 5, -3.0f)
	)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<ShovelItem> STARSTORM_SHOVEL = ITEMS.register("starstorm_shovel", () -> new ShovelItem(CustomTiers.STARSTORM, new Properties().attributes(
			ShovelItem.createAttributes(CustomTiers.STARSTORM, 1.5f, -3.0f)
	)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<HoeItem> STARSTORM_HOE = ITEMS.register("starstorm_hoe", () -> new HoeItem(CustomTiers.STARSTORM, new Properties().attributes(
			HoeItem.createAttributes(CustomTiers.STARSTORM, -7, 0.0f)
	)));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<SwordItem> VOID_SWORD = ITEMS.register("void_sword", () -> new SwordItem(CustomTiers.VOID, new Properties().attributes(
			SwordItem.createAttributes(CustomTiers.VOID, 3, -1.3f)
	)));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<PickaxeItem> VOID_PICKAXE = ITEMS.register("void_pickaxe", () -> new PickaxeItem(CustomTiers.VOID, new Properties().attributes(
			PickaxeItem.createAttributes(CustomTiers.VOID, 1, -1.7f)
	)));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<AxeItem> VOID_AXE = ITEMS.register("void_axe", () -> new AxeItem(CustomTiers.VOID, new Properties().attributes(
			AxeItem.createAttributes(CustomTiers.VOID, 5, -1.9f)
	)));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<ShovelItem> VOID_SHOVEL = ITEMS.register("void_shovel", () -> new ShovelItem(CustomTiers.VOID, new Properties().attributes(
			ShovelItem.createAttributes(CustomTiers.VOID, 1.5f, -1.9f)
	)));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<HoeItem> VOID_HOE = ITEMS.register("void_hoe", () -> new HoeItem(CustomTiers.VOID, new Properties().attributes(
			HoeItem.createAttributes(CustomTiers.VOID, -9, 1.1f)
	)));
	public static final Supplier<TheSword> THE_SWORD = ITEMS.register("the_sword", TheSword::new);

	// Weapons
	public static final Supplier<PikeItem> WOODEN_PIKE = ITEMS.register("wooden_pike", () -> new PikeItem(Tiers.WOOD, new Properties().attributes(
			PikeItem.createAttributes(Tiers.WOOD, -2.6f)
	), Ingredient.of(ItemTags.PLANKS)));
	public static final Supplier<PikeItem> STONE_PIKE = ITEMS.register("stone_pike", () -> new PikeItem(Tiers.STONE, new Properties().attributes(
			PikeItem.createAttributes(Tiers.STONE, -2.6f)
	), Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)));
	public static final Supplier<PikeItem> GOLDEN_PIKE = ITEMS.register("golden_pike", () -> new PikeItem(Tiers.GOLD, new Properties().attributes(
			PikeItem.createAttributes(Tiers.GOLD, -2.6f)
	), Ingredient.of(Tags.Items.INGOTS_GOLD)));
	public static final Supplier<PikeItem> COPPER_PIKE = ITEMS.register("copper_pike", () -> new PikeItem(CustomTiers.COPPER, new Properties().attributes(
			PikeItem.createAttributes(CustomTiers.COPPER, -2.6f)
	), Ingredient.of(Tags.Items.INGOTS_COPPER)));
	public static final Supplier<PikeItem> IRON_PIKE = ITEMS.register("iron_pike", () -> new PikeItem(Tiers.IRON, new Properties().attributes(
			PikeItem.createAttributes(Tiers.IRON, -2.6f)
	), Ingredient.of(Tags.Items.INGOTS_IRON)));
	public static final Supplier<PikeItem> COBALT_PIKE = ITEMS.register("cobalt_pike", () -> new PikeItem(CustomTiers.COBALT, new Properties().attributes(
			PikeItem.createAttributes(CustomTiers.COBALT, -2.6f)
	), Ingredient.of(CommonItemTagGroups.COBALT_INGOTS)));
	public static final Supplier<PikeItem> DIAMOND_PIKE = ITEMS.register("diamond_pike", () -> new PikeItem(Tiers.DIAMOND, new Properties().attributes(
			PikeItem.createAttributes(Tiers.DIAMOND, -2.6f)
	), Ingredient.of(Tags.Items.GEMS_DIAMOND)));
	public static final Supplier<PikeItem> NETHERITE_PIKE = ITEMS.register("netherite_pike", () -> new PikeItem(Tiers.NETHERITE, new Properties().fireResistant().attributes(
			PikeItem.createAttributes(Tiers.NETHERITE, -2.6f)
	), Ingredient.of(Tags.Items.INGOTS_NETHERITE)));
	public static final Supplier<MoltenPikeItem> MOLTEN_PIKE = ITEMS.register("molten_pike", () -> new MoltenPikeItem(CustomTiers.MOLTEN, new Properties().fireResistant().attributes(
			PikeItem.createAttributes(CustomTiers.MOLTEN, -2.6f)
	), Ingredient.of(IWItemTagGroups.MOLTEN_INGOTS)));
	public static final Supplier<TeslaPikeItem> TESLA_PIKE = ITEMS.register("tesla_pike", () -> new TeslaPikeItem(CustomTiers.TESLA, new Properties().fireResistant().attributes(
			PikeItem.createAttributes(CustomTiers.TESLA, -2.6f)
	), Ingredient.of(IWItemTagGroups.TESLA_INGOTS)));
	public static final Supplier<VentusPikeItem> VENTUS_PIKE = ITEMS.register("ventus_pike", () -> new VentusPikeItem(CustomTiers.VENTUS, new Properties().fireResistant().attributes(
			PikeItem.createAttributes(CustomTiers.VENTUS, -2.2f)
	), Ingredient.of(IWItemTagGroups.VENTUS_SHARDS)));
	public static final Supplier<PikeItem> ASTRAL_PIKE = ITEMS.register("astral_pike", () -> new PikeItem(CustomTiers.ASTRAL, new Properties().attributes(
			PikeItem.createAttributes(CustomTiers.ASTRAL, -1.7f)
	), Ingredient.of(IWItemTagGroups.ASTRAL_INGOTS)));
	public static final Supplier<PikeItem> STARSTORM_PIKE = ITEMS.register("starstorm_pike", () -> new PikeItem(CustomTiers.STARSTORM, new Properties().attributes(
			PikeItem.createAttributes(CustomTiers.STARSTORM, -2.6f)
	), Ingredient.of(IWItemTagGroups.STARSTORM_INGOTS)));
	public static final Supplier<PikeItem> VOID_PIKE = ITEMS.register("void_pike", () -> new PikeItem(CustomTiers.VOID, new Properties().attributes(
			PikeItem.createAttributes(CustomTiers.VOID, -1.5f)
	), Ingredient.of(IWItemTagGroups.VOID_INGOTS)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<SimplePistolItem> FLINTLOCK_PISTOL = ITEMS.register("flintlock_pistol", () -> new SimplePistolItem(new Properties().durability(499)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<SimpleShotgunItem> BLUNDERBUSS = ITEMS.register("blunderbuss", () -> new SimpleShotgunItem(new Properties().durability(449)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<MusketItem> MUSKET = ITEMS.register("musket", () -> new MusketItem(new Properties().durability(499), false));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@LanguageEntryOverride("Musket (Scope)")
	public static final Supplier<MusketItem> MUSKET_SCOPE = ITEMS.register("musket_scope", () -> new MusketItem(new Properties().durability(499), true));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<FlareGunItem> FLARE_GUN = ITEMS.register("flare_gun", () -> new FlareGunItem(new Properties().durability(399)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<HandCannonItem> HAND_CANNON = ITEMS.register("hand_cannon", () -> new HandCannonItem(new Properties().durability(199)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@LanguageEntryOverride("Dragon's Breath Cannon")
	public static final Supplier<DragonsBreathCannonItem> DRAGONS_BREATH_CANNON = ITEMS.register("dragons_breath_cannon", () -> new DragonsBreathCannonItem(new Properties().durability(249)));
	public static final Supplier<GauntletItem> WOODEN_GAUNTLET = ITEMS.register("wooden_gauntlet", () -> new GauntletItem(Tiers.WOOD, new Properties().attributes(
			GauntletItem.createAttributes(Tiers.WOOD, -2.3f)
	), 0.15f, 0, Ingredient.of(ItemTags.PLANKS)));
	public static final Supplier<GauntletItem> STONE_GAUNTLET = ITEMS.register("stone_gauntlet", () -> new GauntletItem(Tiers.STONE, new Properties().attributes(
			GauntletItem.createAttributes(Tiers.STONE, -2.3f)
	), 0.25f, 0, Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)));
	public static final Supplier<GauntletItem> GOLDEN_GAUNTLET = ITEMS.register("golden_gauntlet", () -> new GauntletItem(Tiers.GOLD, new Properties().attributes(
			GauntletItem.createAttributes(Tiers.GOLD, -2.3f)
	), 0.35f, 0, Ingredient.of(Tags.Items.INGOTS_GOLD)));
	public static final Supplier<GauntletItem> COPPER_GAUNTLET = ITEMS.register("copper_gauntlet", () -> new GauntletItem(CustomTiers.COPPER, new Properties().attributes(
			GauntletItem.createAttributes(CustomTiers.COPPER, -2.3f)
	), 0.45f, 0, Ingredient.of(Tags.Items.INGOTS_COPPER)));
	public static final Supplier<GauntletItem> IRON_GAUNTLET = ITEMS.register("iron_gauntlet", () -> new GauntletItem(Tiers.IRON, new Properties().attributes(
			GauntletItem.createAttributes(Tiers.IRON, -2.3f)
	), 0.55f, 0, Ingredient.of(Tags.Items.INGOTS_IRON)));
	public static final Supplier<GauntletItem> COBALT_GAUNTLET = ITEMS.register("cobalt_gauntlet", () -> new GauntletItem(CustomTiers.COBALT, new Properties().attributes(
			GauntletItem.createAttributes(CustomTiers.COBALT, -2.3f)
	), 0.60f, 0, Ingredient.of(CommonItemTagGroups.COBALT_INGOTS)));
	public static final Supplier<GauntletItem> DIAMOND_GAUNTLET = ITEMS.register("diamond_gauntlet", () -> new GauntletItem(Tiers.DIAMOND, new Properties().attributes(
			GauntletItem.createAttributes(Tiers.DIAMOND, -2.3f)
	), 0.75f, 1, Ingredient.of(Tags.Items.GEMS_DIAMOND)));
	public static final Supplier<GauntletItem> NETHERITE_GAUNTLET = ITEMS.register("netherite_gauntlet", () -> new GauntletItem(Tiers.NETHERITE, new Properties().attributes(
			GauntletItem.createAttributes(Tiers.NETHERITE, -2.3f)
	), 0.85f, 1, Ingredient.of(Tags.Items.INGOTS_NETHERITE)));
	public static final Supplier<MoltenGauntletItem> MOLTEN_GAUNTLET = ITEMS.register("molten_gauntlet", () -> new MoltenGauntletItem(CustomTiers.MOLTEN, new Properties().attributes(
			GauntletItem.createAttributes(CustomTiers.MOLTEN, -2.3f)
	), 0.95f, 2, Ingredient.of(IWItemTagGroups.MOLTEN_INGOTS)));
	public static final Supplier<TeslaGauntletItem> TESLA_GAUNTLET = ITEMS.register("tesla_gauntlet", () -> new TeslaGauntletItem(CustomTiers.TESLA, new Properties().attributes(
			GauntletItem.createAttributes(CustomTiers.TESLA, -2.3f)
	), 0.95f, 3, Ingredient.of(IWItemTagGroups.TESLA_INGOTS)));
	public static final Supplier<VentusGauntletItem> VENTUS_GAUNTLET = ITEMS.register("ventus_gauntlet", () -> new VentusGauntletItem(CustomTiers.VENTUS, new Properties().attributes(
			GauntletItem.createAttributes(CustomTiers.VENTUS, -1.9f)
	), 0.95f, 2, Ingredient.of(IWItemTagGroups.VENTUS_SHARDS)));
	public static final Supplier<GauntletItem> ASTRAL_GAUNTLET = ITEMS.register("astral_gauntlet", () -> new GauntletItem(CustomTiers.ASTRAL, new Properties().attributes(
			GauntletItem.createAttributes(CustomTiers.ASTRAL, -1.4f)
	), 0.95f, 2, Ingredient.of(IWItemTagGroups.ASTRAL_INGOTS)));
	public static final Supplier<GauntletItem> STARSTORM_GAUNTLET = ITEMS.register("starstorm_gauntlet", () -> new GauntletItem(CustomTiers.STARSTORM, new Properties().attributes(
			GauntletItem.createAttributes(CustomTiers.STARSTORM, -2.3f)
	), 0.95f, 2, Ingredient.of(IWItemTagGroups.STARSTORM_INGOTS)));
	public static final Supplier<GauntletItem> VOID_GAUNTLET = ITEMS.register("void_gauntlet", () -> new GauntletItem(CustomTiers.VOID, new Properties().attributes(
			GauntletItem.createAttributes(CustomTiers.VOID, -1.2f)
	), 0.95f, 3, Ingredient.of(IWItemTagGroups.VOID_INGOTS)));
	public static final Supplier<MeteorStaffItem> METEOR_STAFF = ITEMS.register("meteor_staff", () -> new MeteorStaffItem(new Properties().durability(199)));
	public static final Supplier<CursedSightStaffItem> CURSED_SIGHT_STAFF = ITEMS.register("cursed_sight_staff", () -> new CursedSightStaffItem(new Properties().durability(149)));
	@TextureMetadataMarker(frameTime = 3)
	public static final Supplier<SculkStaffItem> SCULK_STAFF = ITEMS.register("sculk_staff", () -> new SculkStaffItem(new Properties().durability(129)));
	public static final Supplier<RecoveryStaffItem> RECOVERY_STAFF = ITEMS.register("recovery_staff", () -> new RecoveryStaffItem(new Properties().durability(399)));
	public static final Supplier<IceBowItem> ICE_BOW = ITEMS.register("ice_bow", () -> new IceBowItem(new Properties().durability(149)));
	@LanguageEntryOverride("Dragon's Breath Bow")
	public static final Supplier<DragonBreathBow> DRAGONS_BREATH_BOW = ITEMS.register("dragons_breath_bow", () -> new DragonBreathBow(new Properties().durability(99)));
	public static final Supplier<AuroraBow> AURORA_BOW = ITEMS.register("aurora_bow", () -> new AuroraBow(new Properties().durability(299)));

	// Items
	public static final Supplier<Item> WOODEN_SHARD = ITEMS.register("wooden_shard", () -> new Item(new Properties()));
	public static final Supplier<Item> STONE_SHARD = ITEMS.register("stone_shard", () -> new Item(new Properties()));
	public static final Supplier<Item> VENTUS_SHARD = ITEMS.register("ventus_shard", () -> new Item(new Properties()));
	@TextureMetadataMarker(frameTime = 6)
	public static final Supplier<Item> MOLTEN_SHARD = ITEMS.register("molten_shard", () -> new Item(new Properties().fireResistant()));
	public static final Supplier<Item> STARSTORM_SHARD = ITEMS.register("starstorm_shard", () -> new Item(new Properties()));
	public static final Supplier<Item> OBSIDIAN_SHARD = ITEMS.register("obsidian_shard", () -> new Item(new Properties()));
	public static final Supplier<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Properties()));
	public static final Supplier<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () -> new Item(new Properties()));
	public static final Supplier<Item> WOODEN_TOOL_ROD = ITEMS.register("wooden_tool_rod", () -> new Item(new Properties()));
	public static final Supplier<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget", () -> new Item(new Properties()));
	public static final Supplier<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Properties()));
	public static final Supplier<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Properties()));
	public static final Supplier<Item> RAW_COBALT = ITEMS.register("raw_cobalt", () -> new Item(new Properties()));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<Item> STARSTORM_INGOT = ITEMS.register("starstorm_ingot", () -> new Item(new Properties()));
	public static final Supplier<Item> ASTRAL_INGOT = ITEMS.register("astral_ingot", () -> new Item(new Properties()));
	public static final Supplier<Item> ASTRAL_NUGGET = ITEMS.register("astral_nugget", () -> new Item(new Properties()));
	public static final Supplier<Item> RAW_ASTRAL = ITEMS.register("raw_astral", () -> new Item(new Properties()));
	@TextureMetadataMarker
	public static final Supplier<Item> TESLA_INGOT = ITEMS.register("tesla_ingot", () -> new Item(new Properties()));
	public static final Supplier<Item> TESLA_NUGGET = ITEMS.register("tesla_nugget", () -> new Item(new Properties()));
	public static final Supplier<Item> ELECTRIC_INGOT = ITEMS.register("electric_ingot", () -> new Item(new Properties()));
	public static final Supplier<Item> CONDUCTIVE_ALLOY = ITEMS.register("conductive_alloy", () -> new Item(new Properties()));
	public static final Supplier<Item> MOLTEN_INGOT = ITEMS.register("molten_ingot", () -> new FuelItem(new Properties().fireResistant(), 24000));
	public static final Supplier<Item> MOLTEN_SMITHING_TEMPLATE = ITEMS.register("molten_smithing_template", () -> new Item(new Properties().fireResistant()));
	public static final Supplier<Item> ENDER_ESSENCE = ITEMS.register("ender_essence", () -> new Item(new Properties()));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<Item> VOID_INGOT = ITEMS.register("void_ingot", () -> new Item(new Properties()));
	public static final Supplier<Item> HANSIUM_INGOT = ITEMS.register("hansium_ingot", () -> new Item(new Properties()));
	public static final Supplier<Item> BLACKPOWDER = ITEMS.register("blackpowder", () -> new Item(new Properties()));
	public static final Supplier<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Properties()));
	public static final Supplier<Item> SULFUR_DUST = ITEMS.register("sulfur_dust", () -> new Item(new Properties()));
	public static final Supplier<Item> POTASSIUM_NITRATE = ITEMS.register("potassium_nitrate", () -> new Item(new Properties()));
	public static final Supplier<Item> VENTUS_STAFF_CORE = ITEMS.register("ventus_staff_core", () -> new Item(new Properties()));
	public static final Supplier<Item> CURSED_SIGHT_STAFF_CORE = ITEMS.register("cursed_sight_staff_core", () -> new Item(new Properties()));
	public static final Supplier<Item> WARDEN_HEART = ITEMS.register("warden_heart", () -> new Item(new Properties()));
	public static final Supplier<Item> AZUL_KEYSTONE = ITEMS.register("azul_keystone", () -> new Item(new Properties()));
	public static final Supplier<Item> AZUL_KEYSTONE_FRAGMENT = ITEMS.register("azul_keystone_fragment", () -> new Item(new Properties()));
	public static final Supplier<AzulLocatorItem> AZUL_LOCATOR = ITEMS.register("azul_locator", () -> new AzulLocatorItem(new Properties().durability(1)));
	public static final Supplier<Item> CELESTIAL_FRAGMENT = ITEMS.register("celestial_fragment", () -> new Item(new Properties().fireResistant()));
	public static final Supplier<Item> BROKEN_LENS = ITEMS.register("broken_lens", () -> new Item(new Properties()));
	public static final Supplier<Item> WOODEN_PIKE_HEAD = ITEMS.register("wooden_pike_head", () -> new Item(new Properties()));
	public static final Supplier<Item> STONE_PIKE_HEAD = ITEMS.register("stone_pike_head", () -> new Item(new Properties()));
	public static final Supplier<Item> GOLDEN_PIKE_HEAD = ITEMS.register("golden_pike_head", () -> new Item(new Properties()));
	public static final Supplier<Item> COPPER_PIKE_HEAD = ITEMS.register("copper_pike_head", () -> new Item(new Properties()));
	public static final Supplier<Item> IRON_PIKE_HEAD = ITEMS.register("iron_pike_head", () -> new Item(new Properties()));
	public static final Supplier<Item> COBALT_PIKE_HEAD = ITEMS.register("cobalt_pike_head", () -> new Item(new Properties()));
	public static final Supplier<Item> DIAMOND_PIKE_HEAD = ITEMS.register("diamond_pike_head", () -> new Item(new Properties()));
	public static final Supplier<Item> TESLA_PIKE_HEAD = ITEMS.register("tesla_pike_head", () -> new Item(new Properties()));
	public static final Supplier<Item> VENTUS_PIKE_HEAD = ITEMS.register("ventus_pike_head", () -> new Item(new Properties()));
	public static final Supplier<CustomArrowItem<?>> WOODEN_ARROW = ITEMS.register("wooden_arrow", () -> new ArrowBuilder<>(new Properties(), 1.65d, EntityRegistry.WOODEN_ARROW_ENTITY).shootingVector(0.0185d, 5.8d, 7.2d).build());
	public static final Supplier<CustomArrowItem<?>> STONE_ARROW = ITEMS.register("stone_arrow", () -> new ArrowBuilder<>(new Properties(), 1.85d, EntityRegistry.STONE_ARROW_ENTITY).shootingVector(0.0175d, 2.6d, 4.3d).build());
	public static final Supplier<CustomArrowItem<?>> GOLDEN_ARROW = ITEMS.register("golden_arrow", () -> new ArrowBuilder<>(new Properties(), 2.10d, EntityRegistry.GOLDEN_ARROW_ENTITY).build());
	public static final Supplier<CustomArrowItem<?>> COPPER_ARROW = ITEMS.register("copper_arrow", () -> new ArrowBuilder<>(new Properties(), 2.15d, EntityRegistry.COPPER_ARROW_ENTITY).build());
	public static final Supplier<CustomArrowItem<?>> IRON_ARROW = ITEMS.register("iron_arrow", () -> new ArrowBuilder<>(new Properties(), 2.35d, EntityRegistry.IRON_ARROW_ENTITY).build());
	public static final Supplier<CustomArrowItem<?>> COBALT_ARROW = ITEMS.register("cobalt_arrow", () -> new ArrowBuilder<>(new Properties(), 2.55d, EntityRegistry.COBALT_ARROW_ENTITY).build());
	public static final Supplier<CustomArrowItem<?>> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new ArrowBuilder<>(new Properties(), 3.0d, EntityRegistry.DIAMOND_ARROW_ENTITY).canBeInfinite(false).pierceLevel(1).build());
	public static final Supplier<CustomArrowItem<?>> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new ArrowBuilder<>(new Properties().fireResistant(), 5.75d, EntityRegistry.NETHERITE_ARROW_ENTITY).canBeInfinite(false).pierceLevel(2).gravityModifier(0.0455d).shootingVector(0.0025d, 0.2d, 1.1d).build());
	public static final Supplier<CustomArrowItem<?>> MOLTEN_ARROW = ITEMS.register("molten_arrow", () -> new ArrowBuilder<>(new Properties().fireResistant(), 6.50d, EntityRegistry.MOLTEN_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.0455d).shootingVector(0.0025d, 0.2d, 1.0d).knockbackStrength(1).hitEffect(HitEffect.MOLTEN).build());
	public static final Supplier<CustomArrowItem<?>> TESLA_ARROW = ITEMS.register("tesla_arrow", () -> new ArrowBuilder<>(new Properties(), 7.0d, EntityRegistry.TESLA_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.0355d).shootingVector(0.0025d, 0.2d, 0.9d).hitEffect(HitEffect.TESLA).build());
	public static final Supplier<CustomArrowItem<?>> VENTUS_ARROW = ITEMS.register("ventus_arrow", () -> new ArrowBuilder<>(new Properties(), 6.5d, EntityRegistry.VENTUS_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.0355d).shootingVector(0.0025d, 0.2d, 0.9d).hitEffect(HitEffect.VENTUS).build());
	public static final Supplier<CustomArrowItem<?>> ASTRAL_ARROW = ITEMS.register("astral_arrow", () -> new ArrowBuilder<>(new Properties(), 5.50d, EntityRegistry.ASTRAL_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.01d).shootingVector(0.002d, 0.1d, 0.6d).build());
	public static final Supplier<CustomArrowItem<?>> STARSTORM_ARROW = ITEMS.register("starstorm_arrow", () -> new ArrowBuilder<>(new Properties(), 7.65d, EntityRegistry.STARSTORM_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.0355d).shootingVector(0.0025d, 0.2d, 0.9d).build());
	public static final Supplier<CustomArrowItem<?>> VOID_ARROW = ITEMS.register("void_arrow", () -> new ArrowBuilder<>(new Properties(), 8.0d, EntityRegistry.VOID_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.01d).shootingVector(0.002d, 0.1d, 0.6d).build());
	@LanguageEntryOverride("Smoke Grenade Arrow")
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW = ITEMS.register("smoke_grenade_arrow", () -> new ArrowBuilder<>(new Properties(), 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(0).build());
	@LanguageEntryOverride("Red Smoke Grenade Arrow")
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_RED = ITEMS.register("smoke_grenade_arrow_red", () -> new ArrowBuilder<>(new Properties(), 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(1).build());
	@LanguageEntryOverride("Green Smoke Grenade Arrow")
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_GREEN = ITEMS.register("smoke_grenade_arrow_green", () -> new ArrowBuilder<>(new Properties(), 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(2).build());
	@LanguageEntryOverride("Blue Smoke Grenade Arrow")
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_BLUE = ITEMS.register("smoke_grenade_arrow_blue", () -> new ArrowBuilder<>(new Properties(), 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(3).build());
	@LanguageEntryOverride("Purple Smoke Grenade Arrow")
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_PURPLE = ITEMS.register("smoke_grenade_arrow_purple", () -> new ArrowBuilder<>(new Properties(), 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(4).build());
	@LanguageEntryOverride("Yellow Smoke Grenade Arrow")
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_YELLOW = ITEMS.register("smoke_grenade_arrow_yellow", () -> new ArrowBuilder<>(new Properties(), 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(5).build());
	public static final Supplier<BulletItem<?>> WOODEN_MUSKET_BALL = ITEMS.register("wooden_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 2.0d, EntityRegistry.WOODEN_MUSKET_BALL_ENTITY).misfireChance(0.3f).gravityModifier(0.55d).shootingVector(0.0175d, 3.2d, 5.1d).build());
	public static final Supplier<BulletItem<?>> STONE_MUSKET_BALL = ITEMS.register("stone_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 2.20d, EntityRegistry.STONE_MUSKET_BALL_ENTITY).misfireChance(0.15f).gravityModifier(0.075d).shootingVector(0.0175d, 2.4d, 4.1d).build());
	public static final Supplier<BulletItem<?>> GOLDEN_MUSKET_BALL = ITEMS.register("golden_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 2.30d, EntityRegistry.GOLDEN_MUSKET_BALL_ENTITY).gravityModifier(0.03d).build());
	public static final Supplier<BulletItem<?>> COPPER_MUSKET_BALL = ITEMS.register("copper_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 2.40d, EntityRegistry.COPPER_MUSKET_BALL_ENTITY).build());
	public static final Supplier<BulletItem<?>> IRON_MUSKET_BALL = ITEMS.register("iron_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 2.65d, EntityRegistry.IRON_MUSKET_BALL_ENTITY).build());
	public static final Supplier<BulletItem<?>> COBALT_MUSKET_BALL = ITEMS.register("cobalt_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 2.90d, EntityRegistry.COBALT_MUSKET_BALL_ENTITY).build());
	public static final Supplier<BulletItem<?>> DIAMOND_MUSKET_BALL = ITEMS.register("diamond_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 3.35d, EntityRegistry.DIAMOND_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(1).gravityModifier(0.01d).shootingVector(0.0025d, 0.2d, 0.9).build());
	public static final Supplier<BulletItem<?>> NETHERITE_MUSKET_BALL = ITEMS.register("netherite_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99).fireResistant(), 6.50d, EntityRegistry.NETHERITE_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(2).gravityModifier(0.005d).shootingVector(0.002d, 0.2d, 0.7d).build());
	public static final Supplier<BulletItem<?>> MOLTEN_MUSKET_BALL = ITEMS.register("molten_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99).fireResistant(), 7.50d, EntityRegistry.MOLTEN_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.005d).knockbackStrength(1).hitEffect(HitEffect.MOLTEN).shootingVector(0.002d, 0.2d, 0.6d).build());
	public static final Supplier<BulletItem<?>> TESLA_MUSKET_BALL = ITEMS.register("tesla_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 8.0d, EntityRegistry.TESLA_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.004d).hitEffect(HitEffect.TESLA).shootingVector(0.002d, 0.2d, 0.5d).build());
	public static final Supplier<BulletItem<?>> VENTUS_MUSKET_BALL = ITEMS.register("ventus_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 7.50d, EntityRegistry.VENTUS_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.002d).hitEffect(HitEffect.VENTUS).shootingVector(0.002d, 0.2d, 0.5d).build());
	public static final Supplier<BulletItem<?>> ASTRAL_MUSKET_BALL = ITEMS.register("astral_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 6.25d, EntityRegistry.ASTRAL_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.001d).shootingVector(0.001d, 0.1d, 0.2d).build());
	public static final Supplier<BulletItem<?>> STARSTORM_MUSKET_BALL = ITEMS.register("starstorm_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 8.65d, EntityRegistry.STARSTORM_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.002d).shootingVector(0.002d, 0.2d, 0.5d).build());
	public static final Supplier<BulletItem<?>> VOID_MUSKET_BALL = ITEMS.register("void_musket_ball", () -> new BulletBuilder<>(new Properties().stacksTo(99), 9.0d, EntityRegistry.VOID_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.001d).shootingVector(0.001d, 0.1d, 0.2d).build());
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<BulletItem<?>> FLARE = ITEMS.register("flare", () -> new BulletBuilder<>(new Properties(), 0.1d, EntityRegistry.FLARE_ENTITY).gravityModifier(0.06d).build());
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<BulletItem<?>> CANNONBALL = ITEMS.register("cannonball", () -> new BulletItem.BulletBuilder<>(new Properties(), 6.0d, EntityRegistry.CANNONBALL_ENTITY).gravityModifier(0.055d).build());
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<BulletItem<?>> EXPLOSIVE_CANNONBALL = ITEMS.register("explosive_cannonball", () -> new BulletItem.BulletBuilder<>(new Properties(), 6.0d, EntityRegistry.CANNONBALL_ENTITY).isExplosive(true).build());
	public static final Supplier<DragonFireballItem> DRAGON_FIREBALL = ITEMS.register("dragon_fireball", () -> DragonFireballItem.createFromBulletBuilder(new BulletBuilder<>(new Properties(), 12.0d, EntityRegistry.DRAGON_FIREBALL_ENTITY).gravityModifier(0.001d).isExplosive(true)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<Item> MORTAR_SHELL = ITEMS.register("mortar_shell", () -> new Item(new Properties()));
	public static final Supplier<Item> GRENADE_ASSEMBLY = ITEMS.register("grenade_assembly", () -> new Item(new Properties()));
	public static final Supplier<Item> TOOL_JOINT = ITEMS.register("tool_joint", () -> new Item(new Properties()));
	public static final Supplier<Item> GAUNTLET_SCAFFOLDING = ITEMS.register("gauntlet_scaffolding", () -> new Item(new Properties()));
	public static final Supplier<Item> FLINTLOCK_ASSEMBLY = ITEMS.register("flintlock_assembly", () -> new Item(new Properties()));
	public static final Supplier<Item> TRIGGER_ASSEMBLY = ITEMS.register("trigger_assembly", () -> new Item(new Properties()));
	public static final Supplier<Item> HEAVY_WOODEN_STOCK = ITEMS.register("heavy_wooden_stock", () -> new Item(new Properties()));
	public static final Supplier<Item> WOODEN_PISTOL_HANDLE = ITEMS.register("wooden_pistol_handle", () -> new Item(new Properties()));
	public static final Supplier<Item> IRON_BARREL = ITEMS.register("iron_barrel", () -> new Item(new Properties()));
	public static final Supplier<Item> EXTENDED_IRON_BARREL = ITEMS.register("extended_iron_barrel", () -> new Item(new Properties()));
	public static final Supplier<Item> SHORT_IRON_BARREL = ITEMS.register("short_iron_barrel", () -> new Item(new Properties()));
	public static final Supplier<Item> WIDE_GOLDEN_BARREL = ITEMS.register("wide_golden_barrel", () -> new Item(new Properties()));
	public static final Supplier<Item> SCOPE_MOUNT = ITEMS.register("scope_mount", () -> new Item(new Properties()));
	public static final Supplier<Item> SCOPE = ITEMS.register("scope", () -> new Item(new Properties()));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@LanguageEntryOverride("Smoke Grenade")
	public static final Supplier<ThrowableItem> SMOKE_GRENADE = ITEMS.register("smoke_grenade", () -> new ThrowableItem(new Properties().stacksTo(16), 0));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@LanguageEntryOverride("Red Smoke Grenade")
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_RED = ITEMS.register("smoke_grenade_red", () -> new ThrowableItem(new Properties().stacksTo(16), 1));
	@LanguageEntryOverride("Green Smoke Grenade")
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_GREEN = ITEMS.register("smoke_grenade_green", () -> new ThrowableItem(new Properties().stacksTo(16), 2));
	@LanguageEntryOverride("Blue Smoke Grenade")
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_BLUE = ITEMS.register("smoke_grenade_blue", () -> new ThrowableItem(new Properties().stacksTo(16), 3));
	@LanguageEntryOverride("Purple Smoke Grenade")
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_PURPLE = ITEMS.register("smoke_grenade_purple", () -> new ThrowableItem(new Properties().stacksTo(16), 4));
	@LanguageEntryOverride("Yellow Smoke Grenade")
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_YELLOW = ITEMS.register("smoke_grenade_yellow", () -> new ThrowableItem(new Properties().stacksTo(16), 5));
	public static final Supplier<ThrowableItem> FLASHBANG = ITEMS.register("flashbang", () -> new ThrowableItem(new Properties().stacksTo(16), ThrowableType.FLASHBANG));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<ThrowableItem> MOLOTOV_COCKTAIL = ITEMS.register("molotov_cocktail", () -> new ThrowableItem(new Properties().stacksTo(16), ThrowableType.MOLOTOV));
	public static final Supplier<Item> SMOKE_POWDER = ITEMS.register("smoke_powder", () -> new Item(new Properties()));
	public static final Supplier<BasicContainerItem> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle", () -> new BasicContainerItem(new Properties()));
	public static final Supplier<PliersItem> PLIERS = ITEMS.register("pliers", () -> new PliersItem(new Properties().stacksTo(1)));
	public static final Supplier<AlcoholItem> BOTTLE_OF_ALCOHOL = ITEMS.register("bottle_of_alcohol", () -> new AlcoholItem(new Properties().stacksTo(16)));
	public static final Supplier<WineItem> BOTTLE_OF_WINE = ITEMS.register("bottle_of_wine", () -> new WineItem(new Properties().stacksTo(16)));
	public static final Supplier<ChocolateBarItem> CHOCOLATE_BAR = ITEMS.register("chocolate_bar", () -> new ChocolateBarItem(new Properties().food(FoodItemProperties.CHOCOLATE_BAR), false));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@LanguageEntryOverride("Chocolate Bar")
	public static final Supplier<ChocolateBarItem> EXPLOSIVE_CHOCOLATE_BAR = ITEMS.register("explosive_chocolate_bar", () -> new ChocolateBarItem(new Properties().food(FoodItemProperties.CHOCOLATE_BAR), true));
	@LanguageEntryOverride("Meal Ready-to-Eat (MRE)")
	public static final Supplier<Item> MRE = ITEMS.register("mre", () -> new Item(new Properties().food(FoodItemProperties.MRE)));
	public static final Supplier<Item> MOLDY_BREAD = ITEMS.register("moldy_bread", () -> new Item(new Properties().food(FoodItemProperties.MOLDY_BREAD)));
	public static final Supplier<BandageItem> BANDAGE = ITEMS.register("bandage", () -> new BandageItem(new Properties().stacksTo(16)));
	public static final Supplier<PainkillerItem> PAINKILLERS = ITEMS.register("painkillers", () -> new PainkillerItem(new Properties().stacksTo(24)));
	public static final Supplier<Item> SYRINGE = ITEMS.register("syringe", () -> new Item(new Properties().stacksTo(16)));
	public static final Supplier<MorphineItem> MORPHINE = ITEMS.register("morphine", () -> new MorphineItem(new Properties().stacksTo(16)));
	public static final Supplier<UsedSyringeItem> USED_SYRINGE = ITEMS.register("used_syringe", () -> new UsedSyringeItem(new Properties().stacksTo(16)));
	public static final Supplier<FirstAidKitItem> FIRST_AID_KIT = ITEMS.register("first_aid_kit", () -> new FirstAidKitItem(new Properties().stacksTo(8)));
	public static final Supplier<Item> CLOTH_SCRAP = ITEMS.register("cloth_scrap", () -> new Item(new Properties()));
	public static final Supplier<ThrowableItem> MUD_BALL = ITEMS.register("mud_ball", () -> new ThrowableItem(new Properties(), ThrowableType.MUD_BALL));
	public static final Supplier<CustomBoatItem> BURNED_OAK_BOAT = ITEMS.register("burned_oak_boat", () -> new CustomBoatItem(new Item.Properties().stacksTo(1)));
	public static final Supplier<CustomBoatItem> BURNED_OAK_CHEST_BOAT = ITEMS.register("burned_oak_chest_boat", () -> new CustomBoatItem(new Item.Properties().stacksTo(1)));
	public static final Supplier<CustomBoatItem> STARDUST_BOAT = ITEMS.register("stardust_boat", () -> new CustomBoatItem(new Item.Properties().stacksTo(1)));
	public static final Supplier<CustomBoatItem> STARDUST_CHEST_BOAT = ITEMS.register("stardust_chest_boat", () -> new CustomBoatItem(new Item.Properties().stacksTo(1)));
	public static final Supplier<CurseCleaningSoapItem> CURSE_CLEANING_SOAP = ITEMS.register("curse_cleaning_soap", () -> new CurseCleaningSoapItem(new Properties().stacksTo(1)));
	public static final Supplier<Item> SATCHEL = ITEMS.register("satchel", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> POWDER_HORN = ITEMS.register("powder_horn", () -> new Item(new Properties().stacksTo(1)));
	@LanguageEntryOverride("Berserker's Amulet")
	public static final Supplier<Item> BERSERKERS_AMULET = ITEMS.register("berserkers_amulet", () -> new Item(new Properties().stacksTo(1)));
	@LanguageEntryOverride("Hans' Blessing")
	public static final Supplier<Item> HANS_BLESSING = ITEMS.register("hans_blessing", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> CELESTIAL_SPIRIT = ITEMS.register("celestial_spirit", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> VOID_BLESSING = ITEMS.register("void_blessing", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> BLADEMASTER_EMBLEM = ITEMS.register("blademaster_emblem", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> DEADEYE_PENDANT = ITEMS.register("deadeye_pendant", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> BLOATED_HEART = ITEMS.register("bloated_heart", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> NETHERITE_SHIELD = ITEMS.register("netherite_shield", () -> new Item(new Properties().stacksTo(1)));
	@LanguageEntryOverride("Melee Master's Molten Glove")
	public static final Supplier<Item> MELEE_MASTERS_MOLTEN_GLOVE = ITEMS.register("melee_masters_molten_glove", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> IRON_FIST = ITEMS.register("iron_fist", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> GLOVE_OF_RAPID_SWINGING = ITEMS.register("glove_of_rapid_swinging", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> HAND_OF_DOOM = ITEMS.register("hand_of_doom", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> COPPER_RING = ITEMS.register("copper_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> IRON_RING = ITEMS.register("iron_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> COBALT_RING = ITEMS.register("cobalt_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> GOLDEN_RING = ITEMS.register("golden_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> AMETHYST_RING = ITEMS.register("amethyst_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> EMERALD_RING = ITEMS.register("emerald_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> DIAMOND_RING = ITEMS.register("diamond_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> NETHERITE_RING = ITEMS.register("netherite_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> DEATH_GEM_RING = ITEMS.register("death_gem_ring", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> MEDAL_OF_ADEQUACY = ITEMS.register("medal_of_adequacy", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> DEPTH_CHARM = ITEMS.register("depth_charm", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> REINFORCED_DEPTH_CHARM = ITEMS.register("reinforced_depth_charm", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> INSOMNIA_AMULET = ITEMS.register("insomnia_amulet", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> GOGGLES = ITEMS.register("goggles", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> LAVA_GOGGLES = ITEMS.register("lava_goggles", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> NIGHT_VISION_GOGGLES = ITEMS.register("night_vision_goggles", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> AGILITY_BRACELET = ITEMS.register("agility_bracelet", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> BLOODY_CLOTH = ITEMS.register("bloody_cloth", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> ANCIENT_SCROLL = ITEMS.register("ancient_scroll", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> HOLY_MANTLE = ITEMS.register("holy_mantle", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> VENSTRAL_JAR = ITEMS.register("venstral_jar", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> SUPER_BLANKET_CAPE = ITEMS.register("super_blanket_cape", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> MEDAL_OF_HONOR = ITEMS.register("medal_of_honor", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<Item> MEDAL_OF_DISHONOR = ITEMS.register("medal_of_dishonor", () -> new Item(new Properties().stacksTo(1)));
	public static final Supplier<CursedItem> BLOODY_SACRIFICE = ITEMS.register("bloody_sacrifice", () -> new CursedItem(new Properties().stacksTo(1).setNoRepair().durability(100), "bloody_sacrifice"));
	@LanguageEntryOverride("Jonny's Curse")
	public static final Supplier<CursedItem> JONNYS_CURSE = ITEMS.register("jonnys_curse", () -> new CursedItem(new Properties().stacksTo(1).setNoRepair().durability(100), "jonnys_curse"));
	public static final Supplier<Item> CHAMPION_KEYCARD = ITEMS.register("champion_keycard", () -> new Item(new Properties().stacksTo(1).fireResistant()));
	public static final Supplier<Item> KILL_COUNTER = ITEMS.register("kill_counter", () -> new Item(new Properties()));
	@LanguageEntryOverride("Pedestal Augment: Speed")
	public static final Supplier<Item> PEDESTAL_AUGMENT_SPEED = ITEMS.register("pedestal_augment_speed", () -> new Item(new Properties().stacksTo(4)));
	@LanguageEntryOverride("Pedestal Augment: Armor")
	public static final Supplier<Item> PEDESTAL_AUGMENT_ARMOR = ITEMS.register("pedestal_augment_armor", () -> new Item(new Properties().stacksTo(4)));
	@LanguageEntryOverride("Pedestal Augment: Enchantment")
	public static final Supplier<Item> PEDESTAL_AUGMENT_ENCHANTMENT = ITEMS.register("pedestal_augment_enchantment", () -> new Item(new Properties().stacksTo(4)));
	@LanguageEntryOverride("Pedestal Augment: Capacity")
	public static final Supplier<Item> PEDESTAL_AUGMENT_CAPACITY = ITEMS.register("pedestal_augment_capacity", () -> new Item(new Properties().stacksTo(4)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_STARLIGHT_PLAINS_THEME_1 = ITEMS.register("music_disc_starlight_plains_theme_1", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.STARLIGHT_PLAINS_THEME_1)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_STARLIGHT_PLAINS_THEME_2 = ITEMS.register("music_disc_starlight_plains_theme_2", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.STARLIGHT_PLAINS_THEME_2)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_TILTROS_WASTES_THEME = ITEMS.register("music_disc_tiltros_wastes_theme", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.TILTROS_WASTES_THEME)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_DEADMANS_DESERT_THEME_1 = ITEMS.register("music_disc_deadmans_desert_theme_1", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.DEADMANS_DESERT_THEME_1)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_DEADMANS_DESERT_THEME_2 = ITEMS.register("music_disc_deadmans_desert_theme_2", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.DEADMANS_DESERT_THEME_2)));

	// Armor
	public static final Supplier<MoltenArmorItem> MOLTEN_HELMET = ITEMS.register("molten_helmet", () -> new MoltenArmorItem(ArmorMaterialRegistry.MOLTEN, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(39)).fireResistant()));
	public static final Supplier<MoltenArmorItem> MOLTEN_CHESTPLATE = ITEMS.register("molten_chestplate", () -> new MoltenArmorItem(ArmorMaterialRegistry.MOLTEN, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(39)).fireResistant()));
	public static final Supplier<MoltenArmorItem> MOLTEN_LEGGINGS = ITEMS.register("molten_leggings", () -> new MoltenArmorItem(ArmorMaterialRegistry.MOLTEN, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(39)).fireResistant()));
	public static final Supplier<MoltenArmorItem> MOLTEN_BOOTS = ITEMS.register("molten_boots", () -> new MoltenArmorItem(ArmorMaterialRegistry.MOLTEN, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(39)).fireResistant()));
	public static final Supplier<ArmorItem> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new ArmorItem(ArmorMaterialRegistry.COPPER, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(13))));
	public static final Supplier<ArmorItem> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new ArmorItem(ArmorMaterialRegistry.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(13))));
	public static final Supplier<ArmorItem> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new ArmorItem(ArmorMaterialRegistry.COPPER, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(13))));
	public static final Supplier<ArmorItem> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new ArmorItem(ArmorMaterialRegistry.COPPER, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(13))));
	public static final Supplier<TeslaArmorItem> TESLA_HELMET = ITEMS.register("tesla_helmet", () -> new TeslaArmorItem(ArmorMaterialRegistry.TESLA, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(42))));
	public static final Supplier<TeslaArmorItem> TESLA_CHESTPLATE = ITEMS.register("tesla_chestplate", () -> new TeslaArmorItem(ArmorMaterialRegistry.TESLA, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(42))));
	public static final Supplier<TeslaArmorItem> TESLA_LEGGINGS = ITEMS.register("tesla_leggings", () -> new TeslaArmorItem(ArmorMaterialRegistry.TESLA, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(42))));
	public static final Supplier<TeslaArmorItem> TESLA_BOOTS = ITEMS.register("tesla_boots", () -> new TeslaArmorItem(ArmorMaterialRegistry.TESLA, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(42))));
	public static final Supplier<CobaltArmorItem> COBALT_HELMET = ITEMS.register("cobalt_helmet", () -> new CobaltArmorItem(ArmorMaterialRegistry.COBALT, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
	public static final Supplier<CobaltArmorItem> COBALT_CHESTPLATE = ITEMS.register("cobalt_chestplate", () -> new CobaltArmorItem(ArmorMaterialRegistry.COBALT, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(19))));
	public static final Supplier<CobaltArmorItem> COBALT_LEGGINGS = ITEMS.register("cobalt_leggings", () -> new CobaltArmorItem(ArmorMaterialRegistry.COBALT, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(19))));
	public static final Supplier<CobaltArmorItem> COBALT_BOOTS = ITEMS.register("cobalt_boots", () -> new CobaltArmorItem(ArmorMaterialRegistry.COBALT, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(19))));
	public static final Supplier<VentusArmorItem> VENTUS_HELMET = ITEMS.register("ventus_helmet", () -> new VentusArmorItem(ArmorMaterialRegistry.VENTUS, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(39))));
	public static final Supplier<VentusArmorItem> VENTUS_CHESTPLATE = ITEMS.register("ventus_chestplate", () -> new VentusArmorItem(ArmorMaterialRegistry.VENTUS, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(39))));
	public static final Supplier<VentusArmorItem> VENTUS_LEGGINGS = ITEMS.register("ventus_leggings", () -> new VentusArmorItem(ArmorMaterialRegistry.VENTUS, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(39))));
	public static final Supplier<VentusArmorItem> VENTUS_BOOTS = ITEMS.register("ventus_boots", () -> new VentusArmorItem(ArmorMaterialRegistry.VENTUS, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(39))));
	public static final Supplier<AstralArmorItem> ASTRAL_HELMET = ITEMS.register("astral_helmet", () -> new AstralArmorItem(ArmorMaterialRegistry.ASTRAL, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(25))));
	public static final Supplier<AstralArmorItem> ASTRAL_CHESTPLATE = ITEMS.register("astral_chestplate", () -> new AstralArmorItem(ArmorMaterialRegistry.ASTRAL, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(25))));
	public static final Supplier<AstralArmorItem> ASTRAL_LEGGINGS = ITEMS.register("astral_leggings", () -> new AstralArmorItem(ArmorMaterialRegistry.ASTRAL, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(25))));
	public static final Supplier<AstralArmorItem> ASTRAL_BOOTS = ITEMS.register("astral_boots", () -> new AstralArmorItem(ArmorMaterialRegistry.ASTRAL, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(25))));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<ArmorItem> STARSTORM_HELMET = ITEMS.register("starstorm_helmet", () -> new ArmorItem(ArmorMaterialRegistry.STARSTORM, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(32))));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<ArmorItem> STARSTORM_CHESTPLATE = ITEMS.register("starstorm_chestplate", () -> new ArmorItem(ArmorMaterialRegistry.STARSTORM, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(32))));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<ArmorItem> STARSTORM_LEGGINGS = ITEMS.register("starstorm_leggings", () -> new ArmorItem(ArmorMaterialRegistry.STARSTORM, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(32))));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<ArmorItem> STARSTORM_BOOTS = ITEMS.register("starstorm_boots", () -> new ArmorItem(ArmorMaterialRegistry.STARSTORM, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(32))));
	@LanguageEntryOverride("Padded Leather Cap")
	public static final Supplier<ArmorItem> PADDED_LEATHER_HELMET = ITEMS.register("padded_leather_helmet", () -> new ArmorItem(ArmorMaterialRegistry.PADDED_LEATHER, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(6))));
	@LanguageEntryOverride("Padded Leather Tunic")
	public static final Supplier<ArmorItem> PADDED_LEATHER_CHESTPLATE = ITEMS.register("padded_leather_chestplate", () -> new ArmorItem(ArmorMaterialRegistry.PADDED_LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(6))));
	@LanguageEntryOverride("Padded Leather Pants")
	public static final Supplier<ArmorItem> PADDED_LEATHER_LEGGINGS = ITEMS.register("padded_leather_leggings", () -> new ArmorItem(ArmorMaterialRegistry.PADDED_LEATHER, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(6))));
	public static final Supplier<ArmorItem> PADDED_LEATHER_BOOTS = ITEMS.register("padded_leather_boots", () -> new ArmorItem(ArmorMaterialRegistry.PADDED_LEATHER, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(6))));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<VoidArmorItem> VOID_HELMET = ITEMS.register("void_helmet", () -> new VoidArmorItem(ArmorMaterialRegistry.VOID, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(35))));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<VoidArmorItem> VOID_CHESTPLATE = ITEMS.register("void_chestplate", () -> new VoidArmorItem(ArmorMaterialRegistry.VOID, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(35))));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<VoidArmorItem> VOID_LEGGINGS = ITEMS.register("void_leggings", () -> new VoidArmorItem(ArmorMaterialRegistry.VOID, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(35))));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<VoidArmorItem> VOID_BOOTS = ITEMS.register("void_boots", () -> new VoidArmorItem(ArmorMaterialRegistry.VOID, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(35))));

	// Spawn eggs
	public static final Supplier<DeferredSpawnEggItem> DYING_SOLDIER_SPAWN_EGG = ITEMS.register("dying_soldier_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.DYING_SOLDIER_ENTITY, 0x7a6851, 0x783d22, (new Item.Properties()).stacksTo(16)));
	public static final Supplier<DeferredSpawnEggItem> THE_COMMANDER_SPAWN_EGG = ITEMS.register("the_commander_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.THE_COMMANDER_ENTITY, 0x7a6851, 0x783d22, (new Item.Properties()).stacksTo(16)));
	public static final Supplier<DeferredSpawnEggItem> MINUTEMAN_SPAWN_EGG = ITEMS.register("minuteman_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.MINUTEMAN_ENTITY, 0x494522, 0x204b2a, (new Item.Properties()).stacksTo(16)));
	public static final Supplier<DeferredSpawnEggItem> FIELD_MEDIC_SPAWN_EGG = ITEMS.register("field_medic_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.FIELD_MEDIC_ENTITY, 0xde5451, 0xebe4d2, (new Item.Properties()).stacksTo(16)));
	public static final Supplier<DeferredSpawnEggItem> WANDERING_WARRIOR_SPAWN_EGG = ITEMS.register("wandering_warrior_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.WANDERING_WARRIOR_ENTITY, 0x614226, 0x2e6278, (new Item.Properties()).stacksTo(16)));
	@LanguageEntryOverride("Hans the Almighty Spawn Egg")
	public static final Supplier<DeferredSpawnEggItem> HANS_SPAWN_EGG = ITEMS.register("hans_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.HANS_ENTITY, 0xd0a873, 0xafafaf, (new Item.Properties().stacksTo(16))));
	@LanguageEntryOverride("Super Hans the Almighty Spawn Egg")
	public static final Supplier<DeferredSpawnEggItem> SUPER_HANS_SPAWN_EGG = ITEMS.register("super_hans_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.SUPER_HANS_ENTITY, 0xd0a873, 0xafafaf, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> LAVA_REVENANT_SPAWN_EGG = ITEMS.register("lava_revenant_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.LAVA_REVENANT_ENTITY, 0x640000, 0x990000, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> ROCK_SPIDER_SPAWN_EGG = ITEMS.register("rock_spider_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.ROCK_SPIDER_ENTITY, 0x7f7f7f, 0xa80e0e, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> CELESTIAL_TOWER_SPAWN_EGG = ITEMS.register("celestial_tower_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.CELESTIAL_TOWER_ENTITY, 0x63353d, 0xb3754b, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> STARMITE_SPAWN_EGG = ITEMS.register("starmite_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.STARMITE_ENTITY, 0x8f4f1c, 0xa55c1e, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> FIREFLY_SPAWN_EGG = ITEMS.register("firefly_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.FIREFLY_ENTITY, 0x703a2a, 0x43e88d, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> STORM_CREEPER_SPAWN_EGG = ITEMS.register("storm_creeper_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.STORM_CREEPER_ENTITY, 0xfe162c, 0x00eaf6, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> EVIL_EYE_SPAWN_EGG = ITEMS.register("evil_eye_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.EVIL_EYE_ENTITY, 0xd7d7d7, 0x4e8386, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> STAR_WOLF_SPAWN_EGG = ITEMS.register("star_wolf_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.STAR_WOLF_ENTITY, 0x0b0707, 0x919191, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> SKYGAZER_SPAWN_EGG = ITEMS.register("skygazer_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.SKYGAZER_ENTITY, 0x4c1036, 0x4c1036, (new Item.Properties().stacksTo(16))));
	public static final Supplier<DeferredSpawnEggItem> SKELETON_MERCHANT_SPAWN_EGG = ITEMS.register("skeleton_merchant_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.SKELETON_MERCHANT_ENTITY, 0x798580, 0x832121, (new Item.Properties().stacksTo(16))));
}