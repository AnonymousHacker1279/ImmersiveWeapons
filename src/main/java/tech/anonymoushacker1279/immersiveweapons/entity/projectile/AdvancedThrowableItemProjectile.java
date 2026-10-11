package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public abstract class AdvancedThrowableItemProjectile extends ThrowableItemProjectile {

	/// The fraction of speed into a surface that is kept when bouncing off of it.
	private static final double RESTITUTION = 0.45D;
	/// The fraction of speed along a surface that is kept when bouncing off of it.
	private static final double FLOOR_FRICTION = 0.75D;
	private static final double WALL_FRICTION = 0.85D;
	/// The fraction of speed kept each tick while rolling along the ground.
	private static final double ROLLING_FRICTION = 0.85D;
	/// A bounce off of the ground slower than this, in blocks per tick, is the grenade coming to rest.
	private static final double REST_BOUNCE_SPEED = 0.08D;
	/// A rolling grenade slower than this, in blocks per tick, stops.
	private static final double STOP_SPEED = 0.01D;
	/// How far to place the grenade off of a surface it bounced from, so the next ray doesn't start inside the block.
	private static final double SURFACE_OFFSET = 0.02D;

	/// How fast the model tumbles in the air, in radians per tick for each block per tick of speed, up to a limit.
	private static final float TUMBLE_PER_SPEED = 0.5F;
	private static final float MAX_TUMBLE = 0.45F;
	/// How fast the model rolls on the ground, in radians per tick for each block per tick of speed, up to a limit.
	private static final float ROLL_PER_SPEED = 8.0F;
	private static final float MAX_ROLL = 0.5F;

	// Visual orientation of the model, only tracked on the client
	private final Quaternionf orientation = new Quaternionf();
	private final Quaternionf previousOrientation = new Quaternionf();
	private boolean orientationReady;
	private float spinDirection = 1.0F;
	private float spinYaw;
	private float restBlend;
	private float previousRestBlend;

	/// True once the grenade is fully at rest.
	protected boolean stopMoving = false;
	protected boolean hasActivated = false;
	/// True while the grenade is rolling along a surface, rather than flying or bouncing.
	protected boolean resting = false;
	protected int ticksInGround;

	public AdvancedThrowableItemProjectile(EntityType<? extends AdvancedThrowableItemProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public AdvancedThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> entityType, LivingEntity livingEntity, Level level, ItemStack stack) {
		super(entityType, livingEntity, level, stack);
	}

	public AdvancedThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level, double x, double y, double z, ItemStack stack) {
		super(entityType, x, y, z, level, stack);
	}

	public static boolean canSee(LivingEntity livingEntity, Entity entity, boolean lookingAt) {
		if (livingEntity.hasLineOfSight(entity)) {
			if (!lookingAt) {
				return true;
			}

			Vec3 lookVec = livingEntity.getLookAngle();
			Vec3 toEntity = entity.position().subtract(livingEntity.position()).normalize();

			double dotProduct = lookVec.dot(toEntity);
			double maxCosine = Math.cos(Math.toRadians(90));
			double angleToGround = 180 - (Math.acos(lookVec.y) * (180.0f / Math.PI));
			return dotProduct > maxCosine || angleToGround < 30;
		}

		return false;
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (stopMoving) {
			return;
		}

		Vec3 velocity = getDeltaMovement();

		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHitResult = (BlockHitResult) hitResult;
			Vec3 normal = Vec3.atLowerCornerOf(blockHitResult.getDirection().getUnitVec3i());

			// Split the velocity into the part into the surface and the part along it. The first is reflected and loses
			// energy, the second is only slowed by friction.
			double intoSurface = velocity.dot(normal);
			if (intoSurface >= 0.0D) {
				return;
			}

			Vec3 normalPart = normal.scale(intoSurface);
			Vec3 tangentPart = velocity.subtract(normalPart);
			boolean floor = normal.y > 0.5D;

			double restitution = Mth.clamp(RESTITUTION + 0.05D * random.nextGaussian(), 0.25D, 0.65D);
			Vec3 outgoing = tangentPart.scale(floor ? FLOOR_FRICTION : WALL_FRICTION)
					.subtract(normalPart.scale(restitution));

			// Rest on the surface rather than bouncing forever with ever smaller hops
			if (floor && outgoing.y < REST_BOUNCE_SPEED) {
				outgoing = new Vec3(outgoing.x, 0.0D, outgoing.z);
				resting = true;
				setNoGravity(true);
			}

			setPos(blockHitResult.getLocation().add(normal.scale(SURFACE_OFFSET)));
			setDeltaMovement(outgoing);

			// A bounce knocks the spin in a new direction
			spinDirection = random.nextBoolean() ? 1.0F : -1.0F;
			spinYaw = (random.nextFloat() - 0.5F) * 1.4F;

			double impactSpeed = -intoSurface;
			if (impactSpeed > 0.12D) {
				level().playLocalSound(this,
						SoundEvents.METAL_HIT,
						SoundSource.NEUTRAL,
						(float) Math.min(1.0D, impactSpeed * 2.0D),
						1.0f + (-0.2f + getRandom().nextFloat() * 0.2f));
			}
		} else if (hitResult.getType() == Type.ENTITY) {
			setDeltaMovement(
					-velocity.x * 0.3d + (0.1D * random.nextGaussian()),
					-velocity.y * 0.3d + (0.1D * random.nextGaussian()),
					-velocity.z * 0.3d + (0.1D * random.nextGaussian()));

			level().playLocalSound(this,
					SoundEvents.METAL_HIT,
					SoundSource.NEUTRAL,
					1.0f,
					1.0f + (-0.2f + getRandom().nextFloat() * 0.2f));
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (level().isClientSide()) {
			updateOrientation();
		}

		if (resting) {
			if (hasSupport()) {
				Vec3 velocity = getDeltaMovement();
				double horizontalSpeed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);

				if (horizontalSpeed < STOP_SPEED) {
					setDeltaMovement(Vec3.ZERO);
					stopMoving = true;
				} else {
					setDeltaMovement(velocity.x * ROLLING_FRICTION, 0.0D, velocity.z * ROLLING_FRICTION);
				}
			} else {
				// Rolled off an edge, or the block it was on went away
				resting = false;
				stopMoving = false;
				setNoGravity(false);
			}
		}

		if (stopMoving) {
			ticksInGround++;

			if (!hasActivated && activatesOnRest()) {
				hasActivated = true;
				onActivate();
			}
		}

		int fuse = getFuseTicks();
		if (!hasActivated && fuse >= 0 && tickCount >= fuse) {
			hasActivated = true;
			onActivate();
		}

		if (ticksInGround > getMaxTicksInGround()) {
			discard();
		}
	}

	/// Moves the model's orientation along. It tumbles end over end while flying, and eases onto its side when it lands
	/// and then rolls as it slows to a stop.
	private void updateOrientation() {
		if (!orientationReady) {
			orientation.rotationY(random.nextFloat() * Mth.TWO_PI);
			previousOrientation.set(orientation);
			orientationReady = true;
		}

		previousOrientation.set(orientation);
		previousRestBlend = restBlend;

		boolean settled = resting || stopMoving;
		restBlend = Mth.approach(restBlend, settled ? 1.0F : 0.0F, 0.2F);

		Vec3 velocity = getDeltaMovement();
		float speed = (float) velocity.length();
		Vector3f modelAxis = orientation.transform(new Vector3f(0.0F, 1.0F, 0.0F));

		if (!settled) {
			// Tumble end over end, around an axis across the direction of travel
			float horizontalLength = (float) Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
			Vector3f spinAxis = horizontalLength > 1.0E-4F
					? new Vector3f((float) velocity.z / horizontalLength, 0.0F, (float) -velocity.x / horizontalLength)
					: new Vector3f(1.0F, 0.0F, 0.0F);
			spinAxis.rotateY(spinYaw);

			float tumble = Math.min(speed * TUMBLE_PER_SPEED, MAX_TUMBLE) * spinDirection;
			orientation.premul(new Quaternionf().fromAxisAngleRad(spinAxis, tumble));

			// A little spin around its own length, so it doesn't look like it is on a rail
			orientation.rotateY(0.06F * spinDirection);
		} else {
			// Swing the model's length toward horizontal, so it lies on its side
			Vector3f onSide = new Vector3f(modelAxis.x, 0.0F, modelAxis.z);
			if (onSide.lengthSquared() < 1.0E-4F) {
				onSide.set(Mth.cos(spinYaw), 0.0F, Mth.sin(spinYaw));
			}
			onSide.normalize();

			Quaternionf swing = new Quaternionf().slerp(new Quaternionf().rotationTo(modelAxis, onSide), 0.3F);
			orientation.premul(swing);

			// Roll along the ground while it still has speed
			float horizontalSpeed = (float) Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
			float roll = Math.min(horizontalSpeed * ROLL_PER_SPEED, MAX_ROLL) * spinDirection;
			if (roll > 1.0E-3F) {
				orientation.premul(new Quaternionf().fromAxisAngleRad(orientation.transform(new Vector3f(0.0F, 1.0F, 0.0F)), roll));
			}
		}

		orientation.normalize();
	}

	/// The model's orientation, smoothed between ticks. Client only.
	public void getOrientation(float partialTick, Quaternionf dest) {
		if (!orientationReady) {
			dest.identity();
		} else {
			previousOrientation.slerp(orientation, partialTick, dest);
		}
	}

	/// @return 0 while in the air, up to 1 once it is lying on the ground. Client only.
	public float getRestBlend(float partialTick) {
		return Mth.lerp(partialTick, previousRestBlend, restBlend);
	}

	/// @return how far, in blocks, the center of the item model is from the origin the item renderer draws it around
	public float getModelCenterOffset() {
		return -0.28125F;
	}

	/// @return true if there is something solid just below to rest on
	private boolean hasSupport() {
		BlockPos below = BlockPos.containing(getX(), getY() - 0.1D, getZ());
		return !level().getBlockState(below).getCollisionShape(level(), below).isEmpty();
	}

	public void shootFromDirection(Direction direction, float velocity, float inaccuracy) {
		float x;
		float y;
		float z;

		switch (direction) {
			case EAST, WEST -> {
				x = 0;
				y = direction.toYRot();
				z = 0;
			}
			default -> {
				x = direction.toYRot();
				y = 0;
				z = 0;
			}
		}

		float newX = -Mth.sin(y * (float) (Math.PI / 180.0f)) * Mth.cos(x * (float) (Math.PI / 180.0f));
		float newY = -Mth.sin((x + z) * (float) (Math.PI / 180.0f));
		float newZ = Mth.cos(y * (float) (Math.PI / 180.0f)) * Mth.cos(x * (float) (Math.PI / 180.0f));

		this.shoot(newX, newY, newZ, velocity, inaccuracy);
	}

	@Override
	public void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);

		valueOutput.putBoolean("stopMoving", stopMoving);
		valueOutput.putBoolean("hasActivated", hasActivated);
		valueOutput.putBoolean("resting", resting);
		valueOutput.putInt("ticksInGround", ticksInGround);
	}

	@Override
	public void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);

		stopMoving = valueInput.getBooleanOr("stopMoving", false);
		hasActivated = valueInput.getBooleanOr("hasActivated", false);
		resting = valueInput.getBooleanOr("resting", false);
		ticksInGround = valueInput.getIntOr("ticksInGround", 0);
	}

	/// @return the number of ticks after being thrown that the projectile activates, or -1 to activate when it comes
	/// to rest instead
	protected int getFuseTicks() {
		return -1;
	}

	/// @return true if [#onActivate()] should run once the projectile comes to rest, which is when it has no fuse
	protected boolean activatesOnRest() {
		return getFuseTicks() < 0;
	}

	/// @return how many ticks the projectile stays in the ground before it despawns
	protected int getMaxTicksInGround() {
		return 300;
	}

	/// Called whenever activation effects should take place.
	protected void onActivate() {
	}

	/// Get the number of ticks the projectile has been in the ground. Used by IWCB.
	///
	/// @return int
	@SuppressWarnings("unused")
	public int getTicksInGround() {
		return ticksInGround;
	}
}