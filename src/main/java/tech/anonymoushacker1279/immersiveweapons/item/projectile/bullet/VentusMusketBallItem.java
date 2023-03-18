package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.VentusMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class VentusMusketBallItem extends AbstractBulletItem {

	public VentusMusketBallItem(Properties properties, double damage) {
		super(properties, damage);
		this.damage = damage;
	}

	@Override
	public VentusMusketBallEntity createBullet(Level level, LivingEntity shooter) {
		VentusMusketBallEntity bulletEntity = new VentusMusketBallEntity(shooter, level);
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