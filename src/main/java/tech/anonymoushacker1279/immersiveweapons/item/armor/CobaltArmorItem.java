package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
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
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Supplier;

public class CobaltArmorItem extends ArmorItem {

	private final boolean isLeggings;

	/**
	 * Constructor for CobaltArmorItem.
	 *
	 * @param material the <code>ArmorMaterial</code> for the item
	 * @param slot     the <code>EquipmentSlot</code>
	 */
	public CobaltArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties properties, boolean isLeggings) {
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
				? ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_1.png"
				: ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_2.png");
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
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.COBALT_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.COBALT_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.COBALT_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.COBALT_BOOTS.get()) {

			if (player.getUUID().toString().equals("380df991-f603-344c-a090-369bad2a924a")
					|| player.getUUID().toString().equals("94f11dac-d1bc-46da-877b-c69f533f2da2")) {

				boolean effectEnabled = player.getPersistentData().getBoolean("CobaltArmorEffectEnabled");

				if (level.isClientSide) {
					if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
						// Store the toggle variable in the player's NBT
						player.getPersistentData().putBoolean("CobaltArmorEffectEnabled", !effectEnabled);

						// Send packet to server
						PacketHandler.INSTANCE.sendToServer(new CobaltArmorItemPacketHandler(!effectEnabled));
					}
				}

				if (effectEnabled) {
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,
							1, 4, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
							1, 1, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
							1, 1, false, false));

					if (level.isClientSide) {
						level.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
								false,
								player.getX(),
								player.getY() + 2.2D,
								player.getZ(),
								GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
								GeneralUtilities.getRandomNumber(0.0d, 0.001d),
								GeneralUtilities.getRandomNumber(-0.01d, 0.01d));
						level.addParticle(ParticleTypes.FLAME,
								false,
								player.getX(),
								player.getY() + 2.2D,
								player.getZ(),
								GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
								GeneralUtilities.getRandomNumber(0.0d, 0.001d),
								GeneralUtilities.getRandomNumber(-0.01d, 0.01d));
					}
				}
			}
		}
	}

	public record CobaltArmorItemPacketHandler(boolean state) {

		public static void encode(CobaltArmorItemPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBoolean(msg.state);
		}

		public static CobaltArmorItemPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new CobaltArmorItemPacketHandler(packetBuffer.readBoolean());
		}

		public static void handle(CobaltArmorItemPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			if (context.getSender() != null) {
				context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> run(msg, context.getSender())));
				context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> run(msg, context.getSender())));
			}
			context.setPacketHandled(true);
		}

		/**
		 * Runs when a packet is received
		 */
		private static void run(CobaltArmorItemPacketHandler msg, ServerPlayer player) {
			player.getPersistentData().putBoolean("CobaltArmorEffectEnabled", msg.state);
		}
	}
}