package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.BiomesAndDimensions;

import java.util.List;

public class MinutemanStatueBlockEntity extends AbstractStatueBlockEntity {

	/**
	 * Constructor for MinutemanStatueBlockEntity.
	 */
	public MinutemanStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(DeferredRegistryHandler.MINUTEMAN_STATUE_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Runs once each tick. Handle scanning and spawning entities.
	 */
	public void tick(Level level, BlockPos blockPos) {
		if (level.getBiome(blockPos).is(BiomesAndDimensions.BATTLEFIELD) && cooldown == 0) {
			List<MinutemanEntity> listOfMinutemenInArea = level.getEntitiesOfClass(MinutemanEntity.class,
					new AABB(getBlockPos().getX() - 48,
							getBlockPos().getY() - 16,
							getBlockPos().getZ() - 48,
							getBlockPos().getX() + 48,
							getBlockPos().getY() + 16,
							getBlockPos().getZ() + 48));

			scannedEntities = listOfMinutemenInArea.size();

			if (scannedEntities <= 16) {
				MinutemanEntity minutemanEntity = DeferredRegistryHandler.MINUTEMAN_ENTITY.get().create(level);
				if (minutemanEntity != null) {
					attemptSpawnEntity(minutemanEntity);
				}
			}
		} else if (cooldown > 0) {
			cooldown--;
		}
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new MinutemanStatueBlockEntity(blockPos, blockState);
	}
}