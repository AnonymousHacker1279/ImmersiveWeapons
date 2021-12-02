package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.model.CelestialTowerModel;
import com.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CelestialTowerRenderer<T extends CelestialTowerEntity> extends MobRenderer<T, CelestialTowerModel<T>> {

	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/celestial_tower/celestial_tower.png");

	public CelestialTowerRenderer(EntityRendererProvider.Context context) {
		super(context, new CelestialTowerModel<>(context.bakeLayer(CelestialTowerModel.LAYER_LOCATION)), 1.0F);
	}

	@Override
	protected void scale(@NotNull T pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
		pMatrixStack.scale(8.0f, 9.0f, 8.0f);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
		return TEXTURE_LOCATION;
	}
}