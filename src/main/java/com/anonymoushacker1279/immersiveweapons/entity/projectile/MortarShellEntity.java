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

	public MortarShellEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	private MortarShellEntity(World world, BlockPos source, double yOffset) {
		this(DeferredRegistryHandler.MORTAR_SHELL_ENTITY.get(), world);
		setPos(source.getX() + 0.5D, source.getY() + yOffset, source.getZ() + 0.5D);
	}

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

	@Override
	public void tick() {
		super.tick();
		Vector3d deltaMovement = this.getDeltaMovement();

		// Check for collisions
		Vector3d currentPosition = this.position();
		Vector3d currentPositionPlusMovement = currentPosition.add(deltaMovement);
		RayTraceResult rayTraceResult = this.level.clip(new RayTraceContext(currentPosition, currentPositionPlusMovement, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
		if (rayTraceResult.getType() != RayTraceResult.Type.MISS) {
			currentPositionPlusMovement = rayTraceResult.getLocation();
		}

		while (this.isAlive()) {
			EntityRayTraceResult entityRayTraceResult = ProjectileHelper.getEntityHitResult(this.level, this, currentPosition, currentPositionPlusMovement, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
			if (entityRayTraceResult != null) {
				rayTraceResult = entityRayTraceResult;
			}

			if (rayTraceResult != null && rayTraceResult.getType() != RayTraceResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, rayTraceResult)) {
				this.onHit(rayTraceResult);
				this.hasImpulse = true;
			}

			if (entityRayTraceResult == null) {
				break;
			}

			rayTraceResult = null;
		}

		deltaMovement = this.getDeltaMovement();
		double deltaMovementX = deltaMovement.x;
		double deltaMovementY = deltaMovement.y;
		double deltaMovementZ = deltaMovement.z;
		double deltaMovementXPlusPosition = this.getX() + deltaMovementX;
		double deltaMovementYPlusPosition = this.getY() + deltaMovementY;
		double deltaMovementZPlusPosition = this.getZ() + deltaMovementZ;

		float inertia = 0.99F;
		if (this.isInWater()) {
			for (int j = 0; j < 4; ++j) {
				this.level.addParticle(ParticleTypes.BUBBLE, deltaMovementXPlusPosition - deltaMovementX * 0.25D, deltaMovementYPlusPosition - deltaMovementY * 0.25D, deltaMovementZPlusPosition - deltaMovementZ * 0.25D, deltaMovementX, deltaMovementY, deltaMovementZ);
			}
			inertia = 0.6f;
		}
		this.setDeltaMovement(deltaMovement.scale(inertia));
		if (!this.isNoGravity()) {
			Vector3d newDeltaMovement = this.getDeltaMovement();
			this.setDeltaMovement(newDeltaMovement.x, newDeltaMovement.y - 0.0355f, newDeltaMovement.z);
		}

		this.setPos(deltaMovementXPlusPosition, deltaMovementYPlusPosition, deltaMovementZPlusPosition);
		this.checkInsideBlocks();
	}

	protected void onHit(RayTraceResult rayTraceResult) {
		Vector3d vector3d = rayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
		this.setDeltaMovement(vector3d);
		Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
		this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
		if (!this.level.isClientSide) {
			this.level.explode(this.getEntity(), damageSource, null, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ(), 4.0F, false, Mode.BREAK);
		}
		this.kill();
	}

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

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(DeferredRegistryHandler.MORTAR_SHELL.get());
	}
}