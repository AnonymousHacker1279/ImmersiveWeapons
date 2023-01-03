package tech.anonymoushacker1279.immersiveweapons.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class CelestialTowerModel<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(ImmersiveWeapons.MOD_ID, "celestial_tower"),
			"main");
	private final ModelPart base;

	public CelestialTowerModel(ModelPart root) {
		base = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		partDefinition.addOrReplaceChild("main",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-8.0F, -16.0F, -8.0F,
								16.0F, 16.0F, 16.0F,
								new CubeDeformation(0.0F))
						.texOffs(0, 32)
						.addBox(-8.0F, -19.0F, -8.0F,
								16.0F, 3.0F, 16.0F,
								new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
	                      float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
	                           int packedOverlay, float red, float green, float blue, float alpha) {

		base.render(poseStack, buffer, packedLight, packedOverlay);
	}
}