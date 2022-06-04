package com.anonymoushacker1279.immersiveweapons.event;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomSkullTypes;
import com.anonymoushacker1279.immersiveweapons.block.properties.WoodTypes;
import com.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import com.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.SmallPartsTableScreen;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.TeslaSynthesizerScreen;
import com.anonymoushacker1279.immersiveweapons.client.model.CelestialTowerModel;
import com.anonymoushacker1279.immersiveweapons.client.particle.BloodParticle;
import com.anonymoushacker1279.immersiveweapons.client.particle.MuzzleFlashParticle;
import com.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact.BulletImpactParticle;
import com.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticle;
import com.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.ChairRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.blockentity.ShelfRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.dimension.TiltrosDimensionSpecialEffects;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.misc.BurnedOakBoatRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob.*;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.projectile.*;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Locale;
import java.util.Objects;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	private static final Minecraft mc = Minecraft.getInstance();

	private static final ModelLayerLocation MINUTEMAN_HEAD_LAYER = new ModelLayerLocation(
			DeferredRegistryHandler.MINUTEMAN_HEAD.getId(), "main");
	private static final ModelLayerLocation FIELD_MEDIC_HEAD_LAYER = new ModelLayerLocation(
			DeferredRegistryHandler.FIELD_MEDIC_HEAD.getId(), "main");
	private static final ModelLayerLocation DYING_SOLDIER_HEAD_LAYER = new ModelLayerLocation(
			DeferredRegistryHandler.DYING_SOLDIER_HEAD.getId(), "main");
	private static final ModelLayerLocation WANDERING_WARRIOR_HEAD_LAYER = new ModelLayerLocation(
			DeferredRegistryHandler.WANDERING_WARRIOR_HEAD.getId(), "main");
	private static final ModelLayerLocation HANS_HEAD_LAYER = new ModelLayerLocation(
			DeferredRegistryHandler.HANS_HEAD.getId(), "main");

	/**
	 * Event handler for the FMLClientSetupEvent.
	 *
	 * @param event the <code>FMLClientSetupEvent</code> instance
	 */
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ImmersiveWeapons.LOGGER.debug("Performing client-side setup");

		// Register GUI screens
		MenuScreens.register(DeferredRegistryHandler.SMALL_PARTS_TABLE_CONTAINER.get(), SmallPartsTableScreen::new);
		MenuScreens.register(DeferredRegistryHandler.TESLA_SYNTHESIZER_CONTAINER.get(), TeslaSynthesizerScreen::new);

		// Register key binds
		ClientRegistry.registerKeyBinding(IWKeyBinds.TOGGLE_ARMOR_EFFECT);

		// Register block renderers
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BULLETPROOF_GLASS.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.WHITE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.GRAY_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BLACK_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.ORANGE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.MAGENTA_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.YELLOW_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.LIME_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.PINK_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.CYAN_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.PURPLE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BLUE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BROWN_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.GREEN_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.RED_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_BARS.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.TESLA_SYNTHESIZER.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.CLOUD.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BURNED_OAK_BRANCH.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BURNED_OAK_DOOR.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BURNED_OAK_TRAPDOOR.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.AZUL_STAINED_ORCHID.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.CELESTIAL_LANTERN.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.COBALT_ORE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.DEEPSLATE_COBALT_ORE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.SULFUR_ORE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.DEEPSLATE_SULFUR_ORE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.NETHER_SULFUR_ORE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.ELECTRIC_ORE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.MOLTEN_ORE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.OAK_TABLE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.SPRUCE_TABLE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BIRCH_TABLE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.JUNGLE_TABLE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.ACACIA_TABLE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.DARK_OAK_TABLE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.CRIMSON_TABLE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.WARPED_TABLE.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BURNED_OAK_TABLE.get(), RenderType.translucent());

		mc.getBlockColors().register((color1, color2, color3, color4) -> BiomeColors.
						getAverageGrassColor(Objects.requireNonNull(color2), Objects.requireNonNull(color3)),
				DeferredRegistryHandler.PITFALL.get());

		mc.getItemColors().register((color1, color2) -> GrassColor.get(0.5d, 1.0d),
				DeferredRegistryHandler.PITFALL_ITEM.get());

		event.enqueueWork(() -> {
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.MINUTEMAN, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/minuteman.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.FIELD_MEDIC, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/field_medic.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.DYING_SOLDIER, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/dying_soldier.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.WANDERING_WARRIOR, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/wandering_warrior.png"));
			SkullBlockRenderer.SKIN_BY_TYPE.put(CustomSkullTypes.HANS, new ResourceLocation(ImmersiveWeapons.MOD_ID,
					"textures/entity/hans.png"));

			Sheets.addWoodType(WoodTypes.BURNED_OAK);
		});

		event.enqueueWork(ClientModEventSubscriber::registerPropertyGetters);

		DimensionSpecialEffects.EFFECTS.put(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros"),
				new TiltrosDimensionSpecialEffects());

		IWOverlays.init();
	}

	/**
	 * Event handler for the EntityRenderersEvent.RegisterRenderers
	 *
	 * @param event the <code>RegisterRenderers</code> instance
	 */
	@SubscribeEvent
	public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(DeferredRegistryHandler.WOODEN_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/wood_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/stone_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.GOLDEN_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/gold_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/copper_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/iron_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.COBALT_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/cobalt_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/diamond_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/netherite_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_ENTITY.get(), context ->
				new CustomArrowRenderer<>(context, new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"textures/entity/projectiles/smoke_bomb_arrow.png")));
		event.registerEntityRenderer(DeferredRegistryHandler.WOODEN_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.STONE_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.GOLDEN_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.COPPER_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.IRON_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.COBALT_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.DIAMOND_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.NETHERITE_MUSKET_BALL_ENTITY.get(), MusketBallRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.FLARE_ENTITY.get(), FlareRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.MORTAR_SHELL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.SMOKE_GRENADE_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.MOLOTOV_COCKTAIL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), DyingSoldierRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.MINUTEMAN_ENTITY.get(), MinutemanRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.FIELD_MEDIC_ENTITY.get(), FieldMedicRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), WanderingWarriorRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.HANS_ENTITY.get(), HansRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.CHAIR_ENTITY.get(), ChairRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.BURNED_OAK_BOAT_ENTITY.get(), BurnedOakBoatRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.MUD_BALL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), LavaRevenantRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), RockSpiderRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(), CelestialTowerRenderer::new);
		event.registerBlockEntityRenderer(DeferredRegistryHandler.WALL_SHELF_BLOCK_ENTITY.get(), context -> new ShelfRenderer());
		event.registerBlockEntityRenderer(DeferredRegistryHandler.BURNED_OAK_SIGN_ENTITY.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(DeferredRegistryHandler.CUSTOM_SKULL_BLOCK_ENTITY.get(), SkullBlockRenderer::new);
	}

	/**
	 * Event handler for the RegisterLayerDefinitions.
	 *
	 * @param event the <code>RegisterLayerDefinitions</code> instance
	 */
	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(CelestialTowerModel.LAYER_LOCATION, CelestialTowerModel::createBodyLayer);
		event.registerLayerDefinition(MINUTEMAN_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(FIELD_MEDIC_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(DYING_SOLDIER_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(WANDERING_WARRIOR_HEAD_LAYER, SkullModel::createMobHeadLayer);
		event.registerLayerDefinition(HANS_HEAD_LAYER, SkullModel::createMobHeadLayer);
	}

	/**
	 * Event handler for the ParticleFactoryRegisterEvent.
	 *
	 * @param event the <code>ParticleFactoryRegisterEvent</code> instance
	 */
	@SubscribeEvent
	public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
		mc.particleEngine.register(DeferredRegistryHandler.SMOKE_GRENADE_PARTICLE.get(), SmokeGrenadeParticle.Provider::new);
		mc.particleEngine.register(DeferredRegistryHandler.BLOOD_PARTICLE.get(), BloodParticle.Provider::new);
		mc.particleEngine.register(DeferredRegistryHandler.BULLET_IMPACT_PARTICLE.get(), BulletImpactParticle.Provider::new);
		mc.particleEngine.register(DeferredRegistryHandler.MUZZLE_FLASH_PARTICLE.get(), MuzzleFlashParticle.Provider::new);
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
	}

	private static void registerPropertyGetters() {
		registerPropertyGetter(DeferredRegistryHandler.IRON_GAUNTLET.get(), prefix("gunslinger"),
				(stack, clientLevel, livingEntity, i) -> stack.getDisplayName().getString().toLowerCase(Locale.ROOT)
						.equals("[the gunslinger]") ? 1 : 0);
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