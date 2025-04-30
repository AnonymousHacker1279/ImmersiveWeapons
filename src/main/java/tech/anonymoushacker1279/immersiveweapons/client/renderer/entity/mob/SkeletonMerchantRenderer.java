package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.SkeletonMerchantModel;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkeletonMerchantEntity;

public class SkeletonMerchantRenderer extends HumanoidMobRenderer<SkeletonMerchantEntity, HumanoidRenderState, SkeletonMerchantModel> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/skeleton_merchant/skeleton_merchant.png");

	public SkeletonMerchantRenderer(EntityRendererProvider.Context context) {
		super(context, new SkeletonMerchantModel(context.bakeLayer(SkeletonMerchantModel.LAYER_LOCATION)), 0.5f);

		//addLayer(new SkeletonMerchantClothingLayer<>(this, context.getModelSet()));   TODO: fix
	}

	@Override
	public HumanoidRenderState createRenderState() {
		return new HumanoidRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(HumanoidRenderState renderState) {
		return TEXTURE_LOCATION;
	}
}