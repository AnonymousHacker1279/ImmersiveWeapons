package com.anonymoushacker1279.immersiveweapons.util;

// TODO: Rework when Forge API updates
/*
public class ConfiguredSurfaceBuilders {

	public static final ResourceKey<ConfiguredSurfaceBuilder<?>> BATTLEFIELD = key("battlefield");
	public static final ResourceKey<ConfiguredSurfaceBuilder<?>> TILTROS = key("tiltros");

	*/
/**
	 * Register surface builders.
	 *//*

	public static void register() {
		register(BATTLEFIELD,
				new ConfiguredSurfaceBuilder<>(
						SurfaceBuilder.DEFAULT,
						new SurfaceBuilderBaseConfiguration(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.COARSE_DIRT.defaultBlockState(), Blocks.STONE.defaultBlockState())
				)
		);
		register(TILTROS,
				new ConfiguredSurfaceBuilder<>(
						SurfaceBuilder.NOPE,
						new SurfaceBuilderBaseConfiguration(Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState())
				)
		);
	}

	*/
/**
	 * Register a configured surface builder.
	 *
	 * @param key                      the <code>ResourceKey</code>, must extend ConfiguredSurfaceBuilder
	 * @param configuredSurfaceBuilder the <code>ConfiguredSurfaceBuilder</code> instance
	 *//*

	private static void register(ResourceKey<ConfiguredSurfaceBuilder<?>> key, ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder) {
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, key.location(), configuredSurfaceBuilder);
	}

	*/
/**
	 * Create resource keys.
	 *
	 * @param name the name
	 * @return ResourceKey extending ConfiguredSurfaceBuilder
	 *//*

	private static ResourceKey<ConfiguredSurfaceBuilder<?>> key(String name) {
		return ResourceKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}
}*/