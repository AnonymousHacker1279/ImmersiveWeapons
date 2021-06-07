package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.model.BasicPlayerModel;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractMinutemanEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;

public class MinutemanRenderer extends BipedRenderer<AbstractMinutemanEntity, BasicPlayerModel<AbstractMinutemanEntity>> {

	private static final ResourceLocation MINUTEMAN_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/minuteman/minuteman.png");

	public MinutemanRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BasicPlayerModel<>(1.0f, true), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new BasicPlayerModel<>(0.5F, false), new BasicPlayerModel<>(1.0F, false)));
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractMinutemanEntity entity) {
		return MINUTEMAN_TEXTURE;
	}
}