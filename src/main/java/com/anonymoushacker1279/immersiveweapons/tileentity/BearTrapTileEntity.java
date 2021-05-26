package com.anonymoushacker1279.immersiveweapons.tileentity;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

public class BearTrapTileEntity extends TileEntity implements ITickableTileEntity {

	public static final DamageSource damageSource = new DamageSource("immersiveweapons.bear_trap");
	Minecraft mc = Minecraft.getInstance();
	@Nullable
	private MobEntity entityliving;
	private PlayerEntity entitylivingPlayer;
	private Goal doNothingGoal;
	private UUID id;


	public BearTrapTileEntity() {
		super(DeferredRegistryHandler.BEAR_TRAP_TILE_ENTITY.get());
	}

	@Override
	public void tick() {
		final MobEntity trapped = this.getTrappedEntity();
		final PlayerEntity trappedPlayer = this.getTrappedPlayerEntity();

		if (!this.world.isRemote) {
			if (trapped != null) {
				// Entity has escaped
				if (!trapped.getBoundingBox().intersects(new AxisAlignedBB(this.pos)) || !trapped.isAlive()) {
					this.setTrappedEntity(null);
				}
			}

			if (trappedPlayer != null) {
				// Player has escaped
				if (!trappedPlayer.getBoundingBox().intersects(new AxisAlignedBB(this.pos)) || !trappedPlayer.isAlive()) {
					this.setTrappedPlayerEntity(null);
				} else {
					trappedPlayer.setMotionMultiplier(this.getBlockState(), new Vector3d(0.0F, 0.0D, 0.0F));
					mc.gameSettings.keyBindJump.setPressed(false);
					mc.gameSettings.keyBindForward.setPressed(false);
					mc.gameSettings.keyBindLeft.setPressed(false);
					mc.gameSettings.keyBindRight.setPressed(false);
					mc.gameSettings.keyBindBack.setPressed(false);
				}
			}
		}
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		if (nbt.hasUniqueId("trapped_entity")) {
			this.id = nbt.getUniqueId("trapped_entity");
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (this.entityliving != null && this.entityliving.isAlive()) {
			compound.putUniqueId("trapped_entity", this.entityliving.getUniqueID());
		}
		if (this.entitylivingPlayer != null && this.entitylivingPlayer.isAlive()) {
			compound.putUniqueId("trapped_entity", this.entitylivingPlayer.getUniqueID());
		}
		return compound;
	}

	public boolean setTrappedEntity(@Nullable MobEntity livingEntity) {
		if (this.hasTrappedEntity() && livingEntity != null) {
			return false;
		} else {

			if (livingEntity == null) {
				if (this.entityliving != null) {
					this.entityliving.goalSelector.removeGoal(this.doNothingGoal);
				}
				this.id = null;
				this.doNothingGoal = null;
			} else {
				livingEntity.attackEntityFrom(damageSource, 2);
				livingEntity.goalSelector.getRunningGoals().filter(PrioritizedGoal::isRunning).forEach(PrioritizedGoal::resetTask);
				livingEntity.goalSelector.addGoal(0, this.doNothingGoal = new DoNothingGoal(livingEntity, this));
			}

			this.entityliving = livingEntity;
			return true;
		}
	}

	public boolean setTrappedPlayerEntity(@Nullable PlayerEntity livingEntity) {
		if (this.hasTrappedPlayerEntity() && livingEntity != null) {
			return false;
		} else {

			if (livingEntity == null) {
				this.id = null;
			}

			this.entitylivingPlayer = livingEntity;
			return true;
		}
	}

	public MobEntity getTrappedEntity() {
		if (this.id != null && this.world instanceof ServerWorld) {
			Entity entity = ((ServerWorld) this.world).getEntityByUuid(this.id);
			this.id = null;
			if (entity instanceof MobEntity)
				this.setTrappedEntity((MobEntity) entity);
		}
		return this.entityliving;
	}

	public PlayerEntity getTrappedPlayerEntity() {
		if (this.id != null && this.world instanceof ServerWorld) {
			Entity entity = ((ServerWorld) this.world).getEntityByUuid(this.id);
			this.id = null;
			if (entity instanceof PlayerEntity)
				this.setTrappedPlayerEntity((PlayerEntity) entity);
		}
		return this.entitylivingPlayer;
	}

	public boolean hasTrappedEntity() {
		return this.getTrappedEntity() != null;
	}

	public boolean hasTrappedPlayerEntity() {
		return this.getTrappedPlayerEntity() != null;
	}

	public boolean isEntityTrapped(final MobEntity trappedEntity) {
		return this.getTrappedEntity() == trappedEntity;
	}

	public boolean isPlayerEntityTrapped(final PlayerEntity trappedEntity) {
		return this.getTrappedPlayerEntity() == trappedEntity;
	}

	class DoNothingGoal extends Goal {
		private final MobEntity trappedEntity;
		private final BearTrapTileEntity trap;

		public DoNothingGoal(MobEntity trappedEntity, BearTrapTileEntity trap) {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.trappedEntity = trappedEntity;
			this.trap = trap;
		}

		@Override
		public boolean shouldExecute() {
			return this.trap.isEntityTrapped(this.trappedEntity);
		}
	}
}