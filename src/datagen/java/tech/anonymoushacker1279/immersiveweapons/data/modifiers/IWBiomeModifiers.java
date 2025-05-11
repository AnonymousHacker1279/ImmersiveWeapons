package tech.anonymoushacker1279.immersiveweapons.data.modifiers;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.Weighted;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnsBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWPlacedFeatures;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

public class IWBiomeModifiers {

	public static void bootstrap(BootstrapContext<BiomeModifier> context) {
		// Ores
		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "molten_ore"),
				new BiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_NETHER),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.MOLTEN_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "nether_sulfur_ore"),
				new BiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_NETHER),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.NETHER_SULFUR_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deepslate_sulfur_ore"),
				new BiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(CommonWorldGenTagGroups.IS_WET_CAVE),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.DEEPSLATE_SULFUR_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "sulfur_ore"),
				new BiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.WATER_ON_MAP_OUTLINES),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.SULFUR_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deepslate_cobalt_ore"),
				new BiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.DEEPSLATE_COBALT_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "potassium_nitrate_ore"),
				new BiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.POTASSIUM_NITRATE_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "cobalt_ore"),
				new BiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.COBALT_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "void_ore"),
				new BiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_END),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.VOID_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		// Spawns
		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "wandering_warrior_spawn"),
				AddSpawnsBiomeModifier.singleSpawn(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						new Weighted<>(new SpawnerData(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), 1, 1), 65)
				));
		biomeModifier(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "hans_spawn"),
				AddSpawnsBiomeModifier.singleSpawn(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						new Weighted<>(new SpawnerData(EntityRegistry.HANS_ENTITY.get(), 1, 1), 5)
				));
	}

	protected static void biomeModifier(BootstrapContext<BiomeModifier> context, ResourceLocation name, BiomeModifier modifier) {
		context.register(ResourceKey.create(Keys.BIOME_MODIFIERS, name), modifier);
	}
}