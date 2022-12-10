package tech.anonymoushacker1279.immersiveweapons.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class EntityDiscoveredTrigger extends SimpleCriterionTrigger<EntityDiscoveredTrigger.TriggerInstance> {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "entity_discovered");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	protected EntityDiscoveredTrigger.TriggerInstance createInstance(JsonObject pJson,
	                                                                 EntityPredicate.Composite pPlayer,
	                                                                 DeserializationContext pContext) {

		ResourceLocation entityLocation = pJson.has("entity")
				? new ResourceLocation(GsonHelper.getAsString(pJson, "entity")) : null;

		assert entityLocation != null;
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
		public JsonObject serializeToJson(SerializationContext pConditions) {
			JsonObject jsonObject = super.serializeToJson(pConditions);
			jsonObject.addProperty("entity", entityLocation.toString());
			return jsonObject;
		}

		public boolean matches(ResourceLocation entityLocation) {
			return this.entityLocation.equals(entityLocation);
		}
	}
}