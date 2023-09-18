package tech.anonymoushacker1279.immersiveweapons.entity;

import net.minecraft.world.entity.Entity;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.number_providers.EntityKillersValue;

import java.util.List;

/**
 * Simply keeps a list of entities which have attacked this entity. Provides a method to get the number of entities
 * that dealt some form of damage, used in {@link EntityKillersValue} for loot table functions.
 */
public interface AttackerTracker {

	default void attackedByEntity(Entity entity, List<Entity> attackingEntities) {
		if (!attackingEntities.contains(entity)) {
			attackingEntities.add(entity);
		}
	}

	int getAttackingEntities();
}