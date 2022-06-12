package com.anonymoushacker1279.immersiveweapons.data.tags.groups.forge;

import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ForgeWorldGenTagGroups {

	// For some reason Mojang doesn't have tags for plains biomes...
	public static final TagKey<Biome> IS_PLAINS = BiomeTags.create("forge:is_plains");
}