package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDyingSoldierEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class DyingSoldierRenderer extends BipedRenderer<AbstractDyingSoldierEntity, PlayerModel<AbstractDyingSoldierEntity>> {
	private static final ResourceLocation DYING_SOLDIER_RENDERER = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/dying_soldier/dying_soldier.png");

	/**
	 * Constructor for DyingSoldierRenderer.
	 * @param renderManagerIn an <code>EntityRendererManager</code> instance
	 */
	public DyingSoldierRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PlayerModel<>(0.0f, false), 0.5F);
		addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.25F), new BipedModel<>(0.75F)));
		addLayer(new ArrowLayer<>(this));
		addLayer(new HeldItemLayer<>(this));
	}

	/**
	 * Get the texture location.
	 * @param entity the <code>AbstractDyingSoldierEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getTextureLocation(AbstractDyingSoldierEntity entity) {
		return DYING_SOLDIER_RENDERER;
	}
}