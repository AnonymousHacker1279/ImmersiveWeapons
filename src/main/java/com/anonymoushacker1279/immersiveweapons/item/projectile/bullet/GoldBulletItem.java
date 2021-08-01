package com.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.GoldBulletEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GoldBulletItem extends AbstractArrowItem {

	/**
	 * Constructor for GoldBulletItem.
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn the damage to deal on impact
	 */
	public GoldBulletItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	/**
	 * Create an arrow item.
	 * @param level the <code>Level</code> the shooter is in
	 * @param stack the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return GoldBulletEntity
	 */
	@Override
	public GoldBulletEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		GoldBulletEntity bulletEntity = new GoldBulletEntity(shooter, level, ref.get());
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
		return bulletEntity;
	}
}