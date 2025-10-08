package tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

public record BulletImpactParticleOptions(float scale, int blockID) implements ParticleOptions {

	public BulletImpactParticleOptions(float scale, int blockID) {
		this.scale = Mth.clamp(scale, 0.001F, 100.0F);
		this.blockID = blockID;
	}

	@Override
	public ParticleType<BulletImpactParticleOptions> getType() {
		return ParticleTypesRegistry.BULLET_IMPACT_PARTICLE.get();
	}
}