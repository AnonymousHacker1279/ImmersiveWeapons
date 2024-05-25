package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);

		float randomNumber = level.getRandom().nextFloat();
		if (randomNumber <= 0.8f) {
			player.addEffect(new MobEffectInstance(MobEffects.POISON, 500, 0, false, true));
			if (randomNumber <= 0.3f) {
				player.hurt(IWDamageSources.usedSyringe(level.registryAccess()), 8.0F);
			}
		}

		if (!player.isCreative()) {
			itemInHand.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}
}