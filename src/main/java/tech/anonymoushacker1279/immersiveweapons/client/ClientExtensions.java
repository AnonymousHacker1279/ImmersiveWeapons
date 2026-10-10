package tech.anonymoushacker1279.immersiveweapons.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;

public class ClientExtensions {

	public static final IClientItemExtensions FIREARM = new IClientItemExtensions() {

		@Override
		public ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack itemStack) {
			return CustomArmPoses.getFirearmPose(entity, hand, itemStack);
		}

		@Override
		public boolean applyForgeHandTransform(PoseStack poseStack, PlayerRenderState playerRenderState, HumanoidArm arm,
		                                       ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

			float aimProgress = GunAimClient.getProgress(partialTick);
			boolean reloading = playerRenderState.avatarRenderState.ticksUsingItem(arm) > 0;
			boolean loaded = itemInHand.has(DataComponentTypeRegistry.LOADED_AMMUNITION.get());

			// Only raise the gun while it's being aimed, reloaded, or is loaded and ready. Otherwise, use vanilla.
			if (aimProgress <= 0.0f && !reloading && !loaded) {
				return false;
			}

			applyItemArmTransform(poseStack, arm, aimProgress, GunAimOffsets.getPose(itemInHand.getItem(), arm));
			return true;
		}

		/// Position the gun in the first-person view, easing from the hip position to the sighted one while aiming.
		private void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm, float aimProgress, GunAimOffsets.AimPose aimed) {
			int i = arm == HumanoidArm.RIGHT ? 1 : -1;

			float hipX = i * 0.56F;
			float hipY = -0.52F;
			float hipZ = -0.72F;

			if (aimed == null || aimProgress <= 0.0f) {
				poseStack.translate(hipX, hipY, hipZ);
				return;
			}

			poseStack.translate(
					Mth.lerp(aimProgress, hipX, aimed.translation().x()),
					Mth.lerp(aimProgress, hipY, aimed.translation().y()),
					Mth.lerp(aimProgress, hipZ, aimed.translation().z())
			);
			poseStack.mulPose(new Matrix4f().rotate(new Quaternionf().slerp(aimed.rotation(), aimProgress)));
			poseStack.scale(
					Mth.lerp(aimProgress, 1.0F, aimed.scale().x()),
					Mth.lerp(aimProgress, 1.0F, aimed.scale().y()),
					Mth.lerp(aimProgress, 1.0F, aimed.scale().z())
			);
		}
	};
}