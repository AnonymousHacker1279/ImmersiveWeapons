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

	/**
	 * Constructor for BearTrapTileEntity.
	 */
	public BearTrapTileEntity() {
		super(DeferredRegistryHandler.BEAR_TRAP_TILE_ENTITY.get());
	}

	/**
	 * Runs once each tick. Handle trapping and releasing entities.
	 */
	@Override
	public void tick() {
		MobEntity trapped = getTrappedEntity();
		PlayerEntity trappedPlayer = getTrappedPlayerEntity();

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

	/**
	 * Load NBT data.
	 * @param state the <code>BlockState</code> of the block
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if (nbt.hasUUID("trapped_entity")) {
			id = nbt.getUUID("trapped_entity");
		}
	}

	/**
	 * Save NBT data.
	 * @param nbt the <code>CompoundNBT</code> to save
	 */
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		if (entityliving != null && entityliving.isAlive()) {
			nbt.putUUID("trapped_entity", entityliving.getUUID());
		}
		if (entitylivingPlayer != null && entitylivingPlayer.isAlive()) {
			nbt.putUUID("trapped_entity", entitylivingPlayer.getUUID());
		}
		return nbt;
	}

	/**
	 * Set trapped entities
	 * @param mobEntity the <code>MobEntity</code> to trap
	 * @return boolean
	 */
	public boolean setTrappedEntity(@Nullable MobEntity mobEntity) {
		if (hasTrappedEntity() && mobEntity != null) {
			return false;
		} else {

			if (mobEntity == null) {
				if (entityliving != null) {
					entityliving.goalSelector.removeGoal(doNothingGoal);
				}
				id = null;
				doNothingGoal = null;
			} else {
				mobEntity.hurt(damageSource, 2);
				mobEntity.goalSelector.getRunningGoals().filter(PrioritizedGoal::isRunning).forEach(PrioritizedGoal::stop);
				mobEntity.goalSelector.addGoal(0, doNothingGoal = new DoNothingGoal(mobEntity, this));
			}

			entityliving = mobEntity;
			return true;
		}
	}

	/**
	 * Set trapped players
	 * @param playerEntity the <code>PlayerEntity</code> to trap
	 * @return boolean
	 */
	public boolean setTrappedPlayerEntity(@Nullable PlayerEntity playerEntity) {
		if (hasTrappedPlayerEntity() && playerEntity != null) {
			return false;
		} else {

			if (playerEntity == null) {
				id = null;
			}

			entitylivingPlayer = playerEntity;
			return true;
		}
	}

	/**
	 * Get trapped entities.
	 * @return MobEntity
	 */
	public MobEntity getTrappedEntity() {
		if (id != null && level instanceof ServerWorld) {
			Entity entity = ((ServerWorld) level).getEntity(id);
			id = null;
			if (entity instanceof MobEntity)
				setTrappedEntity((MobEntity) entity);
		}
		return entityliving;
	}

	/**
	 * Get trapped players.
	 * @return PlayerEntity
	 */
	public PlayerEntity getTrappedPlayerEntity() {
		if (id != null && level instanceof ServerWorld) {
			Entity entity = ((ServerWorld) level).getEntity(id);
			id = null;
			if (entity instanceof PlayerEntity)
				setTrappedPlayerEntity((PlayerEntity) entity);
		}
		return entitylivingPlayer;
	}

	/**
	 * Check for trapped entities.
	 * @return boolean
	 */
	public boolean hasTrappedEntity() {
		return getTrappedEntity() != null;
	}

	/**
	 * Check for trapped players.
	 * @return boolean
	 */
	public boolean hasTrappedPlayerEntity() {
		return getTrappedPlayerEntity() != null;
	}

	/**
	 * Check if a specific entity is trapped.
	 * @return boolean
	 */
	boolean isEntityTrapped(MobEntity trappedEntity) {
		return getTrappedEntity() == trappedEntity;
	}

	static class DoNothingGoal extends Goal {
		private final MobEntity trappedEntity;
		private final BearTrapTileEntity trap;

		/**
		 * Constructor for DoNothingGoal.
		 * @param trappedEntity the <code>MobEntity</code> instance
		 * @param trap the <code>BearTrapTileEntity</code> instance
		 */
		DoNothingGoal(MobEntity trappedEntity, BearTrapTileEntity trap) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.trappedEntity = trappedEntity;
			this.trap = trap;
		}

		/**
		 * Check if entities can use the goal.
		 * @return boolean
		 */
		@Override
		public boolean canUse() {
			return trap.isEntityTrapped(trappedEntity);
		}
	}
}