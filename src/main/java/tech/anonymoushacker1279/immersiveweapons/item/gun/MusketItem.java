package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.sounds.SoundEvent;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
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
		return CommonConfig.musketFireVelocity;
	}

	@Override
	public float getInaccuracy() {
		return CommonConfig.musketFireInaccuracy;
	}
}