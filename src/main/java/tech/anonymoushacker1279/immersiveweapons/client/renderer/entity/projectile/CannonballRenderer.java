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
import tech.anonymoushacker1279.immersiveweapons.client.model.CannonballModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CannonballEntity;

public class CannonballRenderer extends EntityRenderer<CannonballEntity, LivingEntityRenderState> {

	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/item/cannonball.png");
	private static final RenderType RENDER_TYPE = RenderTypes.entityCutout(TEXTURE_LOCATION);
	private final CannonballModel model;

	public CannonballRenderer(Context context) {
		super(context);
		model = new CannonballModel(context.bakeLayer(CannonballModel.LAYER_LOCATION));
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
		stack.translate(-0.125f, 0.0125f, -0.125f);

		collector.submitModelPart(model.root(), stack, RENDER_TYPE, state.lightCoords, OverlayTexture.NO_OVERLAY, null);

		stack.popPose();
	}
}