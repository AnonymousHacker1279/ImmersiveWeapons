package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectBuilder.EffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.potion.BleedingEffect;
import tech.anonymoushacker1279.immersiveweapons.util.IWCBBridge;

import java.util.*;

public class AccessoryItem extends Item {

	private final AccessorySlot slot;
	private final Map<EffectType, Double> effects;
	private final EffectScalingType effectScalingType;
	private final Map<AttributeModifier, Attribute> attributeModifiers;
	private final Map<Map<AttributeModifier, Attribute>, Double> dynamicAttributeModifiers;
	private final List<MobEffectInstance> mobEffects;

	private static final Map<AttributeModifier, Attribute> GLOBAL_ATTRIBUTE_MODIFIER_MAP = new HashMap<>(5);

	/**
	 * AccessoryItems provide various effects when equipped. There are specific categories they may be placed in, and only
	 * one item from each category may be active at a time.
	 *
	 * @param properties    the <code>Properties</code> for the item
	 * @param slot          the <code>AccessorySlot</code> the item belongs to
	 * @param effectBuilder the <code>EffectBuilder</code> for the item
	 */
	public AccessoryItem(Properties properties, AccessorySlot slot, EffectBuilder effectBuilder) {
		super(properties);

		this.slot = slot;
		this.effects = effectBuilder.getEffects();
		this.effectScalingType = effectBuilder.getScalingType();
		this.attributeModifiers = effectBuilder.getAttributeModifiers();
		this.dynamicAttributeModifiers = effectBuilder.getDynamicAttributeModifiers();
		this.mobEffects = effectBuilder.getMobEffects();

		GLOBAL_ATTRIBUTE_MODIFIER_MAP.putAll(attributeModifiers);
		for (Map<AttributeModifier, Attribute> map : dynamicAttributeModifiers.keySet()) {
			GLOBAL_ATTRIBUTE_MODIFIER_MAP.putAll(map);
		}
	}

	public AccessorySlot getSlot() {
		return slot;
	}

	public Map<EffectType, Double> getEffects() {
		return effects;
	}

	public EffectScalingType getEffectScalingType() {
		return effectScalingType;
	}

	public Map<AttributeModifier, Attribute> getStandardAttributeModifiers() {
		return attributeModifiers;
	}

	public Map<Map<AttributeModifier, Attribute>, Double> getDynamicAttributeModifiers() {
		return dynamicAttributeModifiers;
	}

	public List<MobEffectInstance> getMobEffects() {
		return mobEffects;
	}

	/**
	 * The global attribute modifier map contains all standard and dynamic modifiers for all registered accessories.
	 *
	 * @return the global attribute modifier map
	 */
	public static Map<AttributeModifier, Attribute> getGlobalAttributeModifierMap() {
		return GLOBAL_ATTRIBUTE_MODIFIER_MAP;
	}

	/**
	 * Check if this accessory is active. This should be used for effects that can stack, because it will ensure only one
	 * accessory of its type is active at a time. For non-stacking effects, {@link #isActive(Player)} can be used.
	 *
	 * @param player the <code>Player</code> to check
	 * @param stack  the <code>ItemStack</code> of the accessory
	 * @return true if the accessory is active, false otherwise
	 */
	public boolean isActive(Player player, ItemStack stack) {
		List<ItemStack> accessories = new ArrayList<>(10);

		// Check the player's inventory for accessories of the same type. Only one may be active at a time.
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack1 = player.getInventory().getItem(i);
			if (stack1.getItem() instanceof AccessoryItem accessoryItem) {
				if (accessoryItem.getSlot() == slot) {
					if (!player.getCooldowns().isOnCooldown(this)) {
						accessories.add(stack1);
					}
				}
			}
		}

		if (accessories.isEmpty()) {
			return false;
		}

		// If there are multiple accessories of the same type, only the first one is active.
		return accessories.get(0) == stack;
	}

	/**
	 * Check if this accessory is active. Note, this is not the same as {@link #isActive(Player, ItemStack)}. It is not
	 * sensitive with multiple of the same accessory in the player's inventory.
	 *
	 * @param player the <code>Player</code> to check
	 * @return true if the accessory is active, false otherwise
	 */
	public boolean isActive(Player player) {
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack1 = player.getInventory().getItem(i);
			if (stack1.getItem() instanceof AccessoryItem accessoryItem) {
				if (accessoryItem.getSlot() == slot) {
					if (!player.getCooldowns().isOnCooldown(this)) {
						return accessoryItem == this;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Check if the specified accessory is active for the player. By default, this refers back to {@link #isActive(Player)}.
	 * If IWCB is installed and the Curios plugin is registered, it will defer to IWCB.
	 *
	 * @param player the <code>Player</code> to check
	 * @param item   the <code>AccessoryItem</code> to check
	 * @return true if the accessory is active, false otherwise
	 */
	public static boolean isAccessoryActive(Player player, AccessoryItem item) {
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			return IWCBBridge.isAccessoryActive(player, item);
		} else {
			return item.isActive(player);
		}
	}

	/**
	 * Builder for creating effects.
	 */
	public static class EffectBuilder {

		private final Map<EffectType, Double> effects = new HashMap<>(5);
		private EffectScalingType scalingType = EffectScalingType.NO_SCALING;
		private final Map<AttributeModifier, Attribute> attributeModifiers = new HashMap<>(5);
		private final Map<Map<AttributeModifier, Attribute>, Double> dynamicAttributeModifiers = new HashMap<>(5);
		private final List<MobEffectInstance> mobEffects = new ArrayList<>(5);

		/**
		 * Add an effect to the accessory. See {@link EffectType} for a list of available effects.
		 *
		 * @param type  the <code>EffectType</code>
		 * @param value the value of the effect
		 * @return the <code>EffectBuilder</code> for chaining
		 */
		public EffectBuilder addEffect(EffectType type, double value) {
			effects.put(type, value);
			return this;
		}

		/**
		 * Add an effect to the accessory. See {@link EffectType} for a list of available effects.
		 * Accepts a scaling type, which will be used to scale the effect value based player conditions.
		 *
		 * @param type        the <code>EffectType</code>
		 * @param value       the value of the effect
		 * @param scalingType the <code>EffectScalingType</code>
		 * @return the <code>EffectBuilder</code> for chaining
		 */
		public EffectBuilder addEffect(EffectType type, double value, EffectScalingType scalingType) {
			effects.put(type, value);
			this.scalingType = scalingType;
			return this;
		}

		/**
		 * Add an attribute modifier to the accessory. These are static and unchanging in value.
		 *
		 * @param modifier  the <code>AttributeModifier</code>
		 * @param attribute the <code>Attribute</code>
		 * @return the <code>EffectBuilder</code> for chaining
		 */
		public EffectBuilder addAttributeModifier(AttributeModifier modifier, Attribute attribute) {
			attributeModifiers.put(modifier, attribute);
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
		public EffectBuilder addDynamicAttributeModifier(AttributeModifier modifier, Attribute attribute, double targetValue) {
			dynamicAttributeModifiers.put(Map.of(modifier, attribute), targetValue);
			return this;
		}

		/**
		 * Add a mob effect to the accessory.
		 *
		 * @param effect the <code>MobEffectInstance</code>
		 * @return the <code>EffectBuilder</code> for chaining
		 */
		public EffectBuilder addMobEffect(MobEffectInstance effect) {
			mobEffects.add(effect);
			return this;
		}

		/**
		 * Add all effects from another builder to this builder.
		 *
		 * @param builder the <code>EffectBuilder</code> to add from
		 * @return the <code>EffectBuilder</code> for chaining
		 */
		public EffectBuilder addObjectsFromBuilder(EffectBuilder builder) {
			effects.putAll(builder.getEffects());
			scalingType = builder.getScalingType();
			attributeModifiers.putAll(builder.getAttributeModifiers());
			dynamicAttributeModifiers.putAll(builder.getDynamicAttributeModifiers());
			mobEffects.addAll(builder.getMobEffects());
			return this;
		}

		public Map<EffectType, Double> getEffects() {
			return effects;
		}

		public EffectScalingType getScalingType() {
			return scalingType;
		}

		public Map<AttributeModifier, Attribute> getAttributeModifiers() {
			return attributeModifiers;
		}

		public Map<Map<AttributeModifier, Attribute>, Double> getDynamicAttributeModifiers() {
			return dynamicAttributeModifiers;
		}

		public List<MobEffectInstance> getMobEffects() {
			return mobEffects;
		}

		/**
		 * An enum of scaling types for effects.
		 */
		public enum EffectScalingType {
			/**
			 * The default scaling type, which has no effects on the final value.
			 */
			NO_SCALING,

			/**
			 * Scales the effect value based on the player's depth, beginning at y{@literal <}64.
			 */
			DEPTH_SCALING,

			/**
			 * Scales the effect value based on the player's insomnia value, beginning after 24000 ticks.
			 */
			INSOMNIA_SCALING
		}
	}

	/**
	 * An enum of accessory slots.
	 */
	public enum AccessorySlot {
		HEAD,
		BODY,
		NECKLACE,
		HAND,
		BRACELET,
		RING,
		BELT,
		CHARM,
		SPIRIT
	}

	/**
	 * An enum of accessory effects.
	 */
	public enum EffectType {
		/**
		 * Chance for firearms to not consume ammo.
		 */
		FIREARM_AMMO_CONSERVATION_CHANCE,

		/**
		 * Modifier for reload time for firearms.
		 */
		FIREARM_RELOAD_SPEED,

		/**
		 * Modifier for melee damage.
		 */
		MELEE_DAMAGE,

		/**
		 * Modifier for projectile damage.
		 */
		PROJECTILE_DAMAGE,

		/**
		 * Modifier to all outgoing damage sources.
		 */
		GENERAL_DAMAGE,

		/**
		 * Modifier to all incoming damage sources.
		 */
		DAMAGE_RESISTANCE,

		/**
		 * Modifier to melee knockback.
		 */
		MELEE_KNOCKBACK,

		/**
		 * Chance for melee attacks to inflict {@link BleedingEffect}.
		 */
		MELEE_BLEED_CHANCE,

		/**
		 * Modifier to melee critical damage. Additive with vanilla critical damage, which is 50% by default.
		 * For example, a value of 0.5d will result in 100% critical damage.
		 */
		MELEE_CRIT_DAMAGE_BONUS,

		/**
		 * Chance for any melee attack to become critical, regardless of vanilla critical hit conditions.
		 */
		MELEE_CRIT_CHANCE,

		/**
		 * Chance for {@link BleedingEffect} to be cancelled each damage tick.
		 */
		BLEED_CANCEL_CHANCE,

		/**
		 * Modifier to {@link BleedingEffect} damage.
		 */
		BLEED_RESISTANCE,

		/**
		 * Chance for attacks to inflict {@link MobEffects#WITHER}.
		 */
		GENERAL_WITHER_CHANCE,

		/**
		 * Modifier for experience drops.
		 */
		EXPERIENCE_MODIFIER
	}
}