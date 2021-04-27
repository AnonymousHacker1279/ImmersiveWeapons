package com.anonymoushacker1279.immersiveweapons;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.init.DispenserBehaviorRegistry;
import com.anonymoushacker1279.immersiveweapons.init.OreGeneratorHandler;
import com.anonymoushacker1279.immersiveweapons.util.AddAttributesAfterSetup;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.ConfiguredStructures;
import com.anonymoushacker1279.immersiveweapons.util.Structures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmersiveWeapons.MOD_ID)
public class ImmersiveWeapons {

	public static final String MOD_ID = "immersiveweapons";

	// Setup logger
	public static final Logger LOGGER = LogManager.getLogger();

	public ImmersiveWeapons() {
		// Load configuration
		Config.setup(FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".toml"));

		DeferredRegistryHandler.init();

		MinecraftForge.EVENT_BUS.register(this);

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		Structures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		modEventBus.addListener(this::setup);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
	}

	@SubscribeEvent
	public void setup(final FMLCommonSetupEvent event) {
		OreGeneratorHandler.init(event);
		DispenserBehaviorRegistry.init();
		event.enqueueWork(() -> {
			Structures.setupStructures();
			Structures.registerAllPieces();
			ConfiguredStructures.registerConfiguredStructures();
			Structures.init();
		});
		AddAttributesAfterSetup.init();
	}

	public void biomeModification(final BiomeLoadingEvent event) {
		// Add structure to all biomes

		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		if (event.getCategory() == Category.FOREST) {
			generation.getStructures().add(() -> (ConfiguredStructures.CONFIGURED_ABANDONED_FACTORY));
			generation.getStructures().add(() -> (ConfiguredStructures.CONFIGURED_UNDERGROUND_BUNKER));
			generation.getStructures().add(() -> (ConfiguredStructures.CONFIGURED_BEAR_TRAP));
		}
		if (event.getCategory() == Category.PLAINS) {
			generation.getStructures().add(() -> (ConfiguredStructures.CONFIGURED_ABANDONED_FACTORY));
		}
		if (event.getCategory() == Category.JUNGLE) {
			generation.getStructures().add(() -> (ConfiguredStructures.CONFIGURED_PITFALL_TRAP));
		}
		if (event.getCategory() == Category.DESERT) {
			generation.getStructures().add(() -> (ConfiguredStructures.CONFIGURED_LANDMINE_TRAP));
		}
	}


	@SubscribeEvent
	public void onBiomeLoading(final BiomeLoadingEvent biome) {
		if (biome.getCategory() != Biome.Category.NETHER || biome.getCategory() != Biome.Category.THEEND) {
			biome.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
					.add(() -> OreGeneratorHandler.ORE_COPPER_CONFIG);
		}
		if (biome.getCategory() == Biome.Category.NETHER) {
			biome.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
					.add(() -> OreGeneratorHandler.ORE_MOLTEN_CONFIG);
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
			serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;

		}
	}

	public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) {
		entry.setRegistryName(new ResourceLocation(ImmersiveWeapons.MOD_ID, registryKey));
		registry.register(entry);
		return entry;
	}
}