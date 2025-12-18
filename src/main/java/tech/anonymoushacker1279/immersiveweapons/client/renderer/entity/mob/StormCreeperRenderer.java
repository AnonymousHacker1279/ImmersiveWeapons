package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class StormCreeperRenderer extends CreeperRenderer {

	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/storm_creeper/storm_creeper.png");

	public StormCreeperRenderer(Context context) {
		super(context);
	}

	@Override
	public Identifier getTextureLocation(CreeperRenderState state) {
		return TEXTURE_LOCATION;
	}
}