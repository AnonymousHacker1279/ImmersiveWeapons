package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.DiamondMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class DiamondMusketBallItem extends AbstractBulletItem {

	/**
	 * Constructor for DiamondBulletItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public DiamondMusketBallItem(Properties properties, double damage) {
		super(properties, damage);
		this.damage = damage;
	}

	/**
	 * Create a bullet item.
	 *
	 * @param level   the <code>Level</code> the shooter is in
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return DiamondBulletEntity
	 */
	@Override
	public DiamondMusketBallEntity createBullet(Level level, LivingEntity shooter) {
		DiamondMusketBallEntity bulletEntity = new DiamondMusketBallEntity(shooter, level);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(SoundEventRegistry.BULLET_WHIZZ.get());
		bulletEntity.setPierceLevel((byte) 1);
		return bulletEntity;
	}

	@Override
	public boolean canBeInfinite() {
		return false;
	}
}