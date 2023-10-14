package tech.anonymoushacker1279.immersiveweapons.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AmmunitionTableBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.MenuTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;

import java.util.function.Supplier;

public class AmmunitionTableMenu extends AbstractContainerMenu {

	private final Container container;
	private final ContainerData containerData;

	public AmmunitionTableMenu(int containerID, Inventory inventory) {
		this(containerID, inventory, new SimpleContainer(7), new SimpleContainerData(1));
	}

	public AmmunitionTableMenu(int containerID, Inventory inventory, Container container, ContainerData containerData) {
		super(MenuTypeRegistry.AMMUNITION_TABLE_MENU.get(), containerID);
		this.container = container;
		this.containerData = containerData;
		
		// Ammunition table inventory slots (first begins at (8, 19), it is a 3x2 grid)
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlot(new Slot(container, j + i * 3, 8 + j * 18, 19 + i * 18));
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
					blockEntity.completeCraft();
				}

				super.onTake(player, stack);
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
			} else {
				slot.setChanged();
			}
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

	public void setDensityModifier(float densityModifier) {
		// Clamp between 0 and 1
		float modifier = Mth.clamp(densityModifier, 0.0F, 1.0F);
		// Round to nearest 0.01
		modifier = (float) Math.round(modifier * 100.0F) / 100.0F;
		containerData.set(0, (int) (modifier * 100.0F));

		PacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new AmmunitionTableMenuPacketHandler(containerId, modifier));
	}

	public static void setDensityModifierOnServer(ServerPlayer player, int containerId, float densityModifier) {
		// Get the menu from the container ID
		AbstractContainerMenu menu = player.containerMenu;

		if (menu.containerId == containerId && menu instanceof AmmunitionTableMenu ammunitionTableMenu) {
			ammunitionTableMenu.containerData.set(0, (int) (densityModifier * 100.0f));
			ammunitionTableMenu.container.setChanged();
		}
	}

	public record AmmunitionTableMenuPacketHandler(int containerId, float densityModifier) {

		public static void encode(AmmunitionTableMenuPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeInt(msg.containerId).writeFloat(msg.densityModifier);
		}

		public static AmmunitionTableMenuPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new AmmunitionTableMenuPacketHandler(packetBuffer.readInt(), packetBuffer.readFloat());
		}

		public static void handle(AmmunitionTableMenuPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> run(msg, context.getSender())));
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> run(msg, context.getSender())));
			context.setPacketHandled(true);
		}

		private static void run(AmmunitionTableMenuPacketHandler msg, @Nullable ServerPlayer sender) {
			if (sender != null) {
				setDensityModifierOnServer(sender, msg.containerId, msg.densityModifier);
			}
		}
	}
}