package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class FirstAidKitItem extends AbstractFortitudeItem {

	/**
	 * Constructor for FirstAidKitItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public FirstAidKitItem(Properties properties) {
		super(properties, true);
	}

	@Override
	public int getCooldown() {
		return 400;
	}

	@Override
	public List<MobEffectInstance> effects() {
		List<MobEffectInstance> effects = new ArrayList<>(3);

		effects.add(new MobEffectInstance(MobEffects.REGENERATION, 240, 1, false, true));
		effects.add(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, true));

		return effects;
	}
}