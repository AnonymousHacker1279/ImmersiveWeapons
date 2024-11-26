package tech.anonymoushacker1279.immersiveweapons.menu;

import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.config.ServerConfig;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.concurrent.atomic.AtomicInteger;

public class CelestialAltarMenu extends AbstractContainerMenu {

	private final ContainerLevelAccess access;
	private final Slot inputSlot;
	private final Slot fragmentSlot;
	private final Slot bookSlot;
	private final Slot resultSlot;
	private final Container inputContainer = new SimpleContainer(3) {
		@Override
		public void setChanged() {
			super.setChanged();
			slotsChanged(this);
		}
	};
	private final ResultContainer resultContainer = new ResultContainer();
	private final ContainerData containerData;
	private final RegistryLookup<Enchantment> enchantmentLookup;

	public CelestialAltarMenu(int containerId, Inventory inventory, RegistryLookup<Enchantment> lookup) {
		this(containerId, inventory, ContainerLevelAccess.NULL, lookup);
	}

	public CelestialAltarMenu(int containerId, Inventory inventory, ContainerLevelAccess levelAccess, RegistryLookup<Enchantment> lookup) {
		super(MenuTypeRegistry.CELESTIAL_ALTAR_MENU.get(), containerId);
		access = levelAccess;
		containerData = new SimpleContainerData(2);
		enchantmentLookup = lookup;

		inputSlot = addSlot(new Slot(inputContainer, 0, 12, 28) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.isEnchanted();
			}

			@Override
			public void setChanged() {
				super.setChanged();

				if (hasItem()) {
					containerData.set(1, 1);
				} else {
					containerData.set(1, 0);
				}
			}
		});

		fragmentSlot = addSlot(new Slot(inputContainer, 1, 80, 11) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == ItemRegistry.CELESTIAL_FRAGMENT.get() && !bookSlot.hasItem();
			}
		});

		bookSlot = addSlot(new Slot(inputContainer, 2, 80, 45) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == Items.ENCHANTED_BOOK && !fragmentSlot.hasItem();
			}
		});

		resultSlot = addSlot(new Slot(resultContainer, 3, 144, 27) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			@Override
			public void onTake(Player player, ItemStack stack) {
				super.onTake(player, stack);

				if (fragmentSlot.hasItem()) {
					fragmentSlot.remove(containerData.get(0));
				} else if (bookSlot.hasItem()) {
					bookSlot.remove(1);
				}

				inputSlot.remove(1);
				containerData.set(0, 0);
				containerData.set(1, 0);
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

		addDataSlots(containerData);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = slots.get(index);

		if (slot.hasItem()) {
			ItemStack slotStack = slot.getItem();
			stack = slotStack.copy();

			if (index == 3) {
				if (!moveItemStackTo(slotStack, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(slotStack, stack);
			} else if (index != 0 && index != 1 && index != 2) {
				if (slotStack.getItem() == Items.ENCHANTED_BOOK) {
					if (!moveItemStackTo(slotStack, 2, 3, false)) {
						return ItemStack.EMPTY;
					}
				} else if (slotStack.getItem() == ItemRegistry.CELESTIAL_FRAGMENT.get()) {
					if (!moveItemStackTo(slotStack, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!moveItemStackTo(slotStack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(slotStack, 4, 40, false)) {
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

			slot.onTake(player, slotStack);
		}

		return stack;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(access, player, BlockRegistry.CELESTIAL_ALTAR.get());
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		resultContainer.clearContent();
		access.execute((level, pos) -> clearContainer(player, inputContainer));
	}

	@Override
	public void slotsChanged(Container container) {
		super.slotsChanged(container);

		if (container == inputContainer) {
			resultContainer.clearContent();
			calculateOutput();
		}
	}

	public int getFragmentCost() {
		return containerData.get(0);
	}

	public boolean hasInputItem() {
		return containerData.get(1) == 1;
	}

	public void calculateOutput() {
		ItemStack input = inputSlot.getItem();
		ItemStack fragment = fragmentSlot.getItem();
		ItemStack book = bookSlot.getItem();
		ItemStack result = input.copy();

		if (book.isEmpty()) {
			if (tryUpgradeEnchantments(fragment, result)) {
				resultSlot.set(result);
			}
		} else if (fragment.isEmpty()) {
			if (tryAddEnchantments(input, book, result)) {
				resultSlot.set(result);
			}
		}
	}

	private boolean tryUpgradeEnchantments(ItemStack fragment, ItemStack result) {
		ItemEnchantments enchantments = result.getTagEnchantments();

		enchantments.keySet().forEach(enchantmentHolder -> {
			int maxLevel = ServerConfig.getEnchantCap(enchantmentHolder.getRegisteredName());
			int currentLevel = enchantments.getLevel(enchantmentHolder);

			if (maxLevel == -1) {
				EnchantmentHelper.updateEnchantments(result, mutable -> mutable.upgrade(enchantmentHolder, currentLevel + 1));
			} else {
				EnchantmentHelper.updateEnchantments(result, mutable -> mutable.upgrade(enchantmentHolder, Math.min(currentLevel + 1, maxLevel)));
			}
		});

		// Add up the total levels of all enchantments
		int totalEnchantmentLevels = GeneralUtilities.getTotalEnchantmentLevels(enchantmentLookup, result);

		// The item cost rises exponentially with higher enchantment levels
		containerData.set(0, Math.min(IWConfigs.SERVER.celestialAltarMaxEnchantUpgradeCost.getAsInt(), (int) Math.pow(1.3, ((float) totalEnchantmentLevels / 2))));

		return fragment.getCount() >= containerData.get(0);
	}

	private boolean tryAddEnchantments(ItemStack input, ItemStack book, ItemStack result) {
		ItemEnchantments enchantments = result.getTagEnchantments();
		ItemEnchantments bookEnchantments = book.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);

		AtomicInteger enchantmentsAdded = new AtomicInteger();
		bookEnchantments.keySet().forEach(enchantmentHolder -> {
			if (!input.supportsEnchantment(enchantmentHolder)) {
				return;
			}

			int level = bookEnchantments.getLevel(enchantmentHolder);
			int currentLevel = enchantments.getLevel(enchantmentHolder);
			int maxLevel = ServerConfig.getEnchantCap(enchantmentHolder.getRegisteredName());

			if (((currentLevel < level) || (currentLevel == 0)) && (maxLevel == -1 || currentLevel < maxLevel)) {
				EnchantmentHelper.updateEnchantments(result, mutable -> mutable.upgrade(enchantmentHolder, level));
				enchantmentsAdded.incrementAndGet();
			}
		});

		return enchantmentsAdded.get() > 0;
	}
}