package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

/**
 * An enum of accessory slots.
 */
public enum AccessorySlot implements StringRepresentable {
	HEAD("head"),
	BODY("body"),
	NECKLACE("necklace"),
	HAND("hand"),
	BRACELET("bracelet"),
	RING("ring"),
	BELT("belt"),
	CHARM("charm"),
	SPIRIT("spirit");

	public static final StringRepresentable.EnumCodec<AccessorySlot> CODEC = StringRepresentable.fromEnum(AccessorySlot::values);

	private final String name;

	AccessorySlot(String name) {
		this.name = name;
	}

	public Component getComponent() {
		return Component.translatable("tooltip.immersiveweapons.accessory.slot." + name);
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}