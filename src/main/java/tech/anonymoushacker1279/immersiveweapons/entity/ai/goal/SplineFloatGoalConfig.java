package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

/**
 * Configuration record for SplineFloatGoal parameters.
 *
 * @param waypointCount             Number of waypoints to generate
 * @param splineSegments            Interpolation points between waypoints
 * @param searchRadiusHorizontal    Horizontal search range
 * @param searchRadiusVertical      Vertical search range
 * @param minWaypointDistance       Minimum distance between waypoints
 * @param arrivalThreshold          Distance to consider "arrived" at a point
 * @param pathRecalculationInterval Recalculate path every N ticks
 * @param maxYLevelAboveWorld       Maximum height above world surface for pathfinding
 * @param averageHeightAboveGround  Average height above ground where pathfinding occurs (0 = at ground level)
 * @param requiredBiome             The biome the mob must stay in (if naturally spawned), or null for no constraint
 * @param requiredDimension         The dimension where biome constraint applies, or null for no constraint
 */
public record SplineFloatGoalConfig(
		int waypointCount,
		int splineSegments,
		int searchRadiusHorizontal,
		int searchRadiusVertical,
		int minWaypointDistance,
		double arrivalThreshold,
		int pathRecalculationInterval,
		int maxYLevelAboveWorld,
		int averageHeightAboveGround,
		@Nullable ResourceKey<Biome> requiredBiome,
		@Nullable ResourceKey<Level> requiredDimension) {

	public static SplineFloatGoalConfig wisp() {
		return new SplineFloatGoalConfig(
				8,
				12,
				48,
				32,
				12,
				1.5d,
				600,
				64,
				0,
				null,
				null
		);
	}

	public static SplineFloatGoalConfig evilEye(ResourceKey<Biome> biome, ResourceKey<Level> dimension) {
		return new SplineFloatGoalConfig(
				12,
				12,
				64,
				48,
				16,
				1.5d,
				600,
				72,
				20,
				biome,
				dimension
		);
	}
}