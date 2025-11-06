package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.WispEntity;

public class WispOrbitEntityGoal extends Goal {

	private final WispEntity entity;
	private double orbitAngle;
	private final double tiltAngle;
	private final double tiltDirection;
	private static final double ORBIT_RADIUS = 2.0d;
	private static final double ORBIT_SPEED = 0.05d; // Radians per tick
	private static final double VERTICAL_OFFSET = 0.5d; // Height offset from entity center

	public WispOrbitEntityGoal(WispEntity wisp) {
		entity = wisp;
		// Tilt angle: how much to tilt the orbit plane (0 to 45 degrees)
		tiltAngle = entity.getRandom().nextDouble() * Math.PI / 4.0d;
		// Tilt direction: which direction to tilt (0 to 360 degrees)
		tiltDirection = entity.getRandom().nextDouble() * Math.PI * 2.0d;
		// Random starting angle on the orbit
		orbitAngle = entity.getRandom().nextDouble() * Math.PI * 2.0d;
	}

	@Override
	public boolean canUse() {
		return entity.getTargetEntity() != null;
	}

	@Override
	public boolean canContinueToUse() {
		Entity target = entity.getTargetEntity();
		return target != null && target.isAlive() && entity.distanceTo(target) <= 12.0f;
	}

	@Override
	public void tick() {
		Entity target = entity.getTargetEntity();
		if (target == null) {
			return;
		}

		// Calculate the target position on the tilted circular orbit
		Vec3 targetPos = calculateOrbitPosition(target);

		// Move towards the target position
		moveTowards(targetPos);

		// Make the wisp look at the entity it's orbiting
		entity.lookAt(Anchor.EYES, target.position().add(0, target.getBbHeight() / 2.0f, 0));

		// Advance the orbit angle
		orbitAngle += ORBIT_SPEED;
		if (orbitAngle > Math.PI * 2.0d) {
			orbitAngle -= Math.PI * 2.0d;
		}

		// Every so often, add a slight random variation to the orbit angle to keep movement dynamic
		if (entity.getRandom().nextInt(20) == 0) {
			orbitAngle += (entity.getRandom().nextDouble() - 0.5d) * 0.2d; // Small random adjustment
		}

		// Occasionally attempt to apply effects to the target entity
		if (entity.getRandom().nextFloat() < 0.1f) {
			entity.attemptApplyEffectToTarget();
		}
	}

	/**
	 * Calculates the position on a tilted circular orbit around the target entity.
	 *
	 * @param target The entity to orbit around.
	 * @return The calculated orbit position as a Vec3.
	 */
	private Vec3 calculateOrbitPosition(Entity target) {
		Vec3 center = target.position().add(0, VERTICAL_OFFSET, 0);

		// Start with a circular orbit in the XZ plane
		double x = Math.cos(orbitAngle) * ORBIT_RADIUS;
		double y = 0;
		double z = Math.sin(orbitAngle) * ORBIT_RADIUS;

		// Apply tilt transformation
		// Rotate around an axis defined by tiltDirection
		double cosDir = Math.cos(tiltDirection);
		double sinDir = Math.sin(tiltDirection);
		double cosTilt = Math.cos(tiltAngle);
		double sinTilt = Math.sin(tiltAngle);

		// Rotation matrix for tilting the orbit plane
		// This rotates around an axis in the XZ plane
		double x2 = x * (cosDir * cosDir * (1 - cosTilt) + cosTilt) +
				z * (cosDir * sinDir * (1 - cosTilt)) +
				y * (sinDir * sinTilt);

		double y2 = x * (cosDir * sinDir * (1 - cosTilt)) +
				z * (sinDir * sinDir * (1 - cosTilt) + cosTilt) +
				y * (-cosDir * sinTilt);

		double z2 = x * (-sinDir * sinTilt) +
				z * (cosDir * sinTilt) +
				y * cosTilt;

		return center.add(x2, z2, y2);
	}

	/**
	 * Moves the entity toward a target position.
	 *
	 * @param target The target position to move towards.
	 */
	private void moveTowards(Vec3 target) {
		double deltaX = target.x - entity.getX();
		double deltaY = target.y - entity.getY();
		double deltaZ = target.z - entity.getZ();
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

		if (distance > 0.01d) {
			double speed = entity.getAttributeValue(Attributes.FLYING_SPEED) * 1.75d;
			entity.setDeltaMovement(entity.getDeltaMovement().add(
					(deltaX / distance) * speed,
					(deltaY / distance) * speed,
					(deltaZ / distance) * speed
			));
		}
	}
}