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
import tech.anonymoushacker1279.immersiveweapons.block.crafting.AmmunitionTableBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AmmunitionTableBlockEntity;

public class AmmunitionTableRenderer implements BlockEntityRenderer<AmmunitionTableBlockEntity> {

	/**
	 * Constructor for ShelfRenderer.
	 */
	public AmmunitionTableRenderer() {
	}

	@Override
	public void render(AmmunitionTableBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
	                   MultiBufferSource buffer, int packedLight, int packedOverlay) {

		NonNullList<ItemStack> inventory = blockEntity.getInventory();

		Direction direction = blockEntity.getBlockState().getValue(AmmunitionTableBlock.FACING);
		for (ItemStack stack : inventory) {
			if (stack != ItemStack.EMPTY) {
				poseStack.pushPose();
				// Translate to the center of the blockLocation
				poseStack.translate(0.5D, 0.0D, 0.5D);

				// Rotate by direction
				switch (direction) {
					case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(270f));
					case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180f));
					case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(90f));
					default -> poseStack.mulPose(Axis.YP.rotationDegrees(0f));
				}

				// Render the material inventory
				if (inventory.get(0) == stack) {
					// Top left
					poseStack.translate(-0.38D, 0.8D, 0.065D);
				} else if (inventory.get(1) == stack) {
					// Top right
					poseStack.translate(-0.295D, 0.8D, 0.065D);
				} else if (inventory.get(2) == stack) {
					// Center left
					poseStack.translate(-0.38D, 0.8D, 0.175D);
				} else if (inventory.get(3) == stack) {
					// Center right
					poseStack.translate(-0.295D, 0.8D, 0.175D);
				} else if (inventory.get(4) == stack) {
					// Bottom left
					poseStack.translate(-0.38D, 0.8D, 0.285D);
				} else if (inventory.get(5) == stack) {
					// Bottom right
					poseStack.translate(-0.295D, 0.8D, 0.285D);
				}

				if (inventory.get(6) != stack) {
					poseStack.scale(0.075f, 0.075f, 0.075f);
					poseStack.mulPose(Axis.XP.rotationDegrees(-25f));

					// Render the item
					Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, buffer, blockEntity.getLevel(), 0);
				} else {
					poseStack.translate(0.225D, 0.775D, -0.05D); // Baseline position
					poseStack.scale(0.25f, 0.25f, 0.25f);
					float prevX = 0.0f;
					float prevZ = 0.0f;
					for (int i = 0; i < stack.getCount(); i++) {
						// Render the items in an Archimede spiral
						float theta = i * 0.5f;
						float x = (float) (theta * 0.03f * Math.cos(theta));
						float z = (float) (theta * 0.03f * Math.sin(theta));

						poseStack.translate(x - prevX, 0.00001D, z - prevZ);

						prevX = x;
						prevZ = z;

						// Render the item
						Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, buffer, blockEntity.getLevel(), 0);
					}
				}

				poseStack.popPose();
			}
		}
	}
}