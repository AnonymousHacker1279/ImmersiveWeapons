package tech.anonymoushacker1279.immersiveweapons.client.tooltip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.List;
import java.util.Optional;

public record SerializableTooltip(String key, Optional<List<ChatFormatting>> formats) {

	public static final Codec<SerializableTooltip> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("key").forGetter(SerializableTooltip::key),
			Codec.list(ChatFormatting.CODEC).lenientOptionalFieldOf("formats").forGetter(tooltip -> tooltip.formats)
	).apply(instance, SerializableTooltip::new));

	public static final StreamCodec<FriendlyByteBuf, SerializableTooltip> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			SerializableTooltip::key,
			ByteBufCodecs.optional(NeoForgeStreamCodecs.enumCodec(ChatFormatting.class).apply(ByteBufCodecs.list())),
			SerializableTooltip::formats,
			SerializableTooltip::new
	);

	public static SerializableTooltip fromComponent(String key, ChatFormatting... formats) {
		return new SerializableTooltip(key, Optional.of(List.of(formats)));
	}

	public MutableComponent getComponent() {
		return formats.map(formatList -> Component.translatable(key)
						.withStyle(formatList.toArray(ChatFormatting[]::new)))
				.orElseGet(() -> Component.translatable(key));
	}
}