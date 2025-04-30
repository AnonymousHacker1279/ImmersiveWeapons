package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import tech.anonymoushacker1279.immersiveweapons.api.events.ComputeEnchantedLootBonusEvent;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.init.AccessoryEffectTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class AccessoryEffects {

	public static void damageResistanceEffects(LivingIncomingDamageEvent event, Player player) {
		// Get the total damage resistance from all items
		double damageReduction = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), player);

		// Apply the damage resistance
		event.setAmount((float) (event.getAmount() * (1 - damageReduction)));
	}

	public static void bleedResistanceEffects(LivingIncomingDamageEvent event, Player player) {
		if (event.getSource().is(IWDamageSources.BLEEDING_KEY)) {
			// Get the total bleed resistance from all items
			double bleedResistance = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.BLEED_RESISTANCE.get(), player);

			// Apply the bleed resistance
			event.setAmount((float) (event.getAmount() * (1 - bleedResistance)));
		}
	}

	public static void bleedCancelEffects(LivingIncomingDamageEvent event, Player player) {
		if (event.getSource().is(IWDamageSources.BLEEDING_KEY)) {
			// Get the total bleed cancel chance from all items
			double bleedCancelChance = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.BLEED_CANCEL_CHANCE.get(), player);

			// Roll for bleed cancel
			if (player.getRandom().nextFloat() <= bleedCancelChance) {
				player.removeEffect(EffectRegistry.BLEEDING_EFFECT);
			}
		}
	}

	public static void meleeDamageEffects(LivingIncomingDamageEvent event, Player player) {
		// Get the total melee damage from all items
		double meleeDamage = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.MELEE_DAMAGE.get(), player);

		if (event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
			// Apply the melee damage
			event.setAmount((float) (event.getAmount() * (1 + meleeDamage)));
		}
	}

	public static void projectileDamageEffects(LivingIncomingDamageEvent event, Player player) {
		// Get the total projectile damage from all items
		double projectileDamage = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.PROJECTILE_DAMAGE.get(), player);

		if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
			// Apply the projectile damage
			event.setAmount((float) (event.getAmount() * (1 + projectileDamage)));
		}
	}

	public static void generalDamageEffects(LivingIncomingDamageEvent event, Player player) {
		// Get the total general damage from all items
		double generalDamage = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), player);

		// Apply the general damage
		event.setAmount((float) (event.getAmount() * (1 + generalDamage)));
	}

	public static void meleeBleedChanceEffects(LivingIncomingDamageEvent event, Player player, LivingEntity damagedEntity) {
		// Get the total bleed chance from all items
		double bleedChance = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.MELEE_BLEED_CHANCE.get(), player);

		// Roll for bleeding
		if (event.getSource().is(DamageTypes.PLAYER_ATTACK) && player.getRandom().nextFloat() <= bleedChance) {
			// If bleeding already exists, increase the duration
			if (damagedEntity.hasEffect(EffectRegistry.BLEEDING_EFFECT)) {
				MobEffectInstance effect = damagedEntity.getEffect(EffectRegistry.BLEEDING_EFFECT);
				damagedEntity.removeEffect(EffectRegistry.BLEEDING_EFFECT);
				damagedEntity.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT, effect.getDuration() + 140, effect.getAmplifier()));
			} else {
				damagedEntity.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT, 140, 0));
			}
		}
	}

	public static void meleeKnockbackEffects(LivingKnockBackEvent event, Player player) {
		// Get the total melee knockback from all items
		double meleeKnockback = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.MELEE_KNOCKBACK.get(), player);

		LivingEntity entity = event.getEntity();

		if (entity.getLastDamageSource() != null && entity.getLastDamageSource().is(DamageTypes.PLAYER_ATTACK)) {
			// Apply the melee knockback
			event.setStrength((float) (event.getStrength() * (1 + meleeKnockback)));
		}
	}

	public static void meleeCritDamageBonusEffects(CriticalHitEvent event, Player player) {
		// Get the total melee critical damage from all items
		double meleeCritDamageBonus = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.MELEE_CRIT_DAMAGE_BONUS.get(), player);

		// Apply the melee critical damage
		event.setDamageMultiplier((float) (event.getDamageMultiplier() + meleeCritDamageBonus));
	}

	public static void meleeCritChanceEffects(CriticalHitEvent event, Player player) {
		// Get the total melee critical chance from all items
		double meleeCritChance = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.MELEE_CRIT_CHANCE.get(), player);

		// Roll for critical hit
		if (player.getRandom().nextFloat() <= meleeCritChance) {
			event.setCriticalHit(true);
		}
	}

	public static void generalWitherChanceEffects(Player player, LivingEntity damagedEntity) {
		// Get the total general withering chance from all items
		double witherChance = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.GENERAL_WITHER_CHANCE.get(), player);

		// Roll for wither
		if (player.getRandom().nextFloat() <= witherChance) {
			// If wither already exists, increase the duration
			damagedEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 140, 0));
		}
	}

	public static void experienceEffects(LivingExperienceDropEvent event, Player player) {
		// Get the total experience boost from all items
		double experienceBoost = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.EXPERIENCE_MODIFIER.get(), player);

		// Apply the experience boost
		event.setDroppedExperience((int) (event.getDroppedExperience() * (1 + experienceBoost)));
	}

	public static void bloodySacrificeEffect(LivingIncomingDamageEvent event, LivingEntity damagedEntity) {
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

	public static void jonnysCurseEffect(LivingIncomingDamageEvent event, LivingEntity damagedEntity) {
		if (damagedEntity instanceof Player player) {
			// Player takes 200% more damage if they used the item
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse")) {
				if (GeneralUtilities.notJonny(player.getUUID())) {
					event.setAmount(event.getAmount() * 3f);
				} else {
					event.setAmount(event.getAmount() / 3f);
				}
			}
		}

		if (event.getSource().getEntity() instanceof Player player) {
			// Projectiles deal zero damage
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse") && event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
				if (GeneralUtilities.notJonny(player.getUUID())) {
					event.setAmount(0f);
				} else {
					event.setAmount(event.getAmount() * 2f);
				}
			}
		}
	}

	public static void celestialSpiritEffect(Player player, LivingEntity sourceEntity) {
		// 15% chance to summon meteor
		if (Accessory.isAccessoryActive(player, ItemRegistry.CELESTIAL_SPIRIT.get()) && player.getRandom().nextFloat() <= 0.15f) {
			if (player != sourceEntity) {
				MeteorEntity.create(player.level(), player, null, player.blockPosition(), sourceEntity);
			}
		}
	}

	public static void holyMantleEffect(LivingIncomingDamageEvent event, LivingEntity damagedEntity) {
		// Completely negates damage, incurs 30s cooldown
		if (damagedEntity instanceof Player player) {
			ItemStack stack = ItemRegistry.HOLY_MANTLE.get().getDefaultInstance();
			if (!player.getCooldowns().isOnCooldown(stack)) {
				player.getCooldowns().addCooldown(stack, 600);
				event.setCanceled(true);
			}
		}
	}

	public static void sonicBoomResistanceEffects(LivingIncomingDamageEvent event, Player player) {
		if (event.getSource().is(DamageTypes.SONIC_BOOM)) {
			// Get the total sonic boom resistance from all items
			double sonicBoomResistance = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.SONIC_BOOM_RESISTANCE.get(), player);

			// Apply the sonic boom resistance
			event.setAmount((float) (event.getAmount() * (1 - sonicBoomResistance)));
		}
	}

	public static void lootingEffects(ComputeEnchantedLootBonusEvent event, Player player) {
		// Get the total looting level from all items
		double lootingLevel = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.LOOTING_LEVEL.get(), player);

		// Apply the looting level
		event.setEnchantmentLevel(event.getEnchantmentLevel() + (int) lootingLevel);
	}
}