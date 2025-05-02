package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class ThrowableProjectileRenderState extends LivingEntityRenderState {

	public float randomRotation = 0.0f;
	public float movementLengthSqr = 0.0f;
	public ItemStack stack;
	public BakedModel model;
}