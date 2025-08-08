package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class TeleporterBlockEntity extends BlockEntity {

	@Nullable
	private BlockPos linkedTeleporterPos;
	@Nullable
	private ResourceLocation linkedTeleporterDimension;

	public TeleporterBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.TELEPORTER_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	public void setLinkedTeleporter(@Nullable BlockPos pos, @Nullable ResourceLocation dimension) {
		linkedTeleporterPos = pos;
		linkedTeleporterDimension = dimension;
	}

	@Nullable
	public BlockPos getLinkedTeleporterPos() {
		return linkedTeleporterPos;
	}

	@Nullable
	public ResourceKey<Level> getLinkedTeleporterDimension() {
		return linkedTeleporterDimension != null ? ResourceKey.create(Registries.DIMENSION, linkedTeleporterDimension) : null;
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);

		if (linkedTeleporterPos != null && linkedTeleporterDimension != null) {
			valueOutput.putLong("linkedTeleporterPos", linkedTeleporterPos.asLong());
			valueOutput.putString("linkedTeleporterDimension", linkedTeleporterDimension.toString());
		}
	}

	@Override
	protected void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);

		Long teleporterPosLong = valueInput.getLong("linkedTeleporterPos").orElse(null);
		String teleporterDimensionString = valueInput.getString("linkedTeleporterDimension").orElse(null);
		if (teleporterPosLong != null && teleporterDimensionString != null) {
			linkedTeleporterPos = BlockPos.of(teleporterPosLong);
			linkedTeleporterDimension = ResourceLocation.parse(teleporterDimensionString);
		}
	}
}