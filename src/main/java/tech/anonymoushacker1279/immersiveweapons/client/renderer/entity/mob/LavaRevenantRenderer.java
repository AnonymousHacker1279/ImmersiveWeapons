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
	protected void scale(LavaRevenantEntity pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
		int i = pLivingEntity.getSize();
		float f = 1.0F + 5.0F * (float) i;
		pMatrixStack.scale(f, f, f);
		pMatrixStack.translate(0.0D, 1.3125D, 0.1875D);
	}

	@Override
	protected void setupRotations(LavaRevenantEntity pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
		super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
		pMatrixStack.mulPose(Axis.XP.rotationDegrees(pEntityLiving.getXRot()));
	}
}