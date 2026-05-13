package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import tech.anonymoushacker1279.immersiveweapons.client.tooltip.SerializableTooltip;
import tech.anonymoushacker1279.immersiveweapons.init.AccessoryEffectTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AccessoryEffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/// Builder for creating accessory effects.
public class AccessoryEffectBuilder {

	private final List<AccessoryEffectInstance> effects = new ArrayList<>(5);
	private final List<AttributeOperation> attributeModifiers = new ArrayList<>(5);
	private final List<DynamicAttributeOperationInstance> dynamicAttributeModifiers = new ArrayList<>(5);
	private final List<MobEffectInstance> mobEffects = new ArrayList<>(5);
	private final List<SerializableTooltip> tooltips = new ArrayList<>(5);

	/// Add an effect to the accessory. See [AccessoryEffectTypeRegistry] for a list of available effects.
	///
	/// @param type  the `AccessoryEffectType`
	/// @param value the value of the effect
	/// @return the `EffectBuilder` for chaining
	public AccessoryEffectBuilder addEffect(AccessoryEffectType type, double value) {
		effects.add(new AccessoryEffectInstance(type, Optional.empty(), value));
		return this;
	}

	/// Add an effect to the accessory. See [AccessoryEffectTypeRegistry] for a list of available effects. Accepts a
	/// scaling type, which will be used to scale the effect value based player conditions.
	///
	/// @param type        the `AccessoryEffectType`
	/// @param value       the value of the effect
	/// @param scalingType the `AccessoryEffectScalingType` to use
	/// @return the `EffectBuilder` for chaining
	public AccessoryEffectBuilder addEffect(AccessoryEffectType type, double value, AccessoryEffectScalingType scalingType) {
		effects.add(new AccessoryEffectInstance(type, Optional.of(scalingType), value));
		return this;
	}

	/// Add an attribute modifier to the accessory. These are static and unchanging in value.
	///
	/// @param modifier  the `AttributeModifier`
	/// @param attribute the `Attribute`
	/// @return the `EffectBuilder` for chaining
	public AccessoryEffectBuilder addAttributeModifier(AttributeModifier modifier, Holder<Attribute> attribute) {
		attributeModifiers.add(new AttributeOperation(modifier, attribute));
		return this;
	}

	/// Add a dynamic attribute modifier to the accessory. These are reconstructed as necessary to achieve the target
	/// value.
	///
	/// @param modifier    the `AttributeModifier`
	/// @param attribute   the `Attribute`
	/// @param targetValue the target value of the attribute
	/// @return the `EffectBuilder` for chaining
	public AccessoryEffectBuilder addDynamicAttributeModifier(AttributeModifier modifier, Holder<Attribute> attribute, double targetValue) {
		dynamicAttributeModifiers.add(new DynamicAttributeOperationInstance(new AttributeOperation(modifier, attribute), targetValue));
		return this;
	}

	/// Add a mob effect to the accessory.
	///
	/// @param effect the `MobEffectInstance`
	/// @return the `EffectBuilder` for chaining
	public AccessoryEffectBuilder addMobEffect(MobEffectInstance effect) {
		mobEffects.add(effect);
		return this;
	}

	/// Add a tooltip to the accessory.
	///
	/// @param key     the translation key for the tooltip
	/// @param formats the `ChatFormatting` to apply to the tooltip
	/// @return the `EffectBuilder` for chaining
	public AccessoryEffectBuilder addTooltip(String key, ChatFormatting... formats) {
		tooltips.add(SerializableTooltip.fromComponent(key, formats));
		return this;
	}

	/// Add all effects from another builder to this builder.
	///
	/// @param builder the `EffectBuilder` to add from
	/// @return the `EffectBuilder` for chaining
	public AccessoryEffectBuilder addObjectsFromBuilder(AccessoryEffectBuilder builder) {
		effects.addAll(builder.getEffects());
		attributeModifiers.addAll(builder.getAttributeModifiers());
		dynamicAttributeModifiers.addAll(builder.getDynamicAttributeModifiers());
		mobEffects.addAll(builder.getMobEffects());
		tooltips.addAll(builder.getTooltips());
		return this;
	}

	public List<AccessoryEffectInstance> getEffects() {
		return effects;
	}

	public List<AttributeOperation> getAttributeModifiers() {
		return attributeModifiers;
	}

	public List<DynamicAttributeOperationInstance> getDynamicAttributeModifiers() {
		return dynamicAttributeModifiers;
	}

	public List<MobEffectInstance> getMobEffects() {
		return mobEffects;
	}

	public List<SerializableTooltip> getTooltips() {
		return tooltips;
	}
}