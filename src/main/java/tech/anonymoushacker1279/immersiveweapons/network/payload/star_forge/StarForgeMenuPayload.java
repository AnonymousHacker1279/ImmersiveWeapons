package tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record StarForgeMenuPayload(int containerId, int menuSelectionIndex,
                                   boolean beginCrafting) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "star_forge_menu");

	public StarForgeMenuPayload(final FriendlyByteBuf buffer) {
		this(buffer.readInt(), buffer.readInt(), buffer.readBoolean());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeInt(containerId);
		buffer.writeInt(menuSelectionIndex);
		buffer.writeBoolean(beginCrafting);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}