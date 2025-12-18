package tech.anonymoushacker1279.immersiveweapons.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.LavaRevenantRenderState;

public class LavaRevenantModel extends EntityModel<LavaRevenantRenderState> {
	private final ModelPart leftWingBase;
	private final ModelPart leftWingTip;
	private final ModelPart rightWingBase;
	private final ModelPart rightWingTip;
	private final ModelPart tailBase;
	private final ModelPart tailTip;

	public LavaRevenantModel(ModelPart modelPart) {
		super(modelPart, RenderTypes::entityCutout);
		ModelPart body = modelPart.getChild("body");
		tailBase = body.getChild("tail_base");
		tailTip = tailBase.getChild("tail_tip");
		leftWingBase = body.getChild("left_wing_base");
		leftWingTip = leftWingBase.getChild("left_wing_tip");
		rightWingBase = body.getChild("right_wing_base");
		rightWingTip = rightWingBase.getChild("right_wing_tip");
	}

	@Override
	public void setupAnim(LavaRevenantRenderState renderState) {
		super.setupAnim(renderState);

		float f = renderState.ageInTicks * 7.448451F * ((float) Math.PI / 180F);
		leftWingBase.zRot = Mth.cos(f) * 16.0F * ((float) Math.PI / 180F);
		leftWingTip.zRot = Mth.cos(f) * 16.0F * ((float) Math.PI / 180F);
		rightWingBase.zRot = -leftWingBase.zRot;
		rightWingTip.zRot = -leftWingTip.zRot;
		tailBase.xRot = -(5.0F + Mth.cos(f * 2.0F) * 5.0F) * ((float) Math.PI / 180F);
		tailTip.xRot = -(5.0F + Mth.cos(f * 2.0F) * 5.0F) * ((float) Math.PI / 180F);
	}
}