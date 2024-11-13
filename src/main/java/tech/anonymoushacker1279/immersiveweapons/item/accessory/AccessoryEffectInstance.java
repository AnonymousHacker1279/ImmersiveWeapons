package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AccessoryEffectInstance(AccessoryEffectType type, double value) {

	public static final Codec<AccessoryEffectInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			AccessoryEffectType.CODEC.fieldOf("type").forGetter(AccessoryEffectInstance::type),
			Codec.DOUBLE.fieldOf("value").forGetter(AccessoryEffectInstance::value)
	).apply(instance, AccessoryEffectInstance::new));
}