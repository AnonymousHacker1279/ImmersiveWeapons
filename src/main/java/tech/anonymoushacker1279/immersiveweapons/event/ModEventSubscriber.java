package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.config.ConfigHelper.TomlConfigOps;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.FireflyEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.animal.StarWolfEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.*;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.AbstractFieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.AbstractMinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkeletonMerchantEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkygazerEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

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

		event.put(EntityRegistry.DYING_SOLDIER_ENTITY.get(), AbstractDyingSoldierEntity.registerAttributes().build());
		event.put(EntityRegistry.MINUTEMAN_ENTITY.get(), AbstractMinutemanEntity.registerAttributes().build());
		event.put(EntityRegistry.FIELD_MEDIC_ENTITY.get(), AbstractFieldMedicEntity.registerAttributes().build());
		event.put(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), AbstractWanderingWarriorEntity.registerAttributes().build());
		event.put(EntityRegistry.HANS_ENTITY.get(), HansEntity.registerAttributes().build());
		event.put(EntityRegistry.LAVA_REVENANT_ENTITY.get(), LavaRevenantEntity.registerAttributes().build());
		event.put(EntityRegistry.ROCK_SPIDER_ENTITY.get(), RockSpiderEntity.registerAttributes().build());
		event.put(EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), CelestialTowerEntity.registerAttributes().build());
		event.put(EntityRegistry.STARMITE_ENTITY.get(), StarmiteEntity.createAttributes().build());
		event.put(EntityRegistry.FIREFLY_ENTITY.get(), FireflyEntity.createAttributes().build());
		event.put(EntityRegistry.STORM_CREEPER_ENTITY.get(), StormCreeperEntity.createAttributes().build());
		event.put(EntityRegistry.EVIL_EYE_ENTITY.get(), EvilEyeEntity.registerAttributes().build());
		event.put(EntityRegistry.STAR_WOLF_ENTITY.get(), StarWolfEntity.createAttributes().build());
		event.put(EntityRegistry.SKYGAZER_ENTITY.get(), SkygazerEntity.createMobAttributes().build());
		event.put(EntityRegistry.SKELETON_MERCHANT_ENTITY.get(), SkeletonMerchantEntity.createMobAttributes().build());
	}

	@SubscribeEvent
	public static void modConfigReloadEvent(ModConfigEvent.Reloading event) {
		ImmersiveWeapons.LOGGER.info("Reloading configs");

		// Populate enchant cap map
		TomlConfigOps.INSTANCE.getMapValues(ImmersiveWeapons.COMMON_CONFIG.skygazerEnchantCaps().get())
				.result()
				.ifPresent(map -> map.forEach((pair) -> SkygazerEntity.ENCHANT_CAPS.put((String) pair.getFirst(), (Integer) pair.getSecond())));
	}
}