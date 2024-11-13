package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import tech.anonymoushacker1279.immersiveweapons.init.AccessoryEffectScalingTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.AccessoryEffectTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.*;

import java.util.*;

/**
 * Builder for creating accessory effects.
 */
public class AccessoryEffectBuilder {

	private final List<AccessoryEffectInstance> effects = new ArrayList<>(5);
	private Map<String, AccessoryEffectScalingType> effectScalingTypes = new HashMap<>(5);
	private final List<AttributeOperation> attributeModifiers = new ArrayList<>(5);
	private final List<DynamicAttributeOperationInstance> dynamicAttributeModifiers = new ArrayList<>(5);
	private final List<MobEffectInstance> mobEffects = new ArrayList<>(5);

	/**
	 * Add an effect to the accessory. See {@link AccessoryEffectTypeRegistry} for a list of available effects.
	 *
	 * @param type  the <code>AccessoryEffectType</code>
	 * @param value the value of the effect
	 * @return the <code>EffectBuilder</code> for chaining
	 */
	public AccessoryEffectBuilder addEffect(AccessoryEffectType type, double value) {
		effects.add(new AccessoryEffectInstance(type, value));
		effectScalingTypes.put(type.name(), AccessoryEffectScalingTypeRegistry.NONE.get());
		return this;
	}

	/**
	 * Add an effect to the accessory. See {@link AccessoryEffectTypeRegistry} for a list of available effects.
	 * Accepts a scaling type, which will be used to scale the effect value based player conditions.
	 *
	 * @param type        the <code>AccessoryEffectType</code>
	 * @param value       the value of the effect
	 * @param scalingType the <code>AccessoryEffectScalingType</code> to use
	 * @return the <code>EffectBuilder</code> for chaining
	 */
	public AccessoryEffectBuilder addEffect(AccessoryEffectType type, double value, AccessoryEffectScalingType scalingType) {
		effects.add(new AccessoryEffectInstance(type, value));
		effectScalingTypes.put(type.name(), scalingType);
		return this;
	}

	/**
	 * Add an attribute modifier to the accessory. These are static and unchanging in value.
	 *
	 * @param modifier  the <code>AttributeModifier</code>
	 * @param attribute the <code>Attribute</code>
	 * @return the <code>EffectBuilder</code> for chaining
	 */
	public AccessoryEffectBuilder addAttributeModifier(AttributeModifier modifier, Holder<Attribute> attribute) {
		attributeModifiers.add(new AttributeOperation(modifier, attribute));
		return this;
	}

	/**
	 * Add a dynamic attribute modifier to the accessory. These are reconstructed as necessary to achieve the
	 * target value.
	 *
	 * @param modifier    the <code>AttributeModifier</code>
	 * @param attribute   the <code>Attribute</code>
	 * @param targetValue the target value of the attribute
	 * @return the <code>EffectBuilder</code> for chaining
	 */
	public AccessoryEffectBuilder addDynamicAttributeModifier(AttributeModifier modifier, Holder<Attribute> attribute, double targetValue) {
		dynamicAttributeModifiers.add(new DynamicAttributeOperationInstance(new AttributeOperation(modifier, attribute), targetValue));
		return this;
	}

	/**
	 * Add a mob effect to the accessory.
	 *
	 * @param effect the <code>MobEffectInstance</code>
	 * @return the <code>EffectBuilder</code> for chaining
	 */
	public AccessoryEffectBuilder addMobEffect(MobEffectInstance effect) {
		mobEffects.add(effect);
		return this;
	}

	/**
	 * Add all effects from another builder to this builder.
	 *
	 * @param builder the <code>EffectBuilder</code> to add from
	 * @return the <code>EffectBuilder</code> for chaining
	 */
	public AccessoryEffectBuilder addObjectsFromBuilder(AccessoryEffectBuilder builder) {
		effects.addAll(builder.getEffects());
		effectScalingTypes.putAll(builder.getEffectScalingTypes());
		attributeModifiers.addAll(builder.getAttributeModifiers());
		dynamicAttributeModifiers.addAll(builder.getDynamicAttributeModifiers());
		mobEffects.addAll(builder.getMobEffects());
		return this;
	}

	public List<AccessoryEffectInstance> getEffects() {
		return effects;
	}

	public Map<String, AccessoryEffectScalingType> getEffectScalingTypes() {
		return effectScalingTypes;
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
}