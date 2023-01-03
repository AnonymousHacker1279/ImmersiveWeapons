package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity.SmokeGrenadeEntityPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class ArrowEntities {

	public static class WoodenArrowEntity extends CustomArrowEntity {

		/**
		 * Constructor for WoodenArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public WoodenArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.WOODEN_ARROW.get();
		}

		/**
		 * Constructor for WoodenArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public WoodenArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.WOODEN_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.WOODEN_ARROW.get();
		}

		/**
		 * Constructor for WoodenArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public WoodenArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.WOODEN_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.WOODEN_ARROW.get();
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
			super.shoot(x, y, z, velocity, (inaccuracy + GeneralUtilities.getRandomNumber(5.8f, 7.2f)));
		}
	}

	public static class StoneArrowEntity extends CustomArrowEntity {

		/**
		 * Constructor for StoneArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public StoneArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.STONE_ARROW.get();
		}

		/**
		 * Constructor for StoneArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public StoneArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.STONE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.STONE_ARROW.get();
		}

		/**
		 * Constructor for StoneArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public StoneArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.STONE_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.STONE_ARROW.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0075F, -0.0095F, random.nextGaussian() * 0.0075F).scale(velocity);
		}
	}

	public static class GoldenArrowEntity extends CustomArrowEntity {

		/**
		 * Constructor for GoldenArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public GoldenArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.GOLDEN_ARROW.get();
		}

		/**
		 * Constructor for GoldenArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public GoldenArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.GOLDEN_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.GOLDEN_ARROW.get();
		}

		/**
		 * Constructor for GoldenArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public GoldenArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.GOLDEN_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.GOLDEN_ARROW.get();
		}
	}

	public static class CopperArrowEntity extends CustomArrowEntity {

		/**
		 * Constructor for CopperArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public CopperArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.COPPER_ARROW.get();
		}

		/**
		 * Constructor for CopperArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public CopperArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.COPPER_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.COPPER_ARROW.get();
		}

		/**
		 * Constructor for CopperArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public CopperArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.COPPER_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.COPPER_ARROW.get();
		}
	}

	public static class IronArrowEntity extends CustomArrowEntity {

		/**
		 * Constructor for IronArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public IronArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.IRON_ARROW.get();
		}

		/**
		 * Constructor for IronArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public IronArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.IRON_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.IRON_ARROW.get();
		}

		/**
		 * Constructor for IronArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public IronArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.IRON_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.IRON_ARROW.get();
		}
	}

	public static class CobaltArrowEntity extends CustomArrowEntity {

		/**
		 * Constructor for CobaltArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public CobaltArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.COBALT_ARROW.get();
		}

		/**
		 * Constructor for CobaltArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public CobaltArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.COBALT_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.COBALT_ARROW.get();
		}

		/**
		 * Constructor for CobaltArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public CobaltArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.COBALT_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.COBALT_ARROW.get();
		}
	}

	public static class DiamondArrowEntity extends CustomArrowEntity {

		/**
		 * Constructor for DiamondArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public DiamondArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.DIAMOND_ARROW.get();
		}

		/**
		 * Constructor for DiamondArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public DiamondArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.DIAMOND_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.DIAMOND_ARROW.get();
		}

		/**
		 * Constructor for DiamondArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public DiamondArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.DIAMOND_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.DIAMOND_ARROW.get();
		}
	}

	public static class NetheriteArrowEntity extends CustomArrowEntity {

		/**
		 * Constructor for NetheriteArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public NetheriteArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.NETHERITE_ARROW.get();
		}

		/**
		 * Constructor for NetheriteArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public NetheriteArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.NETHERITE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.NETHERITE_ARROW.get();
		}

		/**
		 * Constructor for NetheriteArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public NetheriteArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.NETHERITE_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.NETHERITE_ARROW.get();
		}

		@Override
		protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
			return new Vec3(x, y, z)
					.normalize()
					.add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)),
							random.nextGaussian() * 0.0025F).scale(velocity);
		}

		/**
		 * Get the movement modifier.
		 *
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.0455d;
		}
	}

	public static class SmokeGrenadeArrowEntity extends CustomArrowEntity {

		private int color = 0;

		/**
		 * Constructor for SmokeGrenadeArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public SmokeGrenadeArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = ItemRegistry.SMOKE_GRENADE_ARROW.get();
		}

		/**
		 * Constructor for SmokeGrenadeArrowEntity.
		 *
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world   the <code>World</code> the entity is in
		 */
		public SmokeGrenadeArrowEntity(LivingEntity shooter, Level world) {
			super(EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = ItemRegistry.SMOKE_GRENADE_ARROW.get();
		}

		/**
		 * Constructor for SmokeBombArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public SmokeGrenadeArrowEntity(Level worldIn, double x, double y, double z) {
			super(EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = ItemRegistry.SMOKE_GRENADE_ARROW.get();
		}

		/**
		 * Set the color of smoke.
		 *
		 * @param color an integer representing the color
		 */
		public void setColor(int color) {
			this.color = color;
		}

		/**
		 * Runs when the entity hits something.
		 *
		 * @param hitResult the <code>HitResult</code> instance
		 */
		@Override
		public void onHit(HitResult hitResult) {
			super.onHit(hitResult);
			if (!level.isClientSide) {
				PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())),
						new SmokeGrenadeEntityPacketHandler(getX(), getY(), getZ(), color));
			}
		}
	}
}