package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.world.structures.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class Structures {

	public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, ImmersiveWeapons.MOD_ID);
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> ABANDONED_FACTORY = setupStructure("abandoned_factory", () -> (new AbandonedFactory(NoneFeatureConfiguration.CODEC)));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> PITFALL_TRAP = setupStructure("pitfall_trap", () -> (new PitfallTrap(NoneFeatureConfiguration.CODEC)));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> BEAR_TRAP = setupStructure("bear_trap", () -> (new BearTrap(NoneFeatureConfiguration.CODEC)));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> LANDMINE_TRAP = setupStructure("landmine_trap", () -> (new LandmineTrap(NoneFeatureConfiguration.CODEC)));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> UNDERGROUND_BUNKER = setupStructure("underground_bunker", () -> (new UndergroundBunker(NoneFeatureConfiguration.CODEC)));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> BATTLEFIELD_CAMP = setupStructure("battlefield_camp", () -> (new BattlefieldCamp(NoneFeatureConfiguration.CODEC)));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> BATTLEFIELD_VILLAGE = setupStructure("battlefield_village", () -> (new BattlefieldVillage(NoneFeatureConfiguration.CODEC)));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CLOUD_ISLAND = setupStructure("cloud_island", () -> (new CloudIsland(NoneFeatureConfiguration.CODEC)));
	public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CAMPSITE = setupStructure("campsite", () -> (new Campsite(NoneFeatureConfiguration.CODEC)));
	public static StructurePieceType AF = AbandonedFactoryPieces.Piece::new;
	public static StructurePieceType PT = PitfallTrapPieces.Piece::new;
	public static StructurePieceType BT = BearTrapPieces.Piece::new;
	public static StructurePieceType LT = LandmineTrapPieces.Piece::new;
	public static StructurePieceType UB = UndergroundBunkerPieces.Piece::new;
	public static StructurePieceType BF_C = BattlefieldCampPieces.Piece::new;
	public static StructurePieceType CI = CloudIslandPieces.Piece::new;
	public static StructurePieceType CS = CampsitePieces.Piece::new;

	/**
	 * Initialize the deferred registry structures.
	 */
	public static void init() {
		DEFERRED_REGISTRY_STRUCTURE.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	/**
	 * Setup a structure.
	 * @param name the structure name
	 * @param structure the <code>Supplier</code> for the structure
	 * @return RegistryObject
	 */
	private static <T extends StructureFeature<?>> RegistryObject<T> setupStructure(String name, Supplier<T> structure) {
		return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
	}

	/**
	 * Setup structures.
	 */
	public static void setupStructures() {
		setupStructure(
				ABANDONED_FACTORY.get(),
				new StructureFeatureConfiguration(
						Config.MAX_ABANDONED_FACTORY_DISTANCE.get(),
						Config.MIN_ABANDONED_FACTORY_DISTANCE.get(),
						959874384),
				true);
		setupStructure(
				PITFALL_TRAP.get(),
				new StructureFeatureConfiguration(
						Config.MAX_PITFALL_TRAP_DISTANCE.get(),
						Config.MIN_PITFALL_TRAP_DISTANCE.get(),
						875412395),
				false);
		setupStructure(
				BEAR_TRAP.get(),
				new StructureFeatureConfiguration(
						Config.MAX_BEAR_TRAP_DISTANCE.get(),
						Config.MIN_BEAR_TRAP_DISTANCE.get(),
						794532168),
				false);
		setupStructure(
				LANDMINE_TRAP.get(),
				new StructureFeatureConfiguration(
						Config.MAX_LANDMINE_TRAP_DISTANCE.get(),
						Config.MIN_LANDMINE_TRAP_DISTANCE.get(),
						959874384),
				true);
		setupStructure(
				UNDERGROUND_BUNKER.get(),
				new StructureFeatureConfiguration(
						Config.MAX_UNDERGROUND_BUNKER_DISTANCE.get(),
						Config.MIN_UNDERGROUND_BUNKER_DISTANCE.get(),
						548796135),
				false);
		setupStructure(
				BATTLEFIELD_CAMP.get(),
				new StructureFeatureConfiguration(
						Config.MAX_BATTLEFIELD_CAMP_DISTANCE.get(),
						Config.MIN_BATTLEFIELD_CAMP_DISTANCE.get(),
						458962175),
				true);
		setupStructure(
				BATTLEFIELD_VILLAGE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_BATTLEFIELD_VILLAGE_DISTANCE.get(),
						Config.MIN_BATTLEFIELD_VILLAGE_DISTANCE.get(),
						176482913),
				true);
		setupStructure(
				CLOUD_ISLAND.get(),
				new StructureFeatureConfiguration(
						Config.MAX_CLOUD_ISLAND_DISTANCE.get(),
						Config.MIN_CLOUD_ISLAND_DISTANCE.get(),
						349821657),
				false);
		setupStructure(
				CAMPSITE.get(),
				new StructureFeatureConfiguration(
						Config.MAX_CAMPSITE_DISTANCE.get(),
						Config.MIN_CAMPSITE_DISTANCE.get(),
						671249835),
				true);
	}

	/**
	 * Setup structure with separation settings and transforms.
	 * @param structure the structure
	 * @param structureSeparationSettings the <code>StructureSeparationSettings</code>
	 * @param transformSurroundingLand if the structure should transform surrounding land
	 */
	private static <F extends StructureFeature<?>> void setupStructure(F structure, StructureFeatureConfiguration structureSeparationSettings, boolean transformSurroundingLand) {
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
						.put(structure, structureSeparationSettings)
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
	 * @param structurePiece the <code>IStructurePieceType</code>
	 * @param resourceLocation the <code>ResourceLocation</code> of the piece
	 */
	private static void registerStructurePiece(StructurePieceType structurePiece, ResourceLocation resourceLocation) {
		Registry.register(Registry.STRUCTURE_PIECE, resourceLocation, structurePiece);
	}

}