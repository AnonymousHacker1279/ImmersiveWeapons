package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.DragonFireballBulletEntity;

public class DragonFireballBulletRenderer<T extends DragonFireballBulletEntity> extends EntityRenderer<T> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/enderdragon/dragon_fireball.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

	public DragonFireballBulletRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	protected int getBlockLightLevel(T entity, BlockPos pos) {
		return 15;
	}

	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		poseStack.mulPose(entityRenderDispatcher.cameraOrientation());
		PoseStack.Pose lastPose = poseStack.last();
		VertexConsumer cutoutBuffer = buffer.getBuffer(RENDER_TYPE);
		vertex(cutoutBuffer, lastPose, packedLight, 0.0F, 0, 0, 1);
		vertex(cutoutBuffer, lastPose, packedLight, 1.0F, 0, 1, 1);
		vertex(cutoutBuffer, lastPose, packedLight, 1.0F, 1, 1, 0);
		vertex(cutoutBuffer, lastPose, packedLight, 0.0F, 1, 0, 0);
		poseStack.popPose();

		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
		consumer.addVertex(pose, x - 0.5F, (float) y - 0.25F, 0.0F)
				.setColor(-1)
				.setUv((float) u, (float) v)
				.setOverlay(OverlayTexture.NO_OVERLAY)
				.setLight(packedLight)
				.setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	public ResourceLocation getTextureLocation(T entity) {
		return TEXTURE_LOCATION;
	}
}