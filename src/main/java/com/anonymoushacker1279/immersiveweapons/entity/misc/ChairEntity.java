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

	public ChairEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	private ChairEntity(World world, BlockPos source, double yOffset) {
		this(DeferredRegistryHandler.CHAIR_ENTITY.get(), world);
		this.source = source;
		setPos(source.getX() + 0.5D, source.getY() + yOffset, source.getZ() + 0.5D);
	}

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

	@Override
	public void tick() {
		super.tick();
		if (this.source == null) {
			this.source = blockPosition();
		}
		if (!this.level.isClientSide) {
			if (getPassengers().isEmpty() || this.level.isEmptyBlock(this.source)) {
				remove();
				this.level.updateNeighbourForOutputSignal(blockPosition(), this.level.getBlockState(blockPosition()).getBlock());
			}
		}
	}

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

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}