package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

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
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Supplier;

public class CustomArrowEntity {

	public static class CopperArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for CopperArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public CopperArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
		}

		/**
		 * Constructor for CopperArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public CopperArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
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
			super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
		}
	}

	public static class IronArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for IronArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public IronArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.IRON_ARROW.get();
		}

		/**
		 * Constructor for IronArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public IronArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
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
			super(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.IRON_ARROW.get();
		}
	}

	public static class CobaltArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for CobaltArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public CobaltArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.COBALT_ARROW.get();
		}

		/**
		 * Constructor for CobaltArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public CobaltArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COBALT_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
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
			super(DeferredRegistryHandler.COBALT_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.COBALT_ARROW.get();
		}
	}

	public static class DiamondArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for DiamondArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public DiamondArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.DIAMOND_ARROW.get();
		}

		/**
		 * Constructor for DiamondArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public DiamondArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
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
			super(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.DIAMOND_ARROW.get();
		}
	}

	public static class GoldenArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for GoldArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public GoldenArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.GOLDEN_ARROW.get();
		}

		/**
		 * Constructor for GoldArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public GoldenArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.GOLDEN_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for GoldArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public GoldenArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.GOLDEN_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.GOLDEN_ARROW.get();
		}
	}

	public static class StoneArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for StoneArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public StoneArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.STONE_ARROW.get();
		}

		/**
		 * Constructor for StoneArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public StoneArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
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
			super(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.STONE_ARROW.get();
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
			Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(random.nextGaussian() * 0.0075F * inaccuracy, -0.1085F * inaccuracy, random.nextGaussian() * 0.0075F * inaccuracy).scale(velocity);
			setDeltaMovement(vector3d);
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			float yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			float xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}
	}

	public static class WoodenArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for WoodArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public WoodenArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.WOODEN_ARROW.get();
		}

		/**
		 * Constructor for WoodArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public WoodenArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.WOODEN_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
		}

		/**
		 * Constructor for WoodArrowEntity.
		 *
		 * @param worldIn the <code>World</code> the entity is in
		 * @param x       the X position
		 * @param y       the Y position
		 * @param z       the Z position
		 */
		public WoodenArrowEntity(Level worldIn, double x, double y, double z) {
			super(DeferredRegistryHandler.WOODEN_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.WOODEN_ARROW.get();
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

	public static class NetheriteArrowEntity extends AbstractCustomArrowEntity {

		/**
		 * Constructor for NetheriteArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public NetheriteArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.NETHERITE_ARROW.get();
		}

		/**
		 * Constructor for NetheriteArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public NetheriteArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
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
			super(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.NETHERITE_ARROW.get();
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

	public static class SmokeGrenadeArrowEntity extends AbstractCustomArrowEntity {

		private static int color = 0;
		private final int configMaxParticles = CommonConfig.MAX_SMOKE_GRENADE_PARTICLES.get();
		private static final byte VANILLA_IMPACT_STATUS_ID = 3;

		/**
		 * Constructor for SmokeGrenadeArrowEntity.
		 *
		 * @param type  the <code>EntityType</code> instance; must extend AbstractArrowEntity
		 * @param world the <code>World</code> the entity is in
		 */
		public SmokeGrenadeArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
			super(type, world);
			referenceItem = DeferredRegistryHandler.SMOKE_GRENADE_ARROW.get();
		}

		/**
		 * Constructor for SmokeGrenadeArrowEntity.
		 *
		 * @param shooter         the <code>LivingEntity</code> shooting the entity
		 * @param world           the <code>World</code> the entity is in
		 * @param referenceItemIn the reference item
		 */
		public SmokeGrenadeArrowEntity(LivingEntity shooter, Level world, Item referenceItemIn) {
			super(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_ENTITY.get(), shooter, world);
			referenceItem = referenceItemIn;
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
			super(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_ENTITY.get(), worldIn, x, y, z);
			referenceItem = DeferredRegistryHandler.SMOKE_GRENADE_ARROW.get();
		}

		/**
		 * Set the color of smoke.
		 *
		 * @param color an integer representing the color
		 */
		public static void setColor(int color) {
			SmokeGrenadeArrowEntity.color = color;
		}

		/**
		 * Runs when the entity hits something.
		 *
		 * @param rayTraceResult the <code>RayTraceResult</code> instance
		 */
		@Override
		public void onHit(@NotNull HitResult rayTraceResult) {
			super.onHit(rayTraceResult);
			if (!level.isClientSide) {
				PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())),
						new SmokeGrenadeArrowEntityPacketHandler(color));

				level.broadcastEntityEvent(this, VANILLA_IMPACT_STATUS_ID);
			}
		}

		/**
		 * Handle entity events.
		 *
		 * @param statusID the <code>byte</code> containing status ID
		 */
		@Override
		public void handleEntityEvent(byte statusID) {
			if (statusID == VANILLA_IMPACT_STATUS_ID) {
				double x = getX();
				double y = getY();
				double z = getZ();

				// Spawn smoke particles
				for (int i = 0; i < configMaxParticles; ++i) {
					level.addParticle(SmokeGrenadeParticleOptions.getParticleByColor(color),
							true, x, y, z,
							GeneralUtilities.getRandomNumber(-0.1d, 0.1d),
							GeneralUtilities.getRandomNumber(-0.1d, 0.1d),
							GeneralUtilities.getRandomNumber(-0.1d, 0.1d));
				}

				// Play a hissing sound
				level.playLocalSound(x, y, z, DeferredRegistryHandler.SMOKE_GRENADE_HISS.get(),
						SoundSource.NEUTRAL, 0.2f, 0.6f, true);
			}
		}
	}

	public record SmokeGrenadeArrowEntityPacketHandler(int color) {

		/**
		 * Constructor for SmokeGrenadeArrowEntityPacketHandler.
		 *
		 * @param color the color ID
		 */
		public SmokeGrenadeArrowEntityPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>SmokeGrenadeArrowEntityPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(SmokeGrenadeArrowEntityPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeInt(msg.color);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return SmokeGrenadeArrowEntityPacketHandler
		 */
		public static SmokeGrenadeArrowEntityPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new SmokeGrenadeArrowEntityPacketHandler(packetBuffer.readInt());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>SmokeGrenadeArrowEntityPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(SmokeGrenadeArrowEntityPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>SmokeGrenadeArrowEntityPacketHandler</code> message being sent
		 */
		private static void handleOnClient(SmokeGrenadeArrowEntityPacketHandler msg) {
			SmokeGrenadeArrowEntity.setColor(msg.color);
		}
	}
}