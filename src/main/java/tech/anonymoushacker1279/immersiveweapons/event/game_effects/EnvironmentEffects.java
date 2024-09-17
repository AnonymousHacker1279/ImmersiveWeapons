package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncPlayerDataPayload;

import java.util.Objects;

public class EnvironmentEffects {

	// Handle stuff for the celestial protection effect
	public static void celestialProtectionEffect(LivingIncomingDamageEvent event, LivingEntity damagedEntity) {
		if (damagedEntity.hasEffect(EffectRegistry.CELESTIAL_PROTECTION_EFFECT)) {
			float damage = event.getAmount();
			float celestialProtectionChanceForNoDamage = 0.0f;

			if (!damagedEntity.getPersistentData().isEmpty()) {
				celestialProtectionChanceForNoDamage = damagedEntity.getPersistentData().getFloat("celestialProtectionChanceForNoDamage");
			}

			// Check if the damage should be neutralized
			if (celestialProtectionChanceForNoDamage > 0.0f) {
				if (damagedEntity.getRandom().nextFloat() <= celestialProtectionChanceForNoDamage) {
					event.setCanceled(true);
					celestialProtectionChanceForNoDamage = 0.0f;
				}
			}

			// Increase the chance that the next damage taken will be neutralized
			if (ArmorUtils.isWearingAstralArmor(damagedEntity)) {
				celestialProtectionChanceForNoDamage += damage * 0.03f; // Astral armor has a 3% charge rate
			} else {
				celestialProtectionChanceForNoDamage += damage * 0.01f; // Other armor has a 1% charge rate
			}

			// This effect grants a 5% damage reduction to all damage taken, unless they rolled for no damage
			damage = damage * 0.95f;

			// Update the entity persistent data
			damagedEntity.getPersistentData().putFloat("celestialProtectionChanceForNoDamage", celestialProtectionChanceForNoDamage);

			// Re-sync persistent data to client for debug tracing
			if (damagedEntity instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new SyncPlayerDataPayload(serverPlayer.getPersistentData(), serverPlayer.getUUID()));
			}

			event.setAmount(damage);
		}
	}

	// Handle stuff for the damage vulnerability effect
	public static void damageVulnerabilityEffect(LivingIncomingDamageEvent event, LivingEntity damagedEntity) {
		if (damagedEntity.hasEffect(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT)) {
			int level = Objects.requireNonNull(damagedEntity.getEffect(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT))
					.getAmplifier();
			float damage = event.getAmount();

			// Each level of the effect results in a 10% increase in damage taken (excluding bosses, which take 5%)
			if (damagedEntity.getType().is(EntityTypes.BOSSES)) {
				damage *= ((level + 1) * 0.05f) + 1.0f;
			} else {
				damage *= ((level + 1) * 0.1f) + 1.0f;
			}

			event.setAmount(damage);
		}
	}

	// Handle stuff for the Starstorm Armor set bonus
	public static void starstormArmorSetBonus(LivingIncomingDamageEvent event, @Nullable LivingEntity sourceEntity) {
		if (sourceEntity != null) {
			if (ArmorUtils.isWearingStarstormArmor(sourceEntity)) {
				float damage = event.getAmount();
				damage *= 1.2f;
				event.setAmount(damage);
			}
		}
	}

	// Handle stuff for the Molten armor set bonus
	public static void moltenArmorSetBonus(LivingIncomingDamageEvent event, @Nullable LivingEntity sourceEntity, LivingEntity damagedEntity) {
		if (sourceEntity != null) {
			if (ArmorUtils.isWearingMoltenArmor(sourceEntity)) {
				// If in the Nether, increase all outgoing damage by 20%
				if (sourceEntity.level().dimension() == Level.NETHER) {
					float damage = event.getAmount();
					damage *= 1.2f;
					event.setAmount(damage);
				}

				// If submerged in lava, increase all outgoing damage by 10%
				if (sourceEntity.isInLava()) {
					float damage = event.getAmount();
					damage *= 1.1f;
					event.setAmount(damage);
				}
			}

			if (ArmorUtils.isWearingMoltenArmor(damagedEntity)) {
				// Inflict Hellfire on the attacking entity
				sourceEntity.addEffect(new MobEffectInstance(EffectRegistry.HELLFIRE_EFFECT, 200, 0, false, false));
			}
		}
	}
}