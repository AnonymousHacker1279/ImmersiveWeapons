package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.Objects;

public class MedicStatueBlockEntity extends AbstractStatueBlockEntity<FieldMedicEntity> {

	public MedicStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.MEDIC_STATUE_BLOCK_ENTITY.get(), blockPos, blockState, 2);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new MedicStatueBlockEntity(blockPos, blockState);
	}

	@Override
	protected FieldMedicEntity createEntity(Level level) {
		return Objects.requireNonNull(EntityRegistry.FIELD_MEDIC_ENTITY.get().create(level, EntitySpawnReason.SPAWNER));
	}
}