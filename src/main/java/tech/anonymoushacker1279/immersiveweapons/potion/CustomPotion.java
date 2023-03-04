package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class CustomPotion extends Potion {

	public CustomPotion(MobEffectInstance mobEffectInstance) {
		super(mobEffectInstance);
	}

	@Override
	public String getName(String prefix) {
		prefix = prefix.replace("item.minecraft.", "")
				.replace(".effect", "");
		return super.getName("item.immersiveweapons." + prefix);
	}
}