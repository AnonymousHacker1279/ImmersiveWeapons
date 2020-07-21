package com.anonymoushacker1279.immersiveweapons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anonymoushacker1279.immersiveweapons.util.DeferredRegistryHandler;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

// The value here should match an entry in the META-INF/mods.toml file
@SuppressWarnings("deprecation")
@Mod("immersiveweapons")
@Mod.EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD)
public class ImmersiveWeapons
{
	
	public static final String MOD_ID = "immersiveweapons";
	public static final ResourceLocation ABANDONED_FACTORY_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "abandoned_factory");
	
	public static IStructurePieceType ABANDONED_FACTORY_PIECE = null;
	
	@ObjectHolder(ImmersiveWeapons.MOD_ID+":abandoned_factory")
	public static Structure<NoFeatureConfig> ABANDONED_FACTORY;
	
	// Setup logger
	public static final Logger LOGGER = LogManager.getLogger();

    public ImmersiveWeapons() {
        // Register the setup method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        DeferredRegistryHandler.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
   }

    
    private void setup(final FMLCommonSetupEvent event) {
    	DeferredWorkQueue.runLater(new Runnable() {
    		@Override
    		public void run() {
    			for(Biome biome : ForgeRegistries.BIOMES) {
    				if(biome == Biomes.NETHER) {
    					// Ore generation
    					ConfiguredPlacement<CountRangeConfig> customConfig = Placement.COUNT_RANGE
    							.configure(new CountRangeConfig(10, 3, 3, 10));
    					biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
    							.withConfiguration(new OreFeatureConfig(FillerBlockType.NETHERRACK, DeferredRegistryHandler.MOLTEN_ORE.get().getDefaultState(), 10))
    							.withPlacement(customConfig));
    				}
    				if(biome == Biomes.PLAINS) {
    					// Structure generation
    					biome.addStructure(ABANDONED_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    					biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ABANDONED_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    				}
    				if(biome == Biomes.SWAMP) {
    					// Structure generation
    					biome.addStructure(ABANDONED_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    					biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ABANDONED_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    				}
    				if(biome == Biomes.SAVANNA) {
    					// Structure generation
    					biome.addStructure(ABANDONED_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    					biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ABANDONED_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    				}
    				// Ores that should generate in all biomes should go here
    				// Copper Ore
    				ConfiguredPlacement<CountRangeConfig> customConfig = Placement.COUNT_RANGE
							.configure(new CountRangeConfig(10, 20, 10, 12));
					biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
							.withConfiguration(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, DeferredRegistryHandler.COPPER_ORE.get().getDefaultState(), 10))
							.withPlacement(customConfig));
    			}
    		}
    	});
    }
    

    private void doClientStuff(final FMLClientSetupEvent event) {

    }
    
    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
    	LOGGER.debug("FML_Load_Complete","Forge loading complete!");
    	
    }
    
    public static final ItemGroup TAB = new ItemGroup("immersiveweaponsTab") {
    	@Override
    	public ItemStack createIcon() {
    		return new ItemStack(DeferredRegistryHandler.MOLTEN_SWORD.get());
    	}
    };

}
