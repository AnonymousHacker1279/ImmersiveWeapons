package tech.anonymoushacker1279.immersiveweapons.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class FireflyModel extends EntityModel<LivingEntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "firefly"),
			"main");

	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;

	public FireflyModel(ModelPart root) {
		super(root, RenderType::entityCutout);
		ModelPart main = root.getChild("main");
		ModelPart wings = main.getChild("wings");
		body = main.getChild("body");

		rightWing = wings.getChild("right_wing");
		leftWing = wings.getChild("left_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition part = mesh.getRoot();

		PartDefinition main = part.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition wings = main.addOrReplaceChild("wings", CubeListBuilder.create(),
				PartPose.offset(1.5F, 22.0F, 0.0F));

		wings.addOrReplaceChild("right_wing", CubeListBuilder.create()
						.texOffs(0, 7)
						.addBox(-1.0F, 0.5F, -1.0F, 3.0F, 0.0F, 2.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

		wings.addOrReplaceChild("left_wing", CubeListBuilder.create()
						.texOffs(6, 7)
						.addBox(-1.5F, 0.75F, -1.0F, 3.0F, 0.0F, 2.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

		main.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -3.0F, 2.0F, 1.0F, 6.0F,
								new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(mesh, 16, 16);
	}

	@Override
	public void setupAnim(LivingEntityRenderState renderState) {
		super.setupAnim(renderState);

		body.xRot = 0.0F;
		body.z = 0.0F;
		rightWing.z = 0.0F;
		leftWing.z = rightWing.z;

		rightWing.xRot = Mth.cos(renderState.ageInTicks * 74.48451F * ((float) Math.PI / 180F)) * (float) Math.PI * 0.25F;
		leftWing.xRot = -rightWing.xRot;
	}
}