package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.event.ForgeEventSubscriber;

public class CurseCleaningSoapItem extends Item {

	public CurseCleaningSoapItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
		// Get all curses in the player's persistent data
		if (level.isClientSide) {
			if (player.getPersistentData().isEmpty()) {
				player.displayClientMessage(Component.translatable("immersiveweapons.item.curse_cleaning_soap.not_cursed")
						.withStyle(ChatFormatting.YELLOW), true);
			}

			handleData(player);
		}

		if (!level.isClientSide) {
			// Regex search for "used_curse_accessory_" in persistent data
			boolean wasCursed = handleData(player);

			// Clear effects
			player.removeAllEffects();

			// Clear Jonny's Curse modifiers
			AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
			if (attributeInstance != null && attributeInstance.hasModifier(ForgeEventSubscriber.JONNYS_CURSE_SPEED_MODIFIER.id())) {
				attributeInstance.removeModifier(ForgeEventSubscriber.JONNYS_CURSE_SPEED_MODIFIER);
			}

			if (wasCursed) {
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	private boolean handleData(Player player) {
		int initialSize = player.getPersistentData().keySet().size();
		player.getPersistentData().keySet().removeIf(
				key -> key.matches("used_curse_accessory_.*")
		);

		if (initialSize != player.getPersistentData().keySet().size()) {
			player.displayClientMessage(Component.translatable("immersiveweapons.item.curse_cleaning_soap.cleaned")
					.withStyle(ChatFormatting.GREEN), true);

			return true;
		} else {
			player.displayClientMessage(Component.translatable("immersiveweapons.item.curse_cleaning_soap.not_cursed")
					.withStyle(ChatFormatting.YELLOW), true);

			return false;
		}
	}
}