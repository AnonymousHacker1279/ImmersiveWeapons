package tech.anonymoushacker1279.immersiveweapons.data.modifiers;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWPlacedFeatures;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

public class IWBiomeModifiers {

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		// Ores
		biomeModifier(context, new ResourceLocation(ImmersiveWeapons.MOD_ID, "molten_ore"),
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_NETHER),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.MOLTEN_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, new ResourceLocation(ImmersiveWeapons.MOD_ID, "nether_sulfur_ore"),
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_NETHER),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.NETHER_SULFUR_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, new ResourceLocation(ImmersiveWeapons.MOD_ID, "deepslate_sulfur_ore"),
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(ForgeWorldGenTagGroups.IS_WET_CAVE),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.DEEPSLATE_SULFUR_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, new ResourceLocation(ImmersiveWeapons.MOD_ID, "sulfur_ore"),
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.WATER_ON_MAP_OUTLINES),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.SULFUR_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, new ResourceLocation(ImmersiveWeapons.MOD_ID, "deepslate_cobalt_ore"),
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.DEEPSLATE_COBALT_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		biomeModifier(context, new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_ore"),
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(IWPlacedFeatures.COBALT_ORE)),
						Decoration.UNDERGROUND_ORES
				));

		// Spawns
		biomeModifier(context, new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_spawn"),
				AddSpawnsBiomeModifier.singleSpawn(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						new SpawnerData(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), 65, 1, 1)
				));
		biomeModifier(context, new ResourceLocation(ImmersiveWeapons.MOD_ID, "hans_spawn"),
				AddSpawnsBiomeModifier.singleSpawn(
						context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
						new SpawnerData(EntityRegistry.HANS_ENTITY.get(), 5, 1, 1)
				));
	}

	protected static void biomeModifier(BootstapContext<BiomeModifier> context, ResourceLocation name, BiomeModifier modifier) {
		context.register(ResourceKey.create(Keys.BIOME_MODIFIERS, name), modifier);
	}
}