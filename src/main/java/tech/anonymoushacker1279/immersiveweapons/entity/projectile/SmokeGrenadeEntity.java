package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeCloudField;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeFloodFill;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.GameEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SmokeGrenadePayload;

import java.util.List;

public class SmokeGrenadeEntity extends AdvancedThrowableItemProjectile {

	private static final int SPREAD_TICKS_PER_BLOCK = 2;
	/// How long the grenade releases smoke for once it goes off, in ticks, and how often it releases a burst.
	private static final int EMIT_TICKS = 60;
	private static final int EMIT_INTERVAL_TICKS = 3;
	private static final int CELL_CACHE_TICKS = 10;

	private static List<SmokeFloodFill.Cell> cachedCells = List.of();
	@Nullable
	private static Level cachedLevel;
	@Nullable
	private static BlockPos cachedOrigin;
	private static double cachedRadius;
	private static long cachedTime;

	private final SmokeCloudVolume cloud = new SmokeCloudVolume();
	private int color;
	/// Ticks since the fuse ran out.
	private int activeTicks;

	public SmokeGrenadeEntity(EntityType<? extends SmokeGrenadeEntity> entityType, Level level) {
		super(entityType, level);
	}

	public SmokeGrenadeEntity(Level level, LivingEntity livingEntity, ItemStack stack) {
		super(EntityRegistry.SMOKE_GRENADE_ENTITY.get(), livingEntity, level, stack);
	}

	public SmokeGrenadeEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.SMOKE_GRENADE_ENTITY.get(), level, x, y, z, ItemRegistry.SMOKE_GRENADE.get().getDefaultInstance());
	}

	/// Spawns the smoke for one burst of a grenade's emission. The grenade releases its smoke over several bursts, so
	/// the cloud follows it as it rolls.
	///
	/// @param particleFraction the share of the full particle count this burst releases
	/// @param duration         how long, in ticks, the smoke from this burst should last
	/// @param playSound        whether to play the hiss, which is only done by the first burst
	public static void runOnClientImpact(double x, double y, double z, int color, Level level, int forcedParticleCount,
	                                     double radius, int duration, float particleFraction, boolean playSound) {
		int total = forcedParticleCount == -1 ? IWConfigs.CLIENT.smokeGrenadeParticles.getAsInt() : forcedParticleCount;
		int particles = Math.max(1, Math.round(total * particleFraction));

		SmokeGrenadeParticleOptions options = SmokeGrenadeParticleOptions.getParticleByColor(color);
		RandomSource random = level.getRandom();

		// Spread the smoke over the space it can actually reach, so it fills the room instead of leaking through walls
		BlockPos origin = BlockPos.containing(x, y, z);
		List<SmokeFloodFill.Cell> cells = getCells(level, origin, radius);

		// Puffs clip themselves to this, so they don't show through walls
		int cloudId = cells.isEmpty() ? 0 : SmokeCloudField.create(origin, radius, duration, cells).id();

		for (int i = 0; i < particles; ++i) {
			double px = x;
			double py = y;
			double pz = z;
			int delay = 0;

			if (!cells.isEmpty()) {
				// Squaring the roll favors the cells nearest the grenade, so the cloud is densest at the source
				float roll = random.nextFloat();
				SmokeFloodFill.Cell cell = cells.get((int) (cells.size() * roll * roll));
				px = cell.pos().getX() + random.nextDouble();
				py = cell.pos().getY() + random.nextDouble();
				pz = cell.pos().getZ() + random.nextDouble();
				delay = cell.distance() * SPREAD_TICKS_PER_BLOCK;
			}

			level.addParticle(options.with(cloudId, delay),
					true, true, px, py, pz,
					(0.1d * random.nextGaussian()),
					(0.1d * random.nextGaussian()),
					(0.1d * random.nextGaussian()));
		}

		if (playSound) {
			level.playLocalSound(x, y, z, SoundEventRegistry.SMOKE_GRENADE_HISS.get(),
					SoundSource.NEUTRAL, 0.2f, 0.6f, true);
		}
	}

	/// A grenade that is not moving much releases several bursts from the same place, so the fill is reused.
	private static List<SmokeFloodFill.Cell> getCells(Level level, BlockPos origin, double radius) {
		long gameTime = level.getGameTime();
		if (cachedLevel != level || !origin.equals(cachedOrigin) || radius != cachedRadius
				|| gameTime - cachedTime > CELL_CACHE_TICKS) {
			cachedCells = SmokeFloodFill.fill(level, origin, radius, SmokeFloodFill.MAX_CELLS);
			cachedLevel = level;
			cachedOrigin = origin;
			cachedRadius = radius;
		}

		cachedTime = gameTime;
		return cachedCells;
	}

	/// Set the particle color. Color IDs can be found in [SmokeGrenadeParticleOptions.SmokeGrenadeColors].
	///
	/// @param color a color ID
	public void setColor(int color) {
		this.color = color;
	}

	/// It goes off on a timer, wherever it has ended up.
	@Override
	protected int getFuseTicks() {
		return IWConfigs.SERVER.smokeGrenadeFuse.getAsInt();
	}

	/// The grenade goes away with its smoke, so this never despawns it first.
	@Override
	protected int getMaxTicksInGround() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected Item getDefaultItem() {
		return ItemRegistry.SMOKE_GRENADE.get();
	}

	@Override
	public void tick() {
		super.tick();

		if (!(level() instanceof ServerLevel serverLevel)) {
			return;
		}

		if (!hasActivated) {
			return;
		}

		int duration = IWConfigs.SERVER.smokeGrenadeDuration.getAsInt();
		activeTicks++;
		if (activeTicks > duration) {
			discard();
			return;
		}

		// Release the smoke over a few seconds, from wherever the grenade is by then
		if (activeTicks <= EMIT_TICKS && (activeTicks - 1) % EMIT_INTERVAL_TICKS == 0) {
			emitBurst(serverLevel, duration);
		}

		if (activeTicks <= SmokeCloudVolume.getEffectTicks(duration)) {
			if (tickCount % 2 == 0) {
				double range = IWConfigs.SERVER.smokeGrenadeEffectRange.getAsDouble();
				cloud.update(serverLevel, range);

				for (Mob mob : serverLevel.getEntitiesOfClass(Mob.class, getBoundingBox().inflate(range + SmokeCloudVolume.TARGETER_SEARCH_MARGIN))) {
					if (!mob.is(EntityTypes.BOSSES) && cloud.hidesTarget(mob)) {
						mob.setTarget(null);
					}
				}
			}

			if (activeTicks % 10 == 0) {
				gameEvent(GameEventRegistry.SMOKE_GRENADE_HISS, getOwner());
			}
		}
	}

	private void emitBurst(ServerLevel serverLevel, int duration) {
		cloud.addOrigin(position());

		// Every burst should end together, so later ones last for less time
		int remaining = Math.max(duration - (activeTicks - 1), 1);
		float fraction = (float) EMIT_INTERVAL_TICKS / EMIT_TICKS;

		PacketDistributor.sendToPlayersTrackingChunk(serverLevel, serverLevel.getChunkAt(blockPosition()).getPos(),
				new SmokeGrenadePayload(getX(), getY(), getZ(), color, IWConfigs.SERVER.forceSmokeGrenadeParticles.getAsInt(),
						(float) IWConfigs.SERVER.smokeGrenadeEffectRange.getAsDouble(), remaining, fraction, activeTicks == 1));
	}

	@Override
	public void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.putInt("activeTicks", activeTicks);
	}

	@Override
	public void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		activeTicks = valueInput.getIntOr("activeTicks", 0);
	}
}
