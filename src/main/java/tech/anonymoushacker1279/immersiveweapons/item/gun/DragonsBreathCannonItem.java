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

public class DragonsBreathCannonItem extends HandCannonItem {

	public DragonsBreathCannonItem(Properties properties) {
		super(properties);
	}

	@Override
	public Item defaultAmmo() {
		return ItemRegistry.DRAGON_FIREBALL.get();
	}

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return DRAGON_FIREBALLS;
	}

	@Override
	public SoundEvent getFireSound() {
		return SoundEventRegistry.DRAGONS_BREATH_CANNON_FIRE.get();
	}

	@Override
	public float getBaseFireVelocity() {
		return (float) IWConfigs.SERVER.dragonsBreathCannonFireVelocity.getAsDouble();
	}

	@Override
	public float getInaccuracy() {
		return (float) IWConfigs.SERVER.dragonsBreathCannonFireInaccuracy.getAsDouble();
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
		return -1.75f;
	}

	@Override
	public float getMaxXRecoil() {
		return -25.0f;
	}

	@Override
	public int getCooldown() {
		return 600;
	}
}