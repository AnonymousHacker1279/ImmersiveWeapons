package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.MooGlowRenderState;

public class MooGlowMoonglowLayer extends RenderLayer<MooGlowRenderState, CowModel> {

	public MooGlowMoonglowLayer(RenderLayerParent<MooGlowRenderState, CowModel> renderer) {
		super(renderer);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, MooGlowRenderState renderState, float yRot, float xRot) {
		if (!renderState.isBaby && !renderState.moonglowModel.isEmpty()) {
			boolean appearsGlowingWithInvisibility = renderState.appearsGlowing() && renderState.isInvisible;
			if (!renderState.isInvisible || appearsGlowingWithInvisibility) {
				int overlayCoords = LivingEntityRenderer.getOverlayCoords(renderState, 0.0F);
				poseStack.pushPose();
				poseStack.translate(0.2F, -0.35F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
				poseStack.scale(-0.5F, -0.5F, 0.5F);
				poseStack.translate(-0.5F, -1.0F, -0.5F);
				submitBlock(poseStack, nodeCollector, 0xFF, appearsGlowingWithInvisibility, renderState.outlineColor, renderState.moonglowModel, overlayCoords);

				poseStack.popPose();
				poseStack.pushPose();
				poseStack.translate(0.2F, -0.35F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(42.0F));
				poseStack.translate(0.1F, 0.0F, -0.6F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
				poseStack.scale(-0.5F, -0.5F, 0.5F);
				poseStack.translate(-0.5F, -1.0F, -0.5F);
				submitBlock(poseStack, nodeCollector, 0xFF, appearsGlowingWithInvisibility, renderState.outlineColor, renderState.moonglowModel, overlayCoords);

				poseStack.popPose();
				poseStack.pushPose();
				getParentModel().getHead().translateAndRotate(poseStack);

				poseStack.translate(0.0F, -0.7F, -0.2F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-78.0F));
				poseStack.scale(-0.5F, -0.5F, 0.5F);
				poseStack.translate(-0.5F, -1.0F, -0.5F);
				submitBlock(poseStack, nodeCollector, 0xFF, appearsGlowingWithInvisibility, renderState.outlineColor, renderState.moonglowModel, overlayCoords);

				poseStack.popPose();
			}
		}
	}

	private void submitBlock(
			PoseStack poseStack,
			SubmitNodeCollector submitNodeCollector,
			int lightCoords,
			boolean appearsGlowingWithInvisibility,
			int outlineColor,
			BlockModelRenderState model,
			int overlayCoords
	) {
		if (appearsGlowingWithInvisibility) {
			model.submitOnlyOutline(poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);
		} else {
			model.submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);
		}
	}
}