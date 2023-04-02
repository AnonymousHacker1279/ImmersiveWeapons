package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;

import java.util.Collection;
import java.util.function.Supplier;

public record ToolFamilies(TagKey<Item> material,
                           TagKey<Item> nugget,
                           TagKey<Item> handle,
                           Supplier<? extends SwordItem> sword,
                           Supplier<? extends PickaxeItem> pickaxe,
                           Supplier<? extends AxeItem> axe,
                           Supplier<? extends ShovelItem> shovel,
                           Supplier<? extends HoeItem> hoe,
                           Supplier<? extends GauntletItem> gauntlet,
                           Supplier<? extends PikeItem> pike,
                           Supplier<? extends Item> pikeHead,
                           Supplier<? extends ArrowItem> arrow,
                           Supplier<? extends Item> musketBall) {

	public static final ToolFamilies COPPER = new ToolFamilies(
			Tags.Items.INGOTS_COPPER,
			ForgeItemTagGroups.COPPER_NUGGETS,
			Tags.Items.RODS_WOODEN,
			ItemRegistry.COPPER_SWORD,
			ItemRegistry.COPPER_PICKAXE,
			ItemRegistry.COPPER_AXE,
			ItemRegistry.COPPER_SHOVEL,
			ItemRegistry.COPPER_HOE,
			ItemRegistry.COPPER_GAUNTLET,
			ItemRegistry.COPPER_PIKE,
			ItemRegistry.COPPER_PIKE_HEAD,
			ItemRegistry.COPPER_ARROW,
			ItemRegistry.COPPER_MUSKET_BALL
	);

	public static final ToolFamilies COBALT = new ToolFamilies(
			ForgeItemTagGroups.COBALT_INGOTS,
			ForgeItemTagGroups.COBALT_NUGGETS,
			Tags.Items.RODS_WOODEN,
			ItemRegistry.COBALT_SWORD,
			ItemRegistry.COBALT_PICKAXE,
			ItemRegistry.COBALT_AXE,
			ItemRegistry.COBALT_SHOVEL,
			ItemRegistry.COBALT_HOE,
			ItemRegistry.COBALT_GAUNTLET,
			ItemRegistry.COBALT_PIKE,
			ItemRegistry.COBALT_PIKE_HEAD,
			ItemRegistry.COBALT_ARROW,
			ItemRegistry.COBALT_MUSKET_BALL
	);

	public static final ToolFamilies MOLTEN = new ToolFamilies(
			IWItemTagGroups.MOLTEN_INGOTS,
			IWItemTagGroups.MOLTEN_SHARDS,
			IWItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.MOLTEN_SWORD,
			ItemRegistry.MOLTEN_PICKAXE,
			ItemRegistry.MOLTEN_AXE,
			ItemRegistry.MOLTEN_SHOVEL,
			ItemRegistry.MOLTEN_HOE,
			ItemRegistry.MOLTEN_GAUNTLET,
			ItemRegistry.MOLTEN_PIKE,
			ItemRegistry.MOLTEN_PIKE_HEAD,
			ItemRegistry.MOLTEN_ARROW,
			ItemRegistry.MOLTEN_MUSKET_BALL
	);

	public static final ToolFamilies TESLA = new ToolFamilies(
			IWItemTagGroups.TESLA_INGOTS,
			IWItemTagGroups.TESLA_NUGGETS,
			IWItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.TESLA_SWORD,
			ItemRegistry.TESLA_PICKAXE,
			ItemRegistry.TESLA_AXE,
			ItemRegistry.TESLA_SHOVEL,
			ItemRegistry.TESLA_HOE,
			ItemRegistry.TESLA_GAUNTLET,
			ItemRegistry.TESLA_PIKE,
			ItemRegistry.TESLA_PIKE_HEAD,
			ItemRegistry.TESLA_ARROW,
			ItemRegistry.TESLA_MUSKET_BALL
	);

	public static final ToolFamilies VENTUS = new ToolFamilies(
			IWItemTagGroups.VENTUS_SHARDS,
			IWItemTagGroups.VENTUS_SHARDS,
			IWItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.VENTUS_SWORD,
			ItemRegistry.VENTUS_PICKAXE,
			ItemRegistry.VENTUS_AXE,
			ItemRegistry.VENTUS_SHOVEL,
			ItemRegistry.VENTUS_HOE,
			ItemRegistry.VENTUS_GAUNTLET,
			ItemRegistry.VENTUS_PIKE,
			ItemRegistry.VENTUS_PIKE_HEAD,
			ItemRegistry.VENTUS_ARROW,
			ItemRegistry.VENTUS_MUSKET_BALL
	);

	public static final ToolFamilies ASTRAL = new ToolFamilies(
			IWItemTagGroups.ASTRAL_INGOTS,
			IWItemTagGroups.ASTRAL_NUGGETS,
			IWItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.ASTRAL_SWORD,
			ItemRegistry.ASTRAL_PICKAXE,
			ItemRegistry.ASTRAL_AXE,
			ItemRegistry.ASTRAL_SHOVEL,
			ItemRegistry.ASTRAL_HOE,
			ItemRegistry.ASTRAL_GAUNTLET,
			ItemRegistry.ASTRAL_PIKE,
			ItemRegistry.ASTRAL_PIKE_HEAD,
			ItemRegistry.ASTRAL_ARROW,
			ItemRegistry.ASTRAL_MUSKET_BALL
	);

	public static final ToolFamilies STARSTORM = new ToolFamilies(
			IWItemTagGroups.STARSTORM_INGOTS,
			IWItemTagGroups.STARSTORM_SHARDS,
			IWItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.STARSTORM_SWORD,
			ItemRegistry.STARSTORM_PICKAXE,
			ItemRegistry.STARSTORM_AXE,
			ItemRegistry.STARSTORM_SHOVEL,
			ItemRegistry.STARSTORM_HOE,
			ItemRegistry.STARSTORM_GAUNTLET,
			ItemRegistry.STARSTORM_PIKE,
			ItemRegistry.STARSTORM_PIKE_HEAD,
			ItemRegistry.STARSTORM_ARROW,
			ItemRegistry.STARSTORM_MUSKET_BALL
	);

	public static final Collection<ToolFamilies> FAMILIES = ImmutableList.of(COPPER, COBALT, MOLTEN, VENTUS, TESLA, ASTRAL, STARSTORM);
	public static final ImmutableList<ToolFamilies> FAMILIES_USE_NUGGETS_FOR_PROJECTILES = ImmutableList.of(MOLTEN, VENTUS, STARSTORM);
}