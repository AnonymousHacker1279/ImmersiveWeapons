package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;
import tech.anonymoushacker1279.immersiveweapons.client.MobGunAnimations;

public class SimpleHumanoidRenderer<T extends Mob> extends HumanoidMobRenderer<T, GunAnimatedRenderState, GunAnimatedHumanoidModel> {

	private final Identifier textureLocation;

	public SimpleHumanoidRenderer(Context context, Identifier textureLocation) {
		super(context, new GunAnimatedHumanoidModel(context.bakeLayer(ModelLayers.PLAYER), location -> RenderTypes.entityTranslucent(location, true)), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this,
				ArmorModelSet.bake(
						ModelLayers.PLAYER_ARMOR,
						context.getModelSet(),
						part -> new GunAnimatedHumanoidModel(part, location -> RenderTypes.entityTranslucent(location, true))
				),
				context.getEquipmentRenderer()));

		this.textureLocation = textureLocation;
	}

	@Override
	public GunAnimatedRenderState createRenderState() {
		return new GunAnimatedRenderState();
	}

	@Override
	public void extractRenderState(T entity, GunAnimatedRenderState state, float partialTick) {
		super.extractRenderState(entity, state, partialTick);
		state.gunAnimation = MobGunAnimations.update(entity, partialTick);
	}

	@Override
	public Identifier getTextureLocation(GunAnimatedRenderState entity) {
		return textureLocation;
	}
}
