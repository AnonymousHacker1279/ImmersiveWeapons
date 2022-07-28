package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import javax.annotation.Nullable;

public class TiltrosDimensionSpecialEffects extends DimensionSpecialEffects {

	private static final ResourceLocation SKY_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"textures/environment/tiltros_sky.png");

	public TiltrosDimensionSpecialEffects() {
		super(Float.NaN, true, SkyType.END, true, false);
	}

	@Override
	public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 scale, float pBrightness) {
		return scale.scale(0.15F);
	}

	@Override
	public boolean isFoggyAt(int pX, int pY) {
		return false;
	}

	@Override
	@Nullable
	public float[] getSunriseColor(float pTimeOfDay, float pPartialTicks) {
		return null;
	}

	@Override
	public boolean renderSky(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack,
	                         @NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy,
	                         @NotNull Runnable setupFog) {

		FogRenderer.setupFog(camera, FogMode.FOG_SKY, 32.0f, false, partialTick);

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, SKY_LOCATION);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tesselator.getBuilder();

		for (int i = 0; i < 6; ++i) {
			poseStack.pushPose();
			if (i == 1) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F)); // North
			}

			if (i == 2) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F)); // South
			}

			if (i == 3) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F)); // Up
			}

			if (i == 4) {
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F)); // East
			}

			if (i == 5) {
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F)); // West
			}

			Matrix4f matrix4f = poseStack.last().pose();
			bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 75).endVertex();
			bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 75).endVertex();
			bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 75).endVertex();
			bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 75).endVertex();
			tesselator.end();
			poseStack.popPose();
		}

		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();

		return true;
	}

	@Override
	public void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken, float skyLight, float blockLight, int pixelX, int pixelY, Vector3f colors) {
		colors.set(colors.x() + 0.12f, colors.y(), colors.z() + 0.22f);
	}
}