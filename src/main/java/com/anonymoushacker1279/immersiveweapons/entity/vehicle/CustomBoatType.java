package com.anonymoushacker1279.immersiveweapons.entity.vehicle;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

public class CustomBoatType {
	private static final List<CustomBoatType> VALUES = new ArrayList<>(1);
	private final int id;
	private final String name;
	private ItemLike boatItem;

	private CustomBoatType(int id, String name, ResourceLocation texture) {
		this.id = id;
		this.name = name;
	}

	public static CustomBoatType register(String name) {
		CustomBoatType type = new CustomBoatType(VALUES.size(), name,
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/boat/" + name + ".png")
		);
		VALUES.add(type);
		return type;
	}

	public static CustomBoatType byId(int integer) {
		return VALUES.get(integer);
	}

	public static CustomBoatType getTypeFromString(String typeName) {
		for (CustomBoatType type : VALUES) {
			if (type.getName().equals(typeName)) {
				return type;
			}
		}
		return VALUES.get(0);
	}

	public int getId() {
		return id;
	}

	public ItemLike getBoatItem() {
		return boatItem;
	}

	public void setBoatItem(ItemLike boatItem) {
		this.boatItem = boatItem;
	}

	public String getName() {
		return name;
	}
}