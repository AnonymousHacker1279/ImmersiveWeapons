package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags.Items;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.function.Supplier;

public record ArmorFamilies(TagKey<Item> material,
                            Supplier<? extends ArmorItem> helmet,
                            Supplier<? extends ArmorItem> chestplate,
                            Supplier<? extends ArmorItem> leggings,
                            Supplier<? extends ArmorItem> boots) {

	public static final ArmorFamilies COPPER = new ArmorFamilies(
			Items.INGOTS_COPPER,
			DeferredRegistryHandler.COPPER_HELMET,
			DeferredRegistryHandler.COPPER_CHESTPLATE,
			DeferredRegistryHandler.COPPER_LEGGINGS,
			DeferredRegistryHandler.COPPER_BOOTS
	);

	public static final ArmorFamilies COBALT = new ArmorFamilies(
			ForgeItemTagGroups.COBALT_INGOTS,
			DeferredRegistryHandler.COBALT_HELMET,
			DeferredRegistryHandler.COBALT_CHESTPLATE,
			DeferredRegistryHandler.COBALT_LEGGINGS,
			DeferredRegistryHandler.COBALT_BOOTS
	);

	public static final ArmorFamilies MOLTEN = new ArmorFamilies(
			ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS,
			DeferredRegistryHandler.MOLTEN_HELMET,
			DeferredRegistryHandler.MOLTEN_CHESTPLATE,
			DeferredRegistryHandler.MOLTEN_LEGGINGS,
			DeferredRegistryHandler.MOLTEN_BOOTS
	);

	public static final ArmorFamilies VENTUS = new ArmorFamilies(
			ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS,
			DeferredRegistryHandler.VENTUS_HELMET,
			DeferredRegistryHandler.VENTUS_CHESTPLATE,
			DeferredRegistryHandler.VENTUS_LEGGINGS,
			DeferredRegistryHandler.VENTUS_BOOTS
	);

	public static final ArmorFamilies TESLA = new ArmorFamilies(
			ImmersiveWeaponsItemTagGroups.TESLA_INGOTS,
			DeferredRegistryHandler.TESLA_HELMET,
			DeferredRegistryHandler.TESLA_CHESTPLATE,
			DeferredRegistryHandler.TESLA_LEGGINGS,
			DeferredRegistryHandler.TESLA_BOOTS
	);

	public static final ArmorFamilies ASTRAL = new ArmorFamilies(
			ImmersiveWeaponsItemTagGroups.ASTRAL_INGOTS,
			DeferredRegistryHandler.ASTRAL_HELMET,
			DeferredRegistryHandler.ASTRAL_CHESTPLATE,
			DeferredRegistryHandler.ASTRAL_LEGGINGS,
			DeferredRegistryHandler.ASTRAL_BOOTS
	);

	public static final ImmutableList<ArmorFamilies> FAMILIES = ImmutableList.of(COPPER, COBALT, MOLTEN, VENTUS, TESLA, ASTRAL);
}