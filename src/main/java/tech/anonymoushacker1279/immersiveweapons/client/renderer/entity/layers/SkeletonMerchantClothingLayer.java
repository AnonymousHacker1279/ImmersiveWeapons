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

	private static final ResourceLocation CLOTHES_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/skeleton_merchant/skeleton_merchant_overlay.png");
	private final SkeletonMerchantModel<T> layerModel;

	public SkeletonMerchantClothingLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
		super(pRenderer);
		this.layerModel = new SkeletonMerchantModel<>(pModelSet.bakeLayer(ModelLayers.STRAY_OUTER_LAYER));
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity,
	                   float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
	                   float netHeadYaw, float headPitch) {

		coloredCutoutModelCopyLayerRender(
				this.getParentModel(),
				this.layerModel,
				CLOTHES_LOCATION,
				poseStack,
				bufferSource,
				packedLight,
				livingEntity,
				limbSwing,
				limbSwingAmount,
				ageInTicks,
				netHeadYaw,
				headPitch,
				partialTick,
				-1
		);
	}
}