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
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity;

public class SmokeGrenadeRenderer<T extends SmokeGrenadeEntity> extends EntityRenderer<T> {

	public SmokeGrenadeRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(SmokeGrenadeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		poseStack.pushPose();

		if (entity.randomRotation == 0.0f) {
			entity.randomRotation = entity.level().getRandom().nextFloat() * 360.0f;
		}

		if (entity.getDeltaMovement().lengthSqr() < 0.01) {
			poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(entity.randomRotation));
			poseStack.translate(0, 0, -0.1f);
		} else {
			// Entity is moving, display it upright
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
			poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
			poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getYRot() + entity.randomRotation));
		}

		poseStack.scale(1.25f, 1.25f, 1.25f);

		Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), 0);

		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return null;
	}
}