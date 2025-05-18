package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.LavaRevenantModel;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers.LavaRevenantEyesLayer;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.LavaRevenantRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;

public class LavaRevenantRenderer extends MobRenderer<LavaRevenantEntity, LavaRevenantRenderState, LavaRevenantModel> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/lava_revenant/lava_revenant.png");

	public LavaRevenantRenderer(EntityRendererProvider.Context context) {
		super(context, new LavaRevenantModel(context.bakeLayer(ModelLayers.PHANTOM)), 0.75F);
		addLayer(new LavaRevenantEyesLayer(this));
	}

	@Override
	public LavaRevenantRenderState createRenderState() {
		return new LavaRevenantRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(LavaRevenantRenderState state) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected void scale(LavaRevenantRenderState state, PoseStack poseStack) {
		int size = state.size;
		float sizeModifier = 1.0F + 5.0F * (float) size;
		poseStack.scale(sizeModifier, sizeModifier, sizeModifier);
		poseStack.translate(0.0D, 1.3125D, 0.1875D);
	}

	@Override
	protected void setupRotations(LavaRevenantRenderState renderState, PoseStack poseStack, float bodyRot, float scale) {
		super.setupRotations(renderState, poseStack, bodyRot, scale);
		poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));
	}

	@Override
	public void extractRenderState(LavaRevenantEntity entity, LavaRevenantRenderState reusedState, float partialTick) {
		super.extractRenderState(entity, reusedState, partialTick);

		reusedState.size = entity.getSize();
	}
}