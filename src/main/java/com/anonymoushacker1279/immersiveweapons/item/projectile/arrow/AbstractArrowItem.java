package com.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractArrowItem extends ArrowItem {

	public double damage;
	protected RegistryObject<? extends Item> ref;

	/**
	 * Constructor for AbstractArrowItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	protected AbstractArrowItem(Properties properties, double damageIn) {
		super(properties);
		damage = damageIn;
	}

	/**
	 * Set item references.
	 *
	 * @param refIn the <code>RegistryObject</code> reference, must extend Item
	 */
	public void setItemReference(RegistryObject<? extends Item> refIn) {
		ref = refIn;
	}

	/**
	 * Create an arrow item.
	 *
	 * @param worldIn the <code>World</code> the shooter is in
	 * @param stack   the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return AbstractArrow
	 */
	@Override
	public @NotNull AbstractArrow createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		AbstractArrow arrowEntity = new Arrow(worldIn, shooter);
		arrowEntity.setBaseDamage(damage);
		arrowEntity.setOwner(shooter);
		return arrowEntity;
	}

	/**
	 * Check if the arrow is infinite. A more flexible check than Vanilla provides.
	 *
	 * @param stack  the <code>ItemStack</code> being checked
	 * @param bow    the <code>ItemStack</code> containing the bow that's firing
	 * @param player the <code>PlayerEntity</code> firing the bow
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(@NotNull ItemStack stack, @NotNull ItemStack bow, @NotNull Player player) {
		int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
		return enchant > 0;
	}
}