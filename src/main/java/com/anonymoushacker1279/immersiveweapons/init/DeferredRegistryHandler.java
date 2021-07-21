package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.block.*;
import com.anonymoushacker1279.immersiveweapons.block.CorrugatedBlock.CorrugatedBlockNormal;
import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleType;
import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;
import com.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import com.anonymoushacker1279.immersiveweapons.entity.misc.ChairEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.DyingSoldierEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.HansEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.WanderingWarriorEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.FieldMedicEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.MinutemanEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.*;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.*;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.MortarShellEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity;
import com.anonymoushacker1279.immersiveweapons.item.*;
import com.anonymoushacker1279.immersiveweapons.item.BottleItems.AlcoholBottleItem;
import com.anonymoushacker1279.immersiveweapons.item.BottleItems.WineBottleItem;
import com.anonymoushacker1279.immersiveweapons.item.BulletItems.*;
import com.anonymoushacker1279.immersiveweapons.item.ArrowItems.*;
import com.anonymoushacker1279.immersiveweapons.item.MoltenItems.*;
import com.anonymoushacker1279.immersiveweapons.item.PikeItems.*;
import com.anonymoushacker1279.immersiveweapons.item.TeslaItems.*;
import com.anonymoushacker1279.immersiveweapons.item.VentusItems.*;
import com.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;
import com.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;
import com.anonymoushacker1279.immersiveweapons.potion.MorphineEffect;
import com.anonymoushacker1279.immersiveweapons.tileentity.*;
import com.anonymoushacker1279.immersiveweapons.util.*;
import com.anonymoushacker1279.immersiveweapons.world.gen.carver.TrenchWorldCarver;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
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
	// Global Loot Modifier Register
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER_SERIALIZER = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ImmersiveWeapons.MOD_ID);
	// Tile Entity Register
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ImmersiveWeapons.MOD_ID);
	// Biome Register
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ImmersiveWeapons.MOD_ID);
	// World Carver Register
	public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, ImmersiveWeapons.MOD_ID);
	// Effect Register
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ImmersiveWeapons.MOD_ID);

	/**
	 * Initialize deferred registers.
	 */
	public static void init() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(modEventBus);
		BLOCKS.register(modEventBus);
		ENTITY_TYPES.register(modEventBus);
		SOUND_EVENTS.register(modEventBus);
		CONTAINER_TYPES.register(modEventBus);
		RECIPE_SERIALIZER.register(modEventBus);
		PARTICLE_TYPES.register(modEventBus);
		GLOBAL_LOOT_MODIFIER_SERIALIZER.register(modEventBus);
		TILE_ENTITIES.register(modEventBus);
		BIOMES.register(modEventBus);
		WORLD_CARVERS.register(modEventBus);
		EFFECTS.register(modEventBus);
	}

	public static final ItemGroup ITEM_GROUP = new CreativeTabSorter("ImmersiveWeaponsTab");

	// Tools
	public static final RegistryObject<Item> MOLTEN_SWORD = ITEMS.register("molten_sword", () -> new MoltenSword(CustomItemMaterials.MOLTEN, 4, -2.1f, new Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant()));
	public static final RegistryObject<Item> MOLTEN_PICKAXE = ITEMS.register("molten_pickaxe", () -> new MoltenPickaxe(CustomItemMaterials.MOLTEN, 2, -2.3F, new Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant()));
	public static final RegistryObject<Item> MOLTEN_AXE = ITEMS.register("molten_axe", () -> new MoltenAxe(CustomItemMaterials.MOLTEN, 6, -3.0f, new Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant()));
	public static final RegistryObject<Item> MOLTEN_SHOVEL = ITEMS.register("molten_shovel", () -> new MoltenShovel(CustomItemMaterials.MOLTEN, -1, -3.0f, new Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant()));
	public static final RegistryObject<Item> MOLTEN_HOE = ITEMS.register("molten_hoe", () -> new MoltenHoe(CustomItemMaterials.MOLTEN, -3, 1.0f, new Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant()));
	public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(CustomItemMaterials.COPPER, 2, -2.4f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(CustomItemMaterials.COPPER, 1, -2.3F, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(CustomItemMaterials.COPPER, 3, -3.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(CustomItemMaterials.COPPER, -1, -2.7f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(CustomItemMaterials.COPPER, -2, 1.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> TESLA_SWORD = ITEMS.register("tesla_sword", () -> new TeslaSword(CustomItemMaterials.TESLA, 5, -2.1f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> TESLA_PICKAXE = ITEMS.register("tesla_pickaxe", () -> new TeslaPickaxe(CustomItemMaterials.TESLA, 4, -2.3f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> TESLA_AXE = ITEMS.register("tesla_axe", () -> new TeslaAxe(CustomItemMaterials.TESLA, 7, -3.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> TESLA_SHOVEL = ITEMS.register("tesla_shovel", () -> new TeslaShovel(CustomItemMaterials.TESLA, 0, -3.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> TESLA_HOE = ITEMS.register("tesla_hoe", () -> new TeslaHoe(CustomItemMaterials.TESLA, -3, 1.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COBALT_SWORD = ITEMS.register("cobalt_sword", () -> new SwordItem(CustomItemMaterials.COBALT, 2, -2.4f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe", () -> new PickaxeItem(CustomItemMaterials.COBALT, 1, -2.3F, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COBALT_AXE = ITEMS.register("cobalt_axe", () -> new AxeItem(CustomItemMaterials.COBALT, 3, -3.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COBALT_SHOVEL = ITEMS.register("cobalt_shovel", () -> new ShovelItem(CustomItemMaterials.COBALT, -1, -2.7f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COBALT_HOE = ITEMS.register("cobalt_hoe", () -> new HoeItem(CustomItemMaterials.COBALT, -3, 1.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> VENTUS_SWORD = ITEMS.register("ventus_sword", () -> new VentusSword(CustomItemMaterials.VENTUS, 4, -2.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> VENTUS_PICKAXE = ITEMS.register("ventus_pickaxe", () -> new VentusPickaxe(CustomItemMaterials.VENTUS, 3, -2.2f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> VENTUS_AXE = ITEMS.register("ventus_axe", () -> new VentusAxe(CustomItemMaterials.VENTUS, 6, -2.9f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> VENTUS_SHOVEL = ITEMS.register("ventus_shovel", () -> new VentusShovel(CustomItemMaterials.VENTUS, 0, -2.9f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> VENTUS_HOE = ITEMS.register("ventus_hoe", () -> new VentusHoe(CustomItemMaterials.VENTUS, -3, 1.0f, new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> VENTUS_STAFF = ITEMS.register("ventus_staff", () -> new VentusStaff(new Properties().durability(300).tab(ITEM_GROUP)));
	public static final RegistryObject<Item> WOOD_PIKE = ITEMS.register("wood_pike", () -> new WoodPikeItem((new Properties()).durability(59).tab(ITEM_GROUP), 4.0d, -2.6d));
	public static final RegistryObject<Item> STONE_PIKE = ITEMS.register("stone_pike", () -> new StonePikeItem((new Properties()).durability(131).tab(ITEM_GROUP), 5.0d, -2.6d));
	public static final RegistryObject<Item> GOLD_PIKE = ITEMS.register("gold_pike", () -> new GoldPikeItem((new Properties()).durability(32).tab(ITEM_GROUP), 4.0d, -2.6d));
	public static final RegistryObject<Item> COPPER_PIKE = ITEMS.register("copper_pike", () -> new CopperPikeItem((new Properties()).durability(180).tab(ITEM_GROUP), 6.0d, -2.6d));
	public static final RegistryObject<Item> IRON_PIKE = ITEMS.register("iron_pike", () -> new IronPikeItem((new Properties()).durability(250).tab(ITEM_GROUP), 6.0d, -2.6d));
	public static final RegistryObject<Item> DIAMOND_PIKE = ITEMS.register("diamond_pike", () -> new DiamondPikeItem((new Properties()).durability(1561).tab(ITEM_GROUP), 7.0d, -2.6d));
	public static final RegistryObject<Item> NETHERITE_PIKE = ITEMS.register("netherite_pike", () -> new NetheritePikeItem((new Properties()).durability(2031).tab(ITEM_GROUP).fireResistant(), 8.0d, -2.6d));
	public static final RegistryObject<Item> FLINTLOCK_PISTOL = ITEMS.register("flintlock_pistol", () -> new SimplePistolItem(new Properties().tab(ITEM_GROUP).durability(499)));
	public static final RegistryObject<Item> BLUNDERBUSS = ITEMS.register("blunderbuss", () -> new SimpleShotgunItem(new Properties().tab(ITEM_GROUP).durability(449)));
	public static final RegistryObject<Item> FLARE_GUN = ITEMS.register("flare_gun", () -> new FlareGunItem(new Properties().tab(ITEM_GROUP).durability(399)));

	// Items
	public static final RegistryObject<Item> STONE_SHARD = ITEMS.register("stone_shard", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> OBSIDIAN_SHARD = ITEMS.register("obsidian_shard", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> MOLTEN_SHARD = ITEMS.register("molten_shard", () -> new Item(new Properties().tab(ITEM_GROUP).fireResistant()));
	public static final RegistryObject<Item> VENTUS_SHARD = ITEMS.register("ventus_shard", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> WOOD_SHARD = ITEMS.register("wood_shard", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> MOLTEN_PLATE = ITEMS.register("molten_plate", () -> new Item(new Properties().tab(ITEM_GROUP).fireResistant()));
	public static final RegistryObject<Item> TESLA_PLATE = ITEMS.register("tesla_plate", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> WOOD_TOOL_ROD = ITEMS.register("wood_tool_rod", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> MOLTEN_INGOT = ITEMS.register("molten_ingot", () -> new Item(new Properties().tab(ITEM_GROUP).fireResistant()));
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> CONDUCTIVE_ALLOY = ITEMS.register("conductive_alloy", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> ELECTRIC_INGOT = ITEMS.register("electric_ingot", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> TESLA_INGOT = ITEMS.register("tesla_ingot", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> VENTUS_STAFF_CORE = ITEMS.register("ventus_staff_core", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> WOOD_PIKE_HEAD = ITEMS.register("wood_pike_head", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> STONE_PIKE_HEAD = ITEMS.register("stone_pike_head", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> GOLD_PIKE_HEAD = ITEMS.register("gold_pike_head", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> COPPER_PIKE_HEAD = ITEMS.register("copper_pike_head", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> IRON_PIKE_HEAD = ITEMS.register("iron_pike_head", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> DIAMOND_PIKE_HEAD = ITEMS.register("diamond_pike_head", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> WOOD_ARROW = ITEMS.register("wood_arrow", () -> new WoodArrowItem(new Properties().tab(ITEM_GROUP), 1.65d));
	public static final RegistryObject<Item> STONE_ARROW = ITEMS.register("stone_arrow", () -> new StoneArrowItem(new Properties().tab(ITEM_GROUP), 1.85d));
	public static final RegistryObject<Item> GOLD_ARROW = ITEMS.register("gold_arrow", () -> new GoldArrowItem(new Properties().tab(ITEM_GROUP), 2.10d));
	public static final RegistryObject<Item> COPPER_ARROW = ITEMS.register("copper_arrow", () -> new CopperArrowItem(new Properties().tab(ITEM_GROUP), 2.15d));
	public static final RegistryObject<Item> IRON_ARROW = ITEMS.register("iron_arrow", () -> new IronArrowItem(new Properties().tab(ITEM_GROUP), 2.35d));
	public static final RegistryObject<Item> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new DiamondArrowItem(new Properties().tab(ITEM_GROUP), 3.00d));
	public static final RegistryObject<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new NetheriteArrowItem(new Properties().tab(ITEM_GROUP).fireResistant(), 5.75d));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW = ITEMS.register("smoke_bomb_arrow", () -> new SmokeBombArrowItem(new Properties().tab(ITEM_GROUP), 2.00d, 0));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_RED = ITEMS.register("smoke_bomb_arrow_red", () -> new SmokeBombArrowItem(new Properties().tab(ITEM_GROUP), 2.00d, 1));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_GREEN = ITEMS.register("smoke_bomb_arrow_green", () -> new SmokeBombArrowItem(new Properties().tab(ITEM_GROUP), 2.00d, 2));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_BLUE = ITEMS.register("smoke_bomb_arrow_blue", () -> new SmokeBombArrowItem(new Properties().tab(ITEM_GROUP), 2.00d, 3));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_PURPLE = ITEMS.register("smoke_bomb_arrow_purple", () -> new SmokeBombArrowItem(new Properties().tab(ITEM_GROUP), 2.00d, 4));
	public static final RegistryObject<Item> SMOKE_BOMB_ARROW_YELLOW = ITEMS.register("smoke_bomb_arrow_yellow", () -> new SmokeBombArrowItem(new Properties().tab(ITEM_GROUP), 2.00d, 5));
	public static final RegistryObject<Item> WOOD_MUSKET_BALL = ITEMS.register("wood_musket_ball", () -> new WoodBulletItem(new Properties().tab(ITEM_GROUP), 2.0d));
	public static final RegistryObject<Item> STONE_MUSKET_BALL = ITEMS.register("stone_musket_ball", () -> new StoneBulletItem(new Properties().tab(ITEM_GROUP), 2.20d));
	public static final RegistryObject<Item> GOLD_MUSKET_BALL = ITEMS.register("gold_musket_ball", () -> new GoldBulletItem(new Properties().tab(ITEM_GROUP), 2.30d));
	public static final RegistryObject<Item> COPPER_MUSKET_BALL = ITEMS.register("copper_musket_ball", () -> new CopperBulletItem(new Properties().tab(ITEM_GROUP), 2.40d));
	public static final RegistryObject<Item> IRON_MUSKET_BALL = ITEMS.register("iron_musket_ball", () -> new IronBulletItem(new Properties().tab(ITEM_GROUP), 2.65d));
	public static final RegistryObject<Item> DIAMOND_MUSKET_BALL = ITEMS.register("diamond_musket_ball", () -> new DiamondBulletItem(new Properties().tab(ITEM_GROUP), 3.35d));
	public static final RegistryObject<Item> NETHERITE_MUSKET_BALL = ITEMS.register("netherite_musket_ball", () -> new NetheriteBulletItem(new Properties().tab(ITEM_GROUP), 6.50d));
	public static final RegistryObject<Item> FLARE = ITEMS.register("flare", () -> new FlareItem(new Properties().tab(ITEM_GROUP), 0.1d));
	public static final RegistryObject<Item> MORTAR_SHELL = ITEMS.register("mortar_shell", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> BLANK_BLUEPRINT = ITEMS.register("blank_blueprint", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> SMALL_PARTS_BLUEPRINT = ITEMS.register("small_parts_blueprint", () -> new BlueprintItem(new Properties().tab(ITEM_GROUP).stacksTo(1)));
	public static final RegistryObject<Item> SMALL_PARTS_METAL_THROWABLE_BOMB_BLUEPRINT = ITEMS.register("small_parts_metal_throwable_bomb_blueprint", () -> new BlueprintItem(new Properties().tab(ITEM_GROUP).stacksTo(1)));
	public static final RegistryObject<Item> SMALL_PARTS_METAL_TOOL_BLUEPRINT = ITEMS.register("small_parts_metal_tool_blueprint", () -> new BlueprintItem(new Properties().tab(ITEM_GROUP).stacksTo(1)));
	public static final RegistryObject<Item> SMALL_PARTS_IRON = ITEMS.register("small_parts_iron", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> SMALL_PARTS_METAL_THROWABLE_BOMB = ITEMS.register("small_parts_metal_throwable_bomb", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> SMALL_PARTS_METAL_TOOL = ITEMS.register("small_parts_metal_tool", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> SMOKE_BOMB = ITEMS.register("smoke_bomb", () -> new SmokeBombItem(new Properties().tab(ITEM_GROUP).stacksTo(16), 0));
	public static final RegistryObject<Item> SMOKE_BOMB_RED = ITEMS.register("smoke_bomb_red", () -> new SmokeBombItem(new Properties().tab(ITEM_GROUP).stacksTo(16), 1));
	public static final RegistryObject<Item> SMOKE_BOMB_GREEN = ITEMS.register("smoke_bomb_green", () -> new SmokeBombItem(new Properties().tab(ITEM_GROUP).stacksTo(16), 2));
	public static final RegistryObject<Item> SMOKE_BOMB_BLUE = ITEMS.register("smoke_bomb_blue", () -> new SmokeBombItem(new Properties().tab(ITEM_GROUP).stacksTo(16), 3));
	public static final RegistryObject<Item> SMOKE_BOMB_PURPLE = ITEMS.register("smoke_bomb_purple", () -> new SmokeBombItem(new Properties().tab(ITEM_GROUP).stacksTo(16), 4));
	public static final RegistryObject<Item> SMOKE_BOMB_YELLOW = ITEMS.register("smoke_bomb_yellow", () -> new SmokeBombItem(new Properties().tab(ITEM_GROUP).stacksTo(16), 5));
	public static final RegistryObject<Item> MOLOTOV_COCKTAIL = ITEMS.register("molotov_cocktail", () -> new MolotovItem(new Properties().tab(ITEM_GROUP).stacksTo(16)));
	public static final RegistryObject<Item> SMOKE_POWDER = ITEMS.register("smoke_powder", () -> new Item(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle", () -> new BasicContainerItem(new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> BOTTLE_OF_ALCOHOL = ITEMS.register("bottle_of_alcohol", () -> new AlcoholBottleItem(new Properties().tab(ITEM_GROUP).stacksTo(16)));
	public static final RegistryObject<Item> BOTTLE_OF_WINE = ITEMS.register("bottle_of_wine", () -> new WineBottleItem(new Properties().tab(ITEM_GROUP).stacksTo(16)));
	public static final RegistryObject<Item> PLIERS = ITEMS.register("pliers", () -> new Item(new Properties().tab(ITEM_GROUP).stacksTo(1)));
	public static final RegistryObject<Item> CHOCOLATE_BAR = ITEMS.register("chocolate_bar", () -> new Item(new Properties().tab(ITEM_GROUP).food(FoodItems.CHOCOLATE_BAR)));
	public static final RegistryObject<Item> EXPLOSIVE_CHOCOLATE_BAR = ITEMS.register("explosive_chocolate_bar", () -> new ExplosiveChocolateBar(new Properties().tab(ITEM_GROUP).food(FoodItems.CHOCOLATE_BAR)));
	public static final RegistryObject<Item> BANDAGE = ITEMS.register("bandage", () -> new BandageItem(new Properties().tab(ITEM_GROUP).stacksTo(16)));
	public static final RegistryObject<Item> MRE = ITEMS.register("mre", () -> new Item(new Properties().tab(ITEM_GROUP).food(FoodItems.MRE)));
	public static final RegistryObject<Item> PAINKILLERS = ITEMS.register("painkillers", () -> new PainkillerItem(new Properties().tab(ITEM_GROUP).stacksTo(24)));
	public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe", () -> new Item(new Properties().tab(ITEM_GROUP).stacksTo(16)));
	public static final RegistryObject<Item> MORPHINE = ITEMS.register("morphine", () -> new MorphineItem(new Properties().tab(ITEM_GROUP).stacksTo(16)));
	public static final RegistryObject<Item> USED_SYRINGE = ITEMS.register("used_syringe", () -> new UsedSyringeItem(new Properties().tab(ITEM_GROUP).stacksTo(16)));
	public static final RegistryObject<Item> FIRST_AID_KIT = ITEMS.register("first_aid_kit", () -> new FirstAidKitItem(new Properties().tab(ITEM_GROUP).stacksTo(8)));
	public static final RegistryObject<Item> CLOTH_SCRAP = ITEMS.register("cloth_scrap", () -> new Item(new Properties().tab(ITEM_GROUP)));

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
	public static final RegistryObject<Item> COBALT_HELMET = ITEMS.register("cobalt_helmet", () -> new CobaltArmorItem(CustomArmorMaterials.COBALT, EquipmentSlotType.HEAD, 1));
	public static final RegistryObject<Item> COBALT_CHESTPLATE = ITEMS.register("cobalt_chestplate", () -> new CobaltArmorItem(CustomArmorMaterials.COBALT, EquipmentSlotType.CHEST, 1));
	public static final RegistryObject<Item> COBALT_LEGGINGS = ITEMS.register("cobalt_leggings", () -> new CobaltArmorItem(CustomArmorMaterials.COBALT, EquipmentSlotType.LEGS, 2));
	public static final RegistryObject<Item> COBALT_BOOTS = ITEMS.register("cobalt_boots", () -> new CobaltArmorItem(CustomArmorMaterials.COBALT, EquipmentSlotType.FEET, 1));
	public static final RegistryObject<Item> VENTUS_HELMET = ITEMS.register("ventus_helmet", () -> new VentusArmorItem(CustomArmorMaterials.VENTUS, EquipmentSlotType.HEAD, 1));
	public static final RegistryObject<Item> VENTUS_CHESTPLATE = ITEMS.register("ventus_chestplate", () -> new VentusArmorItem(CustomArmorMaterials.VENTUS, EquipmentSlotType.CHEST, 1));
	public static final RegistryObject<Item> VENTUS_LEGGINGS = ITEMS.register("ventus_leggings", () -> new VentusArmorItem(CustomArmorMaterials.VENTUS, EquipmentSlotType.LEGS, 2));
	public static final RegistryObject<Item> VENTUS_BOOTS = ITEMS.register("ventus_boots", () -> new VentusArmorItem(CustomArmorMaterials.VENTUS, EquipmentSlotType.FEET, 1));

	// Spawn eggs
	public static final RegistryObject<Item> DYING_SOLDIER_SPAWN_EGG = ITEMS.register("dying_soldier_spawn_egg", () -> new CustomSpawnEggItem(DeferredRegistryHandler.DYING_SOLDIER_ENTITY, 0x7a6851, 0x783d22, (new Item.Properties()).tab(ITEM_GROUP)));
	public static final RegistryObject<Item> MINUTEMAN_SPAWN_EGG = ITEMS.register("minuteman_spawn_egg", () -> new CustomSpawnEggItem(DeferredRegistryHandler.MINUTEMAN_ENTITY, 0x494522, 0x204b2a, (new Item.Properties()).tab(ITEM_GROUP)));
	public static final RegistryObject<Item> FIELD_MEDIC_SPAWN_EGG = ITEMS.register("field_medic_spawn_egg", () -> new CustomSpawnEggItem(DeferredRegistryHandler.FIELD_MEDIC_ENTITY, 0xde5451, 0xebe4d2, (new Item.Properties()).tab(ITEM_GROUP)));
	public static final RegistryObject<Item> WANDERING_WARRIOR_SPAWN_EGG = ITEMS.register("wandering_warrior_spawn_egg", () -> new CustomSpawnEggItem(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY, 0x614226, 0x2e6278, (new Item.Properties()).tab(ITEM_GROUP)));
	public static final RegistryObject<Item> HANS_SPAWN_EGG = ITEMS.register("hans_spawn_egg", () -> new CustomSpawnEggItem(DeferredRegistryHandler.HANS_ENTITY, 0xd0a873, 0xafafaf, (new Item.Properties().tab(ITEM_GROUP))));

	// Blocks
	public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).strength(2.5f).sound(SoundType.STONE).requiresCorrectToolForDrops().harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> MOLTEN_ORE = BLOCKS.register("molten_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).strength(6.0f, 8.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> ELECTRIC_ORE = BLOCKS.register("electric_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).strength(6.0f, 8.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> MOLTEN_BLOCK = BLOCKS.register("molten_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL).strength(45.0f, 1100.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> TESLA_BLOCK = BLOCKS.register("tesla_block", () -> new BasicOrientableBlock(AbstractBlock.Properties.of(Material.METAL).strength(25.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestLevel(3).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> SMALL_PARTS_TABLE = BLOCKS.register("small_parts_table", () -> new SmallPartsTable(AbstractBlock.Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.WOOD).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> BARREL_TAP = BLOCKS.register("barrel_tap", () -> new BarrelTapBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0f).sound(SoundType.METAL).harvestLevel(0)));
	public static final RegistryObject<Block> BULLETPROOF_GLASS = BLOCKS.register("bulletproof_glass", () -> new GlassBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> WHITE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("white_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.WHITE, AbstractBlock.Properties.of(Material.GLASS, DyeColor.WHITE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> LIGHT_GRAY_STAINED_BULLETPROOF_GLASS = BLOCKS.register("light_gray_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.of(Material.GLASS, DyeColor.LIGHT_GRAY).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> GRAY_STAINED_BULLETPROOF_GLASS = BLOCKS.register("gray_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.GRAY, AbstractBlock.Properties.of(Material.GLASS, DyeColor.GRAY).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> BLACK_STAINED_BULLETPROOF_GLASS = BLOCKS.register("black_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.BLACK, AbstractBlock.Properties.of(Material.GLASS, DyeColor.BLACK).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> ORANGE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("orange_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.ORANGE, AbstractBlock.Properties.of(Material.GLASS, DyeColor.ORANGE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> MAGENTA_STAINED_BULLETPROOF_GLASS = BLOCKS.register("magenta_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.MAGENTA, AbstractBlock.Properties.of(Material.GLASS, DyeColor.MAGENTA).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> LIGHT_BLUE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("light_blue_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.LIGHT_BLUE, AbstractBlock.Properties.of(Material.GLASS, DyeColor.LIGHT_BLUE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> YELLOW_STAINED_BULLETPROOF_GLASS = BLOCKS.register("yellow_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.YELLOW, AbstractBlock.Properties.of(Material.GLASS, DyeColor.YELLOW).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> LIME_STAINED_BULLETPROOF_GLASS = BLOCKS.register("lime_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.LIME, AbstractBlock.Properties.of(Material.GLASS, DyeColor.LIME).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> PINK_STAINED_BULLETPROOF_GLASS = BLOCKS.register("pink_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.PINK, AbstractBlock.Properties.of(Material.GLASS, DyeColor.PINK).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> CYAN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("cyan_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.CYAN, AbstractBlock.Properties.of(Material.GLASS, DyeColor.CYAN).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> PURPLE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("purple_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.PURPLE, AbstractBlock.Properties.of(Material.GLASS, DyeColor.PURPLE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> BLUE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("blue_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.BLUE, AbstractBlock.Properties.of(Material.GLASS, DyeColor.BLUE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> BROWN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("brown_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.BROWN, AbstractBlock.Properties.of(Material.GLASS, DyeColor.BROWN).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> GREEN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("green_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.GREEN, AbstractBlock.Properties.of(Material.GLASS, DyeColor.GREEN).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> RED_STAINED_BULLETPROOF_GLASS = BLOCKS.register("red_stained_bulletproof_glass", () -> GeneralUtilities.createStainedGlassFromColor(DyeColor.RED, AbstractBlock.Properties.of(Material.GLASS, DyeColor.RED).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<Block> PUNJI_STICKS = BLOCKS.register("punji_sticks", () -> new PunjiSticksBlock(AbstractBlock.Properties.of(Material.BAMBOO).strength(5.0f, 1.0f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.SHOVEL)));
	public static final RegistryObject<Block> PITFALL = BLOCKS.register("pitfall", () -> new PitfallBlock(AbstractBlock.Properties.of(Material.GRASS).strength(0.2f, 1.0f).sound(SoundType.GRAVEL).randomTicks()));
	public static final RegistryObject<Block> BEAR_TRAP = BLOCKS.register("bear_trap", () -> new BearTrapBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(2.0F).sound(SoundType.METAL).harvestLevel(2)));
	public static final RegistryObject<Block> BARBED_WIRE = BLOCKS.register("barbed_wire", () -> new BarbedWireBlock(AbstractBlock.Properties.of(Material.METAL).strength(2.0f).sound(SoundType.CHAIN).harvestLevel(2).noOcclusion().noCollission()));
	public static final RegistryObject<Block> LANDMINE = BLOCKS.register("landmine", () -> new LandmineBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).harvestLevel(0)));
	public static final RegistryObject<Block> SPIKE_TRAP = BLOCKS.register("spike_trap", () -> new SpikeTrapBlock(AbstractBlock.Properties.of(Material.METAL).strength(2.0F).sound(SoundType.METAL).harvestLevel(2).noOcclusion().noCollission()));
	public static final RegistryObject<Block> CORRUGATED_IRON_PANEL = BLOCKS.register("corrugated_iron_panel", () -> new CorrugatedBlockNormal(AbstractBlock.Properties.of(Material.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> CORRUGATED_IRON_PANEL_BARS = BLOCKS.register("corrugated_iron_panel_bars", () -> new CorrugatedBlockNormal(AbstractBlock.Properties.of(Material.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> CORRUGATED_IRON_PANEL_FLAT = BLOCKS.register("corrugated_iron_panel_flat", () -> new CorrugatedBlock.CorrugatedBlockFlat(AbstractBlock.Properties.of(Material.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> CORRUGATED_IRON_PANEL_FLAT_BARS = BLOCKS.register("corrugated_iron_panel_flat_bars", () -> new CorrugatedBlock.CorrugatedBlockFlat(AbstractBlock.Properties.of(Material.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> SPOTLIGHT = BLOCKS.register("spotlight", () -> new SpotlightBlock(AbstractBlock.Properties.of(Material.METAL).strength(2.0f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).noOcclusion().lightLevel((state) -> 0)));
	public static final RegistryObject<Block> WALL_SHELF = BLOCKS.register("wall_shelf", () -> new ShelfBlock(AbstractBlock.Properties.of(Material.WOOD).strength(1.0f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final RegistryObject<Block> PANIC_ALARM = BLOCKS.register("panic_alarm", () -> new PanicAlarmBlock(AbstractBlock.Properties.of(Material.WOOD).strength(1.0f).sound(SoundType.WOOD).noOcclusion().randomTicks()));
	public static final RegistryObject<Block> WOODEN_TABLE = BLOCKS.register("wooden_table", () -> new WoodenTableBlock(AbstractBlock.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> BARBED_WIRE_FENCE = BLOCKS.register("barbed_wire_fence", () -> new BarbedWireFenceBlock(AbstractBlock.Properties.of(Material.METAL).strength(7.0f, 8.0f).sound(SoundType.METAL).harvestLevel(2).harvestTool(ToolType.PICKAXE).noOcclusion()));
	public static final RegistryObject<Block> WOODEN_SPIKES = BLOCKS.register("wooden_spikes", () -> new WoodenSpikesBlock(AbstractBlock.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).harvestLevel(1).harvestTool(ToolType.AXE).noOcclusion()));
	public static final RegistryObject<Block> BIOHAZARD_BOX = BLOCKS.register("biohazard_box", () -> new BiohazardBoxBlock(AbstractBlock.Properties.of(Material.DECORATION).strength(0.5f).sound(SoundType.LANTERN).noOcclusion()));
	public static final RegistryObject<Block> MINUTEMAN_STATUE = BLOCKS.register("minuteman_statue", () -> new MinutemanStatueBlock(AbstractBlock.Properties.of(Material.STONE).strength(5.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Block> MEDIC_STATUE = BLOCKS.register("medic_statue", () -> new MedicStatueBlock(AbstractBlock.Properties.of(Material.STONE).strength(5.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Block> TESLA_SYNTHESIZER = BLOCKS.register("tesla_synthesizer", () -> new TeslaSynthesizerBlock(AbstractBlock.Properties.of(Material.METAL).strength(10.0f).sound(SoundType.METAL).harvestLevel(3).harvestTool(ToolType.PICKAXE).noOcclusion()));
	public static final RegistryObject<Block> COBALT_ORE = BLOCKS.register("cobalt_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).strength(4.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().harvestLevel(2).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> CLOUD = BLOCKS.register("cloud", () -> new BreakableBlock(AbstractBlock.Properties.of(Material.STRUCTURAL_AIR).strength(0.7f).sound(SoundType.SNOW).noOcclusion()));
	public static final RegistryObject<Block> CLOUD_MARBLE = BLOCKS.register("cloud_marble", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(1.5f, 6.0f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> CLOUD_MARBLE_BRICKS = BLOCKS.register("cloud_marble_bricks", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(1.5f, 6.0f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> CLOUD_MARBLE_PILLAR = BLOCKS.register("cloud_marble_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.5f, 6.0f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> CLOUD_MARBLE_BRICK_STAIRS = BLOCKS.register("cloud_marble_brick_stairs", () -> new StairsBlock(() -> CLOUD_MARBLE_BRICKS.get().defaultBlockState(), AbstractBlock.Properties.copy(CLOUD_MARBLE_BRICKS.get())));
	public static final RegistryObject<Block> CLOUD_MARBLE_BRICK_SLAB = BLOCKS.register("cloud_marble_brick_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.5f, 6.0f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> VENTUS_ORE = BLOCKS.register("ventus_ore", () -> new Block(AbstractBlock.Properties.of(Material.STONE).strength(3.5f, 6.0f).sound(SoundType.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE)));
	public static final RegistryObject<Block> COBALT_BLOCK = BLOCKS.register("cobalt_block", () -> new Block(AbstractBlock.Properties.of(Material.METAL).strength(6.0f)));
	public static final RegistryObject<Block> CAMP_CHAIR = BLOCKS.register("camp_chair", () -> new CampChairBlock(AbstractBlock.Properties.of(Material.CLOTH_DECORATION).strength(1.0f).sound(SoundType.WOOL).noOcclusion()));
	public static final RegistryObject<Block> SANDBAG = BLOCKS.register("sandbag", () -> new SandbagBlock(AbstractBlock.Properties.of(Material.SAND).strength(4.0f, 5.0f).sound(SoundType.SAND).harvestTool(ToolType.SHOVEL).noOcclusion()));
	public static final RegistryObject<Block> MORTAR = BLOCKS.register("mortar", () -> new MortarBlock(AbstractBlock.Properties.of(Material.STONE).strength(5.0f).sound(SoundType.STONE).noOcclusion().harvestTool(ToolType.PICKAXE)));

	// Block Items
	public static final RegistryObject<BlockItem> MOLTEN_ORE_ITEM = ITEMS.register("molten_ore", () -> new BlockItem(MOLTEN_ORE.get(), new Properties().tab(ITEM_GROUP).fireResistant()));
	public static final RegistryObject<BlockItem> ELECTRIC_ORE_ITEM = ITEMS.register("electric_ore", () -> new BlockItem(ELECTRIC_ORE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> COBALT_ORE_ITEM = ITEMS.register("cobalt_ore", () -> new BlockItem(COBALT_ORE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> VENTUS_ORE_ITEM = ITEMS.register("ventus_ore", () -> new BlockItem(VENTUS_ORE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> MOLTEN_BLOCK_ITEM = ITEMS.register("molten_block", () -> new BlockItem(MOLTEN_BLOCK.get(), new Properties().tab(ITEM_GROUP).fireResistant()));
	public static final RegistryObject<BlockItem> TESLA_BLOCK_ITEM = ITEMS.register("tesla_block", () -> new BlockItem(TESLA_BLOCK.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> COBALT_BLOCK_ITEM = ITEMS.register("cobalt_block", () -> new BlockItem(COBALT_BLOCK.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> SMALL_PARTS_TABLE_ITEM = ITEMS.register("small_parts_table", () -> new BlockItem(SMALL_PARTS_TABLE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BARREL_TAP_ITEM = ITEMS.register("barrel_tap", () -> new BlockItem(BARREL_TAP.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BULLETPROOF_GLASS_ITEM = ITEMS.register("bulletproof_glass", () -> new BlockItem(BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> WHITE_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("white_stained_bulletproof_glass", () -> new BlockItem(WHITE_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("light_gray_stained_bulletproof_glass", () -> new BlockItem(LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> GRAY_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("gray_stained_bulletproof_glass", () -> new BlockItem(GRAY_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BLACK_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("black_stained_bulletproof_glass", () -> new BlockItem(BLACK_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> ORANGE_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("orange_stained_bulletproof_glass", () -> new BlockItem(ORANGE_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> MAGENTA_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("magenta_stained_bulletproof_glass", () -> new BlockItem(MAGENTA_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("light_blue_stained_bulletproof_glass", () -> new BlockItem(LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> YELLOW_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("yellow_stained_bulletproof_glass", () -> new BlockItem(YELLOW_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> LIME_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("lime_stained_bulletproof_glass", () -> new BlockItem(LIME_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> PINK_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("pink_stained_bulletproof_glass", () -> new BlockItem(PINK_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CYAN_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("cyan_stained_bulletproof_glass", () -> new BlockItem(CYAN_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> PURPLE_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("purple_stained_bulletproof_glass", () -> new BlockItem(PURPLE_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BLUE_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("blue_stained_bulletproof_glass", () -> new BlockItem(BLUE_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BROWN_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("brown_stained_bulletproof_glass", () -> new BlockItem(BROWN_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> GREEN_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("green_stained_bulletproof_glass", () -> new BlockItem(GREEN_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> RED_STAINED_BULLETPROOF_GLASS_ITEM = ITEMS.register("red_stained_bulletproof_glass", () -> new BlockItem(RED_STAINED_BULLETPROOF_GLASS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> PUNJI_STICKS_ITEM = ITEMS.register("punji_sticks", () -> new BlockItem(PUNJI_STICKS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> PITFALL_ITEM = ITEMS.register("pitfall", () -> new BlockItem(PITFALL.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BEAR_TRAP_ITEM = ITEMS.register("bear_trap", () -> new BlockItem(BEAR_TRAP.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BARBED_WIRE_ITEM = ITEMS.register("barbed_wire", () -> new BlockItem(BARBED_WIRE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> LANDMINE_ITEM = ITEMS.register("landmine", () -> new BlockItem(LANDMINE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> SPIKE_TRAP_ITEM = ITEMS.register("spike_trap", () -> new BlockItem(SPIKE_TRAP.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> SANDBAG_ITEM = ITEMS.register("sandbag", () -> new BlockItem(SANDBAG.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CORRUGATED_IRON_PANEL_ITEM = ITEMS.register("corrugated_iron_panel", () -> new BlockItem(CORRUGATED_IRON_PANEL.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CORRUGATED_IRON_PANEL_BARS_ITEM = ITEMS.register("corrugated_iron_panel_bars", () -> new BlockItem(CORRUGATED_IRON_PANEL_BARS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CORRUGATED_IRON_PANEL_FLAT_ITEM = ITEMS.register("corrugated_iron_panel_flat", () -> new BlockItem(CORRUGATED_IRON_PANEL_FLAT.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CORRUGATED_IRON_PANEL_FLAT_BARS_ITEM = ITEMS.register("corrugated_iron_panel_flat_bars", () -> new BlockItem(CORRUGATED_IRON_PANEL_FLAT_BARS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> SPOTLIGHT_ITEM = ITEMS.register("spotlight", () -> new BlockItem(SPOTLIGHT.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> MORTAR_ITEM = ITEMS.register("mortar", () -> new BlockItem(MORTAR.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> WALL_SHELF_ITEM = ITEMS.register("wall_shelf", () -> new BlockItem(WALL_SHELF.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> PANIC_ALARM_ITEM = ITEMS.register("panic_alarm", () -> new BlockItem(PANIC_ALARM.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> WOODEN_TABLE_ITEM = ITEMS.register("wooden_table", () -> new BlockItem(WOODEN_TABLE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CAMP_CHAIR_ITEM = ITEMS.register("camp_chair", () -> new BlockItem(CAMP_CHAIR.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BARBED_WIRE_FENCE_ITEM = ITEMS.register("barbed_wire_fence", () -> new BlockItem(BARBED_WIRE_FENCE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> WOODEN_SPIKES_ITEM = ITEMS.register("wooden_spikes", () -> new BlockItem(WOODEN_SPIKES.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> BIOHAZARD_BOX_ITEM = ITEMS.register("biohazard_box", () -> new BlockItem(BIOHAZARD_BOX.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> MINUTEMAN_STATUE_ITEM = ITEMS.register("minuteman_statue", () -> new BlockItem(MINUTEMAN_STATUE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> MEDIC_STATUE_ITEM = ITEMS.register("medic_statue", () -> new BlockItem(MEDIC_STATUE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> TESLA_SYNTHESIZER_ITEM = ITEMS.register("tesla_synthesizer", () -> new BlockItem(TESLA_SYNTHESIZER.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CLOUD_ITEM = ITEMS.register("cloud", () -> new BlockItem(CLOUD.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_ITEM = ITEMS.register("cloud_marble", () -> new BlockItem(CLOUD_MARBLE.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_BRICKS_ITEM = ITEMS.register("cloud_marble_bricks", () -> new BlockItem(CLOUD_MARBLE_BRICKS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_PILLAR_ITEM = ITEMS.register("cloud_marble_pillar", () -> new BlockItem(CLOUD_MARBLE_PILLAR.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_BRICK_STAIRS_ITEM = ITEMS.register("cloud_marble_brick_stairs", () -> new BlockItem(CLOUD_MARBLE_BRICK_STAIRS.get(), new Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_BRICK_SLAB_ITEM = ITEMS.register("cloud_marble_brick_slab", () -> new BlockItem(CLOUD_MARBLE_BRICK_SLAB.get(), new Properties().tab(ITEM_GROUP)));

	// Entities
	public static final RegistryObject<EntityType<WoodArrowEntity>> WOOD_ARROW_ENTITY = ENTITY_TYPES.register("wood_arrow", () -> EntityType.Builder.<WoodArrowEntity> of(WoodArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_arrow").toString()));
	public static final RegistryObject<EntityType<StoneArrowEntity>> STONE_ARROW_ENTITY = ENTITY_TYPES.register("stone_arrow", () -> EntityType.Builder.<StoneArrowEntity> of(StoneArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stone_arrow").toString()));
	public static final RegistryObject<EntityType<GoldArrowEntity>> GOLD_ARROW_ENTITY = ENTITY_TYPES.register("gold_arrow", () -> EntityType.Builder.<GoldArrowEntity> of(GoldArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_arrow").toString()));
	public static final RegistryObject<EntityType<CopperArrowEntity>> COPPER_ARROW_ENTITY = ENTITY_TYPES.register("copper_arrow", () -> EntityType.Builder.<CopperArrowEntity> of(CopperArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_arrow").toString()));
	public static final RegistryObject<EntityType<IronArrowEntity>> IRON_ARROW_ENTITY = ENTITY_TYPES.register("iron_arrow", () -> EntityType.Builder.<IronArrowEntity> of(IronArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "iron_arrow").toString()));
	public static final RegistryObject<EntityType<DiamondArrowEntity>> DIAMOND_ARROW_ENTITY = ENTITY_TYPES.register("diamond_arrow", () -> EntityType.Builder.<DiamondArrowEntity> of(DiamondArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "diamond_arrow").toString()));
	public static final RegistryObject<EntityType<NetheriteArrowEntity>> NETHERITE_ARROW_ENTITY = ENTITY_TYPES.register("netherite_arrow", () -> EntityType.Builder.<NetheriteArrowEntity> of(NetheriteArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "netherite_arrow").toString()));
	public static final RegistryObject<EntityType<SmokeBombArrowEntity>> SMOKE_BOMB_ARROW_ENTITY = ENTITY_TYPES.register("smoke_bomb_arrow", () -> EntityType.Builder.<SmokeBombArrowEntity> of(SmokeBombArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow").toString()));
	public static final RegistryObject<EntityType<WoodBulletEntity>> WOOD_BULLET_ENTITY = ENTITY_TYPES.register("wood_musket_ball", () -> EntityType.Builder.<WoodBulletEntity> of((type, world) -> new WoodBulletEntity(type, world, 0), EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_musket_ball").toString()));
	public static final RegistryObject<EntityType<StoneBulletEntity>> STONE_BULLET_ENTITY = ENTITY_TYPES.register("stone_musket_ball", () -> EntityType.Builder.<StoneBulletEntity> of((type, world) -> new StoneBulletEntity(type, world, 0), EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stone_musket_ball").toString()));
	public static final RegistryObject<EntityType<CopperBulletEntity>> COPPER_BULLET_ENTITY = ENTITY_TYPES.register("copper_musket_ball", () -> EntityType.Builder.<CopperBulletEntity> of((type, world) -> new CopperBulletEntity(type, world, 0), EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_musket_ball").toString()));
	public static final RegistryObject<EntityType<IronBulletEntity>> IRON_BULLET_ENTITY = ENTITY_TYPES.register("iron_musket_ball", () -> EntityType.Builder.<IronBulletEntity> of((type, world) -> new IronBulletEntity(type, world, 0), EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "iron_musket_ball").toString()));
	public static final RegistryObject<EntityType<GoldBulletEntity>> GOLD_BULLET_ENTITY = ENTITY_TYPES.register("gold_musket_ball", () -> EntityType.Builder.<GoldBulletEntity> of((type, world) -> new GoldBulletEntity(type, world, 0), EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_musket_ball").toString()));
	public static final RegistryObject<EntityType<DiamondBulletEntity>> DIAMOND_BULLET_ENTITY = ENTITY_TYPES.register("diamond_musket_ball", () -> EntityType.Builder.<DiamondBulletEntity> of((type, world) -> new DiamondBulletEntity(type, world, 0), EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "diamond_musket_ball").toString()));
	public static final RegistryObject<EntityType<NetheriteBulletEntity>> NETHERITE_BULLET_ENTITY = ENTITY_TYPES.register("netherite_musket_ball", () -> EntityType.Builder.<NetheriteBulletEntity> of((type, world) -> new NetheriteBulletEntity(type, world, 0), EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "netherite_musket_ball").toString()));
	public static final RegistryObject<EntityType<FlareEntity>> FLARE_ENTITY = ENTITY_TYPES.register("flare", () -> EntityType.Builder.<FlareEntity> of((type, world) -> new FlareEntity(type, world, 0), EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flare").toString()));
	public static final RegistryObject<EntityType<SmokeBombEntity>> SMOKE_BOMB_ENTITY = ENTITY_TYPES.register("smoke_bomb", () -> EntityType.Builder.<SmokeBombEntity> of(SmokeBombEntity::new, EntityClassification.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb").toString()));
	public static final RegistryObject<EntityType<MolotovEntity>> MOLOTOV_COCKTAIL_ENTITY = ENTITY_TYPES.register("molotov_cocktail", () -> EntityType.Builder.<MolotovEntity> of(MolotovEntity::new, EntityClassification.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "molotov_cocktail").toString()));
	public static final RegistryObject<EntityType<DyingSoldierEntity>> DYING_SOLDIER_ENTITY = ENTITY_TYPES.register("dying_soldier", () -> EntityType.Builder.of(DyingSoldierEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier").toString()));
	public static final RegistryObject<EntityType<MinutemanEntity>> MINUTEMAN_ENTITY = ENTITY_TYPES.register("minuteman", () -> EntityType.Builder.of(MinutemanEntity::new, EntityClassification.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "minuteman").toString()));
	public static final RegistryObject<EntityType<FieldMedicEntity>> FIELD_MEDIC_ENTITY = ENTITY_TYPES.register("field_medic", () -> EntityType.Builder.of(FieldMedicEntity::new, EntityClassification.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic").toString()));
	public static final RegistryObject<EntityType<ChairEntity>> CHAIR_ENTITY = ENTITY_TYPES.register("chair", () -> EntityType.Builder.of(ChairEntity::new, EntityClassification.MISC).sized(0.0f, 0.0f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "chair").toString()));
	public static final RegistryObject<EntityType<WanderingWarriorEntity>> WANDERING_WARRIOR_ENTITY = ENTITY_TYPES.register("wandering_warrior", () -> EntityType.Builder.of(WanderingWarriorEntity::new, EntityClassification.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior").toString()));
	public static final RegistryObject<EntityType<HansEntity>> HANS_ENTITY = ENTITY_TYPES.register("hans", () -> EntityType.Builder.of(HansEntity::new, EntityClassification.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "hans").toString()));
	public static final RegistryObject<EntityType<MortarShellEntity>> MORTAR_SHELL_ENTITY = ENTITY_TYPES.register("mortar_shell", () -> EntityType.Builder.of(MortarShellEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "mortar_shell").toString()));

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
	public static final RegistryObject<SoundEvent> BLUNDERBUSS_FIRE = SOUND_EVENTS.register("blunderbuss_fire", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "blunderbuss_fire")));
	public static final RegistryObject<SoundEvent> BARBED_WIRE_RATTLE = SOUND_EVENTS.register("barbed_wire_rattle", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "barbed_wire_rattle")));
	public static final RegistryObject<SoundEvent> BEAR_TRAP_CLOSE = SOUND_EVENTS.register("bear_trap_close", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap_close")));
	public static final RegistryObject<SoundEvent> SPIKE_TRAP_EXTEND = SOUND_EVENTS.register("spike_trap_extend", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap_extend")));
	public static final RegistryObject<SoundEvent> SPIKE_TRAP_RETRACT = SOUND_EVENTS.register("spike_trap_retract", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap_retract")));
	public static final RegistryObject<SoundEvent> ALARM_1 = SOUND_EVENTS.register("alarm_1", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "alarm_1")));
	public static final RegistryObject<SoundEvent> ALARM_2 = SOUND_EVENTS.register("alarm_2", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "alarm_2")));
	public static final RegistryObject<SoundEvent> ALARM_3 = SOUND_EVENTS.register("alarm_3", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "alarm_3")));
	public static final RegistryObject<SoundEvent> DYING_SOLDIER_AMBIENT = SOUND_EVENTS.register("dying_soldier_ambient", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier_ambient")));
	public static final RegistryObject<SoundEvent> DYING_SOLDIER_STEP = SOUND_EVENTS.register("dying_soldier_step", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier_step")));
	public static final RegistryObject<SoundEvent> DYING_SOLDIER_DEATH = SOUND_EVENTS.register("dying_soldier_death", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier_death")));
	public static final RegistryObject<SoundEvent> DYING_SOLDIER_HURT = SOUND_EVENTS.register("dying_soldier_hurt", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier_hurt")));
	public static final RegistryObject<SoundEvent> BATTLEFIELD_AMBIENT = SOUND_EVENTS.register("battlefield_ambient", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "battlefield_ambient")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_ATTACK = SOUND_EVENTS.register("field_medic_attack", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_attack")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_AMBIENT = SOUND_EVENTS.register("field_medic_ambient", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_ambient")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_HURT = SOUND_EVENTS.register("field_medic_hurt", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_hurt")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_DEATH = SOUND_EVENTS.register("field_medic_death", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_death")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_STEP = SOUND_EVENTS.register("field_medic_step", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_step")));
	public static final RegistryObject<SoundEvent> FLARE_GUN_FIRE = SOUND_EVENTS.register("flare_gun_fire", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flare_gun_fire")));
	public static final RegistryObject<SoundEvent> WANDERING_WARRIOR_AMBIENT = SOUND_EVENTS.register("wandering_warrior_ambient", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_ambient")));
	public static final RegistryObject<SoundEvent> WANDERING_WARRIOR_HURT = SOUND_EVENTS.register("wandering_warrior_hurt", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_hurt")));
	public static final RegistryObject<SoundEvent> WANDERING_WARRIOR_DEATH = SOUND_EVENTS.register("wandering_warrior_death", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_death")));
	public static final RegistryObject<SoundEvent> WANDERING_WARRIOR_STEP = SOUND_EVENTS.register("wandering_warrior_step", () -> new SoundEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_step")));

	// Containers
	public static final RegistryObject<ContainerType<SmallPartsContainer>> SMALL_PARTS_TABLE_CONTAINER = CONTAINER_TYPES.register("small_parts_table", () -> IForgeContainerType.create((id, inv, data) -> new SmallPartsContainer(id, inv)));
	public static final RegistryObject<ContainerType<TeslaSynthesizerContainer>> TESLA_SYNTHESIZER_CONTAINER = CONTAINER_TYPES.register("tesla_synthesizer", () -> IForgeContainerType.create((id, inv, data) -> new TeslaSynthesizerContainer(id, inv)));

	// Recipes
	public static final RegistryObject<IRecipeSerializer<?>> SMALL_PARTS_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("small_parts", SmallPartsRecipe.Serializer::new);
	public static final RegistryObject<IRecipeSerializer<?>> TESLA_SYNTHEZISER_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("tesla_synthesizer", TeslaSynthesizerRecipe.Serializer::new);

	// Loot Table Modifiers
	public static final RegistryObject<LootTableHandler.LogShardsLootModifierHandler.Serializer> WOOD_LOGS_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("log_shards", LootTableHandler.LogShardsLootModifierHandler.Serializer::new);

	// Particles
	public static final RegistryObject<ParticleType<SmokeBombParticleData>> SMOKE_BOMB_PARTICLE_TYPE = PARTICLE_TYPES.register("smoke_bomb", SmokeBombParticleType::new);

	// Tile Entities
	public static final RegistryObject<TileEntityType<?>> BEAR_TRAP_TILE_ENTITY = TILE_ENTITIES.register("bear_trap", () -> new TileEntityType<>(BearTrapTileEntity::new, Sets.newHashSet(BEAR_TRAP.get()), null));
	public static final RegistryObject<TileEntityType<?>> WALL_SHELF_TILE_ENTITY = TILE_ENTITIES.register("wall_shelf", () -> new TileEntityType<>(WallShelfTileEntity::new, Sets.newHashSet(WALL_SHELF.get()), null));
	public static final RegistryObject<TileEntityType<?>> PANIC_ALARM_TILE_ENTITY = TILE_ENTITIES.register("panic_alarm", () -> new TileEntityType<>(PanicAlarmTileEntity::new, Sets.newHashSet(PANIC_ALARM.get()), null));
	public static final RegistryObject<TileEntityType<?>> MINUTEMAN_STATUE_TILE_ENTITY = TILE_ENTITIES.register("minuteman_statue", () -> new TileEntityType<>(MinutemanStatueTileEntity::new, Sets.newHashSet(MINUTEMAN_STATUE.get()), null));
	public static final RegistryObject<TileEntityType<?>> MEDIC_STATUE_TILE_ENTITY = TILE_ENTITIES.register("medic_statue", () -> new TileEntityType<>(MedicStatueTileEntity::new, Sets.newHashSet(MEDIC_STATUE.get()), null));
	public static final RegistryObject<TileEntityType<?>> TESLA_SYNTHESIZER_TILE_ENTITY = TILE_ENTITIES.register("tesla_synthesizer", () -> new TileEntityType<>(TeslaSynthesizerTileEntity::new, Sets.newHashSet(TESLA_SYNTHESIZER.get()), null));

	// Biomes
	public static final RegistryObject<Biome> BATTLEFIELD = BIOMES.register("battlefield", () -> BiomeBuilder.makeBattlefieldBiome(
			BiomeBuilder.getSurfaceBuilder(ConfiguredSurfaceBuilders.BATTLEFIELD),
			0.18f, 0.1f, true)
	);

	// World Carvers
	public static final RegistryObject<WorldCarver<ProbabilityConfig>> TRENCH_WORLD_CARVER = WORLD_CARVERS.register("trench", () -> new TrenchWorldCarver(ProbabilityConfig.CODEC, 256));

	// Effects
	public static final RegistryObject<Effect> MORPHINE_EFFECT = EFFECTS.register("morphine", () -> new MorphineEffect(EffectType.NEUTRAL, 3484189));
}