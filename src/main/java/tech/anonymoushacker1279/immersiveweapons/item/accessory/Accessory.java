package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;
import tech.anonymoushacker1279.immersiveweapons.util.IWCBBridge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public record Accessory(Holder<Item> item,
                        AccessorySlot slot,
                        List<AccessoryEffectInstance> effects,
                        List<AttributeOperation> attributeModifiers,
                        List<DynamicAttributeOperationInstance> dynamicAttributeModifiers,
                        List<MobEffectInstance> mobEffectInstances) {

	public static final Codec<Accessory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Item.CODEC.fieldOf("item").forGetter(Accessory::item),
			AccessorySlot.CODEC.fieldOf("slot").forGetter(Accessory::slot),
			Codec.list(AccessoryEffectInstance.CODEC).fieldOf("effects").forGetter(Accessory::effects),
			Codec.list(AttributeOperation.CODEC).fieldOf("attribute_modifiers").forGetter(Accessory::attributeModifiers),
			Codec.list(DynamicAttributeOperationInstance.CODEC).fieldOf("dynamic_attribute_modifiers").forGetter(Accessory::dynamicAttributeModifiers),
			Codec.list(MobEffectInstance.CODEC).fieldOf("mob_effects").forGetter(Accessory::mobEffectInstances)
	).apply(instance, Accessory::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, Accessory> STREAM_CODEC = StreamCodec.composite(
			Item.STREAM_CODEC,
			Accessory::item,
			AccessorySlot.STREAM_CODEC,
			Accessory::slot,
			AccessoryEffectInstance.STREAM_CODEC.apply(ByteBufCodecs.list()),
			Accessory::effects,
			AttributeOperation.STREAM_CODEC.apply(ByteBufCodecs.list()),
			Accessory::attributeModifiers,
			DynamicAttributeOperationInstance.STREAM_CODEC.apply(ByteBufCodecs.list()),
			Accessory::dynamicAttributeModifiers,
			MobEffectInstance.STREAM_CODEC.apply(ByteBufCodecs.list()),
			Accessory::mobEffectInstances,
			Accessory::new
	);

	private static final Set<AttributeOperation> GLOBAL_ATTRIBUTE_MODIFIER_MAP = new HashSet<>(10);

	public Accessory(Holder<Item> item, AccessorySlot slot, AccessoryEffectBuilder builder) {
		this(item, slot, builder.getEffects(), builder.getAttributeModifiers(), builder.getDynamicAttributeModifiers(), builder.getMobEffects());
	}

	public Accessory(Holder<Item> item, AccessorySlot slot, List<AccessoryEffectInstance> effects, List<AttributeOperation> attributeModifiers, List<DynamicAttributeOperationInstance> dynamicAttributeModifiers, List<MobEffectInstance> mobEffectInstances) {
		this.item = item;
		this.slot = slot;
		this.effects = effects;
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
			Accessory accessory = AccessoryLoader.ACCESSORIES.get(stack.getItem());
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
			Accessory accessory = AccessoryLoader.ACCESSORIES.get(stack.getItem());
			if (accessory != null) {
				return accessory.isActive(player, stack, accessory.slot());
			}
		}

		return false;
	}

	public static boolean isAccessoryActive(Player player, Item item) {
		return isAccessoryActive(player, new ItemStack(item));
	}

	public double getBaseEffectValue(AccessoryEffectType type) {
		for (AccessoryEffectInstance effect : effects) {
			if (effect.type().equals(type)) {
				return effect.value();
			}
		}

		return 0.0;
	}

	public double getEffectValue(AccessoryEffectType type, Player player) {
		for (AccessoryEffectInstance effect : effects) {
			if (effect.type().equals(type)) {
				AtomicReference<Double> value = new AtomicReference<>(effect.value());
				effect.scalingType()
						.ifPresent(scaling -> value.set(scaling.getEffectValue(this, type, player)));

				return value.get();
			}
		}

		return 0.0;
	}
}