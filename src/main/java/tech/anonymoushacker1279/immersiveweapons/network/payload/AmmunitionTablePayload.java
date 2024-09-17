package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record AmmunitionTablePayload(int containerId, float densityModifier) implements CustomPacketPayload {

	public static final Type<AmmunitionTablePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "ammunition_table"));

	public static final StreamCodec<FriendlyByteBuf, AmmunitionTablePayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			AmmunitionTablePayload::containerId,
			ByteBufCodecs.FLOAT,
			AmmunitionTablePayload::densityModifier,
			AmmunitionTablePayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}