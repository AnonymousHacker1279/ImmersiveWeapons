package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.FireflyModel;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.FireflyEntity;

public class FireflyRenderer<T extends FireflyEntity> extends MobRenderer<T, FireflyModel<T>> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/firefly/firefly.png");

	public FireflyRenderer(EntityRendererProvider.Context context) {
		super(context, new FireflyModel<>(context.bakeLayer(FireflyModel.LAYER_LOCATION)), 0.01F);
	}

	@Override
	protected void scale(T pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
		pMatrixStack.scale(0.35f, 0.35f, 0.35f);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return TEXTURE_LOCATION;
	}
}