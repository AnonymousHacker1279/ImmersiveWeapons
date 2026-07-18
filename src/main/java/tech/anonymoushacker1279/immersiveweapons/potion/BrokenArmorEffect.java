package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BrokenArmorEffect extends MobEffect {

	public BrokenArmorEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean isBeneficial() {
		return false;
	}

	public float calculateArmorBreach(int level) {
		return (level + 1) * 0.1f;
	}
}