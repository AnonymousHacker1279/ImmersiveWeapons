package tech.anonymoushacker1279.immersiveweapons.item.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class WineItem extends CustomPotionItem {

	/**
	 * Constructor for WineItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public WineItem(Properties properties) {
		super(properties);
		properties.craftRemainder(asItem());
	}

	@Override
	protected List<MobEffectInstance> getEffects() {
		List<MobEffectInstance> effectInstances = new ArrayList<>(2);
		effectInstances.add(new MobEffectInstance(MobEffects.STRENGTH,
				360, 0, false, true));
		effectInstances.add(new MobEffectInstance(MobEffects.RESISTANCE,
				360, 0, false, true));
		return effectInstances;
	}
}