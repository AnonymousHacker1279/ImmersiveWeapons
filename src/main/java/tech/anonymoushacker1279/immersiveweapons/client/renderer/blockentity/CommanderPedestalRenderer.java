package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.blockentity.CommanderPedestalBlockEntity;

public class CommanderPedestalRenderer implements BlockEntityRenderer<CommanderPedestalBlockEntity> {

	@Override
	public void render(CommanderPedestalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
		NonNullList<ItemStack> inventory = blockEntity.getInventory();

		if (inventory.isEmpty()) {
			return;
		}

		float time = blockEntity.getLevel().getGameTime() + partialTick;
		float speed = 0.05F; // Speed of rotation
		float radius = 0.25F; // Radius of the circle
		float height = 0.1F; // Height of the up and down movement

		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.get(i);
			if (stack != ItemStack.EMPTY) {
				poseStack.pushPose();

				// Calculate the position of the item
				double angle = 2.0 * Math.PI * i / inventory.size() + speed * time;
				double x = Math.sin(angle) * radius;
				double y = Math.sin(time * speed) * height;
				double z = Math.cos(angle) * radius;

				// Translate to the calculated position
				poseStack.translate(0.5 + x, 1.35 + y, 0.5 + z);

				// Rotate the item to face outwards
				poseStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(angle) - 90));

				// Scale the item
				poseStack.scale(0.25F, 0.25F, 0.25F);

				Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight,
						packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);

				poseStack.popPose();
			}
		}
	}
}