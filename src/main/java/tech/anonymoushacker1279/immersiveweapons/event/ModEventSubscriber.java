package tech.anonymoushacker1279.immersiveweapons.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforgespi.language.IModInfo;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.FireflyEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.animal.StarWolfEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.*;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
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

		event.put(EntityRegistry.DYING_SOLDIER_ENTITY.get(), DyingSoldierEntity.registerAttributes().build());
		event.put(EntityRegistry.THE_COMMANDER_ENTITY.get(), TheCommanderEntity.registerAttributes().build());
		event.put(EntityRegistry.MINUTEMAN_ENTITY.get(), MinutemanEntity.registerAttributes().build());
		event.put(EntityRegistry.FIELD_MEDIC_ENTITY.get(), FieldMedicEntity.registerAttributes().build());
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
	public static void registerPayloadHandlerEvent(RegisterPayloadHandlersEvent event) {
		ImmersiveWeapons.LOGGER.info("Registering packet payload handlers");

		// TODO: check and make sure version is correct
		String version = ModList.get()
				.getModContainerById(ImmersiveWeapons.MOD_ID)
				.map(ModContainer::getModInfo)
				.map(IModInfo::getVersion)
				.map(ArtifactVersion::toString)
				.orElse("[UNKNOWN]");

		PayloadRegistrar registrar = event.registrar(version);

		registrar.playToClient(SmokeGrenadePayload.TYPE, SmokeGrenadePayload.STREAM_CODEC, SmokeGrenadePayloadHandler.getInstance()::handleData);
		registrar.playToServer(CobaltArmorPayload.TYPE, CobaltArmorPayload.STREAM_CODEC, CobaltArmorPayloadHandler.getInstance()::handleData);
		registrar.playToServer(TeslaArmorPayload.TYPE, TeslaArmorPayload.STREAM_CODEC, TeslaArmorPayloadHandler.getInstance()::handleData);
		registrar.playToServer(VentusArmorPayload.TYPE, VentusArmorPayload.STREAM_CODEC, VentusArmorPayloadHandler.getInstance()::handleData);
		registrar.playToServer(AstralArmorPayload.TYPE, AstralArmorPayload.STREAM_CODEC, AstralArmorPayloadHandler.getInstance()::handleData);
		registrar.playToClient(AstralCrystalPayload.TYPE, AstralCrystalPayload.STREAM_CODEC, AstralCrystalPayloadHandler.getInstance()::handleData);
		registrar.playToClient(BulletEntityDebugPayload.TYPE, BulletEntityDebugPayload.STREAM_CODEC, BulletEntityDebugPayloadHandler.getInstance()::handleData);
		registrar.playToClient(SyncPlayerDataPayload.TYPE, SyncPlayerDataPayload.STREAM_CODEC, SyncPlayerDataPayloadHandler.getInstance()::handleData);
		registrar.playToClient(DebugDataPayload.TYPE, DebugDataPayload.STREAM_CODEC, DebugDataPayloadHandler.getInstance()::handleData);
		registrar.playToClient(GunScopePayload.TYPE, GunScopePayload.STREAM_CODEC, GunScopePayloadHandler.getInstance()::handleData);
		registrar.playToServer(AmmunitionTablePayload.TYPE, AmmunitionTablePayload.STREAM_CODEC, AmmunitionTablePayloadHandler.getInstance()::handleData);
		registrar.playBidirectional(StarForgeMenuPayload.TYPE, StarForgeMenuPayload.STREAM_CODEC, StarForgeMenuPayloadHandler.getInstance()::handleData);
		registrar.playToClient(StarForgeUpdateRecipesPayload.TYPE, StarForgeUpdateRecipesPayload.STREAM_CODEC, StarForgeUpdateRecipesPayloadHandler.getInstance()::handleData);
		registrar.playToClient(LocalSoundPayload.TYPE, LocalSoundPayload.STREAM_CODEC, LocalSoundPayloadHandler.getInstance()::handleData);
		registrar.playToClient(PlayerSoundPayload.TYPE, PlayerSoundPayload.STREAM_CODEC, PlayerSoundPayloadHandler.getInstance()::handleData);
		registrar.playToClient(SyncMerchantTradesPayload.TYPE, SyncMerchantTradesPayload.STREAM_CODEC, SyncMerchantTradesPayloadHandler.getInstance()::handleData);
	}
}