package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags.Items;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.PikeItem;

import java.util.function.Supplier;

public record VanillaTieredItemFamilies(TagKey<Item> material,
                                        TagKey<Item> nugget,
                                        Supplier<? extends GauntletItem> gauntlet,
                                        Supplier<? extends PikeItem> pike,
                                        Supplier<? extends Item> pikeHead,
                                        Supplier<? extends ArrowItem> arrow,
                                        Supplier<? extends Item> musketBall) {

	public static final VanillaTieredItemFamilies WOODEN = new VanillaTieredItemFamilies(
			ItemTags.PLANKS,
			ImmersiveWeaponsItemTagGroups.WOODEN_SHARDS,
			ItemRegistry.WOODEN_GAUNTLET,
			ItemRegistry.WOODEN_PIKE,
			ItemRegistry.WOODEN_PIKE_HEAD,
			ItemRegistry.WOODEN_ARROW,
			ItemRegistry.WOODEN_MUSKET_BALL
	);

	public static final VanillaTieredItemFamilies STONE = new VanillaTieredItemFamilies(
			ItemTags.STONE_TOOL_MATERIALS,
			ImmersiveWeaponsItemTagGroups.STONE_SHARDS,
			ItemRegistry.STONE_GAUNTLET,
			ItemRegistry.STONE_PIKE,
			ItemRegistry.STONE_PIKE_HEAD,
			ItemRegistry.STONE_ARROW,
			ItemRegistry.STONE_MUSKET_BALL
	);

	public static final VanillaTieredItemFamilies IRON = new VanillaTieredItemFamilies(
			Items.INGOTS_IRON,
			Items.NUGGETS_IRON,
			ItemRegistry.IRON_GAUNTLET,
			ItemRegistry.IRON_PIKE,
			ItemRegistry.IRON_PIKE_HEAD,
			ItemRegistry.IRON_ARROW,
			ItemRegistry.IRON_MUSKET_BALL
	);

	public static final VanillaTieredItemFamilies GOLDEN = new VanillaTieredItemFamilies(
			Items.INGOTS_GOLD,
			Items.NUGGETS_GOLD,
			ItemRegistry.GOLDEN_GAUNTLET,
			ItemRegistry.GOLDEN_PIKE,
			ItemRegistry.GOLDEN_PIKE_HEAD,
			ItemRegistry.GOLDEN_ARROW,
			ItemRegistry.GOLDEN_MUSKET_BALL
	);

	public static final VanillaTieredItemFamilies DIAMOND = new VanillaTieredItemFamilies(
			Items.GEMS_DIAMOND,
			ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS,
			ItemRegistry.DIAMOND_GAUNTLET,
			ItemRegistry.DIAMOND_PIKE,
			ItemRegistry.DIAMOND_PIKE_HEAD,
			ItemRegistry.DIAMOND_ARROW,
			ItemRegistry.DIAMOND_MUSKET_BALL
	);

	public static final ImmutableList<VanillaTieredItemFamilies> FAMILIES = ImmutableList.of(WOODEN, STONE, IRON, GOLDEN, DIAMOND);
	public static final ImmutableList<VanillaTieredItemFamilies> FAMILIES_USE_NUGGETS_FOR_PROJECTILES = ImmutableList.of(WOODEN, STONE, DIAMOND);
}