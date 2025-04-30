package tech.anonymoushacker1279.immersiveweapons.item.crafting.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AstralCrystalRecipeInput(ItemStack material, ItemStack catalyst) implements RecipeInput {

	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> material;
			case 1 -> catalyst;
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		return material.isEmpty() && catalyst.isEmpty();
	}
}