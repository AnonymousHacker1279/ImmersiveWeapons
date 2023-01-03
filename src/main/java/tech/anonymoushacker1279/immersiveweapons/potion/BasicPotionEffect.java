package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BasicPotionEffect extends MobEffect {

	/**
	 * Constructor for BasicPotionEffect.
	 *
	 * @param typeIn        the <code>MobEffectCategory</code> instance
	 * @param liquidColorIn the liquid color
	 */
	public BasicPotionEffect(MobEffectCategory typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}
}