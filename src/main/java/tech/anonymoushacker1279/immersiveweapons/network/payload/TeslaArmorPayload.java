package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.armor.TeslaArmorItem.EffectState;

public record TeslaArmorPayload(EffectState state) implements CustomPacketPayload {

	public static final Type<TeslaArmorPayload> TYPE = new Type<>(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor"));

	public static final StreamCodec<FriendlyByteBuf, TeslaArmorPayload> STREAM_CODEC = StreamCodec.composite(
			NeoForgeStreamCodecs.enumCodec(EffectState.class),
			TeslaArmorPayload::state,
			TeslaArmorPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}