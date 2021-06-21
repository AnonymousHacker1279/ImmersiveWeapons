package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class BulletEntity {

	static boolean canBreakGlass = Config.BULLETS_BREAK_GLASS.get();

	public static class CopperBulletEntity extends AbstractArrowEntity {
		private final Item referenceItem;
		private final SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
		private BlockState inBlockState;
		/**
		 * Called when the arrow hits an entity
		 */

		private IntOpenHashSet piercedEntities;
		private List<Entity> hitEntities;
		private int knockbackStrength;
		private boolean hasAlreadyBrokeGlass = false;

		@SuppressWarnings("unchecked")
		public CopperBulletEntity(EntityType<?> type, World world, int knockbackStrength) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.knockbackStrength = knockbackStrength;
			this.referenceItem = DeferredRegistryHandler.COPPER_MUSKET_BALL.get();
		}

		public CopperBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.COPPER_BULLET_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
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
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F));
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

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
					this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.0355d, vector3d4.z);
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
			super.onHitEntity(p_213868_1_);
			Entity entity = p_213868_1_.getEntity();
			float f = (float) this.getDeltaMovement().length();
			int i = MathHelper.ceil(MathHelper.clamp(f * this.baseDamage, 0.0D, 2.147483647E9D));
			if (this.getPierceLevel() > 0) {
				if (this.piercedEntities == null) {
					this.piercedEntities = new IntOpenHashSet(5);
				}

				if (this.hitEntities == null) {
					this.hitEntities = Lists.newArrayListWithCapacity(5);
				}

				if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
					this.remove();
					return;
				}

				this.piercedEntities.add(entity.getId());
			}

			if (this.isCritArrow()) {
				long j = this.random.nextInt(i / 2 + 2);
				i = (int) Math.min(j + i, 2147483647L);
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource;
			if (entity1 == null) {
				damagesource = DamageSource.arrow(this, this);
			} else {
				damagesource = DamageSource.arrow(this, entity1);
				if (entity1 instanceof LivingEntity) {
					entity.invulnerableTime = 0;
					entity.setInvulnerable(false);
					((LivingEntity) entity1).setLastHurtMob(entity);
				}
			}

			boolean flag = entity.getType() == EntityType.ENDERMAN;
			int k = entity.getRemainingFireTicks();
			if (this.isOnFire() && !flag) {
				entity.setSecondsOnFire(5);
			}

			if (entity.hurt(damagesource, i)) {
				if (flag) {
					return;
				}

				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
						if (vector3d.lengthSqr() > 0.0D) {
							livingentity.push(vector3d.x, 0.1D, vector3d.z);
						}
					}

					if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					}

					this.doPostHurtEffects(livingentity);
					if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
					}

					if (!entity.isAlive() && this.hitEntities != null) {
						this.hitEntities.add(livingentity);
					}


				}

				this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				if (this.getPierceLevel() <= 0) {
					this.remove();
				}
			} else {
				entity.setRemainingFireTicks(k);
				if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.remove();
				}
			}
		}

		@Override
		protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
			this.inBlockState = this.level.getBlockState(blockStateRayTraceResult.getBlockPos());

			if (this.inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
				this.push(0, -0.1, 0);
				this.shakeTime = 4;
			} else {
				Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
				this.setDeltaMovement(vector3d);
				Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
				this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
				this.inGround = true;
				this.shakeTime = 2;
				this.setCritArrow(false);
				this.setPierceLevel((byte) 0);
				this.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
				this.resetPiercedEntities();
			}

			if (canBreakGlass && !this.hasAlreadyBrokeGlass && !this.inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && this.inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || this.inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
				this.level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
				this.hasAlreadyBrokeGlass = true;
			}
		}

		private void resetPiercedEntities() {
			if (this.hitEntities != null) {
				this.hitEntities.clear();
			}

			if (this.piercedEntities != null) {
				this.piercedEntities.clear();
			}

		}
	}

	public static class WoodBulletEntity extends AbstractArrowEntity {
		private final Item referenceItem;
		private final SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
		private BlockState inBlockState;
		/**
		 * Called when the arrow hits an entity
		 */

		private IntOpenHashSet piercedEntities;
		private List<Entity> hitEntities;
		private int knockbackStrength;
		private boolean hasAlreadyBrokeGlass = false;

		@SuppressWarnings("unchecked")
		public WoodBulletEntity(EntityType<?> type, World world, int knockbackStrength) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.knockbackStrength = knockbackStrength;
			this.referenceItem = DeferredRegistryHandler.WOOD_MUSKET_BALL.get();
		}

		public WoodBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.WOOD_BULLET_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
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
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.0075F * (GeneralUtilities.getRandomNumber(3.2f, 5.1f)), -0.0095F * (GeneralUtilities.getRandomNumber(3.2f, 5.1f)), this.random.nextGaussian() * 0.0075F).scale(velocity);
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
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F));
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

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
					this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.035d, vector3d4.z);
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
			super.onHitEntity(p_213868_1_);
			Entity entity = p_213868_1_.getEntity();
			float f = (float) this.getDeltaMovement().length();
			int i = MathHelper.ceil(MathHelper.clamp(f * this.baseDamage, 0.0D, 2.147483647E9D));
			if (this.getPierceLevel() > 0) {
				if (this.piercedEntities == null) {
					this.piercedEntities = new IntOpenHashSet(5);
				}

				if (this.hitEntities == null) {
					this.hitEntities = Lists.newArrayListWithCapacity(5);
				}

				if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
					this.remove();
					return;
				}

				this.piercedEntities.add(entity.getId());
			}

			if (this.isCritArrow()) {
				long j = this.random.nextInt(i / 2 + 2);
				i = (int) Math.min(j + i, 2147483647L);
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource;
			if (entity1 == null) {
				damagesource = DamageSource.arrow(this, this);
			} else {
				damagesource = DamageSource.arrow(this, entity1);
				if (entity1 instanceof LivingEntity) {
					entity.invulnerableTime = 0;
					entity.setInvulnerable(false);
					((LivingEntity) entity1).setLastHurtMob(entity);
				}
			}

			boolean flag = entity.getType() == EntityType.ENDERMAN;
			int k = entity.getRemainingFireTicks();
			if (this.isOnFire() && !flag) {
				entity.setSecondsOnFire(5);
			}

			if (entity.hurt(damagesource, i)) {
				if (flag) {
					return;
				}

				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
						if (vector3d.lengthSqr() > 0.0D) {
							livingentity.push(vector3d.x, 0.1D, vector3d.z);
						}
					}

					if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					}

					this.doPostHurtEffects(livingentity);
					if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
					}

					if (!entity.isAlive() && this.hitEntities != null) {
						this.hitEntities.add(livingentity);
					}


				}

				this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				if (this.getPierceLevel() <= 0) {
					this.remove();
				}
			} else {
				entity.setRemainingFireTicks(k);
				if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.remove();
				}
			}
		}

		@Override
		protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
			this.inBlockState = this.level.getBlockState(blockStateRayTraceResult.getBlockPos());

			if (this.inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
				this.push(0, -0.1, 0);
				this.shakeTime = 4;
			} else {
				Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
				this.setDeltaMovement(vector3d);
				Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
				this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
				this.inGround = true;
				this.shakeTime = 2;
				this.setCritArrow(false);
				this.setPierceLevel((byte) 0);
				this.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
				this.resetPiercedEntities();
			}

			if (canBreakGlass && !this.hasAlreadyBrokeGlass && !this.inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && this.inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || this.inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
				this.level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
				this.hasAlreadyBrokeGlass = true;
			}
		}

		private void resetPiercedEntities() {
			if (this.hitEntities != null) {
				this.hitEntities.clear();
			}

			if (this.piercedEntities != null) {
				this.piercedEntities.clear();
			}

		}
	}

	public static class StoneBulletEntity extends AbstractArrowEntity {
		private final Item referenceItem;
		private final SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
		private BlockState inBlockState;
		/**
		 * Called when the arrow hits an entity
		 */

		private IntOpenHashSet piercedEntities;
		private List<Entity> hitEntities;
		private int knockbackStrength;
		private boolean hasAlreadyBrokeGlass = false;

		@SuppressWarnings("unchecked")
		public StoneBulletEntity(EntityType<?> type, World world, int knockbackStrength) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.knockbackStrength = knockbackStrength;
			this.referenceItem = DeferredRegistryHandler.STONE_MUSKET_BALL.get();
		}

		public StoneBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.STONE_BULLET_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
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
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.0075F * (GeneralUtilities.getRandomNumber(2.4f, 4.1f)), -0.0170F * (GeneralUtilities.getRandomNumber(2.4f, 4.1f)), this.random.nextGaussian() * 0.0075F).scale(velocity);
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
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F));
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

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
					this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.020d, vector3d4.z);
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
			super.onHitEntity(p_213868_1_);
			Entity entity = p_213868_1_.getEntity();
			float f = (float) this.getDeltaMovement().length();
			int i = MathHelper.ceil(MathHelper.clamp(f * this.baseDamage, 0.0D, 2.147483647E9D));
			if (this.getPierceLevel() > 0) {
				if (this.piercedEntities == null) {
					this.piercedEntities = new IntOpenHashSet(5);
				}

				if (this.hitEntities == null) {
					this.hitEntities = Lists.newArrayListWithCapacity(5);
				}

				if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
					this.remove();
					return;
				}

				this.piercedEntities.add(entity.getId());
			}

			if (this.isCritArrow()) {
				long j = this.random.nextInt(i / 2 + 2);
				i = (int) Math.min(j + i, 2147483647L);
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource;
			if (entity1 == null) {
				damagesource = DamageSource.arrow(this, this);
			} else {
				damagesource = DamageSource.arrow(this, entity1);
				if (entity1 instanceof LivingEntity) {
					entity.invulnerableTime = 0;
					entity.setInvulnerable(false);
					((LivingEntity) entity1).setLastHurtMob(entity);
				}
			}

			boolean flag = entity.getType() == EntityType.ENDERMAN;
			int k = entity.getRemainingFireTicks();
			if (this.isOnFire() && !flag) {
				entity.setSecondsOnFire(5);
			}

			if (entity.hurt(damagesource, i)) {
				if (flag) {
					return;
				}

				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
						if (vector3d.lengthSqr() > 0.0D) {
							livingentity.push(vector3d.x, 0.1D, vector3d.z);
						}
					}

					if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					}

					this.doPostHurtEffects(livingentity);
					if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
					}

					if (!entity.isAlive() && this.hitEntities != null) {
						this.hitEntities.add(livingentity);
					}


				}

				this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				if (this.getPierceLevel() <= 0) {
					this.remove();
				}
			} else {
				entity.setRemainingFireTicks(k);
				if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.remove();
				}
			}
		}

		@Override
		protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
			this.inBlockState = this.level.getBlockState(blockStateRayTraceResult.getBlockPos());

			if (this.inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
				this.push(0, -0.1, 0);
				this.shakeTime = 4;
			} else {
				Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
				this.setDeltaMovement(vector3d);
				Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
				this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
				this.inGround = true;
				this.shakeTime = 2;
				this.setCritArrow(false);
				this.setPierceLevel((byte) 0);
				this.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
				this.resetPiercedEntities();
			}

			if (canBreakGlass && !this.hasAlreadyBrokeGlass && !this.inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && this.inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || this.inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
				this.level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
				this.hasAlreadyBrokeGlass = true;
			}
		}

		private void resetPiercedEntities() {
			if (this.hitEntities != null) {
				this.hitEntities.clear();
			}

			if (this.piercedEntities != null) {
				this.piercedEntities.clear();
			}

		}
	}

	public static class IronBulletEntity extends AbstractArrowEntity {
		private final Item referenceItem;
		private final SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
		private BlockState inBlockState;
		/**
		 * Called when the arrow hits an entity
		 */

		private IntOpenHashSet piercedEntities;
		private List<Entity> hitEntities;
		private int knockbackStrength;
		private boolean hasAlreadyBrokeGlass = false;

		@SuppressWarnings("unchecked")
		public IronBulletEntity(EntityType<?> type, World world, int knockbackStrength) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.knockbackStrength = knockbackStrength;
			this.referenceItem = DeferredRegistryHandler.IRON_MUSKET_BALL.get();
		}

		public IronBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.IRON_BULLET_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
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
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F));
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

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
					this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.0355d, vector3d4.z);
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
			super.onHitEntity(p_213868_1_);
			Entity entity = p_213868_1_.getEntity();
			float f = (float) this.getDeltaMovement().length();
			int i = MathHelper.ceil(MathHelper.clamp(f * this.baseDamage, 0.0D, 2.147483647E9D));
			if (this.getPierceLevel() > 0) {
				if (this.piercedEntities == null) {
					this.piercedEntities = new IntOpenHashSet(5);
				}

				if (this.hitEntities == null) {
					this.hitEntities = Lists.newArrayListWithCapacity(5);
				}

				if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
					this.remove();
					return;
				}

				this.piercedEntities.add(entity.getId());
			}

			if (this.isCritArrow()) {
				long j = this.random.nextInt(i / 2 + 2);
				i = (int) Math.min(j + i, 2147483647L);
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource;
			if (entity1 == null) {
				damagesource = DamageSource.arrow(this, this);
			} else {
				damagesource = DamageSource.arrow(this, entity1);
				if (entity1 instanceof LivingEntity) {
					entity.invulnerableTime = 0;
					entity.setInvulnerable(false);
					((LivingEntity) entity1).setLastHurtMob(entity);
				}
			}

			boolean flag = entity.getType() == EntityType.ENDERMAN;
			int k = entity.getRemainingFireTicks();
			if (this.isOnFire() && !flag) {
				entity.setSecondsOnFire(5);
			}

			if (entity.hurt(damagesource, i)) {
				if (flag) {
					return;
				}

				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
						if (vector3d.lengthSqr() > 0.0D) {
							livingentity.push(vector3d.x, 0.1D, vector3d.z);
						}
					}

					if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					}

					this.doPostHurtEffects(livingentity);
					if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
					}

					if (!entity.isAlive() && this.hitEntities != null) {
						this.hitEntities.add(livingentity);
					}


				}

				this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				if (this.getPierceLevel() <= 0) {
					this.remove();
				}
			} else {
				entity.setRemainingFireTicks(k);
				if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.remove();
				}
			}
		}

		@Override
		protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
			this.inBlockState = this.level.getBlockState(blockStateRayTraceResult.getBlockPos());

			if (this.inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
				this.push(0, -0.1, 0);
				this.shakeTime = 4;
			} else {
				Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
				this.setDeltaMovement(vector3d);
				Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
				this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
				this.inGround = true;
				this.shakeTime = 2;
				this.setCritArrow(false);
				this.setPierceLevel((byte) 0);
				this.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
				this.resetPiercedEntities();
			}

			if (canBreakGlass && !this.hasAlreadyBrokeGlass && !this.inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && this.inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || this.inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
				this.level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
				this.hasAlreadyBrokeGlass = true;
			}
		}

		private void resetPiercedEntities() {
			if (this.hitEntities != null) {
				this.hitEntities.clear();
			}

			if (this.piercedEntities != null) {
				this.piercedEntities.clear();
			}

		}
	}

	public static class GoldBulletEntity extends AbstractArrowEntity {
		private final Item referenceItem;
		private final SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
		private BlockState inBlockState;
		/**
		 * Called when the arrow hits an entity
		 */

		private IntOpenHashSet piercedEntities;
		private List<Entity> hitEntities;
		private int knockbackStrength;
		private boolean hasAlreadyBrokeGlass = false;

		@SuppressWarnings("unchecked")
		public GoldBulletEntity(EntityType<?> type, World world, int knockbackStrength) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.knockbackStrength = knockbackStrength;
			this.referenceItem = DeferredRegistryHandler.GOLD_MUSKET_BALL.get();
		}

		public GoldBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.GOLD_BULLET_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
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
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F));
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

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
					this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.0355d, vector3d4.z);
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
			super.onHitEntity(p_213868_1_);
			Entity entity = p_213868_1_.getEntity();
			float f = (float) this.getDeltaMovement().length();
			int i = MathHelper.ceil(MathHelper.clamp(f * this.baseDamage, 0.0D, 2.147483647E9D));
			if (this.getPierceLevel() > 0) {
				if (this.piercedEntities == null) {
					this.piercedEntities = new IntOpenHashSet(5);
				}

				if (this.hitEntities == null) {
					this.hitEntities = Lists.newArrayListWithCapacity(5);
				}

				if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
					this.remove();
					return;
				}

				this.piercedEntities.add(entity.getId());
			}

			if (this.isCritArrow()) {
				long j = this.random.nextInt(i / 2 + 2);
				i = (int) Math.min(j + i, 2147483647L);
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource;
			if (entity1 == null) {
				damagesource = DamageSource.arrow(this, this);
			} else {
				damagesource = DamageSource.arrow(this, entity1);
				if (entity1 instanceof LivingEntity) {
					entity.invulnerableTime = 0;
					entity.setInvulnerable(false);
					((LivingEntity) entity1).setLastHurtMob(entity);
				}
			}

			boolean flag = entity.getType() == EntityType.ENDERMAN;
			int k = entity.getRemainingFireTicks();
			if (this.isOnFire() && !flag) {
				entity.setSecondsOnFire(5);
			}

			if (entity.hurt(damagesource, i)) {
				if (flag) {
					return;
				}

				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
						if (vector3d.lengthSqr() > 0.0D) {
							livingentity.push(vector3d.x, 0.1D, vector3d.z);
						}
					}

					if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					}

					this.doPostHurtEffects(livingentity);
					if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
					}

					if (!entity.isAlive() && this.hitEntities != null) {
						this.hitEntities.add(livingentity);
					}


				}

				this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				if (this.getPierceLevel() <= 0) {
					this.remove();
				}
			} else {
				entity.setRemainingFireTicks(k);
				if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.remove();
				}
			}
		}

		@Override
		protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
			this.inBlockState = this.level.getBlockState(blockStateRayTraceResult.getBlockPos());

			if (this.inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
				this.push(0, -0.1, 0);
				this.shakeTime = 4;
			} else {
				Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
				this.setDeltaMovement(vector3d);
				Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
				this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
				this.inGround = true;
				this.shakeTime = 2;
				this.setCritArrow(false);
				this.setPierceLevel((byte) 0);
				this.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
				this.resetPiercedEntities();
			}

			if (canBreakGlass && !this.hasAlreadyBrokeGlass && !this.inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && this.inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || this.inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
				this.level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
				this.hasAlreadyBrokeGlass = true;
			}
		}

		private void resetPiercedEntities() {
			if (this.hitEntities != null) {
				this.hitEntities.clear();
			}

			if (this.piercedEntities != null) {
				this.piercedEntities.clear();
			}

		}
	}

	public static class DiamondBulletEntity extends AbstractArrowEntity {
		private final Item referenceItem;
		private final SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
		private BlockState inBlockState;
		/**
		 * Called when the arrow hits an entity
		 */

		private IntOpenHashSet piercedEntities;
		private List<Entity> hitEntities;
		private int knockbackStrength;
		private boolean hasAlreadyBrokeGlass = false;

		@SuppressWarnings("unchecked")
		public DiamondBulletEntity(EntityType<?> type, World world, int knockbackStrength) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.knockbackStrength = knockbackStrength;
			this.referenceItem = DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get();
		}

		public DiamondBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.DIAMOND_BULLET_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
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
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.0025F * (GeneralUtilities.getRandomNumber(0.2f, 0.9f)), 0.00025F * (GeneralUtilities.getRandomNumber(0.2f, 0.9f)), this.random.nextGaussian() * 0.0025F).scale(velocity);
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
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F));
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

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
					this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.0385d, vector3d4.z);
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
			super.onHitEntity(p_213868_1_);
			Entity entity = p_213868_1_.getEntity();
			float f = (float) this.getDeltaMovement().length();
			int i = MathHelper.ceil(MathHelper.clamp(f * this.baseDamage, 0.0D, 2.147483647E9D));
			if (this.getPierceLevel() > 0) {
				if (this.piercedEntities == null) {
					this.piercedEntities = new IntOpenHashSet(5);
				}

				if (this.hitEntities == null) {
					this.hitEntities = Lists.newArrayListWithCapacity(5);
				}

				if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
					this.remove();
					return;
				}

				this.piercedEntities.add(entity.getId());
			}

			if (this.isCritArrow()) {
				long j = this.random.nextInt(i / 2 + 2);
				i = (int) Math.min(j + i, 2147483647L);
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource;
			if (entity1 == null) {
				damagesource = DamageSource.arrow(this, this);
			} else {
				damagesource = DamageSource.arrow(this, entity1);
				if (entity1 instanceof LivingEntity) {
					entity.invulnerableTime = 0;
					entity.setInvulnerable(false);
					((LivingEntity) entity1).setLastHurtMob(entity);
				}
			}

			boolean flag = entity.getType() == EntityType.ENDERMAN;
			int k = entity.getRemainingFireTicks();
			if (this.isOnFire() && !flag) {
				entity.setSecondsOnFire(5);
			}

			if (entity.hurt(damagesource, i)) {
				if (flag) {
					return;
				}

				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
						if (vector3d.lengthSqr() > 0.0D) {
							livingentity.push(vector3d.x, 0.1D, vector3d.z);
						}
					}

					if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					}

					this.doPostHurtEffects(livingentity);
					if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
					}

					if (!entity.isAlive() && this.hitEntities != null) {
						this.hitEntities.add(livingentity);
					}


				}

				this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				if (this.getPierceLevel() <= 0) {
					this.remove();
				}
			} else {
				entity.setRemainingFireTicks(k);
				if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.remove();
				}
			}
		}

		@Override
		protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
			this.inBlockState = this.level.getBlockState(blockStateRayTraceResult.getBlockPos());

			if (this.inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
				this.push(0, -0.1, 0);
				this.shakeTime = 4;
			} else {
				Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
				this.setDeltaMovement(vector3d);
				Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
				this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
				this.inGround = true;
				this.shakeTime = 2;
				this.setCritArrow(false);
				this.setPierceLevel((byte) 0);
				this.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
				this.resetPiercedEntities();
			}

			if (canBreakGlass && !this.hasAlreadyBrokeGlass && !this.inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && this.inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || this.inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
				this.level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
				this.hasAlreadyBrokeGlass = true;
			}
		}

		private void resetPiercedEntities() {
			if (this.hitEntities != null) {
				this.hitEntities.clear();
			}

			if (this.piercedEntities != null) {
				this.piercedEntities.clear();
			}

		}
	}

	public static class NetheriteBulletEntity extends AbstractArrowEntity {
		private final Item referenceItem;
		private final SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
		private BlockState inBlockState;
		/**
		 * Called when the arrow hits an entity
		 */

		private IntOpenHashSet piercedEntities;
		private List<Entity> hitEntities;
		private int knockbackStrength;
		private boolean hasAlreadyBrokeGlass = false;

		@SuppressWarnings("unchecked")
		public NetheriteBulletEntity(EntityType<?> type, World world, int knockbackStrength) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.knockbackStrength = knockbackStrength;
			this.referenceItem = DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get();
		}

		public NetheriteBulletEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.NETHERITE_BULLET_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
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
			Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.0020F * (GeneralUtilities.getRandomNumber(0.2f, 0.7f)), 0.0020F * (GeneralUtilities.getRandomNumber(0.2f, 0.7f)), this.random.nextGaussian() * 0.0020F).scale(velocity);
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
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F));
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

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
					this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.04d, vector3d4.z);
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
			super.onHitEntity(p_213868_1_);
			Entity entity = p_213868_1_.getEntity();
			float f = (float) this.getDeltaMovement().length();
			int i = MathHelper.ceil(MathHelper.clamp(f * this.baseDamage, 0.0D, 2.147483647E9D));
			if (this.getPierceLevel() > 0) {
				if (this.piercedEntities == null) {
					this.piercedEntities = new IntOpenHashSet(5);
				}

				if (this.hitEntities == null) {
					this.hitEntities = Lists.newArrayListWithCapacity(5);
				}

				if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
					this.remove();
					return;
				}

				this.piercedEntities.add(entity.getId());
			}

			if (this.isCritArrow()) {
				long j = this.random.nextInt(i / 2 + 2);
				i = (int) Math.min(j + i, 2147483647L);
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource;
			if (entity1 == null) {
				damagesource = DamageSource.arrow(this, this);
			} else {
				damagesource = DamageSource.arrow(this, entity1);
				if (entity1 instanceof LivingEntity) {
					entity.invulnerableTime = 0;
					entity.setInvulnerable(false);
					((LivingEntity) entity1).setLastHurtMob(entity);
				}
			}

			boolean flag = entity.getType() == EntityType.ENDERMAN;
			int k = entity.getRemainingFireTicks();
			if (this.isOnFire() && !flag) {
				entity.setSecondsOnFire(5);
			}

			if (entity.hurt(damagesource, i)) {
				if (flag) {
					return;
				}

				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
						if (vector3d.lengthSqr() > 0.0D) {
							livingentity.push(vector3d.x, 0.1D, vector3d.z);
						}
					}

					if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					}

					this.doPostHurtEffects(livingentity);
					if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
					}

					if (!entity.isAlive() && this.hitEntities != null) {
						this.hitEntities.add(livingentity);
					}


				}

				this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				if (this.getPierceLevel() <= 0) {
					this.remove();
				}
			} else {
				entity.setRemainingFireTicks(k);
				if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.remove();
				}
			}
		}

		@Override
		protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
			this.inBlockState = this.level.getBlockState(blockStateRayTraceResult.getBlockPos());

			if (this.inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
				this.push(0, -0.1, 0);
				this.shakeTime = 4;
			} else {
				Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
				this.setDeltaMovement(vector3d);
				Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
				this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
				this.inGround = true;
				this.shakeTime = 2;
				this.setCritArrow(false);
				this.setPierceLevel((byte) 0);
				this.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
				this.resetPiercedEntities();
			}

			if (canBreakGlass && !this.hasAlreadyBrokeGlass && !this.inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && this.inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || this.inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
				this.level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
				this.hasAlreadyBrokeGlass = true;
			}
		}

		private void resetPiercedEntities() {
			if (this.hitEntities != null) {
				this.hitEntities.clear();
			}

			if (this.piercedEntities != null) {
				this.piercedEntities.clear();
			}

		}
	}

	@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
	public static class FlareEntity extends AbstractArrowEntity implements IRendersAsItem {
		private final Item referenceItem;
		private final SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
		private BlockState inBlockState;
		private IntOpenHashSet piercedEntities;
		private List<Entity> hitEntities;
		private int knockbackStrength;
		private boolean hasAlreadyBrokeGlass = false;
		private int explodeDelay = 10;
		private int deathDelay = 300;

		@SuppressWarnings("unchecked")
		public FlareEntity(EntityType<?> type, World world, int knockbackStrength) {
			super((EntityType<? extends AbstractArrowEntity>) type, world);
			this.knockbackStrength = knockbackStrength;
			this.referenceItem = DeferredRegistryHandler.FLARE.get();
		}

		public FlareEntity(LivingEntity shooter, World world, Item referenceItemIn) {
			super(DeferredRegistryHandler.FLARE_ENTITY.get(), shooter, world);
			this.referenceItem = referenceItemIn;
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
			this.setDeltaMovement(vector3d.multiply(this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F, this.random.nextFloat() * 0.4F));
		}

		@Override
		public boolean shouldRenderAtSqrDistance(double distance) {
			return distance < 4096.0D;
		}

		@Override
		public boolean shouldRender(double x, double y, double z) {
			return super.shouldRender(x, y, z);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void tick() {
			super.tick();

			if (this.level.isClientSide && explodeDelay != 0) {
				this.level.addParticle(ParticleTypes.FIREWORK, this.getX(), this.getY() - 0.3D, this.getZ(), this.random.nextGaussian() * 0.05D, -this.getDeltaMovement().y * 0.5D, this.random.nextGaussian() * 0.05D);
			}

			boolean shouldStopMoving = false;
			if (explodeDelay >= 0) {
				explodeDelay--;
			} else {
				if (deathDelay >= 0) {
					shouldStopMoving = true;
					if (this.level.isClientSide) {
						for (int i = 0; i <= 8; i++) {
							this.level.addAlwaysVisibleParticle(ParticleTypes.FLAME, true, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.1D, -this.getDeltaMovement().y * 0.25D, this.random.nextGaussian() * 0.1D);
							this.level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, true, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.1D, -this.getDeltaMovement().y * 0.25D, this.random.nextGaussian() * 0.1D);
						}
					}
					deathDelay--;
				} else {
					this.remove();
				}
			}

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

					if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
					if (shouldStopMoving) {
						this.setDeltaMovement(0, 0, 0);
					} else {
						this.setDeltaMovement(vector3d4.x, vector3d4.y + 0.0355d, vector3d4.z);
					}
				}

				this.setPos(d5, d1, d2);
				this.checkInsideBlocks();
			}
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
			super.onHitEntity(p_213868_1_);
			Entity entity = p_213868_1_.getEntity();
			float f = (float) this.getDeltaMovement().length();
			int i = MathHelper.ceil(MathHelper.clamp(f * this.baseDamage, 0.0D, 2.147483647E9D));
			if (this.getPierceLevel() > 0) {
				if (this.piercedEntities == null) {
					this.piercedEntities = new IntOpenHashSet(5);
				}

				if (this.hitEntities == null) {
					this.hitEntities = Lists.newArrayListWithCapacity(5);
				}

				if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
					this.remove();
					return;
				}

				this.piercedEntities.add(entity.getId());
			}

			if (this.isCritArrow()) {
				long j = this.random.nextInt(i / 2 + 2);
				i = (int) Math.min(j + i, 2147483647L);
			}

			Entity entity1 = this.getOwner();
			DamageSource damagesource;
			if (entity1 == null) {
				damagesource = DamageSource.arrow(this, this);
			} else {
				damagesource = DamageSource.arrow(this, entity1);
				if (entity1 instanceof LivingEntity) {
					entity.invulnerableTime = 0;
					entity.setInvulnerable(false);
					entity.setSecondsOnFire(6);
					((LivingEntity) entity1).setLastHurtMob(entity);
				}
			}

			boolean flag = entity.getType() == EntityType.ENDERMAN;
			int k = entity.getRemainingFireTicks();
			if (this.isOnFire() && !flag) {
				entity.setSecondsOnFire(5);
			}

			if (entity.hurt(damagesource, i)) {
				if (flag) {
					return;
				}

				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;

					if (this.knockbackStrength > 0) {
						Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
						if (vector3d.lengthSqr() > 0.0D) {
							livingentity.push(vector3d.x, 0.1D, vector3d.z);
						}
					}

					if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
						EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
						EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					}

					this.doPostHurtEffects(livingentity);
					if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
						((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
					}

					if (!entity.isAlive() && this.hitEntities != null) {
						this.hitEntities.add(livingentity);
					}


				}

				this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				if (this.getPierceLevel() <= 0) {
					this.remove();
				}
			} else {
				entity.setRemainingFireTicks(k);
				if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
					this.remove();
				}
			}
		}

		@Override
		protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
			this.inBlockState = this.level.getBlockState(blockStateRayTraceResult.getBlockPos());

			if (this.inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
				this.push(0, -0.1, 0);
				this.shakeTime = 4;
			} else {
				Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
				this.setDeltaMovement(vector3d);
				Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
				this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
				this.inGround = true;
				this.shakeTime = 2;
				this.setCritArrow(false);
				this.setPierceLevel((byte) 0);
				this.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
				this.resetPiercedEntities();
			}

			if (canBreakGlass && !this.hasAlreadyBrokeGlass && !this.inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && this.inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || this.inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
				this.level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
				this.hasAlreadyBrokeGlass = true;
			}
		}

		private void resetPiercedEntities() {
			if (this.hitEntities != null) {
				this.hitEntities.clear();
			}

			if (this.piercedEntities != null) {
				this.piercedEntities.clear();
			}

		}

		@Override
		public ItemStack getItem() {
			return new ItemStack(DeferredRegistryHandler.FLARE.get());
		}
	}
}