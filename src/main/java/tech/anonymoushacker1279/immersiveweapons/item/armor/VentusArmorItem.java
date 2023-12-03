package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent.Context;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class VentusArmorItem extends ArmorItem {

	private final boolean isLeggings;
	private int windShieldCooldown = 0;
	private int windShieldDuration = 0;

	public VentusArmorItem(ArmorMaterial material, ArmorItem.Type armorType, Properties properties, boolean isLeggings) {
		super(material, armorType, properties);
		this.isLeggings = isLeggings;
	}

	/**
	 * Get the armor texture.
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param entity the <code>Entity</code> wearing the armor
	 * @param slot   the <code>EquipmentSlot</code>
	 * @param type   type ID
	 * @return String
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return (!isLeggings
				? ImmersiveWeapons.MOD_ID + ":textures/armor/ventus_layer_1.png"
				: ImmersiveWeapons.MOD_ID + ":textures/armor/ventus_layer_2.png");
	}

	/**
	 * Runs once per tick while armor is equipped
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> wearing the armor
	 */
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.VENTUS_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.VENTUS_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.VENTUS_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.VENTUS_BOOTS.get()) {

			boolean effectEnabled = player.getPersistentData().getBoolean("VentusArmorEffectEnabled");

			if (level.isClientSide) {
				if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
					// Store the toggle variable in the player's NBT
					player.getPersistentData().putBoolean("VentusArmorEffectEnabled", !effectEnabled);

					// Send packet to server
					PacketHandler.INSTANCE.sendToServer(new VentusArmorItemPacketHandler(PacketTypes.CHANGE_STATE, !effectEnabled));

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
						PacketHandler.INSTANCE.sendToServer(new VentusArmorItemPacketHandler(PacketTypes.HANDLE_PROJECTILE_REFLECTION, effectEnabled));

						windShieldDuration--;
					}

					windShieldCooldown--;

					if (windShieldCooldown == 0) {
						player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
					}
				}
			}

			if (effectEnabled) {
				player.addEffect(new MobEffectInstance(MobEffects.JUMP,
						0, 2, false, false));
				player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,
						0, 0, false, false));
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

	public record VentusArmorItemPacketHandler(PacketTypes packetType, boolean state) {

		public static void encode(VentusArmorItemPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeEnum(msg.packetType).writeBoolean(msg.state);
		}

		public static VentusArmorItemPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new VentusArmorItemPacketHandler(packetBuffer.readEnum(PacketTypes.class), packetBuffer.readBoolean());
		}

		public static void handle(VentusArmorItemPacketHandler msg, Context context) {
			if (context.getSender() != null) {
				context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> run(msg, context.getSender())));
				context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> run(msg, context.getSender())));
			}
			context.setPacketHandled(true);
		}

		private static void run(VentusArmorItemPacketHandler msg, ServerPlayer player) {
			if (msg.packetType == PacketTypes.CHANGE_STATE) {
				player.getPersistentData().putBoolean("VentusArmorEffectEnabled", msg.state);
			}

			if (msg.packetType == PacketTypes.HANDLE_PROJECTILE_REFLECTION) {
				VentusArmorItem.handleProjectileReflection(player.level(), player);
			}
		}
	}

	enum PacketTypes {
		CHANGE_STATE,
		HANDLE_PROJECTILE_REFLECTION
	}
}