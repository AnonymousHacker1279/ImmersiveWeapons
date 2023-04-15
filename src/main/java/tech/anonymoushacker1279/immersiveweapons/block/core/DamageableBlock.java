package tech.anonymoushacker1279.immersiveweapons.block.core;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import tech.anonymoushacker1279.immersiveweapons.blockentity.DataBlockEntity;

public abstract class DamageableBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private final int maxHealth;
	private int health;
	private final int stages;
	private final Item repairItem;

	/**
	 * Constructor for DamageableBlock. These blocks have a limited health pool and are destroyed over time.
	 * This is used for blocks like wooden spikes, which slowly degrade as they deal damage.
	 *
	 * @param properties the <code>Properties</code> of the block
	 * @param maxHealth  the health of the block, or number of uses before it breaks
	 * @param stages     the number of visual stages the block will have before it breaks
	 * @param repairItem the <code>Item</code> that can repair the block
	 */
	public DamageableBlock(Properties properties, int maxHealth, int stages, Item repairItem) {
		super(properties);

		this.maxHealth = maxHealth;
		this.stages = stages;
		this.health = maxHealth;
		this.repairItem = repairItem;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}


	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new DataBlockEntity(blockPos, blockState);
	}

	/**
	 * Damage the block, which takes care of stage changes and destruction.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @param level the <code>Level</code> the block is in
	 * @param pos   the <code>BlockPos</code> the block is at
	 */
	protected void takeDamage(BlockState state, Level level, BlockPos pos, IntegerProperty damageStage) {
		if (health > 0) {
			health--;
			int stage = stages - Mth.ceil(((double) health / ((double) maxHealth / stages)));
			level.setBlockAndUpdate(pos, state.setValue(damageStage, stage));

			if (level.getBlockEntity(pos) instanceof DataBlockEntity entity) {
				entity.editValue("health", health);
				entity.editValue("stage", stage);
			}
		} else {
			level.destroyBlock(pos, true);
			health = maxHealth;

			if (level.getBlockEntity(pos) instanceof DataBlockEntity entity) {
				entity.clearContent();
			}
		}
	}

	/**
	 * Repair the block if the given stack matches this block's repair item. Each use
	 * of the repair item will repair the block by one stage.
	 *
	 * @param repairStack the <code>ItemStack</code> to repair the block with
	 * @param state       the <code>BlockState</code> of the block
	 * @param level       the <code>Level</code> the block is in
	 * @param pos         the <code>BlockPos</code> the block is at
	 * @param damageStage the <code>IntegerProperty</code> of the block's damage stage
	 * @return true if the block was repaired, false otherwise
	 */
	protected boolean repair(ItemStack repairStack, BlockState state, Level level, BlockPos pos, Player player, IntegerProperty damageStage) {
		if (level.isClientSide) {
			return false;
		}

		int currentStage = state.getValue(damageStage);
		if (repairStack.getItem() == repairItem && currentStage > 0 && currentStage <= stages) {
			if (health < maxHealth) {
				health += (maxHealth / stages);
				level.setBlockAndUpdate(pos, state.setValue(damageStage, currentStage - 1));

				if (level.getBlockEntity(pos) instanceof DataBlockEntity entity) {
					entity.editValue("health", health);
					entity.editValue("stage", currentStage - 1);
				}

				if (!player.isCreative()) {
					repairStack.shrink(1);
				}

				return true;
			}
		} else if (health != maxHealth) {
			health = maxHealth;

			if (level.getBlockEntity(pos) instanceof DataBlockEntity entity) {
				entity.editValue("health", health);
				entity.editValue("stage", stages);
			}

			if (!player.isCreative()) {
				repairStack.shrink(1);
			}

			return true;
		}
		return false;
	}
}