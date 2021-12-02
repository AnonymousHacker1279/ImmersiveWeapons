package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.SmokeBombArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SmokeBombArrowRenderer extends ArrowRenderer<SmokeBombArrowEntity> implements EntityRendererProvider<SmokeBombArrowEntity> {
	/**
	 * Constructor for SmokeBombArrowRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public SmokeBombArrowRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>SmokeBombArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull SmokeBombArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/smoke_bomb_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<SmokeBombArrowEntity> create(@NotNull Context context) {
		return new SmokeBombArrowRenderer(context);
	}
}