package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags.Items;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
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
			DeferredRegistryHandler.WOODEN_GAUNTLET,
			DeferredRegistryHandler.WOODEN_PIKE,
			DeferredRegistryHandler.WOODEN_PIKE_HEAD,
			DeferredRegistryHandler.WOODEN_ARROW,
			DeferredRegistryHandler.WOODEN_MUSKET_BALL
	);

	public static final VanillaTieredItemFamilies STONE = new VanillaTieredItemFamilies(
			ItemTags.STONE_TOOL_MATERIALS,
			ImmersiveWeaponsItemTagGroups.STONE_SHARDS,
			DeferredRegistryHandler.STONE_GAUNTLET,
			DeferredRegistryHandler.STONE_PIKE,
			DeferredRegistryHandler.STONE_PIKE_HEAD,
			DeferredRegistryHandler.STONE_ARROW,
			DeferredRegistryHandler.STONE_MUSKET_BALL
	);

	public static final VanillaTieredItemFamilies IRON = new VanillaTieredItemFamilies(
			Items.INGOTS_IRON,
			Items.NUGGETS_IRON,
			DeferredRegistryHandler.IRON_GAUNTLET,
			DeferredRegistryHandler.IRON_PIKE,
			DeferredRegistryHandler.IRON_PIKE_HEAD,
			DeferredRegistryHandler.IRON_ARROW,
			DeferredRegistryHandler.IRON_MUSKET_BALL
	);

	public static final VanillaTieredItemFamilies GOLDEN = new VanillaTieredItemFamilies(
			Items.INGOTS_GOLD,
			Items.NUGGETS_GOLD,
			DeferredRegistryHandler.GOLDEN_GAUNTLET,
			DeferredRegistryHandler.GOLDEN_PIKE,
			DeferredRegistryHandler.GOLDEN_PIKE_HEAD,
			DeferredRegistryHandler.GOLDEN_ARROW,
			DeferredRegistryHandler.GOLDEN_MUSKET_BALL
	);

	public static final VanillaTieredItemFamilies DIAMOND = new VanillaTieredItemFamilies(
			Items.GEMS_DIAMOND,
			ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS,
			DeferredRegistryHandler.DIAMOND_GAUNTLET,
			DeferredRegistryHandler.DIAMOND_PIKE,
			DeferredRegistryHandler.DIAMOND_PIKE_HEAD,
			DeferredRegistryHandler.DIAMOND_ARROW,
			DeferredRegistryHandler.DIAMOND_MUSKET_BALL
	);

	public static final ImmutableList<VanillaTieredItemFamilies> FAMILIES = ImmutableList.of(WOODEN, STONE, IRON, GOLDEN, DIAMOND);
}