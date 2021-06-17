package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.ClientModEventSubscriber;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
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

public class CobaltArmorItem extends ArmorItem {

	private boolean isLeggings = false;
	private static boolean effectEnabled = false;

	public CobaltArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
		super(material, slot, (new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP)));
		if (type == 2) {
			isLeggings = true;
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_2.png");
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.COBALT_HELMET.get() &&
				player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.COBALT_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.COBALT_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.COBALT_BOOTS.get()) {
			if (player.getUUID().toString().equals("086ab520-a158-3f3b-b711-6f10d99b4969") || player.getUUID().toString().equals("94f11dac-d1bc-46da-877b-c69f533f2da2")) {
				if (world.isClientSide) {
					if (ClientModEventSubscriber.toggleArmorEffect.consumeClick()) {
						PacketHandler.INSTANCE.sendToServer(new CobaltArmorItemPacketHandler(true));
						toggleEffect();
					}

					if (effectEnabled) {
						world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX(), player.getY() + 2.2D, player.getZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
						world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY() + 2.2D, player.getZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					}
				}

				if (effectEnabled) {
					player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 1, 4, false, false));
					player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 1, 1, false, false));
					player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1, 1, false, false));
				}
			}
		}
	}

	public static void toggleEffect() {
		effectEnabled = !effectEnabled;
	}

	public static class CobaltArmorItemPacketHandler {

		private final boolean clientSide;

		public CobaltArmorItemPacketHandler(final boolean clientSide) {
			this.clientSide = clientSide;
		}

		public static void encode(final CobaltArmorItemPacketHandler msg, final PacketBuffer packetBuffer) {
			packetBuffer.writeBoolean(msg.clientSide);
		}

		public static CobaltArmorItemPacketHandler decode(final PacketBuffer packetBuffer) {
			return new CobaltArmorItemPacketHandler(packetBuffer.readBoolean());
		}

		public static void handle(final CobaltArmorItemPacketHandler msg, final Supplier<Context> contextSupplier) {
			final NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnServer(msg)));
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> handleOnServer(msg)));
			context.setPacketHandled(true);
		}

		private static void handleOnServer(final CobaltArmorItemPacketHandler msg) {
			CobaltArmorItem.toggleEffect();
		}
	}
}