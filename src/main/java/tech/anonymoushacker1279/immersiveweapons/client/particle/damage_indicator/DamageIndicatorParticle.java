package tech.anonymoushacker1279.immersiveweapons.client.particle.damage_indicator;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.RandomSource;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;

import javax.annotation.Nullable;
import java.awt.*;

// TODO: rework entire class to use TextDisplayElements instead of particles
public class DamageIndicatorParticle extends Particle {

	private final String damage;
	private int color;

	public DamageIndicatorParticle(ClientLevel level, double x, double y, double z,
	                               double xSpeed, double ySpeed, double zSpeed, float damage) {
		super(level, x, y, z);
		this.damage = String.valueOf(Math.round(damage * 100.0) / 100.0);
		lifetime = IWConfigs.CLIENT.damageIndicatorParticleLifetime.getAsInt() * 20;
		xd = xSpeed;
		yd = ySpeed;
		zd = zSpeed;
		color = setColorFromDamage(damage);
	}

	@Override
	public ParticleRenderType getGroup() {
		return ParticleRenderType.SINGLE_QUADS;
	}

	private int setColorFromDamage(float damage) {
		if (damage >= 16.0f) {
			return Color.decode(IWConfigs.CLIENT.damageIndicatorHighDamageColor.get()).getRGB();
		} else if (damage >= 8.0f) {
			return lerpColor(
					Color.decode(IWConfigs.CLIENT.damageIndicatorMediumDamageColor.get()),
					Color.decode(IWConfigs.CLIENT.damageIndicatorHighDamageColor.get()),
					(damage - 8.0f) / 8.0f)
					.getRGB();
		} else if (damage >= 2.0f) {
			return lerpColor(
					Color.decode(IWConfigs.CLIENT.damageIndicatorLowDamageColor.get()),
					Color.decode(IWConfigs.CLIENT.damageIndicatorMediumDamageColor.get()),
					(damage - 2.0f) / 6.0f)
					.getRGB();
		} else {
			return Color.decode(IWConfigs.CLIENT.damageIndicatorLowDamageColor.get()).getRGB();
		}
	}

	private Color lerpColor(Color startColor, Color endColor, float delta) {
		return new Color(
				(int) (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * delta),
				(int) (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * delta),
				(int) (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * delta));
	}

	public static class Provider implements ParticleProvider<DamageIndicatorParticleOptions> {

		@Nullable
		@Override
		public Particle createParticle(DamageIndicatorParticleOptions type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource p_447205_) {
			return null;
		}
	}
}