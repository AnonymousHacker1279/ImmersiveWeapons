package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class ThrowableProjectileRenderState extends LivingEntityRenderState {

	public final ItemStackRenderState stackRenderState = new ItemStackRenderState();
	public float movementLengthSqr = 0.0f;
}