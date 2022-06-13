package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class HoverGoal extends Goal {

	private final Mob mob;
	private int flyUpTimer = 40;
	private int floatDownTimer = 40;

	public HoverGoal(Mob pMob) {
		this.mob = pMob;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return true;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (flyUpTimer > 0 && floatDownTimer == 40) {
			this.mob.setDeltaMovement(0, 0.03f, 0);
			flyUpTimer--;
		} else {
			if (floatDownTimer <= 40 && floatDownTimer > 0) {
				this.mob.setDeltaMovement(0, -0.03f, 0);
				floatDownTimer--;
			} else {
				floatDownTimer = 40;
				flyUpTimer = 40;
			}
		}
	}
}