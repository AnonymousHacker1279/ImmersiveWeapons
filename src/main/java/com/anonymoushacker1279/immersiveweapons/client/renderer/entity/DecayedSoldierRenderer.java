package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.model.DecayedSoldierModel;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDecayedSoldierEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;

public class DecayedSoldierRenderer extends BipedRenderer<AbstractDecayedSoldierEntity, DecayedSoldierModel<AbstractDecayedSoldierEntity>> {
	private static final ResourceLocation DECAYED_SOLDIER_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/decayed_soldier/decayed_soldier.png");

	public DecayedSoldierRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new DecayedSoldierModel<>(1.0f, false), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new DecayedSoldierModel<>(0.5F, false), new DecayedSoldierModel<>(1.0F, false)));
	}

	@Override
	public ResourceLocation getEntityTexture(AbstractDecayedSoldierEntity entity) {
		return DECAYED_SOLDIER_TEXTURE;
	}
}