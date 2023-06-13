package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Objects;
import java.util.function.Supplier;

public class EnvironmentEffects {

	public static float celestialProtectionChanceForNoDamage = 0.0f;

	// Handle stuff for the celestial protection effect
	public static void celestialProtectionEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
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

			if (damagedEntity instanceof ServerPlayer player) {
				PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),
						new EnvironmentEffectsPacketHandler(celestialProtectionChanceForNoDamage));
			}

			// This effect grants a 5% damage reduction to all damage taken, unless they rolled for no damage
			damage = damage * 0.95f;
			event.setAmount(damage);
		}
	}

	// Handle stuff for the damage vulnerability effect
	public static void damageVulnerabilityEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
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
	public static void starstormArmorSetBonus(LivingHurtEvent event, @Nullable LivingEntity sourceEntity) {
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
	public static void moltenArmorSetBonus(LivingHurtEvent event, @Nullable LivingEntity sourceEntity) {
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

	public record EnvironmentEffectsPacketHandler(float celestialProtectionChanceForNoDamage) {

		public static void encode(EnvironmentEffectsPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeFloat(msg.celestialProtectionChanceForNoDamage);
		}

		public static EnvironmentEffectsPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new EnvironmentEffectsPacketHandler(packetBuffer.readFloat());
		}

		public static void handle(EnvironmentEffectsPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> runOnClient(msg)));
			context.setPacketHandled(true);
		}

		private static void runOnClient(EnvironmentEffectsPacketHandler msg) {
			EnvironmentEffects.celestialProtectionChanceForNoDamage = msg.celestialProtectionChanceForNoDamage;
		}
	}
}