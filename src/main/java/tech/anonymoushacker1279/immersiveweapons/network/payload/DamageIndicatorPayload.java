package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record DamageIndicatorPayload(float damage, Vector3f pos) implements CustomPacketPayload {

	public static final Type<DamageIndicatorPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "damage_indicator"));

	public static final StreamCodec<FriendlyByteBuf, DamageIndicatorPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT,
			DamageIndicatorPayload::damage,
			ByteBufCodecs.VECTOR3F,
			DamageIndicatorPayload::pos,
			DamageIndicatorPayload::new
	);

	@Override
	public Type<? extends DamageIndicatorPayload> type() {
		return TYPE;
	}
}