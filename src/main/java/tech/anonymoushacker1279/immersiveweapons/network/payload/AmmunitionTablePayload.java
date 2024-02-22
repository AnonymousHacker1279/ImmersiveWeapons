package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record AmmunitionTablePayload(int containerId, float densityModifier) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "ammunition_table");

	public AmmunitionTablePayload(final FriendlyByteBuf buffer) {
		this(buffer.readInt(), buffer.readFloat());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeInt(containerId);
		buffer.writeFloat(densityModifier);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}