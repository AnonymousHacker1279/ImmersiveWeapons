package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.ICustomRecipeType;
import com.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;

public class SmallPartsContainer extends AbstractRepairContainer {

	private final World world;
	private final List<SmallPartsRecipe> smallPartsRecipeList;
	private SmallPartsRecipe smallPartsRecipe;

	/**
	 * Constructor for SmallPartsContainer.
	 * @param id the ID of the container
	 * @param inv the <code>PlayerInventory</code> instance
	 */
	public SmallPartsContainer(int id, PlayerInventory inv) {
		this(id, inv, IWorldPosCallable.NULL);
	}

	/**
	 * Constructor for SmallPartsContainer.
	 * @param id the ID of the container
	 * @param inv the <code>PlayerInventory</code> instance
	 * @param worldPosCallable the <code>IWorldPosCallable</code> instance
	 */
	public SmallPartsContainer(int id, PlayerInventory inv, IWorldPosCallable worldPosCallable) {
		super(DeferredRegistryHandler.SMALL_PARTS_TABLE_CONTAINER.get(), id, inv, worldPosCallable);
		world = inv.player.level;
		smallPartsRecipeList = world.getRecipeManager().getAllRecipesFor(ICustomRecipeType.SMALL_PARTS);
	}

	/**
	 * Check for a valid block.
	 * @param blockState the <code>BlockState</code> of the block
	 * @return boolean
	 */
	@Override
	protected boolean isValidBlock(BlockState blockState) {
		return blockState.is(DeferredRegistryHandler.SMALL_PARTS_TABLE.get());
	}

	/**
	 * Check if the player can pick up a recipe.
	 * @param playerEntity the <code>PlayerEntity</code> instance
	 * @param matchesRecipe set the recipe match
	 * @return boolean
	 */
	@Override
	protected boolean mayPickup(PlayerEntity playerEntity, boolean matchesRecipe) {
		return smallPartsRecipe != null && smallPartsRecipe.matches(inputSlots, world);
	}

	/**
	 * Runs when the result is taken from the container.
	 * @param playerEntity the <code>PlayerEntity</code> instance
	 * @param itemStack the <code>ItemStack</code> being taken
	 * @return ItemStack
	 */
	@Override
	protected ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
		itemStack.onCraftedBy(playerEntity.level, playerEntity, itemStack.getCount());
		resultSlots.awardUsedRecipes(playerEntity);
		shrinkStackInSlot(0);
		// Normally we would destroy both items here. However we don't want to destroy the blueprint item.
		world.playSound(playerEntity, playerEntity.blockPosition(), DeferredRegistryHandler.SMALL_PARTS_TABLE_USED.get(), SoundCategory.NEUTRAL, 1f, 1);
		return itemStack;
	}

	/**
	 * Shrink a stack in a slot.
	 * @param index the slot index
	 */
	private void shrinkStackInSlot(int index) {
		ItemStack itemstack = inputSlots.getItem(index);
		itemstack.shrink(1);
		inputSlots.setItem(index, itemstack);
	}

	/**
	 * Create a result from a recipe.
	 */
	@Override
	public void createResult() {
		List<SmallPartsRecipe> list = world.getRecipeManager().getRecipesFor(ICustomRecipeType.SMALL_PARTS, inputSlots, world);
		if (list.isEmpty()) {
			resultSlots.setItem(0, ItemStack.EMPTY);
		} else {
			smallPartsRecipe = list.get(0);
			ItemStack itemstack = smallPartsRecipe.assemble(inputSlots);
			resultSlots.setRecipeUsed(smallPartsRecipe);
			resultSlots.setItem(0, itemstack);
		}

	}

	/**
	 * Check if a stack should be quick-moved.
	 * @param itemStack the <code>ItemStack</code> being checked
	 * @return boolean
	 */
	@Override
	protected boolean shouldQuickMoveToAdditionalSlot(ItemStack itemStack) {
		return smallPartsRecipeList.stream().anyMatch((smallPartsRecipe) -> smallPartsRecipe.isValidAdditionItem(itemStack));
	}

	/**
	 * Check if the specified slot is valid for stack merging.
	 * @param stack the <code>ItemStack</code> being merged
	 * @param slotIn the <code>Slot</code> instance
	 * @return boolean
	 */
	@Override
	public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
		return slotIn.container != resultSlots && super.canTakeItemForPickAll(stack, slotIn);
	}

}