package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.endermite.EndermiteModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;

public class StarmiteRenderer extends MobRenderer<StarmiteEntity, LivingEntityRenderState, EndermiteModel> {

	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/starmite/starmite.png");

	public StarmiteRenderer(Context context) {
		super(context, new EndermiteModel(context.bakeLayer(ModelLayers.ENDERMITE)), 0.3F);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected float getFlipDegrees() {
		return 180.0F;
	}
}