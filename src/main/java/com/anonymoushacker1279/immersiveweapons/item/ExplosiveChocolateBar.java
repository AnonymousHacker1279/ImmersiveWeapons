package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class ExplosiveChocolateBar extends Item {

	private static final DamageSource damageSource = new DamageSource("immersiveweapons.explosive_chocolate_bar");

	/**
	 * Constructor for ExplosiveChocolateBar.
	 * @param properties the <code>Properties</code> for the item
	 */
	public ExplosiveChocolateBar(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the item is no longer being used.
	 * @param stack the <code>ItemStack</code> instance
	 * @param worldIn the <code>World</code> the entity is in
	 * @param entityLiving the <code>LivingEntity</code> using the item
	 * @return ItemStack
	 */
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		worldIn.explode(null, damageSource, null, entityLiving.blockPosition().getX(), entityLiving.blockPosition().getY(), entityLiving.blockPosition().getZ(), 2.0F, false, Explosion.BlockInteraction.NONE);
		return super.finishUsingItem(stack, worldIn, entityLiving);
	}
}