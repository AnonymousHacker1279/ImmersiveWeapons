package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.blockentity.CommanderPedestalBlockEntity;

public class CommanderPedestalRenderer implements BlockEntityRenderer<CommanderPedestalBlockEntity> {

	public CommanderPedestalRenderer() {
	}

	@Override
	public void render(CommanderPedestalBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		NonNullList<ItemStack> inventory = pBlockEntity.getInventory();

		if (inventory.isEmpty()) {
			return;
		}

		float time = pBlockEntity.getLevel().getGameTime() + pPartialTick;
		float speed = 0.05F; // Speed of rotation
		float radius = 0.25F; // Radius of the circle
		float height = 0.1F; // Height of the up and down movement

		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.get(i);
			if (stack != ItemStack.EMPTY) {
				pPoseStack.pushPose();

				// Calculate the position of the item
				double angle = 2.0 * Math.PI * i / inventory.size() + speed * time;
				double x = Math.sin(angle) * radius;
				double y = Math.sin(time * speed) * height;
				double z = Math.cos(angle) * radius;

				// Translate to the calculated position
				pPoseStack.translate(0.5 + x, 1.35 + y, 0.5 + z);

				// Rotate the item to face outwards
				pPoseStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(angle) - 90));

				// Scale the item
				pPoseStack.scale(0.25F, 0.25F, 0.25F);

				Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, pPackedLight,
						pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);

				pPoseStack.popPose();
			}
		}
	}
}