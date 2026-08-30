package tech.anonymoushacker1279.immersiveweapons.client.tooltip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;

public record SerializableTooltip(String key, Optional<Style> style) {

	public static final Codec<SerializableTooltip> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("key").forGetter(SerializableTooltip::key),
			Style.Serializer.CODEC.lenientOptionalFieldOf("style").forGetter(tooltip -> tooltip.style)
	).apply(instance, SerializableTooltip::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, SerializableTooltip> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			SerializableTooltip::key,
			ByteBufCodecs.optional(Style.Serializer.TRUSTED_STREAM_CODEC),
			SerializableTooltip::style,
			SerializableTooltip::new
	);

	public MutableComponent getComponent() {
		MutableComponent component = Component.translatable(key);
		return style.map(component::withStyle).orElse(component);
	}
}