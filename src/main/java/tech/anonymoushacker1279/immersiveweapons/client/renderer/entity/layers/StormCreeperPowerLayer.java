package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers;

import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StormCreeperEntity;

public class StormCreeperPowerLayer extends EnergySwirlLayer<StormCreeperEntity, CreeperModel<StormCreeperEntity>> {

	private static final ResourceLocation POWER_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper_armor.png");
	private final CreeperModel<StormCreeperEntity> model;

	public StormCreeperPowerLayer(RenderLayerParent<StormCreeperEntity, CreeperModel<StormCreeperEntity>> pRenderer, EntityModelSet modelSet) {
		super(pRenderer);
		model = new CreeperModel<>(modelSet.bakeLayer(ModelLayers.CREEPER_ARMOR));
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
	protected EntityModel<StormCreeperEntity> model() {
		return model;
	}
}