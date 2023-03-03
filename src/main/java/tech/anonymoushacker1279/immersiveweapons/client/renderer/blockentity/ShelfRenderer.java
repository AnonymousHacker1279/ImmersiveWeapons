package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.ShelfBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.ShelfBlockEntity;

public class ShelfRenderer implements BlockEntityRenderer<ShelfBlockEntity> {

	/**
	 * Constructor for ShelfRenderer.
	 */
	public ShelfRenderer() {
	}

	@Override
	public void render(ShelfBlockEntity shelfEntity, float partialTicks, PoseStack poseStack,
	                   MultiBufferSource buffer, int packedLight, int packedOverlay) {

		Direction direction = shelfEntity.getBlockState().getValue(ShelfBlock.FACING);
		NonNullList<ItemStack> inventory = shelfEntity.getInventory();

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
				// Actually render the item
				Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED,
						packedLight, packedOverlay, poseStack, buffer, 0);

				poseStack.popPose();
			}
		}
	}
}