package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

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
			referenceItem = ItemRegistry.WOODEN_MUSKET_BALL.get();
		}

		/**
		 * Constructor for WoodBulletEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public WoodenMusketBallEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.WOODEN_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.WOODEN_MUSKET_BALL.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0075F * (GeneralUtilities.getRandomNumber(3.2f, 5.1f)),
							-0.0095F * (GeneralUtilities.getRandomNumber(3.2f, 5.1f)),
							random.nextGaussian() * 0.0075F).scale(velocity);
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
			referenceItem = ItemRegistry.STONE_MUSKET_BALL.get();
		}

		/**
		 * Constructor for StoneBulletEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public StoneMusketBallEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.STONE_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.STONE_MUSKET_BALL.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0075F * (GeneralUtilities.getRandomNumber(2.4f, 4.1f)),
							-0.0170F * (GeneralUtilities.getRandomNumber(2.4f, 4.1f)),
							random.nextGaussian() * 0.0075F).scale(velocity);
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
			referenceItem = ItemRegistry.GOLDEN_MUSKET_BALL.get();
		}

		/**
		 * Constructor for GoldBulletEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public GoldenMusketBallEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.GOLDEN_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.GOLDEN_MUSKET_BALL.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							random.nextGaussian() * 0.0025F).scale(velocity);
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
			referenceItem = ItemRegistry.COPPER_MUSKET_BALL.get();
		}

		/**
		 * Constructor for CopperBulletEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public CopperMusketBallEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.COPPER_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.COPPER_MUSKET_BALL.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							random.nextGaussian() * 0.0025F).scale(velocity);
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
			referenceItem = ItemRegistry.IRON_MUSKET_BALL.get();
		}

		/**
		 * Constructor for IronBulletEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public IronMusketBallEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.IRON_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.IRON_MUSKET_BALL.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							random.nextGaussian() * 0.0025F).scale(velocity);
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
			referenceItem = ItemRegistry.COBALT_MUSKET_BALL.get();
		}

		/**
		 * Constructor for CobaltBulletEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public CobaltMusketBallEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.COBALT_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.COBALT_MUSKET_BALL.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							random.nextGaussian() * 0.0025F).scale(velocity);
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
			referenceItem = ItemRegistry.DIAMOND_MUSKET_BALL.get();
		}

		/**
		 * Constructor for DiamondBulletEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public DiamondMusketBallEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.DIAMOND_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.DIAMOND_MUSKET_BALL.get();
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

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 0.9f)),
							0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 0.9f)),
							random.nextGaussian() * 0.0025F).scale(velocity);
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
			referenceItem = ItemRegistry.NETHERITE_MUSKET_BALL.get();
		}

		/**
		 * Constructor for NetheriteBulletEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public NetheriteMusketBallEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.NETHERITE_MUSKET_BALL_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.NETHERITE_MUSKET_BALL.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.002F * (GeneralUtilities.getRandomNumber(0.2f, 0.7f)),
							0.002F * (GeneralUtilities.getRandomNumber(0.2f, 0.7f)),
							random.nextGaussian() * 0.002F).scale(velocity);
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
}