package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BottleItems {

	public static class AlcoholBottleItem extends AbstractBottleItem {

		/**
		 * Constructor for AlcoholBottleItem.
		 * @param properties the <code>Properties</code> for the item
		 */
		public AlcoholBottleItem(Properties properties) {
			super(properties);
			properties.craftRemainder(asItem());
		}

		/**
		 * Additional code to run when the item is used.
		 * @param worldIn the <code>World</code> the player is in
		 * @param playerIn the <code>PlayerEntity</code> instance
		 * @param handIn the <code>Hand</code> the player is using
		 */
		@Override
		protected void onUse(Level worldIn, Player playerIn, InteractionHand handIn) {
			playerIn.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0, false, true));
			playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0, false, true));
			playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0, false, true));
		}
	}

	public static class WineBottleItem extends AbstractBottleItem {

		/**
		 * Constructor for WineBottleItem.
		 * @param properties the <code>Properties</code> for the item
		 */
		public WineBottleItem(Properties properties) {
			super(properties);
			properties.craftRemainder(asItem());
		}

		/**
		 * Additional code to run when the item is used.
		 * @param worldIn the <code>World</code> the player is in
		 * @param playerIn the <code>PlayerEntity</code> instance
		 * @param handIn the <code>Hand</code> the player is using
		 */
		@Override
		protected void onUse(Level worldIn, Player playerIn, InteractionHand handIn) {
			playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 360, 0, false, true));
			playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 360, 0, false, true));
		}
	}
}