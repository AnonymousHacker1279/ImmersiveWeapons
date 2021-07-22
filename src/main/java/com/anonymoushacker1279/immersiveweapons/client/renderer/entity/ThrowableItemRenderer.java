package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ThrowableItemRenderer {

	public static class SmokeBombRenderer implements IRenderFactory<SmokeBombEntity> {

		/**
		 * Create a renderer.
		 * @param manager an <code>EntityRendererManager</code> instance
		 * @return EntityRenderer extending SmokeBombEntity
		 */
		@Override
		public EntityRenderer<? super SmokeBombEntity> createRenderFor(EntityRendererManager manager) {
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
			return new SpriteRenderer<>(manager, itemRenderer);
		}
	}

	public static class MolotovRenderer implements IRenderFactory<MolotovEntity> {

		/**
		 * Create a renderer.
		 * @param manager an <code>EntityRendererManager</code> instance
		 * @return EntityRenderer extending MolotovEntity
		 */
		@Override
		public EntityRenderer<? super MolotovEntity> createRenderFor(EntityRendererManager manager) {
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
			return new SpriteRenderer<>(manager, itemRenderer);
		}
	}
}