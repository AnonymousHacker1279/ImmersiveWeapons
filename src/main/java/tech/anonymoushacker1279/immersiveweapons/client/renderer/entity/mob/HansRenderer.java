package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.HansEntity;

public class HansRenderer extends HumanoidMobRenderer<HansEntity, PlayerRenderState, PlayerModel> {

	private static final ResourceLocation HANS_TEXTURE = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/hans/hans.png");

	public HansRenderer(Context context) {
		super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this,
				new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
				context.getEquipmentRenderer()));
	}

	@Override
	public PlayerRenderState createRenderState() {
		return new PlayerRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(PlayerRenderState entity) {
		return HANS_TEXTURE;
	}
}