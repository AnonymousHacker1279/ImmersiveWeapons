package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.*;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CustomArrowRenderer {

	public static class CopperArrowRenderer extends ArrowRenderer<CopperArrowEntity> {
		public CopperArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(CopperArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/copper_arrow.png");
		}
	}

	public static class IronArrowRenderer extends ArrowRenderer<IronArrowEntity> {
		public IronArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(IronArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/iron_arrow.png");
		}
	}

	public static class DiamondArrowRenderer extends ArrowRenderer<DiamondArrowEntity> {
		public DiamondArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(DiamondArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/diamond_arrow.png");
		}
	}

	public static class GoldArrowRenderer extends ArrowRenderer<GoldArrowEntity> {
		public GoldArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(GoldArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/gold_arrow.png");
		}
	}

	public static class StoneArrowRenderer extends ArrowRenderer<StoneArrowEntity> {
		public StoneArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(StoneArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/stone_arrow.png");
		}
	}

	public static class WoodArrowRenderer extends ArrowRenderer<WoodArrowEntity> {
		public WoodArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(WoodArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/wood_arrow.png");
		}
	}

	public static class NetheriteArrowRenderer extends ArrowRenderer<NetheriteArrowEntity> {
		public NetheriteArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(NetheriteArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/netherite_arrow.png");
		}
	}

	public static class SmokeBombArrowRenderer extends ArrowRenderer<SmokeBombArrowEntity> {
		public SmokeBombArrowRenderer(EntityRendererManager renderManagerIn) {
			super(renderManagerIn);
		}

		@Override
		public ResourceLocation getTextureLocation(SmokeBombArrowEntity entity) {
			return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/smoke_bomb_arrow.png");
		}
	}
}