package tech.anonymoushacker1279.immersiveweapons.world.level.loot.number_providers;

import com.google.gson.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import tech.anonymoushacker1279.immersiveweapons.entity.AttackerTracker;
import tech.anonymoushacker1279.immersiveweapons.init.NumberProviderRegistry;

/**
 * Rolls are based on the number of entities that attacked the entity dropping loot.
 * <p>
 * Entities using this loot table must implement {@link AttackerTracker}, otherwise this will do nothing.
 */
public class EntityKillersValue implements NumberProvider {

	EntityKillersValue() {
	}

	public LootNumberProviderType getType() {
		return NumberProviderRegistry.ENTITY_KILLERS.get();
	}

	public float getFloat(LootContext lootContext) {
		Entity entity = lootContext.getParam(LootContextParams.THIS_ENTITY);

		if (entity instanceof AttackerTracker attackerTracker) {
			return attackerTracker.getAttackingEntities();
		}

		return 0;
	}

	public static EntityKillersValue create() {
		return new EntityKillersValue();
	}

	public boolean equals(Object pOther) {
		if (this == pOther) {
			return true;
		} else {
			return pOther != null && this.getClass() == pOther.getClass();
		}
	}

	public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<EntityKillersValue> {

		public void serialize(JsonObject jsonObject, EntityKillersValue value, JsonSerializationContext context) {
		}

		public EntityKillersValue deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
			return new EntityKillersValue();
		}
	}
}