package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.CommanderPedestalBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.state.GenericInventoryRenderState;

public class CommanderPedestalRenderer implements BlockEntityRenderer<CommanderPedestalBlockEntity, GenericInventoryRenderState> {

	private final ItemModelResolver itemModelResolver;

	public CommanderPedestalRenderer(BlockEntityRendererProvider.Context context) {
		itemModelResolver = context.itemModelResolver();
	}

	@Override
	public GenericInventoryRenderState createRenderState() {
		return new GenericInventoryRenderState(4);
	}

	@Override
	public void extractRenderState(CommanderPedestalBlockEntity blockEntity, GenericInventoryRenderState state, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
		GenericInventoryRenderState.extractBaseState(blockEntity, state, itemModelResolver, partialTick, cameraPos, crumblingOverlay);
	}

	@Override
	public void submit(GenericInventoryRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
		float time = Minecraft.getInstance().level.getGameTime() + state.partialTick;
		float speed = 0.05F; // Speed of rotation
		float radius = 0.25F; // Radius of the circle
		float height = 0.1F; // Height of the up and down movement

		for (int i = 0; i < state.items.length; i++) {
			ItemStackRenderState itemStackRenderState = state.items[i];
			if (itemStackRenderState != null) {
				stack.pushPose();

				// Calculate the position of the item
				double angle = 2.0 * Math.PI * i / state.items.length + speed * time;
				double x = Math.sin(angle) * radius;
				double y = Math.sin(time * speed) * height;
				double z = Math.cos(angle) * radius;

				// Translate to the calculated position
				stack.translate(0.5 + x, 1.35 + y, 0.5 + z);

				// Rotate the item to face outwards
				stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(angle) - 90));

				// Scale the item
				stack.scale(0.25F, 0.25F, 0.25F);

				itemStackRenderState.submit(stack, collector, 0, 0, 0);

				stack.popPose();
			}
		}
	}
}