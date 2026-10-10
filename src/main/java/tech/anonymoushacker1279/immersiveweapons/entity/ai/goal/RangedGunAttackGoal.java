package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.RangedSoldierEntity;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.util.EnumSet;

/// Keeps a soldier at range from its target and fires a loaded firearm at it. Reloading is handled separately by
/// `ReloadFirearmGoal`, so the soldier keeps moving and aiming while it reloads.
public class RangedGunAttackGoal<T extends RangedSoldierEntity> extends Goal {

	private final T mob;
	private final double speedModifier;
	private final float attackRadiusSqr;
	private int attackIntervalMin;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;

	/// @param attackInterval ticks to wait after a shot before the next, on top of the reload
	public RangedGunAttackGoal(T mob, double speedModifier, int attackInterval, float attackRadius) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.attackIntervalMin = attackInterval;
		this.attackRadiusSqr = attackRadius * attackRadius;

		setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public void setMinAttackInterval(int attackCooldown) {
		this.attackIntervalMin = attackCooldown;
	}

	@Override
	public boolean canUse() {
		return this.mob.getTarget() != null && this.isHoldingGun() && this.mob.canFireOrReload();
	}

	protected boolean isHoldingGun() {
		return this.mob.isHolding(stack -> stack.getItem() instanceof AbstractGunItem);
	}

	@Override
	public boolean canContinueToUse() {
		return (this.canUse() || !this.mob.getNavigation().isDone()) && this.isHoldingGun();
	}

	/// Execute a one shot task or start executing a continuous task
	@Override
	public void start() {
		super.start();
		mob.setAggressive(true);
	}

	@Override
	public void stop() {
		super.stop();
		mob.setAggressive(false);
		seeTime = 0;
		attackTime = -1;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		LivingEntity target = mob.getTarget();
		if (target != null) {
			double distanceToSqr = mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
			boolean hasLineOfSight = mob.getSensing().hasLineOfSight(target);
			boolean seen = seeTime > 0;

			if (hasLineOfSight != seen) {
				seeTime = 0;
			}

			if (hasLineOfSight) {
				++seeTime;
			} else {
				--seeTime;
			}

			if (!(distanceToSqr > (double) attackRadiusSqr) && seeTime >= 20) {
				mob.getNavigation().stop();
				++strafingTime;
			} else {
				mob.getNavigation().moveTo(target, speedModifier);
				strafingTime = -1;
			}

			if (strafingTime >= 20) {
				if ((double) mob.getRandom().nextFloat() < 0.3D) {
					strafingClockwise = !strafingClockwise;
				}

				if ((double) mob.getRandom().nextFloat() < 0.3D) {
					strafingBackwards = !strafingBackwards;
				}

				strafingTime = 0;
			}

			if (strafingTime > -1) {
				if (distanceToSqr > (double) (attackRadiusSqr * 0.75F)) {
					strafingBackwards = false;
				} else if (distanceToSqr < (double) (attackRadiusSqr * 0.25F)) {
					strafingBackwards = true;
				}

				mob.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
				mob.lookAt(target, 30.0F, 30.0F);
			} else {
				mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
			}

			if (attackTime > 0) {
				--attackTime;
			}

			// Fire as soon as the firearm is loaded and the target is in sight
			if (attackTime <= 0 && hasLineOfSight && seeTime >= 5 && mob.isFirearmLoaded()) {
				mob.performRangedAttack(target, 1);
				attackTime = attackIntervalMin;
			}
		}
	}
}
