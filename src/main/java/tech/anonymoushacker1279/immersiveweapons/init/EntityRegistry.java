package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.FireflyEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.animal.StarWolfEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.misc.ChairEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.*;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkygazerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.*;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.*;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.*;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomBoatEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomChestBoatEntity;

@SuppressWarnings({"unused"})
public class EntityRegistry {

	// Entity Register
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ImmersiveWeapons.MOD_ID);

	// Entities
	public static final RegistryObject<EntityType<WoodenArrowEntity>> WOODEN_ARROW_ENTITY = ENTITY_TYPES.register("wooden_arrow", () -> EntityType.Builder.<WoodenArrowEntity> of(WoodenArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_arrow").toString()));
	public static final RegistryObject<EntityType<StoneArrowEntity>> STONE_ARROW_ENTITY = ENTITY_TYPES.register("stone_arrow", () -> EntityType.Builder.<StoneArrowEntity> of(StoneArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stone_arrow").toString()));
	public static final RegistryObject<EntityType<GoldenArrowEntity>> GOLDEN_ARROW_ENTITY = ENTITY_TYPES.register("golden_arrow", () -> EntityType.Builder.<GoldenArrowEntity> of(GoldenArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_arrow").toString()));
	public static final RegistryObject<EntityType<CopperArrowEntity>> COPPER_ARROW_ENTITY = ENTITY_TYPES.register("copper_arrow", () -> EntityType.Builder.<CopperArrowEntity> of(CopperArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_arrow").toString()));
	public static final RegistryObject<EntityType<IronArrowEntity>> IRON_ARROW_ENTITY = ENTITY_TYPES.register("iron_arrow", () -> EntityType.Builder.<IronArrowEntity> of(IronArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "iron_arrow").toString()));
	public static final RegistryObject<EntityType<CobaltArrowEntity>> COBALT_ARROW_ENTITY = ENTITY_TYPES.register("cobalt_arrow", () -> EntityType.Builder.<CobaltArrowEntity> of(CobaltArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_arrow").toString()));
	public static final RegistryObject<EntityType<DiamondArrowEntity>> DIAMOND_ARROW_ENTITY = ENTITY_TYPES.register("diamond_arrow", () -> EntityType.Builder.<DiamondArrowEntity> of(DiamondArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "diamond_arrow").toString()));
	public static final RegistryObject<EntityType<NetheriteArrowEntity>> NETHERITE_ARROW_ENTITY = ENTITY_TYPES.register("netherite_arrow", () -> EntityType.Builder.<NetheriteArrowEntity> of(NetheriteArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "netherite_arrow").toString()));
	public static final RegistryObject<EntityType<SmokeGrenadeArrowEntity>> SMOKE_GRENADE_ARROW_ENTITY = ENTITY_TYPES.register("smoke_grenade_arrow", () -> EntityType.Builder.<SmokeGrenadeArrowEntity> of(SmokeGrenadeArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_grenade_arrow").toString()));
	public static final RegistryObject<EntityType<WoodenMusketBallEntity>> WOODEN_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("wooden_musket_ball", () -> EntityType.Builder.<WoodenMusketBallEntity> of((type, world) -> new WoodenMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_musket_ball").toString()));
	public static final RegistryObject<EntityType<StoneMusketBallEntity>> STONE_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("stone_musket_ball", () -> EntityType.Builder.<StoneMusketBallEntity> of((type, world) -> new StoneMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stone_musket_ball").toString()));
	public static final RegistryObject<EntityType<GoldenMusketBallEntity>> GOLDEN_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("golden_musket_ball", () -> EntityType.Builder.<GoldenMusketBallEntity> of((type, world) -> new GoldenMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_musket_ball").toString()));
	public static final RegistryObject<EntityType<CopperMusketBallEntity>> COPPER_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("copper_musket_ball", () -> EntityType.Builder.<CopperMusketBallEntity> of((type, world) -> new CopperMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_musket_ball").toString()));
	public static final RegistryObject<EntityType<IronMusketBallEntity>> IRON_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("iron_musket_ball", () -> EntityType.Builder.<IronMusketBallEntity> of((type, world) -> new IronMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "iron_musket_ball").toString()));
	public static final RegistryObject<EntityType<CobaltMusketBallEntity>> COBALT_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("cobalt_musket_ball", () -> EntityType.Builder.<CobaltMusketBallEntity> of((type, world) -> new CobaltMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_musket_ball").toString()));
	public static final RegistryObject<EntityType<DiamondMusketBallEntity>> DIAMOND_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("diamond_musket_ball", () -> EntityType.Builder.<DiamondMusketBallEntity> of((type, world) -> new DiamondMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "diamond_musket_ball").toString()));
	public static final RegistryObject<EntityType<NetheriteMusketBallEntity>> NETHERITE_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("netherite_musket_ball", () -> EntityType.Builder.<NetheriteMusketBallEntity> of((type, world) -> new NetheriteMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "netherite_musket_ball").toString()));
	public static final RegistryObject<EntityType<MoltenMusketBallEntity>> MOLTEN_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("molten_musket_ball", () -> EntityType.Builder.<MoltenMusketBallEntity> of((type, world) -> new MoltenMusketBallEntity(type, world, 1), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "molten_musket_ball").toString()));
	public static final RegistryObject<EntityType<TeslaMusketBallEntity>> TESLA_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("tesla_musket_ball", () -> EntityType.Builder.<TeslaMusketBallEntity> of((type, world) -> new TeslaMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_musket_ball").toString()));
	public static final RegistryObject<EntityType<VentusMusketBallEntity>> VENTUS_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("ventus_musket_ball", () -> EntityType.Builder.<VentusMusketBallEntity> of((type, world) -> new VentusMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_musket_ball").toString()));
	public static final RegistryObject<EntityType<AstralMusketBallEntity>> ASTRAL_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("astral_musket_ball", () -> EntityType.Builder.<AstralMusketBallEntity> of((type, world) -> new AstralMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_musket_ball").toString()));
	public static final RegistryObject<EntityType<StarstormMusketBallEntity>> STARSTORM_MUSKET_BALL_ENTITY = ENTITY_TYPES.register("starstorm_musket_ball", () -> EntityType.Builder.<StarstormMusketBallEntity> of((type, world) -> new StarstormMusketBallEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "starstorm_musket_ball").toString()));
	public static final RegistryObject<EntityType<FlareEntity>> FLARE_ENTITY = ENTITY_TYPES.register("flare", () -> EntityType.Builder.<FlareEntity> of((type, world) -> new FlareEntity(type, world, 0), MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flare").toString()));
	public static final RegistryObject<EntityType<SmokeGrenadeEntity>> SMOKE_GRENADE_ENTITY = ENTITY_TYPES.register("smoke_grenade", () -> EntityType.Builder.<SmokeGrenadeEntity> of(SmokeGrenadeEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_grenade").toString()));
	public static final RegistryObject<EntityType<MolotovEntity>> MOLOTOV_COCKTAIL_ENTITY = ENTITY_TYPES.register("molotov_cocktail", () -> EntityType.Builder.<MolotovEntity> of(MolotovEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "molotov_cocktail").toString()));
	public static final RegistryObject<EntityType<DyingSoldierEntity>> DYING_SOLDIER_ENTITY = ENTITY_TYPES.register("dying_soldier", () -> EntityType.Builder.of(DyingSoldierEntity::new, MobCategory.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(8).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier").toString()));
	public static final RegistryObject<EntityType<MinutemanEntity>> MINUTEMAN_ENTITY = ENTITY_TYPES.register("minuteman", () -> EntityType.Builder.of(MinutemanEntity::new, MobCategory.CREATURE).sized(0.6f, 1.99f).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "minuteman").toString()));
	public static final RegistryObject<EntityType<FieldMedicEntity>> FIELD_MEDIC_ENTITY = ENTITY_TYPES.register("field_medic", () -> EntityType.Builder.of(FieldMedicEntity::new, MobCategory.CREATURE).sized(0.6f, 1.99f).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic").toString()));
	public static final RegistryObject<EntityType<ChairEntity>> CHAIR_ENTITY = ENTITY_TYPES.register("chair", () -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).sized(0.0f, 0.0f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "chair").toString()));
	public static final RegistryObject<EntityType<WanderingWarriorEntity>> WANDERING_WARRIOR_ENTITY = ENTITY_TYPES.register("wandering_warrior", () -> EntityType.Builder.of(WanderingWarriorEntity::new, MobCategory.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior").toString()));
	public static final RegistryObject<EntityType<HansEntity>> HANS_ENTITY = ENTITY_TYPES.register("hans", () -> EntityType.Builder.of(HansEntity::new, MobCategory.MONSTER).sized(0.6f, 1.99f).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "hans").toString()));
	public static final RegistryObject<EntityType<MortarShellEntity>> MORTAR_SHELL_ENTITY = ENTITY_TYPES.register("mortar_shell", () -> EntityType.Builder.of(MortarShellEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "mortar_shell").toString()));
	public static final RegistryObject<EntityType<CustomBoatEntity>> BURNED_OAK_BOAT_ENTITY = ENTITY_TYPES.register("burned_oak_boat", () -> EntityType.Builder.<CustomBoatEntity> of((type, level) -> new CustomBoatEntity(type, level, ItemRegistry.BURNED_OAK_BOAT.get()), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_boat").toString()));
	public static final RegistryObject<EntityType<CustomChestBoatEntity>> BURNED_OAK_CHEST_BOAT_ENTITY = ENTITY_TYPES.register("burned_oak_chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity> of((type, level) -> new CustomChestBoatEntity(type, level, ItemRegistry.BURNED_OAK_CHEST_BOAT.get()), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_chest_boat").toString()));
	public static final RegistryObject<EntityType<MudBallEntity>> MUD_BALL_ENTITY = ENTITY_TYPES.register("mud_ball", () -> EntityType.Builder.<MudBallEntity> of(MudBallEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "mud_ball").toString()));
	public static final RegistryObject<EntityType<LavaRevenantEntity>> LAVA_REVENANT_ENTITY = ENTITY_TYPES.register("lava_revenant", () -> EntityType.Builder.of(LavaRevenantEntity::new, MobCategory.MONSTER).sized(16.0f, 6.0f).clientTrackingRange(32).fireImmune().build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "lava_revenant").toString()));
	public static final RegistryObject<EntityType<RockSpiderEntity>> ROCK_SPIDER_ENTITY = ENTITY_TYPES.register("rock_spider", () -> EntityType.Builder.of(RockSpiderEntity::new, MobCategory.MONSTER).sized(0.30f, 0.30f).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "rock_spider").toString()));
	public static final RegistryObject<EntityType<CelestialTowerEntity>> CELESTIAL_TOWER_ENTITY = ENTITY_TYPES.register("celestial_tower", () -> EntityType.Builder.of(CelestialTowerEntity::new, MobCategory.MONSTER).sized(8.0f, 9.0f).clientTrackingRange(32).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "celestial_tower").toString()));
	public static final RegistryObject<EntityType<CustomBoatEntity>> STARDUST_BOAT_ENTITY = ENTITY_TYPES.register("stardust_boat", () -> EntityType.Builder.<CustomBoatEntity> of((type, level) -> new CustomBoatEntity(type, level, ItemRegistry.STARDUST_BOAT.get()), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_boat").toString()));
	public static final RegistryObject<EntityType<CustomChestBoatEntity>> STARDUST_CHEST_BOAT_ENTITY = ENTITY_TYPES.register("stardust_chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity> of((type, level) -> new CustomChestBoatEntity(type, level, ItemRegistry.STARDUST_CHEST_BOAT.get()), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_chest_boat").toString()));
	public static final RegistryObject<EntityType<StarmiteEntity>> STARMITE_ENTITY = ENTITY_TYPES.register("starmite", () -> EntityType.Builder.of(StarmiteEntity::new, MobCategory.MONSTER).sized(0.4f, 0.3f).clientTrackingRange(8).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "starmite").toString()));
	public static final RegistryObject<EntityType<FireflyEntity>> FIREFLY_ENTITY = ENTITY_TYPES.register("firefly", () -> EntityType.Builder.of(FireflyEntity::new, MobCategory.CREATURE).sized(0.15f, 0.15f).clientTrackingRange(8).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "firefly").toString()));
	public static final RegistryObject<EntityType<MeteorEntity>> METEOR_ENTITY = ENTITY_TYPES.register("meteor", () -> EntityType.Builder.of(MeteorEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(8).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "meteor").toString()));
	public static final RegistryObject<EntityType<StormCreeperEntity>> STORM_CREEPER_ENTITY = ENTITY_TYPES.register("storm_creeper", () -> EntityType.Builder.of(StormCreeperEntity::new, MobCategory.MONSTER).sized(0.6f, 1.7f).clientTrackingRange(8).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "storm_creeper").toString()));
	public static final RegistryObject<EntityType<EvilEyeEntity>> EVIL_EYE_ENTITY = ENTITY_TYPES.register("evil_eye", () -> EntityType.Builder.of(EvilEyeEntity::new, MobCategory.MONSTER).sized(0.15f, 0.15f).clientTrackingRange(16).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "evil_eye").toString()));
	public static final RegistryObject<EntityType<StarWolfEntity>> STAR_WOLF_ENTITY = ENTITY_TYPES.register("star_wolf", () -> EntityType.Builder.of(StarWolfEntity::new, MobCategory.MONSTER).sized(0.7f, 0.95f).clientTrackingRange(8).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "star_wolf").toString()));
	public static final RegistryObject<EntityType<SkygazerEntity>> SKYGAZER_ENTITY = ENTITY_TYPES.register("skygazer", () -> EntityType.Builder.of(SkygazerEntity::new, MobCategory.CREATURE).sized(0.6f, 1.95f).clientTrackingRange(10).build(new ResourceLocation(ImmersiveWeapons.MOD_ID, "skygazer").toString()));
}