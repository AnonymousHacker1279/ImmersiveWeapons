package tech.anonymoushacker1279.immersiveweapons.client.particle.damage_indicator;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.ParticleGroupRenderState;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;

import java.util.List;

public class DamageIndicatorParticleGroup extends ParticleGroup<DamageIndicatorParticle> {

	public DamageIndicatorParticleGroup(ParticleEngine engine) {
		super(engine);
	}

	@Override
	public ParticleGroupRenderState extractRenderState(Frustum frustum, Camera camera, float partialTick) {
		return new DamageIndicatorParticleGroupRenderState(particles.stream().map(particle -> new ParticleInstance(
						particle.damage,
						particle.color,
						partialTick,
						particle.getPos(),
						particle.getOldPos(),
						particle.getAge(),
						(float) IWConfigs.CLIENT.damageIndicatorParticleScale.getAsDouble(),
						IWConfigs.CLIENT.damageIndicatorParticleLifetime.getAsInt()))
				.toList());
	}


	record ParticleInstance(String damage, int color, float partialTick, Vec3 pos, Vec3 posOld, int age,
	                        float distanceScale, int lifetime) {
	}

	record DamageIndicatorParticleGroupRenderState(
			List<ParticleInstance> particles) implements ParticleGroupRenderState {

		@Override
		public void submit(SubmitNodeCollector collector, CameraRenderState cameraState) {
			PoseStack poseStack = new PoseStack();

			for (ParticleInstance instance : particles) {
				float x = (float) (instance.posOld().x + (instance.pos().x - instance.posOld().x) * (double) instance.partialTick() - cameraState.pos.x);
				float y = (float) (instance.posOld().y + (instance.pos().y - instance.posOld().y) * (double) instance.partialTick() - cameraState.pos.y);
				float z = (float) (instance.posOld().z + (instance.pos().z - instance.posOld().z) * (double) instance.partialTick() - cameraState.pos.z);

				float scale = (float) new Vec3(x, y, z).length() * 0.015f;
				scale *= instance.distanceScale;

				Matrix4f matrix = new Matrix4f();
				matrix = matrix.translation(x, y, z);
				matrix = matrix.rotate(cameraState.orientation);
				matrix = matrix.rotate((float) Math.PI, 0.0F, 1.0F, 0.0F);
				matrix = matrix.scale(-scale, -scale, -scale);
				poseStack.last().pose().set(matrix);

				Font font = Minecraft.getInstance().font;
				float textX = font.width(instance.damage()) / -2.0F;
				// Ensure the text does not clip into the ground
				float textY = -0.5F - (font.lineHeight / 2.0F);

				int color = instance.color();

				// Fadeout
				int fadeTime = instance.lifetime * 20 - 10;
				if (instance.age() >= fadeTime) {
					float alpha = 1.0f - ((float) (instance.age() - fadeTime) / 10.0f);
					color = (instance.color() & 0x00FFFFFF) | ((int) (alpha * 255) << 24);
				}

				FormattedCharSequence text = FormattedCharSequence.forward(instance.damage(), Style.EMPTY);
				collector.submitText(poseStack, textX, textY, text, false, Font.DisplayMode.NORMAL, 0xFFFFFFFF, color, 0, 15728880);
			}
		}
	}
}