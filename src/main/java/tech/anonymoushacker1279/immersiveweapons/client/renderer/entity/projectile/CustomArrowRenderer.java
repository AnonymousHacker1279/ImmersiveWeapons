package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class CustomArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<T> implements EntityRendererProvider<T> {

	final ResourceLocation textureLocation;

	/**
	 * Constructor for CustomArrowRenderer.
	 *
	 * @param context         a <code>Context</code> instance
	 * @param textureLocation a <code>ResourceLocation</code> pointing to the texture
	 */
	public CustomArrowRenderer(Context context, ResourceLocation textureLocation) {
		super(context);
		this.textureLocation = textureLocation;
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>AbstractArrow</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return textureLocation;
	}

	@Override
	public EntityRenderer<T> create(Context context) {
		return new CustomArrowRenderer<>(context, textureLocation);
	}
}