package tech.anonymoushacker1279.immersiveweapons.item.crafting.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AmmunitionForgeRecipeInput(ItemStack material, ItemStack mold) implements RecipeInput {

	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> material;
			case 1 -> mold;
			default -> throw new IllegalArgumentException("No item for index " + index);
		};
	}

	@Override
	public int size() {
		return 2;
	}
}
