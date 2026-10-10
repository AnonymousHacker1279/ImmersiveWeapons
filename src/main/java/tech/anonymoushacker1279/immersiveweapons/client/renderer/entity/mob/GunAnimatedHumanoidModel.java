package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.zigythebird.playeranim.util.RenderUtil;
import com.zigythebird.playeranimcore.animation.HumanoidAnimationController;
import com.zigythebird.playeranimcore.bones.PlayerAnimBone;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

/// A humanoid model that applies a PlayerAnimator animation on top of the vanilla pose, the same way PlayerAnimator
/// does for the player model. Used for the mob's body and its armor layers, so both move together.
public class GunAnimatedHumanoidModel extends HumanoidModel<GunAnimatedRenderState> {

	public GunAnimatedHumanoidModel(ModelPart root, Function<Identifier, RenderType> renderType) {
		super(root, renderType);
	}

	@Override
	public void setupAnim(GunAnimatedRenderState state) {
		super.setupAnim(state);

		HumanoidAnimationController animation = state.gunAnimation;
		if (animation != null) {
			apply(animation, head, "head");
			apply(animation, body, "torso");
			apply(animation, rightArm, "right_arm");
			apply(animation, leftArm, "left_arm");
			apply(animation, rightLeg, "right_leg");
			apply(animation, leftLeg, "left_leg");
		}
	}

	/// Start from the pose the model already has, let the animation modify it, and write it back.
	private static void apply(HumanoidAnimationController animation, ModelPart part, String boneName) {
		PlayerAnimBone bone = new PlayerAnimBone(boneName);
		RenderUtil.copyVanillaPart(part, bone);
		animation.get3DTransform(bone);
		RenderUtil.translatePartToBone(part, bone, part.getInitialPose());
	}
}
