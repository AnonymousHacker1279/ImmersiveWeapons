package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MortarShellEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.LocalSoundPayload;

public class MortarBlock extends BasicOrientableBlock {

	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 2);
	public static final BooleanProperty LOADED = BooleanProperty.create("loaded");
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);

	/**
	 * Constructor for MortarBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public MortarBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(ROTATION, 0)
				.setValue(LOADED, false));
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateDefinition.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, FACING, LOADED);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockPlaceContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param getter           the <code>BlockGetter</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param collisionContext the <code>CollisionContext</code> of the block
	 * @return VoxelShape
	 */
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos,
	                           CollisionContext collisionContext) {

		return SHAPE;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (!level.isClientSide && hand.equals(InteractionHand.MAIN_HAND)) {
			ItemStack itemStack = player.getMainHandItem();
			// If the mortar is loaded and the player is holding flint and steel, fire the shell
			if (state.getValue(LOADED) && itemStack.getItem() == Items.FLINT_AND_STEEL) {
				if (!player.isCreative()) {
					itemStack.setDamageValue(itemStack.getDamageValue() - 1);
				}

				fire(level, pos, state, player);
				return ItemInteractionResult.CONSUME;

				// If the mortar is not loaded and the player is holding a mortar shell	, load the mortar
			} else if (!state.getValue(LOADED) && itemStack.getItem() == ItemRegistry.MORTAR_SHELL.get()) {
				level.setBlock(pos, state.setValue(LOADED, true), 3);

				if (!player.isCreative()) {
					itemStack.shrink(1);
				}

				return ItemInteractionResult.CONSUME;

				// If the player is crouching, not holding anything, and the mortar is loaded, remove the shell
				// and give it to the player
			} else if (itemStack.getItem() == Items.AIR && player.isCrouching() && state.getValue(LOADED)) {
				if (!player.isCreative()) {
					player.getInventory().add(new ItemStack(ItemRegistry.MORTAR_SHELL.get()));
				}

				level.setBlock(pos, state.setValue(LOADED, false), 3);

				return ItemInteractionResult.SUCCESS;

				// If the player isn't holding anything, cycle through the rotations
			} else if (itemStack.getItem() == Items.AIR) {
				if (state.getValue(ROTATION) < 2) {
					level.setBlock(pos, state.setValue(ROTATION, state.getValue(ROTATION) + 1), 3);
				} else {
					level.setBlock(pos, state.setValue(ROTATION, 0), 3);
				}
			}

			return ItemInteractionResult.SUCCESS;
		}

		return ItemInteractionResult.CONSUME_PARTIAL;
	}

	/**
	 * Runs when neighboring blocks change state.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param level    the <code>Level</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param block    the <code>Block</code> that is changing
	 * @param fromPos  the <code>BlockPos</code> of the changing block
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

		if (!level.isClientSide) {
			if (state.getValue(LOADED) && level.hasNeighborSignal(pos)) {
				fire(level, pos, state, null);
			}
		}
	}

	/**
	 * Fires a mortar shell and sends packets to tracking players.
	 *
	 * @param level the <code>Level</code> the block is in
	 * @param pos   the <code>BlockPos</code> the block is at
	 * @param state the <code>BlockState</code> of the block
	 */
	private void fire(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX(), pos.getY(), pos.getZ(),
					3, 0.0f, 0.2f, 0.0f, 0.0f);

			PacketDistributor.sendToPlayersTrackingChunk(serverLevel, level.getChunkAt(pos).getPos(), new LocalSoundPayload(pos, SoundEventRegistry.MORTAR_FIRE.get().getLocation(),
					SoundSource.BLOCKS, 1.0f, 1.0f, true));
		}

		level.setBlock(pos, state.setValue(LOADED, false), 3);
		level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(state));
		MortarShellEntity.create(level, pos, 1f, state, player);
	}
}