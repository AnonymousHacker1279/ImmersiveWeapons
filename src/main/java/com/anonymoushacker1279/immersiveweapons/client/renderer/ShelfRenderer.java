package com.anonymoushacker1279.immersiveweapons.client.renderer;

import com.anonymoushacker1279.immersiveweapons.block.ShelfBlock;
import com.anonymoushacker1279.immersiveweapons.tileentity.WallShelfTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class ShelfRenderer extends TileEntityRenderer<WallShelfTileEntity> {

	public ShelfRenderer(TileEntityRendererDispatcher renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void render(WallShelfTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Direction direction = tileEntityIn.getBlockState().getValue(ShelfBlock.FACING);
		NonNullList<ItemStack> nonnulllist = tileEntityIn.getInventory();

		for (ItemStack itemstack : nonnulllist) {
			if (itemstack != ItemStack.EMPTY) {
				matrixStackIn.pushPose();
				// Actual position of the item
				matrixStackIn.translate(0.5D, 0.0D, 0.5D);

				// Rotate by direction
				switch (direction) {
					case NORTH:
						matrixStackIn.mulPose(new Quaternion(Vector3f.YP, 0, true));
						break;
					case EAST:
						matrixStackIn.mulPose(new Quaternion(Vector3f.YP, 270, true));
						break;
					case SOUTH:
						matrixStackIn.mulPose(new Quaternion(Vector3f.YP, 180, true));
						break;
					case WEST:
						matrixStackIn.mulPose(new Quaternion(Vector3f.YP, 90, true));
						break;
				}
				// Rotation occurs here
				matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(50f));
				matrixStackIn.translate(0.0D, 0.10D, -0.10D);
				if (nonnulllist.get(0) == itemstack) {
					// First item goes on bottom left
					matrixStackIn.translate(-0.3125D, 0.3125D, 0.15D);
				} else if (nonnulllist.get(1) == itemstack) {
					// Second item goes on bottom right
					matrixStackIn.translate(0.3125D, 0.3125D, 0.15D);
				} else if (nonnulllist.get(2) == itemstack) {
					// Third item goes on top left
					matrixStackIn.translate(-0.3125D, 0.6D, -0.17D);
				} else if (nonnulllist.get(3) == itemstack) {
					// Fourth item goes on top right
					matrixStackIn.translate(0.3125D, 0.6D, -0.17D);
				}

				// Scale render
				matrixStackIn.scale(0.375F, 0.375F, 0.375F);
				// Actually render the item
				Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
				matrixStackIn.popPose();
			}
		}
	}
}