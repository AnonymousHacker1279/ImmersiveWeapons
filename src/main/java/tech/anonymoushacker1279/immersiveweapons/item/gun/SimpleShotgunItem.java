package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class SimpleShotgunItem extends AbstractGunItem {

	/**
	 * Constructor for SimpleShotgunItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public SimpleShotgunItem(Properties properties) {
		super(properties);
	}

	@Override
	public void prepareBulletForFire(ItemStack gun, BulletEntity bulletEntity, LivingEntity shooter, float powderModifier) {
		RandomSource random = shooter.getRandom();
		bulletEntity.shootFromRotation(shooter,
				(float) (shooter.getXRot() + random.nextGaussian() * 5),
				(float) (shooter.getYRot() + random.nextGaussian() * 5),
				0.0F,
				getFireVelocity(gun, powderModifier, shooter),
				getInaccuracy());
	}

	/**
	 * Get the fire sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	public SoundEvent getFireSound() {
		return SoundEventRegistry.BLUNDERBUSS_FIRE.get();
	}

	@Override
	public int getCooldown() {
		return 100;
	}

	@Override
	public float getMaxYRecoil() {
		return -1.5f;
	}

	@Override
	public float getMaxXRecoil() {
		return -13.0f;
	}

	@Override
	public int getMaxBulletsToFire() {
		return 4;
	}

	@Override
	public float getBaseFireVelocity() {
		return (float) IWConfigs.SERVER.blunderbussFireVelocity.getAsDouble();
	}

	@Override
	public float getInaccuracy() {
		return (float) IWConfigs.SERVER.blunderbussFireInaccuracy.getAsDouble();
	}

	@Override
	public int getKnockbackLevel() {
		return 3;
	}
}