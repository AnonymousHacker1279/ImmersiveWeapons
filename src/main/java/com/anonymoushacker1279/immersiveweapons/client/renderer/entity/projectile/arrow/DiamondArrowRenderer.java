package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.DiamondArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DiamondArrowRenderer extends ArrowRenderer<DiamondArrowEntity> implements EntityRendererProvider<DiamondArrowEntity> {
	/**
	 * Constructor for DiamondArrowRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public DiamondArrowRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>DiamondArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull DiamondArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/diamond_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<DiamondArrowEntity> create(@NotNull Context context) {
		return new DiamondArrowRenderer(context);
	}
}