package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;

public class StarmiteRenderer extends MobRenderer<StarmiteEntity, EndermiteModel<StarmiteEntity>> {
	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/starmite/starmite.png");

	public StarmiteRenderer(EntityRendererProvider.Context context) {
		super(context, new EndermiteModel<>(context.bakeLayer(ModelLayers.ENDERMITE)), 0.3F);
	}

	@Override
	protected float getFlipDegrees(StarmiteEntity pLivingEntity) {
		return 180.0F;
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(StarmiteEntity pEntity) {
		return TEXTURE_LOCATION;
	}
}