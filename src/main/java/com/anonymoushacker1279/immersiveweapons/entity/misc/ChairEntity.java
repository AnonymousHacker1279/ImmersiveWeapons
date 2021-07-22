package com.anonymoushacker1279.immersiveweapons.entity.misc;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class ChairEntity extends Entity {

	private BlockPos source;

	/**
	 * Constructor for ChairEntity.
	 * @param entityType the <code>EntityType</code> instance
	 * @param world the <code>World</code> the entity is in
	 */
	public ChairEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * Constructor for ChairEntity.
	 * @param world the <code>World</code> the entity is in
	 * @param pos the <code>BlockPos</code> the entity is at
	 * @param yOffset the Y offset to spawn at
	 */
	private ChairEntity(World world, BlockPos pos, double yOffset) {
		this(DeferredRegistryHandler.CHAIR_ENTITY.get(), world);
		source = pos;
		setPos(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
	}

	/**
	 * Create the entity and mount the player to it.
	 * @param world the <code>World</code> the entity is in
	 * @param pos the <code>BlockPos</code> the entity is at
	 * @param yOffset the Y offset to spawn at
	 * @param player the <code>PlayerEntity</code> interacting with the entity
	 * @return ActionResultType
	 */
	public static ActionResultType create(World world, BlockPos pos, double yOffset, PlayerEntity player) {
		if (!world.isClientSide) {
			List<ChairEntity> seats = world.getEntitiesOfClass(ChairEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
			if (seats.isEmpty()) {
				ChairEntity seat = new ChairEntity(world, pos, yOffset);
				world.addFreshEntity(seat);
				player.startRiding(seat, false);
			}
		}
		return ActionResultType.SUCCESS;
	}

	/**
	 * Runs once each tick.
	 */
	@Override
	public void tick() {
		super.tick();
		if (source == null) {
			source = blockPosition();
		}
		if (!level.isClientSide) {
			if (getPassengers().isEmpty() || level.isEmptyBlock(source)) {
				remove();
				level.updateNeighbourForOutputSignal(blockPosition(), level.getBlockState(blockPosition()).getBlock());
			}
		}
	}

	/**
	 * Check if this entity can be ridden by the supplied entity.
	 * @param entity the <code>Entity</code> being checked
	 * @return boolean
	 */
	@Override
	protected boolean canRide(Entity entity) {
		return true;
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT nbt) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
	}

	/**
	 * Get the entity spawn packet.
	 * @return IPacket
	 */
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}