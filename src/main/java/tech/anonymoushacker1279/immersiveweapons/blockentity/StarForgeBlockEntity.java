package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.star_forge.StarForgeControllerBlock;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu.StarForgeMenuPacketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StarForgeBlockEntity extends BaseContainerBlockEntity implements EntityBlock {

	protected NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
	protected boolean hasSolarEnergy = false;
	protected int temperature = 0;
	protected int smeltTime = 0;
	protected int menuSelectionIndex = 0;
	protected int availableRecipesMaxSize = 0;
	protected final ContainerData containerData = new ContainerData() {

		/**
		 * Retrieves the value at the specified index.
		 *
		 * @param index the index of the data to retrieve. The following indices are used:
		 *              <ul>
		 *              <li>0: Solar energy status (1 if the Star Forge has solar energy, 0 otherwise)</li>
		 *              <li>1: Temperature (0-1000)</li>
		 *              <li>2: Smelt time (0-1000)</li>
		 *              <li>3: Menu selection index</li>
		 *              <li>4: Available recipes max size</li>
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
				case 4 -> availableRecipesMaxSize;
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
				case 4 -> availableRecipesMaxSize = value;
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
		if (level.getBlockTicks().count() % 20 == 0) {
			Direction controllerDirection = state.getValue(StarForgeControllerBlock.FACING);
			hasSolarEnergy = level.isDay()
					&& level.canSeeSky(pos.above(3).relative(controllerDirection))
					&& StarForgeControllerBlock.checkForValidMultiBlock(state, pos, level);

			// If there is solar energy, the temperature should increase slowly
			// Hotter biomes should increase the temperature faster, and colder biomes should increase the temperature slower
			float biomeTemperature = level.getBiome(pos).value().getBaseTemperature();
			float temperatureModifier = 1.0f + (biomeTemperature * 0.5f);
			temperature = Mth.clamp((int) (temperature + (hasSolarEnergy ? 1 : -1) * temperatureModifier), 0, 1000);

			if (temperature == 1000 && !inventory.get(0).isEmpty()) {
				if (!state.getValue(StarForgeControllerBlock.LIT)) {
					level.setBlock(pos, state.setValue(StarForgeControllerBlock.LIT, true), 3);
				}
			} else if (state.getValue(StarForgeControllerBlock.LIT)) {
				level.setBlock(pos, state.setValue(StarForgeControllerBlock.LIT, false), 3);
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
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);

		ContainerHelper.saveAllItems(tag, inventory);
		tag.putBoolean("hasSolarEnergy", hasSolarEnergy);
		tag.putInt("temperature", temperature);
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
		containerData.set(4, availableRecipes.size());

		if (level != null && !level.isClientSide) {
			List<ResourceLocation> recipeIds = getAvailableRecipeIds();

			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK, new StarForgeMenuPacketHandler(containerId, index));
		}
	}

	/**
	 * Create a list of available recipes based on the current inputs.
	 */
	public List<RecipeHolder<StarForgeRecipe>> getAvailableRecipes(ItemStack ingot, ItemStack secondaryMaterial) {
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
		if (!availableRecipes.isEmpty() && containerData.get(1) == 1000) {
			setItem(2, availableRecipes.get(containerData.get(3)).value().getResult());
		} else {
			setItem(2, ItemStack.EMPTY);
		}
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		inventory.clear();
	}
}