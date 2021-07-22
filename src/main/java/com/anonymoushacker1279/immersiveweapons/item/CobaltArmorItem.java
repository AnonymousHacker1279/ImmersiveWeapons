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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class CobaltArmorItem extends ArmorItem {

	private boolean isLeggings = false;
	private static boolean effectEnabled = false;

	/**
	 * Constructor for CobaltArmorItem.
	 * @param material the <code>IArmorMaterial</code> for the item
	 * @param slot the <code>EquipmentSlotType</code>
	 * @param type type ID
	 */
	public CobaltArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
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
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_2.png");
	}

	/**
	 * Runs once per tick while armor is equipped
	 * @param stack the <code>ItemStack</code> instance
	 * @param world the <code>World</code> the player is in
	 * @param player the <code>PlayerEntity</code> wearing the armor
	 */
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.COBALT_HELMET.get() &&
				player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.COBALT_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.COBALT_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.COBALT_BOOTS.get()) {
			if (player.getUUID().toString().equals("086ab520-a158-3f3b-b711-6f10d99b4969") || player.getUUID().toString().equals("94f11dac-d1bc-46da-877b-c69f533f2da2")) {
				if (world.isClientSide) {
					if (ClientModEventSubscriber.toggleArmorEffect.consumeClick()) {
						PacketHandler.INSTANCE.sendToServer(new CobaltArmorItemPacketHandler(false, player.blockPosition(), player.position()));
						if (!Minecraft.getInstance().hasSingleplayerServer()) {
							toggleEffect();
						}
					}
				}

				if (effectEnabled) {
					player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 1, 4, false, false));
					player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 1, 1, false, false));
					player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1, 1, false, false));

					if (!world.isClientSide) {
						PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(player.blockPosition())), new CobaltArmorItemPacketHandler(true, player.blockPosition(), player.position()));
					}
				}
			}
		}
	}

	/**
	 * Toggle the armor effect.
	 */
	static void toggleEffect() {
		effectEnabled = !effectEnabled;
	}

	public static class CobaltArmorItemPacketHandler {

		private final boolean isParticles;
		private final BlockPos blockPos;
		private final Vector3d vector3d;

		/**
		 * Constructor for CobaltArmorItemPacketHandler.
		 * @param isParticles if the packet is for rendering particles
		 * @param blockPos the <code>BlockPos</code> the packet came from
		 * @param vector3d the <code>Vector3d</code> of the player position
		 */
		CobaltArmorItemPacketHandler(boolean isParticles, BlockPos blockPos, Vector3d vector3d) {
			this.isParticles = isParticles;
			this.blockPos = blockPos;
			this.vector3d = vector3d;
		}

		/**
		 * Encodes a packet
		 * @param msg the <code>CobaltArmorItemPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(CobaltArmorItemPacketHandler msg, PacketBuffer packetBuffer) {
			packetBuffer.writeBoolean(msg.isParticles);
			packetBuffer.writeBlockPos(msg.blockPos);
			packetBuffer.writeDouble(msg.vector3d.x).writeDouble(msg.vector3d.y).writeDouble(msg.vector3d.z);
		}

		/**
		 * Decodes a packet
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return CobaltArmorItemPacketHandler
		 */
		public static CobaltArmorItemPacketHandler decode(PacketBuffer packetBuffer) {
			return new CobaltArmorItemPacketHandler(packetBuffer.readBoolean(), packetBuffer.readBlockPos(), new Vector3d(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble()));
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 * @param msg the <code>CobaltArmorItemPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(CobaltArmorItemPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			if (msg.isParticles) {
				context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			} else {
				context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnServer(msg)));
				context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> handleOnServer(msg)));
			}
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the server, when a packet is received
		 * @param msg the <code>CobaltArmorItemPacketHandler</code> message being sent
		 */
		private static void handleOnServer(CobaltArmorItemPacketHandler msg) {
			CobaltArmorItem.toggleEffect();
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 * @param msg the <code>CobaltArmorItemPacketHandler</code> message being sent
		 */
		private static void handleOnClient(CobaltArmorItemPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				minecraft.level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, msg.vector3d.x, msg.vector3d.y + 2.2D, msg.vector3d.z, GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
				minecraft.level.addParticle(ParticleTypes.FLAME, msg.vector3d.x, msg.vector3d.y + 2.2D, msg.vector3d.z, GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
			}
		}
	}
}