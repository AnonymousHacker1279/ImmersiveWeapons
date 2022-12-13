package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import tech.anonymoushacker1279.immersiveweapons.block.MortarBlock;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class MortarShellEntity extends Projectile implements ItemSupplier {

	private static final DamageSource damageSource = new DamageSource("immersiveweapons.mortar");

	/**
	 * Constructor for MortarShellEntity.
	 *
	 * @param entityType the <code>EntityType</code> instance; must extend MortarShellEntity
	 * @param world      the <code>World</code> the entity is in
	 */
	public MortarShellEntity(EntityType<? extends Projectile> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Constructor for MortarShellEntity.
	 *
	 * @param world   the <code>World</code> the entity is in
	 * @param pos     the <code>BlockPos</code> the entity should spawn at
	 * @param yOffset the Y offset to spawn at
	 */
	private MortarShellEntity(Level world, BlockPos pos, double yOffset) {
		this(EntityRegistry.MORTAR_SHELL_ENTITY.get(), world);
		setPos(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
	}

	/**
	 * Create the entity and set its initial movement.
	 *
	 * @param world   the <code>World</code> the entity is in
	 * @param pos     the <code>BlockPos</code> the entity is at
	 * @param yOffset the Y offset to spawn at
	 */
	public static void create(Level world, BlockPos pos, double yOffset, BlockState state) {
		if (!world.isClientSide) {
			MortarShellEntity mortarShellEntity = new MortarShellEntity(world, pos, yOffset);

			float rotationModifier = 0.0f;
			if (state.getValue(MortarBlock.ROTATION) == 0) {
				rotationModifier = 1.0f;
			} else if (state.getValue(MortarBlock.ROTATION) == 1) {
				rotationModifier = 0.75f;
			} else if (state.getValue(MortarBlock.ROTATION) == 2) {
				rotationModifier = 0.5f;
			}

			if (state.getValue(MortarBlock.FACING) == Direction.NORTH) {
				mortarShellEntity.setDeltaMovement(0.0f, rotationModifier, 1.25f);
			} else if (state.getValue(MortarBlock.FACING) == Direction.SOUTH) {
				mortarShellEntity.setDeltaMovement(0.0f, rotationModifier, -1.25f);
			} else if (state.getValue(MortarBlock.FACING) == Direction.EAST) {
				mortarShellEntity.setDeltaMovement(-1.25f, rotationModifier, 0.0f);
			} else if (state.getValue(MortarBlock.FACING) == Direction.WEST) {
				mortarShellEntity.setDeltaMovement(1.25f, rotationModifier, 0.0f);
			}
			world.addFreshEntity(mortarShellEntity);
		}
	}

	/**
	 * Runs once every tick.
	 */
	@Override
	public void tick() {
		super.tick();
		Vec3 deltaMovement = getDeltaMovement();

		// Check for collisions
		Vec3 currentPosition = position();
		Vec3 currentPositionPlusMovement = currentPosition.add(deltaMovement);
		HitResult rayTraceResult = level.clip(new ClipContext(currentPosition, currentPositionPlusMovement, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		if (rayTraceResult.getType() != HitResult.Type.MISS) {
			currentPositionPlusMovement = rayTraceResult.getLocation();
		}

		while (isAlive()) {
			EntityHitResult entityRayTraceResult = ProjectileUtil.getEntityHitResult(level, this, currentPosition, currentPositionPlusMovement, getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0D), this::canHitEntity);
			if (entityRayTraceResult != null) {
				rayTraceResult = entityRayTraceResult;
			}

			if (rayTraceResult != null && rayTraceResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, rayTraceResult)) {
				onHit(rayTraceResult);
				hasImpulse = true;
			}

			if (entityRayTraceResult == null) {
				break;
			}

			rayTraceResult = null;
		}

		deltaMovement = getDeltaMovement();
		double deltaMovementX = deltaMovement.x;
		double deltaMovementY = deltaMovement.y;
		double deltaMovementZ = deltaMovement.z;
		double deltaMovementXPlusPosition = getX() + deltaMovementX;
		double deltaMovementYPlusPosition = getY() + deltaMovementY;
		double deltaMovementZPlusPosition = getZ() + deltaMovementZ;

		float inertia = 0.99F;
		if (isInWater()) {
			for (int j = 0; j < 4; ++j) {
				level.addParticle(ParticleTypes.BUBBLE, deltaMovementXPlusPosition - deltaMovementX * 0.25D, deltaMovementYPlusPosition - deltaMovementY * 0.25D, deltaMovementZPlusPosition - deltaMovementZ * 0.25D, deltaMovementX, deltaMovementY, deltaMovementZ);
			}
			inertia = 0.6f;
		}
		setDeltaMovement(deltaMovement.scale(inertia));
		if (!isNoGravity()) {
			Vec3 newDeltaMovement = getDeltaMovement();
			setDeltaMovement(newDeltaMovement.x, newDeltaMovement.y - 0.0355f, newDeltaMovement.z);
		}

		setPos(deltaMovementXPlusPosition, deltaMovementYPlusPosition, deltaMovementZPlusPosition);
		checkInsideBlocks();
	}

	/**
	 * Runs when an entity/block is hit.
	 *
	 * @param rayTraceResult the <code>RayTraceResult</code> instance
	 */
	@Override
	protected void onHit(HitResult rayTraceResult) {
		Vec3 vector3d = rayTraceResult.getLocation().subtract(getX(), getY(), getZ());
		setDeltaMovement(vector3d);
		Vec3 vector3d1 = vector3d.normalize().scale(0.05F);
		setPosRaw(getX() - vector3d1.x, getY() - vector3d1.y, getZ() - vector3d1.z);
		if (!level.isClientSide) {
			level.explode(this, damageSource, null, blockPosition().getX(), blockPosition().getY(), blockPosition().getZ(), 4.0F, false, ExplosionInteraction.BLOCK);
		}
		kill();
	}

	/**
	 * Determines if an entity can be hit.
	 *
	 * @param entity the <code>Entity</code> to check
	 * @return boolean
	 */
	@Override
	protected boolean canHitEntity(Entity entity) {
		return true;
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
	}

	/**
	 * Get the entity spawn packet.
	 *
	 * @return IPacket
	 */
	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	/**
	 * Get the reference item.
	 *
	 * @return ItemStack
	 */
	@Override
	public ItemStack getItem() {
		return new ItemStack(ItemRegistry.MORTAR_SHELL.get());
	}
}