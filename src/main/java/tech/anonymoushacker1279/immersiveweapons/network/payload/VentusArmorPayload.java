package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.armor.VentusArmorItem;

public record VentusArmorPayload(VentusArmorItem.PacketTypes packetType, boolean state) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_armor");

	public VentusArmorPayload(final FriendlyByteBuf buffer) {
		this(buffer.readEnum(VentusArmorItem.PacketTypes.class), buffer.readBoolean());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeEnum(packetType);
		buffer.writeBoolean(state);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}