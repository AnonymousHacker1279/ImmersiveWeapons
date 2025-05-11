package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.network.payload.VentusArmorPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import javax.annotation.Nullable;

public class VentusArmorItem extends Item {

	private int windShieldCooldown = 0;
	private int windShieldDuration = 0;

	public VentusArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
		super(properties.humanoidArmor(material, armorType));
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
		return super.getDefaultAttributeModifiers(stack).withModifierAdded(
				Attributes.SAFE_FALL_DISTANCE,
				new AttributeModifier(
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "ventus_safe_fall_distance"),
						10d,
						Operation.ADD_VALUE),
				EquipmentSlotGroup.ARMOR);
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
		if (entity instanceof Player player) {
			if (ArmorUtils.isWearingVentusArmor(player)) {
				boolean effectEnabled = player.getPersistentData().getBoolean("VentusArmorEffectEnabled").orElse(false);

				if (level.isClientSide) {
					if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
						// Store the toggle variable in the player's NBT
						player.getPersistentData().putBoolean("VentusArmorEffectEnabled", !effectEnabled);

						// Send packet to server
						PacketDistributor.sendToServer(new VentusArmorPayload(PacketTypes.CHANGE_STATE, !effectEnabled));

						if (effectEnabled) {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.disabled")
									.withStyle(ChatFormatting.RED), true);
						} else {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.enabled")
									.withStyle(ChatFormatting.GREEN), true);
						}
					}
					if (effectEnabled) {
						if (Minecraft.getInstance().options.keyJump.consumeClick()) {
							level.addParticle(ParticleTypes.CLOUD,
									player.getX(),
									player.getY() + 0.1d,
									player.getZ(),
									GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
									GeneralUtilities.getRandomNumber(0.0d, 0.03d),
									GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
						}
						if (IWKeyBinds.ARMOR_ACTION.consumeClick()) {
							if (windShieldCooldown == 0) {
								windShieldCooldown = 120;
								windShieldDuration = 60;
							}
						}
					}

					if (windShieldCooldown > 0) {
						if (windShieldDuration > 0) {
							handleProjectileReflection(level, player);
							PacketDistributor.sendToServer(new VentusArmorPayload(PacketTypes.HANDLE_PROJECTILE_REFLECTION, effectEnabled));

							windShieldDuration--;
						}

						windShieldCooldown--;

						if (windShieldCooldown == 0) {
							player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
						}
					}
				}

				if (effectEnabled) {
					player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST,
							0, 2, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,
							0, 0, false, false));
				}
			}
		}
	}

	public static void handleProjectileReflection(Level level, Player player) {
		for (Entity entity : level.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(1.5d))) {
			if (entity instanceof Projectile projectile) {
				// Calculate the bullet's existing velocity
				double x = projectile.getX() - projectile.xOld;
				double y = projectile.getY() - projectile.yOld;
				double z = projectile.getZ() - projectile.zOld;

				// Scale by 150% and convert to a float
				float velocity = (float) Math.sqrt(x * x + y * y + z * z) * 1.5f;

				// Teleport the projectile just outside the radius, so it doesn't get reflected again
				projectile.setPos(player.getX() + 1.51d * Math.cos(Math.toRadians(player.getYRot() + 90)),
						player.getY() + 1.25d,
						player.getZ() + 1.51d * Math.sin(Math.toRadians(player.getYRot() + 90)));

				projectile.setOnGround(false);

				// Set the projectile rotation to the player's rotation
				projectile.shootFromRotation(entity,
						player.getXRot(),
						player.getYRot(),
						0.0f, velocity, 0.0f);
			}
		}

		// Spawn a circle of cloud particles around the player
		for (int i = 0; i < 360; i += 10) {
			double x = player.getX() + 1.5d * Math.cos(Math.toRadians(i));
			double z = player.getZ() + 1.5d * Math.sin(Math.toRadians(i));
			level.addParticle(ParticleTypes.CLOUD,
					x, player.getY() + 0.5d, z,
					GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
					GeneralUtilities.getRandomNumber(0.0d, 0.03d),
					GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
		}
	}

	public enum PacketTypes {
		CHANGE_STATE,
		HANDLE_PROJECTILE_REFLECTION
	}
}