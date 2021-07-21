package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BottleItems {

	public static class AlcoholBottleItem extends AbstractBottleItem {

		/**
		 * Constructor for AlcoholBottleItem.
		 * @param properties the <code>Properties</code> for the item
		 */
		public AlcoholBottleItem(Properties properties) {
			super(properties);
			properties.craftRemainder(getItem());
		}

		/**
		 * Additional code to run when the item is used.
		 * @param worldIn the <code>World</code> the player is in
		 * @param playerIn the <code>PlayerEntity</code> instance
		 * @param handIn the <code>Hand</code> the player is using
		 */
		@Override
		protected void onUse(World worldIn, PlayerEntity playerIn, Hand handIn) {
			playerIn.addEffect(new EffectInstance(Effects.CONFUSION, 600, 0, false, true));
			playerIn.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 600, 0, false, true));
			playerIn.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 600, 0, false, true));
		}
	}

	public static class WineBottleItem extends AbstractBottleItem {

		/**
		 * Constructor for WineBottleItem.
		 * @param properties the <code>Properties</code> for the item
		 */
		public WineBottleItem(Properties properties) {
			super(properties);
			properties.craftRemainder(getItem());
		}

		/**
		 * Additional code to run when the item is used.
		 * @param worldIn the <code>World</code> the player is in
		 * @param playerIn the <code>PlayerEntity</code> instance
		 * @param handIn the <code>Hand</code> the player is using
		 */
		@Override
		protected void onUse(World worldIn, PlayerEntity playerIn, Hand handIn) {
			playerIn.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 360, 0, false, true));
			playerIn.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 360, 0, false, true));
		}
	}
}