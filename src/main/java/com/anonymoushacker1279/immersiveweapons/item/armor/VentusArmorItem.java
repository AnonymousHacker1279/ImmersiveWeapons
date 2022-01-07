package com.anonymoushacker1279.immersiveweapons.item.armor;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.ClientModEventSubscriber;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class VentusArmorItem extends ArmorItem {

	private static boolean effectEnabled = false;
	private boolean isLeggings = false;

	/**
	 * Constructor for VentusArmorItem.
	 *
	 * @param material the <code>IArmorMaterial</code> for the item
	 * @param slot     the <code>EquipmentSlotType</code>
	 * @param type     type ID
	 */
	public VentusArmorItem(ArmorMaterial material, EquipmentSlot slot, int type) {
		super(material, slot, (new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP)));
		if (type == 2) {
			isLeggings = true;
		}
	}

	/**
	 * Toggle the armor effect.
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
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/ventus_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/ventus_layer_2.png");
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
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.VENTUS_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.VENTUS_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.VENTUS_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.VENTUS_BOOTS.get()) {

			if (world.isClientSide) {
				if (ClientModEventSubscriber.toggleArmorEffect.consumeClick()) {
					PacketHandler.INSTANCE.sendToServer(new VentusArmorItemPacketHandler(!effectEnabled));
					if (!Minecraft.getInstance().isLocalServer()) {
						setEffectState(!effectEnabled);
					}
				}
				if (effectEnabled) {
					if (Minecraft.getInstance().options.keyJump.consumeClick()) {
						world.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.1d, player.getZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					}
				}
			}

			if (effectEnabled) {
				player.addEffect(new MobEffectInstance(MobEffects.JUMP, 0, 2, false, false));
				player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 0, 0, false, false));
			}
		}
	}

	public record VentusArmorItemPacketHandler(boolean state) {

		/**
		 * Constructor for VentusArmorItemPacketHandler.
		 *
		 * @param state the armor effect state
		 */
		public VentusArmorItemPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>VentusArmorItemPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(VentusArmorItemPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBoolean(msg.state);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return VentusArmorItemPacketHandler
		 */
		public static VentusArmorItemPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new VentusArmorItemPacketHandler(packetBuffer.readBoolean());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(VentusArmorItemPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> run(msg)));
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> run(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs when a packet is received
		 */
		private static void run(VentusArmorItemPacketHandler msg) {
			VentusArmorItem.setEffectState(msg.state);
		}
	}
}