package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.awt.*;

public class SmokeBombEntity extends ProjectileItemEntity {

	private static final byte VANILLA_IMPACT_STATUS_ID = 3;
	private static String color;
	private final int configMaxParticles = Config.MAX_SMOKE_BOMB_PARTICLES.get();
	// We hit something (entity or block).
	Minecraft mc = Minecraft.getInstance();

	public SmokeBombEntity(EntityType<? extends SmokeBombEntity> entityType, World world) {
		super(entityType, world);
	}

	public SmokeBombEntity(World world, LivingEntity livingEntity) {
		super(DeferredRegistryHandler.SMOKE_BOMB_ENTITY.get(), livingEntity, world);
	}

	public SmokeBombEntity(World world, double x, double y, double z) {
		super(DeferredRegistryHandler.SMOKE_BOMB_ENTITY.get(), x, y, z, world);
	}

	public static void setColor(String color) {
		SmokeBombEntity.color = color;
	}

	// If you forget to override this method, the default vanilla method will be called.
	// This sends a vanilla spawn packet, which is then silently discarded when it reaches the client.
	//  Your entity will be present on the server and can cause effects, but the client will not have a copy of the entity
	//    and hence it will not render.
	@Nonnull
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	// ProjectileItemEntity::setItem uses this to save storage space.  It only stores the itemStack if the itemStack is not
	//   the default item.
	@Override
	protected Item getDefaultItem() {
		return DeferredRegistryHandler.SMOKE_BOMB.get();
	}

	@Override
	protected void onHit(RayTraceResult rayTraceResult) {
		if (!this.level.isClientSide) {
			PlayerEntity playerEntity = mc.player;
			this.level.broadcastEntityEvent(this, VANILLA_IMPACT_STATUS_ID);  // calls handleStatusUpdate which tells the client to render particles
			this.level.playSound(playerEntity, this.blockPosition(), DeferredRegistryHandler.SMOKE_BOMB_HISS.get(), SoundCategory.NEUTRAL, 0.1f, 0.6f);
		}
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void handleEntityEvent(byte statusID) {
		if (statusID == VANILLA_IMPACT_STATUS_ID) {
			IParticleData particleData = this.makeParticle();

			for (int i = 0; i < configMaxParticles; ++i) {
				this.level.addParticle(particleData, true, this.getX(), this.getY(), this.getZ(), GeneralUtilities.getRandomNumber(-0.03, 0.03d), GeneralUtilities.getRandomNumber(-0.02d, 0.02d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
			}
			this.remove();
		}
	}

	private IParticleData makeParticle() {
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

		switch (SmokeBombEntity.color) {
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
}