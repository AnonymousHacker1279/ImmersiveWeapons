package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.misc.ChairEntity;

public class ChairRenderer extends EntityRenderer<ChairEntity, EntityRenderState> implements EntityRendererProvider<ChairEntity> {

	/**
	 * Constructor for ChairRenderer.
	 *
	 * @param context the <code>Context</code> instance
	 */
	public ChairRenderer(Context context) {
		super(context);
	}

	@Override
	public EntityRenderState createRenderState() {
		return new EntityRenderState();
	}

	@Override
	public EntityRenderer<ChairEntity, EntityRenderState> create(Context context) {
		return null;
	}
}