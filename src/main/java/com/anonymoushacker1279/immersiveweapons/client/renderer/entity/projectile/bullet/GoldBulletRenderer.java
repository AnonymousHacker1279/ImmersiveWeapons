package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.GoldBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GoldBulletRenderer extends ArrowRenderer<GoldBulletEntity> implements EntityRendererProvider<GoldBulletEntity> {
	/**
	 * Constructor for GoldBulletRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public GoldBulletRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>GoldBulletEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull GoldBulletEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/gold_musket_ball.png");
	}

	@Override
	public @NotNull EntityRenderer<GoldBulletEntity> create(@NotNull Context context) {
		return new GoldBulletRenderer(context);
	}
}