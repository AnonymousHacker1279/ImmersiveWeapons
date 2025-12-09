package tech.anonymoushacker1279.immersiveweapons.client.particle.damage_indicator;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

public record DamageIndicatorParticleOptions(float damage) implements ParticleOptions {

	@Override
	public ParticleType<?> getType() {
		return ParticleTypesRegistry.DAMAGE_INDICATOR_PARTICLE.get();
	}
}