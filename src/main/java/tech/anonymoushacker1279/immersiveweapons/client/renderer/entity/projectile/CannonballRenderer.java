package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.CannonballModel;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CannonballEntity;

public class CannonballRenderer extends EntityRenderer<CannonballEntity, LivingEntityRenderState> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/item/cannonball.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);
	private final CannonballModel model;

	public CannonballRenderer(Context context) {
		super(context);
		model = new CannonballModel(context.bakeLayer(CannonballModel.LAYER_LOCATION));
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void render(LivingEntityRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();

		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
		poseStack.mulPose(Axis.XP.rotationDegrees(state.xRot));

		poseStack.scale(1.25f, 1.25f, 1.25f);
		poseStack.translate(-0.125f, 0.0125f, -0.125f);

		model.renderToBuffer(poseStack, bufferSource.getBuffer(RENDER_TYPE), packedLight, OverlayTexture.NO_OVERLAY, 16777215);

		poseStack.popPose();
	}
}