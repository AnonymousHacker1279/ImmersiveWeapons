package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.HandSide;

import java.util.List;
import java.util.Random;

public class BasicPlayerModel<T extends LivingEntity> extends BipedModel<T> {

	public final ModelRenderer bipedLeftArmwear;
	public final ModelRenderer bipedRightArmwear;
	public final ModelRenderer bipedLeftLegwear;
	public final ModelRenderer bipedRightLegwear;
	public final ModelRenderer bipedBodyWear;
	private final ModelRenderer bipedCape;
	private final boolean smallArms;
	private List<ModelRenderer> modelRenderers = Lists.newArrayList();

	public BasicPlayerModel(float modelSize, boolean smallArmsIn) {
		super(RenderType::entityTranslucent, modelSize, 0.0F, 64, 64);
		this.smallArms = smallArmsIn;
		this.bipedCape = new ModelRenderer(this, 0, 0);
		this.bipedCape.setTexSize(64, 32);
		this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, modelSize);
		if (smallArmsIn) {
			this.leftArm = new ModelRenderer(this, 32, 48);
			this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize);
			this.leftArm.setPos(5.0F, 2.5F, 0.0F);
			this.rightArm = new ModelRenderer(this, 40, 16);
			this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize);
			this.rightArm.setPos(-5.0F, 2.5F, 0.0F);
			this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize + 0.25F);
			this.bipedLeftArmwear.setPos(5.0F, 2.5F, 0.0F);
			this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
			this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize + 0.25F);
			this.bipedRightArmwear.setPos(-5.0F, 2.5F, 10.0F);
		} else {
			this.leftArm = new ModelRenderer(this, 32, 48);
			this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
			this.leftArm.setPos(5.0F, 2.0F, 0.0F);
			this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
			this.bipedLeftArmwear.setPos(5.0F, 2.0F, 0.0F);
			this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
			this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
			this.bipedRightArmwear.setPos(-5.0F, 2.0F, 10.0F);
		}

		this.leftLeg = new ModelRenderer(this, 16, 48);
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
		this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
		this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
		this.bipedLeftLegwear.setPos(1.9F, 12.0F, 0.0F);
		this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
		this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
		this.bipedRightLegwear.setPos(-1.9F, 12.0F, 0.0F);
		this.bipedBodyWear = new ModelRenderer(this, 16, 32);
		this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize + 0.25F);
		this.bipedBodyWear.setPos(0.0F, 0.0F, 0.0F);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return Iterables.concat(super.bodyParts(), ImmutableList.of(this.bipedLeftLegwear, this.bipedRightLegwear, this.bipedLeftArmwear, this.bipedRightArmwear, this.bipedBodyWear));
	}

	public void renderCape(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn) {
		this.bipedCape.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.bipedLeftLegwear.copyFrom(this.leftLeg);
		this.bipedRightLegwear.copyFrom(this.rightLeg);
		this.bipedLeftArmwear.copyFrom(this.leftArm);
		this.bipedRightArmwear.copyFrom(this.rightArm);
		this.bipedBodyWear.copyFrom(this.body);
		if (entityIn.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
			if (entityIn.isCrouching()) {
				this.bipedCape.z = 1.4F;
				this.bipedCape.y = 1.85F;
			} else {
				this.bipedCape.z = 0.0F;
				this.bipedCape.y = 0.0F;
			}
		} else if (entityIn.isCrouching()) {
			this.bipedCape.z = 0.3F;
			this.bipedCape.y = 0.8F;
		} else {
			this.bipedCape.z = -1.1F;
			this.bipedCape.y = -0.85F;
		}

	}

	@Override
	public void setAllVisible(boolean visible) {
		super.setAllVisible(visible);
		this.bipedLeftArmwear.visible = visible;
		this.bipedRightArmwear.visible = visible;
		this.bipedLeftLegwear.visible = visible;
		this.bipedRightLegwear.visible = visible;
		this.bipedBodyWear.visible = visible;
		this.bipedCape.visible = visible;
	}

	@Override
	public void translateToHand(HandSide sideIn, MatrixStack matrixStackIn) {
		ModelRenderer modelrenderer = this.getArm(sideIn);
		if (this.smallArms) {
			float f = 0.5F * (float) (sideIn == HandSide.RIGHT ? 1 : -1);
			modelrenderer.x += f;
			modelrenderer.translateAndRotate(matrixStackIn);
			modelrenderer.x -= f;
		} else {
			modelrenderer.translateAndRotate(matrixStackIn);
		}

	}

	public ModelRenderer getRandomModelRenderer(Random randomIn) {
		return this.modelRenderers.get(randomIn.nextInt(this.modelRenderers.size()));
	}

	@Override
	public void accept(ModelRenderer p_accept_1_) {
		if (this.modelRenderers == null) {
			this.modelRenderers = Lists.newArrayList();
		}

		this.modelRenderers.add(p_accept_1_);
	}
}