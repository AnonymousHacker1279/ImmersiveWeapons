package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.LavaRevenantModel;

public class LavaRevenantEyesLayer extends EyesLayer<LivingEntityRenderState, LavaRevenantModel> {
	private static final RenderType EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/lava_revenant/lava_revenant.png"));

	public LavaRevenantEyesLayer(RenderLayerParent<LivingEntityRenderState, LavaRevenantModel> layerParent) {
		super(layerParent);
	}

	@Override
	public RenderType renderType() {
		return EYES;
	}
}