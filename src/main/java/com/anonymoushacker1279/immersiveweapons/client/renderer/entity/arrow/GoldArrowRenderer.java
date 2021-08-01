package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.GoldArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GoldArrowRenderer extends ArrowRenderer<GoldArrowEntity> implements EntityRendererProvider<GoldArrowEntity> {
	/**
	 * Constructor for GoldArrowRenderer.
	 * @param context a <code>Context</code> instance
	 */
	public GoldArrowRenderer(Context context) {
		super(context);
	}
	/**
	 * Get the texture location.
	 * @param entity the <code>GoldArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull GoldArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/gold_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<GoldArrowEntity> create(@NotNull Context context) {
		return new GoldArrowRenderer(context);
	}
}