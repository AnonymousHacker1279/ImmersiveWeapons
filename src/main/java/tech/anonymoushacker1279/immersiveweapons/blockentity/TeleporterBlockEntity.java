package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);

		if (linkedTeleporterPos != null && linkedTeleporterDimension != null) {
			tag.putLong("linkedTeleporterPos", linkedTeleporterPos.asLong());
			tag.putString("linkedTeleporterDimension", linkedTeleporterDimension.toString());
		}
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		if (tag.contains("linkedTeleporterPos") && tag.contains("linkedTeleporterDimension")) {
			linkedTeleporterPos = BlockPos.of(tag.getLong("linkedTeleporterPos"));
			linkedTeleporterDimension = ResourceLocation.parse(tag.getString("linkedTeleporterDimension"));
		}
	}
}