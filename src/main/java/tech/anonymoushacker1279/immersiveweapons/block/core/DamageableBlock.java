package tech.anonymoushacker1279.immersiveweapons.block.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import tech.anonymoushacker1279.immersiveweapons.blockentity.DamageableBlockEntity;

public abstract class DamageableBlock extends BasicOrientableBlock implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected final int maxHealth;
	protected final int stages;
	protected final Item repairItem;
	private final IntegerProperty damageStage;

	/**
	 * Constructor for DamageableBlock. These blocks have a limited health pool and are destroyed over time.
	 * This is used for blocks like wooden spikes, which slowly degrade as they deal damage.
	 *
	 * @param properties the <code>Properties</code> of the block
	 * @param maxHealth  the health of the block, or number of uses before it breaks
	 * @param stages     the number of visual stages the block will have before it breaks
	 * @param repairItem the <code>Item</code> that can repair the block
	 */
	public DamageableBlock(Properties properties, int maxHealth, int stages, Item repairItem, IntegerProperty damageStage) {
		super(properties);

		this.maxHealth = maxHealth;
		this.stages = stages;
		this.repairItem = repairItem;
		this.damageStage = damageStage;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(FACING, context.getHorizontalDirection())
				.setValue(damageStage, 0)
				.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new DamageableBlockEntity(blockPos, blockState, maxHealth, stages);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		// Handle repairs
		if (hand == InteractionHand.MAIN_HAND) {
			if (level.getBlockEntity(pos) instanceof DamageableBlockEntity damageable) {
				boolean didRepair = damageable.repair(player.getItemInHand(hand), repairItem, state, level, pos, player, damageStage);
				return didRepair ? ItemInteractionResult.sidedSuccess(level.isClientSide) : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
			}
		}

		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}
}