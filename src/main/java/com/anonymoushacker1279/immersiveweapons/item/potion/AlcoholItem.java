package com.anonymoushacker1279.immersiveweapons.item.potion;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class AlcoholItem extends CustomPotionItem {

	/**
	 * Constructor for AlcoholBottleItem.
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
		effectInstances.add(new MobEffectInstance(DeferredRegistryHandler.ALCOHOL_EFFECT.get(),
				600, 0, false, true));
		return effectInstances;
	}
}