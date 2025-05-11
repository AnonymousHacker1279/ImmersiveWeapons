package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.menu.CelestialAltarMenu;

import javax.annotation.Nullable;
import java.util.Map;

public class CelestialAltarBlock extends BasicOrientableBlock {

	private static final Component CONTAINER_NAME = Component.translatable("container.immersiveweapons.celestial_altar");
	private static final VoxelShape SHAPE_COLLISION = Shapes.or(Block.column(16.0, 0.0, 2.0), Block.column(8.0, 2.0, 14.0));
	private static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(
			Shapes.or(
					Block.boxZ(16.0, 10.0, 14.0, 1.0, 5.333333),
					Block.boxZ(16.0, 12.0, 16.0, 5.333333, 9.666667),
					Block.boxZ(16.0, 14.0, 18.0, 9.666667, 14.0),
					SHAPE_COLLISION
			)
	);

	public CelestialAltarBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_COLLISION;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES.get(state.getValue(FACING));
	}

	@Nullable
	@Override
	protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((id, inventory, player)
				-> new CelestialAltarMenu(id, inventory, ContainerLevelAccess.create(level, pos), player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)), CONTAINER_NAME);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			player.openMenu(getMenuProvider(state, level, pos));
			return InteractionResult.CONSUME;
		}
	}
}