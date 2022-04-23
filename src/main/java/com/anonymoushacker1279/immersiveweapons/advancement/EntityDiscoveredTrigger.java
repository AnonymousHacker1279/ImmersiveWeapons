package com.anonymoushacker1279.immersiveweapons.advancement;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

public class EntityDiscoveredTrigger extends SimpleCriterionTrigger<EntityDiscoveredTrigger.TriggerInstance> {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "entity_discovered");

	@Override
	public @NotNull ResourceLocation getId() {
		return ID;
	}

	@Override
	protected EntityDiscoveredTrigger.@NotNull TriggerInstance createInstance(@NotNull JsonObject pJson,
	                                                                          EntityPredicate.@NotNull Composite pPlayer,
	                                                                          @NotNull DeserializationContext pContext) {

		ResourceLocation entityLocation = pJson.has("entity")
				? new ResourceLocation(GsonHelper.getAsString(pJson, "entity")) : null;

		return new EntityDiscoveredTrigger.TriggerInstance(pPlayer, entityLocation);
	}

	/**
	 * Triggers the advancement trigger.
	 *
	 * @param player           The affected player.
	 * @param triggeringEntity The entity triggering the advancement.
	 */
	public void trigger(ServerPlayer player, ResourceLocation triggeringEntity) {
		trigger(player, triggerInstance -> triggerInstance.matches(triggeringEntity));
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {

		private final ResourceLocation entityLocation;

		public TriggerInstance(EntityPredicate.Composite pPlayer, ResourceLocation entityLocation) {
			super(EntityDiscoveredTrigger.ID, pPlayer);
			this.entityLocation = entityLocation;
		}

		@Override
		public @NotNull JsonObject serializeToJson(@NotNull SerializationContext pConditions) {
			JsonObject jsonObject = super.serializeToJson(pConditions);
			if (entityLocation != null) {
				jsonObject.addProperty("entity", entityLocation.toString());
			}
			return jsonObject;
		}

		public boolean matches(ResourceLocation entityLocation) {
			return this.entityLocation == null || this.entityLocation.equals(entityLocation);
		}
	}
}