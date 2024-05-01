package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.armor.VentusArmorItem;

public record VentusArmorPayload(VentusArmorItem.PacketTypes packetType, boolean state) implements CustomPacketPayload {

	public static final Type<VentusArmorPayload> TYPE = new Type<>(new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_armor"));

	public static final StreamCodec<FriendlyByteBuf, VentusArmorPayload> STREAM_CODEC = StreamCodec.composite(
			NeoForgeStreamCodecs.enumCodec(VentusArmorItem.PacketTypes.class),
			VentusArmorPayload::packetType,
			ByteBufCodecs.BOOL,
			VentusArmorPayload::state,
			VentusArmorPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}