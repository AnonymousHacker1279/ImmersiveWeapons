package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import java.awt.Color;

import javax.annotation.Nonnull;

import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MolotovEntity extends ProjectileItemEntity {

	public MolotovEntity(EntityType<? extends MolotovEntity> entityType, World world) {
	    super(entityType, world);
	  }

	public MolotovEntity(World world, LivingEntity livingEntity) {
		super(DeferredRegistryHandler.MOLOTOV_COCKTAIL_ENTITY.get(), livingEntity, world);
	}

	public MolotovEntity(World world, double x, double y, double z) {
		super(DeferredRegistryHandler.MOLOTOV_COCKTAIL_ENTITY.get(), x, y, z, world);
	}

	// If you forget to override this method, the default vanilla method will be called.
	// This sends a vanilla spawn packet, which is then silently discarded when it reaches the client.
	//  Your entity will be present on the server and can cause effects, but the client will not have a copy of the entity
	//    and hence it will not render.
	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	// ProjectileItemEntity::setItem uses this to save storage space.  It only stores the itemStack if the itemStack is not
	//   the default item.
	@Override
	protected Item getDefaultItem() {
		return DeferredRegistryHandler.MOLOTOV_COCKTAIL.get();
	}
	
	private boolean hasAlreadySetFire = false;
	
	// We hit something (entity or block).
	Minecraft mc = Minecraft.getInstance();
	@Override
	protected void onImpact(RayTraceResult rayTraceResult) {
		if (!this.world.isRemote) {
			PlayerEntity playerEntity = mc.player;
			this.world.setEntityState(this, VANILLA_IMPACT_STATUS_ID);  // calls handleStatusUpdate which tells the client to render particles
			if (!this.hasAlreadySetFire) {
				// Create a ring of fire around the point of impact
				this.world.playSound(playerEntity, this.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 1f, 1f);
				if(this.world.getBlockState(this.getPosition()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().up()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().up(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().east()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().east(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().east(2)) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().east(2), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().east().north()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().east().north(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().east().south()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().east().south(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().west()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().west(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().west(2)) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().west(2), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().west().south()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().west().south(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().west().north()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().west().north(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().north()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().north(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().north(2)) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().north(2), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().south()) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().south(), Blocks.FIRE.getDefaultState());
				if(this.world.getBlockState(this.getPosition().south(2)) == Blocks.AIR.getDefaultState()) this.world.setBlockState(this.getPosition().south(2), Blocks.FIRE.getDefaultState());
				this.hasAlreadySetFire = true;
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
	}

	private static final byte VANILLA_IMPACT_STATUS_ID = 3;
	  
	@Override
	public void handleStatusUpdate(byte statusID) {
		if (statusID == VANILLA_IMPACT_STATUS_ID) {
			IParticleData particleData = this.makeParticle();
	    	
			for(int i = 0; i < 2; ++i) { // Create a few smoke particles, like the smoke bomb
				this.world.addParticle(particleData, true, this.getPosX(), this.getPosY(), this.getPosZ(), GeneralUtilities.getRandomNumber(-0.02, 0.02d), GeneralUtilities.getRandomNumber(-0.02d, 0.02d), GeneralUtilities.getRandomNumber(-0.02d, 0.02d));
			}
			this.remove();
	    }
	  }

	  private IParticleData makeParticle() {
		  Color tint = getTint(GeneralUtilities.getRandomNumber(0, 2));
		  double diameter = getDiameter(GeneralUtilities.getRandomNumber(0.2d, 0.4d));
		  SmokeBombParticleData smokeBombParticleData = new SmokeBombParticleData(tint, diameter);
		  
		  return smokeBombParticleData;
	  }
	  
	  private Color getTint(int random) {
		  Color [] tints = {
				  new Color(1.00f, 1.00f, 1.00f),  // no tint (white)
				  new Color(1.00f, 0.97f, 1.00f),  // off white
				  new Color(1.00f, 1.00f, 0.97f),  // off white 2: electric boogaloo
		  };
		  
		  return tints[random];
	  }
	  
	  private double getDiameter(double random) {
		    final double MIN_DIAMETER = 0.01;
		    final double MAX_DIAMETER = 5.5;
		    return MIN_DIAMETER + (MAX_DIAMETER - MIN_DIAMETER) * random;
	  }
}
