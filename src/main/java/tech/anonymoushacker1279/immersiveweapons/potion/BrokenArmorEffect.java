package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.common.EffectCures;

import java.util.Set;

public class BrokenArmorEffect extends BasicMobEffect {

	public BrokenArmorEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean isBeneficial() {
		return false;
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
		cures.add(EffectCures.PROTECTED_BY_TOTEM);
	}

	public float calculateArmorBreach(int level) {
		return (level + 1) * 0.1f;
	}
}