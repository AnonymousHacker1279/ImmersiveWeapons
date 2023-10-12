package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.util.EnumSet;

public class RangedGunAttackGoal<T extends Mob & RangedAttackMob> extends Goal {

	private final T mob;
	private final double speedModifier;
	private int attackIntervalMin;
	private final float attackRadiusSqr;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;

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
		return this.mob.getTarget() != null && this.isHoldingGun();
	}

	protected boolean isHoldingGun() {
		return this.mob.isHolding(stack -> stack.getItem() instanceof AbstractGunItem);
	}

	@Override
	public boolean canContinueToUse() {
		return (this.canUse() || !this.mob.getNavigation().isDone()) && this.isHoldingGun();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
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
		mob.stopUsingItem();
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

			if (mob.isUsingItem()) {
				if (!hasLineOfSight && seeTime < -60) {
					mob.stopUsingItem();
				} else if (hasLineOfSight) {
					int i = mob.getTicksUsingItem();
					if (i >= 20) {
						mob.stopUsingItem();
						mob.performRangedAttack(target, 1);
						attackTime = attackIntervalMin;
					}
				}
			} else if (--attackTime <= 0 && seeTime >= -60) {
				mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(mob, AbstractGunItem.class::isInstance));
			}
		}
	}
}