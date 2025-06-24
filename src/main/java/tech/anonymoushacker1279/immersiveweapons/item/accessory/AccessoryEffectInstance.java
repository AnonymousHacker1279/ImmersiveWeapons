package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AccessoryEffectScalingType;

import java.util.Optional;

public record AccessoryEffectInstance(AccessoryEffectType type, Optional<AccessoryEffectScalingType> scalingType,
                                      double value) {

	public static final Codec<AccessoryEffectInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			AccessoryEffectType.CODEC.fieldOf("type").forGetter(AccessoryEffectInstance::type),
			AccessoryEffectScalingType.CODEC.lenientOptionalFieldOf("scaling_type").forGetter(AccessoryEffectInstance::scalingType),
			Codec.DOUBLE.fieldOf("value").forGetter(AccessoryEffectInstance::value)
	).apply(instance, AccessoryEffectInstance::new));

	public static final StreamCodec<FriendlyByteBuf, AccessoryEffectInstance> STREAM_CODEC = StreamCodec.composite(
			AccessoryEffectType.STREAM_CODEC,
			AccessoryEffectInstance::type,
			ByteBufCodecs.optional(AccessoryEffectScalingType.STREAM_CODEC),
			AccessoryEffectInstance::scalingType,
			ByteBufCodecs.DOUBLE,
			AccessoryEffectInstance::value,
			AccessoryEffectInstance::new
	);
}