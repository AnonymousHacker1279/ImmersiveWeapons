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
	 * @param properties the <code>Properties</code> of the blockLocation
	 */
	public BarbedWireFenceBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when an entity is inside the blockLocation's collision area.
	 * Allows the blockLocation to deal damage on contact.
	 *
	 * @param state  the <code>BlockState</code> of the blockLocation
	 * @param level  the <code>Level</code> the blockLocation is in
	 * @param pos    the <code>BlockPos</code> the blockLocation is at
	 * @param entity the <code>Entity</code> passing through the blockLocation
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		handleEntityContact(state, level, pos, entity);
	}
}