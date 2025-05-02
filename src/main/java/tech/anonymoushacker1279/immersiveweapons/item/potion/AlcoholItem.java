package tech.anonymoushacker1279.immersiveweapons.item.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class AlcoholItem extends CustomPotionItem {

	/**
	 * Constructor for AlcoholItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public AlcoholItem(Properties properties) {
		super(properties);
		properties.craftRemainder(asItem());
	}

	@Override
	protected List<MobEffectInstance> getEffects() {
		List<MobEffectInstance> effectInstances = new ArrayList<>(1);
		effectInstances.add(new MobEffectInstance(MobEffects.CONFUSION,
				600, 0, false, true));
		effectInstances.add(new MobEffectInstance(MobEffects.DAMAGE_BOOST,
				600, 0, false, true));
		effectInstances.add(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
				600, 0, false, true));

		return effectInstances;
	}
}