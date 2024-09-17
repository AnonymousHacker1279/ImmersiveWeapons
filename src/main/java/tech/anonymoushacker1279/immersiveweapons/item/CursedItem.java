package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;

import java.util.ArrayList;
import java.util.List;

public class CursedItem extends Item {

	private final String name;
	public static float CURSE_EFFECT_FADE = 1.0f;
	final DataComponentType<Boolean> AT_MAX_CHARGE = DataComponentTypeRegistry.AT_MAX_CHARGE.get();

	/**
	 * Cursed items cannot be removed once used. Their effects are permanent in survival mode, even persisting through death.
	 * They are "charged" and require 100 entity kills while the item is in the inventory to be used.
	 * <p>
	 * The only way to clear the effects of a cursed item is to use a {@link CurseCleaningSoapItem} item, which is not
	 * obtainable in survival mode.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param name       the name of the item, used for setting tags on the player
	 */
	public CursedItem(Properties properties, String name) {
		super(properties);

		this.name = name;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return 100;
	}

	/**
	 * Get a list of cursed accessories in the player's inventory.
	 */
	public static List<ItemStack> getCurses(Player player) {
		List<ItemStack> curses = new ArrayList<>(5);
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			if (player.getInventory().getItem(i).getItem() instanceof CursedItem) {
				curses.add(player.getInventory().getItem(i));
			}
		}

		return curses;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 20;
	}

	@Override
	public boolean useOnRelease(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
		super.onUseTick(level, livingEntity, stack, remainingUseDuration);

		if (livingEntity instanceof Player player) {
			if (level.isClientSide) {
				if (player.tickCount % 8 == 0) {
					player.displayClientMessage(Component.translatable("immersiveweapons.item.%s.using".formatted(name))
							.withStyle(ChatFormatting.RED), true);
				}

				// Handle the fade effect
				if (CURSE_EFFECT_FADE > 0.0f) {
					CURSE_EFFECT_FADE -= 0.05f;
				}
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);

		if (stack.get(AT_MAX_CHARGE) == null) {
			stack.set(AT_MAX_CHARGE, false);
			stack.setDamageValue(100);
		}

		if (entity instanceof Player player) {
			if (!player.isUsingItem()) {
				CURSE_EFFECT_FADE = 1.0f;
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		ItemStack stack = player.getItemInHand(hand);

		if (getDamage(stack) == 0) {
			if (player.getPersistentData().getBoolean("used_curse_accessory_" + name)) {
				if (player.level().isClientSide) {
					player.displayClientMessage(Component.translatable("immersiveweapons.item.%s.used".formatted(name))
							.withStyle(ChatFormatting.RED), true);
				}

				CURSE_EFFECT_FADE = 1.0f;

				return InteractionResultHolder.fail(stack);
			} else {
				player.startUsingItem(hand);
				return InteractionResultHolder.success(stack);
			}
		} else {
			if (player.level().isClientSide) {
				player.displayClientMessage(Component.translatable("immersiveweapons.item.%s.not_enough_charge".formatted(name))
						.withStyle(ChatFormatting.RED), true);
			}
		}

		return InteractionResultHolder.fail(stack);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
		if (livingEntity instanceof Player player && getDamage(stack) == 0) {
			if (timeCharged > 0) {
				if (level.isClientSide) {
					player.displayClientMessage(Component.translatable("immersiveweapons.item.%s.canceled".formatted(name))
							.withStyle(ChatFormatting.YELLOW), true);
				}
				return;
			}

			// Add a tag to the player to indicate that they have used the item
			// This is used to prevent the item from being used again
			player.getPersistentData().putBoolean("used_curse_accessory_" + name, true);

			if (level.isClientSide) {
				player.displayClientMessage(Component.translatable("immersiveweapons.item.%s.used".formatted(name))
						.withStyle(ChatFormatting.RED), true);

				player.playSound(
						SoundEvents.LIGHTNING_BOLT_THUNDER,
						0.75f,
						0.75f
				);

				CURSE_EFFECT_FADE = 1.0f;
			}

			if (!player.isCreative()) {
				stack.hurtAndBreak(100, player, EquipmentSlot.MAINHAND);
			}
		}
	}
}