package tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ForgeBlockTagGroups {

	public static final TagKey<Block> BULLETPROOF_GLASS = BlockTags.create(new ResourceLocation("forge",
			"bulletproof_glass"));
	public static final TagKey<Block> STAINED_GLASS = BlockTags.create(new ResourceLocation("forge",
			"stained_glass"));
	public static final TagKey<Block> COBALT_ORES = BlockTags.create(new ResourceLocation("forge",
			"ores/cobalt"));
	public static final TagKey<Block> SULFUR_ORES = BlockTags.create(new ResourceLocation("forge",
			"ores/sulfur"));
}