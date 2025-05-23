package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.FireflyModel;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.FireflyRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.FireflyEntity;

public class FireflyRenderer extends MobRenderer<FireflyEntity, FireflyRenderState, FireflyModel> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/firefly/firefly.png");

	public FireflyRenderer(EntityRendererProvider.Context context) {
		super(context, new FireflyModel(context.bakeLayer(FireflyModel.LAYER_LOCATION)), 0.01F);
	}

	@Override
	public FireflyRenderState createRenderState() {
		return new FireflyRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(FireflyRenderState state) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected void scale(FireflyRenderState renderState, PoseStack poseStack) {
		poseStack.scale(0.35f, 0.35f, 0.35f);
	}

	@Override
	public void render(FireflyRenderState renderState, PoseStack stack, MultiBufferSource bufferSource, int light) {
		if (renderState.isResting) {
			switch (renderState.facing) {
				case NORTH, SOUTH -> stack.mulPose(Axis.XP.rotationDegrees(90));
				case EAST, WEST -> stack.mulPose(Axis.ZP.rotationDegrees(90));
			}
		}

		super.render(renderState, stack, bufferSource, light);
	}

	@Override
	public void extractRenderState(FireflyEntity entity, FireflyRenderState reusedState, float partialTick) {
		super.extractRenderState(entity, reusedState, partialTick);

		reusedState.isResting = entity.isResting();
		reusedState.facing = entity.getDirection();
	}

	@Override
	protected int getBlockLightLevel(FireflyEntity entity, BlockPos pos) {
		return 15;
	}
}