package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import tech.anonymoushacker1279.immersiveweapons.client.CustomArmPoses;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.SoldierEntity;

public class SoldierRenderer extends HumanoidMobRenderer<SoldierEntity, AvatarRenderState, HumanoidModel<AvatarRenderState>> {

	private final ResourceLocation textureLocation;

	public SoldierRenderer(Context context, ResourceLocation textureLocation) {
		super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this,
				ArmorModelSet.bake(
						ModelLayers.PLAYER_ARMOR,
						context.getModelSet(),
						part -> new PlayerModel(part, false)
				),
				context.getEquipmentRenderer()));

		this.textureLocation = textureLocation;
	}

	@Override
	public AvatarRenderState createRenderState() {
		return new AvatarRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(AvatarRenderState entity) {
		return textureLocation;
	}

	@Override
	protected HumanoidModel.ArmPose getArmPose(SoldierEntity mob, HumanoidArm arm) {
		return CustomArmPoses.getFirearmPose(mob, mob.getUsedItemHand(), mob.getItemHeldByArm(arm));
	}
}