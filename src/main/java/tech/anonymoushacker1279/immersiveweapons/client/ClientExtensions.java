package tech.anonymoushacker1279.immersiveweapons.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
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

	public static final IClientItemExtensions PIKE = new IClientItemExtensions() {

		@Override
		public ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack itemStack) {
			if (!itemStack.isEmpty()) {
				return CustomArmPoses.HOLD_PIKE_POSE_PARAMS.getValue();
			}
			return ArmPose.EMPTY;
		}

		@Override
		public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
		                                       ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

			// Don't use custom transform until it is fully equipped
			if (equipProcess < 1.0f) {
				return false;
			}

			applyItemArmTransform(poseStack, player, arm, partialTick, swingProcess);
			return true;
		}

		private void applyItemArmTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, float partialTick, float swingProcess) {
			int i = arm == HumanoidArm.RIGHT ? 1 : -1;
			poseStack.translate(i * 0.56F, -0.52F, -0.72F);

			// Thrust the pike forwards if the player is attacking
			if (swingProcess > 0.0F) {
				// Make sure to interpolate the swing process
				float swingProgress = player.getAttackAnim(partialTick);
				swingProgress = Mth.sin(swingProgress * (float) Math.PI);

				// Thrust the pike forwards
				poseStack.translate(i * 0.125F * swingProgress, 0.0F, -1.125F * swingProgress);
			}
		}
	};
}