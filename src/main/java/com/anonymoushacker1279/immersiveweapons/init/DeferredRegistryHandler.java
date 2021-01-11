package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.block.BasicOrientableBlock;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.CopperArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.DiamondArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.GoldArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.IronArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.NetheriteArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.StoneArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.WoodArrowEntity;
import com.anonymoushacker1279.immersiveweapons.items.CopperArmorItem;
import com.anonymoushacker1279.immersiveweapons.items.CustomArrows.CopperArrowItem;
import com.anonymoushacker1279.immersiveweapons.items.CustomArrows.DiamondArrowItem;
import com.anonymoushacker1279.immersiveweapons.items.CustomArrows.GoldArrowItem;
import com.anonymoushacker1279.immersiveweapons.items.CustomArrows.IronArrowItem;
import com.anonymoushacker1279.immersiveweapons.items.CustomArrows.NetheriteArrowItem;
import com.anonymoushacker1279.immersiveweapons.items.CustomArrows.StoneArrowItem;
import com.anonymoushacker1279.immersiveweapons.items.CustomArrows.WoodArrowItem;
import com.anonymoushacker1279.immersiveweapons.items.MoltenArmorItem;
import com.anonymoushacker1279.immersiveweapons.items.MoltenItem.MoltenAxe;
import com.anonymoushacker1279.immersiveweapons.items.MoltenItem.MoltenHoe;
import com.anonymoushacker1279.immersiveweapons.items.MoltenItem.MoltenPickaxe;
import com.anonymoushacker1279.immersiveweapons.items.MoltenItem.MoltenShovel;
import com.anonymoushacker1279.immersiveweapons.items.MoltenItem.MoltenSword;
import com.anonymoushacker1279.immersiveweapons.items.PikeItem.CopperPikeItem;
import com.anonymoushacker1279.immersiveweapons.items.PikeItem.DiamondPikeItem;
import com.anonymoushacker1279.immersiveweapons.items.PikeItem.GoldPikeItem;
import com.anonymoushacker1279.immersiveweapons.items.PikeItem.IronPikeItem;
import com.anonymoushacker1279.immersiveweapons.items.PikeItem.NetheritePikeItem;
import com.anonymoushacker1279.immersiveweapons.items.PikeItem.StonePikeItem;
import com.anonymoushacker1279.immersiveweapons.items.PikeItem.WoodPikeItem;
import com.anonymoushacker1279.immersiveweapons.items.TeslaArmorItem;
import com.anonymoushacker1279.immersiveweapons.items.TeslaItem.TeslaSword;
import com.anonymoushacker1279.immersiveweapons.util.CreativeTabSorter;
import com.anonymoushacker1279.immersiveweapons.util.CustomArmorMaterials;
import com.anonymoushacker1279.immersiveweapons.util.CustomItemMaterials;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DeferredRegistryHandler {
	
	// Item Register
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveWeapons.MOD_ID);
	// Block Register
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ImmersiveWeapons.MOD_ID);
	// Entity Register
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ImmersiveWeapons.MOD_ID);
	// Sound Register
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ImmersiveWeapons.MOD_ID);
	
	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final ItemGroup ITEM_GROUP = new CreativeTabSorter("ImmersiveWeaponsTab");
	
	// Tools
	
	public static final RegistryObject<Item> MOLTEN_SWORD = ITEMS.register("molten_sword", () -> new MoltenSword(CustomItemMaterials.MOLTEN, 3, -2.1f));
	public static final RegistryObject<Item> MOLTEN_PICKAXE = ITEMS.register("molten_pickaxe", () -> new MoltenPickaxe(CustomItemMaterials.MOLTEN, 2, -2.3F));
	public static final RegistryObject<Item> MOLTEN_AXE = ITEMS.register("molten_axe", () -> new MoltenAxe(CustomItemMaterials.MOLTEN, 6, -3.0f));
	public static final RegistryObject<Item> MOLTEN_SHOVEL = ITEMS.register("molten_shovel", () -> new MoltenShovel(CustomItemMaterials.MOLTEN, -1, -3.0f));
	public static final RegistryObject<Item> MOLTEN_HOE = ITEMS.register("molten_hoe", () -> new MoltenHoe(CustomItemMaterials.MOLTEN, 0, 1.0f));
	
	public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(CustomItemMaterials.COPPER, 2, -2.4f, new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(CustomItemMaterials.COPPER, 1, -2.3F, new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(CustomItemMaterials.COPPER, 3, -3.0f, new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(CustomItemMaterials.COPPER, -2, -2.7f, new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(CustomItemMaterials.COPPER, 0, 1.0f, new Item.Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> TESLA_SWORD = ITEMS.register("tesla_sword", () -> new TeslaSword());
	
	public static final RegistryObject<Item> WOOD_PIKE = ITEMS.register("wood_pike", () -> new WoodPikeItem((new Item.Properties()).maxDamage(59).group(ITEM_GROUP), 4.0d, -2.6d));
	public static final RegistryObject<Item> STONE_PIKE = ITEMS.register("stone_pike", () -> new StonePikeItem((new Item.Properties()).maxDamage(131).group(ITEM_GROUP), 5.0d, -2.6d));
	public static final RegistryObject<Item> GOLD_PIKE = ITEMS.register("gold_pike", () -> new GoldPikeItem((new Item.Properties()).maxDamage(32).group(ITEM_GROUP), 4.0d, -2.6d));
	public static final RegistryObject<Item> COPPER_PIKE = ITEMS.register("copper_pike", () -> new CopperPikeItem((new Item.Properties()).maxDamage(180).group(ITEM_GROUP), 6.0d, -2.6d));
	public static final RegistryObject<Item> IRON_PIKE = ITEMS.register("iron_pike", () -> new IronPikeItem((new Item.Properties()).maxDamage(250).group(ITEM_GROUP), 6.0d, -2.6d));
	public static final RegistryObject<Item> DIAMOND_PIKE = ITEMS.register("diamond_pike", () -> new DiamondPikeItem((new Item.Properties()).maxDamage(1561).group(ITEM_GROUP), 7.0d, -2.6d));
	public static final RegistryObject<Item> NETHERITE_PIKE = ITEMS.register("netherite_pike", () -> new NetheritePikeItem((new Item.Properties()).maxDamage(2031).group(ITEM_GROUP).isImmuneToFire(), 8.0d, -2.6d));

	// Items

	public static final RegistryObject<Item> WOOD_SHARD = ITEMS.register("wood_shard", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> STONE_SHARD = ITEMS.register("stone_shard", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> OBSIDIAN_SHARD = ITEMS.register("obsidian_shard", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> MOLTEN_SHARD = ITEMS.register("molten_shard", () -> new Item(new Item.Properties().group(ITEM_GROUP).isImmuneToFire()));

	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> MOLTEN_PLATE = ITEMS.register("molten_plate", () -> new Item(new Item.Properties().group(ITEM_GROUP).isImmuneToFire()));
	public static final RegistryObject<Item> TESLA_PLATE = ITEMS.register("tesla_plate", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

	public static final RegistryObject<Item> WOOD_TOOL_ROD = ITEMS.register("wood_tool_rod", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> MOLTEN_INGOT = ITEMS.register("molten_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP).isImmuneToFire()));
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> CONDUCTIVE_ALLOY = ITEMS.register("conductive_alloy", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> ELECTRIC_INGOT = ITEMS.register("electric_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> TESLA_INGOT = ITEMS.register("tesla_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

	public static final RegistryObject<Item> WOOD_PIKE_HEAD = ITEMS.register("wood_pike_head", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> STONE_PIKE_HEAD = ITEMS.register("stone_pike_head", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> GOLD_PIKE_HEAD = ITEMS.register("gold_pike_head", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_PIKE_HEAD = ITEMS.register("copper_pike_head", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> IRON_PIKE_HEAD = ITEMS.register("iron_pike_head", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> DIAMOND_PIKE_HEAD = ITEMS.register("diamond_pike_head", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

	public static final RegistryObject<Item> WOOD_ARROW = ITEMS.register("wood_arrow", () -> new WoodArrowItem(new Item.Properties().group(ITEM_GROUP), 1.65d));
	public static final RegistryObject<Item> STONE_ARROW = ITEMS.register("stone_arrow", () -> new StoneArrowItem(new Item.Properties().group(ITEM_GROUP), 1.85d));
	public static final RegistryObject<Item> GOLD_ARROW = ITEMS.register("gold_arrow", () -> new GoldArrowItem(new Item.Properties().group(ITEM_GROUP), 2.10d));
	public static final RegistryObject<Item> COPPER_ARROW = ITEMS.register("copper_arrow", () -> new CopperArrowItem(new Item.Properties().group(ITEM_GROUP), 2.15d));
	public static final RegistryObject<Item> IRON_ARROW = ITEMS.register("iron_arrow", () -> new IronArrowItem(new Item.Properties().group(ITEM_GROUP), 2.35d));
	public static final RegistryObject<Item> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new DiamondArrowItem(new Item.Properties().group(ITEM_GROUP), 3.00d));
	public static final RegistryObject<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new NetheriteArrowItem(new Item.Properties().group(ITEM_GROUP).isImmuneToFire(), 5.75d));

	// Armor
	
	public static final RegistryObject<Item> MOLTEN_HELMET = ITEMS.register("molten_helmet", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlotType.HEAD, 1));
	public static final RegistryObject<Item> MOLTEN_CHESTPLATE = ITEMS.register("molten_chestplate", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlotType.CHEST, 1));
	public static final RegistryObject<Item> MOLTEN_LEGGINGS = ITEMS.register("molten_leggings", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlotType.LEGS, 2));
	public static final RegistryObject<Item> MOLTEN_BOOTS = ITEMS.register("molten_boots", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlotType.FEET, 1));
	
	public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlotType.HEAD, 1));
	public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlotType.CHEST, 1));
	public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlotType.LEGS, 2));
	public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlotType.FEET, 1));
	
	public static final RegistryObject<Item> TESLA_HELMET = ITEMS.register("tesla_helmet", () -> new TeslaArmorItem(CustomArmorMaterials.TESLA, EquipmentSlotType.HEAD, 1));
	public static final RegistryObject<Item> TESLA_CHESTPLATE = ITEMS.register("tesla_chestplate", () -> new TeslaArmorItem(CustomArmorMaterials.TESLA, EquipmentSlotType.CHEST, 1));
	public static final RegistryObject<Item> TESLA_LEGGINGS = ITEMS.register("tesla_leggings", () -> new TeslaArmorItem(CustomArmorMaterials.TESLA, EquipmentSlotType.LEGS, 2));
	public static final RegistryObject<Item> TESLA_BOOTS = ITEMS.register("tesla_boots", () -> new TeslaArmorItem(CustomArmorMaterials.TESLA, EquipmentSlotType.FEET, 1));

	
	// Blocks
	
	public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE).setRequiresTool().harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> MOLTEN_ORE = BLOCKS.register("molten_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(6.0f, 8.0f).sound(SoundType.STONE).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> ELECTRIC_ORE = BLOCKS.register("electric_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(6.0f, 8.0f).sound(SoundType.STONE).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<Block> MOLTEN_BLOCK = BLOCKS.register("molten_block", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(13.0f, 15.0f).sound(SoundType.METAL).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> TESLA_BLOCK = BLOCKS.register("tesla_block", () -> new BasicOrientableBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(12.2f, 12.4f).sound(SoundType.METAL).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	
	// Block Items
	
	public static final RegistryObject<BlockItem> MOLTEN_ORE_ITEM = ITEMS.register("molten_ore", () -> new BlockItem(MOLTEN_ORE.get(), new Item.Properties().group(ITEM_GROUP).isImmuneToFire()));
	public static final RegistryObject<BlockItem> MOLTEN_BLOCK_ITEM = ITEMS.register("molten_block", () -> new BlockItem(MOLTEN_BLOCK.get(), new Item.Properties().group(ITEM_GROUP).isImmuneToFire()));
	public static final RegistryObject<BlockItem> ELECTRIC_ORE_ITEM = ITEMS.register("electric_ore", () -> new BlockItem(ELECTRIC_ORE.get(), new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Item.Properties().group(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> TESLA_BLOCK_ITEM = ITEMS.register("tesla_block", () -> new BlockItem(TESLA_BLOCK.get(), new Item.Properties().group(ITEM_GROUP)));
	
	// Entities
	
	public static final RegistryObject<EntityType<WoodArrowEntity>> WOOD_ARROW_ENTITY = ENTITY_TYPES.register("wood_arrow", () -> EntityType.Builder.<WoodArrowEntity>create(WoodArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_arrow").toString()));
	public static final RegistryObject<EntityType<StoneArrowEntity>> STONE_ARROW_ENTITY = ENTITY_TYPES.register("stone_arrow", () -> EntityType.Builder.<StoneArrowEntity>create(StoneArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stone_arrow").toString()));
	public static final RegistryObject<EntityType<GoldArrowEntity>> GOLD_ARROW_ENTITY = ENTITY_TYPES.register("gold_arrow", () -> EntityType.Builder.<GoldArrowEntity>create(GoldArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_arrow").toString()));
	public static final RegistryObject<EntityType<CopperArrowEntity>> COPPER_ARROW_ENTITY = ENTITY_TYPES.register("copper_arrow", () -> EntityType.Builder.<CopperArrowEntity>create(CopperArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_arrow").toString()));
	public static final RegistryObject<EntityType<IronArrowEntity>> IRON_ARROW_ENTITY = ENTITY_TYPES.register("iron_arrow", () -> EntityType.Builder.<IronArrowEntity>create(IronArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "iron_arrow").toString()));
	public static final RegistryObject<EntityType<DiamondArrowEntity>> DIAMOND_ARROW_ENTITY = ENTITY_TYPES.register("diamond_arrow", () -> EntityType.Builder.<DiamondArrowEntity>create(DiamondArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "diamond_arrow").toString()));
	public static final RegistryObject<EntityType<NetheriteArrowEntity>> NETHERITE_ARROW_ENTITY = ENTITY_TYPES.register("netherite_arrow", () -> EntityType.Builder.<NetheriteArrowEntity>create(NetheriteArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "netherite_arrow").toString()));

	// Sounds
	
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_EFFECT = SOUND_EVENTS.register("tesla_armor_effect", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_effect")));
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_POWER_DOWN = SOUND_EVENTS.register("tesla_armor_power_dodwn", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_power_down")));
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_POWER_UP = SOUND_EVENTS.register("tesla_armor_power_up", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_power_up")));
	
}