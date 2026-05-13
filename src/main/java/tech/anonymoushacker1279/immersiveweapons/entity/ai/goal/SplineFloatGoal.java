package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class SplineFloatGoal extends Goal {

	private final Mob entity;
	private final SplineFloatGoalConfig config;
	private final Predicate<Mob> canUseCondition;

	@Nullable
	private List<Vec3> splinePath;
	private int currentPathIndex = 0;
	private int targetingCooldown;
	private int pathRecalculationTimer = 0;

	/// Constructor with custom configuration.
	///
	/// @param entity          The mob to control.
	/// @param config          Configuration for pathfinding behavior.
	/// @param canUseCondition Additional condition for when the goal can be used.
	public SplineFloatGoal(Mob entity, SplineFloatGoalConfig config, Predicate<Mob> canUseCondition) {
		this.entity = entity;
		this.config = config;
		this.canUseCondition = canUseCondition;
	}


	@Override
	public boolean canUse() {
		return canUseCondition.test(entity);
	}

	@Override
	public void tick() {
		if (targetingCooldown > 0) {
			targetingCooldown--;
			return;
		}

		// Recalculate path periodically to keep movement dynamic
		pathRecalculationTimer++;
		if (pathRecalculationTimer >= config.pathRecalculationInterval()) {
			splinePath = null;
			currentPathIndex = 0;
			pathRecalculationTimer = 0;
		}

		// Generate a new spline path if needed
		if (splinePath == null || currentPathIndex >= splinePath.size()) {
			generateSplinePath();
			currentPathIndex = 0;

			// If path generation failed, wait before trying again
			if (splinePath == null) {
				targetingCooldown = 40;
				return;
			}
		}

		// Move along the spline path
		if (currentPathIndex < splinePath.size()) {
			Vec3 targetPoint = splinePath.get(currentPathIndex);
			moveTowards(targetPoint);

			// Check if we've arrived at the current point
			if (entity.position().distanceTo(targetPoint) < config.arrivalThreshold()) {
				currentPathIndex++;
			}
		}
	}

	/// Generates a smooth spline path through open air spaces. Uses waypoints and Catmull-Rom spline interpolation.
	private void generateSplinePath() {
		List<Vec3> waypoints = generateWaypoints();

		// Need at least 2 waypoints for a path
		if (waypoints.size() < 2) {
			splinePath = null;
			return;
		}

		// Generate spline path from waypoints
		// Estimate capacity: (waypoints - 1) segments * (splineSegments + 1) points per segment
		int estimatedCapacity = (waypoints.size() - 1) * (config.splineSegments() + 1);
		List<Vec3> pathPoints = new ArrayList<>(estimatedCapacity);

		// For Catmull-Rom spline, we need to handle the first and last segments specially
		for (int i = 0; i < waypoints.size() - 1; i++) {
			Vec3 p0 = i > 0 ? waypoints.get(i - 1) : waypoints.get(i);
			Vec3 p1 = waypoints.get(i);
			Vec3 p2 = waypoints.get(i + 1);
			Vec3 p3 = i < waypoints.size() - 2 ? waypoints.get(i + 2) : waypoints.get(i + 1);

			// Interpolate between p1 and p2 using Catmull-Rom
			for (int j = 0; j <= config.splineSegments(); j++) {
				double t = (double) j / config.splineSegments();
				Vec3 point = catmullRomInterpolate(p0, p1, p2, p3, t);

				// Verify the point is in open space
				if (isOpenSpace(point)) {
					pathPoints.add(point);
				}
			}
		}

		// If the path is empty after validation, clear it
		if (pathPoints.isEmpty()) {
			splinePath = null;
		} else {
			splinePath = pathPoints;
		}
	}

	/// Generates waypoints in open air spaces. Attempts to find positions that are far from obstacles.
	private List<Vec3> generateWaypoints() {
		List<Vec3> waypoints = new ArrayList<>(config.waypointCount());
		Vec3 currentPos = entity.position();
		waypoints.add(currentPos);

		for (int attempt = 0; attempt < config.waypointCount() && waypoints.size() < config.waypointCount(); attempt++) {
			Vec3 lastWaypoint = waypoints.getLast();

			// Try multiple times to find a valid waypoint
			for (int retry = 0; retry < 10; retry++) {
				Vec3 candidate = generateCandidateWaypoint(lastWaypoint);

				// Check if waypoint is valid (open space, not too close to last waypoint, clear path)
				if (isValidWaypoint(lastWaypoint, candidate)) {
					waypoints.add(candidate);
					break;
				}
			}
		}

		return waypoints;
	}

	/// Generates a candidate waypoint position relative to a starting position.
	///
	/// @param from The starting position.
	/// @return A candidate waypoint position.
	private Vec3 generateCandidateWaypoint(Vec3 from) {
		double xOffset = (entity.getRandom().nextDouble() - 0.5d) * 2 * config.searchRadiusHorizontal();
		double yOffset = (entity.getRandom().nextDouble() - 0.5d) * 2 * config.searchRadiusVertical();
		double zOffset = (entity.getRandom().nextDouble() - 0.5d) * 2 * config.searchRadiusHorizontal();

		Vec3 candidate = from.add(xOffset, yOffset, zOffset);

		// If average height above ground is specified, bias the waypoint towards that height
		if (config.averageHeightAboveGround() > 0) {
			int groundLevel = entity.level().getHeight(Heightmap.Types.WORLD_SURFACE, BlockPos.containing(candidate));
			int targetY = groundLevel + config.averageHeightAboveGround();

			// Bias towards the target height (not forced, allows some variation)
			double currentYDiff = candidate.y - targetY;
			candidate = new Vec3(candidate.x, candidate.y - (currentYDiff * 0.5d), candidate.z);
		}

		// Clamp Y coordinate to maximum height
		int maxY = entity.level().getHeight(Heightmap.Types.WORLD_SURFACE, BlockPos.containing(candidate)) + config.maxYLevelAboveWorld();
		if (candidate.y > maxY) {
			candidate = new Vec3(candidate.x, maxY, candidate.z);
		}

		return candidate;
	}

	/// Validates that a waypoint is suitable for pathfinding.
	///
	/// @param from      The position from which the waypoint is generated.
	/// @param candidate The candidate waypoint position.
	/// @return True if the waypoint is valid, false otherwise.
	private boolean isValidWaypoint(Vec3 from, Vec3 candidate) {
		// Check minimum distance
		if (from.distanceTo(candidate) < config.minWaypointDistance()) {
			return false;
		}

		// Check if the waypoint itself is in open space
		if (!isOpenSpace(candidate)) {
			return false;
		}

		// Check biome constraint if applicable
		if (config.requiredBiome() != null && config.requiredDimension() != null) {
			// Only enforce biome constraint if entity was naturally spawned and is in the required dimension
			if (entity.getSpawnType() == EntitySpawnReason.NATURAL && entity.level().dimension() == config.requiredDimension()) {
				BlockPos candidatePos = BlockPos.containing(candidate);
				if (!entity.level().getBiome(candidatePos).is(config.requiredBiome())) {
					return false;
				}
			}
		}

		// Check if there's a relatively clear path to the waypoint
		// Simple raycast check - if it hits a block, the path is obstructed
		HitResult hitResult = entity.level().clip(new ClipContext(
				from,
				candidate,
				ClipContext.Block.COLLIDER,
				ClipContext.Fluid.NONE,
				entity
		));

		return hitResult.getType() == HitResult.Type.MISS;
	}

	/// Checks if a position is in open space (air blocks). Checks the position and immediate surrounding area.
	///
	/// @param pos The position to check.
	/// @return True if the area is open space, false otherwise.
	private boolean isOpenSpace(Vec3 pos) {
		BlockPos blockPos = BlockPos.containing(pos);

		// Check center and a small radius around it
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					BlockPos checkPos = blockPos.offset(dx, dy, dz);
					if (!entity.level().getBlockState(checkPos).isAir()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/// Catmull-Rom spline interpolation for smooth curves.
	///
	/// @param p0 Point before start
	/// @param p1 Start point
	/// @param p2 End point
	/// @param p3 Point after end
	/// @param t  Interpolation parameter (0 to 1)
	/// @return Interpolated point
	private Vec3 catmullRomInterpolate(Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, double t) {
		double t2 = t * t;
		double t3 = t2 * t;

		// Catmull-Rom basis functions
		double b0 = -t3 + 2 * t2 - t;
		double b1 = 3 * t3 - 5 * t2 + 2;
		double b2 = -3 * t3 + 4 * t2 + t;
		double b3 = t3 - t2;

		// Interpolate each coordinate
		double x = 0.5d * (b0 * p0.x + b1 * p1.x + b2 * p2.x + b3 * p3.x);
		double y = 0.5d * (b0 * p0.y + b1 * p1.y + b2 * p2.y + b3 * p3.y);
		double z = 0.5d * (b0 * p0.z + b1 * p1.z + b2 * p2.z + b3 * p3.z);

		return new Vec3(x, y, z);
	}

	/// Moves the entity toward a target point.
	///
	/// @param target The target position to move towards.
	private void moveTowards(Vec3 target) {
		double deltaX = target.x - entity.getX();
		double deltaY = target.y - entity.getY();
		double deltaZ = target.z - entity.getZ();
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

		if (distance > 0.01d) {
			double speed = entity.getAttributeValue(Attributes.FLYING_SPEED);
			entity.setDeltaMovement(entity.getDeltaMovement().add(
					(deltaX / distance) * speed,
					(deltaY / distance) * speed,
					(deltaZ / distance) * speed
			));

			// Make the entity look where it's going
			entity.lookAt(Anchor.EYES, target);
		}
	}
}