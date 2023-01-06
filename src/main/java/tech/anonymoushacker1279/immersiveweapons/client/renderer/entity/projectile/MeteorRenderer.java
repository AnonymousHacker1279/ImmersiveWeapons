package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.MeteorModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;

public class MeteorRenderer<T extends MeteorEntity> extends EntityRenderer<T> {

	protected MeteorModel<MeteorEntity> model;

	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/meteor/meteor.png");

	public MeteorRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new MeteorModel<>(context.bakeLayer(MeteorModel.LAYER_LOCATION));
	}

	@Override
	public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
		// Render the meteor model
		pMatrixStack.pushPose();
		pMatrixStack.scale(2f, 2f, 2f);

		// Offset the model to the center, to compensate for the scaling
		pMatrixStack.translate(-0.125f, 0, -0.125f);

		model.renderToBuffer(pMatrixStack, pBuffer.getBuffer(RenderType.entityTranslucent(TEXTURE_LOCATION)), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		pMatrixStack.popPose();

		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return TEXTURE_LOCATION;
	}
}