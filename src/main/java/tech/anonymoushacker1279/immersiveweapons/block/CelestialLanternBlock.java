package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.blockentity.CelestialLanternBlockEntity;

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
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new CelestialLanternBlockEntity(blockPos, blockState);
	}
}