package tech.anonymoushacker1279.immersiveweapons.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;

public class MeteorModel<T extends MeteorEntity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "meteor"),
			"main");

	private final ModelPart main;

	public MeteorModel(ModelPart root) {
		main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition root = meshDefinition.getRoot();

		root.addOrReplaceChild("main", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(0F, 0F, 0F, 4.0F, 4.0F, 4.0F,
								new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshDefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		main.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}