package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import net.minecraft.item.Item.Properties;

public class CustomArrowItem extends ArrowItem {

	/**
	 * The amount of damage this arrow does by default.
	 */
	public double damage;

	public RegistryObject<Item> ref;

	/**
	 * @param properties Default Item.Properties
	 * @param damageIn   Amount of damage this arrow deals as base (will be multiplied by projectile velocity later!)
	 */
	public CustomArrowItem(Properties properties, double damageIn) {
		super(properties);
		this.damage = damageIn;
	}

	/**
	 * Set an internal reference to the represented item.
	 * This is needed specifically for ensuring that CustomArrowEntity drops the correct arrow item.
	 *
	 * @param refIn a RegistryObject for building the Item
	 * @returns Reference to this object, for method chaining
	 */
	public CustomArrowItem setItemReference(RegistryObject<Item> refIn) {
		this.ref = refIn;
		return this;
	}

	/**
	 * Create an ArrowEntity representing this Item.
	 *
	 * @returns the relevant Entity (in this case CustomArrowEntity) for use by the firing tool.
	 */
	@Override
	public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		AbstractArrowEntity arrowentity = new ArrowEntity(worldIn, shooter);
		arrowentity.setBaseDamage(this.damage);
		return arrowentity;
	}

	/**
	 * @returns whether the arrow is infinite and, therefore, whether one should be expended upon firing.
	 */
	// We override this method here because the version in ArrowItem *directly* compares against ArrowItem.class, rather than this more flexible check.
	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
		int enchant = net.minecraft.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY_ARROWS, bow);
		return enchant > 0 && this instanceof ArrowItem;
	}
}