package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import java.awt.*;
import java.util.function.Supplier;

public class CustomArrowEntity {

	public static class CopperArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		public CopperArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
		}

		public CopperArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		public CopperArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), x, y, z, worldIn);
			referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
	}

	public static class IronArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		public IronArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.IRON_ARROW.get();
		}

		public IronArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		public IronArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), x, y, z, worldIn);
			referenceItem = DeferredRegistryHandler.IRON_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
	}

	public static class DiamondArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		public DiamondArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.DIAMOND_ARROW.get();
		}

		public DiamondArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		public DiamondArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), x, y, z, worldIn);
			referenceItem = DeferredRegistryHandler.DIAMOND_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
	}

	public static class GoldArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		public GoldArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.GOLD_ARROW.get();
		}

		public GoldArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		public GoldArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), x, y, z, worldIn);
			referenceItem = DeferredRegistryHandler.GOLD_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
	}

	public static class StoneArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		public StoneArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.STONE_ARROW.get();
		}

		public StoneArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		public StoneArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), x, y, z, worldIn);
			referenceItem = DeferredRegistryHandler.STONE_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(random.nextGaussian() * 0.0075F * inaccuracy, -0.1085F * inaccuracy, random.nextGaussian() * 0.0075F * inaccuracy).scale(velocity);
			setDeltaMovement(vector3d);
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}
	}

	public static class WoodArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;

		public WoodArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.WOOD_ARROW.get();
		}

		public WoodArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		public WoodArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), x, y, z, worldIn);
			referenceItem = DeferredRegistryHandler.WOOD_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(referenceItem);
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

		public NetheriteArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.NETHERITE_ARROW.get();
		}

		public NetheriteArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		public NetheriteArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), x, y, z, worldIn);
			referenceItem = DeferredRegistryHandler.NETHERITE_ARROW.get();
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

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

		private boolean shouldFall() {
			return inGround && level.noCollision((new AxisAlignedBB(position(), position())).inflate(0.06D));
		}

		private void startFalling() {
			inGround = false;
			Vector3d vector3d = getDeltaMovement();
			setDeltaMovement(vector3d.multiply(random.nextFloat() * 0.2F, random.nextFloat() * 0.2F, random.nextFloat() * 0.2F));
		}

		@Override
		public void tick() {
			super.tick();
			boolean flag = isNoPhysics();
			Vector3d vector3d = getDeltaMovement();
			if (xRotO == 0.0F && yRotO == 0.0F) {
				float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
				yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
				xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
				yRotO = yRot;
				xRotO = xRot;
			}

			BlockPos blockpos = blockPosition();
			BlockState blockstate = level.getBlockState(blockpos);
			if (!blockstate.isAir(level, blockpos) && !flag) {
				VoxelShape voxelshape = blockstate.getBlockSupportShape(level, blockpos);
				if (!voxelshape.isEmpty()) {
					Vector3d vector3d1 = position();

					for (AxisAlignedBB axisalignedbb : voxelshape.toAabbs()) {
						if (axisalignedbb.move(blockpos).contains(vector3d1)) {
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
				if (inBlockState != blockstate && shouldFall()) {
					startFalling();
				} else if (!level.isClientSide) {
					tickDespawn();
				}

				++inGroundTime;
			} else {
				inGroundTime = 0;
				Vector3d vector3d2 = position();
				Vector3d vector3d3 = vector3d2.add(vector3d);
				RayTraceResult raytraceresult = level.clip(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
				if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
					vector3d3 = raytraceresult.getLocation();
				}

				while (!removed) {
					EntityRayTraceResult entityraytraceresult = findHitEntity(vector3d2, vector3d3);
					if (entityraytraceresult != null) {
						raytraceresult = entityraytraceresult;
					}

					if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
						Entity entity = null;
						if (raytraceresult instanceof EntityRayTraceResult) {
							entity = ((EntityRayTraceResult) raytraceresult).getEntity();
						}
						Entity entity1 = getOwner();
						if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity) entity1).canHarmPlayer((PlayerEntity) entity)) {
							raytraceresult = null;
							entityraytraceresult = null;
						}
					}

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
						onHit(raytraceresult);
						hasImpulse = true;
					}

					if (entityraytraceresult == null || getPierceLevel() <= 0) {
						break;
					}

					raytraceresult = null;
				}

				vector3d = getDeltaMovement();
				double d3 = vector3d.x;
				double d4 = vector3d.y;
				double d0 = vector3d.z;
				if (isCritArrow()) {
					for (int i = 0; i < 4; ++i) {
						level.addParticle(ParticleTypes.CRIT, getX() + d3 * i / 4.0D, getY() + d4 * i / 4.0D, getZ() + d0 * i / 4.0D, -d3, -d4 + 0.2D, -d0);
					}
				}

				double d5 = getX() + d3;
				double d1 = getY() + d4;
				double d2 = getZ() + d0;
				float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
				if (flag) {
					yRot = (float) (MathHelper.atan2(-d3, -d0) * (180F / (float) Math.PI));
				} else {
					yRot = (float) (MathHelper.atan2(d3, d0) * (180F / (float) Math.PI));
				}

				xRot = (float) (MathHelper.atan2(d4, f1) * (180F / (float) Math.PI));
				xRot = lerpRotation(xRotO, xRot);
				yRot = lerpRotation(yRotO, yRot);
				float f2 = 0.99F;
				if (isInWater()) {
					for (int j = 0; j < 4; ++j) {
						level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
					}

					f2 = getWaterInertia();
				}

				setDeltaMovement(vector3d.scale(f2));
				if (!isNoGravity() && !flag) {
					Vector3d vector3d4 = getDeltaMovement();
					setDeltaMovement(vector3d4.x, vector3d4.y + 0.0455d, vector3d4.z);
				}

				setPos(d5, d1, d2);
				checkInsideBlocks();
			}
		}
	}

	public static class SmokeBombArrowEntity extends AbstractArrowEntity {
		private static int color = 0;
		private final Item referenceItem;
		private final int configMaxParticles = Config.MAX_SMOKE_BOMB_PARTICLES.get();
		private boolean hasAlreadyImpacted = false;

		public SmokeBombArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.SMOKE_BOMB_ARROW.get();
		}

		public SmokeBombArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.SMOKE_BOMB_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		public SmokeBombArrowEntity(World worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.SMOKE_BOMB_ARROW_ENTITY.get(), x, y, z, worldIn);
			referenceItem = DeferredRegistryHandler.SMOKE_BOMB_ARROW.get();
		}

		public static void setColor(int color) {
			SmokeBombArrowEntity.color = color;
		}

		@Override
		public ItemStack getPickupItem() {
			return new ItemStack(referenceItem);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		public void onHit(RayTraceResult rayTraceResult) {
			if (!hasAlreadyImpacted && !level.isClientSide) {
				hasAlreadyImpacted = true;
				PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())), new SmokeBombArrowEntityPacketHandler(blockPosition(), configMaxParticles, color));

				RayTraceResult.Type rayTraceResultType = rayTraceResult.getType();
				if (rayTraceResultType == RayTraceResult.Type.ENTITY) {
					onHitEntity((EntityRayTraceResult) rayTraceResult);
				} else if (rayTraceResultType == RayTraceResult.Type.BLOCK) {
					onHitBlock((BlockRayTraceResult) rayTraceResult);
				}
			}
		}

		public static class SmokeBombArrowEntityPacketHandler {

			private final BlockPos blockPos;
			private final int configMaxParticles;
			private final int color;

			public SmokeBombArrowEntityPacketHandler(final BlockPos blockPos, final int configMaxParticles, final int color) {
				this.blockPos = blockPos;
				this.configMaxParticles = configMaxParticles;
				this.color = color;
			}

			public static void encode(final SmokeBombArrowEntityPacketHandler msg, final PacketBuffer packetBuffer) {
				packetBuffer.writeBlockPos(msg.blockPos).writeInt(msg.configMaxParticles).writeInt(msg.color);
			}

			public static SmokeBombArrowEntityPacketHandler decode(final PacketBuffer packetBuffer) {
				return new SmokeBombArrowEntityPacketHandler(packetBuffer.readBlockPos(), packetBuffer.readInt(), packetBuffer.readInt());
			}

			public static void handle(final SmokeBombArrowEntityPacketHandler msg, final Supplier<Context> contextSupplier) {
				final NetworkEvent.Context context = contextSupplier.get();
				context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
				context.setPacketHandled(true);
			}

			@OnlyIn(Dist.CLIENT)
			private static void handleOnClient(final SmokeBombArrowEntityPacketHandler msg) {
				Minecraft minecraft = Minecraft.getInstance();
				if (minecraft.level != null) {
					minecraft.level.playLocalSound(msg.blockPos, DeferredRegistryHandler.SMOKE_BOMB_HISS.get(), SoundCategory.NEUTRAL, 0.1f, 0.6f, true);

					IParticleData particleData = makeParticle(msg);
					for (int i = 0; i < msg.configMaxParticles; ++i) {
						minecraft.level.addParticle(particleData, true, msg.blockPos.getX(), msg.blockPos.getY(), msg.blockPos.getZ(), GeneralUtilities.getRandomNumber(-0.03, 0.03d), GeneralUtilities.getRandomNumber(-0.02d, 0.02d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					}
				}
			}

			protected static IParticleData makeParticle(final SmokeBombArrowEntityPacketHandler msg) {
				Color tint = getTint(GeneralUtilities.getRandomNumber(0, 2), msg);
				double diameter = getDiameter(GeneralUtilities.getRandomNumber(1.0d, 5.5d));

				return new SmokeBombParticleData(tint, diameter);
			}

			private static double getDiameter(double random){
				final double MIN_DIAMETER = 0.5;
				final double MAX_DIAMETER = 5.5;
				return MIN_DIAMETER + (MAX_DIAMETER - MIN_DIAMETER) * random;
			}

			private static Color getTint(int random, final SmokeBombArrowEntityPacketHandler msg){
				Color[] tints = {
						new Color(1.00f, 1.00f, 1.00f),  // no tint (white)
						new Color(1.00f, 0.97f, 1.00f),  // off white
						new Color(1.00f, 1.00f, 0.97f),  // off white 2
				};
				Color[] tintsRed = {
						new Color(1.00f, 0.25f, 0.25f),  // tint (red)
						new Color(1.00f, 0.30f, 0.25f),  // off red
						new Color(1.00f, 0.25f, 0.30f),  // off red 2
				};
				Color[] tintsGreen = {
						new Color(0.25f, 1.00f, 0.25f),  // tint (green)
						new Color(0.30f, 1.00f, 0.25f),  // off green
						new Color(0.25f, 1.00f, 0.30f),  // off green 2
				};
				Color[] tintsBlue = {
						new Color(0.25f, 0.25f, 1.00f),  // tint (blue)
						new Color(0.30f, 0.25f, 1.00f),  // off blue
						new Color(0.25f, 0.30f, 1.00f),  // off blue 2
				};
				Color[] tintsPurple = {
						new Color(1.00f, 0.25f, 1.00f),  // tint (purple)
						new Color(1.00f, 0.30f, 1.00f),  // off purple
						new Color(1.00f, 0.35f, 1.00f),  // off purple 2
				};
				Color[] tintsYellow = {
						new Color(1.00f, 1.00f, 0.25f),  // tint (yellow)
						new Color(1.00f, 1.00f, 0.30f),  // off yellow
						new Color(1.00f, 1.00f, 0.35f),  // off yellow 2
				};

				switch(msg.color) {
					case 1:
						return tintsRed[random];
					case 2:
						return tintsGreen[random];
					case 3:
						return tintsBlue[random];
					case 4:
						return tintsPurple[random];
					case 5:
						return tintsYellow[random];
					default:
						return tints[random];
				}
			}
		}
	}
}