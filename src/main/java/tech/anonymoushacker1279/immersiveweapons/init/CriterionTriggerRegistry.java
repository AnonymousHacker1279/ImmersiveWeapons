package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.advancement.EntityDiscoveredTrigger;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class CriterionTriggerRegistry {

	// Criterion Trigger Register
	public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPE = DeferredRegister.create(Registries.TRIGGER_TYPE, ImmersiveWeapons.MOD_ID);

	// Criterion Triggers
	public static final Supplier<EntityDiscoveredTrigger> ENTITY_DISCOVERED_TRIGGER = TRIGGER_TYPE.register("entity_discovered", EntityDiscoveredTrigger::new);
}