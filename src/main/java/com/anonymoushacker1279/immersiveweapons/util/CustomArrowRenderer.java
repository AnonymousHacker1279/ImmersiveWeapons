package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class CustomArrowRenderer extends ArrowRenderer<CustomArrowEntity>{

	public CustomArrowRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getEntityTexture(CustomArrowEntity entity) {
		Item refItem = entity.getArrowStack().getItem();
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/" + refItem.getRegistryName().getPath() + ".png");
	}
}
