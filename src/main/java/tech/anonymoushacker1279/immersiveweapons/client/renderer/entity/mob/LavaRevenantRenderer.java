package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.HitboxRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.model.LavaRevenantModel;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers.LavaRevenantEyesLayer;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state.LavaRevenantRenderState;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantPart;

public class LavaRevenantRenderer extends MobRenderer<LavaRevenantEntity, LavaRevenantRenderState, LavaRevenantModel> {

	private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/entity/lava_revenant/lava_revenant.png");

	public LavaRevenantRenderer(EntityRendererProvider.Context context) {
		super(context, new LavaRevenantModel(context.bakeLayer(ModelLayers.PHANTOM)), 0.75F);
		addLayer(new LavaRevenantEyesLayer(this));
	}

	@Override
	public LavaRevenantRenderState createRenderState() {
		return new LavaRevenantRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(LavaRevenantRenderState state) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected void scale(LavaRevenantRenderState state, PoseStack poseStack) {
		float sizeModifier = 1.0F + 3.0F * (float) state.size;
		poseStack.scale(sizeModifier, sizeModifier, sizeModifier);
		poseStack.translate(0.0D, 1.45D, 0.1875D);
	}

	@Override
	protected void setupRotations(LavaRevenantRenderState renderState, PoseStack poseStack, float bodyRot, float scale) {
		super.setupRotations(renderState, poseStack, bodyRot, scale);
		poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));
	}

	@Override
	public void extractRenderState(LavaRevenantEntity entity, LavaRevenantRenderState reusedState, float partialTick) {
		super.extractRenderState(entity, reusedState, partialTick);

		reusedState.size = entity.getSize();
	}

	@Override
	protected void extractAdditionalHitboxes(LavaRevenantEntity entity, ImmutableList.Builder<HitboxRenderState> hitboxes, float delta) {
		super.extractAdditionalHitboxes(entity, hitboxes, delta);
		double dX = -Mth.lerp(delta, entity.xOld, entity.getX());
		double dY = -Mth.lerp(delta, entity.yOld, entity.getY());
		double dZ = -Mth.lerp(delta, entity.zOld, entity.getZ());

		for (LavaRevenantPart part : (LavaRevenantPart[]) entity.getParts()) {
			AABB box = part.getBoundingBox();
			HitboxRenderState hitboxrenderstate = new HitboxRenderState(
					box.minX - part.getX(),
					box.minY - part.getY(),
					box.minZ - part.getZ(),
					box.maxX - part.getX(),
					box.maxY - part.getY(),
					box.maxZ - part.getZ(),
					(float) (dX + Mth.lerp(delta, part.xOld, part.getX())),
					(float) (dY + Mth.lerp(delta, part.yOld, part.getY())),
					(float) (dZ + Mth.lerp(delta, part.zOld, part.getZ())),
					0.25F,
					1.0F,
					0.0F
			);

			hitboxes.add(hitboxrenderstate);
		}
	}
}