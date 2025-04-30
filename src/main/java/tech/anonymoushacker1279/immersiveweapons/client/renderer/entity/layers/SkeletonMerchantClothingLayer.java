package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.SkeletonMerchantModel;

public class SkeletonMerchantClothingLayer<S extends SkeletonRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {

	private static final ResourceLocation CLOTHES_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/skeleton_merchant/skeleton_merchant_overlay.png");
	private final SkeletonMerchantModel layerModel;

	public SkeletonMerchantClothingLayer(RenderLayerParent<S, M> pRenderer, EntityModelSet pModelSet) {
		super(pRenderer);
		this.layerModel = new SkeletonMerchantModel(pModelSet.bakeLayer(ModelLayers.STRAY_OUTER_LAYER));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
		coloredCutoutModelCopyLayerRender(layerModel, CLOTHES_LOCATION, poseStack, bufferSource, packedLight, renderState, -1);
	}
}