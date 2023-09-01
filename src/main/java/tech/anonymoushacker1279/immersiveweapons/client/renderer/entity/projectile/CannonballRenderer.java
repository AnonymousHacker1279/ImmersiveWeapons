package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CannonballEntity;

public class CannonballRenderer<T extends CannonballEntity> extends EntityRenderer<T> {

	public CannonballRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(CannonballEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
	                   MultiBufferSource bufferSource, int light) {

		poseStack.pushPose();

		poseStack.scale(1.5f, 1.5f, 1.5f);
		poseStack.translate(0, 0.2f, 0);

		Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(), ItemDisplayContext.GROUND, light,
				OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), 0);

		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return null;
	}
}