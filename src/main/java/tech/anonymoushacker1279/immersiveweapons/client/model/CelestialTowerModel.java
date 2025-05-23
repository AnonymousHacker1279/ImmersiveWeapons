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

public class CelestialTowerModel extends EntityModel<LivingEntityRenderState> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "celestial_tower"),
			"main");

	public CelestialTowerModel(ModelPart root) {
		super(root, RenderType::entityCutout);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition main = root.addOrReplaceChild("main",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F,
								new CubeDeformation(0.0F))
						.texOffs(0, 48)
						.addBox(-8.0F, -19.01F, -8.0F, 16.0F, 3.0F, 0.0F,
								new CubeDeformation(0.0F))
						.texOffs(32, 48)
						.addBox(-8.0F, -19.01F, 8.0F, 16.0F, 3.0F, 0.0F,
								new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		main.addOrReplaceChild("cube1", CubeListBuilder.create()
						.texOffs(16, 48)
						.addBox(-8.0F, -3.01F, -1.0F, 16.0F, 3.0F, 0.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.0F, -16.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		main.addOrReplaceChild("cube2", CubeListBuilder.create()
						.texOffs(16, 48)
						.addBox(-8.0F, -3.01F, -1.0F, 16.0F, 3.0F, 0.0F,
								new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.0F, -16.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(mesh, 64, 64);
	}
}