package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;

public class FieldMedicRenderer extends HumanoidMobRenderer<FieldMedicEntity, PlayerModel<FieldMedicEntity>> {

	private static final ResourceLocation FIELD_MEDIC = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/field_medic/field_medic.png");

	/**
	 * Constructor for FieldMedicRenderer.
	 *
	 * @param context an <code>EntityRendererManager</code> instance
	 */
	public FieldMedicRenderer(Context context) {
		super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>AbstractFieldMedicEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getTextureLocation(FieldMedicEntity entity) {
		return FIELD_MEDIC;
	}
}