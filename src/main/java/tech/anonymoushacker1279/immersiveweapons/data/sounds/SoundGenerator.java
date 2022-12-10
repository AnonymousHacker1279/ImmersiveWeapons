package tech.anonymoushacker1279.immersiveweapons.data.sounds;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition.Sound;
import net.minecraftforge.common.data.SoundDefinition.SoundType;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class SoundGenerator extends SoundDefinitionsProvider {

	/**
	 * Creates a new instance of this data provider.
	 *
	 * @param generator The data generator instance provided by the event you are initializing this provider in.
	 * @param modId     The mod ID of the current mod.
	 * @param helper    The existing file helper provided by the event you are initializing this provider in.
	 */
	public SoundGenerator(DataGenerator generator, String modId, ExistingFileHelper helper) {
		super(generator, modId, helper);
	}

	@Override
	public void registerSounds() {
		add(DeferredRegistryHandler.TESLA_ARMOR_EFFECT.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.tesla.effect")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
										"armor/effect/tesla/tesla_armor_effect"),
								SoundType.SOUND)
						.stream()));

		add(DeferredRegistryHandler.TESLA_ARMOR_POWER_DOWN.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.tesla.power_down")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/effect/tesla/tesla_armor_power_down"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.TESLA_ARMOR_POWER_UP.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.tesla.power_up")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/effect/tesla/tesla_armor_power_up"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.TESLA_ARMOR_EQUIP.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.tesla.equip")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/tesla/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/tesla/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/tesla/equip_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.MOLTEN_ARMOR_EQUIP.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.molten.equip")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/molten/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/molten/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/molten/equip_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.VENTUS_ARMOR_EQUIP.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.ventus.equip")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/ventus/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/ventus/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/ventus/equip_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.COPPER_ARMOR_EQUIP.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.copper.equip")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/copper/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/copper/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/copper/equip_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.COBALT_ARMOR_EQUIP.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.cobalt.equip")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/cobalt/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/cobalt/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/cobalt/equip_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.ASTRAL_ARMOR_EQUIP.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.astral.equip")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/astral/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/astral/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/astral/equip_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.STARSTORM_ARMOR_EQUIP.get(), definition()
				.subtitle("immersiveweapons.subtitle.armor.starstorm.equip")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/starstorm/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/starstorm/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"armor/equip/starstorm/equip_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.MORTAR_FIRE.get(), definition()
				.subtitle("immersiveweapons.subtitle.block.mortar.fire")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"block/mortar/fire/fire"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), definition()
				.subtitle("immersiveweapons.subtitle.item.gun.flintlock_fire")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"item/flintlock_pistol/fire/fire"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FLINTLOCK_PISTOL_MISFIRE.get(), definition()
				.subtitle("immersiveweapons.subtitle.item.gun.flintlock_misfire")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"gunpowder_explosion"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.BLUNDERBUSS_FIRE.get(), definition()
				.subtitle("immersiveweapons.subtitle.item.gun.blunderbuss_fire")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"item/blunderbuss/fire/fire"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FLARE_GUN_FIRE.get(), definition()
				.subtitle("immersiveweapons.subtitle.item.gun.flare_gun_fire")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"item/flare_gun/fire/fire"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.MUSKET_FIRE.get(), definition()
				.subtitle("immersiveweapons.subtitle.item.gun.musket_fire")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"item/musket/fire/fire"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.BULLET_WHIZZ.get(), definition()
				.subtitle("immersiveweapons.subtitle.item.gun.bullet_whizz")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"bullet/bullet_whizz_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"bullet/bullet_whizz_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"bullet/bullet_whizz_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.SMOKE_GRENADE_HISS.get(), definition()
				.subtitle("immersiveweapons.subtitle.item.smoke_grenade_hiss")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
										"item/smoke_grenade/hiss/hiss"),
								SoundType.SOUND)
						.stream()));

		add(DeferredRegistryHandler.GENERIC_WHOOSH.get(), definition()
				.subtitle("immersiveweapons.subtitle.item.generic_whoosh")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"generic_whoosh"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.SMALL_PARTS_TABLE_USED.get(), definition()
				.subtitle("immersiveweapons.subtitle.block.small_parts_table.used")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"block/small_parts_table/used/used"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.BARBED_WIRE_RATTLE.get(), definition()
				.subtitle("immersiveweapons.subtitle.block.barbed_wire.rattle")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"block/barbed_wire/rattle/rattle"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.BEAR_TRAP_CLOSE.get(), definition()
				.subtitle("immersiveweapons.subtitle.block.bear_trap.close")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"block/bear_trap/close/close"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.SPIKE_TRAP_EXTEND.get(), definition()
				.subtitle("immersiveweapons.subtitle.block.spike_trap.extend")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"block/spike_trap/extend/extend"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.SPIKE_TRAP_RETRACT.get(), definition()
				.subtitle("immersiveweapons.subtitle.block.spike_trap.retract")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"block/spike_trap/retract/retract"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.PANIC_ALARM_SOUND.get(), definition()
				.subtitle("immersiveweapons.subtitle.block.panic_alarm.alarm")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"block/panic_alarm/alarm/alarm"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.DYING_SOLDIER_AMBIENT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.dying_soldier.ambient")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/ambient/ambient_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/ambient/ambient_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.DYING_SOLDIER_STEP.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.dying_soldier.step")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/step/step_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/step/step_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/step/step_2"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/step/step_3"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.DYING_SOLDIER_DEATH.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.dying_soldier.death")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/death/death"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.DYING_SOLDIER_HURT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.dying_soldier.hurt")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/hurt/hurt_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/dying_soldier/hurt/hurt_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FIELD_MEDIC_AMBIENT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.field_medic.ambient")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/ambient/ambient_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/ambient/ambient_2"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/ambient/ambient_3"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FIELD_MEDIC_ATTACK.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.field_medic.attack")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/attack/attack"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FIELD_MEDIC_HURT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.field_medic.hurt")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/hurt/hurt_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/hurt/hurt_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FIELD_MEDIC_DEATH.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.field_medic.death")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/death/death"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FIELD_MEDIC_STEP.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.field_medic.step")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/step/step_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/step/step_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/step/step_2"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/step/step_3"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.WANDERING_WARRIOR_AMBIENT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.wandering_warrior.ambient")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/ambient/ambient_1"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.WANDERING_WARRIOR_HURT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.wandering_warrior.hurt")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/hurt/hurt_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/hurt/hurt_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.WANDERING_WARRIOR_DEATH.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.wandering_warrior.death")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/death/death"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.WANDERING_WARRIOR_STEP.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.wandering_warrior.step")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/step/step_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/step/step_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/step/step_2"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/step/step_3"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.LAVA_REVENANT_AMBIENT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.lava_revenant.ambient")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/ambient/ambient_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/ambient/ambient_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.LAVA_REVENANT_HURT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.lava_revenant.hurt")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/hurt/hurt_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/hurt/hurt_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.LAVA_REVENANT_DEATH.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.lava_revenant.death")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/death/death_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/death/death_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/death/death_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.LAVA_REVENANT_FLAP.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.lava_revenant.flap")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_2"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_3"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_4"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_5"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.LAVA_REVENANT_SWOOP.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.lava_revenant.swoop")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/swoop/swoop_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/swoop/swoop_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/swoop/swoop_2"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/swoop/swoop_3"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.LAVA_REVENANT_BITE.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.lava_revenant.bite")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/bite/bite_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/bite/bite_1"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.CELESTIAL_TOWER_AMBIENT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.celestial_tower.ambient")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/ambient/ambient_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/ambient/ambient_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.CELESTIAL_TOWER_HURT.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.celestial_tower.hurt")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/hurt/hurt_1"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.CELESTIAL_TOWER_DEATH.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.celestial_tower.death")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/death/death"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.CELESTIAL_TOWER_SUMMON.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.celestial_tower.summon")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/summon/summon"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.CELESTIAL_TOWER_VULNERABLE.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.celestial_tower.vulnerable")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/vulnerable/vulnerable"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.FIREFLY_FLYING.get(), definition()
				.subtitle("immersiveweapons.subtitle.entity.firefly.flying")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/firefly/flying/flying_0"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/firefly/flying/flying_1"),
						SoundType.SOUND))
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
								"entity/firefly/flying/flying_2"),
						SoundType.SOUND)));

		add(DeferredRegistryHandler.BATTLEFIELD_AMBIENT.get(), definition()
				.subtitle("immersiveweapons.subtitle.biome.battlefield.ambient")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
										"biome/battlefield/ambient/ambient"),
								SoundType.SOUND)
						.stream()));

		add(DeferredRegistryHandler.TILTROS_AMBIENT.get(), definition()
				.subtitle("immersiveweapons.subtitle.biome.tiltros.ambient")
				.with(Sound.sound(new ResourceLocation(ImmersiveWeapons.MOD_ID,
										"biome/tiltros/ambient/ambient"),
								SoundType.SOUND)
						.stream()));
	}
}