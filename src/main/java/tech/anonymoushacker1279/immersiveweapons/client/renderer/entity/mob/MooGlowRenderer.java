package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers.MooGlowMoonglowLayer;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.MooGlowRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.animal.MooGlowEntity;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

@SuppressWarnings("deprecation")
public class MooGlowRenderer extends AgeableMobRenderer<MooGlowEntity, MooGlowRenderState, CowModel> {

	public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/mooglow/mooglow.png");
	private final BlockModelResolver blockModelResolver;

	public MooGlowRenderer(EntityRendererProvider.Context context) {
		super(context, new CowModel(context.bakeLayer(ModelLayers.MOOSHROOM)), new CowModel(context.bakeLayer(ModelLayers.MOOSHROOM_BABY)), 0.7F);
		blockModelResolver = context.getBlockModelResolver();
		addLayer(new MooGlowMoonglowLayer(this));
	}

	@Override
	public MooGlowRenderState createRenderState() {
		return new MooGlowRenderState();
	}

	@Override
	public void extractRenderState(MooGlowEntity entity, MooGlowRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		blockModelResolver.update(state.moonglowModel, BlockRegistry.MOONGLOW.get().defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
	}

	@Override
	public Identifier getTextureLocation(MooGlowRenderState renderState) {
		return TEXTURE_LOCATION;
	}
}