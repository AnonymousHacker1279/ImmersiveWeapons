package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.function.Supplier;

public record ArmorFamilies(TagKey<Item> material,
                            Supplier<? extends Item> helmet,
                            Supplier<? extends Item> chestplate,
                            Supplier<? extends Item> leggings,
                            Supplier<? extends Item> boots,
                            @Nullable Supplier<? extends Item> smithingTemplateItem,
                            @Nullable ImmutableList<Item> smithingBaseUpgrades) {

	private static final ImmutableList<Item> NETHERITE_ARMOR = ImmutableList.of(Items.NETHERITE_HELMET,
			Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS);

	public static final ArmorFamilies COPPER = new ArmorFamilies(
			Tags.Items.INGOTS_COPPER,
			ItemRegistry.COPPER_HELMET,
			ItemRegistry.COPPER_CHESTPLATE,
			ItemRegistry.COPPER_LEGGINGS,
			ItemRegistry.COPPER_BOOTS,
			null,
			null
	);

	public static final ArmorFamilies COBALT = new ArmorFamilies(
			CommonItemTagGroups.COBALT_INGOTS,
			ItemRegistry.COBALT_HELMET,
			ItemRegistry.COBALT_CHESTPLATE,
			ItemRegistry.COBALT_LEGGINGS,
			ItemRegistry.COBALT_BOOTS,
			null,
			null
	);

	public static final ArmorFamilies MOLTEN = new ArmorFamilies(
			IWItemTagGroups.MOLTEN_INGOTS,
			ItemRegistry.MOLTEN_HELMET,
			ItemRegistry.MOLTEN_CHESTPLATE,
			ItemRegistry.MOLTEN_LEGGINGS,
			ItemRegistry.MOLTEN_BOOTS,
			ItemRegistry.MOLTEN_SMITHING_TEMPLATE,
			NETHERITE_ARMOR
	);

	public static final ArmorFamilies VENTUS = new ArmorFamilies(
			IWItemTagGroups.VENTUS_SHARDS,
			ItemRegistry.VENTUS_HELMET,
			ItemRegistry.VENTUS_CHESTPLATE,
			ItemRegistry.VENTUS_LEGGINGS,
			ItemRegistry.VENTUS_BOOTS,
			null,
			null
	);

	public static final ArmorFamilies TESLA = new ArmorFamilies(
			IWItemTagGroups.TESLA_INGOTS,
			ItemRegistry.TESLA_HELMET,
			ItemRegistry.TESLA_CHESTPLATE,
			ItemRegistry.TESLA_LEGGINGS,
			ItemRegistry.TESLA_BOOTS,
			null,
			null
	);

	public static final ImmutableList<ArmorFamilies> FAMILIES = ImmutableList.of(COPPER, COBALT, MOLTEN, VENTUS, TESLA);
}