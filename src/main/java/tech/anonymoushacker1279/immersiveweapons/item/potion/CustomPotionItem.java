package tech.anonymoushacker1279.immersiveweapons.item.potion;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomPotionItem extends Item {

	/**
	 * Constructor for AbstractBottleItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	CustomPotionItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the item is used.
	 *
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> instance
	 * @param hand   the <code>InteractionHand</code> the player is using
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
		Player player = pEntityLiving instanceof Player ? (Player) pEntityLiving : null;
		if (player instanceof ServerPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, pStack);
		}

		if (!pLevel.isClientSide) {
			for (MobEffectInstance effectInstance : getEffects()) {
				if (effectInstance.getEffect().isInstantenous()) {
					effectInstance.getEffect().applyInstantenousEffect(player, player, pEntityLiving, effectInstance.getAmplifier(), 1.0D);
				} else {
					pEntityLiving.addEffect(new MobEffectInstance(effectInstance));
				}
			}
		}

		if (player != null) {
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				pStack.shrink(1);
			}
		}

		if (player == null || !player.getAbilities().instabuild) {
			if (pStack.isEmpty()) {
				return getContainerItem(pStack);
			}

			if (player != null) {
				player.getInventory().add(getContainerItem(pStack));
			}
		}

		pLevel.gameEvent(pEntityLiving, GameEvent.DRINKING_FINISH, pEntityLiving.eyeBlockPosition());
		return pStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getUseDuration(@NotNull ItemStack pStack) {
		return 32;
	}

	/**
	 * Check if the item has a container item.
	 *
	 * @param stack the <code>ItemStack</code> to be checked
	 * @return boolean
	 */
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	/**
	 * Get the container item.
	 *
	 * @param stack the <code>ItemStack</code> instance
	 * @return ItemStack
	 */
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return new ItemStack(Items.GLASS_BOTTLE);
	}

	/**
	 * Get the use animation.
	 *
	 * @param stack the <code>ItemStack</code> instance
	 * @return UseAction
	 */
	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.DRINK;
	}

	protected List<MobEffectInstance> getEffects() {
		return new ArrayList<>(0);
	}
}