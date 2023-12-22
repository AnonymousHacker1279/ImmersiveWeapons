package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.star_forge.StarForgeControllerBlock;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StarForgeBlockEntity extends BaseContainerBlockEntity implements EntityBlock {

	protected NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
	protected boolean hasSolarEnergy = false;
	protected int temperature = 0;
	protected int smeltTime = 0;
	protected int menuSelectionIndex = 0;
	protected boolean inUse = false;
	protected final ContainerData containerData = new ContainerData() {

		/**
		 * Retrieves the value at the specified index.
		 *
		 * @param index the index of the data to retrieve. The following indices are used:
		 *              <ul>
		 *              <li>0: Solar energy status (1 if the Star Forge has solar energy, 0 otherwise)</li>
		 *              <li>1: Temperature (0-1000)</li>
		 *              <li>2: Smelt time</li>
		 *              <li>3: Menu selection index</li>
		 *              <li>4: In use status (1 if it is being used, 0 otherwise)</li>
		 *              </ul>
		 * @return the value at the specified index
		 */
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> hasSolarEnergy ? 1 : 0;
				case 1 -> temperature;
				case 2 -> smeltTime;
				case 3 -> menuSelectionIndex;
				case 4 -> inUse ? 1 : 0;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> hasSolarEnergy = value == 1;
				case 1 -> temperature = value;
				case 2 -> smeltTime = value;
				case 3 -> menuSelectionIndex = value;
				case 4 -> inUse = value == 1;
			}
		}

		@Override
		public int getCount() {
			return 5;
		}
	};

	public static final List<RecipeHolder<StarForgeRecipe>> ALL_RECIPES = new ArrayList<>(20);
	public List<RecipeHolder<StarForgeRecipe>> availableRecipes = new ArrayList<>(20);

	public StarForgeBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.STAR_FORGE_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	public void initializeRecipes(RecipeManager manager) {
		ALL_RECIPES.clear();
		ALL_RECIPES.addAll(manager.getAllRecipesFor(RecipeTypeRegistry.STAR_FORGE_RECIPE_TYPE.get()));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new StarForgeBlockEntity(pos, state);
	}

	public void tick(ServerLevel level, BlockPos pos, BlockState state) {
		if (level.getGameTime() % 20 == 0) {
			Direction controllerDirection = state.getValue(StarForgeControllerBlock.FACING);
			hasSolarEnergy = level.isDay()
					&& level.canSeeSky(pos.above(3).relative(controllerDirection))
					&& StarForgeControllerBlock.checkForValidMultiBlock(state, pos, level);

			float biomeTemperature = level.getBiome(pos).value().getBaseTemperature();
			float temperatureModifier = 1.0f + (biomeTemperature * 5);
			temperature = Mth.clamp((int) (temperature + (hasSolarEnergy ? 1 : -1) * temperatureModifier), 0, 1000);

			if (temperature == 1000 && !inventory.get(0).isEmpty()) {
				if (!state.getValue(StarForgeControllerBlock.LIT)) {
					level.setBlock(pos, state.setValue(StarForgeControllerBlock.LIT, true), 3);
				}
			} else if (state.getValue(StarForgeControllerBlock.LIT)) {
				level.setBlock(pos, state.setValue(StarForgeControllerBlock.LIT, false), 3);
			}
		}

		if (smeltTime > 0 && temperature < 1000) {
			smeltTime = 0;
		}

		// If smelting, decrement the smelting time
		if (smeltTime > 0) {
			smeltTime--;
			// If smelting time reached 0, update the result
			if (containerData.get(2) == 0) {
				updateResult();

				// Decrement the input slots
				inventory.get(0).shrink(availableRecipes.get(menuSelectionIndex).value().ingotCount);
				inventory.get(1).shrink(availableRecipes.get(menuSelectionIndex).value().secondaryMaterialCount);

				// Reset the menu selection index
				menuSelectionIndex = 0;

				// Reset the temperature to 25%
				temperature = 250;
			}
		}
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);

		inventory.clear();
		ContainerHelper.loadAllItems(tag, inventory);
		hasSolarEnergy = tag.getBoolean("hasSolarEnergy");
		temperature = tag.getInt("temperature");

		if (level != null) {
			availableRecipes.clear();

			// Load availableRecipes
			ListTag availableRecipesTag = tag.getList("availableRecipes", 10);
			for (int i = 0; i < availableRecipesTag.size(); i++) {
				CompoundTag recipeTag = availableRecipesTag.getCompound(i);
				ResourceLocation recipeId = new ResourceLocation(recipeTag.getString("recipeId"));

				// Get the recipe from the recipe manager
				level.getRecipeManager().byKey(recipeId).ifPresent(recipeHolder -> {
					if (recipeHolder.value() instanceof StarForgeRecipe recipe) {
						availableRecipes.add(new RecipeHolder<>(recipeId, recipe));
					}
				});
			}

			initializeRecipes(level.getRecipeManager());
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);

		ContainerHelper.saveAllItems(tag, inventory);
		tag.putBoolean("hasSolarEnergy", hasSolarEnergy);
		tag.putInt("temperature", temperature);

		// Save availableRecipes
		ListTag availableRecipesTag = new ListTag();
		for (RecipeHolder<StarForgeRecipe> holder : availableRecipes) {
			CompoundTag recipeTag = new CompoundTag();
			recipeTag.putString("recipeId", holder.id().toString());
			availableRecipesTag.add(recipeTag);
		}
		tag.put("availableRecipes", availableRecipesTag);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		saveAdditional(tag);
		return tag;
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.immersiveweapons.star_forge");
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return new StarForgeMenu(containerId, inventory, this, this.containerData);
	}

	@Override
	public int getContainerSize() {
		return inventory.size();
	}

	@Override
	public void setChanged() {
		super.setChanged();

		availableRecipes = getAvailableRecipes(inventory.get(0), inventory.get(1));

		if (level != null) {
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
		}
	}

	/**
	 * Create a list of available recipes based on the current inputs.
	 */
	public List<RecipeHolder<StarForgeRecipe>> getAvailableRecipes(ItemStack ingot, ItemStack secondaryMaterial) {
		if (ALL_RECIPES.isEmpty() && level != null) {
			initializeRecipes(level.getRecipeManager());
		}

		List<RecipeHolder<StarForgeRecipe>> availableRecipes = new ArrayList<>(ALL_RECIPES.size());

		for (RecipeHolder<StarForgeRecipe> holder : ALL_RECIPES) {
			StarForgeRecipe recipe = holder.value();

			if (ingot.getItem().equals(recipe.getIngot().getItem()) && secondaryMaterial.getItem().equals(recipe.getSecondaryMaterial().getItem())) {
				availableRecipes.add(holder);
			}
		}

		return availableRecipes;
	}

	public List<ResourceLocation> getAvailableRecipeIds() {
		if (availableRecipes.isEmpty()) {
			availableRecipes = getAvailableRecipes(inventory.get(0), inventory.get(1));
		}

		return availableRecipes.stream()
				.map(RecipeHolder::id)
				.collect(Collectors.toList());
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : inventory) {
			if (!stack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return inventory.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		return ContainerHelper.removeItem(inventory, slot, amount);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(inventory, slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		inventory.set(slot, stack);

		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}

		setChanged();
		if (level != null) {
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
		}
	}

	public void updateResult() {
		if (!availableRecipes.isEmpty() && containerData.get(1) == 1000 && containerData.get(2) == 0) {
			StarForgeRecipe recipe = availableRecipes.get(containerData.get(3)).value();
			// Check if the inputs are sufficient
			if (recipe.ingotCount <= inventory.get(0).getCount() && recipe.secondaryMaterialCount <= inventory.get(1).getCount()) {
				// Set the result slot
				setItem(2, recipe.getResult());
			} else {
				setItem(2, ItemStack.EMPTY);
			}
		} else {
			setItem(2, ItemStack.EMPTY);
		}
	}

	public void raiseTemperature(int amount) {
		temperature = Mth.clamp(temperature + amount, 0, 1000);
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		inventory.clear();
	}

	public boolean isInUse() {
		return inUse;
	}
}