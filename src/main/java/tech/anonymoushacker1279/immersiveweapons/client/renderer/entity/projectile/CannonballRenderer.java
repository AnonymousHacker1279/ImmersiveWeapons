package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.CannonballModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CannonballEntity;

public class CannonballRenderer<T extends CannonballEntity> extends EntityRenderer<T> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/item/cannonball.png");
	private final CannonballModel<CannonballEntity> model;

	public CannonballRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new CannonballModel<>(context.bakeLayer(CannonballModel.LAYER_LOCATION));
	}

	@Override
	public void render(CannonballEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
	                   MultiBufferSource bufferSource, int light) {
		poseStack.pushPose();

		poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
		poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));

		poseStack.scale(1.25f, 1.25f, 1.25f);
		poseStack.translate(-0.125f, 0.0125f, -0.125f);

		model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE_LOCATION)), light, OverlayTexture.NO_OVERLAY, 16777215);

		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return TEXTURE_LOCATION;
	}
}