package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.CopperArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CopperArrowRenderer extends ArrowRenderer<CopperArrowEntity> implements EntityRendererProvider<CopperArrowEntity> {
	/**
	 * Constructor for CopperArrowRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public CopperArrowRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>CopperArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull CopperArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/copper_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<CopperArrowEntity> create(@NotNull Context context) {
		return new CopperArrowRenderer(context);
	}
}