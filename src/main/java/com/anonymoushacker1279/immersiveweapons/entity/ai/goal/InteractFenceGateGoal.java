package com.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public abstract class InteractFenceGateGoal extends Goal {

	protected Mob entity;
	protected BlockPos gatePosition = BlockPos.ZERO;
	protected boolean gateInteract = true;
	private boolean hasStoppedGateInteraction;
	private float entityPositionX;
	private float entityPositionZ;

	/**
	 * Constructor for InteractFenceGateGoal.
	 * @param entityIn the <code>MobEntity</code> instance
	 */
	public InteractFenceGateGoal(Mob entityIn) {
		entity = entityIn;
		if (!GoalUtils.hasGroundPathNavigation(entityIn)) {
			throw new IllegalArgumentException("Unsupported mob type for InteractFenceGateGoal");
		}
	}

	/**
	 * Toggle the state of blocks.
	 * @param open set the state of "open"
	 */
	protected void toggleGate(boolean open) {
		if (gateInteract) {
			gatePosition = entity.blockPosition().relative(entity.getDirection());
			BlockState blockstate = entity.level.getBlockState(gatePosition);
			if (blockstate.getBlock() instanceof FenceGateBlock) {
				if (open) {
					blockstate = blockstate.setValue(FenceGateBlock.OPEN, Boolean.TRUE);
					entity.level.setBlock(gatePosition, blockstate, 3);
				}
				if (!open) {
					blockstate = blockstate.setValue(FenceGateBlock.OPEN, Boolean.FALSE);
					entity.level.setBlock(gatePosition, blockstate, 3);
				}
			}
		}

	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 * @return boolean
	 */
	@Override
	public boolean canUse() {
		if (!GoalUtils.hasGroundPathNavigation(entity)) {
			return false;
		} else if (!entity.horizontalCollision) {
			return false;
		} else {
			GroundPathNavigation groundpathnavigator = (GroundPathNavigation) entity.getNavigation();
			Path path = groundpathnavigator.getPath();
			if (path != null && !path.isDone() && groundpathnavigator.canOpenDoors()) {
				for (int i = 0; i < Math.min(path.getNextNodeIndex() + 2, path.getNodeCount()); ++i) {
					Node pathpoint = path.getNode(i);
					gatePosition = new BlockPos(pathpoint.x, pathpoint.y + 1, pathpoint.z);
					if (!(entity.distanceToSqr(gatePosition.getX(), entity.getY(), gatePosition.getZ()) > 2.25D)) {
						return true;
					}
				}

				gatePosition = entity.blockPosition().above();
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 * @return boolean
	 */
	@Override
	public boolean canContinueToUse() {
		return !hasStoppedGateInteraction;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		hasStoppedGateInteraction = false;
		entityPositionX = (float) ((double) gatePosition.getX() + 0.5D - entity.getX());
		entityPositionZ = (float) ((double) gatePosition.getZ() + 0.5D - entity.getZ());
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		float f = (float) ((double) gatePosition.getX() + 0.5D - entity.getX());
		float f1 = (float) ((double) gatePosition.getZ() + 0.5D - entity.getZ());
		float f2 = entityPositionX * f + entityPositionZ * f1;
		if (f2 < 0.0F) {
			hasStoppedGateInteraction = true;
		}
	}
}