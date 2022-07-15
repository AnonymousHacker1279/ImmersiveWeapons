package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
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
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;

import java.util.List;
import java.util.function.Supplier;

public class TeslaArmorItem extends ArmorItem {

	private static boolean effectEnabled = false;
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
	 * Set the armor effect.
	 */
	private static void setEffectState(boolean state) {
		effectEnabled = state;
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
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.TESLA_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.TESLA_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.TESLA_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.TESLA_BOOTS.get()) {

			if (level.isClientSide) {
				if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
					PacketHandler.INSTANCE.sendToServer(new TeslaArmorItemPacketHandler(!effectEnabled));
					if (effectEnabled) {
						level.playSound(player,
								player.blockPosition(),
								DeferredRegistryHandler.TESLA_ARMOR_POWER_DOWN.get(),
								SoundSource.PLAYERS,
								0.9f,
								1.0f);

					} else {
						level.playSound(player,
								player.blockPosition(),
								DeferredRegistryHandler.TESLA_ARMOR_POWER_UP.get(),
								SoundSource.PLAYERS,
								0.9f,
								1.0f);

						countdown = 0;
					}
					if (!Minecraft.getInstance().isLocalServer()) {
						setEffectState(!effectEnabled);
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
					DeferredRegistryHandler.TESLA_ARMOR_EFFECT.get(),
					SoundSource.NEUTRAL,
					0.65f,
					1);

			countdown = 120;
		} else if (countdown > 0) {
			countdown--;
		}
	}

	public record TeslaArmorItemPacketHandler(boolean state) {

		/**
		 * Constructor for TeslaArmorItemPacketHandler.
		 *
		 * @param state the state of the armor effect
		 */
		public TeslaArmorItemPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>TeslaArmorItemPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(TeslaArmorItemPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBoolean(msg.state);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return TeslaArmorItemPacketHandler
		 */
		public static TeslaArmorItemPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new TeslaArmorItemPacketHandler(packetBuffer.readBoolean());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(TeslaArmorItemPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> run(msg)));
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> run(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the server, when a packet is received
		 */
		private static void run(TeslaArmorItemPacketHandler msg) {
			TeslaArmorItem.setEffectState(msg.state);
		}
	}
}