package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.SkeletonMerchantModel;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkeletonMerchantEntity;

public class SkeletonMerchantClothingLayer<T extends SkeletonMerchantEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

	private static final ResourceLocation STRAY_CLOTHES_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/skeleton_merchant/skeleton_merchant_overlay.png");
	private final SkeletonMerchantModel<T> layerModel;

	public SkeletonMerchantClothingLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
		super(pRenderer);
		this.layerModel = new SkeletonMerchantModel<>(pModelSet.bakeLayer(ModelLayers.STRAY_OUTER_LAYER));
	}

	public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight,
	                   T pLivingEntity, float pLimbSwing, float pLimbSwingAmount,
	                   float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

		coloredCutoutModelCopyLayerRender(this.getParentModel(), this.layerModel, STRAY_CLOTHES_LOCATION, pMatrixStack,
				pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw,
				pHeadPitch, pPartialTicks, 1.0F, 1.0F, 1.0F);
	}
}