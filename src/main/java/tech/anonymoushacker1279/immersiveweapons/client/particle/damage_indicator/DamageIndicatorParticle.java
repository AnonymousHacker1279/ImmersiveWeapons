package tech.anonymoushacker1279.immersiveweapons.client.particle.damage_indicator;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;

import java.awt.*;

public class DamageIndicatorParticle extends Particle {

	private final String damage;
	private int color;

	public static class Provider implements ParticleProvider<DamageIndicatorParticleOptions> {

		@Override
		public Particle createParticle(DamageIndicatorParticleOptions options, ClientLevel level, double x, double y, double z,
		                               double xSpeed, double ySpeed, double zSpeed) {

			return new DamageIndicatorParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.damage());
		}
	}

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
	public void render(VertexConsumer buffer, Camera camera, float partialTick) {
	}

	@Override
	public void renderCustom(PoseStack poseStack, MultiBufferSource bufferSource, Camera camera, float partialTick) {
		Vec3 cameraPos = camera.position();

		float particleX = (float) (xo + (x - xo) * (double) partialTick - cameraPos.x);
		float particleY = (float) (yo + (y - yo) * (double) partialTick - cameraPos.y);
		float particleZ = (float) (zo + (z - zo) * (double) partialTick - cameraPos.z);

		float distanceScale = (float) new Vec3(particleX, particleY, particleZ).length() * 0.015f;
		distanceScale *= (float) IWConfigs.CLIENT.damageIndicatorParticleScale.getAsDouble();

		Matrix4f matrix = new Matrix4f();
		matrix = matrix.translation(particleX, particleY, particleZ);
		matrix = matrix.rotate(camera.rotation());
		matrix = matrix.rotate((float) Math.PI, 0.0F, 1.0F, 0.0F);
		matrix = matrix.scale(-distanceScale, -distanceScale, -distanceScale);

		Font font = Minecraft.getInstance().font;
		float textX = font.width(damage) / -2.0F;
		float textY = 0.0F;

		// Fadeout
		int fadeTime = IWConfigs.CLIENT.damageIndicatorParticleLifetime.getAsInt() * 20 - 10;
		if (age >= fadeTime) {
			float alpha = 1.0f - ((float) (age - fadeTime) / 10.0f);
			color = (color & 0x00FFFFFF) | ((int) (alpha * 255) << 24);
		}

		font.drawInBatch(damage, textX, textY, color, false, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
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
}