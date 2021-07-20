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
	static Map<Item, Integer> burnTimesMap = Maps.newLinkedHashMap();
	protected final IRecipeType<TeslaSynthesizerRecipe> recipeType;
	private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
	protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
	LazyOptional<? extends IItemHandler>[] handlers =
			SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
	private int burnTime;
	private int burnTimeTotal;
	private int cookTime;
	private int cookTimeTotal;
	protected final IIntArray teslaSynthesizerData = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
				case 0:
					return burnTime;
				case 1:
					return burnTimeTotal;
				case 2:
					return cookTime;
				case 3:
					return cookTimeTotal;
				default:
					return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0:
					burnTime = value;
					break;
				case 1:
					burnTimeTotal = value;
					break;
				case 2:
					cookTime = value;
					break;
				case 3:
					cookTimeTotal = value;
			}

		}

		@Override
		public int getCount() {
			return 4;
		}
	};

	protected AbstractTeslaSynthesizerTileEntity(TileEntityType<?> tileTypeIn, IRecipeType<TeslaSynthesizerRecipe> recipeTypeIn) {
		super(tileTypeIn);
		recipeType = recipeTypeIn;
		setupBurnTimes();
	}

	public static Map<Item, Integer> getBurnTimes() {
		return burnTimesMap;
	}

	private static boolean isNonFlammable(Item item) {
		return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
	}

	private static void addItemBurnTime(Map<Item, Integer> map, IItemProvider itemProvider, int burnTimeIn) {
		Item item = itemProvider.asItem();
		if (isNonFlammable(item)) {
			if (SharedConstants.IS_RUNNING_IN_IDE) {
				throw Util.pauseInIde(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item.getName(null).getString() + " a furnace fuel. That will not work!"));
			}
		} else {
			map.put(item, burnTimeIn);
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

	public static boolean isFuel(ItemStack stack) {
		Map<Item, Integer> map = getBurnTimes();
		if (map.containsKey(stack.getItem())) {
			return getBurnTime(stack) > 0;
		}
		return false;
	}

	private void setupBurnTimes() {
		addItemBurnTime(burnTimesMap, DeferredRegistryHandler.MOLTEN_INGOT.get(), 24000); // 20 minutes
	}

	private boolean isBurning() {
		return burnTime > 0;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, items);
		burnTime = nbt.getInt("BurnTime");
		cookTime = nbt.getInt("CookTime");
		cookTimeTotal = nbt.getInt("CookTimeTotal");
		burnTimeTotal = getBurnTime(items.get(3));
		CompoundNBT compoundnbt = nbt.getCompound("RecipesUsed");

		for (String s : compoundnbt.getAllKeys()) {
			recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
		}

	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		compound.putInt("BurnTime", burnTime);
		compound.putInt("CookTime", cookTime);
		compound.putInt("CookTimeTotal", cookTimeTotal);
		ItemStackHelper.saveAllItems(compound, items);
		CompoundNBT compoundnbt = new CompoundNBT();
		recipes.forEach((recipeId, craftedAmount) -> compoundnbt.putInt(recipeId.toString(), craftedAmount));
		compound.put("RecipesUsed", compoundnbt);
		return compound;
	}

	@Override
	public void tick() {
		boolean flag = isBurning();
		boolean flag1 = false;
		if (isBurning()) {
			--burnTime;
		}

		if (level != null && !level.isClientSide) {
			ItemStack itemstack = items.get(3);
			if (isBurning() || !itemstack.isEmpty() && !items.get(0).isEmpty() && !items.get(1).isEmpty() && !items.get(2).isEmpty()) {
				RecipeManager recipeManager = level.getRecipeManager();
				IRecipe<?> iRecipe = recipeManager.getRecipeFor(ICustomRecipeType.TESLA_SYNTHESIZER, this, level).orElse(null);
				if (!isBurning() && canSmelt(iRecipe)) {
					burnTime = getBurnTime(itemstack);
					burnTimeTotal = burnTime;
					if (isBurning()) {
						flag1 = true;
						if (itemstack.hasContainerItem())
							items.set(3, itemstack.getContainerItem());
						else if (!itemstack.isEmpty()) {
							itemstack.shrink(1);
							if (itemstack.isEmpty()) {
								items.set(3, itemstack.getContainerItem());
							}
						}
					}
				}

				if (isBurning() && canSmelt(iRecipe)) {
					++cookTime;
					if (cookTime == cookTimeTotal) {
						cookTime = 0;
						cookTimeTotal = getCookTime();
						smelt(iRecipe);
						flag1 = true;
					}
				} else {
					cookTime = 0;
				}
			} else if (!isBurning() && cookTime > 0) {
				cookTime = MathHelper.clamp(cookTime - 2, 0, cookTimeTotal);
			}

			if (flag != isBurning()) {
				flag1 = true;
			}
		}

		if (flag1) {
			setChanged();
		}

	}

	protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
		if (!items.get(0).isEmpty() && !items.get(1).isEmpty() && !items.get(2).isEmpty() && recipeIn != null) {
			ItemStack itemstack = recipeIn.getResultItem();
			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemStack1 = items.get(4);
				if (itemStack1.isEmpty()) {
					return true;
				} else if (!itemStack1.sameItem(itemstack)) {
					return false;
				} else if (itemStack1.getCount() + itemstack.getCount() <= getMaxStackSize() && itemStack1.getCount() + itemstack.getCount() <= itemStack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return itemStack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
		return false;
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
		return false;
	}

	private void smelt(@Nullable IRecipe<?> recipe) {
		if (recipe != null && canSmelt(recipe)) {
			ItemStack ingredient1 = items.get(0);
			ItemStack ingredient2 = items.get(1);
			ItemStack ingredient3 = items.get(2);
			ItemStack recipeOutputStack = items.get(4);
			ItemStack recipeOutput = recipe.getResultItem();
			if (recipeOutputStack.isEmpty()) {
				items.set(4, recipeOutput.copy());
			} else if (recipeOutputStack.getItem() == recipeOutput.getItem()) {
				recipeOutputStack.grow(recipeOutput.getCount());
			}

			if (level != null && !level.isClientSide) {
				setRecipeUsed(recipe);
			}

			ingredient1.shrink(1);
			ingredient2.shrink(1);
			ingredient3.shrink(1);
		}
	}

	protected int getCookTime() {
		if (level != null) {
			return (int) level.getRecipeManager().getRecipeFor(ICustomRecipeType.TESLA_SYNTHESIZER, this, level).map(o -> TeslaSynthesizerRecipe.getCookTime()).orElse(200);
		}
		return 0;
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
	public int getContainerSize() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : items) {
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
	public ItemStack getItem(int index) {
		return items.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack removeItem(int index, int count) {
		return ItemStackHelper.removeItem(items, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ItemStackHelper.takeItem(items, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setItem(int index, ItemStack stack) {
		ItemStack itemstack = items.get(index);
		boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
		items.set(index, stack);
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}

		if (index == 0 && !flag) {
			cookTimeTotal = getCookTime();
			cookTime = 0;
			setChanged();
		}

	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	@Override
	public boolean stillValid(PlayerEntity player) {
		if ((level != null ? level.getBlockEntity(worldPosition) : null) != this) {
			return false;
		} else {
			return player.distanceToSqr((double) worldPosition.getX() + 0.5D, (double) worldPosition.getY() + 0.5D, (double) worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	 * guis use Slot.isItemValid
	 */
	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 2) {
			return false;
		} else if (index != 1) {
			return true;
		} else {
			return isFuel(stack);
		}
	}

	@Override
	public void clearContent() {
		items.clear();
	}

	@Override
	@Nullable
	public IRecipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation resourcelocation = recipe.getId();
			recipes.addTo(resourcelocation, 1);
		}

	}

	@Override
	public void awardUsedRecipes(PlayerEntity player) {
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		for (ItemStack itemstack : items) {
			helper.accountStack(itemstack);
		}

	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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