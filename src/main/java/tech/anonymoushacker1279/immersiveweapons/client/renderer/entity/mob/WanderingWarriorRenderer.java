package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.WanderingWarriorEntity;

public class WanderingWarriorRenderer extends HumanoidMobRenderer<WanderingWarriorEntity, PlayerModel<WanderingWarriorEntity>> {

	private static final ResourceLocation WANDERING_WARRIOR_TEXTURE = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/wandering_warrior/wandering_warrior.png");

	/**
	 * Constructor for WanderingWarriorRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public WanderingWarriorRenderer(Context context) {
		super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this,
				new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
				context.getModelManager()));
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>WanderingWarriorEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getTextureLocation(WanderingWarriorEntity entity) {
		return WANDERING_WARRIOR_TEXTURE;
	}
}