package tech.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class MusketItem extends AbstractGunItem {

	private final boolean hasScope;

	/**
	 * Constructor for MusketItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public MusketItem(Properties properties, boolean hasScope) {
		super(properties);
		this.hasScope = hasScope;
	}

	@Override
	public void setupFire(ItemStack gun, BulletEntity bulletEntity, Player player) {
		bulletEntity.shootFromRotation(player, player.xRot, player.yRot,
				0.0F,
				getFireVelocity(gun),
				CommonConfig.MUSKET_FIRE_INACCURACY.get().floatValue());
	}

	/**
	 * Get the fire sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	public SoundEvent getFireSound() {
		return SoundEventRegistry.MUSKET_FIRE.get();
	}

	/**
	 * Get the repair material.
	 *
	 * @return Ingredient
	 */
	@Override
	protected Ingredient getRepairMaterial() {
		return Ingredient.of(Items.IRON_INGOT);
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
	public boolean canScope() {
		return hasScope;
	}

	@Override
	public float getBaseFireVelocity() {
		return CommonConfig.MUSKET_FIRE_VELOCITY.get().floatValue();
	}
}