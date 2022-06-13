package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.ArrayList;
import java.util.List;

public class BulletEntities {

	public static class WoodenMusketBallEntity extends BulletEntity {
		/**
		 * Constructor for WoodBulletEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public WoodenMusketBallEntity(EntityType<WoodenMusketBallEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.WOODEN_MUSKET_BALL.get();
		}

		/**
		 * Constructor for WoodBulletEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public WoodenMusketBallEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.WOODEN_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0075F * (GeneralUtilities.getRandomNumber(3.2f, 5.1f)), -0.0095F * (GeneralUtilities.getRandomNumber(3.2f, 5.1f)), random.nextGaussian() * 0.0075F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		@Override
		public double getGravityModifier() {
			return 0.055d;
		}
	}

	public static class StoneMusketBallEntity extends BulletEntity {
		/**
		 * Constructor for StoneBulletEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public StoneMusketBallEntity(EntityType<StoneMusketBallEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.STONE_MUSKET_BALL.get();
		}

		/**
		 * Constructor for StoneBulletEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public StoneMusketBallEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.STONE_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0075F * (GeneralUtilities.getRandomNumber(2.4f, 4.1f)), -0.0170F * (GeneralUtilities.getRandomNumber(2.4f, 4.1f)), random.nextGaussian() * 0.0075F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		@Override
		public double getGravityModifier() {
			return 0.075d;
		}
	}

	public static class GoldenMusketBallEntity extends BulletEntity {
		/**
		 * Constructor for GoldBulletEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public GoldenMusketBallEntity(EntityType<GoldenMusketBallEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.GOLDEN_MUSKET_BALL.get();
		}

		/**
		 * Constructor for GoldBulletEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public GoldenMusketBallEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.GOLDEN_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		@Override
		public double getGravityModifier() {
			return 0.03d;
		}
	}

	public static class CopperMusketBallEntity extends BulletEntity {

		/**
		 * Constructor for CopperBulletEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public CopperMusketBallEntity(EntityType<CopperMusketBallEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.COPPER_MUSKET_BALL.get();
		}

		/**
		 * Constructor for CopperBulletEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public CopperMusketBallEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COPPER_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		@Override
		public double getGravityModifier() {
			return 0.035d;
		}
	}

	public static class IronMusketBallEntity extends BulletEntity {
		/**
		 * Constructor for IronBulletEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public IronMusketBallEntity(EntityType<IronMusketBallEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.IRON_MUSKET_BALL.get();
		}

		/**
		 * Constructor for IronBulletEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public IronMusketBallEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.IRON_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		@Override
		public double getGravityModifier() {
			return 0.035d;
		}
	}

	public static class CobaltMusketBallEntity extends BulletEntity {
		/**
		 * Constructor for CobaltBulletEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public CobaltMusketBallEntity(EntityType<CobaltMusketBallEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.COBALT_MUSKET_BALL.get();
		}

		/**
		 * Constructor for CobaltBulletEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public CobaltMusketBallEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COBALT_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		@Override
		public double getGravityModifier() {
			return 0.035d;
		}
	}

	public static class DiamondMusketBallEntity extends BulletEntity {
		/**
		 * Constructor for DiamondBulletEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public DiamondMusketBallEntity(EntityType<DiamondMusketBallEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get();
		}

		/**
		 * Constructor for DiamondBulletEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public DiamondMusketBallEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.DIAMOND_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Get the movement modifier.
		 *
		 * @return double
		 */
		@Override
		public double getGravityModifier() {
			return 0.01d;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 0.9f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 0.9f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}
	}

	public static class NetheriteMusketBallEntity extends BulletEntity {
		/**
		 * Constructor for NetheriteBulletEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public NetheriteMusketBallEntity(EntityType<NetheriteMusketBallEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get();
		}

		/**
		 * Constructor for NetheriteBulletEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public NetheriteMusketBallEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.NETHERITE_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0020F * (GeneralUtilities.getRandomNumber(0.2f, 0.7f)), 0.0020F * (GeneralUtilities.getRandomNumber(0.2f, 0.7f)), random.nextGaussian() * 0.0020F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		/**
		 * Get the movement modifier.
		 *
		 * @return double
		 */
		@Override
		public double getGravityModifier() {
			return 0.005d;
		}
	}

	public static class FlareEntity extends BulletEntity implements ItemSupplier {

		private int explodeDelay = 60;
		private int deathDelay = 600;
		private BlockPos previousLightPosition = BlockPos.ZERO;
		private final List<BlockPos> lightPositions = new ArrayList<>(3);
		private boolean hasHitEntity = false;
		static final BlockState lightState = Blocks.LIGHT.defaultBlockState();
		static final BlockState airState = Blocks.AIR.defaultBlockState();

		/**
		 * Constructor for FlareEntity.
		 *
		 * @param entityType        the <code>EntityType</code> instance
		 * @param world             the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public FlareEntity(EntityType<FlareEntity> entityType, Level world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.FLARE.get();
		}

		/**
		 * Constructor for FlareEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public FlareEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.FLARE_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 *
		 * @param x          the X position
		 * @param y          the Y position
		 * @param z          the Z position
		 * @param velocity   the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
			setSecondsOnFire(30);
		}

		@Override
		protected void doWhileTicking() {
			double x = getX();
			double y = getY();
			double z = getZ();
			Vec3 deltaMovement = getDeltaMovement();

			if (level.isClientSide && explodeDelay != 0 && (tickCount % 4) >= 2) {
				level.addParticle(ParticleTypes.FIREWORK, x, y - 0.3D, z, random.nextGaussian() * 0.05D, -deltaMovement.y * 0.5D, random.nextGaussian() * 0.05D);
			}

			shouldStopMoving = false;
			if (explodeDelay >= 0) {
				explodeDelay--;
			} else {
				if (deathDelay >= 0) {
					shouldStopMoving = true;
					if (level.isClientSide && (tickCount % 4) == 0) {
						for (int i = 8; --i >= 0; ) {
							level.addAlwaysVisibleParticle(ParticleTypes.FLAME, true, x, y, z, random.nextGaussian() * 0.1D, -deltaMovement.y * 0.25D, random.nextGaussian() * 0.1D);
						}
					}
					deathDelay--;
				} else {
					// Remove all lights before dying
					if (!lightPositions.isEmpty()) {
						for (BlockPos pos : lightPositions) {
							if (level.getBlockState(pos) == lightState) {
								level.removeBlock(pos, false);
							}
						}
						lightPositions.clear();
					}
					kill();
				}
			}

			if (tickCount % 4 >= 1) {
				BlockPos currentPosition = blockPosition();
				if (!level.isClientSide && currentPosition != previousLightPosition) {
					if (!lightPositions.isEmpty()) {
						for (BlockPos pos : lightPositions) {
							if (level.getBlockState(pos) == lightState) {
								level.removeBlock(pos, false);
							}
						}
						lightPositions.clear();
					}
					if (!hasHitEntity && level.getBlockState(currentPosition) == airState) {
						level.setBlock(currentPosition, lightState, 3);
						lightPositions.add(currentPosition);
					}
					previousLightPosition = currentPosition;
				}
			}
		}

		/**
		 * Runs when an entity is hit.
		 *
		 * @param entity          the <code>Entity</code> being hit
		 * @param entityHitResult the <code>EntityHitResult</code> of the entity being hit
		 */
		@Override
		protected void doWhenHitEntity(Entity entity, EntityHitResult entityHitResult) {
			hasHitEntity = true;
			entity.setSecondsOnFire(6);
		}

		/**
		 * Get the movement modifier.
		 *
		 * @return double
		 */
		@Override
		public double getGravityModifier() {
			return 0.005d;
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
		public @NotNull ItemStack getItem() {
			return new ItemStack(DeferredRegistryHandler.FLARE.get());
		}

		/**
		 * Add additional save data.
		 *
		 * @param pCompound the <code>CompoundTag</code> containing the save data
		 */
		@Override
		public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
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
		public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
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

		/**
		 * Remove all lights when the entity is killed via commands.
		 */
		@Override
		public void kill() {
			super.kill();

			if (!lightPositions.isEmpty()) {
				for (BlockPos pos : lightPositions) {
					if (level.getBlockState(pos) == lightState) {
						level.removeBlock(pos, false);
					}
				}
				lightPositions.clear();
			}
		}
	}
}