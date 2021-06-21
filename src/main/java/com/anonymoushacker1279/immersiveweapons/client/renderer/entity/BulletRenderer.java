package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class BulletRenderer {

	public static class WoodBulletRenderer extends ArrowRenderer<WoodBulletEntity> {
		public WoodBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(WoodBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/wood_musket_ball.png");
		}
	}

	public static class StoneBulletRenderer extends ArrowRenderer<StoneBulletEntity> {
		public StoneBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(StoneBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/stone_musket_ball.png");
		}
	}

	public static class CopperBulletRenderer extends ArrowRenderer<CopperBulletEntity> {
		public CopperBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(CopperBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/copper_musket_ball.png");
		}
	}

	public static class IronBulletRenderer extends ArrowRenderer<IronBulletEntity> {
		public IronBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(IronBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/iron_musket_ball.png");
		}
	}

	public static class GoldBulletRenderer extends ArrowRenderer<GoldBulletEntity> {
		public GoldBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(GoldBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/gold_musket_ball.png");
		}
	}

	public static class DiamondBulletRenderer extends ArrowRenderer<DiamondBulletEntity> {
		public DiamondBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(DiamondBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/diamond_musket_ball.png");
		}
	}

	public static class NetheriteBulletRenderer extends ArrowRenderer<NetheriteBulletEntity> {
		public NetheriteBulletRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(NetheriteBulletEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/netherite_musket_ball.png");
		}
	}

	public static class FlareRenderer implements IRenderFactory<FlareEntity> {

		@Override
		public EntityRenderer<? super FlareEntity> createRenderFor(EntityRendererManager manager) {
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
			return new SpriteRenderer<>(manager, itemRenderer);
		}
	}
}