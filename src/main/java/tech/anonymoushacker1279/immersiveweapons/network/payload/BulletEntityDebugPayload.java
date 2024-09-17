package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record BulletEntityDebugPayload(double liveBulletDamage,
                                       boolean isBulletCritical) implements CustomPacketPayload {

	public static final Type<BulletEntityDebugPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bullet_entity_debug"));

	public static final StreamCodec<FriendlyByteBuf, BulletEntityDebugPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.DOUBLE,
			BulletEntityDebugPayload::liveBulletDamage,
			ByteBufCodecs.BOOL,
			BulletEntityDebugPayload::isBulletCritical,
			BulletEntityDebugPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}