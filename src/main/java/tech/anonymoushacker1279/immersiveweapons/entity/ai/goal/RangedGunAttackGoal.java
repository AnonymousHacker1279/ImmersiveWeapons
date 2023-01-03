package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;

import java.util.EnumSet;
import java.util.function.Predicate;

public class RangedGunAttackGoal<T extends PathfinderMob & RangedAttackMob> extends Goal {

	protected final T entity;
	protected final double moveSpeedAmp;
	protected final float maxAttackDistance;
	protected int attackCooldown;
	protected int attackTime = -1;
	protected int seeTime;
	protected boolean strafingClockwise;
	protected boolean strafingBackwards;
	protected int strafingTime = -1;
	protected final Item gunItem;

	/**
	 * Constructor for RangedGunAttackGoal.
	 *
	 * @param mob                 the mob that will be using a gun
	 * @param moveSpeedAmpIn      the movement speed amplifier
	 * @param attackCooldownIn    the attack cooldown
	 * @param maxAttackDistanceIn the max attack distance
	 * @param gun                 the gun item
	 */
	public RangedGunAttackGoal(T mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn, Item gun) {
		entity = mob;
		moveSpeedAmp = moveSpeedAmpIn;
		attackCooldown = attackCooldownIn;
		maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
		gunItem = gun;
		setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	/**
	 * Set the max attack cooldown.
	 *
	 * @param attackCooldownIn the max attack cooldown
	 */
	public void setAttackCooldown(int attackCooldownIn) {
		attackCooldown = attackCooldownIn;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 *
	 * @return boolean
	 */
	@Override
	public boolean canUse() {
		return entity.getTarget() != null && isGunInMainHand();
	}

	/**
	 * Check if an instance of SimplePistolItem is held
	 * by the entity
	 *
	 * @return boolean
	 */
	private boolean isGunInMainHand() {
		return entity.isHolding(gunItem);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 *
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
		LivingEntity target = entity.getTarget();
		if (target != null) {
			double distanceToSqr = entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
			boolean hasLineOfSight = entity.getSensing().hasLineOfSight(target);
			boolean seen = seeTime > 0;
			if (hasLineOfSight != seen) {
				seeTime = 0;
			}

			if (hasLineOfSight) {
				++seeTime;
			} else {
				--seeTime;
			}

			if (!(distanceToSqr > (double) maxAttackDistance) && seeTime >= 20) {
				entity.getNavigation().stop();
				++strafingTime;
			} else {
				entity.getNavigation().moveTo(target, moveSpeedAmp);
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
				if (distanceToSqr > (double) (maxAttackDistance * 0.75F)) {
					strafingBackwards = false;
				} else if (distanceToSqr < (double) (maxAttackDistance * 0.25F)) {
					strafingBackwards = true;
				}

				entity.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
				entity.lookAt(target, 30.0F, 30.0F);
			} else {
				entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
			}

			if (entity.isUsingItem()) {
				if (!hasLineOfSight && seeTime < -60) {
					entity.stopUsingItem();
				} else if (hasLineOfSight) {
					int i = entity.getTicksUsingItem();
					if (i >= 20) {
						entity.stopUsingItem();
						entity.performRangedAttack(target, 1);
						attackTime = attackCooldown;
					}
				}
			} else if (--attackTime <= 0 && seeTime >= -60) {
				entity.startUsingItem(ProjectileUtil.getWeaponHoldingHand(entity, Predicate.isEqual(gunItem)));
			}
		}
	}
}