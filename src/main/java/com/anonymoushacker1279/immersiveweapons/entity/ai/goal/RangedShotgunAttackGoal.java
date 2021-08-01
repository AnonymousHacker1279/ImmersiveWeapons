package com.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.gun.SimpleShotgunItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;

import java.util.EnumSet;
import java.util.function.Predicate;

public class RangedShotgunAttackGoal<T extends PathfinderMob & RangedAttackMob> extends Goal {

	private final T entity;
	private final double moveSpeedAmp;
	private final float maxAttackDistance;
	private int attackCooldown;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;

	/**
	 * Constructor for RangedShotgunAttackGoal.
	 * @param mob the <code>MonsterEntity</code> that will be using the goal,
	 *            must implement IRangedAttackMob
	 * @param moveSpeedAmpIn the movement speed amplifier
	 * @param attackCooldownIn the attack cooldown
	 * @param maxAttackDistanceIn the max attack distance
	 */
	public RangedShotgunAttackGoal(T mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn) {
		entity = mob;
		moveSpeedAmp = moveSpeedAmpIn;
		attackCooldown = attackCooldownIn;
		maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
		setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	/**
	 * Set the max attack cooldown.
	 * @param attackCooldownIn the max attack cooldown
	 */
	public void setAttackCooldown(int attackCooldownIn) {
		attackCooldown = attackCooldownIn;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 * @return boolean
	 */
	@Override
	public boolean canUse() {
		return entity.getTarget() != null && isGunInMainHand();
	}

	/**
	 * Check if an instance of SimplePistolItem is held
	 * by the entity
	 * @return boolean
	 */
	private boolean isGunInMainHand() {
		return entity.isHolding(SimpleShotgunItem.class::isInstance);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 * @return boolean
	 */
	@Override
	public boolean canContinueToUse() {
		return (canUse() || !entity.getNavigation().isDone()) && isGunInMainHand();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		super.start();
		entity.setAggressive(true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		super.stop();
		entity.setAggressive(false);
		seeTime = 0;
		attackTime = -1;
		entity.stopUsingItem();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		LivingEntity livingentity = entity.getTarget();
		if (livingentity != null) {
			double d0 = entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			boolean flag = entity.getSensing().hasLineOfSight(livingentity);
			boolean flag1 = seeTime > 0;
			if (flag != flag1) {
				seeTime = 0;
			}

			if (flag) {
				++seeTime;
			} else {
				--seeTime;
			}

			if (!(d0 > (double) maxAttackDistance) && seeTime >= 20) {
				entity.getNavigation().stop();
				++strafingTime;
			} else {
				entity.getNavigation().moveTo(livingentity, moveSpeedAmp);
				strafingTime = -1;
			}

			if (strafingTime >= 20) {
				if ((double) entity.getRandom().nextFloat() < 0.3D) {
					strafingClockwise = !strafingClockwise;
				}

				if ((double) entity.getRandom().nextFloat() < 0.3D) {
					strafingBackwards = !strafingBackwards;
				}

				strafingTime = 0;
			}

			if (strafingTime > -1) {
				if (d0 > (double) (maxAttackDistance * 0.75F)) {
					strafingBackwards = false;
				} else if (d0 < (double) (maxAttackDistance * 0.25F)) {
					strafingBackwards = true;
				}

				entity.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
				entity.lookAt(livingentity, 30.0F, 30.0F);
			} else {
				entity.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			}

			if (entity.isUsingItem()) {
				if (!flag && seeTime < -60) {
					entity.stopUsingItem();
				} else if (flag) {
					int i = entity.getTicksUsingItem();
					if (i >= 20) {
						entity.stopUsingItem();
						entity.performRangedAttack(livingentity, 1);
						attackTime = attackCooldown;
					}
				}
			} else if (--attackTime <= 0 && seeTime >= -60) {
				entity.startUsingItem(ProjectileUtil.getWeaponHoldingHand(entity, Predicate.isEqual(DeferredRegistryHandler.BLUNDERBUSS.get())));
			}
		}
	}
}