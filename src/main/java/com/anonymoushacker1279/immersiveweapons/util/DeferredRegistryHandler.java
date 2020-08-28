package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.block.BasicOrientableBlock;
import com.anonymoushacker1279.immersiveweapons.block.BlockItemBase;
import com.anonymoushacker1279.immersiveweapons.items.CopperArmorItem;
import com.anonymoushacker1279.immersiveweapons.items.CopperArmorItemLeggings;
import com.anonymoushacker1279.immersiveweapons.items.ElectricBlade;
import com.anonymoushacker1279.immersiveweapons.items.FireAxe;
import com.anonymoushacker1279.immersiveweapons.items.FireHoe;
import com.anonymoushacker1279.immersiveweapons.items.FirePickaxe;
import com.anonymoushacker1279.immersiveweapons.items.FireShovel;
import com.anonymoushacker1279.immersiveweapons.items.FireSword;
import com.anonymoushacker1279.immersiveweapons.items.MoltenArmorItem;
import com.anonymoushacker1279.immersiveweapons.items.MoltenArmorItemLeggings;
import com.anonymoushacker1279.immersiveweapons.structures.Abandoned_Factory;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DeferredRegistryHandler {
	
	// Item Register
	@SuppressWarnings("deprecation")
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ImmersiveWeapons.MOD_ID);
	// Block Register
	@SuppressWarnings("deprecation")
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ImmersiveWeapons.MOD_ID);
	// Structure Register
	@SuppressWarnings("deprecation")
	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, ImmersiveWeapons.MOD_ID);
	
	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	// Tools
	
	public static final RegistryObject<Item> MOLTEN_SWORD = ITEMS.register("molten_sword", () -> new FireSword(CustomItemMaterials.MOLTEN, 3, -2.1f));
	public static final RegistryObject<Item> MOLTEN_PICKAXE = ITEMS.register("molten_pickaxe", () -> new FirePickaxe(CustomItemMaterials.MOLTEN, 2, -2.3F));
	public static final RegistryObject<Item> MOLTEN_AXE = ITEMS.register("molten_axe", () -> new FireAxe(CustomItemMaterials.MOLTEN, 6, -3.0f));
	public static final RegistryObject<Item> MOLTEN_SHOVEL = ITEMS.register("molten_shovel", () -> new FireShovel(CustomItemMaterials.MOLTEN, -1, -3.0f));
	public static final RegistryObject<Item> MOLTEN_HOE = ITEMS.register("molten_hoe", () -> new FireHoe(CustomItemMaterials.MOLTEN, 1.0f));
	public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(CustomItemMaterials.COPPER, 1, -2.4f, new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(CustomItemMaterials.COPPER, 1, -2.3F, new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(CustomItemMaterials.COPPER, 3, -3.0f, new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(CustomItemMaterials.COPPER, -2, -2.7f, new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(CustomItemMaterials.COPPER, 1.0f, new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> ELECTRIC_BLADE = ITEMS.register("electric_blade", ElectricBlade::new);
	
	// Items
	
	public static final RegistryObject<Item> MOLTEN_INGOT = ITEMS.register("molten_ingot", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> MOLTEN_SHARD = ITEMS.register("molten_shard", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> OBSIDIAN_SHARD = ITEMS.register("obsidian_shard", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> ELECTRIC_INGOT = ITEMS.register("electric_ingot", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> CONDUCTIVE_ALLOY = ITEMS.register("conductive_alloy", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> TESLA_INGOT = ITEMS.register("tesla_ingot", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	public static final RegistryObject<Item> MOLTEN_PLATE = ITEMS.register("molten_plate", () -> new Item(new Item.Properties().group(ImmersiveWeapons.TAB)));
	
	// Armor
	
	public static final RegistryObject<Item> MOLTEN_HELMET = ITEMS.register("molten_helmet", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlotType.HEAD));
	public static final RegistryObject<Item> MOLTEN_CHESTPLATE = ITEMS.register("molten_chestplate", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlotType.CHEST));
	public static final RegistryObject<Item> MOLTEN_LEGGINGS = ITEMS.register("molten_leggings", () -> new MoltenArmorItemLeggings(CustomArmorMaterials.MOLTEN, EquipmentSlotType.LEGS));
	public static final RegistryObject<Item> MOLTEN_BOOTS = ITEMS.register("molten_boots", () -> new MoltenArmorItem(CustomArmorMaterials.MOLTEN, EquipmentSlotType.LEGS));
	public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlotType.HEAD));
	public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlotType.CHEST));
	public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new CopperArmorItemLeggings(CustomArmorMaterials.COPPER, EquipmentSlotType.LEGS));
	public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new CopperArmorItem(CustomArmorMaterials.COPPER, EquipmentSlotType.FEET));
	
	// Blocks
	
	public static final RegistryObject<Block> MOLTEN_ORE = BLOCKS.register("molten_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 7.0f).sound(SoundType.STONE).harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> MOLTEN_BLOCK = BLOCKS.register("molten_block", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 9.0f).sound(SoundType.METAL).harvestLevel(0).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> ELECTRIC_ORE = BLOCKS.register("electric_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.0f, 5.0f).sound(SoundType.STONE).harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> TESLA_BLOCK = BLOCKS.register("tesla_block", () -> new BasicOrientableBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.8f, 8.8f).sound(SoundType.METAL).harvestLevel(0).harvestTool(ToolType.PICKAXE)));
	
	// Block Items
	
	public static final RegistryObject<BlockItem> MOLTEN_ORE_ITEM = ITEMS.register("molten_ore", () -> new BlockItemBase(MOLTEN_ORE.get()));
	public static final RegistryObject<BlockItem> MOLTEN_BLOCK_ITEM = ITEMS.register("molten_block", () -> new BlockItemBase(MOLTEN_BLOCK.get()));
	public static final RegistryObject<BlockItem> ELECTRIC_ORE_ITEM = ITEMS.register("electric_ore", () -> new BlockItemBase(ELECTRIC_ORE.get()));
	public static final RegistryObject<BlockItem> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItemBase(COPPER_ORE.get()));
	public static final RegistryObject<BlockItem> TESLA_BLOCK_ITEM = ITEMS.register("tesla_block", () -> new BlockItemBase(TESLA_BLOCK.get()));
	
	// Structures
	
	public static final RegistryObject<Structure<NoFeatureConfig>> ABANDONED_FACTORY = FEATURES.register("abandoned_factory", () -> new Abandoned_Factory(NoFeatureConfig::deserialize));
}
