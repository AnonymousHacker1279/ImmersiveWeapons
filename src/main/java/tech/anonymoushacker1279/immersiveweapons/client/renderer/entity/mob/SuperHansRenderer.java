package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.HansEntity;

public class SuperHansRenderer extends HansRenderer {

	/**
	 * Constructor for HansRenderer.
	 *
	 * @param context an <code>EntityRendererManager</code> instance
	 */
	public SuperHansRenderer(Context context) {
		super(context);
	}

	@Override
	protected void scale(HansEntity livingEntity, PoseStack matrixStack, float partialTickTime) {
		matrixStack.scale(2, 2, 2);
	}
}