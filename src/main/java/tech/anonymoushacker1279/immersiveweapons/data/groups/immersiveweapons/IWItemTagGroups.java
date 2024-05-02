package tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWItemTagGroups {

	public static final TagKey<Item> BURNED_OAK_LOGS = createItemTag("burned_oak_logs");
	public static final TagKey<Item> STARDUST_LOGS = createItemTag("stardust_logs");
	public static final TagKey<Item> FLARES = createItemTag("projectiles/flares");
	public static final TagKey<Item> MUSKET_BALLS = createItemTag("projectiles/musket_balls");
	public static final TagKey<Item> CANNONBALLS = createItemTag("projectiles/cannonballs");
	public static final TagKey<Item> MOLTEN_INGOTS = createItemTag("ingots/molten");
	public static final TagKey<Item> ELECTRIC_INGOTS = createItemTag("ingots/electric");
	public static final TagKey<Item> TESLA_INGOTS = createItemTag("ingots/tesla");
	public static final TagKey<Item> TESLA_NUGGETS = createItemTag("nuggets/tesla");
	public static final TagKey<Item> SHARDS = createItemTag("shards/shards");
	public static final TagKey<Item> MOLTEN_SHARDS = createItemTag("shards/molten");
	public static final TagKey<Item> VENTUS_SHARDS = createItemTag("shards/ventus");
	public static final TagKey<Item> DIAMOND_SHARDS = createItemTag("shards/diamond");
	public static final TagKey<Item> STONE_SHARDS = createItemTag("shards/stone");
	public static final TagKey<Item> WOODEN_SHARDS = createItemTag("shards/wood");
	public static final TagKey<Item> OBSIDIAN_RODS = createItemTag("rods/obsidian");
	public static final TagKey<Item> ASTRAL_INGOTS = createItemTag("ingots/astral");
	public static final TagKey<Item> ASTRAL_NUGGETS = createItemTag("nuggets/astral");
	public static final TagKey<Item> STARSTORM_INGOTS = createItemTag("ingots/starstorm");
	public static final TagKey<Item> STARSTORM_SHARDS = createItemTag("shards/starstorm");
	public static final TagKey<Item> ACCESSORIES = createItemTag("accessories");
	public static final TagKey<Item> SMOKE_GRENADES = createItemTag("smoke_grenades");
	public static final TagKey<Item> MOLTEN_TOOLS = createItemTag("tools/molten");
	public static final TagKey<Item> TESLA_TOOLS = createItemTag("tools/tesla");
	public static final TagKey<Item> VENTUS_TOOLS = createItemTag("tools/ventus");
	public static final TagKey<Item> ASTRAL_TOOLS = createItemTag("tools/astral");
	public static final TagKey<Item> STARSTORM_TOOLS = createItemTag("tools/starstorm");
	public static final TagKey<Item> PIKES = createItemTag("weapons/pikes");
	public static final TagKey<Item> GAUNTLETS = createItemTag("weapons/gauntlets");
	public static final TagKey<Item> COMMANDER_PEDESTAL_AUGMENTS = createItemTag("commander_pedestal_augments");
	public static final TagKey<Item> FIREARMS = createItemTag("weapons/firearms");
	public static final TagKey<Item> RANGED_WEAPONS = createItemTag("weapons/ranged_weapons");
	public static final TagKey<Item> WEAPONS_AND_TOOLS = createItemTag("weapons_and_tools");
	public static final TagKey<Item> METEOR_STAFFS = createItemTag("weapons/meteor_staffs");
	public static final TagKey<Item> CURSED_SIGHT_STAFFS = createItemTag("weapons/cursed_sight_staffs");
	public static final TagKey<Item> MOLTEN_ARMOR = createItemTag("armor/molten");
	public static final TagKey<Item> TESLA_ARMOR = createItemTag("armor/tesla");
	public static final TagKey<Item> VENTUS_ARMOR = createItemTag("armor/ventus");
	public static final TagKey<Item> ASTRAL_ARMOR = createItemTag("armor/astral");
	public static final TagKey<Item> STARSTORM_ARMOR = createItemTag("armor/starstorm");
	public static final TagKey<Item> PADDED_LEATHER = createItemTag("armor/padded_leather");

	private static TagKey<Item> createItemTag(String tag) {
		return TagKey.create(Registries.ITEM, new ResourceLocation(ImmersiveWeapons.MOD_ID, tag));
	}
}