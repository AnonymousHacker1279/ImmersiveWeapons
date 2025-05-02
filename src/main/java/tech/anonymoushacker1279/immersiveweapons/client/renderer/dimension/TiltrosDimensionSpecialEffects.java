package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class TiltrosDimensionSpecialEffects extends DimensionSpecialEffects {

	private static final ResourceLocation SKY_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
			"textures/environment/tiltros_sky.png");

	public TiltrosDimensionSpecialEffects() {
		super(Float.NaN, true, SkyType.END, true, false);
	}

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 scale, float pBrightness) {
		return scale.scale(0.15F);
	}

	@Override
	public boolean isFoggyAt(int pX, int pY) {
		return false;
	}

	@Override
	public int getSunriseOrSunsetColor(float timeOfDay) {
		return 0xFFFFFF;
	}

	// TODO: investigate why sky appears to move when looking around
	@Override
	public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, Runnable setupFog) {
		PoseStack poseStack = new PoseStack();
		poseStack.mulPose(modelViewMatrix);

		RenderSystem.enableBlend();
		RenderSystem.depthMask(false);
		RenderSystem.setShader(CoreShaders.POSITION_TEX_COLOR);
		RenderSystem.setShaderTexture(0, SKY_LOCATION);
		Tesselator tesselator = Tesselator.getInstance();

		for (int i = 0; i < 6; ++i) {
			poseStack.pushPose();
			if (i == 1) {
				poseStack.mulPose(Axis.XP.rotationDegrees(90.0F)); // North
			}

			if (i == 2) {
				poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F)); // South
			}

			if (i == 3) {
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F)); // Up
			}

			if (i == 4) {
				poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F)); // East
			}

			if (i == 5) {
				poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F)); // West
			}

			Matrix4f poseMatrix = poseStack.last().pose();
			BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			bufferBuilder.addVertex(poseMatrix, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(255, 255, 255, 75);
			bufferBuilder.addVertex(poseMatrix, -100.0F, -100.0F, 100.0F).setUv(0.0F, 1.0F).setColor(255, 255, 255, 75);
			bufferBuilder.addVertex(poseMatrix, 100.0F, -100.0F, 100.0F).setUv(1.0F, 1.0F).setColor(255, 255, 255, 75);
			bufferBuilder.addVertex(poseMatrix, 100.0F, -100.0F, -100.0F).setUv(1.0F, 0.0F).setColor(255, 255, 255, 75);
			BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
			poseStack.popPose();
		}

		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();

		return true;
	}
}