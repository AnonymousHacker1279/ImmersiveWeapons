package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

import java.util.function.Predicate;

public class FlareGunItem extends SimplePistolItem {

	public FlareGunItem(Properties builder) {
		super(builder);
	}

	@Override
	public Item defaultAmmo() {
		return DeferredRegistryHandler.FLARE.get();
	}

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return FLARES;
	}

	@Override
	public SoundEvent getFireSound() {
		return DeferredRegistryHandler.FLARE_GUN_FIRE.get();
	}
}