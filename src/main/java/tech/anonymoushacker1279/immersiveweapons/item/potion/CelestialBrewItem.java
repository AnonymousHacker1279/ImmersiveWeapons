package tech.anonymoushacker1279.immersiveweapons.item.potion;

import net.minecraft.world.effect.MobEffectInstance;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.ArrayList;
import java.util.List;

public class CelestialBrewItem extends CustomPotionItem {

	/**
	 * Constructor for CelestialBrewItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public CelestialBrewItem(Properties properties) {
		super(properties);
		properties.craftRemainder(asItem());
	}

	@Override
	protected List<MobEffectInstance> getEffects() {
		List<MobEffectInstance> effectInstances = new ArrayList<>(2);
		effectInstances.add(new MobEffectInstance(DeferredRegistryHandler.CELESTIAL_PROTECTION_EFFECT.get(),
				3600, 0, false, true));
		return effectInstances;
	}
}