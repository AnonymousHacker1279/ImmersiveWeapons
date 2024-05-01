package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

import java.util.EnumSet;
import java.util.UUID;

public class BearTrapBlockEntity extends BlockEntity implements EntityBlock {

	@Nullable
	private LivingEntity trappedEntity;
	@Nullable
	private Goal doNothingGoal;
	@Nullable
	private UUID entityUUID;

	/**
	 * Constructor for BearTrapBlockEntity.
	 */
	public BearTrapBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.BEAR_TRAP_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Runs once each tick. Handle trapping and releasing entities.
	 */
	public void tick(BlockPos blockPos) {
		if (trappedEntity == null && entityUUID != null && level instanceof ServerLevel serverLevel) {
			Entity entity = serverLevel.getEntity(entityUUID);
			if (entity instanceof LivingEntity livingEntity) {
				setTrappedEntity(livingEntity);
			}
		}

		if (trappedEntity != null) {
			// The entity has escaped
			if (!trappedEntity.getBoundingBox().intersects(new AABB(blockPos)) || !trappedEntity.isAlive()) {
				setTrappedEntity(null);
			}

			if (trappedEntity instanceof Player player) {
				player.makeStuckInBlock(getBlockState(), new Vec3(0.0F, 0.0D, 0.0F));
				if (level != null && level.isClientSide) {
					player.setDeltaMovement(0, 0, 0);
				}
			}
		}
	}

	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);

		if (tag.hasUUID("trapped_entity")) {
			entityUUID = tag.getUUID("trapped_entity");
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);

		if (trappedEntity != null && trappedEntity.isAlive()) {
			tag.putUUID("trapped_entity", trappedEntity.getUUID());
		}
	}

	@Override
	public void setRemoved() {
		if (trappedEntity != null && trappedEntity.isAlive()) {
			setTrappedEntity(null);
		}

		super.setRemoved();
	}

	@Nullable
	public LivingEntity getTrappedEntity() {
		return trappedEntity;
	}

	public void setTrappedEntity(@Nullable LivingEntity livingEntity) {
		if (livingEntity == null) {
			if (trappedEntity != null && doNothingGoal != null && trappedEntity instanceof Mob mob) {
				mob.goalSelector.removeGoal(doNothingGoal);
			}

			entityUUID = null;
			trappedEntity = null;
			doNothingGoal = null;

			return;
		}

		trappedEntity = livingEntity;
		if (trappedEntity instanceof Mob mob) {
			mob.goalSelector.getAvailableGoals().stream()
					.filter(WrappedGoal::isRunning)
					.forEach(goal -> mob.goalSelector.removeGoal(goal.getGoal()));
			mob.goalSelector.addGoal(0, doNothingGoal = new DoNothingGoal(mob, this));
		}
	}

	/**
	 * Check for trapped entities.
	 *
	 * @return boolean
	 */
	public boolean hasTrappedEntity() {
		return trappedEntity != null;
	}

	/**
	 * Check if a specific entity is trapped.
	 *
	 * @return boolean
	 */
	boolean isEntityTrapped(Mob trappedEntity) {
		return getTrappedEntity() == trappedEntity;
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