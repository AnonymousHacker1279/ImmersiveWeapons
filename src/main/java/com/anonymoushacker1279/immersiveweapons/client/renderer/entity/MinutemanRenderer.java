package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractMinutemanEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class MinutemanRenderer extends BipedRenderer<AbstractMinutemanEntity, PlayerModel<AbstractMinutemanEntity>> {

	private static final ResourceLocation MINUTEMAN_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/minuteman/minuteman.png");

	public MinutemanRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PlayerModel<>(0.0f, true), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.25F), new BipedModel<>(0.75F)));
		this.addLayer(new ArrowLayer<>(this));
		this.addLayer(new HeldItemLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractMinutemanEntity entity) {
		return MINUTEMAN_TEXTURE;
	}
}