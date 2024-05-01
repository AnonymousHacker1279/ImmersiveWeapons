package tech.anonymoushacker1279.immersiveweapons.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import tech.anonymoushacker1279.immersiveweapons.init.CriterionTriggerRegistry;

import java.util.Optional;

public class EntityDiscoveredTrigger extends SimpleCriterionTrigger<EntityDiscoveredTrigger.TriggerInstance> {

	@Override
	public Codec<EntityDiscoveredTrigger.TriggerInstance> codec() {
		return EntityDiscoveredTrigger.TriggerInstance.CODEC;
	}

	public void trigger(ServerPlayer serverPlayer, Entity entity) {
		LootContext lootContext = EntityPredicate.createContext(serverPlayer, entity);
		this.trigger(serverPlayer, predicate -> predicate.matches(lootContext));
	}

	public record TriggerInstance(Optional<ContextAwarePredicate> player,
	                              Optional<ContextAwarePredicate> entity) implements SimpleCriterionTrigger.SimpleInstance {

		public static final Codec<EntityDiscoveredTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(EntityDiscoveredTrigger.TriggerInstance::player),
								EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("entity").forGetter(EntityDiscoveredTrigger.TriggerInstance::entity)
						)
						.apply(instance, EntityDiscoveredTrigger.TriggerInstance::new)
		);

		public static Criterion<EntityDiscoveredTrigger.TriggerInstance> discoveredEntity(EntityPredicate entity) {
			return CriterionTriggerRegistry.ENTITY_DISCOVERED_TRIGGER.get()
					.createCriterion(
							new EntityDiscoveredTrigger.TriggerInstance(Optional.empty(), EntityPredicate.wrap(Optional.of(entity)))
					);
		}

		public static Criterion<EntityDiscoveredTrigger.TriggerInstance> discoveredEntity(EntityType<?> entity) {
			return discoveredEntity(EntityPredicate.Builder.entity().of(entity).build());
		}

		public boolean matches(LootContext lootContext) {
			return entity.isPresent() && entity.get().matches(lootContext);
		}
	}
}