package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.menu.CelestialAltarMenu;

import javax.annotation.Nullable;

public class CelestialAltarBlock extends BasicOrientableBlock {

	private static final Component CONTAINER_NAME = Component.translatable("container.immersiveweapons.celestial_altar");

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
		return LecternBlock.SHAPE_COLLISION;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case NORTH -> LecternBlock.SHAPE_NORTH;
			case SOUTH -> LecternBlock.SHAPE_SOUTH;
			case EAST -> LecternBlock.SHAPE_EAST;
			case WEST -> LecternBlock.SHAPE_WEST;
			default -> LecternBlock.SHAPE_COMMON;
		};
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