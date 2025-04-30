package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.EvilEyeModel;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;

public class EvilEyeRenderer extends MobRenderer<EvilEyeEntity, LivingEntityRenderState, EvilEyeModel> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/evil_eye/evil_eye.png");
	private static final ResourceLocation ALT_TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/evil_eye/evil_eye_alt.png");

	public EvilEyeRenderer(EntityRendererProvider.Context context) {
		super(context, new EvilEyeModel(context.bakeLayer(EvilEyeModel.LAYER_LOCATION)), 0.15F);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		// return state.summonedByStaff() ? ALT_TEXTURE_LOCATION : TEXTURE_LOCATION;
		return TEXTURE_LOCATION;    // TODO: need custom render state
	}

	@Override
	protected void scale(LivingEntityRenderState state, PoseStack pMatrixStack) {
		// int size = state.getSize();
		int size = 1; // TODO: need custom render state
		float scale = 1.0F * size;
		pMatrixStack.scale(scale, scale, scale);
		pMatrixStack.translate(0.0D, 1.125D, 0D);
	}
}