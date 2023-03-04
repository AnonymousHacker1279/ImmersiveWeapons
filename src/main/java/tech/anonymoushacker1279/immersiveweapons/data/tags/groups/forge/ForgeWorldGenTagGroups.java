package tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ForgeWorldGenTagGroups {

	// For some reason Mojang doesn't have tags for plains biomes...
	public static final TagKey<Biome> IS_PLAINS = createBiomeTag("is_plains");
	public static final TagKey<Biome> IS_WET_CAVE = createBiomeTag("is_wet_cave");

	/**
	 * Helper method for creating a biome tag for containing biomes.
	 *
	 * @param tag a string to be used for the tag
	 */
	private static TagKey<Biome> createBiomeTag(String tag) {
		return createBiomeTagInternal("forge" + ":" + tag);
	}

	private static TagKey<Biome> createBiomeTagInternal(String pName) {
		return TagKey.create(Registries.BIOME, new ResourceLocation(pName));
	}
}