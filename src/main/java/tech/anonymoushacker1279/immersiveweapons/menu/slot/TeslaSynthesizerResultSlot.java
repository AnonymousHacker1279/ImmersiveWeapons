package tech.anonymoushacker1279.immersiveweapons.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tech.anonymoushacker1279.immersiveweapons.api.events.TeslaSynthesizerCraftEvent;

public class TeslaSynthesizerResultSlot extends Slot {

	/**
	 * Constructor for TeslaSynthesizerResultSlot.
	 *
	 * @param container the <code>Container</code> object
	 * @param slotIndex the slot index
	 * @param xPosition the X position of the slot
	 * @param yPosition the Y position of the slot
	 */
	public TeslaSynthesizerResultSlot(Container container, int slotIndex, int xPosition, int yPosition) {
		super(container, slotIndex, xPosition, yPosition);
	}

	/**
	 * Check if the stack is allowed to be placed in this slot.
	 *
	 * @param stack the <code>ItemStack</code> to be checked
	 * @return boolean
	 */
	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	/**
	 * Runs when the stack is taken from the object.
	 *
	 * @param player the <code>Player</code> instance
	 * @param stack  the <code>ItemStack</code> being taken
	 */
	@Override
	public void onTake(Player player, ItemStack stack) {
		checkTakeAchievements(stack);
		MinecraftForge.EVENT_BUS.post(new TeslaSynthesizerCraftEvent(player, stack));
		super.onTake(player, stack);
	}
}