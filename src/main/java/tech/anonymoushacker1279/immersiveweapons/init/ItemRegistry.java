package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.ChatFormatting;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.tooltip.DynamicTooltips;
import tech.anonymoushacker1279.immersiveweapons.data.IWJukeboxSongs;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.item.*;
import tech.anonymoushacker1279.immersiveweapons.item.armor.*;
import tech.anonymoushacker1279.immersiveweapons.item.bow.AuroraBow;
import tech.anonymoushacker1279.immersiveweapons.item.bow.DragonBreathBow;
import tech.anonymoushacker1279.immersiveweapons.item.bow.IceBowItem;
import tech.anonymoushacker1279.immersiveweapons.item.fortitude.*;
import tech.anonymoushacker1279.immersiveweapons.item.gun.*;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWArmorMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.potion.AlcoholItem;
import tech.anonymoushacker1279.immersiveweapons.item.potion.WineItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem.BulletBuilder;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem.ArrowBuilder;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.DragonFireballItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem.ThrowableType;
import tech.anonymoushacker1279.immersiveweapons.item.tool.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils.HitEffect;
import tech.anonymoushacker1279.immersiveweapons.item.tool.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.tool.PliersItem;
import tech.anonymoushacker1279.immersiveweapons.item.tool.TheSword;
import tech.anonymoushacker1279.immersiveweapons.item.tool.molten.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.tesla.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.ventus.*;
import tech.anonymoushacker1279.immersiveweapons.item.utility.CraftingToolItem;
import tech.anonymoushacker1279.immersiveweapons.item.utility.FuelItem;
import tech.anonymoushacker1279.immersiveweapons.util.markers.DatagenExclusionMarker;
import tech.anonymoushacker1279.immersiveweapons.util.markers.DatagenExclusionMarker.Type;
import tech.anonymoushacker1279.immersiveweapons.util.markers.LanguageEntryOverride;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TextureMetadataMarker;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TextureMetadataMarker.PredefinedGroups;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TooltipMarker;
import tech.anonymoushacker1279.immersiveweapons.world.food.FoodItemProperties;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ItemRegistry {

	// Item Register
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ImmersiveWeapons.MOD_ID);

	// Tools
	@TextureMetadataMarker(frameTime = 2)
	@TooltipMarker(style = {ChatFormatting.DARK_RED, ChatFormatting.ITALIC})
	public static final Supplier<MoltenSword> MOLTEN_SWORD = ITEMS.registerItem("molten_sword", MoltenSword::new);
	@TextureMetadataMarker(frameTime = 5)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "molten_tool")
	public static final Supplier<MoltenPickaxe> MOLTEN_PICKAXE = ITEMS.registerItem("molten_pickaxe", MoltenPickaxe::new);
	@TextureMetadataMarker(frameTime = 5)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "molten_tool")
	public static final Supplier<MoltenAxe> MOLTEN_AXE = ITEMS.registerItem("molten_axe", MoltenAxe::new);
	@TextureMetadataMarker(frameTime = 5)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "molten_tool")
	public static final Supplier<MoltenShovel> MOLTEN_SHOVEL = ITEMS.registerItem("molten_shovel", MoltenShovel::new);
	@TextureMetadataMarker(frameTime = 5)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "molten_tool")
	public static final Supplier<MoltenHoe> MOLTEN_HOE = ITEMS.registerItem("molten_hoe", MoltenHoe::new);
	public static final Supplier<Item> COPPER_SWORD = ITEMS.registerItem("copper_sword", (properties) -> new Item(properties.sword(IWToolMaterials.COPPER, 3, -2.4f)));
	public static final Supplier<Item> COPPER_PICKAXE = ITEMS.registerItem("copper_pickaxe", (properties) -> new Item(properties.pickaxe(IWToolMaterials.COPPER, 1, -2.8f)));
	public static final Supplier<AxeItem> COPPER_AXE = ITEMS.registerItem("copper_axe", (properties) -> new AxeItem(IWToolMaterials.COPPER, 6, -3.1f, properties));
	public static final Supplier<ShovelItem> COPPER_SHOVEL = ITEMS.registerItem("copper_shovel", (properties) -> new ShovelItem(IWToolMaterials.COPPER, 1.5f, -3.0f, properties));
	public static final Supplier<HoeItem> COPPER_HOE = ITEMS.registerItem("copper_hoe", (properties) -> new HoeItem(IWToolMaterials.COPPER, -1, -1.0f, properties));
	@TextureMetadataMarker(frameTime = 2)
	@TooltipMarker(style = {ChatFormatting.AQUA, ChatFormatting.ITALIC})
	public static final Supplier<TeslaSword> TESLA_SWORD = ITEMS.registerItem("tesla_sword", TeslaSword::new);
	@TextureMetadataMarker(frameTime = 2)
	public static final Supplier<TeslaPickaxe> TESLA_PICKAXE = ITEMS.registerItem("tesla_pickaxe", TeslaPickaxe::new);
	@TextureMetadataMarker(frameTime = 2)
	public static final Supplier<TeslaAxe> TESLA_AXE = ITEMS.registerItem("tesla_axe", TeslaAxe::new);
	@TextureMetadataMarker(frameTime = 3)
	public static final Supplier<TeslaShovel> TESLA_SHOVEL = ITEMS.registerItem("tesla_shovel", TeslaShovel::new);
	@TextureMetadataMarker(frameTime = 3)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<TeslaHoe> TESLA_HOE = ITEMS.registerItem("tesla_hoe", TeslaHoe::new);
	public static final Supplier<Item> COBALT_SWORD = ITEMS.registerItem("cobalt_sword", (properties) -> new Item(properties.sword(IWToolMaterials.COBALT, 3, -2.4f)));
	public static final Supplier<Item> COBALT_PICKAXE = ITEMS.registerItem("cobalt_pickaxe", (properties) -> new Item(properties.pickaxe(IWToolMaterials.COBALT, 1, -2.8f)));
	public static final Supplier<AxeItem> COBALT_AXE = ITEMS.registerItem("cobalt_axe", (properties) -> new AxeItem(IWToolMaterials.COBALT, 6, -3.1f, properties));
	public static final Supplier<ShovelItem> COBALT_SHOVEL = ITEMS.registerItem("cobalt_shovel", (properties) -> new ShovelItem(IWToolMaterials.COBALT, 1.5f, -3.0f, properties));
	public static final Supplier<HoeItem> COBALT_HOE = ITEMS.registerItem("cobalt_hoe", (properties) -> new HoeItem(IWToolMaterials.COBALT, -2, -1.0f, properties));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	@TooltipMarker(style = {ChatFormatting.GRAY, ChatFormatting.ITALIC})
	public static final Supplier<VentusSword> VENTUS_SWORD = ITEMS.registerItem("ventus_sword", VentusSword::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusPickaxe> VENTUS_PICKAXE = ITEMS.registerItem("ventus_pickaxe", VentusPickaxe::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusAxe> VENTUS_AXE = ITEMS.registerItem("ventus_axe", VentusAxe::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusShovel> VENTUS_SHOVEL = ITEMS.registerItem("ventus_shovel", VentusShovel::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.VENTUS_TOOLS)
	public static final Supplier<VentusHoe> VENTUS_HOE = ITEMS.registerItem("ventus_hoe", VentusHoe::new);
	@TextureMetadataMarker(frameTime = 5, frames = {0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1})
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<VentusStaff> VENTUS_STAFF = ITEMS.registerItem("ventus_staff", (properties) -> new VentusStaff(properties.durability(300)));
	@TooltipMarker(style = {ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC})
	public static final Supplier<Item> ASTRAL_SWORD = ITEMS.registerItem("astral_sword", (properties) -> new Item(properties.sword(IWToolMaterials.ASTRAL, 3, -1.5f)));
	public static final Supplier<Item> ASTRAL_PICKAXE = ITEMS.registerItem("astral_pickaxe", (properties) -> new Item(properties.pickaxe(IWToolMaterials.ASTRAL, 1, -1.9f)));
	public static final Supplier<AxeItem> ASTRAL_AXE = ITEMS.registerItem("astral_axe", (properties) -> new AxeItem(IWToolMaterials.ASTRAL, 5, -2.1f, properties));
	public static final Supplier<ShovelItem> ASTRAL_SHOVEL = ITEMS.registerItem("astral_shovel", (properties) -> new ShovelItem(IWToolMaterials.ASTRAL, 1.5f, -2.1f, properties));
	public static final Supplier<HoeItem> ASTRAL_HOE = ITEMS.registerItem("astral_hoe", (properties) -> new HoeItem(IWToolMaterials.ASTRAL, -4, 0.8f, properties));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	@TooltipMarker(style = {ChatFormatting.RED, ChatFormatting.ITALIC})
	public static final Supplier<Item> STARSTORM_SWORD = ITEMS.registerItem("starstorm_sword", (properties) -> new Item(properties.sword(IWToolMaterials.STARSTORM, 3, -2.4f)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<Item> STARSTORM_PICKAXE = ITEMS.registerItem("starstorm_pickaxe", (properties) -> new Item(properties.sword(IWToolMaterials.STARSTORM, 1, -2.8f)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<AxeItem> STARSTORM_AXE = ITEMS.registerItem("starstorm_axe", (properties) -> new AxeItem(IWToolMaterials.STARSTORM, 5, -3.0f, properties));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<ShovelItem> STARSTORM_SHOVEL = ITEMS.registerItem("starstorm_shovel", (properties) -> new ShovelItem(IWToolMaterials.STARSTORM, 1.5f, -3.0f, properties));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<HoeItem> STARSTORM_HOE = ITEMS.registerItem("starstorm_hoe", (properties) -> new HoeItem(IWToolMaterials.STARSTORM, -7, 0.0f, properties));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	@TooltipMarker(style = {ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC})
	public static final Supplier<Item> VOID_SWORD = ITEMS.registerItem("void_sword", (properties) -> new Item(properties.sword(IWToolMaterials.VOID, 3, -1.3f)));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<Item> VOID_PICKAXE = ITEMS.registerItem("void_pickaxe", (properties) -> new Item(properties.pickaxe(IWToolMaterials.VOID, 1, -1.7f)));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<AxeItem> VOID_AXE = ITEMS.registerItem("void_axe", (properties) -> new AxeItem(IWToolMaterials.VOID, 5, -1.9f, properties));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<ShovelItem> VOID_SHOVEL = ITEMS.registerItem("void_shovel", (properties) -> new ShovelItem(IWToolMaterials.VOID, 1.5f, -1.9f, properties));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<HoeItem> VOID_HOE = ITEMS.registerItem("void_hoe", (properties) -> new HoeItem(IWToolMaterials.VOID, -9, 1.1f, properties));
	public static final Supplier<TheSword> THE_SWORD = ITEMS.registerItem("the_sword", TheSword::new);

	// Weapons
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> WOODEN_PIKE = ITEMS.registerItem("wooden_pike", (properties) -> new PikeItem(ToolMaterial.WOOD, -2.6f, properties.repairable(ItemTags.PLANKS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> STONE_PIKE = ITEMS.registerItem("stone_pike", (properties) -> new PikeItem(ToolMaterial.STONE, -2.6f, properties.repairable(ItemTags.STONE_TOOL_MATERIALS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> GOLDEN_PIKE = ITEMS.registerItem("golden_pike", (properties) -> new PikeItem(ToolMaterial.GOLD, -2.6f, properties.repairable(Tags.Items.INGOTS_GOLD)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> COPPER_PIKE = ITEMS.registerItem("copper_pike", (properties) -> new PikeItem(IWToolMaterials.COPPER, -2.6f, properties.repairable(Tags.Items.INGOTS_COPPER)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> IRON_PIKE = ITEMS.registerItem("iron_pike", (properties) -> new PikeItem(ToolMaterial.IRON, -2.6f, properties.repairable(Tags.Items.INGOTS_IRON)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> COBALT_PIKE = ITEMS.registerItem("cobalt_pike", (properties) -> new PikeItem(IWToolMaterials.COBALT, -2.6f, properties.repairable(CommonItemTagGroups.COBALT_INGOTS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> DIAMOND_PIKE = ITEMS.registerItem("diamond_pike", (properties) -> new PikeItem(ToolMaterial.DIAMOND, -2.6f, properties.repairable(Tags.Items.GEMS_DIAMOND)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> NETHERITE_PIKE = ITEMS.registerItem("netherite_pike", (properties) -> new PikeItem(ToolMaterial.NETHERITE, -2.6f, properties.fireResistant().repairable(Tags.Items.INGOTS_NETHERITE)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> MOLTEN_PIKE = ITEMS.registerItem("molten_pike", (properties) -> new PikeItem(IWToolMaterials.MOLTEN, -2.6f, properties.fireResistant().repairable(IWItemTagGroups.MOLTEN_INGOTS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> TESLA_PIKE = ITEMS.registerItem("tesla_pike", (properties) -> new PikeItem(IWToolMaterials.TESLA, -2.6f, properties.fireResistant().repairable(IWItemTagGroups.TESLA_INGOTS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> VENTUS_PIKE = ITEMS.registerItem("ventus_pike", (properties) -> new PikeItem(IWToolMaterials.VENTUS, -2.2f, properties.fireResistant().repairable(IWItemTagGroups.VENTUS_SHARDS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> ASTRAL_PIKE = ITEMS.registerItem("astral_pike", (properties) -> new PikeItem(IWToolMaterials.ASTRAL, -1.7f, properties.repairable(IWItemTagGroups.ASTRAL_INGOTS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> STARSTORM_PIKE = ITEMS.registerItem("starstorm_pike", (properties) -> new PikeItem(IWToolMaterials.STARSTORM, -2.6f, properties.repairable(IWItemTagGroups.STARSTORM_INGOTS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "pike")
	public static final Supplier<PikeItem> VOID_PIKE = ITEMS.registerItem("void_pike", (properties) -> new PikeItem(IWToolMaterials.VOID, -1.5f, properties.repairable(IWItemTagGroups.VOID_INGOTS)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.FirearmTooltip.class)
	public static final Supplier<SimplePistolItem> FLINTLOCK_PISTOL = ITEMS.registerItem("flintlock_pistol", (properties) -> new SimplePistolItem(properties.durability(499).enchantable(1)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.FirearmTooltip.class)
	public static final Supplier<SimpleShotgunItem> BLUNDERBUSS = ITEMS.registerItem("blunderbuss", (properties) -> new SimpleShotgunItem(properties.durability(449).enchantable(3)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.FirearmTooltip.class)
	public static final Supplier<MusketItem> MUSKET = ITEMS.registerItem("musket", (properties) -> new MusketItem(properties.durability(499).enchantable(2)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, components = 2, dynamicTooltip = DynamicTooltips.FirearmTooltip.class)
	public static final Supplier<FlareGunItem> FLARE_GUN = ITEMS.registerItem("flare_gun", (properties) -> new FlareGunItem(properties.durability(399).enchantable(1)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.FirearmTooltip.class)
	public static final Supplier<HandCannonItem> HAND_CANNON = ITEMS.registerItem("hand_cannon", (properties) -> new HandCannonItem(properties.durability(199).enchantable(1)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@LanguageEntryOverride("Dragon's Breath Cannon")
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.FirearmTooltip.class)
	public static final Supplier<DragonsBreathCannonItem> DRAGONS_BREATH_CANNON = ITEMS.registerItem("dragons_breath_cannon", (properties) -> new DragonsBreathCannonItem(properties.durability(249).enchantable(1)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> WOODEN_GAUNTLET = ITEMS.registerItem("wooden_gauntlet", (properties) -> new GauntletItem(ToolMaterial.WOOD, -2.3f, 0.15f, 0, properties.repairable(ItemTags.PLANKS)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> STONE_GAUNTLET = ITEMS.registerItem("stone_gauntlet", (properties) -> new GauntletItem(ToolMaterial.STONE, -2.3f, 0.25f, 0, properties.repairable(ItemTags.STONE_TOOL_MATERIALS)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> GOLDEN_GAUNTLET = ITEMS.registerItem("golden_gauntlet", (properties) -> new GauntletItem(ToolMaterial.GOLD, -2.3f, 0.35f, 0, properties.repairable(Tags.Items.INGOTS_GOLD)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> COPPER_GAUNTLET = ITEMS.registerItem("copper_gauntlet", (properties) -> new GauntletItem(IWToolMaterials.COPPER, -2.3f, 0.45f, 0, properties.repairable(Tags.Items.INGOTS_COPPER)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> IRON_GAUNTLET = ITEMS.registerItem("iron_gauntlet", (properties) -> new GauntletItem(ToolMaterial.IRON, -2.3f, 0.55f, 0, properties.repairable(Tags.Items.INGOTS_IRON)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> COBALT_GAUNTLET = ITEMS.registerItem("cobalt_gauntlet", (properties) -> new GauntletItem(IWToolMaterials.COBALT, -2.3f, 0.60f, 0, properties.repairable(CommonItemTagGroups.COBALT_INGOTS)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> DIAMOND_GAUNTLET = ITEMS.registerItem("diamond_gauntlet", (properties) -> new GauntletItem(ToolMaterial.DIAMOND, -2.3f, 0.75f, 1, properties.repairable(Tags.Items.GEMS_DIAMOND)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> NETHERITE_GAUNTLET = ITEMS.registerItem("netherite_gauntlet", (properties) -> new GauntletItem(ToolMaterial.NETHERITE, -2.3f, 0.85f, 1, properties.repairable(Tags.Items.INGOTS_NETHERITE)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> MOLTEN_GAUNTLET = ITEMS.registerItem("molten_gauntlet", (properties) -> new GauntletItem(IWToolMaterials.MOLTEN, -2.3f, 0.95f, 2, properties.repairable(IWItemTagGroups.MOLTEN_INGOTS)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> TESLA_GAUNTLET = ITEMS.registerItem("tesla_gauntlet", (properties) -> new GauntletItem(IWToolMaterials.TESLA, -2.3f, 0.95f, 3, properties.repairable(IWItemTagGroups.TESLA_INGOTS)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> VENTUS_GAUNTLET = ITEMS.registerItem("ventus_gauntlet", (properties) -> new GauntletItem(IWToolMaterials.VENTUS, -1.9f, 0.95f, 2, properties.repairable(IWItemTagGroups.VENTUS_SHARDS)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> ASTRAL_GAUNTLET = ITEMS.registerItem("astral_gauntlet", (properties) -> new GauntletItem(IWToolMaterials.ASTRAL, -1.4f, 0.95f, 2, properties.repairable(IWItemTagGroups.ASTRAL_INGOTS)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> STARSTORM_GAUNTLET = ITEMS.registerItem("starstorm_gauntlet", (properties) -> new GauntletItem(IWToolMaterials.STARSTORM, -2.3f, 0.95f, 2, properties.repairable(IWItemTagGroups.STARSTORM_INGOTS)));
	@TooltipMarker(dynamicTooltip = DynamicTooltips.GauntletTooltip.class)
	public static final Supplier<GauntletItem> VOID_GAUNTLET = ITEMS.registerItem("void_gauntlet", (properties) -> new GauntletItem(IWToolMaterials.VOID, -1.2f, 0.95f, 3, properties.repairable(IWItemTagGroups.VOID_INGOTS)));
	@TooltipMarker(style = {ChatFormatting.GOLD, ChatFormatting.ITALIC})
	public static final Supplier<MeteorStaffItem> METEOR_STAFF = ITEMS.registerItem("meteor_staff", (properties) -> new MeteorStaffItem(properties.durability(199).enchantable(1)));
	@TooltipMarker(style = {ChatFormatting.DARK_RED, ChatFormatting.ITALIC})
	public static final Supplier<CursedSightStaffItem> CURSED_SIGHT_STAFF = ITEMS.registerItem("cursed_sight_staff", (properties) -> new CursedSightStaffItem(properties.durability(149).enchantable(1)));
	@TextureMetadataMarker(frameTime = 3)
	@TooltipMarker(style = {ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC})
	public static final Supplier<SculkStaffItem> SCULK_STAFF = ITEMS.registerItem("sculk_staff", (properties) -> new SculkStaffItem(properties.durability(129).enchantable(1)));
	public static final Supplier<RecoveryStaffItem> RECOVERY_STAFF = ITEMS.registerItem("recovery_staff", (properties) -> new RecoveryStaffItem(properties.durability(399).enchantable(1)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<IceBowItem> ICE_BOW = ITEMS.registerItem("ice_bow", (properties) -> new IceBowItem(properties.durability(149).enchantable(1)));
	@LanguageEntryOverride("Dragon's Breath Bow")
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<DragonBreathBow> DRAGONS_BREATH_BOW = ITEMS.registerItem("dragons_breath_bow", (properties) -> new DragonBreathBow(properties.durability(99).enchantable(1)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<AuroraBow> AURORA_BOW = ITEMS.registerItem("aurora_bow", (properties) -> new AuroraBow(properties.durability(299).enchantable(1)));

	// Items
	public static final Supplier<Item> WOODEN_SHARD = ITEMS.registerItem("wooden_shard", Item::new);
	public static final Supplier<Item> STONE_SHARD = ITEMS.registerItem("stone_shard", Item::new);
	public static final Supplier<Item> VENTUS_SHARD = ITEMS.registerItem("ventus_shard", Item::new);
	@TextureMetadataMarker(frameTime = 6)
	public static final Supplier<Item> MOLTEN_SHARD = ITEMS.registerItem("molten_shard", (properties) -> new Item(properties.fireResistant()));
	public static final Supplier<Item> STARSTORM_SHARD = ITEMS.registerItem("starstorm_shard", Item::new);
	public static final Supplier<Item> OBSIDIAN_SHARD = ITEMS.registerItem("obsidian_shard", Item::new);
	public static final Supplier<Item> DIAMOND_SHARD = ITEMS.registerItem("diamond_shard", Item::new);
	public static final Supplier<Item> OBSIDIAN_ROD = ITEMS.registerItem("obsidian_rod", Item::new);
	public static final Supplier<Item> WOODEN_TOOL_ROD = ITEMS.registerItem("wooden_tool_rod", Item::new);
	public static final Supplier<Item> COBALT_NUGGET = ITEMS.registerItem("cobalt_nugget", Item::new);
	public static final Supplier<Item> COPPER_NUGGET = ITEMS.registerItem("copper_nugget", Item::new);
	public static final Supplier<Item> COBALT_INGOT = ITEMS.registerItem("cobalt_ingot", Item::new);
	public static final Supplier<Item> RAW_COBALT = ITEMS.registerItem("raw_cobalt", Item::new);
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	public static final Supplier<Item> STARSTORM_INGOT = ITEMS.registerItem("starstorm_ingot", Item::new);
	public static final Supplier<Item> ASTRAL_INGOT = ITEMS.registerItem("astral_ingot", Item::new);
	public static final Supplier<Item> ASTRAL_NUGGET = ITEMS.registerItem("astral_nugget", Item::new);
	public static final Supplier<Item> RAW_ASTRAL = ITEMS.registerItem("raw_astral", Item::new);
	@TextureMetadataMarker
	public static final Supplier<Item> TESLA_INGOT = ITEMS.registerItem("tesla_ingot", Item::new);
	public static final Supplier<Item> TESLA_NUGGET = ITEMS.registerItem("tesla_nugget", Item::new);
	public static final Supplier<Item> ELECTRIC_INGOT = ITEMS.registerItem("electric_ingot", Item::new);
	public static final Supplier<Item> CONDUCTIVE_ALLOY = ITEMS.registerItem("conductive_alloy", Item::new);
	public static final Supplier<Item> MOLTEN_INGOT = ITEMS.registerItem("molten_ingot", (properties) -> new FuelItem(properties.fireResistant(), 24000));
	public static final Supplier<Item> MOLTEN_SMITHING_TEMPLATE = ITEMS.registerItem("molten_smithing_template", (properties) -> new Item(properties.fireResistant()));
	public static final Supplier<Item> ENDER_ESSENCE = ITEMS.registerItem("ender_essence", Item::new);
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	public static final Supplier<Item> VOID_INGOT = ITEMS.registerItem("void_ingot", Item::new);
	public static final Supplier<Item> HANSIUM_INGOT = ITEMS.registerItem("hansium_ingot", Item::new);
	public static final Supplier<Item> BLACKPOWDER = ITEMS.registerItem("blackpowder", Item::new);
	public static final Supplier<Item> SULFUR = ITEMS.registerItem("sulfur", Item::new);
	public static final Supplier<Item> SULFUR_DUST = ITEMS.registerItem("sulfur_dust", Item::new);
	public static final Supplier<Item> POTASSIUM_NITRATE = ITEMS.registerItem("potassium_nitrate", Item::new);
	public static final Supplier<Item> VENTUS_STAFF_CORE = ITEMS.registerItem("ventus_staff_core", Item::new);
	public static final Supplier<Item> CURSED_SIGHT_STAFF_CORE = ITEMS.registerItem("cursed_sight_staff_core", Item::new);
	public static final Supplier<Item> WARDEN_HEART = ITEMS.registerItem("warden_heart", Item::new);
	@TooltipMarker(style = {ChatFormatting.AQUA, ChatFormatting.ITALIC})
	public static final Supplier<Item> AZUL_KEYSTONE = ITEMS.registerItem("azul_keystone", Item::new);
	@TooltipMarker(style = {ChatFormatting.AQUA, ChatFormatting.ITALIC})
	public static final Supplier<Item> AZUL_KEYSTONE_FRAGMENT = ITEMS.registerItem("azul_keystone_fragment", Item::new);
	@TooltipMarker(style = {ChatFormatting.AQUA, ChatFormatting.ITALIC})
	public static final Supplier<AzulLocatorItem> AZUL_LOCATOR = ITEMS.registerItem("azul_locator", (properties) -> new AzulLocatorItem(properties.durability(1)));
	@TooltipMarker(style = {ChatFormatting.YELLOW, ChatFormatting.ITALIC})
	public static final Supplier<Item> CELESTIAL_FRAGMENT = ITEMS.registerItem("celestial_fragment", (properties) -> new Item(properties.fireResistant()));
	public static final Supplier<Item> BROKEN_LENS = ITEMS.registerItem("broken_lens", Item::new);
	public static final Supplier<Item> WOODEN_PIKE_HEAD = ITEMS.registerItem("wooden_pike_head", Item::new);
	public static final Supplier<Item> STONE_PIKE_HEAD = ITEMS.registerItem("stone_pike_head", Item::new);
	public static final Supplier<Item> GOLDEN_PIKE_HEAD = ITEMS.registerItem("golden_pike_head", Item::new);
	public static final Supplier<Item> COPPER_PIKE_HEAD = ITEMS.registerItem("copper_pike_head", Item::new);
	public static final Supplier<Item> IRON_PIKE_HEAD = ITEMS.registerItem("iron_pike_head", Item::new);
	public static final Supplier<Item> COBALT_PIKE_HEAD = ITEMS.registerItem("cobalt_pike_head", Item::new);
	public static final Supplier<Item> DIAMOND_PIKE_HEAD = ITEMS.registerItem("diamond_pike_head", Item::new);
	public static final Supplier<Item> TESLA_PIKE_HEAD = ITEMS.registerItem("tesla_pike_head", Item::new);
	public static final Supplier<Item> VENTUS_PIKE_HEAD = ITEMS.registerItem("ventus_pike_head", Item::new);
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> WOODEN_ARROW = ITEMS.registerItem("wooden_arrow", (properties) -> new ArrowBuilder<>(properties, 1.65d, EntityRegistry.WOODEN_ARROW_ENTITY).shootingVector(0.0185d, 5.8d, 7.2d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> STONE_ARROW = ITEMS.registerItem("stone_arrow", (properties) -> new ArrowBuilder<>(properties, 1.85d, EntityRegistry.STONE_ARROW_ENTITY).shootingVector(0.0175d, 2.6d, 4.3d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> GOLDEN_ARROW = ITEMS.registerItem("golden_arrow", (properties) -> new ArrowBuilder<>(properties, 2.10d, EntityRegistry.GOLDEN_ARROW_ENTITY).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> COPPER_ARROW = ITEMS.registerItem("copper_arrow", (properties) -> new ArrowBuilder<>(properties, 2.15d, EntityRegistry.COPPER_ARROW_ENTITY).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> IRON_ARROW = ITEMS.registerItem("iron_arrow", (properties) -> new ArrowBuilder<>(properties, 2.35d, EntityRegistry.IRON_ARROW_ENTITY).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> COBALT_ARROW = ITEMS.registerItem("cobalt_arrow", (properties) -> new ArrowBuilder<>(properties, 2.55d, EntityRegistry.COBALT_ARROW_ENTITY).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> DIAMOND_ARROW = ITEMS.registerItem("diamond_arrow", (properties) -> new ArrowBuilder<>(properties, 3.0d, EntityRegistry.DIAMOND_ARROW_ENTITY).canBeInfinite(false).pierceLevel(1).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> NETHERITE_ARROW = ITEMS.registerItem("netherite_arrow", (properties) -> new ArrowBuilder<>(properties.fireResistant(), 5.75d, EntityRegistry.NETHERITE_ARROW_ENTITY).canBeInfinite(false).pierceLevel(2).gravityModifier(0.0455d).shootingVector(0.0025d, 0.2d, 1.1d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> MOLTEN_ARROW = ITEMS.registerItem("molten_arrow", (properties) -> new ArrowBuilder<>(properties.fireResistant(), 6.50d, EntityRegistry.MOLTEN_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.0455d).shootingVector(0.0025d, 0.2d, 1.0d).knockbackStrength(1).hitEffect(HitEffect.MOLTEN).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> TESLA_ARROW = ITEMS.registerItem("tesla_arrow", (properties) -> new ArrowBuilder<>(properties, 7.0d, EntityRegistry.TESLA_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.0355d).shootingVector(0.0025d, 0.2d, 0.9d).hitEffect(HitEffect.TESLA).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> VENTUS_ARROW = ITEMS.registerItem("ventus_arrow", (properties) -> new ArrowBuilder<>(properties, 6.5d, EntityRegistry.VENTUS_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.0355d).shootingVector(0.0025d, 0.2d, 0.9d).hitEffect(HitEffect.VENTUS).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> ASTRAL_ARROW = ITEMS.registerItem("astral_arrow", (properties) -> new ArrowBuilder<>(properties, 5.50d, EntityRegistry.ASTRAL_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.01d).shootingVector(0.002d, 0.1d, 0.6d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> STARSTORM_ARROW = ITEMS.registerItem("starstorm_arrow", (properties) -> new ArrowBuilder<>(properties, 7.65d, EntityRegistry.STARSTORM_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.0355d).shootingVector(0.0025d, 0.2d, 0.9d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<CustomArrowItem<?>> VOID_ARROW = ITEMS.registerItem("void_arrow", (properties) -> new ArrowBuilder<>(properties, 8.0d, EntityRegistry.VOID_ARROW_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.01d).shootingVector(0.002d, 0.1d, 0.6d).build());
	@LanguageEntryOverride("Smoke Grenade Arrow")
	@TooltipMarker(dynamicTooltip = DynamicTooltips.SmokeGrenadeArrowTooltip.class)
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW = ITEMS.registerItem("smoke_grenade_arrow", (properties) -> new ArrowBuilder<>(properties, 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(0).build());
	@LanguageEntryOverride("Red Smoke Grenade Arrow")
	@TooltipMarker(dynamicTooltip = DynamicTooltips.SmokeGrenadeArrowTooltip.class)
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_RED = ITEMS.registerItem("smoke_grenade_arrow_red", (properties) -> new ArrowBuilder<>(properties, 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(1).build());
	@LanguageEntryOverride("Green Smoke Grenade Arrow")
	@TooltipMarker(dynamicTooltip = DynamicTooltips.SmokeGrenadeArrowTooltip.class)
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_GREEN = ITEMS.registerItem("smoke_grenade_arrow_green", (properties) -> new ArrowBuilder<>(properties, 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(2).build());
	@LanguageEntryOverride("Blue Smoke Grenade Arrow")
	@TooltipMarker(dynamicTooltip = DynamicTooltips.SmokeGrenadeArrowTooltip.class)
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_BLUE = ITEMS.registerItem("smoke_grenade_arrow_blue", (properties) -> new ArrowBuilder<>(properties, 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(3).build());
	@LanguageEntryOverride("Purple Smoke Grenade Arrow")
	@TooltipMarker(dynamicTooltip = DynamicTooltips.SmokeGrenadeArrowTooltip.class)
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_PURPLE = ITEMS.registerItem("smoke_grenade_arrow_purple", (properties) -> new ArrowBuilder<>(properties, 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(4).build());
	@LanguageEntryOverride("Yellow Smoke Grenade Arrow")
	@TooltipMarker(dynamicTooltip = DynamicTooltips.SmokeGrenadeArrowTooltip.class)
	public static final Supplier<CustomArrowItem<?>> SMOKE_GRENADE_ARROW_YELLOW = ITEMS.registerItem("smoke_grenade_arrow_yellow", (properties) -> new ArrowBuilder<>(properties, 2.0d, EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY).color(5).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> WOODEN_MUSKET_BALL = ITEMS.registerItem("wooden_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 2.0d, EntityRegistry.WOODEN_MUSKET_BALL_ENTITY).misfireChance(0.3f).gravityModifier(0.55d).shootingVector(0.0175d, 3.2d, 5.1d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> STONE_MUSKET_BALL = ITEMS.registerItem("stone_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 2.20d, EntityRegistry.STONE_MUSKET_BALL_ENTITY).misfireChance(0.15f).gravityModifier(0.075d).shootingVector(0.0175d, 2.4d, 4.1d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> GOLDEN_MUSKET_BALL = ITEMS.registerItem("golden_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 2.30d, EntityRegistry.GOLDEN_MUSKET_BALL_ENTITY).gravityModifier(0.03d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> COPPER_MUSKET_BALL = ITEMS.registerItem("copper_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 2.40d, EntityRegistry.COPPER_MUSKET_BALL_ENTITY).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> IRON_MUSKET_BALL = ITEMS.registerItem("iron_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 2.65d, EntityRegistry.IRON_MUSKET_BALL_ENTITY).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> COBALT_MUSKET_BALL = ITEMS.registerItem("cobalt_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 2.90d, EntityRegistry.COBALT_MUSKET_BALL_ENTITY).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> DIAMOND_MUSKET_BALL = ITEMS.registerItem("diamond_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 3.35d, EntityRegistry.DIAMOND_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(1).gravityModifier(0.01d).shootingVector(0.0025d, 0.2d, 0.9).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> NETHERITE_MUSKET_BALL = ITEMS.registerItem("netherite_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99).fireResistant(), 6.50d, EntityRegistry.NETHERITE_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(2).gravityModifier(0.005d).shootingVector(0.002d, 0.2d, 0.7d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> MOLTEN_MUSKET_BALL = ITEMS.registerItem("molten_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99).fireResistant(), 7.50d, EntityRegistry.MOLTEN_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.005d).knockbackStrength(1).hitEffect(HitEffect.MOLTEN).shootingVector(0.002d, 0.2d, 0.6d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> TESLA_MUSKET_BALL = ITEMS.registerItem("tesla_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 8.0d, EntityRegistry.TESLA_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.004d).hitEffect(HitEffect.TESLA).shootingVector(0.002d, 0.2d, 0.5d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> VENTUS_MUSKET_BALL = ITEMS.registerItem("ventus_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 7.50d, EntityRegistry.VENTUS_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.002d).hitEffect(HitEffect.VENTUS).shootingVector(0.002d, 0.2d, 0.5d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> ASTRAL_MUSKET_BALL = ITEMS.registerItem("astral_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 6.25d, EntityRegistry.ASTRAL_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.001d).shootingVector(0.001d, 0.1d, 0.2d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> STARSTORM_MUSKET_BALL = ITEMS.registerItem("starstorm_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 8.65d, EntityRegistry.STARSTORM_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.002d).shootingVector(0.002d, 0.2d, 0.5d).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> VOID_MUSKET_BALL = ITEMS.registerItem("void_musket_ball", (properties) -> new BulletBuilder<>(properties.stacksTo(99), 9.0d, EntityRegistry.VOID_MUSKET_BALL_ENTITY).canBeInfinite(false).pierceLevel(3).gravityModifier(0.001d).shootingVector(0.001d, 0.1d, 0.2d).build());
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> FLARE = ITEMS.registerItem("flare", (properties) -> new BulletBuilder<>(properties, 0.1d, EntityRegistry.FLARE_ENTITY).gravityModifier(0.06d).build());
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> CANNONBALL = ITEMS.registerItem("cannonball", (properties) -> new BulletItem.BulletBuilder<>(properties, 6.0d, EntityRegistry.CANNONBALL_ENTITY).gravityModifier(0.055d).build());
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<BulletItem<?>> EXPLOSIVE_CANNONBALL = ITEMS.registerItem("explosive_cannonball", (properties) -> new BulletItem.BulletBuilder<>(properties, 6.0d, EntityRegistry.CANNONBALL_ENTITY).isExplosive(true).build());
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.BulletTooltip.class)
	public static final Supplier<DragonFireballItem> DRAGON_FIREBALL = ITEMS.registerItem("dragon_fireball", (properties) -> DragonFireballItem.createFromBulletBuilder(new BulletBuilder<>(properties, 12.0d, EntityRegistry.DRAGON_FIREBALL_ENTITY).gravityModifier(0.001d).isExplosive(true)));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	public static final Supplier<Item> MORTAR_SHELL = ITEMS.registerItem("mortar_shell", Item::new);
	public static final Supplier<Item> GRENADE_ASSEMBLY = ITEMS.registerItem("grenade_assembly", Item::new);
	public static final Supplier<Item> TOOL_JOINT = ITEMS.registerItem("tool_joint", Item::new);
	public static final Supplier<Item> GAUNTLET_SCAFFOLDING = ITEMS.registerItem("gauntlet_scaffolding", Item::new);
	public static final Supplier<Item> FLINTLOCK_ASSEMBLY = ITEMS.registerItem("flintlock_assembly", Item::new);
	public static final Supplier<Item> TRIGGER_ASSEMBLY = ITEMS.registerItem("trigger_assembly", Item::new);
	public static final Supplier<Item> HEAVY_WOODEN_STOCK = ITEMS.registerItem("heavy_wooden_stock", Item::new);
	public static final Supplier<Item> WOODEN_PISTOL_HANDLE = ITEMS.registerItem("wooden_pistol_handle", Item::new);
	public static final Supplier<Item> IRON_BARREL = ITEMS.registerItem("iron_barrel", Item::new);
	public static final Supplier<Item> EXTENDED_IRON_BARREL = ITEMS.registerItem("extended_iron_barrel", Item::new);
	public static final Supplier<Item> SHORT_IRON_BARREL = ITEMS.registerItem("short_iron_barrel", Item::new);
	public static final Supplier<Item> WIDE_GOLDEN_BARREL = ITEMS.registerItem("wide_golden_barrel", Item::new);
	public static final Supplier<Item> SCOPE_MOUNT = ITEMS.registerItem("scope_mount", Item::new);
	public static final Supplier<Item> SCOPE = ITEMS.registerItem("scope", Item::new);
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@LanguageEntryOverride("Smoke Grenade")
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.ThrowableItemTooltip.class)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE = ITEMS.registerItem("smoke_grenade", (properties) -> new ThrowableItem(properties.stacksTo(16), 0));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@LanguageEntryOverride("Red Smoke Grenade")
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.ThrowableItemTooltip.class)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_RED = ITEMS.registerItem("smoke_grenade_red", (properties) -> new ThrowableItem(properties.stacksTo(16), 1));
	@LanguageEntryOverride("Green Smoke Grenade")
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.ThrowableItemTooltip.class)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_GREEN = ITEMS.registerItem("smoke_grenade_green", (properties) -> new ThrowableItem(properties.stacksTo(16), 2));
	@LanguageEntryOverride("Blue Smoke Grenade")
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.ThrowableItemTooltip.class)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_BLUE = ITEMS.registerItem("smoke_grenade_blue", (properties) -> new ThrowableItem(properties.stacksTo(16), 3));
	@LanguageEntryOverride("Purple Smoke Grenade")
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.ThrowableItemTooltip.class)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_PURPLE = ITEMS.registerItem("smoke_grenade_purple", (properties) -> new ThrowableItem(properties.stacksTo(16), 4));
	@LanguageEntryOverride("Yellow Smoke Grenade")
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.ThrowableItemTooltip.class)
	public static final Supplier<ThrowableItem> SMOKE_GRENADE_YELLOW = ITEMS.registerItem("smoke_grenade_yellow", (properties) -> new ThrowableItem(properties.stacksTo(16), 5));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.ThrowableItemTooltip.class)
	public static final Supplier<ThrowableItem> FLASHBANG = ITEMS.registerItem("flashbang", (properties) -> new ThrowableItem(properties.stacksTo(16), ThrowableType.FLASHBANG));
	@DatagenExclusionMarker(Type.MODEL_GENERATOR_ITEM)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, dynamicTooltip = DynamicTooltips.ThrowableItemTooltip.class)
	public static final Supplier<ThrowableItem> MOLOTOV_COCKTAIL = ITEMS.registerItem("molotov_cocktail", (properties) -> new ThrowableItem(properties.stacksTo(16), ThrowableType.MOLOTOV));
	public static final Supplier<Item> SMOKE_POWDER = ITEMS.registerItem("smoke_powder", Item::new);
	public static final Supplier<CraftingToolItem> MORTAR_AND_PESTLE = ITEMS.registerItem("mortar_and_pestle", CraftingToolItem::new);
	public static final Supplier<PliersItem> PLIERS = ITEMS.registerItem("pliers", (properties) -> new PliersItem(properties.stacksTo(1)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<AlcoholItem> BOTTLE_OF_ALCOHOL = ITEMS.registerItem("bottle_of_alcohol", (properties) -> new AlcoholItem(properties.stacksTo(16)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<WineItem> BOTTLE_OF_WINE = ITEMS.registerItem("bottle_of_wine", (properties) -> new WineItem(properties.stacksTo(16)));
	public static final Supplier<ChocolateBarItem> CHOCOLATE_BAR = ITEMS.registerItem("chocolate_bar", (properties) -> new ChocolateBarItem(properties.food(FoodItemProperties.CHOCOLATE_BAR, FoodItemProperties.CHOCOLATE_BAR_CONSUMABLE)));
	@LanguageEntryOverride("Meal Ready-to-Eat (MRE)")
	@TooltipMarker(style = {ChatFormatting.GREEN, ChatFormatting.ITALIC})
	public static final Supplier<Item> MRE = ITEMS.registerItem("mre", (properties) -> new Item(properties.food(FoodItemProperties.MRE)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<Item> MOLDY_BREAD = ITEMS.registerItem("moldy_bread", (properties) -> new Item(properties.food(FoodItemProperties.MOLDY_BREAD, FoodItemProperties.MOLDY_BREAD_CONSUMABLE)));
	@TooltipMarker(style = {ChatFormatting.GREEN, ChatFormatting.ITALIC})
	public static final Supplier<BandageItem> BANDAGE = ITEMS.registerItem("bandage", (properties) -> new BandageItem(properties.stacksTo(16)));
	@TooltipMarker(style = {ChatFormatting.GREEN, ChatFormatting.ITALIC})
	public static final Supplier<PainkillerItem> PAINKILLERS = ITEMS.registerItem("painkillers", (properties) -> new PainkillerItem(properties.stacksTo(24)));
	public static final Supplier<Item> SYRINGE = ITEMS.registerItem("syringe", (properties) -> new Item(properties.stacksTo(16)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<MorphineItem> MORPHINE = ITEMS.registerItem("morphine", (properties) -> new MorphineItem(properties.stacksTo(16)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<UsedSyringeItem> USED_SYRINGE = ITEMS.registerItem("used_syringe", (properties) -> new UsedSyringeItem(properties.stacksTo(16)));
	@TooltipMarker(style = {ChatFormatting.GREEN, ChatFormatting.ITALIC})
	public static final Supplier<FirstAidKitItem> FIRST_AID_KIT = ITEMS.registerItem("first_aid_kit", (properties) -> new FirstAidKitItem(properties.stacksTo(8)));
	public static final Supplier<Item> CLOTH_SCRAP = ITEMS.registerItem("cloth_scrap", Item::new);
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<ThrowableItem> MUD_BALL = ITEMS.registerItem("mud_ball", (properties) -> new ThrowableItem(properties, ThrowableType.MUD_BALL));
	public static final Supplier<BoatItem> BURNED_OAK_BOAT = ITEMS.registerItem("burned_oak_boat", (properties) -> new BoatItem(EntityRegistry.BURNED_OAK_BOAT_ENTITY.get(), properties.stacksTo(1)));
	public static final Supplier<BoatItem> BURNED_OAK_CHEST_BOAT = ITEMS.registerItem("burned_oak_chest_boat", (properties) -> new BoatItem(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get(), properties.stacksTo(1)));
	public static final Supplier<BoatItem> STARDUST_BOAT = ITEMS.registerItem("stardust_boat", (properties) -> new BoatItem(EntityRegistry.STARDUST_BOAT_ENTITY.get(), properties.stacksTo(1)));
	public static final Supplier<BoatItem> STARDUST_CHEST_BOAT = ITEMS.registerItem("stardust_chest_boat", (properties) -> new BoatItem(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get(), properties.stacksTo(1)));
	public static final Supplier<CurseCleaningSoapItem> CURSE_CLEANING_SOAP = ITEMS.registerItem("curse_cleaning_soap", (properties) -> new CurseCleaningSoapItem(properties.stacksTo(1)));
	public static final Supplier<Item> SATCHEL = ITEMS.registerItem("satchel", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> POWDER_HORN = ITEMS.registerItem("powder_horn", (properties) -> new Item(properties.stacksTo(1)));
	@LanguageEntryOverride("Berserker's Amulet")
	public static final Supplier<Item> BERSERKERS_AMULET = ITEMS.registerItem("berserkers_amulet", (properties) -> new Item(properties.stacksTo(1)));
	@LanguageEntryOverride("Hans' Blessing")
	public static final Supplier<Item> HANS_BLESSING = ITEMS.registerItem("hans_blessing", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> CELESTIAL_SPIRIT = ITEMS.registerItem("celestial_spirit", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> VOID_BLESSING = ITEMS.registerItem("void_blessing", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> BLADEMASTER_EMBLEM = ITEMS.registerItem("blademaster_emblem", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> DEADEYE_PENDANT = ITEMS.registerItem("deadeye_pendant", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> BLOATED_HEART = ITEMS.registerItem("bloated_heart", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> NETHERITE_SHIELD = ITEMS.registerItem("netherite_shield", (properties) -> new Item(properties.stacksTo(1)));
	@LanguageEntryOverride("Melee Master's Molten Glove")
	public static final Supplier<Item> MELEE_MASTERS_MOLTEN_GLOVE = ITEMS.registerItem("melee_masters_molten_glove", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> IRON_FIST = ITEMS.registerItem("iron_fist", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> GLOVE_OF_RAPID_SWINGING = ITEMS.registerItem("glove_of_rapid_swinging", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> HAND_OF_DOOM = ITEMS.registerItem("hand_of_doom", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> COPPER_RING = ITEMS.registerItem("copper_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> IRON_RING = ITEMS.registerItem("iron_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> COBALT_RING = ITEMS.registerItem("cobalt_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> GOLDEN_RING = ITEMS.registerItem("golden_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> AMETHYST_RING = ITEMS.registerItem("amethyst_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> EMERALD_RING = ITEMS.registerItem("emerald_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> DIAMOND_RING = ITEMS.registerItem("diamond_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> NETHERITE_RING = ITEMS.registerItem("netherite_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> DEATH_GEM_RING = ITEMS.registerItem("death_gem_ring", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> MEDAL_OF_ADEQUACY = ITEMS.registerItem("medal_of_adequacy", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> DEPTH_CHARM = ITEMS.registerItem("depth_charm", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> REINFORCED_DEPTH_CHARM = ITEMS.registerItem("reinforced_depth_charm", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> INSOMNIA_AMULET = ITEMS.registerItem("insomnia_amulet", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> GOGGLES = ITEMS.registerItem("goggles", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> LAVA_GOGGLES = ITEMS.registerItem("lava_goggles", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> NIGHT_VISION_GOGGLES = ITEMS.registerItem("night_vision_goggles", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> AGILITY_BRACELET = ITEMS.registerItem("agility_bracelet", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> BLOODY_CLOTH = ITEMS.registerItem("bloody_cloth", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> ANCIENT_SCROLL = ITEMS.registerItem("ancient_scroll", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> HOLY_MANTLE = ITEMS.registerItem("holy_mantle", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> VENSTRAL_JAR = ITEMS.registerItem("venstral_jar", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> SUPER_BLANKET_CAPE = ITEMS.registerItem("super_blanket_cape", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> MEDAL_OF_HONOR = ITEMS.registerItem("medal_of_honor", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<Item> MEDAL_OF_DISHONOR = ITEMS.registerItem("medal_of_dishonor", (properties) -> new Item(properties.stacksTo(1)));
	public static final Supplier<CursedItem> BLOODY_SACRIFICE = ITEMS.registerItem("bloody_sacrifice", (properties) -> new CursedItem(properties.stacksTo(1).durability(100), "bloody_sacrifice"));
	@LanguageEntryOverride("Jonny's Curse")
	public static final Supplier<CursedItem> JONNYS_CURSE = ITEMS.registerItem("jonnys_curse", (properties) -> new CursedItem(properties.stacksTo(1).durability(100), "jonnys_curse"));
	@TooltipMarker(style = {ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC})
	public static final Supplier<Item> CHAMPION_KEYCARD = ITEMS.registerItem("champion_keycard", (properties) -> new Item(properties.stacksTo(1).fireResistant()));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC})
	public static final Supplier<Item> KILL_COUNTER = ITEMS.registerItem("kill_counter", Item::new);
	@LanguageEntryOverride("Pedestal Augment: Speed")
	public static final Supplier<Item> PEDESTAL_AUGMENT_SPEED = ITEMS.registerItem("pedestal_augment_speed", (properties) -> new Item(properties.stacksTo(4)));
	@LanguageEntryOverride("Pedestal Augment: Armor")
	public static final Supplier<Item> PEDESTAL_AUGMENT_ARMOR = ITEMS.registerItem("pedestal_augment_armor", (properties) -> new Item(properties.stacksTo(4)));
	@LanguageEntryOverride("Pedestal Augment: Enchantment")
	public static final Supplier<Item> PEDESTAL_AUGMENT_ENCHANTMENT = ITEMS.registerItem("pedestal_augment_enchantment", (properties) -> new Item(properties.stacksTo(4)));
	@LanguageEntryOverride("Pedestal Augment: Capacity")
	public static final Supplier<Item> PEDESTAL_AUGMENT_CAPACITY = ITEMS.registerItem("pedestal_augment_capacity", (properties) -> new Item(properties.stacksTo(4)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_STARLIGHT_PLAINS_THEME_1 = ITEMS.registerItem("music_disc_starlight_plains_theme_1", (properties) -> new Item(properties.stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.STARLIGHT_PLAINS_THEME_1)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_STARLIGHT_PLAINS_THEME_2 = ITEMS.registerItem("music_disc_starlight_plains_theme_2", (properties) -> new Item(properties.stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.STARLIGHT_PLAINS_THEME_2)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_TILTROS_WASTES_THEME = ITEMS.registerItem("music_disc_tiltros_wastes_theme", (properties) -> new Item(properties.stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.TILTROS_WASTES_THEME)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_DEADMANS_DESERT_THEME_1 = ITEMS.registerItem("music_disc_deadmans_desert_theme_1", (properties) -> new Item(properties.stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.DEADMANS_DESERT_THEME_1)));
	@LanguageEntryOverride("Music Disc")
	public static final Supplier<Item> MUSIC_DISC_DEADMANS_DESERT_THEME_2 = ITEMS.registerItem("music_disc_deadmans_desert_theme_2", (properties) -> new Item(properties.stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(IWJukeboxSongs.DEADMANS_DESERT_THEME_2)));

	// Armor
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "molten_armor")
	public static final Supplier<MoltenArmorItem> MOLTEN_HELMET = ITEMS.registerItem("molten_helmet", (properties) -> new MoltenArmorItem(IWArmorMaterials.MOLTEN, ArmorType.HELMET, properties.fireResistant()));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "molten_armor")
	public static final Supplier<MoltenArmorItem> MOLTEN_CHESTPLATE = ITEMS.registerItem("molten_chestplate", (properties) -> new MoltenArmorItem(IWArmorMaterials.MOLTEN, ArmorType.CHESTPLATE, properties.fireResistant()));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "molten_armor")
	public static final Supplier<MoltenArmorItem> MOLTEN_LEGGINGS = ITEMS.registerItem("molten_leggings", (properties) -> new MoltenArmorItem(IWArmorMaterials.MOLTEN, ArmorType.LEGGINGS, properties.fireResistant()));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "molten_armor")
	public static final Supplier<MoltenArmorItem> MOLTEN_BOOTS = ITEMS.registerItem("molten_boots", (properties) -> new MoltenArmorItem(IWArmorMaterials.MOLTEN, ArmorType.BOOTS, properties.fireResistant()));
	public static final Supplier<Item> COPPER_HELMET = ITEMS.registerItem("copper_helmet", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.COPPER, ArmorType.HELMET)));
	public static final Supplier<Item> COPPER_CHESTPLATE = ITEMS.registerItem("copper_chestplate", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.COPPER, ArmorType.CHESTPLATE)));
	public static final Supplier<Item> COPPER_LEGGINGS = ITEMS.registerItem("copper_leggings", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.COPPER, ArmorType.LEGGINGS)));
	public static final Supplier<Item> COPPER_BOOTS = ITEMS.registerItem("copper_boots", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.COPPER, ArmorType.BOOTS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "tesla_armor")
	public static final Supplier<TeslaArmorItem> TESLA_HELMET = ITEMS.registerItem("tesla_helmet", (properties) -> new TeslaArmorItem(IWArmorMaterials.TESLA, ArmorType.HELMET, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "tesla_armor")
	public static final Supplier<TeslaArmorItem> TESLA_CHESTPLATE = ITEMS.registerItem("tesla_chestplate", (properties) -> new TeslaArmorItem(IWArmorMaterials.TESLA, ArmorType.CHESTPLATE, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "tesla_armor")
	public static final Supplier<TeslaArmorItem> TESLA_LEGGINGS = ITEMS.registerItem("tesla_leggings", (properties) -> new TeslaArmorItem(IWArmorMaterials.TESLA, ArmorType.LEGGINGS, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "tesla_armor")
	public static final Supplier<TeslaArmorItem> TESLA_BOOTS = ITEMS.registerItem("tesla_boots", (properties) -> new TeslaArmorItem(IWArmorMaterials.TESLA, ArmorType.BOOTS, properties));
	public static final Supplier<CobaltArmorItem> COBALT_HELMET = ITEMS.registerItem("cobalt_helmet", (properties) -> new CobaltArmorItem(IWArmorMaterials.COBALT, ArmorType.HELMET, properties));
	public static final Supplier<CobaltArmorItem> COBALT_CHESTPLATE = ITEMS.registerItem("cobalt_chestplate", (properties) -> new CobaltArmorItem(IWArmorMaterials.COBALT, ArmorType.CHESTPLATE, properties));
	public static final Supplier<CobaltArmorItem> COBALT_LEGGINGS = ITEMS.registerItem("cobalt_leggings", (properties) -> new CobaltArmorItem(IWArmorMaterials.COBALT, ArmorType.LEGGINGS, properties));
	public static final Supplier<CobaltArmorItem> COBALT_BOOTS = ITEMS.registerItem("cobalt_boots", (properties) -> new CobaltArmorItem(IWArmorMaterials.COBALT, ArmorType.BOOTS, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "ventus_armor")
	public static final Supplier<VentusArmorItem> VENTUS_HELMET = ITEMS.registerItem("ventus_helmet", (properties) -> new VentusArmorItem(IWArmorMaterials.VENTUS, ArmorType.HELMET, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "ventus_armor")
	public static final Supplier<VentusArmorItem> VENTUS_CHESTPLATE = ITEMS.registerItem("ventus_chestplate", (properties) -> new VentusArmorItem(IWArmorMaterials.VENTUS, ArmorType.CHESTPLATE, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "ventus_armor")
	public static final Supplier<VentusArmorItem> VENTUS_LEGGINGS = ITEMS.registerItem("ventus_leggings", (properties) -> new VentusArmorItem(IWArmorMaterials.VENTUS, ArmorType.LEGGINGS, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "ventus_armor")
	public static final Supplier<VentusArmorItem> VENTUS_BOOTS = ITEMS.registerItem("ventus_boots", (properties) -> new VentusArmorItem(IWArmorMaterials.VENTUS, ArmorType.BOOTS, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "astral_armor")
	public static final Supplier<AstralArmorItem> ASTRAL_HELMET = ITEMS.registerItem("astral_helmet", (properties) -> new AstralArmorItem(IWArmorMaterials.ASTRAL, ArmorType.HELMET, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "astral_armor")
	public static final Supplier<AstralArmorItem> ASTRAL_CHESTPLATE = ITEMS.registerItem("astral_chestplate", (properties) -> new AstralArmorItem(IWArmorMaterials.ASTRAL, ArmorType.CHESTPLATE, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "astral_armor")
	public static final Supplier<AstralArmorItem> ASTRAL_LEGGINGS = ITEMS.registerItem("astral_leggings", (properties) -> new AstralArmorItem(IWArmorMaterials.ASTRAL, ArmorType.LEGGINGS, properties));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "astral_armor")
	public static final Supplier<AstralArmorItem> ASTRAL_BOOTS = ITEMS.registerItem("astral_boots", (properties) -> new AstralArmorItem(IWArmorMaterials.ASTRAL, ArmorType.BOOTS, properties));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "starstorm_armor")
	public static final Supplier<Item> STARSTORM_HELMET = ITEMS.registerItem("starstorm_helmet", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.STARSTORM, ArmorType.HELMET)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "starstorm_armor")
	public static final Supplier<Item> STARSTORM_CHESTPLATE = ITEMS.registerItem("starstorm_chestplate", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.STARSTORM, ArmorType.CHESTPLATE)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "starstorm_armor")
	public static final Supplier<Item> STARSTORM_LEGGINGS = ITEMS.registerItem("starstorm_leggings", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.STARSTORM, ArmorType.LEGGINGS)));
	@TextureMetadataMarker(predefinedGroup = PredefinedGroups.STARSTORM_ITEMS)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "starstorm_armor")
	public static final Supplier<Item> STARSTORM_BOOTS = ITEMS.registerItem("starstorm_boots", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.STARSTORM, ArmorType.BOOTS)));
	@LanguageEntryOverride("Padded Leather Cap")
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "padded_leather_armor")
	public static final Supplier<Item> PADDED_LEATHER_HELMET = ITEMS.registerItem("padded_leather_helmet", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.PADDED_LEATHER, ArmorType.HELMET)));
	@LanguageEntryOverride("Padded Leather Tunic")
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "padded_leather_armor")
	public static final Supplier<Item> PADDED_LEATHER_CHESTPLATE = ITEMS.registerItem("padded_leather_chestplate", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.PADDED_LEATHER, ArmorType.CHESTPLATE)));
	@LanguageEntryOverride("Padded Leather Pants")
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "padded_leather_armor")
	public static final Supplier<Item> PADDED_LEATHER_LEGGINGS = ITEMS.registerItem("padded_leather_leggings", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.PADDED_LEATHER, ArmorType.LEGGINGS)));
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "padded_leather_armor")
	public static final Supplier<Item> PADDED_LEATHER_BOOTS = ITEMS.registerItem("padded_leather_boots", (properties) -> new Item(properties.humanoidArmor(IWArmorMaterials.PADDED_LEATHER, ArmorType.BOOTS)));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "void_armor")
	public static final Supplier<VoidArmorItem> VOID_HELMET = ITEMS.registerItem("void_helmet", (properties) -> new VoidArmorItem(IWArmorMaterials.VOID, ArmorType.HELMET, properties));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "void_armor")
	public static final Supplier<VoidArmorItem> VOID_CHESTPLATE = ITEMS.registerItem("void_chestplate", (properties) -> new VoidArmorItem(IWArmorMaterials.VOID, ArmorType.CHESTPLATE, properties));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "void_armor")
	public static final Supplier<VoidArmorItem> VOID_LEGGINGS = ITEMS.registerItem("void_leggings", (properties) -> new VoidArmorItem(IWArmorMaterials.VOID, ArmorType.LEGGINGS, properties));
	@TextureMetadataMarker(frameTime = 25, interpolate = true)
	@TooltipMarker(style = {ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}, key = "void_armor")
	public static final Supplier<VoidArmorItem> VOID_BOOTS = ITEMS.registerItem("void_boots", (properties) -> new VoidArmorItem(IWArmorMaterials.VOID, ArmorType.BOOTS, properties));

	// Spawn eggs
	public static final Supplier<SpawnEggItem> DYING_SOLDIER_SPAWN_EGG = ITEMS.registerItem("dying_soldier_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.DYING_SOLDIER_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> THE_COMMANDER_SPAWN_EGG = ITEMS.registerItem("the_commander_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.THE_COMMANDER_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> MINUTEMAN_SPAWN_EGG = ITEMS.registerItem("minuteman_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.MINUTEMAN_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> FIELD_MEDIC_SPAWN_EGG = ITEMS.registerItem("field_medic_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.FIELD_MEDIC_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> WANDERING_WARRIOR_SPAWN_EGG = ITEMS.registerItem("wandering_warrior_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), properties.stacksTo(16)));
	@LanguageEntryOverride("Hans the Almighty Spawn Egg")
	@TooltipMarker(style = {ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC})
	public static final Supplier<SpawnEggItem> HANS_SPAWN_EGG = ITEMS.registerItem("hans_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.HANS_ENTITY.get(), properties.stacksTo(16)));
	@LanguageEntryOverride("Super Hans the Almighty Spawn Egg")
	@TooltipMarker(style = {ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC})
	public static final Supplier<SpawnEggItem> SUPER_HANS_SPAWN_EGG = ITEMS.registerItem("super_hans_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.SUPER_HANS_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> LAVA_REVENANT_SPAWN_EGG = ITEMS.registerItem("lava_revenant_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.LAVA_REVENANT_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> ROCK_SPIDER_SPAWN_EGG = ITEMS.registerItem("rock_spider_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.ROCK_SPIDER_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> CELESTIAL_TOWER_SPAWN_EGG = ITEMS.registerItem("celestial_tower_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> STARMITE_SPAWN_EGG = ITEMS.registerItem("starmite_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.STARMITE_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> FIREFLY_SPAWN_EGG = ITEMS.registerItem("firefly_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.FIREFLY_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> STORM_CREEPER_SPAWN_EGG = ITEMS.registerItem("storm_creeper_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.STORM_CREEPER_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> EVIL_EYE_SPAWN_EGG = ITEMS.registerItem("evil_eye_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.EVIL_EYE_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> STAR_WOLF_SPAWN_EGG = ITEMS.registerItem("star_wolf_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.STAR_WOLF_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> SKYGAZER_SPAWN_EGG = ITEMS.registerItem("skygazer_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.SKYGAZER_ENTITY.get(), properties.stacksTo(16)));
	public static final Supplier<SpawnEggItem> SKELETON_MERCHANT_SPAWN_EGG = ITEMS.registerItem("skeleton_merchant_spawn_egg", (properties) -> new SpawnEggItem(EntityRegistry.SKELETON_MERCHANT_ENTITY.get(), properties.stacksTo(16)));
}