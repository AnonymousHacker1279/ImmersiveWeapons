package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.Objects;

public interface DataGenUtils {

	default ResourceLocation getItemLocation(Item item) {
		return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item))
				.withPrefix("item/");
	}

	default ResourceLocation getBlockItemLocation(Item item) {
		return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item))
				.withPrefix("block/");
	}

	default ResourceLocation getBowPullingPredicateLocation() {
		return ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "pulling");
	}

	default ResourceLocation getBowPullingLocation(Item item, int stage) {
		return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item))
				.withPrefix("item/")
				.withSuffix("_pulling_" + stage);
	}

	default ResourceLocation getArmorTrimLocation(Item item, ResourceKey<TrimMaterial> material) {
		return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item))
				.withPrefix("item/")
				.withSuffix("_" + material.location().getPath() + "_trim");
	}

	static ResourceLocation getItemFromNuggetsLocation(Item item) {
		return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item))
				.withSuffix("_from_nuggets");
	}
}