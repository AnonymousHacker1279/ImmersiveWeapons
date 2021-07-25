package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public abstract class AbstractBullet extends Item {
	static final Predicate<ItemStack> MUSKET_BALLS = (stack) -> stack.is(ItemTags.bind("immersiveweapons:projectiles/musket_ball"));
	static final Predicate<ItemStack> FLARES = (stack) -> stack.is(ItemTags.bind("immersiveweapons:projectiles/flares"));

	/**
	 * Constructor for AbstractBullet.
	 * @param properties the <code>Properties</code> for the item
	 */
	AbstractBullet(Properties properties) {
		super(properties);
	}

	/**
	 * Get ammunition from the hand.
	 * @param livingEntity the <code>LivingEntity</code> instance
	 * @param isAmmo <code>Predicate</code> extending ItemStack checking for ammo
	 * @return ItemStack
	 */
	static ItemStack getHeldAmmo(LivingEntity livingEntity, Predicate<ItemStack> isAmmo) {
		if (isAmmo.test(livingEntity.getItemInHand(InteractionHand.OFF_HAND))) {
			return livingEntity.getItemInHand(InteractionHand.OFF_HAND);
		} else {
			return isAmmo.test(livingEntity.getItemInHand(InteractionHand.MAIN_HAND)) ? livingEntity.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
		}
	}

	/**
	 * Get ammo predicates.
	 * @return Predicate extending ItemStack
	 */
	Predicate<ItemStack> getAmmoPredicate() {
		return getInventoryAmmoPredicate();
	}

	/**
	 * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
	 * @return Predicate extending ItemStack
	 */
	public abstract Predicate<ItemStack> getInventoryAmmoPredicate();

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 * @return int
	 */
	@Override
	public int getEnchantmentValue() {
		return 1;
	}
}