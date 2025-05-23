package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.MobEffectCategory;

public class BrokenArmorEffect extends BasicMobEffect {

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