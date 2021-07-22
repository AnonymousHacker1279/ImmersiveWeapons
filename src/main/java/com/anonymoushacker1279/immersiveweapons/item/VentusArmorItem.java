package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.ClientModEventSubscriber;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class VentusArmorItem extends ArmorItem {

	private static boolean armorIsToggled = false;
	private boolean isLeggings = false;

	/**
	 * Constructor for VentusArmorItem.
	 * @param material the <code>IArmorMaterial</code> for the item
	 * @param slot the <code>EquipmentSlotType</code>
	 * @param type type ID
	 */
	public VentusArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
		super(material, slot, (new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP)));
		if (type == 2) {
			isLeggings = true;
		}
	}

	/**
	 * Get the armor texture.
	 * @param stack the <code>ItemStack</code> instance
	 * @param entity the <code>Entity</code> wearing the armor
	 * @param slot the <code>EquipmentSlotType</code>
	 * @param type type ID
	 * @return String
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/ventus_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/ventus_layer_2.png");
	}

	/**
	 * Runs once per tick while armor is equipped
	 * @param stack the <code>ItemStack</code> instance
	 * @param world the <code>World</code> the player is in
	 * @param player the <code>PlayerEntity</code> wearing the armor
	 */
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.VENTUS_HELMET.get() &&
				player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.VENTUS_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.VENTUS_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.VENTUS_BOOTS.get()) {

			if (world.isClientSide) {
				if (ClientModEventSubscriber.toggleArmorEffect.consumeClick()) {
					PacketHandler.INSTANCE.sendToServer(new VentusArmorItemPacketHandler(true));
					if (!Minecraft.getInstance().isLocalServer()) {
						toggleEffect();
					}
				}
				if (armorIsToggled) {
					if (Minecraft.getInstance().options.keyJump.consumeClick()) {
						world.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.1d, player.getZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					}
				}
			}

			if (armorIsToggled) {
				player.addEffect(new EffectInstance(Effects.JUMP, 0, 2, false, false));
				player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 0, 0, false, false));
			}
		}
	}

	/**
	 * Toggle the armor effect.
	 */
	static void toggleEffect() {
		armorIsToggled = !armorIsToggled;
	}

	public static class VentusArmorItemPacketHandler {

		private final boolean clientSide;

		/**
		 * Constructor for VentusArmorItemPacketHandler.
		 * @param clientSide if the packet is from the client
		 */
		VentusArmorItemPacketHandler(boolean clientSide) {
			this.clientSide = clientSide;
		}

		/**
		 * Encodes a packet
		 * @param msg the <code>VentusArmorItemPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(VentusArmorItemPacketHandler msg, PacketBuffer packetBuffer) {
			packetBuffer.writeBoolean(msg.clientSide);
		}

		/**
		 * Decodes a packet
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return VentusArmorItemPacketHandler
		 */
		public static VentusArmorItemPacketHandler decode(PacketBuffer packetBuffer) {
			return new VentusArmorItemPacketHandler(packetBuffer.readBoolean());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 * @param msg the <code>VentusArmorItemPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(VentusArmorItemPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnServer(msg)));
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> handleOnServer(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the server, when a packet is received
		 * @param msg the <code>VentusArmorItemPacketHandler</code> message being sent
		 */
		private static void handleOnServer(VentusArmorItemPacketHandler msg) {
			VentusArmorItem.toggleEffect();
		}
	}
}