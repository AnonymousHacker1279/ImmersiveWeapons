package tech.anonymoushacker1279.immersiveweapons.menu;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import tech.anonymoushacker1279.immersiveweapons.blockentity.TeslaSynthesizerBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.MenuTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.menu.slot.*;

public class TeslaSynthesizerMenu extends AbstractContainerMenu implements StackedContentsCompatible {

	private final Container container;
	private final ContainerData containerData;

	/**
	 * Constructor for TeslaSynthesizerMenu.
	 *
	 * @param id        the ID of the container
	 * @param inventory the <code>Inventory</code> instance
	 */
	public TeslaSynthesizerMenu(int id, Inventory inventory) {
		this(id, inventory, new SimpleContainer(5), new SimpleContainerData(4));
	}

	/**
	 * Constructor for TeslaSynthesizerMenu.
	 *
	 * @param id            the ID of the container
	 * @param inventory     the player inventory
	 * @param container     the container
	 * @param containerData the container data
	 */
	public TeslaSynthesizerMenu(int id, Inventory inventory, Container container, ContainerData containerData) {
		super(MenuTypeRegistry.TESLA_SYNTHESIZER_MENU.get(), id);
		checkContainerSize(container, 5);
		checkContainerDataCount(containerData, 4);
		this.container = container;
		this.containerData = containerData;
		// First ingredient slot
		addSlot(new TeslaSynthesizerMaterialSlot(this, inventory.player.level().getRecipeManager(), container, 0, 6, 17));
		// Second ingredient slot
		addSlot(new TeslaSynthesizerMaterialSlot(this, inventory.player.level().getRecipeManager(), container, 1, 31, 17));
		// Third ingredient slot
		addSlot(new TeslaSynthesizerMaterialSlot(this, inventory.player.level().getRecipeManager(), container, 2, 56, 17));
		// Fuel slot
		addSlot(new TeslaSynthesizerFuelSlot(this, container, 3, 56, 53));
		// Result slot
		addSlot(new TeslaSynthesizerResultSlot(container, 4, 116, 35));

		// Player inventory slots
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}

		addDataSlots(containerData);
	}

	@Override
	public boolean stillValid(Player player) {
		return container.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);

		if (slot.hasItem()) {
			ItemStack oldStack = slot.getItem();
			newStack = oldStack.copy();

			// If the slot is the result slot
			if (index == 4) {
				if (!moveItemStackTo(oldStack, 5, 41, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(oldStack, newStack);
			}
			// If the slot is the fuel slot
			else if (index != 3 && index != 2 && index != 1 && index != 0) {
				// If the item is a fuel item
				if (isFuel(oldStack)) {
					if (!moveItemStackTo(oldStack, 3, 4, false)) {
						return ItemStack.EMPTY;
					}
				}
				// If the item is a valid ingredient
				else if (isIngredient(player.level().getRecipeManager(), oldStack)) {
					if (!moveItemStackTo(oldStack, 0, 3, false)) {
						return ItemStack.EMPTY;
					}
				}
				// If the item is in the player inventory
				else if (index >= 5 && index < 32) {
					if (!moveItemStackTo(oldStack, 32, 41, false)) {
						return ItemStack.EMPTY;
					}
				}
				// If the item is in the player hotbar
				else if (index >= 32 && index < 41 && !moveItemStackTo(oldStack, 5, 32, false)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (!moveItemStackTo(oldStack, 5, 41, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (oldStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (oldStack.getCount() == newStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, oldStack);
		}

		return newStack;
	}

	@Override
	public void fillStackedContents(StackedContents helper) {
		if (container instanceof StackedContentsCompatible stackedContentsCompatible) {
			stackedContentsCompatible.fillStackedContents(helper);
		}
	}

	/**
	 * Check if the given ItemStack is a fuel item.
	 *
	 * @param stack the <code>ItemStack</code> being checked
	 * @return boolean
	 */
	public boolean isFuel(ItemStack stack) {
		return TeslaSynthesizerBlockEntity.isFuel(stack);
	}

	/**
	 * Check if the given ItemStack is a valid ingredient for any recipe.
	 *
	 * @param recipeManager the <code>RecipeManager</code> instance
	 * @param stack         the <code>ItemStack</code> being checked
	 * @return boolean
	 */
	public boolean isIngredient(RecipeManager recipeManager, ItemStack stack) {
		return recipeManager.getAllRecipesFor(RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get())
				.stream().anyMatch(recipe -> recipe.value().getIngredients()
						.stream().anyMatch(ingredient -> ingredient.test(stack)));
	}

	/**
	 * Check if the given ItemStack is a valid ingredient for the given recipe in the given slot.
	 *
	 * @param recipeManager the <code>RecipeManager</code> instance
	 * @param stack         the <code>ItemStack</code> being checked
	 * @param slot          the slot index
	 */
	public boolean isIngredient(RecipeManager recipeManager, ItemStack stack, int slot) {
		return recipeManager.getAllRecipesFor(RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get())
				.stream().anyMatch(recipe -> recipe.value().getIngredients().get(slot).test(stack));
	}

	/**
	 * Get the current progression.
	 *
	 * @return int
	 */
	public int getCookProgressionScaled() {
		int i = containerData.get(2);
		int j = containerData.get(3);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}

	/**
	 * Get the scaled burn time left.
	 *
	 * @return int
	 */
	public int getBurnLeftScaled() {
		int i = containerData.get(1);
		if (i == 0) {
			i = 200;
		}

		return containerData.get(0) * 13 / i;
	}

	/**
	 * Check if the fuel is currently burning.
	 *
	 * @return boolean
	 */
	public boolean isBurning() {
		return containerData.get(0) > 0;
	}
}