package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.state;

import net.minecraft.core.Direction;

public class GenericDirectionalInventoryRenderState extends GenericInventoryRenderState {
	public Direction facing = Direction.NORTH;

	public GenericDirectionalInventoryRenderState(int size) {
		super(size);
	}
}