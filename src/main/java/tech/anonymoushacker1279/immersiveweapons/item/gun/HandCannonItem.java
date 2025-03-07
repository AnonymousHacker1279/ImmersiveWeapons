package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

import java.util.function.Predicate;

public class HandCannonItem extends SimplePistolItem {

	public HandCannonItem(Properties properties) {
		super(properties);
	}

	@Override
	public Item defaultAmmo() {
		return ItemRegistry.CANNONBALL.get();
	}

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return CANNONBALLS;
	}

	@Override
	public SoundEvent getFireSound() {
		return SoundEventRegistry.HAND_CANNON_FIRE.get();
	}

	@Override
	public int getKnockbackLevel() {
		return 5;
	}

	@Override
	public float getBaseFireVelocity() {
		return (float) IWConfigs.SERVER.handCannonFireVelocity.getAsDouble();
	}

	@Override
	public float getInaccuracy() {
		return (float) IWConfigs.SERVER.handCannonFireInaccuracy.getAsDouble();
	}

	@Override
	public void prepareBulletForFire(ItemStack gun, BulletEntity bulletEntity, LivingEntity shooter, float powderModifier) {
		bulletEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(),
				0.0F,
				getFireVelocity(gun, powderModifier, shooter),
				getInaccuracy());
	}

	@Override
	public float getMaxYRecoil() {
		return -1.5f;
	}

	@Override
	public float getMaxXRecoil() {
		return -20.0f;
	}

	@Override
	public int getCooldown() {
		return 400;
	}
}