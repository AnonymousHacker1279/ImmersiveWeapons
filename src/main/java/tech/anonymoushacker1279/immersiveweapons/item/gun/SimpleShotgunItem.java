package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
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
	public void prepareBulletForFire(ItemStack gun, BulletEntity bulletEntity, LivingEntity livingEntity, float powderModifier) {
		RandomSource random = livingEntity.getRandom();
		bulletEntity.shootFromRotation(livingEntity,
				(float) (livingEntity.getXRot() + random.nextGaussian() * 5),
				(float) (livingEntity.getYRot() + random.nextGaussian() * 5),
				0.0F,
				getFireVelocity(gun, powderModifier),
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

	/**
	 * Get the repair material.
	 *
	 * @return Ingredient
	 */
	@Override
	protected Ingredient getRepairMaterial() {
		return Ingredient.of(Items.GOLD_INGOT);
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
		return CommonConfig.blunderbussFireVelocity;
	}

	@Override
	public float getInaccuracy() {
		return CommonConfig.blunderbussFireInaccuracy;
	}

	@Override
	public int getKnockbackLevel() {
		return 3;
	}
}