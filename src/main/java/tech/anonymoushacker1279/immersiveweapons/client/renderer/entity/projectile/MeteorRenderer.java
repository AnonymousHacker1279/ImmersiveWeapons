package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.MeteorModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;

public class MeteorRenderer extends EntityRenderer<MeteorEntity, EntityRenderState> {

	private final MeteorModel model;

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/meteor/meteor.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);

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