package com.anonymoushacker1279.immersiveweapons;

import com.anonymoushacker1279.immersiveweapons.entity.passive.MinutemanEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.init.DispenserBehaviorRegistry;
import com.anonymoushacker1279.immersiveweapons.init.OreGeneratorHandler;
import com.anonymoushacker1279.immersiveweapons.util.*;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.WorldEvent.PotentialSpawns;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmersiveWeapons.MOD_ID)
@Mod.EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD)
public class ImmersiveWeapons {

	public static final String MOD_ID = "immersiveweapons";

	// Setup logger
	public static final Logger LOGGER = LogManager.getLogger();

	public ImmersiveWeapons() {
		// Load configuration
		Config.setup(FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".toml"));

		ConfiguredSurfaceBuilders.register();
		DeferredRegistryHandler.init();

		MinecraftForge.EVENT_BUS.register(this);

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		Structures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		modEventBus.addListener(this::setup);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, this::onBiomeLoading);
	}

	private static void setupBiome(final Biome biome, final BiomeManager.BiomeType biomeType, final int weight, final BiomeDictionary.Type... types) {
		BiomeDictionary.addTypes(key(biome), types);
		BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(key(biome), weight));
	}

	private static RegistryKey<Biome> key(final Biome biome) {
		return RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome), "Biome registry name was null"));
	}

	@SubscribeEvent
	public void setup(final FMLCommonSetupEvent event) {
		OreGeneratorHandler.init(event);
		DispenserBehaviorRegistry.init();
		event.enqueueWork(() -> {
			setupBiome(DeferredRegistryHandler.BATTLEFIELD.get(), BiomeManager.BiomeType.WARM, 3, Type.PLAINS, Type.OVERWORLD);
			Structures.setupStructures();
			Structures.registerAllPieces();
			ConfiguredStructures.registerConfiguredStructures();
			Structures.init();
		});
		AddAttributesAfterSetup.init();
	}

	@SubscribeEvent
	public void onBiomeLoading(final BiomeLoadingEvent event) {
		// Ore generation
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		if (event.getCategory() != Biome.Category.NETHER || event.getCategory() != Biome.Category.THEEND) {
			event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
					.add(() -> OreGeneratorHandler.ORE_COPPER_CONFIG);
		}
		if (event.getCategory() == Biome.Category.NETHER) {
			event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
					.add(() -> OreGeneratorHandler.ORE_MOLTEN_CONFIG);
		}

		// Biome modification
		if (event.getCategory() == Category.FOREST) {
			generation.withStructure(ConfiguredStructures.CONFIGURED_ABANDONED_FACTORY);
			generation.withStructure(ConfiguredStructures.CONFIGURED_UNDERGROUND_BUNKER);
			generation.withStructure(ConfiguredStructures.CONFIGURED_BEAR_TRAP);
		}
		if (event.getCategory() == Category.PLAINS) {
			generation.withStructure(ConfiguredStructures.CONFIGURED_ABANDONED_FACTORY);
		}
		if (event.getCategory() == Category.JUNGLE) {
			generation.withStructure(ConfiguredStructures.CONFIGURED_PITFALL_TRAP);
		}
		if (event.getCategory() == Category.DESERT) {
			generation.withStructure(ConfiguredStructures.CONFIGURED_LANDMINE_TRAP);
		}
		if (Objects.requireNonNull(event.getName()).toString().equals(Objects.requireNonNull(DeferredRegistryHandler.BATTLEFIELD.get().getRegistryName()).toString())) {
			generation.withStructure(ConfiguredStructures.CONFIGURED_BATTLEFIELD_CAMP);
			generation.withStructure(ConfiguredStructures.CONFIGURED_UNDERGROUND_BUNKER);
			generation.withStructure(ConfiguredStructures.CONFIGURED_BEAR_TRAP);
			generation.withCarver(GenerationStage.Carving.AIR, new ConfiguredCarver(DeferredRegistryHandler.TRENCH_WORLD_CARVER.get(), new ProbabilityConfig(0.11f)));

		}
	}

	@SubscribeEvent
	public void potentialSpawns(final PotentialSpawns event) {
		if (Objects.equals(event.getWorld().getBiome(event.getPos()).getRegistryName(), DeferredRegistryHandler.BATTLEFIELD.get().getRegistryName())) {
			if (((ServerWorld) event.getWorld()).doesVillageHaveAllSections(event.getPos(), 2)) {
				this.spawnMinuteman((ServerWorld) event.getWorld(), event.getPos());
			}
		}
	}

	@SuppressWarnings("resource")

	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) event.getWorld();

			if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
					serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
				return;
			}

			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
			tempMap.put(Structures.ABANDONED_FACTORY.get(), DimensionStructuresSettings.field_236191_b_.get(Structures.ABANDONED_FACTORY.get()));
			tempMap.put(Structures.PITFALL_TRAP.get(), DimensionStructuresSettings.field_236191_b_.get(Structures.PITFALL_TRAP.get()));
			tempMap.put(Structures.BEAR_TRAP.get(), DimensionStructuresSettings.field_236191_b_.get(Structures.BEAR_TRAP.get()));
			tempMap.put(Structures.LANDMINE_TRAP.get(), DimensionStructuresSettings.field_236191_b_.get(Structures.LANDMINE_TRAP.get()));
			tempMap.put(Structures.UNDERGROUND_BUNKER.get(), DimensionStructuresSettings.field_236191_b_.get(Structures.UNDERGROUND_BUNKER.get()));
			tempMap.put(Structures.BATTLEFIELD_CAMP.get(), DimensionStructuresSettings.field_236191_b_.get(Structures.BATTLEFIELD_CAMP.get()));
			serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;

		}
	}

	private void spawnMinuteman(ServerWorld worldIn, BlockPos blockPos) {
		if (worldIn.getPointOfInterestManager().getCountInRange(PointOfInterestType.HOME.getPredicate(), blockPos, 48, PointOfInterestManager.Status.IS_OCCUPIED) > 4L) {
			List<MinutemanEntity> list = worldIn.getEntitiesWithinAABB(MinutemanEntity.class, (new AxisAlignedBB(blockPos)).grow(48.0D, 8.0D, 48.0D));
			if (list.size() < 3) {
				MinutemanEntity minutemanEntity = DeferredRegistryHandler.MINUTEMAN_ENTITY.get().create(worldIn);
				if (minutemanEntity == null) {
				} else {
					minutemanEntity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(blockPos), SpawnReason.NATURAL, null, null);
					minutemanEntity.moveToBlockPosAndAngles(blockPos, 0.0F, 0.0F);
					worldIn.addEntity(minutemanEntity);
				}
			}
		}
	}
}