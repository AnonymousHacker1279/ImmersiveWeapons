package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import net.minecraft.world.item.ItemDisplayContext;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.ThrowableProjectileRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.AdvancedThrowableItemProjectile;

public class AdvancedThrowableProjectileRenderer extends EntityRenderer<AdvancedThrowableItemProjectile, ThrowableProjectileRenderState> {

	private static final float MODEL_SCALE = 1.25f;
	/// Height of the model's center above the entity's feet while it is in the air, and while lying on its side.
	private static final float AIRBORNE_CENTER_HEIGHT = 0.25f;
	private static final float RESTING_CENTER_HEIGHT = 0.12f;

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

		// Turn about the middle of the model, which sits at the middle of the entity in the air and rests on the
		// ground, rather than about the corner it was authored from
		float restingCenter = Mth.lerp(state.uprightness, RESTING_CENTER_HEIGHT, AIRBORNE_CENTER_HEIGHT);
		stack.translate(0.0D, Mth.lerp(state.restBlend, AIRBORNE_CENTER_HEIGHT, restingCenter), 0.0D);
		stack.rotate(state.orientation);

		stack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
		stack.translate(0.0D, -state.modelCenterOffset, 0.0D);

		state.stackRenderState.submit(stack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);

		stack.popPose();
	}

	@Override
	public void extractRenderState(AdvancedThrowableItemProjectile entity, ThrowableProjectileRenderState reusedState, float partialTick) {
		super.extractRenderState(entity, reusedState, partialTick);

		reusedState.movementLengthSqr = (float) entity.getDeltaMovement().lengthSqr();

		entity.getOrientation(partialTick, reusedState.orientation);
		reusedState.uprightness = Math.abs(reusedState.orientation.transform(new Vector3f(0.0f, 1.0f, 0.0f)).y());
		reusedState.restBlend = entity.getRestBlend(partialTick);
		reusedState.modelCenterOffset = entity.getModelCenterOffset();
		itemModelResolver.updateForNonLiving(reusedState.stackRenderState, entity.getItem(), ItemDisplayContext.NONE, entity);
	}
}