package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

public class BandageItem extends Item {

	/**
	 * Constructor for BandageItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public BandageItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 *
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> performing the action
	 * @param handIn the <code>Hand</code> the player is using
	 * @return InteractionResultHolder extending ItemStack
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand handIn) {

		ItemStack itemInHand = player.getItemInHand(handIn);

		setEffects(player);

		if (!player.isCreative()) {
			itemInHand.shrink(1);
			player.getCooldowns().addCooldown(this, 300);
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
	 * @return InteractionResult
	 */
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player,
	                                              LivingEntity entity, InteractionHand hand) {

		if (entity.level().isClientSide) {
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
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 0, false, true));
	}
}