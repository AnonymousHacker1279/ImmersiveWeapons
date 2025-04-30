package tech.anonymoushacker1279.immersiveweapons.item.utility;

import net.minecraft.world.item.Item;

public class FuelItem extends Item {

	private final int burnTime;

	public FuelItem(Properties properties, int burnTime) {
		super(properties);
		this.burnTime = burnTime;
	}

	public int getBurnTime() {
		return burnTime;
	}
}