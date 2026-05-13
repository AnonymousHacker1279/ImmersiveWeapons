package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.MusketBallModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;

public class MusketBallRenderer extends EntityRenderer<BulletEntity, LivingEntityRenderState> {

	private final MusketBallModel model;
	private final RenderType renderType;

	public MusketBallRenderer(Context context, String name) {
		this(context, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/item/%s_musket_ball.png".formatted(name)));
	}

	public MusketBallRenderer(Context context, Identifier location) {
		super(context);
		model = new MusketBallModel(context.bakeLayer(MusketBallModel.LAYER_LOCATION));
		renderType = RenderTypes.entityCutout(location);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void submit(LivingEntityRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
		stack.pushPose();

		stack.mulPose(Axis.YP.rotationDegrees(state.yRot));
		stack.mulPose(Axis.XP.rotationDegrees(state.xRot));

		stack.scale(1.25f, 1.25f, 1.25f);
		stack.translate(-0.025f, 0.025f, -0.025f);

		collector.submitModelPart(model.root(), stack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, null);

		stack.popPose();
	}
}