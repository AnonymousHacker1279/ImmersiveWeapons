package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PainkillerItem extends Item {

	/**
	 * Constructor for PainkillerItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	public PainkillerItem(Properties properties) {
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
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, true));
		if (!playerIn.isCreative()) {
			itemstack.shrink(1);
			playerIn.getCooldowns().addCooldown(this, 2400);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}