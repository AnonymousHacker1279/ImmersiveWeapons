package tech.anonymoushacker1279.immersiveweapons.entity.vehicle;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.utility.CustomBoatItem;

import java.util.function.Supplier;

public enum CustomBoatType {
	BURNED_OAK(BlockRegistry.BURNED_OAK_PLANKS.get(), ItemRegistry.BURNED_OAK_BOAT, EntityRegistry.BURNED_OAK_BOAT_ENTITY.get(), "burned_oak"),
	BURNED_OAK_CHEST(BlockRegistry.BURNED_OAK_PLANKS.get(), ItemRegistry.BURNED_OAK_CHEST_BOAT, EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get(), "burned_oak"),
	STARDUST(BlockRegistry.STARDUST_PLANKS.get(), ItemRegistry.STARDUST_BOAT, EntityRegistry.STARDUST_BOAT_ENTITY.get(), "stardust"),
	STARDUST_CHEST(BlockRegistry.STARDUST_PLANKS.get(), ItemRegistry.STARDUST_CHEST_BOAT, EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get(), "stardust");

	private final Block block;
	private final Supplier<CustomBoatItem> dropItem;
	private final EntityType<? extends CustomBoatEntity> entityType;
	private final String name;

	CustomBoatType(Block block, Supplier<CustomBoatItem> dropItem, EntityType<? extends CustomBoatEntity> entityType, String name) {
		this.block = block;
		this.dropItem = dropItem;
		this.entityType = entityType;
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

	public Item getDropItem() {
		return dropItem.get();
	}

	public EntityType<? extends CustomBoatEntity> getEntityType() {
		return entityType;
	}

	public String getName() {
		return name;
	}
}