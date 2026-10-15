package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeFloodFill;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/// The space a smoke cloud currently occupies, used to decide which mobs it hides from, and which it hides. It follows the same flood fill
/// as the visuals, so the effect fills a room and stops at walls instead of being a sphere.
///
/// The volume is rebuilt periodically so opening a door or breaking a wall lets the smoke through.
public class SmokeCloudVolume {

	private static final int REFRESH_INTERVAL_TICKS = 20;

	/// Clouds that were updated recently, so targeting can ask whether anything is hiding a target without having to
	/// go through the entities that own them. Weak, so a cloud disappears with its entity.
	private static final Set<SmokeCloudVolume> ACTIVE = Collections.newSetFromMap(new WeakHashMap<>());
	/// Entities update their cloud every other tick, so a cloud that has gone quiet for longer than this is over.
	private static final int ACTIVE_TIMEOUT_TICKS = 10;

	private final LongOpenHashSet cells = new LongOpenHashSet();
	private long nextRefreshTime;
	private long lastUpdateTime = Long.MIN_VALUE;
	@Nullable
	private ResourceKey<Level> dimension;

	/// Puffs live for between 1x and this multiple of the smoke duration.
	public static final float PUFF_LIFETIME_SPREAD = 1.05F;
	/// The fraction of the smoke duration the effect lasts, so it ends when the puffs have mostly faded. They fade
	/// over the last quarter of their lifetime, so this is partway through that.
	private static final float EFFECT_FRACTION = 0.95F;

	/// @param smokeDuration how long the smoke lasts, in ticks
	/// @return how long the target-clearing effect lasts, in ticks
	public static int getEffectTicks(int smokeDuration) {
		return (int) (smokeDuration * EFFECT_FRACTION);
	}

	/// The most distinct points smoke is released from. A grenade that rolls while it emits releases from many.
	private static final int MAX_ORIGINS = 16;

	private final LongOpenHashSet origins = new LongOpenHashSet();
	private boolean dirty;

	/// Records a point the smoke is released from. The volume is the union of what each of them can reach, so a
	/// grenade that rolls while it emits fills a trail rather than a single sphere.
	public void addOrigin(Vec3 origin) {
		if (origins.size() < MAX_ORIGINS && origins.add(BlockPos.containing(origin).asLong())) {
			dirty = true;
		}
	}

	/// Rebuilds the volume if it is due. Cheap to call every tick. A cloud with no origins yet gets the given one.
	///
	/// @param level  the level
	/// @param origin the center of the cloud, if it has not been given any
	/// @param radius the maximum reach of the cloud, in blocks
	public void update(Level level, Vec3 origin, double radius) {
		if (origins.isEmpty()) {
			addOrigin(origin);
		}

		update(level, radius);
	}

	/// Rebuilds the volume if it is due, or if there are new origins. Cheap to call every tick.
	///
	/// @param level  the level
	/// @param radius the maximum reach of the cloud from each origin, in blocks
	public void update(Level level, double radius) {
		long gameTime = level.getGameTime();
		lastUpdateTime = gameTime;
		dimension = level.dimension();
		ACTIVE.add(this);

		if (!dirty && gameTime < nextRefreshTime) {
			return;
		}

		dirty = false;
		nextRefreshTime = gameTime + REFRESH_INTERVAL_TICKS;
		cells.clear();
		for (long origin : origins) {
			for (SmokeFloodFill.Cell cell : SmokeFloodFill.fill(level, BlockPos.of(origin), radius, SmokeFloodFill.MAX_CELLS)) {
				cells.add(cell.pos().asLong());
			}
		}
	}

	/// How far past the cloud to look for mobs that could be targeting something inside it, in blocks.
	public static final double TARGETER_SEARCH_MARGIN = 32.0d;

	/// Whether any active smoke cloud in the mob's level stops it from having the target. Used by targeting, so mobs
	/// can't pick a target out of the smoke, or keep one that walks into it.
	///
	/// @param target may be null, in which case only the mob being in the smoke matters
	public static boolean isTargetHidden(Mob mob, @Nullable LivingEntity target) {
		if (ACTIVE.isEmpty() || mob.is(EntityTypes.BOSSES)) {
			return false;
		}

		Level level = mob.level();
		long gameTime = level.getGameTime();
		for (SmokeCloudVolume cloud : ACTIVE) {
			if (cloud.dimension == level.dimension()
					&& gameTime - cloud.lastUpdateTime <= ACTIVE_TIMEOUT_TICKS
					&& (cloud.contains(mob) || (target != null && cloud.contains(target)))) {
				return true;
			}
		}

		return false;
	}

	/// A mob loses its target when it is inside the smoke, so it can't see out, or when the target is inside the
	/// smoke, so it can't be seen.
	///
	/// @return true if the mob should drop its current target
	public boolean hidesTarget(Mob mob) {
		LivingEntity target = mob.getTarget();
		return contains(mob) || (target != null && contains(target));
	}

	/// @return true if the entity's feet or eyes are inside the smoke
	public boolean contains(LivingEntity entity) {
		return cells.contains(entity.blockPosition().asLong())
				|| cells.contains(BlockPos.containing(entity.getEyePosition()).asLong());
	}
}
