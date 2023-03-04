package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.GoldenMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class GoldenMusketBallItem extends AbstractBulletItem {

	/**
	 * Constructor for GoldBulletItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public GoldenMusketBallItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	/**
	 * Create a bullet item.
	 *
	 * @param level   the <code>Level</code> the shooter is in
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return GoldBulletEntity
	 */
	@Override
	public GoldenMusketBallEntity createBullet(Level level, LivingEntity shooter) {
		GoldenMusketBallEntity bulletEntity = new GoldenMusketBallEntity(shooter, level);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(SoundEventRegistry.BULLET_WHIZZ.get());
		return bulletEntity;
	}
}