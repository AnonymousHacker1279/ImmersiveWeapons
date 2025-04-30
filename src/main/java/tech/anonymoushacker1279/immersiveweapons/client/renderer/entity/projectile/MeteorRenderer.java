package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.MeteorModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;

public class MeteorRenderer extends EntityRenderer<MeteorEntity, EntityRenderState> {

	private final MeteorModel model;

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/meteor/meteor.png");

	public MeteorRenderer(Context context) {
		super(context);
		model = new MeteorModel(context.bakeLayer(MeteorModel.LAYER_LOCATION));
	}

	@Override
	public EntityRenderState createRenderState() {
		return new EntityRenderState();
	}

	@Override
	public void render(EntityRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();
		poseStack.scale(2f, 2f, 2f);

		poseStack.translate(-0.125f, 0, -0.125f);

		model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE_LOCATION)), packedLight, OverlayTexture.NO_OVERLAY, 16777215);
		poseStack.popPose();

		super.render(renderState, poseStack, bufferSource, packedLight);
	}
}