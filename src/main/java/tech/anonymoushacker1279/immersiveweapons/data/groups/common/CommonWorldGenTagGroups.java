package tech.anonymoushacker1279.immersiveweapons.data.groups.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class CommonWorldGenTagGroups {

	public static final TagKey<Biome> IS_WET_CAVE = createBiomeTag("is_wet_cave");
	public static final TagKey<Biome> IS_LUSH_CAVE = createBiomeTag("is_lush_cave");

	/**
	 * Helper method for creating a biome tag for containing biomes.
	 *
	 * @param tag a string to be used for the tag
	 */
	private static TagKey<Biome> createBiomeTag(String tag) {
		return TagKey.create(Registries.BIOME, new ResourceLocation("c", tag));
	}
}