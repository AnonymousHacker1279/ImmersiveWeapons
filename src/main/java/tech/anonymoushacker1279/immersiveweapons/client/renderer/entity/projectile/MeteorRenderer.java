package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.MeteorModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;

public class MeteorRenderer extends EntityRenderer<MeteorEntity, EntityRenderState> {

	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/meteor/meteor.png");
	private static final RenderType RENDER_TYPE = RenderTypes.entityCutout(TEXTURE_LOCATION);
	private final MeteorModel model;

	public MeteorRenderer(Context context) {
		super(context);
		model = new MeteorModel(context.bakeLayer(MeteorModel.LAYER_LOCATION));
	}

	@Override
	public EntityRenderState createRenderState() {
		return new EntityRenderState();
	}

	@Override
	public void submit(EntityRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
		stack.pushPose();
		stack.scale(2f, 2f, 2f);

		stack.translate(-0.125f, 0, -0.125f);

		collector.submitModelPart(model.root(), stack, RENDER_TYPE, state.lightCoords, OverlayTexture.NO_OVERLAY, null);
		stack.popPose();
	}
}