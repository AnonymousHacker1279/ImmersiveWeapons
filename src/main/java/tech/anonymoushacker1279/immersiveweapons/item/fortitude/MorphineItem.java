package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class MorphineItem extends Item {

	/**
	 * Constructor for MorphineItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public MorphineItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 *
	 * @param worldIn  the <code>World</code> the player is in
	 * @param playerIn the <code>Player</code> performing the action
	 * @param handIn   the <code>InteractionHand</code> the player is using
	 * @return InteractionResultHolder extending ItemStack
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn,
	                                              InteractionHand handIn) {

		ItemStack itemInHand = playerIn.getItemInHand(handIn);

		setEffects(playerIn);

		if (!playerIn.isCreative()) {
			itemInHand.shrink(1);
			playerIn.getInventory().add(new ItemStack(ItemRegistry.USED_SYRINGE.get()));
			playerIn.getCooldowns().addCooldown(this, 2400);
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, worldIn.isClientSide());
	}

	/**
	 * Runs when the player right-clicks an entity.
	 *
	 * @param stack    the <code>ItemStack</code> right-clicked with
	 * @param playerIn the <code>Player</code> performing the action
	 * @param entity   the <code>LivingEntity</code> being interacted with
	 * @param hand     the <code>InteractionHand</code> the player is using
	 * @return InteractionResult
	 */
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn,
	                                              LivingEntity entity, InteractionHand hand) {

		if (entity.level().isClientSide) {
			return InteractionResult.PASS;
		}

		setEffects(entity);

		if (!playerIn.isCreative()) {
			stack.shrink(1);
			playerIn.getInventory().add(new ItemStack(ItemRegistry.USED_SYRINGE.get()));
			playerIn.getCooldowns().addCooldown(this, 2400);
		}

		return InteractionResult.PASS;
	}

	private void setEffects(LivingEntity entity) {
		entity.addEffect(new MobEffectInstance(EffectRegistry.MORPHINE_EFFECT.get(), 1800, 0,
				false, true));
	}
}