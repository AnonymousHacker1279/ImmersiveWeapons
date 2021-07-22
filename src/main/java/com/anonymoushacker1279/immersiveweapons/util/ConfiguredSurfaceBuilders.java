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

	/**
	 * Register surface builders.
	 */
	public static void register() {
		register(BATTLEFIELD,
				new ConfiguredSurfaceBuilder<>(
						SurfaceBuilder.DEFAULT,
						new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.COARSE_DIRT.defaultBlockState(), Blocks.STONE.defaultBlockState())
				)
		);
	}

	/**
	 * Create registry keys.
	 * @param name the name
	 * @return RegistryKey extending ConfiguredSurfaceBuilder
	 */
	private static RegistryKey<ConfiguredSurfaceBuilder<?>> key(String name) {
		return RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}

	/**
	 * Register a configured surface builder.
	 * @param key the <code>RegistryKey</code>, must extend ConfiguredSurfaceBuilder
	 * @param configuredSurfaceBuilder the <code>ConfiguredSurfaceBuilder</code> instance
	 */
	private static void register(RegistryKey<ConfiguredSurfaceBuilder<?>> key, ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder) {
		Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, key.location(), configuredSurfaceBuilder);
	}
}