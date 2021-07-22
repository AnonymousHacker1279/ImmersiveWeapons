package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

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
	public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		AbstractArrowEntity arrowEntity = new ArrowEntity(worldIn, shooter);
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
	public boolean isInfinite(ItemStack stack, ItemStack bow, PlayerEntity player) {
		int enchant = EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY_ARROWS, bow);
		return enchant > 0;
	}
}