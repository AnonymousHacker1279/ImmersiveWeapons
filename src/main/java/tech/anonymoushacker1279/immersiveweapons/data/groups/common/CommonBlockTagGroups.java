package tech.anonymoushacker1279.immersiveweapons.data.groups.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CommonBlockTagGroups {

	public static final TagKey<Block> BULLETPROOF_GLASS = createBlockTag("bulletproof_glass");
	public static final TagKey<Block> STAINED_GLASS = createBlockTag("stained_glass");
	public static final TagKey<Block> COBALT_ORES = createBlockTag("ores/cobalt");
	public static final TagKey<Block> SULFUR_ORES = createBlockTag("ores/sulfur");
	public static final TagKey<Block> POTASSIUM_NITRATE_ORES = createBlockTag("ores/potassium_nitrate");

	private static TagKey<Block> createBlockTag(String tag) {
		return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", tag));
	}
}