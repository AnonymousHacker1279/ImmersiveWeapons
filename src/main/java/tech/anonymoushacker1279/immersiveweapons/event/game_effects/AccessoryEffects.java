package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class AccessoryEffects {

	public static final ResourceKey<DamageType> BLEEDING = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "bleeding"));

	public static void damageResistanceEffects(LivingHurtEvent event, Player player) {
		// Get the total damage resistance from all items
		double damageReduction = AccessoryManager.collectEffects(EffectType.DAMAGE_RESISTANCE, player);

		// Apply the damage resistance
		event.setAmount((float) (event.getAmount() * (1 - damageReduction)));
	}

	public static void bleedResistanceEffects(LivingHurtEvent event, Player player) {
		if (event.getSource().is(BLEEDING)) {
			// Get the total bleed resistance from all items
			double bleedResistance = AccessoryManager.collectEffects(EffectType.BLEED_RESISTANCE, player);

			// Apply the bleed resistance
			event.setAmount((float) (event.getAmount() * (1 - bleedResistance)));
		}
	}

	public static void bleedCancelEffects(LivingHurtEvent event, Player player) {
		if (event.getSource().is(BLEEDING)) {
			// Get the total bleed cancel chance from all items
			double bleedCancelChance = AccessoryManager.collectEffects(EffectType.BLEED_CANCEL_CHANCE, player);

			// Roll for bleed cancel
			if (player.getRandom().nextFloat() <= bleedCancelChance) {
				player.removeEffect(EffectRegistry.BLEEDING_EFFECT.get());
			}
		}
	}

	public static void meleeDamageEffects(LivingHurtEvent event, Player player) {
		// Get the total melee damage from all items
		double meleeDamage = AccessoryManager.collectEffects(EffectType.MELEE_DAMAGE, player);

		if (event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
			// Apply the melee damage
			event.setAmount((float) (event.getAmount() * (1 + meleeDamage)));
		}
	}

	public static void projectileDamageEffects(LivingHurtEvent event, Player player) {
		// Get the total projectile damage from all items
		double projectileDamage = AccessoryManager.collectEffects(EffectType.PROJECTILE_DAMAGE, player);

		if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
			// Apply the projectile damage
			event.setAmount((float) (event.getAmount() * (1 + projectileDamage)));
		}
	}

	public static void generalDamageEffects(LivingHurtEvent event, Player player) {
		// Get the total general damage from all items
		double generalDamage = AccessoryManager.collectEffects(EffectType.GENERAL_DAMAGE, player);

		// Apply the general damage
		event.setAmount((float) (event.getAmount() * (1 + generalDamage)));
	}

	public static void meleeBleedChanceEffects(LivingHurtEvent event, Player player, LivingEntity damagedEntity) {
		// Get the total bleed chance from all items
		double bleedChance = AccessoryManager.collectEffects(EffectType.MELEE_BLEED_CHANCE, player);

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

	public static void meleeKnockbackEffects(LivingKnockBackEvent event, Player player) {
		// Get the total melee knockback from all items
		double meleeKnockback = AccessoryManager.collectEffects(EffectType.MELEE_KNOCKBACK, player);

		LivingEntity entity = event.getEntity();

		if (entity.getLastDamageSource() != null && entity.getLastDamageSource().is(DamageTypes.PLAYER_ATTACK)) {
			// Apply the melee knockback
			event.setStrength((float) (event.getStrength() * (1 + meleeKnockback)));
		}
	}

	public static void meleeCritDamageBonusEffects(CriticalHitEvent event, Player player) {
		// Get the total melee critical damage from all items
		double meleeCritDamageBonus = AccessoryManager.collectEffects(EffectType.MELEE_CRIT_DAMAGE_BONUS, player);

		// Apply the melee critical damage
		event.setDamageModifier((float) (event.getDamageModifier() + meleeCritDamageBonus));
	}

	public static void meleeCritChanceEffects(CriticalHitEvent event, Player player) {
		// Get the total melee critical chance from all items
		double meleeCritChance = AccessoryManager.collectEffects(EffectType.MELEE_CRIT_CHANCE, player);

		// Roll for critical hit
		if (player.getRandom().nextFloat() <= meleeCritChance) {
			event.setResult(CriticalHitEvent.Result.ALLOW);
		}
	}

	public static void generalWitherChanceEffects(Player player, LivingEntity damagedEntity) {
		// Get the total general withering chance from all items
		double witherChance = AccessoryManager.collectEffects(EffectType.GENERAL_WITHER_CHANCE, player);

		// Roll for wither
		if (player.getRandom().nextFloat() <= witherChance) {
			// If wither already exists, increase the duration
			damagedEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 140, 0));
		}
	}

	public static void experienceEffects(LivingExperienceDropEvent event, Player player) {
		// Get the total experience boost from all items
		double experienceBoost = AccessoryManager.collectEffects(EffectType.EXPERIENCE_MODIFIER, player);

		// Apply the experience boost
		event.setDroppedExperience((int) (event.getDroppedExperience() * (1 + experienceBoost)));
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

	public static void jonnysCurseEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
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
		if (AccessoryItem.isAccessoryActive(player, ItemRegistry.CELESTIAL_SPIRIT.get()) && player.getRandom().nextFloat() <= 0.15f) {
			if (player != sourceEntity) {
				MeteorEntity.create(player.level(), player, null, player.blockPosition(), sourceEntity);
			}
		}
	}

	public static void holyMantleEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		// Completely negates damage, incurs 30s cooldown
		if (damagedEntity instanceof Player player) {
			if (AccessoryItem.isAccessoryActive(player, ItemRegistry.HOLY_MANTLE.get())) {
				if (!player.getCooldowns().isOnCooldown(ItemRegistry.HOLY_MANTLE.get())) {
					player.getCooldowns().addCooldown(ItemRegistry.HOLY_MANTLE.get(), 600);
					event.setCanceled(true);
				}
			}
		}
	}
}