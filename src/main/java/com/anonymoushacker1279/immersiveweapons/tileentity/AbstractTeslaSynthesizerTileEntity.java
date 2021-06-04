package com.anonymoushacker1279.immersiveweapons.tileentity;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.ICustomRecipeType;
import com.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class AbstractTeslaSynthesizerTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {

	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};
	protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
	static Map<Item, Integer> burnTimesMap = Maps.newLinkedHashMap();
	private int burnTime;
	private int burnTimeTotal;
	private int cookTime;
	private int cookTimeTotal;
	protected final IIntArray teslaSynthesizerData = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
				case 0:
					return AbstractTeslaSynthesizerTileEntity.this.burnTime;
				case 1:
					return AbstractTeslaSynthesizerTileEntity.this.burnTimeTotal;
				case 2:
					return AbstractTeslaSynthesizerTileEntity.this.cookTime;
				case 3:
					return AbstractTeslaSynthesizerTileEntity.this.cookTimeTotal;
				default:
					return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0:
					AbstractTeslaSynthesizerTileEntity.this.burnTime = value;
					break;
				case 1:
					AbstractTeslaSynthesizerTileEntity.this.burnTimeTotal = value;
					break;
				case 2:
					AbstractTeslaSynthesizerTileEntity.this.cookTime = value;
					break;
				case 3:
					AbstractTeslaSynthesizerTileEntity.this.cookTimeTotal = value;
			}

		}

		@Override
		public int size() {
			return 4;
		}
	};
	private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
	protected final IRecipeType recipeType;

	protected AbstractTeslaSynthesizerTileEntity(TileEntityType<?> tileTypeIn, IRecipeType recipeTypeIn) {
		super(tileTypeIn);
		this.recipeType = recipeTypeIn;
		this.setupBurnTimes();
	}

	public static Map<Item, Integer> getBurnTimes() {
		return burnTimesMap;
	}

	private void setupBurnTimes() {
		addItemBurnTime(burnTimesMap, DeferredRegistryHandler.MOLTEN_INGOT.get(), 24000); // 20 minutes
	}

	private static boolean isNonFlammable(Item item) {
		return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
	}

	private static void addItemBurnTime(Map<Item, Integer> map, IItemProvider itemProvider, int burnTimeIn) {
		Item item = itemProvider.asItem();
		if (isNonFlammable(item)) {
			if (SharedConstants.developmentMode) {
				throw Util.pauseDevMode(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item.getDisplayName(null).getString() + " a furnace fuel. That will not work!"));
			}
		} else {
			map.put(item, burnTimeIn);
		}
	}

	private boolean isBurning() {
		return this.burnTime > 0;
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, this.items);
		this.burnTime = nbt.getInt("BurnTime");
		this.cookTime = nbt.getInt("CookTime");
		this.cookTimeTotal = nbt.getInt("CookTimeTotal");
		this.burnTimeTotal = getBurnTime(this.items.get(3));
		CompoundNBT compoundnbt = nbt.getCompound("RecipesUsed");

		for (String s : compoundnbt.keySet()) {
			this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
		}

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("BurnTime", this.burnTime);
		compound.putInt("CookTime", this.cookTime);
		compound.putInt("CookTimeTotal", this.cookTimeTotal);
		ItemStackHelper.saveAllItems(compound, this.items);
		CompoundNBT compoundnbt = new CompoundNBT();
		this.recipes.forEach((recipeId, craftedAmount) -> {
			compoundnbt.putInt(recipeId.toString(), craftedAmount);
		});
		compound.put("RecipesUsed", compoundnbt);
		return compound;
	}

	@Override
	public void tick() {
		boolean flag = this.isBurning();
		boolean flag1 = false;
		if (this.isBurning()) {
			--this.burnTime;
		}

		if (!this.world.isRemote) {
			ItemStack itemstack = this.items.get(3);
			if (this.isBurning() || !itemstack.isEmpty() && !this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && !this.items.get(2).isEmpty()) {
				RecipeManager recipeManager = this.world.getRecipeManager();
				IRecipe<?> iRecipe = recipeManager.getRecipe(ICustomRecipeType.TESLA_SYNTHESIZER, this, this.world).orElse(null);
				if (!this.isBurning() && this.canSmelt(iRecipe)) {
					this.burnTime = getBurnTime(itemstack);
					this.burnTimeTotal = this.burnTime;
					if (this.isBurning()) {
						flag1 = true;
						if (itemstack.hasContainerItem())
							this.items.set(3, itemstack.getContainerItem());
						else if (!itemstack.isEmpty()) {
							itemstack.shrink(1);
							if (itemstack.isEmpty()) {
								this.items.set(3, itemstack.getContainerItem());
							}
						}
					}
				}

				if (this.isBurning() && this.canSmelt(iRecipe)) {
					++this.cookTime;
					if (this.cookTime == this.cookTimeTotal) {
						this.cookTime = 0;
						this.cookTimeTotal = this.getCookTime();
						this.smelt(iRecipe);
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			} else if (!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
			}

			if (flag != this.isBurning()) {
				flag1 = true;
			}
		}

		if (flag1) {
			this.markDirty();
		}

	}

	protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
		if (!this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && !this.items.get(2).isEmpty() && recipeIn != null) {
			ItemStack itemstack = recipeIn.getRecipeOutput();
			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.items.get(4);
				if (itemstack1.isEmpty()) {
					return true;
				} else if (!itemstack1.isItemEqual(itemstack)) {
					return false;
				} else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return false;
	}

	private void smelt(@Nullable IRecipe<?> recipe) {
		if (recipe != null && this.canSmelt(recipe)) {
			ItemStack ingredient1 = this.items.get(0);
			ItemStack ingredient2 = this.items.get(1);
			ItemStack ingredient3 = this.items.get(2);
			ItemStack recipeOutputStack = this.items.get(4);
			ItemStack recipeOutput = recipe.getRecipeOutput();
			if (recipeOutputStack.isEmpty()) {
				this.items.set(4, recipeOutput.copy());
			} else if (recipeOutputStack.getItem() == recipeOutput.getItem()) {
				recipeOutputStack.grow(recipeOutput.getCount());
			}

			if (!this.world.isRemote) {
				this.setRecipeUsed(recipe);
			}

			ingredient1.shrink(1);
			ingredient2.shrink(1);
			ingredient3.shrink(1);
		}
	}

	protected static int getBurnTime(ItemStack fuel) {
		if (!fuel.isEmpty()) {
			Item item = fuel.getItem();
			Map<Item, Integer> map = getBurnTimes();
			if (map.containsKey(item)) {
				return map.get(item);
			}
		}
		return 0;
	}

	protected int getCookTime() {
		if (this.world != null) {
			return (int) this.world.getRecipeManager().getRecipe(ICustomRecipeType.TESLA_SYNTHESIZER, this, this.world).map(o -> TeslaSynthesizerRecipe.getCookTime()).orElse(200);
		}
		return 0;
	}

	public static boolean isFuel(ItemStack stack) {
		Map<Item, Integer> map = getBurnTimes();
		if (map.containsKey(stack.getItem())) {
			return getBurnTime(stack) > 0;
		}
		return false;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_DOWN;
		} else {
			return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.items.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.cookTimeTotal = this.getCookTime();
			this.cookTime = 0;
			this.markDirty();
		}

	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	 * guis use Slot.isItemValid
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 2) {
			return false;
		} else if (index != 1) {
			return true;
		} else {
			return isFuel(stack);
		}
	}

	@Override
	public void clear() {
		this.items.clear();
	}

	@Override
	public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation resourcelocation = recipe.getId();
			this.recipes.addTo(resourcelocation, 1);
		}

	}

	@Override
	@Nullable
	public IRecipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	public void onCrafting(PlayerEntity player) {
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		for (ItemStack itemstack : this.items) {
			helper.accountStack(itemstack);
		}

	}

	LazyOptional<? extends IItemHandler>[] handlers =
			SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP)
				return handlers[0].cast();
			else if (facing == Direction.DOWN)
				return handlers[1].cast();
			else
				return handlers[2].cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void invalidateCaps() {
		super.invalidateCaps();
		for (LazyOptional<? extends IItemHandler> handler : handlers)
			handler.invalidate();
	}
}