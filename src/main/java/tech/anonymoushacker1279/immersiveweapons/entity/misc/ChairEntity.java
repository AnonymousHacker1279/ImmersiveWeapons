package tech.anonymoushacker1279.immersiveweapons.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.List;

public class ChairEntity extends Entity {

	private BlockPos source;

	public ChairEntity(EntityType<?> entityType, Level level) {
		super(entityType, level);
		source = BlockPos.ZERO;
	}

	private ChairEntity(Level level, BlockPos pos, double yOffset) {
		this(EntityRegistry.CHAIR_ENTITY.get(), level);
		source = pos;
		setPos(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
	}

	public static InteractionResult create(Level level, BlockPos pos, double yOffset, Player player) {
		if (!level.isClientSide) {
			List<ChairEntity> seats = level.getEntitiesOfClass(ChairEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
			if (seats.isEmpty()) {
				ChairEntity seat = new ChairEntity(level, pos, yOffset);
				level.addFreshEntity(seat);
				player.startRiding(seat, false);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public void tick() {
		super.tick();
		if (level() instanceof ServerLevel serverLevel) {
			if (getPassengers().isEmpty() || level().isEmptyBlock(source)) {
				kill(serverLevel);
				level().updateNeighbourForOutputSignal(blockPosition(), level().getBlockState(blockPosition()).getBlock());
			}
		}
	}

	@Override
	protected boolean canRide(Entity entity) {
		return true;
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		return false;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
	}

}