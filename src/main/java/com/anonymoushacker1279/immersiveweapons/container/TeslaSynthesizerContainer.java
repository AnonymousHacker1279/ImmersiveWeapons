package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIntArray;

public class TeslaSynthesizerContainer extends AbstractTeslaSynthesizerContainer {

	public TeslaSynthesizerContainer(int id, PlayerInventory playerInventoryIn) {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_CONTAINER.get(), id, playerInventoryIn);
	}

	public TeslaSynthesizerContainer(int id, PlayerInventory playerInventoryIn, IInventory teslaSynthesizerInventory, IIntArray teslaSynthesizerData) {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_CONTAINER.get(), id, playerInventoryIn, teslaSynthesizerInventory, teslaSynthesizerData);
	}
}