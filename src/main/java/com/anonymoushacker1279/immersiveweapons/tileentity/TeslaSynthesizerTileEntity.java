package com.anonymoushacker1279.immersiveweapons.tileentity;

import com.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.ICustomRecipeType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TeslaSynthesizerTileEntity extends AbstractTeslaSynthesizerTileEntity {

	public TeslaSynthesizerTileEntity() {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_TILE_ENTITY.get(), ICustomRecipeType.TESLA_SYNTHESIZER);
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.immersiveweapons.tesla_synthesizer");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new TeslaSynthesizerContainer(id, player, this, this.teslaSynthesizerData);
	}
}