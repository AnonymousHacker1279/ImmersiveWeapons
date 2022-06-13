package tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class ImmersiveWeaponsBlockTagGroups {

	public static final TagKey<Block> BURNED_OAK_LOGS = BlockTags.create(new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"burned_oak_logs"));
}