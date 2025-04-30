package tech.anonymoushacker1279.immersiveweapons.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class CannonballModel extends EntityModel<ArrowRenderState> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "cannonball"),
			"main");

	public CannonballModel(ModelPart root) {
		super(root, RenderType::entityCutout);
	}

	public static LayerDefinition createBodyLayer() {
		return MeteorModel.createBodyLayer();   // Both are a 4x4 cube
	}
}