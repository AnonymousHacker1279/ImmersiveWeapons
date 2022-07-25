package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.common.Tags;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.Collection;
import java.util.function.Supplier;

public record ToolFamilies(TagKey<Item> material,
                           TagKey<Item> handle,
                           Supplier<? extends SwordItem> sword,
                           Supplier<? extends PickaxeItem> pickaxe,
                           Supplier<? extends AxeItem> axe,
                           Supplier<? extends ShovelItem> shovel,
                           Supplier<? extends HoeItem> hoe) {

	public static final ToolFamilies COPPER = new ToolFamilies(
			Tags.Items.INGOTS_COPPER,
			Tags.Items.RODS_WOODEN,
			DeferredRegistryHandler.COPPER_SWORD,
			DeferredRegistryHandler.COPPER_PICKAXE,
			DeferredRegistryHandler.COPPER_AXE,
			DeferredRegistryHandler.COPPER_SHOVEL,
			DeferredRegistryHandler.COPPER_HOE
	);

	public static final ToolFamilies COBALT = new ToolFamilies(
			ForgeItemTagGroups.COBALT_INGOTS,
			Tags.Items.RODS_WOODEN,
			DeferredRegistryHandler.COBALT_SWORD,
			DeferredRegistryHandler.COBALT_PICKAXE,
			DeferredRegistryHandler.COBALT_AXE,
			DeferredRegistryHandler.COBALT_SHOVEL,
			DeferredRegistryHandler.COBALT_HOE
	);

	public static final ToolFamilies MOLTEN = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			DeferredRegistryHandler.MOLTEN_SWORD,
			DeferredRegistryHandler.MOLTEN_PICKAXE,
			DeferredRegistryHandler.MOLTEN_AXE,
			DeferredRegistryHandler.MOLTEN_SHOVEL,
			DeferredRegistryHandler.MOLTEN_HOE
	);

	public static final ToolFamilies VENTUS = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			DeferredRegistryHandler.VENTUS_SWORD,
			DeferredRegistryHandler.VENTUS_PICKAXE,
			DeferredRegistryHandler.VENTUS_AXE,
			DeferredRegistryHandler.VENTUS_SHOVEL,
			DeferredRegistryHandler.VENTUS_HOE
	);

	public static final ToolFamilies TESLA = new ToolFamilies(
			ImmersiveWeaponsItemTagGroups.TESLA_INGOTS,
			ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS,
			DeferredRegistryHandler.TESLA_SWORD,
			DeferredRegistryHandler.TESLA_PICKAXE,
			DeferredRegistryHandler.TESLA_AXE,
			DeferredRegistryHandler.TESLA_SHOVEL,
			DeferredRegistryHandler.TESLA_HOE
	);

	public static final Collection<ToolFamilies> FAMILIES = ImmutableList.of(COPPER, COBALT, MOLTEN, VENTUS, TESLA);
}