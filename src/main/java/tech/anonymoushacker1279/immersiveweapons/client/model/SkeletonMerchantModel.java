package tech.anonymoushacker1279.immersiveweapons.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkeletonMerchantEntity;

public class SkeletonMerchantModel<T extends SkeletonMerchantEntity> extends HumanoidModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(ImmersiveWeapons.MOD_ID, "skeleton_merchant"),
			"main");

	public SkeletonMerchantModel(ModelPart pRoot) {
		super(pRoot);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16)
						.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F),
				PartPose.offset(-5.0F, 2.0F, 0.0F));
		root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror()
						.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F),
				PartPose.offset(5.0F, 2.0F, 0.0F));
		root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16)
						.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F),
				PartPose.offset(-2.0F, 12.0F, 0.0F));
		root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror()
						.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F),
				PartPose.offset(2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(mesh, 64, 32);
	}

	public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
		rightArmPose = HumanoidModel.ArmPose.EMPTY;
		leftArmPose = HumanoidModel.ArmPose.EMPTY;
		ItemStack itemInHand = pEntity.getItemInHand(InteractionHand.MAIN_HAND);
		if (itemInHand.is(Items.BOW) && pEntity.isAggressive()) {
			if (pEntity.getMainArm() == HumanoidArm.RIGHT) {
				rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
			} else {
				leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
			}
		}

		super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
	}

	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
		ItemStack handItem = pEntity.getMainHandItem();
		
		if (pEntity.isAggressive() && handItem.isEmpty()) {
			float f = Mth.sin(this.attackTime * (float) Math.PI);
			float f1 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);

			rightArm.zRot = 0.0F;
			leftArm.zRot = 0.0F;

			rightArm.yRot = -(0.1F - f * 0.6F);
			leftArm.yRot = 0.1F - f * 0.6F;

			rightArm.xRot = (-(float) Math.PI / 2F);
			leftArm.xRot = (-(float) Math.PI / 2F);
			rightArm.xRot -= f * 1.2F - f1 * 0.4F;
			leftArm.xRot -= f * 1.2F - f1 * 0.4F;

			AnimationUtils.bobArms(rightArm, leftArm, pAgeInTicks);
		}
	}

	public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
		float sideModifier = pSide == HumanoidArm.RIGHT ? 1.0F : -1.0F;
		ModelPart arm = this.getArm(pSide);
		arm.x += sideModifier;
		arm.translateAndRotate(pPoseStack);
		arm.x -= sideModifier;
	}
}