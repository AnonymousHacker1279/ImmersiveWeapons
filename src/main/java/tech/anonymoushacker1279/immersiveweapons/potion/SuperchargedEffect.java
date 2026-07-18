package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

public class SuperchargedEffect extends MobEffect {

	private int duration;

	public SuperchargedEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
		this.duration = tickCount;
		return super.shouldApplyEffectTickThisTick(tickCount, amplification);
	}

	@Override
	public void onEffectAdded(LivingEntity mob, int amplifier) {
		super.onEffectAdded(mob, amplifier);
		mob.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, duration, amplifier, false, true));

		if (mob instanceof Creeper creeper) {
			creeper.getEntityData().set(Creeper.DATA_IS_POWERED, true);
		}
	}

	/// Supercharged deals thorns-like damage to entities based on its level. The base level deals 5-8 damage
	/// (inclusive), with additional levels adding +3 to the min/max.
	public int getDamage(RandomSource random, int amplification) {
		return random.nextInt(5 + amplification, 8 + amplification);
	}
}