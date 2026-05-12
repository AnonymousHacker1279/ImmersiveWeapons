package tech.anonymoushacker1279.immersiveweapons.item.potion;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

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
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		Player player = livingEntity instanceof Player ? (Player) livingEntity : null;
		if (player instanceof ServerPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
		}

		if (level instanceof ServerLevel serverLevel) {
			for (MobEffectInstance effectInstance : getEffects()) {
				if (effectInstance.getEffect().value().isInstantenous()) {
					effectInstance.getEffect().value().applyInstantenousEffect(serverLevel, player, player, livingEntity, effectInstance.getAmplifier(), 1.0D);
				} else {
					livingEntity.addEffect(new MobEffectInstance(effectInstance));
				}
			}
		}

		if (player != null) {
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
		}

		if (player == null || !player.getAbilities().instabuild) {
			if (stack.isEmpty()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (player != null) {
				player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		livingEntity.gameEvent(GameEvent.DRINK);
		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 32;
	}

	/**
	 * Get the use animation.
	 *
	 * @param stack the <code>ItemStack</code> instance
	 * @return UseAction
	 */
	@Override
	public ItemUseAnimation getUseAnimation(ItemStack stack) {
		return ItemUseAnimation.DRINK;
	}

	protected List<MobEffectInstance> getEffects() {
		return new ArrayList<>(0);
	}
}