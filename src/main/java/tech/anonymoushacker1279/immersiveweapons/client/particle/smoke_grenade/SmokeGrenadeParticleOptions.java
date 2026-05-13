package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

public record SmokeGrenadeParticleOptions(int color, float scale) implements ParticleOptions {

	public SmokeGrenadeParticleOptions(int color, float scale) {
		this.color = color;
		this.scale = Mth.clamp(scale, 0.001F, 100.0F);
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
			case 1 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.RED, 1.0F);
			case 2 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.GREEN, 1.0F);
			case 3 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.BLUE, 1.0F);
			case 4 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.PURPLE, 1.0F);
			case 5 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.YELLOW, 1.0F);
			default -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.GRAY, 1.0F);
		};
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