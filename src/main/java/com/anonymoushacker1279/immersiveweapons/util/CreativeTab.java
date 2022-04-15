package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreativeTab extends CreativeModeTab {

	/**
	 * Constructor for CreativeTabSorter.
	 *
	 * @param label the tab label
	 */
	public CreativeTab(String label) {
		super(label);
	}

	/**
	 * Set the tab icon.
	 *
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack makeIcon() {
		return new ItemStack(DeferredRegistryHandler.TESLA_SWORD.get());
	}

}