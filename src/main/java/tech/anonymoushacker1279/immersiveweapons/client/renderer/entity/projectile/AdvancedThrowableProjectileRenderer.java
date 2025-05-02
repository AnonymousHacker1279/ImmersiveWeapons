package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.ThrowableProjectileRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.AdvancedThrowableItemProjectile;

public class AdvancedThrowableProjectileRenderer extends EntityRenderer<AdvancedThrowableItemProjectile, ThrowableProjectileRenderState> {

	private final ItemRenderer itemRenderer;

	public AdvancedThrowableProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
		itemRenderer = context.getItemRenderer();
	}

	@Override
	public ThrowableProjectileRenderState createRenderState() {
		return new ThrowableProjectileRenderState();
	}

	@Override
	public void render(ThrowableProjectileRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();

		if (state.movementLengthSqr < 0.01f) {
			poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
			poseStack.mulPose(Axis.ZP.rotationDegrees(state.randomRotation));
			poseStack.translate(0, 0, -0.1f);
		} else {
			// Entity is moving, display it upright
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - state.yRot));
			poseStack.mulPose(Axis.XP.rotationDegrees(state.xRot));
			poseStack.mulPose(Axis.ZP.rotationDegrees(state.yRot + state.randomRotation));
		}

		poseStack.scale(1.25f, 1.25f, 1.25f);

		itemRenderer.render(
				state.stack,
				ItemDisplayContext.NONE,
				false,
				poseStack,
				bufferSource,
				packedLight,
				OverlayTexture.NO_OVERLAY,
				state.model
		);

		poseStack.popPose();
	}

	@Override
	public void extractRenderState(AdvancedThrowableItemProjectile entity, ThrowableProjectileRenderState reusedState, float partialTick) {
		super.extractRenderState(entity, reusedState, partialTick);

		if (reusedState.randomRotation == 0.0f) {
			reusedState.randomRotation = entity.getRandom().nextFloat() * 360.0F;
		}

		reusedState.movementLengthSqr = (float) entity.getDeltaMovement().lengthSqr();
		reusedState.stack = entity.getItem();
		reusedState.model = itemRenderer.getModel(entity.getItem(), entity.level(), null, entity.getId());
	}
}