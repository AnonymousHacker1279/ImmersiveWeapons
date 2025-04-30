package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.Objects;

public class MinutemanStatueBlockEntity extends AbstractStatueBlockEntity<MinutemanEntity> {

	public MinutemanStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.MINUTEMAN_STATUE_BLOCK_ENTITY.get(), blockPos, blockState, 16);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new MinutemanStatueBlockEntity(blockPos, blockState);
	}

	@Override
	protected MinutemanEntity createEntity(Level level) {
		return Objects.requireNonNull(EntityRegistry.MINUTEMAN_ENTITY.get().create(level, EntitySpawnReason.SPAWNER));
	}
}