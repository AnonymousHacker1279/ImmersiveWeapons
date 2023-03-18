package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.NetheriteMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class NetheriteMusketBallItem extends AbstractBulletItem {

	/**
	 * Constructor for NetheriteBulletItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public NetheriteMusketBallItem(Properties properties, double damage) {
		super(properties, damage);
		this.damage = damage;
	}

	/**
	 * Create a bullet item.
	 *
	 * @param level   the <code>Level</code> the shooter is in
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return NetheriteBulletEntity
	 */
	@Override
	public NetheriteMusketBallEntity createBullet(Level level, LivingEntity shooter) {
		NetheriteMusketBallEntity bulletEntity = new NetheriteMusketBallEntity(shooter, level);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(SoundEventRegistry.BULLET_WHIZZ.get());
		bulletEntity.setPierceLevel((byte) 2);
		return bulletEntity;
	}

	@Override
	public boolean canBeInfinite() {
		return false;
	}
}