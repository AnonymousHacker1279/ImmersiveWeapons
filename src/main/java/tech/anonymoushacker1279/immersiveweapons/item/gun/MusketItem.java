package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
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
	public void setupFire(ItemStack gun, BulletEntity bulletEntity, Player player, float powderModifier) {
		bulletEntity.shootFromRotation(player, player.getXRot(), player.getYRot(),
				0.0F,
				getFireVelocity(gun, powderModifier),
				ImmersiveWeapons.COMMON_CONFIG.musketFireInaccuracy().get().floatValue());
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
		return ImmersiveWeapons.COMMON_CONFIG.musketFireVelocity().get().floatValue();
	}
}