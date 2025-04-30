package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class StormCreeperRenderer extends CreeperRenderer {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/storm_creeper/storm_creeper.png");

	public StormCreeperRenderer(Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(CreeperRenderState p_363813_) {
		return TEXTURE_LOCATION;
	}
}