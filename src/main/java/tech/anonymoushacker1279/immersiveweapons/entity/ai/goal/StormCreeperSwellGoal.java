package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StormCreeperEntity;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class StormCreeperSwellGoal extends Goal {

	private final StormCreeperEntity creeper;
	@Nullable
	private LivingEntity target;

	public StormCreeperSwellGoal(StormCreeperEntity pCreeper) {
		creeper = pCreeper;
		setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		LivingEntity creeperTarget = creeper.getTarget();
		return creeper.getSwellState() > 0 || creeperTarget != null && creeper.distanceToSqr(creeperTarget) < 9.0D;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		creeper.getNavigation().stop();
		target = creeper.getTarget();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		target = null;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (target == null) {
			creeper.setSwellState(-1);
		} else if (creeper.distanceToSqr(target) > 49.0D) {
			creeper.setSwellState(-1);
		} else if (!creeper.getSensing().hasLineOfSight(target)) {
			creeper.setSwellState(-1);
		} else {
			creeper.setSwellState(1);
		}
	}
}