package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.WoodBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WoodBulletRenderer extends ArrowRenderer<WoodBulletEntity> implements EntityRendererProvider<WoodBulletEntity> {
	/**
	 * Constructor for WoodBulletRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public WoodBulletRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>WoodBulletEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull WoodBulletEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/wood_musket_ball.png");
	}

	@Override
	public @NotNull EntityRenderer<WoodBulletEntity> create(@NotNull Context context) {
		return new WoodBulletRenderer(context);
	}
}