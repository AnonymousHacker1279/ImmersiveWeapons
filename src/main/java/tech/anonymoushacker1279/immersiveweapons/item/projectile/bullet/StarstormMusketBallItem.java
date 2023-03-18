package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.StarstormMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class StarstormMusketBallItem extends AbstractBulletItem {

	public StarstormMusketBallItem(Properties properties, double damage) {
		super(properties, damage);
		this.damage = damage;
	}

	@Override
	public StarstormMusketBallEntity createBullet(Level level, LivingEntity shooter) {
		StarstormMusketBallEntity bulletEntity = new StarstormMusketBallEntity(shooter, level);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(SoundEventRegistry.BULLET_WHIZZ.get());
		bulletEntity.setPierceLevel((byte) 3);
		return bulletEntity;
	}

	@Override
	public boolean canBeInfinite() {
		return false;
	}
}