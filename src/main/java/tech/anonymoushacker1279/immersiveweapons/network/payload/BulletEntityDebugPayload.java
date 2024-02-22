package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record BulletEntityDebugPayload(double liveBulletDamage,
                                       boolean isBulletCritical) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "bullet_entity_debug");

	public BulletEntityDebugPayload(final FriendlyByteBuf buffer) {
		this(buffer.readDouble(), buffer.readBoolean());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeDouble(liveBulletDamage);
		buffer.writeBoolean(isBulletCritical);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}