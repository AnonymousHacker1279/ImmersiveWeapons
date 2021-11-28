package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.IronArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IronArrowRenderer extends ArrowRenderer<IronArrowEntity> implements EntityRendererProvider<IronArrowEntity> {
	/**
	 * Constructor for IronArrowRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public IronArrowRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>IronArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull IronArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/iron_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<IronArrowEntity> create(@NotNull Context context) {
		return new IronArrowRenderer(context);
	}
}