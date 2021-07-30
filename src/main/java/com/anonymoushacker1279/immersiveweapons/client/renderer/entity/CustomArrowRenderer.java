package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.*;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class CustomArrowRenderer {

	public static class CopperArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<CopperArrowEntity> implements EntityRendererProvider<CopperArrowEntity> {
		/**
		 * Constructor for CopperArrowRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public CopperArrowRenderer(Context context) {
			super(context);
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

		@Override
		public EntityRenderer<CopperArrowEntity> create(Context context) {
			return new CopperArrowRenderer<CopperArrowEntity>(context);
		}
	}

	public static class IronArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<IronArrowEntity> implements EntityRendererProvider<IronArrowEntity> {
		/**
		 * Constructor for IronArrowRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public IronArrowRenderer(Context context) {
			super(context);
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

		@Override
		public EntityRenderer<IronArrowEntity> create(Context context) {
			return new IronArrowRenderer<IronArrowEntity>(context);
		}
	}

	public static class DiamondArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<DiamondArrowEntity> implements EntityRendererProvider<DiamondArrowEntity> {
		/**
		 * Constructor for DiamondArrowRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public DiamondArrowRenderer(Context context) {
			super(context);
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

		@Override
		public EntityRenderer<DiamondArrowEntity> create(Context context) {
			return new DiamondArrowRenderer<DiamondArrowEntity>(context);
		}
	}

	public static class GoldArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<GoldArrowEntity> implements EntityRendererProvider<GoldArrowEntity> {
		/**
		 * Constructor for GoldArrowRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public GoldArrowRenderer(Context context) {
			super(context);
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

		@Override
		public EntityRenderer<GoldArrowEntity> create(Context context) {
			return new GoldArrowRenderer<GoldArrowEntity>(context);
		}
	}

	public static class StoneArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<StoneArrowEntity> implements EntityRendererProvider<StoneArrowEntity> {
		/**
		 * Constructor for StoneArrowRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public StoneArrowRenderer(Context context) {
			super(context);
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

		@Override
		public EntityRenderer<StoneArrowEntity> create(Context context) {
			return new StoneArrowRenderer<StoneArrowEntity>(context);
		}
	}

	public static class WoodArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<WoodArrowEntity> implements EntityRendererProvider<WoodArrowEntity> {
		/**
		 * Constructor for WoodArrowRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public WoodArrowRenderer(Context context) {
			super(context);
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

		@Override
		public EntityRenderer<WoodArrowEntity> create(Context context) {
			return new WoodArrowRenderer<WoodArrowEntity>(context);
		}
	}

	public static class NetheriteArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<NetheriteArrowEntity> implements EntityRendererProvider<NetheriteArrowEntity> {
		/**
		 * Constructor for NetheriteArrowRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public NetheriteArrowRenderer(Context context) {
			super(context);
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

		@Override
		public EntityRenderer<NetheriteArrowEntity> create(Context context) {
			return new NetheriteArrowRenderer<NetheriteArrowEntity>(context);
		}
	}

	public static class SmokeBombArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<SmokeBombArrowEntity> implements EntityRendererProvider<SmokeBombArrowEntity> {
		/**
		 * Constructor for SmokeBombArrowRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public SmokeBombArrowRenderer(Context context) {
			super(context);
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

		@Override
		public EntityRenderer<SmokeBombArrowEntity> create(Context context) {
			return new SmokeBombArrowRenderer<SmokeBombArrowEntity>(context);
		}
	}
}