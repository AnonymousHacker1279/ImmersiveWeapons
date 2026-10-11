package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.joml.Quaternionf;

public class ThrowableProjectileRenderState extends LivingEntityRenderState {

	public final ItemStackRenderState stackRenderState = new ItemStackRenderState();
	public float movementLengthSqr = 0.0f;
	public final Quaternionf orientation = new Quaternionf();
	/// How upright the model is, from 0 (lying on its side) to 1.
	public float uprightness = 1.0f;
	/// 0 while in the air, up to 1 once lying on the ground.
	public float restBlend = 0.0f;
	public float modelCenterOffset = 0.0f;
}