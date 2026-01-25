package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.wolf.Wolf;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class StarWolfRenderer extends WolfRenderer {

	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/star_wolf/star_wolf.png");
	private static final Identifier TEXTURE_LOCATION_TAMED = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/star_wolf/star_wolf_tame.png");
	private static final Identifier TEXTURE_LOCATION_ANGRY = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/star_wolf/star_wolf_angry.png");

	public StarWolfRenderer(Context context) {
		super(context);
	}

	@Override
	protected void scale(WolfRenderState state, PoseStack matrixStack) {
		super.scale(state, matrixStack);

		// Increase the size by 10%
		matrixStack.scale(1.1F, 1.1F, 1.1F);
	}

	@Nullable
	@Override
	protected RenderType getRenderType(WolfRenderState renderState, boolean isVisible, boolean renderTranslucent, boolean appearsGlowing) {
		return RenderTypes.entityTranslucent(getTextureLocation(renderState));
	}

	@Override
	public void extractRenderState(Wolf wolf, WolfRenderState reusedState, float partialTick) {
		super.extractRenderState(wolf, reusedState, partialTick);

		if (wolf.isTame()) {
			reusedState.texture = TEXTURE_LOCATION_TAMED;
		} else if (wolf.isAngry()) {
			reusedState.texture = TEXTURE_LOCATION_ANGRY;
		} else {
			reusedState.texture = TEXTURE_LOCATION;
		}
	}
}