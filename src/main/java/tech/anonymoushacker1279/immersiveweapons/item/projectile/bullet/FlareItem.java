package com.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntities.FlareEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FlareItem extends AbstractBulletItem {

	/**
	 * Constructor for FlareItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public FlareItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	/**
	 * Create a bullet item.
	 *
	 * @param level   the <code>Level</code> the shooter is in
	 * @param stack   the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return FlareEntity
	 */
	@Override
	public @NotNull FlareEntity createBullet(@NotNull Level level, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		FlareEntity flareEntity = new FlareEntity(shooter, level);
		flareEntity.setBaseDamage(damage);
		flareEntity.pickup = Pickup.DISALLOWED;
		flareEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
		return flareEntity;
	}
}