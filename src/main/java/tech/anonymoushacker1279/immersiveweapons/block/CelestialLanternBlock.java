package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.blockentity.CelestialLanternBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.world.level.saveddata.CelestialLanternData;

public class CelestialLanternBlock extends LanternBlock implements EntityBlock {

	protected static final VoxelShape AABB = Shapes.or(
			Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
			Block.box(5.0D, 10.0D, 5.0D, 11.0D, 11.0D, 11.0D));

	public CelestialLanternBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return AABB;
	}

	@Override
	public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
		super.destroy(pLevel, pPos, pState);
		if (pLevel instanceof ServerLevel serverLevel) {
			CelestialLanternData.getData(serverLevel.getServer()).removeLantern(pPos);
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new CelestialLanternBlockEntity(blockPos, blockState);
	}
}