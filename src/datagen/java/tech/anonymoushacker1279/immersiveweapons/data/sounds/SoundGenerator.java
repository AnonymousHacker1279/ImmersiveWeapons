package tech.anonymoushacker1279.immersiveweapons.data.sounds;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition.Sound;
import net.neoforged.neoforge.common.data.SoundDefinition.SoundType;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class SoundGenerator extends SoundDefinitionsProvider {

	/**
	 * Creates a new instance of this data provider.
	 *
	 * @param modId  The mod ID of the current mod.
	 * @param helper The existing file helper provided by the event you are initializing this provider in.
	 */
	public SoundGenerator(PackOutput output, String modId, ExistingFileHelper helper) {
		super(output, modId, helper);
	}

	@Override
	public void registerSounds() {
		add(SoundEventRegistry.TESLA_ARMOR_EFFECT.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.tesla.effect")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/effect/tesla/tesla_armor_effect"),
						SoundType.SOUND)));

		add(SoundEventRegistry.TESLA_ARMOR_POWER_DOWN.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.tesla.power_down")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/effect/tesla/tesla_armor_power_down"),
						SoundType.SOUND)));

		add(SoundEventRegistry.TESLA_ARMOR_POWER_UP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.tesla.power_up")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/effect/tesla/tesla_armor_power_up"),
						SoundType.SOUND)));

		add(SoundEventRegistry.TESLA_ARMOR_EQUIP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.tesla.equip")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/tesla/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/tesla/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/tesla/equip_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.MOLTEN_ARMOR_EQUIP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.molten.equip")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/molten/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/molten/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/molten/equip_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.VENTUS_ARMOR_EQUIP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.ventus.equip")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/ventus/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/ventus/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/ventus/equip_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.COPPER_ARMOR_EQUIP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.copper.equip")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/copper/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/copper/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/copper/equip_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.COBALT_ARMOR_EQUIP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.cobalt.equip")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/cobalt/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/cobalt/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/cobalt/equip_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.ASTRAL_ARMOR_EQUIP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.astral.equip")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/astral/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/astral/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/astral/equip_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.STARSTORM_ARMOR_EQUIP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.starstorm.equip")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/starstorm/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/starstorm/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/starstorm/equip_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.VOID_ARMOR_EQUIP.get(), definition()
				.subtitle("subtitles.immersiveweapons.armor.void.equip")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/void/equip_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/void/equip_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"armor/equip/void/equip_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.MORTAR_FIRE.get(), definition()
				.subtitle("subtitles.immersiveweapons.block.mortar.fire")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"block/mortar/fire/fire"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FLINTLOCK_PISTOL_FIRE.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.gun.flintlock.fire")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"item/flintlock_pistol/fire/fire"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FLINTLOCK_PISTOL_MISFIRE.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.gun.flintlock.misfire")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"gunpowder_explosion"),
						SoundType.SOUND)));

		add(SoundEventRegistry.BLUNDERBUSS_FIRE.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.gun.blunderbuss.fire")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"item/blunderbuss/fire/fire"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FLARE_GUN_FIRE.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.gun.flare_gun.fire")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"item/flare_gun/fire/fire"),
						SoundType.SOUND)));

		add(SoundEventRegistry.MUSKET_FIRE.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.gun.musket.fire")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"item/musket/fire/fire"),
						SoundType.SOUND)));

		add(SoundEventRegistry.HAND_CANNON_FIRE.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.gun.hand_cannon.fire")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"item/hand_cannon/fire/fire"),
						SoundType.SOUND)));

		add(SoundEventRegistry.BULLET_WHIZZ.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.gun.bullet_whizz")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"bullet/bullet_whizz_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"bullet/bullet_whizz_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"bullet/bullet_whizz_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.SMOKE_GRENADE_HISS.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.smoke_grenade_hiss")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"item/smoke_grenade/hiss/hiss"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FLASHBANG_EXPLODE.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.flashbang.explode")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"item/flashbang/explode/explode"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FLASHBANG_RINGING.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.flashbang.ringing")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"item/flashbang/ringing/ringing"),
						SoundType.SOUND)));

		add(SoundEventRegistry.GENERIC_ITEM_THROW.get(), definition()
				.subtitle("subtitles.immersiveweapons.item.generic_item_throw")
				.with(Sound.sound(ResourceLocation.withDefaultNamespace("entity.snowball.throw"),
						SoundType.EVENT)));

		add(SoundEventRegistry.SMALL_PARTS_TABLE_USED.get(), definition()
				.subtitle("subtitles.immersiveweapons.block.small_parts_table.used")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"block/small_parts_table/used/used"),
						SoundType.SOUND)));

		add(SoundEventRegistry.BARBED_WIRE_RATTLE.get(), definition()
				.subtitle("subtitles.immersiveweapons.block.barbed_wire.rattle")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"block/barbed_wire/rattle/rattle"),
						SoundType.SOUND)));

		add(SoundEventRegistry.BEAR_TRAP_CLOSE.get(), definition()
				.subtitle("subtitles.immersiveweapons.block.bear_trap.close")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"block/bear_trap/close/close"),
						SoundType.SOUND)));

		add(SoundEventRegistry.SPIKE_TRAP_EXTEND.get(), definition()
				.subtitle("subtitles.immersiveweapons.block.spike_trap.extend")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"block/spike_trap/extend/extend"),
						SoundType.SOUND)));

		add(SoundEventRegistry.SPIKE_TRAP_RETRACT.get(), definition()
				.subtitle("subtitles.immersiveweapons.block.spike_trap.retract")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"block/spike_trap/retract/retract"),
						SoundType.SOUND)));

		add(SoundEventRegistry.PANIC_ALARM_SOUND.get(), definition()
				.subtitle("subtitles.immersiveweapons.block.panic_alarm.alarm")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"block/panic_alarm/alarm/alarm"),
						SoundType.SOUND)));

		add(SoundEventRegistry.SOLDIER_AMBIENT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.soldier.ambient")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/ambient/ambient_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/ambient/ambient_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.SOLDIER_STEP.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.soldier.step")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/step/step_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/step/step_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/step/step_2"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/step/step_3"),
						SoundType.SOUND)));

		add(SoundEventRegistry.SOLDIER_DEATH.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.soldier.death")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/death/death"),
						SoundType.SOUND)));

		add(SoundEventRegistry.SOLDIER_HURT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.soldier.hurt")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/hurt/hurt_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/soldier/hurt/hurt_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FIELD_MEDIC_AMBIENT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.field_medic.ambient")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/ambient/ambient_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/ambient/ambient_2"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/ambient/ambient_3"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FIELD_MEDIC_HURT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.field_medic.hurt")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/hurt/hurt_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/hurt/hurt_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FIELD_MEDIC_DEATH.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.field_medic.death")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/death/death"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FIELD_MEDIC_STEP.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.field_medic.step")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/step/step_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/step/step_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/step/step_2"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/field_medic/step/step_3"),
						SoundType.SOUND)));

		add(SoundEventRegistry.WANDERING_WARRIOR_AMBIENT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.wandering_warrior.ambient")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/ambient/ambient_1"),
						SoundType.SOUND)));

		add(SoundEventRegistry.WANDERING_WARRIOR_HURT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.wandering_warrior.hurt")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/hurt/hurt_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/hurt/hurt_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.WANDERING_WARRIOR_DEATH.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.wandering_warrior.death")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/death/death"),
						SoundType.SOUND)));

		add(SoundEventRegistry.WANDERING_WARRIOR_STEP.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.wandering_warrior.step")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/step/step_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/step/step_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/step/step_2"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/wandering_warrior/step/step_3"),
						SoundType.SOUND)));

		add(SoundEventRegistry.LAVA_REVENANT_AMBIENT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.lava_revenant.ambient")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/ambient/ambient_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/ambient/ambient_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.LAVA_REVENANT_HURT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.lava_revenant.hurt")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/hurt/hurt_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/hurt/hurt_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.LAVA_REVENANT_DEATH.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.lava_revenant.death")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/death/death_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/death/death_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/death/death_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.LAVA_REVENANT_FLAP.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.lava_revenant.flap")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_2"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_3"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_4"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/flap/flap_5"),
						SoundType.SOUND)));

		add(SoundEventRegistry.LAVA_REVENANT_SWOOP.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.lava_revenant.swoop")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/swoop/swoop_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/swoop/swoop_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/swoop/swoop_2"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/swoop/swoop_3"),
						SoundType.SOUND)));

		add(SoundEventRegistry.LAVA_REVENANT_BITE.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.lava_revenant.bite")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/bite/bite_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/lava_revenant/bite/bite_1"),
						SoundType.SOUND)));

		add(SoundEventRegistry.CELESTIAL_TOWER_AMBIENT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.celestial_tower.ambient")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/ambient/ambient_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/ambient/ambient_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/ambient/ambient_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.CELESTIAL_TOWER_HURT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.celestial_tower.hurt")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/hurt/hurt_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/hurt/hurt_1"),
						SoundType.SOUND)));

		add(SoundEventRegistry.CELESTIAL_TOWER_DEATH.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.celestial_tower.death")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/death/death"),
						SoundType.SOUND)));

		add(SoundEventRegistry.CELESTIAL_TOWER_SUMMON.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.celestial_tower.summon")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/summon/summon"),
						SoundType.SOUND)));

		add(SoundEventRegistry.CELESTIAL_TOWER_VULNERABLE.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.celestial_tower.vulnerable")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/celestial_tower/vulnerable/vulnerable"),
						SoundType.SOUND)));

		add(SoundEventRegistry.FIREFLY_FLYING.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.firefly.flying")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/firefly/flying/flying_0"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/firefly/flying/flying_1"),
						SoundType.SOUND))
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"entity/firefly/flying/flying_2"),
						SoundType.SOUND)));

		add(SoundEventRegistry.STARMITE_AMBIENT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.starmite.ambient")
				.with(Sound.sound(ResourceLocation.withDefaultNamespace("entity.silverfish.ambient"),
						SoundType.EVENT)));

		add(SoundEventRegistry.STARMITE_HURT.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.starmite.hurt")
				.with(Sound.sound(ResourceLocation.withDefaultNamespace("entity.silverfish.hurt"),
						SoundType.EVENT)));

		add(SoundEventRegistry.STARMITE_DEATH.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.starmite.death")
				.with(Sound.sound(ResourceLocation.withDefaultNamespace("entity.silverfish.death"),
						SoundType.EVENT)));

		add(SoundEventRegistry.STARMITE_STEP.get(), definition()
				.subtitle("subtitles.immersiveweapons.entity.starmite.step")
				.with(Sound.sound(ResourceLocation.withDefaultNamespace("entity.silverfish.step"),
						SoundType.EVENT)));

		add(SoundEventRegistry.BATTLEFIELD_AMBIENT.get(), definition()
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/battlefield/ambient/ambient"),
								SoundType.SOUND)
						.stream()));

		add(SoundEventRegistry.STARLIGHT_PLAINS_THEME_1.get(), definition()
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/starlight_plains/music/music_0"),
								SoundType.SOUND)
						.stream()));

		add(SoundEventRegistry.STARLIGHT_PLAINS_THEME_2.get(), definition()
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/starlight_plains/music/music_1"),
								SoundType.SOUND)
						.stream()));

		add(SoundEventRegistry.STARLIGHT_PLAINS_BACKGROUND_MUSIC.get(), definition()
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/starlight_plains/music/music_0"),
								SoundType.SOUND)
						.stream())
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/starlight_plains/music/music_1"),
								SoundType.SOUND)
						.stream()));

		add(SoundEventRegistry.TILTROS_WASTES_MUSIC.get(), definition()
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/tiltros_wastes/music/music"),
								SoundType.SOUND)
						.stream()));

		add(SoundEventRegistry.DEADMANS_DESERT_THEME_1.get(), definition()
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/deadmans_desert/music/music_0"),
								SoundType.SOUND)
						.stream()));

		add(SoundEventRegistry.DEADMANS_DESERT_THEME_2.get(), definition()
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/deadmans_desert/music/music_1"),
								SoundType.SOUND)
						.stream()));

		add(SoundEventRegistry.DEADMANS_DESERT_BACKGROUND_MUSIC.get(), definition()
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/deadmans_desert/music/music_0"),
								SoundType.SOUND)
						.stream())
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
										"biome/deadmans_desert/music/music_1"),
								SoundType.SOUND)
						.stream()));

		add(SoundEventRegistry.TILTROS_PORTAL_WHOOSH.get(), definition()
				.subtitle("subtitles.immersiveweapons.block.tiltros_portal.whoosh")
				.with(Sound.sound(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
								"block/tiltros_portal/whoosh/whoosh"),
						SoundType.SOUND)));
	}
}