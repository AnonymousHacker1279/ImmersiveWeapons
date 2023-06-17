package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

public class FirstAidKitItem extends Item {

	/**
	 * Constructor for FirstAidKitItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public FirstAidKitItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 *
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> performing the action
	 * @param hand   the <code>InteractionHand</code> the player is using
	 * @return InteractionResultHolder extending ItemStack
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		ItemStack itemInHand = player.getItemInHand(hand);
		if (player.getMaxHealth() - player.getHealth() <= player.getMaxHealth() / 2) { // Only use if at or less than half health
			if (level.isClientSide) {
				player.displayClientMessage(Component.translatable("immersiveweapons.item.first_aid_kit")
						.withStyle(ChatFormatting.RED), true);
			}
			return InteractionResultHolder.pass(itemInHand);
		}

		setEffects(player);

		if (!player.isCreative()) {
			itemInHand.shrink(1);
			player.getCooldowns().addCooldown(this, 400);
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}

	/**
	 * Runs when the player right-clicks an entity.
	 *
	 * @param stack  the <code>ItemStack</code> right-clicked with
	 * @param player the <code>Player</code> performing the action
	 * @param entity the <code>LivingEntity</code> being interacted with
	 * @param hand   the <code>Hand</code> the player is using
	 * @return ActionResultType
	 */
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player,
	                                              LivingEntity entity, InteractionHand hand) {

		if (entity.level().isClientSide) {
			return InteractionResult.PASS;
		}

		if (entity.getMaxHealth() - entity.getHealth() <= entity.getMaxHealth() / 2) { // Only use if at or less than half health
			return InteractionResult.PASS;
		}

		setEffects(entity);

		if (!player.isCreative()) {
			stack.shrink(1);
		}

		return InteractionResult.PASS;
	}

	private void setEffects(LivingEntity entity) {
		if (entity.hasEffect(EffectRegistry.BLEEDING_EFFECT.get())) {
			entity.removeEffect(EffectRegistry.BLEEDING_EFFECT.get());
		}
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 1, false, true));
		entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, true));
	}
}