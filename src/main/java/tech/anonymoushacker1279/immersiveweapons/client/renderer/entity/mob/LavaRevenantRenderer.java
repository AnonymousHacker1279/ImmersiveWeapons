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
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;

public class LavaRevenantRenderer extends MobRenderer<LavaRevenantEntity, LavaRevenantModel<LavaRevenantEntity>> {

	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/lava_revenant/lava_revenant.png");

	public LavaRevenantRenderer(EntityRendererProvider.Context context) {
		super(context, new LavaRevenantModel<>(context.bakeLayer(ModelLayers.PHANTOM)), 0.75F);
		addLayer(new LavaRevenantEyesLayer<>(this));
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(LavaRevenantEntity pEntity) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected void scale(LavaRevenantEntity entity, PoseStack poseStack, float partialTick) {
		int size = entity.getSize();
		float sizeModifier = 1.0F + 5.0F * (float) size;
		poseStack.scale(sizeModifier, sizeModifier, sizeModifier);
		poseStack.translate(0.0D, 1.3125D, 0.1875D);
	}

	@Override
	protected void setupRotations(LavaRevenantEntity entity, PoseStack pMatrixStack, float age, float yaw, float partialTick, float scale) {
		super.setupRotations(entity, pMatrixStack, age, yaw, partialTick, scale);
		pMatrixStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
	}
}