package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record AstralCrystalPayload(BlockPos pos) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_crystal");

	public AstralCrystalPayload(final FriendlyByteBuf buffer) {
		this(buffer.readBlockPos());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeBlockPos(pos);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}