package com.anonymoushacker1279.immersiveweapons.item.misc;

import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class UsedSyringeItem extends Item {

	public static final DamageSource damageSource = new DamageSource("immersiveweapons.used_syringe");

	/**
	 * Constructor for UsedSyringeItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	public UsedSyringeItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 * @param worldIn the <code>World</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param handIn the <code>Hand</code> the player is using
	 * @return ActionResult extending ItemStack
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		HitResult rayTraceResult = Minecraft.getInstance().hitResult;
		if (rayTraceResult != null && rayTraceResult.getType() != Type.ENTITY) {
			int randomNumber = GeneralUtilities.getRandomNumber(0, 100);
			if (randomNumber <= 80) {
				playerIn.addEffect(new MobEffectInstance(MobEffects.POISON, 500, 0, false, true));
				if (randomNumber <= 30) {
					playerIn.hurt(damageSource, 8.0F);
				}
			}
			if (!playerIn.isCreative()) {
				itemstack.shrink(1);
			}
		}

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}