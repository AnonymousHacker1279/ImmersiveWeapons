package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.RangedSoldierEntity;

import java.util.EnumSet;

/// A soldier that can't shoot any more (unloaded, and nothing to reload with) runs away from its target.
public class FleeWithoutAmmunitionGoal extends Goal {

	private final RangedSoldierEntity mob;
	private final double speedModifier;
	private final double fleeDistanceSqr;

	public FleeWithoutAmmunitionGoal(RangedSoldierEntity mob, double speedModifier, float fleeDistance) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.fleeDistanceSqr = fleeDistance * fleeDistance;
		setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		LivingEntity target = mob.getTarget();
		if (target == null || mob.getFirearm().isEmpty() || mob.canFireOrReload()) {
			return false;
		}

		return mob.distanceToSqr(target) < fleeDistanceSqr && findFleePosition(target) != null;
	}

	private Vec3 findFleePosition(LivingEntity target) {
		return DefaultRandomPos.getPosAway(mob, 16, 7, target.position());
	}

	@Override
	public boolean canContinueToUse() {
		return !mob.getNavigation().isDone() && !mob.canFireOrReload();
	}

	@Override
	public void start() {
		LivingEntity target = mob.getTarget();
		Vec3 position = target != null ? findFleePosition(target) : null;

		if (position != null) {
			mob.getNavigation().moveTo(position.x, position.y, position.z, speedModifier);
		}
	}

	@Override
	public void stop() {
		mob.getNavigation().stop();
	}
}
