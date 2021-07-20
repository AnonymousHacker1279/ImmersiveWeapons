package com.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.entity.MobEntity;

public class OpenFenceGateGoal extends InteractFenceGateGoal {

	private final boolean closeGate;
	private int closeGateTemporisation;

	/**
	 * Constructor for InteractFenceGateGoal.
	 * @param entitylivingIn the <code>MobEntity</code> instance
	 * @param shouldClose if true, the gate will be closed behind the entity
	 */
	public OpenFenceGateGoal(MobEntity entitylivingIn, boolean shouldClose) {
		super(entitylivingIn);
		entity = entitylivingIn;
		closeGate = shouldClose;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 * @return boolean
	 */
	@Override
	public boolean canContinueToUse() {
		return closeGate && closeGateTemporisation > 0 && super.canContinueToUse();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		closeGateTemporisation = 20;
		toggleGate(true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		toggleGate(false);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		--closeGateTemporisation;
		super.tick();
	}
}