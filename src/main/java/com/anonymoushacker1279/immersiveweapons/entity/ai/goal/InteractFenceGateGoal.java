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
		if (!GroundPathHelper.isGroundNavigator(entityIn)) {
			throw new IllegalArgumentException("Unsupported mob type for InteractFenceGateGoal");
		}
	}

	protected void toggleGate(boolean open) {
		if (this.gateInteract) {
			this.gatePosition = this.entity.getPosition().offset(this.entity.getHorizontalFacing());
			BlockState blockstate = this.entity.world.getBlockState(this.gatePosition);
			if (blockstate.getBlock() instanceof FenceGateBlock) {
				if (open) {
					blockstate = blockstate.with(FenceGateBlock.OPEN, Boolean.TRUE);
					this.entity.world.setBlockState(this.gatePosition, blockstate, 3);
				}
				if (!open) {
					blockstate = blockstate.with(FenceGateBlock.OPEN, Boolean.FALSE);
					this.entity.world.setBlockState(this.gatePosition, blockstate, 3);
				}
			}
		}

	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean shouldExecute() {
		if (!GroundPathHelper.isGroundNavigator(this.entity)) {
			return false;
		} else if (!this.entity.collidedHorizontally) {
			return false;
		} else {
			GroundPathNavigator groundpathnavigator = (GroundPathNavigator) this.entity.getNavigator();
			Path path = groundpathnavigator.getPath();
			if (path != null && !path.isFinished() && groundpathnavigator.getEnterDoors()) {
				for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i) {
					PathPoint pathpoint = path.getPathPointFromIndex(i);
					this.gatePosition = new BlockPos(pathpoint.x, pathpoint.y + 1, pathpoint.z);
					if (!(this.entity.getDistanceSq(this.gatePosition.getX(), this.entity.getPosY(), this.gatePosition.getZ()) > 2.25D)) {
						return true;
					}
				}

				this.gatePosition = this.entity.getPosition().up();
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
	public boolean shouldContinueExecuting() {
		return !this.hasStoppedGateInteraction;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.hasStoppedGateInteraction = false;
		this.entityPositionX = (float) ((double) this.gatePosition.getX() + 0.5D - this.entity.getPosX());
		this.entityPositionZ = (float) ((double) this.gatePosition.getZ() + 0.5D - this.entity.getPosZ());
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		float f = (float) ((double) this.gatePosition.getX() + 0.5D - this.entity.getPosX());
		float f1 = (float) ((double) this.gatePosition.getZ() + 0.5D - this.entity.getPosZ());
		float f2 = this.entityPositionX * f + this.entityPositionZ * f1;
		if (f2 < 0.0F) {
			this.hasStoppedGateInteraction = true;
		}

	}
}