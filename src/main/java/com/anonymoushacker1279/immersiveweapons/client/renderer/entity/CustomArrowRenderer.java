package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.*;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CustomArrowRenderer {

	public static class CopperArrowRenderer extends ArrowRenderer<CopperArrowEntity> {
		/**
		 * Constructor for CopperArrowRenderer.
		 * @param renderManagerIn an <code>EntityRendererManager</code> instance
		 */
		public CopperArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>CopperArrowEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(CopperArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/copper_arrow.png");
		}
	}

	public static class IronArrowRenderer extends ArrowRenderer<IronArrowEntity> {
		/**
		 * Constructor for IronArrowRenderer.
		 * @param renderManagerIn an <code>EntityRendererManager</code> instance
		 */
		public IronArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>IronArrowEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(IronArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/iron_arrow.png");
		}
	}

	public static class DiamondArrowRenderer extends ArrowRenderer<DiamondArrowEntity> {
		/**
		 * Constructor for DiamondArrowRenderer.
		 * @param renderManagerIn an <code>EntityRendererManager</code> instance
		 */
		public DiamondArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>DiamondArrowEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(DiamondArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/diamond_arrow.png");
		}
	}

	public static class GoldArrowRenderer extends ArrowRenderer<GoldArrowEntity> {
		/**
		 * Constructor for GoldArrowRenderer.
		 * @param renderManagerIn an <code>EntityRendererManager</code> instance
		 */
		public GoldArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>GoldArrowEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(GoldArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/gold_arrow.png");
		}
	}

	public static class StoneArrowRenderer extends ArrowRenderer<StoneArrowEntity> {
		/**
		 * Constructor for StoneArrowRenderer.
		 * @param renderManagerIn an <code>EntityRendererManager</code> instance
		 */
		public StoneArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>StoneArrowEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(StoneArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/stone_arrow.png");
		}
	}

	public static class WoodArrowRenderer extends ArrowRenderer<WoodArrowEntity> {
		/**
		 * Constructor for WoodArrowRenderer.
		 * @param renderManagerIn an <code>EntityRendererManager</code> instance
		 */
		public WoodArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>WoodArrowEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(WoodArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/wood_arrow.png");
		}
	}

	public static class NetheriteArrowRenderer extends ArrowRenderer<NetheriteArrowEntity> {
		/**
		 * Constructor for NetheriteArrowRenderer.
		 * @param renderManagerIn an <code>EntityRendererManager</code> instance
		 */
		public NetheriteArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>NetheriteArrowEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(NetheriteArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/netherite_arrow.png");
		}
	}

	public static class SmokeBombArrowRenderer extends ArrowRenderer<SmokeBombArrowEntity> {
		/**
		 * Constructor for SmokeBombArrowRenderer.
		 * @param renderManagerIn an <code>EntityRendererManager</code> instance
		 */
		public SmokeBombArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>SmokeBombArrowEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(SmokeBombArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/smoke_bomb_arrow.png");
		}
	}
}