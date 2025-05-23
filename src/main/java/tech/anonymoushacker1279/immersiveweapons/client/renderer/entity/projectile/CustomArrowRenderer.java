package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity;

public class CustomArrowRenderer extends ArrowRenderer<CustomArrowEntity, ArrowRenderState> {

	private final ResourceLocation textureLocation;

	public CustomArrowRenderer(Context context, ResourceLocation textureLocation) {
		super(context);
		this.textureLocation = textureLocation;
	}

	@Override
	protected ResourceLocation getTextureLocation(ArrowRenderState renderState) {
		return textureLocation;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}
}