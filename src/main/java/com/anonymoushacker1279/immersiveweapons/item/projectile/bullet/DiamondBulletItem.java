package com.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.DiamondBulletEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DiamondBulletItem extends AbstractArrowItem {

	/**
	 * Constructor for DiamondBulletItem.
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn the damage to deal on impact
	 */
	public DiamondBulletItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	/**
	 * Create an arrow item.
	 * @param level the <code>Level</code> the shooter is in
	 * @param stack the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return DiamondBulletEntity
	 */
	@Override
	public @NotNull DiamondBulletEntity createArrow(@NotNull Level level, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		DiamondBulletEntity bulletEntity = new DiamondBulletEntity(shooter, level, ref.get().asItem());
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
		bulletEntity.setPierceLevel((byte) 1);
		return bulletEntity;
	}
}