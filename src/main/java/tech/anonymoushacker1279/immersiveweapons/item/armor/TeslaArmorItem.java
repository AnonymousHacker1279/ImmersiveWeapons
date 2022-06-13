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
	private boolean isLeggings = false;
	private int countdown = 0;

	/**
	 * Constructor for TeslaArmorItem.
	 *
	 * @param material the <code>IArmorMaterial</code> for the item
	 * @param slot     the <code>EquipmentSlotType</code>
	 * @param type     type ID
	 */
	public TeslaArmorItem(ArmorMaterial material, EquipmentSlot slot, int type) {
		super(material, slot, (new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP)));
		if (type == 2) {
			isLeggings = true;
		}
	}

	/**
	 * Set the armor effect.
	 */
	static void setEffectState(boolean state) {
		effectEnabled = state;
	}

	/**
	 * Get the armor texture.
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param entity the <code>Entity</code> wearing the armor
	 * @param slot   the <code>EquipmentSlotType</code>
	 * @param type   type ID
	 * @return String
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_2.png");
	}

	/**
	 * Runs once per tick while armor is equipped
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param world  the <code>World</code> the player is in
	 * @param player the <code>PlayerEntity</code> wearing the armor
	 */
	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.TESLA_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.TESLA_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.TESLA_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.TESLA_BOOTS.get()) {
			if (world.isClientSide) {
				if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
					PacketHandler.INSTANCE.sendToServer(new TeslaArmorItemPacketHandler(!effectEnabled));
					if (effectEnabled) {
						world.playSound(player, player.blockPosition(), DeferredRegistryHandler.TESLA_ARMOR_POWER_DOWN.get(), SoundSource.PLAYERS, 0.9f, 1.0f);
					} else {
						world.playSound(player, player.blockPosition(), DeferredRegistryHandler.TESLA_ARMOR_POWER_UP.get(), SoundSource.PLAYERS, 0.9f, 1.0f);
						countdown = 0;
					}
					if (!Minecraft.getInstance().isLocalServer()) {
						setEffectState(!effectEnabled);
					}
				}
			}

			if (effectEnabled) {
				List<Entity> entity = world.getEntities(player, player.getBoundingBox().move(-3, -3, -3).expandTowards(6, 6, 6));

				if (!entity.isEmpty()) {
					for (Entity element : entity) {
						if (element instanceof LivingEntity) {
							((LivingEntity) element).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0, false, false));
							((LivingEntity) element).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0, false, false));
							((LivingEntity) element).addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0, false, false));
							effectNoise(world, player);
						}
					}
				}
			}

		}
	}

	/**
	 * Play a sound while the armor effect is toggled.
	 *
	 * @param world  the <code>World</code> the player is in
	 * @param player the <code>PlayerEntity</code> instance
	 */
	private void effectNoise(Level world, Player player) {
		if (countdown == 0 && ClientConfig.TESLA_ARMOR_EFFECT_SOUND.get()) {
			world.playSound(player, player.blockPosition(), DeferredRegistryHandler.TESLA_ARMOR_EFFECT.get(), SoundSource.NEUTRAL, 0.65f, 1);
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