package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.block.BarrelTapBlock;
import com.anonymoushacker1279.immersiveweapons.block.BasicOrientableBlock;
import com.anonymoushacker1279.immersiveweapons.block.SmallPartsTable;
import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleType;
import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.CopperBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.DiamondBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.GoldBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.IronBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.NetheriteBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.StoneBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.WoodBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.CopperArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.DiamondArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.GoldArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.IronArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.NetheriteArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.SmokeBombArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.StoneArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.WoodArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity;
import com.anonymoushacker1279.immersiveweapons.item.BottleItem.AlcoholBottleItem;
import com.anonymoushacker1279.immersiveweapons.item.BottleItem.WineBottleItem;
import com.anonymoushacker1279.immersiveweapons.item.Bullets.CopperBulletItem;
import com.anonymoushacker1279.immersiveweapons.item.Bullets.DiamondBulletItem;
import com.anonymoushacker1279.immersiveweapons.item.Bullets.GoldBulletItem;
import com.anonymoushacker1279.immersiveweapons.item.Bullets.IronBulletItem;
import com.anonymoushacker1279.immersiveweapons.item.Bullets.NetheriteBulletItem;
import com.anonymoushacker1279.immersiveweapons.item.Bullets.StoneBulletItem;
import com.anonymoushacker1279.immersiveweapons.item.Bullets.WoodBulletItem;
import com.anonymoushacker1279.immersiveweapons.item.CopperArmorItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrows.CopperArrowItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrows.DiamondArrowItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrows.GoldArrowItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrows.IronArrowItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrows.NetheriteArrowItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrows.SmokeBombArrowItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrows.StoneArrowItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrows.WoodArrowItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomContainerItem.BasicContainerItem;
import com.anonymoushacker1279.immersiveweapons.item.CustomContainerItem.BlueprintItem;
import com.anonymoushacker1279.immersiveweapons.item.MolotovItem;
import com.anonymoushacker1279.immersiveweapons.item.MoltenArmorItem;
import com.anonymoushacker1279.immersiveweapons.item.MoltenItem.MoltenAxe;
import com.anonymoushacker1279.immersiveweapons.item.MoltenItem.MoltenHoe;
import com.anonymoushacker1279.immersiveweapons.item.MoltenItem.MoltenPickaxe;
import com.anonymoushacker1279.immersiveweapons.item.MoltenItem.MoltenShovel;
import com.anonymoushacker1279.immersiveweapons.item.MoltenItem.MoltenSword;
import com.anonymoushacker1279.immersiveweapons.item.PikeItem.CopperPikeItem;
import com.anonymoushacker1279.immersiveweapons.item.PikeItem.DiamondPikeItem;
import com.anonymoushacker1279.immersiveweapons.item.PikeItem.GoldPikeItem;
import com.anonymoushacker1279.immersiveweapons.item.PikeItem.IronPikeItem;
import com.anonymoushacker1279.immersiveweapons.item.PikeItem.NetheritePikeItem;
import com.anonymoushacker1279.immersiveweapons.item.PikeItem.StonePikeItem;
import com.anonymoushacker1279.immersiveweapons.item.PikeItem.WoodPikeItem;
import com.anonymoushacker1279.immersiveweapons.item.SimplePistolItem;
import com.anonymoushacker1279.immersiveweapons.item.SmokeBombItem;
import com.anonymoushacker1279.immersiveweapons.item.TeslaArmorItem;
import com.anonymoushacker1279.immersiveweapons.item.TeslaItem.TeslaAxe;
import com.anonymoushacker1279.immersiveweapons.item.TeslaItem.TeslaHoe;
import com.anonymoushacker1279.immersiveweapons.item.TeslaItem.TeslaPickaxe;
import com.anonymoushacker1279.immersiveweapons.item.TeslaItem.TeslaShovel;
import com.anonymoushacker1279.immersiveweapons.item.TeslaItem.TeslaSword;
import com.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;
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
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
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
	// Container Register
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ImmersiveWeapons.MOD_ID);
	// Recipe Register
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ImmersiveWeapons.MOD_ID);
	// Particle Register
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ImmersiveWeapons.MOD_ID);

	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		RECIPE_SERIALIZER.register(FMLJavaModLoadingContext.get().getModEventBus());
		PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final ItemGroup ITEM_GROUP = new CreativeTabSorter("ImmersiveWeaponsTab");
	
	// Tools
	
	public static final RegistryObject<Item> MOLTEN_SWORD = ITEMS.register("molten_sword", () -> new MoltenSword(CustomItemMaterials.MOLTEN, 3, -2.1f));
	public static final RegistryObject<Item> MOLTEN_PICKAXE = ITEMS.register("molten_pickaxe", () -> new MoltenPickaxe(CustomItemMaterials.MOLTEN, 2, -2.3F));
	public static final RegistryObject<Item> MOLTEN_AXE = ITEMS.register("molten_axe", () -> new MoltenAxe(CustomItemMaterials.MOLTEN, 6, -3.0f));
	public static final RegistryObject<Item> MOLTEN_SHOVEL = ITEMS.register("molten_shovel", () -> new MoltenShovel(CustomItemMaterials.MOLTEN, -1, -3.0f));
	public static final RegistryObject<Item> MOLTEN_HOE = ITEMS.register("molten_hoe", () -> new MoltenHoe(CustomItemMaterials.MOLTEN, -3, 1.0f));
	
	public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(CustomItemMaterials.COPPER, 2, -2.4f, new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(CustomItemMaterials.COPPER, 1, -2.3F, new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(CustomItemMaterials.COPPER, 3, -3.0f, new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(CustomItemMaterials.COPPER, -1, -2.7f, new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(CustomItemMaterials.COPPER, -2, 1.0f, new Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> TESLA_SWORD = ITEMS.register("tesla_sword", () -> new TeslaSword(CustomItemMaterials.TESLA, 5, -2.1f));
	public static final RegistryObject<Item> TESLA_PICKAXE = ITEMS.register("tesla_pickaxe", () -> new TeslaPickaxe(CustomItemMaterials.TESLA, 4, -2.3f));
	public static final RegistryObject<Item> TESLA_AXE = ITEMS.register("tesla_axe", () -> new TeslaAxe(CustomItemMaterials.TESLA, 7, -3.0f));
	public static final RegistryObject<Item> TESLA_SHOVEL = ITEMS.register("tesla_shovel", () -> new TeslaShovel(CustomItemMaterials.TESLA, 0, -3.0f));
	public static final RegistryObject<Item> TESLA_HOE = ITEMS.register("tesla_hoe", () -> new TeslaHoe(CustomItemMaterials.TESLA, -3, 1.0f));
	
	public static final RegistryObject<Item> WOOD_PIKE = ITEMS.register("wood_pike", () -> new WoodPikeItem((new Properties()).maxDamage(59).group(ITEM_GROUP), 4.0d, -2.6d));
	public static final RegistryObject<Item> STONE_PIKE = ITEMS.register("stone_pike", () -> new StonePikeItem((new Properties()).maxDamage(131).group(ITEM_GROUP), 5.0d, -2.6d));
	public static final RegistryObject<Item> GOLD_PIKE = ITEMS.register("gold_pike", () -> new GoldPikeItem((new Properties()).maxDamage(32).group(ITEM_GROUP), 4.0d, -2.6d));
	public static final RegistryObject<Item> COPPER_PIKE = ITEMS.register("copper_pike", () -> new CopperPikeItem((new Properties()).maxDamage(180).group(ITEM_GROUP), 6.0d, -2.6d));
	public static final RegistryObject<Item> IRON_PIKE = ITEMS.register("iron_pike", () -> new IronPikeItem((new Properties()).maxDamage(250).group(ITEM_GROUP), 6.0d, -2.6d));
	public static final RegistryObject<Item> DIAMOND_PIKE = ITEMS.register("diamond_pike", () -> new DiamondPikeItem((new Properties()).maxDamage(1561).group(ITEM_GROUP), 7.0d, -2.6d));
	public static final RegistryObject<Item> NETHERITE_PIKE = ITEMS.register("netherite_pike", () -> new NetheritePikeItem((new Properties()).maxDamage(2031).group(ITEM_GROUP).isImmuneToFire(), 8.0d, -2.6d));

	public static final RegistryObject<Item> FLINTLOCK_PISTOL = ITEMS.register("flintlock_pistol", () -> new SimplePistolItem(new Properties().group(ITEM_GROUP).maxDamage(499)));
	
	// Items

	public static final RegistryObject<Item> WOOD_SHARD = ITEMS.register("wood_shard", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> STONE_SHARD = ITEMS.register("stone_shard", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> OBSIDIAN_SHARD = ITEMS.register("obsidian_shard", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> MOLTEN_SHARD = ITEMS.register("molten_shard", () -> new Item(new Properties().group(ITEM_GROUP).isImmuneToFire()));

	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> MOLTEN_PLATE = ITEMS.register("molten_plate", () -> new Item(new Properties().group(ITEM_GROUP).isImmuneToFire()));
	public static final RegistryObject<Item> TESLA_PLATE = ITEMS.register("tesla_plate", () -> new Item(new Properties().group(ITEM_GROUP)));

	public static final RegistryObject<Item> WOOD_TOOL_ROD = ITEMS.register("wood_tool_rod", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () -> new Item(new Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> MOLTEN_INGOT = ITEMS.register("molten_ingot", () -> new Item(new Properties().group(ITEM_GROUP).isImmuneToFire()));
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> CONDUCTIVE_ALLOY = ITEMS.register("conductive_alloy", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> ELECTRIC_INGOT = ITEMS.register("electric_ingot", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> TESLA_INGOT = ITEMS.register("tesla_ingot", () -> new Item(new Properties().group(ITEM_GROUP)));

	public static final RegistryObject<Item> WOOD_PIKE_HEAD = ITEMS.register("wood_pike_head", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> STONE_PIKE_HEAD = ITEMS.register("stone_pike_head", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> GOLD_PIKE_HEAD = ITEMS.register("gold_pike_head", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_PIKE_HEAD = ITEMS.register("copper_pike_head", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> IRON_PIKE_HEAD = ITEMS.register("iron_pike_head", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> DIAMOND_PIKE_HEAD = ITEMS.register("diamond_pike_head", () -> new Item(new Properties().group(ITEM_GROUP)));

	public static final RegistryObject<Item> WOOD_ARROW = ITEMS.register("wood_arrow", () -> new WoodArrowItem(new Properties().group(ITEM_GROUP), 1.65d));
	public static final RegistryObject<Item> STONE_ARROW = ITEMS.register("stone_arrow", () -> new StoneArrowItem(new Properties().group(ITEM_GROUP), 1.85d));
	public static final RegistryObject<Item> GOLD_ARROW = ITEMS.register("gold_arrow", () -> new GoldArrowItem(new Properties().group(ITEM_GROUP), 2.10d));
	public static final RegistryObject<Item> COPPER_ARROW = ITEMS.register("copper_arrow", () -> new CopperArrowItem(new Properties().group(ITEM_GROUP), 2.15d));
	public static final RegistryObject<Item> IRON_ARROW = ITEMS.register("iron_arrow", () -> new IronArrowItem(new Properties().group(ITEM_GROUP), 2.35d));
	public static final RegistryObject<Item> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new DiamondArrowItem(new Properties().group(ITEM_GROUP), 3.00d));
	public static final RegistryObject<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new NetheriteArrowItem(new Properties().group(ITEM_GROUP).isImmuneToFire(), 5.75d));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW = ITEMS.register("smoke_bomb_arrow", () -> new SmokeBombArrowItem(new Properties().group(ITEM_GROUP), 2.00d, "none"));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_RED = ITEMS.register("smoke_bomb_arrow_red", () -> new SmokeBombArrowItem(new Properties().group(ITEM_GROUP), 2.00d, "red"));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_GREEN = ITEMS.register("smoke_bomb_arrow_green", () -> new SmokeBombArrowItem(new Properties().group(ITEM_GROUP), 2.00d, "green"));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_BLUE = ITEMS.register("smoke_bomb_arrow_blue", () -> new SmokeBombArrowItem(new Properties().group(ITEM_GROUP), 2.00d, "blue"));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_PURPLE = ITEMS.register("smoke_bomb_arrow_purple", () -> new SmokeBombArrowItem(new Properties().group(ITEM_GROUP), 2.00d, "purple"));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_YELLOW = ITEMS.register("smoke_bomb_arrow_yellow", () -> new SmokeBombArrowItem(new Properties().group(ITEM_GROUP), 2.00d, "yellow"));

	// public static final RegistryObject<Item> EMPTY_BULLET_CASING = ITEMS.register("empty_bullet_casing", () -> new Item(new Properties().group(ITEM_GROUP)));	// Disabled for now: Was going to be used previously but I don't have any use for it yet
	
	public static final RegistryObject<Item> WOOD_MUSKET_BALL = ITEMS.register("wood_musket_ball", () -> new WoodBulletItem(new Properties().group(ITEM_GROUP), 2.0d));
	public static final RegistryObject<Item> STONE_MUSKET_BALL = ITEMS.register("stone_musket_ball", () -> new StoneBulletItem(new Properties().group(ITEM_GROUP), 2.20d));
	public static final RegistryObject<Item> GOLD_MUSKET_BALL = ITEMS.register("gold_musket_ball", () -> new GoldBulletItem(new Properties().group(ITEM_GROUP), 2.30d));
	public static final RegistryObject<Item> COPPER_MUSKET_BALL = ITEMS.register("copper_musket_ball", () -> new CopperBulletItem(new Properties().group(ITEM_GROUP), 2.40d));
	public static final RegistryObject<Item> IRON_MUSKET_BALL = ITEMS.register("iron_musket_ball", () -> new IronBulletItem(new Properties().group(ITEM_GROUP), 2.65d));
	public static final RegistryObject<Item> DIAMOND_MUSKET_BALL = ITEMS.register("diamond_musket_ball", () -> new DiamondBulletItem(new Properties().group(ITEM_GROUP), 3.35d));
	public static final RegistryObject<Item> NETHERITE_MUSKET_BALL = ITEMS.register("netherite_musket_ball", () -> new NetheriteBulletItem(new Properties().group(ITEM_GROUP), 6.50d));

	public static final RegistryObject<Item> BLANK_BLUEPRINT = ITEMS.register("blank_blueprint", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> SMALL_PARTS_BLUEPRINT = ITEMS.register("small_parts_blueprint", () -> new BlueprintItem(new Properties().group(ITEM_GROUP).maxStackSize(1)));
	public static final RegistryObject<Item> SMALL_PARTS_METAL_THROWABLE_BOMB_BLUEPRINT = ITEMS.register("small_parts_metal_throwable_bomb_blueprint", () -> new BlueprintItem(new Properties().group(ITEM_GROUP).maxStackSize(1)));

	public static final RegistryObject<Item> SMALL_PARTS_IRON = ITEMS.register("small_parts_iron", () -> new Item(new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<Item> SMALL_PARTS_METAL_THROWABLE_BOMB = ITEMS.register("small_parts_metal_throwable_bomb", () -> new Item(new Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> SMOKE_BOMB = ITEMS.register("smoke_bomb", () -> new SmokeBombItem(new Properties().group(ITEM_GROUP).maxStackSize(16), "none"));
	public static final RegistryObject<Item> SMOKE_BOMB_RED = ITEMS.register("smoke_bomb_red", () -> new SmokeBombItem(new Properties().group(ITEM_GROUP).maxStackSize(16), "red"));
	public static final RegistryObject<Item> SMOKE_BOMB_GREEN = ITEMS.register("smoke_bomb_green", () -> new SmokeBombItem(new Properties().group(ITEM_GROUP).maxStackSize(16), "green"));
	public static final RegistryObject<Item> SMOKE_BOMB_BLUE = ITEMS.register("smoke_bomb_blue", () -> new SmokeBombItem(new Properties().group(ITEM_GROUP).maxStackSize(16), "blue"));
	public static final RegistryObject<Item> SMOKE_BOMB_PURPLE = ITEMS.register("smoke_bomb_purple", () -> new SmokeBombItem(new Properties().group(ITEM_GROUP).maxStackSize(16), "purple"));
	public static final RegistryObject<Item> SMOKE_BOMB_YELLOW = ITEMS.register("smoke_bomb_yellow", () -> new SmokeBombItem(new Properties().group(ITEM_GROUP).maxStackSize(16), "yellow"));

	public static final RegistryObject<Item> MOLOTOV_COCKTAIL = ITEMS.register("molotov_cocktail", () -> new MolotovItem(new Properties().group(ITEM_GROUP).maxStackSize(16)));
	
	public static final RegistryObject<Item> SMOKE_POWDER = ITEMS.register("smoke_powder", () -> new Item(new Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle", () -> new BasicContainerItem(new Properties().group(ITEM_GROUP)));
	
	public static final RegistryObject<Item> BOTTLE_OF_ALCOHOL = ITEMS.register("bottle_of_alcohol", () -> new AlcoholBottleItem(new Properties().group(ITEM_GROUP).maxStackSize(16)));
	public static final RegistryObject<Item> BOTTLE_OF_WINE = ITEMS.register("bottle_of_wine", () -> new WineBottleItem(new Properties().group(ITEM_GROUP).maxStackSize(16)));

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
	
	public static final RegistryObject<Block> MOLTEN_BLOCK = BLOCKS.register("molten_block", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(45.0f, 1100.0f).sound(SoundType.METAL).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> TESLA_BLOCK = BLOCKS.register("tesla_block", () -> new BasicOrientableBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0f, 5.0f).sound(SoundType.METAL).setRequiresTool().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<Block> SMALL_PARTS_TABLE = BLOCKS.register("small_parts_table", () -> new SmallPartsTable(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.WOOD).harvestLevel(0).harvestTool(ToolType.AXE)));
	
	public static final RegistryObject<Block> BARREL_TAP = BLOCKS.register("barrel_tap", () -> new BarrelTapBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f, 1.0f).sound(SoundType.METAL).harvestLevel(0)));
	
	// Block Items
	
	public static final RegistryObject<BlockItem> MOLTEN_ORE_ITEM = ITEMS.register("molten_ore", () -> new BlockItem(MOLTEN_ORE.get(), new Properties().group(ITEM_GROUP).isImmuneToFire()));
	public static final RegistryObject<BlockItem> MOLTEN_BLOCK_ITEM = ITEMS.register("molten_block", () -> new BlockItem(MOLTEN_BLOCK.get(), new Properties().group(ITEM_GROUP).isImmuneToFire()));
	public static final RegistryObject<BlockItem> ELECTRIC_ORE_ITEM = ITEMS.register("electric_ore", () -> new BlockItem(ELECTRIC_ORE.get(), new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> TESLA_BLOCK_ITEM = ITEMS.register("tesla_block", () -> new BlockItem(TESLA_BLOCK.get(), new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> SMALL_PARTS_TABLE_ITEM = ITEMS.register("small_parts_table", () -> new BlockItem(SMALL_PARTS_TABLE.get(), new Properties().group(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BARREL_TAP_ITEM = ITEMS.register("barrel_tap", () -> new BlockItem(BARREL_TAP.get(), new Properties().group(ITEM_GROUP)));

	// Entities
	
	public static final RegistryObject<EntityType<WoodArrowEntity>> WOOD_ARROW_ENTITY = ENTITY_TYPES.register("wood_arrow", () -> EntityType.Builder.<WoodArrowEntity>create(WoodArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_arrow").toString()));
	public static final RegistryObject<EntityType<StoneArrowEntity>> STONE_ARROW_ENTITY = ENTITY_TYPES.register("stone_arrow", () -> EntityType.Builder.<StoneArrowEntity>create(StoneArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stone_arrow").toString()));
	public static final RegistryObject<EntityType<GoldArrowEntity>> GOLD_ARROW_ENTITY = ENTITY_TYPES.register("gold_arrow", () -> EntityType.Builder.<GoldArrowEntity>create(GoldArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_arrow").toString()));
	public static final RegistryObject<EntityType<CopperArrowEntity>> COPPER_ARROW_ENTITY = ENTITY_TYPES.register("copper_arrow", () -> EntityType.Builder.<CopperArrowEntity>create(CopperArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_arrow").toString()));
	public static final RegistryObject<EntityType<IronArrowEntity>> IRON_ARROW_ENTITY = ENTITY_TYPES.register("iron_arrow", () -> EntityType.Builder.<IronArrowEntity>create(IronArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "iron_arrow").toString()));
	public static final RegistryObject<EntityType<DiamondArrowEntity>> DIAMOND_ARROW_ENTITY = ENTITY_TYPES.register("diamond_arrow", () -> EntityType.Builder.<DiamondArrowEntity>create(DiamondArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "diamond_arrow").toString()));
	public static final RegistryObject<EntityType<NetheriteArrowEntity>> NETHERITE_ARROW_ENTITY = ENTITY_TYPES.register("netherite_arrow", () -> EntityType.Builder.<NetheriteArrowEntity>create(NetheriteArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "netherite_arrow").toString()));
	public static final RegistryObject<EntityType<SmokeBombArrowEntity>> SMOKE_BOMB_ARROW_ENTITY = ENTITY_TYPES.register("smoke_bomb_arrow", () -> EntityType.Builder.<SmokeBombArrowEntity>create(SmokeBombArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow").toString()));

	public static final RegistryObject<EntityType<WoodBulletEntity>> WOOD_BULLET_ENTITY = ENTITY_TYPES.register("wood_musket_ball", () -> EntityType.Builder.<WoodBulletEntity>create(WoodBulletEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_musket_ball").toString()));
	public static final RegistryObject<EntityType<StoneBulletEntity>> STONE_BULLET_ENTITY = ENTITY_TYPES.register("stone_musket_ball", () -> EntityType.Builder.<StoneBulletEntity>create(StoneBulletEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stone_musket_ball").toString()));
	public static final RegistryObject<EntityType<CopperBulletEntity>> COPPER_BULLET_ENTITY = ENTITY_TYPES.register("copper_musket_ball", () -> EntityType.Builder.<CopperBulletEntity>create(CopperBulletEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_musket_ball").toString()));
	public static final RegistryObject<EntityType<IronBulletEntity>> IRON_BULLET_ENTITY = ENTITY_TYPES.register("iron_musket_ball", () -> EntityType.Builder.<IronBulletEntity>create(IronBulletEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "iron_musket_ball").toString()));
	public static final RegistryObject<EntityType<GoldBulletEntity>> GOLD_BULLET_ENTITY = ENTITY_TYPES.register("gold_musket_ball", () -> EntityType.Builder.<GoldBulletEntity>create(GoldBulletEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_musket_ball").toString()));
	public static final RegistryObject<EntityType<DiamondBulletEntity>> DIAMOND_BULLET_ENTITY = ENTITY_TYPES.register("diamond_musket_ball", () -> EntityType.Builder.<DiamondBulletEntity>create(DiamondBulletEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "diamond_musket_ball").toString()));
	public static final RegistryObject<EntityType<NetheriteBulletEntity>> NETHERITE_BULLET_ENTITY = ENTITY_TYPES.register("netherite_musket_ball", () -> EntityType.Builder.<NetheriteBulletEntity>create(NetheriteBulletEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "netherite_musket_ball").toString()));

	public static final RegistryObject<EntityType<SmokeBombEntity>> SMOKE_BOMB_ENTITY = ENTITY_TYPES.register("smoke_bomb", () -> EntityType.Builder.<SmokeBombEntity>create(SmokeBombEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb").toString()));
	
	public static final RegistryObject<EntityType<MolotovEntity>> MOLOTOV_COCKTAIL_ENTITY = ENTITY_TYPES.register("molotov_cocktail", () -> EntityType.Builder.<MolotovEntity>create(MolotovEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "molotov_cocktail").toString()));
	
	// Sounds
	
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_EFFECT = SOUND_EVENTS.register("tesla_armor_effect", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_effect")));
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_POWER_DOWN = SOUND_EVENTS.register("tesla_armor_power_down", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_power_down")));
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_POWER_UP = SOUND_EVENTS.register("tesla_armor_power_up", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_power_up")));
	public static final RegistryObject<SoundEvent> FLINTLOCK_PISTOL_FIRE = SOUND_EVENTS.register("flintlock_pistol_fire", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flintlock_pistol_fire")));
	public static final RegistryObject<SoundEvent> BULLET_WHIZZ = SOUND_EVENTS.register("bullet_whizz", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "bullet_whizz")));
	public static final RegistryObject<SoundEvent> FLINTLOCK_PISTOL_MISFIRE = SOUND_EVENTS.register("flintlock_pistol_misfire", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flintlock_pistol_misfire")));
	public static final RegistryObject<SoundEvent> SMALL_PARTS_TABLE_USED = SOUND_EVENTS.register("small_parts_table_used", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts_table_used")));
	public static final RegistryObject<SoundEvent> SMOKE_BOMB_HISS = SOUND_EVENTS.register("smoke_bomb_hiss", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_hiss")));
	public static final RegistryObject<SoundEvent> GENERIC_WHOOSH = SOUND_EVENTS.register("generic_whoosh", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "generic_whoosh")));

	// Containers
	
	public static final RegistryObject<ContainerType<SmallPartsContainer>> SMALL_PARTS_TABLE_CONTAINER = CONTAINER_TYPES.register("small_parts_table", () -> IForgeContainerType.create((id, inv, data) -> {
		return new SmallPartsContainer(id, inv);
	}));
	
	// Recipes
	
	public static final RegistryObject<IRecipeSerializer<?>> SMALL_PARTS_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("small_parts", () -> new SmallPartsRecipe.Serializer());
	
	// Particles
	
	public static final RegistryObject<ParticleType<SmokeBombParticleData>> SMOKE_BOMB_PARTICLE_TYPE = PARTICLE_TYPES.register("smoke_bomb", () -> new SmokeBombParticleType());
}