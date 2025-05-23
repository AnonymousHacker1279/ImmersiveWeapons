package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.MusketItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.SimpleShotgunItem;

public class CustomArmPoses {

	public static void bootstrap() {
	}

	public static final EnumProxy<ArmPose> AIM_PISTOL_POSE_PARAMS = new EnumProxy<>(ArmPose.class, false, (IArmPoseTransformer) (model, state, arm) -> {
		// Hold the gun up as if it's being aimed
		if (arm == HumanoidArm.RIGHT) {
			model.rightArm.xRot = -1.5f;
			model.rightArm.yRot = -0.25f;

			// Adjust to move with the head (looking up/down moves the gun)
			model.rightArm.xRot += model.head.xRot * 0.5f;
			model.rightArm.yRot += model.head.yRot * 0.5f;
		} else {
			model.leftArm.xRot = -1.5f;
			model.leftArm.yRot = 0.25f;

			// Adjust to move with the head
			model.leftArm.xRot += model.head.xRot * 0.5f;
			model.leftArm.yRot += model.head.yRot * 0.5f;
		}
	});

	public static final EnumProxy<ArmPose> AIM_MUSKET_POSE_PARAMS = new EnumProxy<>(ArmPose.class, true, (IArmPoseTransformer) (model, state, arm) -> {
		// Hold the gun up as if it's being aimed. This one uses two hands, one needs to be supporting the gun at the end and the other midway
		if (arm == HumanoidArm.RIGHT) {
			model.rightArm.xRot = -1.5f;
			model.rightArm.yRot = -0.25f;
			// The left arm needs to be moved over more to support the gun
			model.leftArm.xRot = -1.5f;
			model.leftArm.yRot = 1.0f;

			// Adjust to move with the head
			model.rightArm.xRot += model.head.xRot * 0.5f;
			model.rightArm.yRot += model.head.yRot * 0.5f;
			model.leftArm.xRot += model.head.xRot * 0.25f;
			model.leftArm.yRot += model.head.yRot * 0.25f;
		} else {
			model.leftArm.xRot = -1.5f;
			model.leftArm.yRot = 0.25f;
			// The right arm needs to be moved over more to support the gun
			model.rightArm.xRot = -1.5f;
			model.rightArm.yRot = -1.0f;

			// Adjust to move with the head
			model.leftArm.xRot += model.head.xRot * 0.5f;
			model.leftArm.yRot += model.head.yRot * 0.5f;
			model.rightArm.xRot += model.head.xRot * 0.25f;
			model.rightArm.yRot += model.head.yRot * 0.25f;
		}
	});

	public static final EnumProxy<ArmPose> HOLD_PIKE_POSE_PARAMS = new EnumProxy<>(ArmPose.class, true, (IArmPoseTransformer) (model, state, arm) -> {
		// Hold the pike with both hands, like a spear
		if (state.attackTime == 0) {
			if (arm == HumanoidArm.RIGHT) {
				model.rightArm.xRot = -0.35F;
				model.rightArm.yRot = -0.4F;

				model.leftArm.xRot = -0.85F;
				model.leftArm.yRot = 0.15F;
				model.leftArm.zRot = 0.75F;
				// Make the left arm slightly longer
				model.leftArm.yScale = 1.15F;
			} else {
				model.leftArm.xRot = -0.35F;
				model.leftArm.yRot = 0.4F;

				model.rightArm.xRot = -0.85F;
				model.rightArm.yRot = -0.15F;
				model.rightArm.zRot = -0.75F;
				// Make the right arm slightly longer
				model.rightArm.yScale = 1.15F;
			}
		} else {
			// As this is a spear-like item, it needs to stab in a thrusting motion
			float armRotation = Mth.lerp(state.attackTime * 2, 1.0F, 0.0F);

			// Disable the arm swinging animation
			state.attackTime = 0.0F;

			// Animate the arms moving forward in a thrust motion
			if (arm == HumanoidArm.RIGHT) {
				// Interpolate from the idle position to the thrust position
				model.rightArm.xRot = -0.15F + armRotation * 0.75F;
				model.rightArm.yRot = -0.4F + armRotation * 0.175F;
			} else {
				model.leftArm.xRot = -0.15F + armRotation * 0.75F;
				model.leftArm.yRot = 0.4F - armRotation * 0.175F;
			}
		}
	});

	public static ArmPose getFirearmPose(LivingEntity entity, InteractionHand hand, ItemStack itemStack) {
		if (!itemStack.isEmpty()) {
			if (entity.getUsedItemHand() == hand && entity.getUseItemRemainingTicks() > 0) {
				Item item = itemStack.getItem();
				if (item instanceof MusketItem || item instanceof SimpleShotgunItem) {
					return CustomArmPoses.AIM_MUSKET_POSE_PARAMS.getValue();
				} else if (item instanceof AbstractGunItem) {
					return CustomArmPoses.AIM_PISTOL_POSE_PARAMS.getValue();
				}
			}
		}

		return ArmPose.EMPTY;
	}
}