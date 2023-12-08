package tech.anonymoushacker1279.immersiveweapons.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent.Context;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.StarForgeBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.MenuTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;

import java.util.*;

public class StarForgeMenu extends AbstractContainerMenu {

	public final Container container;
	public final ContainerData containerData;

	public final Slot ingotInputSlot;
	public final Slot secondaryInputSlot;

	public static Map<BlockPos, List<StarForgeRecipe>> AVAILABLE_RECIPES = new HashMap<>(50);

	public StarForgeMenu(int containerID, Inventory inventory) {
		this(containerID, inventory, new SimpleContainer(3), new SimpleContainerData(4));
	}

	public StarForgeMenu(int containerID, Inventory inventory, Container container, ContainerData containerData) {
		super(MenuTypeRegistry.STAR_FORGE_MENU.get(), containerID);
		this.container = container;
		this.containerData = containerData;

		// Primary input slot at (8, 52)
		ingotInputSlot = addSlot(new Slot(container, 0, 8, 52) {
			@Override
			public void setChanged() {
				super.setChanged();
				slotsChanged(container);
			}
		});

		// Secondary input slot at (34, 52)
		secondaryInputSlot = addSlot(new Slot(container, 1, 34, 52) {
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

		if (container instanceof StarForgeBlockEntity blockEntity) {
			blockEntity.initializeRecipes(inventory.player.level().getRecipeManager());
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

		if (container == this.container) {

		}
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return container.stillValid(pPlayer);
	}

	public void setMenuSelectionIndex(int index) {
		// Add code here to update the containerData and the result slot
		index = Mth.clamp(index, 0, AVAILABLE_RECIPES.size() - 1);
		if (index == -1) {
			index = 0;
		}
		containerData.set(3, index);

		PacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(),
				new StarForgeMenuPacketHandler(PacketType.UPDATE_MENU_SELECTION_INDEX, containerId, index, null));
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

	public int getAvailableRecipesSize() {
		return containerData.get(4);
	}

	public static void updateServer(ServerPlayer player, int containerId, int menuSelectionIndex) {
		// Get the menu from the container ID
		AbstractContainerMenu menu = player.containerMenu;

		if (menu.containerId == containerId && menu instanceof StarForgeMenu starForgeMenu) {
			starForgeMenu.containerData.set(3, menuSelectionIndex);
			((StarForgeBlockEntity) starForgeMenu.container).updateResult();
			starForgeMenu.container.setChanged();
		}
	}

	public record StarForgeMenuPacketHandler(PacketType type, int containerId, int menuSelectionIndex, BlockPos pos,
	                                         List<ResourceLocation> availableRecipeIds) {

		public static void encode(StarForgeMenuPacketHandler msg, FriendlyByteBuf packetBuffer) {
			switch (msg.type) {
				case UPDATE_MENU_SELECTION_INDEX -> packetBuffer
						.writeEnum(msg.type)
						.writeInt(msg.containerId)
						.writeInt(msg.menuSelectionIndex);
				case UPDATE_CLIENT_RECIPES -> {
					packetBuffer.writeEnum(msg.type).writeInt(msg.containerId).writeBlockPos(msg.pos);
					packetBuffer.writeInt(msg.availableRecipeIds.size());
					for (ResourceLocation id : msg.availableRecipeIds) {
						packetBuffer.writeResourceLocation(id);
					}
				}
			}
		}

		public static StarForgeMenuPacketHandler decode(FriendlyByteBuf packetBuffer) {
			PacketType type = packetBuffer.readEnum(PacketType.class);
			int containerId = packetBuffer.readInt();
			switch (type) {
				case UPDATE_MENU_SELECTION_INDEX -> {
					int menuSelectionIndex = packetBuffer.readInt();
					return new StarForgeMenuPacketHandler(type, containerId, menuSelectionIndex, null, null);
				}
				case UPDATE_CLIENT_RECIPES -> {
					int size = packetBuffer.readInt();
					BlockPos pos = packetBuffer.readBlockPos();
					List<ResourceLocation> availableRecipeIds = new ArrayList<>(size);
					for (int i = 0; i < size; i++) {
						availableRecipeIds.add(packetBuffer.readResourceLocation());
					}
					return new StarForgeMenuPacketHandler(type, containerId, -1, pos, availableRecipeIds);
				}
			}
			return null;
		}

		public static void handle(StarForgeMenuPacketHandler msg, Context context) {
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> run(msg, context.getSender())));
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> run(msg, context.getSender())));
			context.setPacketHandled(true);
		}

		private static void run(StarForgeMenuPacketHandler msg, @Nullable ServerPlayer sender) {
			if (msg.type == PacketType.UPDATE_MENU_SELECTION_INDEX && sender != null) {
				updateServer(sender, msg.containerId, msg.menuSelectionIndex);
			} else if (msg.type == PacketType.UPDATE_CLIENT_RECIPES) {
				AVAILABLE_RECIPES.put(msg.pos, msg.availableRecipeIds.stream()
						.map(id -> sender.level().getRecipeManager().byKey(id).orElseThrow(IllegalStateException::new))
						.map(StarForgeRecipe.class::cast)
						.toList());
			}
		}
	}

	enum PacketType {
		UPDATE_MENU_SELECTION_INDEX,
		UPDATE_CLIENT_RECIPES
	}
}