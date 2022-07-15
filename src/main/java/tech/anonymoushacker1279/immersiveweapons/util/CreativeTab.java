package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class CreativeTab extends CreativeModeTab {

	/**
	 * Constructor for CreativeTab.
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