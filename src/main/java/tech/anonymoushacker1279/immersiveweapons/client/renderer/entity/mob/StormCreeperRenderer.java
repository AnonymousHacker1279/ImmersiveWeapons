package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers.StormCreeperPowerLayer;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StormCreeperEntity;

public class StormCreeperRenderer extends MobRenderer<StormCreeperEntity, CreeperModel<StormCreeperEntity>> {

	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/storm_creeper/storm_creeper.png");

	public StormCreeperRenderer(Context context) {
		super(context, new CreeperModel<>(context.bakeLayer(ModelLayers.CREEPER)), 0.5F);
		addLayer(new StormCreeperPowerLayer(this, context.getModelSet()));
	}

	@Override
	protected void scale(StormCreeperEntity livingEntity, PoseStack matrixStack, float partialTickTime) {
		super.scale(livingEntity, matrixStack, partialTickTime);
		matrixStack.scale(1.25F, 1.25F, 1.25F);

		float swelling = livingEntity.getSwelling(partialTickTime);
		float sizeModifier = 1.0F + Mth.sin(swelling * 100.0F) * swelling * 0.01F;
		swelling = Mth.clamp(swelling, 0.0F, 1.0F);
		swelling *= swelling;
		swelling *= swelling;
		float x = (1.0F + swelling * 0.4F) * sizeModifier;
		float y = (1.0F + swelling * 0.1F) / sizeModifier;
		matrixStack.scale(x, y, x);
	}

	@Override
	protected float getWhiteOverlayProgress(StormCreeperEntity pLivingEntity, float pPartialTicks) {
		float swelling = pLivingEntity.getSwelling(pPartialTicks);
		return (int) (swelling * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swelling, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(StormCreeperEntity pEntity) {
		return TEXTURE_LOCATION;
	}
}