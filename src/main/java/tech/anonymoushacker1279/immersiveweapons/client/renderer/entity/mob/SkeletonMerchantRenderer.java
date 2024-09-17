package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.SkeletonMerchantModel;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers.SkeletonMerchantClothingLayer;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkeletonMerchantEntity;

public class SkeletonMerchantRenderer extends HumanoidMobRenderer<SkeletonMerchantEntity, SkeletonMerchantModel<SkeletonMerchantEntity>> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/skeleton_merchant/skeleton_merchant.png");

	public SkeletonMerchantRenderer(EntityRendererProvider.Context context) {
		this(context, SkeletonMerchantModel.LAYER_LOCATION, ModelLayers.STRAY_INNER_ARMOR, ModelLayers.STRAY_OUTER_ARMOR);
	}

	public SkeletonMerchantRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation layerLocation, ModelLayerLocation pInnerModelLayer, ModelLayerLocation pOuterModelLayer) {
		super(pContext, new SkeletonMerchantModel<>(pContext.bakeLayer(layerLocation)), 0.5F);

		addLayer(new HumanoidArmorLayer<>(this,
				new SkeletonMerchantModel<>(pContext.bakeLayer(pInnerModelLayer)),
				new SkeletonMerchantModel<>(pContext.bakeLayer(pOuterModelLayer)), pContext.getModelManager()));
		addLayer(new SkeletonMerchantClothingLayer<>(this, pContext.getModelSet()));
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getTextureLocation(SkeletonMerchantEntity pEntity) {
		return TEXTURE_LOCATION;
	}
}