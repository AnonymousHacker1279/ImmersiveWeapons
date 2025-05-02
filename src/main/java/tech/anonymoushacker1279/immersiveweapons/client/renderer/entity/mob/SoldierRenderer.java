package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.client.model.SoldierModel;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.SoldierEntity;

public class SoldierRenderer extends HumanoidMobRenderer<SoldierEntity, HumanoidRenderState, SoldierModel> {

	private final ResourceLocation textureLocation;

	public SoldierRenderer(Context context, ResourceLocation textureLocation) {
		super(context, new SoldierModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this,
				new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
				context.getEquipmentRenderer()));

		this.textureLocation = textureLocation;
	}

	@Override
	public HumanoidRenderState createRenderState() {
		return new HumanoidRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(HumanoidRenderState entity) {
		return textureLocation;
	}
}