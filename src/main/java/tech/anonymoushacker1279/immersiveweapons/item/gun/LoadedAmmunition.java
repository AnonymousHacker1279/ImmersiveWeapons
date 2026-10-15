package tech.anonymoushacker1279.immersiveweapons.item.gun;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

/// The ammunition and powder loaded into a firearm, stored on the firearm itself. Present only while loaded.
///
/// @param ammo   the ammunition, with a count of how many bullets will be fired
/// @param powder the powder, always a count of one
public record LoadedAmmunition(ItemStack ammo, ItemStack powder) {

	public static final Codec<LoadedAmmunition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ItemStack.CODEC.fieldOf("ammo").forGetter(LoadedAmmunition::ammo),
			ItemStack.CODEC.fieldOf("powder").forGetter(LoadedAmmunition::powder)
	).apply(instance, LoadedAmmunition::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, LoadedAmmunition> STREAM_CODEC = StreamCodec.composite(
			ItemStack.STREAM_CODEC,
			LoadedAmmunition::ammo,
			ItemStack.STREAM_CODEC,
			LoadedAmmunition::powder,
			LoadedAmmunition::new
	);

	@Override
	public boolean equals(Object other) {
		return other instanceof LoadedAmmunition loaded
				&& ItemStack.matches(ammo, loaded.ammo)
				&& ItemStack.matches(powder, loaded.powder);
	}

	@Override
	public int hashCode() {
		return 31 * ItemStack.hashItemAndComponents(ammo) + ItemStack.hashItemAndComponents(powder);
	}
}
