package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.awt.*;

public class CustomArrowEntity {

	public static class CopperArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		@SuppressWarnings("unchecked")
		public CopperArrowEntity(EntityType<?> type, World world) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
		}

		public CopperArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
		}

		public CopperArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), x, y, z, worldIn);
			this.referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(this.referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
	}

	public static class IronArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		@SuppressWarnings("unchecked")
		public IronArrowEntity(EntityType<?> type, World world) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.referenceItem = DeferredRegistryHandler.IRON_ARROW.get();
		}

		public IronArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
		}

		public IronArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), x, y, z, worldIn);
			this.referenceItem = DeferredRegistryHandler.IRON_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(this.referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
	}

	public static class DiamondArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		@SuppressWarnings("unchecked")
		public DiamondArrowEntity(EntityType<?> type, World world) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.referenceItem = DeferredRegistryHandler.DIAMOND_ARROW.get();
		}

		public DiamondArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
		}

		public DiamondArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), x, y, z, worldIn);
			this.referenceItem = DeferredRegistryHandler.DIAMOND_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(this.referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
	}

	public static class GoldArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		@SuppressWarnings("unchecked")
		public GoldArrowEntity(EntityType<?> type, World world) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.referenceItem = DeferredRegistryHandler.GOLD_ARROW.get();
		}

		public GoldArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
		}

		public GoldArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), x, y, z, worldIn);
			this.referenceItem = DeferredRegistryHandler.GOLD_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(this.referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
	}

	public static class StoneArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		@SuppressWarnings("unchecked")
		public StoneArrowEntity(EntityType<?> type, World world) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.referenceItem = DeferredRegistryHandler.STONE_ARROW.get();
		}

		public StoneArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
		}

		public StoneArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), x, y, z, worldIn);
			this.referenceItem = DeferredRegistryHandler.STONE_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(this.referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.0075F * inaccuracy, -0.1085F * inaccuracy, this.random.nextGaussian() * 0.0075F * inaccuracy).scale(velocity);
			this.setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			this.yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			this.xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			this.yRotO = this.yRot;
			this.xRotO = this.xRot;
		}
	}

	public static class WoodArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		@SuppressWarnings("unchecked")
		public WoodArrowEntity(EntityType<?> type, World world) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.referenceItem = DeferredRegistryHandler.WOOD_ARROW.get();
		}

		public WoodArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
		}

		public WoodArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), x, y, z, worldIn);
			this.referenceItem = DeferredRegistryHandler.WOOD_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(this.referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			super.shoot(x, y, z, velocity, (inaccuracy + GeneralUtilities.getRandomNumber(5.8f, 7.2f)));
		}
	}

	public static class NetheriteArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;
		private BlockState inBlockState;

		@SuppressWarnings("unchecked")
		public NetheriteArrowEntity(EntityType<?> type, World world) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.referenceItem = DeferredRegistryHandler.NETHERITE_ARROW.get();
		}

		public NetheriteArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
		}

		public NetheriteArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), x, y, z, worldIn);
			this.referenceItem = DeferredRegistryHandler.NETHERITE_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(this.referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), this.random.nextGaussian() * 0.0025F).scale(velocity);
			this.setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			this.yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			this.xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			this.yRotO = this.yRot;
			this.xRotO = this.xRot;
		}

		private boolean shouldFall() {
			return this.inGround && this.level.noCollision((new AxisAlignedBB(this.position(), this.position())).inflate(0.06D));
		}

		private void startFalling() {
			this.inGround = false;
			Vector3d vector3d = this.getDeltaMovement();
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.2F, this.random.nextFloat() * 0.2F, this.random.nextFloat() * 0.2F));
		}

		@SuppressWarnings("deprecation")
		@Override
		public void tick() {
			super.tick();
			boolean flag = this.isNoPhysics();
			Vector3d vector3d = this.getDeltaMovement();
			if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
				float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
				this.yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
				this.xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
				this.yRotO = this.yRot;
				this.xRotO = this.xRot;
			}

			BlockPos blockpos = this.blockPosition();
			BlockState blockstate = this.level.getBlockState(blockpos);
			if (!blockstate.isAir(this.level, blockpos) && !flag) {
				VoxelShape voxelshape = blockstate.getBlockSupportShape(this.level, blockpos);
				if (!voxelshape.isEmpty()) {
					Vector3d vector3d1 = this.position();

					for (AxisAlignedBB axisalignedbb : voxelshape.toAabbs()) {
						if (axisalignedbb.move(blockpos).contains(vector3d1)) {
							this.inGround = true;
							break;
						}
					}
				}
			}

			if (this.shakeTime > 0) {
				--this.shakeTime;
			}

			if (this.isInWaterOrRain()) {
				this.clearFire();
			}

			if (this.inGround && !flag) {
				if (this.inBlockState != blockstate && this.shouldFall()) {
					this.startFalling();
				} else if (!this.level.isClientSide) {
					this.tickDespawn();
				}

				++this.inGroundTime;
			} else {
				this.inGroundTime = 0;
				Vector3d vector3d2 = this.position();
				Vector3d vector3d3 = vector3d2.add(vector3d);
				RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
				if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
					vector3d3 = raytraceresult.getLocation();
				}

				while (!this.removed) {
					EntityRayTraceResult entityraytraceresult = this.findHitEntity(vector3d2, vector3d3);
					if (entityraytraceresult != null) {
						raytraceresult = entityraytraceresult;
					}

					if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
						Entity entity = null;
						if (raytraceresult instanceof EntityRayTraceResult) {
							entity = ((EntityRayTraceResult) raytraceresult).getEntity();
						}
						Entity entity1 = this.getOwner();
						if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity) entity1).canHarmPlayer((PlayerEntity) entity)) {
							raytraceresult = null;
							entityraytraceresult = null;
						}
					}

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
						this.onHit(raytraceresult);
						this.hasImpulse = true;
					}

					if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
						break;
					}

					raytraceresult = null;
				}

				vector3d = this.getDeltaMovement();
				double d3 = vector3d.x;
				double d4 = vector3d.y;
				double d0 = vector3d.z;
				if (this.isCritArrow()) {
					for (int i = 0; i < 4; ++i) {
						this.level.addParticle(ParticleTypes.CRIT, this.getX() + d3 * i / 4.0D, this.getY() + d4 * i / 4.0D, this.getZ() + d0 * i / 4.0D, -d3, -d4 + 0.2D, -d0);
					}
				}

				double d5 = this.getX() + d3;
				double d1 = this.getY() + d4;
				double d2 = this.getZ() + d0;
				float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
				if (flag) {
					this.yRot = (float) (MathHelper.atan2(-d3, -d0) * (180F / (float) Math.PI));
				} else {
					this.yRot = (float) (MathHelper.atan2(d3, d0) * (180F / (float) Math.PI));
				}

				this.xRot = (float) (MathHelper.atan2(d4, f1) * (180F / (float) Math.PI));
				this.xRot = lerpRotation(this.xRotO, this.xRot);
				this.yRot = lerpRotation(this.yRotO, this.yRot);
				float f2 = 0.99F;
				if (this.isInWater()) {
					for (int j = 0; j < 4; ++j) {
						this.level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
					}

					f2 = this.getWaterInertia();
				}

				this.setDeltaMovement(vector3d.scale(f2));
				if (!this.isNoGravity() && !flag) {
					Vector3d vector3d4 = this.getDeltaMovement();
					this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.0455d, vector3d4.z);
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}
	}

	public static class SmokeBombArrowEntity extends AbstractArrowEntity {
		private static String color;
		private final Item referenceItem;
		private final int configMaxParticles = Config.MAX_SMOKE_BOMB_PARTICLES.get();
		private boolean hasAlreadyImpacted = false;

		@SuppressWarnings("unchecked")
		public SmokeBombArrowEntity(EntityType<?> type, World world) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.referenceItem = DeferredRegistryHandler.SMOKE_BOMB_ARROW.get();
		}

		public SmokeBombArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.SMOKE_BOMB_ARROW_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
		}

		public SmokeBombArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.SMOKE_BOMB_ARROW_ENTITY.get(), x, y, z, worldIn);
			this.referenceItem = DeferredRegistryHandler.SMOKE_BOMB_ARROW.get();
		}

		public static void setColor(String color) {
			SmokeBombArrowEntity.color = color;
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(this.referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		protected IParticleData makeParticle() {
			Color tint = getTint(GeneralUtilities.getRandomNumber(0, 2));
			double diameter = getDiameter(GeneralUtilities.getRandomNumber(1.0d, 5.5d));

			return new SmokeBombParticleData(tint, diameter);
		}

		private Color getTint(int random) {
			Color[] tints = {
					new Color(1.00f, 1.00f, 1.00f),  // no tint (white)
					new Color(1.00f, 0.97f, 1.00f),  // off white
					new Color(1.00f, 1.00f, 0.97f),  // off white 2: electric boogaloo
			};
			Color[] tintsRed = {
					new Color(1.00f, 0.25f, 0.25f),  // tint (red)
					new Color(1.00f, 0.30f, 0.25f),  // off red
					new Color(1.00f, 0.25f, 0.30f),  // off red 2: electric boogaloo
			};
			Color[] tintsGreen = {
					new Color(0.25f, 1.00f, 0.25f),  // tint (green)
					new Color(0.30f, 1.00f, 0.25f),  // off green
					new Color(0.25f, 1.00f, 0.30f),  // off green 2: electric boogaloo
			};
			Color[] tintsBlue = {
					new Color(0.25f, 0.25f, 1.00f),  // tint (blue)
					new Color(0.30f, 0.25f, 1.00f),  // off blue
					new Color(0.25f, 0.30f, 1.00f),  // off blue 2: electric boogaloo
			};
			Color[] tintsPurple = {
					new Color(1.00f, 0.25f, 1.00f),  // tint (purple)
					new Color(1.00f, 0.30f, 1.00f),  // off purple
					new Color(1.00f, 0.35f, 1.00f),  // off purple 2: electric boogaloo
			};
			Color[] tintsYellow = {
					new Color(1.00f, 1.00f, 0.25f),  // tint (yellow)
					new Color(1.00f, 1.00f, 0.30f),  // off yellow
					new Color(1.00f, 1.00f, 0.35f),  // off yellow 2: electric boogaloo
			};

			switch (SmokeBombArrowEntity.color) {
				case "red":
					return tintsRed[random];
				case "green":
					return tintsGreen[random];
				case "blue":
					return tintsBlue[random];
				case "purple":
					return tintsPurple[random];
				case "yellow":
					return tintsYellow[random];
				default:
					return tints[random];
			}
		}

		private double getDiameter(double random) {
			final double MIN_DIAMETER = 0.5;
			final double MAX_DIAMETER = 5.5;
			return MIN_DIAMETER + (MAX_DIAMETER - MIN_DIAMETER) * random;
		}

		@Override
		public void onHit(RayTraceResult rayTraceResult) {
			if (!hasAlreadyImpacted) {
				hasAlreadyImpacted = true;
				IParticleData particleData = this.makeParticle();
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), DeferredRegistryHandler.SMOKE_BOMB_HISS.get(), SoundCategory.NEUTRAL, 0.1f, 0.6f, true);

				for (int i = 0; i < configMaxParticles; ++i) {
					this.level.addParticle(particleData, true, this.getX(), this.getY(), this.getZ(), GeneralUtilities.getRandomNumber(-0.03, 0.03d), GeneralUtilities.getRandomNumber(-0.02d, 0.02d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
				}

				RayTraceResult.Type raytraceresult$type = rayTraceResult.getType();
				if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
					this.onHitEntity((EntityRayTraceResult) rayTraceResult);
				} else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
					this.onHitBlock((BlockRayTraceResult) rayTraceResult);
				}
			}
		}
	}
}