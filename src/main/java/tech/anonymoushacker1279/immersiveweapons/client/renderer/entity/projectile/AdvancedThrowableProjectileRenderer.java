package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.AdvancedThrowableItemProjectile;

public class AdvancedThrowableProjectileRenderer<T extends AdvancedThrowableItemProjectile> extends EntityRenderer<T, LivingEntityRenderState> {

	public AdvancedThrowableProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	// TODO: need custom render state + custom model
	/*@Override
	public void render(LivingEntityRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();

		if (state.randomRotation == 0.0f) {
			state.randomRotation = state.level().getRandom().nextFloat() * 360.0f;
		}

		if (state.getDeltaMovement().lengthSqr() < 0.01) {
			poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(state.randomRotation));
			poseStack.translate(0, 0, -0.1f);
		} else {
			// Entity is moving, display it upright
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
			poseStack.mulPose(Axis.XP.rotationDegrees(state.getXRot()));
			poseStack.mulPose(Axis.ZP.rotationDegrees(state.getYRot() + state.randomRotation));
		}

		poseStack.scale(1.25f, 1.25f, 1.25f);

		Minecraft.getInstance().getItemRenderer().renderStatic(state.getItem(), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, state.level(), 0);

		poseStack.popPose();
	}*/
}