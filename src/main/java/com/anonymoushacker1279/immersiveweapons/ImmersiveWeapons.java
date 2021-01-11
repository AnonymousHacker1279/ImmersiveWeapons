package com.anonymoushacker1279.immersiveweapons;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.init.DispenserBehaviorRegistry;
import com.anonymoushacker1279.immersiveweapons.init.OreGeneratorHandler;
import com.anonymoushacker1279.immersiveweapons.util.AddAttributesAfterSetup;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.STConfiguredStructures;
import com.anonymoushacker1279.immersiveweapons.util.STStructures;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod("immersiveweapons")
public class ImmersiveWeapons
{
	
	public static final String MOD_ID = "immersiveweapons";
	
	// Setup logger
	public static final Logger LOGGER = LogManager.getLogger();

    public ImmersiveWeapons() {
    	// Load configuration
    	Config.setup(FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".toml"));
    	
    	DeferredRegistryHandler.init();
    	
    	MinecraftForge.EVENT_BUS.register(this);
    	
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        STStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
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
            STStructures.setupStructures();
            STStructures.registerAllPieces();
            STConfiguredStructures.registerConfiguredStructures();
            STStructures.init();
        });
    	
    	AddAttributesAfterSetup.init();
	}

    public void biomeModification(final BiomeLoadingEvent event) {
        // Add structure to all biomes

        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        if(
                event.getCategory() == Biome.Category.FOREST
                // && event.getDepth() < 0.2f
        ) {
            generation.getStructures().add(() -> (STConfiguredStructures.CONFIGURED_ABANDONED_FACTORY));
        }
    }


    
    @SubscribeEvent
    public void onBiomeLoading(final BiomeLoadingEvent biome) {
    	if(biome.getCategory() != Biome.Category.NETHER || biome.getCategory() != Biome.Category.THEEND) {
    		biome.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
    		.add(() -> OreGeneratorHandler.ORE_COPPER_CONFIG);
    	};
    	if(biome.getCategory() == Biome.Category.NETHER) {
    		biome.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
    		.add(() -> OreGeneratorHandler.ORE_MOLTEN_CONFIG);
    	};
    }
    
    @SuppressWarnings("resource")
	public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            // Prevent spawning our structure in Vanilla's superflat world

            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.getDimensionKey().equals(World.OVERWORLD)){
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap.put(STStructures.ABANDONED_FACTORY.get(), DimensionStructuresSettings.field_236191_b_.get(STStructures.ABANDONED_FACTORY.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;

        }
    }

    
    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) {
        entry.setRegistryName(new ResourceLocation(ImmersiveWeapons.MOD_ID, registryKey));
        registry.register(entry);
        return entry;
    }
}
