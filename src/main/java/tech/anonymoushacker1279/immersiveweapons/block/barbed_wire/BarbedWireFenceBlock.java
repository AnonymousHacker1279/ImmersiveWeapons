package tech.anonymoushacker1279.immersiveweapons.block.barbed_wire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BarbedWireFenceBlock extends FenceBlock implements BarbedWireUtils {

	/**
	 * Constructor for BarbedWireFenceBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public BarbedWireFenceBlock(Properties properties) {
		super(properties);
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