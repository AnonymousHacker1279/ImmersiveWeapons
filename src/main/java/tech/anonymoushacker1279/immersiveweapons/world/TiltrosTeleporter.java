package tech.anonymoushacker1279.immersiveweapons.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.ITeleporter;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueTorso;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AzulStainedOrchidBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.function.Function;
import java.util.stream.Stream;

public class TiltrosTeleporter implements ITeleporter {

	final BlockPos targetPos;
	final BlockPos currentPos;

	public TiltrosTeleporter(BlockPos targetPos, BlockPos currentPos) {
		this.targetPos = targetPos;
		this.currentPos = currentPos;
	}

	@Override
	public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
		repositionEntity.apply(false);

		BlockPos teleportPos = targetPos.relative(entity.getDirection().getOpposite());

		// Teleport the player to the target position
		entity.teleportTo(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());

		// Build a spawn area with another portal, if one doesn't already exist
		Stream<BlockState> destinationBlockStates = destWorld.getBlockStates(
				new AABB(targetPos.getX() - 2,
						targetPos.getY() - 2,
						targetPos.getZ() - 2,
						targetPos.getX() + 2,
						targetPos.getY() + 2,
						targetPos.getZ() + 2));

		if (destinationBlockStates.noneMatch(blockState ->
				blockState == BlockRegistry.AZUL_STAINED_ORCHID.get().defaultBlockState())) {

			destWorld.destroyBlock(targetPos.above(), true);
			destWorld.destroyBlock(targetPos.below(), true);
			destWorld.setBlock(targetPos.below(),
					Blocks.GRASS_BLOCK.defaultBlockState(), 3);
			destWorld.destroyBlock(targetPos, true);

			// An Azul Stained Orchid goes here, set its NBT to include the current position
			destWorld.setBlock(targetPos,
					BlockRegistry.AZUL_STAINED_ORCHID.get().defaultBlockState(), 3);

			if (destWorld.getBlockEntity(targetPos) instanceof AzulStainedOrchidBlockEntity blockEntity) {
				blockEntity.setTargetPos(currentPos);
			}

			destWorld.destroyBlock(targetPos.relative(entity.getDirection()), true);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection()).above(), true);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection()).below(), true);
			destWorld.setBlock(targetPos.relative(entity.getDirection()).below(),
					Blocks.STONE_BRICKS.defaultBlockState(), 3);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getClockWise()), true);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getClockWise()).above(), true);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getClockWise()).below(), true);
			destWorld.setBlock(targetPos.relative(entity.getDirection().getClockWise()).below(),
					Blocks.STONE_BRICKS.defaultBlockState(), 3);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getCounterClockWise()), true);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getCounterClockWise()).above(), true);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getCounterClockWise()).below(), true);
			destWorld.setBlock(targetPos.relative(entity.getDirection().getCounterClockWise()).below(),
					Blocks.STONE_BRICKS.defaultBlockState(), 3);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getOpposite()), true);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getOpposite()).above(), true);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection().getOpposite()).below(), true);
			destWorld.setBlock(targetPos.relative(entity.getDirection().getOpposite()).below(),
					Blocks.STONE_BRICKS.defaultBlockState(), 3);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection()), true);
			destWorld.setBlock(targetPos.relative(entity.getDirection()),
					BlockRegistry.WARRIOR_STATUE_BASE.get().defaultBlockState()
							.setValue(WarriorStatueTorso.FACING,
									entity.getDirection().getOpposite()), 3);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection()).above(), true);
			destWorld.setBlock(targetPos.relative(entity.getDirection()).above(),
					BlockRegistry.WARRIOR_STATUE_TORSO.get().defaultBlockState()
							.setValue(WarriorStatueTorso.FACING,
									entity.getDirection().getOpposite())
							.setValue(WarriorStatueTorso.POWERED, true), 3);
			destWorld.destroyBlock(targetPos.relative(entity.getDirection()).above(2), true);
			destWorld.setBlock(targetPos.relative(entity.getDirection()).above(2),
					BlockRegistry.WARRIOR_STATUE_HEAD.get().defaultBlockState()
							.setValue(WarriorStatueTorso.FACING,
									entity.getDirection().getOpposite())
							.setValue(WarriorStatueTorso.POWERED, true), 3);
		}

		return entity;
	}

	@Override
	public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destinationWorld) {
		return false;
	}
}