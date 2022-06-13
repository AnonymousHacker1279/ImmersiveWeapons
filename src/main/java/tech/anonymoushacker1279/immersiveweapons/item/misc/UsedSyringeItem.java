package tech.anonymoushacker1279.immersiveweapons.item.misc;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class UsedSyringeItem extends Item {

	public static final DamageSource damageSource = new DamageSource("immersiveweapons.used_syringe");

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
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player playerIn,
	                                                       @NotNull InteractionHand handIn) {

		ItemStack itemInHand = playerIn.getItemInHand(handIn);

		int randomNumber = GeneralUtilities.getRandomNumber(0, 100);
		if (randomNumber <= 80) {
			playerIn.addEffect(new MobEffectInstance(MobEffects.POISON, 500, 0, false, true));
			if (randomNumber <= 30) {
				playerIn.hurt(damageSource, 8.0F);
			}
		}
		if (!playerIn.isCreative()) {
			itemInHand.shrink(1);
		}


		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}
}