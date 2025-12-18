package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

public class MooGlowMoonglowLayer extends RenderLayer<LivingEntityRenderState, CowModel> {

	private final BlockRenderDispatcher blockRenderer;
	private final BlockState moonglow = BlockRegistry.MOONGLOW.get().defaultBlockState();

	public MooGlowMoonglowLayer(RenderLayerParent<LivingEntityRenderState, CowModel> renderer, BlockRenderDispatcher blockRenderer) {
		super(renderer);
		this.blockRenderer = blockRenderer;
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, LivingEntityRenderState renderState, float yRot, float xRot) {
		if (!renderState.isBaby) {
			boolean flag = renderState.appearsGlowing() && renderState.isInvisible;
			if (!renderState.isInvisible || flag) {
				int overlayCoords = LivingEntityRenderer.getOverlayCoords(renderState, 0.0F);
				BlockStateModel moonglowModel = blockRenderer.getBlockModel(moonglow);

				poseStack.pushPose();
				poseStack.translate(0.2F, -0.35F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
				poseStack.scale(-0.5F, -0.5F, 0.5F);
				poseStack.translate(-0.5F, -1.0F, -0.5F);
				submitBlock(poseStack, nodeCollector, 0xFF, flag, renderState.outlineColor, moonglow, overlayCoords, moonglowModel);

				poseStack.popPose();
				poseStack.pushPose();
				poseStack.translate(0.2F, -0.35F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(42.0F));
				poseStack.translate(0.1F, 0.0F, -0.6F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
				poseStack.scale(-0.5F, -0.5F, 0.5F);
				poseStack.translate(-0.5F, -1.0F, -0.5F);
				submitBlock(poseStack, nodeCollector, 0xFF, flag, renderState.outlineColor, moonglow, overlayCoords, moonglowModel);

				poseStack.popPose();
				poseStack.pushPose();
				getParentModel().getHead().translateAndRotate(poseStack);

				poseStack.translate(0.0F, -0.7F, -0.2F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-78.0F));
				poseStack.scale(-0.5F, -0.5F, 0.5F);
				poseStack.translate(-0.5F, -1.0F, -0.5F);
				submitBlock(poseStack, nodeCollector, 0xFF, flag, renderState.outlineColor, moonglow, overlayCoords, moonglowModel);

				poseStack.popPose();
			}
		}
	}

	private void submitBlock(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, boolean renderOutline, int outlineColor, BlockState blockState, int packedOverlay, BlockStateModel model) {
		if (renderOutline) {
			nodeCollector.submitBlockModel(poseStack, RenderTypes.outline(TextureAtlas.LOCATION_BLOCKS), model, 0.0F, 0.0F, 0.0F, packedLight, packedOverlay, outlineColor);
		} else {
			nodeCollector.submitBlock(poseStack, blockState, packedLight, packedOverlay, outlineColor);
		}
	}
}