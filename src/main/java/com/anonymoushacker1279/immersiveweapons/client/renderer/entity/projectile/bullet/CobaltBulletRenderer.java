package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.CobaltBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CobaltBulletRenderer extends ArrowRenderer<CobaltBulletEntity> implements EntityRendererProvider<CobaltBulletEntity> {
	/**
	 * Constructor for CobaltBulletRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public CobaltBulletRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>CobaltBulletEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull CobaltBulletEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/cobalt_musket_ball.png");
	}

	@Override
	public @NotNull EntityRenderer<CobaltBulletEntity> create(@NotNull Context context) {
		return new CobaltBulletRenderer(context);
	}
}