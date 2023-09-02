package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.CelestialLanternBlock;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

import java.util.ArrayList;
import java.util.List;

public class CelestialLanternBlockEntity extends BlockEntity implements EntityBlock {

	/**
	 * Constructor for CelestialLanternBlockEntity.
	 */
	public CelestialLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.CELESTIAL_LANTERN_BLOCK_ENTITY.get(), blockPos, blockState);
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
		return new CelestialLanternBlockEntity(blockPos, blockState);
	}

	/**
	 * Save NBT data.
	 *
	 * @param tag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);

		ListTag listTag = new ListTag();
		CelestialLanternBlock.ALL_TILTROS_LANTERNS
				.forEach(pos -> listTag.add(NbtUtils.writeBlockPos(pos)));

		tag.put("tiltros_lanterns", listTag);
	}

	/**
	 * Load NBT data.
	 *
	 * @param tag the <code>CompoundTag</code> to load
	 */
	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		ListTag SAVED_LANTERNS = (ListTag) tag.get("tiltros_lanterns");
		if (SAVED_LANTERNS != null) {
			if (CelestialLanternBlock.ALL_TILTROS_LANTERNS.isEmpty() && !SAVED_LANTERNS.isEmpty()) {
				List<BlockPos> blockPosList = new ArrayList<>(SAVED_LANTERNS.size());
				SAVED_LANTERNS.forEach(tag1 -> blockPosList.add(NbtUtils.readBlockPos((CompoundTag) tag1)));

				CelestialLanternBlock.ALL_TILTROS_LANTERNS = blockPosList;
			}
		}
	}
}