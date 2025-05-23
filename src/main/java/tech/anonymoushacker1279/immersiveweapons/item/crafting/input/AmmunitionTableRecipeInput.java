package tech.anonymoushacker1279.immersiveweapons.item.crafting.input;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AmmunitionTableRecipeInput(NonNullList<ItemStack> items) implements RecipeInput {

	@Override
	public ItemStack getItem(int index) {
		return items.get(index);
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}
}