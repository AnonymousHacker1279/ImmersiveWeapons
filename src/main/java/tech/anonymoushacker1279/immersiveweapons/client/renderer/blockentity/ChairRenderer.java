package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.entity.misc.ChairEntity;

public class ChairRenderer extends EntityRenderer<ChairEntity> implements EntityRendererProvider<ChairEntity> {

	/**
	 * Constructor for ChairRenderer.
	 *
	 * @param context the <code>Context</code> instance
	 */
	public ChairRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>ChairEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getTextureLocation(ChairEntity entity) {
		return null;
	}

	@Override
	public EntityRenderer<ChairEntity> create(Context context) {
		return null;
	}
}