package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.blockentity.CommanderPedestalBlockEntity;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class CommanderPedestalBlock extends Block implements EntityBlock {

	private static final VoxelShape SHAPE = Stream.of(
			Block.box(5, 2, 5, 11, 14, 11),
			Shapes.join(Block.box(2, 0, 2, 14, 1, 14), Block.box(3, 1, 3, 13, 2, 13), BooleanOp.OR),
			Shapes.join(Block.box(3, 14, 3, 13, 15, 13), Block.box(2, 15, 2, 14, 16, 14), BooleanOp.OR)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

	public CommanderPedestalBlock(Properties properties) {
		super(properties);
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return SHAPE;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new CommanderPedestalBlockEntity(pPos, pState);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (hand != InteractionHand.MAIN_HAND) {
			return InteractionResult.PASS;
		}

		if (level.getBlockEntity(pos) instanceof CommanderPedestalBlockEntity blockEntity) {
			ItemStack itemInHand = player.getItemInHand(hand);

			if (itemInHand.isEmpty()) {
				blockEntity.removeItem();
				return InteractionResult.SUCCESS;
			} else {
				if (blockEntity.addItem(player.isCreative() ? itemInHand.copy() : itemInHand)) {
					return InteractionResult.CONSUME;
				}
			}
		}

		return InteractionResult.PASS;
	}
}