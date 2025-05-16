package tech.anonymoushacker1279.immersiveweapons.init;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.TooltipHandler;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class PostSetupHandler {

	private static final ResourceKey<Level> TILTROS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tiltros"));

	/**
	 * Initialize attributes which must be applied after setup.
	 */
	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing post-setup handler");

		// Add custom plants to the flower pot block
		FlowerPotBlock emptyPot = ((FlowerPotBlock) Blocks.FLOWER_POT);
		emptyPot.addPlant(BuiltInRegistries.BLOCK.getKey(BlockRegistry.MOONGLOW.get()), BlockRegistry.POTTED_MOONGLOW);
		emptyPot.addPlant(BuiltInRegistries.BLOCK.getKey(BlockRegistry.DEATHWEED.get()), BlockRegistry.POTTED_DEATHWEED);

		// Compile simple tooltips
		TooltipHandler.compileTooltips();

		// Initialize custom portals
		new CustomPortalBuilder()
				.frame(BlockRegistry.TILTROS_PORTAL_FRAME.get())
				.customPortalBlock(BlockRegistry.TILTROS_PORTAL.get())
				.lightWithItem(ItemRegistry.AZUL_KEYSTONE.get())
				.destination(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tiltros"))
				.flatPortal()
				.preTeleportEvent(entity -> IWConfigs.SERVER.tiltrosEnabled.getAsBoolean())
				.postTeleportEvent(entity -> {
					ImmersiveWeapons.LOGGER.debug("Teleporting entity to Tiltros");
					if (!entity.level().dimension().equals(TILTROS)) {
						return;
					}

					if (entity instanceof LivingEntity livingEntity) {
						livingEntity.addEffect(new MobEffectInstance(EffectRegistry.CELESTIAL_PROTECTION_EFFECT, 100, 0, false, true));
					}

					generateBiodome(entity.level(), entity.blockPosition(), 7);
				})
				.ambientSound(SoundEventRegistry.TILTROS_PORTAL_WHOOSH.getId(), (level) -> 0.5F, (level) -> level.random.nextFloat() * 0.4F + 0.8F)
				.travelSound(SoundEventRegistry.TILTROS_PORTAL_TRAVEL.getId(), (entity) -> 0.5F, (entity) -> entity.getRandom().nextFloat() * 0.4F + 0.8F)
				.triggerSound(SoundEventRegistry.TILTROS_PORTAL_TRAVEL.getId(), (entity) -> 0.5F, (entity) -> entity.getRandom().nextFloat() * 0.4F + 0.8F)
				.portalParticle((level, pos) -> ParticleTypesRegistry.TILTROS_PORTAL_PARTICLE.get())
				.build();
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
							if (level.getBlockState(pos.above()).is(BlockTags.REPLACEABLE)) {
								level.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
							} else {
								level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
							}
						} else if (level.getBlockState(pos).is(BlockRegistry.BLOOD_SANDSTONE.get())) {
							level.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
						}
					}
					if (distance >= radius - 1 && distance <= radius) {
						if (level.getBlockState(pos).is(BlockTags.REPLACEABLE)) {
							level.setBlock(pos, Blocks.GLASS.defaultBlockState(), 3);
						}
					}
				}
			}
		}

		// Determine the positions for the Biodome Life Support Units
		BlockPos pos1 = center.offset(-radius + 4, -radius, -radius + 4);
		BlockPos pos2 = center.offset(radius - 4, -radius, radius - 4);

		// Move upwards until we find a grass block or reach past the radius
		while (!(level.getBlockState(pos1).is(Blocks.GRASS_BLOCK) || level.getBlockState(pos1).is(BlockTags.REPLACEABLE)) && pos1.getY() < center.getY() + radius) {
			pos1 = pos1.above();
		}
		while (!(level.getBlockState(pos2).is(Blocks.GRASS_BLOCK) || level.getBlockState(pos2).is(BlockTags.REPLACEABLE)) && pos2.getY() < center.getY() + radius) {
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

			if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK) && level.getBlockState(pos.above()).is(BlockTags.REPLACEABLE)) {
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