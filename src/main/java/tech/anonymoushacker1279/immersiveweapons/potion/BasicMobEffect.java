package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BasicMobEffect extends MobEffect {

	/**
	 * Constructor for BasicPotionEffect.
	 *
	 * @param category the <code>MobEffectCategory</code> instance
	 * @param color    the liquid color
	 */
	public BasicMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
}