package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class FlareEntity extends BulletEntity implements ItemSupplier {

	private int explodeDelay = 60;
	private int deathDelay = 600;
	private BlockPos previousLightPosition = BlockPos.ZERO;
	private final List<BlockPos> lightPositions = new ArrayList<>(10);
	private boolean hasHitEntity = false;
	static final BlockState lightState = Blocks.LIGHT.defaultBlockState();
	static final BlockState airState = Blocks.AIR.defaultBlockState();
	private static final EntityDataAccessor<Boolean> USE_LEGACY_LIGHTING = SynchedEntityData.defineId(FlareEntity.class,
			EntityDataSerializers.BOOLEAN);

	public FlareEntity(EntityType<? extends Arrow> entityType, Level level) {
		super(entityType, level);
	}

	public FlareEntity(EntityType<? extends BulletEntity> entityType, LivingEntity shooter, Level level, @Nullable ItemStack firedFromWeapon) {
		super(entityType, shooter, level, firedFromWeapon);
	}

	@Override
	protected void doWhileTicking() {
		if (!isOnFire()) {
			igniteForSeconds(300);

			entityData.set(USE_LEGACY_LIGHTING, !PluginHandler.isPluginActive("iwcompatbridge:ryoamiclights_plugin"));
		}

		double x = getX();
		double y = getY();
		double z = getZ();
		Vec3 deltaMovement = getDeltaMovement();

		if (level().isClientSide && explodeDelay != 0 && (tickCount % 4) >= 2) {
			level().addParticle(ParticleTypes.FIREWORK, x, y - 0.3D, z, random.nextGaussian() * 0.05D, -deltaMovement.y * 0.5D, random.nextGaussian() * 0.05D);
		}

		shouldStopMoving = false;
		if (explodeDelay >= 0) {
			explodeDelay--;
		} else {
			if (deathDelay >= 0) {
				shouldStopMoving = true;
				if (level().isClientSide && (tickCount % 4) == 0) {
					for (int i = 8; --i >= 0; ) {
						level().addAlwaysVisibleParticle(ParticleTypes.FLAME, true, x, y, z, random.nextGaussian() * 0.1D, -deltaMovement.y * 0.25D, random.nextGaussian() * 0.1D);
					}
				}
				deathDelay--;
			} else {
				// Remove all lights before dying
				if (!lightPositions.isEmpty()) {
					for (BlockPos pos : lightPositions) {
						if (level().getBlockState(pos) == lightState) {
							level().removeBlock(pos, false);
						}
					}
					lightPositions.clear();
				}

				discard();
			}
		}

		// The area lighting effect used here places light blocks at the entity's location. This is inefficient but the default behavior.
		// If the Lucent plugin is available, it will handle dynamic lighting instead.
		if (entityData.get(USE_LEGACY_LIGHTING)) {
			if (tickCount % 2 == 0) {
				BlockPos currentPosition = blockPosition();
				if (!level().isClientSide && currentPosition != previousLightPosition) {
					if (!lightPositions.isEmpty()) {
						for (BlockPos pos : lightPositions) {
							if (level().getBlockState(pos) == lightState) {
								level().removeBlock(pos, false);
							}
						}
						lightPositions.clear();
					}

					if (!hasHitEntity && level().getBlockState(currentPosition) == airState) {
						level().setBlock(currentPosition, lightState, 3);
						lightPositions.add(currentPosition);
					}

					previousLightPosition = currentPosition;
				}
			}
		}

		// Aggro nearby minutemen to attack the nearest monster
		if (shouldStopMoving && level() instanceof ServerLevel serverLevel && tickCount % 4 == 0) {
			aggroNearbyMinutemen(serverLevel);
		}
	}

	/**
	 * Runs when an entity is hit.
	 *
	 * @param entity the <code>Entity</code> being hit
	 */
	@Override
	protected void doWhenHitEntity(Entity entity) {
		super.doWhenHitEntity(entity);
		hasHitEntity = true;
		entity.igniteForSeconds(6);

		if (entity.level() instanceof ServerLevel serverLevel) {
			aggroNearbyMinutemen(serverLevel);
		}
	}

	/**
	 * Get the item associated with this entity.
	 *
	 * @return ItemStack
	 */
	@Override
	public ItemStack getItem() {
		return new ItemStack(ItemRegistry.FLARE.get());
	}

	/**
	 * Add additional save data.
	 *
	 * @param pCompound the <code>CompoundTag</code> containing the save data
	 */
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);

		if (!lightPositions.isEmpty()) {
			int[] xPositions = new int[lightPositions.size()];
			int[] yPositions = new int[lightPositions.size()];
			int[] zPositions = new int[lightPositions.size()];
			for (BlockPos pos : lightPositions) {
				xPositions[lightPositions.indexOf(pos)] = pos.getX();
				yPositions[lightPositions.indexOf(pos)] = pos.getY();
				zPositions[lightPositions.indexOf(pos)] = pos.getZ();
			}

			pCompound.putIntArray("xPositions", xPositions);
			pCompound.putIntArray("yPositions", yPositions);
			pCompound.putIntArray("zPositions", zPositions);
		}
	}

	/**
	 * Read additional save data.
	 *
	 * @param pCompound the <code>CompoundTag</code> containing the save data
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);

		int[] xPositions = pCompound.getIntArray("xPositions").orElse(null);
		int[] yPositions = pCompound.getIntArray("yPositions").orElse(null);
		int[] zPositions = pCompound.getIntArray("zPositions").orElse(null);

		// Each item in xPositions should match an entry in y/zPositions, so build a list of BlockPos
		// with each individual position
		if (xPositions != null && yPositions != null && zPositions != null) {
			for (int i = 0; i < xPositions.length; i++) {
				BlockPos pos = new BlockPos(xPositions[i], yPositions[i], zPositions[i]);
				lightPositions.add(pos);
			}
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(USE_LEGACY_LIGHTING, false);
	}

	@Override
	public void remove(RemovalReason removalReason) {
		super.remove(removalReason);

		// Remove all lights before dying
		if (!lightPositions.isEmpty()) {
			for (BlockPos pos : lightPositions) {
				if (level().getBlockState(pos) == lightState) {
					level().removeBlock(pos, false);
				}
			}

			lightPositions.clear();
		}
	}

	private void aggroNearbyMinutemen(ServerLevel serverLevel) {
		Monster monster = serverLevel.getNearestEntity(Monster.class, TargetingConditions.forCombat(), null, getX(), getY(), getZ(), getBoundingBox().inflate(7));
		MinutemanEntity minuteman = serverLevel.getNearestEntity(MinutemanEntity.class, TargetingConditions.forNonCombat(), null, getX(), getY(), getZ(), getBoundingBox().inflate(16));

		if (monster != null && minuteman != null && getOwner() instanceof LivingEntity owner) {
			if (!minuteman.isAngryAt(owner, serverLevel)) {
				minuteman.aggroNearbyMinutemen(getBoundingBox(), monster);
			}
		}
	}
}