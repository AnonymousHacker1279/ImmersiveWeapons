package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record GunScopePayload(double playerFOV, double changingPlayerFOV,
                              float scopeScale) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gun_scope");

	public GunScopePayload(final FriendlyByteBuf buffer) {
		this(buffer.readDouble(), buffer.readDouble(), buffer.readFloat());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeDouble(playerFOV);
		buffer.writeDouble(changingPlayerFOV);
		buffer.writeFloat(scopeScale);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}