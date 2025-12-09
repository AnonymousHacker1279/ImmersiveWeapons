package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.ShelfBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.ShelfBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.state.GenericDirectionalInventoryRenderState;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.state.GenericInventoryRenderState;

public record ShelfRenderer(
		ItemModelResolver itemModelResolver) implements BlockEntityRenderer<ShelfBlockEntity, GenericDirectionalInventoryRenderState> {

	@Override
	public GenericDirectionalInventoryRenderState createRenderState() {
		return new GenericDirectionalInventoryRenderState(4);
	}

	@Override
	public void extractRenderState(ShelfBlockEntity blockEntity, GenericDirectionalInventoryRenderState state, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
		GenericInventoryRenderState.extractBaseState(blockEntity, state, itemModelResolver, partialTick, crumblingOverlay);
		state.facing = blockEntity.getBlockState().getValue(ShelfBlock.FACING);
	}

	@Override
	public void submit(GenericDirectionalInventoryRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
		for (ItemStackRenderState itemStackRenderState : state.items) {
			if (itemStackRenderState != null) {
				stack.pushPose();
				// Actual position of the item
				stack.translate(0.5D, 0.0D, 0.5D);

				// Rotate by direction
				switch (state.facing) {
					case EAST -> stack.mulPose(Axis.YP.rotationDegrees(270f));
					case SOUTH -> stack.mulPose(Axis.YP.rotationDegrees(180f));
					case WEST -> stack.mulPose(Axis.YP.rotationDegrees(90f));
					default -> stack.mulPose(Axis.YP.rotationDegrees(0f));
				}

				// Rotation occurs here
				stack.mulPose(Axis.XP.rotationDegrees(50f));
				stack.translate(0.0D, 0.10D, -0.10D);
				if (state.items[0] == itemStackRenderState) {
					// First item goes on bottom left
					stack.translate(-0.3125D, 0.3125D, 0.15D);
				} else if (state.items[1] == itemStackRenderState) {
					// Second item goes on bottom right
					stack.translate(0.3125D, 0.3125D, 0.15D);
				} else if (state.items[2] == itemStackRenderState) {
					// Third item goes on top left
					stack.translate(-0.3125D, 0.6D, -0.17D);
				} else if (state.items[3] == itemStackRenderState) {
					// Fourth item goes on top right
					stack.translate(0.3125D, 0.6D, -0.17D);
				}

				// Scale render
				stack.scale(0.375F, 0.375F, 0.375F);

				itemStackRenderState.submit(stack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

				stack.popPose();
			}
		}
	}
}