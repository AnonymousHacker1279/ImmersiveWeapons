package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.client.model.CustomArmPoses;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.SoldierEntity;

public class SoldierRenderer<T extends SoldierEntity> extends HumanoidMobRenderer<T, PlayerModel<T>> {

	private final ResourceLocation textureLocation;

	public SoldierRenderer(Context context, ResourceLocation textureLocation) {
		super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		addLayer(new HumanoidArmorLayer<>(this,
				new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
				context.getModelManager()));

		this.textureLocation = textureLocation;
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		PlayerModel<T> model = super.getModel();

		model.rightArmPose = CustomArmPoses.getFirearmPose(entity, entity.getUsedItemHand(), entity.getUseItem());

		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return textureLocation;
	}
}