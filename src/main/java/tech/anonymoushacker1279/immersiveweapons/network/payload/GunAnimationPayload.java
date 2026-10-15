package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

/// Tells clients to play a PlayerAnimator animation on a player (e.g., the arms reloading a firearm).
///
/// Animations are authored for the gun held in the left arm; `mirrored` flips them for the right arm. If `stop` is
/// true, the animation layer is stopped instead of playing the animation.
public record GunAnimationPayload(int entityID, Identifier animation, float speed, boolean mirrored,
                                  boolean stop) implements CustomPacketPayload {

	public static final Type<GunAnimationPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gun_animation"));

	public static final StreamCodec<FriendlyByteBuf, GunAnimationPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			GunAnimationPayload::entityID,
			Identifier.STREAM_CODEC,
			GunAnimationPayload::animation,
			ByteBufCodecs.FLOAT,
			GunAnimationPayload::speed,
			ByteBufCodecs.BOOL,
			GunAnimationPayload::mirrored,
			ByteBufCodecs.BOOL,
			GunAnimationPayload::stop,
			GunAnimationPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
