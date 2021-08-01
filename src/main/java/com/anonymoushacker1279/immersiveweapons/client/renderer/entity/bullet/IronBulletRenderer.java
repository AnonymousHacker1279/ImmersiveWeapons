package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.bullet;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.IronBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IronBulletRenderer extends ArrowRenderer<IronBulletEntity> implements EntityRendererProvider<IronBulletEntity> {
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
	public @NotNull ResourceLocation getTextureLocation(@NotNull IronBulletEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/iron_musket_ball.png");
	}

	@Override
	public @NotNull EntityRenderer<IronBulletEntity> create(@NotNull Context context) {
		return new IronBulletRenderer(context);
	}
}