package com.anonymoushacker1279.immersiveweapons.blockentity;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.ICustomRecipeType;
import com.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractTeslaSynthesizerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible, EntityBlock {

	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};
	private static final Map<Item, Integer> burnTimesMap = Maps.newLinkedHashMap();
	private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
	protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
	private int burnTime;
	private int burnTimeTotal;
	private int cookTime;
	private int cookTimeTotal;
	final ContainerData teslaSynthesizerData = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> burnTime;
				case 1 -> burnTimeTotal;
				case 2 -> cookTime;
				case 3 -> cookTimeTotal;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 1 -> burnTimeTotal = value;
				case 2 -> cookTime = value;
				case 3 -> cookTimeTotal = value;
				default -> burnTime = value;
			}

		}

		@Override
		public int getCount() {
			return 4;
		}
	};

	/**
	 * Constructor for AbstractTeslaTileEntity.
	 */
	AbstractTeslaSynthesizerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_BLOCK_ENTITY.get(), blockPos, blockState);
		setupBurnTimes();
	}

	/**
	 * Check if a fuel source is blacklisted.
	 *
	 * @param item the <code>Item</code> to check
	 * @return boolean
	 */
	private static boolean isNonFlammable(Item item) {
		return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
	}

	/**
	 * Add an item burn time to the map.
	 *
	 * @param map          the burn time <code>Map</code> extending Item, Integer
	 * @param itemProvider the <code>IItemProvider</code> instance
	 * @param burnTimeIn   the burn time
	 */
	private static void addItemBurnTime(Map<Item, Integer> map, ItemLike itemProvider, int burnTimeIn) {
		Item item = itemProvider.asItem();
		if (isNonFlammable(item)) {
			if (SharedConstants.IS_RUNNING_IN_IDE) {
				throw Util.pauseInIde(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item.getName(new ItemStack(item)).getString() + " a furnace fuel. That will not work!"));
			}
		} else {
			map.put(item, burnTimeIn);
		}
	}

	/**
	 * Get the burn time for a fuel item.
	 *
	 * @param fuel the <code>ItemStack</code> to check
	 * @return int
	 */
	private static int getBurnTime(ItemStack fuel) {
		if (!fuel.isEmpty()) {
			Item item = fuel.getItem();
			if (burnTimesMap.containsKey(item)) {
				return burnTimesMap.get(item);
			}
		}
		return 0;
	}

	/**
	 * Check if an item is fuel.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return boolean
	 */
	public static boolean isFuel(ItemStack stack) {
		if (burnTimesMap.containsKey(stack.getItem())) {
			return getBurnTime(stack) > 0;
		}
		return false;
	}

	/**
	 * Runs once per tick. Handle smelting procedures here.
	 */
	public static void serverTick(Level level, AbstractTeslaSynthesizerBlockEntity abstractTeslaSynthesizerTileEntity) {
		boolean flag = abstractTeslaSynthesizerTileEntity.isBurning();
		boolean flag1 = false;
		if (abstractTeslaSynthesizerTileEntity.isBurning()) {
			--abstractTeslaSynthesizerTileEntity.burnTime;
		}

		ItemStack itemstack = abstractTeslaSynthesizerTileEntity.items.get(3);
		if (abstractTeslaSynthesizerTileEntity.isBurning() || !itemstack.isEmpty() && !abstractTeslaSynthesizerTileEntity.items.get(0).isEmpty() && !abstractTeslaSynthesizerTileEntity.items.get(1).isEmpty() && !abstractTeslaSynthesizerTileEntity.items.get(2).isEmpty()) {
			RecipeManager recipeManager = level.getRecipeManager();
			Recipe<?> iRecipe = recipeManager.getRecipeFor(ICustomRecipeType.TESLA_SYNTHESIZER, abstractTeslaSynthesizerTileEntity, level).orElse(null);
			if (!abstractTeslaSynthesizerTileEntity.isBurning() && abstractTeslaSynthesizerTileEntity.canSmelt(iRecipe)) {
				abstractTeslaSynthesizerTileEntity.burnTime = getBurnTime(itemstack);
				abstractTeslaSynthesizerTileEntity.burnTimeTotal = abstractTeslaSynthesizerTileEntity.burnTime;
				if (abstractTeslaSynthesizerTileEntity.isBurning()) {
					flag1 = true;
					if (itemstack.hasContainerItem())
						abstractTeslaSynthesizerTileEntity.items.set(3, itemstack.getContainerItem());
					else if (!itemstack.isEmpty()) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							abstractTeslaSynthesizerTileEntity.items.set(3, itemstack.getContainerItem());
						}
					}
				}
			}

			if (abstractTeslaSynthesizerTileEntity.isBurning() && abstractTeslaSynthesizerTileEntity.canSmelt(iRecipe)) {
				++abstractTeslaSynthesizerTileEntity.cookTime;
				if (abstractTeslaSynthesizerTileEntity.cookTime == abstractTeslaSynthesizerTileEntity.cookTimeTotal) {
					abstractTeslaSynthesizerTileEntity.cookTime = 0;
					abstractTeslaSynthesizerTileEntity.cookTimeTotal = abstractTeslaSynthesizerTileEntity.getCookTime();
					abstractTeslaSynthesizerTileEntity.smelt(iRecipe);
					flag1 = true;
				}
			} else {
				abstractTeslaSynthesizerTileEntity.cookTime = 0;
			}
		} else if (!abstractTeslaSynthesizerTileEntity.isBurning() && abstractTeslaSynthesizerTileEntity.cookTime > 0) {
			abstractTeslaSynthesizerTileEntity.cookTime = Mth.clamp(abstractTeslaSynthesizerTileEntity.cookTime - 2, 0, abstractTeslaSynthesizerTileEntity.cookTimeTotal);
		}

		if (flag != abstractTeslaSynthesizerTileEntity.isBurning()) {
			flag1 = true;
		}

		if (flag1) {
			abstractTeslaSynthesizerTileEntity.setChanged();
		}

	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new TeslaSynthesizerBlockEntity(blockPos, blockState);
	}

	/**
	 * Set up the burn time map
	 */
	private void setupBurnTimes() {
		addItemBurnTime(burnTimesMap, DeferredRegistryHandler.MOLTEN_INGOT.get(), 24000); // 20 minutes
	}

	/**
	 * Check if the fuel is burning.
	 *
	 * @return boolean
	 */
	private boolean isBurning() {
		return burnTime > 0;
	}

	/**
	 * Load NBT data.
	 *
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(@NotNull CompoundTag nbt) {
		super.load(nbt);
		items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, items);
		burnTime = nbt.getInt("BurnTime");
		cookTime = nbt.getInt("CookTime");
		cookTimeTotal = nbt.getInt("CookTimeTotal");
		burnTimeTotal = getBurnTime(items.get(3));
		CompoundTag compoundTag = nbt.getCompound("RecipesUsed");

		for (String s : compoundTag.getAllKeys()) {
			recipes.put(new ResourceLocation(s), compoundTag.getInt(s));
		}

	}

	/**
	 * Save NBT data.
	 *
	 * @param nbt the <code>CompoundNBT</code> to save
	 */
	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag nbt) {
		super.save(nbt);
		nbt.putInt("BurnTime", burnTime);
		nbt.putInt("CookTime", cookTime);
		nbt.putInt("CookTimeTotal", cookTimeTotal);
		ContainerHelper.saveAllItems(nbt, items);
		CompoundTag compoundTag = new CompoundTag();
		recipes.forEach((recipeId, craftedAmount) -> compoundTag.putInt(recipeId.toString(), craftedAmount));
		nbt.put("RecipesUsed", compoundTag);
		return nbt;
	}

	/**
	 * Determines if the recipe can be smelt.
	 *
	 * @param recipeIn the <code>Recipe</code> instance
	 * @return boolean
	 */
	private boolean canSmelt(@Nullable Recipe<?> recipeIn) {
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
					return itemStack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
				}
			}
		} else {
			return false;
		}
	}

	/**
	 * Smelt a recipe.
	 *
	 * @param recipe the <code>IRecipe</code> instance
	 */
	private void smelt(@Nullable Recipe<?> recipe) {
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

	/**
	 * Get the cook time for a recipe.
	 *
	 * @return int
	 */
	private int getCookTime() {
		if (level != null) {
			Optional<TeslaSynthesizerRecipe> optional = level.getRecipeManager().getRecipeFor(ICustomRecipeType.TESLA_SYNTHESIZER, this, level);
			if (optional.isPresent())
				return optional.get().getCookTime();
		}
		return 0;
	}

	/**
	 * Get slots for faces.
	 *
	 * @param side the <code>Direction</code> to check
	 * @return int[]
	 */
	@Override
	public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_DOWN;
		} else {
			return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
		}
	}

	/**
	 * Get the number of slots in the inventory.
	 *
	 * @return int
	 */
	@Override
	public int getContainerSize() {
		return items.size();
	}

	/**
	 * Check if the inventory is empty.
	 *
	 * @return boolean
	 */
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
	 * Determine if an item can be placed through a face.
	 *
	 * @param index     the slot index
	 * @param itemStack the <code>ItemStack</code> to insert
	 * @param direction the <code>Direction</code> the block is facing
	 * @return boolean
	 */
	@Override
	public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, Direction direction) {
		return false;
	}

	/**
	 * Determine if an item can be removed through a face.
	 *
	 * @param index     the slot index
	 * @param itemStack the <code>ItemStack</code> to remove
	 * @param direction the <code>Direction</code> the block is facing
	 * @return boolean
	 */
	@Override
	public boolean canTakeItemThroughFace(int index, @NotNull ItemStack itemStack, @NotNull Direction direction) {
		return false;
	}

	/**
	 * Get the stack in the given slot.
	 *
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack getItem(int index) {
		return items.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 *
	 * @param index the slot index
	 * @param count the number to remove
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(items, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 *
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(items, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory.
	 *
	 * @param index the slot index
	 * @param stack the <code>ItemStack</code> to set
	 */
	@Override
	public void setItem(int index, ItemStack stack) {
		ItemStack itemstack = items.get(index);
		boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
		items.set(index, stack);
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}

		if (index == 0 || index == 1 || index == 2 && !flag) {
			cookTimeTotal = getCookTime();
			cookTime = 0;
			setChanged();
		}

	}

	/**
	 * Check if the player is still valid.
	 *
	 * @param player the <code>PlayerEntity</code> to check
	 * @return boolean
	 */
	@Override
	public boolean stillValid(@NotNull Player player) {
		if ((level != null ? level.getBlockEntity(worldPosition) : null) != this) {
			return false;
		} else {
			return player.distanceToSqr((double) worldPosition.getX() + 0.5D, (double) worldPosition.getY() + 0.5D, (double) worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	/**
	 * Check if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 *
	 * @param index the slot index
	 * @param stack the <code>ItemStack</code> to insert
	 */
	@Override
	public boolean canPlaceItem(int index, @NotNull ItemStack stack) {
		return false;
	}

	/**
	 * Clear the inventory.
	 */
	@Override
	public void clearContent() {
		items.clear();
	}

	/**
	 * Get the used recipe.
	 *
	 * @return IRecipe
	 */
	@Override
	public Recipe<?> getRecipeUsed() {
		return null;
	}

	/**
	 * Set the used recipe.
	 *
	 * @param recipe the <code>IRecipe</code> to set
	 */
	@Override
	public void setRecipeUsed(@Nullable Recipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation resourcelocation = recipe.getId();
			recipes.addTo(resourcelocation, 1);
		}

	}

	/**
	 * Award used recipes to the player.
	 *
	 * @param player the <code>PlayerEntity</code> instance
	 */
	@Override
	public void awardUsedRecipes(@NotNull Player player) {
	}

	/**
	 * Fill stacked contents.
	 *
	 * @param helper the <code>RecipeItemHelper</code> instance
	 */
	@Override
	public void fillStackedContents(@NotNull StackedContents helper) {
		for (ItemStack itemstack : items) {
			helper.accountStack(itemstack);
		}

	}

	/**
	 * Get capabilities.
	 *
	 * @param capability the <code>Capability</code> instance
	 * @param facing     the <code>Direction</code> the block is facing
	 * @return LazyOptional
	 */
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
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

	/**
	 * Invalidate capabilities.
	 */
	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		for (LazyOptional<? extends IItemHandler> handler : handlers)
			handler.invalidate();
	}
}