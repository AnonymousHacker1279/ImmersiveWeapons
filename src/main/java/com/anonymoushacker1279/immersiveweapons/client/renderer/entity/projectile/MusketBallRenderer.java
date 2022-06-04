package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MusketBallRenderer<T extends BulletEntity> extends EntityRenderer<T> {

	public MusketBallRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(BulletEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {
		poseStack.pushPose();

		poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		poseStack.mulPose(Vector3f.YP.rotationDegrees(entityYaw));
		poseStack.mulPose(Vector3f.XP.rotationDegrees(entity.getXRot()));

		poseStack.scale(1.6f, 1.6f, 1.6f);
		poseStack.translate(0, 0.3f, 0);

		Minecraft.getInstance().getItemRenderer().renderStatic(entity.getPickupItem(), ItemTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, renderTypeBuffer, 0);

		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull T pEntity) {
		return null;
	}
}