package tech.anonymoushacker1279.immersiveweapons.menu;

import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.common.MinecraftForge;
import tech.anonymoushacker1279.immersiveweapons.api.events.SmallPartsTableCraftEvent;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;

import java.util.*;

public class SmallPartsMenu extends AbstractContainerMenu {

	private static final int INV_SLOT_START = 4;
	private static final int INV_SLOT_END_USE_ROW_SLOT_START = 31;
	private static final int USE_ROW_SLOT_END = 38;
	public static final List<Pair<Item, Item>> ALL_CRAFTABLES = new ArrayList<>(15);
	private final ContainerLevelAccess access;
	final DataSlot selectedPartsPatternIndex = DataSlot.standalone();
	Runnable slotUpdateListener = () -> {
	};
	final Slot materialSlot;
	private final Slot resultSlot;
	long lastSoundTime;

	private final Container inputContainer = new SimpleContainer(1) {
		@Override
		public void setChanged() {
			super.setChanged();
			slotsChanged(this);
			slotUpdateListener.run();
		}
	};

	private final Container outputContainer = new SimpleContainer(1) {
		@Override
		public void setChanged() {
			super.setChanged();
			slotUpdateListener.run();
		}
	};

	public SmallPartsMenu(int containerID, Inventory inventory) {
		this(containerID, inventory, ContainerLevelAccess.NULL);
	}

	public SmallPartsMenu(int containerID, Inventory inventory, ContainerLevelAccess levelAccess) {
		super(MenuTypeRegistry.SMALL_PARTS_TABLE_MENU.get(), containerID);
		access = levelAccess;
		materialSlot = addSlot(new Slot(inputContainer, 0, 23, 36) {
		});
		resultSlot = addSlot(new Slot(outputContainer, 0, 143, 58) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			@Override
			public void onTake(Player player, ItemStack stack) {
				materialSlot.remove(1);
				if (!materialSlot.hasItem()) {
					selectedPartsPatternIndex.set(0);
				}

				levelAccess.execute((level, pos) -> {
					long gameTime = level.getGameTime();
					if (lastSoundTime != gameTime) {
						level.playSound(null, pos, SoundEventRegistry.SMALL_PARTS_TABLE_USED.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
						lastSoundTime = gameTime;
					}
				});

				MinecraftForge.EVENT_BUS.post(new SmallPartsTableCraftEvent(player, stack));

				super.onTake(player, stack);
			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}

		addDataSlot(selectedPartsPatternIndex);
		initializeRecipes(inventory.player.getLevel().getRecipeManager());
	}

	private void initializeRecipes(RecipeManager manager) {
		List<SmallPartsRecipe> recipes = manager.getAllRecipesFor(RecipeTypeRegistry.SMALL_PARTS_RECIPE_TYPE.get());

		for (SmallPartsRecipe recipe : recipes) {
			for (Item craftable : recipe.craftables()) {
				if (!(craftable == Items.AIR)) {
					for (ItemStack material : recipe.material().getItems()) {
						Pair<Item, Item> pair = new Pair<>(material.getItem(), craftable);
						if (!ALL_CRAFTABLES.contains(pair)) {
							ALL_CRAFTABLES.add(pair);
						}
					}
				}
			}
		}

		// Sort the recipes alphabetically
		ALL_CRAFTABLES.sort(Comparator.comparing(o -> o.getSecond().getDescriptionId()));
	}

	public List<Item> getAvailableCraftables(ItemStack stack) {
		List<Pair<Item, Item>> filteredCraftables = ALL_CRAFTABLES.stream()
				.filter(itemItemPair -> stack.is(itemItemPair.getFirst()))
				.toList();

		List<Item> craftables = new ArrayList<>(15);
		for (Pair<Item, Item> itemItemPair : filteredCraftables) {
			craftables.add(itemItemPair.getSecond());
		}

		return craftables;
	}

	public int getSelectedPartsPatternIndex() {
		return selectedPartsPatternIndex.get();
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean stillValid(Player pPlayer) {
		return stillValid(access, pPlayer, BlockRegistry.SMALL_PARTS_TABLE.get());
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		if (id >= 0 && id <= ALL_CRAFTABLES.size()) {
			selectedPartsPatternIndex.set(id);
			setupResultSlot();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void slotsChanged(Container pInventory) {
		resultSlot.set(ItemStack.EMPTY);
		selectedPartsPatternIndex.set(0);

		setupResultSlot();
		broadcastChanges();
	}

	public void registerUpdateListener(Runnable runnable) {
		slotUpdateListener = runnable;
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = slots.get(pIndex);
		if (slot.hasItem()) {
			ItemStack slotStack = slot.getItem();
			stack = slotStack.copy();
			if (pIndex == resultSlot.index) {
				if (!moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(slotStack, stack);
			} else if (pIndex != materialSlot.index) {
				if (!moveItemStackTo(slotStack, materialSlot.index, materialSlot.index + 1, false)) {
					return ItemStack.EMPTY;
				} else if (pIndex >= INV_SLOT_START && pIndex < INV_SLOT_END_USE_ROW_SLOT_START) {
					if (!moveItemStackTo(slotStack, INV_SLOT_END_USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
						return ItemStack.EMPTY;
					}
				} else if (pIndex >= INV_SLOT_END_USE_ROW_SLOT_START && pIndex < USE_ROW_SLOT_END && !moveItemStackTo(slotStack, INV_SLOT_START, INV_SLOT_END_USE_ROW_SLOT_START, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (slotStack.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(pPlayer, slotStack);
		}

		return stack;
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void removed(Player pPlayer) {
		super.removed(pPlayer);
		access.execute((level, pos) -> clearContainer(pPlayer, inputContainer));
	}

	private void setupResultSlot() {
		ItemStack material = materialSlot.getItem();
		List<Item> resultPatterns = getAvailableCraftables(material);

		if (selectedPartsPatternIndex.get() > 0 && resultPatterns.size() >= selectedPartsPatternIndex.get() - 1) {
			if (!material.isEmpty()) {
				resultSlot.set(new ItemStack(resultPatterns.get(selectedPartsPatternIndex.get() - 1)));
			}
		}

	}

	public Slot getMaterialSlot() {
		return materialSlot;
	}
}