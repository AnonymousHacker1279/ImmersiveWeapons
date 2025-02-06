package tech.anonymoushacker1279.immersiveweapons.data.groups.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CommonItemTagGroups {

	public static final TagKey<Item> COBALT_ORES = createItemTag("ores/cobalt");
	public static final TagKey<Item> SULFUR_ORES = createItemTag("ores/sulfur");
	public static final TagKey<Item> POTASSIUM_NITRATE_ORES = createItemTag("ores/potassium_nitrate");
	public static final TagKey<Item> COBALT_INGOTS = createItemTag("ingots/cobalt");
	public static final TagKey<Item> METAL_INGOTS = createItemTag("ingots/metal");
	public static final TagKey<Item> COBALT_NUGGETS = createItemTag("nuggets/cobalt");
	public static final TagKey<Item> COPPER_NUGGETS = createItemTag("nuggets/copper");
	public static final TagKey<Item> METAL_NUGGETS = createItemTag("nuggets/metal");
	public static final TagKey<Item> SULFUR_DUSTS = createItemTag("dusts/sulfur");

	private static TagKey<Item> createItemTag(String tag) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", tag));
	}
}