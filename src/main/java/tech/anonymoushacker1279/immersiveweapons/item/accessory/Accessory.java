package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AccessoryEffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;
import tech.anonymoushacker1279.immersiveweapons.util.IWCBBridge;

import java.util.*;

public record Accessory(AccessorySlot slot,
                        List<AccessoryEffectInstance> effects,
                        Map<String, AccessoryEffectScalingType> effectScalingTypes,
                        List<AttributeOperation> attributeModifiers,
                        List<DynamicAttributeOperationInstance> dynamicAttributeModifiers,
                        List<MobEffectInstance> mobEffectInstances) {

	public static final Codec<Accessory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			AccessorySlot.CODEC.fieldOf("slot").forGetter(Accessory::slot),
			Codec.list(AccessoryEffectInstance.CODEC).fieldOf("effects").forGetter(Accessory::effects),
			Codec.unboundedMap(Codec.STRING, AccessoryEffectScalingType.CODEC).fieldOf("effect_scaling_types").forGetter(Accessory::effectScalingTypes),
			Codec.list(AttributeOperation.CODEC).fieldOf("attribute_modifiers").forGetter(Accessory::attributeModifiers),
			Codec.list(DynamicAttributeOperationInstance.CODEC).fieldOf("dynamic_attribute_modifiers").forGetter(Accessory::dynamicAttributeModifiers),
			Codec.list(MobEffectInstance.CODEC).fieldOf("mob_effects").forGetter(Accessory::mobEffectInstances)
	).apply(instance, Accessory::new));

	private static final Set<AttributeOperation> GLOBAL_ATTRIBUTE_MODIFIER_MAP = new HashSet<>(10);

	public static final DataMapType<Item, Accessory> ACCESSORY = DataMapType.builder(
					ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "accessory"),
					Registries.ITEM, CODEC)
			.synced(CODEC, true)
			.build();

	public Accessory(AccessorySlot slot, AccessoryEffectBuilder builder) {
		this(slot, builder.getEffects(), builder.getEffectScalingTypes(), builder.getAttributeModifiers(), builder.getDynamicAttributeModifiers(), builder.getMobEffects());
	}

	public Accessory(AccessorySlot slot, List<AccessoryEffectInstance> effects, Map<String, AccessoryEffectScalingType> effectScalingTypes, List<AttributeOperation> attributeModifiers, List<DynamicAttributeOperationInstance> dynamicAttributeModifiers, List<MobEffectInstance> mobEffectInstances) {
		this.slot = slot;
		this.effects = effects;
		this.effectScalingTypes = effectScalingTypes;
		this.attributeModifiers = attributeModifiers;
		this.dynamicAttributeModifiers = dynamicAttributeModifiers;
		this.mobEffectInstances = mobEffectInstances;

		GLOBAL_ATTRIBUTE_MODIFIER_MAP.addAll(attributeModifiers);
		GLOBAL_ATTRIBUTE_MODIFIER_MAP.addAll(dynamicAttributeModifiers.stream()
				.map(DynamicAttributeOperationInstance::attributeOperation)
				.toList());
	}

	/**
	 * The global attribute modifier map contains all standard and dynamic modifiers for all registered accessories.
	 *
	 * @return the global attribute modifier map
	 */
	public static Set<AttributeOperation> getGlobalAttributeModifiers() {
		return GLOBAL_ATTRIBUTE_MODIFIER_MAP;
	}

	/**
	 * Check if this accessory is active.
	 *
	 * @param player the <code>Player</code> to check
	 * @return true if the accessory is active, false otherwise
	 */
	public boolean isActive(Player player, ItemStack accessoryStack, AccessorySlot slot) {
		List<ItemStack> accessories = new ArrayList<>(10);

		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			Accessory accessory = stack.getItemHolder().getData(Accessory.ACCESSORY);
			if (accessory != null && accessory.slot() == slot) {
				if (!player.getCooldowns().isOnCooldown(stack)) {
					accessories.add(stack);
				}
			}
		}

		if (accessories.isEmpty()) {
			return false;
		}

		return accessories.getFirst().getItem() == accessoryStack.getItem();
	}

	/**
	 * Check if the specified accessory is active for the player. If IWCB is installed and the Curios plugin is
	 * registered, it will defer to IWCB.
	 *
	 * @param player the <code>Player</code> to check
	 * @param stack  the <code>ItemStack</code> to check
	 * @return true if the accessory is active, false otherwise
	 */
	public static boolean isAccessoryActive(Player player, ItemStack stack) {
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			return IWCBBridge.isAccessoryActive(player, stack);
		} else {
			Accessory accessory = stack.getItemHolder().getData(Accessory.ACCESSORY);
			if (accessory != null) {
				return accessory.isActive(player, stack, accessory.slot());
			}
		}

		return false;
	}

	public static boolean isAccessoryActive(Player player, Item item) {
		return isAccessoryActive(player, new ItemStack(item));
	}

	public double getEffectValue(AccessoryEffectType type) {
		return effects.stream()
				.filter(effect -> effect.type().name().equals(type.name()))
				.mapToDouble(AccessoryEffectInstance::value)
				.sum();
	}
}