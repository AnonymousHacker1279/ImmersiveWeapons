package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.MusketBallModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;

public class MusketBallRenderer extends EntityRenderer<BulletEntity, LivingEntityRenderState> {

	private final ResourceLocation textureLocation;
	private final MusketBallModel model;

	public MusketBallRenderer(Context context, String name) {
		super(context);
		textureLocation = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/item/%s_musket_ball.png".formatted(name));
		model = new MusketBallModel(context.bakeLayer(MusketBallModel.LAYER_LOCATION));
	}

	public MusketBallRenderer(Context context, ResourceLocation location) {
		super(context);
		textureLocation = location;
		model = new MusketBallModel(context.bakeLayer(MusketBallModel.LAYER_LOCATION));
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void render(LivingEntityRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();

		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
		poseStack.mulPose(Axis.XP.rotationDegrees(state.xRot));

		poseStack.scale(1.25f, 1.25f, 1.25f);
		poseStack.translate(-0.025f, 0.025f, -0.025f);

		model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(textureLocation)), packedLight, OverlayTexture.NO_OVERLAY, 16777215);

		poseStack.popPose();
	}
}