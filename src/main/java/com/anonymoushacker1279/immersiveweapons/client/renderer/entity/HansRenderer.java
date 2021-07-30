package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.monster.HansEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class HansRenderer extends HumanoidMobRenderer<HansEntity, PlayerModel<HansEntity>> {

	private static final ResourceLocation HANS_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/hans/hans.png");

	/**
	 * Constructor for HansRenderer.
	 * @param context an <code>EntityRendererManager</code> instance
	 */
	public HansRenderer(Context context) {
		super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
	}

	/**
	 * Get the texture location.
	 * @param entity the <code>HansEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getTextureLocation(HansEntity entity) {
		return HANS_TEXTURE;
	}
}