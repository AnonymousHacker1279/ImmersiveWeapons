package tech.anonymoushacker1279.immersiveweapons.event.environment_effects;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Objects;

public class EnvironmentEffects {

	private float celestialProtectionChanceForNoDamage = 0.0f;

	public EnvironmentEffects() {
	}

	// Handle stuff for the celestial protection effect
	public void celestialProtectionEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		if (damagedEntity.hasEffect(EffectRegistry.CELESTIAL_PROTECTION_EFFECT.get())) {
			float damage = event.getAmount();

			// Check if the damage should be neutralized
			if (celestialProtectionChanceForNoDamage >= 1.0f) {
				event.setCanceled(true);
				celestialProtectionChanceForNoDamage = 0.0f;
				return;
			} else if (celestialProtectionChanceForNoDamage > 0.0f) {
				if (GeneralUtilities.getRandomNumber(0, 1.0f) <= celestialProtectionChanceForNoDamage) {
					event.setCanceled(true);
					celestialProtectionChanceForNoDamage = 0.0f;
				}
			}
			// Increase the chance that the next damage taken will be neutralized
			if (damagedEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.ASTRAL_HELMET.get() &&
					damagedEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.ASTRAL_CHESTPLATE.get() &&
					damagedEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.ASTRAL_LEGGINGS.get() &&
					damagedEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.ASTRAL_BOOTS.get()) {

				celestialProtectionChanceForNoDamage += damage * 0.03f; // Astral armor has a 3% charge rate
			} else {
				celestialProtectionChanceForNoDamage += damage * 0.01f; // Other armor has a 1% charge rate
			}

			// This effect grants a 5% damage reduction to all damage taken, unless they rolled for no damage
			damage = damage * 0.95f;
			event.setAmount(damage);
		}
	}

	// Handle stuff for the damage vulnerability effect
	public void damageVulnerabilityEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		if (damagedEntity.hasEffect(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT.get())) {
			int level = Objects.requireNonNull(damagedEntity.getEffect(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT.get()))
					.getAmplifier();
			float damage = event.getAmount();

			// Each level of the effect results in a 10% increase in damage taken
			damage *= (level + 1) * 1.1f;
			event.setAmount(damage);
		}
	}

	// Handle stuff for the Starstorm Armor set bonus
	public void starstormArmorSetBonus(LivingHurtEvent event, LivingEntity sourceEntity) {
		if (sourceEntity != null) {
			if (sourceEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.STARSTORM_HELMET.get() &&
					sourceEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.STARSTORM_CHESTPLATE.get() &&
					sourceEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.STARSTORM_LEGGINGS.get() &&
					sourceEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.STARSTORM_BOOTS.get()) {

				float damage = event.getAmount();
				damage *= 1.2f;
				event.setAmount(damage);
			}
		}
	}

	// Handle stuff for the Molten armor set bonus
	public void moltenArmorSetBonus(LivingHurtEvent event, LivingEntity sourceEntity) {
		if (sourceEntity != null) {
			if (sourceEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
					sourceEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
					sourceEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
					sourceEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {

				// If in the Nether, increase all outgoing damage by 20%
				if (sourceEntity.level.dimension() == Level.NETHER) {
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
		}
	}
}