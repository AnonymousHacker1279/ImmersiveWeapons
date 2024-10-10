package tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

public class BulletImpactParticleOptions implements ParticleOptions {

	protected final float scale;
	protected final int blockID;

	public BulletImpactParticleOptions(float vibrancy, int blockID) {
		scale = Mth.clamp(vibrancy, 0.001F, 100.0F);
		this.blockID = blockID;
	}

	public int getBlockID() {
		return blockID;
	}

	@Override
	public ParticleType<BulletImpactParticleOptions> getType() {
		return ParticleTypesRegistry.BULLET_IMPACT_PARTICLE.get();
	}
}