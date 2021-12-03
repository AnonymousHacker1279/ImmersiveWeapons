package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators.BurnedBranchDecorator;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ConfiguredStructures {

	// TODO: Rework when Forge API updates
	/*public static final ConfiguredStructureFeature<?, ?> CONFIGURED_ABANDONED_FACTORY = DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_PITFALL_TRAP = DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_BEAR_TRAP = DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_LANDMINE_TRAP = DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_UNDERGROUND_BUNKER = DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_BATTLEFIELD_CAMP = DeferredRegistryHandler.BATTLEFIELD_CAMP_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_BATTLEFIELD_VILLAGE = DeferredRegistryHandler.BATTLEFIELD_VILLAGE_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_CLOUD_ISLAND = DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_CAMPSITE = DeferredRegistryHandler.CAMPSITE_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_BATTLEFIELD_HOUSE = DeferredRegistryHandler.BATTLEFIELD_HOUSE_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_OUTHOUSE = DeferredRegistryHandler.OUTHOUSE_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_WATER_TOWER = DeferredRegistryHandler.WATER_TOWER_STRUCTURE.get().configured(FeatureConfiguration.NONE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_GRAVEYARD = DeferredRegistryHandler.GRAVEYARD_STRUCTURE.get().configured(FeatureConfiguration.NONE);*/

	public static final ConfiguredFeature<?, ?> CONFIGURED_WOODEN_SPIKES = Feature.RANDOM_PATCH.configured(
			FeatureUtils.simpleRandomPatchConfiguration(1, Feature.SIMPLE_BLOCK.configured(
					new SimpleBlockConfiguration(BlockStateProvider.simple(DeferredRegistryHandler.WOODEN_SPIKES.get()))).onlyWhenEmpty()));
	public static final ConfiguredFeature<?, ?> CONFIGURED_BURNED_OAK_TREE = Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(
			SimpleStateProvider.simple(DeferredRegistryHandler.BURNED_OAK_LOG.get().defaultBlockState()),
					new StraightTrunkPlacer(8, 4, 1),
					SimpleStateProvider.simple(Blocks.AIR.defaultBlockState()),
					new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0),
					new TwoLayersFeatureSize(1, 0, 1)))
			.decorators(ImmutableList.of(new BurnedBranchDecorator(0.8F))).ignoreVines().build());

	// TODO: Rework when Forge API updates
	/**
	 * Register configured structures.
	 */
	/*public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_abandoned_factory"), CONFIGURED_ABANDONED_FACTORY);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get(), CONFIGURED_ABANDONED_FACTORY);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_pitfall_trap"), CONFIGURED_PITFALL_TRAP);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get(), CONFIGURED_PITFALL_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_bear_trap"), CONFIGURED_BEAR_TRAP);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get(), CONFIGURED_BEAR_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_landmine_trap"), CONFIGURED_LANDMINE_TRAP);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get(), CONFIGURED_LANDMINE_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_underground_bunker"), CONFIGURED_UNDERGROUND_BUNKER);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get(), CONFIGURED_UNDERGROUND_BUNKER);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_battlefield_camp"), CONFIGURED_BATTLEFIELD_CAMP);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.BATTLEFIELD_CAMP_STRUCTURE.get(), CONFIGURED_BATTLEFIELD_CAMP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_battlefield_village"), CONFIGURED_BATTLEFIELD_VILLAGE);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.BATTLEFIELD_VILLAGE_STRUCTURE.get(), CONFIGURED_BATTLEFIELD_VILLAGE);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_cloud_island"), CONFIGURED_CLOUD_ISLAND);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get(), CONFIGURED_CLOUD_ISLAND);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_campsite"), CONFIGURED_CAMPSITE);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.CAMPSITE_STRUCTURE.get(), CONFIGURED_CAMPSITE);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_battlefield_house"), CONFIGURED_BATTLEFIELD_HOUSE);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.BATTLEFIELD_HOUSE_STRUCTURE.get(), CONFIGURED_BATTLEFIELD_HOUSE);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_outhouse"), CONFIGURED_OUTHOUSE);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.OUTHOUSE_STRUCTURE.get(), CONFIGURED_OUTHOUSE);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_water_tower"), CONFIGURED_WATER_TOWER);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.WATER_TOWER_STRUCTURE.get(), CONFIGURED_WATER_TOWER);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_graveyard"), CONFIGURED_GRAVEYARD);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(DeferredRegistryHandler.GRAVEYARD_STRUCTURE.get(), CONFIGURED_GRAVEYARD);
	}*/
}