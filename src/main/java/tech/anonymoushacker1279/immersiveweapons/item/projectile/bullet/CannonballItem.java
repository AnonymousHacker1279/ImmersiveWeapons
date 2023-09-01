package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CannonballEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class CannonballItem extends AbstractBulletItem {

	private final boolean isExplosive;

	public CannonballItem(Properties properties, double damage, boolean isExplosive) {
		super(properties, damage);
		this.damage = damage;
		this.isExplosive = isExplosive;
	}

	@Override
	public CannonballEntity createBullet(Level level, LivingEntity shooter) {
		CannonballEntity cannonballEntity = new CannonballEntity(shooter, level);
		cannonballEntity.setBaseDamage(damage);
		cannonballEntity.pickup = Pickup.DISALLOWED;
		cannonballEntity.setPierceLevel((byte) 1);
		cannonballEntity.setSoundEvent(SoundEventRegistry.BULLET_WHIZZ.get());
		cannonballEntity.setExplosive(isExplosive);
		return cannonballEntity;
	}
}