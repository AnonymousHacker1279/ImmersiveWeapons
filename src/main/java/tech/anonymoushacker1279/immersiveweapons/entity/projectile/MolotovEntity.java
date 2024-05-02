package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class MolotovEntity extends AdvancedThrowableItemProjectile {

	private static final byte VANILLA_IMPACT_STATUS_ID = 3;
	static final BlockState airState = Blocks.AIR.defaultBlockState();
	static final BlockState fireState = Blocks.FIRE.defaultBlockState();

	public MolotovEntity(EntityType<? extends MolotovEntity> entityType, Level level) {
		super(entityType, level);
	}

	public MolotovEntity(Level level, LivingEntity livingEntity) {
		super(EntityRegistry.MOLOTOV_COCKTAIL_ENTITY.get(), livingEntity, level);
	}

	public MolotovEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.MOLOTOV_COCKTAIL_ENTITY.get(), level, x, y, z);
	}

	/**
	 * Get the entity spawn packet.
	 *
	 * @return IPacket
	 */
	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	/**
	 * ProjectileItemEntity::setItem uses this to save storage space.
	 * It only stores the itemStack if the itemStack is not the default item.
	 *
	 * @return Item
	 */
	@Override
	protected Item getDefaultItem() {
		return ItemRegistry.MOLOTOV_COCKTAIL.get();
	}

	/**
	 * Runs when an entity/block is hit.
	 *
	 * @param rayTraceResult the <code>RayTraceResult</code> instance
	 */
	@Override
	protected void onHit(HitResult rayTraceResult) {
		super.onHit(rayTraceResult);
		if (!level().isClientSide) {
			level().broadcastEntityEvent(this, VANILLA_IMPACT_STATUS_ID);

			BlockPos currentPosition = blockPosition();

			// Build a list of blocks where the fire will be placed.
			List<BlockPos> firePositionList = new ArrayList<>(15);
			firePositionList.add(adjustFirePosition(currentPosition, 4));
			firePositionList.add(adjustFirePosition(currentPosition.east(), 3));
			firePositionList.add(adjustFirePosition(currentPosition.east(2), 3));
			firePositionList.add(adjustFirePosition(currentPosition.east().north(), 3));
			firePositionList.add(adjustFirePosition(currentPosition.east().south(), 3));
			firePositionList.add(adjustFirePosition(currentPosition.west(), 3));
			firePositionList.add(adjustFirePosition(currentPosition.west(2), 3));
			firePositionList.add(adjustFirePosition(currentPosition.west().north(), 3));
			firePositionList.add(adjustFirePosition(currentPosition.west().south(), 3));
			firePositionList.add(adjustFirePosition(currentPosition.north(), 3));
			firePositionList.add(adjustFirePosition(currentPosition.north(2), 3));
			firePositionList.add(adjustFirePosition(currentPosition.south(), 3));
			firePositionList.add(adjustFirePosition(currentPosition.south(2), 3));

			for (BlockPos pos : firePositionList) {
				if (!level().getBlockState(pos.below()).isAir() && level().getBlockState(pos).isAir()) {
					level().setBlockAndUpdate(pos, fireState);
				}
			}

			kill();
		}
	}

	/**
	 * Move the position down until a solid block is under it.
	 *
	 * @param pos          the <code>BlockPos</code> being moved
	 * @param distanceDown the number of blocks to try moving down
	 * @return BlockPos
	 */
	private BlockPos adjustFirePosition(BlockPos pos, int distanceDown) {
		BlockPos movedPosition = pos;
		for (int i = 0; i <= distanceDown; i++) {
			if (level().getBlockState(movedPosition) != airState) {
				return movedPosition.above();
			}
			movedPosition = movedPosition.below();
		}
		return pos;
	}

	/**
	 * Handle entity events.
	 *
	 * @param statusID the <code>byte</code> containing status ID
	 */
	@Override
	public void handleEntityEvent(byte statusID) {
		if (statusID == VANILLA_IMPACT_STATUS_ID) {
			level().playLocalSound(getX(), getY(), getZ(), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 1f, 1f, false);
			kill();
		}
	}
}