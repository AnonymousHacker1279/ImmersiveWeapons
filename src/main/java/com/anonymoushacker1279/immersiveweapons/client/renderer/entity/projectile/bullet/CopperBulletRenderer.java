package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.CopperBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CopperBulletRenderer extends ArrowRenderer<CopperBulletEntity> implements EntityRendererProvider<CopperBulletEntity> {
	/**
	 * Constructor for CopperBulletRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public CopperBulletRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>CopperBulletEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull CopperBulletEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/copper_musket_ball.png");
	}

	@Override
	public @NotNull EntityRenderer<CopperBulletEntity> create(@NotNull Context context) {
		return new CopperBulletRenderer(context);
	}
}