package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIntArray;

public class TeslaSynthesizerContainer extends AbstractTeslaSynthesizerContainer {

	/**
	 * Constructor for TeslaSynthesizerContainer.
	 * @param id the ID of the container
	 * @param playerInventoryIn the <code>PlayerInventory</code> instance
	 */
	public TeslaSynthesizerContainer(int id, PlayerInventory playerInventoryIn) {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_CONTAINER.get(), id, playerInventoryIn);
	}

	/**
	 * Constructor for TeslaSynthesizerContainer.
	 * @param id the ID of the container
	 * @param playerInventoryIn the <code>PlayerInventory</code> instance
	 * @param teslaSynthesizerInventory the <code>IInventory</code> of the container
	 * @param teslaSynthesizerData the <code>IIntArray</code> data
	 */
	public TeslaSynthesizerContainer(int id, PlayerInventory playerInventoryIn, IInventory teslaSynthesizerInventory, IIntArray teslaSynthesizerData) {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_CONTAINER.get(), id, playerInventoryIn, teslaSynthesizerInventory, teslaSynthesizerData);
	}
}