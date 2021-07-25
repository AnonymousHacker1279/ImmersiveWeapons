package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.*;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class BulletRenderer {

	public static class WoodBulletRenderer<T extends AbstractArrow> extends ArrowRenderer<WoodBulletEntity> implements EntityRendererProvider<WoodBulletEntity> {
		/**
		 * Constructor for WoodBulletRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public WoodBulletRenderer(Context context) {
			super(context);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>WoodBulletEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(WoodBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/wood_musket_ball.png");
		}

		@Override
		public EntityRenderer<WoodBulletEntity> create(Context context) {
			return new WoodBulletRenderer<WoodBulletEntity>(context);
		}
	}

	public static class StoneBulletRenderer<T extends AbstractArrow> extends ArrowRenderer<StoneBulletEntity> implements EntityRendererProvider<StoneBulletEntity> {
		/**
		 * Constructor for StoneBulletRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public StoneBulletRenderer(Context context) {
			super(context);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>StoneBulletEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(StoneBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/stone_musket_ball.png");
		}

		@Override
		public EntityRenderer<StoneBulletEntity> create(Context context) {
			return new StoneBulletRenderer<StoneBulletEntity>(context);
		}
	}

	public static class CopperBulletRenderer<T extends AbstractArrow> extends ArrowRenderer<CopperBulletEntity> implements EntityRendererProvider<CopperBulletEntity> {
		/**
		 * Constructor for CopperBulletRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public CopperBulletRenderer(Context context) {
			super(context);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>CopperBulletEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(CopperBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/copper_musket_ball.png");
		}

		@Override
		public EntityRenderer<CopperBulletEntity> create(Context context) {
			return new CopperBulletRenderer<CopperBulletEntity>(context);
		}
	}

	public static class IronBulletRenderer<T extends AbstractArrow> extends ArrowRenderer<IronBulletEntity> implements EntityRendererProvider<IronBulletEntity> {
		/**
		 * Constructor for IronBulletRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public IronBulletRenderer(Context context) {
			super(context);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>IronBulletEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(IronBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/iron_musket_ball.png");
		}

		@Override
		public EntityRenderer<IronBulletEntity> create(Context context) {
			return new IronBulletRenderer<IronBulletEntity>(context);
		}
	}

	public static class GoldBulletRenderer<T extends AbstractArrow> extends ArrowRenderer<GoldBulletEntity> implements EntityRendererProvider<GoldBulletEntity> {
		/**
		 * Constructor for GoldBulletRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public GoldBulletRenderer(Context context) {
			super(context);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>GoldBulletEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(GoldBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/gold_musket_ball.png");
		}

		@Override
		public EntityRenderer<GoldBulletEntity> create(Context context) {
			return new GoldBulletRenderer<GoldBulletEntity>(context);
		}
	}

	public static class DiamondBulletRenderer<T extends AbstractArrow> extends ArrowRenderer<DiamondBulletEntity> implements EntityRendererProvider<DiamondBulletEntity> {
		/**
		 * Constructor for DiamondBulletRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public DiamondBulletRenderer(Context context) {
			super(context);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>DiamondBulletEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(DiamondBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/diamond_musket_ball.png");
		}

		@Override
		public EntityRenderer<DiamondBulletEntity> create(Context context) {
			return new DiamondBulletRenderer<DiamondBulletEntity>(context);
		}
	}

	public static class NetheriteBulletRenderer<T extends AbstractArrow> extends ArrowRenderer<NetheriteBulletEntity> implements EntityRendererProvider<NetheriteBulletEntity> {
		/**
		 * Constructor for NetheriteBulletRenderer.
		 * @param context a <code>Context</code> instance
		 */
		public NetheriteBulletRenderer(Context context) {
			super(context);
		}

		/**
		 * Get the texture location.
		 * @param entity the <code>NetheriteBulletEntity</code> instance
		 * @return ResourceLocation
		 */
		@Override
		public ResourceLocation getTextureLocation(NetheriteBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/netherite_musket_ball.png");
		}

		@Override
		public EntityRenderer<NetheriteBulletEntity> create(Context context) {
			return new NetheriteBulletRenderer<NetheriteBulletEntity>(context);
		}
	}
}