package com.anonymoushacker1279.immersiveweapons.item.bottle;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class WineBottleItem extends AbstractBottleItem {

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
	 * @param playerIn the <code>PlayerEntity</code> instance
	 */
	@Override
	protected void onUse(Player playerIn) {
		playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 360, 0, false, true));
		playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 360, 0, false, true));
	}
}