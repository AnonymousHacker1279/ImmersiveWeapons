package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity.SmokeGrenadeEntityPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public class CustomArrowEntity extends AbstractArrow implements HitEffectUtils {

	public Item referenceItem = Items.AIR;
	public double gravityModifier = 1.0d;
	public List<Double> shootingVectorInputs = List.of(0.0075d, -0.0095d, 0.0075d);
	public HitEffect hitEffect = HitEffect.NONE;
	public int color = -1;

	public static final EntityDataAccessor<Float> GRAVITY_MODIFIER_ACCESSOR = SynchedEntityData.defineId(CustomArrowEntity.class,
			EntityDataSerializers.FLOAT);

	public CustomArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public CustomArrowEntity(EntityType<? extends CustomArrowEntity> type, LivingEntity shooter, Level level) {
		super(type, shooter, level);
	}

	public static class ArrowEntityBuilder implements HitEffectUtils {

		private final EntityType<? extends AbstractArrow> entityType;
		private final Item referenceItem;

		public ArrowEntityBuilder(EntityType<? extends AbstractArrow> entityType, Item referenceItem) {
			this.entityType = entityType;
			this.referenceItem = referenceItem;
		}

		public CustomArrowEntity build(Level level) {
			CustomArrowEntity arrowEntity = new CustomArrowEntity(entityType, level);
			arrowEntity.referenceItem = referenceItem;

			return arrowEntity;
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(referenceItem);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(GRAVITY_MODIFIER_ACCESSOR, 1.0f);
	}

	@Override
	public void tick() {
		// TODO: investigate super here and optimize
		super.tick();

		if (gravityModifier == 1.0d) {  // TODO: would be better to use !hasBeenShot but it will always be true here, investigate alternatives
			if (level().isClientSide) {
				gravityModifier = entityData.get(GRAVITY_MODIFIER_ACCESSOR);
			}
		}

		boolean flag = isNoPhysics();
		Vec3 vector3d = getDeltaMovement();
		float yRot;
		float xRot;
		if (xRotO == 0.0F && yRotO == 0.0F) {
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		BlockPos blockPos = blockPosition();
		BlockState blockState = level().getBlockState(blockPos);
		if (!blockState.isAir() && !flag) {
			VoxelShape voxelShape = blockState.getCollisionShape(level(), blockPos);
			if (!voxelShape.isEmpty()) {
				Vec3 vector3d1 = position();

				for (AABB aabb : voxelShape.toAabbs()) {
					if (aabb.move(blockPos).contains(vector3d1)) {
						inGround = true;
						break;
					}
				}
			}
		}

		if (shakeTime > 0) {
			--shakeTime;
		}

		if (isInWaterOrRain()) {
			clearFire();
		}

		if (inGround && !flag) {
			if (shouldFall()) {
				startFalling();
			} else if (!level().isClientSide) {
				tickDespawn();
			}

			++inGroundTime;
		} else {
			inGroundTime = 0;
			Vec3 vector3d2 = position();
			Vec3 vector3d3 = vector3d2.add(vector3d);
			HitResult rayTraceResult = level().clip(new ClipContext(vector3d2, vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (rayTraceResult.getType() != HitResult.Type.MISS) {
				vector3d3 = rayTraceResult.getLocation();
			}

			while (isAlive()) {
				EntityHitResult entityRayTraceResult = findHitEntity(vector3d2, vector3d3);
				if (entityRayTraceResult != null) {
					rayTraceResult = entityRayTraceResult;
				}

				if (rayTraceResult != null && rayTraceResult.getType() == HitResult.Type.ENTITY) {
					Entity entity = null;
					if (rayTraceResult instanceof EntityHitResult) {
						entity = ((EntityHitResult) rayTraceResult).getEntity();
					}
					Entity entity1 = getOwner();
					if (entity instanceof Player && entity1 instanceof Player && !((Player) entity1).canHarmPlayer((Player) entity)) {
						rayTraceResult = null;
						entityRayTraceResult = null;
					}
				}

				if (rayTraceResult != null && rayTraceResult.getType() != HitResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, rayTraceResult)) {
					onHit(rayTraceResult);
					hasImpulse = true;
				}

				if (entityRayTraceResult == null || getPierceLevel() <= 0) {
					break;
				}

				rayTraceResult = null;
			}

			vector3d = getDeltaMovement();
			double d3 = vector3d.x;
			double d4 = vector3d.y;
			double d0 = vector3d.z;
			if (isCritArrow()) {
				for (int i = 0; i < 4; ++i) {
					level().addParticle(ParticleTypes.CRIT, getX() + d3 * i / 4.0D, getY() + d4 * i / 4.0D, getZ() + d0 * i / 4.0D, -d3, -d4 + 0.2D, -d0);
				}
			}

			double d5 = getX() + d3;
			double d1 = getY() + d4;
			double d2 = getZ() + d0;

			float f2 = 0.99F;
			if (isInWater()) {
				for (int j = 0; j < 4; ++j) {
					level().addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
				}

				f2 = getWaterInertia();
			}

			setDeltaMovement(vector3d.scale(f2));
			if (!isNoGravity() && !flag) {
				Vec3 vector3d4 = getDeltaMovement();
				setDeltaMovement(vector3d4.x, vector3d4.y - 0.05d + gravityModifier, vector3d4.z);
			}

			setPos(d5, d1, d2);
			checkInsideBlocks();
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		if (color != -1 && !level().isClientSide) {
			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level().getChunkAt(blockPosition())),
					new SmokeGrenadeEntityPacketHandler(getX(), getY(), getZ(), color));
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);

		// Extra code to run when an entity is hit
		doWhenHitEntity(result.getEntity());

		if (result.getEntity() instanceof LivingEntity livingEntity) {
			// Apply any hit effects from the bullet
			switch (hitEffect) {
				case MOLTEN -> addMoltenEffects(livingEntity);
				case TESLA -> addTeslaEffects(livingEntity);
				case VENTUS -> addVentusEffects(livingEntity);
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		Vec3 shootingVector = new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))),
						random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))),
						random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))))
				.scale(velocity);

		setDeltaMovement(shootingVector);
		double horizontalDistanceSqr = shootingVector.horizontalDistanceSqr();
		float yRot = (float) (Mth.atan2(shootingVector.x, shootingVector.z) * (180F / (float) Math.PI));
		float xRot = (float) (Mth.atan2(shootingVector.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
		yRotO = yRot;
		xRotO = xRot;
	}

	/**
	 * Additional stuff to do when an entity is hit.
	 *
	 * @param entity the <code>Entity</code> being hit
	 */
	protected void doWhenHitEntity(Entity entity) {
	}
}