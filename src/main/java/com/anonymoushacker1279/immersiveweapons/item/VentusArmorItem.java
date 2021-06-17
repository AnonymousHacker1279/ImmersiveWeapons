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

	public static boolean armorIsToggled = false;
	private boolean isLeggings = false;

	public VentusArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
		super(material, slot, (new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP)));
		if (type == 2) {
			isLeggings = true;
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/ventus_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/ventus_layer_2.png");
	}

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

	public static void toggleEffect() {
		armorIsToggled = !armorIsToggled;
	}

	public static class VentusArmorItemPacketHandler {

		private final boolean clientSide;

		public VentusArmorItemPacketHandler(final boolean clientSide) {
			this.clientSide = clientSide;
		}

		public static void encode(final VentusArmorItemPacketHandler msg, final PacketBuffer packetBuffer) {
			packetBuffer.writeBoolean(msg.clientSide);
		}

		public static VentusArmorItemPacketHandler decode(final PacketBuffer packetBuffer) {
			return new VentusArmorItemPacketHandler(packetBuffer.readBoolean());
		}

		public static void handle(final VentusArmorItemPacketHandler msg, final Supplier<Context> contextSupplier) {
			final NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnServer(msg)));
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> handleOnServer(msg)));
			context.setPacketHandled(true);
		}

		private static void handleOnServer(final VentusArmorItemPacketHandler msg) {
			VentusArmorItem.toggleEffect();
		}
	}
}