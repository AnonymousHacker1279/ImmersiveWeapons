package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.CobaltArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CobaltArrowRenderer extends ArrowRenderer<CobaltArrowEntity> implements EntityRendererProvider<CobaltArrowEntity> {

	/**
	 * Constructor for CobaltArrowRenderer.
	 * @param context a <code>Context</code> instance
	 */
	public CobaltArrowRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 * @param entity the <code>CobaltArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull CobaltArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/cobalt_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<CobaltArrowEntity> create(@NotNull Context context) {
		return new CobaltArrowRenderer(context);
	}
}