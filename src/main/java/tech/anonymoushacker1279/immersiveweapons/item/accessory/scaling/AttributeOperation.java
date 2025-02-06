package tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record AttributeOperation(AttributeModifier modifier, Holder<Attribute> attribute) {

	public static final Codec<AttributeOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			AttributeModifier.CODEC.fieldOf("modifier").forGetter(AttributeOperation::modifier),
			Attribute.CODEC.fieldOf("attribute").forGetter(AttributeOperation::attribute)
	).apply(instance, AttributeOperation::new));
}