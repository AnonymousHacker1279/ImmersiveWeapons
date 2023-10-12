package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

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
	public void setupFire(ItemStack gun, BulletEntity bulletEntity, Player player) {
		bulletEntity.shootFromRotation(player,
				player.getXRot() + GeneralUtilities.getRandomNumber(-5.0f, 5.0f),
				player.getYRot() + GeneralUtilities.getRandomNumber(-5.0f, 5.0f),
				0.0F,
				getFireVelocity(gun),
				ImmersiveWeapons.COMMON_CONFIG.blunderbussFireInaccuracy().get().floatValue());
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
		return ImmersiveWeapons.COMMON_CONFIG.blunderbussFireVelocity().get().floatValue();
	}

	@Override
	public int getKnockbackLevel() {
		return 3;
	}
}