package tech.anonymoushacker1279.immersiveweapons.api.events;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.ApiStatus;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;

import java.util.ArrayList;
import java.util.List;

/// Handles accessory collection events. These are fired by IW when collecting accessory effects, attributes, etc. and
/// can be listened to by plugins to modify the results.
public class AccessoryEvent extends Event {

	private final Player player;

	/// Parent class for all accessory collection events.
	@ApiStatus.Internal
	AccessoryEvent(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public static class CollectEffects extends AccessoryEvent {

		private final AccessoryEffectType type;
		private double effect = 0.0d;

		/// Collect generic effects.
		///
		/// @param player the player to collect from
		/// @param type   the type of effect to collect
		@ApiStatus.AvailableSince("1.37.0")
		public CollectEffects(Player player, AccessoryEffectType type) {
			super(player);
			this.type = type;
		}

		public AccessoryEffectType getType() {
			return type;
		}

		public double getEffect() {
			return effect;
		}

		public void setEffect(double effect) {
			this.effect = effect;
		}
	}

	public static class CollectStandardAttributes extends AccessoryEvent {

		private final List<AttributeOperation> attributes = new ArrayList<>();

		/// Collect attribute modifiers.
		///
		/// @param player the player to collect from
		@ApiStatus.AvailableSince("1.37.0")
		public CollectStandardAttributes(Player player) {
			super(player);
		}

		public List<AttributeOperation> getAttributes() {
			return attributes;
		}

		public void addAttribute(AttributeOperation attribute) {
			this.attributes.add(attribute);
		}

		public void addAttributes(List<AttributeOperation> attributes) {
			this.attributes.addAll(attributes);
		}

		public void removeAttribute(AttributeOperation attribute) {
			this.attributes.remove(attribute);
		}

		public void removeAttributes(List<AttributeOperation> attributes) {
			this.attributes.removeAll(attributes);
		}
	}

	public static class CollectDynamicAttributes extends AccessoryEvent {

		private final List<DynamicAttributeOperationInstance> attributes = new ArrayList<>();

		/// Collect dynamic attribute modifiers.
		///
		/// @param player the player to collect from
		@ApiStatus.AvailableSince("1.37.0")
		public CollectDynamicAttributes(Player player) {
			super(player);
		}

		public List<DynamicAttributeOperationInstance> getAttributes() {
			return attributes;
		}

		public void addAttribute(DynamicAttributeOperationInstance attribute) {
			this.attributes.add(attribute);
		}

		public void addAttributes(List<DynamicAttributeOperationInstance> attributes) {
			this.attributes.addAll(attributes);
		}

		public void removeAttribute(DynamicAttributeOperationInstance attribute) {
			this.attributes.remove(attribute);
		}

		public void removeAttributes(List<DynamicAttributeOperationInstance> attributes) {
			this.attributes.removeAll(attributes);
		}
	}

	public static class CollectMobEffects extends AccessoryEvent {

		private final List<MobEffectInstance> effects = new ArrayList<>();

		/// Collect mob effects.
		///
		/// @param player the player to collect from
		@ApiStatus.AvailableSince("1.37.0")
		public CollectMobEffects(Player player) {
			super(player);
		}

		public List<MobEffectInstance> getEffects() {
			return effects;
		}

		public void addEffect(MobEffectInstance effect) {
			this.effects.add(effect);
		}

		public void addEffects(List<MobEffectInstance> effects) {
			this.effects.addAll(effects);
		}

		public void removeEffect(MobEffectInstance effect) {
			this.effects.remove(effect);
		}
	}

	public static class AccessoryActive extends AccessoryEvent {

		private final Accessory accessory;
		private final ItemStack stack;
		private boolean active = false;

		/// Determine if an accessory is currently active.
		public AccessoryActive(Player player, Accessory accessory, ItemStack stack) {
			super(player);
			this.accessory = accessory;
			this.stack = stack;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public Accessory getAccessory() {
			return accessory;
		}

		public ItemStack getStack() {
			return stack;
		}
	}
}