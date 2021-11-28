package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.StoneBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StoneBulletRenderer extends ArrowRenderer<StoneBulletEntity> implements EntityRendererProvider<StoneBulletEntity> {
	/**
	 * Constructor for StoneBulletRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public StoneBulletRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>StoneBulletEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull StoneBulletEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/stone_musket_ball.png");
	}

	@Override
	public @NotNull EntityRenderer<StoneBulletEntity> create(@NotNull Context context) {
		return new StoneBulletRenderer(context);
	}
}