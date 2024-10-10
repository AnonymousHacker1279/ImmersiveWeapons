package tech.anonymoushacker1279.immersiveweapons.block.barbed_wire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import tech.anonymoushacker1279.immersiveweapons.block.core.DamageableBlock;

public class BarbedWireBlock extends DamageableBlock implements BarbedWireUtils {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty DAMAGE_STAGE = IntegerProperty.create("damage_stage", 0, 2);
	protected static int soundCooldown = 0;

	/**
	 * Constructor for BarbedWireBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public BarbedWireBlock(Properties properties) {
		super(properties, 212, 2, Items.IRON_INGOT, DAMAGE_STAGE);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING, DAMAGE_STAGE);
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
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		handleEntityContact(state, level, pos, entity);
	}
}