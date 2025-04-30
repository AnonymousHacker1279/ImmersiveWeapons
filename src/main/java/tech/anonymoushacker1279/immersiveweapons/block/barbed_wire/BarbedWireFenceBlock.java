package tech.anonymoushacker1279.immersiveweapons.block.barbed_wire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BarbedWireFenceBlock extends FenceBlock implements BarbedWireUtils {

	public BarbedWireFenceBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		handleEntityContact(state, level, pos, entity);
	}
}