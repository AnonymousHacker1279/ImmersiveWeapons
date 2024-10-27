package tech.anonymoushacker1279.immersiveweapons.menu;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.blockentity.StarForgeBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.MenuTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;
import tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge.StarForgeMenuPayload;
import tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge.StarForgeUpdateRecipesPayload;

import java.util.*;

public class StarForgeMenu extends AbstractContainerMenu {

	public final Container container;
	private final ContainerData containerData;
	private final Player player;

	public List<StarForgeRecipe> availableRecipes = new ArrayList<>(25);

	public StarForgeMenu(int containerID, Inventory inventory, List<ResourceLocation> availableRecipeLocations) {
		this(containerID, inventory, new SimpleContainer(3), new SimpleContainerData(5));
		populateAvailableRecipes(availableRecipeLocations, inventory.player.level(), availableRecipes);
	}

	public StarForgeMenu(int containerID, Inventory inventory, Container container, ContainerData containerData) {
		super(MenuTypeRegistry.STAR_FORGE_MENU.get(), containerID);
		this.container = container;
		this.containerData = containerData;
		player = inventory.player;

		// Primary input slot at (8, 52)
		addSlot(new Slot(container, 0, 8, 52) {
			@Override
			public void setChanged() {
				super.setChanged();
				slotsChanged(container);
			}
		});

		// Secondary input slot at (34, 52)
		addSlot(new Slot(container, 1, 34, 52) {
			@Override
			public void setChanged() {
				super.setChanged();
				slotsChanged(container);
			}
		});

		// Result slot at (143, 30)
		addSlot(new Slot(container, 2, 143, 30) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		// Player inventory slots
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// Player hotbar slots
		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}

		addDataSlots(containerData);

		if (container instanceof StarForgeBlockEntity starForgeBlockEntity) {
			containerData.set(4, 1);    // Set as in use

			for (RecipeHolder<StarForgeRecipe> holder : starForgeBlockEntity.availableRecipes) {
				availableRecipes.add(holder.value());
			}
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);

		if (slot.hasItem()) {
			ItemStack oldStack = slot.getItem();
			itemStack = oldStack.copy();

			if (index < container.getContainerSize()) {
				if (!moveItemStackTo(oldStack, container.getContainerSize(), slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(oldStack, 0, container.getContainerSize(), false)) {
				return ItemStack.EMPTY;
			}

			if (oldStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			slot.onTake(player, oldStack);
		}

		return itemStack;
	}

	@Override
	public void slotsChanged(Container container) {
		super.slotsChanged(container);

		if (container instanceof StarForgeBlockEntity starForgeBlockEntity) {
			availableRecipes.clear();
			for (RecipeHolder<StarForgeRecipe> holder : starForgeBlockEntity.availableRecipes) {
				availableRecipes.add(holder.value());
			}

			// Send the packet to the client
			PacketDistributor.sendToPlayersNear(
					(ServerLevel) starForgeBlockEntity.getLevel(),
					null,
					starForgeBlockEntity.getBlockPos().getX(),
					starForgeBlockEntity.getBlockPos().getY(),
					starForgeBlockEntity.getBlockPos().getZ(),
					16,
					new StarForgeUpdateRecipesPayload(player.getUUID(), containerId, starForgeBlockEntity.getAvailableRecipeIds())
			);

			// If there is a recipe already being crafted, cancel it
			if (containerData.get(2) > 0) {
				containerData.set(2, 0);
			}
		}
	}

	@Override
	public void removed(Player player) {
		super.removed(player);

		if (!player.level().isClientSide) {
			containerData.set(4, 0);    // Set as no longer in use
		}
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return container.stillValid(pPlayer);
	}

	public void setMenuSelectionIndex(int index, boolean beginCrafting) {
		index = Mth.clamp(index, 0, availableRecipes.size() - 1);
		if (index == -1) {
			index = 0;
		}

		containerData.set(3, index);

		// Send the packet to the server
		PacketDistributor.sendToServer(new StarForgeMenuPayload(containerId, index, beginCrafting));
	}

	public boolean hasSolarEnergy() {
		return containerData.get(0) > 0;
	}

	public int getTemperature() {
		return containerData.get(1);
	}

	public int getSmeltTime() {
		return containerData.get(2);
	}

	public int getMenuSelectionIndex() {
		return containerData.get(3);
	}

	public static void populateAvailableRecipes(List<ResourceLocation> recipeLocations, Level level, List<StarForgeRecipe> availableRecipes) {
		for (ResourceLocation location : recipeLocations) {
			Optional<StarForgeRecipe> recipe = level.getRecipeManager().byKey(location).map(recipeHolder -> {
				if (recipeHolder.value() instanceof StarForgeRecipe starForgeRecipe) {
					return starForgeRecipe;
				}
				return null;
			});
			recipe.ifPresent(availableRecipes::add);
		}
	}

	public static void updateServer(ServerPlayer player, int containerId, int menuSelectionIndex, boolean beginCrafting) {
		// Get the menu from the container ID
		AbstractContainerMenu menu = player.containerMenu;

		if (menu.containerId == containerId && menu instanceof StarForgeMenu starForgeMenu) {
			starForgeMenu.containerData.set(3, menuSelectionIndex);
			if (beginCrafting) {
				// Start the smelting process
				StarForgeRecipe selectedRecipe = starForgeMenu.availableRecipes.get(menuSelectionIndex);
				starForgeMenu.containerData.set(2, selectedRecipe.smeltTime());
			}
			starForgeMenu.container.setChanged();
		}
	}
}