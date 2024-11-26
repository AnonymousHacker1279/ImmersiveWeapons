package tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWBlockTagGroups {

	public static final TagKey<Block> BURNED_OAK_LOGS = createBlockTag("burned_oak_logs");
	public static final TagKey<Block> STARDUST_LOGS = createBlockTag("stardust_logs");
	public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = createBlockTag("incorrect_for_copper_tool");
	public static final TagKey<Block> INCORRECT_FOR_COBALT_TOOL = createBlockTag("incorrect_for_cobalt_tool");
	public static final TagKey<Block> INCORRECT_FOR_MOLTEN_TOOL = createBlockTag("incorrect_for_molten_tool");
	public static final TagKey<Block> INCORRECT_FOR_TESLA_TOOL = createBlockTag("incorrect_for_tesla_tool");
	public static final TagKey<Block> INCORRECT_FOR_VENTUS_TOOL = createBlockTag("incorrect_for_ventus_tool");
	public static final TagKey<Block> INCORRECT_FOR_STARSTORM_TOOL = createBlockTag("incorrect_for_starstorm_tool");
	public static final TagKey<Block> INCORRECT_FOR_ASTRAL_TOOL = createBlockTag("incorrect_for_astral_tool");
	public static final TagKey<Block> INCORRECT_FOR_VOID_TOOL = createBlockTag("incorrect_for_void_tool");
	public static final TagKey<Block> INCORRECT_FOR_HANSIUM_TOOL = createBlockTag("incorrect_for_hansium_tool");
	public static final TagKey<Block> NEEDS_ASTRAL_STARSTORM_TOOL = createBlockTag("needs_astral_starstorm_tool");

	private static TagKey<Block> createBlockTag(String tag) {
		return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, tag));
	}
}