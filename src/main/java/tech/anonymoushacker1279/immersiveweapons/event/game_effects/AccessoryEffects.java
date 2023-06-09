package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.AccessorySlot;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import static tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.getAccessory;

public class AccessoryEffects {

	public static void berserkersAmuletEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		// Take 20% more damage if the player has the amulet
		if (damagedEntity instanceof Player player) {
			AccessoryItem amulet = getAccessory(player, AccessorySlot.CHARM);
			if (amulet == ItemRegistry.BERSERKERS_AMULET.get()) {
				event.setAmount(event.getAmount() * 1.2f);
			}
		}

		// Deal 20% more damage if the player has the amulet
		if (event.getSource().getEntity() instanceof Player player) {
			AccessoryItem amulet = getAccessory(player, AccessorySlot.CHARM);
			if (amulet == ItemRegistry.BERSERKERS_AMULET.get()) {
				// Check if a projectile was used, and if so, only deal 10% more damage
				if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
					event.setAmount(event.getAmount() * 1.1f);
				} else if (event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
					event.setAmount(event.getAmount() * 1.2f);
				}
			}
		}
	}

	public static void hansBlessingEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		// Take 15% less damage if the player has the blessing
		if (damagedEntity instanceof Player player) {
			AccessoryItem blessing = getAccessory(player, AccessorySlot.SPIRIT);

			if (blessing == ItemRegistry.HANS_BLESSING.get()) {
				event.setAmount(event.getAmount() * 0.85f);
			}
		}
	}

	public static void blademasterEmblemEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		// Increase damage with swords by 10% and add a 30% chance to inflict Bleeding
		if (event.getSource().getEntity() instanceof Player player && event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
			AccessoryItem emblem = getAccessory(player, AccessorySlot.CHARM);

			if (emblem == ItemRegistry.BLADEMASTER_EMBLEM.get()) {
				if (event.getSource().getEntity() instanceof Player) {
					event.setAmount(event.getAmount() * 1.1f);

					// Roll for bleeding
					if (GeneralUtilities.getRandomNumber(0, 10) <= 2) {
						// If bleeding already exists, increase the duration
						if (damagedEntity.hasEffect(EffectRegistry.BLEEDING_EFFECT.get())) {
							MobEffectInstance effect = damagedEntity.getEffect(EffectRegistry.BLEEDING_EFFECT.get());
							damagedEntity.removeEffect(EffectRegistry.BLEEDING_EFFECT.get());
							damagedEntity.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT.get(), effect.getDuration() + 140, effect.getAmplifier()));
						} else {
							damagedEntity.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT.get(), 140, 0));
						}
					}
				}
			}
		}
	}

	public static void bloodySacrificeEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		if (damagedEntity instanceof Player player) {
			// Player takes 50% more damage if they used the item
			if (player.getPersistentData().getBoolean("used_curse_accessory_bloody_sacrifice")) {
				event.setAmount(event.getAmount() * 1.5f);
			}
		}

		if (event.getSource().getEntity() instanceof Player player) {
			// Player deals 10% less damage if they used the item
			if (player.getPersistentData().getBoolean("used_curse_accessory_bloody_sacrifice")) {
				event.setAmount(event.getAmount() * 0.9f);
			}
		}
	}
}