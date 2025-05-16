package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class SoundEventRegistry {

	// Sound Event Register
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, ImmersiveWeapons.MOD_ID);

	// Sounds
	public static final DeferredHolder<SoundEvent, SoundEvent> TESLA_ARMOR_EFFECT = register("tesla_armor_effect");
	public static final DeferredHolder<SoundEvent, SoundEvent> TESLA_ARMOR_POWER_DOWN = register("tesla_armor_power_down");
	public static final DeferredHolder<SoundEvent, SoundEvent> TESLA_ARMOR_POWER_UP = register("tesla_armor_power_up");
	public static final DeferredHolder<SoundEvent, SoundEvent> TESLA_ARMOR_EQUIP = register("tesla_armor_equip");
	public static final DeferredHolder<SoundEvent, SoundEvent> MOLTEN_ARMOR_EQUIP = register("molten_armor_equip");
	public static final DeferredHolder<SoundEvent, SoundEvent> VENTUS_ARMOR_EQUIP = register("ventus_armor_equip");
	public static final DeferredHolder<SoundEvent, SoundEvent> COPPER_ARMOR_EQUIP = register("copper_armor_equip");
	public static final DeferredHolder<SoundEvent, SoundEvent> COBALT_ARMOR_EQUIP = register("cobalt_armor_equip");
	public static final DeferredHolder<SoundEvent, SoundEvent> ASTRAL_ARMOR_EQUIP = register("astral_armor_equip");
	public static final DeferredHolder<SoundEvent, SoundEvent> STARSTORM_ARMOR_EQUIP = register("starstorm_armor_equip");
	public static final DeferredHolder<SoundEvent, SoundEvent> VOID_ARMOR_EQUIP = register("void_armor_equip");
	public static final DeferredHolder<SoundEvent, SoundEvent> FLINTLOCK_PISTOL_FIRE = register("flintlock_pistol_fire");
	public static final DeferredHolder<SoundEvent, SoundEvent> BULLET_WHIZZ = register("bullet_whizz");
	public static final DeferredHolder<SoundEvent, SoundEvent> FLINTLOCK_PISTOL_MISFIRE = register("flintlock_pistol_misfire");
	public static final DeferredHolder<SoundEvent, SoundEvent> SMALL_PARTS_TABLE_USED = register("small_parts_table_used");
	public static final DeferredHolder<SoundEvent, SoundEvent> SMOKE_GRENADE_HISS = register("smoke_grenade_hiss");
	public static final DeferredHolder<SoundEvent, SoundEvent> FLASHBANG_EXPLODE = register("flashbang_explode");
	public static final DeferredHolder<SoundEvent, SoundEvent> FLASHBANG_RINGING = register("flashbang_ringing");
	public static final DeferredHolder<SoundEvent, SoundEvent> GENERIC_ITEM_THROW = register("generic_whoosh");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLUNDERBUSS_FIRE = register("blunderbuss_fire");
	public static final DeferredHolder<SoundEvent, SoundEvent> BARBED_WIRE_RATTLE = register("barbed_wire_rattle");
	public static final DeferredHolder<SoundEvent, SoundEvent> BEAR_TRAP_CLOSE = register("bear_trap_close");
	public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TRAP_EXTEND = register("spike_trap_extend");
	public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TRAP_RETRACT = register("spike_trap_retract");
	public static final DeferredHolder<SoundEvent, SoundEvent> PANIC_ALARM_SOUND = register("panic_alarm");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOLDIER_AMBIENT = register("soldier_ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOLDIER_STEP = register("soldier_step");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOLDIER_DEATH = register("soldier_death");
	public static final DeferredHolder<SoundEvent, SoundEvent> SOLDIER_HURT = register("soldier_hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> BATTLEFIELD_AMBIENT = register("battlefield_ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> FIELD_MEDIC_AMBIENT = register("field_medic_ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> FIELD_MEDIC_HURT = register("field_medic_hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> FIELD_MEDIC_DEATH = register("field_medic_death");
	public static final DeferredHolder<SoundEvent, SoundEvent> FIELD_MEDIC_STEP = register("field_medic_step");
	public static final DeferredHolder<SoundEvent, SoundEvent> FLARE_GUN_FIRE = register("flare_gun_fire");
	public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_WARRIOR_AMBIENT = register("wandering_warrior_ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_WARRIOR_HURT = register("wandering_warrior_hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_WARRIOR_DEATH = register("wandering_warrior_death");
	public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_WARRIOR_STEP = register("wandering_warrior_step");
	public static final DeferredHolder<SoundEvent, SoundEvent> STARLIGHT_PLAINS_BACKGROUND_MUSIC = register("starlight_plains_background_music");
	public static final DeferredHolder<SoundEvent, SoundEvent> STARLIGHT_PLAINS_THEME_1 = register("starlight_plains_theme_1");
	public static final DeferredHolder<SoundEvent, SoundEvent> STARLIGHT_PLAINS_THEME_2 = register("starlight_plains_theme_2");
	public static final DeferredHolder<SoundEvent, SoundEvent> TILTROS_WASTES_MUSIC = register("tiltros_wastes_music");
	public static final DeferredHolder<SoundEvent, SoundEvent> DEADMANS_DESERT_BACKGROUND_MUSIC = register("deadmans_desert_background_music");
	public static final DeferredHolder<SoundEvent, SoundEvent> DEADMANS_DESERT_THEME_1 = register("deadmans_desert_theme_1");
	public static final DeferredHolder<SoundEvent, SoundEvent> DEADMANS_DESERT_THEME_2 = register("deadmans_desert_theme_2");
	public static final DeferredHolder<SoundEvent, SoundEvent> LAVA_REVENANT_AMBIENT = register("lava_revenant_ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> LAVA_REVENANT_HURT = register("lava_revenant_hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> LAVA_REVENANT_DEATH = register("lava_revenant_death");
	public static final DeferredHolder<SoundEvent, SoundEvent> LAVA_REVENANT_FLAP = register("lava_revenant_flap");
	public static final DeferredHolder<SoundEvent, SoundEvent> LAVA_REVENANT_SWOOP = register("lava_revenant_swoop");
	public static final DeferredHolder<SoundEvent, SoundEvent> LAVA_REVENANT_BITE = register("lava_revenant_bite");
	public static final DeferredHolder<SoundEvent, SoundEvent> CELESTIAL_TOWER_AMBIENT = register("celestial_tower_ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> CELESTIAL_TOWER_HURT = register("celestial_tower_hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> CELESTIAL_TOWER_DEATH = register("celestial_tower_death");
	public static final DeferredHolder<SoundEvent, SoundEvent> CELESTIAL_TOWER_SUMMON = register("celestial_tower_summon");
	public static final DeferredHolder<SoundEvent, SoundEvent> CELESTIAL_TOWER_VULNERABLE = register("celestial_tower_vulnerable");
	public static final DeferredHolder<SoundEvent, SoundEvent> MORTAR_FIRE = register("mortar_fire");
	public static final DeferredHolder<SoundEvent, SoundEvent> MUSKET_FIRE = register("musket_fire");
	public static final DeferredHolder<SoundEvent, SoundEvent> HAND_CANNON_FIRE = register("hand_cannon_fire");
	public static final DeferredHolder<SoundEvent, SoundEvent> DRAGONS_BREATH_CANNON_FIRE = register("dragons_breath_cannon_fire");
	public static final DeferredHolder<SoundEvent, SoundEvent> FIREFLY_FLYING = register("firefly_flying");
	public static final DeferredHolder<SoundEvent, SoundEvent> STARMITE_AMBIENT = register("starmite_ambient");
	public static final DeferredHolder<SoundEvent, SoundEvent> STARMITE_HURT = register("starmite_hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> STARMITE_DEATH = register("starmite_death");
	public static final DeferredHolder<SoundEvent, SoundEvent> STARMITE_STEP = register("starmite_step");
	public static final DeferredHolder<SoundEvent, SoundEvent> TILTROS_PORTAL_WHOOSH = register("tiltros_portal_whoosh");
	public static final DeferredHolder<SoundEvent, SoundEvent> TILTROS_PORTAL_TRAVEL = register("tiltros_portal_travel");
	public static final DeferredHolder<SoundEvent, SoundEvent> TILTROS_PORTAL_TRIGGER = register("tiltros_portal_trigger");

	private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name)));
	}
}