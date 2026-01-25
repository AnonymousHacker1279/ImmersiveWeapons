package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity;

public class CustomArrowRenderer extends ArrowRenderer<CustomArrowEntity, ArrowRenderState> {

	private final Identifier textureLocation;

	public CustomArrowRenderer(Context context, Identifier textureLocation) {
		super(context);
		this.textureLocation = textureLocation;
	}

	@Override
	protected Identifier getTextureLocation(ArrowRenderState renderState) {
		return textureLocation;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}
}