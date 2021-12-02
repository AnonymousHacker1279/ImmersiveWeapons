package com.anonymoushacker1279.immersiveweapons.item.bottle;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class AlcoholBottleItem extends AbstractBottleItem {

	/**
	 * Constructor for AlcoholBottleItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public AlcoholBottleItem(Properties properties) {
		super(properties);
		properties.craftRemainder(asItem());
	}

	/**
	 * Additional code to run when the item is used.
	 *
	 * @param playerIn the <code>PlayerEntity</code> instance
	 */
	@Override
	protected void onUse(Player playerIn) {
		playerIn.addEffect(new MobEffectInstance(DeferredRegistryHandler.ALCOHOL_EFFECT.get(), 600, 0, false, true));
	}
}