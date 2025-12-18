package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.CelestialTowerModel;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;

public class CelestialTowerRenderer extends MobRenderer<CelestialTowerEntity, LivingEntityRenderState, CelestialTowerModel> {

	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/celestial_tower/celestial_tower.png");

	public CelestialTowerRenderer(EntityRendererProvider.Context context) {
		super(context, new CelestialTowerModel(context.bakeLayer(CelestialTowerModel.LAYER_LOCATION)), 1.0F);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState renderState) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected void scale(LivingEntityRenderState renderState, PoseStack poseStack) {
		poseStack.scale(8.0f, 9.0f, 8.0f);
	}
}