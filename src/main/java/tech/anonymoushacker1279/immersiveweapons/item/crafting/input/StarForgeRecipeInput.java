package tech.anonymoushacker1279.immersiveweapons.item.crafting.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.Nullable;

public record StarForgeRecipeInput(ItemStack primaryMaterial,
                                   @Nullable ItemStack secondaryMaterial) implements RecipeInput {

	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> primaryMaterial;
			case 1 -> secondaryMaterial == null ? ItemStack.EMPTY : secondaryMaterial;
			default -> ItemStack.EMPTY;
		};
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		return primaryMaterial.isEmpty() && (secondaryMaterial == null || secondaryMaterial.isEmpty());
	}
}