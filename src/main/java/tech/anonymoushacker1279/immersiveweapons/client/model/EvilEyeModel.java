package tech.anonymoushacker1279.immersiveweapons.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class EvilEyeModel<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(ImmersiveWeapons.MOD_ID, "evil_eye"),
			"main");
	private final ModelPart main;

	public EvilEyeModel(ModelPart root) {
		main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition root = meshDefinition.getRoot();

		PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-2.0F, -4.0F, -4.0F, 4.0F, 4.0F, 5.0F,
								new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create(),
				PartPose.offset(2.0F, -2.0F, 3.0F));

		tail.addOrReplaceChild("tail_r1", CubeListBuilder.create()
						.texOffs(0, 5)
						.addBox(-0.4375F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, -2.0F, 0.0F, 0.0F, -0.2182F, -1.5708F));

		tail.addOrReplaceChild("tail_r2", CubeListBuilder.create()
						.texOffs(8, 5)
						.addBox(-1.5F, -2.0F, -2.375F, 0.0F, 4.0F, 4.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.2182F, -1.5708F));

		tail.addOrReplaceChild("tail_r3",
				CubeListBuilder.create().texOffs(0, 9)
						.addBox(0.4375F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F,
								new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

		tail.addOrReplaceChild("tail_r4",
				CubeListBuilder.create().texOffs(8, 9)
						.addBox(-0.4375F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

		return LayerDefinition.create(meshDefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}