package tech.anonymoushacker1279.immersiveweapons.data.data_maps;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.VibrationFrequency;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.GameEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessorySlot;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.FlammablePowder;

import java.util.concurrent.CompletableFuture;

public class DataMapsGenerator extends DataMapProvider {

	public DataMapsGenerator(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void gather() {
		builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES)
				.add(GameEventRegistry.FLASHBANG_EXPLODE, new VibrationFrequency(15), false)
				.add(GameEventRegistry.SMOKE_GRENADE_HISS, new VibrationFrequency(14), false)
				.add(GameEventRegistry.PANIC_ALARM_TRIGGER, new VibrationFrequency(15), false);

		builder(NeoForgeDataMaps.COMPOSTABLES)
				.add(BlockItemRegistry.STARDUST_SAPLING_ITEM.get().builtInRegistryHolder(), new Compostable(0.3f), false)
				.add(BlockItemRegistry.STARDUST_LEAVES_ITEM.get().builtInRegistryHolder(), new Compostable(0.5f), false)
				.add(BlockItemRegistry.BURNED_OAK_BRANCH_ITEM.get().builtInRegistryHolder(), new Compostable(0.3f), false)
				.add(BlockItemRegistry.MOONGLOW_ITEM.get().builtInRegistryHolder(), new Compostable(0.65f), false)
				.add(BlockItemRegistry.DEATHWEED_ITEM.get().builtInRegistryHolder(), new Compostable(0.65f), false)
				.add(ItemRegistry.CHOCOLATE_BAR.get().builtInRegistryHolder(), new Compostable(0.2f), false)
				.add(ItemRegistry.MRE.get().builtInRegistryHolder(), new Compostable(1.0f), false);

		builder(AbstractGunItem.POWDER_TYPE)
				.add(ItemRegistry.SULFUR_DUST.get().builtInRegistryHolder(), new FlammablePowder(0.9f, -0.05f, 2, 3), false)
				.add(ItemRegistry.BLACKPOWDER.get().builtInRegistryHolder(), new FlammablePowder(0.75f, 0.025f, 2, 2), false)
				.add(Items.GUNPOWDER.builtInRegistryHolder(), new FlammablePowder(0.33f, 0.05f, 1, 1), false)
				.add(Items.BLAZE_POWDER.builtInRegistryHolder(), new FlammablePowder(0.25f, 0.1f, 1, 0, true), false);

		builder(Accessory.ACCESSORY)
				.add(ItemRegistry.SATCHEL.get().builtInRegistryHolder(), new Accessory(AccessorySlot.BELT, AccessoryItemEffects.SATCHEL), false)
				.add(ItemRegistry.POWDER_HORN.get().builtInRegistryHolder(), new Accessory(AccessorySlot.BODY, AccessoryItemEffects.POWDER_HORN), false)
				.add(ItemRegistry.BERSERKERS_AMULET.get().builtInRegistryHolder(), new Accessory(AccessorySlot.CHARM, AccessoryItemEffects.BERSERKERS_AMULET), false)
				.add(ItemRegistry.HANS_BLESSING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.SPIRIT, AccessoryItemEffects.HANS_BLESSING), false)
				.add(ItemRegistry.CELESTIAL_SPIRIT.get().builtInRegistryHolder(), new Accessory(AccessorySlot.SPIRIT, AccessoryItemEffects.CELESTIAL_SPIRIT), false)
				.add(ItemRegistry.VOID_BLESSING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.SPIRIT, AccessoryItemEffects.VOID_BLESSING), false)
				.add(ItemRegistry.BLADEMASTER_EMBLEM.get().builtInRegistryHolder(), new Accessory(AccessorySlot.CHARM, AccessoryItemEffects.BLADEMASTER_EMBLEM), false)
				.add(ItemRegistry.DEADEYE_PENDANT.get().builtInRegistryHolder(), new Accessory(AccessorySlot.NECKLACE, AccessoryItemEffects.DEADEYE_PENDANT), false)
				.add(ItemRegistry.BLOATED_HEART.get().builtInRegistryHolder(), new Accessory(AccessorySlot.BODY, AccessoryItemEffects.BLOATED_HEART), false)
				.add(ItemRegistry.NETHERITE_SHIELD.get().builtInRegistryHolder(), new Accessory(AccessorySlot.BODY, AccessoryItemEffects.NETHERITE_SHIELD), false)
				.add(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get().builtInRegistryHolder(), new Accessory(AccessorySlot.HAND, AccessoryItemEffects.MELEE_MASTERS_MOLTEN_GLOVE), false)
				.add(ItemRegistry.IRON_FIST.get().builtInRegistryHolder(), new Accessory(AccessorySlot.HAND, AccessoryItemEffects.IRON_FIST), false)
				.add(ItemRegistry.GLOVE_OF_RAPID_SWINGING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.HAND, AccessoryItemEffects.GLOVE_OF_RAPID_SWINGING), false)
				.add(ItemRegistry.HAND_OF_DOOM.get().builtInRegistryHolder(), new Accessory(AccessorySlot.HAND, AccessoryItemEffects.HAND_OF_DOOM), false)
				.add(ItemRegistry.COPPER_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.COPPER_RING), false)
				.add(ItemRegistry.IRON_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.IRON_RING), false)
				.add(ItemRegistry.COBALT_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.COBALT_RING), false)
				.add(ItemRegistry.GOLDEN_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.GOLDEN_RING), false)
				.add(ItemRegistry.AMETHYST_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.AMETHYST_RING), false)
				.add(ItemRegistry.EMERALD_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.EMERALD_RING), false)
				.add(ItemRegistry.DIAMOND_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.DIAMOND_RING), false)
				.add(ItemRegistry.NETHERITE_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.NETHERITE_RING), false)
				.add(ItemRegistry.DEATH_GEM_RING.get().builtInRegistryHolder(), new Accessory(AccessorySlot.RING, AccessoryItemEffects.DEATH_GEM_RING), false)
				.add(ItemRegistry.MEDAL_OF_ADEQUACY.get().builtInRegistryHolder(), new Accessory(AccessorySlot.NECKLACE, AccessoryItemEffects.MEDAL_OF_ADEQUACY), false)
				.add(ItemRegistry.DEPTH_CHARM.get().builtInRegistryHolder(), new Accessory(AccessorySlot.CHARM, AccessoryItemEffects.DEPTH_CHARM), false)
				.add(ItemRegistry.REINFORCED_DEPTH_CHARM.get().builtInRegistryHolder(), new Accessory(AccessorySlot.CHARM, AccessoryItemEffects.REINFORCED_DEPTH_CHARM), false)
				.add(ItemRegistry.INSOMNIA_AMULET.get().builtInRegistryHolder(), new Accessory(AccessorySlot.NECKLACE, AccessoryItemEffects.INSOMNIA_AMULET), false)
				.add(ItemRegistry.GOGGLES.get().builtInRegistryHolder(), new Accessory(AccessorySlot.HEAD, AccessoryItemEffects.GOGGLES), false)
				.add(ItemRegistry.LAVA_GOGGLES.get().builtInRegistryHolder(), new Accessory(AccessorySlot.HEAD, AccessoryItemEffects.LAVA_GOGGLES), false)
				.add(ItemRegistry.NIGHT_VISION_GOGGLES.get().builtInRegistryHolder(), new Accessory(AccessorySlot.HEAD, AccessoryItemEffects.NIGHT_VISION_GOGGLES), false)
				.add(ItemRegistry.AGILITY_BRACELET.get().builtInRegistryHolder(), new Accessory(AccessorySlot.BRACELET, AccessoryItemEffects.AGILITY_BRACELET), false)
				.add(ItemRegistry.BLOODY_CLOTH.get().builtInRegistryHolder(), new Accessory(AccessorySlot.HEAD, AccessoryItemEffects.BLOODY_CLOTH), false)
				.add(ItemRegistry.ANCIENT_SCROLL.get().builtInRegistryHolder(), new Accessory(AccessorySlot.CHARM, AccessoryItemEffects.ANCIENT_SCROLL), false)
				.add(ItemRegistry.HOLY_MANTLE.get().builtInRegistryHolder(), new Accessory(AccessorySlot.BELT, AccessoryItemEffects.HOLY_MANTLE), false)
				.add(ItemRegistry.VENSTRAL_JAR.get().builtInRegistryHolder(), new Accessory(AccessorySlot.BELT, AccessoryItemEffects.VENSTRAL_JAR), false)
				.add(ItemRegistry.SUPER_BLANKET_CAPE.get().builtInRegistryHolder(), new Accessory(AccessorySlot.BODY, AccessoryItemEffects.SUPER_BLANKET_CAPE), false)
				.add(ItemRegistry.MEDAL_OF_HONOR.get().builtInRegistryHolder(), new Accessory(AccessorySlot.CHARM, AccessoryItemEffects.MEDAL_OF_HONOR), false)
				.add(ItemRegistry.MEDAL_OF_DISHONOR.get().builtInRegistryHolder(), new Accessory(AccessorySlot.CHARM, AccessoryItemEffects.MEDAL_OF_DISHONOR), false);
	}
}