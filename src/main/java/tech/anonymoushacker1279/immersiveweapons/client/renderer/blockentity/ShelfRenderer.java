package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.block.ShelfBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.ShelfBlockEntity;

public class ShelfRenderer implements BlockEntityRenderer<ShelfBlockEntity> {

	@Override
	public void render(ShelfBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
		NonNullList<ItemStack> inventory = blockEntity.getInventory();

		if (inventory.isEmpty()) {
			return;
		}

		Direction direction = blockEntity.getBlockState().getValue(ShelfBlock.FACING);
		for (ItemStack stack : inventory) {
			if (stack != ItemStack.EMPTY) {
				poseStack.pushPose();
				// Actual position of the item
				poseStack.translate(0.5D, 0.0D, 0.5D);

				// Rotate by direction
				switch (direction) {
					case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(270f));
					case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180f));
					case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(90f));
					default -> poseStack.mulPose(Axis.YP.rotationDegrees(0f));
				}

				// Rotation occurs here
				poseStack.mulPose(Axis.XP.rotationDegrees(50f));
				poseStack.translate(0.0D, 0.10D, -0.10D);
				if (inventory.get(0) == stack) {
					// First item goes on bottom left
					poseStack.translate(-0.3125D, 0.3125D, 0.15D);
				} else if (inventory.get(1) == stack) {
					// Second item goes on bottom right
					poseStack.translate(0.3125D, 0.3125D, 0.15D);
				} else if (inventory.get(2) == stack) {
					// Third item goes on top left
					poseStack.translate(-0.3125D, 0.6D, -0.17D);
				} else if (inventory.get(3) == stack) {
					// Fourth item goes on top right
					poseStack.translate(0.3125D, 0.6D, -0.17D);
				}

				// Scale render
				poseStack.scale(0.375F, 0.375F, 0.375F);

				if (blockEntity.getLevel() != null) {
					Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight,
							packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
				}

				poseStack.popPose();
			}
		}
	}
}