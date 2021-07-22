package com.anonymoushacker1279.immersiveweapons.tileentity;

import com.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TeslaSynthesizerTileEntity extends AbstractTeslaSynthesizerTileEntity {

	/**
	 * Constructor for TeslaSynthesizerTileEntity.
	 */
	public TeslaSynthesizerTileEntity() {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_TILE_ENTITY.get());
	}

	/**
	 * Get the default name.
	 * @return ITextComponent
	 */
	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.immersiveweapons.tesla_synthesizer");
	}

	/**
	 * Create a new menu.
	 * @param id the menu ID
	 * @param player the <code>PlayerEntity</code> instance
	 * @return Container
	 */
	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new TeslaSynthesizerContainer(id, player, this, teslaSynthesizerData);
	}
}