package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.GrassColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomSkullTypes;
import tech.anonymoushacker1279.immersiveweapons.block.properties.WoodTypes;
import tech.anonymoushacker1279.immersiveweapons.client.ClientExtensions;
import tech.anonymoushacker1279.immersiveweapons.client.CustomArmPoses;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.client.ModelLayerLocations;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.client.gui.screen.*;
import tech.anonymoushacker1279.immersiveweapons.client.model.*;
import tech.anonymoushacker1279.immersiveweapons.client.particle.*;
import tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact.BulletImpactParticle;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticle;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.*;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension.TiltrosDimensionSpecialEffects;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob.*;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.*;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.properties.HasSpecificName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	/**
	 * Event handler for the FMLClientSetupEvent.
	 *
	 * @param event the <code>FMLClientSetupEvent</code> instance
	 */
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ImmersiveWeapons.LOGGER.debug("Initializing client setup");

		event.enqueueWork(() -> {
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.MINUTEMAN, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/minuteman.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.FIELD_MEDIC, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/field_medic.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.DYING_SOLDIER, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/dying_soldier.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.THE_COMMANDER, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/the_commander.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.WANDERING_WARRIOR, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/wandering_warrior.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.HANS, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/hans.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.STORM_CREEPER, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/storm_creeper.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.SKELETON_MERCHANT, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/skeleton_merchant.png"));

			Sheets.addWoodType(WoodTypes.BURNED_OAK);
			Sheets.addWoodType(WoodTypes.STARDUST);

			CustomArmPoses.bootstrap();
		});

		initializeCustomItemProperties();
	}

	@SubscribeEvent
	public static void registerMenuScreensEvent(RegisterMenuScreensEvent event) {
		event.register(MenuTypeRegistry.SMALL_PARTS_TABLE_MENU.get(), SmallPartsTableScreen::new);
		event.register(MenuTypeRegistry.TESLA_SYNTHESIZER_MENU.get(), TeslaSynthesizerScreen::new);
		event.register(MenuTypeRegistry.AMMUNITION_TABLE_MENU.get(), AmmunitionTableScreen::new);
		event.register(MenuTypeRegistry.STAR_FORGE_MENU.get(), StarForgeScreen::new);
		event.register(MenuTypeRegistry.CELESTIAL_ALTAR_MENU.get(), CelestialAltarScreen::new);
	}

	@SubscribeEvent
	public static void setupCreativeTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTab() == DeferredRegistryHandler.IMMERSIVE_WEAPONS_TAB.get()) {
			Collection<DeferredHolder<Item, ? extends Item>> registryObjects = ItemRegistry.ITEMS.getEntries();
			List<Item> items = new ArrayList<>(registryObjects.size());
			registryObjects.stream().map(Supplier::get).forEach(items::add);

			for (Item item : items) {
				event.accept(item);
			}

			// Add potion items, which are not in the item registry.
			// Celestial potions
			event.accept(PotionContents.createItemStack(Items.POTION, PotionRegistry.CELESTIAL_BREW_POTION));
			event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, PotionRegistry.CELESTIAL_BREW_POTION));
			event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, PotionRegistry.CELESTIAL_BREW_POTION));
			event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, PotionRegistry.CELESTIAL_BREW_POTION));
			event.accept(PotionContents.createItemStack(Items.POTION, PotionRegistry.LONG_CELESTIAL_BREW_POTION));
			event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, PotionRegistry.LONG_CELESTIAL_BREW_POTION));
			event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, PotionRegistry.LONG_CELESTIAL_BREW_POTION));
			event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, PotionRegistry.LONG_CELESTIAL_BREW_POTION));
			// Death potions
			event.accept(PotionContents.createItemStack(Items.POTION, PotionRegistry.DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, PotionRegistry.DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, PotionRegistry.DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, PotionRegistry.DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.POTION, PotionRegistry.STRONG_DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, PotionRegistry.STRONG_DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, PotionRegistry.STRONG_DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, PotionRegistry.STRONG_DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.POTION, PotionRegistry.LONG_DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, PotionRegistry.LONG_DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, PotionRegistry.LONG_DEATH_POTION));
			event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, PotionRegistry.LONG_DEATH_POTION));
			// Broken Armor potions
			event.accept(PotionContents.createItemStack(Items.POTION, PotionRegistry.BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, PotionRegistry.BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, PotionRegistry.BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, PotionRegistry.BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.POTION, PotionRegistry.STRONG_BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, PotionRegistry.STRONG_BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, PotionRegistry.STRONG_BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, PotionRegistry.STRONG_BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.POTION, PotionRegistry.LONG_BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, PotionRegistry.LONG_BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, PotionRegistry.LONG_BROKEN_ARMOR_POTION));
			event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, PotionRegistry.LONG_BROKEN_ARMOR_POTION));
		}
	}

	/**
	 * Event handler for the EntityRenderersEvent.RegisterRenderers
	 *
	 * @param event the <code>RegisterRenderers</code> instance
	 */
	@SubscribeEvent
	public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		ImmersiveWeapons.LOGGER.info("Registering entity renderers");

		event.registerEntityRenderer(EntityRegistry.WOODEN_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/wood_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.STONE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/stone_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.GOLDEN_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/gold_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.COPPER_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/copper_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.IRON_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/iron_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.COBALT_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/cobalt_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.DIAMOND_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/diamond_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.NETHERITE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/netherite_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.MOLTEN_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/molten_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.TESLA_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/tesla_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.VENTUS_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/ventus_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.ASTRAL_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/astral_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.STARSTORM_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/starstorm_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.VOID_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/void_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/smoke_bomb_arrow.png")));

		event.registerEntityRenderer(EntityRegistry.WOODEN_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "wooden"));
		event.registerEntityRenderer(EntityRegistry.STONE_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "stone"));
		event.registerEntityRenderer(EntityRegistry.GOLDEN_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "golden"));
		event.registerEntityRenderer(EntityRegistry.COPPER_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "copper"));
		event.registerEntityRenderer(EntityRegistry.IRON_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "iron"));
		event.registerEntityRenderer(EntityRegistry.COBALT_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "cobalt"));
		event.registerEntityRenderer(EntityRegistry.DIAMOND_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "diamond"));
		event.registerEntityRenderer(EntityRegistry.NETHERITE_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "netherite"));
		event.registerEntityRenderer(EntityRegistry.MOLTEN_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "molten"));
		event.registerEntityRenderer(EntityRegistry.TESLA_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "tesla"));
		event.registerEntityRenderer(EntityRegistry.VENTUS_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "ventus"));
		event.registerEntityRenderer(EntityRegistry.ASTRAL_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "astral"));
		event.registerEntityRenderer(EntityRegistry.STARSTORM_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "starstorm"));
		event.registerEntityRenderer(EntityRegistry.VOID_MUSKET_BALL_ENTITY.get(), context -> new MusketBallRenderer(context, "void"));

		event.registerEntityRenderer(EntityRegistry.FLARE_ENTITY.get(), context -> new MusketBallRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
				"textures/item/flare.png")));
		event.registerEntityRenderer(EntityRegistry.CANNONBALL_ENTITY.get(), CannonballRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DRAGON_FIREBALL_ENTITY.get(), DragonFireballBulletRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MORTAR_SHELL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SMOKE_GRENADE_ENTITY.get(), AdvancedThrowableProjectileRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLASHBANG_ENTITY.get(), AdvancedThrowableProjectileRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MOLOTOV_COCKTAIL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DYING_SOLDIER_ENTITY.get(), context -> new SoldierRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
				"textures/entity/dying_soldier/dying_soldier.png")));
		event.registerEntityRenderer(EntityRegistry.THE_COMMANDER_ENTITY.get(), context -> new SoldierRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
				"textures/entity/the_commander/the_commander.png")));
		event.registerEntityRenderer(EntityRegistry.MINUTEMAN_ENTITY.get(), context -> new SoldierRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
				"textures/entity/minuteman/minuteman.png")));
		event.registerEntityRenderer(EntityRegistry.FIELD_MEDIC_ENTITY.get(), context -> new SoldierRenderer(context, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
				"textures/entity/field_medic/field_medic.png")));
		event.registerEntityRenderer(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), WanderingWarriorRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HANS_ENTITY.get(), HansRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SUPER_HANS_ENTITY.get(), SuperHansRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHAIR_ENTITY.get(), ChairRenderer::new);

		event.registerEntityRenderer(EntityRegistry.BURNED_OAK_BOAT_ENTITY.get(),
				context -> new BoatRenderer(context, ModelLayerLocations.BURNED_OAK_BOAT_LAYER));
		event.registerEntityRenderer(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get(),
				context -> new BoatRenderer(context, ModelLayerLocations.BURNED_OAK_CHEST_BOAT_LAYER));
		event.registerEntityRenderer(EntityRegistry.STARDUST_BOAT_ENTITY.get(),
				context -> new BoatRenderer(context, ModelLayerLocations.STARDUST_BOAT_LAYER));
		event.registerEntityRenderer(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get(),
				context -> new BoatRenderer(context, ModelLayerLocations.STARDUST_CHEST_BOAT_LAYER));

		event.registerEntityRenderer(EntityRegistry.MUD_BALL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LAVA_REVENANT_ENTITY.get(), LavaRevenantRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ROCK_SPIDER_ENTITY.get(), RockSpiderRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), CelestialTowerRenderer::new);
		event.registerEntityRenderer(EntityRegistry.STARMITE_ENTITY.get(), StarmiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FIREFLY_ENTITY.get(), FireflyRenderer::new);
		event.registerEntityRenderer(EntityRegistry.METEOR_ENTITY.get(), MeteorRenderer::new);
		event.registerEntityRenderer(EntityRegistry.STORM_CREEPER_ENTITY.get(), StormCreeperRenderer::new);
		event.registerEntityRenderer(EntityRegistry.EVIL_EYE_ENTITY.get(), EvilEyeRenderer::new);
		event.registerEntityRenderer(EntityRegistry.STAR_WOLF_ENTITY.get(), StarWolfRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SKYGAZER_ENTITY.get(), SkygazerRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SKELETON_MERCHANT_ENTITY.get(), SkeletonMerchantRenderer::new);

		event.registerBlockEntityRenderer(BlockEntityRegistry.SHELF_BLOCK_ENTITY.get(), context -> new ShelfRenderer());
		event.registerBlockEntityRenderer(BlockEntityRegistry.CUSTOM_SIGN_ENTITY.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(BlockEntityRegistry.CUSTOM_HANGING_SIGN_ENTITY.get(), HangingSignRenderer::new);
		event.registerBlockEntityRenderer(BlockEntityRegistry.CUSTOM_SKULL_BLOCK_ENTITY.get(), SkullBlockRenderer::new);
		event.registerBlockEntityRenderer(BlockEntityRegistry.ASTRAL_CRYSTAL_BLOCK_ENTITY.get(), context -> new AstralCrystalRenderer());
		event.registerBlockEntityRenderer(BlockEntityRegistry.AMMUNITION_TABLE_BLOCK_ENTITY.get(), context -> new AmmunitionTableRenderer());
		event.registerBlockEntityRenderer(BlockEntityRegistry.COMMANDER_PEDESTAL_BLOCK_ENTITY.get(), context -> new CommanderPedestalRenderer());
	}

	/**
	 * Event handler for the RegisterLayerDefinitions.
	 *
	 * @param event the <code>RegisterLayerDefinitions</code> instance
	 */
	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		ImmersiveWeapons.LOGGER.info("Registering entity layer definitions");

		event.registerLayerDefinition(CelestialTowerModel.LAYER_LOCATION, CelestialTowerModel::createBodyLayer);
		event.registerLayerDefinition(FireflyModel.LAYER_LOCATION, FireflyModel::createBodyLayer);
		event.registerLayerDefinition(MeteorModel.LAYER_LOCATION, MeteorModel::createBodyLayer);
		event.registerLayerDefinition(MusketBallModel.LAYER_LOCATION, MusketBallModel::createBodyLayer);
		event.registerLayerDefinition(CannonballModel.LAYER_LOCATION, CannonballModel::createBodyLayer);
		event.registerLayerDefinition(EvilEyeModel.LAYER_LOCATION, EvilEyeModel::createBodyLayer);
		event.registerLayerDefinition(SkeletonMerchantModel.LAYER_LOCATION, SkeletonMerchantModel::createBodyLayer);
		event.registerLayerDefinition(ModelLayerLocations.MINUTEMAN_HEAD_LAYER, SkullModel::createHumanoidHeadLayer);
		event.registerLayerDefinition(ModelLayerLocations.FIELD_MEDIC_HEAD_LAYER, SkullModel::createHumanoidHeadLayer);
		event.registerLayerDefinition(ModelLayerLocations.DYING_SOLDIER_HEAD_LAYER, SkullModel::createHumanoidHeadLayer);
		event.registerLayerDefinition(ModelLayerLocations.THE_COMMANDER_HEAD_LAYER, SkullModel::createHumanoidHeadLayer);
		event.registerLayerDefinition(ModelLayerLocations.WANDERING_WARRIOR_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(ModelLayerLocations.HANS_HEAD_LAYER, SkullModel::createHumanoidHeadLayer);
		event.registerLayerDefinition(ModelLayerLocations.STORM_CREEPER_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(ModelLayerLocations.SKELETON_MERCHANT_HEAD_LAYER, SkullModel::createHumanoidHeadLayer);
		event.registerLayerDefinition(ModelLayerLocations.BURNED_OAK_BOAT_LAYER, BoatModel::createBoatModel);
		event.registerLayerDefinition(ModelLayerLocations.BURNED_OAK_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
		event.registerLayerDefinition(ModelLayerLocations.STARDUST_BOAT_LAYER, BoatModel::createBoatModel);
		event.registerLayerDefinition(ModelLayerLocations.STARDUST_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
	}

	/**
	 * Event handler for the ParticleFactoryRegisterEvent.
	 *
	 * @param event the <code>ParticleFactoryRegisterEvent</code> instance
	 */
	@SubscribeEvent
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		ImmersiveWeapons.LOGGER.info("Registering particle factories");

		event.registerSpriteSet(ParticleTypesRegistry.SMOKE_GRENADE_PARTICLE.get(), SmokeGrenadeParticle.Provider::new);
		event.registerSpriteSet(ParticleTypesRegistry.BLOOD_PARTICLE.get(), BloodParticle.Provider::new);
		event.registerSpriteSet(ParticleTypesRegistry.BULLET_IMPACT_PARTICLE.get(), BulletImpactParticle.Provider::new);
		event.registerSpriteSet(ParticleTypesRegistry.MUZZLE_FLASH_PARTICLE.get(), MuzzleFlashParticle.Provider::new);
		event.registerSpriteSet(ParticleTypesRegistry.MOONGLOW_PARTICLE.get(), MoonglowParticle.Provider::new);
		event.registerSpriteSet(ParticleTypesRegistry.STARDUST_LEAVES_PARTICLE.get(), StardustLeavesParticle.Provider::new);
		event.registerSpriteSet(ParticleTypesRegistry.DEADMANS_DESERT_AMBIENT_PARTICLE.get(), DeadmansDesertAmbientParticle.Provider::new);
		event.registerSpriteSet(ParticleTypesRegistry.TILTROS_PORTAL_PARTICLE.get(), TiltrosPortalParticle.Provider::new);
	}

	@SubscribeEvent
	public static void registerSkullModel(EntityRenderersEvent.CreateSkullModels event) {
		event.registerSkullModel(CustomSkullTypes.MINUTEMAN, ModelLayerLocations.MINUTEMAN_HEAD_LAYER);
		event.registerSkullModel(CustomSkullTypes.FIELD_MEDIC, ModelLayerLocations.FIELD_MEDIC_HEAD_LAYER);
		event.registerSkullModel(CustomSkullTypes.DYING_SOLDIER, ModelLayerLocations.DYING_SOLDIER_HEAD_LAYER);
		event.registerSkullModel(CustomSkullTypes.THE_COMMANDER, ModelLayerLocations.THE_COMMANDER_HEAD_LAYER);
		event.registerSkullModel(CustomSkullTypes.WANDERING_WARRIOR, ModelLayerLocations.WANDERING_WARRIOR_HEAD_LAYER);
		event.registerSkullModel(CustomSkullTypes.HANS, ModelLayerLocations.HANS_HEAD_LAYER);
		event.registerSkullModel(CustomSkullTypes.STORM_CREEPER, ModelLayerLocations.STORM_CREEPER_HEAD_LAYER);
		event.registerSkullModel(CustomSkullTypes.SKELETON_MERCHANT, ModelLayerLocations.SKELETON_MERCHANT_HEAD_LAYER);
	}

	@SubscribeEvent
	public static void registerKeyBinds(RegisterKeyMappingsEvent event) {
		ImmersiveWeapons.LOGGER.info("Registering key bindings");

		event.register(IWKeyBinds.TOGGLE_ARMOR_EFFECT);
		event.register(IWKeyBinds.ARMOR_ACTION);
		event.register(IWKeyBinds.DEBUG_TRACING);
	}

	@SubscribeEvent
	public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
		ImmersiveWeapons.LOGGER.info("Registering dimension special effects");

		event.register(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tiltros"),
				new TiltrosDimensionSpecialEffects());
	}

	@SubscribeEvent
	public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
		ImmersiveWeapons.LOGGER.info("Registering block color handlers");

		event.register((blockState, tintGetter, pos, color) ->
						tintGetter != null && pos != null ? BiomeColors.getAverageGrassColor(tintGetter, pos) : GrassColor.getDefaultColor(),
				BlockRegistry.PITFALL.get());
	}

	@SubscribeEvent
	public static void registerGuiOverlaysEvent(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.CROSSHAIR,
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "scope"),
				IWOverlays.SCOPE_ELEMENT);

		event.registerAbove(VanillaGuiLayers.DEBUG_OVERLAY,
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "debug_overlay"),
				IWOverlays.DEBUG_TRACING_ELEMENT);
	}

	@SubscribeEvent
	public static void registerClientExtensionsEvent(RegisterClientExtensionsEvent event) {
		event.registerItem(ClientExtensions.FIREARM,
				ItemRegistry.FLINTLOCK_PISTOL.get(),
				ItemRegistry.BLUNDERBUSS.get(),
				ItemRegistry.MUSKET.get(),
				ItemRegistry.MUSKET_SCOPE.get(),
				ItemRegistry.FLARE_GUN.get(),
				ItemRegistry.HAND_CANNON.get(),
				ItemRegistry.DRAGONS_BREATH_CANNON.get()
		);
		event.registerItem(ClientExtensions.PIKE,
				ItemRegistry.WOODEN_PIKE.get(),
				ItemRegistry.STONE_PIKE.get(),
				ItemRegistry.GOLDEN_PIKE.get(),
				ItemRegistry.COPPER_PIKE.get(),
				ItemRegistry.IRON_PIKE.get(),
				ItemRegistry.COBALT_PIKE.get(),
				ItemRegistry.DIAMOND_PIKE.get(),
				ItemRegistry.NETHERITE_PIKE.get(),
				ItemRegistry.MOLTEN_PIKE.get(),
				ItemRegistry.TESLA_PIKE.get(),
				ItemRegistry.VENTUS_PIKE.get(),
				ItemRegistry.ASTRAL_PIKE.get(),
				ItemRegistry.STARSTORM_PIKE.get(),
				ItemRegistry.VOID_PIKE.get());
	}

	public static void initializeCustomItemProperties() {
		ConditionalItemModelProperties.ID_MAPPER.put(
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "has_specific_name"),
				HasSpecificName.TYPE
		);
	}
}