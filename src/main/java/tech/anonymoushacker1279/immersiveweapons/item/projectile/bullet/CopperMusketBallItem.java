package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntities;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntities.CopperMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class CopperMusketBallItem extends AbstractBulletItem {

	/**
	 * Constructor for CopperBulletItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public CopperMusketBallItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	/**
	 * Create a bullet item.
	 *
	 * @param level   the <code>Level</code> the shooter is in
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return CopperBulletItem
	 */
	@Override
	public BulletEntities.CopperMusketBallEntity createBullet(Level level, LivingEntity shooter) {
		CopperMusketBallEntity bulletEntity = new CopperMusketBallEntity(shooter, level);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
		return bulletEntity;
	}
}