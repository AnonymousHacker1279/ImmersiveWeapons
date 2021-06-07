package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ConfiguredSurfaceBuilders {

	public static RegistryKey<ConfiguredSurfaceBuilder<?>> BATTLEFIELD = key("battlefield");

	public static void register() {
		register(BATTLEFIELD,
				new ConfiguredSurfaceBuilder<>(
						SurfaceBuilder.DEFAULT,
						new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.COARSE_DIRT.defaultBlockState(), Blocks.STONE.defaultBlockState())
				)
		);
	}

	private static RegistryKey<ConfiguredSurfaceBuilder<?>> key(final String name) {
		return RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}

	private static void register(final RegistryKey<ConfiguredSurfaceBuilder<?>> key, final ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder) {
		Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, key.location(), configuredSurfaceBuilder);
	}
}