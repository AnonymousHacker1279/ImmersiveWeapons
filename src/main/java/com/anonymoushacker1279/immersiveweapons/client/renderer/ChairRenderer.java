package com.anonymoushacker1279.immersiveweapons.client.renderer;

import com.anonymoushacker1279.immersiveweapons.entity.misc.ChairEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ChairRenderer extends EntityRenderer<ChairEntity> {

	/**
	 * Constructor for ChairRenderer.
	 * @param manager an <code>EntityRendererManager</code> instance
	 */
	public ChairRenderer(EntityRendererManager manager) {
		super(manager);
	}

	/**
	 * Get the texture location.
	 * @param entity the <code>ChairEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getTextureLocation(ChairEntity entity) {
		return null;
	}
}