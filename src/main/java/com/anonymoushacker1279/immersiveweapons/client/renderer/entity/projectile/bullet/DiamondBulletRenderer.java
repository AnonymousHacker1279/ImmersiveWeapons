package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.DiamondBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DiamondBulletRenderer extends ArrowRenderer<DiamondBulletEntity> implements EntityRendererProvider<DiamondBulletEntity> {
	/**
	 * Constructor for DiamondBulletRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public DiamondBulletRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>DiamondBulletEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull DiamondBulletEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/diamond_musket_ball.png");
	}

	@Override
	public @NotNull EntityRenderer<DiamondBulletEntity> create(@NotNull Context context) {
		return new DiamondBulletRenderer(context);
	}
}