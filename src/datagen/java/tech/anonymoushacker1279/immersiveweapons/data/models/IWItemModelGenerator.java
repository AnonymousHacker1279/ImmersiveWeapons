package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.HasComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.IWEquipmentAssets;
import tech.anonymoushacker1279.immersiveweapons.event.ClientModEventSubscriber;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.properties.HasSpecificName;

public class IWItemModelGenerator {

	public static void registerModels(ItemModelGenerators itemModels) {
		ClientModEventSubscriber.initializeCustomItemProperties();

		itemModels.generateFlatItem(ItemRegistry.MOLTEN_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLTEN_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLTEN_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLTEN_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLTEN_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ASTRAL_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ASTRAL_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ASTRAL_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ASTRAL_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ASTRAL_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARSTORM_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARSTORM_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARSTORM_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARSTORM_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARSTORM_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VOID_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VOID_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VOID_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VOID_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VOID_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.THE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		generatePikeItem(itemModels, ItemRegistry.WOODEN_PIKE.get(), ResourceLocation.withDefaultNamespace("block/stripped_oak_log"));
		generatePikeItem(itemModels, ItemRegistry.STONE_PIKE.get(), ResourceLocation.withDefaultNamespace("block/stone"));
		generatePikeItem(itemModels, ItemRegistry.GOLDEN_PIKE.get(), ResourceLocation.withDefaultNamespace("block/gold_block"));
		generatePikeItem(itemModels, ItemRegistry.COPPER_PIKE.get(), ResourceLocation.withDefaultNamespace("block/copper_block"));
		generatePikeItem(itemModels, ItemRegistry.IRON_PIKE.get(), ResourceLocation.withDefaultNamespace("block/iron_block"));
		generatePikeItem(itemModels, ItemRegistry.COBALT_PIKE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cobalt_block"));
		generatePikeItem(itemModels, ItemRegistry.DIAMOND_PIKE.get(), ResourceLocation.withDefaultNamespace("block/diamond_block"));
		generatePikeItem(itemModels, ItemRegistry.NETHERITE_PIKE.get(), ResourceLocation.withDefaultNamespace("block/netherite_block"));
		generatePikeItem(itemModels, ItemRegistry.MOLTEN_PIKE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/molten"));
		generatePikeItem(itemModels, ItemRegistry.TESLA_PIKE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/tesla"));
		generatePikeItem(itemModels, ItemRegistry.VENTUS_PIKE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/ventus"));
		generatePikeItem(itemModels, ItemRegistry.ASTRAL_PIKE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/astral"));
		generatePikeItem(itemModels, ItemRegistry.STARSTORM_PIKE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/starstorm"));
		generatePikeItem(itemModels, ItemRegistry.VOID_PIKE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/void"));
		generateStateOnly(itemModels, ItemRegistry.FLINTLOCK_PISTOL.get());
		generateStateOnly(itemModels, ItemRegistry.BLUNDERBUSS.get());
		generateMusket(itemModels, ItemRegistry.MUSKET.get());
		generateStateOnly(itemModels, ItemRegistry.FLARE_GUN.get());
		generateStateOnly(itemModels, ItemRegistry.HAND_CANNON.get());
		generateStateOnly(itemModels, ItemRegistry.DRAGONS_BREATH_CANNON.get());
		generateGauntletItem(itemModels, ItemRegistry.WOODEN_GAUNTLET.get(), ResourceLocation.withDefaultNamespace("block/stripped_oak_log"));
		generateGauntletItem(itemModels, ItemRegistry.STONE_GAUNTLET.get(), ResourceLocation.withDefaultNamespace("block/stone"));
		generateGauntletItem(itemModels, ItemRegistry.GOLDEN_GAUNTLET.get(), ResourceLocation.withDefaultNamespace("block/gold_block"));
		generateGauntletItem(itemModels, ItemRegistry.COPPER_GAUNTLET.get(), ResourceLocation.withDefaultNamespace("block/copper_block"));
		generateIronGauntletItem(itemModels, ItemRegistry.IRON_GAUNTLET.get(), ResourceLocation.withDefaultNamespace("block/iron_block"));
		generateGauntletItem(itemModels, ItemRegistry.COBALT_GAUNTLET.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cobalt_block"));
		generateGauntletItem(itemModels, ItemRegistry.DIAMOND_GAUNTLET.get(), ResourceLocation.withDefaultNamespace("block/diamond_block"));
		generateGauntletItem(itemModels, ItemRegistry.NETHERITE_GAUNTLET.get(), ResourceLocation.withDefaultNamespace("block/netherite_block"));
		generateGauntletItem(itemModels, ItemRegistry.MOLTEN_GAUNTLET.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/molten"));
		generateGauntletItem(itemModels, ItemRegistry.TESLA_GAUNTLET.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/tesla"));
		generateGauntletItem(itemModels, ItemRegistry.VENTUS_GAUNTLET.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/ventus"));
		generateGauntletItem(itemModels, ItemRegistry.ASTRAL_GAUNTLET.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/astral"));
		generateGauntletItem(itemModels, ItemRegistry.STARSTORM_GAUNTLET.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/starstorm"));
		generateGauntletItem(itemModels, ItemRegistry.VOID_GAUNTLET.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/custom/void"));
		itemModels.generateFlatItem(ItemRegistry.METEOR_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CURSED_SIGHT_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SCULK_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModels.generateFlatItem(ItemRegistry.RECOVERY_STAFF.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		generateBow(itemModels, ItemRegistry.ICE_BOW.get());
		generateBow(itemModels, ItemRegistry.DRAGONS_BREATH_BOW.get());
		generateBow(itemModels, ItemRegistry.AURORA_BOW.get());
		itemModels.generateFlatItem(ItemRegistry.WOODEN_SHARD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STONE_SHARD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_SHARD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLTEN_SHARD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARSTORM_SHARD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.OBSIDIAN_SHARD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.DIAMOND_SHARD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.OBSIDIAN_ROD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.WOODEN_TOOL_ROD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_NUGGET.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_NUGGET.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_INGOT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.RAW_COBALT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARSTORM_INGOT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ASTRAL_INGOT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ASTRAL_NUGGET.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.RAW_ASTRAL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_INGOT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_NUGGET.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ELECTRIC_INGOT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CONDUCTIVE_ALLOY.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLTEN_INGOT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLTEN_SMITHING_TEMPLATE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ENDER_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VOID_INGOT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.HANSIUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BLACKPOWDER.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SULFUR.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SULFUR_DUST.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.POTASSIUM_NITRATE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_STAFF_CORE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CURSED_SIGHT_STAFF_CORE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.WARDEN_HEART.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.AZUL_KEYSTONE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.AZUL_LOCATOR.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CELESTIAL_FRAGMENT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BROKEN_LENS.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.WOODEN_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STONE_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.GOLDEN_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.IRON_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.DIAMOND_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_PIKE_HEAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.WOODEN_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STONE_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.GOLDEN_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.IRON_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.DIAMOND_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.NETHERITE_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLTEN_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TESLA_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENTUS_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ASTRAL_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARSTORM_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VOID_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SMOKE_GRENADE_ARROW.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SMOKE_GRENADE_ARROW_RED.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get(), ModelTemplates.FLAT_ITEM);
		generateMusketBallItem(itemModels, ItemRegistry.WOODEN_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.STONE_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.GOLDEN_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.COPPER_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.IRON_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.COBALT_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.DIAMOND_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.NETHERITE_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.MOLTEN_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.TESLA_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.VENTUS_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.ASTRAL_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.STARSTORM_MUSKET_BALL.get());
		generateMusketBallItem(itemModels, ItemRegistry.VOID_MUSKET_BALL.get());
		generateStateOnly(itemModels, ItemRegistry.FLARE.get());
		generateStateOnly(itemModels, ItemRegistry.CANNONBALL.get());
		generateStateOnly(itemModels, ItemRegistry.EXPLOSIVE_CANNONBALL.get());
		itemModels.generateFlatItem(ItemRegistry.DRAGON_FIREBALL.get(), ModelTemplates.FLAT_ITEM);
		generateStateOnly(itemModels, ItemRegistry.MORTAR_SHELL.get());
		itemModels.generateFlatItem(ItemRegistry.GRENADE_ASSEMBLY.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TOOL_JOINT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.GAUNTLET_SCAFFOLDING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.FLINTLOCK_ASSEMBLY.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.TRIGGER_ASSEMBLY.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.HEAVY_WOODEN_STOCK.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.WOODEN_PISTOL_HANDLE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.IRON_BARREL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.EXTENDED_IRON_BARREL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SHORT_IRON_BARREL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.WIDE_GOLDEN_BARREL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SCOPE_MOUNT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SCOPE.get(), ModelTemplates.FLAT_ITEM);
		generateStateOnly(itemModels, ItemRegistry.SMOKE_GRENADE.get());
		generateStateOnly(itemModels, ItemRegistry.SMOKE_GRENADE_RED.get());
		generateStateOnly(itemModels, ItemRegistry.SMOKE_GRENADE_GREEN.get());
		generateStateOnly(itemModels, ItemRegistry.SMOKE_GRENADE_BLUE.get());
		generateStateOnly(itemModels, ItemRegistry.SMOKE_GRENADE_PURPLE.get());
		generateStateOnly(itemModels, ItemRegistry.SMOKE_GRENADE_YELLOW.get());
		generateStateOnly(itemModels, ItemRegistry.FLASHBANG.get());
		generateStateOnly(itemModels, ItemRegistry.MOLOTOV_COCKTAIL.get());
		itemModels.generateFlatItem(ItemRegistry.SMOKE_POWDER.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MORTAR_AND_PESTLE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.PLIERS.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BOTTLE_OF_ALCOHOL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BOTTLE_OF_WINE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CHOCOLATE_BAR.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MRE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MOLDY_BREAD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BANDAGE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.PAINKILLERS.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SYRINGE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MORPHINE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.USED_SYRINGE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.FIRST_AID_KIT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CLOTH_SCRAP.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MUD_BALL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BURNED_OAK_BOAT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BURNED_OAK_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARDUST_BOAT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARDUST_CHEST_BOAT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CURSE_CLEANING_SOAP.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SATCHEL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.POWDER_HORN.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BERSERKERS_AMULET.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.HANS_BLESSING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CELESTIAL_SPIRIT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VOID_BLESSING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BLADEMASTER_EMBLEM.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.DEADEYE_PENDANT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BLOATED_HEART.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.NETHERITE_SHIELD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.IRON_FIST.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.GLOVE_OF_RAPID_SWINGING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.HAND_OF_DOOM.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COPPER_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.IRON_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.GOLDEN_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.AMETHYST_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.EMERALD_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.COBALT_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.DIAMOND_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.NETHERITE_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.DEATH_GEM_RING.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MEDAL_OF_ADEQUACY.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.DEPTH_CHARM.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.REINFORCED_DEPTH_CHARM.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.INSOMNIA_AMULET.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.GOGGLES.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.LAVA_GOGGLES.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.NIGHT_VISION_GOGGLES.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.AGILITY_BRACELET.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BLOODY_CLOTH.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ANCIENT_SCROLL.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.HOLY_MANTLE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.VENSTRAL_JAR.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SUPER_BLANKET_CAPE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MEDAL_OF_HONOR.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MEDAL_OF_DISHONOR.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.BLOODY_SACRIFICE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.JONNYS_CURSE.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CHAMPION_KEYCARD.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.KILL_COUNTER.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.PEDESTAL_AUGMENT_SPEED.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.PEDESTAL_AUGMENT_ARMOR.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.PEDESTAL_AUGMENT_ENCHANTMENT.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.PEDESTAL_AUGMENT_CAPACITY.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MUSIC_DISC_STARLIGHT_PLAINS_THEME_1.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MUSIC_DISC_STARLIGHT_PLAINS_THEME_2.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MUSIC_DISC_TILTROS_WASTES_THEME.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MUSIC_DISC_DEADMANS_DESERT_THEME_1.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MUSIC_DISC_DEADMANS_DESERT_THEME_2.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateTrimmableItem(ItemRegistry.MOLTEN_HELMET.get(), IWEquipmentAssets.MOLTEN, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
		itemModels.generateTrimmableItem(ItemRegistry.MOLTEN_CHESTPLATE.get(), IWEquipmentAssets.MOLTEN, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
		itemModels.generateTrimmableItem(ItemRegistry.MOLTEN_LEGGINGS.get(), IWEquipmentAssets.MOLTEN, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
		itemModels.generateTrimmableItem(ItemRegistry.MOLTEN_BOOTS.get(), IWEquipmentAssets.MOLTEN, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
		itemModels.generateTrimmableItem(ItemRegistry.COPPER_HELMET.get(), IWEquipmentAssets.COPPER, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
		itemModels.generateTrimmableItem(ItemRegistry.COPPER_CHESTPLATE.get(), IWEquipmentAssets.COPPER, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
		itemModels.generateTrimmableItem(ItemRegistry.COPPER_LEGGINGS.get(), IWEquipmentAssets.COPPER, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
		itemModels.generateTrimmableItem(ItemRegistry.COPPER_BOOTS.get(), IWEquipmentAssets.COPPER, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
		itemModels.generateTrimmableItem(ItemRegistry.TESLA_HELMET.get(), IWEquipmentAssets.TESLA, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
		itemModels.generateTrimmableItem(ItemRegistry.TESLA_CHESTPLATE.get(), IWEquipmentAssets.TESLA, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
		itemModels.generateTrimmableItem(ItemRegistry.TESLA_LEGGINGS.get(), IWEquipmentAssets.TESLA, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
		itemModels.generateTrimmableItem(ItemRegistry.TESLA_BOOTS.get(), IWEquipmentAssets.TESLA, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
		itemModels.generateTrimmableItem(ItemRegistry.COBALT_HELMET.get(), IWEquipmentAssets.COBALT, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
		itemModels.generateTrimmableItem(ItemRegistry.COBALT_CHESTPLATE.get(), IWEquipmentAssets.COBALT, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
		itemModels.generateTrimmableItem(ItemRegistry.COBALT_LEGGINGS.get(), IWEquipmentAssets.COBALT, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
		itemModels.generateTrimmableItem(ItemRegistry.COBALT_BOOTS.get(), IWEquipmentAssets.COBALT, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
		itemModels.generateTrimmableItem(ItemRegistry.VENTUS_HELMET.get(), IWEquipmentAssets.VENTUS, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
		itemModels.generateTrimmableItem(ItemRegistry.VENTUS_CHESTPLATE.get(), IWEquipmentAssets.VENTUS, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
		itemModels.generateTrimmableItem(ItemRegistry.VENTUS_LEGGINGS.get(), IWEquipmentAssets.VENTUS, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
		itemModels.generateTrimmableItem(ItemRegistry.VENTUS_BOOTS.get(), IWEquipmentAssets.VENTUS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
		itemModels.generateTrimmableItem(ItemRegistry.ASTRAL_HELMET.get(), IWEquipmentAssets.ASTRAL, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
		itemModels.generateTrimmableItem(ItemRegistry.ASTRAL_CHESTPLATE.get(), IWEquipmentAssets.ASTRAL, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
		itemModels.generateTrimmableItem(ItemRegistry.ASTRAL_LEGGINGS.get(), IWEquipmentAssets.ASTRAL, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
		itemModels.generateTrimmableItem(ItemRegistry.ASTRAL_BOOTS.get(), IWEquipmentAssets.ASTRAL, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
		itemModels.generateTrimmableItem(ItemRegistry.STARSTORM_HELMET.get(), IWEquipmentAssets.STARSTORM, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
		itemModels.generateTrimmableItem(ItemRegistry.STARSTORM_CHESTPLATE.get(), IWEquipmentAssets.STARSTORM, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
		itemModels.generateTrimmableItem(ItemRegistry.STARSTORM_LEGGINGS.get(), IWEquipmentAssets.STARSTORM, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
		itemModels.generateTrimmableItem(ItemRegistry.STARSTORM_BOOTS.get(), IWEquipmentAssets.STARSTORM, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
		itemModels.generateTrimmableItem(ItemRegistry.PADDED_LEATHER_HELMET.get(), IWEquipmentAssets.PADDED_LEATHER, ItemModelGenerators.TRIM_PREFIX_HELMET, true);
		itemModels.generateTrimmableItem(ItemRegistry.PADDED_LEATHER_CHESTPLATE.get(), IWEquipmentAssets.PADDED_LEATHER, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, true);
		itemModels.generateTrimmableItem(ItemRegistry.PADDED_LEATHER_LEGGINGS.get(), IWEquipmentAssets.PADDED_LEATHER, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, true);
		itemModels.generateTrimmableItem(ItemRegistry.PADDED_LEATHER_BOOTS.get(), IWEquipmentAssets.PADDED_LEATHER, ItemModelGenerators.TRIM_PREFIX_BOOTS, true);
		itemModels.generateTrimmableItem(ItemRegistry.VOID_HELMET.get(), IWEquipmentAssets.VOID, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
		itemModels.generateTrimmableItem(ItemRegistry.VOID_CHESTPLATE.get(), IWEquipmentAssets.VOID, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
		itemModels.generateTrimmableItem(ItemRegistry.VOID_LEGGINGS.get(), IWEquipmentAssets.VOID, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
		itemModels.generateTrimmableItem(ItemRegistry.VOID_BOOTS.get(), IWEquipmentAssets.VOID, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
		itemModels.generateFlatItem(ItemRegistry.DYING_SOLDIER_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.THE_COMMANDER_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.MINUTEMAN_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.FIELD_MEDIC_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.WANDERING_WARRIOR_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.HANS_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SUPER_HANS_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.LAVA_REVENANT_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.ROCK_SPIDER_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.CELESTIAL_TOWER_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STARMITE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.FIREFLY_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STORM_CREEPER_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.EVIL_EYE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.STAR_WOLF_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SKYGAZER_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ItemRegistry.SKELETON_MERCHANT_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
	}

	private static void generatePikeItem(ItemModelGenerators models, Item item, ResourceLocation materialLocation) {
		models.itemModelOutput.accept(
				item,
				ItemModelUtils.plainModel(
						IWModelTemplates.PIKE.create(
								item,
								new TextureMapping()
										.put(IWModelTemplates.Slots.HANDLE, ResourceLocation.withDefaultNamespace("block/spruce_planks"))
										.put(IWModelTemplates.Slots.MATERIAL, materialLocation)
										.put(TextureSlot.PARTICLE, materialLocation),
								models.modelOutput
						)
				)
		);
	}

	private static void generateGauntletItem(ItemModelGenerators models, Item item, ResourceLocation materialLocation) {
		models.itemModelOutput.accept(
				item,
				ItemModelUtils.plainModel(
						IWModelTemplates.GAUNTLET.create(
								item,
								new TextureMapping()
										.put(IWModelTemplates.Slots.MATERIAL, materialLocation)
										.put(TextureSlot.PARTICLE, materialLocation),
								models.modelOutput
						)
				)
		);
	}

	private static void generateIronGauntletItem(ItemModelGenerators models, Item item, ResourceLocation materialLocation) {
		ItemModel.Unbaked model = ItemModelUtils.plainModel(
				IWModelTemplates.GAUNTLET.create(
						item,
						new TextureMapping()
								.put(IWModelTemplates.Slots.MATERIAL, materialLocation)
								.put(TextureSlot.PARTICLE, materialLocation),
						models.modelOutput
				)
		);

		ItemModel.Unbaked altModel = ItemModelUtils.plainModel(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/iron_gauntlet_alt"));

		models.itemModelOutput.accept(
				item,
				ItemModelUtils.conditional(
						new HasSpecificName("The Gunslinger"),
						altModel,
						model
				)
		);
	}

	private static void generateMusket(ItemModelGenerators models, Item item) {
		ItemModel.Unbaked model = ItemModelUtils.plainModel(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/musket"));
		ItemModel.Unbaked scopeModel = ItemModelUtils.plainModel(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "item/musket_scope"));

		models.itemModelOutput.accept(
				item,
				ItemModelUtils.conditional(
						new HasComponent(DataComponentTypeRegistry.SCOPE.get(), true),
						scopeModel,
						model
				)
		);
	}

	private static void generateBow(ItemModelGenerators models, Item item) {
		models.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM);
		models.generateBow(item);
	}

	private static void generateMusketBallItem(ItemModelGenerators models, Item item) {
		models.itemModelOutput.accept(
				item,
				ItemModelUtils.plainModel(
						IWModelTemplates.MUSKET_BALL.create(
								item,
								new TextureMapping()
										.put(TextureSlot.ALL, ModelLocationUtils.getModelLocation(item))
										.put(TextureSlot.PARTICLE, ModelLocationUtils.getModelLocation(item)),
								models.modelOutput
						)
				)
		);
	}

	private static void generateStateOnly(ItemModelGenerators models, Item item) {
		models.itemModelOutput.accept(
				item,
				ItemModelUtils.plainModel(
						ModelLocationUtils.getModelLocation(item)
				)
		);
	}
}