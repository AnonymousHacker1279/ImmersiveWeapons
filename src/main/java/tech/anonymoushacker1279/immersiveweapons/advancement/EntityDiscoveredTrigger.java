package tech.anonymoushacker1279.immersiveweapons.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Optional;

public class EntityDiscoveredTrigger extends SimpleCriterionTrigger<EntityDiscoveredTrigger.TriggerInstance> {

	@Override
	protected EntityDiscoveredTrigger.TriggerInstance createInstance(JsonObject pJson,
	                                                                 Optional<ContextAwarePredicate> predicate,
	                                                                 DeserializationContext pContext) {

		Optional<ContextAwarePredicate> entity = EntityPredicate.fromJson(pJson, "entity", pContext);

		return new EntityDiscoveredTrigger.TriggerInstance(predicate, entity);
	}

	public static Criterion<EntityDiscoveredTrigger.TriggerInstance> create(EntityType<?> entityType) {
		return IWCriteriaTriggers.ENTITY_DISCOVERED_TRIGGER.createCriterion(
				new EntityDiscoveredTrigger.TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap(Builder.entity().of(entityType).build())))
		);
	}

	/**
	 * Triggers the advancement trigger.
	 *
	 * @param player           The affected player.
	 * @param triggeringEntity The entity triggering the advancement.
	 */
	public void trigger(ServerPlayer player, Entity triggeringEntity) {
		trigger(player, triggerInstance -> triggerInstance.matches(EntityPredicate.createContext(player, triggeringEntity)));
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {

		private final Optional<ContextAwarePredicate> entityPredicate;

		public TriggerInstance(Optional<ContextAwarePredicate> predicate, Optional<ContextAwarePredicate> entity) {
			super(predicate);
			this.entityPredicate = entity;
		}

		public boolean matches(LootContext entityContext) {
			return entityPredicate.isPresent() && entityPredicate.get().matches(entityContext);
		}
	}
}