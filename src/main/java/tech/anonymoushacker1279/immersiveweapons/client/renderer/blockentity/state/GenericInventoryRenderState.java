package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AbstractInventoryBlockEntity;

public class GenericInventoryRenderState extends BlockEntityRenderState {
	public ItemStackRenderState[] items;
	public float partialTick;

	public GenericInventoryRenderState(int size) {
		items = new ItemStackRenderState[size];
		for (int i = 0; i < size; i++) {
			items[i] = new ItemStackRenderState();
		}
	}

	public static void extractBaseState(AbstractInventoryBlockEntity blockEntity, GenericInventoryRenderState state, ItemModelResolver resolver, float partialTick, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
		BlockEntityRenderState.extractBase(blockEntity, state, crumblingOverlay);
		state.partialTick = partialTick;
		for (int i = 0; i < blockEntity.getInventory().size(); i++) {
			if (blockEntity.getInventory().get(i).isEmpty()) {
				state.items[i] = null;
			} else {
				resolver.updateForTopItem(state.items[i], blockEntity.getInventory().get(i), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
			}
		}
	}
}