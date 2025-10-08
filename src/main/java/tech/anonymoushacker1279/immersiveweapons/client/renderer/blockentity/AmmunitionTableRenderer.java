package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.AmmunitionTableBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AmmunitionTableBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.state.GenericDirectionalInventoryRenderState;

public class AmmunitionTableRenderer implements BlockEntityRenderer<AmmunitionTableBlockEntity, GenericDirectionalInventoryRenderState> {

	private final ItemModelResolver itemModelResolver;

	public AmmunitionTableRenderer(BlockEntityRendererProvider.Context context) {
		itemModelResolver = context.itemModelResolver();
	}

	@Override
	public GenericDirectionalInventoryRenderState createRenderState() {
		return new GenericDirectionalInventoryRenderState(6);
	}

	@Override
	public void extractRenderState(AmmunitionTableBlockEntity blockEntity, GenericDirectionalInventoryRenderState state, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTick, cameraPos, crumblingOverlay);

		state.facing = blockEntity.getBlockState().getValue(AmmunitionTableBlock.FACING);
		NonNullList<ItemStack> inventory = blockEntity.getInventory();
		for (int i = 0; i < inventory.size(); i++) {
			itemModelResolver.updateForTopItem(state.items[i], inventory.get(i), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
		}
	}

	@Override
	public void submit(GenericDirectionalInventoryRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
		for (ItemStackRenderState itemStackRenderState : state.items) {
			if (itemStackRenderState != null) {
				stack.pushPose();
				// Translate to the center of the block
				stack.translate(0.5D, 0.0D, 0.5D);

				// Rotate by direction
				switch (state.facing) {
					case EAST -> stack.mulPose(Axis.YP.rotationDegrees(270f));
					case SOUTH -> stack.mulPose(Axis.YP.rotationDegrees(180f));
					case WEST -> stack.mulPose(Axis.YP.rotationDegrees(90f));
					default -> stack.mulPose(Axis.YP.rotationDegrees(0f));
				}

				// Render the material inventory
				if (state.items[0] == itemStackRenderState) {
					// Top left
					stack.translate(-0.38D, 0.8D, 0.065D);
				} else if (state.items[1] == itemStackRenderState) {
					// Top right
					stack.translate(-0.295D, 0.8D, 0.065D);
				} else if (state.items[2] == itemStackRenderState) {
					// Center left
					stack.translate(-0.38D, 0.8D, 0.175D);
				} else if (state.items[3] == itemStackRenderState) {
					// Center right
					stack.translate(-0.295D, 0.8D, 0.175D);
				} else if (state.items[4] == itemStackRenderState) {
					// Bottom left
					stack.translate(-0.38D, 0.8D, 0.285D);
				} else if (state.items[5] == itemStackRenderState) {
					// Bottom right
					stack.translate(-0.295D, 0.8D, 0.285D);
				}

				if (state.items[6] != itemStackRenderState) {
					stack.scale(0.075f, 0.075f, 0.075f);
					stack.mulPose(Axis.XP.rotationDegrees(-25f));

					// Render the item
					itemStackRenderState.submit(stack, collector, 0, 0, 0);
				} else {
					stack.translate(0.225D, 0.775D, -0.05D); // Baseline position
					stack.scale(0.25f, 0.25f, 0.25f);
					float prevX = 0.0f;
					float prevZ = 0.0f;
					for (int i = 0; i < state.items.length; i++) {
						// Render the items in an Archimede spiral
						float theta = i * 0.5f;
						float x = (float) (theta * 0.0235f * Math.cos(theta));
						float z = (float) (theta * 0.0235f * Math.sin(theta));

						stack.translate(x - prevX, 0.00001D, z - prevZ);

						prevX = x;
						prevZ = z;

						// Render the item
						itemStackRenderState.submit(stack, collector, 0, 0, 0);
					}
				}

				stack.popPose();
			}
		}
	}
}