package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class TeslaSynthesizerContainer extends AbstractTeslaSynthesizerContainer {

	/**
	 * Constructor for TeslaSynthesizerContainer.
	 *
	 * @param id                the ID of the container
	 * @param playerInventoryIn the <code>PlayerInventory</code> instance
	 */
	public TeslaSynthesizerContainer(int id, Inventory playerInventoryIn) {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_CONTAINER.get(), id, playerInventoryIn);
	}

	/**
	 * Constructor for TeslaSynthesizerContainer.
	 *
	 * @param id                        the ID of the container
	 * @param playerInventoryIn         the <code>PlayerInventory</code> instance
	 * @param teslaSynthesizerInventory the <code>IInventory</code> of the container
	 * @param teslaSynthesizerData      the <code>IIntArray</code> data
	 */
	public TeslaSynthesizerContainer(int id, Inventory playerInventoryIn, Container teslaSynthesizerInventory, ContainerData teslaSynthesizerData) {
		super(DeferredRegistryHandler.TESLA_SYNTHESIZER_CONTAINER.get(), id, playerInventoryIn, teslaSynthesizerInventory, teslaSynthesizerData);
	}
}