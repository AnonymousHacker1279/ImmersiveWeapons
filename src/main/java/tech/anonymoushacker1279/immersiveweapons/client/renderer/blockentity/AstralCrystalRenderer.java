package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AstralCrystalBlockEntity;

public class AstralCrystalRenderer implements BlockEntityRenderer<AstralCrystalBlockEntity> {

	public AstralCrystalRenderer() {
	}

	@Override
	public void render(AstralCrystalBlockEntity entity, float partialTick, @NotNull PoseStack poseStack,
	                   @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
		NonNullList<ItemStack> inventory = entity.getInventory();

		for (ItemStack itemStack : inventory) {
			if (itemStack != ItemStack.EMPTY) {
				poseStack.pushPose();

				// Rotation occurs here
				poseStack.mulPose(Vector3f.XP.rotationDegrees(90f));
				poseStack.translate(0.5D, 0.5D, -0.25D);
				if (inventory.get(0) == itemStack) {
					// First item goes on top
					poseStack.translate(0D, 1D, 0D);
				} else if (inventory.get(1) == itemStack) {
					// Second item goes on right
					poseStack.translate(-1D, 0D, 0D);
					poseStack.mulPose(Vector3f.ZP.rotationDegrees(90f));
				} else if (inventory.get(2) == itemStack) {
					// Third item goes on bottom
					poseStack.translate(0D, -1D, 0D);
					poseStack.mulPose(Vector3f.ZP.rotationDegrees(180f));
				} else if (inventory.get(3) == itemStack) {
					// Fourth item goes on left
					poseStack.translate(1D, 0D, 0D);
					poseStack.mulPose(Vector3f.ZN.rotationDegrees(90f));
				}

				// Scale render
				poseStack.scale(0.75F, 0.75F, 0.75F);

				// Rotate all items 45 degrees diagonally
				poseStack.mulPose(Vector3f.XN.rotationDegrees(45f));

				// All the items should smoothly rotate around the center of the block
				poseStack.mulPose(Vector3f.ZP.rotationDegrees((Minecraft.getInstance().level.getGameTime() + partialTick) * 2f));

				// Actually render the item
				Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED, packedLight, packedOverlay, poseStack, buffer, 0);
				poseStack.popPose();
			}
		}
	}
}