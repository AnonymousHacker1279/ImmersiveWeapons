package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AstralCrystalBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.state.GenericInventoryRenderState;

public record AstralCrystalRenderer(
		ItemModelResolver itemModelResolver) implements BlockEntityRenderer<AstralCrystalBlockEntity, GenericInventoryRenderState> {

	@Override
	public GenericInventoryRenderState createRenderState() {
		return new GenericInventoryRenderState(4);
	}

	@Override
	public void extractRenderState(AstralCrystalBlockEntity blockEntity, GenericInventoryRenderState state, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
		GenericInventoryRenderState.extractBaseState(blockEntity, state, itemModelResolver, partialTick, crumblingOverlay);
	}

	@Override
	public void submit(GenericInventoryRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
		for (ItemStackRenderState itemStackRenderState : state.items) {
			if (itemStackRenderState != null) {
				stack.pushPose();

				// Rotation occurs here
				stack.mulPose(Axis.XP.rotationDegrees(90f));
				stack.translate(0.5D, 0.5D, -0.25D);
				if (state.items[0] == itemStackRenderState) {
					// First item goes on top
					stack.translate(0D, 1D, 0D);
				} else if (state.items[1] == itemStackRenderState) {
					// Second item goes on right
					stack.translate(-1D, 0D, 0D);
					stack.mulPose(Axis.ZP.rotationDegrees(90f));
				} else if (state.items[2] == itemStackRenderState) {
					// Third item goes on bottom
					stack.translate(0D, -1D, 0D);
					stack.mulPose(Axis.ZP.rotationDegrees(180f));
				} else if (state.items[3] == itemStackRenderState) {
					// Fourth item goes on left
					stack.translate(1D, 0D, 0D);
					stack.mulPose(Axis.ZN.rotationDegrees(90f));
				}

				// Scale render
				stack.scale(0.75F, 0.75F, 0.75F);

				// Rotate all items 45 degrees diagonally
				stack.mulPose(Axis.XN.rotationDegrees(45f));

				Level level = Minecraft.getInstance().level;

				// All the items should smoothly rotate around the center of the block
				if (level != null) {
					stack.mulPose(Axis.ZP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + state.partialTick) * 2f));
					itemStackRenderState.submit(stack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
				}

				stack.popPose();
			}
		}
	}
}