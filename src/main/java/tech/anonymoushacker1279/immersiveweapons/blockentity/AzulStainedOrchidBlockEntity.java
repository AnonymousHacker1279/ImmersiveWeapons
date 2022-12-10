package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.world.TiltrosTeleporter;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.BiomesAndDimensions;

public class AzulStainedOrchidBlockEntity extends BlockEntity implements EntityBlock {

	int teleportDelay = 100;
	@Nullable
	BlockPos targetPos;

	public AzulStainedOrchidBlockEntity(BlockPos pos, BlockState state) {
		super(DeferredRegistryHandler.AZUL_STAINED_ORCHID_BLOCK_ENTITY.get(), pos, state);
	}

	public void entityInside(BlockPos pos, Entity entity) {
		entity.playSound(SoundEvents.ENDERMAN_AMBIENT, 0.05f, 0.075f);
		if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions() && !entity.isOnPortalCooldown() && teleportDelay <= 0) {
			teleportDelay = 100;

			if (!entity.level.isClientSide && !pos.equals(entity.portalEntrancePos)) {
				entity.portalEntrancePos = pos.immutable();
			}

			Level entityWorld = entity.level;
			MinecraftServer server = entityWorld.getServer();
			ResourceKey<Level> destination = entityWorld.dimension() == BiomesAndDimensions.TILTROS ? Level.OVERWORLD
					: BiomesAndDimensions.TILTROS;

			if (server != null) {
				ServerLevel destinationLevel = server.getLevel(destination);
				if (destinationLevel != null && !entity.isPassenger()) {
					entityWorld.getProfiler().push("tiltros_portal");
					entity.setPortalCooldown();

					// Get a valid target position if it is unset
					if (targetPos == null) {
						targetPos = findValidTeleportPosition(destinationLevel, pos);
					}

					entity.changeDimension(destinationLevel, new TiltrosTeleporter(targetPos, pos));

					entityWorld.getProfiler().pop();
				}
			}
		} else if (teleportDelay > 0) {
			teleportDelay--;
		}
	}

	private BlockPos findValidTeleportPosition(ServerLevel destinationLevel, BlockPos targetPos) {
		// A valid teleport location must meet the following criteria:
		// 1. The block at and above the current position is air
		// 2. It must be on the surface of the world
		// 3. It must not be in the Deadman's Desert biome

		// Check if the target position is in a valid biome
		if (destinationLevel.getBiome(targetPos).is(BiomesAndDimensions.DEADMANS_DESERT)) {
			// If it is, find the nearest valid chunk
			int x = targetPos.getX();
			int y = targetPos.getY();
			int z = targetPos.getZ();
			int radius = 1;
			boolean found = false;
			while (!found) {
				for (int i = -radius; i <= radius; i++) {
					for (int j = -radius; j <= radius; j++) {
						if (i == -radius || i == radius || j == -radius || j == radius) {
							BlockPos pos = new BlockPos(x + i, y, z + j);
							if (!destinationLevel.getBiome(pos).is(BiomesAndDimensions.DEADMANS_DESERT)) {
								// Move the target position at least 10 blocks away from the edge of the chunk
								targetPos = new BlockPos(pos.getX() + 10, pos.getY(), pos.getZ() + 10);
								found = true;
								break;
							}
						}
					}
				}
				radius++;
			}
		}

		// Load the target chunk
		destinationLevel.getChunk(targetPos);

		// Ensure the target position is on the surface of the world
		int topOfWorld = destinationLevel.getHeight(Heightmap.Types.WORLD_SURFACE, targetPos.getX(), targetPos.getZ());
		targetPos.atY(topOfWorld);

		// Ensure the target position is not in a block (i.e. air, as sometimes it can be water and considered the surface)
		while (!destinationLevel.getBlockState(targetPos).isAir()) {
			targetPos = targetPos.above();
		}

		return targetPos;
	}

	public void setTargetPos(BlockPos targetPos) {
		this.targetPos = targetPos;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AzulStainedOrchidBlockEntity(pos, state);
	}

	/**
	 * Save NBT data.
	 *
	 * @param pTag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putInt("teleportDelay", teleportDelay);
		if (targetPos != null) {
			pTag.putInt("targetX", targetPos.getX());
			pTag.putInt("targetY", targetPos.getY());
			pTag.putInt("targetZ", targetPos.getZ());
		}
	}

	/**
	 * Load NBT data.
	 *
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		teleportDelay = nbt.getInt("teleportDelay");
		if (nbt.contains("targetX") && nbt.contains("targetY") && nbt.contains("targetZ")) {
			targetPos = new BlockPos(nbt.getInt("targetX"), nbt.getInt("targetY"), nbt.getInt("targetZ"));
		}
	}
}