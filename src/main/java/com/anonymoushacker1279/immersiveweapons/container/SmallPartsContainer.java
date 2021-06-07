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

import javax.annotation.Nullable;
import java.util.List;

public class SmallPartsContainer extends AbstractRepairContainer {

	private final World world;
	private final List<SmallPartsRecipe> smallPartsRecipeList;
	@Nullable
	private SmallPartsRecipe smallPartsRecipe;

	public SmallPartsContainer(int id, PlayerInventory inv) {
		this(id, inv, IWorldPosCallable.NULL);
	}

	public SmallPartsContainer(int id, PlayerInventory inv, IWorldPosCallable worldPosCallable) {
		super(DeferredRegistryHandler.SMALL_PARTS_TABLE_CONTAINER.get(), id, inv, worldPosCallable);
		this.world = inv.player.level;
		this.smallPartsRecipeList = this.world.getRecipeManager().getAllRecipesFor(ICustomRecipeType.SMALL_PARTS);
	}

	@Override
	protected boolean isValidBlock(BlockState blockState) {
		return blockState.is(DeferredRegistryHandler.SMALL_PARTS_TABLE.get());
	}

	@Override
	protected boolean mayPickup(PlayerEntity playerEntity, boolean matchesRecipe) {
		return this.smallPartsRecipe != null && this.smallPartsRecipe.matches(this.inputSlots, this.world);
	}

	@Override
	protected ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
		itemStack.onCraftedBy(playerEntity.level, playerEntity, itemStack.getCount());
		this.resultSlots.awardUsedRecipes(playerEntity);
		this.shrinkStackInSlot(0);
		// Normally we would destroy both items here. However we don't want to destroy the blueprint item.
		world.playSound(playerEntity, playerEntity.blockPosition(), DeferredRegistryHandler.SMALL_PARTS_TABLE_USED.get(), SoundCategory.NEUTRAL, 1f, 1);
		return itemStack;
	}

	private void shrinkStackInSlot(int index) {
		ItemStack itemstack = this.inputSlots.getItem(index);
		itemstack.shrink(1);
		this.inputSlots.setItem(index, itemstack);
	}

	@Override
	public void createResult() {
		List<SmallPartsRecipe> list = this.world.getRecipeManager().getRecipesFor(ICustomRecipeType.SMALL_PARTS, this.inputSlots, this.world);
		if (list.isEmpty()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
		} else {
			this.smallPartsRecipe = list.get(0);
			ItemStack itemstack = this.smallPartsRecipe.assemble(this.inputSlots);
			this.resultSlots.setRecipeUsed(this.smallPartsRecipe);
			this.resultSlots.setItem(0, itemstack);
		}

	}

	@Override
	protected boolean shouldQuickMoveToAdditionalSlot(ItemStack itemStack) {
		return this.smallPartsRecipeList.stream().anyMatch((p_241444_1_) -> p_241444_1_.isValidAdditionItem(itemStack));
	}

	/**
	 * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
	 * null for the initial slot that was double-clicked.
	 */
	@Override
	public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
		return slotIn.container != this.resultSlots && super.canTakeItemForPickAll(stack, slotIn);
	}

}