package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.EvilEyeModel;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;

public class EvilEyeRenderer extends MobRenderer<EvilEyeEntity, EvilEyeModel<EvilEyeEntity>> {

	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/evil_eye/evil_eye.png");
	private static final ResourceLocation ALT_TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/evil_eye/evil_eye_alt.png");

	public EvilEyeRenderer(EntityRendererProvider.Context context) {
		super(context, new EvilEyeModel<>(context.bakeLayer(EvilEyeModel.LAYER_LOCATION)), 0.15F);
	}

	@Override
	public void render(EvilEyeEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);

		matrixStack.pushPose();

		// Rotate 180 degrees around the Y axis
		matrixStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(180)));

		matrixStack.popPose();
	}

	@Override
	protected void scale(EvilEyeEntity pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
		int size = pLivingEntity.getSize();
		float scale = 1.0F * size;
		pMatrixStack.scale(scale, scale, scale);
		pMatrixStack.translate(0.0D, 1.125D, 0D);
	}

	@Override
	public ResourceLocation getTextureLocation(EvilEyeEntity pEntity) {
		return pEntity.summonedByStaff() ? ALT_TEXTURE_LOCATION : TEXTURE_LOCATION;
	}
}