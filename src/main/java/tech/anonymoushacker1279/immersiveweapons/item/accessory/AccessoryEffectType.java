package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record AccessoryEffectType(String name, boolean clamp) {

	public static final Codec<AccessoryEffectType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("name").forGetter(AccessoryEffectType::name),
			Codec.BOOL.fieldOf("clamp").forGetter(AccessoryEffectType::clamp)
	).apply(instance, AccessoryEffectType::new));

	public static final StreamCodec<FriendlyByteBuf, AccessoryEffectType> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			AccessoryEffectType::name,
			ByteBufCodecs.BOOL,
			AccessoryEffectType::clamp,
			AccessoryEffectType::new
	);

	public AccessoryEffectType(Identifier name) {
		this(name.getPath(), false);
	}

	public AccessoryEffectType(Identifier name, boolean clamp) {
		this(name.getPath(), clamp);
	}

	public String createTranslation() {
		return "tooltip.immersiveweapons.accessory.effect_type." + name;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof AccessoryEffectType(String name1, boolean clamp1)
				&& this.name.equals(name1)
				&& this.clamp == clamp1;
	}

	@Override
	public int hashCode() {
		return name.hashCode() + Boolean.hashCode(clamp);
	}
}