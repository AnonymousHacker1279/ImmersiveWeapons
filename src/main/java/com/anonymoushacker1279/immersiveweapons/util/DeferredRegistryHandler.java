package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.blocks.BlockItemBase;
import com.anonymoushacker1279.immersiveweapons.blocks.CopperOre;
import com.anonymoushacker1279.immersiveweapons.blocks.ElectricOre;
import com.anonymoushacker1279.immersiveweapons.blocks.MoltenBlock;
import com.anonymoushacker1279.immersiveweapons.blocks.MoltenOre;
import com.anonymoushacker1279.immersiveweapons.blocks.TeslaBlock;
import com.anonymoushacker1279.immersiveweapons.items.CopperAxe;
import com.anonymoushacker1279.immersiveweapons.items.CopperBoots;
import com.anonymoushacker1279.immersiveweapons.items.CopperChestplate;
import com.anonymoushacker1279.immersiveweapons.items.CopperHelmet;
import com.anonymoushacker1279.immersiveweapons.items.CopperHoe;
import com.anonymoushacker1279.immersiveweapons.items.CopperLeggings;
import com.anonymoushacker1279.immersiveweapons.items.CopperPickaxe;
import com.anonymoushacker1279.immersiveweapons.items.CopperShovel;
import com.anonymoushacker1279.immersiveweapons.items.CopperSword;
import com.anonymoushacker1279.immersiveweapons.items.ElectricBlade;
import com.anonymoushacker1279.immersiveweapons.items.ItemBase;
import com.anonymoushacker1279.immersiveweapons.items.MoltenAxe;
import com.anonymoushacker1279.immersiveweapons.items.MoltenBoots;
import com.anonymoushacker1279.immersiveweapons.items.MoltenChestplate;
import com.anonymoushacker1279.immersiveweapons.items.MoltenHelmet;
import com.anonymoushacker1279.immersiveweapons.items.MoltenHoe;
import com.anonymoushacker1279.immersiveweapons.items.MoltenLeggings;
import com.anonymoushacker1279.immersiveweapons.items.MoltenPickaxe;
import com.anonymoushacker1279.immersiveweapons.items.MoltenShovel;
import com.anonymoushacker1279.immersiveweapons.items.MoltenSword;
import com.anonymoushacker1279.immersiveweapons.structures.Abandoned_Factory;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
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
	
	public static final RegistryObject<Item> MOLTEN_SWORD = ITEMS.register("molten_sword", MoltenSword::new);
	public static final RegistryObject<Item> MOLTEN_PICKAXE = ITEMS.register("molten_pickaxe", MoltenPickaxe::new);
	public static final RegistryObject<Item> MOLTEN_AXE = ITEMS.register("molten_axe", MoltenAxe::new);
	public static final RegistryObject<Item> MOLTEN_SHOVEL = ITEMS.register("molten_shovel", MoltenShovel::new);
	public static final RegistryObject<Item> MOLTEN_HOE = ITEMS.register("molten_hoe", MoltenHoe::new);
	public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", CopperSword::new);
	public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", CopperPickaxe::new);
	public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", CopperAxe::new);
	public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", CopperShovel::new);
	public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", CopperHoe::new);
	public static final RegistryObject<Item> ELECTRIC_BLADE = ITEMS.register("electric_blade", ElectricBlade::new);
	// Items
	
	public static final RegistryObject<Item> MOLTEN_INGOT = ITEMS.register("molten_ingot", ItemBase::new);
	public static final RegistryObject<Item> MOLTEN_SHARD = ITEMS.register("molten_shard", ItemBase::new);
	public static final RegistryObject<Item> OBSIDIAN_SHARD = ITEMS.register("obsidian_shard", ItemBase::new);
	public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", ItemBase::new);
	public static final RegistryObject<Item> ELECTRIC_INGOT = ITEMS.register("electric_ingot", ItemBase::new);
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", ItemBase::new);
	public static final RegistryObject<Item> CONDUCTIVE_ALLOY = ITEMS.register("conductive_alloy", ItemBase::new);
	public static final RegistryObject<Item> TESLA_INGOT = ITEMS.register("tesla_ingot", ItemBase::new);
	public static final RegistryObject<Item> MOLTEN_PLATE = ITEMS.register("molten_plate", ItemBase::new);
	// Armor
	public static final RegistryObject<Item> MOLTEN_HELMET = ITEMS.register("molten_helmet", MoltenHelmet::new);
	public static final RegistryObject<Item> MOLTEN_CHESTPLATE = ITEMS.register("molten_chestplate", MoltenChestplate::new);
	public static final RegistryObject<Item> MOLTEN_LEGGINGS = ITEMS.register("molten_leggings", MoltenLeggings::new);
	public static final RegistryObject<Item> MOLTEN_BOOTS = ITEMS.register("molten_boots", MoltenBoots::new);
	public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet", CopperHelmet::new);
	public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", CopperChestplate::new);
	public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", CopperLeggings::new);
	public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots", CopperBoots::new);
	
	// Blocks
	public static final RegistryObject<Block> MOLTEN_ORE = BLOCKS.register("molten_ore", MoltenOre::new);
	public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", CopperOre::new);
	public static final RegistryObject<Block> MOLTEN_BLOCK = BLOCKS.register("molten_block", MoltenBlock::new);
	public static final RegistryObject<Block> ELECTRIC_ORE = BLOCKS.register("electric_ore", ElectricOre::new);
	public static final RegistryObject<Block> TESLA_BLOCK = BLOCKS.register("tesla_block", TeslaBlock::new);
	
	// Block Items
	public static final RegistryObject<BlockItem> MOLTEN_ORE_ITEM = ITEMS.register("molten_ore", () -> new BlockItemBase(MOLTEN_ORE.get()));
	public static final RegistryObject<BlockItem> MOLTEN_BLOCK_ITEM = ITEMS.register("molten_block", () -> new BlockItemBase(MOLTEN_BLOCK.get()));
	public static final RegistryObject<BlockItem> ELECTRIC_ORE_ITEM = ITEMS.register("electric_ore", () -> new BlockItemBase(ELECTRIC_ORE.get()));
	public static final RegistryObject<BlockItem> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItemBase(COPPER_ORE.get()));
	public static final RegistryObject<BlockItem> TESLA_BLOCK_ITEM = ITEMS.register("tesla_block", () -> new BlockItemBase(TESLA_BLOCK.get()));
	
	// Structures
	public static final RegistryObject<Structure<NoFeatureConfig>> ABANDONED_FACTORY = FEATURES.register("abandoned_factory", () -> new Abandoned_Factory(NoFeatureConfig::deserialize));
}
