package com.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBulletItem extends ArrowItem {

	public double damage;
	protected RegistryObject<? extends Item> referenceItem;

	/**
	 * Constructor for AbstractBulletItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	protected AbstractBulletItem(Properties properties, double damageIn) {
		super(properties);
		damage = damageIn;
	}

	/**
	 * Set item references.
	 *
	 * @param reference the <code>RegistryObject</code> reference, must extend Item
	 */
	public void setItemReference(RegistryObject<? extends Item> reference) {
		referenceItem = reference;
	}

	public @NotNull BulletEntity createBullet(@NotNull Level level, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		BulletEntity bulletEntity = new BulletEntity(DeferredRegistryHandler.IRON_BULLET_ENTITY.get(), shooter, level);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.setOwner(shooter);
		return bulletEntity;
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