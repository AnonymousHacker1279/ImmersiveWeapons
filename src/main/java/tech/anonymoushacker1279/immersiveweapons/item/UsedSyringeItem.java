package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class UsedSyringeItem extends Item {

	/**
	 * Constructor for UsedSyringeItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public UsedSyringeItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 *
	 * @param level    the <code>Level</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param handIn   the <code>InteractionHand</code> the player is using
	 * @return InteractionResultHolder extending ItemStack
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn,
	                                              InteractionHand handIn) {

		ItemStack itemInHand = playerIn.getItemInHand(handIn);

		int randomNumber = GeneralUtilities.getRandomNumber(0, 100);
		if (randomNumber <= 80) {
			playerIn.addEffect(new MobEffectInstance(MobEffects.POISON, 500, 0, false, true));
			if (randomNumber <= 30) {
				playerIn.hurt(IWDamageSources.USED_SYRINGE, 8.0F);
			}
		}
		if (!playerIn.isCreative()) {
			itemInHand.shrink(1);
		}


		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}
}