package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// The space a smoke cloud fills, as a smooth 0-1 field that particles use to clip themselves to it.
///
/// Puffs are camera-facing quads much larger than the cells they sit in, so they would otherwise poke through walls.
/// Each puff samples this field at the corners of its tiles instead, so the smoke stops at walls from any angle.
///
/// Particles hold a strong reference to their field, so it lives as long as any puff from the cloud does.
public class SmokeCloudField {

	private static final Map<Integer, WeakReference<SmokeCloudField>> REGISTRY = new HashMap<>();
	private static int nextId = 1;

	private final int id;
	private final int minX;
	private final int minY;
	private final int minZ;
	private final int size;
	private final int duration;
	private final boolean[] filled;

	private SmokeCloudField(int id, BlockPos origin, int radius, int duration, List<SmokeFloodFill.Cell> cells) {
		this.id = id;
		this.duration = duration;
		// A margin of one cell lets the trilinear sampling read empty space around the edge
		int extent = radius + 2;
		this.minX = origin.getX() - extent;
		this.minY = origin.getY() - extent;
		this.minZ = origin.getZ() - extent;
		this.size = extent * 2 + 1;
		this.filled = new boolean[size * size * size];

		for (SmokeFloodFill.Cell cell : cells) {
			BlockPos pos = cell.pos();
			int x = pos.getX() - minX;
			int y = pos.getY() - minY;
			int z = pos.getZ() - minZ;
			if (inBounds(x, y, z)) {
				filled[index(x, y, z)] = true;
			}
		}
	}

	/// Creates a field and registers it so particles can find it by ID.
	public static SmokeCloudField create(BlockPos origin, double radius, int duration, List<SmokeFloodFill.Cell> cells) {
		REGISTRY.values().removeIf(reference -> reference.get() == null);

		SmokeCloudField field = new SmokeCloudField(nextId++, origin, (int) Math.ceil(radius), duration, cells);
		REGISTRY.put(field.id, new WeakReference<>(field));
		return field;
	}

	@Nullable
	public static SmokeCloudField get(int id) {
		WeakReference<SmokeCloudField> reference = REGISTRY.get(id);
		return reference == null ? null : reference.get();
	}

	public int id() {
		return id;
	}

	/// @return how long, in ticks, the smoke lasts
	public int duration() {
		return duration;
	}

	/// @return 0 where the smoke can't be, and 1 inside it and up to the surface of a wall. It reaches 0 half a block
	///         into the wall.
	public float visibility(double x, double y, double z) {
		// Trilinear between cell centers
		double fx = x - minX - 0.5d;
		double fy = y - minY - 0.5d;
		double fz = z - minZ - 0.5d;
		int ix = Mth.floor(fx);
		int iy = Mth.floor(fy);
		int iz = Mth.floor(fz);
		float tx = (float) (fx - ix);
		float ty = (float) (fy - iy);
		float tz = (float) (fz - iz);

		float value = 0.0F;
		for (int dx = 0; dx <= 1; dx++) {
			for (int dy = 0; dy <= 1; dy++) {
				for (int dz = 0; dz <= 1; dz++) {
					if (isFilled(ix + dx, iy + dy, iz + dz)) {
						value += (dx == 0 ? 1.0F - tx : tx) * (dy == 0 ? 1.0F - ty : ty) * (dz == 0 ? 1.0F - tz : tz);
					}
				}
			}
		}

		// Halfway between a filled cell and an empty one is the wall face. Smoke stays fully visible up to the face,
		// where the wall itself hides the puff, and is gone by the middle of the wall, so nothing shows on the other
		// side. Fading before the face would leave a gap between the smoke and the walls of a small room.
		float t = Mth.clamp(value * 2.0F, 0.0F, 1.0F);
		return t * t * (3.0F - 2.0F * t);
	}

	private boolean isFilled(int x, int y, int z) {
		return inBounds(x, y, z) && filled[index(x, y, z)];
	}

	private boolean inBounds(int x, int y, int z) {
		return x >= 0 && y >= 0 && z >= 0 && x < size && y < size && z < size;
	}

	private int index(int x, int y, int z) {
		return (y * size + z) * size + x;
	}
}
