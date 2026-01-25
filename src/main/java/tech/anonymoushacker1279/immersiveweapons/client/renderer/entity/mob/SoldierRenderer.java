package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import tech.anonymoushacker1279.immersiveweapons.client.CustomArmPoses;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.SoldierEntity;

public class SoldierRenderer extends SimpleHumanoidRenderer<SoldierEntity> {

	public SoldierRenderer(Context context, Identifier textureLocation) {
		super(context, textureLocation);
	}

	@Override
	protected HumanoidModel.ArmPose getArmPose(SoldierEntity mob, HumanoidArm arm) {
		return CustomArmPoses.getFirearmPose(mob, mob.getUsedItemHand(), mob.getItemHeldByArm(arm));
	}
}