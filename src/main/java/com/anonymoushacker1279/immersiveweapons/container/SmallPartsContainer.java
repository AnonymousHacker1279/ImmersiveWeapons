package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.ICustomRecipeType;
import com.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SmallPartsContainer extends ItemCombinerMenu {

	private final Level world;
	private final List<SmallPartsRecipe> smallPartsRecipeList;
	private SmallPartsRecipe smallPartsRecipe;

	/**
	 * Constructor for SmallPartsContainer.
	 *
	 * @param id  the ID of the container
	 * @param inv the <code>PlayerInventory</code> instance
	 */
	public SmallPartsContainer(int id, Inventory inv) {
		this(id, inv, ContainerLevelAccess.NULL);
	}

	/**
	 * Constructor for SmallPartsContainer.
	 *
	 * @param id               the ID of the container
	 * @param inv              the <code>PlayerInventory</code> instance
	 * @param worldPosCallable the <code>IWorldPosCallable</code> instance
	 */
	public SmallPartsContainer(int id, Inventory inv, ContainerLevelAccess worldPosCallable) {
		super(DeferredRegistryHandler.SMALL_PARTS_TABLE_CONTAINER.get(), id, inv, worldPosCallable);
		world = inv.player.level;
		smallPartsRecipeList = world.getRecipeManager().getAllRecipesFor(ICustomRecipeType.SMALL_PARTS);
	}

	/**
	 * Check for a valid block.
	 *
	 * @param blockState the <code>BlockState</code> of the block
	 * @return boolean
	 */
	@Override
	protected boolean isValidBlock(BlockState blockState) {
		return blockState.is(DeferredRegistryHandler.SMALL_PARTS_TABLE.get());
	}

	/**
	 * Check if the player can pick up a recipe.
	 *
	 * @param playerEntity  the <code>PlayerEntity</code> instance
	 * @param matchesRecipe set the recipe match
	 * @return boolean
	 */
	@Override
	protected boolean mayPickup(@NotNull Player playerEntity, boolean matchesRecipe) {
		return smallPartsRecipe != null && smallPartsRecipe.matches(inputSlots, world);
	}

	/**
	 * Runs when the result is taken from the container.
	 *
	 * @param player    the <code>Player</code> instance
	 * @param itemStack the <code>ItemStack</code> being taken
	 */
	@Override
	protected void onTake(@NotNull Player player, ItemStack itemStack) {
		itemStack.onCraftedBy(player.level, player, itemStack.getCount());
		resultSlots.awardUsedRecipes(player);
		shrinkStackInSlot(0);
		// Normally we would destroy both items here. However, we don't want to destroy the blueprint item.
		world.playSound(player, player.blockPosition(), DeferredRegistryHandler.SMALL_PARTS_TABLE_USED.get(), SoundSource.NEUTRAL, 1f, 1);
	}

	/**
	 * Shrink a stack in a slot.
	 *
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
	 *
	 * @param itemStack the <code>ItemStack</code> being checked
	 * @return boolean
	 */
	@Override
	protected boolean shouldQuickMoveToAdditionalSlot(@NotNull ItemStack itemStack) {
		return smallPartsRecipeList.stream().anyMatch((smallPartsRecipe) -> smallPartsRecipe.isValidAdditionItem(itemStack));
	}

	/**
	 * Check if the specified slot is valid for stack merging.
	 *
	 * @param stack  the <code>ItemStack</code> being merged
	 * @param slotIn the <code>Slot</code> instance
	 * @return boolean
	 */
	@Override
	public boolean canTakeItemForPickAll(@NotNull ItemStack stack, Slot slotIn) {
		return slotIn.container != resultSlots && super.canTakeItemForPickAll(stack, slotIn);
	}

}