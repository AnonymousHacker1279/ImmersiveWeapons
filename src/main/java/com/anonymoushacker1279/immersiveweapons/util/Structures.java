package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.world.structures.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.Objects;
import java.util.function.Supplier;

public class Structures {

	public static final StructurePieceType AF = AbandonedFactoryPieces.Piece::new;
	public static final StructurePieceType PT = PitfallTrapPieces.Piece::new;
	public static final StructurePieceType BT = BearTrapPieces.Piece::new;
	public static final StructurePieceType LT = LandmineTrapPieces.Piece::new;
	public static final StructurePieceType UB = UndergroundBunkerPieces.Piece::new;
	public static final StructurePieceType BF_C = BattlefieldCampPieces.Piece::new;
	public static final StructurePieceType CI = CloudIslandPieces.Piece::new;
	public static final StructurePieceType CS = CampsitePieces.Piece::new;

	/**
	 * Setup a structure.
	 * @param name the structure name
	 * @param structure the <code>Supplier</code> for the structure
	 * @return RegistryObject
	 */
	public static <T extends StructureFeature<?>> RegistryObject<T> setupStructure(String name, Supplier<T> structure) {
		return DeferredRegistryHandler.STRUCTURES.register(name, structure);
	}

	/**
	 * Setup structures.
	 */
	public static void setupStructures() {
		setupStructure(
				DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_ABANDONED_FACTORY_DISTANCE.get(),
						Config.MIN_ABANDONED_FACTORY_DISTANCE.get(),
						959874384),
				true);
		setupStructure(
				DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_PITFALL_TRAP_DISTANCE.get(),
						Config.MIN_PITFALL_TRAP_DISTANCE.get(),
						875412395),
				false);
		setupStructure(
				DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_BEAR_TRAP_DISTANCE.get(),
						Config.MIN_BEAR_TRAP_DISTANCE.get(),
						794532168),
				false);
		setupStructure(
				DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_LANDMINE_TRAP_DISTANCE.get(),
						Config.MIN_LANDMINE_TRAP_DISTANCE.get(),
						959874384),
				true);
		setupStructure(
				DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_UNDERGROUND_BUNKER_DISTANCE.get(),
						Config.MIN_UNDERGROUND_BUNKER_DISTANCE.get(),
						548796135),
				false);
		setupStructure(
				DeferredRegistryHandler.BATTLEFIELD_CAMP_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_BATTLEFIELD_CAMP_DISTANCE.get(),
						Config.MIN_BATTLEFIELD_CAMP_DISTANCE.get(),
						458962175),
				true);
		setupStructure(
				DeferredRegistryHandler.BATTLEFIELD_VILLAGE_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_BATTLEFIELD_VILLAGE_DISTANCE.get(),
						Config.MIN_BATTLEFIELD_VILLAGE_DISTANCE.get(),
						176482913),
				true);
		setupStructure(
				DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_CLOUD_ISLAND_DISTANCE.get(),
						Config.MIN_CLOUD_ISLAND_DISTANCE.get(),
						349821657),
				false);
		setupStructure(
				DeferredRegistryHandler.CAMPSITE_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_CAMPSITE_DISTANCE.get(),
						Config.MIN_CAMPSITE_DISTANCE.get(),
						671249835),
				true);
	}

	/**
	 * Setup structure with separation settings and transforms.
	 * @param structure the structure
	 * @param featureConfiguration the <code>StructureFeatureConfiguration</code>
	 * @param transformSurroundingLand if the structure should transform surrounding land
	 */
	private static <F extends StructureFeature<?>> void setupStructure(F structure, StructureFeatureConfiguration featureConfiguration, boolean transformSurroundingLand) {
		StructureFeature.STRUCTURES_REGISTRY.put(Objects.requireNonNull(structure.getRegistryName()).toString(), structure);

		/*
		 * Will add land at the base of the structure
		 */
		if (transformSurroundingLand) {
			StructureFeature.NOISE_AFFECTING_FEATURES =
					ImmutableList.<StructureFeature<?>> builder()
							.addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
							.add(structure)
							.build();
		}

		StructureSettings.DEFAULTS =
				ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration> builder()
						.putAll(StructureSettings.DEFAULTS)
						.put(structure, featureConfiguration)
						.build();
	}

	/**
	 * Register pieces.
	 */
	public static void registerAllPieces() {
		registerStructurePiece(AF, new ResourceLocation(ImmersiveWeapons.MOD_ID, "af"));
		registerStructurePiece(PT, new ResourceLocation(ImmersiveWeapons.MOD_ID, "pt"));
		registerStructurePiece(BT, new ResourceLocation(ImmersiveWeapons.MOD_ID, "bt"));
		registerStructurePiece(LT, new ResourceLocation(ImmersiveWeapons.MOD_ID, "lt"));
		registerStructurePiece(UB, new ResourceLocation(ImmersiveWeapons.MOD_ID, "ub"));
		registerStructurePiece(BF_C, new ResourceLocation(ImmersiveWeapons.MOD_ID, "bf_c"));
		registerStructurePiece(CI, new ResourceLocation(ImmersiveWeapons.MOD_ID, "ci"));
		registerStructurePiece(CS, new ResourceLocation(ImmersiveWeapons.MOD_ID, "cs"));
	}

	/**
	 * Register a structure piece.
	 * @param structurePiece the <code>StructurePieceType</code>
	 * @param resourceLocation the <code>ResourceLocation</code> of the piece
	 */
	private static void registerStructurePiece(StructurePieceType structurePiece, ResourceLocation resourceLocation) {
		Registry.register(Registry.STRUCTURE_PIECE, resourceLocation, structurePiece);
	}

}