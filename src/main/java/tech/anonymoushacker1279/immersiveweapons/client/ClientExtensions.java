package tech.anonymoushacker1279.immersiveweapons.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class ClientExtensions {

	public static final IClientItemExtensions FIREARM = new IClientItemExtensions() {

		@Override
		public ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack itemStack) {
			return CustomArmPoses.getFirearmPose(entity, hand, itemStack);
		}

		@Override
		public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
		                                       ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

			// Don't use custom transform until it is fully equipped
			if (equipProcess < 1.0f && !player.isUsingItem()) {
				return false;
			}

			applyItemArmTransform(poseStack, arm);
			return true;
		}

		private void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm) {
			int i = arm == HumanoidArm.RIGHT ? 1 : -1;
			poseStack.translate(i * 0.56F, -0.52F, -0.72F);
		}
	};
}