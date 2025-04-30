package tech.anonymoushacker1279.immersiveweapons.world.level.loot.number_providers;

import com.mojang.serialization.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import tech.anonymoushacker1279.immersiveweapons.entity.AttackerTracker;
import tech.anonymoushacker1279.immersiveweapons.init.NumberProviderRegistry;

import java.util.stream.Stream;

/**
 * Rolls are based on the number of entities that attacked the entity dropping loot.
 * <p>
 * Entities using this loot table must implement {@link AttackerTracker}, otherwise this will do nothing.
 */
public record EntityKillersValue() implements NumberProvider {

	public static final MapCodec<EntityKillersValue> CODEC = new MapCodec<>() {
		@Override
		public <T> Stream<T> keys(DynamicOps<T> ops) {
			return Stream.empty();
		}

		@Override
		public <T> DataResult<EntityKillersValue> decode(DynamicOps<T> ops, MapLike<T> input) {
			return DataResult.success(new EntityKillersValue());
		}

		@Override
		public <T> RecordBuilder<T> encode(EntityKillersValue input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
			return prefix;
		}

		@Override
		public String toString() {
			return "EntityKillersValue";
		}
	};

	public LootNumberProviderType getType() {
		return NumberProviderRegistry.ENTITY_KILLERS.get();
	}

	public float getFloat(LootContext lootContext) {
		Entity entity = lootContext.getParameter(LootContextParams.THIS_ENTITY);

		if (entity instanceof AttackerTracker attackerTracker) {
			return attackerTracker.getAttackingEntities();
		}

		return 0;
	}

	public static EntityKillersValue create() {
		return new EntityKillersValue();
	}

	@Override
	public boolean equals(Object pOther) {
		if (this == pOther) {
			return true;
		} else {
			return pOther != null && this.getClass() == pOther.getClass();
		}
	}

	@Override
	public int hashCode() {
		return 0;
	}
}