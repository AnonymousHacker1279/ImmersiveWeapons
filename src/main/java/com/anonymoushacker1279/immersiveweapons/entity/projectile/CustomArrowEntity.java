package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import java.awt.Color;

import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;

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
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CustomArrowEntity {
	
	private static float getRandomNumber(float min, float max) {
	    return (float) ((Math.random() * (max - min)) + min);
	}
	
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
	    public ItemStack getArrowStack() {
	        return new ItemStack(this.referenceItem);
	    }
		
		@Override
		public IPacket<?> createSpawnPacket() {
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
	    public ItemStack getArrowStack() {
	        return new ItemStack(this.referenceItem);
	    }
		
		@Override
		public IPacket<?> createSpawnPacket() {
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
	    public ItemStack getArrowStack() {
	        return new ItemStack(this.referenceItem);
	    }
		
		@Override
		public IPacket<?> createSpawnPacket() {
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
	    public ItemStack getArrowStack() {
	        return new ItemStack(this.referenceItem);
	    }
		
		@Override
		public IPacket<?> createSpawnPacket() {
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
	    public ItemStack getArrowStack() {
	        return new ItemStack(this.referenceItem);
	    }
		
		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
		
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * 0.0075F * inaccuracy, -0.1085F * inaccuracy, this.rand.nextGaussian() * 0.0075F * inaccuracy).scale(velocity);
		    this.setMotion(vector3d);
		    float f = MathHelper.sqrt(horizontalMag(vector3d));
		    this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float)Math.PI));
		    this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, f) * (180F / (float)Math.PI));
		    this.prevRotationYaw = this.rotationYaw;
		    this.prevRotationPitch = this.rotationPitch;
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
	    public ItemStack getArrowStack() {
	        return new ItemStack(this.referenceItem);
	    }
		
		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
		
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			super.shoot(x, y, z, velocity, (inaccuracy + getRandomNumber(5.8f, 7.2f)));
		}
	}
	
	public static class NetheriteArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;
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
	    public ItemStack getArrowStack() {
	        return new ItemStack(this.referenceItem);
	    }
		
		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
		
		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * 0.0025F * (getRandomNumber(0.2f, 1.1f)), 0.00025F * (getRandomNumber(0.2f, 1.1f)), this.rand.nextGaussian() * 0.0025F).scale(velocity);
		    this.setMotion(vector3d);
		    float f = MathHelper.sqrt(horizontalMag(vector3d));
		    this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float)Math.PI));
		    this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, f) * (180F / (float)Math.PI));
		    this.prevRotationYaw = this.rotationYaw;
		    this.prevRotationPitch = this.rotationPitch;
		}
		
		private BlockState inBlockState;
		
		private boolean func_234593_u_() {
		      return this.inGround && this.world.hasNoCollisions((new AxisAlignedBB(this.getPositionVec(), this.getPositionVec())).grow(0.06D));
		}
		private void func_234594_z_() {
		      this.inGround = false;
		      Vector3d vector3d = this.getMotion();
		      this.setMotion(vector3d.mul(this.rand.nextFloat() * 0.2F, this.rand.nextFloat() * 0.2F, this.rand.nextFloat() * 0.2F));
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void tick() {
		      super.tick();
		      boolean flag = this.getNoClip();
		      Vector3d vector3d = this.getMotion();
		      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
		         float f = MathHelper.sqrt(horizontalMag(vector3d));
		         this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float)Math.PI));
		         this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, f) * (180F / (float)Math.PI));
		         this.prevRotationYaw = this.rotationYaw;
		         this.prevRotationPitch = this.rotationPitch;
		      }

		      BlockPos blockpos = this.getPosition();
		      BlockState blockstate = this.world.getBlockState(blockpos);
		      if (!blockstate.isAir(this.world, blockpos) && !flag) {
		         VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
		         if (!voxelshape.isEmpty()) {
		            Vector3d vector3d1 = this.getPositionVec();

		            for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
		               if (axisalignedbb.offset(blockpos).contains(vector3d1)) {
		                  this.inGround = true;
		                  break;
		               }
		            }
		         }
		      }

		      if (this.arrowShake > 0) {
		         --this.arrowShake;
		      }

		      if (this.isWet()) {
		         this.extinguish();
		      }

		      if (this.inGround && !flag) {
		         if (this.inBlockState != blockstate && this.func_234593_u_()) {
		            this.func_234594_z_();
		         } else if (!this.world.isRemote) {
		            this.func_225516_i_();
		         }

		         ++this.timeInGround;
		      } else {
		         this.timeInGround = 0;
		         Vector3d vector3d2 = this.getPositionVec();
		         Vector3d vector3d3 = vector3d2.add(vector3d);
		         RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
		         if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
		            vector3d3 = raytraceresult.getHitVec();
		         }

		         while(!this.removed) {
		            EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(vector3d2, vector3d3);
		            if (entityraytraceresult != null) {
		               raytraceresult = entityraytraceresult;
		            }

		            if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
		               Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
		               Entity entity1 = this.func_234616_v_();
		               if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
		                  raytraceresult = null;
		                  entityraytraceresult = null;
		               }
		            }

		            if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
		               this.onImpact(raytraceresult);
		               this.isAirBorne = true;
		            }

		            if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
		               break;
		            }

		            raytraceresult = null;
		         }

		         vector3d = this.getMotion();
		         double d3 = vector3d.x;
		         double d4 = vector3d.y;
		         double d0 = vector3d.z;
		         if (this.getIsCritical()) {
		            for(int i = 0; i < 4; ++i) {
		               this.world.addParticle(ParticleTypes.CRIT, this.getPosX() + d3 * i / 4.0D, this.getPosY() + d4 * i / 4.0D, this.getPosZ() + d0 * i / 4.0D, -d3, -d4 + 0.2D, -d0);
		            }
		         }

		         double d5 = this.getPosX() + d3;
		         double d1 = this.getPosY() + d4;
		         double d2 = this.getPosZ() + d0;
		         float f1 = MathHelper.sqrt(horizontalMag(vector3d));
		         if (flag) {
		            this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * (180F / (float)Math.PI));
		         } else {
		            this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (180F / (float)Math.PI));
		         }

		         this.rotationPitch = (float)(MathHelper.atan2(d4, f1) * (180F / (float)Math.PI));
		         this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
		         this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
		         float f2 = 0.99F;
		         if (this.isInWater()) {
		            for(int j = 0; j < 4; ++j) {
		               this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
		            }

		            f2 = this.getWaterDrag();
		         }

		         this.setMotion(vector3d.scale(f2));
		         if (!this.hasNoGravity() && !flag) {
		            Vector3d vector3d4 = this.getMotion();
		            this.setMotion(vector3d4.x, vector3d4.y + 0.0455d, vector3d4.z);
		         }

		         this.setPosition(d5, d1, d2);
		         this.doBlockCollisions();
		      }
		}
	}

	public static class SmokeBombArrowEntity extends AbstractArrowEntity {
		private final Item referenceItem;
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

		@Override
	    public ItemStack getArrowStack() {
	        return new ItemStack(this.referenceItem);
	    }
		
		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
		
		Minecraft mc = Minecraft.getInstance();
		private int configMaxParticles = Config.MAX_SMOKE_BOMB_PARTICLES.get();
		
		protected IParticleData makeParticle() {
			  Color tint = getTint(getRandomNumber(0, 2));
			  double diameter = getDiameter(getRandomNumber(1.0d, 5.5d));
			  SmokeBombParticleData smokeBombParticleData = new SmokeBombParticleData(tint, diameter);
			  
			  return smokeBombParticleData;
		}
		
		private double getRandomNumber(double min, double max) {
			return Math.random() * (max - min) + min;
		}
		private int getRandomNumber(int min, int max) {
		    return (int) ((Math.random() * (max - min)) + min);
		}
		
		private static String color;
		  
		public static void setColor(String color) {
			SmokeBombArrowEntity.color = color;
		}
		
		private Color getTint(int random) {
			  Color [] tints = {
					  new Color(1.00f, 1.00f, 1.00f),  // no tint (white)
					  new Color(1.00f, 0.97f, 1.00f),  // off white
					  new Color(1.00f, 1.00f, 0.97f),  // off white 2: electric boogaloo
			  };
			  Color [] tintsRed = {
					  new Color(1.00f, 0.25f, 0.25f),  // tint (red)
					  new Color(1.00f, 0.30f, 0.25f),  // off red
					  new Color(1.00f, 0.25f, 0.30f),  // off red 2: electric boogaloo
			  };
			  Color [] tintsGreen = {
					  new Color(0.25f, 1.00f, 0.25f),  // tint (green)
					  new Color(0.30f, 1.00f, 0.25f),  // off green
					  new Color(0.25f, 1.00f, 0.30f),  // off green 2: electric boogaloo
			  };
			  Color [] tintsBlue = {
					  new Color(0.25f, 0.25f, 1.00f),  // tint (blue)
					  new Color(0.30f, 0.25f, 1.00f),  // off blue
					  new Color(0.25f, 0.30f, 1.00f),  // off blue 2: electric boogaloo
			  };
			  Color [] tintsPurple = {
					  new Color(1.00f, 0.25f, 1.00f),  // tint (purple)
					  new Color(1.00f, 0.30f, 1.00f),  // off purple
					  new Color(1.00f, 0.35f, 1.00f),  // off purple 2: electric boogaloo
			  };
			  Color [] tintsYellow = {
					  new Color(1.00f, 1.00f, 0.25f),  // tint (yellow)
					  new Color(1.00f, 1.00f, 0.30f),  // off yellow
					  new Color(1.00f, 1.00f, 0.35f),  // off yellow 2: electric boogaloo
			  };
			  
			  if (SmokeBombArrowEntity.color == "none") {
				  return tints[random];
			  } else if (SmokeBombArrowEntity.color == "red") { 
				  return tintsRed[random];
			  } else if (SmokeBombArrowEntity.color == "green") { 
				  return tintsGreen[random];
			  } else if (SmokeBombArrowEntity.color == "blue") { 
				  return tintsBlue[random];
			  } else if (SmokeBombArrowEntity.color == "purple") { 
				  return tintsPurple[random];
			  } else if (SmokeBombArrowEntity.color == "yellow") { 
				  return tintsYellow[random];
			  } else {
				  return tints[random];
			  }
		  }
		  
		  private double getDiameter(double random) {
			    final double MIN_DIAMETER = 0.5;
			    final double MAX_DIAMETER = 5.5;
			    return MIN_DIAMETER + (MAX_DIAMETER - MIN_DIAMETER) * random;
		  }
		
		@Override
		public void onImpact(RayTraceResult rayTraceResult) {
			if (!hasAlreadyImpacted) {
				hasAlreadyImpacted = true;
				IParticleData particleData = this.makeParticle();
				this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), DeferredRegistryHandler.SMOKE_BOMB_HISS.get(), SoundCategory.NEUTRAL, 0.1f, 0.6f, true);
				
				for(int i = 0; i < configMaxParticles; ++i) {
		    		this.world.addParticle(particleData, true, this.getPosX(), this.getPosY(), this.getPosZ(), getRandomNumber(-0.03, 0.03d), getRandomNumber(-0.02d, 0.02d), getRandomNumber(-0.03d, 0.03d));
		    	}
				
				RayTraceResult.Type raytraceresult$type = rayTraceResult.getType();
				if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
					this.onEntityHit((EntityRayTraceResult)rayTraceResult);
				} else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
					this.func_230299_a_((BlockRayTraceResult)rayTraceResult);
				}
			}
		}
	}
}
