package com.anonymoushacker1279.immersiveweapons;

import java.util.function.Supplier;

import com.anonymoushacker1279.immersiveweapons.world.structures.AbandonedFactory;
import com.anonymoushacker1279.immersiveweapons.world.structures.AbandonedFactoryPieces;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class STStructures {

	public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, ImmersiveWeapons.MOD_ID);
	
	public static void init() {
		DEFERRED_REGISTRY_STRUCTURE.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<Structure<NoFeatureConfig>> ABANDONED_FACTORY = setupStructure("abandoned_factory", () -> (new AbandonedFactory(NoFeatureConfig.field_236558_a_)));
	
	private static <T extends Structure<?>> RegistryObject<T> setupStructure(String name, Supplier<T> structure) {
		return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
	}
	

	public static IStructurePieceType AF = AbandonedFactoryPieces.Piece::new;
	
	public static void setupStructures() {
		setupStructure(
				ABANDONED_FACTORY.get(),
				new StructureSeparationSettings(
						Config.MAX_ABANDONED_FACTORY_DISTANCE.get(),
						Config.MIN_ABANDONED_FACTORY_DISTANCE.get(),
						959874384),
				true);
	}
	
	public static <F extends Structure<?>> void setupStructure(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        /*
         * Will add land at the base of the structure
         */
        if(transformSurroundingLand) {
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }

        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }

	public static void registerAllPieces() {
        registerStructurePiece(AF, new ResourceLocation(ImmersiveWeapons.MOD_ID, "af"));
    }
	
    static void registerStructurePiece(IStructurePieceType structurePiece, ResourceLocation rl) {
        Registry.register(Registry.STRUCTURE_PIECE, rl, structurePiece);
    }

}
