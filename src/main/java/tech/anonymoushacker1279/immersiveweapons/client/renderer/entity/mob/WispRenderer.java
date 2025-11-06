package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.WispEntity;

public class WispRenderer extends EntityRenderer<WispEntity, EntityRenderState> implements EntityRendererProvider<WispEntity> {

	public WispRenderer(Context context) {
		super(context);
	}

	@Override
	public EntityRenderState createRenderState() {
		return new EntityRenderState();
	}

	@Override
	public EntityRenderer<WispEntity, ?> create(Context context) {
		return null;
	}
}