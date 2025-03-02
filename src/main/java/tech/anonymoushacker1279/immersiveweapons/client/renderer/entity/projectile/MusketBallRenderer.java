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
import tech.anonymoushacker1279.immersiveweapons.client.model.MusketBallModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;

public class MusketBallRenderer<T extends BulletEntity> extends EntityRenderer<T> {

	private final ResourceLocation textureLocation;
	private final MusketBallModel<BulletEntity> model;

	public MusketBallRenderer(EntityRendererProvider.Context context, String name) {
		super(context);
		textureLocation = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/item/%s_musket_ball.png".formatted(name));
		model = new MusketBallModel<>(context.bakeLayer(MusketBallModel.LAYER_LOCATION));
	}

	@Override
	public void render(BulletEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
		poseStack.pushPose();

		poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
		poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));

		poseStack.scale(1.25f, 1.25f, 1.25f);
		poseStack.translate(-0.025f, 0.025f, -0.025f);

		model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(textureLocation)), light, OverlayTexture.NO_OVERLAY, 16777215);

		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return textureLocation;
	}
}