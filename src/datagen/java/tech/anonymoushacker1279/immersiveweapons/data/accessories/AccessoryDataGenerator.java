package tech.anonymoushacker1279.immersiveweapons.data.accessories;

import net.minecraft.data.PackOutput;
import tech.anonymoushacker1279.immersiveweapons.data.data_maps.AccessoryItemEffects;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessorySlot;

import java.util.Set;

public class AccessoryDataGenerator extends AccessoryDataProvider {

	public AccessoryDataGenerator(PackOutput output) {
		super(output);
	}

	@Override
	protected Set<Accessory> getAccessories() {
		return Set.of(
				new Accessory(ItemRegistry.SATCHEL.getDelegate(), AccessorySlot.BELT, AccessoryItemEffects.SATCHEL),
				new Accessory(ItemRegistry.POWDER_HORN.getDelegate(), AccessorySlot.BODY, AccessoryItemEffects.POWDER_HORN),
				new Accessory(ItemRegistry.BERSERKERS_AMULET.getDelegate(), AccessorySlot.CHARM, AccessoryItemEffects.BERSERKERS_AMULET),
				new Accessory(ItemRegistry.HANS_BLESSING.getDelegate(), AccessorySlot.SPIRIT, AccessoryItemEffects.HANS_BLESSING),
				new Accessory(ItemRegistry.CELESTIAL_SPIRIT.getDelegate(), AccessorySlot.SPIRIT, AccessoryItemEffects.CELESTIAL_SPIRIT),
				new Accessory(ItemRegistry.VOID_BLESSING.getDelegate(), AccessorySlot.SPIRIT, AccessoryItemEffects.VOID_BLESSING),
				new Accessory(ItemRegistry.BLADEMASTER_EMBLEM.getDelegate(), AccessorySlot.CHARM, AccessoryItemEffects.BLADEMASTER_EMBLEM),
				new Accessory(ItemRegistry.DEADEYE_PENDANT.getDelegate(), AccessorySlot.NECKLACE, AccessoryItemEffects.DEADEYE_PENDANT),
				new Accessory(ItemRegistry.BLOATED_HEART.getDelegate(), AccessorySlot.BODY, AccessoryItemEffects.BLOATED_HEART),
				new Accessory(ItemRegistry.NETHERITE_SHIELD.getDelegate(), AccessorySlot.BODY, AccessoryItemEffects.NETHERITE_SHIELD),
				new Accessory(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.getDelegate(), AccessorySlot.HAND, AccessoryItemEffects.MELEE_MASTERS_MOLTEN_GLOVE),
				new Accessory(ItemRegistry.IRON_FIST.getDelegate(), AccessorySlot.HAND, AccessoryItemEffects.IRON_FIST),
				new Accessory(ItemRegistry.GLOVE_OF_RAPID_SWINGING.getDelegate(), AccessorySlot.HAND, AccessoryItemEffects.GLOVE_OF_RAPID_SWINGING),
				new Accessory(ItemRegistry.HAND_OF_DOOM.getDelegate(), AccessorySlot.HAND, AccessoryItemEffects.HAND_OF_DOOM),
				new Accessory(ItemRegistry.COPPER_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.COPPER_RING),
				new Accessory(ItemRegistry.IRON_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.IRON_RING),
				new Accessory(ItemRegistry.COBALT_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.COBALT_RING),
				new Accessory(ItemRegistry.GOLDEN_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.GOLDEN_RING),
				new Accessory(ItemRegistry.AMETHYST_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.AMETHYST_RING),
				new Accessory(ItemRegistry.EMERALD_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.EMERALD_RING),
				new Accessory(ItemRegistry.DIAMOND_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.DIAMOND_RING),
				new Accessory(ItemRegistry.NETHERITE_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.NETHERITE_RING),
				new Accessory(ItemRegistry.DEATH_GEM_RING.getDelegate(), AccessorySlot.RING, AccessoryItemEffects.DEATH_GEM_RING),
				new Accessory(ItemRegistry.MEDAL_OF_ADEQUACY.getDelegate(), AccessorySlot.NECKLACE, AccessoryItemEffects.MEDAL_OF_ADEQUACY),
				new Accessory(ItemRegistry.DEPTH_CHARM.getDelegate(), AccessorySlot.CHARM, AccessoryItemEffects.DEPTH_CHARM),
				new Accessory(ItemRegistry.REINFORCED_DEPTH_CHARM.getDelegate(), AccessorySlot.CHARM, AccessoryItemEffects.REINFORCED_DEPTH_CHARM),
				new Accessory(ItemRegistry.INSOMNIA_AMULET.getDelegate(), AccessorySlot.NECKLACE, AccessoryItemEffects.INSOMNIA_AMULET),
				new Accessory(ItemRegistry.GOGGLES.getDelegate(), AccessorySlot.HEAD, AccessoryItemEffects.GOGGLES),
				new Accessory(ItemRegistry.LAVA_GOGGLES.getDelegate(), AccessorySlot.HEAD, AccessoryItemEffects.LAVA_GOGGLES),
				new Accessory(ItemRegistry.NIGHT_VISION_GOGGLES.getDelegate(), AccessorySlot.HEAD, AccessoryItemEffects.NIGHT_VISION_GOGGLES),
				new Accessory(ItemRegistry.AGILITY_BRACELET.getDelegate(), AccessorySlot.BRACELET, AccessoryItemEffects.AGILITY_BRACELET),
				new Accessory(ItemRegistry.BLOODY_CLOTH.getDelegate(), AccessorySlot.HEAD, AccessoryItemEffects.BLOODY_CLOTH),
				new Accessory(ItemRegistry.ANCIENT_SCROLL.getDelegate(), AccessorySlot.CHARM, AccessoryItemEffects.ANCIENT_SCROLL),
				new Accessory(ItemRegistry.HOLY_MANTLE.getDelegate(), AccessorySlot.BELT, AccessoryItemEffects.HOLY_MANTLE),
				new Accessory(ItemRegistry.VENSTRAL_JAR.getDelegate(), AccessorySlot.BELT, AccessoryItemEffects.VENSTRAL_JAR),
				new Accessory(ItemRegistry.SUPER_BLANKET_CAPE.getDelegate(), AccessorySlot.BODY, AccessoryItemEffects.SUPER_BLANKET_CAPE),
				new Accessory(ItemRegistry.MEDAL_OF_HONOR.getDelegate(), AccessorySlot.CHARM, AccessoryItemEffects.MEDAL_OF_HONOR),
				new Accessory(ItemRegistry.MEDAL_OF_DISHONOR.getDelegate(), AccessorySlot.CHARM, AccessoryItemEffects.MEDAL_OF_DISHONOR)
		);
	}
}