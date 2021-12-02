package com.anonymoushacker1279.immersiveweapons.client.model;

import com.anonymoushacker1279.immersiveweapons.entity.monster.LavaRevenantEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class LavaRevenantModel<T extends LavaRevenantEntity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart leftWingBase;
	private final ModelPart leftWingTip;
	private final ModelPart rightWingBase;
	private final ModelPart rightWingTip;
	private final ModelPart tailBase;
	private final ModelPart tailTip;

	public LavaRevenantModel(ModelPart modelPart) {
		root = modelPart;
		ModelPart modelpart = modelPart.getChild("body");
		tailBase = modelpart.getChild("tail_base");
		tailTip = tailBase.getChild("tail_tip");
		leftWingBase = modelpart.getChild("left_wing_base");
		leftWingTip = leftWingBase.getChild("left_wing_tip");
		rightWingBase = modelpart.getChild("right_wing_base");
		rightWingTip = rightWingBase.getChild("right_wing_tip");
	}

	@Override
	public @NotNull ModelPart root() {
		return root;
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		float f = ((float) pEntity.getUniqueFlapTickOffset() + pAgeInTicks) * 7.448451F * ((float) Math.PI / 180F);
		leftWingBase.zRot = Mth.cos(f) * 16.0F * ((float) Math.PI / 180F);
		leftWingTip.zRot = Mth.cos(f) * 16.0F * ((float) Math.PI / 180F);
		rightWingBase.zRot = -leftWingBase.zRot;
		rightWingTip.zRot = -leftWingTip.zRot;
		tailBase.xRot = -(5.0F + Mth.cos(f * 2.0F) * 5.0F) * ((float) Math.PI / 180F);
		tailTip.xRot = -(5.0F + Mth.cos(f * 2.0F) * 5.0F) * ((float) Math.PI / 180F);
	}
}