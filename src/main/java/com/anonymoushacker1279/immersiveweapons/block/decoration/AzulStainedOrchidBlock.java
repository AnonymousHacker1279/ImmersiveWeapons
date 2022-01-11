package com.anonymoushacker1279.immersiveweapons.block.decoration;

import com.anonymoushacker1279.immersiveweapons.block.misc.portal.statue.warrior.WarriorStatueTorso;
import com.anonymoushacker1279.immersiveweapons.config.ServerConfig;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.world.TiltrosTeleporter;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class AzulStainedOrchidBlock extends FlowerBlock {

	int teleportDelay = 80;

	public AzulStainedOrchidBlock(MobEffect mobEffect, int effectDuration, Properties properties) {
		super(mobEffect, effectDuration, properties);
	}

	@Override
	public BlockBehaviour.@NotNull OffsetType getOffsetType() {
		return OffsetType.NONE;
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (ServerConfig.TILTROS_ENABLED.get()) {
			entity.playSound(SoundEvents.ENDERMAN_AMBIENT, 0.05f, 0.075f);
			if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions() && teleportDelay <= 0) {
				teleportDelay = 80;
				if (!entity.level.isClientSide && !pos.equals(entity.portalEntrancePos)) {
					entity.portalEntrancePos = pos.immutable();
				}
				Level entityWorld = entity.level;
				MinecraftServer server = entityWorld.getServer();
				ResourceKey<Level> destination = entityWorld.dimension() == GeneralUtilities.TILTROS ? Level.OVERWORLD : GeneralUtilities.TILTROS;
				if (server != null) {
					ServerLevel destinationWorld = server.getLevel(destination);
					if (destinationWorld != null && !entity.isPassenger()) {
						entityWorld.getProfiler().push("tiltros_portal");
						entity.setPortalCooldown();
						entity.changeDimension(destinationWorld, new TiltrosTeleporter());
						BlockPos oldEntityPos = entity.blockPosition();
						entity.teleportTo(oldEntityPos.getX() - 0.5f, oldEntityPos.getY() + 0.5f, oldEntityPos.getZ() + 0.5f);
						// Build a spawn area with another portal, if one doesn't already exist
						Stream<BlockState> destinationBlockStates = destinationWorld.getBlockStates(new AABB(oldEntityPos.getX() - 10, oldEntityPos.getY() - 5, oldEntityPos.getZ() - 10, oldEntityPos.getX() + 10, oldEntityPos.getY() + 5, oldEntityPos.getZ() + 10));
						if (destinationBlockStates.noneMatch(blockState -> blockState == DeferredRegistryHandler.AZUL_STAINED_ORCHID.get().defaultBlockState())) {
							destinationWorld.destroyBlock(oldEntityPos.above(), true);
							destinationWorld.destroyBlock(oldEntityPos.below(), true);
							destinationWorld.setBlock(oldEntityPos.below(), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
							destinationWorld.destroyBlock(oldEntityPos, true);
							destinationWorld.setBlock(oldEntityPos, DeferredRegistryHandler.AZUL_STAINED_ORCHID.get().defaultBlockState(), 3);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection()), true);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection()).above(), true);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection()).below(), true);
							destinationWorld.setBlock(oldEntityPos.relative(entity.getDirection()).below(), Blocks.STONE_BRICKS.defaultBlockState(), 3);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getClockWise()), true);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getClockWise()).above(), true);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getClockWise()).below(), true);
							destinationWorld.setBlock(oldEntityPos.relative(entity.getDirection().getClockWise()).below(), Blocks.STONE_BRICKS.defaultBlockState(), 3);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getCounterClockWise()), true);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getCounterClockWise()).above(), true);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getCounterClockWise()).below(), true);
							destinationWorld.setBlock(oldEntityPos.relative(entity.getDirection().getCounterClockWise()).below(), Blocks.STONE_BRICKS.defaultBlockState(), 3);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getOpposite()), true);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getOpposite()).above(), true);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection().getOpposite()).below(), true);
							destinationWorld.setBlock(oldEntityPos.relative(entity.getDirection().getOpposite()).below(), Blocks.STONE_BRICKS.defaultBlockState(), 3);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection()), true);
							destinationWorld.setBlock(oldEntityPos.relative(entity.getDirection()), DeferredRegistryHandler.WARRIOR_STATUE_BASE.get().defaultBlockState().setValue(WarriorStatueTorso.FACING, entity.getDirection().getOpposite()), 3);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection()).above(), true);
							destinationWorld.setBlock(oldEntityPos.relative(entity.getDirection()).above(), DeferredRegistryHandler.WARRIOR_STATUE_TORSO.get().defaultBlockState().setValue(WarriorStatueTorso.FACING, entity.getDirection().getOpposite()).setValue(WarriorStatueTorso.POWERED, true), 3);
							destinationWorld.destroyBlock(oldEntityPos.relative(entity.getDirection()).above(2), true);
							destinationWorld.setBlock(oldEntityPos.relative(entity.getDirection()).above(2), DeferredRegistryHandler.WARRIOR_STATUE_HEAD.get().defaultBlockState().setValue(WarriorStatueTorso.FACING, entity.getDirection().getOpposite()).setValue(WarriorStatueTorso.POWERED, true), 3);
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