package tech.anonymoushacker1279.immersiveweapons.blockentity;

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
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.EnumSet;
import java.util.UUID;

public class BearTrapBlockEntity extends BlockEntity implements EntityBlock {

	public static final DamageSource damageSource = new DamageSource("immersiveweapons.bear_trap");
	@Nullable
	private Mob trappedMobEntity;
	@Nullable
	private Player trappedPlayerEntity;
	@Nullable
	private Goal doNothingGoal;
	@Nullable
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
	public void tick(BlockPos blockPos) {
		Mob trapped = getTrappedMobEntity();
		Player trappedPlayer = getTrappedPlayerEntity();

		if (trapped != null) {
			// Entity has escaped
			if (!trapped.getBoundingBox().intersects(new AABB(blockPos)) || !trapped.isAlive()) {
				setTrappedMobEntity(null);
			}
		}

		if (trappedPlayer != null) {
			// Player has escaped
			if (!trappedPlayer.getBoundingBox().intersects(new AABB(blockPos)) || !trappedPlayer.isAlive()) {
				setTrappedPlayerEntity(null);
				trappedPlayerEntity = null;
			} else {
				trappedPlayer.makeStuckInBlock(getBlockState(), new Vec3(0.0F, 0.0D, 0.0F));
				if (level != null && level.isClientSide) {
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
	 *
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		if (nbt.hasUUID("trapped_entity")) {
			id = nbt.getUUID("trapped_entity");
		}
	}

	/**
	 * Save NBT data.
	 *
	 * @param pTag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		if (trappedMobEntity != null && trappedMobEntity.isAlive()) {
			pTag.putUUID("trapped_entity", trappedMobEntity.getUUID());
		}
		if (trappedPlayerEntity != null && trappedPlayerEntity.isAlive()) {
			pTag.putUUID("trapped_entity", trappedPlayerEntity.getUUID());
		}
	}

	/**
	 * Get trapped entities.
	 *
	 * @return MobEntity
	 */
	@Nullable
	public Mob getTrappedMobEntity() {
		if (id != null && level instanceof ServerLevel serverLevel) {
			Entity entity = serverLevel.getEntity(id);
			id = null;
			if (entity instanceof Mob mob) {
				setTrappedMobEntity(mob);
			}
		}
		return trappedMobEntity;
	}

	/**
	 * Set trapped entities
	 *
	 * @param mobEntity the <code>MobEntity</code> to trap
	 */
	public void setTrappedMobEntity(@Nullable Mob mobEntity) {
		if (mobEntity == null) {
			if (trappedMobEntity != null && doNothingGoal != null) {
				trappedMobEntity.goalSelector.removeGoal(doNothingGoal);
			}
			id = null;
			doNothingGoal = null;
		} else {
			mobEntity.hurt(damageSource, 2);
			mobEntity.goalSelector.getRunningGoals().filter(WrappedGoal::isRunning).forEach(WrappedGoal::stop);
			mobEntity.goalSelector.addGoal(0, doNothingGoal = new DoNothingGoal(mobEntity, this));
		}

		trappedMobEntity = mobEntity;
	}

	/**
	 * Get trapped players.
	 *
	 * @return PlayerEntity
	 */
	@Nullable
	public Player getTrappedPlayerEntity() {
		if (id != null && level instanceof ServerLevel) {
			Entity entity = ((ServerLevel) level).getEntity(id);
			id = null;
			if (entity instanceof Player)
				setTrappedPlayerEntity((Player) entity);
		}
		return trappedPlayerEntity;
	}

	/**
	 * Set trapped players
	 *
	 * @param playerEntity the <code>PlayerEntity</code> to trap
	 */
	public void setTrappedPlayerEntity(@Nullable Player playerEntity) {
		if (hasTrappedPlayerEntity() && playerEntity != null) {
			id = null;
			trappedPlayerEntity = playerEntity;
		}
	}

	/**
	 * Check for trapped entities.
	 *
	 * @return boolean
	 */
	public boolean hasTrappedEntity() {
		return getTrappedMobEntity() != null;
	}

	/**
	 * Check for trapped players.
	 *
	 * @return boolean
	 */
	public boolean hasTrappedPlayerEntity() {
		return getTrappedPlayerEntity() == null;
	}

	/**
	 * Check if a specific entity is trapped.
	 *
	 * @return boolean
	 */
	boolean isEntityTrapped(Mob trappedEntity) {
		return getTrappedMobEntity() == trappedEntity;
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new BearTrapBlockEntity(blockPos, blockState);
	}

	static class DoNothingGoal extends Goal {
		private final Mob trappedEntity;
		private final BearTrapBlockEntity trap;

		/**
		 * Constructor for DoNothingGoal.
		 *
		 * @param trappedEntity the <code>MobEntity</code> instance
		 * @param trap          the <code>BearTrapBlockEntity</code> instance
		 */
		DoNothingGoal(Mob trappedEntity, BearTrapBlockEntity trap) {
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
			this.trappedEntity = trappedEntity;
			this.trap = trap;
		}

		/**
		 * Check if entities can use the goal.
		 *
		 * @return boolean
		 */
		@Override
		public boolean canUse() {
			return trap.isEntityTrapped(trappedEntity);
		}
	}
}