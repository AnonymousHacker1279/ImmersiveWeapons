package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntities;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntities.FlareEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

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
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return FlareEntity
	 */
	@Override
	public @NotNull BulletEntities.FlareEntity createBullet(@NotNull Level level, @NotNull LivingEntity shooter) {
		FlareEntity flareEntity = new FlareEntity(shooter, level);
		flareEntity.setBaseDamage(damage);
		flareEntity.pickup = Pickup.DISALLOWED;
		flareEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
		return flareEntity;
	}
}