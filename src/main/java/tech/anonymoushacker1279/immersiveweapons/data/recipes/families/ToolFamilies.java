package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.PikeItem;

import java.util.Collection;
import java.util.function.Supplier;

public record ToolFamilies(TagKey<Item> material,
                           @Nullable TagKey<Item> nugget,
                           TagKey<Item> handle,
                           Supplier<? extends SwordItem> sword,
                           Supplier<? extends PickaxeItem> pickaxe,
                           Supplier<? extends AxeItem> axe,
                           Supplier<? extends ShovelItem> shovel,
                           Supplier<? extends HoeItem> hoe,
                           @Nullable Supplier<? extends GauntletItem> gauntlet,
                           @Nullable Supplier<? extends PikeItem> pike,
                           @Nullable Supplier<? extends Item> pikeHead,
                           @Nullable Supplier<? extends ArrowItem> arrow,
                           @Nullable Supplier<? extends Item> musketBall) {

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
			ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.MOLTEN_SWORD,
			ItemRegistry.MOLTEN_PICKAXE,
			ItemRegistry.MOLTEN_AXE,
			ItemRegistry.MOLTEN_SHOVEL,
			ItemRegistry.MOLTEN_HOE,
			null, null, null, null, null
	);

	public static final ToolFamilies VENTUS = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.VENTUS_SWORD,
			ItemRegistry.VENTUS_PICKAXE,
			ItemRegistry.VENTUS_AXE,
			ItemRegistry.VENTUS_SHOVEL,
			ItemRegistry.VENTUS_HOE,
			null, null, null, null, null
	);

	public static final ToolFamilies TESLA = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.TESLA_INGOTS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.TESLA_SWORD,
			ItemRegistry.TESLA_PICKAXE,
			ItemRegistry.TESLA_AXE,
			ItemRegistry.TESLA_SHOVEL,
			ItemRegistry.TESLA_HOE,
			null, null, null, null, null
	);

	public static final ToolFamilies ASTRAL = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.ASTRAL_INGOTS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.ASTRAL_SWORD,
			ItemRegistry.ASTRAL_PICKAXE,
			ItemRegistry.ASTRAL_AXE,
			ItemRegistry.ASTRAL_SHOVEL,
			ItemRegistry.ASTRAL_HOE,
			null, null, null, null, null
	);

	public static final ToolFamilies STARSTORM = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.STARSTORM_INGOTS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			ItemRegistry.STARSTORM_SWORD,
			ItemRegistry.STARSTORM_PICKAXE,
			ItemRegistry.STARSTORM_AXE,
			ItemRegistry.STARSTORM_SHOVEL,
			ItemRegistry.STARSTORM_HOE,
			null, null, null, null, null
	);

	public static final Collection<ToolFamilies> FAMILIES = ImmutableList.of(COPPER, COBALT, MOLTEN, VENTUS, TESLA, ASTRAL, STARSTORM);
}