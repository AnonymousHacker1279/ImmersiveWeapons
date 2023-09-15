package tech.anonymoushacker1279.immersiveweapons.entity;

import net.minecraft.world.entity.Entity;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.number_providers.EntityKillersValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Simply keeps a list of entities which have attacked this entity. Provides a method to get the number of entities
 * that dealt some form of damage, used in {@link EntityKillersValue} for loot table functions.
 */
public interface AttackerTracker {

	List<Entity> attackingEntities = new ArrayList<>(5);

	default void attackedByEntity(Entity entity) {
		if (!attackingEntities.contains(entity)) {
			attackingEntities.add(entity);
		}
	}

	default int getAttackingEntities() {
		return attackingEntities.size();
	}
}