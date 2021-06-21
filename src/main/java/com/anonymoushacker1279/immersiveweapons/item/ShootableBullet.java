package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;

import java.util.function.Predicate;

public abstract class ShootableBullet extends Item {
	public static final Predicate<ItemStack> MUSKET_BALLS = (stack) -> stack.getItem().is(ItemTags.bind("immersiveweapons:projectiles/musket_ball"));
	public static final Predicate<ItemStack> FLARES = (stack) -> stack.getItem().is(ItemTags.bind("immersiveweapons:projectiles/flares"));

	public ShootableBullet(Item.Properties builder) {
		super(builder);
	}

	public static ItemStack getHeldAmmo(LivingEntity living, Predicate<ItemStack> isAmmo) {
		if (isAmmo.test(living.getItemInHand(Hand.OFF_HAND))) {
			return living.getItemInHand(Hand.OFF_HAND);
		} else {
			return isAmmo.test(living.getItemInHand(Hand.MAIN_HAND)) ? living.getItemInHand(Hand.MAIN_HAND) : ItemStack.EMPTY;
		}
	}

	public Predicate<ItemStack> getAmmoPredicate() {
		return this.getInventoryAmmoPredicate();
	}

	/**
	 * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
	 */
	public abstract Predicate<ItemStack> getInventoryAmmoPredicate();

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getEnchantmentValue() {
		return 1;
	}
}