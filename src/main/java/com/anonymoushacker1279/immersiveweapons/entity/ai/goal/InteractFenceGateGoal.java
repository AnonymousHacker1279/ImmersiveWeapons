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

	public InteractFenceGateGoal(MobEntity entityIn) {
		this.entity = entityIn;
		if (!GroundPathHelper.hasGroundPathNavigation(entityIn)) {
			throw new IllegalArgumentException("Unsupported mob type for InteractFenceGateGoal");
		}
	}

	protected void toggleGate(boolean open) {
		if (this.gateInteract) {
			this.gatePosition = this.entity.blockPosition().relative(this.entity.getDirection());
			BlockState blockstate = this.entity.level.getBlockState(this.gatePosition);
			if (blockstate.getBlock() instanceof FenceGateBlock) {
				if (open) {
					blockstate = blockstate.setValue(FenceGateBlock.OPEN, Boolean.TRUE);
					this.entity.level.setBlock(this.gatePosition, blockstate, 3);
				}
				if (!open) {
					blockstate = blockstate.setValue(FenceGateBlock.OPEN, Boolean.FALSE);
					this.entity.level.setBlock(this.gatePosition, blockstate, 3);
				}
			}
		}

	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		if (!GroundPathHelper.hasGroundPathNavigation(this.entity)) {
			return false;
		} else if (!this.entity.horizontalCollision) {
			return false;
		} else {
			GroundPathNavigator groundpathnavigator = (GroundPathNavigator) this.entity.getNavigation();
			Path path = groundpathnavigator.getPath();
			if (path != null && !path.isDone() && groundpathnavigator.canOpenDoors()) {
				for (int i = 0; i < Math.min(path.getNextNodeIndex() + 2, path.getNodeCount()); ++i) {
					PathPoint pathpoint = path.getNode(i);
					this.gatePosition = new BlockPos(pathpoint.x, pathpoint.y + 1, pathpoint.z);
					if (!(this.entity.distanceToSqr(this.gatePosition.getX(), this.entity.getY(), this.gatePosition.getZ()) > 2.25D)) {
						return true;
					}
				}

				this.gatePosition = this.entity.blockPosition().above();
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return !this.hasStoppedGateInteraction;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.hasStoppedGateInteraction = false;
		this.entityPositionX = (float) ((double) this.gatePosition.getX() + 0.5D - this.entity.getX());
		this.entityPositionZ = (float) ((double) this.gatePosition.getZ() + 0.5D - this.entity.getZ());
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		float f = (float) ((double) this.gatePosition.getX() + 0.5D - this.entity.getX());
		float f1 = (float) ((double) this.gatePosition.getZ() + 0.5D - this.entity.getZ());
		float f2 = this.entityPositionX * f + this.entityPositionZ * f1;
		if (f2 < 0.0F) {
			this.hasStoppedGateInteraction = true;
		}

	}
}