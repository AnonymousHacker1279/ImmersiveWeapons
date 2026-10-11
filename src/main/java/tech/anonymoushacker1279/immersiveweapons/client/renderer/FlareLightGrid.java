package tech.anonymoushacker1279.immersiveweapons.client.renderer;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

/// How far a flare's light can travel through the world, as a small 3D grid of path lengths around it.
///
/// A search spreads out from the flare's block through anything that doesn't fully block light, so corners, tunnels, and
/// walls are handled by the world itself rather than by whatever the camera can see. Each cell holds the length of the
/// shortest path to it, or is marked unreachable. The grid is stored as slices tiled into one 2D image, ready to be
/// uploaded as a texture, since 3D textures aren't available.
final class FlareLightGrid {

	/// Cells per axis. The flare sits at the center, so this covers `HALF` blocks in every direction.
	static final int SIZE = 32;
	static final int HALF = SIZE / 2;
	/// Slices are tiled `TILES_X` across, so the image is `WIDTH` by `HEIGHT`.
	static final int TILES_X = 8;
	static final int WIDTH = SIZE * TILES_X;
	static final int HEIGHT = SIZE * (SIZE / TILES_X);
	/// Path lengths are stored in a byte at this many steps per block. 255 means unreachable.
	static final float COST_SCALE = 12.0F;
	static final int UNREACHABLE = 255;

	/// Light is fully stopped by this much dampening (solid blocks).
	private static final int OPAQUE = 15;
	private static final int CELLS = SIZE * SIZE * SIZE;
	private static final float BLOCKED = -1.0F;
	/// Rebuild even without the flare moving, so changes to the surrounding blocks eventually show up.
	private static final long REFRESH_TICKS = 40;
	/// Rebuilds are limited for a moving flare, as each one is a fair amount of work.
	private static final long MIN_REBUILD_TICKS = 2;

	// The 26 neighbors, along with the step cost and which in-between cells must be open to cut a corner
	private static final int[][] MOVES = new int[26][];
	private static final float[] MOVE_COST = new float[26];
	private static final int[][][] MOVE_CLEARANCE = new int[26][][];

	static {
		int index = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					if (dx == 0 && dy == 0 && dz == 0) {
						continue;
					}

					MOVES[index] = new int[]{dx, dy, dz};
					MOVE_COST[index] = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
					MOVE_CLEARANCE[index] = clearanceFor(dx, dy, dz);
					index++;
				}
			}
		}
	}

	// Scratch space for the search. Building only happens on the render thread, so one copy is shared.
	private static final float[] COST = new float[CELLS];
	private static final float[] EXTRA = new float[CELLS];
	private static final boolean[] QUEUED = new boolean[CELLS];
	private static final int[] QUEUE = new int[1 << 16];

	/// The grid as it is uploaded: slices tiled into a `WIDTH` by `HEIGHT` image, one byte per cell.
	final byte[] image = new byte[WIDTH * HEIGHT];

	/// The block the flare was in when this was built, which is the center of the grid.
	BlockPos cell = BlockPos.ZERO;
	/// Whether a build happened that hasn't been uploaded yet.
	boolean dirty;
	private long builtAt = Long.MIN_VALUE;

	/// The block position of the grid's minimum corner.
	int originX() {
		return cell.getX() - HALF;
	}

	int originY() {
		return cell.getY() - HALF;
	}

	int originZ() {
		return cell.getZ() - HALF;
	}

	boolean isBuilt() {
		return builtAt != Long.MIN_VALUE;
	}

	void invalidate() {
		builtAt = Long.MIN_VALUE;
	}

	boolean needsBuild(BlockPos flareCell, long gameTime) {
		if (!isBuilt()) {
			return true;
		}

		long age = gameTime - builtAt;
		return (!flareCell.equals(cell) && age >= MIN_REBUILD_TICKS) || age >= REFRESH_TICKS;
	}

	void build(ClientLevel level, BlockPos flareCell, long gameTime) {
		cell = flareCell.immutable();
		builtAt = gameTime;
		dirty = true;

		int originX = originX();
		int originY = originY();
		int originZ = originZ();

		// What each cell lets through: blocked entirely, or an extra cost for partly transparent blocks
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for (int z = 0; z < SIZE; z++) {
			for (int y = 0; y < SIZE; y++) {
				for (int x = 0; x < SIZE; x++) {
					pos.set(originX + x, originY + y, originZ + z);

					float extra;
					if (!level.hasChunkAt(pos)) {
						extra = BLOCKED;
					} else {
						BlockState state = level.getBlockState(pos);
						int dampening = state.getLightDampening();
						extra = dampening >= OPAQUE ? BLOCKED : dampening * 0.5F;
					}

					EXTRA[index(x, y, z)] = extra;
				}
			}
		}

		Arrays.fill(COST, Float.POSITIVE_INFINITY);

		// The flare's own cell is always the start, even if it ended up partly inside a block
		int start = index(HALF, HALF, HALF);
		COST[start] = 0.0F;
		int head = 0;
		int tail = 0;
		QUEUE[tail++] = start;
		QUEUED[start] = true;

		while (head != tail) {
			int current = QUEUE[head];
			head = (head + 1) & (QUEUE.length - 1);
			QUEUED[current] = false;

			float currentCost = COST[current];
			int x = current % SIZE;
			int y = (current / SIZE) % SIZE;
			int z = current / (SIZE * SIZE);

			moves:
			for (int m = 0; m < MOVES.length; m++) {
				int[] move = MOVES[m];
				int nx = x + move[0];
				int ny = y + move[1];
				int nz = z + move[2];
				if (nx < 0 || ny < 0 || nz < 0 || nx >= SIZE || ny >= SIZE || nz >= SIZE) {
					continue;
				}

				int neighbor = index(nx, ny, nz);
				if (EXTRA[neighbor] == BLOCKED) {
					continue;
				}

				// No slipping diagonally between two blocks that only touch at an edge or corner
				for (int[] offset : MOVE_CLEARANCE[m]) {
					if (EXTRA[index(x + offset[0], y + offset[1], z + offset[2])] == BLOCKED) {
						continue moves;
					}
				}

				float newCost = currentCost + MOVE_COST[m] + EXTRA[neighbor];
				if (newCost > HALF || newCost >= COST[neighbor]) {
					continue;
				}

				COST[neighbor] = newCost;
				if (!QUEUED[neighbor]) {
					QUEUED[neighbor] = true;
					QUEUE[tail] = neighbor;
					tail = (tail + 1) & (QUEUE.length - 1);
				}
			}
		}

		for (int z = 0; z < SIZE; z++) {
			int tileX = (z % TILES_X) * SIZE;
			int tileY = (z / TILES_X) * SIZE;

			for (int y = 0; y < SIZE; y++) {
				for (int x = 0; x < SIZE; x++) {
					float cost = COST[index(x, y, z)];
					int value = cost == Float.POSITIVE_INFINITY
							? UNREACHABLE
							: Math.min(UNREACHABLE - 1, Math.round(cost * COST_SCALE));

					image[(tileY + y) * WIDTH + tileX + x] = (byte) value;
				}
			}
		}
	}

	private static int index(int x, int y, int z) {
		return x + SIZE * (y + SIZE * z);
	}

	/// Every cell a diagonal step passes next to: all non-empty, proper subsets of its axes.
	private static int[][] clearanceFor(int dx, int dy, int dz) {
		int[] axes = {dx, dy, dz};
		int nonZero = (dx != 0 ? 1 : 0) + (dy != 0 ? 1 : 0) + (dz != 0 ? 1 : 0);
		if (nonZero == 1) {
			return new int[0][];
		}

		java.util.List<int[]> offsets = new java.util.ArrayList<>();
		for (int mask = 1; mask < 8; mask++) {
			int[] offset = new int[3];
			int count = 0;
			boolean valid = true;

			for (int axis = 0; axis < 3; axis++) {
				if ((mask & (1 << axis)) != 0) {
					if (axes[axis] == 0) {
						valid = false;
						break;
					}

					offset[axis] = axes[axis];
					count++;
				}
			}

			if (valid && count < nonZero) {
				offsets.add(offset);
			}
		}

		return offsets.toArray(new int[0][]);
	}
}
