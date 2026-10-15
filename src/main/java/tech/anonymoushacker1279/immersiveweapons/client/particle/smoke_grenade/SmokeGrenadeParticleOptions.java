package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

/// @param color   the tint color
/// @param cloudId the [SmokeCloudField] this puff is clipped to, or 0 for none
/// @param delay   ticks before the puff starts growing, used to make the cloud spread out from the grenade
public record SmokeGrenadeParticleOptions(int color, int cloudId, int delay) implements ParticleOptions {

	public SmokeGrenadeParticleOptions(int color, int cloudId, int delay) {
		this.color = color;
		this.cloudId = cloudId;
		this.delay = Math.max(delay, 0);
	}

	public SmokeGrenadeParticleOptions(int color) {
		this(color, 0, 0);
	}

	/// Utility for getting particle colors from resources spawning smoke grenade particles.
	///
	/// @param colorID the ID representing the color to be selected.
	///
	/// 0 -> Gray
	///
	/// 1 -> Red
	///
	/// 2 -> Green
	///
	/// 3 -> Blue
	///
	/// 4 -> Purple
	///
	/// 5 -> Yellow
	/// @return SmokeGrenadeParticleOptions
	public static SmokeGrenadeParticleOptions getParticleByColor(int colorID) {
		return switch (colorID) {
			case 1 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.RED);
			case 2 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.GREEN);
			case 3 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.BLUE);
			case 4 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.PURPLE);
			case 5 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.YELLOW);
			default -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.GRAY);
		};
	}

	public SmokeGrenadeParticleOptions with(int cloudId, int delay) {
		return new SmokeGrenadeParticleOptions(color, cloudId, delay);
	}

	@Override
	public ParticleType<SmokeGrenadeParticleOptions> getType() {
		return ParticleTypesRegistry.SMOKE_GRENADE_PARTICLE.get();
	}

	public static class SmokeGrenadeColors {
		public static final int GRAY = 16777215;
		public static final int RED = 16711680;
		public static final int GREEN = 5294200;
		public static final int BLUE = 1644912;
		public static final int PURPLE = 5046349;
		public static final int YELLOW = 16318253;
	}
}
