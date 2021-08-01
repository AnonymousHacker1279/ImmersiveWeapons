package com.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.anonymoushacker1279.immersiveweapons.entity.misc.ChairEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ChairRenderer extends EntityRenderer<ChairEntity> implements EntityRendererProvider<ChairEntity> {

	/**
	 * Constructor for ChairRenderer.
	 * @param context the <code>Context</code> instance
	 */
	public ChairRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 * @param entity the <code>ChairEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull ChairEntity entity) {
		return null;
	}

	@Override
	public @NotNull EntityRenderer<ChairEntity> create(@NotNull Context context) {
		return null;
	}
}