package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.FireflyModel;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.FireflyEntity;

public class FireflyRenderer extends MobRenderer<FireflyEntity, LivingEntityRenderState, FireflyModel> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/firefly/firefly.png");

	public FireflyRenderer(EntityRendererProvider.Context context) {
		super(context, new FireflyModel(context.bakeLayer(FireflyModel.LAYER_LOCATION)), 0.01F);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected void scale(LivingEntityRenderState renderState, PoseStack poseStack) {
		poseStack.scale(0.35f, 0.35f, 0.35f);
	}
}