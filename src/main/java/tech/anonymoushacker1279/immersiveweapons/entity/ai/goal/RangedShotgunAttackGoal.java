package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.Item;

public class RangedShotgunAttackGoal<T extends PathfinderMob & RangedAttackMob> extends RangedGunAttackGoal<T> {

	public RangedShotgunAttackGoal(T mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn, Item gun) {
		super(mob, moveSpeedAmpIn, attackCooldownIn, maxAttackDistanceIn, gun);
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
}