package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PainkillerItem extends Item {

	/**
	 * Constructor for PainkillerItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public PainkillerItem(Properties properties) {
		super(properties);
	}


	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 1200, 0, false, true));

		if (!player.isCreative()) {
			itemInHand.shrink(1);
			player.getCooldowns().addCooldown(itemInHand, 2400);
		}

		return InteractionResult.SUCCESS;
	}
}