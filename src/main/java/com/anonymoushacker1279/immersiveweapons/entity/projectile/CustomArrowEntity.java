package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.client.particle.smokebomb.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Supplier;

public class CustomArrowEntity {

	public static class CopperArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for CopperArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public CopperArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
		}

		/**
		 * Constructor for CopperArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public CopperArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for CopperArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public CopperArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
		}
	}

	public static class IronArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for IronArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public IronArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.IRON_ARROW.get();
		}

		/**
		 * Constructor for IronArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public IronArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for IronArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public IronArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.IRON_ARROW.get();
		}
	}

	public static class CobaltArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for CobaltArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public CobaltArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.COBALT_ARROW.get();
		}

		/**
		 * Constructor for CobaltArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public CobaltArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COBALT_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for CobaltArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public CobaltArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.COBALT_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.COBALT_ARROW.get();
		}
	}

	public static class DiamondArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for DiamondArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public DiamondArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.DIAMOND_ARROW.get();
		}

		/**
		 * Constructor for DiamondArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public DiamondArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for DiamondArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public DiamondArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.DIAMOND_ARROW.get();
		}
	}

	public static class GoldArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for GoldArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public GoldArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.GOLD_ARROW.get();
		}

		/**
		 * Constructor for GoldArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public GoldArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for GoldArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public GoldArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.GOLD_ARROW.get();
		}
	}

	public static class StoneArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for StoneArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public StoneArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.STONE_ARROW.get();
		}

		/**
		 * Constructor for StoneArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public StoneArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for StoneArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public StoneArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.STONE_ARROW.get();
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
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0075F * inaccuracy, -0.1085F * inaccuracy, random.nextGaussian() * 0.0075F * inaccuracy).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}
	}

	public static class WoodArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for WoodArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public WoodArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.WOOD_ARROW.get();
		}

		/**
		 * Constructor for WoodArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public WoodArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for WoodArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public WoodArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.WOOD_ARROW.get();
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
			super.shoot(x, y, z, velocity, (inaccuracy + GeneralUtilities.getRandomNumber(5.8f, 7.2f)));
		}
	}

	public static class NetheriteArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for NetheriteArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public NetheriteArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.NETHERITE_ARROW.get();
		}

		/**
		 * Constructor for NetheriteArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public NetheriteArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for NetheriteArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public NetheriteArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.NETHERITE_ARROW.get();
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
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 1.1f)), random.nextGaussian() * 0.0025F).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		/**
		 * Get the movement modifier.
		 * @return double
		 */
		@Override
		public double getMovementModifier() {
			return 0.0455d;
		}
	}

	public static class SmokeBombArrowEntity extends AbstractCustomArrowEntity {
		private static int color = 0;
		private final int configMaxParticles = Config.MAX_SMOKE_BOMB_PARTICLES.get();

		/**
		 * Constructor for SmokeBombArrowEntity.
		 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public SmokeBombArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.SMOKE_BOMB_ARROW.get();
		}

		/**
		 * Constructor for SmokeBombArrowEntity.
		 * @param shooter the <code>LivingEntity</code> shooting the entity
		 * @param world the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public SmokeBombArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.SMOKE_BOMB_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for SmokeBombArrowEntity.
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x the X position
		 * @param y the Y position
		 * @param z the Z position
		 */
		public SmokeBombArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.SMOKE_BOMB_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.SMOKE_BOMB_ARROW.get();
		}

		/**
		 * Set the color of smoke.
		 * @param color an integer representing the color
		 */
		public static void setColor(int color) {
			SmokeBombArrowEntity.color = color;
		}

		/**
		 * Runs when the entity hits something.
		 * @param rayTraceResult the <code>RayTraceResult</code> instance
		 */
		@Override
		public void onHit(@NotNull HitResult rayTraceResult) {
			super.onHit(rayTraceResult);
			if (!level.isClientSide) {
				PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())), new SmokeBombArrowEntityPacketHandler(blockPosition(), configMaxParticles, color));
			}
		}

		public record SmokeBombArrowEntityPacketHandler(BlockPos blockPos, int configMaxParticles,
		                                                int color) {

			/**
			 * Constructor for SmokeBombArrowEntityPacketHandler.
			 *
			 * @param blockPos           the <code>BlockPos</code> of the block where the packet was sent
			 * @param configMaxParticles the max particles to generate
			 * @param color              an integer representing the color
			 */
			public SmokeBombArrowEntityPacketHandler {
			}

			/**
			 * Encodes a packet
			 *
			 * @param msg          the <code>SmokeBombArrowEntityPacketHandler</code> message being sent
			 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
			 */
			public static void encode(SmokeBombArrowEntityPacketHandler msg, FriendlyByteBuf packetBuffer) {
				packetBuffer.writeBlockPos(msg.blockPos).writeInt(msg.configMaxParticles).writeInt(msg.color);
			}

			/**
			 * Decodes a packet
			 *
			 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
			 * @return SmokeBombArrowEntityPacketHandler
			 */
			public static SmokeBombArrowEntityPacketHandler decode(FriendlyByteBuf packetBuffer) {
				return new SmokeBombArrowEntityPacketHandler(packetBuffer.readBlockPos(), packetBuffer.readInt(), packetBuffer.readInt());
			}

			/**
			 * Handles an incoming packet, by sending it to the client/server
			 *
			 * @param msg             the <code>SmokeBombArrowEntityPacketHandler</code> message being sent
			 * @param contextSupplier the <code>Supplier</code> providing context
			 */
			public static void handle(SmokeBombArrowEntityPacketHandler msg, Supplier<Context> contextSupplier) {
				NetworkEvent.Context context = contextSupplier.get();
				context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
				context.setPacketHandled(true);
			}

			/**
			 * Runs specifically on the client, when a packet is received
			 *
			 * @param msg the <code>SmokeBombArrowEntityPacketHandler</code> message being sent
			 */
			@OnlyIn(Dist.CLIENT)
			private static void handleOnClient(SmokeBombArrowEntityPacketHandler msg) {
				Minecraft minecraft = Minecraft.getInstance();
				if (minecraft.level != null) {
					minecraft.level.playLocalSound(msg.blockPos, DeferredRegistryHandler.SMOKE_BOMB_HISS.get(), SoundSource.NEUTRAL, 0.1f, 0.6f, true);

					ParticleOptions particleData = makeParticle(msg);
					for (int i = 0; i < msg.configMaxParticles; ++i) {
						minecraft.level.addParticle(particleData, true, msg.blockPos.getX(), msg.blockPos.getY(), msg.blockPos.getZ(), GeneralUtilities.getRandomNumber(-0.03, 0.03d), GeneralUtilities.getRandomNumber(-0.02d, 0.02d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					}
				}
			}

			/**
			 * Create a new particle.
			 *
			 * @param msg the <code>SmokeBombArrowEntityPacketHandler</code> message
			 * @return IParticleData
			 */
			static ParticleOptions makeParticle(SmokeBombArrowEntityPacketHandler msg) {
				Color tint = getTint(GeneralUtilities.getRandomNumber(0, 2), msg);
				double diameter = getDiameter(GeneralUtilities.getRandomNumber(1.0d, 5.5d));

				return new SmokeBombParticleData(tint, diameter);
			}

			/**
			 * Get the particle diameter.
			 *
			 * @param random a random number
			 * @return double
			 */
			private static double getDiameter(double random) {
				final double MIN_DIAMETER = 0.5;
				final double MAX_DIAMETER = 5.5;
				return MIN_DIAMETER + (MAX_DIAMETER - MIN_DIAMETER) * random;
			}

			/**
			 * Get the particle tint.
			 *
			 * @param random a random number
			 * @param msg    the <code>SmokeBombArrowEntityPacketHandler</code> message
			 * @return Color
			 */
			private static Color getTint(int random, SmokeBombArrowEntityPacketHandler msg) {
				Color[] tints = {
						new Color(1.00f, 1.00f, 1.00f),  // no tint (white)
						new Color(1.00f, 0.97f, 1.00f),  // off-white
						new Color(1.00f, 1.00f, 0.97f),  // off-white 2
				};
				Color[] tintsRed = {
						new Color(1.00f, 0.25f, 0.25f),  // tint (red)
						new Color(1.00f, 0.30f, 0.25f),  // off-red
						new Color(1.00f, 0.25f, 0.30f),  // off-red 2
				};
				Color[] tintsGreen = {
						new Color(0.25f, 1.00f, 0.25f),  // tint (green)
						new Color(0.30f, 1.00f, 0.25f),  // off-green
						new Color(0.25f, 1.00f, 0.30f),  // off-green 2
				};
				Color[] tintsBlue = {
						new Color(0.25f, 0.25f, 1.00f),  // tint (blue)
						new Color(0.30f, 0.25f, 1.00f),  // off-blue
						new Color(0.25f, 0.30f, 1.00f),  // off-blue 2
				};
				Color[] tintsPurple = {
						new Color(1.00f, 0.25f, 1.00f),  // tint (purple)
						new Color(1.00f, 0.30f, 1.00f),  // off-purple
						new Color(1.00f, 0.35f, 1.00f),  // off-purple 2
				};
				Color[] tintsYellow = {
						new Color(1.00f, 1.00f, 0.25f),  // tint (yellow)
						new Color(1.00f, 1.00f, 0.30f),  // off-yellow
						new Color(1.00f, 1.00f, 0.35f),  // off-yellow 2
				};

				return switch (msg.color) {
					case 1 -> tintsRed[random];
					case 2 -> tintsGreen[random];
					case 3 -> tintsBlue[random];
					case 4 -> tintsPurple[random];
					case 5 -> tintsYellow[random];
					default -> tints[random];
				};
			}
		}
	}
}