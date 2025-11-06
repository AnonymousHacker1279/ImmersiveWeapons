package tech.anonymoushacker1279.immersiveweapons.client.particle.wisp;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

public record WispParticleOptions(int color) implements ParticleOptions {

	@Override
	public ParticleType<?> getType() {
		return ParticleTypesRegistry.WISP_PARTICLE.get();
	}
}