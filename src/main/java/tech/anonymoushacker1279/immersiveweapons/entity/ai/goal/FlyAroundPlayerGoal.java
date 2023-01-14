package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;

public class FlyAroundPlayerGoal extends Goal {

	private final EvilEyeEntity evilEyeEntity;

	public FlyAroundPlayerGoal(EvilEyeEntity pMob) {
		evilEyeEntity = pMob;
	}

	@Override
	public boolean canUse() {
		return true;
	}

	@Override
	public void tick() {
		Player targetedPlayer = evilEyeEntity.getTargetedPlayer();

		if (targetedPlayer == null) {
			return;
		}

		// Fly in a circle around the player, within 3 blocks of them

		// Calculate the angle between the player and the entity
		double angle = Math.atan2(evilEyeEntity.getX() - targetedPlayer.getX(), evilEyeEntity.getZ() - targetedPlayer.getZ());

		// Calculate the x and z coordinates of the circle
		double x = targetedPlayer.getX() + Math.sin(angle) * 3;
		double z = targetedPlayer.getZ() + Math.cos(angle) * 3;

		// Calculate the y coordinate of the circle
		double y = targetedPlayer.getY() + 1.5;

		// Move the position by a small random amount for a more natural look
		x += evilEyeEntity.getRandom().nextGaussian() * 0.05;
		y += evilEyeEntity.getRandom().nextGaussian() * 0.05;
		z += evilEyeEntity.getRandom().nextGaussian() * 0.05;


		// Set the entity's motion to move towards the coordinates
		evilEyeEntity.setDeltaMovement(
				(x - evilEyeEntity.getX()) * 0.05,
				(y - evilEyeEntity.getY()) * 0.05,
				(z - evilEyeEntity.getZ()) * 0.05);
	}
}