package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.CopperBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.DiamondBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.GoldBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.IronBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.NetheriteBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.StoneBulletEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.WoodBulletEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BulletRenderer {

	public static class WoodBulletRenderer extends ArrowRenderer<WoodBulletEntity> {
		public WoodBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}
		
		@Override
		public ResourceLocation getEntityTexture(WoodBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/wood_musket_ball.png");
		}
	}
	
	public static class StoneBulletRenderer extends ArrowRenderer<StoneBulletEntity> {
		public StoneBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}
		
		@Override
		public ResourceLocation getEntityTexture(StoneBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/stone_musket_ball.png");
		}
	}
	
	public static class CopperBulletRenderer extends ArrowRenderer<CopperBulletEntity> {
		public CopperBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}
		
		@Override
		public ResourceLocation getEntityTexture(CopperBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/copper_musket_ball.png");
		}
	}
	
	public static class IronBulletRenderer extends ArrowRenderer<IronBulletEntity> {
		public IronBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}
		
		@Override
		public ResourceLocation getEntityTexture(IronBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/iron_musket_ball.png");
		}
	}
	
	public static class GoldBulletRenderer extends ArrowRenderer<GoldBulletEntity> {
		public GoldBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}
		
		@Override
		public ResourceLocation getEntityTexture(GoldBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/gold_musket_ball.png");
		}
	}
	
	public static class DiamondBulletRenderer extends ArrowRenderer<DiamondBulletEntity> {
		public DiamondBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}
		
		@Override
		public ResourceLocation getEntityTexture(DiamondBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/diamond_musket_ball.png");
		}
	}
	
	public static class NetheriteBulletRenderer extends ArrowRenderer<NetheriteBulletEntity> {
		public NetheriteBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}
		
		@Override
		public ResourceLocation getEntityTexture(NetheriteBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/netherite_musket_ball.png");
		}
	}
}
