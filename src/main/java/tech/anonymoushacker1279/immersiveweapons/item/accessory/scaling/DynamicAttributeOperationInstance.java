package tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record DynamicAttributeOperationInstance(AttributeOperation attributeOperation, double targetValue) {

	public static final Codec<DynamicAttributeOperationInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			AttributeOperation.CODEC.fieldOf("attributeOperation").forGetter(DynamicAttributeOperationInstance::attributeOperation),
			Codec.DOUBLE.fieldOf("targetValue").forGetter(DynamicAttributeOperationInstance::targetValue)
	).apply(instance, DynamicAttributeOperationInstance::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, DynamicAttributeOperationInstance> STREAM_CODEC = StreamCodec.composite(
			AttributeOperation.STREAM_CODEC,
			DynamicAttributeOperationInstance::attributeOperation,
			ByteBufCodecs.DOUBLE,
			DynamicAttributeOperationInstance::targetValue,
			DynamicAttributeOperationInstance::new
	);
}