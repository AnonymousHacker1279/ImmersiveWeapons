package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.RockSpiderEntity;

public class RockSpiderRenderer<T extends RockSpiderEntity> extends MobRenderer<T, SpiderModel<T>> {

	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/rock_spider/rock_spider.png");

	public RockSpiderRenderer(EntityRendererProvider.Context context) {
		super(context, new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER)), 0.25F);
		addLayer(new SpiderEyesLayer<>(this));
	}

	@Override
	protected float getFlipDegrees(T livingEntity) {
		return 180.0F;
	}

	@Override
	protected void scale(T pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
		pMatrixStack.scale(0.25f, 0.25f, 0.25f);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return TEXTURE_LOCATION;
	}
}