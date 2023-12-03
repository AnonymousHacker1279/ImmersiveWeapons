package tech.anonymoushacker1279.immersiveweapons.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWCriteriaTriggers {

	public static final EntityDiscoveredTrigger ENTITY_DISCOVERED_TRIGGER = CriteriaTriggers.register("entity_discovered", new EntityDiscoveredTrigger());
	public static final WarriorStatueActivatedTrigger WARRIOR_STATUE_ACTIVATED_TRIGGER = CriteriaTriggers.register("warrior_statue_activated", new WarriorStatueActivatedTrigger());

	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing advancement criteria triggers");
	}
}