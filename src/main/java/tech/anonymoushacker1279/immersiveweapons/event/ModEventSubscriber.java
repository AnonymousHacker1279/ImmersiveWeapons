package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.*;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.AbstractFieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.AbstractMinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	/**
	 * Event handler for the EntityAttributeCreationEvent.
	 *
	 * @param event the <code>EntityAttributeCreationEvent</code> instance
	 */
	@SubscribeEvent
	public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
		ImmersiveWeapons.LOGGER.info("Applying entity attributes");

		event.put(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), AbstractDyingSoldierEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.MINUTEMAN_ENTITY.get(), AbstractMinutemanEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.FIELD_MEDIC_ENTITY.get(), AbstractFieldMedicEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), AbstractWanderingWarriorEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.HANS_ENTITY.get(), HansEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), LavaRevenantEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), RockSpiderEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(), CelestialTowerEntity.registerAttributes().build());
	}
}