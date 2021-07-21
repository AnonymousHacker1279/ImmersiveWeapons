package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.ClientModEventSubscriber;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.List;
import java.util.function.Supplier;

public class TeslaArmorItem extends ArmorItem {

	private static boolean armorIsToggled = false;
	private boolean isLeggings = false;
	private int countdown = 0;

	/**
	 * Constructor for TeslaArmorItem.
	 * @param material the <code>IArmorMaterial</code> for the item
	 * @param slot the <code>EquipmentSlotType</code>
	 * @param type type ID
	 */
	public TeslaArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
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
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_2.png");
	}

	/**
	 * Runs once per tick while armor is equipped
	 * @param stack the <code>ItemStack</code> instance
	 * @param world the <code>World</code> the player is in
	 * @param player the <code>PlayerEntity</code> wearing the armor
	 */
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.TESLA_HELMET.get() &&
				player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.TESLA_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.TESLA_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.TESLA_BOOTS.get()) {
			if (world.isClientSide) {
				if (ClientModEventSubscriber.toggleArmorEffect.consumeClick()) {
					PacketHandler.INSTANCE.sendToServer(new TeslaArmorItemPacketHandler(true));
					if (armorIsToggled) {
						world.playSound(player, player.blockPosition(), DeferredRegistryHandler.TESLA_ARMOR_POWER_DOWN.get(), SoundCategory.PLAYERS, 0.9f, 1.0f);
					} else {
						world.playSound(player, player.blockPosition(), DeferredRegistryHandler.TESLA_ARMOR_POWER_UP.get(), SoundCategory.PLAYERS, 0.9f, 1.0f);
						countdown = 0;
					}
					if (!Minecraft.getInstance().isLocalServer()) {
						toggleEffect();
					}
				}
			}

			if (armorIsToggled) {
				List<Entity> entity = world.getEntities(player, player.getBoundingBox().move(-3, -3, -3).expandTowards(6, 6, 6));

				if (!entity.isEmpty()) {
					for (Entity element : entity) {
						if (element instanceof LivingEntity) {
							((LivingEntity) element).addEffect(new EffectInstance(Effects.WEAKNESS, 100, 0, false, false));
							((LivingEntity) element).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0, false, false));
							((LivingEntity) element).addEffect(new EffectInstance(Effects.CONFUSION, 100, 0, false, false));
							effectNoise(world, player);
						}
					}
				}
			}

		}
	}

	/**
	 * Toggle the armor effect.
	 */
	static void toggleEffect() {
		armorIsToggled = !armorIsToggled;
	}

	/**
	 * Play a sound while the armor effect is toggled.
	 * @param world the <code>World</code> the player is in
	 * @param player the <code>PlayerEntity</code> instance
	 */
	private void effectNoise(World world, PlayerEntity player) {
		if (countdown == 0 && Config.TESLA_ARMOR_EFFECT_SOUND.get()) {
			world.playSound(player, player.blockPosition(), DeferredRegistryHandler.TESLA_ARMOR_EFFECT.get(), SoundCategory.NEUTRAL, 0.65f, 1);
			countdown = 120;
		} else if (countdown > 0) {
			countdown--;
		}
	}

	public static class TeslaArmorItemPacketHandler {

		private final boolean clientSide;

		/**
		 * Constructor for TeslaArmorItemPacketHandler.
		 * @param clientSide if the packet is from the client
		 */
		TeslaArmorItemPacketHandler(boolean clientSide) {
			this.clientSide = clientSide;
		}

		/**
		 * Encodes a packet
		 * @param msg the <code>TeslaArmorItemPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(TeslaArmorItemPacketHandler msg, PacketBuffer packetBuffer) {
			packetBuffer.writeBoolean(msg.clientSide);
		}

		/**
		 * Decodes a packet
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return TeslaArmorItemPacketHandler
		 */
		public static TeslaArmorItemPacketHandler decode(PacketBuffer packetBuffer) {
			return new TeslaArmorItemPacketHandler(packetBuffer.readBoolean());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 * @param msg the <code>TeslaArmorItemPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(TeslaArmorItemPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnServer(msg)));
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> handleOnServer(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the server, when a packet is received
		 * @param msg the <code>TeslaArmorItemPacketHandler</code> message being sent
		 */
		private static void handleOnServer(TeslaArmorItemPacketHandler msg) {
			TeslaArmorItem.toggleEffect();
		}
	}
}