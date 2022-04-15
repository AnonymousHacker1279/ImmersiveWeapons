package com.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class BiomesAndDimensions {

	public static final ResourceKey<Biome> BATTLEFIELD = register("battlefield");

	public static final ResourceKey<Level> TILTROS = ResourceKey.create(Registry.DIMENSION_REGISTRY,
			new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros"));

	private static ResourceKey<Biome> register(String name) {
		return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}
}