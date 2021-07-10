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
		final MobEntity trapped = getTrappedEntity();
		final PlayerEntity trappedPlayer = getTrappedPlayerEntity();

		if (level != null && !level.isClientSide) {
			if (trapped != null) {
				// Entity has escaped
				if (!trapped.getBoundingBox().intersects(new AxisAlignedBB(worldPosition)) || !trapped.isAlive()) {
					setTrappedEntity(null);
				}
			}

			if (trappedPlayer != null) {
				// Player has escaped
				if (!trappedPlayer.getBoundingBox().intersects(new AxisAlignedBB(worldPosition)) || !trappedPlayer.isAlive()) {
					setTrappedPlayerEntity(null);
				} else {
					trappedPlayer.makeStuckInBlock(getBlockState(), new Vector3d(0.0F, 0.0D, 0.0F));
					Minecraft.getInstance().options.keyJump.setDown(false);
					Minecraft.getInstance().options.keyUp.setDown(false);
					Minecraft.getInstance().options.keyLeft.setDown(false);
					Minecraft.getInstance().options.keyRight.setDown(false);
					Minecraft.getInstance().options.keyDown.setDown(false);
				}
			}
		}
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if (nbt.hasUUID("trapped_entity")) {
			id = nbt.getUUID("trapped_entity");
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		if (entityliving != null && entityliving.isAlive()) {
			compound.putUUID("trapped_entity", entityliving.getUUID());
		}
		if (entitylivingPlayer != null && entitylivingPlayer.isAlive()) {
			compound.putUUID("trapped_entity", entitylivingPlayer.getUUID());
		}
		return compound;
	}

	public boolean setTrappedEntity(@Nullable MobEntity livingEntity) {
		if (hasTrappedEntity() && livingEntity != null) {
			return false;
		} else {

			if (livingEntity == null) {
				if (entityliving != null) {
					entityliving.goalSelector.removeGoal(doNothingGoal);
				}
				id = null;
				doNothingGoal = null;
			} else {
				livingEntity.hurt(damageSource, 2);
				livingEntity.goalSelector.getRunningGoals().filter(PrioritizedGoal::isRunning).forEach(PrioritizedGoal::stop);
				livingEntity.goalSelector.addGoal(0, doNothingGoal = new DoNothingGoal(livingEntity, this));
			}

			entityliving = livingEntity;
			return true;
		}
	}

	public boolean setTrappedPlayerEntity(@Nullable PlayerEntity livingEntity) {
		if (hasTrappedPlayerEntity() && livingEntity != null) {
			return false;
		} else {

			if (livingEntity == null) {
				id = null;
			}

			entitylivingPlayer = livingEntity;
			return true;
		}
	}

	public MobEntity getTrappedEntity() {
		if (id != null && level instanceof ServerWorld) {
			Entity entity = ((ServerWorld) level).getEntity(id);
			id = null;
			if (entity instanceof MobEntity)
				setTrappedEntity((MobEntity) entity);
		}
		return entityliving;
	}

	public PlayerEntity getTrappedPlayerEntity() {
		if (id != null && level instanceof ServerWorld) {
			Entity entity = ((ServerWorld) level).getEntity(id);
			id = null;
			if (entity instanceof PlayerEntity)
				setTrappedPlayerEntity((PlayerEntity) entity);
		}
		return entitylivingPlayer;
	}

	public boolean hasTrappedEntity() {
		return getTrappedEntity() != null;
	}

	public boolean hasTrappedPlayerEntity() {
		return getTrappedPlayerEntity() != null;
	}

	public boolean isEntityTrapped(final MobEntity trappedEntity) {
		return getTrappedEntity() == trappedEntity;
	}

	static class DoNothingGoal extends Goal {
		private final MobEntity trappedEntity;
		private final BearTrapTileEntity trap;

		public DoNothingGoal(MobEntity trappedEntity, BearTrapTileEntity trap) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.trappedEntity = trappedEntity;
			this.trap = trap;
		}

		@Override
		public boolean canUse() {
			return trap.isEntityTrapped(trappedEntity);
		}
	}
}