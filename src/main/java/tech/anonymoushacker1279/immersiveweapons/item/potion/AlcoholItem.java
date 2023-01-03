package tech.anonymoushacker1279.immersiveweapons.item.potion;

import net.minecraft.world.effect.MobEffectInstance;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

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
		effectInstances.add(new MobEffectInstance(EffectRegistry.ALCOHOL_EFFECT.get(),
				600, 0, false, true));
		return effectInstances;
	}
}