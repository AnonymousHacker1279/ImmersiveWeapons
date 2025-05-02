package tech.anonymoushacker1279.immersiveweapons.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import tech.anonymoushacker1279.immersiveweapons.client.CustomArmPoses;

public class SoldierModel extends HumanoidModel<HumanoidRenderState> {

	public SoldierModel(ModelPart root) {
		super(root);
	}

	@Override
	protected ArmPose getArmPose(HumanoidRenderState renderState, HumanoidArm arm) {
		return CustomArmPoses.getFirearmPose(renderState);
	}
}