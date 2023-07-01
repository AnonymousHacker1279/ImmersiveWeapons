package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.BulletEntity;

public class MusketBallRenderer<T extends BulletEntity> extends EntityRenderer<T> {

	public MusketBallRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(BulletEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		poseStack.pushPose();

		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
		poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));

		poseStack.scale(1.6f, 1.6f, 1.6f);
		poseStack.translate(0, 0.3f, 0);

		Minecraft.getInstance().getItemRenderer().renderStatic(entity.getPickupItem(), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), 0);

		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return null;
	}
}