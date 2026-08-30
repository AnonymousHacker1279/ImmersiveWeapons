package tech.anonymoushacker1279.immersiveweapons.data.groups.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CommonBlockTagGroups {

	public static final TagKey<Block> BULLETPROOF_GLASS = createBlockTag("bulletproof_glass");
	public static final TagKey<Block> BULLETPROOF_GLASS_PANES = createBlockTag("bulletproof_glass_panes");
	public static final TagKey<Block> COBALT_ORES = createBlockTag("ores/cobalt");

	private static TagKey<Block> createBlockTag(String tag) {
		return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", tag));
	}
}