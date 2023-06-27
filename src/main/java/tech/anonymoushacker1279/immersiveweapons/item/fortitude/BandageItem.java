package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class BandageItem extends AbstractFortitudeItem {

	/**
	 * Constructor for BandageItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public BandageItem(Properties properties) {
		super(properties, false);
	}

	@Override
	public List<MobEffectInstance> effects() {
		List<MobEffectInstance> effects = new ArrayList<>(2);

		effects.add(new MobEffectInstance(MobEffects.REGENERATION, 240, 0, false, true));

		return effects;
	}
}