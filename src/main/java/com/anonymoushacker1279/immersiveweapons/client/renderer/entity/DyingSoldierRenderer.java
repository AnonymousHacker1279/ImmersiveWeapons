package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.model.BasicPlayerModel;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDyingSoldierEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;

public class DyingSoldierRenderer extends BipedRenderer<AbstractDyingSoldierEntity, BasicPlayerModel<AbstractDyingSoldierEntity>> {
	private static final ResourceLocation DYING_SOLDIER_RENDERER = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/dying_soldier/dying_soldier.png");

	public DyingSoldierRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BasicPlayerModel<>(1.0f, false), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new BasicPlayerModel<>(0.5F, false), new BasicPlayerModel<>(1.0F, false)));
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractDyingSoldierEntity entity) {
		return DYING_SOLDIER_RENDERER;
	}
}