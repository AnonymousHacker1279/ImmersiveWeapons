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
		this(id, inv, IWorldPosCallable.DUMMY);
	}

	public SmallPartsContainer(int id, PlayerInventory inv, IWorldPosCallable worldPosCallable) {
		super(DeferredRegistryHandler.SMALL_PARTS_TABLE_CONTAINER.get(), id, inv, worldPosCallable);
		this.world = inv.player.world;
		this.smallPartsRecipeList = this.world.getRecipeManager().getRecipesForType(ICustomRecipeType.SMALL_PARTS);
	}

	@Override
	protected boolean func_230302_a_(BlockState blockState) {
		return blockState.matchesBlock(DeferredRegistryHandler.SMALL_PARTS_TABLE.get());
	}

	@Override
	protected boolean func_230303_b_(PlayerEntity playerEntity, boolean matchesRecipe) {
		return this.smallPartsRecipe != null && this.smallPartsRecipe.matches(this.field_234643_d_, this.world);
	}

	@Override
	protected ItemStack func_230301_a_(PlayerEntity playerEntity, ItemStack itemStack) {
		itemStack.onCrafting(playerEntity.world, playerEntity, itemStack.getCount());
		this.field_234642_c_.onCrafting(playerEntity);
		this.func_234654_d_(0);
		// Normally we would destroy both items here. However we don't want to destroy the blueprint item.
		world.playSound(playerEntity, playerEntity.getPosition(), DeferredRegistryHandler.SMALL_PARTS_TABLE_USED.get(), SoundCategory.NEUTRAL, 1f, 1);
		return itemStack;
	}

	private void func_234654_d_(int index) {
		ItemStack itemstack = this.field_234643_d_.getStackInSlot(index);
		itemstack.shrink(1);
		this.field_234643_d_.setInventorySlotContents(index, itemstack);
	}

	@Override
	public void updateRepairOutput() {
		List<SmallPartsRecipe> list = this.world.getRecipeManager().getRecipes(ICustomRecipeType.SMALL_PARTS, this.field_234643_d_, this.world);
		if (list.isEmpty()) {
			this.field_234642_c_.setInventorySlotContents(0, ItemStack.EMPTY);
		} else {
			this.smallPartsRecipe = list.get(0);
			ItemStack itemstack = this.smallPartsRecipe.getCraftingResult(this.field_234643_d_);
			this.field_234642_c_.setRecipeUsed(this.smallPartsRecipe);
			this.field_234642_c_.setInventorySlotContents(0, itemstack);
		}

	}

	@Override
	protected boolean func_241210_a_(ItemStack itemStack) {
		return this.smallPartsRecipeList.stream().anyMatch((p_241444_1_) -> {
			return p_241444_1_.isValidAdditionItem(itemStack);
		});
	}

	/**
	 * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
	 * null for the initial slot that was double-clicked.
	 */
	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		return slotIn.inventory != this.field_234642_c_ && super.canMergeSlot(stack, slotIn);
	}

}