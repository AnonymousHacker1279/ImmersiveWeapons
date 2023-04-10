package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class WoodenSpikesBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15D, 14D, 15D);

	/**
	 * Constructor for WoodenSpikesBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public WoodenSpikesBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param reader           the <code>BlockGetter</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>CollisionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
	                           CollisionContext selectionContext) {

		return SHAPE;
	}

	/**
	 * Determines if skylight should pass through the block.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>BlockGetter</code> for the block
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @return boolean
	 */
	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	/**
	 * Runs when an entity is inside the block's collision area.
	 * Allows the block to deal damage on contact.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param level  the <code>Level</code> the block is in
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity) {
			entity.makeStuckInBlock(state, new Vec3(0.85F, 0.80D, 0.85F));
			if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double deltaX = Math.abs(entity.getX() - entity.xOld);
				double deltaZ = Math.abs(entity.getZ() - entity.zOld);
				if (deltaX >= (double) 0.003F || deltaZ >= (double) 0.003F) {
					entity.hurt(IWDamageSources.WOODEN_SPIKES, 1.5F);

					if (entity instanceof Player player && player.isCreative()) {
						return;
					}

					if (GeneralUtilities.getRandomNumber(0.0f, 1.0f) <= 0.15f) {
						((LivingEntity) entity).addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT.get(),
								200, 0, true, false));
					}
				}
			}
		}
	}
}