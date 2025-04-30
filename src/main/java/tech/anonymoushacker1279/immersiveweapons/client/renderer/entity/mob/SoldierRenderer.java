package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.SoldierEntity;

public class SoldierRenderer extends HumanoidMobRenderer<SoldierEntity, PlayerRenderState, PlayerModel> {

	private final ResourceLocation textureLocation;

	public SoldierRenderer(Context context, ResourceLocation textureLocation) {
		super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this,
				new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
				context.getEquipmentRenderer()));

		this.textureLocation = textureLocation;
	}

	@Override
	public PlayerRenderState createRenderState() {
		return new PlayerRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(PlayerRenderState entity) {
		return textureLocation;
	}

	@Override
	public void render(PlayerRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		PlayerModel model = super.getModel();

		// TODO: fix arm poses
		// model.rightArmPose = CustomArmPoses.getFirearmPose(renderState, renderState.getUsedItemHand(), renderState.getUseItem());

		super.render(renderState, poseStack, bufferSource, packedLight);
	}
}