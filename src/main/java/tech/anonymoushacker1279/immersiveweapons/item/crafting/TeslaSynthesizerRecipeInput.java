package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record TeslaSynthesizerRecipeInput(ItemStack material1, ItemStack material2,
                                          ItemStack material3) implements RecipeInput {

	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> material1;
			case 1 -> material2;
			case 2 -> material3;
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	public int size() {
		return 3;
	}
}