package com.anonymoushacker1279.immersiveweapons.blockentity;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

public class BearTrapBlockEntity extends BlockEntity implements EntityBlock {

	public static final DamageSource damageSource = new DamageSource("immersiveweapons.bear_trap");
	@Nullable
	private Mob livingEntity;
	private Player playerEntity;
	private Goal doNothingGoal;
	private UUID id;

	/**
	 * Constructor for BearTrapBlockEntity.
	 */
	public BearTrapBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(DeferredRegistryHandler.BEAR_TRAP_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Runs once each tick. Handle trapping and releasing entities.
	 */
	public static void serverTick(BlockPos blockPos, BearTrapBlockEntity bearTrapBlockEntity) {
		Mob trapped = bearTrapBlockEntity.getTrappedEntity();
		Player trappedPlayer = bearTrapBlockEntity.getTrappedPlayerEntity();

		if (trapped != null) {
			// Entity has escaped
			if (!trapped.getBoundingBox().intersects(new AABB(blockPos)) || !trapped.isAlive()) {
				bearTrapBlockEntity.setTrappedEntity(null);
			}
		}

		if (trappedPlayer != null) {
			// Player has escaped
			if (!trappedPlayer.getBoundingBox().intersects(new AABB(blockPos)) || !trappedPlayer.isAlive()) {
				bearTrapBlockEntity.setTrappedPlayerEntity(null);
			} else {
				trappedPlayer.makeStuckInBlock(bearTrapBlockEntity.getBlockState(), new Vec3(0.0F, 0.0D, 0.0F));
				Minecraft.getInstance().options.keyJump.setDown(false);
				Minecraft.getInstance().options.keyUp.setDown(false);
				Minecraft.getInstance().options.keyLeft.setDown(false);
				Minecraft.getInstance().options.keyRight.setDown(false);
				Minecraft.getInstance().options.keyDown.setDown(false);
			}
		}
	}

	/**
	 * Load NBT data.
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(@NotNull CompoundTag nbt) {
		super.load(nbt);
		if (nbt.hasUUID("trapped_entity")) {
			id = nbt.getUUID("trapped_entity");
		}
	}

	/**
	 * Save NBT data.
	 * @param nbt the <code>CompoundNBT</code> to save
	 */
	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag nbt) {
		super.save(nbt);
		if (livingEntity != null && livingEntity.isAlive()) {
			nbt.putUUID("trapped_entity", livingEntity.getUUID());
		}
		if (playerEntity != null && playerEntity.isAlive()) {
			nbt.putUUID("trapped_entity", playerEntity.getUUID());
		}
		return nbt;
	}

	/**
	 * Set trapped entities
	 * @param mobEntity the <code>MobEntity</code> to trap
	 */
	public void setTrappedEntity(@Nullable Mob mobEntity) {
		if (mobEntity == null) {
			if (livingEntity != null) {
				livingEntity.goalSelector.removeGoal(doNothingGoal);
			}
			id = null;
			doNothingGoal = null;
		} else {
			mobEntity.hurt(damageSource, 2);
			mobEntity.goalSelector.getRunningGoals().filter(WrappedGoal::isRunning).forEach(WrappedGoal::stop);
			mobEntity.goalSelector.addGoal(0, doNothingGoal = new DoNothingGoal(mobEntity, this));
		}

		livingEntity = mobEntity;
	}

	/**
	 * Set trapped players
	 * @param playerEntity the <code>PlayerEntity</code> to trap
	 */
	public void setTrappedPlayerEntity(@Nullable Player playerEntity) {
		if (!hasTrappedPlayerEntity() && playerEntity != null) {
			id = null;
			this.playerEntity = playerEntity;
		}
	}

	/**
	 * Get trapped entities.
	 * @return MobEntity
	 */
	public Mob getTrappedEntity() {
		if (id != null && level instanceof ServerLevel) {
			Entity entity = ((ServerLevel) level).getEntity(id);
			id = null;
			if (entity instanceof Mob)
				setTrappedEntity((Mob) entity);
		}
		return livingEntity;
	}

	/**
	 * Get trapped players.
	 * @return PlayerEntity
	 */
	public Player getTrappedPlayerEntity() {
		if (id != null && level instanceof ServerLevel) {
			Entity entity = ((ServerLevel) level).getEntity(id);
			id = null;
			if (entity instanceof Player)
				setTrappedPlayerEntity((Player) entity);
		}
		return playerEntity;
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
	boolean isEntityTrapped(Mob trappedEntity) {
		return getTrappedEntity() == trappedEntity;
	}

	/**
	 * Create a block entity for the block.
	 * @param blockPos the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new BearTrapBlockEntity(blockPos, blockState);
	}

	static class DoNothingGoal extends Goal {
		private final Mob trappedEntity;
		private final BearTrapBlockEntity trap;

		/**
		 * Constructor for DoNothingGoal.
		 * @param trappedEntity the <code>MobEntity</code> instance
		 * @param trap the <code>BearTrapBlockEntity</code> instance
		 */
		DoNothingGoal(Mob trappedEntity, BearTrapBlockEntity trap) {
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