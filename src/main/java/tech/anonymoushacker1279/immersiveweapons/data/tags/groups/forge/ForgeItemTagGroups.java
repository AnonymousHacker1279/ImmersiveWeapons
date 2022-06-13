package tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeItemTagGroups {

	public static final TagKey<Item> COBALT_ORES = ItemTags.create(new ResourceLocation("forge",
			"ores/cobalt"));
	public static final TagKey<Item> COBALT_INGOTS = ItemTags.create(new ResourceLocation("forge",
			"ingots/cobalt"));
	public static final TagKey<Item> COPPER_INGOTS = ItemTags.create(new ResourceLocation("forge",
			"ingots/copper"));
	public static final TagKey<Item> METAL_INGOTS = ItemTags.create(new ResourceLocation("forge",
			"ingots/metal"));
	public static final TagKey<Item> COBALT_NUGGETS = ItemTags.create(new ResourceLocation("forge",
			"nuggets/cobalt"));
	public static final TagKey<Item> COPPER_NUGGETS = ItemTags.create(new ResourceLocation("forge",
			"nuggets/copper"));
	public static final TagKey<Item> METAL_NUGGETS = ItemTags.create(new ResourceLocation("forge",
			"nuggets/metal"));
	public static final TagKey<Item> SULFUR_DUSTS = ItemTags.create(new ResourceLocation("forge",
			"dusts/sulfur"));
	public static final TagKey<Item> PICKAXES = ItemTags.create(new ResourceLocation("forge",
			"pickaxes"));
}