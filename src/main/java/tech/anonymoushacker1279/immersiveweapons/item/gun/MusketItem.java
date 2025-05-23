package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.ArrowAttributeAccessor;

public class MusketItem extends AbstractGunItem {

	public MusketItem(Properties properties) {
		super(properties);
	}

	@Override
	public SoundEvent getFireSound() {
		return SoundEventRegistry.MUSKET_FIRE.get();
	}

	@Override
	public int getCooldown() {
		return 200;
	}

	@Override
	public float getMaxYRecoil() {
		return -0.15f;
	}

	@Override
	public float getMaxXRecoil() {
		return -2.0f;
	}

	@Override
	public float getBaseFireVelocity() {
		return (float) IWConfigs.SERVER.musketFireVelocity.getAsDouble();
	}

	@Override
	public float getInaccuracy() {
		return (float) IWConfigs.SERVER.musketFireInaccuracy.getAsDouble();
	}

	@Override
	public void setupFire(LivingEntity shooter, BulletEntity bullet, ItemStack gun, @Nullable ItemStack ammo, PowderType powderType) {
		super.setupFire(shooter, bullet, gun, ammo, powderType);
		((ArrowAttributeAccessor) bullet).immersiveWeapons$setGravity(bullet.getGravity() / 4);
	}
}