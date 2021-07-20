package com.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.util.math.BlockPos;

public abstract class InteractFenceGateGoal extends Goal {

	protected MobEntity entity;
	protected BlockPos gatePosition = BlockPos.ZERO;
	protected boolean gateInteract = true;
	private boolean hasStoppedGateInteraction;
	private float entityPositionX;
	private float entityPositionZ;

	/**
	 * Constructor for InteractFenceGateGoal.
	 * @param entityIn the <code>MobEntity</code> instance
	 */
	public InteractFenceGateGoal(MobEntity entityIn) {
		entity = entityIn;
		if (!GroundPathHelper.hasGroundPathNavigation(entityIn)) {
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
		if (!GroundPathHelper.hasGroundPathNavigation(entity)) {
			return false;
		} else if (!entity.horizontalCollision) {
			return false;
		} else {
			GroundPathNavigator groundpathnavigator = (GroundPathNavigator) entity.getNavigation();
			Path path = groundpathnavigator.getPath();
			if (path != null && !path.isDone() && groundpathnavigator.canOpenDoors()) {
				for (int i = 0; i < Math.min(path.getNextNodeIndex() + 2, path.getNodeCount()); ++i) {
					PathPoint pathpoint = path.getNode(i);
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