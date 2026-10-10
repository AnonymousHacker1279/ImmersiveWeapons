package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.GunAimTracker;
import tech.anonymoushacker1279.immersiveweapons.item.gun.MusketItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.SimpleShotgunItem;

public class CustomArmPoses {

	public static void bootstrap() {
	}

	/// Pitch of an arm held straight out in front, level with the ground.
	private static final float ARM_LEVEL = -1.5707964f;

	/// A slight inward turn so the aimed arm converges on the same point the bullet goes to, rather than being parallel
	/// to it from the shoulder.
	private static final float AIM_CONVERGENCE = 0.06f;

	public static final EnumProxy<ArmPose> AIM_PISTOL_POSE_PARAMS = new EnumProxy<>(ArmPose.class, false, true, (IArmPoseTransformer) (model, state, arm) -> {
		// Point the arm exactly where the head is looking. The head's rotation is relative to the body, as the arm's is,
		// so adding it directly keeps the gun on the line the bullet travels along (even when the body lags the head).
		if (arm == HumanoidArm.RIGHT) {
			model.rightArm.xRot = ARM_LEVEL + model.head.xRot;
			model.rightArm.yRot = -AIM_CONVERGENCE + model.head.yRot;
		} else {
			model.leftArm.xRot = ARM_LEVEL + model.head.xRot;
			model.leftArm.yRot = AIM_CONVERGENCE + model.head.yRot;
		}
	});

	public static final EnumProxy<ArmPose> AIM_MUSKET_POSE_PARAMS = new EnumProxy<>(ArmPose.class, true, true, (IArmPoseTransformer) (model, state, arm) -> {
		// The firing arm points along the line of sight, and the other supports the gun further along it, so it needs to
		// be turned across the body. Both follow the head fully.
		if (arm == HumanoidArm.RIGHT) {
			model.rightArm.xRot = ARM_LEVEL + model.head.xRot;
			model.rightArm.yRot = -AIM_CONVERGENCE + model.head.yRot;
			model.leftArm.xRot = ARM_LEVEL + model.head.xRot;
			model.leftArm.yRot = 1.0f + model.head.yRot;
		} else {
			model.leftArm.xRot = ARM_LEVEL + model.head.xRot;
			model.leftArm.yRot = AIM_CONVERGENCE + model.head.yRot;
			model.rightArm.xRot = ARM_LEVEL + model.head.xRot;
			model.rightArm.yRot = -1.0f + model.head.yRot;
		}
	});

	/// A loaded one-handed gun that is not being aimed: held out in front, lowered from the aimed pose.
	public static final EnumProxy<ArmPose> READY_PISTOL_POSE_PARAMS = new EnumProxy<>(ArmPose.class, false, true, (IArmPoseTransformer) (model, state, arm) -> {
		if (arm == HumanoidArm.RIGHT) {
			model.rightArm.xRot = -0.85f + model.head.xRot * 0.25f;
			model.rightArm.yRot = -0.15f + model.head.yRot * 0.25f;
		} else {
			model.leftArm.xRot = -0.85f + model.head.xRot * 0.25f;
			model.leftArm.yRot = 0.15f + model.head.yRot * 0.25f;
		}
	});

	/// A loaded two-handed gun that is not being aimed: held across the body at a low ready.
	public static final EnumProxy<ArmPose> READY_MUSKET_POSE_PARAMS = new EnumProxy<>(ArmPose.class, true, true, (IArmPoseTransformer) (model, state, arm) -> {
		if (arm == HumanoidArm.RIGHT) {
			model.rightArm.xRot = -0.9f + model.head.xRot * 0.25f;
			model.rightArm.yRot = -0.2f + model.head.yRot * 0.25f;
			model.leftArm.xRot = -1.0f + model.head.xRot * 0.15f;
			model.leftArm.yRot = 0.9f + model.head.yRot * 0.15f;
		} else {
			model.leftArm.xRot = -0.9f + model.head.xRot * 0.25f;
			model.leftArm.yRot = 0.2f + model.head.yRot * 0.25f;
			model.rightArm.xRot = -1.0f + model.head.xRot * 0.15f;
			model.rightArm.yRot = -0.9f + model.head.yRot * 0.15f;
		}
	});

	public static final EnumProxy<ArmPose> HOLD_PIKE_POSE_PARAMS = new EnumProxy<>(ArmPose.class, true, false, (IArmPoseTransformer) (model, state, arm) -> {
		// Hold the pike with both hands, like a spear
		if (state.swingAnimation == 0) {
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
			float armRotation = Mth.lerp(state.swingAnimation * 2, 1.0F, 0.0F);

			// Disable the arm swinging animation
			state.swingAnimation = 0.0F;

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
		if (!itemStack.isEmpty() && itemStack.getItem() instanceof AbstractGunItem) {
			boolean isReloading = entity.getUsedItemHand() == hand && entity.getUseItemRemainingTicks() > 0;
			boolean isLoaded = itemStack.has(DataComponentTypeRegistry.LOADED_AMMUNITION.get());

			if (isReloading || isLoaded) {
				// The local player's own state is known immediately, everyone else's comes from the synced attachment
				boolean isAiming = entity instanceof Player player
						&& (player instanceof LocalPlayer ? GunAimClient.isAiming() : GunAimTracker.isAiming(player));

				// Raised while aiming or reloading, lowered while loaded and just carried
				boolean raised = isReloading || isAiming;
				boolean twoHanded = itemStack.getItem() instanceof MusketItem || itemStack.getItem() instanceof SimpleShotgunItem;

				if (twoHanded) {
					return (raised ? CustomArmPoses.AIM_MUSKET_POSE_PARAMS : CustomArmPoses.READY_MUSKET_POSE_PARAMS).getValue();
				}

				return (raised ? CustomArmPoses.AIM_PISTOL_POSE_PARAMS : CustomArmPoses.READY_PISTOL_POSE_PARAMS).getValue();
			}
		}

		return ArmPose.EMPTY;
	}
}
