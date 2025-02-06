package tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DynamicAttributeOperationInstance(AttributeOperation attributeOperation, double targetValue) {

	public static final Codec<DynamicAttributeOperationInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			AttributeOperation.CODEC.fieldOf("attributeOperation").forGetter(DynamicAttributeOperationInstance::attributeOperation),
			Codec.DOUBLE.fieldOf("targetValue").forGetter(DynamicAttributeOperationInstance::targetValue)
	).apply(instance, DynamicAttributeOperationInstance::new));
}