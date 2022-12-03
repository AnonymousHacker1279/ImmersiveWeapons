package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public class StarstormCrystalBlock extends AmethystClusterBlock {

	public StarstormCrystalBlock(int size, int offset, Properties properties) {
		super(size, offset, properties);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		// If the block is being destroyed by a piston that is above it, drop a Starstorm Ingot
		if (level.getBlockState(pos.above()).getBlock() == Blocks.PISTON) {
			Block.popResource(level, pos, new ItemStack(DeferredRegistryHandler.STARSTORM_INGOT.get()));

			// At this point, this block should not be dropped. Kill the dropped entity in this position.
			// It can't be removed from the loot table because it's generated before this is called.
			List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos));
			// Ensure it is a Starstorm Crystal item
			for (ItemEntity entity : entities) {
				if (entity.getItem().getItem() == DeferredRegistryHandler.STARSTORM_CRYSTAL.get().asItem()) {
					entity.kill();
				}
			}

			// There is a 15% chance to spawn a Starmite
			if (GeneralUtilities.getRandomNumber(0.0f, 1.0f) <= 0.15) {
				StarmiteEntity entity = DeferredRegistryHandler.STARMITE_ENTITY.get().create(level);
				if (entity != null) {
					entity.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
					level.addFreshEntity(entity);
				}
			}
		}
	}
}