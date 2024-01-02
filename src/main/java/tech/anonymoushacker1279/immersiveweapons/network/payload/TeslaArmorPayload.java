package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.armor.TeslaArmorItem.EffectState;

public record TeslaArmorPayload(EffectState state) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor");

	public TeslaArmorPayload(final FriendlyByteBuf buffer) {
		this(buffer.readEnum(EffectState.class));
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeEnum(state);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}