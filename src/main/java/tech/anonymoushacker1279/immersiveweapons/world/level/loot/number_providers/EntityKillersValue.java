package tech.anonymoushacker1279.immersiveweapons.world.level.loot.number_providers;

import com.mojang.serialization.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import tech.anonymoushacker1279.immersiveweapons.entity.AttackerTracker;

import java.util.stream.Stream;

/// Rolls are based on the number of entities that attacked the entity dropping loot.
///
/// Entities using this loot table must implement [AttackerTracker], otherwise this will do nothing.
public record EntityKillersValue() implements ContextIntProvider {

	public static final MapCodec<EntityKillersValue> MAP_CODEC = new MapCodec<>() {
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

	public static Holder<ContextIntProvider> create() {
		return Holder.direct(new EntityKillersValue());
	}

	@Override
	public void validate(ValidationContext context) {
		// No validation required
	}

	@Override
	public int getIntUnsafe(LootContext lootContext) {
		Entity entity = lootContext.getOptional(LootContextParams.THIS_ENTITY);

		if (entity instanceof AttackerTracker attackerTracker) {
			return attackerTracker.getAttackingEntities();
		}

		return 0;
	}

	@Override
	public MapCodec<? extends ContextIntProvider> codec() {
		return MAP_CODEC;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else {
			return this.getClass() == other.getClass();
		}
	}

	@Override
	public int hashCode() {
		return 0;
	}
}