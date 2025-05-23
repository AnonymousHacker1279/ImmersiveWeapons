package tech.anonymoushacker1279.immersiveweapons.menu;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AmmunitionTableBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.MenuTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.AmmunitionTablePayload;

public class AmmunitionTableMenu extends AbstractContainerMenu {

	private final Container container;
	private final ContainerData containerData;

	public AmmunitionTableMenu(int containerID, Inventory inventory) {
		this(containerID, inventory, new SimpleContainer(7), new SimpleContainerData(2));
	}

	public AmmunitionTableMenu(int containerID, Inventory inventory, Container container, ContainerData containerData) {
		super(MenuTypeRegistry.AMMUNITION_TABLE_MENU.get(), containerID);
		this.container = container;
		this.containerData = containerData;

		// Ammunition table inventory slots (first begins at (8, 19), it is a 3x2 grid)
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlot(new Slot(container, j + i * 3, 8 + j * 18, 19 + i * 18) {
					@Override
					public void setChanged() {
						super.setChanged();

						if (container instanceof AmmunitionTableBlockEntity blockEntity) {
							blockEntity.calculateOutput(false);
						}
					}
				});
			}
		}

		// Ammunition table result slot
		addSlot(new Slot(container, 6, 144, 27) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			@Override
			public void onTake(Player player, ItemStack stack) {
				if (container instanceof AmmunitionTableBlockEntity blockEntity) {
					blockEntity.depleteMaterials();
					blockEntity.calculateOutput(true);
					super.onTake(player, stack);
				}
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
			}

			slot.onTake(player, oldStack);
		}

		return itemStack;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return container.stillValid(pPlayer);
	}

	public float getDensityModifier() {
		return containerData.get(0) / 100.0F;
	}

	public int getExcessStackSize() {
		return containerData.get(1);
	}

	public void setDensityModifier(float densityModifier) {
		// Clamp between 0 and 1
		float modifier = Mth.clamp(densityModifier, 0.0F, 1.0F);
		// Round to nearest 0.01
		modifier = (float) Math.round(modifier * 100.0F) / 100.0F;
		containerData.set(0, (int) (modifier * 100.0F));

		PacketDistributor.sendToServer(new AmmunitionTablePayload(containerId, modifier));
	}

	public static void setDensityModifierOnServer(ServerPlayer player, int containerId, float densityModifier) {
		// Get the menu from the container ID
		AbstractContainerMenu menu = player.containerMenu;

		if (menu.containerId == containerId && menu instanceof AmmunitionTableMenu ammunitionTableMenu) {
			ammunitionTableMenu.containerData.set(0, (int) (densityModifier * 100.0f));
			if (ammunitionTableMenu.container instanceof AmmunitionTableBlockEntity blockEntity) {
				blockEntity.calculateOutput(false);
			}
		}
	}
}