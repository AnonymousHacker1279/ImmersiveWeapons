package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class BiomesAndDimensions {

	public static final ResourceKey<Biome> BATTLEFIELD = create("battlefield");
	public static final ResourceKey<Biome> TILTROS_WASTES = create("tiltros_wastes");
	public static final ResourceKey<Biome> STARLIGHT_PLAINS = create("starlight_plains");
	public static final ResourceKey<Biome> DEADMANS_DESERT = create("deadmans_desert");

	public static final ResourceKey<Level> TILTROS = ResourceKey.create(Registries.DIMENSION,
			new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros"));

	private static ResourceKey<Biome> create(String name) {
		return ResourceKey.create(Registries.BIOME, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}
}