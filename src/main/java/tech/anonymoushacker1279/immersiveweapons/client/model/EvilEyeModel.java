package tech.anonymoushacker1279.immersiveweapons.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class EvilEyeModel extends EntityModel<LivingEntityRenderState> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "evil_eye"),
			"main");

	public EvilEyeModel(ModelPart root) {
		super(root, RenderType::entityCutout);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition root = meshDefinition.getRoot();

		PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-2.0F, -4.0F, -4.0F, 4.0F, 4.0F, 5.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 6.0F, 0F, 0.0F, 0.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(2.0F, -2.0F, 3.0F));

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
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

		tail.addOrReplaceChild("tail_r4",
				CubeListBuilder.create().texOffs(8, 9)
						.addBox(-0.4375F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2182F, 0.0F));

		return LayerDefinition.create(meshDefinition, 32, 32);
	}
}