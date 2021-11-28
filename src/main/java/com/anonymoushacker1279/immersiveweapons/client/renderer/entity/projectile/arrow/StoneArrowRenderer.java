package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.StoneArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StoneArrowRenderer extends ArrowRenderer<StoneArrowEntity> implements EntityRendererProvider<StoneArrowEntity> {
	/**
	 * Constructor for StoneArrowRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public StoneArrowRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>StoneArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull StoneArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/stone_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<StoneArrowEntity> create(@NotNull Context context) {
		return new StoneArrowRenderer(context);
	}
}