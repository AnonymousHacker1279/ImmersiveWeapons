package tech.anonymoushacker1279.immersiveweapons.blockentity;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;
import tech.anonymoushacker1279.immersiveweapons.menu.TeslaSynthesizerMenu;

import java.util.Map;
import java.util.Optional;

public class TeslaSynthesizerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, EntityBlock {

	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};
	private static final Map<Item, Integer> BURN_TIMES_MAP = Maps.newLinkedHashMap();
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
	 * Constructor for AbstractTeslaBlockEntity.
	 */
	public TeslaSynthesizerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.TESLA_SYNTHESIZER_BLOCK_ENTITY.get(), blockPos, blockState);
		setupBurnTimes();
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.immersiveweapons.tesla_synthesizer");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new TeslaSynthesizerMenu(id, inventory, this, teslaSynthesizerData);
	}

	/**
	 * Add an item burn time to the map.
	 *
	 * @param itemProvider the <code>IItemProvider</code> instance
	 * @param burnTimeIn   the burn time
	 */
	private static void addItemBurnTime(ItemLike itemProvider, int burnTimeIn) {
		BURN_TIMES_MAP.put(itemProvider.asItem(), burnTimeIn);
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
			if (BURN_TIMES_MAP.containsKey(item)) {
				return BURN_TIMES_MAP.get(item);
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
		if (BURN_TIMES_MAP.containsKey(stack.getItem())) {
			return getBurnTime(stack) > 0;
		}
		return false;
	}

	/**
	 * Runs once per tick. Handle smelting procedures here.
	 */
	public void tick(Level level) {
		boolean isBurning = isBurning();
		boolean hasChanged = false;
		if (isBurning()) {
			--burnTime;
		}

		ItemStack material1 = items.get(0);
		ItemStack material2 = items.get(1);
		ItemStack material3 = items.get(2);
		ItemStack fuel = items.get(3);
		if (isBurning() || !material1.isEmpty()
				&& !material2.isEmpty() && !material3.isEmpty() && !fuel.isEmpty()) {

			Optional<RecipeHolder<TeslaSynthesizerRecipe>> recipeHolder = level.getRecipeManager()
					.getRecipeFor(RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get(), this, level);

			TeslaSynthesizerRecipe synthesizerRecipe = recipeHolder.map(RecipeHolder::value).orElse(null);

			if (!isBurning() && canSmelt(synthesizerRecipe)) {
				burnTime = getBurnTime(fuel);
				burnTimeTotal = burnTime;
				if (isBurning()) {
					hasChanged = true;
					if (fuel.hasCraftingRemainingItem()) {
						items.set(3, fuel.getCraftingRemainingItem());
					} else if (!fuel.isEmpty()) {
						fuel.shrink(1);
						if (fuel.isEmpty()) {
							items.set(3, fuel.getCraftingRemainingItem());
						}
					}
				}
			}

			if (isBurning() && canSmelt(synthesizerRecipe)) {
				++cookTime;
				if (cookTime == cookTimeTotal) {
					cookTime = 0;
					cookTimeTotal = getCookTime();

					// Smelt the recipe
					if (synthesizerRecipe != null && canSmelt(synthesizerRecipe)) {
						ItemStack ingredient1 = items.get(0);
						ItemStack ingredient2 = items.get(1);
						ItemStack ingredient3 = items.get(2);
						ItemStack recipeOutputStack = items.get(4);
						ItemStack recipeOutput = synthesizerRecipe.getResultItem(level.registryAccess());
						if (recipeOutputStack.isEmpty()) {
							items.set(4, recipeOutput.copy());
						} else if (recipeOutputStack.getItem() == recipeOutput.getItem()) {
							recipeOutputStack.grow(recipeOutput.getCount());
						}

						ingredient1.shrink(1);
						ingredient2.shrink(1);
						ingredient3.shrink(1);

						hasChanged = true;
					}
				}
			} else {
				cookTime = 0;
			}
		} else if (!isBurning() && cookTime > 0) {
			cookTime = Mth.clamp(cookTime - 2, 0, cookTimeTotal);
		}

		if (isBurning != isBurning()) {
			hasChanged = true;
		}

		if (hasChanged) {
			setChanged();
		}
	}

	/**
	 * Create a blockLocation entity for the blockLocation.
	 *
	 * @param blockPos   the <code>BlockPos</code> the blockLocation is at
	 * @param blockState the <code>BlockState</code> of the blockLocation
	 * @return BlockEntity
	 */
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TeslaSynthesizerBlockEntity(blockPos, blockState);
	}

	/**
	 * Set up the burn time map
	 */
	private void setupBurnTimes() {
		addItemBurnTime(ItemRegistry.MOLTEN_INGOT.get(), 24000); // 20 minutes
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
	public void load(CompoundTag nbt) {
		super.load(nbt);
		items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, items);
		burnTime = nbt.getInt("BurnTime");
		cookTime = nbt.getInt("CookTime");
		cookTimeTotal = nbt.getInt("CookTimeTotal");
		burnTimeTotal = getBurnTime(items.get(3));
		CompoundTag compoundTag = nbt.getCompound("RecipesUsed");

		for (String string : compoundTag.getAllKeys()) {
			recipes.put(new ResourceLocation(string), compoundTag.getInt(string));
		}

	}

	/**
	 * Save NBT data.
	 *
	 * @param pTag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putInt("BurnTime", burnTime);
		pTag.putInt("CookTime", cookTime);
		pTag.putInt("CookTimeTotal", cookTimeTotal);
		ContainerHelper.saveAllItems(pTag, items);
		CompoundTag compoundTag = new CompoundTag();
		recipes.forEach((recipeId, craftedAmount) -> compoundTag.putInt(recipeId.toString(), craftedAmount));
		pTag.put("RecipesUsed", compoundTag);
	}

	/**
	 * Determines if the recipe can be smelt.
	 *
	 * @param recipe the <code>Recipe</code> instance
	 * @return boolean
	 */
	private boolean canSmelt(@Nullable Recipe<?> recipe) {
		if (!items.get(0).isEmpty() && !items.get(1).isEmpty() && !items.get(2).isEmpty() && recipe != null) {
			ItemStack resultItem = recipe.getResultItem(level.registryAccess());
			if (resultItem.isEmpty()) {
				return false;
			} else {
				ItemStack output = items.get(4);
				if (output.isEmpty()) {
					return true;
				} else if (!output.is(resultItem.getItem())) {
					return false;
				} else if (output.getCount() + resultItem.getCount() <= getMaxStackSize() && output.getCount()
						+ resultItem.getCount() <= output.getMaxStackSize()) {
					return true;
				} else {
					return output.getCount() + resultItem.getCount() <= resultItem.getMaxStackSize();
				}
			}
		} else {
			return false;
		}
	}

	/**
	 * Get the cook time for a recipe.
	 *
	 * @return int
	 */
	private int getCookTime() {
		if (level != null) {
			Optional<RecipeHolder<TeslaSynthesizerRecipe>> recipe = level.getRecipeManager()
					.getRecipeFor(RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get(), this, level);

			if (recipe.isPresent()) {
				return recipe.get().value().getCookTime();
			}
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
	public int[] getSlotsForFace(Direction side) {
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
		for (ItemStack itemStack : items) {
			if (!itemStack.isEmpty()) {
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
	 * @param direction the <code>Direction</code> the blockLocation is facing
	 * @return boolean
	 */
	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
		return false;
	}

	/**
	 * Determine if an item can be removed through a face.
	 *
	 * @param index     the slot index
	 * @param itemStack the <code>ItemStack</code> to remove
	 * @param direction the <code>Direction</code> the blockLocation is facing
	 * @return boolean
	 */
	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
		return false;
	}

	/**
	 * Get the stack in the given slot.
	 *
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public ItemStack getItem(int index) {
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
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(items, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 *
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public ItemStack removeItemNoUpdate(int index) {
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
		ItemStack itemStack = items.get(index);
		boolean flag = !stack.isEmpty() && stack.is(itemStack.getItem());
		items.set(index, stack);
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}

		if (!flag) {
			if (index == 0 || index == 1 || index == 2) {
				cookTimeTotal = getCookTime();
				cookTime = 0;
				setChanged();
			}
		}
	}

	/**
	 * Check if the player is still valid.
	 *
	 * @param player the <code>PlayerEntity</code> to check
	 * @return boolean
	 */
	@Override
	public boolean stillValid(Player player) {
		if ((level != null ? level.getBlockEntity(worldPosition) : null) != this) {
			return false;
		} else {
			return player.distanceToSqr((double) worldPosition.getX() + 0.5D,
					(double) worldPosition.getY() + 0.5D,
					(double) worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	/**
	 * Check if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 *
	 * @param index the slot index
	 * @param stack the <code>ItemStack</code> to insert
	 */
	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
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
	 * Fill stacked contents.
	 *
	 * @param helper the <code>RecipeItemHelper</code> instance
	 */
	@Override
	public void fillStackedContents(StackedContents helper) {
		for (ItemStack itemStack : items) {
			helper.accountStack(itemStack);
		}

	}

	/**
	 * Get capabilities.
	 *
	 * @param capability the <code>Capability</code> instance
	 * @param facing     the <code>Direction</code> the blockLocation is facing
	 * @return LazyOptional
	 */
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!remove && facing != null && capability == Capabilities.ITEM_HANDLER) {
			if (facing == Direction.UP) {
				return handlers[0].cast();
			} else if (facing == Direction.DOWN) {
				return handlers[1].cast();
			} else {
				return handlers[2].cast();
			}
		}
		return super.getCapability(capability, facing);
	}

	/**
	 * Invalidate capabilities.
	 */
	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		for (LazyOptional<? extends IItemHandler> handler : handlers) {
			handler.invalidate();
		}
	}
}