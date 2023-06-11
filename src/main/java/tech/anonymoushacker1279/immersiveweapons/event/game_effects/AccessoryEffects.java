package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;

public class AccessoryEffects {

	/**
	 * Collect the value of the given effect from all {@link AccessoryItem}s in the player's inventory.
	 *
	 * @param type   the <code>EffectType</code> to collect
	 * @param player the <code>Player</code> to collect from
	 * @return the value of the effect
	 */
	public static double collectEffects(EffectType type, Player player) {
		double value = 0;

		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			if (player.getInventory().getItem(i).getItem() instanceof AccessoryItem accessoryItem) {
				if (accessoryItem.isActive(player)) {
					value += accessoryItem.getEffects().getOrDefault(type, 0d);
				}
			}
		}

		return value;
	}

	public static void damageResistanceEffects(LivingHurtEvent event, Player player) {
		// Get the total damage resistance from all items
		double damageReduction = collectEffects(EffectType.DAMAGE_RESISTANCE, player);

		// Apply the damage resistance
		event.setAmount((float) (event.getAmount() * (1 - damageReduction)));
	}

	public static void meleeDamageEffects(LivingHurtEvent event, Player player) {
		// Get the total melee damage from all items
		double meleeDamage = collectEffects(EffectType.MELEE_DAMAGE, player);

		if (event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
			// Apply the melee damage
			event.setAmount((float) (event.getAmount() * (1 + meleeDamage)));
		}
	}

	public static void projectileDamageEffects(LivingHurtEvent event, Player player) {
		// Get the total projectile damage from all items
		double projectileDamage = collectEffects(EffectType.PROJECTILE_DAMAGE, player);

		if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
			// Apply the projectile damage
			event.setAmount((float) (event.getAmount() * (1 + projectileDamage)));
		}
	}

	public static void meleeBleedChanceEffects(LivingHurtEvent event, Player player, LivingEntity damagedEntity) {
		// Get the total bleed chance from all items
		double bleedChance = collectEffects(EffectType.MELEE_BLEED_CHANCE, player);

		// Roll for bleeding
		if (event.getSource().is(DamageTypes.PLAYER_ATTACK) && player.getRandom().nextFloat() <= bleedChance) {
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

	public static void knockbackResistanceEffects(LivingKnockBackEvent event, Player player) {
		// Get the total knockback resistance from all items
		double knockbackResistance = collectEffects(EffectType.KNOCKBACK_RESISTANCE, player);

		// Apply the knockback resistance
		event.setStrength((float) (event.getStrength() * (1 - knockbackResistance)));

		if (event.getStrength() <= 0.0f) {
			event.setCanceled(true);
		}
	}

	public static void meleeKnockbackEffects(LivingKnockBackEvent event, Player player) {
		// Get the total melee knockback from all items
		double meleeKnockback = collectEffects(EffectType.MELEE_KNOCKBACK, player);

		LivingEntity entity = event.getEntity();

		if (entity.getLastDamageSource() != null && entity.getLastDamageSource().is(DamageTypes.PLAYER_ATTACK)) {
			// Apply the melee knockback
			event.setStrength((float) (event.getStrength() * (1 + meleeKnockback)));
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