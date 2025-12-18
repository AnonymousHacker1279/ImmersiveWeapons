package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.SuperHansEntity;

public class SuperHansRenderer extends SimpleHumanoidRenderer<SuperHansEntity> {

	public SuperHansRenderer(Context context, Identifier textureLocation) {
		super(context, textureLocation);
	}

	@Override
	protected void scale(HumanoidRenderState state, PoseStack matrixStack) {
		matrixStack.scale(2, 2, 2);
	}
}