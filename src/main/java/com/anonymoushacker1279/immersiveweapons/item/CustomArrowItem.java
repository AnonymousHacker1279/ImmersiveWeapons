package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.RegistryObject;

public class CustomArrowItem extends ArrowItem {

	public double damage;
	RegistryObject<Item> ref;

	/**
	 * Constructor for CustomArrowItem.
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn the damage to deal on impact
	 */
	CustomArrowItem(Properties properties, double damageIn) {
		super(properties);
		damage = damageIn;
	}

	/**
	 * Set item references.
	 * @param refIn the <code>RegistryObject</code> reference, must extend Item
	 */
	public void setItemReference(RegistryObject<Item> refIn) {
		ref = refIn;
	}

	/**
	 * Create an arrow item.
	 * @param worldIn the <code>World</code> the shooter is in
	 * @param stack the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return AbstractArrowEntity
	 */
	@Override
	public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		AbstractArrow arrowEntity = new Arrow(worldIn, shooter);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}

	/**
	 * Check if the arrow is infinite. A more flexible check than Vanilla provides.
	 * @param stack the <code>ItemStack</code> being checked
	 * @param bow the <code>ItemStack</code> containing the bow that's firing
	 * @param player the <code>PlayerEntity</code> firing the bow
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
		int enchant = EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS, bow);
		return enchant > 0;
	}
}