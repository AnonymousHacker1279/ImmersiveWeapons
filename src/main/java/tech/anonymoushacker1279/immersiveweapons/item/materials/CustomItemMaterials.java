package tech.anonymoushacker1279.immersiveweapons.item.materials;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.TierSortingRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.List;

public class CustomItemMaterials {
	public static final Tier COPPER = TierSortingRegistry.registerTier(
			new SimpleTier(2, 180, 5.9F, 1.0f, 12, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(Items.COPPER_INGOT)),
			new ResourceLocation("immersiveweapons:copper"),
			List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
	public static final Tier COBALT = TierSortingRegistry.registerTier(
			new SimpleTier(2, 300, 6.2F, 2.0f, 15, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(Items.COPPER_INGOT)),
			new ResourceLocation("immersiveweapons:cobalt"),
			List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
	public static final Tier MOLTEN = TierSortingRegistry.registerTier(
			new SimpleTier(4, 1900, 10.2F, 5.0F, 17, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.MOLTEN_INGOT.get())),
			new ResourceLocation("immersiveweapons:molten"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier TESLA = TierSortingRegistry.registerTier(
			new SimpleTier(4, 2100, 18.0F, 6.0F, 20, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.TESLA_INGOT.get())),
			new ResourceLocation("immersiveweapons:tesla"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier VENTUS = TierSortingRegistry.registerTier(
			new SimpleTier(4, 1900, 12.6F, 5.0F, 16, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.VENTUS_SHARD.get())),
			new ResourceLocation("immersiveweapons:ventus"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier ASTRAL = TierSortingRegistry.registerTier(
			new SimpleTier(4, 600, 24.0F, 4.0F, 22, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.ASTRAL_INGOT.get())),
			new ResourceLocation("immersiveweapons:astral"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier STARSTORM = TierSortingRegistry.registerTier(
			new SimpleTier(4, 1800, 14.0F, 7.0F, 20, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.STARSTORM_INGOT.get())),
			new ResourceLocation("immersiveweapons:starstorm"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier HANSIUM = TierSortingRegistry.registerTier(
			new SimpleTier(5, 3000, 24.0F, 16.0F, 22, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.HANSIUM_INGOT.get())),
			new ResourceLocation("immersiveweapons:hansium"),
			List.of(MOLTEN, TESLA, VENTUS, ASTRAL, STARSTORM), List.of());
}