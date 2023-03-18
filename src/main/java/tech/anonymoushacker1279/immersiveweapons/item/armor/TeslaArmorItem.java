package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.config.ClientConfig;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.List;
import java.util.function.Supplier;

public class TeslaArmorItem extends ArmorItem {

	private final boolean isLeggings;
	private int countdown = 0;

	/**
	 * Constructor for TeslaArmorItem.
	 *
	 * @param material the <code>ArmorMaterial</code> for the item
	 * @param slot     the <code>EquipmentSlot</code>
	 */
	public TeslaArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties properties, boolean isLeggings) {
		super(material, slot, properties);
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
				? ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_1.png"
				: ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_2.png");
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
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.TESLA_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.TESLA_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.TESLA_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.TESLA_BOOTS.get()) {

			boolean effectEnabled = player.getPersistentData().getBoolean("TeslaArmorEffectEnabled");

			if (level.isClientSide) {
				if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
					// Store the toggle variable in the player's NBT
					player.getPersistentData().putBoolean("TeslaArmorEffectEnabled", !effectEnabled);

					// Send packet to server
					PacketHandler.INSTANCE.sendToServer(new TeslaArmorItemPacketHandler(!effectEnabled));

					if (effectEnabled) {
						level.playSound(player,
								player.blockPosition(),
								SoundEventRegistry.TESLA_ARMOR_POWER_DOWN.get(),
								SoundSource.PLAYERS,
								0.9f,
								1.0f);

					} else {
						level.playSound(player,
								player.blockPosition(),
								SoundEventRegistry.TESLA_ARMOR_POWER_UP.get(),
								SoundSource.PLAYERS,
								0.9f,
								1.0f);

						countdown = 0;
					}
				}
			}

			if (effectEnabled) {
				List<Entity> entities = level.getEntities(player,
						player.getBoundingBox()
								.move(-3, -3, -3)
								.expandTowards(6, 6, 6));

				if (!entities.isEmpty()) {
					for (Entity entity : entities) {
						if (entity instanceof LivingEntity) {
							((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
									100, 0, false, false));
							((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
									100, 0, false, false));
							((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.CONFUSION,
									100, 0, false, false));

							effectNoise(level, player);
						}
					}
				}
			}

		}
	}

	/**
	 * Play a sound while the armor effect is toggled.
	 *
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> instance
	 */
	private void effectNoise(Level level, Player player) {
		if (countdown == 0 && ClientConfig.TESLA_ARMOR_EFFECT_SOUND.get()) {
			level.playSound(player,
					player.blockPosition(),
					SoundEventRegistry.TESLA_ARMOR_EFFECT.get(),
					SoundSource.NEUTRAL,
					0.65f,
					1);

			countdown = 120;
		} else if (countdown > 0) {
			countdown--;
		}
	}

	public record TeslaArmorItemPacketHandler(boolean state) {

		public static void encode(TeslaArmorItemPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBoolean(msg.state);
		}

		public static TeslaArmorItemPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new TeslaArmorItemPacketHandler(packetBuffer.readBoolean());
		}

		public static void handle(TeslaArmorItemPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			if (context.getSender() != null) {
				context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> run(msg, context.getSender())));
				context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> run(msg, context.getSender())));
			}
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the server, when a packet is received
		 */
		private static void run(TeslaArmorItemPacketHandler msg, ServerPlayer player) {
			player.getPersistentData().putBoolean("TeslaArmorEffectEnabled", msg.state);
		}
	}
}