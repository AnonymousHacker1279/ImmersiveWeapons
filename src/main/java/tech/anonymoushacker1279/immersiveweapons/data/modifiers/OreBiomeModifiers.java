package tech.anonymoushacker1279.immersiveweapons.data.modifiers;

import com.google.gson.JsonElement;
import net.minecraft.core.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.*;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.OreReplacementData.OreReplacementTargets;

import java.util.HashMap;
import java.util.List;

public class OreBiomeModifiers {

	public static class PlacedFeatures {
		protected static final ResourceLocation MOLTEN_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "ore_molten");
		protected static final ResourceKey<PlacedFeature> MOLTEN_ORE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, MOLTEN_ORE);
		protected static final PlacedFeature MOLTEN_ORE_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.SCATTERED_ORE,
						new OreConfiguration(OreReplacementTargets.MOLTEN_ORE_TARGETS, 2, 1.0f))),

				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(-64),
								VerticalAnchor.absolute(72)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(6)
				));

		protected static final ResourceLocation NETHER_SULFUR_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "ore_nether_sulfur");
		protected static final ResourceKey<PlacedFeature> NETHER_SULFUR_ORE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, NETHER_SULFUR_ORE);
		protected static final PlacedFeature NETHER_SULFUR_ORE_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.SCATTERED_ORE,
						new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, 16, 0.08f))),

				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(-16),
								VerticalAnchor.absolute(128)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(12)
				));

		protected static final ResourceLocation DEEPSLATE_SULFUR_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "ore_deepslate_sulfur");
		protected static final ResourceKey<PlacedFeature> DEEPSLATE_SULFUR_ORE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, DEEPSLATE_SULFUR_ORE);
		protected static final PlacedFeature DEEPSLATE_SULFUR_ORE_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.SCATTERED_ORE,
						new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, 16, 0.04f))),

				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(-64),
								VerticalAnchor.absolute(0)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(4)
				));

		protected static final ResourceLocation SULFUR_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "ore_sulfur");
		protected static final ResourceKey<PlacedFeature> SULFUR_ORE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, SULFUR_ORE);
		protected static final PlacedFeature SULFUR_ORE_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.SCATTERED_ORE,
						new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, 6, 0.1f))),

				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(32),
								VerticalAnchor.absolute(72)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(4)
				));

		protected static final ResourceLocation DEEPSLATE_COBALT_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "ore_deepslate_cobalt");
		protected static final ResourceKey<PlacedFeature> DEEPSLATE_COBALT_ORE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, DEEPSLATE_COBALT_ORE);
		protected static final PlacedFeature DEEPSLATE_COBALT_ORE_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(OreReplacementTargets.COBALT_ORE_TARGETS, 12, 0.1f))),

				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(-64),
								VerticalAnchor.absolute(0)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(12)
				));

		protected static final ResourceLocation COBALT_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "ore_cobalt");
		protected static final ResourceKey<PlacedFeature> COBALT_ORE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, COBALT_ORE);
		protected static final PlacedFeature COBALT_ORE_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(OreReplacementTargets.COBALT_ORE_TARGETS, 12, 0.15f))),

				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(7),
								VerticalAnchor.absolute(196)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(4)
				));

		public static JsonCodecProvider<PlacedFeature> getCodecProvider(DataGenerator generator,
		                                                                ExistingFileHelper existingFileHelper,
		                                                                RegistryOps<JsonElement> registryOps,
		                                                                ResourceKey<Registry<PlacedFeature>> placedFeatureKey) {

			return JsonCodecProvider.forDatapackRegistry(
					generator, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, placedFeatureKey, getPlacedFeatures());
		}

		private static HashMap<ResourceLocation, PlacedFeature> getPlacedFeatures() {
			HashMap<ResourceLocation, PlacedFeature> placedFeatures = new HashMap<>(10);
			placedFeatures.put(MOLTEN_ORE, MOLTEN_ORE_FEATURE);
			placedFeatures.put(NETHER_SULFUR_ORE, NETHER_SULFUR_ORE_FEATURE);
			placedFeatures.put(DEEPSLATE_SULFUR_ORE, DEEPSLATE_SULFUR_ORE_FEATURE);
			placedFeatures.put(SULFUR_ORE, SULFUR_ORE_FEATURE);
			placedFeatures.put(DEEPSLATE_COBALT_ORE, DEEPSLATE_COBALT_ORE_FEATURE);
			placedFeatures.put(COBALT_ORE, COBALT_ORE_FEATURE);

			return placedFeatures;
		}
	}

	private static final ResourceLocation ADD_MOLTEN_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_molten_ore");
	private static BiomeModifier addMoltenOreFeature;
	private static final ResourceLocation ADD_NETHER_SULFUR_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_nether_sulfur_ore");
	private static BiomeModifier addNetherSulfurOreFeature;
	private static final ResourceLocation ADD_DEEPSLATE_SULFUR_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_deepslate_sulfur_ore");
	private static BiomeModifier addDeepslateSulfurOreFeature;
	private static final ResourceLocation ADD_SULFUR_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_sulfur_ore");
	private static BiomeModifier addSulfurOreFeature;
	private static final ResourceLocation ADD_DEEPSLATE_COBALT_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_deepslate_cobalt_ore");
	private static BiomeModifier addDeepslateCobaltOreFeature;
	private static final ResourceLocation ADD_COBALT_ORE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_cobalt_ore");
	private static BiomeModifier addCobaltOreFeature;

	public static JsonCodecProvider<BiomeModifier> getCodecProvider(DataGenerator generator,
	                                                                ExistingFileHelper existingFileHelper,
	                                                                RegistryOps<JsonElement> registryOps,
	                                                                ResourceKey<Registry<BiomeModifier>> biomeModifiersKey) {

		fillFeatures(registryOps);
		return JsonCodecProvider.forDatapackRegistry(
				generator, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, biomeModifiersKey, getBiomeModifiers());
	}

	private static void fillFeatures(RegistryOps<JsonElement> registryOps) {
		HolderSet.Named<Biome> netherTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_NETHER);
		HolderSet.Named<Biome> wetCavesTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), ForgeWorldGenTagGroups.IS_WET_CAVE);
		HolderSet.Named<Biome> waterTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.WATER_ON_MAP_OUTLINES);
		HolderSet.Named<Biome> overworldTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD);

		addMoltenOreFeature = new AddFeaturesBiomeModifier(
				netherTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.MOLTEN_ORE_KEY.location()))),
				Decoration.UNDERGROUND_ORES);
		addNetherSulfurOreFeature = new AddFeaturesBiomeModifier(
				netherTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.NETHER_SULFUR_ORE_KEY.location()))),
				Decoration.UNDERGROUND_ORES);
		addDeepslateSulfurOreFeature = new AddFeaturesBiomeModifier(
				wetCavesTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.DEEPSLATE_SULFUR_ORE_KEY.location()))),
				Decoration.UNDERGROUND_ORES);
		addSulfurOreFeature = new AddFeaturesBiomeModifier(
				waterTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.SULFUR_ORE_KEY.location()))),
				Decoration.UNDERGROUND_ORES);
		addDeepslateCobaltOreFeature = new AddFeaturesBiomeModifier(
				overworldTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.DEEPSLATE_COBALT_ORE_KEY.location()))),
				Decoration.UNDERGROUND_ORES);
		addCobaltOreFeature = new AddFeaturesBiomeModifier(
				overworldTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.COBALT_ORE_KEY.location()))),
				Decoration.UNDERGROUND_ORES);
	}

	private static HashMap<ResourceLocation, BiomeModifier> getBiomeModifiers() {
		HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>(10);
		modifiers.put(ADD_MOLTEN_ORE, addMoltenOreFeature);
		modifiers.put(ADD_NETHER_SULFUR_ORE, addNetherSulfurOreFeature);
		modifiers.put(ADD_DEEPSLATE_SULFUR_ORE, addDeepslateSulfurOreFeature);
		modifiers.put(ADD_SULFUR_ORE, addSulfurOreFeature);
		modifiers.put(ADD_DEEPSLATE_COBALT_ORE, addDeepslateCobaltOreFeature);
		modifiers.put(ADD_COBALT_ORE, addCobaltOreFeature);

		return modifiers;
	}
}