package tech.anonymoushacker1279.immersiveweapons.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.menu.TeslaSynthesizerMenu;

public class TeslaSynthesizerFuelSlot extends Slot {

	private final TeslaSynthesizerMenu menu;

	public TeslaSynthesizerFuelSlot(TeslaSynthesizerMenu menu, Container container, int slotIndex, int xPos, int yPos) {
		super(container, slotIndex, xPos, yPos);
		this.menu = menu;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot.
	 *
	 * @param stack the <code>ItemStack</code> to be checked
	 * @return boolean
	 */
	@Override
	public boolean mayPlace(ItemStack stack) {
		return menu.isFuel(stack);
	}
}