package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueTorso;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.world.TiltrosTeleporter;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.BiomesAndDimensions;

import java.util.stream.Stream;

public class AzulStainedOrchidBlock extends FlowerBlock {

	int teleportDelay = 80;

	public AzulStainedOrchidBlock(MobEffect mobEffect, int effectDuration, Properties properties) {
		super(mobEffect, effectDuration, properties);
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (CommonConfig.TILTROS_ENABLED.get()) {
			entity.playSound(SoundEvents.ENDERMAN_AMBIENT, 0.05f, 0.075f);
			if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions() && teleportDelay <= 0) {
				teleportDelay = 80;
				if (!entity.level.isClientSide && !pos.equals(entity.portalEntrancePos)) {
					entity.portalEntrancePos = pos.immutable();
				}
				Level entityWorld = entity.level;
				MinecraftServer server = entityWorld.getServer();
				ResourceKey<Level> destination = entityWorld.dimension() == BiomesAndDimensions.TILTROS ? Level.OVERWORLD
						: BiomesAndDimensions.TILTROS;

				if (server != null) {
					ServerLevel destinationWorld = server.getLevel(destination);
					if (destinationWorld != null && !entity.isPassenger()) {
						entityWorld.getProfiler().push("tiltros_portal");
						entity.setPortalCooldown();
						entity.changeDimension(destinationWorld, new TiltrosTeleporter());
						BlockPos entityPos = entity.blockPosition();
						while (destinationWorld.getBlockState(entityPos.above()) != Blocks.AIR.defaultBlockState()
								&& destinationWorld.getBlockState(entityPos) != Blocks.AIR.defaultBlockState()) {
							entityPos = entityPos.above(2);
						}
						entity.teleportTo(entityPos.getX() - 0.5f, entityPos.getY() + 0.5f, entityPos.getZ() + 0.5f);

						// Build a spawn area with another portal, if one doesn't already exist
						Stream<BlockState> destinationBlockStates = destinationWorld.getBlockStates(
								new AABB(entityPos.getX() - 10,
										entityPos.getY() - 5,
										entityPos.getZ() - 10,
										entityPos.getX() + 10,
										entityPos.getY() + 5,
										entityPos.getZ() + 10));

						if (destinationBlockStates.noneMatch(blockState ->
								blockState == DeferredRegistryHandler.AZUL_STAINED_ORCHID.get().defaultBlockState())) {

							destinationWorld.destroyBlock(entityPos.above(), true);
							destinationWorld.destroyBlock(entityPos.below(), true);
							destinationWorld.setBlock(entityPos.below(),
									Blocks.GRASS_BLOCK.defaultBlockState(), 3);
							destinationWorld.destroyBlock(entityPos, true);
							destinationWorld.setBlock(entityPos,
									DeferredRegistryHandler.AZUL_STAINED_ORCHID.get().defaultBlockState(), 3);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection()), true);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection()).above(), true);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection()).below(), true);
							destinationWorld.setBlock(entityPos.relative(entity.getDirection()).below(),
									Blocks.STONE_BRICKS.defaultBlockState(), 3);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getClockWise()), true);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getClockWise()).above(), true);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getClockWise()).below(), true);
							destinationWorld.setBlock(entityPos.relative(entity.getDirection().getClockWise()).below(),
									Blocks.STONE_BRICKS.defaultBlockState(), 3);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getCounterClockWise()), true);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getCounterClockWise()).above(), true);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getCounterClockWise()).below(), true);
							destinationWorld.setBlock(entityPos.relative(entity.getDirection().getCounterClockWise()).below(),
									Blocks.STONE_BRICKS.defaultBlockState(), 3);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getOpposite()), true);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getOpposite()).above(), true);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection().getOpposite()).below(), true);
							destinationWorld.setBlock(entityPos.relative(entity.getDirection().getOpposite()).below(),
									Blocks.STONE_BRICKS.defaultBlockState(), 3);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection()), true);
							destinationWorld.setBlock(entityPos.relative(entity.getDirection()),
									DeferredRegistryHandler.WARRIOR_STATUE_BASE.get().defaultBlockState()
											.setValue(WarriorStatueTorso.FACING,
													entity.getDirection().getOpposite()), 3);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection()).above(), true);
							destinationWorld.setBlock(entityPos.relative(entity.getDirection()).above(),
									DeferredRegistryHandler.WARRIOR_STATUE_TORSO.get().defaultBlockState()
											.setValue(WarriorStatueTorso.FACING,
													entity.getDirection().getOpposite())
											.setValue(WarriorStatueTorso.POWERED, true), 3);
							destinationWorld.destroyBlock(entityPos.relative(entity.getDirection()).above(2), true);
							destinationWorld.setBlock(entityPos.relative(entity.getDirection()).above(2),
									DeferredRegistryHandler.WARRIOR_STATUE_HEAD.get().defaultBlockState()
											.setValue(WarriorStatueTorso.FACING,
													entity.getDirection().getOpposite())
											.setValue(WarriorStatueTorso.POWERED, true), 3);
						}
						entityWorld.getProfiler().pop();
					}
				}
			} else if (teleportDelay > 0) {
				teleportDelay--;
			}
		}
	}
}