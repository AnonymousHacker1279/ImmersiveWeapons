package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
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

	public FlareEntity(EntityType<FlareEntity> entityType, Level level) {
		super(entityType, level);
		gravityModifier = 0.005d;
	}

	public FlareEntity(EntityType<? extends BulletEntity> entityType, LivingEntity shooter, Level level) {
		super(entityType, shooter, level);
	}

	@Override
	protected void doWhileTicking() {
		if (!isOnFire()) {
			setSecondsOnFire(300);

			entityData.set(USE_LEGACY_LIGHTING, !PluginHandler.isPluginActive("iwcompatbridge:lucent_plugin"));
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
				kill();
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
		entity.setSecondsOnFire(6);
	}

	@Override
	protected float getDefaultInertia() {
		return 0.85f;
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
			List<Integer> xPositions = new ArrayList<>(3);
			List<Integer> yPositions = new ArrayList<>(3);
			List<Integer> zPositions = new ArrayList<>(3);
			for (BlockPos pos : lightPositions) {
				xPositions.add(pos.getX());
				yPositions.add(pos.getY());
				zPositions.add(pos.getZ());
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

		int[] xPositions = pCompound.getIntArray("xPositions");
		int[] yPositions = pCompound.getIntArray("yPositions");
		int[] zPositions = pCompound.getIntArray("zPositions");

		// Each item in xPositions should match an entry in y/zPositions, so build a list of BlockPos
		// with each individual position
		int iteration = 0;
		for (Integer integer : xPositions) {
			lightPositions.add(new BlockPos(integer, yPositions[iteration], zPositions[iteration]));
			iteration++;
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(USE_LEGACY_LIGHTING, false);
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
}