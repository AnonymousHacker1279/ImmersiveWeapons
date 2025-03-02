package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.FireflyEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.animal.StarWolfEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.misc.ChairEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.*;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkeletonMerchantEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkygazerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.*;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.ArrowEntityBuilder;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomBoatEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomChestBoatEntity;
import tech.anonymoushacker1279.immersiveweapons.util.markers.LanguageEntryOverride;

import java.util.function.Supplier;

public class EntityRegistry {

	// Entity Register
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ImmersiveWeapons.MOD_ID);

	// Entities
	public static final Supplier<EntityType<CustomArrowEntity>> WOODEN_ARROW_ENTITY = ENTITY_TYPES.register("wooden_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.WOODEN_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "wood_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> STONE_ARROW_ENTITY = ENTITY_TYPES.register("stone_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.STONE_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "stone_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> GOLDEN_ARROW_ENTITY = ENTITY_TYPES.register("golden_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.GOLDEN_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gold_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> COPPER_ARROW_ENTITY = ENTITY_TYPES.register("copper_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.COPPER_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "copper_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> IRON_ARROW_ENTITY = ENTITY_TYPES.register("iron_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.IRON_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "iron_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> COBALT_ARROW_ENTITY = ENTITY_TYPES.register("cobalt_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.COBALT_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "cobalt_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> DIAMOND_ARROW_ENTITY = ENTITY_TYPES.register("diamond_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.DIAMOND_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "diamond_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> NETHERITE_ARROW_ENTITY = ENTITY_TYPES.register("netherite_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.NETHERITE_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "netherite_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> MOLTEN_ARROW_ENTITY = ENTITY_TYPES.register("molten_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.MOLTEN_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "molten_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> TESLA_ARROW_ENTITY = ENTITY_TYPES.register("tesla_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.TESLA_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tesla_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> VENTUS_ARROW_ENTITY = ENTITY_TYPES.register("ventus_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.VENTUS_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "ventus_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> ASTRAL_ARROW_ENTITY = ENTITY_TYPES.register("astral_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.ASTRAL_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "astral_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> STARSTORM_ARROW_ENTITY = ENTITY_TYPES.register("starstorm_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.STARSTORM_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "starstorm_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> VOID_ARROW_ENTITY = ENTITY_TYPES.register("void_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.VOID_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "void_arrow").toString()));
	public static final Supplier<EntityType<CustomArrowEntity>> SMOKE_GRENADE_ARROW_ENTITY = ENTITY_TYPES.register("smoke_grenade_arrow", () -> EntityType.Builder.<CustomArrowEntity>of((type, level) -> new ArrowEntityBuilder(type, ItemRegistry.SMOKE_GRENADE_ARROW.get()).build(level), MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "smoke_grenade_arrow").toString()));
	public static final Supplier<EntityType<BulletEntity>> WOODEN_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("wooden_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.WOODEN_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "wooden_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> STONE_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("stone_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.STONE_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "stone_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> GOLDEN_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("golden_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.GOLDEN_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "golden_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> COPPER_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("copper_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.COPPER_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "copper_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> IRON_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("iron_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.IRON_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.25f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "iron_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> COBALT_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("cobalt_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.COBALT_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "cobalt_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> DIAMOND_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("diamond_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.DIAMOND_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "diamond_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> NETHERITE_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("netherite_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.NETHERITE_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "netherite_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> MOLTEN_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("molten_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.MOLTEN_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "molten_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> TESLA_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("tesla_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.TESLA_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tesla_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> VENTUS_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("ventus_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.VENTUS_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "ventus_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> ASTRAL_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("astral_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.ASTRAL_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "astral_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> STARSTORM_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("starstorm_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.STARSTORM_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.15f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "starstorm_musket_ball").toString()));
	public static final Supplier<EntityType<BulletEntity>> VOID_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("void_musket_ball", () -> EntityType.Builder.<BulletEntity>of((type, level) -> new BulletEntity.BulletEntityBuilder(type, ItemRegistry.VOID_MUSKET_BALL.get()).build(level), MobCategory.MISC).sized(0.25f, 0.15f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "void_musket_ball").toString()));
	public static final Supplier<EntityType<FlareEntity>> FLARE_ENTITY = ENTITY_TYPES.register("flare", () -> EntityType.Builder.<FlareEntity>of(FlareEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "flare").toString()));
	public static final Supplier<EntityType<CannonballEntity>> CANNONBALL_ENTITY = ENTITY_TYPES.register("cannonball", () -> EntityType.Builder.<CannonballEntity>of(CannonballEntity::new, MobCategory.MISC).sized(0.35f, 0.35f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "cannonball").toString()));
	public static final Supplier<EntityType<DragonFireballBulletEntity>> DRAGON_FIREBALL_ENTITY = ENTITY_TYPES.register("dragon_fireball", () -> EntityType.Builder.<DragonFireballBulletEntity>of(DragonFireballBulletEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "dragon_fireball").toString()));
	public static final Supplier<EntityType<SmokeGrenadeEntity>> SMOKE_GRENADE_ENTITY = ENTITY_TYPES.register("smoke_grenade", () -> EntityType.Builder.<SmokeGrenadeEntity>of(SmokeGrenadeEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "smoke_grenade").toString()));
	public static final Supplier<EntityType<FlashbangEntity>> FLASHBANG_ENTITY = ENTITY_TYPES.register("flashbang", () -> EntityType.Builder.<FlashbangEntity>of(FlashbangEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "flashbang").toString()));
	public static final Supplier<EntityType<MolotovEntity>> MOLOTOV_COCKTAIL_ENTITY = ENTITY_TYPES.register("molotov_cocktail", () -> EntityType.Builder.<MolotovEntity>of(MolotovEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "molotov_cocktail").toString()));
	public static final Supplier<EntityType<DyingSoldierEntity>> DYING_SOLDIER_ENTITY = ENTITY_TYPES.register("dying_soldier", () -> EntityType.Builder.of(DyingSoldierEntity::new, MobCategory.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(8).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "dying_soldier").toString()));
	public static final Supplier<EntityType<TheCommanderEntity>> THE_COMMANDER_ENTITY = ENTITY_TYPES.register("the_commander", () -> EntityType.Builder.of(TheCommanderEntity::new, MobCategory.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(8).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "the_commander").toString()));
	public static final Supplier<EntityType<MinutemanEntity>> MINUTEMAN_ENTITY = ENTITY_TYPES.register("minuteman", () -> EntityType.Builder.of(MinutemanEntity::new, MobCategory.CREATURE).sized(0.6f, 1.99f).clientTrackingRange(16).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "minuteman").toString()));
	public static final Supplier<EntityType<FieldMedicEntity>> FIELD_MEDIC_ENTITY = ENTITY_TYPES.register("field_medic", () -> EntityType.Builder.of(FieldMedicEntity::new, MobCategory.CREATURE).sized(0.6f, 1.99f).clientTrackingRange(16).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "field_medic").toString()));
	public static final Supplier<EntityType<ChairEntity>> CHAIR_ENTITY = ENTITY_TYPES.register("chair", () -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).sized(0.0f, 0.0f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "chair").toString()));
	public static final Supplier<EntityType<WanderingWarriorEntity>> WANDERING_WARRIOR_ENTITY = ENTITY_TYPES.register("wandering_warrior", () -> EntityType.Builder.of(WanderingWarriorEntity::new, MobCategory.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(16).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "wandering_warrior").toString()));
	@LanguageEntryOverride("Hans the Almighty")
	public static final Supplier<EntityType<HansEntity>> HANS_ENTITY = ENTITY_TYPES.register("hans", () -> EntityType.Builder.of(HansEntity::new, MobCategory.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(16).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "hans").toString()));
	@LanguageEntryOverride("Super Hans the Almighty")
	public static final Supplier<EntityType<SuperHansEntity>> SUPER_HANS_ENTITY = ENTITY_TYPES.register("super_hans", () -> EntityType.Builder.of(SuperHansEntity::new, MobCategory.MONSTER).sized(1.2f, 3.98f).clientTrackingRange(16).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "super_hans").toString()));
	public static final Supplier<EntityType<MortarShellEntity>> MORTAR_SHELL_ENTITY = ENTITY_TYPES.register("mortar_shell", () -> EntityType.Builder.of(MortarShellEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "mortar_shell").toString()));
	public static final Supplier<EntityType<CustomBoatEntity>> BURNED_OAK_BOAT_ENTITY = ENTITY_TYPES.register("burned_oak_boat", () -> EntityType.Builder.<CustomBoatEntity>of((type, level) -> new CustomBoatEntity(type, level, ItemRegistry.BURNED_OAK_BOAT.get()), MobCategory.MISC).sized(1.375f, 0.5625f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "burned_oak_boat").toString()));
	@LanguageEntryOverride("Burned Oak Boat with Chest")
	public static final Supplier<EntityType<CustomChestBoatEntity>> BURNED_OAK_CHEST_BOAT_ENTITY = ENTITY_TYPES.register("burned_oak_chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity>of((type, level) -> new CustomChestBoatEntity(type, level, ItemRegistry.BURNED_OAK_CHEST_BOAT.get()), MobCategory.MISC).sized(1.375f, 0.5625f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "burned_oak_chest_boat").toString()));
	public static final Supplier<EntityType<MudBallEntity>> MUD_BALL_ENTITY = ENTITY_TYPES.register("mud_ball", () -> EntityType.Builder.<MudBallEntity>of(MudBallEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "mud_ball").toString()));
	public static final Supplier<EntityType<LavaRevenantEntity>> LAVA_REVENANT_ENTITY = ENTITY_TYPES.register("lava_revenant", () -> EntityType.Builder.of(LavaRevenantEntity::new, MobCategory.MONSTER).sized(16.0f, 6.0f).clientTrackingRange(32).fireImmune().build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "lava_revenant").toString()));
	public static final Supplier<EntityType<RockSpiderEntity>> ROCK_SPIDER_ENTITY = ENTITY_TYPES.register("rock_spider", () -> EntityType.Builder.of(RockSpiderEntity::new, MobCategory.MONSTER).sized(0.30f, 0.30f).clientTrackingRange(16).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "rock_spider").toString()));
	public static final Supplier<EntityType<CelestialTowerEntity>> CELESTIAL_TOWER_ENTITY = ENTITY_TYPES.register("celestial_tower", () -> EntityType.Builder.of(CelestialTowerEntity::new, MobCategory.MONSTER).sized(8.0f, 9.0f).clientTrackingRange(32).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "celestial_tower").toString()));
	public static final Supplier<EntityType<CustomBoatEntity>> STARDUST_BOAT_ENTITY = ENTITY_TYPES.register("stardust_boat", () -> EntityType.Builder.<CustomBoatEntity>of((type, level) -> new CustomBoatEntity(type, level, ItemRegistry.STARDUST_BOAT.get()), MobCategory.MISC).sized(1.375f, 0.5625f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "stardust_boat").toString()));
	@LanguageEntryOverride("Stardust Boat with Chest")
	public static final Supplier<EntityType<CustomChestBoatEntity>> STARDUST_CHEST_BOAT_ENTITY = ENTITY_TYPES.register("stardust_chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity>of((type, level) -> new CustomChestBoatEntity(type, level, ItemRegistry.STARDUST_CHEST_BOAT.get()), MobCategory.MISC).sized(1.375f, 0.5625f).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "stardust_chest_boat").toString()));
	public static final Supplier<EntityType<StarmiteEntity>> STARMITE_ENTITY = ENTITY_TYPES.register("starmite", () -> EntityType.Builder.of(StarmiteEntity::new, MobCategory.MONSTER).sized(0.4f, 0.3f).clientTrackingRange(8).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "starmite").toString()));
	public static final Supplier<EntityType<FireflyEntity>> FIREFLY_ENTITY = ENTITY_TYPES.register("firefly", () -> EntityType.Builder.of(FireflyEntity::new, MobCategory.CREATURE).sized(0.15f, 0.15f).clientTrackingRange(8).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "firefly").toString()));
	public static final Supplier<EntityType<MeteorEntity>> METEOR_ENTITY = ENTITY_TYPES.register("meteor", () -> EntityType.Builder.of(MeteorEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(8).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "meteor").toString()));
	public static final Supplier<EntityType<StormCreeperEntity>> STORM_CREEPER_ENTITY = ENTITY_TYPES.register("storm_creeper", () -> EntityType.Builder.of(StormCreeperEntity::new, MobCategory.MONSTER).sized(0.6f, 1.7f).clientTrackingRange(8).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "storm_creeper").toString()));
	public static final Supplier<EntityType<EvilEyeEntity>> EVIL_EYE_ENTITY = ENTITY_TYPES.register("evil_eye", () -> EntityType.Builder.of(EvilEyeEntity::new, MobCategory.MONSTER).sized(0.15f, 0.15f).clientTrackingRange(16).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "evil_eye").toString()));
	public static final Supplier<EntityType<StarWolfEntity>> STAR_WOLF_ENTITY = ENTITY_TYPES.register("star_wolf", () -> EntityType.Builder.of(StarWolfEntity::new, MobCategory.CREATURE).sized(0.7f, 0.95f).clientTrackingRange(8).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "star_wolf").toString()));
	public static final Supplier<EntityType<SkygazerEntity>> SKYGAZER_ENTITY = ENTITY_TYPES.register("skygazer", () -> EntityType.Builder.of(SkygazerEntity::new, MobCategory.CREATURE).sized(0.6f, 1.95f).clientTrackingRange(10).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "skygazer").toString()));
	public static final Supplier<EntityType<SkeletonMerchantEntity>> SKELETON_MERCHANT_ENTITY = ENTITY_TYPES.register("skeleton_merchant", () -> EntityType.Builder.of(SkeletonMerchantEntity::new, MobCategory.CREATURE).sized(0.6f, 1.99f).clientTrackingRange(10).build(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "skeleton_merchant").toString()));
}