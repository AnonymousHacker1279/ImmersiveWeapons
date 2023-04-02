package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.CobaltMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class CobaltMusketBallItem extends AbstractBulletItem {

	/**
	 * Constructor for CobaltBulletItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public CobaltMusketBallItem(Properties properties, double damage) {
		super(properties, damage);
		this.damage = damage;
	}

	/**
	 * Create a bullet item.
	 *
	 * @param level   the <code>Level</code> the shooter is in
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return CobaltBulletEntity
	 */
	@Override
	public CobaltMusketBallEntity createBullet(Level level, LivingEntity shooter) {
		CobaltMusketBallEntity bulletEntity = new CobaltMusketBallEntity(shooter, level);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(SoundEventRegistry.BULLET_WHIZZ.get());
		return bulletEntity;
	}
}