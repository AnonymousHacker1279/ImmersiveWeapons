package tech.anonymoushacker1279.immersiveweapons.entity.vehicle;

import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public enum CustomBoatType {
	BURNED_OAK(DeferredRegistryHandler.BURNED_OAK_PLANKS.get(), "burned_oak"),
	BURNED_OAK_CHEST(DeferredRegistryHandler.BURNED_OAK_PLANKS.get(), "burned_oak_chest");

	private final Block block;
	private final String name;

	CustomBoatType(Block block, String name) {
		this.block = block;
		this.name = name;
	}

	public static CustomBoatType byId(int id) {
		CustomBoatType[] type = values();
		if (id < 0 || id >= type.length) {
			id = 0;
		}

		return type[id];
	}

	public static CustomBoatType getTypeFromString(String typeName) {
		CustomBoatType[] type = values();

		for (CustomBoatType customBoatType : type) {
			if (customBoatType.getName().equals(typeName)) {
				return customBoatType;
			}
		}

		return type[0];
	}

	public Block getBlock() {
		return block;
	}

	public String getName() {
		return name;
	}
}