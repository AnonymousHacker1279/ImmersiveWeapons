package tech.anonymoushacker1279.immersiveweapons.item.materials;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.List;

public class CustomItemMaterials {
	public static final Tier COPPER = TierSortingRegistry.registerTier(
			new ForgeTier(2, 180, 5.9F, 2.0f, 12, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(Items.COPPER_INGOT)),
			new ResourceLocation("immersiveweapons:copper"),
			List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
	public static final Tier COBALT = TierSortingRegistry.registerTier(
			new ForgeTier(2, 300, 6.2F, 3.0f, 15, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(Items.COPPER_INGOT)),
			new ResourceLocation("immersiveweapons:cobalt"),
			List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
	public static final Tier MOLTEN = TierSortingRegistry.registerTier(
			new ForgeTier(4, 1900, 10.2F, 3.0F, 17, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.MOLTEN_INGOT.get())),
			new ResourceLocation("immersiveweapons:molten"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier TESLA = TierSortingRegistry.registerTier(
			new ForgeTier(4, 2100, 18.0F, 3.0F, 20, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.TESLA_INGOT.get())),
			new ResourceLocation("immersiveweapons:tesla"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier VENTUS = TierSortingRegistry.registerTier(
			new ForgeTier(4, 1900, 12.6F, 3.0F, 16, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.VENTUS_SHARD.get())),
			new ResourceLocation("immersiveweapons:ventus"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier ASTRAL = TierSortingRegistry.registerTier(
			new ForgeTier(4, 600, 24.0F, 3.0F, 22, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.ASTRAL_INGOT.get())),
			new ResourceLocation("immersiveweapons:astral"),
			List.of(Tiers.NETHERITE), List.of());
	public static final Tier STARSTORM = TierSortingRegistry.registerTier(
			new ForgeTier(4, 1800, 14.0F, 5.0F, 20, BlockTags.create(new ResourceLocation("")),
					() -> Ingredient.of(ItemRegistry.ASTRAL_INGOT.get())),
			new ResourceLocation("immersiveweapons:starstorm"),
			List.of(Tiers.NETHERITE), List.of());
}