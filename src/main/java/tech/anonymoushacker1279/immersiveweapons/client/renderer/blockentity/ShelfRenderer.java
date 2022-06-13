package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.ShelfBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.WallShelfBlockEntity;

public class ShelfRenderer implements BlockEntityRenderer<WallShelfBlockEntity> {

	/**
	 * Constructor for ShelfRenderer.
	 */
	public ShelfRenderer() {
	}

	/**
	 * Render the tile entity.
	 *
	 * @param tileEntityIn      the <code>WallShelfTileEntity</code> instance
	 * @param partialTicks      the current partial tick
	 * @param matrixStackIn     the <code>MatrixStack</code> instance
	 * @param bufferIn          the <code>IRenderTypeBuffer</code> instance
	 * @param combinedLightIn   the combined light value
	 * @param combinedOverlayIn the combined overlay value
	 */
	@Override
	public void render(WallShelfBlockEntity tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Direction direction = tileEntityIn.getBlockState().getValue(ShelfBlock.FACING);
		NonNullList<ItemStack> tileEntityInventory = tileEntityIn.getInventory();

		for (ItemStack itemstack : tileEntityInventory) {
			if (itemstack != ItemStack.EMPTY) {
				matrixStackIn.pushPose();
				// Actual position of the item
				matrixStackIn.translate(0.5D, 0.0D, 0.5D);

				// Rotate by direction
				switch (direction) {
					case EAST -> matrixStackIn.mulPose(new Quaternion(Vector3f.YP, 270, true));
					case SOUTH -> matrixStackIn.mulPose(new Quaternion(Vector3f.YP, 180, true));
					case WEST -> matrixStackIn.mulPose(new Quaternion(Vector3f.YP, 90, true));
					default -> matrixStackIn.mulPose(new Quaternion(Vector3f.YP, 0, true));
				}
				// Rotation occurs here
				matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(50f));
				matrixStackIn.translate(0.0D, 0.10D, -0.10D);
				if (tileEntityInventory.get(0) == itemstack) {
					// First item goes on bottom left
					matrixStackIn.translate(-0.3125D, 0.3125D, 0.15D);
				} else if (tileEntityInventory.get(1) == itemstack) {
					// Second item goes on bottom right
					matrixStackIn.translate(0.3125D, 0.3125D, 0.15D);
				} else if (tileEntityInventory.get(2) == itemstack) {
					// Third item goes on top left
					matrixStackIn.translate(-0.3125D, 0.6D, -0.17D);
				} else if (tileEntityInventory.get(3) == itemstack) {
					// Fourth item goes on top right
					matrixStackIn.translate(0.3125D, 0.6D, -0.17D);
				}

				// Scale render
				matrixStackIn.scale(0.375F, 0.375F, 0.375F);
				// Actually render the item
				Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
				matrixStackIn.popPose();
			}
		}
	}
}