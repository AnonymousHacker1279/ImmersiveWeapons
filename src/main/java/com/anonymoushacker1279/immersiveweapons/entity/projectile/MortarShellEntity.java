package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.block.MortarBlock;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class MortarShellEntity extends Entity implements IRendersAsItem {

	private static final DamageSource damageSource = new DamageSource("immersiveweapons.mortar");

	/**
	 * Constructor for MortarShellEntity.
	 * @param entityType the <code>EntityType</code> instance; must extend MolotovEntity
	 * @param world the <code>World</code> the entity is in
	 */
	public MortarShellEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * Constructor for MortarShellEntity.
	 * @param world the <code>World</code> the entity is in
	 * @param pos the <code>BlockPos</code> the entity should spawn at
	 * @param yOffset the Y offset to spawn at
	 */
	private MortarShellEntity(World world, BlockPos pos, double yOffset) {
		this(DeferredRegistryHandler.MORTAR_SHELL_ENTITY.get(), world);
		setPos(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
	}

	/**
	 * Create the entity and set its initial movement.
	 * @param world the <code>World</code> the entity is in
	 * @param pos the <code>BlockPos</code> the entity is at
	 * @param yOffset the Y offset to spawn at
	 * @return ActionResultType
	 */
	public static ActionResultType create(World world, BlockPos pos, double yOffset, BlockState state) {
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
		return ActionResultType.CONSUME;
	}

	/**
	 * Runs once every tick.
	 */
	@Override
	public void tick() {
		super.tick();
		Vector3d deltaMovement = getDeltaMovement();

		// Check for collisions
		Vector3d currentPosition = position();
		Vector3d currentPositionPlusMovement = currentPosition.add(deltaMovement);
		RayTraceResult rayTraceResult = level.clip(new RayTraceContext(currentPosition, currentPositionPlusMovement, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
		if (rayTraceResult.getType() != RayTraceResult.Type.MISS) {
			currentPositionPlusMovement = rayTraceResult.getLocation();
		}

		while (isAlive()) {
			EntityRayTraceResult entityRayTraceResult = ProjectileHelper.getEntityHitResult(level, this, currentPosition, currentPositionPlusMovement, getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0D), this::canHitEntity);
			if (entityRayTraceResult != null) {
				rayTraceResult = entityRayTraceResult;
			}

			if (rayTraceResult != null && rayTraceResult.getType() != RayTraceResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, rayTraceResult)) {
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
			Vector3d newDeltaMovement = getDeltaMovement();
			setDeltaMovement(newDeltaMovement.x, newDeltaMovement.y - 0.0355f, newDeltaMovement.z);
		}

		setPos(deltaMovementXPlusPosition, deltaMovementYPlusPosition, deltaMovementZPlusPosition);
		checkInsideBlocks();
	}

	/**
	 * Runs when an entity/block is hit.
	 * @param rayTraceResult the <code>RayTraceResult</code> instance
	 */
	private void onHit(RayTraceResult rayTraceResult) {
		Vector3d vector3d = rayTraceResult.getLocation().subtract(getX(), getY(), getZ());
		setDeltaMovement(vector3d);
		Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
		setPosRaw(getX() - vector3d1.x, getY() - vector3d1.y, getZ() - vector3d1.z);
		if (!level.isClientSide) {
			level.explode(getEntity(), damageSource, null, blockPosition().getX(), blockPosition().getY(), blockPosition().getZ(), 4.0F, false, Mode.BREAK);
		}
		kill();
	}

	/**
	 * Determines if an entity can be hit.
	 * @param entity the <code>Entity</code> to check
	 * @return boolean
	 */
	private boolean canHitEntity(Entity entity) {
		return true;
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT nbt) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
	}

	/**
	 * Get the entity spawn packet.
	 * @return IPacket
	 */
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/**
	 * Get the reference item.
	 * @return ItemStack
	 */
	@Override
	public ItemStack getItem() {
		return new ItemStack(DeferredRegistryHandler.MORTAR_SHELL.get());
	}
}