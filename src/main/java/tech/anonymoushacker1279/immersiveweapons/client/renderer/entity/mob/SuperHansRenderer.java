package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;

public class SuperHansRenderer extends HansRenderer {

	public SuperHansRenderer(Context context) {
		super(context);
	}

	@Override
	protected void scale(PlayerRenderState state, PoseStack matrixStack) {
		matrixStack.scale(2, 2, 2);
	}
}