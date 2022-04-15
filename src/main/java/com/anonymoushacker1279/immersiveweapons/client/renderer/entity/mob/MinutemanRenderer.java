package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MinutemanRenderer extends HumanoidMobRenderer<MinutemanEntity, PlayerModel<MinutemanEntity>> {
	private static final ResourceLocation MINUTEMAN_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/minuteman/minuteman.png");

	/**
	 * Constructor for MinutemanRenderer.
	 *
	 * @param context a <code>Context</code> instance
	 */
	public MinutemanRenderer(Context context) {
		super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM), false), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
	}

	/**
	 * Get the texture location.
	 *
	 * @param entity the <code>MinutemanEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull MinutemanEntity entity) {
		return MINUTEMAN_TEXTURE;
	}
}