package tech.anonymoushacker1279.immersiveweapons.init;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.config.ConfigHelper.TomlConfigOps;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.IWDimensions;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkygazerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomBoatType;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomArmorMaterials;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class PostSetupHandler {

	/**
	 * Initialize attributes which must be applied after setup.
	 */
	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing post-setup handler");

		// Set custom armor equip sounds, as these don't exist during the initialization of materials
		CustomArmorMaterials.TESLA.setEquipSound(SoundEventRegistry.TESLA_ARMOR_EQUIP.get());
		CustomArmorMaterials.MOLTEN.setEquipSound(SoundEventRegistry.MOLTEN_ARMOR_EQUIP.get());
		CustomArmorMaterials.VENTUS.setEquipSound(SoundEventRegistry.VENTUS_ARMOR_EQUIP.get());
		CustomArmorMaterials.COPPER.setEquipSound(SoundEventRegistry.COPPER_ARMOR_EQUIP.get());
		CustomArmorMaterials.COBALT.setEquipSound(SoundEventRegistry.COBALT_ARMOR_EQUIP.get());
		CustomArmorMaterials.ASTRAL.setEquipSound(SoundEventRegistry.ASTRAL_ARMOR_EQUIP.get());
		CustomArmorMaterials.STARSTORM.setEquipSound(SoundEventRegistry.STARSTORM_ARMOR_EQUIP.get());

		// Add custom plants to the flower pot block
		FlowerPotBlock emptyPot = ((FlowerPotBlock) Blocks.FLOWER_POT);
		emptyPot.addPlant(BuiltInRegistries.BLOCK.getKey(BlockRegistry.MOONGLOW.get()), BlockRegistry.POTTED_MOONGLOW);
		emptyPot.addPlant(BuiltInRegistries.BLOCK.getKey(BlockRegistry.DEATHWEED.get()), BlockRegistry.POTTED_DEATHWEED);

		// Setup custom boats
		ItemRegistry.BURNED_OAK_BOAT.get()
				.postSetup(EntityRegistry.BURNED_OAK_BOAT_ENTITY.get(), CustomBoatType.BURNED_OAK);
		ItemRegistry.BURNED_OAK_CHEST_BOAT.get()
				.postSetup(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get(), CustomBoatType.BURNED_OAK_CHEST);
		ItemRegistry.STARDUST_BOAT.get()
				.postSetup(EntityRegistry.STARDUST_BOAT_ENTITY.get(), CustomBoatType.STARDUST);
		ItemRegistry.STARDUST_CHEST_BOAT.get()
				.postSetup(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get(), CustomBoatType.STARDUST_CHEST);

		// Set brewing recipes, as these are not done via JSON like other recipes
		// Celestial potions
		PotionBrewing.addMix(
				Potions.AWKWARD,
				BlockItemRegistry.MOONGLOW_ITEM.get(),
				PotionRegistry.CELESTIAL_BREW_POTION.get());
		PotionBrewing.addMix(
				PotionRegistry.CELESTIAL_BREW_POTION.get(),
				Items.REDSTONE,
				PotionRegistry.LONG_CELESTIAL_BREW_POTION.get());

		// Death potions
		PotionBrewing.addMix(
				Potions.AWKWARD,
				BlockItemRegistry.DEATHWEED_ITEM.get(),
				PotionRegistry.DEATH_POTION.get());
		PotionBrewing.addMix(
				PotionRegistry.DEATH_POTION.get(),
				Items.GLOWSTONE_DUST,
				PotionRegistry.STRONG_DEATH_POTION.get());
		PotionBrewing.addMix(
				PotionRegistry.DEATH_POTION.get(),
				Items.REDSTONE,
				PotionRegistry.LONG_DEATH_POTION.get());
		PotionBrewing.addMix(
				Potions.STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.DEATH_POTION.get());
		PotionBrewing.addMix(
				Potions.STRONG_STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.STRONG_DEATH_POTION.get());
		PotionBrewing.addMix(
				Potions.LONG_STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.LONG_DEATH_POTION.get());

		// Populate enchant cap map
		TomlConfigOps.INSTANCE.getMapValues(ImmersiveWeapons.COMMON_CONFIG.skygazerEnchantCaps().get())
				.result()
				.ifPresent(map -> map.forEach((pair) -> SkygazerEntity.ENCHANT_CAPS.put((String) pair.getFirst(), (Integer) pair.getSecond())));

		// Initialize custom portals
		CustomPortalBuilder.beginPortal()
				.frameBlock(BlockRegistry.COBALT_BLOCK.get())
				.lightWithItem(ItemRegistry.AZUL_KEYSTONE.get())
				.destDimID(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros"))
				.tintColor(0, 71, 171)
				.flatPortal()
				.registerPostTPEvent(entity -> {
					if (!entity.level().dimension().equals(IWDimensions.TILTROS)) {
						return;
					}

					generateBiodome(entity.level(), entity.blockPosition(), 7);
				})
				.registerPortal();
	}

	public static void generateBiodome(Level level, BlockPos center, int radius) {
		// Scan for Biodome Life Support Units in the area. If any are found, do not generate a biodome.
		AABB aabb = new AABB(center.offset(-radius, -radius, -radius).getCenter(), center.offset(radius, radius, radius).getCenter());
		if (level.getBlockStates(aabb).anyMatch(blockState -> blockState.is(BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get()))) {
			return;
		}

		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					BlockPos pos = center.offset(x, y, z);
					double distance = Math.sqrt(x * x + y * y + z * z);
					if (distance <= radius) {
						if (level.getBlockState(pos).is(BlockRegistry.BLOOD_SAND.get())) {
							if (level.getBlockState(pos.above()).isAir()) {
								level.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
							} else {
								level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
							}
						} else if (level.getBlockState(pos).is(BlockRegistry.BLOOD_SANDSTONE.get())) {
							level.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
						}
					}
					if (distance >= radius - 1 && distance <= radius) {
						if (level.getBlockState(pos).isAir()) {
							level.setBlock(pos, Blocks.GLASS.defaultBlockState(), 3);
						}
					}
				}
			}
		}

		// Determine the positions for the Biodome Life Support Units
		BlockPos pos1 = center.offset(-radius + 4, 0, -radius + 4);
		BlockPos pos2 = center.offset(radius - 4, 0, radius - 4);

		// Move upwards until we find a grass block or reach past the radius
		while (!level.getBlockState(pos1).is(Blocks.GRASS_BLOCK) && pos1.getY() < center.getY() + radius) {
			pos1 = pos1.above();
		}
		while (!level.getBlockState(pos2).is(Blocks.GRASS_BLOCK) && pos2.getY() < center.getY() + radius) {
			pos2 = pos2.above();
		}

		// Place the redstone blocks
		level.setBlock(pos1, Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
		level.setBlock(pos2, Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);

		// Place the Biodome Life Support Units
		level.setBlock(pos1.above(), BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get().defaultBlockState(), 3);
		level.setBlock(pos2.above(), BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get().defaultBlockState(), 3);

		int numPlants = GeneralUtilities.getRandomNumber(4, 7);

		// Plant Moonglow plants
		int retries = 0;
		for (int i = 0; i < numPlants; i++) {
			int x = GeneralUtilities.getRandomNumber(-radius + 1, radius);
			int z = GeneralUtilities.getRandomNumber(-radius + 1, radius);
			BlockPos pos = center.offset(x, 0, z);

			// Move upwards until we find a grass block or reach past the radius
			while (!level.getBlockState(pos).is(Blocks.GRASS_BLOCK) && pos.getY() < center.getY() + radius) {
				pos = pos.above();
			}

			if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK) && level.getBlockState(pos.above()).isAir()) {
				level.setBlock(pos.above(), BlockRegistry.MOONGLOW.get().defaultBlockState(), 3);
				retries = 0;
			} else {
				i--;
				retries++;

				if (retries > 10) {
					return;
				}
			}
		}
	}
}