package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.WoodArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WoodArrowRenderer extends ArrowRenderer<WoodArrowEntity> implements EntityRendererProvider<WoodArrowEntity> {
	/**
	 * Constructor for WoodArrowRenderer.
	 * @param context a <code>Context</code> instance
	 */
	public WoodArrowRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 * @param entity the <code>WoodArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull WoodArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/wood_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<WoodArrowEntity> create(@NotNull Context context) {
		return new WoodArrowRenderer(context);
	}
}