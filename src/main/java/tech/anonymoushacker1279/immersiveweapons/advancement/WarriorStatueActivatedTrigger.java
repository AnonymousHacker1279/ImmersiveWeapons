package tech.anonymoushacker1279.immersiveweapons.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class WarriorStatueActivatedTrigger extends SimpleCriterionTrigger<WarriorStatueActivatedTrigger.TriggerInstance> {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "warrior_statue_activated");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	protected WarriorStatueActivatedTrigger.TriggerInstance createInstance(JsonObject pJson,
	                                                                       ContextAwarePredicate predicate,
	                                                                       DeserializationContext pContext) {

		return new WarriorStatueActivatedTrigger.TriggerInstance(predicate);
	}

	/**
	 * Triggers the advancement trigger.
	 *
	 * @param player The affected player.
	 */
	public void trigger(ServerPlayer player) {
		trigger(player, triggerInstance -> true);
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {

		public TriggerInstance(ContextAwarePredicate predicate) {
			super(WarriorStatueActivatedTrigger.ID, predicate);
		}
	}
}