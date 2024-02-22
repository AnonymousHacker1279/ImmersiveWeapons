package tech.anonymoushacker1279.immersiveweapons.client.model;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.gun.*;

public class CustomArmPoses {

	public static void bootstrap() {}

	public static final ArmPose AIM_PISTOL_POSE = ArmPose.create("AIM_PISTOL", false, (model, entity, arm) -> {
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

	public static final ArmPose AIM_MUSKET_POSE = ArmPose.create("AIM_MUSKET", true, (model, entity, arm) -> {
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

	public static ArmPose getFirearmPose(LivingEntity entity, InteractionHand hand, ItemStack itemStack) {
		if (!itemStack.isEmpty()) {
			if (entity.getUsedItemHand() == hand && entity.getUseItemRemainingTicks() > 0) {
				Item item = itemStack.getItem();
				if (item instanceof MusketItem || item instanceof SimpleShotgunItem) {
					return CustomArmPoses.AIM_MUSKET_POSE;
				} else if (item instanceof AbstractGunItem) {
					return CustomArmPoses.AIM_PISTOL_POSE;
				}
			}
		}
		return ArmPose.EMPTY;
	}
}