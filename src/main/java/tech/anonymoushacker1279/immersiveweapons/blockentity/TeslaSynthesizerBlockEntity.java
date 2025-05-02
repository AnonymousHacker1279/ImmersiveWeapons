package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.TeslaSynthesizerRecipeInput;
import tech.anonymoushacker1279.immersiveweapons.menu.TeslaSynthesizerMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TeslaSynthesizerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {

	// Slot indices
	private static final int SLOT_INPUT_1 = 0;
	private static final int SLOT_INPUT_2 = 1;
	private static final int SLOT_INPUT_3 = 2;
	private static final int SLOT_FUEL = 3;
	private static final int SLOT_RESULT = 4;
	private static final int SLOT_COUNT = 5;

	// Slot access for automation
	private static final int[] SLOTS_UP = new int[]{SLOT_INPUT_1, SLOT_INPUT_2, SLOT_INPUT_3};
	private static final int[] SLOTS_DOWN = new int[]{SLOT_RESULT};
	private static final int[] SLOTS_HORIZONTAL = new int[]{SLOT_FUEL};

	// Burn time for different fuel types
	private static final Map<Item, Integer> FUEL_BURN_TIMES = new HashMap<>(5);

	// Inventory and process tracking
	private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
	private int burnTime;
	private int burnTimeTotal;
	private int cookTime;
	private int cookTimeTotal = 200; // Default cook time

	// Recipe usage tracking
	private final Map<ResourceLocation, Integer> usedRecipes = new HashMap<>(5);

	// Container data for the GUI
	public final ContainerData containerData = new ContainerData() {
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
				case 0 -> burnTime = value;
				case 1 -> burnTimeTotal = value;
				case 2 -> cookTime = value;
				case 3 -> cookTimeTotal = value;
			}
		}

		@Override
		public int getCount() {
			return 4;
		}
	};

	static {
		// Initialize burn times
		FUEL_BURN_TIMES.put(ItemRegistry.MOLTEN_INGOT.get(), 24000); // 20 minutes
	}

	public TeslaSynthesizerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.TESLA_SYNTHESIZER_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Checks if an item is valid fuel for the Tesla Synthesizer
	 *
	 * @param stack The ItemStack to check
	 * @return true if the item is valid fuel
	 */
	public static boolean isFuel(ItemStack stack) {
		return FUEL_BURN_TIMES.containsKey(stack.getItem());
	}

	/**
	 * Gets the burn time for a fuel item
	 *
	 * @param fuel The fuel ItemStack
	 * @return The burn time in ticks, or 0 if not a valid fuel
	 */
	private static int getBurnTime(ItemStack fuel) {
		if (fuel.isEmpty()) {
			return 0;
		}
		return FUEL_BURN_TIMES.getOrDefault(fuel.getItem(), 0);
	}

	/**
	 * Processes recipes and manages fuel consumption each tick
	 */
	public void tick(ServerLevel level) {
		boolean wasBurning = isBurning();
		boolean inventoryChanged = false;

		// Decrease burn time if burning
		if (isBurning()) {
			--burnTime;
		}

		ItemStack fuel = items.get(SLOT_FUEL);
		ItemStack input1 = items.get(SLOT_INPUT_1);
		ItemStack input2 = items.get(SLOT_INPUT_2);
		ItemStack input3 = items.get(SLOT_INPUT_3);

		// Only proceed if we have input materials
		if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) {
			// Get a matching recipe
			Optional<RecipeHolder<TeslaSynthesizerRecipe>> recipeHolder = level.recipeAccess()
					.getRecipeFor(RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get(),
							new TeslaSynthesizerRecipeInput(input1, input2, input3), level);

			TeslaSynthesizerRecipe recipe = recipeHolder.map(RecipeHolder::value).orElse(null);

			// If we have a valid recipe
			if (recipe != null) {
				// Start burning fuel if needed
				if (!isBurning() && canSmelt(recipe)) {
					burnTime = getBurnTime(fuel);
					burnTimeTotal = burnTime;

					if (isBurning()) {
						inventoryChanged = true;

						if (!fuel.isEmpty()) {
							Item fuelItem = fuel.getItem();
							fuel.shrink(1);

							if (fuel.isEmpty()) {
								items.set(SLOT_FUEL, fuelItem.getCraftingRemainder());
							}
						}
					}
				}

				// Progress cooking if burning
				if (isBurning() && canSmelt(recipe)) {
					// Update cook time if it changed in the recipe
					if (cookTimeTotal != recipe.getCookTime()) {
						cookTimeTotal = recipe.getCookTime();
					}

					// Increment cooking progress
					cookTime++;

					// Process the recipe when cooking is complete
					if (cookTime >= cookTimeTotal) {
						cookTime = 0;
						smeltRecipe(recipe);
						inventoryChanged = true;

						// Track recipe usage
						ResourceLocation recipeId = recipeHolder.get().id().location();
						usedRecipes.put(recipeId, usedRecipes.getOrDefault(recipeId, 0) + 1);
					}
				} else {
					// Reset cooking progress if we can't smelt
					cookTime = 0;
				}
			} else {
				// No matching recipe, reset cooking progress
				cookTime = 0;
			}
		} else if (!isBurning() && cookTime > 0) {
			// If not burning and we were cooking, decrease cookTime
			cookTime = Mth.clamp(cookTime - 2, 0, cookTimeTotal);
		}

		// Update block state if burning state changed
		if (wasBurning != isBurning()) {
			inventoryChanged = true;
		}

		if (inventoryChanged) {
			setChanged();
		}
	}

	/**
	 * Processes a recipe, consuming inputs and producing output
	 *
	 * @param recipe The recipe to process
	 */
	private void smeltRecipe(TeslaSynthesizerRecipe recipe) {
		if (!canSmelt(recipe)) {
			return;
		}

		ItemStack resultStack = recipe.result();
		ItemStack outputSlot = items.get(SLOT_RESULT);

		// Add to existing output stack or create new one
		if (outputSlot.isEmpty()) {
			items.set(SLOT_RESULT, resultStack.copy());
		} else if (outputSlot.is(resultStack.getItem())) {
			outputSlot.grow(resultStack.getCount());
		}

		// Consume input materials
		items.get(SLOT_INPUT_1).shrink(1);
		items.get(SLOT_INPUT_2).shrink(1);
		items.get(SLOT_INPUT_3).shrink(1);
	}

	/**
	 * Determines if a recipe can be processed with current inventory
	 *
	 * @param recipe The recipe to check
	 * @return true if the recipe can be processed
	 */
	private boolean canSmelt(@Nullable TeslaSynthesizerRecipe recipe) {
		if (recipe == null) {
			return false;
		}

		// Check if we have all input ingredients
		if (items.get(SLOT_INPUT_1).isEmpty() ||
				items.get(SLOT_INPUT_2).isEmpty() ||
				items.get(SLOT_INPUT_3).isEmpty()) {
			return false;
		}

		// Get the recipe result
		ItemStack resultStack = recipe.result();
		if (resultStack.isEmpty()) {
			return false;
		}

		// Check if the output slot can accept the result
		ItemStack outputStack = items.get(SLOT_RESULT);
		if (outputStack.isEmpty()) {
			return true;
		}

		// Check if the output item matches and has space
		if (!outputStack.is(resultStack.getItem())) {
			return false;
		}

		int combinedCount = outputStack.getCount() + resultStack.getCount();
		return (combinedCount <= getMaxStackSize() && combinedCount <= outputStack.getMaxStackSize());
	}

	/**
	 * Checks if fuel is currently burning
	 *
	 * @return true if burning
	 */
	public boolean isBurning() {
		return burnTime > 0;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return switch (side) {
			case UP -> SLOTS_UP;
			case DOWN -> SLOTS_DOWN;
			default -> SLOTS_HORIZONTAL;
		};
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
		return canPlaceItem(slot, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
		return slot == SLOT_RESULT;
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		if (slot == SLOT_RESULT) {
			return false;
		} else if (slot == SLOT_FUEL) {
			return isFuel(stack);
		} else {
			return true; // Allow any item in the input slots
		}
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.immersiveweapons.tesla_synthesizer");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items.clear();
		this.items.addAll(items);
	}

	@Override
	public int getContainerSize() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : items) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return items.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		return ContainerHelper.removeItem(items, slot, amount);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(items, slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		ItemStack existingStack = items.get(slot);
		boolean itemChanged = !stack.isEmpty() && !ItemStack.isSameItemSameComponents(stack, existingStack);

		items.set(slot, stack);

		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}

		// Reset cooking progress when inputs change
		if (itemChanged && (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2 || slot == SLOT_INPUT_3)) {
			cookTime = 0;
			setChanged();
		}
	}

	@Override
	public boolean stillValid(Player player) {
		if (level == null || level.getBlockEntity(worldPosition) != this) {
			return false;
		}

		return player.distanceToSqr(
				worldPosition.getX() + 0.5,
				worldPosition.getY() + 0.5,
				worldPosition.getZ() + 0.5) <= 64.0;
	}

	@Override
	public void clearContent() {
		items.clear();
	}

	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);

		// Load inventory
		ContainerHelper.loadAllItems(tag, items, provider);

		// Load process state
		burnTime = tag.getInt("BurnTime");
		cookTime = tag.getInt("CookTime");
		cookTimeTotal = tag.getInt("CookTimeTotal");
		burnTimeTotal = getBurnTime(items.get(SLOT_FUEL));

		// Load recipe usage data
		CompoundTag recipesTag = tag.getCompound("RecipesUsed");
		for (String key : recipesTag.getAllKeys()) {
			usedRecipes.put(ResourceLocation.parse(key), recipesTag.getInt(key));
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);

		// Save inventory
		ContainerHelper.saveAllItems(tag, items, provider);

		// Save process state
		tag.putInt("BurnTime", burnTime);
		tag.putInt("CookTime", cookTime);
		tag.putInt("CookTimeTotal", cookTimeTotal);

		// Save recipe usage data
		CompoundTag recipesTag = new CompoundTag();
		usedRecipes.forEach((recipeId, count) -> recipesTag.putInt(recipeId.toString(), count));
		tag.put("RecipesUsed", recipesTag);
	}

	@Override
	public void fillStackedContents(net.minecraft.world.entity.player.StackedItemContents contents) {
		for (ItemStack itemstack : items) {
			contents.accountStack(itemstack);
		}
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new TeslaSynthesizerMenu(id, inventory, this, containerData);
	}
}