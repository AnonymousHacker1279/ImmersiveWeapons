package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe.MaterialGroup;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.AmmunitionTableRecipeInput;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionTableMenu;

import java.util.List;
import java.util.Optional;

public class AmmunitionTableBlockEntity extends BaseContainerBlockEntity implements EntityBlock {

	protected final NonNullList<ItemStack> inventory = NonNullList.withSize(7, ItemStack.EMPTY);
	protected float densityModifier = 0.0f;
	public int excessStackSize = 0;
	public ItemStack excessStack = ItemStack.EMPTY;
	protected final NonNullList<Integer> slotCosts = NonNullList.withSize(7, 0);

	final DataComponentType<Float> DENSITY_MODIFIER = DataComponentTypeRegistry.DENSITY_MODIFIER.get();

	public final ContainerData containerData = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> (int) (densityModifier * 100);
				case 1 -> excessStackSize;
				default -> throw new IllegalStateException("Unexpected value: " + index);
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> densityModifier = value / 100.0f;
				case 1 -> excessStackSize = value;
				default -> throw new IllegalStateException("Unexpected value: " + index);
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public AmmunitionTableBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.AMMUNITION_TABLE_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.immersiveweapons.ammunition_table");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		for (int i = 0; i < Math.min(stacks.size(), inventory.size()); i++) {
			inventory.set(i, stacks.get(i));
		}
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new AmmunitionTableMenu(id, inventory, this, containerData);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AmmunitionTableBlockEntity(blockPos, blockState);
	}

	@Override
	public void loadAdditional(CompoundTag tag, Provider provider) {
		super.loadAdditional(tag, provider);
		inventory.clear();
		ContainerHelper.loadAllItems(tag, inventory, provider);
		densityModifier = tag.getFloat("densityModifier");
		containerData.set(1, tag.getInt("excessStackSize"));

		if (tag.contains("excessStack")) {
			String itemName = tag.getString("excessStack");
			excessStack = BuiltInRegistries.ITEM.getValue(ResourceLocation.parse(itemName)).getDefaultInstance();
			excessStack.set(DENSITY_MODIFIER, densityModifier);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, Provider provider) {
		super.saveAdditional(tag, provider);
		ContainerHelper.saveAllItems(tag, inventory, provider);
		tag.putFloat("densityModifier", densityModifier);
		tag.putInt("excessStackSize", excessStackSize);
		tag.putString("excessStack", excessStack.getItemHolder().getRegisteredName());
	}

	/**
	 * Get the number of slots in the inventory.
	 *
	 * @return int
	 */
	@Override
	public int getContainerSize() {
		return inventory.size();
	}

	/**
	 * Check if the inventory is empty.
	 *
	 * @return boolean
	 */
	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : inventory) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Get the ingredient in the given slot.
	 *
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public ItemStack getItem(int index) {
		return inventory.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new ingredient.
	 *
	 * @param index the slot index
	 * @param count the number to remove
	 * @return ItemStack
	 */
	@Override
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(inventory, index, count);
	}

	/**
	 * Removes a ingredient from the given slot and returns it.
	 *
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(inventory, index);
	}

	/**
	 * Sets the given item ingredient to the specified slot in the inventory.
	 *
	 * @param index the slot index
	 * @param stack the <code>ItemStack</code> to set
	 */
	@Override
	public void setItem(int index, ItemStack stack) {
		inventory.set(index, stack);
	}

	@Override
	public CompoundTag getUpdateTag(Provider provider) {
		CompoundTag tag = new CompoundTag();
		super.saveAdditional(tag, provider);
		ContainerHelper.saveAllItems(tag, inventory, provider);
		tag.putFloat("densityModifier", densityModifier);
		return tag;
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (level != null) {
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
		}
	}

	public NonNullList<ItemStack> getInventory() {
		return inventory;
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	/**
	 * Clear the inventory.
	 */
	@Override
	public void clearContent() {
		inventory.clear();
	}

	/**
	 * Try to find a valid recipe based on the first item in the inventory.
	 *
	 * @param serverLevel the <code>ServerLevel</code> instance
	 * @return RecipeHolder
	 */
	private @Nullable RecipeHolder<AmmunitionTableRecipe> getValidRecipe(ServerLevel serverLevel) {
		// Create a temporary inventory to hold the items that only include the first 6 slots
		NonNullList<ItemStack> tempInventory = NonNullList.withSize(6, ItemStack.EMPTY);
		for (int i = 0; i < 6; i++) {
			tempInventory.set(i, inventory.get(i));
		}

		Optional<RecipeHolder<AmmunitionTableRecipe>> optional = serverLevel.recipeAccess()
				.getRecipeFor(RecipeTypeRegistry.AMMUNITION_TABLE_RECIPE_TYPE.get(), new AmmunitionTableRecipeInput(tempInventory), serverLevel);

		return optional.orElse(null);
	}

	/**
	 * Calculates the output size based on the current recipe and available inventory materials. It will additionally
	 * set slot costs for each input item.
	 *
	 * @param materialGroups the list of <code>MaterialGroup</code> instances
	 * @return int
	 */
	private int calculateOutputSize(List<MaterialGroup> materialGroups) {
		NonNullList<Integer> outputPerSlot = NonNullList.withSize(6, 0);

		for (int i = 0; i < 6; i++) {
			ItemStack stack = inventory.get(i);
			if (!stack.isEmpty()) {
				for (MaterialGroup materialGroup : materialGroups) {
					if (materialGroup.ingredient().test(stack)) {
						int output = (int) Math.floor(((stack.getCount() * (1.0f + materialGroup.density())) * materialGroup.baseMultiplier()) / (1.0f + densityModifier));
						outputPerSlot.set(i, output);

						slotCosts.set(i, stack.getCount());
					}
				}
			}
		}

		return outputPerSlot.stream().mapToInt(Integer::intValue).sum();
	}

	/**
	 * Calculate the output item based on the given inputs.
	 */
	public void calculateOutput(boolean didCraft) {
		if (level == null || remove) {
			return;
		}

		if (level instanceof ServerLevel serverLevel) {
			RecipeHolder<AmmunitionTableRecipe> recipe = getValidRecipe(serverLevel);

			if (recipe == null) {
				if (excessStack != ItemStack.EMPTY && didCraft) {
					handleExcess();
				} else {
					inventory.set(6, ItemStack.EMPTY);
					containerData.set(1, 0);
				}

				return;
			}

			List<MaterialGroup> recipeMaterials = recipe.value().materials();
			ItemStack result = recipe.value().result().copy();

			slotCosts.clear();
			int outputSize = calculateOutputSize(recipeMaterials);

			if (outputSize > 99) {
				containerData.set(1, outputSize - 99);
				outputSize = 99;
				excessStack = result.copy();

				if (densityModifier > 0) {
					excessStack.set(DENSITY_MODIFIER, densityModifier);
				}
			} else {
				containerData.set(1, 0);
			}

			if (densityModifier > 0) {
				result.set(DENSITY_MODIFIER, densityModifier);
			}

			result.setCount(outputSize);
			inventory.set(6, result);
		}
	}

	/**
	 * Runs after a result ingredient has been taken. Decreases the ingredient size of each input item by the amount
	 * specified in the slotCosts list.
	 */
	public void depleteMaterials() {
		for (int i = 0; i < 6; i++) {
			ItemStack stack = inventory.get(i);
			if (!stack.isEmpty()) {
				stack.shrink(slotCosts.get(i));
			}
		}
	}

	/**
	 * Handle excess output that cannot fit in the output slot.
	 */
	public void handleExcess() {
		int excess = containerData.get(1);
		if (excess > 0 && !excessStack.isEmpty()) {
			// Drop excess items on the ground, splitting by max stack size
			int maxStackSize = excessStack.getMaxStackSize();
			while (excess > 0) {
				int dropCount = Math.min(excess, maxStackSize);
				ItemStack dropStack = excessStack.copyWithCount(dropCount);
				dropStack.set(DENSITY_MODIFIER, densityModifier);
				excess -= dropCount;

				// Drop the item in the world
				if (level != null) {
					BlockPos pos = getBlockPos();
					level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), dropStack));
				}

				// Update the excess stack size
				excessStackSize = excess;

				if (excess == 0) {
					excessStack = ItemStack.EMPTY;
					containerData.set(1, 0);
				} else {
					containerData.set(1, excess);
				}
			}
		}
	}

	/**
	 * Checks if there are no materials present (first six slots)
	 *
	 * @return boolean
	 */
	public boolean hasNoMaterials() {
		return inventory.stream().limit(6).allMatch(ItemStack::isEmpty);
	}
}