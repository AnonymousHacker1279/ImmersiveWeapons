package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.GameEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class PanicAlarmBlockEntity extends BlockEntity implements EntityBlock {

	private boolean isPowered = false;
	private int cooldown = 0;

	/// Constructor for PanicAlarmBlockEntity.
	public PanicAlarmBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.PANIC_ALARM_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/// Runs once each tick. Handle playing alarm sounds.
	public void tick(Level level, BlockPos blockPos) {

		if (isPowered && cooldown-- <= 0) {
			for (ServerPlayer serverPlayer : ((ServerLevel) level).getPlayers(player -> player.blockPosition()
					.distSqr(blockPos) <= Math.pow(IWConfigs.SERVER.panicAlarmRange.getAsInt(), 2))) {

				serverPlayer.playSound(SoundEventRegistry.PANIC_ALARM_SOUND.get(), 1.0F, 1.0F);

				level.gameEvent(GameEventRegistry.PANIC_ALARM_TRIGGER, blockPos, GameEvent.Context.of(getBlockState()));
			}

			setCooldown(40);
		}
	}

	/// Create a block entity for the block.
	///
	/// @param blockPos   the `BlockPos` the block is at
	/// @param blockState the `BlockState` of the block
	/// @return BlockEntity
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PanicAlarmBlockEntity(blockPos, blockState);
	}

	/// Get the power status.
	///
	/// @return boolean
	public boolean isPowered() {
		return isPowered;
	}

	/// Set the power status.
	public void setPowered(boolean state) {
		isPowered = state;
		setChanged();
	}

	public void setCooldown(int delay) {
		cooldown = delay;
		setChanged();
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);

		valueOutput.putInt("cooldown", cooldown);
		valueOutput.putBoolean("isPowered", isPowered);
	}

	@Override
	public void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);

		cooldown = valueInput.getIntOr("cooldown", 0);
		isPowered = valueInput.getBooleanOr("isPowered", false);
	}
}