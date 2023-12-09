package tech.anonymoushacker1279.immersiveweapons.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import tech.anonymoushacker1279.immersiveweapons.init.CriterionTriggerRegistry;

import java.util.Optional;

public class WarriorStatueActivatedTrigger extends SimpleCriterionTrigger<WarriorStatueActivatedTrigger.TriggerInstance> {

	@Override
	public Codec<WarriorStatueActivatedTrigger.TriggerInstance> codec() {
		return WarriorStatueActivatedTrigger.TriggerInstance.CODEC;
	}

	public void trigger(ServerPlayer player) {
		trigger(player, triggerInstance -> true);
	}

	public record TriggerInstance(
			Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {

		public static final Codec<WarriorStatueActivatedTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(WarriorStatueActivatedTrigger.TriggerInstance::player)
						)
						.apply(instance, WarriorStatueActivatedTrigger.TriggerInstance::new)
		);

		public static Criterion<WarriorStatueActivatedTrigger.TriggerInstance> statueActivated() {
			return CriterionTriggerRegistry.WARRIOR_STATUE_ACTIVATED_TRIGGER.get()
					.createCriterion(
							new WarriorStatueActivatedTrigger.TriggerInstance(Optional.empty())
					);
		}
	}
}