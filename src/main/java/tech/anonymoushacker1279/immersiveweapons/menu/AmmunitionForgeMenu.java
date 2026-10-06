package tech.anonymoushacker1279.immersiveweapons.menu;

import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AmmunitionForgeBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.MenuTypeRegistry;

import static tech.anonymoushacker1279.immersiveweapons.blockentity.AmmunitionForgeBlockEntity.*;

public class AmmunitionForgeMenu extends AbstractContainerMenu {

	private static final int INVENTORY_START = 4;
	private static final int INVENTORY_END = 31;
	private static final int HOTBAR_START = 31;
	private static final int HOTBAR_END = 40;

	private final Container container;
	private final ContainerData containerData;

	public AmmunitionForgeMenu(int containerID, Inventory inventory) {
		this(containerID, inventory, new SimpleContainer(4), new SimpleContainerData(DATA_COUNT));
	}

	public AmmunitionForgeMenu(int containerID, Inventory inventory, Container container, ContainerData containerData) {
		super(MenuTypeRegistry.AMMUNITION_FORGE_MENU.get(), containerID);
		this.container = container;
		this.containerData = containerData;

		// Material, mold, fuel, and result slots
		addSlot(new Slot(container, SLOT_MATERIAL, 44, 17));
		addSlot(new Slot(container, SLOT_MOLD, 68, 17) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return isMold(stack);
			}
		});
		addSlot(new Slot(container, SLOT_FUEL, 56, 53) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return isFuel(stack);
			}
		});
		addSlot(new Slot(container, SLOT_RESULT, 116, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		// Player inventory slots
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// Player hotbar slots
		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}

		addDataSlots(containerData);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);

		if (slot.hasItem()) {
			ItemStack oldStack = slot.getItem();
			itemStack = oldStack.copy();

			if (index == SLOT_RESULT) {
				if (!moveItemStackTo(oldStack, INVENTORY_START, HOTBAR_END, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(oldStack, itemStack);
			} else if (index >= INVENTORY_START) {
				// From the player's inventory, pick the matching forge slot
				boolean moved;
				if (isFuel(oldStack)) {
					moved = moveItemStackTo(oldStack, SLOT_FUEL, SLOT_FUEL + 1, false);
				} else if (isMold(oldStack)) {
					moved = moveItemStackTo(oldStack, SLOT_MOLD, SLOT_MOLD + 1, false);
				} else {
					moved = moveItemStackTo(oldStack, SLOT_MATERIAL, SLOT_MATERIAL + 1, false);
				}

				if (!moved) {
					if (index < INVENTORY_END) {
						if (!moveItemStackTo(oldStack, HOTBAR_START, HOTBAR_END, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!moveItemStackTo(oldStack, INVENTORY_START, INVENTORY_END, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!moveItemStackTo(oldStack, INVENTORY_START, HOTBAR_END, false)) {
				return ItemStack.EMPTY;
			}

			if (oldStack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (oldStack.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, oldStack);
		}

		return itemStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return container.stillValid(player);
	}

	/// Check if the forge is currently burning fuel.
	///
	/// @return boolean
	public boolean isLit() {
		return containerData.get(DATA_LIT_TIME) > 0;
	}

	/// Get the remaining burn time as a fraction, for the flame sprite.
	///
	/// @return float
	public float getLitProgress() {
		int duration = containerData.get(DATA_LIT_DURATION);
		if (duration == 0) {
			duration = 200;
		}

		return Mth.clamp((float) containerData.get(DATA_LIT_TIME) / duration, 0.0F, 1.0F);
	}

	/// Get the casting progress as a fraction, for the arrow sprite.
	///
	/// @return float
	public float getCookingProgress() {
		int total = containerData.get(DATA_COOKING_TOTAL_TIME);
		int progress = containerData.get(DATA_COOKING_PROGRESS);

		return total != 0 && progress != 0 ? Mth.clamp((float) progress / total, 0.0F, 1.0F) : 0.0F;
	}
}
