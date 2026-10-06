package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.AmmunitionForgeBlock;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionForgeRecipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.AmmunitionForgeRecipeInput;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionForgeMenu;

/// Furnace-like block entity. A material and a mold are cast into ammunition, fueled like a furnace.
public class AmmunitionForgeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

	public static final int SLOT_MATERIAL = 0;
	public static final int SLOT_MOLD = 1;
	public static final int SLOT_FUEL = 2;
	public static final int SLOT_RESULT = 3;

	public static final int DATA_LIT_TIME = 0;
	public static final int DATA_LIT_DURATION = 1;
	public static final int DATA_COOKING_PROGRESS = 2;
	public static final int DATA_COOKING_TOTAL_TIME = 3;
	public static final int DATA_COUNT = 4;

	private static final int[] SLOTS_FOR_UP = {SLOT_MATERIAL};
	private static final int[] SLOTS_FOR_DOWN = {SLOT_RESULT, SLOT_FUEL};
	private static final int[] SLOTS_FOR_SIDES = {SLOT_FUEL, SLOT_MOLD};

	protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	private int litTimeRemaining;
	private int litTotalTime;
	private int cookingProgress;
	private int cookingTotalTime = AmmunitionForgeRecipe.DEFAULT_COOKING_TIME;

	public final ContainerData containerData = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case DATA_LIT_TIME -> {
					// Data slots are synced as shorts, so preserve the ratio when the burn time is too large
					if (litTotalTime > Short.MAX_VALUE) {
						yield Mth.floor(((double) litTimeRemaining / litTotalTime) * Short.MAX_VALUE);
					}

					yield litTimeRemaining;
				}
				case DATA_LIT_DURATION -> Math.min(litTotalTime, Short.MAX_VALUE);
				case DATA_COOKING_PROGRESS -> cookingProgress;
				case DATA_COOKING_TOTAL_TIME -> cookingTotalTime;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case DATA_LIT_TIME -> litTimeRemaining = value;
				case DATA_LIT_DURATION -> litTotalTime = value;
				case DATA_COOKING_PROGRESS -> cookingProgress = value;
				case DATA_COOKING_TOTAL_TIME -> cookingTotalTime = value;
			}
		}

		@Override
		public int getCount() {
			return DATA_COUNT;
		}
	};

	private final RecipeManager.CachedCheck<AmmunitionForgeRecipeInput, AmmunitionForgeRecipe> quickCheck =
			RecipeManager.createCheck(RecipeTypeRegistry.AMMUNITION_FORGE_RECIPE_TYPE.get());

	public AmmunitionForgeBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.AMMUNITION_FORGE_BLOCK_ENTITY.get(), pos, state);
	}

	/// Check if a stack is a mold the forge accepts.
	///
	/// @param stack the `ItemStack` to check
	/// @return boolean
	public static boolean isMold(ItemStack stack) {
		return stack.is(IWItemTagGroups.AMMUNITION_MOLDS);
	}

	/// Check if a stack can be burned as fuel.
	///
	/// @param stack the `ItemStack` to check
	/// @return boolean
	public static boolean isFuel(ItemStack stack) {
		return stack.has(DataComponents.COOKING_FUEL);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(input, items);
		litTimeRemaining = input.getIntOr("lit_time_remaining", 0);
		litTotalTime = input.getIntOr("lit_total_time", 0);
		cookingProgress = input.getIntOr("cooking_progress", 0);
		cookingTotalTime = input.getIntOr("cooking_total_time", AmmunitionForgeRecipe.DEFAULT_COOKING_TIME);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		ContainerHelper.saveAllItems(output, items);
		output.putInt("lit_time_remaining", litTimeRemaining);
		output.putInt("lit_total_time", litTotalTime);
		output.putInt("cooking_progress", cookingProgress);
		output.putInt("cooking_total_time", cookingTotalTime);
	}

	public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, AmmunitionForgeBlockEntity forge) {
		boolean changed = false;

		boolean wasLit = forge.litTimeRemaining > 0;
		if (wasLit) {
			forge.litTimeRemaining--;
		}
		boolean isLit = forge.litTimeRemaining > 0;

		ItemStack material = forge.items.get(SLOT_MATERIAL);
		ItemStack mold = forge.items.get(SLOT_MOLD);
		ItemStack fuel = forge.items.get(SLOT_FUEL);

		RecipeHolder<AmmunitionForgeRecipe> recipe = null;
		ItemStack result = ItemStack.EMPTY;
		AmmunitionForgeRecipeInput input = new AmmunitionForgeRecipeInput(material, mold);

		if (!material.isEmpty() && !mold.isEmpty()) {
			recipe = forge.quickCheck.getRecipeFor(input, level).orElse(null);
			if (recipe != null) {
				result = recipe.value().assemble(input);
			}
		}

		if (recipe != null && forge.canOutput(result)) {
			int totalTime = recipe.value().cookingTime();
			if (forge.cookingTotalTime != totalTime) {
				forge.cookingTotalTime = totalTime;
				changed = true;
			}

			if (!isLit && !fuel.isEmpty()) {
				int burnTime = forge.getBurnDuration(level, fuel);
				if (burnTime > 0) {
					forge.litTimeRemaining = burnTime;
					forge.litTotalTime = burnTime;
					consumeFuel(level, pos, forge.items, fuel);
					isLit = true;
					changed = true;
				}
			}

			if (isLit) {
				forge.cookingProgress++;
				if (forge.cookingProgress >= forge.cookingTotalTime) {
					forge.cookingProgress = 0;
					forge.craft(result, material);
					changed = true;
				}
			} else {
				forge.cookingProgress = 0;
			}
		} else if (forge.cookingProgress > 0) {
			forge.cookingProgress = Mth.clamp(forge.cookingProgress - 2, 0, forge.cookingTotalTime);
		}

		if (wasLit != isLit) {
			changed = true;
			state = state.setValue(AmmunitionForgeBlock.LIT, isLit);
			level.setBlockAndUpdate(pos, state);
		}

		if (changed) {
			setChanged(level, pos, state);
		}
	}

	private boolean canOutput(ItemStack result) {
		if (result.isEmpty()) {
			return false;
		}

		ItemStack current = items.get(SLOT_RESULT);
		if (current.isEmpty()) {
			return true;
		}

		if (!ItemStack.isSameItemSameComponents(current, result)) {
			return false;
		}

		return current.getCount() + result.getCount() <= Math.min(getMaxStackSize(), result.getMaxStackSize());
	}

	/// Produce the result and consume one material. The mold is left untouched.
	private void craft(ItemStack result, ItemStack material) {
		ItemStack current = items.get(SLOT_RESULT);
		if (current.isEmpty()) {
			items.set(SLOT_RESULT, result.copy());
		} else {
			current.grow(result.getCount());
		}

		material.shrink(1);
	}

	private static void consumeFuel(ServerLevel level, BlockPos pos, NonNullList<ItemStack> items, ItemStack fuel) {
		ItemStackTemplate remainder = fuel.getCraftingRemainder();
		ItemStack newFuel = fuel;
		fuel.shrink(1);

		if (remainder != null) {
			if (fuel.isEmpty()) {
				newFuel = remainder.create();
			} else {
				Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remainder.create());
			}
		}

		items.set(SLOT_FUEL, newFuel);
	}

	private int getBurnDuration(ServerLevel level, ItemStack fuel) {
		return ResolvableInt.getFromItem(fuel, DataComponents.COOKING_FUEL, CookingFuel::burnTime, getLootContext(level, fuel), 0);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.immersiveweapons.ammunition_forge");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new AmmunitionForgeMenu(id, inventory, this, containerData);
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	@Override
	public int getContainerSize() {
		return items.size();
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return switch (slot) {
			case SLOT_MOLD -> isMold(stack);
			case SLOT_FUEL -> isFuel(stack);
			case SLOT_RESULT -> false;
			default -> true;
		};
	}

	@Override
	public int[] getSlotsForFace(Direction direction) {
		if (direction == Direction.DOWN) {
			return SLOTS_FOR_DOWN;
		}

		return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
		return canPlaceItem(slot, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
		// Only the result may be pulled out, and spent fuel containers (e.g. buckets) from the bottom
		return slot == SLOT_RESULT || (slot == SLOT_FUEL && !isFuel(stack));
	}
}
