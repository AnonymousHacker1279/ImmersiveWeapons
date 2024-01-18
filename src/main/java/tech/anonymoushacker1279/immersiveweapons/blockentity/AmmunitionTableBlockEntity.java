package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe.MaterialGroup;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionTableMenu;

import java.util.ArrayList;
import java.util.List;

public class AmmunitionTableBlockEntity extends BaseContainerBlockEntity implements EntityBlock {

	protected NonNullList<ItemStack> inventory = NonNullList.withSize(7, ItemStack.EMPTY);
	protected float densityModifier = 0.0f;
	protected NonNullList<Integer> slotCosts = NonNullList.withSize(7, 0);

	protected final ContainerData containerData = new ContainerData() {
		@Override
		public int get(int index) {
			return (int) (densityModifier * 100);
		}

		@Override
		public void set(int index, int value) {
			densityModifier = value / 100.0f;
		}

		@Override
		public int getCount() {
			return 1;
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
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new AmmunitionTableMenu(id, inventory, this, containerData);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AmmunitionTableBlockEntity(blockPos, blockState);
	}

	/**
	 * Load NBT data.
	 *
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		inventory.clear();
		ContainerHelper.loadAllItems(nbt, inventory);
		densityModifier = nbt.getFloat("densityModifier");
	}

	/**
	 * Save NBT data.
	 *
	 * @param pTag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		ContainerHelper.saveAllItems(pTag, inventory);
		pTag.putFloat("densityModifier", densityModifier);
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
	 * Get the stack in the given slot.
	 *
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public ItemStack getItem(int index) {
		return inventory.get(index);
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
		return ContainerHelper.removeItem(inventory, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 *
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(inventory, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory.
	 *
	 * @param index the slot index
	 * @param stack the <code>ItemStack</code> to set
	 */
	@Override
	public void setItem(int index, ItemStack stack) {
		inventory.set(index, stack);
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}

		setChanged();
		if (level != null) {
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
		}
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		super.saveAdditional(tag);
		ContainerHelper.saveAllItems(tag, inventory, true);
		return tag;
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

	@Override
	public void setChanged() {
		super.setChanged();
		calculateOutput();
	}

	/**
	 * Calculate the crafting result based on the input items and the density modifier.
	 * <p>
	 * One ingot produces 2.6 musket balls, and nuggets/shards produce 0.28.
	 * A higher density modifier increases the amount of resources required. Each input item has its own density.
	 */
	private void calculateOutput() {
		if (level == null || remove) {
			return;
		}

		List<AmmunitionTableRecipe> recipes = level.getRecipeManager()
				.getAllRecipesFor(RecipeTypeRegistry.AMMUNITION_TABLE_RECIPE_TYPE.get());

		// Select a recipe that matches based on the first input item
		// Determine the first non-air item in the table's inventory
		ItemStack firstStack = inventory.stream().filter(stack -> stack != ItemStack.EMPTY).findFirst().orElse(ItemStack.EMPTY);

		AmmunitionTableRecipe recipe = recipes.stream()
				.filter(r -> r.getIngredients().stream().anyMatch(ingredient -> ingredient.test(firstStack)))
				.findFirst().orElse(null);

		if (recipe == null) {
			inventory.set(6, ItemStack.EMPTY);
			return;
		}

		List<MaterialGroup> recipeMaterials = recipe.getMaterials();

		List<ItemStack> materialList = recipeMaterials.stream().collect(
				ArrayList::new,
				(list, group) -> list.addAll(List.of(group.getIngredient().getItems())),
				ArrayList::addAll
		);
		List<Float> densityList = recipeMaterials.stream().collect(
				ArrayList::new,
				(list, group) -> list.add(group.getDensity()),
				ArrayList::addAll
		);
		List<Float> baseMultiplierList = recipeMaterials.stream().collect(
				ArrayList::new,
				(list, group) -> list.add(group.getBaseMultiplier()),
				ArrayList::addAll
		);

		ItemStack result = recipe.getResultItem(level.registryAccess());

		// The size of the output stack is calculated here
		float outputSize = 0.0f;
		// The cost list stores the cost of the output, for each slot
		slotCosts.clear();

		// First, determine the maximum crafts possible with a density modifier of zero
		// So check the inventory, multiply all ingots by 2.6, and all nuggets/shards by 0.28
		int inventoryIndex = 0;
		for (ItemStack stack : inventory) {
			if (stack != ItemStack.EMPTY) {
				int materialListIndex = 0;
				for (ItemStack material : materialList) {
					if (material.getItem() == stack.getItem()) {
						float modifier = Mth.clamp((baseMultiplierList.get(materialListIndex) * stack.getCount()), 0, 64);

						outputSize += modifier;

						int slotCost = Mth.ceil(modifier / baseMultiplierList.get(materialListIndex));
						slotCosts.set(inventoryIndex, slotCost);
					}

					materialListIndex++;
				}
			}

			inventoryIndex++;
		}

		if (densityModifier > 0) {
			// Adjust the output size and slot costs. Higher densities consume more material. Pull the density values
			// from the material map.
			inventoryIndex = 0;
			for (ItemStack inventoryStack : inventory) {
				if (inventoryStack != ItemStack.EMPTY) {
					int materialListIndex = 0;
					for (ItemStack material : materialList) {
						if (material.getItem() == inventoryStack.getItem()) {
							float modifier = Mth.clamp((densityList.get(materialListIndex) * inventoryStack.getCount() * densityModifier), 0, 64);

							outputSize -= modifier;

							int slotCost = Mth.ceil((modifier / densityList.get(materialListIndex)));
							slotCosts.set(inventoryIndex, slotCost);
						}

						materialListIndex++;
					}
				}

				inventoryIndex++;
			}

			result.getOrCreateTag().putFloat("densityModifier", densityModifier);
		}

		result.setCount(Mth.floor(Mth.clamp(outputSize, 0, 64)));
		inventory.set(6, result);
	}

	/**
	 * Runs after a result stack has been taken. Decreases the stack size of each input item by the amount specified in the slotCosts list.
	 */
	public void completeCraft() {
		for (int i = 0; i < 6; i++) {
			ItemStack stack = inventory.get(i);
			if (!stack.isEmpty()) {
				stack.shrink(slotCosts.get(i));
			}
		}
	}
}