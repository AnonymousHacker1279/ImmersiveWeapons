package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record AstralCrystalPayload(BlockPos pos) implements CustomPacketPayload {

	public static final Type<AstralCrystalPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "astral_crystal"));

	public static final StreamCodec<FriendlyByteBuf, AstralCrystalPayload> STREAM_CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC,
			AstralCrystalPayload::pos,
			AstralCrystalPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}