package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.ThrowableProjectileRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.AdvancedThrowableItemProjectile;

public class AdvancedThrowableProjectileRenderer extends EntityRenderer<AdvancedThrowableItemProjectile, ThrowableProjectileRenderState> {

	private final ItemModelResolver itemModelResolver;

	public AdvancedThrowableProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
		itemModelResolver = context.getItemModelResolver();
	}

	@Override
	public ThrowableProjectileRenderState createRenderState() {
		return new ThrowableProjectileRenderState();
	}

	@Override
	public void submit(ThrowableProjectileRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
		stack.pushPose();

		if (state.movementLengthSqr < 0.01f) {
			stack.mulPose(Axis.XP.rotationDegrees(90.0F));
			stack.translate(0.0D, 0.4D, 0.0D);
		} else {
			stack.translate(0.0D, 0.6D, 0.0D);
		}

		stack.scale(1.25f, 1.25f, 1.25f);

		state.stackRenderState.submit(stack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);

		stack.popPose();
	}

	@Override
	public void extractRenderState(AdvancedThrowableItemProjectile entity, ThrowableProjectileRenderState reusedState, float partialTick) {
		super.extractRenderState(entity, reusedState, partialTick);

		reusedState.movementLengthSqr = (float) entity.getDeltaMovement().lengthSqr();
		itemModelResolver.updateForNonLiving(reusedState.stackRenderState, entity.getItem(), ItemDisplayContext.NONE, entity);
	}
}