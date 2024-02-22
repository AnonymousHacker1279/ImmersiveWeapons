package tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.List;
import java.util.UUID;

public record StarForgeUpdateRecipesPayload(UUID playerUUID, int containerId,
                                            List<ResourceLocation> recipeIds) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "star_forge_update_recipes");

	public StarForgeUpdateRecipesPayload(final FriendlyByteBuf buffer) {
		this(buffer.readUUID(), buffer.readInt(), buffer.readList(FriendlyByteBuf::readResourceLocation));
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeUUID(playerUUID);
		buffer.writeInt(containerId);
		buffer.writeCollection(recipeIds, FriendlyByteBuf::writeResourceLocation);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}