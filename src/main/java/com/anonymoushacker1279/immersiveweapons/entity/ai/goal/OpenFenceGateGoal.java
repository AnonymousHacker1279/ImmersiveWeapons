package com.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.entity.MobEntity;

public class OpenFenceGateGoal extends InteractFenceGateGoal {

	private final boolean closeGate;
	private int closeGateTemporisation;

	public OpenFenceGateGoal(MobEntity entitylivingIn, boolean shouldClose) {
		super(entitylivingIn);
		this.entity = entitylivingIn;
		this.closeGate = shouldClose;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return this.closeGate && this.closeGateTemporisation > 0 && super.shouldContinueExecuting();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.closeGateTemporisation = 20;
		this.toggleGate(true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		this.toggleGate(false);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		--this.closeGateTemporisation;
		super.tick();
	}
}