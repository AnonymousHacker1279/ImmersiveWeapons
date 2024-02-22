package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommanderPedestalBlockEntity extends AbstractInventoryBlockEntity {

	public CommanderPedestalBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.COMMANDER_PEDESTAL_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new CommanderPedestalBlockEntity(pPos, pState);
	}

	@Override
	public boolean addItem(ItemStack itemStack) {
		if (!getAugmentItems().contains(itemStack.getItem())) {
			return false;
		}

		return super.addItem(itemStack);
	}

	public List<Item> getAugmentItems() {
		List<Item> items = new ArrayList<>(5);
		for (AugmentTypes type : AugmentTypes.values()) {
			items.add(type.getItem());
		}

		return items;
	}

	public enum AugmentTypes {
		SPEED(ItemRegistry.PEDESTAL_AUGMENT_SPEED.get()),
		ARMOR(ItemRegistry.PEDESTAL_AUGMENT_ARMOR.get()),
		ENCHANTMENT(ItemRegistry.PEDESTAL_AUGMENT_ENCHANTMENT.get()),
		CAPACITY(ItemRegistry.PEDESTAL_AUGMENT_CAPACITY.get());

		private final Item item;

		AugmentTypes(Item item) {
			this.item = item;
		}

		public Item getItem() {
			return item;
		}
	}
}