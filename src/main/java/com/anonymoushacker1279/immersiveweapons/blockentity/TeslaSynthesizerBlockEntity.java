package com.anonymoushacker1279.immersiveweapons.blockentity;

import com.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class TeslaSynthesizerBlockEntity extends AbstractTeslaSynthesizerBlockEntity {

	/**
	 * Constructor for TeslaSynthesizerBlockEntity.
	 */
	public TeslaSynthesizerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(blockPos, blockState);
	}

	/**
	 * Get the default name.
	 * @return ITextComponent
	 */
	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent("container.immersiveweapons.tesla_synthesizer");
	}

	/**
	 * Create a new menu.
	 * @param id the menu ID
	 * @param player the <code>PlayerEntity</code> instance
	 * @return Container
	 */
	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return new TeslaSynthesizerContainer(id, player, this, teslaSynthesizerData);
	}
}