package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.bullet;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.NetheriteBulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class NetheriteBulletRenderer extends ArrowRenderer<NetheriteBulletEntity> implements EntityRendererProvider<NetheriteBulletEntity> {
	/**
	 * Constructor for NetheriteBulletRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public NetheriteBulletRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>NetheriteBulletEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull NetheriteBulletEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/netherite_musket_ball.png");
	}

	@Override
	public @NotNull EntityRenderer<NetheriteBulletEntity> create(@NotNull Context context) {
		return new NetheriteBulletRenderer(context);
	}
}