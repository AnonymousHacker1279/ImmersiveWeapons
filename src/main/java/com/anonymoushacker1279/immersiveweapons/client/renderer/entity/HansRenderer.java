package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.monster.HansEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class HansRenderer extends MobRenderer<HansEntity, PlayerModel<HansEntity>> {

	private static final ResourceLocation HANS_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/hans/hans.png");

	/**
	 * Constructor for HansRenderer.
	 * @param renderManagerIn an <code>EntityRendererManager</code> instance
	 */
	public HansRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PlayerModel<>(0.0F, false), 0.5F);
		addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.25F), new BipedModel<>(0.75F)));
		addLayer(new ArrowLayer<>(this));
		addLayer(new HeldItemLayer<>(this));
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