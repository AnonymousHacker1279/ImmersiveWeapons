package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.world.entity.EntityType;

public class BlockPredicates {

	public static Boolean ocelotOrParrot(EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}

	public static boolean never() {
		return false;
	}
}