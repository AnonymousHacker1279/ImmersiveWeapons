package tech.anonymoushacker1279.immersiveweapons.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class WarriorStatueActivatedTrigger extends SimpleCriterionTrigger<WarriorStatueActivatedTrigger.TriggerInstance> {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "warrior_statue_activated");

	@Override
	public @NotNull ResourceLocation getId() {
		return ID;
	}

	@Override
	protected WarriorStatueActivatedTrigger.@NotNull TriggerInstance createInstance(@NotNull JsonObject pJson,
	                                                                                EntityPredicate.@NotNull Composite pPlayer,
	                                                                                @NotNull DeserializationContext pContext) {

		return new WarriorStatueActivatedTrigger.TriggerInstance(pPlayer);
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

		public TriggerInstance(EntityPredicate.Composite pPlayer) {
			super(WarriorStatueActivatedTrigger.ID, pPlayer);
		}
	}
}