package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.CopperArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class CustomArrowRenderer {

	public static class CopperArrowRenderer extends ArrowRenderer<CopperArrowEntity> {
		public CopperArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}
		
		@Override
		public ResourceLocation getEntityTexture(CopperArrowEntity entity) {
			Item refItem = entity.getArrowStack().getItem();
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/" + refItem.getRegistryName().getPath() + ".png");
		}
	}
	
}
