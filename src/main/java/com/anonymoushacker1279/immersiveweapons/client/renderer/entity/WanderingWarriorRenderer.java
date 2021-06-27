package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractWanderingWarriorEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class WanderingWarriorRenderer extends MobRenderer<AbstractWanderingWarriorEntity, PlayerModel<AbstractWanderingWarriorEntity>> {

	private static final ResourceLocation WANDERING_WARRIOR_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/wandering_warrior/wandering_warrior.png");

	public WanderingWarriorRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PlayerModel<>(0.0F, false), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.25F), new BipedModel(0.75F)));
		this.addLayer(new ArrowLayer<>(this));
		this.addLayer(new HeldItemLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractWanderingWarriorEntity entity) {
		return WANDERING_WARRIOR_TEXTURE;
	}
}