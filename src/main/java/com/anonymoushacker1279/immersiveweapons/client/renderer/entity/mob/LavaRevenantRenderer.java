package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.model.LavaRevenantModel;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers.LavaRevenantEyesLayer;
import com.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

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
	public @NotNull ResourceLocation getTextureLocation(@NotNull LavaRevenantEntity pEntity) {
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
	protected void setupRotations(@NotNull LavaRevenantEntity pEntityLiving, @NotNull PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
		super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
		pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(pEntityLiving.getXRot()));
	}
}