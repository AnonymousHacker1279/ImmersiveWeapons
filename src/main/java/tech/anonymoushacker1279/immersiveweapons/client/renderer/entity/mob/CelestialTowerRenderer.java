package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.CelestialTowerModel;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;

public class CelestialTowerRenderer<T extends CelestialTowerEntity> extends MobRenderer<T, CelestialTowerModel<T>> {

	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/celestial_tower/celestial_tower.png");

	public CelestialTowerRenderer(EntityRendererProvider.Context context) {
		super(context, new CelestialTowerModel<>(context.bakeLayer(CelestialTowerModel.LAYER_LOCATION)), 1.0F);
	}

	@Override
	protected void scale(T pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
		pMatrixStack.scale(8.0f, 9.0f, 8.0f);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return TEXTURE_LOCATION;
	}
}