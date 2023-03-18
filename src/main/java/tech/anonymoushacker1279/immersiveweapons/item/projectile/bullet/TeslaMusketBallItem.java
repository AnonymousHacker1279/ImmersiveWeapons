package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.TeslaMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class TeslaMusketBallItem extends AbstractBulletItem {

	public TeslaMusketBallItem(Properties properties, double damage) {
		super(properties, damage);
		this.damage = damage;
	}

	@Override
	public TeslaMusketBallEntity createBullet(Level level, LivingEntity shooter) {
		TeslaMusketBallEntity bulletEntity = new TeslaMusketBallEntity(shooter, level);
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