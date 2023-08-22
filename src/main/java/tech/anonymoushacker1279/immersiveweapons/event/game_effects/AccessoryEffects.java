package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectBuilder.EffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;
import tech.anonymoushacker1279.immersiveweapons.util.IWCBBridge;

public class AccessoryEffects {

	/**
	 * Collect the value of the given effect from all {@link AccessoryItem}s in the player's inventory.
	 * <p>
	 * This will check to see if IWCB is loaded, and if so, defer to it for collecting effects as it will utilize Curios.
	 *
	 * @param type   the <code>EffectType</code> to collect
	 * @param player the <code>Player</code> to collect from
	 * @return the value of the effect
	 */
	public static double collectEffects(EffectType type, Player player) {
		double effectValue = 0;
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			effectValue = IWCBBridge.collectEffects(type, player);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				if (stack.getItem() instanceof AccessoryItem accessoryItem) {
					if (accessoryItem.isActive(player, stack)) {
						effectValue += handleEffectScaling(accessoryItem, type, player);
					}
				}
			}
		}

		// Clamp the value at a maximum of 100%
		return Mth.clamp(effectValue, 0, 1);
	}

	public static double handleEffectScaling(AccessoryItem accessoryItem, EffectType type, Player player) {
		if (accessoryItem.getEffectScalingType() == EffectScalingType.DEPTH_SCALING) {
			// Depth scaling increases the value inverse proportionally to the player's depth (y-level), starting at y<64
			// Note, this should continue to the bottom of the world, which may be lower than y=0

			double depth = player.getY();
			if (depth < 64) {
				double rawValue = accessoryItem.getEffects().getOrDefault(type, 0d);
				int worldFloor = player.level().getMinBuildHeight();

				// The scaling is inverse proportionally to the player's depth
				double depthScaling = Mth.clamp(Math.min(1.0, ((64 - depth) / (64 - worldFloor))) * 100, 0, 100);
				return rawValue * depthScaling;
			}
		} else if (accessoryItem.getEffectScalingType() == EffectScalingType.INSOMNIA_SCALING) {
			// Insomnia scaling increases the value proportionally to the player's insomnia level, starting after
			// one full day/night cycle without sleep (24000 ticks)

			int timeSinceRest;
			if (player instanceof ServerPlayer serverPlayer) {
				timeSinceRest = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
			} else {
				// If this is on the client, use the debug tracing data. The result here does not need to be accurate
				// since it will only ever be used in the debug overlay
				timeSinceRest = DebugTracingData.TICKS_SINCE_REST;
			}

			if (timeSinceRest > 24000) {
				double rawValue = accessoryItem.getEffects().getOrDefault(type, 0d);
				// Scaling should max out after 7 in-game days and nights (168000 ticks)
				double insomniaScaling = Mth.clamp(Math.min(1.0, ((timeSinceRest - 24000) / 144000.0)) * 100, 0, 100);
				return rawValue * insomniaScaling;
			}
		}

		// If no scaling is needed, just return the effect value
		return accessoryItem.getEffects().getOrDefault(type, 0d);
	}

	public static void damageResistanceEffects(LivingHurtEvent event, Player player) {
		// Get the total damage resistance from all items
		double damageReduction = collectEffects(EffectType.DAMAGE_RESISTANCE, player);

		// Apply the damage resistance
		event.setAmount((float) (event.getAmount() * (1 - damageReduction)));
	}

	public static void bleedResistanceEffects(LivingHurtEvent event, Player player) {
		if (event.getSource().is(IWDamageTypes.BLEEDING_KEY)) {
			// Get the total bleed resistance from all items
			double bleedResistance = collectEffects(EffectType.BLEED_RESISTANCE, player);

			// Apply the bleed resistance
			event.setAmount((float) (event.getAmount() * (1 - bleedResistance)));
		}
	}

	public static void bleedCancelEffects(LivingHurtEvent event, Player player) {
		if (event.getSource().is(IWDamageTypes.BLEEDING_KEY)) {
			// Get the total bleed cancel chance from all items
			double bleedCancelChance = collectEffects(EffectType.BLEED_CANCEL_CHANCE, player);

			// Roll for bleed cancel
			if (player.getRandom().nextFloat() <= bleedCancelChance) {
				player.removeEffect(EffectRegistry.BLEEDING_EFFECT.get());
			}
		}
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

	public static void generalDamageEffects(LivingHurtEvent event, Player player) {
		// Get the total general damage from all items
		double generalDamage = collectEffects(EffectType.GENERAL_DAMAGE, player);

		// Apply the general damage
		event.setAmount((float) (event.getAmount() * (1 + generalDamage)));
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

	public static void jonnysCurseEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		if (damagedEntity instanceof Player player) {
			// Player takes 200% more damage if they used the item
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse")) {
				event.setAmount(event.getAmount() * 3f);
			}
		}

		if (event.getSource().getEntity() instanceof Player player) {
			// Projectiles deal zero damage
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse") && event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
				event.setAmount(0);
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
}