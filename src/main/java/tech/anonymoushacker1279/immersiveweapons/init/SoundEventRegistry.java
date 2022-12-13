package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

@SuppressWarnings({"unused"})
public class SoundEventRegistry {

	// Sound Event Register
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ImmersiveWeapons.MOD_ID);

	// Sounds
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_EFFECT = SOUND_EVENTS.register("tesla_armor_effect", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_effect")));
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_POWER_DOWN = SOUND_EVENTS.register("tesla_armor_power_down", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_power_down")));
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_POWER_UP = SOUND_EVENTS.register("tesla_armor_power_up", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_power_up")));
	public static final RegistryObject<SoundEvent> TESLA_ARMOR_EQUIP = SOUND_EVENTS.register("tesla_armor_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_armor_equip")));
	public static final RegistryObject<SoundEvent> MOLTEN_ARMOR_EQUIP = SOUND_EVENTS.register("molten_armor_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "molten_armor_equip")));
	public static final RegistryObject<SoundEvent> VENTUS_ARMOR_EQUIP = SOUND_EVENTS.register("ventus_armor_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_armor_equip")));
	public static final RegistryObject<SoundEvent> COPPER_ARMOR_EQUIP = SOUND_EVENTS.register("copper_armor_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_armor_equip")));
	public static final RegistryObject<SoundEvent> COBALT_ARMOR_EQUIP = SOUND_EVENTS.register("cobalt_armor_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_armor_equip")));
	public static final RegistryObject<SoundEvent> ASTRAL_ARMOR_EQUIP = SOUND_EVENTS.register("astral_armor_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_armor_equip")));
	public static final RegistryObject<SoundEvent> STARSTORM_ARMOR_EQUIP = SOUND_EVENTS.register("starstorm_armor_equip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "starstorm_armor_equip")));
	public static final RegistryObject<SoundEvent> FLINTLOCK_PISTOL_FIRE = SOUND_EVENTS.register("flintlock_pistol_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flintlock_pistol_fire")));
	public static final RegistryObject<SoundEvent> BULLET_WHIZZ = SOUND_EVENTS.register("bullet_whizz", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "bullet_whizz")));
	public static final RegistryObject<SoundEvent> FLINTLOCK_PISTOL_MISFIRE = SOUND_EVENTS.register("flintlock_pistol_misfire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flintlock_pistol_misfire")));
	public static final RegistryObject<SoundEvent> SMALL_PARTS_TABLE_USED = SOUND_EVENTS.register("small_parts_table_used", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts_table_used")));
	public static final RegistryObject<SoundEvent> SMOKE_GRENADE_HISS = SOUND_EVENTS.register("smoke_grenade_hiss", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_grenade_hiss")));
	public static final RegistryObject<SoundEvent> GENERIC_WHOOSH = SOUND_EVENTS.register("generic_whoosh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "generic_whoosh")));
	public static final RegistryObject<SoundEvent> BLUNDERBUSS_FIRE = SOUND_EVENTS.register("blunderbuss_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "blunderbuss_fire")));
	public static final RegistryObject<SoundEvent> BARBED_WIRE_RATTLE = SOUND_EVENTS.register("barbed_wire_rattle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "barbed_wire_rattle")));
	public static final RegistryObject<SoundEvent> BEAR_TRAP_CLOSE = SOUND_EVENTS.register("bear_trap_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap_close")));
	public static final RegistryObject<SoundEvent> SPIKE_TRAP_EXTEND = SOUND_EVENTS.register("spike_trap_extend", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap_extend")));
	public static final RegistryObject<SoundEvent> SPIKE_TRAP_RETRACT = SOUND_EVENTS.register("spike_trap_retract", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap_retract")));
	public static final RegistryObject<SoundEvent> PANIC_ALARM_SOUND = SOUND_EVENTS.register("panic_alarm", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "panic_alarm")));
	public static final RegistryObject<SoundEvent> DYING_SOLDIER_AMBIENT = SOUND_EVENTS.register("dying_soldier_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier_ambient")));
	public static final RegistryObject<SoundEvent> DYING_SOLDIER_STEP = SOUND_EVENTS.register("dying_soldier_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier_step")));
	public static final RegistryObject<SoundEvent> DYING_SOLDIER_DEATH = SOUND_EVENTS.register("dying_soldier_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier_death")));
	public static final RegistryObject<SoundEvent> DYING_SOLDIER_HURT = SOUND_EVENTS.register("dying_soldier_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "dying_soldier_hurt")));
	public static final RegistryObject<SoundEvent> BATTLEFIELD_AMBIENT = SOUND_EVENTS.register("battlefield_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "battlefield_ambient")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_ATTACK = SOUND_EVENTS.register("field_medic_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_attack")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_AMBIENT = SOUND_EVENTS.register("field_medic_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_ambient")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_HURT = SOUND_EVENTS.register("field_medic_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_hurt")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_DEATH = SOUND_EVENTS.register("field_medic_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_death")));
	public static final RegistryObject<SoundEvent> FIELD_MEDIC_STEP = SOUND_EVENTS.register("field_medic_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "field_medic_step")));
	public static final RegistryObject<SoundEvent> FLARE_GUN_FIRE = SOUND_EVENTS.register("flare_gun_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flare_gun_fire")));
	public static final RegistryObject<SoundEvent> WANDERING_WARRIOR_AMBIENT = SOUND_EVENTS.register("wandering_warrior_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_ambient")));
	public static final RegistryObject<SoundEvent> WANDERING_WARRIOR_HURT = SOUND_EVENTS.register("wandering_warrior_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_hurt")));
	public static final RegistryObject<SoundEvent> WANDERING_WARRIOR_DEATH = SOUND_EVENTS.register("wandering_warrior_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_death")));
	public static final RegistryObject<SoundEvent> WANDERING_WARRIOR_STEP = SOUND_EVENTS.register("wandering_warrior_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wandering_warrior_step")));
	public static final RegistryObject<SoundEvent> TILTROS_AMBIENT = SOUND_EVENTS.register("tiltros_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros_ambient")));
	public static final RegistryObject<SoundEvent> LAVA_REVENANT_AMBIENT = SOUND_EVENTS.register("lava_revenant_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "lava_revenant_ambient")));
	public static final RegistryObject<SoundEvent> LAVA_REVENANT_HURT = SOUND_EVENTS.register("lava_revenant_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "lava_revenant_hurt")));
	public static final RegistryObject<SoundEvent> LAVA_REVENANT_DEATH = SOUND_EVENTS.register("lava_revenant_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "lava_revenant_death")));
	public static final RegistryObject<SoundEvent> LAVA_REVENANT_FLAP = SOUND_EVENTS.register("lava_revenant_flap", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "lava_revenant_flap")));
	public static final RegistryObject<SoundEvent> LAVA_REVENANT_SWOOP = SOUND_EVENTS.register("lava_revenant_swoop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "lava_revenant_swoop")));
	public static final RegistryObject<SoundEvent> LAVA_REVENANT_BITE = SOUND_EVENTS.register("lava_revenant_bite", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "lava_revenant_bite")));
	public static final RegistryObject<SoundEvent> CELESTIAL_TOWER_AMBIENT = SOUND_EVENTS.register("celestial_tower_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "celestial_tower_ambient")));
	public static final RegistryObject<SoundEvent> CELESTIAL_TOWER_HURT = SOUND_EVENTS.register("celestial_tower_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "celestial_tower_hurt")));
	public static final RegistryObject<SoundEvent> CELESTIAL_TOWER_DEATH = SOUND_EVENTS.register("celestial_tower_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "celestial_tower_death")));
	public static final RegistryObject<SoundEvent> CELESTIAL_TOWER_SUMMON = SOUND_EVENTS.register("celestial_tower_summon", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "celestial_tower_summon")));
	public static final RegistryObject<SoundEvent> CELESTIAL_TOWER_VULNERABLE = SOUND_EVENTS.register("celestial_tower_vulnerable", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "celestial_tower_vulnerable")));
	public static final RegistryObject<SoundEvent> MORTAR_FIRE = SOUND_EVENTS.register("mortar_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "mortar_fire")));
	public static final RegistryObject<SoundEvent> MUSKET_FIRE = SOUND_EVENTS.register("musket_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "musket_fire")));
	public static final RegistryObject<SoundEvent> FIREFLY_FLYING = SOUND_EVENTS.register("firefly_flying", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "firefly_flying")));
	public static final RegistryObject<SoundEvent> STARMITE_AMBIENT = SOUND_EVENTS.register("starmite_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "starmite_ambient")));
	public static final RegistryObject<SoundEvent> STARMITE_HURT = SOUND_EVENTS.register("starmite_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "starmite_hurt")));
	public static final RegistryObject<SoundEvent> STARMITE_DEATH = SOUND_EVENTS.register("starmite_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "starmite_death")));
	public static final RegistryObject<SoundEvent> STARMITE_STEP = SOUND_EVENTS.register("starmite_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ImmersiveWeapons.MOD_ID, "starmite_step")));
}