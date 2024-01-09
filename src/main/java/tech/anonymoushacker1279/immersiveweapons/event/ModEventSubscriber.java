package tech.anonymoushacker1279.immersiveweapons.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
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
import tech.anonymoushacker1279.immersiveweapons.network.handler.*;
import tech.anonymoushacker1279.immersiveweapons.network.handler.star_forge.StarForgeMenuPayloadHandler;
import tech.anonymoushacker1279.immersiveweapons.network.handler.star_forge.StarForgeUpdateRecipesPayloadHandler;
import tech.anonymoushacker1279.immersiveweapons.network.payload.*;
import tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge.StarForgeMenuPayload;
import tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge.StarForgeUpdateRecipesPayload;

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
		event.put(EntityRegistry.SUPER_HANS_ENTITY.get(), SuperHansEntity.registerAttributes().build());
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

	@SubscribeEvent
	public static void registerPayloadHandlerEvent(RegisterPayloadHandlerEvent event) {
		ImmersiveWeapons.LOGGER.info("Registering packet payload handlers");

		final IPayloadRegistrar registrar = event.registrar(ImmersiveWeapons.MOD_ID);

		registrar.play(SmokeGrenadePayload.ID, SmokeGrenadePayload::new, handler -> handler
				.client(SmokeGrenadePayloadHandler.getInstance()::handleData));
		registrar.play(CobaltArmorPayload.ID, CobaltArmorPayload::new, handler -> handler
				.server(CobaltArmorPayloadHandler.getInstance()::handleData));
		registrar.play(TeslaArmorPayload.ID, TeslaArmorPayload::new, handler -> handler
				.server(TeslaArmorPayloadHandler.getInstance()::handleData));
		registrar.play(VentusArmorPayload.ID, VentusArmorPayload::new, handler -> handler
				.server(VentusArmorPayloadHandler.getInstance()::handleData));
		registrar.play(AstralArmorPayload.ID, AstralArmorPayload::new, handler -> handler
				.server(AstralArmorPayloadHandler.getInstance()::handleData));
		registrar.play(AstralCrystalPayload.ID, AstralCrystalPayload::new, handler -> handler
				.client(AstralCrystalPayloadHandler.getInstance()::handleData));
		registrar.play(BulletEntityDebugPayload.ID, BulletEntityDebugPayload::new, handler -> handler
				.client(BulletEntityDebugPayloadHandler.getInstance()::handleData));
		registrar.play(SyncPlayerDataPayload.ID, SyncPlayerDataPayload::new, handler -> handler
				.client(SyncPlayerDataPayloadHandler.getInstance()::handleData));
		registrar.play(DebugDataPayload.ID, DebugDataPayload::new, handler -> handler
				.client(DebugDataPayloadHandler.getInstance()::handleData));
		registrar.play(GunScopePayload.ID, GunScopePayload::new, handler -> handler
				.client(GunScopePayloadHandler.getInstance()::handleData));
		registrar.play(AmmunitionTablePayload.ID, AmmunitionTablePayload::new, handler -> handler
				.server(AmmunitionTablePayloadHandler.getInstance()::handleData));
		registrar.play(StarForgeMenuPayload.ID, StarForgeMenuPayload::new, handler -> handler
				.client(StarForgeMenuPayloadHandler.getInstance()::handleData)
				.server(StarForgeMenuPayloadHandler.getInstance()::handleData));
		registrar.play(StarForgeUpdateRecipesPayload.ID, StarForgeUpdateRecipesPayload::new, handler -> handler
				.client(StarForgeUpdateRecipesPayloadHandler.getInstance()::handleData));
		registrar.play(LocalSoundPayload.ID, LocalSoundPayload::new, handler -> handler
				.client(LocalSoundPayloadHandler.getInstance()::handleData));
	}
}