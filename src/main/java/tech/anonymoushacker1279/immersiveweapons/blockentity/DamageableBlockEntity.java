package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class DamageableBlockEntity extends BlockEntity {

	private int maxHealth;
	private int health;
	private int stages;
	private int currentStage;

	public DamageableBlockEntity(BlockPos blockPos, BlockState blockState, int maxHealth, int stages) {
		this(blockPos, blockState);

		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.stages = stages;
	}

	public DamageableBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.DAMAGEABLE_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Load NBT data.
	 *
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);

		maxHealth = nbt.getInt("maxHealth");
		health = nbt.getInt("health");
		stages = nbt.getInt("stages");
		currentStage = nbt.getInt("currentStage");
	}

	/**
	 * Save NBT data.
	 *
	 * @param tag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);

		tag.putInt("maxHealth", maxHealth);
		tag.putInt("health", health);
		tag.putInt("stages", stages);
		tag.putInt("currentStage", currentStage);
	}

	/**
	 * Get the entity update packet.
	 *
	 * @return ClientboundBlockEntityDataPacket
	 */
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * Get the update tag.
	 *
	 * @return CompoundTag
	 */
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		saveAdditional(tag);
		return tag;
	}

	/**
	 * Damage the block, which takes care of stage changes and destruction.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @param level the <code>Level</code> the block is in
	 * @param pos   the <code>BlockPos</code> the block is at
	 */
	public void takeDamage(BlockState state, Level level, BlockPos pos, IntegerProperty damageStage) {
		if (health > 0) {
			health--;

			double healthPerStage = maxHealth / (double) (stages + 1);
			int stage = stages - (int) Math.ceil((health - healthPerStage) / healthPerStage);
			currentStage = Math.min(Math.max(stage, 0), stages);

			level.setBlockAndUpdate(pos, state.setValue(damageStage, currentStage));
		} else {
			level.destroyBlock(pos, false);
		}
	}

	/**
	 * Repair the block if the given stack matches this block's repair item. Each use
	 * of the repair item will repair the block by one stage.
	 *
	 * @param repairStack the <code>ItemStack</code> to attempt to repair the block with
	 * @param repairItem  the <code>Item</code> which this block is repairable with
	 * @param state       the <code>BlockState</code> of the block
	 * @param level       the <code>Level</code> the block is in
	 * @param pos         the <code>BlockPos</code> the block is at
	 * @param damageStage the <code>IntegerProperty</code> of the block's damage stage
	 * @return true if the block was repaired, false otherwise
	 */
	public boolean repair(ItemStack repairStack, Item repairItem, BlockState state, Level level, BlockPos pos, Player player, IntegerProperty damageStage) {
		if (level.isClientSide) {
			return false;
		}

		currentStage = state.getValue(damageStage);
		if (repairStack.getItem() == repairItem && currentStage > 0 && currentStage <= stages) {
			if (health < maxHealth) {
				health += maxHealth / (double) (stages + 1);
				level.setBlockAndUpdate(pos, state.setValue(damageStage, currentStage - 1));

				if (!player.isCreative()) {
					repairStack.shrink(1);
				}

				return true;
			}
		} else if (health != maxHealth) {
			health = maxHealth;

			if (!player.isCreative()) {
				repairStack.shrink(1);
			}

			return true;
		}
		return false;
	}

	/**
	 * Calculate the damage dealt by the block based on the starting damage and the reduction per stage.
	 *
	 * @param startingDamage              the starting damage a block will deal (like wooden spikes)
	 * @param reductionPerStageMultiplier the reduction in damage per stage (i.e. 33% less)
	 * @return the adjusted damage
	 */
	public float calculateDamage(float startingDamage, float reductionPerStageMultiplier) {
		return startingDamage * (float) Math.pow(1 - reductionPerStageMultiplier, currentStage);
	}
}