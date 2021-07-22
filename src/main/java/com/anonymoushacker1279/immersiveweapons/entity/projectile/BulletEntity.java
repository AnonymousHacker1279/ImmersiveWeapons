package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class BulletEntity {

	public static class CopperBulletEntity extends AbstractBulletEntity {

		/**
		 * Constructor for CopperBulletEntity.
		 * @param entityType the <code>EntityType</code> instance
		 * @param world the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public CopperBulletEntity(EntityType<CopperBulletEntity> entityType, World world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.COPPER_MUSKET_BALL.get();
		}

		/**
		 * Constructor for CopperBulletEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public CopperBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COPPER_BULLET_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 * @param velocity the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.0355d;
		}
	}

	public static class WoodBulletEntity extends AbstractBulletEntity {
		/**
		 * Constructor for WoodBulletEntity.
		 * @param entityType the <code>EntityType</code> instance
		 * @param world the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public WoodBulletEntity(EntityType<WoodBulletEntity> entityType, World world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.WOOD_MUSKET_BALL.get();
		}

		/**
		 * Constructor for WoodBulletEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public WoodBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.WOOD_BULLET_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 * @param velocity the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0075F * (GeneralUtilities.getRandomNumber(3.2f, 5.1f)), -0.0095F * (GeneralUtilities.getRandomNumber(3.2f, 5.1f)), random.nextGaussian() * 0.0075F).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.035d;
		}
	}

	public static class StoneBulletEntity extends AbstractBulletEntity {
		/**
		 * Constructor for StoneBulletEntity.
		 * @param entityType the <code>EntityType</code> instance
		 * @param world the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public StoneBulletEntity(EntityType<StoneBulletEntity> entityType, World world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.STONE_MUSKET_BALL.get();
		}

		/**
		 * Constructor for StoneBulletEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public StoneBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.STONE_BULLET_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 * @param velocity the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0075F * (GeneralUtilities.getRandomNumber(2.4f, 4.1f)), -0.0170F * (GeneralUtilities.getRandomNumber(2.4f, 4.1f)), random.nextGaussian() * 0.0075F).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.02d;
		}
	}

	public static class IronBulletEntity extends AbstractBulletEntity {
		/**
		 * Constructor for IronBulletEntity.
		 * @param entityType the <code>EntityType</code> instance
		 * @param world the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public IronBulletEntity(EntityType<IronBulletEntity> entityType, World world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.IRON_MUSKET_BALL.get();
		}

		/**
		 * Constructor for IronBulletEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public IronBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.IRON_BULLET_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 * @param velocity the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.0355d;
		}
	}

	public static class GoldBulletEntity extends AbstractBulletEntity {
		/**
		 * Constructor for GoldBulletEntity.
		 * @param entityType the <code>EntityType</code> instance
		 * @param world the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public GoldBulletEntity(EntityType<GoldBulletEntity> entityType, World world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.GOLD_MUSKET_BALL.get();
		}

		/**
		 * Constructor for GoldBulletEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public GoldBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.GOLD_BULLET_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 * @param velocity the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.0355d;
		}
	}

	public static class DiamondBulletEntity extends AbstractBulletEntity {
		/**
		 * Constructor for DiamondBulletEntity.
		 * @param entityType the <code>EntityType</code> instance
		 * @param world the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public DiamondBulletEntity(EntityType<DiamondBulletEntity> entityType, World world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get();
		}

		/**
		 * Constructor for DiamondBulletEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public DiamondBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.DIAMOND_BULLET_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.0385d;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 * @param velocity the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 0.9f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 0.9f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}
	}

	public static class NetheriteBulletEntity extends AbstractBulletEntity {
		/**
		 * Constructor for NetheriteBulletEntity.
		 * @param entityType the <code>EntityType</code> instance
		 * @param world the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public NetheriteBulletEntity(EntityType<NetheriteBulletEntity> entityType, World world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get();
		}

		/**
		 * Constructor for NetheriteBulletEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public NetheriteBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.NETHERITE_BULLET_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 * @param velocity the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0020F * (GeneralUtilities.getRandomNumber(0.2f, 0.7f)), 0.0020F * (GeneralUtilities.getRandomNumber(0.2f, 0.7f)), random.nextGaussian() * 0.0020F).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.04d;
		}
	}

	@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
	public static class FlareEntity extends AbstractBulletEntity implements IRendersAsItem {

		private int explodeDelay = 10;
		private int deathDelay = 300;

		/**
		 * Constructor for FlareEntity.
		 * @param entityType the <code>EntityType</code> instance
		 * @param world the <code>World</code> the entity is in
		 * @param knockbackStrength the bullet knockback strength
		 */
		public FlareEntity(EntityType<FlareEntity> entityType, World world, int knockbackStrength) {
			super(entityType, world);
			this.knockbackStrength = knockbackStrength;
			referenceItem = DeferredRegistryHandler.FLARE.get();
		}

		/**
		 * Constructor for FlareEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public FlareEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.FLARE_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Fire the entity from a position with a velocity and inaccuracy.
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 * @param velocity the velocity
		 * @param inaccuracy the inaccuracy modifier
		 */
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		@Override
		protected void doWhileTicking() {
			if (level.isClientSide && explodeDelay != 0) {
				level.addParticle(ParticleTypes.FIREWORK, getX(), getY() - 0.3D, getZ(), random.nextGaussian() * 0.05D, -getDeltaMovement().y * 0.5D, random.nextGaussian() * 0.05D);
			}

			shouldStopMoving = false;
			if (explodeDelay >= 0) {
				explodeDelay--;
			} else {
				if (deathDelay >= 0) {
					shouldStopMoving = true;
					if (level.isClientSide) {
						for (int i = 0; i <= 8; i++) {
							level.addAlwaysVisibleParticle(ParticleTypes.FLAME, true, getX(), getY(), getZ(), random.nextGaussian() * 0.1D, -getDeltaMovement().y * 0.25D, random.nextGaussian() * 0.1D);
							level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, true, getX(), getY(), getZ(), random.nextGaussian() * 0.1D, -getDeltaMovement().y * 0.25D, random.nextGaussian() * 0.1D);
						}
					}
					deathDelay--;
				} else {
					remove();
				}
			}
		}

		/**
		 * Runs when an entity is hit.
		 * @param entity the <code>Entity</code> being hit
		 */
		@Override
		protected void doWhenHitEntity(Entity entity) {
			entity.setSecondsOnFire(6);
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.0355d;
		}

		/**
		 * Get the item associated with this entity.
		 * @return ItemStack
		 */
		@Override
		public @NotNull ItemStack getItem() {
			return new ItemStack(DeferredRegistryHandler.FLARE.get());
		}
	}
}