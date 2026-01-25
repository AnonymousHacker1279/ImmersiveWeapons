package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;

public class SimpleHumanoidRenderer<T extends Mob> extends HumanoidMobRenderer<T, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

	private final Identifier textureLocation;

	public SimpleHumanoidRenderer(Context context, Identifier textureLocation) {
		super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER), location -> RenderTypes.entityTranslucent(location, true)), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this,
				ArmorModelSet.bake(
						ModelLayers.PLAYER_ARMOR,
						context.getModelSet(),
						part -> new HumanoidModel<>(part, location -> RenderTypes.entityTranslucent(location, true))
				),
				context.getEquipmentRenderer()));

		this.textureLocation = textureLocation;
	}

	@Override
	public HumanoidRenderState createRenderState() {
		return new HumanoidRenderState();
	}

	@Override
	public Identifier getTextureLocation(HumanoidRenderState entity) {
		return textureLocation;
	}
}