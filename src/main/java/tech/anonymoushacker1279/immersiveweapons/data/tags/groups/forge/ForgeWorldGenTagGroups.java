package tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class ForgeWorldGenTagGroups {

	// For some reason Mojang doesn't have tags for plains biomes...
	public static final TagKey<Biome> IS_PLAINS = GeneralUtilities.createBiomeTag("forge", "is_plains");
	public static final TagKey<Biome> IS_WET_CAVE = GeneralUtilities.createBiomeTag("forge", "is_wet_cave");

}