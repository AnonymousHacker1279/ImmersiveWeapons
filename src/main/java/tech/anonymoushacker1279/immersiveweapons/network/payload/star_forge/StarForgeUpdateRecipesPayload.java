package tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.List;
import java.util.UUID;

public record StarForgeUpdateRecipesPayload(UUID playerUUID, int containerId,
                                            List<ResourceLocation> recipeIds) implements CustomPacketPayload {

	public static final Type<StarForgeUpdateRecipesPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "star_forge_update_recipes"));

	public static final StreamCodec<FriendlyByteBuf, StarForgeUpdateRecipesPayload> STREAM_CODEC = StreamCodec.composite(
			UUIDUtil.STREAM_CODEC,
			StarForgeUpdateRecipesPayload::playerUUID,
			ByteBufCodecs.INT,
			StarForgeUpdateRecipesPayload::containerId,
			ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()),
			StarForgeUpdateRecipesPayload::recipeIds,
			StarForgeUpdateRecipesPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}