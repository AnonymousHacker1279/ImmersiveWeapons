package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.DragonFireballBulletEntity;

public class DragonFireballBulletRenderer extends EntityRenderer<DragonFireballBulletEntity, EntityRenderState> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/enderdragon/dragon_fireball.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

	public DragonFireballBulletRenderer(Context context) {
		super(context);
	}

	@Override
	public EntityRenderState createRenderState() {
		return new EntityRenderState();
	}

	@Override
	protected int getBlockLightLevel(DragonFireballBulletEntity entity, BlockPos pos) {
		return 15;
	}

	@Override
	public void submit(EntityRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
		stack.pushPose();
		stack.scale(2.0F, 2.0F, 2.0F);
		stack.mulPose(cameraState.orientation);
		collector.submitCustomGeometry(stack, RENDER_TYPE, (stack2, consumer) -> {
			vertex(consumer, stack2, state.lightCoords, 0.0F, 0, 0, 1);
			vertex(consumer, stack2, state.lightCoords, 1.0F, 0, 1, 1);
			vertex(consumer, stack2, state.lightCoords, 1.0F, 1, 1, 0);
			vertex(consumer, stack2, state.lightCoords, 0.0F, 1, 0, 0);
		});
		stack.popPose();
		super.submit(state, stack, collector, cameraState);
	}

	private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
		consumer.addVertex(pose, x - 0.5F, (float) y - 0.25F, 0.0F)
				.setColor(-1)
				.setUv((float) u, (float) v)
				.setOverlay(OverlayTexture.NO_OVERLAY)
				.setLight(packedLight)
				.setNormal(pose, 0.0F, 1.0F, 0.0F);
	}
}