package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
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
			DeferredRegistryHandler.COPPER_SWORD,
			DeferredRegistryHandler.COPPER_PICKAXE,
			DeferredRegistryHandler.COPPER_AXE,
			DeferredRegistryHandler.COPPER_SHOVEL,
			DeferredRegistryHandler.COPPER_HOE,
			DeferredRegistryHandler.COPPER_GAUNTLET,
			DeferredRegistryHandler.COPPER_PIKE,
			DeferredRegistryHandler.COPPER_PIKE_HEAD,
			DeferredRegistryHandler.COPPER_ARROW,
			DeferredRegistryHandler.COPPER_MUSKET_BALL
	);

	public static final ToolFamilies COBALT = new ToolFamilies(
			ForgeItemTagGroups.COBALT_INGOTS,
			ForgeItemTagGroups.COBALT_NUGGETS,
			Tags.Items.RODS_WOODEN,
			DeferredRegistryHandler.COBALT_SWORD,
			DeferredRegistryHandler.COBALT_PICKAXE,
			DeferredRegistryHandler.COBALT_AXE,
			DeferredRegistryHandler.COBALT_SHOVEL,
			DeferredRegistryHandler.COBALT_HOE,
			DeferredRegistryHandler.COBALT_GAUNTLET,
			DeferredRegistryHandler.COBALT_PIKE,
			DeferredRegistryHandler.COBALT_PIKE_HEAD,
			DeferredRegistryHandler.COBALT_ARROW,
			DeferredRegistryHandler.COBALT_MUSKET_BALL
	);

	public static final ToolFamilies MOLTEN = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			DeferredRegistryHandler.MOLTEN_SWORD,
			DeferredRegistryHandler.MOLTEN_PICKAXE,
			DeferredRegistryHandler.MOLTEN_AXE,
			DeferredRegistryHandler.MOLTEN_SHOVEL,
			DeferredRegistryHandler.MOLTEN_HOE,
			null, null, null, null, null
	);

	public static final ToolFamilies VENTUS = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			DeferredRegistryHandler.VENTUS_SWORD,
			DeferredRegistryHandler.VENTUS_PICKAXE,
			DeferredRegistryHandler.VENTUS_AXE,
			DeferredRegistryHandler.VENTUS_SHOVEL,
			DeferredRegistryHandler.VENTUS_HOE,
			null, null, null, null, null
	);

	public static final ToolFamilies TESLA = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.TESLA_INGOTS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			DeferredRegistryHandler.TESLA_SWORD,
			DeferredRegistryHandler.TESLA_PICKAXE,
			DeferredRegistryHandler.TESLA_AXE,
			DeferredRegistryHandler.TESLA_SHOVEL,
			DeferredRegistryHandler.TESLA_HOE,
			null, null, null, null, null
	);

	public static final ToolFamilies ASTRAL = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.ASTRAL_INGOTS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			DeferredRegistryHandler.ASTRAL_SWORD,
			DeferredRegistryHandler.ASTRAL_PICKAXE,
			DeferredRegistryHandler.ASTRAL_AXE,
			DeferredRegistryHandler.ASTRAL_SHOVEL,
			DeferredRegistryHandler.ASTRAL_HOE,
			null, null, null, null, null
	);

	public static final ToolFamilies STARSTORM = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.STARSTORM_INGOTS,
			null,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			DeferredRegistryHandler.STARSTORM_SWORD,
			DeferredRegistryHandler.STARSTORM_PICKAXE,
			DeferredRegistryHandler.STARSTORM_AXE,
			DeferredRegistryHandler.STARSTORM_SHOVEL,
			DeferredRegistryHandler.STARSTORM_HOE,
			null, null, null, null, null
	);

	public static final Collection<ToolFamilies> FAMILIES = ImmutableList.of(COPPER, COBALT, MOLTEN, VENTUS, TESLA, ASTRAL, STARSTORM);
}