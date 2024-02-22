package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record CobaltArmorPayload(boolean state) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_armor");

	public CobaltArmorPayload(final FriendlyByteBuf buffer) {
		this(buffer.readBoolean());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeBoolean(state);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}