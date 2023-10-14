package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomSkullTypes;
import tech.anonymoushacker1279.immersiveweapons.block.properties.WoodTypes;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.client.gui.screen.*;
import tech.anonymoushacker1279.immersiveweapons.client.model.*;
import tech.anonymoushacker1279.immersiveweapons.client.particle.*;
import tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact.BulletImpactParticle;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticle;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.*;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension.TiltrosDimensionSpecialEffects;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.misc.CustomBoatRenderer;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob.*;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.*;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.*;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	private static final ModelLayerLocation MINUTEMAN_HEAD_LAYER = new ModelLayerLocation(
			BlockRegistry.MINUTEMAN_HEAD.getId(), "main");
	private static final ModelLayerLocation FIELD_MEDIC_HEAD_LAYER = new ModelLayerLocation(
			BlockRegistry.FIELD_MEDIC_HEAD.getId(), "main");
	private static final ModelLayerLocation DYING_SOLDIER_HEAD_LAYER = new ModelLayerLocation(
			BlockRegistry.DYING_SOLDIER_HEAD.getId(), "main");
	private static final ModelLayerLocation WANDERING_WARRIOR_HEAD_LAYER = new ModelLayerLocation(
			BlockRegistry.WANDERING_WARRIOR_HEAD.getId(), "main");
	private static final ModelLayerLocation HANS_HEAD_LAYER = new ModelLayerLocation(
			BlockRegistry.HANS_HEAD.getId(), "main");
	private static final ModelLayerLocation STORM_CREEPER_HEAD_LAYER = new ModelLayerLocation(
			BlockRegistry.STORM_CREEPER_HEAD.getId(), "main");
	private static final ModelLayerLocation SKELETON_MERCHANT_HEAD_LAYER = new ModelLayerLocation(
			BlockRegistry.SKELETON_MERCHANT_HEAD.getId(), "main");

	/**
	 * Event handler for the FMLClientSetupEvent.
	 *
	 * @param event the <code>FMLClientSetupEvent</code> instance
	 */
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ImmersiveWeapons.LOGGER.debug("Initializing client setup");

		// Register GUI screens
		MenuScreens.register(MenuTypeRegistry.SMALL_PARTS_TABLE_MENU.get(), SmallPartsTableScreen::new);
		MenuScreens.register(MenuTypeRegistry.TESLA_SYNTHESIZER_MENU.get(), TeslaSynthesizerScreen::new);
		MenuScreens.register(MenuTypeRegistry.AMMUNITION_TABLE_MENU.get(), AmmunitionTableScreen::new);

		event.enqueueWork(() -> {
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.MINUTEMAN, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/minuteman.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.FIELD_MEDIC, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/field_medic.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.DYING_SOLDIER, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/dying_soldier.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.WANDERING_WARRIOR, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/wandering_warrior.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.HANS, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/hans.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.STORM_CREEPER, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/storm_creeper.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.SKELETON_MERCHANT, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/heads/skeleton_merchant.png"));

			Sheets.addWoodType(WoodTypes.BURNED_OAK);
			Sheets.addWoodType(WoodTypes.STARDUST);
		});

		event.enqueueWork(ClientModEventSubscriber::registerPropertyGetters);

		IWOverlays.init();
	}

	@SubscribeEvent
	public static void setupCreativeTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTab() == DeferredRegistryHandler.IMMERSIVE_WEAPONS_TAB.get()) {
			Collection<RegistryObject<Item>> registryObjects = ItemRegistry.ITEMS.getEntries();
			List<Item> items = new ArrayList<>(registryObjects.size());
			registryObjects.stream().map(RegistryObject::get).forEach(items::add);

			for (Item item : items) {
				event.accept(item);
			}

			// Add potion items, which are not in the item registry.
			// Celestial potions
			event.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.CELESTIAL_BREW_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.CELESTIAL_BREW_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.CELESTIAL_BREW_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.CELESTIAL_BREW_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
			// Death potions
			event.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.STRONG_DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.STRONG_DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.STRONG_DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.STRONG_DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.LONG_DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.LONG_DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.LONG_DEATH_POTION.get()));
			event.accept(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.LONG_DEATH_POTION.get()));
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
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/wood_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.STONE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/stone_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.GOLDEN_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/gold_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.COPPER_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/copper_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.IRON_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/iron_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.COBALT_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/cobalt_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.DIAMOND_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/diamond_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.NETHERITE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/netherite_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.MOLTEN_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/molten_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.TESLA_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/tesla_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.VENTUS_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/ventus_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.ASTRAL_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/astral_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.STARSTORM_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/starstorm_arrow.png")));
		event.registerEntityRenderer(EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/smoke_bomb_arrow.png")));

		event.registerEntityRenderer(EntityRegistry.WOODEN_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.STONE_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GOLDEN_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.COPPER_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.IRON_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.COBALT_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DIAMOND_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NETHERITE_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MOLTEN_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TESLA_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.VENTUS_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ASTRAL_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(EntityRegistry.STARSTORM_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);

		event.registerEntityRenderer(EntityRegistry.FLARE_ENTITY.get(), FlareRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CANNONBALL_ENTITY.get(), CannonballRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MORTAR_SHELL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SMOKE_GRENADE_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MOLOTOV_COCKTAIL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DYING_SOLDIER_ENTITY.get(), DyingSoldierRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MINUTEMAN_ENTITY.get(), MinutemanRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FIELD_MEDIC_ENTITY.get(), FieldMedicRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), WanderingWarriorRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HANS_ENTITY.get(), HansRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SUPER_HANS_ENTITY.get(), SuperHansRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHAIR_ENTITY.get(), ChairRenderer::new);

		event.registerEntityRenderer(EntityRegistry.BURNED_OAK_BOAT_ENTITY.get(),
				context -> new CustomBoatRenderer(context, false,
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/boat/burned_oak.png")));
		event.registerEntityRenderer(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get(),
				context -> new CustomBoatRenderer(context, true,
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/chest_boat/burned_oak.png")));
		event.registerEntityRenderer(EntityRegistry.STARDUST_BOAT_ENTITY.get(),
				context -> new CustomBoatRenderer(context, false,
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/boat/stardust.png")));
		event.registerEntityRenderer(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get(),
				context -> new CustomBoatRenderer(context, true,
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/chest_boat/stardust.png")));

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
		event.registerLayerDefinition(EvilEyeModel.LAYER_LOCATION, EvilEyeModel::createBodyLayer);
		event.registerLayerDefinition(SkeletonMerchantModel.LAYER_LOCATION, SkeletonMerchantModel::createBodyLayer);
		event.registerLayerDefinition(MINUTEMAN_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(FIELD_MEDIC_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(DYING_SOLDIER_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(WANDERING_WARRIOR_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(HANS_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(STORM_CREEPER_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(SKELETON_MERCHANT_HEAD_LAYER, SkullModel::createMobHeadLayer);
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
	}

	@SubscribeEvent
	public static void registerSkullModel(EntityRenderersEvent.CreateSkullModels event) {
		event.registerSkullModel(CustomSkullTypes.MINUTEMAN, new SkullModel(event.getEntityModelSet()
				.bakeLayer(MINUTEMAN_HEAD_LAYER)));
		event.registerSkullModel(CustomSkullTypes.FIELD_MEDIC, new SkullModel(event.getEntityModelSet()
				.bakeLayer(FIELD_MEDIC_HEAD_LAYER)));
		event.registerSkullModel(CustomSkullTypes.DYING_SOLDIER, new SkullModel(event.getEntityModelSet()
				.bakeLayer(DYING_SOLDIER_HEAD_LAYER)));
		event.registerSkullModel(CustomSkullTypes.WANDERING_WARRIOR, new SkullModel(event.getEntityModelSet()
				.bakeLayer(WANDERING_WARRIOR_HEAD_LAYER)));
		event.registerSkullModel(CustomSkullTypes.HANS, new SkullModel(event.getEntityModelSet()
				.bakeLayer(HANS_HEAD_LAYER)));
		event.registerSkullModel(CustomSkullTypes.STORM_CREEPER, new SkullModel(event.getEntityModelSet()
				.bakeLayer(STORM_CREEPER_HEAD_LAYER)));
		event.registerSkullModel(CustomSkullTypes.SKELETON_MERCHANT, new SkullModel(event.getEntityModelSet()
				.bakeLayer(SKELETON_MERCHANT_HEAD_LAYER)));
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

		event.register(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros"),
				new TiltrosDimensionSpecialEffects());
	}

	@SubscribeEvent
	public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
		ImmersiveWeapons.LOGGER.info("Registering item color handlers");

		event.register((stack, color) -> GrassColor.get(0.5d, 1.0d),
				BlockItemRegistry.PITFALL_ITEM.get());

		// Handle dyeable armor
		ArrayList<DyeableArmorItem> dyeableArmorItems = new ArrayList<>(4);

		for (RegistryObject<Item> registryObject : ItemRegistry.ITEMS.getEntries()) {
			if (registryObject.get() instanceof DyeableArmorItem item) {
				dyeableArmorItems.add(item);
			}
		}

		event.register((stack, color) -> color > 0 ? -1 : ((DyeableLeatherItem) stack.getItem())
				.getColor(stack), dyeableArmorItems.toArray(DyeableArmorItem[]::new));
	}

	@SubscribeEvent
	public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
		ImmersiveWeapons.LOGGER.info("Registering block color handlers");

		event.register((state, tintGetter, pos, color) -> BiomeColors
						.getAverageGrassColor(Objects.requireNonNull(tintGetter), Objects.requireNonNull(pos)),
				BlockRegistry.PITFALL.get());
	}

	private static void registerPropertyGetters() {
		registerPropertyGetter(ItemRegistry.IRON_GAUNTLET.get(), prefix("gunslinger"),
				(stack, clientLevel, livingEntity, i) -> stack.getDisplayName().getString().toLowerCase(Locale.ROOT)
						.equals("[the gunslinger]") ? 1 : 0);

		// Register pulling properties for bows
		registerPropertyGetter(ItemRegistry.ICE_BOW.get(), prefix("pull"),
				(stack, clientLevel, livingEntity, i) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return livingEntity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
					}
				});
		registerPropertyGetter(ItemRegistry.ICE_BOW.get(), prefix("pulling"),
				(stack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);

		registerPropertyGetter(ItemRegistry.DRAGONS_BREATH_BOW.get(), prefix("pull"),
				(stack, clientLevel, livingEntity, i) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return livingEntity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
					}
				});
		registerPropertyGetter(ItemRegistry.DRAGONS_BREATH_BOW.get(), prefix("pulling"),
				(stack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);

		registerPropertyGetter(ItemRegistry.AURORA_BOW.get(), prefix("pull"),
				(stack, clientLevel, livingEntity, i) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return livingEntity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
					}
				});
		registerPropertyGetter(ItemRegistry.AURORA_BOW.get(), prefix("pulling"),
				(stack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);
	}

	/**
	 * Register an item property getter.
	 *
	 * @param item             the <code>ItemLike</code> instance
	 * @param resourceLocation the <code>ResourceLocation</code> of the item
	 * @param propertyValue    the <code>ClampedItemPropertyFunction</code> value
	 */
	public static void registerPropertyGetter(ItemLike item, ResourceLocation resourceLocation,
	                                          ClampedItemPropertyFunction propertyValue) {

		ItemProperties.register(item.asItem(), resourceLocation, propertyValue);
	}

	/**
	 * Get the prefix of a string.
	 *
	 * @param path the path to prefix
	 * @return ResourceLocation
	 */
	public static ResourceLocation prefix(String path) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, path);
	}
}