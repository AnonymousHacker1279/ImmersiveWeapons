package tech.anonymoushacker1279.immersiveweapons.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.List;

public class ChairEntity extends Entity {

	private BlockPos source;

	/**
	 * Constructor for ChairEntity.
	 *
	 * @param entityType the <code>EntityType</code> instance
	 * @param world      the <code>World</code> the entity is in
	 */
	public ChairEntity(EntityType<?> entityType, Level world) {
		super(entityType, world);
		source = BlockPos.ZERO;
	}

	/**
	 * Constructor for ChairEntity.
	 *
	 * @param world   the <code>World</code> the entity is in
	 * @param pos     the <code>BlockPos</code> the entity is at
	 * @param yOffset the Y offset to spawn at
	 */
	private ChairEntity(Level world, BlockPos pos, double yOffset) {
		this(EntityRegistry.CHAIR_ENTITY.get(), world);
		source = pos;
		setPos(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
	}

	/**
	 * Create the entity and mount the player to it.
	 *
	 * @param world   the <code>World</code> the entity is in
	 * @param pos     the <code>BlockPos</code> the entity is at
	 * @param yOffset the Y offset to spawn at
	 * @param player  the <code>PlayerEntity</code> interacting with the entity
	 * @return ActionResultType
	 */
	public static InteractionResult create(Level world, BlockPos pos, double yOffset, Player player) {
		if (!world.isClientSide) {
			List<ChairEntity> seats = world.getEntitiesOfClass(ChairEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
			if (seats.isEmpty()) {
				ChairEntity seat = new ChairEntity(world, pos, yOffset);
				world.addFreshEntity(seat);
				player.startRiding(seat, false);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	/**
	 * Runs once each tick.
	 */
	@Override
	public void tick() {
		super.tick();
		if (!level.isClientSide) {
			if (getPassengers().isEmpty() || level.isEmptyBlock(source)) {
				kill();
				level.updateNeighbourForOutputSignal(blockPosition(), level.getBlockState(blockPosition()).getBlock());
			}
		}
	}

	/**
	 * Check if this entity can be ridden by the supplied entity.
	 *
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
	protected void readAdditionalSaveData(CompoundTag nbt) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
	}

	/**
	 * Get the entity spawn packet.
	 *
	 * @return IPacket
	 */
	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}