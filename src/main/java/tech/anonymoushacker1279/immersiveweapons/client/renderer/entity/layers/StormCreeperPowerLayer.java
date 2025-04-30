package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers;

import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.ResourceLocation;

public class StormCreeperPowerLayer extends EnergySwirlLayer<CreeperRenderState, CreeperModel> {

	private static final ResourceLocation POWER_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper_armor.png");
	private final CreeperModel model;

	public StormCreeperPowerLayer(RenderLayerParent<CreeperRenderState, CreeperModel> pRenderer, EntityModelSet modelSet) {
		super(pRenderer);
		model = new CreeperModel(modelSet.bakeLayer(ModelLayers.CREEPER_ARMOR));
	}

	@Override
	protected boolean isPowered(CreeperRenderState renderState) {
		return false;
	}

	@Override
	protected float xOffset(float v) {
		return v * 0.01F;
	}

	@Override
	protected ResourceLocation getTextureLocation() {
		return POWER_LOCATION;
	}

	@Override
	protected CreeperModel model() {
		return model;
	}
}