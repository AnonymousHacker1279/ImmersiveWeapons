package tech.anonymoushacker1279.immersiveweapons.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import tech.anonymoushacker1279.immersiveweapons.menu.TeslaSynthesizerMenu;

public class TeslaSynthesizerMaterialSlot extends Slot {

	private final TeslaSynthesizerMenu menu;
	private final RecipeManager recipeManager;

	public TeslaSynthesizerMaterialSlot(TeslaSynthesizerMenu menu, RecipeManager recipeManager, Container container, int slotIndex, int xPosition, int yPosition) {
		super(container, slotIndex, xPosition, yPosition);
		this.menu = menu;
		this.recipeManager = recipeManager;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		// Check if it is a valid ingredient for the specified slot
		return menu.isIngredient(recipeManager, stack, getSlotIndex());
	}
}