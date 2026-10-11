package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/// Finds the space a smoke cloud can reach, so it pools in a room instead of leaking through walls.
///
/// Uses no client-only classes, so the server can reuse it for the gameplay volume.
public class SmokeFloodFill {

	/// A sensible cap on cells for a single cloud.
	public static final int MAX_CELLS = 2000;

	/// A reachable cell and how many steps it is from the origin.
	public record Cell(BlockPos pos, int distance) {}

	/// Breadth-first search through cells with no collision, in order of distance from the origin.
	///
	/// @param level     the level
	/// @param origin    the starting position
	/// @param radius    the maximum straight-line distance from the origin, in blocks
	/// @param maxCells  a hard cap on the number of cells returned
	/// @return the reachable cells, nearest first
	public static List<Cell> fill(Level level, BlockPos origin, double radius, int maxCells) {
		List<Cell> result = new ArrayList<>();
		LongOpenHashSet visited = new LongOpenHashSet();
		ArrayDeque<Cell> queue = new ArrayDeque<>();
		double radiusSqr = radius * radius;

		// A grenade that lands embedded in a block still needs somewhere to start
		BlockPos start = origin;
		if (isBlocked(level, start)) {
			start = null;
			for (Direction direction : Direction.values()) {
				BlockPos neighbor = origin.relative(direction);
				if (!isBlocked(level, neighbor)) {
					start = neighbor;
					break;
				}
			}
			if (start == null) {
				return result;
			}
		}

		visited.add(start.asLong());
		queue.add(new Cell(start, 0));

		while (!queue.isEmpty() && result.size() < maxCells) {
			Cell cell = queue.poll();
			result.add(cell);

			for (Direction direction : Direction.values()) {
				BlockPos next = cell.pos().relative(direction);
				if (next.distSqr(origin) > radiusSqr || !visited.add(next.asLong())) {
					continue;
				}

				if (!isBlocked(level, next)) {
					queue.add(new Cell(next, cell.distance() + 1));
				}
			}
		}

		return result;
	}

	/// Anything with a collision shape blocks smoke. That is conservative: fences and slabs hold it back too, but it
	/// still flows around them, and closed doors seal a room.
	///
	/// Unloaded chunks also block, so the fill never forces a chunk to load.
	private static boolean isBlocked(Level level, BlockPos pos) {
		if (!level.hasChunkAt(pos)) {
			return true;
		}

		BlockState state = level.getBlockState(pos);
		return !state.getCollisionShape(level, pos).isEmpty();
	}
}
