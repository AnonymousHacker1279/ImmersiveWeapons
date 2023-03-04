package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import javax.annotation.Nullable;

public class StarWolfRenderer extends WolfRenderer {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/star_wolf/star_wolf.png");
	private static final ResourceLocation TEXTURE_LOCATION_TAMED = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/star_wolf/star_wolf_tame.png");
	private static final ResourceLocation TEXTURE_LOCATION_ANGRY = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/star_wolf/star_wolf_angry.png");

	public StarWolfRenderer(Context context) {
		super(context);
	}

	@Override
	protected void scale(Wolf livingEntity, PoseStack matrixStack, float partialTickTime) {
		super.scale(livingEntity, matrixStack, partialTickTime);

		// Increase the size by 10%
		matrixStack.scale(1.1F, 1.1F, 1.1F);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(Wolf pEntity) {
		if (pEntity.isTame()) {
			return TEXTURE_LOCATION_TAMED;
		} else {
			return pEntity.isAngry() ? TEXTURE_LOCATION_ANGRY : TEXTURE_LOCATION;
		}
	}

	@Nullable
	@Override
	protected RenderType getRenderType(Wolf livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
		return RenderType.entityTranslucent(getTextureLocation(livingEntity));
	}
}